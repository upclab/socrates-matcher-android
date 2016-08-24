package com.adsdk.sdk.mraid;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout.LayoutParams;
import com.adsdk.sdk.BannerAd;
import com.adsdk.sdk.Const;
import com.adsdk.sdk.banner.InAppWebView;
import com.adsdk.sdk.mraid.MoPubView.LocationAwareness;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTimeConstants;

public class AdView extends WebView {
    public static final String AD_ORIENTATION_BOTH = "b";
    public static final String AD_ORIENTATION_LANDSCAPE_ONLY = "l";
    public static final String AD_ORIENTATION_PORTRAIT_ONLY = "p";
    public static final String DEVICE_ORIENTATION_LANDSCAPE = "l";
    public static final String DEVICE_ORIENTATION_PORTRAIT = "p";
    public static final String DEVICE_ORIENTATION_SQUARE = "s";
    public static final String DEVICE_ORIENTATION_UNKNOWN = "u";
    public static final String EXTRA_AD_CLICK_DATA = "com.mopub.intent.extra.AD_CLICK_DATA";
    private static final int MINIMUM_REFRESH_TIME_MILLISECONDS = 10000;
    private static final LayoutParams WRAP_AND_CENTER_LAYOUT_PARAMS;
    private String mAdOrientation;
    private String mAdUnitId;
    private boolean mAutorefreshEnabled;
    private String mClickthroughUrl;
    private String mFailUrl;
    private final Handler mHandler;
    private int mHeight;
    private String mImpressionUrl;
    private boolean mIsDestroyed;
    private boolean mIsLoading;
    private String mKeywords;
    private Location mLocation;
    protected MoPubView mMoPubView;
    private String mRedirectUrl;
    private Handler mRefreshHandler;
    private Runnable mRefreshRunnable;
    private int mRefreshTimeMilliseconds;
    private BannerAd mResponse;
    private String mResponseString;
    private String mUrl;
    private String mUserAgent;
    private int mWidth;

    /* renamed from: com.adsdk.sdk.mraid.AdView.1 */
    class C00421 implements Runnable {
        C00421() {
        }

