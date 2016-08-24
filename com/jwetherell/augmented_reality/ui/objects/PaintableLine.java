package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;

public class PaintableLine extends PaintableObject {
    private int color;
    private float f141x;
    private float f142y;

    public PaintableLine(int color, float x, float y) {
        this.color = 0;
        this.f141x = 0.0f;
        this.f142y = 0.0f;
        set(color, x, y);
    }

    public void set(int color, float x, float y) {
        this.color = color;
        this.f141x = x;
        this.f142y = y;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setFill(false);
        setColor(this.color);
        paintLine(canvas, 0.0f, 0.0f, this.f141x, this.f142y);
    }

    public float getWidth() {
        return this.f141x;
    }

    public float getHeight() {
        return this.f142y;
    }
}
