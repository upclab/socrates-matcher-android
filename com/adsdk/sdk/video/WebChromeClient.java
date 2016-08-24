package com.adsdk.sdk.video;

import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient.CustomViewCallback;

public class WebChromeClient extends android.webkit.WebChromeClient {
    private RichMediaActivity mActivity;

    public WebChromeClient(Activity context) {
        if (context instanceof RichMediaActivity) {
            this.mActivity = (RichMediaActivity) context;
        }
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (this.mActivity == null) {
            super.onShowCustomView(view, callback);
        } else {
            this.mActivity.onShowCustomView(view, callback);
        }
    }

    public void onHideCustomView() {
        if (this.mActivity == null) {
            super.onHideCustomView();
        } else {
            this.mActivity.onHideCustomView();
        }
    }
}
