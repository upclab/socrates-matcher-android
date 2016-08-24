package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;

public class PaintableGps extends PaintableObject {
    private static final int FRAME_SIZE = 15;
    private int color;
    private boolean fill;
    private float radius;
    private float strokeWidth;

    public PaintableGps(float radius, float strokeWidth, boolean fill, int color) {
        this.radius = 0.0f;
        this.strokeWidth = 0.0f;
        this.fill = false;
        this.color = 0;
        set(radius, strokeWidth, fill, color);
    }

    public void set(float radius, float strokeWidth, boolean fill, int color) {
        this.radius = radius;
        this.strokeWidth = strokeWidth;
        this.fill = fill;
        this.color = color;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setStrokeWidth(this.strokeWidth);
        setFill(this.fill);
        setColor(this.color);
        paintCircle(canvas, 0.0f, 0.0f, this.radius);
    }

    public float getWidth() {
        return (this.radius * 2.0f) + 15.0f;
    }

    public float getHeight() {
        return (this.radius * 2.0f) + 15.0f;
    }
}
