package com.adsdk.sdk.mraid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.adsdk.sdk.mraid.MraidView.OnCloseListener;
import com.adsdk.sdk.mraid.MraidView.ViewState;
import com.adsdk.sdk.video.ResourceManager;
import com.google.analytics.containertag.proto.MutableServing.ServingValue;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;

class MraidDisplayController extends MraidAbstractController {
    private static final int CLOSE_BUTTON_SIZE_DP = 50;
    private static final String LOGTAG = "MraidDisplayController";
    private static final long VIEWABILITY_TIMER_MILLIS = 3000;
    private boolean mAdWantsCustomCloseButton;
    private Runnable mCheckViewabilityTask;
    private ImageView mCloseButton;
    protected float mDensity;
    private final ExpansionStyle mExpansionStyle;
    private Handler mHandler;
    private boolean mIsViewable;
    private final NativeCloseButtonStyle mNativeCloseButtonStyle;
    private BroadcastReceiver mOrientationBroadcastReceiver;
    private final int mOriginalRequestedOrientation;
    FrameLayout mPlaceholderView;
    private FrameLayout mRootView;
    protected int mScreenHeight;
    protected int mScreenWidth;
    private MraidView mTwoPartExpansionView;
    private int mViewIndexInParent;
    private ViewState mViewState;

    /* renamed from: com.adsdk.sdk.mraid.MraidDisplayController.1 */
    class C00471 implements Runnable {
        C00471() {
        }

