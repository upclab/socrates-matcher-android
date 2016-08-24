package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;

public class PaintableCircle extends PaintableObject {
    private int color;
    private boolean fill;
    private float radius;

    public PaintableCircle(int color, float radius, boolean fill) {
        this.color = 0;
        this.radius = 0.0f;
        this.fill = false;
        set(color, radius, fill);
    }

    public void set(int color, float radius, boolean fill) {
        this.color = color;
        this.radius = radius;
        this.fill = fill;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setFill(this.fill);
        setColor(this.color);
        paintCircle(canvas, 0.0f, 0.0f, this.radius);
    }

    public float getWidth() {
        return this.radius * 2.0f;
    }

    public float getHeight() {
        return this.radius * 2.0f;
    }
}
