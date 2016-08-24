package com.adsdk.sdk.banner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.AdRequest;
import com.adsdk.sdk.BannerAd;
import com.adsdk.sdk.RequestBannerAd;
import com.adsdk.sdk.Util;
import com.adsdk.sdk.mraid.MoPubView;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Timer;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeConstants;

public class AdView extends FrameLayout {
    public static final int LIVE = 0;
    public static final int TEST = 1;
    private AdListener adListener;
    private boolean animation;
    private boolean includeLocation;
    private int isAccessCoarseLocation;
    private int isAccessFineLocation;
    private boolean isInternalBrowser;
    private Thread loadContentThread;
    private LocationManager locationManager;
    private BannerAdView mBannerView;
    private Context mContext;
    protected boolean mIsInForeground;
    private MoPubView mMoPubview;
    private BroadcastReceiver mScreenStateReceiver;
    private String mUserAgent;
    private String publisherId;
    private Timer reloadTimer;
    private AdRequest request;
    private String requestURL;
    private BannerAd response;
    private final Runnable showContent;
    private int telephonyPermission;
    private final Handler updateHandler;
    private InputStream xml;

    /* renamed from: com.adsdk.sdk.banner.AdView.1 */
    class C00291 implements Runnable {
        C00291() {
        }

        public void run() {
            AdView.this.showContent();
        }
    }

