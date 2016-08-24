package com.jwetherell.augmented_reality.activity;

import android.graphics.Color;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.jwetherell.augmented_reality.camera.CameraSurface;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.widget.VerticalSeekBar;
import com.jwetherell.augmented_reality.widget.VerticalTextView;
import java.text.DecimalFormat;

public class AugmentedReality extends SensorsActivity implements OnTouchListener {
    public static final float EIGHTY_PERCENTY = 80.0f;
    private static final String END_TEXT;
    private static final int END_TEXT_COLOR = -1;
    private static final DecimalFormat FORMAT;
    public static final float MAX_ZOOM = 100.0f;
    public static final float ONE_PERCENT = 1.0f;
    private static final String TAG = "AugmentedReality";
    public static final float TEN_PERCENT = 10.0f;
    public static final float TWENTY_PERCENT = 20.0f;
    private static final int ZOOMBAR_BACKGROUND_COLOR;
    protected static AugmentedView augmentedView;
    protected static CameraSurface camScreen;
    protected static VerticalTextView endLabel;
    protected static VerticalSeekBar myZoomBar;
    public static boolean portrait;
    public static boolean showRadar;
    public static boolean showZoomBar;
    public static boolean useCollisionDetection;
    protected static WakeLock wakeLock;
    protected static LinearLayout zoomLayout;
    private OnSeekBarChangeListener myZoomBarOnSeekBarChangeListener;

    /* renamed from: com.jwetherell.augmented_reality.activity.AugmentedReality.1 */
    class C03601 implements OnSeekBarChangeListener {
        C03601() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            AugmentedReality.this.updateDataOnZoom();
            AugmentedReality.camScreen.invalidate();
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            AugmentedReality.this.updateDataOnZoom();
            AugmentedReality.camScreen.invalidate();
        }
    }

    public AugmentedReality() {
        this.myZoomBarOnSeekBarChangeListener = new C03601();
    }

    static {
        FORMAT = new DecimalFormat("#.##");
        ZOOMBAR_BACKGROUND_COLOR = Color.argb(125, 55, 55, 55);
        END_TEXT = new StringBuilder(String.valueOf(FORMAT.format(100.0d))).append(" km").toString();
        wakeLock = null;
        camScreen = null;
        myZoomBar = null;
        endLabel = null;
        zoomLayout = null;
        augmentedView = null;
        portrait = false;
        useCollisionDetection = false;
        showRadar = true;
        showZoomBar = true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        camScreen = new CameraSurface(this);
        setContentView(camScreen);
        augmentedView = new AugmentedView(this);
        augmentedView.setOnTouchListener(this);
        addContentView(augmentedView, new LayoutParams(-2, -2));
        zoomLayout = new LinearLayout(this);
        zoomLayout.setVisibility(showZoomBar ? 0 : 8);
        zoomLayout.setOrientation(1);
        zoomLayout.setPadding(5, 5, 5, 5);
        zoomLayout.setBackgroundColor(ZOOMBAR_BACKGROUND_COLOR);
        endLabel = new VerticalTextView(this);
        endLabel.setText(END_TEXT);
        endLabel.setTextColor(END_TEXT_COLOR);
        LinearLayout.LayoutParams zoomTextParams = new LinearLayout.LayoutParams(-2, -2);
        zoomTextParams.gravity = 17;
        zoomLayout.addView(endLabel, zoomTextParams);
        myZoomBar = new VerticalSeekBar(this);
        myZoomBar.setMax(100);
        myZoomBar.setProgress(50);
        myZoomBar.setOnSeekBarChangeListener(this.myZoomBarOnSeekBarChangeListener);
        LinearLayout.LayoutParams zoomBarParams = new LinearLayout.LayoutParams(-2, END_TEXT_COLOR);
        zoomBarParams.gravity = 1;
        zoomLayout.addView(myZoomBar, zoomBarParams);
        addContentView(zoomLayout, new FrameLayout.LayoutParams(-2, END_TEXT_COLOR, 5));
        updateDataOnZoom();
        wakeLock = ((PowerManager) getSystemService("power")).newWakeLock(26, "DoNotDimScreen");
    }

    public void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    public void onPause() {
        super.onPause();
        wakeLock.release();
    }

    public void onSensorChanged(SensorEvent evt) {
        super.onSensorChanged(evt);
        if (evt.sensor.getType() == 1 || evt.sensor.getType() == 2) {
            augmentedView.postInvalidate();
        }
    }

    private static float calcZoomLevel() {
        int myZoomLevel = myZoomBar.getProgress();
        if (myZoomLevel <= 25) {
            return ONE_PERCENT * (((float) myZoomLevel) / 25.0f);
        }
        if (myZoomLevel > 25 && myZoomLevel <= 50) {
            return ONE_PERCENT + (TEN_PERCENT * ((((float) myZoomLevel) - 25.0f) / 25.0f));
        }
        if (myZoomLevel <= 50 || myZoomLevel > 75) {
            return TWENTY_PERCENT + (EIGHTY_PERCENTY * ((((float) myZoomLevel) - 75.0f) / 25.0f));
        }
        return TEN_PERCENT + (TWENTY_PERCENT * ((((float) myZoomLevel) - 50.0f) / 25.0f));
    }

    protected void updateDataOnZoom() {
        float zoomLevel = calcZoomLevel();
        ARData.setRadius(zoomLevel);
        ARData.setZoomLevel(FORMAT.format((double) zoomLevel));
        ARData.setZoomProgress(myZoomBar.getProgress());
    }

    public boolean onTouch(View view, MotionEvent me) {
        for (Marker marker : ARData.getMarkers()) {
            if (marker.handleClick(me.getX(), me.getY())) {
                if (me.getAction() != 1) {
                    return true;
                }
                markerTouched(marker);
                return true;
            }
        }
        return super.onTouchEvent(me);
    }

    protected void markerTouched(Marker marker) {
        Log.w(TAG, "markerTouched() not implemented.");
    }
}
