package com.adsdk.sdk.mraid;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.adsdk.sdk.video.ResourceManager;

public abstract class BaseActivity extends Activity {
    private static final float CLOSE_BUTTON_PADDING_DP = 8.0f;
    private static final float CLOSE_BUTTON_SIZE_DP = 50.0f;
    private ImageView mCloseButton;
    private RelativeLayout mLayout;

    /* renamed from: com.adsdk.sdk.mraid.BaseActivity.1 */
    class C00461 implements OnClickListener {
        C00461() {
        }

        public void onClick(View v) {
            BaseActivity.this.finish();
        }
    }

    public abstract View getAdView();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().addFlags(AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END);
        this.mLayout = new RelativeLayout(this);
        LayoutParams adViewLayout = new LayoutParams(-1, -2);
        adViewLayout.addRule(13);
        this.mLayout.addView(getAdView(), adViewLayout);
        setContentView(this.mLayout);
        showInterstitialCloseButton();
    }

    protected void showInterstitialCloseButton() {
        if (this.mCloseButton == null) {
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{-16842919}, ResourceManager.getStaticResource(this, -29));
            states.addState(new int[]{16842919}, ResourceManager.getStaticResource(this, -30));
            this.mCloseButton = new ImageButton(this);
            this.mCloseButton.setImageDrawable(states);
            this.mCloseButton.setBackgroundDrawable(null);
            this.mCloseButton.setOnClickListener(new C00461());
        }
        float scale = getResources().getDisplayMetrics().density;
        int buttonSize = (int) ((CLOSE_BUTTON_SIZE_DP * scale) + 0.5f);
        int buttonPadding = (int) ((CLOSE_BUTTON_PADDING_DP * scale) + 0.5f);
        LayoutParams buttonLayout = new LayoutParams(buttonSize, buttonSize);
        buttonLayout.addRule(11);
        buttonLayout.setMargins(buttonPadding, 0, buttonPadding, 0);
        this.mLayout.removeView(this.mCloseButton);
        this.mLayout.addView(this.mCloseButton, buttonLayout);
    }

    protected void hideInterstitialCloseButton() {
        this.mLayout.removeView(this.mCloseButton);
    }

    protected void onDestroy() {
        this.mLayout.removeAllViews();
        super.onDestroy();
    }
}
