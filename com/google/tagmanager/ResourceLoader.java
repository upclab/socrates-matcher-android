package com.google.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.analytics.containertag.proto.Serving.OptionalResource;
import com.google.analytics.containertag.proto.Serving.Resource;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.tagmanager.LoadCallback.Failure;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

class ResourceLoader implements Runnable {
    private static final String CTFE_URL_PREFIX = "/r?id=";
    private static final String CTFE_URL_SUFFIX = "&v=a50788154";
    private static final String PREVIOUS_CONTAINER_VERSION_QUERY_NAME = "pv";
    @VisibleForTesting
    static final String SDK_VERSION = "a50788154";
    private LoadCallback<Resource> mCallback;
    private final NetworkClientFactory mClientFactory;
    private final String mContainerId;
    private final Context mContext;
    private volatile CtfeHost mCtfeHost;
    private volatile String mCtfeUrlPathAndQuery;
    private final String mDefaultCtfeUrlPathAndQuery;
    private volatile String mPreviousVersion;

    public ResourceLoader(Context context, String containerId, CtfeHost ctfeHost) {
        this(context, containerId, new NetworkClientFactory(), ctfeHost);
    }

    @VisibleForTesting
    ResourceLoader(Context context, String containerId, NetworkClientFactory factory, CtfeHost ctfeHost) {
        this.mContext = context;
        this.mClientFactory = factory;
        this.mContainerId = containerId;
        this.mCtfeHost = ctfeHost;
        this.mDefaultCtfeUrlPathAndQuery = CTFE_URL_PREFIX + containerId;
        this.mCtfeUrlPathAndQuery = this.mDefaultCtfeUrlPathAndQuery;
        this.mPreviousVersion = null;
    }

    public void run() {
        if (this.mCallback == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.mCallback.startLoad();
        loadResource();
    }

    private boolean okToLoad() {
        NetworkInfo network = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (network != null && network.isConnected()) {
            return true;
        }
        Log.m610v("...no network connectivity");
        return false;
    }

    void setLoadCallback(LoadCallback<Resource> callback) {
        this.mCallback = callback;
    }

    private void loadResource() {
        if (okToLoad()) {
            Log.m610v("Start loading resource from network ...");
            String url = getCtfeUrl();
            NetworkClient networkClient = this.mClientFactory.createNetworkClient();
            try {
                try {
                    OptionalResource resource = OptionalResource.parseFrom(networkClient.getInputStream(url), ProtoExtensionRegistry.getRegistry());
                    Log.m610v("Successfully loaded resource: " + resource);
                    if (!resource.hasResource()) {
                        Log.m610v("No change for container: " + this.mContainerId);
                    }
                    this.mCallback.onSuccess(resource.hasResource() ? resource.getResource() : null);
                    Log.m610v("Load resource from network finished.");
                } catch (IOException e) {
                    Log.m613w("Error when parsing downloaded resources from url: " + url + " " + e.getMessage(), e);
                    this.mCallback.onFailure(Failure.SERVER_ERROR);
                    networkClient.close();
                }
            } catch (FileNotFoundException e2) {
                Log.m612w("No data is retrieved from the given url: " + url + ". Make sure container_id: " + this.mContainerId + " is correct.");
                this.mCallback.onFailure(Failure.SERVER_ERROR);
            } catch (IOException e3) {
                Log.m613w("Error when loading resources from url: " + url + " " + e3.getMessage(), e3);
                this.mCallback.onFailure(Failure.IO_ERROR);
            } finally {
                networkClient.close();
            }
        } else {
            this.mCallback.onFailure(Failure.NOT_AVAILABLE);
        }
    }

    @VisibleForTesting
    String getCtfeUrl() {
        String url = this.mCtfeHost.getCtfeServerAddress() + this.mCtfeUrlPathAndQuery + CTFE_URL_SUFFIX;
        if (!(this.mPreviousVersion == null || this.mPreviousVersion.trim().equals(StringUtils.EMPTY))) {
            url = url + "&pv=" + this.mPreviousVersion;
        }
        if (PreviewManager.getInstance().getPreviewMode().equals(PreviewMode.CONTAINER_DEBUG)) {
            return url + "&gtm_debug=x";
        }
        return url;
    }

    @VisibleForTesting
    void setCtfeURLPathAndQuery(String urlPathAndQuery) {
        if (urlPathAndQuery == null) {
            this.mCtfeUrlPathAndQuery = this.mDefaultCtfeUrlPathAndQuery;
            return;
        }
        Log.m604d("Setting CTFE URL path: " + urlPathAndQuery);
        this.mCtfeUrlPathAndQuery = urlPathAndQuery;
    }

    @VisibleForTesting
    void setPreviousVersion(String version) {
        Log.m604d("Setting previous container version: " + version);
        this.mPreviousVersion = version;
    }
}
