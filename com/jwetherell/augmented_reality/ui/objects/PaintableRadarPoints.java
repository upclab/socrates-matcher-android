package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.ui.Radar;

public class PaintableRadarPoints extends PaintableObject {
    private final float[] locationArray;
    private PaintablePoint paintablePoint;
    private PaintablePosition pointContainer;

    public PaintableRadarPoints() {
        this.locationArray = new float[3];
        this.paintablePoint = null;
        this.pointContainer = null;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        float scale = (ARData.getRadius() * 1000.0f) / Radar.RADIUS;
        for (Marker pm : ARData.getMarkers()) {
            pm.getLocation().get(this.locationArray);
            float x = this.locationArray[0] / scale;
            float y = this.locationArray[2] / scale;
            if ((x * x) + (y * y) < 2304.0f) {
                if (this.paintablePoint == null) {
                    this.paintablePoint = new PaintablePoint(pm.getColor(), true);
                } else {
                    this.paintablePoint.set(pm.getColor(), true);
                }
                if (this.pointContainer == null) {
                    this.pointContainer = new PaintablePosition(this.paintablePoint, (x + Radar.RADIUS) - AugmentedReality.ONE_PERCENT, (y + Radar.RADIUS) - AugmentedReality.ONE_PERCENT, 0.0f, AugmentedReality.ONE_PERCENT);
                } else {
                    this.pointContainer.set(this.paintablePoint, (x + Radar.RADIUS) - AugmentedReality.ONE_PERCENT, (y + Radar.RADIUS) - AugmentedReality.ONE_PERCENT, 0.0f, AugmentedReality.ONE_PERCENT);
                }
                this.pointContainer.paint(canvas);
            }
        }
    }

    public float getWidth() {
        return 96.0f;
    }

    public float getHeight() {
        return 96.0f;
    }
}
