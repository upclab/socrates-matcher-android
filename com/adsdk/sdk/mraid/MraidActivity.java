package com.adsdk.sdk.mraid;

import android.view.View;
import com.adsdk.sdk.mraid.MraidView.OnCloseButtonStateChangeListener;
import com.adsdk.sdk.mraid.MraidView.OnCloseListener;
import com.adsdk.sdk.mraid.MraidView.OnReadyListener;
import com.adsdk.sdk.mraid.MraidView.ViewState;

public class MraidActivity extends BaseActivity {
    private MraidView mAdView;

    /* renamed from: com.adsdk.sdk.mraid.MraidActivity.1 */
    class C04351 implements OnReadyListener {
        C04351() {
        }

        public void onReady(MraidView view) {
            MraidActivity.this.showInterstitialCloseButton();
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidActivity.2 */
    class C04362 implements OnCloseButtonStateChangeListener {
        C04362() {
        }

        public void onCloseButtonStateChange(MraidView view, boolean enabled) {
            if (enabled) {
                MraidActivity.this.showInterstitialCloseButton();
            } else {
                MraidActivity.this.hideInterstitialCloseButton();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidActivity.3 */
    class C04373 implements OnCloseListener {
        C04373() {
        }

        public void onClose(MraidView view, ViewState newViewState) {
            MraidActivity.this.finish();
        }
    }

    public View getAdView() {
        this.mAdView = new MraidView(this, ExpansionStyle.DISABLED, NativeCloseButtonStyle.AD_CONTROLLED, PlacementType.INTERSTITIAL);
        this.mAdView.setOnReadyListener(new C04351());
        this.mAdView.setOnCloseButtonStateChange(new C04362());
        this.mAdView.setOnCloseListener(new C04373());
        this.mAdView.loadHtmlData(getIntent().getStringExtra("com.adsdk.sdk.mraid.Source"));
        return this.mAdView;
    }

    protected void onDestroy() {
        this.mAdView.destroy();
        super.onDestroy();
    }
}
