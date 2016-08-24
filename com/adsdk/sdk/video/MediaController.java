package com.adsdk.sdk.video;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.location.LocationRequest;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;
import org.joda.time.DateTimeConstants;

public class MediaController extends FrameLayout {
    private static final int DEFAULT_TIMEOUT = 5000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private double buttonWidthPercent;
    private LinearLayout mBottomBar;
    private Context mContext;
    private boolean mFixed;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ResourceHandler mHandler;
    private TextView mLeftTime;
    private OnPauseListener mOnPauseListener;
    private OnReplayListener mOnReplayListener;
    private OnUnpauseListener mOnUnpauseListener;
    private AspectRatioImageViewWidth mPauseButton;
    private OnClickListener mPauseListener;
    private MediaPlayerControl mPlayer;
    private AspectRatioImageViewWidth mReplayButton;
    private OnClickListener mReplayListener;
    private ResourceManager mResourceManager;
    private boolean mShowing;
    private LinearLayout mTopBar;
    private VideoData mVideoData;

    /* renamed from: com.adsdk.sdk.video.MediaController.1 */
    class C00561 implements OnClickListener {
        C00561() {
        }

        public void onClick(View v) {
            MediaController.this.doPauseResume();
        }
    }

    /* renamed from: com.adsdk.sdk.video.MediaController.2 */
    class C00572 implements OnClickListener {
        C00572() {
        }

        public void onClick(View v) {
            MediaController.this.replay();
        }
    }

    public interface OnPauseListener {
        void onVideoPause();
    }

    public interface OnReplayListener {
        void onVideoReplay();
    }

    public interface OnUnpauseListener {
        void onVideoUnpause();
    }

    private static class ResourceHandler extends Handler {
        private final WeakReference<MediaController> mController;

        public ResourceHandler(MediaController controller) {
            this.mController = new WeakReference(controller);
        }

        public void handleMessage(Message msg) {
            MediaController wController = (MediaController) this.mController.get();
            if (wController != null) {
                wController.handleMessage(msg);
            }
        }
    }

    public MediaController(Context context, VideoData videoData) {
        super(context);
        this.buttonWidthPercent = 0.09d;
        this.mHandler = new ResourceHandler(this);
        this.mPauseListener = new C00561();
        this.mReplayListener = new C00572();
        setVisibility(8);
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
        this.mShowing = false;
        this.mFixed = false;
        this.mContext = context;
        this.mVideoData = videoData;
        if (this.mVideoData == null) {
            throw new IllegalArgumentException("Video info cannot be null");
        }
        this.mFormatBuilder = new StringBuilder();
        this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
        this.mResourceManager = new ResourceManager(this.mContext, this.mHandler);
        buildNavigationBarView(metrics);
    }

    public void setMediaPlayer(MediaPlayerControl player) {
        this.mPlayer = player;
        updatePausePlay();
    }

    protected void buildNavigationBarView(DisplayMetrics metrics) {
        int barHeight = metrics.widthPixels;
        setLayoutParams(new LayoutParams(-1, -1));
        this.mTopBar = new LinearLayout(this.mContext);
        this.mTopBar.setOrientation(0);
        this.mTopBar.setWeightSum(AugmentedReality.ONE_PERCENT);
        this.mTopBar.setBackgroundColor(0);
        LayoutParams paramsFrame = new LayoutParams(-1, (int) (((double) barHeight) * 0.119d));
        paramsFrame.gravity = 55;
        this.mTopBar.setGravity(16);
        int padding = (int) TypedValue.applyDimension(FADE_OUT, 5.0f, getResources().getDisplayMetrics());
        addView(this.mTopBar, paramsFrame);
        this.mBottomBar = new LinearLayout(this.mContext);
        this.mBottomBar.setOrientation(0);
        this.mBottomBar.setGravity(16);
        paramsFrame = new LayoutParams(-1, (int) (((double) barHeight) * 0.119d));
        paramsFrame.gravity = 80;
        this.mBottomBar.setWeightSum(AugmentedReality.ONE_PERCENT);
        this.mBottomBar.setPadding(padding, 0, padding, 0);
        this.mBottomBar.setBackgroundColor(0);
        addView(this.mBottomBar, paramsFrame);
        LinearLayout buttonPanel = new LinearLayout(this.mContext);
        new LinearLayout.LayoutParams(-2, -1).gravity = 3;
        buttonPanel.setOrientation(0);
        buttonPanel.setGravity(16);
        buttonPanel.setBackgroundColor(-16711936);
        this.mReplayButton = new AspectRatioImageViewWidth(this.mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (((double) barHeight) * this.buttonWidthPercent), (int) (((double) barHeight) * this.buttonWidthPercent));
        params.gravity = 16;
        this.mReplayButton.setAdjustViewBounds(true);
        this.mReplayButton.setPadding(padding, padding, padding, padding);
        this.mBottomBar.addView(this.mReplayButton, params);
        this.mPauseButton = new AspectRatioImageViewWidth(this.mContext);
        params = new LinearLayout.LayoutParams((int) (((double) barHeight) * this.buttonWidthPercent), (int) (((double) barHeight) * this.buttonWidthPercent));
        params.gravity = 16;
        this.mPauseButton.setPadding(padding, padding, padding, padding);
        this.mPauseButton.setAdjustViewBounds(true);
        this.mBottomBar.addView(this.mPauseButton, params);
        this.mLeftTime = new AutoResizeTextView(this.mContext);
        params = new LinearLayout.LayoutParams(-2, -2);
        params.gravity = 16;
        this.mLeftTime.setTypeface(Typeface.defaultFromStyle(FADE_OUT));
        this.mLeftTime.setPadding(padding, padding, padding, padding);
        this.mLeftTime.setGravity(16);
        this.mLeftTime.setTextSize(23.0f);
        this.mBottomBar.addView(this.mLeftTime, params);
        View view = new View(this.mContext);
        params = new LinearLayout.LayoutParams(0, 0);
        params.weight = AugmentedReality.ONE_PERCENT;
        params.gravity = 16;
        this.mBottomBar.addView(view, params);
        initNavigationBarControllerView(padding, metrics);
    }

