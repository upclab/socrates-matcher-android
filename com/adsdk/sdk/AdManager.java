package com.adsdk.sdk;

import android.content.Context;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Handler;
import com.adsdk.sdk.video.ResourceManager;
import com.adsdk.sdk.video.RichMediaAd;
import com.adsdk.sdk.video.TrackerService;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;

public class AdManager {
    private static Context mContext;
    private static HashMap<Long, AdManager> sRunningAds;
    private boolean mEnabled;
    private Handler mHandler;
    private boolean mIncludeLocation;
    private AdListener mListener;
    private String mPublisherId;
    private AdRequest mRequest;
    private Thread mRequestThread;
    private RichMediaAd mResponse;
    private String mUniqueId1;
    private String mUniqueId2;
    private String mUserAgent;
    private String requestURL;

    /* renamed from: com.adsdk.sdk.AdManager.1 */
    class C00191 implements Runnable {

        /* renamed from: com.adsdk.sdk.AdManager.1.1 */
        class C00151 implements Runnable {
            C00151() {
            }

            public void run() {
                AdManager.this.mListener.adLoadSucceeded(AdManager.this.mResponse);
            }
        }

        /* renamed from: com.adsdk.sdk.AdManager.1.2 */
        class C00162 implements Runnable {
            C00162() {
            }

            public void run() {
                AdManager.this.notifyNoAdFound();
            }
        }

        /* renamed from: com.adsdk.sdk.AdManager.1.3 */
        class C00173 implements Runnable {
            C00173() {
            }

            public void run() {
                AdManager.this.notifyNoAdFound();
            }
        }

        /* renamed from: com.adsdk.sdk.AdManager.1.4 */
        class C00184 implements Runnable {
            C00184() {
            }

            public void run() {
                AdManager.this.notifyNoAdFound();
            }
        }

        C00191() {
        }

