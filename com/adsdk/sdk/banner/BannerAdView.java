package com.adsdk.sdk.banner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ViewFlipper;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.BannerAd;
import com.adsdk.sdk.Const;
import com.adsdk.sdk.data.ClickType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import org.apache.commons.lang3.CharEncoding;

public class BannerAdView extends RelativeLayout {
    public static final int LIVE = 0;
    public static final int TEST = 1;
    private static Field mWebView_LAYER_TYPE_SOFTWARE;
    private static Method mWebView_SetLayerType;
    private AdListener adListener;
    private boolean animation;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private WebView firstWebView;
    private boolean isInternalBrowser;
    private Context mContext;
    protected boolean mIsInForeground;
    private BannerAd response;
    private WebView secondWebView;
    private boolean touchMove;
    private final Handler updateHandler;
    private ViewFlipper viewFlipper;
    private WebSettings webSettings;
    private InputStream xml;

    /* renamed from: com.adsdk.sdk.banner.BannerAdView.1 */
    class C00351 extends WebView {
        C00351(Context $anonymous0) {
            super($anonymous0);
        }

        public void draw(Canvas canvas) {
            if (getWidth() > 0 && getHeight() > 0) {
                super.draw(canvas);
            }
        }
    }

    /* renamed from: com.adsdk.sdk.banner.BannerAdView.2 */
    class C00362 extends WebViewClient {
        C00362() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (BannerAdView.this.response.getSkipOverlay() == BannerAdView.TEST) {
                BannerAdView.this.doOpenUrl(url);
            } else {
                BannerAdView.this.openLink();
            }
            return true;
        }
    }

    /* renamed from: com.adsdk.sdk.banner.BannerAdView.3 */
    class C00373 extends ViewFlipper {
        C00373(Context $anonymous0) {
            super($anonymous0);
        }

        protected void onDetachedFromWindow() {
            try {
                super.onDetachedFromWindow();
            } catch (IllegalArgumentException e) {
                stopFlipping();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.banner.BannerAdView.4 */
    class C00384 implements Runnable {
        C00384() {
        }

        public void run() {
            if (BannerAdView.this.adListener != null) {
                BannerAdView.this.adListener.adClicked();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.banner.BannerAdView.5 */
    class C00395 implements Runnable {
        C00395() {
        }

        public void run() {
            if (BannerAdView.this.adListener != null) {
                BannerAdView.this.adListener.adLoadSucceeded(null);
            }
        }
    }

    public void setWidth(int width) {
    }

    public void setHeight(int width) {
    }

    public BannerAdView(Context context, BannerAd response, AdListener adListener) {
        this(context, response, false, adListener);
    }

    public BannerAdView(Context context, InputStream xml, boolean animation) {
        super(context);
        this.isInternalBrowser = false;
        this.fadeInAnimation = null;
        this.fadeOutAnimation = null;
        this.mContext = null;
        this.updateHandler = new Handler();
        this.xml = xml;
        this.mContext = context;
        this.animation = animation;
        initialize(context);
    }

    public BannerAdView(Context context, BannerAd response, boolean animation, AdListener adListener) {
        super(context);
        this.isInternalBrowser = false;
        this.fadeInAnimation = null;
        this.fadeOutAnimation = null;
        this.mContext = null;
        this.updateHandler = new Handler();
        this.response = response;
        this.mContext = context;
        this.animation = animation;
        this.adListener = adListener;
        initialize(context);
    }

    private WebView createWebView(Context context) {
        WebView webView = new C00351(getContext());
        this.webSettings = webView.getSettings();
        this.webSettings.setJavaScriptEnabled(true);
        webView.setBackgroundColor(LIVE);
        setLayer(webView);
        webView.setWebViewClient(new C00362());
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        return webView;
    }

    private void doOpenUrl(String url) {
        notifyAdClicked();
        if (this.response.getClickType() == null || !this.response.getClickType().equals(ClickType.INAPP) || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } else if (url.endsWith(".mp4")) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setDataAndType(Uri.parse(url), "video/mp4");
            getContext().startActivity(i);
        } else {
            Intent intent = new Intent(getContext(), InAppWebView.class);
            intent.putExtra(Const.REDIRECT_URI, url);
            getContext().startActivity(intent);
        }
    }

    static {
        initCompatibility();
    }

    private static void initCompatibility() {
        try {
            Method[] methods = WebView.class.getMethods();
            int length = methods.length;
            for (int i = LIVE; i < length; i += TEST) {
                Method m = methods[i];
                if (m.getName().equals("setLayerType")) {
                    mWebView_SetLayerType = m;
                    break;
                }
            }
            mWebView_LAYER_TYPE_SOFTWARE = WebView.class.getField("LAYER_TYPE_SOFTWARE");
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e2) {
        }
    }

    private static void setLayer(WebView webView) {
        if (mWebView_SetLayerType != null && mWebView_LAYER_TYPE_SOFTWARE != null) {
            try {
                mWebView_SetLayerType.invoke(webView, new Object[]{Integer.valueOf(mWebView_LAYER_TYPE_SOFTWARE.getInt(WebView.class)), null});
            } catch (InvocationTargetException e) {
            } catch (IllegalArgumentException e2) {
            } catch (IllegalAccessException e3) {
            }
        }
    }

    private void buildBannerView() {
        this.firstWebView = createWebView(this.mContext);
        this.secondWebView = createWebView(this.mContext);
        this.viewFlipper = new C00373(getContext());
        float scale = this.mContext.getResources().getDisplayMetrics().density;
        setLayoutParams(new LayoutParams((int) ((BitmapDescriptorFactory.HUE_MAGENTA * scale) + 0.5f), (int) ((50.0f * scale) + 0.5f)));
        FrameLayout.LayoutParams webViewParams = new FrameLayout.LayoutParams(-1, -1);
        this.viewFlipper.addView(this.firstWebView, webViewParams);
        this.viewFlipper.addView(this.secondWebView, webViewParams);
        addView(this.viewFlipper, new LayoutParams(-1, -1));
        if (this.animation) {
            this.fadeInAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, AugmentedReality.ONE_PERCENT, 2, 0.0f);
            this.fadeInAnimation.setDuration(1000);
            this.fadeOutAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, GroundOverlayOptions.NO_DIMENSION);
            this.fadeOutAnimation.setDuration(1000);
            this.viewFlipper.setInAnimation(this.fadeInAnimation);
            this.viewFlipper.setOutAnimation(this.fadeOutAnimation);
        }
    }

    private void initialize(Context context) {
        initCompatibility();
        buildBannerView();
        showContent();
    }

    public boolean isInternalBrowser() {
        return this.isInternalBrowser;
    }

    private void notifyAdClicked() {
        this.updateHandler.post(new C00384());
    }

    private void notifyLoadAdSucceeded() {
        this.updateHandler.post(new C00395());
    }

    private void openLink() {
        if (this.response != null && this.response.getClickUrl() != null) {
            doOpenUrl(this.response.getClickUrl());
        }
    }

    public void setAdListener(AdListener bannerListener) {
        this.adListener = bannerListener;
    }

    public void setInternalBrowser(boolean isInternalBrowser) {
        this.isInternalBrowser = isInternalBrowser;
    }

    private void showContent() {
        try {
            WebView webView;
            if (this.viewFlipper.getCurrentView() == this.firstWebView) {
                webView = this.secondWebView;
            } else {
                webView = this.firstWebView;
            }
            if (this.response.getType() == 0) {
                webView.loadData(Uri.encode(new StringBuilder(Const.HIDE_BORDER).append(MessageFormat.format(Const.IMAGE_BODY, new Object[]{this.response.getImageUrl(), Integer.valueOf(this.response.getBannerWidth()), Integer.valueOf(this.response.getBannerHeight())})).toString()), "text/html", CharEncoding.UTF_8);
                notifyLoadAdSucceeded();
            } else if (this.response.getType() == TEST) {
                webView.loadData(Uri.encode(new StringBuilder(Const.HIDE_BORDER).append(this.response.getText()).toString()), "text/html", CharEncoding.UTF_8);
                notifyLoadAdSucceeded();
            }
            if (this.viewFlipper.getCurrentView() == this.firstWebView) {
                this.viewFlipper.showNext();
            } else {
                this.viewFlipper.showPrevious();
            }
        } catch (Throwable th) {
        }
    }
}