        public void run() {
            boolean currentViewable = MraidDisplayController.this.checkViewable();
            if (MraidDisplayController.this.mIsViewable != currentViewable) {
                MraidDisplayController.this.mIsViewable = currentViewable;
                MraidDisplayController.this.getView().fireChangeEventForProperty(MraidViewableProperty.createWithViewable(MraidDisplayController.this.mIsViewable));
            }
            MraidDisplayController.this.mHandler.postDelayed(this, MraidDisplayController.VIEWABILITY_TIMER_MILLIS);
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidDisplayController.2 */
    class C00482 extends BroadcastReceiver {
        private int mLastRotation;

        C00482() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                int orientation = MraidDisplayController.this.getDisplayRotation();
                if (orientation != this.mLastRotation) {
                    this.mLastRotation = orientation;
                    MraidDisplayController.this.onOrientationChanged(this.mLastRotation);
                }
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidDisplayController.4 */
    class C00494 implements OnTouchListener {
        C00494() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidDisplayController.5 */
    class C00505 implements OnClickListener {
        C00505() {
        }

        public void onClick(View v) {
            MraidDisplayController.this.close();
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MraidDisplayController.3 */
    class C04463 implements OnCloseListener {
        C04463() {
        }

        public void onClose(MraidView view, ViewState newViewState) {
            MraidDisplayController.this.close();
        }
    }

    MraidDisplayController(MraidView view, ExpansionStyle expStyle, NativeCloseButtonStyle buttonStyle) {
        int i = -1;
        super(view);
        this.mViewState = ViewState.HIDDEN;
        this.mCheckViewabilityTask = new C00471();
        this.mHandler = new Handler();
        this.mOrientationBroadcastReceiver = new C00482();
        this.mScreenWidth = -1;
        this.mScreenHeight = -1;
        this.mExpansionStyle = expStyle;
        this.mNativeCloseButtonStyle = buttonStyle;
        Context context = getView().getContext();
        if (context instanceof Activity) {
            i = ((Activity) context).getRequestedOrientation();
        }
        this.mOriginalRequestedOrientation = i;
        initialize();
    }

    private void initialize() {
        this.mViewState = ViewState.LOADING;
        initializeScreenMetrics();
        initializeViewabilityTimer();
        getView().getContext().registerReceiver(this.mOrientationBroadcastReceiver, new IntentFilter("android.intent.action.CONFIGURATION_CHANGED"));
    }

    private void initializeScreenMetrics() {
        Context context = getView().getContext();
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
        this.mDensity = metrics.density;
        int statusBarHeight = 0;
        int titleBarHeight = 0;
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            Rect rect = new Rect();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            statusBarHeight = rect.top;
            titleBarHeight = window.findViewById(16908290).getTop() - statusBarHeight;
        }
        int heightPixels = (metrics.heightPixels - statusBarHeight) - titleBarHeight;
        this.mScreenWidth = (int) (((double) metrics.widthPixels) * (160.0d / ((double) metrics.densityDpi)));
        this.mScreenHeight = (int) (((double) heightPixels) * (160.0d / ((double) metrics.densityDpi)));
    }

    private void initializeViewabilityTimer() {
        this.mHandler.removeCallbacks(this.mCheckViewabilityTask);
        this.mHandler.post(this.mCheckViewabilityTask);
    }

    private int getDisplayRotation() {
        return ((WindowManager) getView().getContext().getSystemService("window")).getDefaultDisplay().getOrientation();
    }

    private void onOrientationChanged(int currentRotation) {
        initializeScreenMetrics();
        getView().fireChangeEventForProperty(MraidScreenSizeProperty.createWithSize(this.mScreenWidth, this.mScreenHeight));
    }

    public void destroy() {
        this.mHandler.removeCallbacks(this.mCheckViewabilityTask);
        try {
            getView().getContext().unregisterReceiver(this.mOrientationBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Receiver not registered")) {
                throw e;
            }
        }
    }

    protected void initializeJavaScriptState() {
        ArrayList<MraidProperty> properties = new ArrayList();
        properties.add(MraidScreenSizeProperty.createWithSize(this.mScreenWidth, this.mScreenHeight));
        properties.add(MraidViewableProperty.createWithViewable(this.mIsViewable));
        getView().fireChangeEventForProperties(properties);
        this.mViewState = ViewState.DEFAULT;
        getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
    }

    protected boolean isExpanded() {
        return this.mViewState == ViewState.EXPANDED;
    }

    protected void close() {
        if (this.mViewState == ViewState.EXPANDED) {
            resetViewToDefaultState();
            setOrientationLockEnabled(false);
            this.mViewState = ViewState.DEFAULT;
            getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
        } else if (this.mViewState == ViewState.DEFAULT) {
            getView().setVisibility(4);
            this.mViewState = ViewState.HIDDEN;
            getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
        }
        if (getView().getOnCloseListener() != null) {
            getView().getOnCloseListener().onClose(getView(), this.mViewState);
        }
    }

    private void resetViewToDefaultState() {
        FrameLayout adContainerLayout = (FrameLayout) this.mRootView.findViewById(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        RelativeLayout expansionLayout = (RelativeLayout) this.mRootView.findViewById(ServingValue.EXT_FIELD_NUMBER);
        setNativeCloseButtonEnabled(false);
        adContainerLayout.removeAllViewsInLayout();
        this.mRootView.removeView(expansionLayout);
        getView().requestLayout();
        ViewGroup parent = (ViewGroup) this.mPlaceholderView.getParent();
        parent.addView(getView(), this.mViewIndexInParent);
        parent.removeView(this.mPlaceholderView);
        parent.invalidate();
    }

    protected void expand(String url, int width, int height, boolean shouldUseCustomClose, boolean shouldLockOrientation) {
        if (this.mExpansionStyle != ExpansionStyle.DISABLED) {
            if (url == null || URLUtil.isValidUrl(url)) {
                this.mRootView = (FrameLayout) getView().getRootView().findViewById(16908290);
                useCustomClose(shouldUseCustomClose);
                setOrientationLockEnabled(shouldLockOrientation);
                swapViewWithPlaceholderView();
                View expansionContentView = getView();
                if (url != null) {
                    this.mTwoPartExpansionView = new MraidView(getView().getContext(), ExpansionStyle.DISABLED, NativeCloseButtonStyle.AD_CONTROLLED, PlacementType.INLINE);
                    this.mTwoPartExpansionView.setOnCloseListener(new C04463());
                    this.mTwoPartExpansionView.loadUrl(url);
                    expansionContentView = this.mTwoPartExpansionView;
                }
                this.mRootView.addView(createExpansionViewContainer(expansionContentView, (int) (((float) width) * this.mDensity), (int) (((float) height) * this.mDensity)), new LayoutParams(-1, -1));
                if (this.mNativeCloseButtonStyle == NativeCloseButtonStyle.ALWAYS_VISIBLE || !(this.mAdWantsCustomCloseButton || this.mNativeCloseButtonStyle == NativeCloseButtonStyle.ALWAYS_HIDDEN)) {
                    setNativeCloseButtonEnabled(true);
                }
                this.mViewState = ViewState.EXPANDED;
                getView().fireChangeEventForProperty(MraidStateProperty.createWithViewState(this.mViewState));
                if (getView().getOnExpandListener() != null) {
                    getView().getOnExpandListener().onExpand(getView());
                    return;
                }
                return;
            }
            getView().fireErrorEvent("expand", "URL passed to expand() was invalid.");
        }
    }

    private void swapViewWithPlaceholderView() {
        ViewGroup parent = (ViewGroup) getView().getParent();
        if (parent != null) {
            this.mPlaceholderView = new FrameLayout(getView().getContext());
            int count = parent.getChildCount();
            int index = 0;
            while (index < count && parent.getChildAt(index) != getView()) {
                index++;
            }
            this.mViewIndexInParent = index;
            parent.addView(this.mPlaceholderView, index, new ViewGroup.LayoutParams(getView().getWidth(), getView().getHeight()));
            parent.removeView(getView());
        }
    }

    private ViewGroup createExpansionViewContainer(View expansionContentView, int expandWidth, int expandHeight) {
        int closeButtonSize = (int) ((50.0f * this.mDensity) + 0.5f);
        if (expandWidth < closeButtonSize) {
            expandWidth = closeButtonSize;
        }
        if (expandHeight < closeButtonSize) {
            expandHeight = closeButtonSize;
        }
        RelativeLayout expansionLayout = new RelativeLayout(getView().getContext());
        expansionLayout.setId(ServingValue.EXT_FIELD_NUMBER);
        View dimmingView = new View(getView().getContext());
        dimmingView.setBackgroundColor(0);
        dimmingView.setOnTouchListener(new C00494());
        expansionLayout.addView(dimmingView, new LayoutParams(-1, -1));
        FrameLayout adContainerLayout = new FrameLayout(getView().getContext());
        adContainerLayout.setId(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        adContainerLayout.addView(expansionContentView, new LayoutParams(-1, -1));
        LayoutParams lp = new LayoutParams(expandWidth, expandHeight);
        lp.addRule(13);
        expansionLayout.addView(adContainerLayout, lp);
        return expansionLayout;
    }

    private void setOrientationLockEnabled(boolean enabled) {
        try {
            int requestedOrientation;
            Activity activity = (Activity) getView().getContext();
            if (enabled) {
                requestedOrientation = activity.getResources().getConfiguration().orientation;
            } else {
                requestedOrientation = this.mOriginalRequestedOrientation;
            }
            activity.setRequestedOrientation(requestedOrientation);
        } catch (ClassCastException e) {
            Log.d(LOGTAG, "Unable to modify device orientation.");
        }
    }

    protected void setNativeCloseButtonEnabled(boolean enabled) {
        if (this.mRootView != null) {
            FrameLayout adContainerLayout = (FrameLayout) this.mRootView.findViewById(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (enabled) {
                if (this.mCloseButton == null) {
                    StateListDrawable states = new StateListDrawable();
                    states.addState(new int[]{-16842919}, ResourceManager.getStaticResource(getView().getContext(), -29));
                    states.addState(new int[]{16842919}, ResourceManager.getStaticResource(getView().getContext(), -29));
                    this.mCloseButton = new ImageButton(getView().getContext());
                    this.mCloseButton.setImageDrawable(states);
                    this.mCloseButton.setBackgroundDrawable(null);
                    this.mCloseButton.setOnClickListener(new C00505());
                }
                int buttonSize = (int) ((50.0f * this.mDensity) + 0.5f);
                adContainerLayout.addView(this.mCloseButton, new FrameLayout.LayoutParams(buttonSize, buttonSize, 5));
            } else {
                adContainerLayout.removeView(this.mCloseButton);
            }
            MraidView view = getView();
            if (view.getOnCloseButtonStateChangeListener() != null) {
                view.getOnCloseButtonStateChangeListener().onCloseButtonStateChange(view, enabled);
            }
        }
    }

    protected void useCustomClose(boolean shouldUseCustomCloseButton) {
        this.mAdWantsCustomCloseButton = shouldUseCustomCloseButton;
        MraidView view = getView();
        boolean enabled = !shouldUseCustomCloseButton;
        if (view.getOnCloseButtonStateChangeListener() != null) {
            view.getOnCloseButtonStateChangeListener().onCloseButtonStateChange(view, enabled);
        }
    }

    protected boolean checkViewable() {
        return true;
    }
}
