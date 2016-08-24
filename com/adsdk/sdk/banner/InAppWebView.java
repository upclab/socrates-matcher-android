package com.adsdk.sdk.banner;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.adsdk.sdk.Const;

public class InAppWebView extends Activity {
    public static final String URL_EXTRA = "extra_url";

    /* renamed from: com.adsdk.sdk.banner.InAppWebView.1 */
    class C00401 extends WebViewClient {
        C00401() {
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText((Activity) view.getContext(), "MRAID error: " + description, 0).show();
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) {
                return false;
            }
            String host = Uri.parse(url).getHost();
            if ((!url.startsWith("http:") && !url.startsWith("https:")) || "play.google.com".equals(host) || "market.android.com".equals(host)) {
                try {
                    InAppWebView.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (ActivityNotFoundException e) {
                }
                InAppWebView.this.finish();
                return true;
            }
            view.loadUrl(url);
            return true;
        }
    }

    /* renamed from: com.adsdk.sdk.banner.InAppWebView.2 */
    class C00412 extends WebChromeClient {
        C00412() {
        }

        public void onProgressChanged(WebView view, int progress) {
            Activity a = (Activity) view.getContext();
            a.setTitle("Loading...");
            a.setProgress(progress * 100);
            if (progress == 100) {
                a.setTitle(view.getUrl());
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(2);
        getWindow().setFeatureInt(2, -1);
        initializeWebView(getIntent());
    }

    private void initializeWebView(Intent intent) {
        WebView webView = new WebView(this);
        setContentView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webView.setWebViewClient(new C00401());
        webView.setWebChromeClient(new C00412());
        webView.loadUrl(intent.getStringExtra(Const.REDIRECT_URI));
    }
}
