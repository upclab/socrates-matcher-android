package com.adsdk.sdk.mraid;

import android.widget.FrameLayout.LayoutParams;
import com.adsdk.sdk.mraid.MraidView.OnCloseListener;
import com.adsdk.sdk.mraid.MraidView.OnExpandListener;
import com.adsdk.sdk.mraid.MraidView.OnFailureListener;
import com.adsdk.sdk.mraid.MraidView.OnReadyListener;
import com.adsdk.sdk.mraid.MraidView.ViewState;

public class MraidAdapter extends BaseAdapter {
    private MraidView mMraidView;
    private boolean mPreviousAutorefreshSetting;

    /* renamed from: com.adsdk.sdk.mraid.MraidAdapter.1 */
    class C04381 implements OnReadyListener {
        C04381() {
        }

        public void onReady(MraidView view) {
            if (!MraidAdapter.this.isInvalidated()) {
                MraidAdapter.this.mMoPubView.nativeAdLoaded();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidAdapter.2 */
    class C04392 implements OnExpandListener {
        C04392() {
        }

        public void onExpand(MraidView view) {
            if (!MraidAdapter.this.isInvalidated()) {
                MraidAdapter.this.mPreviousAutorefreshSetting = MraidAdapter.this.mMoPubView.getAutorefreshEnabled();
                MraidAdapter.this.mMoPubView.setAutorefreshEnabled(false);
                MraidAdapter.this.mMoPubView.adPresentedOverlay();
                MraidAdapter.this.mMoPubView.registerClick();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidAdapter.3 */
    class C04403 implements OnCloseListener {
        C04403() {
        }

        public void onClose(MraidView view, ViewState newViewState) {
            if (!MraidAdapter.this.isInvalidated()) {
                MraidAdapter.this.mMoPubView.setAutorefreshEnabled(MraidAdapter.this.mPreviousAutorefreshSetting);
                MraidAdapter.this.mMoPubView.adClosed();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidAdapter.4 */
    class C04414 implements OnFailureListener {
        C04414() {
        }

        public void onFailure(MraidView view) {
            if (!MraidAdapter.this.isInvalidated()) {
                MraidAdapter.this.mMoPubView.loadFailUrl();
            }
        }
    }

    public void init(MoPubView view, String jsonParams) {
        super.init(view, jsonParams);
        this.mPreviousAutorefreshSetting = false;
    }

    public void loadAd() {
        if (!isInvalidated()) {
            this.mMraidView = new MraidView(this.mMoPubView.getContext());
            this.mMraidView.loadHtmlData(this.mJsonParams);
            initMraidListeners();
            this.mMoPubView.removeAllViews();
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            layoutParams.gravity = 17;
            this.mMoPubView.addView(this.mMraidView, layoutParams);
        }
    }

    public void invalidate() {
        this.mMoPubView = null;
        if (this.mMraidView != null) {
            this.mMraidView.destroy();
        }
        super.invalidate();
    }

    private void initMraidListeners() {
        this.mMraidView.setOnReadyListener(new C04381());
        this.mMraidView.setOnExpandListener(new C04392());
        this.mMraidView.setOnCloseListener(new C04403());
        this.mMraidView.setOnFailureListener(new C04414());
    }
}
