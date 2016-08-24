package com.adsdk.sdk.video;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.location.LocationRequest;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;
import org.joda.time.DateTimeConstants;

public class InterstitialController extends LinearLayout {
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private double buttonWidthPercent;
    private boolean mAutoclose;
    private AspectRatioImageViewWidth mBackButton;
    private OnClickListener mBackListener;
    private LinearLayout mBottomBar;
    private AspectRatioImageView mBottomBarBackground;
    private BrowserControl mBrowser;
    private FrameLayout mBrowserView;
    private Context mContext;
    private int mDefaultTimeout;
    private AspectRatioImageViewWidth mExternalButton;
    private OnClickListener mExternalListener;
    private boolean mFixed;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private AspectRatioImageViewWidth mForwardButton;
    private OnClickListener mForwardListener;
    private Handler mHandler;
    private InterstitialData mInterstitialData;
    private TextView mLeftTime;
    private LinearLayout mNavIconsLayout;
    private OnReloadListener mOnReloadListener;
    private OnResetAutocloseListener mOnResetAutocloseListener;
    private AspectRatioImageViewWidth mReloadButton;
    private OnClickListener mReloadListener;
    private ResourceManager mResourceManager;
    private boolean mShowing;
    private String mTitle;
    private TextView mTitleText;
    private LinearLayout mTopBar;
    private AspectRatioImageView mTopBarBackground;

    /* renamed from: com.adsdk.sdk.video.InterstitialController.1 */
    class C00521 implements OnClickListener {
        C00521() {
        }

        public void onClick(View v) {
            if (InterstitialController.this.mBrowser != null) {
                InterstitialController.this.mBrowser.goBack();
            }
            InterstitialController.this.show(InterstitialController.this.mDefaultTimeout);
        }
    }

    /* renamed from: com.adsdk.sdk.video.InterstitialController.2 */
    class C00532 implements OnClickListener {
        C00532() {
        }

        public void onClick(View v) {
            InterstitialController.this.reload();
        }
    }

    /* renamed from: com.adsdk.sdk.video.InterstitialController.3 */
    class C00543 implements OnClickListener {
        C00543() {
        }

        public void onClick(View v) {
            if (InterstitialController.this.mBrowser != null) {
                InterstitialController.this.mBrowser.goForward();
            }
            InterstitialController.this.show(InterstitialController.this.mDefaultTimeout);
        }
    }

    /* renamed from: com.adsdk.sdk.video.InterstitialController.4 */
    class C00554 implements OnClickListener {
        C00554() {
        }

        public void onClick(View v) {
            if (InterstitialController.this.mBrowser != null) {
                InterstitialController.this.mBrowser.launchExternalBrowser();
            }
        }
    }

    public interface BrowserControl {
        boolean canGoBack();

        boolean canGoForward();

        String getPageTitle();

        int getTime();

        void goBack();

        void goForward();

        void launchExternalBrowser();

        void reload();
    }

    public interface OnReloadListener {
        void onInterstitialReload();
    }

    public interface OnResetAutocloseListener {
        void onResetAutoclose();
    }

    private static class ResourceHandler extends Handler {
        WeakReference<InterstitialController> interstitialController;

        public ResourceHandler(InterstitialController i) {
            this.interstitialController = new WeakReference(i);
        }

        public void handleMessage(Message msg) {
            InterstitialController wInterstitialController = (InterstitialController) this.interstitialController.get();
            if (wInterstitialController != null) {
                switch (msg.what) {
                    case InterstitialController.SHOW_PROGRESS /*2*/:
                        wInterstitialController.setProgress();
                        if (wInterstitialController.mShowing && wInterstitialController.mInterstitialData.showTimer) {
                            sendMessageDelayed(obtainMessage(InterstitialController.SHOW_PROGRESS), 1000);
                        }
                    default:
                        wInterstitialController.handleMessage(msg);
                }
            }
        }
    }

    public InterstitialController(Context context, InterstitialData interstitialData) {
        super(context);
        this.buttonWidthPercent = 0.09d;
        this.mBackListener = new C00521();
        this.mReloadListener = new C00532();
        this.mForwardListener = new C00543();
        this.mExternalListener = new C00554();
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
        this.mContext = context;
        this.mInterstitialData = interstitialData;
        if (this.mInterstitialData == null) {
            throw new IllegalArgumentException("Interstitial info cannot be null");
        }
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        this.mFixed = false;
        this.mAutoclose = this.mInterstitialData.autoclose > 0;
        this.mDefaultTimeout = DEFAULT_TIMEOUT;
        if (!(this.mInterstitialData == null || this.mInterstitialData.allowTapNavigationBars)) {
            this.mDefaultTimeout = 0;
        }
        this.mHandler = new ResourceHandler(this);
        this.mResourceManager = new ResourceManager(this.mContext, this.mHandler);
        buildNavigationBarView(metrics);
    }

