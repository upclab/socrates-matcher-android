package com.adsdk.sdk.video;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.Const;
import com.adsdk.sdk.Util;
import com.adsdk.sdk.video.InterstitialController.OnResetAutocloseListener;
import com.adsdk.sdk.video.MediaController.OnPauseListener;
import com.adsdk.sdk.video.MediaController.OnReplayListener;
import com.adsdk.sdk.video.MediaController.OnUnpauseListener;
import com.adsdk.sdk.video.SDKVideoView.OnStartListener;
import com.adsdk.sdk.video.SDKVideoView.OnTimeEventListener;
import com.adsdk.sdk.video.WebViewClient.OnPageLoadedListener;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.location.LocationRequest;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import org.joda.time.DateTimeConstants;

public class RichMediaActivity extends Activity {
    public static final int TYPE_BROWSER = 0;
    public static final int TYPE_INTERSTITIAL = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_VIDEO = 1;
    private RichMediaAd mAd;
    private boolean mCanClose;
    private Runnable mCheckProgressTask;
    private VideoView mCustomVideoView;
    private FrameLayout mCustomView;
    private CustomViewCallback mCustomViewCallback;
    private int mEnterAnim;
    private int mExitAnim;
    private ResourceHandler mHandler;
    protected boolean mInterstitialAutocloseReset;
    private Timer mInterstitialAutocloseTimer;
    private Timer mInterstitialCanCloseTimer;
    private final OnClickListener mInterstitialClickListener;
    private InterstitialController mInterstitialController;
    private InterstitialData mInterstitialData;
    private Timer mInterstitialLoadingTimer;
    private WebFrame mInterstitialView;
    private FrameLayout mLoadingView;
    private MediaController mMediaController;
    OnPageLoadedListener mOnInterstitialLoadedListener;
    OnClickListener mOnInterstitialSkipListener;
    OnResetAutocloseListener mOnResetAutocloseListener;
    OnTimeEventListener mOnVideoCanCloseEventListener;
    OnCompletionListener mOnVideoCompletionListener;
    OnErrorListener mOnVideoErrorListener;
    OnInfoListener mOnVideoInfoListener;
    OnPauseListener mOnVideoPauseListener;
    OnPreparedListener mOnVideoPreparedListener;
    OnReplayListener mOnVideoReplayListener;
    OnClickListener mOnVideoSkipListener;
    OnStartListener mOnVideoStartListener;
    OnTimeEventListener mOnVideoTimeEventListener;
    OnUnpauseListener mOnVideoUnpauseListener;
    OnPageLoadedListener mOnWebBrowserLoadedListener;
    private final OnClickListener mOverlayClickListener;
    private final OnTimeEventListener mOverlayShowListener;
    private WebFrame mOverlayView;
    private boolean mPageLoaded;
    private ResourceManager mResourceManager;
    private boolean mResult;
    private FrameLayout mRootLayout;
    private ImageView mSkipButton;
    protected int mTimeTest;
    private int mType;
    private VideoData mVideoData;
    private int mVideoHeight;
    private int mVideoLastPosition;
    private FrameLayout mVideoLayout;
    private Timer mVideoTimeoutTimer;
    private SDKVideoView mVideoView;
    private int mVideoWidth;
    private WebFrame mWebBrowserView;
    private int mWindowHeight;
    private int mWindowWidth;
    int marginArg;
    DisplayMetrics metrics;
    int paddingArg;
    int skipButtonSizeLand;
    int skipButtonSizePort;
    private Uri uri;

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.2 */
    class C00602 implements OnClickListener {
        C00602() {
        }

