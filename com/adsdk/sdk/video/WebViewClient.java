package com.adsdk.sdk.video;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

public class WebViewClient extends android.webkit.WebViewClient {
    private Activity mActivity;
    private boolean mAllowNavigation;
    private String mAllowedUrl;
    private long mFinishedLoadingTime;
    private OnPageLoadedListener mOnPageLoadedListener;

    public interface OnPageLoadedListener {
        void onPageLoaded();
    }

    public WebViewClient(Activity activity, boolean allowNavigation) {
        this.mAllowNavigation = false;
        this.mActivity = activity;
        this.mAllowNavigation = allowNavigation;
        this.mFinishedLoadingTime = 0;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("market:") || url.startsWith("http://market.android.com") || url.startsWith("sms:") || url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("voicemail:") || url.startsWith("geo:") || url.startsWith("google.streetview:")) {
            try {
                this.mActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (ActivityNotFoundException e) {
            }
        } else if (url.startsWith("mfox:external:")) {
            this.mActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url.substring(14))));
        } else if (url.startsWith("mfox:replayvideo")) {
            try {
                this.mActivity.getClass().getMethod("replayVideo", new Class[0]).invoke(this.mActivity, new Object[0]);
            } catch (NoSuchMethodException e2) {
            } catch (Exception e3) {
            }
        } else if (url.startsWith("mfox:playvideo")) {
            try {
                this.mActivity.getClass().getMethod("playVideo", new Class[0]).invoke(this.mActivity, new Object[0]);
            } catch (NoSuchMethodException e4) {
            } catch (Exception e5) {
            }
        } else if (url.startsWith("mfox:skip")) {
            this.mActivity.finish();
        } else if (this.mAllowNavigation || url.equals(this.mAllowedUrl)) {
            view.loadUrl(url);
        } else {
            Intent intent = new Intent(this.mActivity, RichMediaActivity.class);
            intent.setData(Uri.parse(url));
            this.mActivity.startActivity(intent);
        }
        return true;
    }

    public void setAllowedUrl(String url) {
        this.mFinishedLoadingTime = 0;
        this.mAllowedUrl = url;
        if (this.mAllowedUrl != null) {
            String path = Uri.parse(this.mAllowedUrl).getPath();
            if (path == null || path.length() == 0) {
                this.mAllowedUrl += "/";
            }
        }
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (this.mAllowedUrl == null || url.equals(this.mAllowedUrl)) {
            if (this.mFinishedLoadingTime == 0) {
                this.mFinishedLoadingTime = System.currentTimeMillis();
            }
            if (this.mOnPageLoadedListener != null) {
                this.mOnPageLoadedListener.onPageLoaded();
            }
        }
    }

    public long getFinishedLoadingTime() {
        return this.mFinishedLoadingTime;
    }

    public String getAllowedUrl() {
        return this.mAllowedUrl;
    }

    public void setOnPageLoadedListener(OnPageLoadedListener l) {
        this.mOnPageLoadedListener = l;
    }
}