        public void run() {
            AdView.this.loadAd();
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.AdView.2 */
    class C00442 implements OnTouchListener {
        C00442() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return event.getAction() == 2;
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.AdView.3 */
    class C00453 implements Runnable {
        C00453() {
        }

        public void run() {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(AdView.this.mClickthroughUrl);
            httpget.addHeader(HttpHeaders.USER_AGENT, AdView.this.mUserAgent);
            try {
                httpclient.execute(httpget);
            } catch (ClientProtocolException e) {
                Log.i("MoPub", "Click tracking failed: " + AdView.this.mClickthroughUrl);
            } catch (IOException e2) {
                Log.i("MoPub", "Click tracking failed: " + AdView.this.mClickthroughUrl);
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        }
    }

    private class AdWebViewClient extends WebViewClient {
        private AdWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean isIntentSafe = false;
            AdView adView = (AdView) view;
            if (url.startsWith("mopub://")) {
                Uri uri = Uri.parse(url);
                String host = uri.getHost();
                if (host.equals("finishLoad")) {
                    adView.adDidLoad();
                } else if (host.equals("close")) {
                    adView.adDidClose();
                } else if (host.equals("failLoad")) {
                    adView.loadFailUrl();
                } else if (host.equals("custom")) {
                    adView.handleCustomIntentFromUri(uri);
                }
            } else if (url.startsWith("tel:") || url.startsWith("voicemail:") || url.startsWith("sms:") || url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("google.streetview:")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.addFlags(268435456);
                try {
                    AdView.this.getContext().startActivity(intent);
                    AdView.this.registerClick();
                } catch (ActivityNotFoundException e) {
                    Log.w("MoPub", "Could not handle intent with URI: " + url + ". Is this intent unsupported on your phone?");
                }
            } else {
                if (url.startsWith("market://")) {
                    if (AdView.this.getContext().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(url)), 0).size() > 0) {
                        isIntentSafe = true;
                    }
                    if (!isIntentSafe) {
                        Log.w("MoPub", "Could not handle market action: " + url + ". Perhaps you're running in the emulator, which does not have " + "the Android Market?");
                    }
                }
                url = urlWithClickTrackingRedirect(adView, url);
                Log.d("MoPub", "Ad clicked. Click URL: " + url);
                AdView.this.mMoPubView.adClicked();
                AdView.this.showBrowserForUrl(url);
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            AdView adView = (AdView) view;
            String redirectUrl = adView.getRedirectUrl();
            if (redirectUrl != null && url.startsWith(redirectUrl)) {
                url = urlWithClickTrackingRedirect(adView, url);
                view.stopLoading();
                AdView.this.showBrowserForUrl(url);
            }
        }

        private String urlWithClickTrackingRedirect(AdView adView, String url) {
            String clickthroughUrl = adView.getClickthroughUrl();
            if (clickthroughUrl == null) {
                return url;
            }
            return new StringBuilder(String.valueOf(clickthroughUrl)).append("&r=").append(Uri.encode(url)).toString();
        }
    }

    static {
        WRAP_AND_CENTER_LAYOUT_PARAMS = new LayoutParams(-2, -2, 17);
    }

    public AdView(Context context, MoPubView view, BannerAd response) {
        super(context.getApplicationContext());
        this.mRefreshTimeMilliseconds = DateTimeConstants.MILLIS_PER_MINUTE;
        this.mHandler = new Handler();
        this.mRefreshHandler = new Handler();
        this.mRefreshRunnable = new C00421();
        this.mResponse = response;
        this.mMoPubView = view;
        this.mAutorefreshEnabled = true;
        this.mUserAgent = getSettings().getUserAgentString();
        disableScrollingAndZoom();
        getSettings().setJavaScriptEnabled(true);
        getSettings().setPluginsEnabled(true);
        setBackgroundColor(0);
        setWebViewClient(new AdWebViewClient());
        addMoPubUriJavascriptInterface();
    }

    private void disableScrollingAndZoom() {
        setHorizontalScrollBarEnabled(false);
        setHorizontalScrollbarOverlay(false);
        setVerticalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        getSettings().setSupportZoom(false);
    }

    private void addMoPubUriJavascriptInterface() {
        addJavascriptInterface(new Object() {

            /* renamed from: com.adsdk.sdk.mraid.AdView.1MoPubUriJavascriptInterface.1 */
            class C00431 implements Runnable {
                C00431() {
                }

                public void run() {
                    AdView.this.adDidLoad();
                }
            }

            public boolean fireFinishLoad() {
                AdView.this.postHandlerRunnable(new C00431());
                return true;
            }
        }, "mopubUriInterface");
    }

    private void postHandlerRunnable(Runnable r) {
        this.mHandler.post(r);
    }

    public void loadAd() {
        HashMap<String, String> paramsHash = new HashMap();
        paramsHash.put("X-Adtype", "mraid");
        paramsHash.put("X-Nativeparams", this.mResponse.getText());
        this.mMoPubView.loadNativeSDK(paramsHash);
    }

    private boolean isNetworkAvailable() {
        if (getContext().checkCallingPermission("android.permission.ACCESS_NETWORK_STATE") == -1) {
            return true;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    private Location getLastKnownLocation() {
        LocationAwareness locationAwareness = this.mMoPubView.getLocationAwareness();
        int locationPrecision = this.mMoPubView.getLocationPrecision();
        if (locationAwareness == LocationAwareness.LOCATION_AWARENESS_DISABLED) {
            return null;
        }
        LocationManager lm = (LocationManager) getContext().getSystemService("location");
        Location gpsLocation = null;
        try {
            gpsLocation = lm.getLastKnownLocation("gps");
        } catch (SecurityException e) {
            Log.d("MoPub", "Failed to retrieve GPS location: access appears to be disabled.");
        } catch (IllegalArgumentException e2) {
            Log.d("MoPub", "Failed to retrieve GPS location: device has no GPS provider.");
        }
        Location networkLocation = null;
        try {
            networkLocation = lm.getLastKnownLocation("network");
        } catch (SecurityException e3) {
            Log.d("MoPub", "Failed to retrieve network location: access appears to be disabled.");
        } catch (IllegalArgumentException e4) {
            Log.d("MoPub", "Failed to retrieve network location: device has no network provider.");
        }
        if (gpsLocation == null && networkLocation == null) {
            return null;
        }
        Location result;
        if (gpsLocation == null || networkLocation == null) {
            if (gpsLocation != null) {
                result = gpsLocation;
            } else {
                result = networkLocation;
            }
        } else if (gpsLocation.getTime() > networkLocation.getTime()) {
            result = gpsLocation;
        } else {
            result = networkLocation;
        }
        if (locationAwareness == LocationAwareness.LOCATION_AWARENESS_TRUNCATED) {
            result.setLatitude(BigDecimal.valueOf(result.getLatitude()).setScale(locationPrecision, 5).doubleValue());
            result.setLongitude(BigDecimal.valueOf(result.getLongitude()).setScale(locationPrecision, 5).doubleValue());
        }
        return result;
    }

    private String getTimeZoneOffsetString() {
        SimpleDateFormat format = new SimpleDateFormat("Z");
        format.setTimeZone(TimeZone.getDefault());
        return format.format(new Date());
    }

    protected void configureAdViewUsingHeadersFromHttpResponse(HttpResponse response) {
        Header ntHeader = response.getFirstHeader("X-Networktype");
        if (ntHeader != null) {
            Log.i("MoPub", "Fetching ad network type: " + ntHeader.getValue());
        }
        Header rdHeader = response.getFirstHeader("X-Launchpage");
        if (rdHeader != null) {
            this.mRedirectUrl = rdHeader.getValue();
        } else {
            this.mRedirectUrl = null;
        }
        Header ctHeader = response.getFirstHeader("X-Clickthrough");
        if (ctHeader != null) {
            this.mClickthroughUrl = ctHeader.getValue();
        } else {
            this.mClickthroughUrl = null;
        }
        Header flHeader = response.getFirstHeader("X-Failurl");
        if (flHeader != null) {
            this.mFailUrl = flHeader.getValue();
        } else {
            this.mFailUrl = null;
        }
        Header imHeader = response.getFirstHeader("X-Imptracker");
        if (imHeader != null) {
            this.mImpressionUrl = imHeader.getValue();
        } else {
            this.mImpressionUrl = null;
        }
        Header scHeader = response.getFirstHeader("X-Scrollable");
        boolean enabled = false;
        if (scHeader != null) {
            enabled = scHeader.getValue().equals("1");
        }
        setWebViewScrollingEnabled(enabled);
        Header wHeader = response.getFirstHeader("X-Width");
        Header hHeader = response.getFirstHeader("X-Height");
        if (wHeader == null || hHeader == null) {
            this.mWidth = 0;
            this.mHeight = 0;
        } else {
            this.mWidth = Integer.parseInt(wHeader.getValue().trim());
            this.mHeight = Integer.parseInt(hHeader.getValue().trim());
        }
        Header rtHeader = response.getFirstHeader("X-Refreshtime");
        if (rtHeader != null) {
            this.mRefreshTimeMilliseconds = Integer.valueOf(rtHeader.getValue()).intValue() * DateTimeConstants.MILLIS_PER_SECOND;
            if (this.mRefreshTimeMilliseconds < MINIMUM_REFRESH_TIME_MILLISECONDS) {
                this.mRefreshTimeMilliseconds = MINIMUM_REFRESH_TIME_MILLISECONDS;
            }
        } else {
            this.mRefreshTimeMilliseconds = 0;
        }
        Header orHeader = response.getFirstHeader("X-Orientation");
        this.mAdOrientation = orHeader != null ? orHeader.getValue() : null;
    }

    private void setWebViewScrollingEnabled(boolean enabled) {
        if (enabled) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(new C00442());
        }
    }

    private void adDidLoad() {
        Log.i("MoPub", "Ad successfully loaded.");
        this.mIsLoading = false;
        scheduleRefreshTimerIfEnabled();
        setAdContentView(this, getHtmlAdLayoutParams());
        this.mMoPubView.adLoaded();
    }

    public void setAdContentView(View view) {
        setAdContentView(view, WRAP_AND_CENTER_LAYOUT_PARAMS);
    }

    private void setAdContentView(View view, LayoutParams layoutParams) {
        this.mMoPubView.removeAllViews();
        this.mMoPubView.addView(view, layoutParams);
    }

    private void adDidFail() {
        Log.i("MoPub", "Ad failed to load.");
        this.mIsLoading = false;
        scheduleRefreshTimerIfEnabled();
        this.mMoPubView.adFailed();
    }

    private void adDidClose() {
        this.mMoPubView.adClosed();
    }

    private void handleCustomIntentFromUri(Uri uri) {
        registerClick();
        String action = uri.getQueryParameter("fnc");
        String adData = uri.getQueryParameter("data");
        Intent customIntent = new Intent(action);
        customIntent.addFlags(268435456);
        if (adData != null) {
            customIntent.putExtra(EXTRA_AD_CLICK_DATA, adData);
        }
        try {
            getContext().startActivity(customIntent);
        } catch (ActivityNotFoundException e) {
            Log.w("MoPub", "Could not handle custom intent: " + action + ". Is your intent spelled correctly?");
        }
    }

    private void showBrowserForUrl(String url) {
        if (!isDestroyed()) {
            if (url == null || url.equals(StringUtils.EMPTY)) {
                url = "about:blank";
            }
            Log.d("MoPub", "Final URI to show in browser: " + url);
            Context context = getContext();
            if (url.endsWith(".mp4")) {
                Intent i = new Intent("android.intent.action.VIEW");
                i.setDataAndType(Uri.parse(url), "video/mp4");
                context.startActivity(i);
                return;
            }
            Intent intent = new Intent(context, InAppWebView.class);
            intent.putExtra(Const.REDIRECT_URI, url);
            intent.addFlags(268435456);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.w("MoPub", "Could not handle intent action: " + intent.getAction() + ". Perhaps you forgot to declare com.adsdk.sdk.mraid.MraidBrowser" + " in your Android manifest file.");
                getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("about:blank")).addFlags(268435456));
            }
        }
    }

