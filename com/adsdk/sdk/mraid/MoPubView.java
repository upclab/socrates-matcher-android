package com.adsdk.sdk.mraid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebViewDatabase;
import android.widget.FrameLayout;
import com.adsdk.sdk.AdListener;
import java.util.HashMap;

public class MoPubView extends FrameLayout {
    public static final int DEFAULT_LOCATION_PRECISION = 6;
    private AdListener mAdListener;
    protected AdView mAdView;
    protected BaseAdapter mAdapter;
    private Context mContext;
    private boolean mIsInForeground;
    private LocationAwareness mLocationAwareness;
    private int mLocationPrecision;
    private boolean mPreviousAutorefreshSetting;
    private BroadcastReceiver mScreenStateReceiver;

    public enum LocationAwareness {
        LOCATION_AWARENESS_NORMAL,
        LOCATION_AWARENESS_TRUNCATED,
        LOCATION_AWARENESS_DISABLED
    }

    public MoPubView(Context context) {
        this(context, null);
    }

    public MoPubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPreviousAutorefreshSetting = false;
        this.mContext = context;
        this.mIsInForeground = getVisibility() == 0;
        this.mLocationAwareness = LocationAwareness.LOCATION_AWARENESS_NORMAL;
        this.mLocationPrecision = DEFAULT_LOCATION_PRECISION;
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
        if (WebViewDatabase.getInstance(context) == null) {
            Log.e("MoPub", "Disabling MoPub. Local cache file is inaccessible so MoPub will fail if we try to create a WebView. Details of this Android bug found at:http://code.google.com/p/android/issues/detail?id=10789");
        }
    }

    public void loadAd() {
        if (this.mAdView != null) {
            this.mAdView.loadAd();
        }
    }

    public void destroy() {
        if (this.mAdView != null) {
            this.mAdView.cleanup();
            this.mAdView = null;
        }
        if (this.mAdapter != null) {
            this.mAdapter.invalidate();
            this.mAdapter = null;
        }
    }

    protected void loadFailUrl() {
        if (this.mAdView != null) {
            this.mAdView.loadFailUrl();
        }
    }

    protected void loadNativeSDK(HashMap<String, String> paramsHash) {
        if (this.mAdapter != null) {
            this.mAdapter.invalidate();
        }
        String type = (String) paramsHash.get("X-Adtype");
        this.mAdapter = BaseAdapter.getAdapterForType(type);
        if (this.mAdapter != null) {
            Log.i("MoPub", "Loading native adapter for type: " + type);
            this.mAdapter.init(this, (String) paramsHash.get("X-Nativeparams"));
            this.mAdapter.loadAd();
            return;
        }
        Log.i("MoPub", "Couldn't load native adapter. Trying next ad...");
        loadFailUrl();
    }

    protected void registerClick() {
        if (this.mAdView != null) {
            this.mAdView.registerClick();
            adClicked();
        }
    }

    protected void onWindowVisibilityChanged(int visibility) {
        if (this.mAdView != null) {
            if (visibility == 0) {
                Log.d("MoPub", "Ad Unit (" + this.mAdView.getAdUnitId() + ") going visible: enabling refresh");
                this.mIsInForeground = true;
                this.mAdView.setAutorefreshEnabled(true);
                return;
            }
            Log.d("MoPub", "Ad Unit (" + this.mAdView.getAdUnitId() + ") going invisible: disabling refresh");
            this.mIsInForeground = false;
            this.mAdView.setAutorefreshEnabled(false);
        }
    }

    protected void adWillLoad(String url) {
        Log.d("MoPub", "adWillLoad: " + url);
    }

    protected void adLoaded() {
        Log.d("MoPub", "adLoaded");
        if (this.mAdListener != null) {
            this.mAdListener.adLoadSucceeded(null);
        }
    }

    protected void adFailed() {
        if (this.mAdListener != null) {
            this.mAdListener.noAdFound();
        }
    }

    protected void adPresentedOverlay() {
        if (this.mAdListener != null) {
            this.mAdListener.adShown(null, true);
        }
    }

    protected void adClosed() {
        if (this.mAdListener != null) {
            this.mAdListener.adClosed(null, true);
        }
    }

    protected void adClicked() {
        if (this.mAdListener != null) {
            this.mAdListener.adClicked();
        }
    }

    protected void nativeAdLoaded() {
        if (this.mAdView != null) {
            this.mAdView.scheduleRefreshTimerIfEnabled();
        }
        adLoaded();
    }

    protected void adAppeared() {
        if (this.mAdView != null) {
            this.mAdView.adAppeared();
        }
    }

    public void customEventDidLoadAd() {
        if (this.mAdView != null) {
            this.mAdView.customEventDidLoadAd();
        }
    }

    public void customEventDidFailToLoadAd() {
        if (this.mAdView != null) {
            this.mAdView.customEventDidFailToLoadAd();
        }
    }

    public void customEventActionWillBegin() {
        if (this.mAdView != null) {
            this.mAdView.customEventActionWillBegin();
        }
    }

    public void setAdUnitId(String adUnitId) {
        if (this.mAdView != null) {
            this.mAdView.setAdUnitId(adUnitId);
        }
    }

    public void setKeywords(String keywords) {
        if (this.mAdView != null) {
            this.mAdView.setKeywords(keywords);
        }
    }

    public String getKeywords() {
        return this.mAdView != null ? this.mAdView.getKeywords() : null;
    }

    public void setLocation(Location location) {
        if (this.mAdView != null) {
            this.mAdView.setLocation(location);
        }
    }

    public Location getLocation() {
        return this.mAdView != null ? this.mAdView.getLocation() : null;
    }

    public int getAdWidth() {
        return this.mAdView != null ? this.mAdView.getAdWidth() : 0;
    }

    public int getAdHeight() {
        return this.mAdView != null ? this.mAdView.getAdHeight() : 0;
    }

    public String getResponseString() {
        return this.mAdView != null ? this.mAdView.getResponseString() : null;
    }

    public void setClickthroughUrl(String url) {
        if (this.mAdView != null) {
            this.mAdView.setClickthroughUrl(url);
        }
    }

    public String getClickthroughUrl() {
        return this.mAdView != null ? this.mAdView.getClickthroughUrl() : null;
    }

    public Activity getActivity() {
        return (Activity) this.mContext;
    }

    public void setLocationAwareness(LocationAwareness awareness) {
        this.mLocationAwareness = awareness;
    }

    public LocationAwareness getLocationAwareness() {
        return this.mLocationAwareness;
    }

    public void setLocationPrecision(int precision) {
        if (precision < 0) {
            precision = 0;
        }
        this.mLocationPrecision = precision;
    }

    public int getLocationPrecision() {
        return this.mLocationPrecision;
    }

    public void setAutorefreshEnabled(boolean enabled) {
        if (this.mAdView != null) {
            this.mAdView.setAutorefreshEnabled(enabled);
        }
    }

    public boolean getAutorefreshEnabled() {
        if (this.mAdView != null) {
            return this.mAdView.getAutorefreshEnabled();
        }
        Log.d("MoPub", "Can't get autorefresh status for destroyed MoPubView. Returning false.");
        return false;
    }

    public void setAdContentView(View view) {
        if (this.mAdView != null) {
            this.mAdView.setAdContentView(view);
        }
    }

    public void setAdListener(AdListener adListener) {
        this.mAdListener = adListener;
    }

    public void forceRefresh() {
        if (this.mAdapter != null) {
            this.mAdapter.invalidate();
            this.mAdapter = null;
        }
        if (this.mAdView != null) {
            this.mAdView.forceRefresh();
        }
    }
}