        public void run() {
            while (ResourceManager.isDownloading()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
            try {
                AdManager.this.mResponse = (RichMediaAd) new RequestRichMediaAd().sendRequest(AdManager.this.getRequest());
                if (AdManager.this.mResponse.getVideo() != null && VERSION.SDK_INT < 8) {
                    AdManager.this.notifyNoAdFound();
                    AdManager.this.mRequestThread = null;
                } else if (AdManager.this.mResponse.getType() == 3 || AdManager.this.mResponse.getType() == 4 || AdManager.this.mResponse.getType() == 5 || AdManager.this.mResponse.getType() == 6) {
                    if (AdManager.this.mListener != null) {
                        AdManager.this.mHandler.post(new C00151());
                    }
                    AdManager.this.mRequestThread = null;
                } else {
                    if (AdManager.this.mResponse.getType() == 2) {
                        if (AdManager.this.mListener != null) {
                            AdManager.this.mHandler.post(new C00162());
                        }
                    } else if (AdManager.this.mListener != null) {
                        AdManager.this.mHandler.post(new C00173());
                    }
                    AdManager.this.mRequestThread = null;
                }
            } catch (Throwable t) {
                AdManager.this.mResponse = new RichMediaAd();
                AdManager.this.mResponse.setType(-1);
                if (AdManager.this.mListener != null) {
                    t.printStackTrace();
                    AdManager.this.mHandler.post(new C00184());
                }
            }
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.2 */
    class C00202 implements UncaughtExceptionHandler {
        C00202() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            AdManager.this.mResponse = new RichMediaAd();
            AdManager.this.mResponse.setType(-1);
            AdManager.this.mRequestThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.3 */
    class C00243 implements Runnable {
        private final /* synthetic */ InputStream val$xml;

        /* renamed from: com.adsdk.sdk.AdManager.3.1 */
        class C00211 implements Runnable {
            C00211() {
            }

            public void run() {
                AdManager.this.mListener.adLoadSucceeded(AdManager.this.mResponse);
            }
        }

        /* renamed from: com.adsdk.sdk.AdManager.3.2 */
        class C00222 implements Runnable {
            C00222() {
            }

            public void run() {
                AdManager.this.notifyNoAdFound();
            }
        }

        /* renamed from: com.adsdk.sdk.AdManager.3.3 */
        class C00233 implements Runnable {
            C00233() {
            }

            public void run() {
                AdManager.this.notifyNoAdFound();
            }
        }

        C00243(InputStream inputStream) {
            this.val$xml = inputStream;
        }

        public void run() {
            while (ResourceManager.isDownloading()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
            try {
                AdManager.this.mResponse = (RichMediaAd) new RequestRichMediaAd(this.val$xml).sendRequest(AdManager.this.getRequest());
                if (AdManager.this.mResponse.getType() != 2) {
                    if (AdManager.this.mListener != null) {
                        AdManager.this.mHandler.post(new C00211());
                    }
                } else if (AdManager.this.mListener != null) {
                    AdManager.this.mHandler.post(new C00222());
                }
            } catch (Throwable th) {
                AdManager.this.mResponse = new RichMediaAd();
                AdManager.this.mResponse.setType(-1);
                if (AdManager.this.mListener != null) {
                    AdManager.this.mHandler.post(new C00233());
                }
            }
            AdManager.this.mRequestThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.4 */
    class C00254 implements UncaughtExceptionHandler {
        C00254() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            AdManager.this.mResponse = new RichMediaAd();
            AdManager.this.mResponse.setType(-1);
            AdManager.this.mRequestThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.5 */
    class C00265 implements Runnable {
        C00265() {
        }

        public void run() {
            AdManager.this.mListener.noAdFound();
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.6 */
    class C00276 implements Runnable {
        private final /* synthetic */ RichMediaAd val$ad;
        private final /* synthetic */ boolean val$ok;

        C00276(RichMediaAd richMediaAd, boolean z) {
            this.val$ad = richMediaAd;
            this.val$ok = z;
        }

        public void run() {
            AdManager.this.mListener.adShown(this.val$ad, this.val$ok);
        }
    }

    /* renamed from: com.adsdk.sdk.AdManager.7 */
    class C00287 implements Runnable {
        private final /* synthetic */ RichMediaAd val$ad;
        private final /* synthetic */ boolean val$ok;

        C00287(RichMediaAd richMediaAd, boolean z) {
            this.val$ad = richMediaAd;
            this.val$ok = z;
        }

        public void run() {
            AdManager.this.mListener.adClosed(this.val$ad, this.val$ok);
        }
    }

    static {
        sRunningAds = new HashMap();
    }

    public static AdManager getAdManager(RichMediaAd ad) {
        return (AdManager) sRunningAds.remove(Long.valueOf(ad.getTimestamp()));
    }

    public static void closeRunningAd(RichMediaAd ad, boolean result) {
        ((AdManager) sRunningAds.remove(Long.valueOf(ad.getTimestamp()))).notifyAdClose(ad, result);
    }

    public void release() {
        TrackerService.release();
        ResourceManager.cancel();
    }

    public AdManager(Context ctx, String requestURL, String publisherId, boolean includeLocation) throws IllegalArgumentException {
        this.mRequest = null;
        this.mEnabled = true;
        setmContext(ctx);
        this.requestURL = requestURL;
        this.mPublisherId = publisherId;
        this.mIncludeLocation = includeLocation;
        this.mRequestThread = null;
        this.mHandler = new Handler();
        initialize();
    }

    public void setListener(AdListener listener) {
        this.mListener = listener;
    }

    public void requestAd() {
        if (this.mEnabled && this.mRequestThread == null) {
            this.mResponse = null;
            this.mRequestThread = new Thread(new C00191());
            this.mRequestThread.setUncaughtExceptionHandler(new C00202());
            this.mRequestThread.start();
        }
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void requestAd(InputStream xml) {
        if (this.mEnabled && this.mRequestThread == null) {
            this.mResponse = null;
            this.mRequestThread = new Thread(new C00243(xml));
            this.mRequestThread.setUncaughtExceptionHandler(new C00254());
            this.mRequestThread.start();
        }
    }

    public boolean isAdLoaded() {
        return this.mResponse != null;
    }

    public void requestAdAndShow(long timeout) {
        AdListener l = this.mListener;
        this.mListener = null;
        requestAd();
        long now = System.currentTimeMillis();
        long timeoutTime = now + timeout;
        while (!isAdLoaded() && now < timeoutTime) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            now = System.currentTimeMillis();
        }
        this.mListener = l;
        showAd();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showAd() {
        /*
        r9 = this;
        r8 = 0;
        r0 = r9.getContext();
        r0 = (android.app.Activity) r0;
        r6 = r9.mResponse;
        if (r6 == 0) goto L_0x001d;
    L_0x000b:
        r6 = r9.mResponse;
        r6 = r6.getType();
        r7 = 2;
        if (r6 == r7) goto L_0x001d;
    L_0x0014:
        r6 = r9.mResponse;
        r6 = r6.getType();
        r7 = -1;
        if (r6 != r7) goto L_0x0023;
    L_0x001d:
        r6 = r9.mResponse;
        r9.notifyAdShown(r6, r8);
    L_0x0022:
        return;
    L_0x0023:
        r1 = r9.mResponse;
        r5 = 0;
        r6 = r9.getContext();	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = com.adsdk.sdk.Util.isNetworkAvailable(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        if (r6 == 0) goto L_0x0068;
    L_0x0030:
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r1.setTimestamp(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r4 = new android.content.Intent;	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = com.adsdk.sdk.video.RichMediaActivity.class;
        r4.<init>(r0, r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = "RICH_AD_DATA";
        r4.putExtra(r6, r1);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = 0;
        r0.startActivityForResult(r4, r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = r1.getAnimation();	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r2 = com.adsdk.sdk.Util.getEnterAnimation(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6 = r1.getAnimation();	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r3 = com.adsdk.sdk.Util.getExitAnimation(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        com.adsdk.sdk.video.RichMediaActivity.setActivityAnimation(r0, r2, r3);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r5 = 1;
        r6 = sRunningAds;	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r7 = r1.getTimestamp();	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r7 = java.lang.Long.valueOf(r7);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
        r6.put(r7, r9);	 Catch:{ Exception -> 0x006c, all -> 0x0071 }
    L_0x0068:
        r9.notifyAdShown(r1, r5);
        goto L_0x0022;
    L_0x006c:
        r6 = move-exception;
        r9.notifyAdShown(r1, r5);
        goto L_0x0022;
    L_0x0071:
        r6 = move-exception;
        r9.notifyAdShown(r1, r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adsdk.sdk.AdManager.showAd():void");
    }

    private void initialize() throws IllegalArgumentException {
        this.mUserAgent = Util.getDefaultUserAgentString(getContext());
        this.mUniqueId1 = Util.getTelephonyDeviceId(getContext());
        this.mUniqueId2 = Util.getDeviceId(getContext());
        if (this.mPublisherId == null || this.mPublisherId.length() == 0) {
            throw new IllegalArgumentException("User Id cannot be null or empty");
        } else if (this.mUniqueId2 == null || this.mUniqueId2.length() == 0) {
            throw new IllegalArgumentException("System Device Id cannot be null or empty");
        } else {
            this.mEnabled = Util.getMemoryClass(getContext()) > 16;
            Util.initializeAnimations(getContext());
        }
    }

    private void notifyNoAdFound() {
        if (this.mListener != null) {
            this.mHandler.post(new C00265());
        }
        this.mResponse = null;
    }

    private void notifyAdShown(RichMediaAd ad, boolean ok) {
        if (this.mListener != null) {
            this.mHandler.post(new C00276(ad, ok));
        }
        this.mResponse = null;
    }

    private void notifyAdClose(RichMediaAd ad, boolean ok) {
        if (this.mListener != null) {
            this.mHandler.post(new C00287(ad, ok));
        }
    }

    private AdRequest getRequest() {
        if (this.mRequest == null) {
            this.mRequest = new AdRequest();
            this.mRequest.setDeviceId(this.mUniqueId1);
            this.mRequest.setDeviceId2(this.mUniqueId2);
            this.mRequest.setPublisherId(this.mPublisherId);
            this.mRequest.setUserAgent(this.mUserAgent);
            this.mRequest.setUserAgent2(Util.buildUserAgent());
        }
        Location location = null;
        if (this.mIncludeLocation) {
            location = Util.getLocation(getContext());
        }
        if (location != null) {
            this.mRequest.setLatitude(location.getLatitude());
            this.mRequest.setLongitude(location.getLongitude());
        } else {
            this.mRequest.setLatitude(0.0d);
            this.mRequest.setLongitude(0.0d);
        }
        this.mRequest.setConnectionType(Util.getConnectionType(getContext()));
        this.mRequest.setIpAddress(Util.getLocalIpAddress());
        this.mRequest.setTimestamp(System.currentTimeMillis());
        this.mRequest.setType(1);
        this.mRequest.setRequestURL(this.requestURL);
        return this.mRequest;
    }

    private Context getContext() {
        return getmContext();
    }

    private static Context getmContext() {
        return mContext;
    }

    private static void setmContext(Context mContext) {
        mContext = mContext;
    }
}