    public void customEventDidLoadAd() {
        this.mIsLoading = false;
        scheduleRefreshTimerIfEnabled();
    }

    public void customEventDidFailToLoadAd() {
        loadFailUrl();
    }

    public void customEventActionWillBegin() {
        registerClick();
    }

    protected boolean isDestroyed() {
        return this.mIsDestroyed;
    }

    protected void cleanup() {
        if (!this.mIsDestroyed) {
            setAutorefreshEnabled(false);
            cancelRefreshTimer();
            destroy();
            this.mResponseString = null;
            this.mMoPubView.removeView(this);
            this.mMoPubView = null;
            this.mIsDestroyed = true;
        }
    }

    public void reload() {
        Log.d("MoPub", "Reload ad: " + this.mUrl);
        loadUrl(this.mUrl);
    }

    public void loadFailUrl() {
        this.mIsLoading = false;
        if (this.mFailUrl != null) {
            Log.d("MoPub", "Loading failover url: " + this.mFailUrl);
            loadUrl(this.mFailUrl);
            return;
        }
        adDidFail();
    }

    protected void registerClick() {
        if (this.mClickthroughUrl != null) {
            new Thread(new C00453()).start();
        }
    }

    protected void adAppeared() {
        loadUrl("javascript:webviewDidAppear();");
    }

