package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;

public class PaintablePoint extends PaintableObject {
    private static int height;
    private static int width;
    private int color;
    private boolean fill;

    static {
        width = 2;
        height = 2;
    }

    public PaintablePoint(int color, boolean fill) {
        this.color = 0;
        this.fill = false;
        set(color, fill);
    }

    public void set(int color, boolean fill) {
        this.color = color;
        this.fill = fill;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setFill(this.fill);
        setColor(this.color);
        paintRect(canvas, (float) ((-width) / 2), (float) ((-height) / 2), (float) width, (float) height);
    }

    public float getWidth() {
        return (float) width;
    }

    public float getHeight() {
        return (float) height;
    }
}
