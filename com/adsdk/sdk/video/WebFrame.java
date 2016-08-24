package com.adsdk.sdk.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.adsdk.sdk.video.InterstitialController.BrowserControl;
import com.adsdk.sdk.video.WebViewClient.OnPageLoadedListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

public class WebFrame extends FrameLayout implements BrowserControl {
    private static Field mWebView_LAYER_TYPE_SOFTWARE;
    private static Method mWebView_SetLayerType;
    private boolean enableZoom;
    private Activity mActivity;
    private InterstitialController mController;
    private ImageView mExitButton;
    private WebView mWebView;
    private WebViewClient mWebViewClient;

    /* renamed from: com.adsdk.sdk.video.WebFrame.1 */
    class C00801 implements OnClickListener {
        private final /* synthetic */ Activity val$localContext;

        C00801(Activity activity) {
            this.val$localContext = activity;
        }

        public void onClick(View v) {
            this.val$localContext.finish();
        }
    }

    private class LoadUrlTask extends AsyncTask<String, Void, String> {
        String userAgent;

        public LoadUrlTask() {
            this.userAgent = WebFrame.this.getUserAgentString();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected java.lang.String doInBackground(java.lang.String... r12) {
            /*
            r11 = this;
            r9 = 0;
            r3 = r12[r9];
            r7 = 0;
            r8 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x003c }
            r8.<init>(r3);	 Catch:{ MalformedURLException -> 0x003c }
            r6 = -1;
            r1 = 0;
            r4 = r8.toString();
            r5 = new java.util.HashSet;
            r5.<init>();
            r5.add(r4);
        L_0x0017:
            r9 = r8.openConnection();	 Catch:{ IOException -> 0x0073 }
            r0 = r9;
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x0073 }
            r1 = r0;
            r9 = "User-Agent";
            r10 = r11.userAgent;	 Catch:{ IOException -> 0x0073 }
            r1.setRequestProperty(r9, r10);	 Catch:{ IOException -> 0x0073 }
            r9 = 0;
            r1.setInstanceFollowRedirects(r9);	 Catch:{ IOException -> 0x0073 }
            r6 = r1.getResponseCode();	 Catch:{ IOException -> 0x0073 }
            r9 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r6 != r9) goto L_0x0044;
        L_0x0032:
            r1.disconnect();	 Catch:{ IOException -> 0x0073 }
            r7 = r8;
        L_0x0036:
            if (r1 == 0) goto L_0x003b;
        L_0x0038:
            r1.disconnect();
        L_0x003b:
            return r4;
        L_0x003c:
            r2 = move-exception;
            if (r3 == 0) goto L_0x0041;
        L_0x003f:
            r4 = r3;
            goto L_0x003b;
        L_0x0041:
            r3 = "";
            goto L_0x003f;
        L_0x0044:
            r9 = "location";
            r4 = r1.getHeaderField(r9);	 Catch:{ IOException -> 0x0073 }
            r1.disconnect();	 Catch:{ IOException -> 0x0073 }
            r9 = r5.add(r4);	 Catch:{ IOException -> 0x0073 }
            if (r9 != 0) goto L_0x005c;
        L_0x0053:
            if (r1 == 0) goto L_0x0058;
        L_0x0055:
            r1.disconnect();
        L_0x0058:
            r4 = "";
            r7 = r8;
            goto L_0x003b;
        L_0x005c:
            r7 = new java.net.URL;	 Catch:{ IOException -> 0x0073 }
            r7.<init>(r4);	 Catch:{ IOException -> 0x0073 }
            r9 = 302; // 0x12e float:4.23E-43 double:1.49E-321;
            if (r6 == r9) goto L_0x0071;
        L_0x0065:
            r9 = 301; // 0x12d float:4.22E-43 double:1.487E-321;
            if (r6 == r9) goto L_0x0071;
        L_0x0069:
            r9 = 307; // 0x133 float:4.3E-43 double:1.517E-321;
            if (r6 == r9) goto L_0x0071;
        L_0x006d:
            r9 = 303; // 0x12f float:4.25E-43 double:1.497E-321;
            if (r6 != r9) goto L_0x0036;
        L_0x0071:
            r8 = r7;
            goto L_0x0017;
        L_0x0073:
            r2 = move-exception;
            if (r4 == 0) goto L_0x007d;
        L_0x0076:
            if (r1 == 0) goto L_0x007b;
        L_0x0078:
            r1.disconnect();
        L_0x007b:
            r7 = r8;
            goto L_0x003b;
        L_0x007d:
            r4 = "";
            goto L_0x0076;
        L_0x0080:
            r9 = move-exception;
            if (r1 == 0) goto L_0x0086;
        L_0x0083:
            r1.disconnect();
        L_0x0086:
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.adsdk.sdk.video.WebFrame.LoadUrlTask.doInBackground(java.lang.String[]):java.lang.String");
        }

        protected void onPostExecute(String url) {
            if (url == null || url.equals(StringUtils.EMPTY)) {
                url = "about:blank";
            }
            WebFrame.this.mWebViewClient.setAllowedUrl(url);
            WebFrame.this.mWebView.loadUrl(url);
            WebFrame.this.requestLayout();
        }
    }

    static {
        initCompatibility();
    }

    private static void initCompatibility() {
        try {
            for (Method m : WebView.class.getMethods()) {
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

    public WebFrame(Activity context, boolean allowNavigation, boolean scroll, boolean showExit) {
        super(context);
        this.enableZoom = true;
        initCompatibility();
        this.mActivity = context;
        this.mWebView = new WebView(context);
        this.mWebView.setVerticalScrollBarEnabled(scroll);
        this.mWebView.setHorizontalScrollBarEnabled(scroll);
        this.mWebView.setBackgroundColor(0);
        setLayer(this.mWebView);
        WebSettings webSettings = this.mWebView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginsEnabled(true);
        webSettings.setSupportZoom(this.enableZoom);
        webSettings.setBuiltInZoomControls(this.enableZoom);
        this.mWebViewClient = new WebViewClient(this.mActivity, allowNavigation);
        this.mWebView.setWebViewClient(this.mWebViewClient);
        Activity localContext = context;
        if (showExit) {
            ImageView bg = new ImageView(context);
            bg.setBackgroundColor(0);
            addView(bg, new LayoutParams(-1, -1, 17));
            addView(this.mWebView, new LayoutParams(-1, -1, 17));
            this.mExitButton = new ImageView(context);
            this.mExitButton.setAdjustViewBounds(false);
            this.mExitButton.setOnClickListener(new C00801(localContext));
            int buttonSize = (int) TypedValue.applyDimension(1, 35.0f, getResources().getDisplayMetrics());
            this.mExitButton.setImageDrawable(ResourceManager.getStaticResource(context, -18));
            LayoutParams params = new LayoutParams(buttonSize, buttonSize, 53);
            int margin = (int) TypedValue.applyDimension(1, 6.0f, getResources().getDisplayMetrics());
            params.topMargin = margin;
            params.rightMargin = margin;
            addView(this.mExitButton, params);
            return;
        }
        addView(this.mWebView, new LayoutParams(-1, -1, 17));
    }

    public void loadUrl(String url) {
        new LoadUrlTask().execute(new String[]{url});
    }

    public void setMarkup(String htmlMarkup) {
        String data = Uri.encode(htmlMarkup);
        this.mWebViewClient.setAllowedUrl(null);
        this.mWebView.loadData(data, "text/html", CharEncoding.UTF_8);
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        this.mWebView.setBackgroundColor(color);
    }

    private String getUserAgentString() {
        return this.mWebView.getSettings().getUserAgentString();
    }

    public WebView getWebView() {
        return this.mWebView;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    public boolean canGoBack() {
        return this.mWebView.canGoBack();
    }

    public void goBack() {
        this.mWebView.goBack();
    }

    public boolean canGoForward() {
        return this.mWebView.canGoForward();
    }

    public void goForward() {
        this.mWebView.goForward();
    }

    public void reload() {
        this.mWebView.reload();
    }

    public void setBrowserController(InterstitialController controller) {
        if (this.mController != null) {
            this.mController.hide();
        }
        this.mController = controller;
        attachController();
    }

    private void attachController() {
        if (this.mController != null) {
            this.mController.setBrowser(this);
        }
    }

    public int getTime() {
        long finishedLoadedTime = this.mWebViewClient.getFinishedLoadingTime();
        if (finishedLoadedTime > 0) {
            return (int) (System.currentTimeMillis() - finishedLoadedTime);
        }
        return 0;
    }

    public void launchExternalBrowser() {
        String url = this.mWebViewClient.getAllowedUrl();
        if (url == null || url.length() == 0) {
            url = "about:blank";
        }
        this.mActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    public String getPageTitle() {
        return this.mWebView.getTitle();
    }

    public void setOnPageLoadedListener(OnPageLoadedListener l) {
        this.mWebViewClient.setOnPageLoadedListener(l);
    }

    public boolean isEnableZoom() {
        return this.enableZoom;
    }

    public void setEnableZoom(boolean enableZoom) {
        this.enableZoom = enableZoom;
        WebSettings webSettings = this.mWebView.getSettings();
        webSettings.setSupportZoom(enableZoom);
        webSettings.setBuiltInZoomControls(enableZoom);
    }
}