    protected void scheduleRefreshTimerIfEnabled() {
        cancelRefreshTimer();
        if (this.mAutorefreshEnabled && this.mRefreshTimeMilliseconds > 0) {
            this.mRefreshHandler.postDelayed(this.mRefreshRunnable, (long) this.mRefreshTimeMilliseconds);
        }
    }

    protected void cancelRefreshTimer() {
        this.mRefreshHandler.removeCallbacks(this.mRefreshRunnable);
    }

    protected int getRefreshTimeMilliseconds() {
        return this.mRefreshTimeMilliseconds;
    }

    protected void setRefreshTimeMilliseconds(int refreshTimeMilliseconds) {
        this.mRefreshTimeMilliseconds = refreshTimeMilliseconds;
    }

    private String addKeyword(String keywords, String addition) {
        if (addition == null || addition.length() == 0) {
            return keywords;
        }
        return (keywords == null || keywords.length() == 0) ? addition : new StringBuilder(String.valueOf(keywords)).append(",").append(addition).toString();
    }

    public String getKeywords() {
        return this.mKeywords;
    }

    public void setKeywords(String keywords) {
        this.mKeywords = keywords;
    }

    public Location getLocation() {
        return this.mLocation;
    }

    public void setLocation(Location location) {
        this.mLocation = location;
    }

    public String getAdUnitId() {
        return this.mAdUnitId;
    }

    public void setAdUnitId(String adUnitId) {
        this.mAdUnitId = adUnitId;
    }

    public int getAdWidth() {
        return this.mWidth;
    }

    public int getAdHeight() {
        return this.mHeight;
    }

    public String getAdOrientation() {
        return this.mAdOrientation;
    }

    public String getClickthroughUrl() {
        return this.mClickthroughUrl;
    }

    public void setClickthroughUrl(String url) {
        this.mClickthroughUrl = url;
    }

    public String getRedirectUrl() {
        return this.mRedirectUrl;
    }

    public String getResponseString() {
        return this.mResponseString;
    }

    protected void setResponseString(String responseString) {
        this.mResponseString = responseString;
    }

    protected void setIsLoading(boolean isLoading) {
        this.mIsLoading = isLoading;
    }

    public void setAutorefreshEnabled(boolean enabled) {
        this.mAutorefreshEnabled = enabled;
        Log.d("MoPub", "Automatic refresh for " + this.mAdUnitId + " set to: " + enabled + ".");
        if (this.mAutorefreshEnabled) {
            scheduleRefreshTimerIfEnabled();
        } else {
            cancelRefreshTimer();
        }
    }

    public boolean getAutorefreshEnabled() {
        return this.mAutorefreshEnabled;
    }

    public void forceRefresh() {
        this.mIsLoading = false;
        loadAd();
    }

    private LayoutParams getHtmlAdLayoutParams() {
        if (this.mWidth <= 0 || this.mHeight <= 0) {
            return WRAP_AND_CENTER_LAYOUT_PARAMS;
        }
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return new LayoutParams((int) TypedValue.applyDimension(1, (float) this.mWidth, displayMetrics), (int) TypedValue.applyDimension(1, (float) this.mHeight, displayMetrics), 17);
    }
}
