package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;

public class PaintableBox extends PaintableObject {
    private int backgroundColor;
    private int borderColor;
    private float height;
    private float width;

    public PaintableBox(float width, float height) {
        this(width, height, Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK), Color.argb(AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER, 0, 0, 0));
    }

    public PaintableBox(float width, float height, int borderColor, int bgColor) {
        this.width = 0.0f;
        this.height = 0.0f;
        this.borderColor = Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK);
        this.backgroundColor = Color.argb(AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER, 0, 0, 0);
        set(width, height, borderColor, bgColor);
    }

    public void set(float width, float height) {
        set(width, height, this.borderColor, this.backgroundColor);
    }

    public void set(float width, float height, int borderColor, int bgColor) {
        this.width = width;
        this.height = height;
        this.borderColor = borderColor;
        this.backgroundColor = bgColor;
    }

    public void paint(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        setFill(true);
        setColor(this.backgroundColor);
        paintRect(canvas, 0.0f, 0.0f, this.width, this.height);
        setFill(false);
        setColor(this.borderColor);
        paintRect(canvas, 0.0f, 0.0f, this.width, this.height);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