    private void initNavigationBarControllerView(int padding, DisplayMetrics metrics) {
        int barHeight = metrics.widthPixels;
        if (this.mVideoData.showBottomNavigationBar) {
            this.mBottomBar.setVisibility(0);
            if (this.mVideoData.bottomNavigationBarBackground == null || this.mVideoData.bottomNavigationBarBackground.length() <= 0) {
                this.mBottomBar.setBackgroundDrawable(this.mResourceManager.getResource(this.mContext, -2));
            } else {
                this.mResourceManager.fetchResource(this.mContext, this.mVideoData.bottomNavigationBarBackground, -2);
            }
            if (this.mPauseButton != null) {
                if (this.mVideoData.pauseButtonImage == null || this.mVideoData.pauseButtonImage.length() <= 0) {
                    this.mPauseButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -12));
                } else {
                    this.mPauseButton.setBackgroundDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mVideoData.pauseButtonImage, -12);
                }
                if (this.mVideoData.playButtonImage != null && this.mVideoData.playButtonImage.length() > 0) {
                    this.mResourceManager.fetchResource(this.mContext, this.mVideoData.playButtonImage, -11);
                }
                this.mPauseButton.setOnClickListener(this.mPauseListener);
                if (this.mVideoData.showPauseButton) {
                    this.mPauseButton.setVisibility(0);
                } else {
                    this.mPauseButton.setVisibility(8);
                }
            }
            if (this.mReplayButton != null) {
                if (this.mVideoData.replayButtonImage == null || this.mVideoData.replayButtonImage.length() <= 0) {
                    this.mReplayButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -13));
                } else {
                    this.mReplayButton.setImageDrawable(null);
                    this.mResourceManager.fetchResource(this.mContext, this.mVideoData.replayButtonImage, -13);
                }
                this.mReplayButton.setOnClickListener(this.mReplayListener);
                if (this.mVideoData.showReplayButton) {
                    this.mReplayButton.setVisibility(0);
                } else {
                    this.mReplayButton.setVisibility(8);
                }
            }
            if (this.mLeftTime != null) {
                if (this.mVideoData.showTimer) {
                    this.mLeftTime.setVisibility(0);
                } else {
                    this.mLeftTime.setVisibility(8);
                }
            }
            if (!this.mVideoData.icons.isEmpty()) {
                for (int i = 0; i < this.mVideoData.icons.size(); i += FADE_OUT) {
                    this.mBottomBar.addView(new NavIcon(this.mContext, (NavIconData) this.mVideoData.icons.get(i)), new LinearLayout.LayoutParams((int) (((double) barHeight) * this.buttonWidthPercent), (int) (((double) barHeight) * this.buttonWidthPercent)));
                }
            }
        } else {
            this.mBottomBar.setVisibility(8);
        }
        if (this.mVideoData.showTopNavigationBar) {
            this.mTopBar.setVisibility(0);
            if (this.mVideoData.topNavigationBarBackground == null || this.mVideoData.topNavigationBarBackground.length() <= 0) {
                this.mTopBar.setBackgroundDrawable(this.mResourceManager.getResource(this.mContext, -1));
            } else {
                this.mResourceManager.fetchResource(this.mContext, this.mVideoData.topNavigationBarBackground, -1);
            }
        } else {
            this.mTopBar.setVisibility(8);
        }
        if (!this.mVideoData.showNavigationBars) {
            setVisibility(8);
        }
    }

    public void show() {
        show(DEFAULT_TIMEOUT);
    }

    public void show(int timeout) {
        if (timeout == 0) {
            this.mFixed = true;
        }
        if (!this.mShowing) {
            setVisibility(0);
            this.mShowing = true;
        }
        refreshProgress();
        this.mHandler.removeMessages(FADE_OUT);
        if (timeout != 0 && !this.mFixed) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(FADE_OUT), (long) timeout);
        }
    }

    public boolean isShowing() {
        return this.mShowing;
    }

    public void hide() {
        this.mFixed = false;
        if (canToggle() && this.mShowing) {
            this.mHandler.removeMessages(SHOW_PROGRESS);
            setVisibility(8);
            this.mShowing = false;
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

    public void replay() {
        if (this.mPlayer != null) {
            this.mPlayer.seekTo(0);
            this.mPlayer.start();
        }
        refreshProgress();
        if (this.mOnReplayListener != null) {
            this.mOnReplayListener.onVideoReplay();
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case FADE_OUT /*1*/:
                hide();
            case SHOW_PROGRESS /*2*/:
                refreshProgress();
            case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                Drawable d;
                switch (msg.arg1) {
                    case ResourceManager.DEFAULT_REPLAY_IMAGE_RESOURCE_ID /*-13*/:
                        if (this.mReplayButton != null) {
                            updateReplay();
                            break;
                        }
                        break;
                    case ResourceManager.DEFAULT_PAUSE_IMAGE_RESOURCE_ID /*-12*/:
                        if (this.mPauseButton != null) {
                            updatePausePlay();
                            break;
                        }
                        break;
                    case ResourceManager.DEFAULT_PLAY_IMAGE_RESOURCE_ID /*-11*/:
                        if (this.mPauseButton != null) {
                            updatePausePlay();
                            break;
                        }
                        break;
                    case ResourceManager.DEFAULT_BOTTOMBAR_BG_RESOURCE_ID /*-2*/:
                        if (this.mBottomBar != null) {
                            d = this.mResourceManager.getResource(this.mContext, -2);
                            if (d != null) {
                                this.mBottomBar.setBackgroundDrawable(d);
                                break;
                            }
                        }
                        break;
                    case Room.ROOM_VARIANT_ANY /*-1*/:
                        if (this.mTopBar != null) {
                            d = this.mResourceManager.getResource(this.mContext, -1);
                            if (d != null) {
                                this.mTopBar.setBackgroundDrawable(d);
                                break;
                            }
                        }
                        break;
                }
                requestLayout();
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
        if (this.mPlayer == null) {
            return 0;
        }
        int position = this.mPlayer.getCurrentPosition();
        int timeLeft = this.mPlayer.getDuration() - position;
        if (this.mLeftTime == null) {
            return position;
        }
        this.mLeftTime.setText(stringForTime(timeLeft));
        return position;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!(keyCode == 4 || keyCode == 24 || keyCode == 25 || keyCode == 82 || keyCode == 5 || keyCode == 6)) {
            if (keyCode == 79 || keyCode == 85) {
                doPauseResume();
                return true;
            } else if (keyCode == 86 && this.mPlayer != null && this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                if (this.mOnPauseListener != null) {
                    this.mOnPauseListener.onVideoPause();
                }
            } else {
                toggle();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateReplay() {
        if (this.mReplayButton != null) {
            if (this.mResourceManager.containsResource(-13)) {
                this.mReplayButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -13));
                return;
            }
            this.mReplayButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -13));
        }
    }

    private void updatePausePlay() {
        if (this.mPauseButton != null) {
            if (this.mPlayer == null || !this.mPlayer.isPlaying()) {
                if (this.mResourceManager.containsResource(-11)) {
                    this.mPauseButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -11));
                    return;
                }
                this.mPauseButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -11));
            } else if (this.mResourceManager.containsResource(-12)) {
                this.mPauseButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -12));
            } else {
                this.mPauseButton.setImageDrawable(this.mResourceManager.getResource(this.mContext, -12));
            }
        }
    }

    private void doPauseResume() {
        if (this.mPlayer != null) {
            if (this.mPlayer.isPlaying()) {
                this.mPlayer.pause();
                if (this.mOnPauseListener != null) {
                    this.mOnPauseListener.onVideoPause();
                }
            } else {
                this.mPlayer.start();
                if (this.mOnUnpauseListener != null) {
                    this.mOnUnpauseListener.onVideoUnpause();
                }
            }
            updatePausePlay();
        }
    }

    public boolean canToggle() {
        return this.mVideoData.allowTapNavigationBars;
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

    public void onStart() {
        refreshProgress();
    }

    private void refreshProgress() {
        if (this.mShowing) {
            updatePausePlay();
            int pos = setProgress();
            if (this.mPlayer != null && this.mPlayer.isPlaying()) {
                this.mHandler.removeMessages(SHOW_PROGRESS);
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(SHOW_PROGRESS), (long) (1000 - (pos % DateTimeConstants.MILLIS_PER_SECOND)));
            }
        }
    }

    public void onPause() {
        show(0);
    }

    public void setOnPauseListener(OnPauseListener l) {
        this.mOnPauseListener = l;
    }

    public void setOnUnpauseListener(OnUnpauseListener l) {
        this.mOnUnpauseListener = l;
    }

    public void setOnReplayListener(OnReplayListener l) {
        this.mOnReplayListener = l;
    }
}