    /* renamed from: com.adsdk.sdk.banner.AdView.2 */
    class C00302 extends BroadcastReceiver {
        C00302() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                if (AdView.this.mIsInForeground) {
                    AdView.this.pause();
                }
            } else if (intent.getAction().equals("android.intent.action.USER_PRESENT") && AdView.this.mIsInForeground) {
                AdView.this.resume();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.banner.AdView.3 */
    class C00313 implements Runnable {
        C00313() {
        }

        public void run() {
            RequestBannerAd requestAd;
            if (AdView.this.xml == null) {
                requestAd = new RequestBannerAd();
            } else {
                requestAd = new RequestBannerAd(AdView.this.xml);
            }
            try {
                AdView.this.response = (BannerAd) requestAd.sendRequest(AdView.this.getRequest());
                if (AdView.this.response != null) {
                    AdView.this.updateHandler.post(AdView.this.showContent);
                }
            } catch (Throwable e) {
                AdView.this.notifyLoadAdFailed(e);
            }
            AdView.this.loadContentThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.banner.AdView.4 */
    class C00324 implements UncaughtExceptionHandler {
        C00324() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            AdView.this.loadContentThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.banner.AdView.5 */
    class C00335 implements Runnable {
        C00335() {
        }

        public void run() {
            if (AdView.this.adListener != null) {
                AdView.this.adListener.noAdFound();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.banner.AdView.6 */
    class C00346 implements Runnable {
        C00346() {
        }

        public void run() {
            if (AdView.this.adListener != null) {
                AdView.this.adListener.noAdFound();
            }
        }
    }

    public void setWidth(int width) {
    }

    public void setHeight(int width) {
    }

    public AdView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.includeLocation = false;
        this.isInternalBrowser = false;
        this.requestURL = null;
        this.mContext = null;
        this.updateHandler = new Handler();
        this.showContent = new C00291();
        this.mContext = context;
        if (attributes != null) {
            int count = attributes.getAttributeCount();
            for (int i = LIVE; i < count; i += TEST) {
                String name = attributes.getAttributeName(i);
                if (name.equals("publisherId")) {
                    this.publisherId = attributes.getAttributeValue(i);
                } else if (name.equals("request_url")) {
                    this.requestURL = attributes.getAttributeValue(i);
                } else if (name.equals("animation")) {
                    this.animation = attributes.getAttributeBooleanValue(i, false);
                } else if (name.equals("includeLocation")) {
                    this.includeLocation = attributes.getAttributeBooleanValue(i, false);
                }
            }
        }
        initialize(context);
    }

    public AdView(Context context, String requestURL, String publisherId) {
        this(context, requestURL, publisherId, false, false);
    }

    public AdView(Context context, String requestURL, InputStream xml, String publisherId, boolean includeLocation, boolean animation) {
        this(context, xml, requestURL, publisherId, includeLocation, animation);
    }

    public AdView(Context context, InputStream xml, String requestURL, String publisherId, boolean includeLocation, boolean animation) {
        super(context);
        this.includeLocation = false;
        this.isInternalBrowser = false;
        this.requestURL = null;
        this.mContext = null;
        this.updateHandler = new Handler();
        this.showContent = new C00291();
        this.xml = xml;
        this.requestURL = requestURL;
        this.mContext = context;
        this.publisherId = publisherId;
        this.includeLocation = includeLocation;
        this.animation = animation;
        initialize(context);
    }

    public AdView(Context context, String requestURL, String publisherId, boolean includeLocation, boolean animation) {
        this(context, requestURL, publisherId, includeLocation, animation, null);
    }

    public AdView(Context context, String requestURL, String publisherId, boolean includeLocation, boolean animation, AdListener listener) {
        super(context);
        this.includeLocation = false;
        this.isInternalBrowser = false;
        this.requestURL = null;
        this.mContext = null;
        this.updateHandler = new Handler();
        this.showContent = new C00291();
        this.requestURL = requestURL;
        this.mContext = context;
        this.publisherId = publisherId;
        this.includeLocation = includeLocation;
        this.animation = animation;
        this.adListener = listener;
        initialize(context);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        this.mContext.registerReceiver(this.mScreenStateReceiver, filter);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterScreenStateBroadcastReceiver();
    }

    private Location getLocation() {
        if (this.locationManager != null) {
            if (this.isAccessFineLocation == 0 && this.locationManager.isProviderEnabled("gps")) {
                return this.locationManager.getLastKnownLocation("gps");
            }
            if (this.isAccessCoarseLocation == 0 && this.locationManager.isProviderEnabled("network")) {
                return this.locationManager.getLastKnownLocation("network");
            }
        }
        return null;
    }

    public int getRefreshRate() {
        if (this.response != null) {
            return this.response.getRefresh();
        }
        return -1;
    }

    private AdRequest getRequest() {
        if (this.request == null) {
            this.request = new AdRequest();
            if (this.telephonyPermission == 0) {
                this.request.setDeviceId(((TelephonyManager) getContext().getSystemService("phone")).getDeviceId());
            } else {
                this.request.setDeviceId(Util.getDeviceId(this.mContext));
            }
            this.request.setPublisherId(this.publisherId);
            this.request.setUserAgent(this.mUserAgent);
            this.request.setUserAgent2(Util.buildUserAgent());
        }
        Location location = null;
        if (this.includeLocation) {
            location = getLocation();
        }
        if (location != null) {
            this.request.setLatitude(location.getLatitude());
            this.request.setLongitude(location.getLongitude());
        } else {
            this.request.setLatitude(0.0d);
            this.request.setLongitude(0.0d);
        }
        this.request.setType(LIVE);
        this.request.setRequestURL(this.requestURL);
        return this.request;
    }

    private void initialize(Context context) {
        this.mUserAgent = Util.getDefaultUserAgentString(getContext());
        registerScreenStateBroadcastReceiver();
        this.locationManager = null;
        this.telephonyPermission = context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE");
        this.isAccessFineLocation = context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
        this.isAccessCoarseLocation = context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
        if (this.isAccessFineLocation == 0 || this.isAccessCoarseLocation == 0) {
            this.locationManager = (LocationManager) getContext().getSystemService("location");
        }
    }

    public boolean isInternalBrowser() {
        return this.isInternalBrowser;
    }

    private void registerScreenStateBroadcastReceiver() {
        this.mScreenStateReceiver = new C00302();
        IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
        filter.addAction("android.intent.action.USER_PRESENT");
        this.mContext.registerReceiver(this.mScreenStateReceiver, filter);
    }

    private void loadContent() {
        if (this.loadContentThread == null) {
            this.loadContentThread = new Thread(new C00313());
            this.loadContentThread.setUncaughtExceptionHandler(new C00324());
            this.loadContentThread.start();
        }
    }

    public void loadNextAd() {
        loadContent();
    }

    private void notifyNoAd() {
        this.updateHandler.post(new C00335());
    }

    private void notifyLoadAdFailed(Throwable e) {
        this.updateHandler.post(new C00346());
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == 0) {
            this.mIsInForeground = true;
            resume();
            return;
        }
        this.mIsInForeground = false;
        pause();
    }

    public void pause() {
        if (this.reloadTimer != null) {
            try {
                this.reloadTimer.cancel();
                this.reloadTimer = null;
            } catch (Exception e) {
            }
        }
    }

    private void showContent() {
        if (this.mMoPubview != null) {
            this.mMoPubview.destroy();
            removeView(this.mMoPubview);
        }
        if (this.mBannerView != null) {
            removeView(this.mBannerView);
        }
        if (this.response.getType() == TEST || this.response.getType() == 0) {
            this.mBannerView = new BannerAdView(this.mContext, this.response, this.animation, this.adListener);
            addView(this.mBannerView);
        }
        if (this.response.getType() == 7) {
            this.mMoPubview = new MoPubView(this.mContext);
            addView(this.mMoPubview, new LayoutParams(-2, (int) ((50.0f * this.mContext.getResources().getDisplayMetrics().density) + 0.5f)));
            com.adsdk.sdk.mraid.AdView m = new com.adsdk.sdk.mraid.AdView(this.mContext, this.mMoPubview, this.response);
            this.mMoPubview.setAdListener(this.adListener);
            m.setAdUnitId(StringUtils.EMPTY);
            m.loadAd();
        }
        if (this.response.getType() == 2) {
            notifyNoAd();
        }
        startReloadTimer();
    }

    public void release() {
        unregisterScreenStateBroadcastReceiver();
        if (this.mMoPubview != null) {
            this.mMoPubview.destroy();
        }
    }

    private void unregisterScreenStateBroadcastReceiver() {
        try {
            this.mContext.unregisterReceiver(this.mScreenStateReceiver);
        } catch (Exception e) {
        }
    }

    public void resume() {
        if (this.reloadTimer != null) {
            this.reloadTimer.cancel();
            this.reloadTimer = null;
        }
        this.reloadTimer = new Timer();
        if (this.response != null && this.response.getRefresh() > 0) {
            startReloadTimer();
        } else if (this.response == null || (this.mMoPubview == null && this.mBannerView == null)) {
            loadContent();
        }
    }

    public void setAdListener(AdListener bannerListener) {
        this.adListener = bannerListener;
        if (this.mMoPubview != null) {
            this.mMoPubview.setAdListener(bannerListener);
        }
        if (this.mBannerView != null) {
            this.mBannerView.setAdListener(bannerListener);
        }
    }

    public void setInternalBrowser(boolean isInternalBrowser) {
        this.isInternalBrowser = isInternalBrowser;
    }

    private void startReloadTimer() {
        if (this.reloadTimer != null && this.response.getRefresh() > 0) {
            int refreshTime = this.response.getRefresh() * DateTimeConstants.MILLIS_PER_SECOND;
            this.reloadTimer.schedule(new ReloadTask(this), (long) refreshTime);
        }
    }
}