        public void onClick(View arg0) {
            if (RichMediaActivity.this.mMediaController != null) {
                RichMediaActivity.this.mMediaController.toggle();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.3 */
    class C00613 implements OnErrorListener {
        C00613() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            RichMediaActivity.this.finish();
            return false;
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.4 */
    class C00624 implements OnInfoListener {
        C00624() {
        }

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if (what == 703) {
                RichMediaActivity.this.mTimeTest = RichMediaActivity.this.mVideoView.getCurrentPosition();
                new Handler().postDelayed(RichMediaActivity.this.mCheckProgressTask, 5000);
            }
            return false;
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.5 */
    class C00635 implements Runnable {
        C00635() {
        }

        public void run() {
            if (RichMediaActivity.this.mVideoView.getCurrentPosition() - RichMediaActivity.this.mTimeTest <= RichMediaActivity.TYPE_VIDEO) {
                RichMediaActivity.this.finish();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.6 */
    class C00646 implements OnPreparedListener {
        C00646() {
        }

        public void onPrepared(MediaPlayer mp) {
            if (RichMediaActivity.this.mVideoTimeoutTimer != null) {
                RichMediaActivity.this.mVideoTimeoutTimer.cancel();
                RichMediaActivity.this.mVideoTimeoutTimer = null;
            }
            if (RichMediaActivity.this.mLoadingView != null) {
                RichMediaActivity.this.mLoadingView.setVisibility(8);
            }
            if (RichMediaActivity.this.mVideoData.showNavigationBars) {
                RichMediaActivity.this.mMediaController.setVisibility(RichMediaActivity.TYPE_BROWSER);
            }
            RichMediaActivity.this.mVideoView.requestFocus();
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.7 */
    class C00657 implements OnCompletionListener {
        C00657() {
        }

        public void onCompletion(MediaPlayer mp) {
            Vector<String> trackers = RichMediaActivity.this.mVideoData.completeEvents;
            for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                TrackEvent event = new TrackEvent();
                event.url = (String) trackers.get(i);
                event.timestamp = System.currentTimeMillis();
                TrackerService.requestTrack(event);
            }
            if (RichMediaActivity.this.mType == RichMediaActivity.TYPE_VIDEO && RichMediaActivity.this.mAd.getType() == 3) {
                Intent intent = new Intent(RichMediaActivity.this, RichMediaActivity.class);
                intent.putExtra(Const.AD_EXTRA, RichMediaActivity.this.mAd);
                intent.putExtra(Const.AD_TYPE_EXTRA, RichMediaActivity.TYPE_INTERSTITIAL);
                try {
                    RichMediaActivity.this.startActivity(intent);
                    RichMediaActivity.setActivityAnimation(RichMediaActivity.this, RichMediaActivity.this.mEnterAnim, RichMediaActivity.this.mExitAnim);
                } catch (Exception e) {
                }
            }
            RichMediaActivity.this.mResult = true;
            RichMediaActivity.this.setResult(RichMediaActivity.TYPE_UNKNOWN);
            RichMediaActivity.this.finish();
        }
    }

    class CanSkipTask extends TimerTask {
        private final RichMediaActivity mActivity;

        /* renamed from: com.adsdk.sdk.video.RichMediaActivity.CanSkipTask.1 */
        class C00661 implements Runnable {
            C00661() {
            }

            public void run() {
                CanSkipTask.this.mActivity.mSkipButton.setVisibility(RichMediaActivity.TYPE_BROWSER);
            }
        }

        public CanSkipTask(RichMediaActivity activity) {
            this.mActivity = activity;
        }

        public void run() {
            this.mActivity.mCanClose = true;
            if (this.mActivity.mSkipButton != null) {
                this.mActivity.runOnUiThread(new C00661());
            }
        }
    }

    class InterstitialAutocloseTask extends TimerTask {
        private final Activity mActivity;

        /* renamed from: com.adsdk.sdk.video.RichMediaActivity.InterstitialAutocloseTask.1 */
        class C00671 implements Runnable {
            C00671() {
            }

            public void run() {
                RichMediaActivity.this.setResult(RichMediaActivity.TYPE_UNKNOWN);
                RichMediaActivity.this.finish();
            }
        }

        public InterstitialAutocloseTask(Activity activity) {
            this.mActivity = activity;
        }

        public void run() {
            RichMediaActivity.this.mResult = true;
            this.mActivity.runOnUiThread(new C00671());
        }
    }

    class InterstitialLoadingTimeoutTask extends TimerTask {
        InterstitialLoadingTimeoutTask() {
        }

        public void run() {
            RichMediaActivity.this.mCanClose = true;
            RichMediaActivity.this.mInterstitialController.pageLoaded();
        }
    }

    static class ResourceHandler extends Handler {
        WeakReference<RichMediaActivity> richMediaActivity;

        public ResourceHandler(RichMediaActivity activity) {
            this.richMediaActivity = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            RichMediaActivity wRichMediaActivity = (RichMediaActivity) this.richMediaActivity.get();
            if (wRichMediaActivity != null) {
                wRichMediaActivity.handleMessage(msg);
            }
        }
    }

    class VideoTimeoutTask extends TimerTask {
        private final Activity mActivity;

        /* renamed from: com.adsdk.sdk.video.RichMediaActivity.VideoTimeoutTask.1 */
        class C00681 implements Runnable {
            C00681() {
            }

            public void run() {
                VideoTimeoutTask.this.mActivity.finish();
            }
        }

        public VideoTimeoutTask(Activity activity) {
            this.mActivity = activity;
        }

        public void run() {
            this.mActivity.runOnUiThread(new C00681());
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.1 */
    class C04471 implements OnTimeEventListener {
        C04471() {
        }

        public void onTimeEvent(int time) {
            if (RichMediaActivity.this.mOverlayView != null) {
                RichMediaActivity.this.mOverlayView.setVisibility(RichMediaActivity.TYPE_BROWSER);
                RichMediaActivity.this.mOverlayView.requestLayout();
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.8 */
    class C04488 implements OnStartListener {
        C04488() {
        }

        public void onVideoStart() {
            Vector<String> trackers = RichMediaActivity.this.mVideoData.startEvents;
            for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                TrackEvent event = new TrackEvent();
                event.url = (String) trackers.get(i);
                event.timestamp = System.currentTimeMillis();
                TrackerService.requestTrack(event);
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.RichMediaActivity.9 */
    class C04499 implements OnPauseListener {
        C04499() {
        }

        public void onVideoPause() {
            Vector<String> trackers = RichMediaActivity.this.mVideoData.pauseEvents;
            for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                TrackEvent event = new TrackEvent();
                event.url = (String) trackers.get(i);
                event.timestamp = System.currentTimeMillis();
                TrackerService.requestTrack(event);
            }
        }
    }

    public RichMediaActivity() {
        this.mPageLoaded = false;
        this.paddingArg = 5;
        this.marginArg = 8;
        this.skipButtonSizeLand = 50;
        this.skipButtonSizePort = 40;
        this.mOverlayShowListener = new C04471();
        this.mOverlayClickListener = new C00602();
        this.mOnVideoErrorListener = new C00613();
        this.mOnVideoInfoListener = new C00624();
        this.mCheckProgressTask = new C00635();
        this.mOnVideoPreparedListener = new C00646();
        this.mOnVideoCompletionListener = new C00657();
        this.mOnVideoStartListener = new C04488();
        this.mOnVideoPauseListener = new C04499();
        this.mOnVideoUnpauseListener = new OnUnpauseListener() {
            public void onVideoUnpause() {
                Vector<String> trackers = RichMediaActivity.this.mVideoData.unpauseEvents;
                for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                    TrackEvent event = new TrackEvent();
                    event.url = (String) trackers.get(i);
                    event.timestamp = System.currentTimeMillis();
                    TrackerService.requestTrack(event);
                }
            }
        };
        this.mOnVideoTimeEventListener = new OnTimeEventListener() {
            public void onTimeEvent(int time) {
                Vector<String> trackers = (Vector) RichMediaActivity.this.mVideoData.timeTrackingEvents.get(Integer.valueOf(time));
                if (trackers != null) {
                    for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                        TrackEvent event = new TrackEvent();
                        event.url = (String) trackers.get(i);
                        event.timestamp = System.currentTimeMillis();
                        TrackerService.requestTrack(event);
                    }
                }
            }
        };
        this.mOnVideoCanCloseEventListener = new OnTimeEventListener() {
            public void onTimeEvent(int time) {
                RichMediaActivity.this.mCanClose = true;
                if (RichMediaActivity.this.mVideoData.showSkipButton && RichMediaActivity.this.mSkipButton != null) {
                    RichMediaActivity.this.mSkipButton.setImageDrawable(RichMediaActivity.this.mResourceManager.getResource(RichMediaActivity.this, -18));
                    RichMediaActivity.this.mSkipButton.setVisibility(RichMediaActivity.TYPE_BROWSER);
                }
            }
        };
        this.mOnVideoSkipListener = new OnClickListener() {
            public void onClick(View v) {
                Vector<String> trackers = RichMediaActivity.this.mVideoData.skipEvents;
                for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                    TrackEvent event = new TrackEvent();
                    event.url = (String) trackers.get(i);
                    event.timestamp = System.currentTimeMillis();
                    TrackerService.requestTrack(event);
                }
                RichMediaActivity.this.mResult = true;
                RichMediaActivity.this.setResult(RichMediaActivity.TYPE_UNKNOWN);
                RichMediaActivity.this.finish();
            }
        };
        this.mOnVideoReplayListener = new OnReplayListener() {
            public void onVideoReplay() {
                Vector<String> trackers = RichMediaActivity.this.mVideoData.replayEvents;
                for (int i = RichMediaActivity.TYPE_BROWSER; i < trackers.size(); i += RichMediaActivity.TYPE_VIDEO) {
                    TrackEvent event = new TrackEvent();
                    event.url = (String) trackers.get(i);
                    event.timestamp = System.currentTimeMillis();
                    TrackerService.requestTrack(event);
                }
            }
        };
        this.mInterstitialClickListener = new OnClickListener() {
            public void onClick(View arg0) {
                if (RichMediaActivity.this.mInterstitialController != null) {
                    RichMediaActivity.this.mInterstitialController.toggle();
                    RichMediaActivity.this.mInterstitialController.resetAutoclose();
                }
            }
        };
        this.mOnInterstitialSkipListener = new OnClickListener() {
            public void onClick(View v) {
                RichMediaActivity.this.mResult = true;
                RichMediaActivity.this.setResult(RichMediaActivity.TYPE_UNKNOWN);
                RichMediaActivity.this.finish();
            }
        };
        this.mOnResetAutocloseListener = new OnResetAutocloseListener() {
            public void onResetAutoclose() {
                RichMediaActivity.this.mInterstitialAutocloseReset = true;
                if (RichMediaActivity.this.mInterstitialAutocloseTimer != null) {
                    RichMediaActivity.this.mInterstitialAutocloseTimer.cancel();
                    RichMediaActivity.this.mInterstitialAutocloseTimer = null;
                }
            }
        };
        this.mOnInterstitialLoadedListener = new OnPageLoadedListener() {
            public void onPageLoaded() {
                if (RichMediaActivity.this.mInterstitialData != null && RichMediaActivity.this.mInterstitialData.autoclose > 0 && RichMediaActivity.this.mInterstitialAutocloseTimer == null && !RichMediaActivity.this.mInterstitialAutocloseReset) {
                    InterstitialAutocloseTask autocloseTask = new InterstitialAutocloseTask(RichMediaActivity.this);
                    RichMediaActivity.this.mInterstitialAutocloseTimer = new Timer();
                    RichMediaActivity.this.mInterstitialAutocloseTimer.schedule(autocloseTask, (long) (RichMediaActivity.this.mInterstitialData.autoclose * DateTimeConstants.MILLIS_PER_SECOND));
                }
                if (RichMediaActivity.this.mInterstitialData == null || RichMediaActivity.this.mInterstitialData.showSkipButtonAfter <= 0) {
                    RichMediaActivity.this.mCanClose = true;
                } else if (RichMediaActivity.this.mInterstitialCanCloseTimer == null) {
                    CanSkipTask skipTask = new CanSkipTask(RichMediaActivity.this);
                    RichMediaActivity.this.mInterstitialCanCloseTimer = new Timer();
                    RichMediaActivity.this.mInterstitialCanCloseTimer.schedule(skipTask, (long) (RichMediaActivity.this.mInterstitialData.showSkipButtonAfter * DateTimeConstants.MILLIS_PER_SECOND));
                }
                if (RichMediaActivity.this.mInterstitialLoadingTimer != null) {
                    RichMediaActivity.this.mInterstitialLoadingTimer.cancel();
                    RichMediaActivity.this.mInterstitialLoadingTimer = null;
                }
                RichMediaActivity.this.mPageLoaded = true;
                RichMediaActivity.this.mInterstitialController.pageLoaded();
            }
        };
        this.mOnWebBrowserLoadedListener = new OnPageLoadedListener() {
            public void onPageLoaded() {
                RichMediaActivity.this.mPageLoaded = true;
            }
        };
    }

    public static void setActivityAnimation(Activity activity, int in, int out) {
        try {
            activity.overridePendingTransition(in, out);
        } catch (Exception e) {
        }
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                switch (msg.arg1) {
                    case ResourceManager.DEFAULT_SKIP_IMAGE_RESOURCE_ID /*-18*/:
                        if (this.mSkipButton == null) {
                            return;
                        }
                        if (this.mResourceManager.containsResource(-18)) {
                            this.mSkipButton.setImageDrawable(this.mResourceManager.getResource(this, -18));
                        } else {
                            this.mSkipButton.setImageDrawable(this.mResourceManager.getResource(this, -18));
                        }
                    default:
                }
            default:
        }
    }

    public void finish() {
        if (this.mAd != null) {
            switch (this.mType) {
                case TYPE_VIDEO /*1*/:
                    if (this.mAd.getType() != 5) {
                        if (this.mAd.getType() == 3 && !this.mResult) {
                            AdManager.closeRunningAd(this.mAd, this.mResult);
                            break;
                        }
                    }
                    AdManager.closeRunningAd(this.mAd, this.mResult);
                    break;
                case TYPE_INTERSTITIAL /*2*/:
                    if (this.mAd.getType() == 6 || this.mAd.getType() == 3 || this.mAd.getType() == 4) {
                        AdManager.closeRunningAd(this.mAd, this.mResult);
                        break;
                    }
            }
        }
        super.finish();
        setActivityAnimation(this, this.mEnterAnim, this.mExitAnim);
    }

    public int getDipSize(int argSize) {
        return (int) TypedValue.applyDimension(TYPE_VIDEO, (float) argSize, getResources().getDisplayMetrics());
    }

    public FrameLayout getRootLayout() {
        return this.mRootLayout;
    }

    public void goBack() {
        if (this.mCustomView != null) {
            onHideCustomView();
            return;
        }
        switch (this.mType) {
            case TYPE_BROWSER /*0*/:
                if (this.mWebBrowserView.canGoBack()) {
                    this.mWebBrowserView.goBack();
                } else {
                    finish();
                }
            case TYPE_VIDEO /*1*/:
                if (this.mCanClose) {
                    finish();
                }
            case TYPE_INTERSTITIAL /*2*/:
                if (this.mInterstitialView.canGoBack()) {
                    this.mInterstitialView.goBack();
                } else if (this.mCanClose) {
                    this.mResult = true;
                    setResult(TYPE_UNKNOWN);
                    finish();
                }
            default:
        }
    }

    private void initInterstitialView() {
        this.mInterstitialData = this.mAd.getInterstitial();
        this.mInterstitialAutocloseReset = false;
        setRequestedOrientation(this.mInterstitialData.orientation);
        FrameLayout layout = new FrameLayout(this);
        this.mInterstitialView = new WebFrame(this, true, false, false);
        this.mInterstitialView.setBackgroundColor(TYPE_BROWSER);
        this.mInterstitialView.setOnPageLoadedListener(this.mOnInterstitialLoadedListener);
        this.mInterstitialController = new InterstitialController(this, this.mInterstitialData);
        this.mInterstitialController.setBrowser(this.mInterstitialView);
        this.mInterstitialController.setBrowserView(this.mInterstitialView);
        this.mInterstitialController.setOnResetAutocloseListener(this.mOnResetAutocloseListener);
        layout.addView(this.mInterstitialController, new LayoutParams(TYPE_UNKNOWN, TYPE_UNKNOWN, 17));
        if (this.mInterstitialData.showNavigationBars) {
            this.mInterstitialController.show(TYPE_BROWSER);
        }
        if (this.mInterstitialData.showSkipButton) {
            this.mSkipButton = new ImageView(this);
            this.mSkipButton.setAdjustViewBounds(false);
            int buttonSize = (int) TypedValue.applyDimension(TYPE_VIDEO, (float) this.skipButtonSizeLand, getResources().getDisplayMetrics());
            buttonSize = (int) (((double) Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels)) * 0.1d);
            LayoutParams params = new LayoutParams(buttonSize, buttonSize, 53);
            int margin;
            if (this.mInterstitialData.orientation == TYPE_VIDEO) {
                margin = (int) TypedValue.applyDimension(TYPE_VIDEO, 8.0f, getResources().getDisplayMetrics());
                params.topMargin = margin;
                params.rightMargin = margin;
            } else {
                margin = (int) TypedValue.applyDimension(TYPE_VIDEO, AugmentedReality.TEN_PERCENT, getResources().getDisplayMetrics());
                params.topMargin = margin;
                params.rightMargin = margin;
            }
            if (this.mInterstitialData.skipButtonImage == null || this.mInterstitialData.skipButtonImage.length() <= 0) {
                this.mSkipButton.setImageDrawable(this.mResourceManager.getResource(this, -18));
            } else {
                this.mSkipButton.setBackgroundDrawable(null);
                this.mResourceManager.fetchResource(this, this.mInterstitialData.skipButtonImage, -18);
            }
            this.mSkipButton.setOnClickListener(this.mOnInterstitialSkipListener);
            if (this.mInterstitialData.showSkipButtonAfter > 0) {
                this.mCanClose = false;
                this.mSkipButton.setVisibility(8);
                if (this.mInterstitialLoadingTimer == null) {
                    InterstitialLoadingTimeoutTask loadingTimeoutTask = new InterstitialLoadingTimeoutTask();
                    this.mInterstitialLoadingTimer = new Timer();
                    this.mInterstitialLoadingTimer.schedule(loadingTimeoutTask, 10000);
                }
            } else {
                this.mCanClose = true;
                this.mSkipButton.setVisibility(TYPE_BROWSER);
            }
            layout.addView(this.mSkipButton, params);
        } else {
            this.mCanClose = false;
        }
        this.mInterstitialView.setOnClickListener(this.mInterstitialClickListener);
        this.mRootLayout.addView(layout);
        switch (this.mInterstitialData.interstitialType) {
            case TYPE_BROWSER /*0*/:
                this.mInterstitialView.loadUrl(this.mInterstitialData.interstitialUrl);
            case TYPE_VIDEO /*1*/:
                this.mInterstitialView.setMarkup(this.mInterstitialData.interstitialMarkup);
            default:
        }
    }

    private void initRootLayout() {
        this.mRootLayout = new FrameLayout(this);
        this.mRootLayout.setBackgroundColor(-16777216);
    }

    private void initVideoView() {
        LayoutParams params;
        this.mVideoData = this.mAd.getVideo();
        setRequestedOrientation(this.mVideoData.orientation);
        int size;
        if (this.mVideoData.orientation == 0) {
            if (this.mWindowWidth < this.mWindowHeight) {
                size = this.mWindowWidth;
                this.mWindowWidth = this.mWindowHeight;
                this.mWindowHeight = size;
            }
        } else if (this.mWindowHeight < this.mWindowWidth) {
            size = this.mWindowHeight;
            this.mWindowHeight = this.mWindowWidth;
            this.mWindowWidth = size;
        }
        this.mVideoWidth = this.mVideoData.width;
        this.mVideoHeight = this.mVideoData.height;
        if (this.mVideoWidth <= 0) {
            this.mVideoWidth = this.mWindowWidth;
            this.mVideoHeight = this.mWindowHeight;
        } else {
            DisplayMetrics m = getResources().getDisplayMetrics();
            this.mVideoWidth = (int) TypedValue.applyDimension(TYPE_VIDEO, (float) this.mVideoWidth, m);
            this.mVideoHeight = (int) TypedValue.applyDimension(TYPE_VIDEO, (float) this.mVideoHeight, m);
            if (this.mVideoWidth > this.mWindowWidth) {
                this.mVideoWidth = this.mWindowWidth;
            }
            if (this.mVideoHeight > this.mWindowHeight) {
                this.mVideoHeight = this.mWindowHeight;
            }
        }
        this.mVideoLayout = new FrameLayout(this);
        this.mVideoView = new SDKVideoView(this, this.mVideoWidth, this.mVideoHeight, this.mVideoData.display);
        this.mVideoLayout.addView(this.mVideoView, new LayoutParams(TYPE_UNKNOWN, TYPE_UNKNOWN, 17));
        if (this.mVideoData.showHtmlOverlay) {
            this.mOverlayView = new WebFrame(this, false, false, false);
            this.mOverlayView.setEnableZoom(false);
            this.mOverlayView.setOnClickListener(this.mOverlayClickListener);
            this.mOverlayView.setBackgroundColor(TYPE_BROWSER);
            if (this.mVideoData.showHtmlOverlayAfter > 0) {
                this.mOverlayView.setVisibility(8);
                this.mVideoView.setOnTimeEventListener(this.mVideoData.showHtmlOverlayAfter, this.mOverlayShowListener);
            }
            if (this.mVideoData.htmlOverlayType == 0) {
                this.mOverlayView.loadUrl(this.mVideoData.htmlOverlayUrl);
            } else {
                this.mOverlayView.setMarkup(this.mVideoData.htmlOverlayMarkup);
            }
            LayoutParams overlayParams = new LayoutParams(TYPE_UNKNOWN, TYPE_UNKNOWN);
            if (this.mVideoData.showBottomNavigationBar && this.mVideoData.showTopNavigationBar) {
                overlayParams.bottomMargin = (int) (((double) this.mWindowWidth) * 0.11875d);
                overlayParams.topMargin = (int) (((double) this.mWindowWidth) * 0.11875d);
                overlayParams.gravity = 17;
            } else if (this.mVideoData.showBottomNavigationBar && !this.mVideoData.showTopNavigationBar) {
                overlayParams.bottomMargin = (int) (((double) this.mWindowWidth) * 0.11875d);
                overlayParams.gravity = 48;
            } else if (this.mVideoData.showTopNavigationBar && !this.mVideoData.showBottomNavigationBar) {
                overlayParams.topMargin = (int) (((double) this.mWindowWidth) * 0.11875d);
                overlayParams.gravity = 80;
            }
            this.mVideoLayout.addView(this.mOverlayView, overlayParams);
        }
        this.mMediaController = new MediaController(this, this.mVideoData);
        this.mVideoView.setMediaController(this.mMediaController);
        if (this.mVideoData.showNavigationBars) {
            this.mMediaController.toggle();
        }
        if (!this.mVideoData.pauseEvents.isEmpty()) {
            this.mMediaController.setOnPauseListener(this.mOnVideoPauseListener);
        }
        if (!this.mVideoData.unpauseEvents.isEmpty()) {
            this.mMediaController.setOnUnpauseListener(this.mOnVideoUnpauseListener);
        }
        if (!this.mVideoData.replayEvents.isEmpty()) {
            this.mMediaController.setOnReplayListener(this.mOnVideoReplayListener);
        }
        this.mVideoLayout.addView(this.mMediaController, new LayoutParams(TYPE_UNKNOWN, TYPE_UNKNOWN, 7));
        if (this.mVideoData.showSkipButton) {
            this.mSkipButton = new ImageView(this);
            this.mSkipButton.setAdjustViewBounds(false);
            int buttonSize = (int) TypedValue.applyDimension(TYPE_VIDEO, (float) this.skipButtonSizeLand, getResources().getDisplayMetrics());
            buttonSize = (int) (((double) Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels)) * 0.09d);
            params = new LayoutParams(buttonSize, buttonSize, 53);
            int margin;
            if (this.mVideoData.orientation == TYPE_VIDEO) {
                margin = (int) TypedValue.applyDimension(TYPE_VIDEO, 8.0f, getResources().getDisplayMetrics());
                params.topMargin = margin;
                params.rightMargin = margin;
            } else {
                margin = (int) TypedValue.applyDimension(TYPE_VIDEO, AugmentedReality.TEN_PERCENT, getResources().getDisplayMetrics());
                params.topMargin = margin;
                params.rightMargin = margin;
            }
            if (this.mVideoData.skipButtonImage == null || this.mVideoData.skipButtonImage.length() <= 0) {
                this.mSkipButton.setImageDrawable(this.mResourceManager.getResource(this, -18));
            } else {
                this.mResourceManager.fetchResource(this, this.mVideoData.skipButtonImage, -18);
            }
            this.mSkipButton.setOnClickListener(this.mOnVideoSkipListener);
            if (this.mVideoData.showSkipButtonAfter > 0) {
                this.mCanClose = false;
                this.mSkipButton.setVisibility(8);
            } else {
                this.mCanClose = true;
                this.mSkipButton.setVisibility(TYPE_BROWSER);
            }
            this.mVideoLayout.addView(this.mSkipButton, params);
        } else {
            this.mCanClose = false;
        }
        if (this.mVideoData.showSkipButtonAfter > 0) {
            this.mVideoView.setOnTimeEventListener(this.mVideoData.showSkipButtonAfter, this.mOnVideoCanCloseEventListener);
        }
        params = new LayoutParams(-2, -2, 17);
        this.mLoadingView = new FrameLayout(this);
        TextView loadingText = new TextView(this);
        loadingText.setText(Const.LOADING);
        this.mLoadingView.addView(loadingText, params);
        this.mVideoLayout.addView(this.mLoadingView, new LayoutParams(TYPE_UNKNOWN, TYPE_UNKNOWN, 17));
        this.mVideoView.setOnPreparedListener(this.mOnVideoPreparedListener);
        this.mVideoView.setOnCompletionListener(this.mOnVideoCompletionListener);
        this.mVideoView.setOnErrorListener(this.mOnVideoErrorListener);
        this.mVideoView.setOnInfoListener(this.mOnVideoInfoListener);
        if (!this.mVideoData.startEvents.isEmpty()) {
            this.mVideoView.setOnStartListener(this.mOnVideoStartListener);
        }
        if (!this.mVideoData.timeTrackingEvents.isEmpty()) {
            for (Integer intValue : this.mVideoData.timeTrackingEvents.keySet()) {
                this.mVideoView.setOnTimeEventListener(intValue.intValue(), this.mOnVideoTimeEventListener);
            }
        }
        this.mVideoLastPosition = TYPE_BROWSER;
        this.mVideoView.setVideoPath(this.mVideoData.videoUrl);
    }

    private void initWebBrowserView(boolean showExit) {
        this.mWebBrowserView = new WebFrame(this, true, true, showExit);
        this.mWebBrowserView.setOnPageLoadedListener(this.mOnWebBrowserLoadedListener);
        this.mRootLayout.addView(this.mWebBrowserView);
    }

    public void navigate(String clickUrl) {
        switch (this.mType) {
            case TYPE_BROWSER /*0*/:
                this.mWebBrowserView.loadUrl(clickUrl);
            case TYPE_INTERSTITIAL /*2*/:
                this.mInterstitialView.loadUrl(clickUrl);
            default:
                Intent intent = new Intent(this, RichMediaActivity.class);
                intent.setData(Uri.parse(clickUrl));
                startActivity(intent);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.mResult = false;
        this.mPageLoaded = false;
        setResult(TYPE_BROWSER);
        Window win = getWindow();
        win.setFlags(AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END, AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END);
        requestWindowFeature(TYPE_VIDEO);
        win.addFlags(AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START);
        Display display = getWindowManager().getDefaultDisplay();
        this.metrics = new DisplayMetrics();
        ((WindowManager) getSystemService("window")).getDefaultDisplay().getMetrics(this.metrics);
        this.mWindowWidth = display.getWidth();
        this.mWindowHeight = display.getHeight();
        win.clearFlags(AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START);
        setVolumeControlStream(3);
        this.mType = TYPE_UNKNOWN;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null || extras.getSerializable(Const.AD_EXTRA) == null) {
            this.uri = intent.getData();
            if (this.uri == null) {
                finish();
                return;
            }
            this.mType = TYPE_BROWSER;
        } else {
            requestWindowFeature(TYPE_VIDEO);
        }
        this.mHandler = new ResourceHandler(this);
        this.mResourceManager = new ResourceManager(this, this.mHandler);
        initRootLayout();
        if (this.mType != 0) {
            this.mAd = (RichMediaAd) extras.getSerializable(Const.AD_EXTRA);
            this.mEnterAnim = Util.getEnterAnimation(this.mAd.getAnimation());
            this.mExitAnim = Util.getExitAnimation(this.mAd.getAnimation());
            this.mCanClose = false;
            this.mType = extras.getInt(Const.AD_TYPE_EXTRA, TYPE_UNKNOWN);
            if (this.mType == TYPE_UNKNOWN) {
                switch (this.mAd.getType()) {
                    case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                        this.mType = TYPE_VIDEO;
                        break;
                    case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                        this.mType = TYPE_INTERSTITIAL;
                        break;
                }
            }
            switch (this.mType) {
                case TYPE_VIDEO /*1*/:
                    initVideoView();
                    break;
                case TYPE_INTERSTITIAL /*2*/:
                    initInterstitialView();
                    break;
                default:
                    break;
            }
        }
        initWebBrowserView(true);
        this.mWebBrowserView.loadUrl(this.uri.toString());
        this.mEnterAnim = Util.getEnterAnimation(TYPE_VIDEO);
        this.mExitAnim = Util.getExitAnimation(TYPE_VIDEO);
        setContentView(this.mRootLayout);
    }

    protected void onDestroy() {
        this.mMediaController = null;
        this.mResourceManager.releaseInstance();
        if (this.mVideoView != null) {
            this.mVideoView.destroy();
        }
        super.onDestroy();
    }

    public void onHideCustomView() {
        if (this.mCustomView != null) {
            this.mCustomView.setVisibility(8);
            this.mCustomView = null;
            if (this.mCustomVideoView != null) {
                try {
                    this.mCustomVideoView.stopPlayback();
                } catch (Exception e) {
                }
                this.mCustomVideoView = null;
            }
        }
        this.mCustomViewCallback.onCustomViewHidden();
        this.mRootLayout.setVisibility(TYPE_BROWSER);
        setContentView(this.mRootLayout);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        goBack();
        return true;
    }

    protected void onPause() {
        super.onPause();
        switch (this.mType) {
            case TYPE_VIDEO /*1*/:
                this.mVideoLastPosition = this.mVideoView.getCurrentPosition();
                this.mVideoView.stopPlayback();
                this.mRootLayout.removeView(this.mVideoLayout);
                if (this.mVideoTimeoutTimer != null) {
                    this.mVideoTimeoutTimer.cancel();
                    this.mVideoTimeoutTimer = null;
                }
            case TYPE_INTERSTITIAL /*2*/:
                if (this.mInterstitialLoadingTimer != null) {
                    this.mInterstitialLoadingTimer.cancel();
                    this.mInterstitialLoadingTimer = null;
                }
                if (this.mInterstitialAutocloseTimer != null) {
                    this.mInterstitialAutocloseTimer.cancel();
                    this.mInterstitialAutocloseTimer = null;
                }
                if (this.mInterstitialCanCloseTimer != null) {
                    this.mInterstitialCanCloseTimer.cancel();
                    this.mInterstitialCanCloseTimer = null;
                }
            default:
        }
    }

    protected void onResume() {
        super.onResume();
        switch (this.mType) {
            case TYPE_VIDEO /*1*/:
                this.mRootLayout.addView(this.mVideoLayout);
                this.mVideoView.seekTo(this.mVideoLastPosition);
                this.mVideoView.start();
                if (this.mVideoTimeoutTimer == null) {
                    VideoTimeoutTask autocloseTask = new VideoTimeoutTask(this);
                    this.mVideoTimeoutTimer = new Timer();
                    this.mVideoTimeoutTimer.schedule(autocloseTask, Const.VIDEO_LOAD_TIMEOUT);
                }
            case TYPE_INTERSTITIAL /*2*/:
                switch (this.mInterstitialData.interstitialType) {
                    case TYPE_BROWSER /*0*/:
                        if (!this.mPageLoaded) {
                            this.mInterstitialView.loadUrl(this.mInterstitialData.interstitialUrl);
                        }
                    case TYPE_VIDEO /*1*/:
                        if (!this.mPageLoaded) {
                            this.mInterstitialView.setMarkup(this.mInterstitialData.interstitialMarkup);
                        }
                    default:
                }
            default:
        }
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (view instanceof FrameLayout) {
            this.mCustomView = (FrameLayout) view;
            this.mCustomViewCallback = callback;
            if (this.mCustomView.getFocusedChild() instanceof VideoView) {
                this.mCustomVideoView = (VideoView) this.mCustomView.getFocusedChild();
                this.mCustomVideoView.setOnCompletionListener(new OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        RichMediaActivity.this.onHideCustomView();
                    }
                });
                this.mCustomVideoView.start();
            }
            this.mRootLayout.setVisibility(8);
            this.mCustomView.setVisibility(TYPE_BROWSER);
            setContentView(this.mCustomView);
        }
    }

    public void playVideo() {
        switch (this.mType) {
            case TYPE_VIDEO /*1*/:
                if (this.mMediaController != null) {
                    this.mMediaController.replay();
                }
            case TYPE_INTERSTITIAL /*2*/:
                if (this.mAd.getType() == 4) {
                    Intent intent = new Intent(this, RichMediaActivity.class);
                    intent.putExtra(Const.AD_EXTRA, this.mAd);
                    intent.putExtra(Const.AD_TYPE_EXTRA, TYPE_VIDEO);
                    try {
                        startActivity(intent);
                        setActivityAnimation(this, this.mEnterAnim, this.mExitAnim);
                        this.mResult = true;
                        setResult(TYPE_UNKNOWN);
                    } catch (Exception e) {
                    }
                }
            default:
        }
    }

    public void replayVideo() {
        if (this.mMediaController != null) {
            this.mMediaController.replay();
        }
    }
}
