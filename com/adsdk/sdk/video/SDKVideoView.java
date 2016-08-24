package com.adsdk.sdk.video;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.ConditionVariable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.MediaController.MediaPlayerControl;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import org.joda.time.DateTimeConstants;

public class SDKVideoView extends SurfaceView implements MediaPlayerControl {
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PREPARING = 1;
    private OnBufferingUpdateListener mBufferingUpdateListener;
    private OnCompletionListener mCompletionListener;
    private Context mContext;
    private int mCurrentBufferPercentage;
    private int mCurrentState;
    private int mDisplayMode;
    private int mDuration;
    private OnErrorListener mErrorListener;
    public Handler mHandler;
    private int mHeight;
    private OnInfoListener mInfoListener;
    private MediaController mMediaController;
    private MediaPlayer mMediaPlayer;
    private OnCompletionListener mOnCompletionListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnPreparedListener mOnPreparedListener;
    private OnStartListener mOnStartListener;
    private boolean mPlayWhenSurfaceReady;
    OnPreparedListener mPreparedListener;
    Callback mSHCallback;
    private int mSeekWhenPrepared;
    OnVideoSizeChangedListener mSizeChangedListener;
    private int mSurfaceHeight;
    private boolean mSurfaceReady;
    private int mSurfaceWidth;
    private int mTargetState;
    private HashMap<Integer, Vector<OnTimeEventListener>> mTimeEventListeners;
    private Runnable mTimeEventRunnable;
    private Thread mTimeEventThread;
    private ConditionVariable mTimeEventThreadDone;
    private Uri mUri;
    private int mVideoHeight;
    private int mVideoWidth;
    private int mWidth;

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.1 */
    class C00691 implements OnVideoSizeChangedListener {
        C00691() {
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.2 */
    class C00702 implements OnPreparedListener {
        C00702() {
        }

        public void onPrepared(MediaPlayer mp) {
            SDKVideoView.this.mCurrentState = SDKVideoView.STATE_PREPARED;
            if (SDKVideoView.this.mOnPreparedListener != null) {
                SDKVideoView.this.mOnPreparedListener.onPrepared(SDKVideoView.this.mMediaPlayer);
            }
            if (SDKVideoView.this.mMediaController != null) {
                SDKVideoView.this.mMediaController.setEnabled(true);
            }
            int seekToPosition = SDKVideoView.this.mSeekWhenPrepared;
            if (seekToPosition != 0) {
                SDKVideoView.this.seekTo(seekToPosition);
            }
            if (SDKVideoView.this.mSurfaceReady) {
                SDKVideoView.this.setVideoDisplaySize();
                if (SDKVideoView.this.mTargetState == SDKVideoView.STATE_PLAYING) {
                    SDKVideoView.this.start();
                } else if (!SDKVideoView.this.isPlaying()) {
                    if ((seekToPosition != 0 || SDKVideoView.this.getCurrentPosition() > 0) && SDKVideoView.this.mMediaController != null) {
                        SDKVideoView.this.mMediaController.show(SDKVideoView.STATE_IDLE);
                    }
                }
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.3 */
    class C00713 implements OnCompletionListener {
        C00713() {
        }

        public void onCompletion(MediaPlayer mp) {
            SDKVideoView.this.mCurrentState = SDKVideoView.STATE_PLAYBACK_COMPLETED;
            SDKVideoView.this.mTargetState = SDKVideoView.STATE_PLAYBACK_COMPLETED;
            if (SDKVideoView.this.mMediaController != null) {
                SDKVideoView.this.mMediaController.show(SDKVideoView.STATE_IDLE);
            }
            if (SDKVideoView.this.mOnCompletionListener != null) {
                SDKVideoView.this.mOnCompletionListener.onCompletion(SDKVideoView.this.mMediaPlayer);
            }
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.4 */
    class C00724 implements OnErrorListener {
        C00724() {
        }

        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            SDKVideoView.this.mCurrentState = SDKVideoView.STATE_ERROR;
            SDKVideoView.this.mTargetState = SDKVideoView.STATE_ERROR;
            if (SDKVideoView.this.mMediaController != null) {
                SDKVideoView.this.mMediaController.hide();
            }
            return (SDKVideoView.this.mOnErrorListener == null || SDKVideoView.this.mOnErrorListener.onError(SDKVideoView.this.mMediaPlayer, framework_err, impl_err)) ? true : true;
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.5 */
    class C00735 implements OnInfoListener {
        C00735() {
        }

        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            return (SDKVideoView.this.mOnInfoListener == null || SDKVideoView.this.mOnInfoListener.onInfo(SDKVideoView.this.mMediaPlayer, what, extra)) ? true : true;
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.6 */
    class C00746 implements OnBufferingUpdateListener {
        C00746() {
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            SDKVideoView.this.mCurrentBufferPercentage = percent;
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.7 */
    class C00757 implements Callback {
        C00757() {
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            SDKVideoView.this.mSurfaceWidth = w;
            SDKVideoView.this.mSurfaceHeight = h;
            SDKVideoView.this.setVideoDisplaySize();
        }

        public void surfaceCreated(SurfaceHolder holder) {
            SDKVideoView.this.mSurfaceReady = true;
            if (SDKVideoView.this.mPlayWhenSurfaceReady) {
                SDKVideoView.this.openVideo();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            SDKVideoView.this.mSurfaceReady = false;
            if (SDKVideoView.this.mMediaController != null) {
                SDKVideoView.this.mMediaController.hide();
            }
            SDKVideoView.this.release(true);
        }
    }

    /* renamed from: com.adsdk.sdk.video.SDKVideoView.8 */
    class C00778 implements Runnable {

        /* renamed from: com.adsdk.sdk.video.SDKVideoView.8.1 */
        class C00761 implements Runnable {
            private final /* synthetic */ OnTimeEventListener val$l;
            private final /* synthetic */ int val$time;

            C00761(OnTimeEventListener onTimeEventListener, int i) {
                this.val$l = onTimeEventListener;
                this.val$time = i;
            }

            public void run() {
                this.val$l.onTimeEvent(this.val$time);
            }
        }

        C00778() {
        }

        public void run() {
            do {
                if (SDKVideoView.this.mMediaPlayer != null && SDKVideoView.this.mCurrentState == SDKVideoView.STATE_PLAYING) {
                    try {
                        int time = SDKVideoView.this.mMediaPlayer.getCurrentPosition() / DateTimeConstants.MILLIS_PER_SECOND;
                        Vector<OnTimeEventListener> listeners = (Vector) SDKVideoView.this.mTimeEventListeners.get(Integer.valueOf(time));
                        if (listeners != null) {
                            for (int i = SDKVideoView.STATE_IDLE; i < listeners.size(); i += SDKVideoView.STATE_PREPARING) {
                                SDKVideoView.this.mHandler.post(new C00761((OnTimeEventListener) listeners.elementAt(i), time));
                            }
                            listeners.clear();
                        }
                    } catch (Exception e) {
                    }
                }
            } while (!SDKVideoView.this.mTimeEventThreadDone.block(1000));
        }
    }

    public interface OnStartListener {
        void onVideoStart();
    }

    public interface OnTimeEventListener {
        void onTimeEvent(int i);
    }

    public SDKVideoView(Context context, int width, int height, int displayMode) {
        super(context);
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
        this.mMediaPlayer = null;
        this.mSurfaceReady = false;
        this.mTimeEventThreadDone = new ConditionVariable(false);
        this.mTimeEventListeners = new HashMap();
        this.mSizeChangedListener = new C00691();
        this.mPreparedListener = new C00702();
        this.mCompletionListener = new C00713();
        this.mErrorListener = new C00724();
        this.mInfoListener = new C00735();
        this.mBufferingUpdateListener = new C00746();
        this.mSHCallback = new C00757();
        this.mContext = context;
        this.mWidth = width;
        this.mHeight = height;
        this.mDisplayMode = displayMode;
        initVideoView();
    }

    public void destroy() {
        this.mTimeEventThreadDone.open();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            if (this.mVideoWidth * height > this.mVideoHeight * width) {
                height = (this.mVideoHeight * width) / this.mVideoWidth;
            } else if (this.mVideoWidth * height < this.mVideoHeight * width) {
                width = (this.mVideoWidth * height) / this.mVideoHeight;
            }
        }
        setMeasuredDimension(width, height);
    }

    private void initVideoView() {
        this.mHandler = new Handler();
        this.mVideoWidth = STATE_IDLE;
        this.mVideoHeight = STATE_IDLE;
        this.mSurfaceWidth = STATE_IDLE;
        this.mSurfaceHeight = STATE_IDLE;
        this.mSurfaceReady = false;
        setVisibility(STATE_IDLE);
        getHolder().addCallback(this.mSHCallback);
        getHolder().setType(STATE_PLAYING);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mCurrentState = STATE_IDLE;
        this.mTargetState = STATE_IDLE;
    }

    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        this.mUri = uri;
        this.mSeekWhenPrepared = STATE_IDLE;
        openVideo();
    }

    public void stopPlayback() {
        if (this.mMediaPlayer != null) {
            if (this.mMediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            this.mCurrentState = STATE_IDLE;
            this.mTargetState = STATE_IDLE;
        }
    }

    private void openVideo() {
        if (this.mUri != null) {
            this.mPlayWhenSurfaceReady = false;
            if (this.mSurfaceReady) {
                release(false);
                try {
                    this.mMediaPlayer = new MediaPlayer();
                    this.mMediaPlayer.setDisplay(getHolder());
                    this.mMediaPlayer.setOnPreparedListener(this.mPreparedListener);
                    this.mMediaPlayer.setOnVideoSizeChangedListener(this.mSizeChangedListener);
                    this.mDuration = STATE_ERROR;
                    this.mMediaPlayer.setOnCompletionListener(this.mCompletionListener);
                    this.mMediaPlayer.setOnErrorListener(this.mErrorListener);
                    this.mMediaPlayer.setOnBufferingUpdateListener(this.mBufferingUpdateListener);
                    this.mMediaPlayer.setOnInfoListener(this.mInfoListener);
                    this.mCurrentBufferPercentage = STATE_IDLE;
                    this.mMediaPlayer.setDataSource(this.mContext, this.mUri);
                    this.mMediaPlayer.setAudioStreamType(STATE_PLAYING);
                    this.mMediaPlayer.setScreenOnWhilePlaying(true);
                    this.mMediaPlayer.prepareAsync();
                    this.mTimeEventRunnable = new C00778();
                    this.mTimeEventThread = new Thread(this.mTimeEventRunnable);
                    this.mTimeEventThread.start();
                    this.mCurrentState = STATE_PREPARING;
                    attachMediaController();
                    return;
                } catch (IOException e) {
                    this.mCurrentState = STATE_ERROR;
                    this.mTargetState = STATE_ERROR;
                    this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
                    return;
                } catch (IllegalArgumentException e2) {
                    this.mCurrentState = STATE_ERROR;
                    this.mTargetState = STATE_ERROR;
                    this.mErrorListener.onError(this.mMediaPlayer, STATE_PREPARING, STATE_IDLE);
                    return;
                }
            }
            this.mPlayWhenSurfaceReady = true;
        }
    }

    public void setMediaController(MediaController controller) {
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        this.mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (this.mMediaPlayer != null && this.mMediaController != null) {
            this.mMediaController.setMediaPlayer(this);
            this.mMediaController.setEnabled(isInPlaybackState());
        }
    }

    private void setVideoDisplaySize() {
        this.mVideoWidth = STATE_IDLE;
        this.mVideoHeight = STATE_IDLE;
        if (this.mMediaPlayer != null) {
            this.mVideoWidth = this.mMediaPlayer.getVideoWidth();
            this.mVideoHeight = this.mMediaPlayer.getVideoHeight();
        }
        if (this.mSurfaceReady && this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            if (this.mDisplayMode == STATE_PREPARING) {
                if (this.mVideoWidth * this.mHeight > this.mWidth * this.mVideoHeight) {
                    this.mHeight = (this.mWidth * this.mVideoHeight) / this.mVideoWidth;
                } else if (this.mVideoWidth * this.mHeight < this.mWidth * this.mVideoHeight) {
                    this.mWidth = (this.mHeight * this.mVideoWidth) / this.mVideoHeight;
                }
            }
            getHolder().setFixedSize(this.mWidth, this.mHeight);
        }
        getHolder().setFixedSize(this.mVideoWidth, this.mVideoHeight);
    }

    private void release(boolean cleartargetstate) {
        if (this.mMediaPlayer != null) {
            this.mCurrentState = STATE_IDLE;
            if (this.mTimeEventThread != null) {
                this.mTimeEventThreadDone.open();
                this.mTimeEventThread = null;
            }
            this.mMediaPlayer.reset();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
            if (cleartargetstate) {
                this.mTargetState = STATE_IDLE;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null && ev.getAction() == 0) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onTrackballEvent(MotionEvent ev) {
        if (isInPlaybackState() && this.mMediaController != null) {
            toggleMediaControlsVisiblity();
        }
        return false;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported;
        if (keyCode == STATE_PAUSED || keyCode == 24 || keyCode == 25 || keyCode == 82 || keyCode == STATE_PLAYBACK_COMPLETED || keyCode == 6) {
            isKeyCodeSupported = false;
        } else {
            isKeyCodeSupported = true;
        }
        if (isInPlaybackState() && isKeyCodeSupported && this.mMediaController != null) {
            if (keyCode == 79 || keyCode == 85) {
                if (this.mMediaPlayer.isPlaying()) {
                    pause();
                    return true;
                }
                start();
                return true;
            } else if (keyCode == 86 && this.mMediaPlayer.isPlaying()) {
                pause();
            } else {
                toggleMediaControlsVisiblity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toggleMediaControlsVisiblity() {
        if (this.mMediaController != null) {
            this.mMediaController.toggle();
        }
    }

    public void start() {
        this.mTargetState = STATE_PLAYING;
        if (isInPlaybackState()) {
            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra("command", "pause");
            this.mContext.sendBroadcast(intent);
            this.mMediaPlayer.start();
            if (this.mMediaController != null) {
                this.mMediaController.onStart();
            }
            if (this.mCurrentState == STATE_PREPARED && this.mOnStartListener != null) {
                this.mOnStartListener.onVideoStart();
            }
            this.mCurrentState = STATE_PLAYING;
        } else if (this.mMediaPlayer == null) {
            openVideo();
        }
    }

    public void pause() {
        if (isInPlaybackState() && this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            this.mCurrentState = STATE_PAUSED;
            if (this.mMediaController != null) {
                this.mMediaController.onPause();
            }
        }
        this.mTargetState = STATE_PAUSED;
    }

    public int getDuration() {
        if (!isInPlaybackState()) {
            this.mDuration = STATE_ERROR;
            return this.mDuration;
        } else if (this.mDuration > 0) {
            return this.mDuration;
        } else {
            this.mDuration = this.mMediaPlayer.getDuration();
            return this.mDuration;
        }
    }

    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return this.mMediaPlayer.getCurrentPosition();
        }
        return STATE_IDLE;
    }

    public void seekTo(int msec) {
        if (isInPlaybackState()) {
            this.mMediaPlayer.seekTo(msec);
            this.mSeekWhenPrepared = STATE_IDLE;
            return;
        }
        this.mSeekWhenPrepared = msec;
    }

    public boolean isPlaying() {
        return isInPlaybackState() && this.mMediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        if (this.mMediaPlayer != null) {
            return this.mCurrentBufferPercentage;
        }
        return STATE_IDLE;
    }

    private boolean isInPlaybackState() {
        return (this.mMediaPlayer == null || this.mCurrentState == STATE_ERROR || this.mCurrentState == 0 || this.mCurrentState == STATE_PREPARING) ? false : true;
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        this.mOnPreparedListener = l;
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        this.mOnCompletionListener = l;
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(OnInfoListener l) {
        this.mOnInfoListener = l;
    }

    public void setOnStartListener(OnStartListener l) {
        this.mOnStartListener = l;
    }

    public void setOnTimeEventListener(int time, OnTimeEventListener onTimeEventListener) {
        Vector<OnTimeEventListener> listeners = (Vector) this.mTimeEventListeners.get(Integer.valueOf(time));
        if (listeners == null) {
            listeners = new Vector();
            this.mTimeEventListeners.put(Integer.valueOf(time), listeners);
        }
        listeners.add(onTimeEventListener);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return false;
    }

    public boolean canSeekForward() {
        return true;
    }
}
