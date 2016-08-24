package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;

public class PaintableText extends PaintableObject {
    private static final float HEIGHT_PAD = 2.0f;
    private static final float WIDTH_PAD = 4.0f;
    private boolean bg;
    private int color;
    private float height;
    private int size;
    private String text;
    private float width;

    public PaintableText(String text, int color, int size, boolean paintBackground) {
        this.text = null;
        this.color = 0;
        this.size = 0;
        this.width = 0.0f;
        this.height = 0.0f;
        this.bg = false;
        set(text, color, size, paintBackground);
    }

    public void set(String text, int color, int size, boolean paintBackground) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        this.bg = paintBackground;
        this.color = color;
        this.size = size;
        this.width = getTextWidth(text) + 8.0f;
        this.height = (getTextAsc() + getTextDesc()) + WIDTH_PAD;
    }

    public void paint(Canvas canvas) {
        if (canvas == null || this.text == null) {
            throw new NullPointerException();
        }
        setColor(this.color);
        setFontSize((float) this.size);
        if (this.bg) {
            setColor(Color.rgb(0, 0, 0));
            setFill(true);
            paintRect(canvas, -(this.width / HEIGHT_PAD), -(this.height / HEIGHT_PAD), this.width, this.height);
            setColor(Color.rgb(MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK, MotionEventCompat.ACTION_MASK));
            setFill(false);
            paintRect(canvas, -(this.width / HEIGHT_PAD), -(this.height / HEIGHT_PAD), this.width, this.height);
        }
        paintText(canvas, WIDTH_PAD - (this.width / HEIGHT_PAD), (getTextAsc() + HEIGHT_PAD) - (this.height / HEIGHT_PAD), this.text);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
