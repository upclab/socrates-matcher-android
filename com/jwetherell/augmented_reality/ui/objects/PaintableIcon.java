package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class PaintableIcon extends PaintableObject {
    private Bitmap bitmap;

    public PaintableIcon(Bitmap bitmap, int width, int height) {
        this.bitmap = null;
        set(bitmap, width, height);
    }

    public void set(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            throw new NullPointerException();
        }
        this.bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    public void paint(Canvas canvas) {
        if (canvas == null || this.bitmap == null) {
            throw new NullPointerException();
        }
        paintBitmap(canvas, this.bitmap, (float) (-(this.bitmap.getWidth() / 2)), (float) (-(this.bitmap.getHeight() / 2)));
    }

    public float getWidth() {
        return (float) this.bitmap.getWidth();
    }

    public float getHeight() {
        return (float) this.bitmap.getHeight();
    }
}