    public void setBrowserView(View browserView) {
        this.mBrowserView.addView(browserView, new LayoutParams(-1, -1, 17));
    }

    public void setBrowser(BrowserControl browser) {
        this.mBrowser = browser;
        updateBackForward();
    }

    private void buildNavigationBarView(DisplayMetrics metrics) {
        setWeightSum(AugmentedReality.ONE_PERCENT);
        setOrientation(FADE_OUT);
        setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.mTopBar = new LinearLayout(this.mContext);
        this.mTopBar.setOrientation(0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.gravity = 48;
        params.weight = 0.0f;
        this.mTopBar.setLayoutParams(params);
        this.mTopBar.setBackgroundColor(0);
        this.mTopBar.setGravity(17);
        int padding = (int) TypedValue.applyDimension(FADE_OUT, 4.0f, getResources().getDisplayMetrics());
        this.mTopBar.setPadding(padding, 0, padding, 0);
        this.mTopBarBackground = new AspectRatioImageView(this.mContext);
        this.mTopBarBackground.setLayoutParams(params);
        this.mTopBarBackground.fillParent(true, getWidth(), this.mTopBar.getHeight());
        addView(this.mTopBarBackground, params);
        addView(this.mTopBar, params);
        this.mTitleText = new TextView(this.mContext);
        params = new LinearLayout.LayoutParams(-2, -2, AugmentedReality.ONE_PERCENT);
        params.gravity = 17;
        this.mTitleText.setTextAppearance(this.mContext, 16973894);
        this.mTitleText.setTypeface(Typeface.defaultFromStyle(FADE_OUT));
        this.mTitleText.setGravity(17);
        this.mTopBar.addView(this.mTitleText, params);
        this.mBrowserView = new FrameLayout(this.mContext);
        LinearLayout.LayoutParams browserParams = new LinearLayout.LayoutParams(-1, -2);
        browserParams.gravity = 48;
        browserParams.weight = AugmentedReality.ONE_PERCENT;
        this.mBrowserView.setBackgroundColor(-1);
        addView(this.mBrowserView, browserParams);
        this.mBottomBar = new LinearLayout(this.mContext);
        this.mBottomBar.setOrientation(0);
        params = new LinearLayout.LayoutParams(-1, (int) (((double) metrics.widthPixels) * 0.119d));
        params.gravity = 80;
        params.weight = 0.0f;
        this.mBottomBar.setLayoutParams(params);
        this.mBottomBar.setBackgroundColor(0);
        this.mBottomBar.setGravity(16);
        this.mBottomBar.setWeightSum(AugmentedReality.ONE_PERCENT);
        this.mBottomBarBackground = new AspectRatioImageView(this.mContext);
        this.mBottomBarBackground.fillParent(true, getWidth(), this.mBottomBar.getHeight());
        addView(this.mBottomBarBackground);
        addView(this.mBottomBar, params);
        LinearLayout buttonPanel = new LinearLayout(this.mContext);
        params = new LinearLayout.LayoutParams(-2, -1, 0.0f);
        params.gravity = 19;
        buttonPanel.setOrientation(0);
        this.mBottomBar.addView(buttonPanel, params);
        padding = (int) TypedValue.applyDimension(FADE_OUT, 4.0f, getResources().getDisplayMetrics());
        this.mReloadButton = new AspectRatioImageViewWidth(this.mContext);
        params = new LinearLayout.LayoutParams((int) (((double) metrics.widthPixels) * this.buttonWidthPercent), (int) (((double) metrics.widthPixels) * this.buttonWidthPercent));
        params.leftMargin = 4;
        params.rightMargin = 4;
        params.gravity = 16;
        this.mReloadButton.setAdjustViewBounds(true);
        this.mReloadButton.setPadding(padding, 0, padding, 0);
        buttonPanel.addView(this.mReloadButton, params);
        this.mBackButton = new AspectRatioImageViewWidth(this.mContext);
        this.mBackButton.setPadding(padding, 0, padding, 0);
        buttonPanel.addView(this.mBackButton, params);
        this.mForwardButton = new AspectRatioImageViewWidth(this.mContext);
        this.mForwardButton.setPadding(padding, 0, padding, 0);
        buttonPanel.addView(this.mForwardButton, params);
        this.mExternalButton = new AspectRatioImageViewWidth(this.mContext);
        this.mExternalButton.setPadding(padding, 0, padding, 0);
        buttonPanel.addView(this.mExternalButton, params);
        this.mLeftTime = new AutoResizeTextView(this.mContext);
        params = new LinearLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        this.mLeftTime.setTypeface(Typeface.defaultFromStyle(FADE_OUT));
        buttonPanel.addView(this.mLeftTime, params);
        this.mNavIconsLayout = new LinearLayout(this.mContext);
        params = new LinearLayout.LayoutParams(-2, -2, AugmentedReality.ONE_PERCENT);
        params.gravity = 21;
        this.mNavIconsLayout.setOrientation(0);
        this.mNavIconsLayout.setPadding(0, 0, 0, 0);
        this.mNavIconsLayout.setGravity(21);
        this.mBottomBar.addView(this.mNavIconsLayout, params);
        initNavigationBarControllerView(padding, metrics);
    }

    private void initNavigationBarControllerView(int padding, DisplayMetrics metrics) {
        if (this.mInterstitialData.showBottomNavigationBar) {
            this.mBottomBar.setVisibility(0);
            if (this.mInterstitialData.bottomNavigationBarBackground == null || this.mInterstitialData.bottomNavigationBarBackground.length() <= 0) {
                this.mBottomBar.setBackgroundDrawable(this.mResourceManager.getResource(this.mContext, -2));
            } else {
                this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.bottomNavigationBarBackground, -2);
            }
            if (this.mBackButton != null) {
                if (this.mInterstitialData.backButtonImage == null || this.mInterstitialData.backButtonImage.length() <= 0) {
                    this.mBackButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -14));
                    this.mBackButton.setEnabled(false);
                } else {
                    this.mBackButton.setBackgroundDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.backButtonImage, -14);
                }
                this.mBackButton.setOnClickListener(this.mBackListener);
                if (this.mInterstitialData.showBackButton) {
                    this.mBackButton.setVisibility(0);
                } else {
                    this.mBackButton.setVisibility(8);
                }
            }
            if (this.mForwardButton != null) {
                if (this.mInterstitialData.forwardButtonImage == null || this.mInterstitialData.forwardButtonImage.length() <= 0) {
                    this.mForwardButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -15));
                } else {
                    this.mForwardButton.setBackgroundDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.forwardButtonImage, -15);
                }
                this.mForwardButton.setOnClickListener(this.mForwardListener);
                if (this.mInterstitialData.showForwardButton) {
                    this.mForwardButton.setVisibility(0);
                } else {
                    this.mForwardButton.setVisibility(8);
                }
            }
            if (this.mReloadButton != null) {
                if (this.mInterstitialData.reloadButtonImage == null || this.mInterstitialData.reloadButtonImage.length() <= 0) {
                    this.mReloadButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -16));
                } else {
                    this.mReloadButton.setBackgroundDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.reloadButtonImage, -16);
                }
                this.mReloadButton.setOnClickListener(this.mReloadListener);
                if (this.mInterstitialData.showReloadButton) {
                    this.mReloadButton.setVisibility(0);
                } else {
                    this.mReloadButton.setVisibility(8);
                }
            }
            if (this.mExternalButton != null) {
                if (this.mInterstitialData.externalButtonImage == null || this.mInterstitialData.externalButtonImage.length() <= 0) {
                    this.mExternalButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -17));
                } else {
                    this.mExternalButton.setBackgroundDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.externalButtonImage, -17);
                }
                this.mExternalButton.setOnClickListener(this.mExternalListener);
                if (this.mInterstitialData.showExternalButton) {
                    this.mExternalButton.setVisibility(0);
                } else {
                    this.mExternalButton.setVisibility(8);
                }
            }
            this.mFormatBuilder = new StringBuilder();
            this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
            if (this.mLeftTime != null) {
                if (this.mInterstitialData.showTimer && this.mAutoclose) {
                    this.mLeftTime.setVisibility(0);
                } else {
                    this.mLeftTime.setVisibility(8);
                }
            }
            if (!this.mInterstitialData.icons.isEmpty()) {
                for (int i = 0; i < this.mInterstitialData.icons.size(); i += FADE_OUT) {
                    this.mNavIconsLayout.addView(new NavIcon(this.mContext, (NavIconData) this.mInterstitialData.icons.get(i)), new LinearLayout.LayoutParams((int) (((double) metrics.widthPixels) * this.buttonWidthPercent), (int) (((double) metrics.widthPixels) * this.buttonWidthPercent)));
                }
            }
        } else {
            this.mBottomBar.setVisibility(8);
        }
        if (this.mInterstitialData.showTopNavigationBar) {
            this.mTopBar.setVisibility(0);
            if (this.mInterstitialData.topNavigationBarBackground == null || this.mInterstitialData.topNavigationBarBackground.length() <= 0) {
                this.mTopBarBackground.setImageDrawable(this.mResourceManager.getResource(this.mContext, -1));
            } else {
                this.mResourceManager.fetchResource(this.mContext, this.mInterstitialData.topNavigationBarBackground, -1);
            }
            if (this.mTitleText != null) {
                if (this.mInterstitialData.topNavigationBarTitleType == 0) {
                    this.mTitleText.setText(this.mInterstitialData.topNavigationBarTitle);
                } else if (this.mInterstitialData.topNavigationBarTitleType == SHOW_PROGRESS) {
                    this.mTitleText.setVisibility(8);
                }
            }
        } else {
            this.mTopBar.setVisibility(8);
        }
        if (!this.mInterstitialData.showNavigationBars) {
            if (this.mTopBar != null) {
                this.mTopBar.setVisibility(8);
            }
            if (this.mBottomBar != null) {
                this.mBottomBar.setVisibility(8);
            }
        }
    }

    public void resetAutoclose() {
        if (this.mAutoclose) {
            this.mAutoclose = false;
            if (this.mOnResetAutocloseListener != null) {
                this.mOnResetAutocloseListener.onResetAutoclose();
            }
        }
    }

    public void show() {
        show(this.mDefaultTimeout);
    }

    public void show(int timeout) {
        if (timeout == 0) {
            this.mFixed = true;
        }
        if (!this.mShowing) {
            setProgress();
            if (this.mTopBar != null && this.mInterstitialData.showTopNavigationBar) {
                this.mTopBar.setVisibility(0);
            }
            if (this.mBottomBar != null && this.mInterstitialData.showBottomNavigationBar) {
                this.mBottomBar.setVisibility(0);
            }
            this.mShowing = true;
        }
        updateBackForward();
        this.mHandler.removeMessages(FADE_OUT);
        this.mHandler.sendEmptyMessage(SHOW_PROGRESS);
        if (timeout != 0 && !this.mFixed) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(FADE_OUT), (long) timeout);
        }
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public void hide() {
        if (canToggle() && this.mShowing) {
            this.mHandler.removeMessages(SHOW_PROGRESS);
            if (this.mTopBar != null) {
                this.mTopBar.setVisibility(8);
            }
            if (this.mBottomBar != null) {
                this.mBottomBar.setVisibility(8);
            }
            this.mShowing = false;
            this.mFixed = false;
        }
    }

    public void resizeTopBar(int bottom) {
        if (bottom > 0) {
            int padding = (int) TypedValue.applyDimension(FADE_OUT, 4.0f, getResources().getDisplayMetrics());
            if (this.mTopBar != null) {
                ViewGroup.LayoutParams params = this.mTopBar.getLayoutParams();
                params.height = bottom + padding;
                this.mTopBar.setLayoutParams(params);
            }
        }
    }

    public void toggle() {
        if (!canToggle()) {
            return;
        }
        if (this.mShowing) {
            hide();
        } else {
            show();
        }
    }

    public void reload() {
        if (this.mBrowser != null) {
            this.mBrowser.reload();
        }
        setProgress();
        show(this.mDefaultTimeout);
        if (this.mOnReloadListener != null) {
            this.mOnReloadListener.onInterstitialReload();
        }
    }

    public void pageLoaded() {
        setProgress();
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case FADE_OUT /*1*/:
                hide();
            case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                Drawable d;
                switch (msg.arg1) {
                    case ResourceManager.DEFAULT_EXTERNAL_IMAGE_RESOURCE_ID /*-17*/:
                        if (this.mExternalButton != null) {
                            d = this.mResourceManager.getResource(this.mContext, -17);
                            if (d != null) {
                                this.mExternalButton.setImageDrawable(d);
                            }
                        }
                    case ResourceManager.DEFAULT_RELOAD_IMAGE_RESOURCE_ID /*-16*/:
                        if (this.mReloadButton != null) {
                            d = this.mResourceManager.getResource(this.mContext, -16);
                            if (d != null) {
                                this.mReloadButton.setImageDrawable(d);
                            }
                        }
                    case ResourceManager.DEFAULT_FORWARD_IMAGE_RESOURCE_ID /*-15*/:
                        if (this.mForwardButton != null) {
                            d = this.mResourceManager.getResource(this.mContext, -15);
                            if (d != null) {
                                this.mForwardButton.setImageDrawable(d);
                            }
                        }
                    case ResourceManager.DEFAULT_BACK_IMAGE_RESOURCE_ID /*-14*/:
                        if (this.mBackButton != null) {
                            d = this.mResourceManager.getResource(this.mContext, -14);
                            if (d != null) {
                                this.mBackButton.setImageDrawable(d);
                            }
                        }
                    case ResourceManager.DEFAULT_BOTTOMBAR_BG_RESOURCE_ID /*-2*/:
                        if (this.mBottomBar != null) {
                            d = this.mResourceManager.getResource(this.mContext, -2);
                            if (d != null) {
                                this.mBottomBar.setBackgroundDrawable(d);
                            }
                        }
                    case Room.ROOM_VARIANT_ANY /*-1*/:
                        if (this.mTopBar != null) {
                            d = this.mResourceManager.getResource(this.mContext, -1);
                            if (d != null) {
                                this.mTopBarBackground.setImageDrawable(d);
                            }
                        }
                    default:
                }
            default:
        }
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / DateTimeConstants.MILLIS_PER_SECOND;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / DateTimeConstants.SECONDS_PER_HOUR;
        this.mFormatBuilder.setLength(0);
        if (hours > 0) {
            return this.mFormatter.format("%d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)}).toString();
        } else if (minutes > 0) {
            r6 = new Object[SHOW_PROGRESS];
            r6[0] = Integer.valueOf(minutes);
            r6[FADE_OUT] = Integer.valueOf(seconds);
            return this.mFormatter.format("%02d:%02d", r6).toString();
        } else {
            r6 = new Object[FADE_OUT];
            r6[0] = Integer.valueOf(seconds);
            return this.mFormatter.format("0:%02d", r6).toString();
        }
    }

    private int setProgress() {
        int position = 0;
        if (this.mBrowser != null) {
            position = this.mBrowser.getTime();
        }
        int duration = this.mInterstitialData.autoclose * DateTimeConstants.MILLIS_PER_SECOND;
        int timeLeft = duration - position;
        if (!this.mAutoclose || duration <= 0 || timeLeft < 0) {
            if (this.mLeftTime != null) {
                this.mLeftTime.setVisibility(8);
            }
        } else if (this.mLeftTime != null) {
            this.mLeftTime.setText(stringForTime(timeLeft));
        }
        if (this.mTitleText != null && this.mInterstitialData.topNavigationBarTitleType == FADE_OUT && this.mBrowser != null && (this.mTitle == null || !this.mTitle.equals(this.mBrowser.getPageTitle()))) {
            this.mTitle = this.mBrowser.getPageTitle();
            this.mTitleText.setText(this.mTitle);
        }
        updateBackForward();
        return position;
    }

    private void updateBackForward() {
        if (this.mBrowser != null) {
            if (this.mBrowser.canGoBack()) {
                if (this.mBackButton != null) {
                    this.mBackButton.setEnabled(true);
                }
            } else if (this.mBackButton != null) {
                this.mBackButton.setEnabled(false);
            }
            if (this.mBrowser.canGoForward()) {
                if (this.mForwardButton != null) {
                    this.mForwardButton.setEnabled(true);
                }
            } else if (this.mForwardButton != null) {
                this.mForwardButton.setEnabled(false);
            }
        }
    }

    public boolean canToggle() {
        return this.mInterstitialData.allowTapNavigationBars;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        resetAutoclose();
        return true;
    }

    public void setOnReloadListener(OnReloadListener l) {
        this.mOnReloadListener = l;
    }

    public void setOnResetAutocloseListener(OnResetAutocloseListener l) {
        this.mOnResetAutocloseListener = l;
    }
}
