package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public abstract class PaintableObject {
    private Paint paint;

    public abstract float getHeight();

    public abstract float getWidth();

    public abstract void paint(Canvas canvas);

    public PaintableObject() {
        this.paint = new Paint(1);
        if (this.paint == null) {
            this.paint = new Paint();
            this.paint.setTextSize(16.0f);
            this.paint.setAntiAlias(true);
            this.paint.setColor(-16776961);
            this.paint.setStyle(Style.STROKE);
        }
    }

    public void setFill(boolean fill) {
        if (fill) {
            this.paint.setStyle(Style.FILL);
        } else {
            this.paint.setStyle(Style.STROKE);
        }
    }

    public void setColor(int c) {
        this.paint.setColor(c);
    }

    public void setStrokeWidth(float w) {
        this.paint.setStrokeWidth(w);
    }

    public float getTextWidth(String txt) {
        if (txt != null) {
            return this.paint.measureText(txt);
        }
        throw new NullPointerException();
    }

    public float getTextAsc() {
        return -this.paint.ascent();
    }

    public float getTextDesc() {
        return this.paint.descent();
    }

    public void setFontSize(float size) {
        this.paint.setTextSize(size);
    }

    public void paintLine(Canvas canvas, float x1, float y1, float x2, float y2) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        canvas.drawLine(x1, y1, x2, y2, this.paint);
    }

    public void paintRect(Canvas canvas, float x, float y, float width, float height) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        canvas.drawRect(x, y, x + width, y + height, this.paint);
    }

    public void paintRoundedRect(Canvas canvas, float x, float y, float width, float height) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        canvas.drawRoundRect(new RectF(x, y, x + width, y + height), 15.0f, 15.0f, this.paint);
    }

    public void paintBitmap(Canvas canvas, Bitmap bitmap, Rect src, Rect dst) {
        if (canvas == null || bitmap == null) {
            throw new NullPointerException();
        }
        canvas.drawBitmap(bitmap, src, dst, this.paint);
    }

    public void paintBitmap(Canvas canvas, Bitmap bitmap, float left, float top) {
        if (canvas == null || bitmap == null) {
            throw new NullPointerException();
        }
        canvas.drawBitmap(bitmap, left, top, this.paint);
    }

    public void paintCircle(Canvas canvas, float x, float y, float radius) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        canvas.drawCircle(x, y, radius, this.paint);
    }

    public void paintText(Canvas canvas, float x, float y, String text) {
        if (canvas == null || text == null) {
            throw new NullPointerException();
        }
        canvas.drawText(text, x, y, this.paint);
    }

    public void paintObj(Canvas canvas, PaintableObject obj, float x, float y, float rotation, float scale) {
        if (canvas == null || obj == null) {
            throw new NullPointerException();
        }
        canvas.save();
        canvas.translate((obj.getWidth() / 2.0f) + x, (obj.getHeight() / 2.0f) + y);
        canvas.rotate(rotation);
        canvas.scale(scale, scale);
        canvas.translate(-(obj.getWidth() / 2.0f), -(obj.getHeight() / 2.0f));
        obj.paint(canvas);
        canvas.restore();
    }

    public void paintPath(Canvas canvas, Path path, float x, float y, float width, float height, float rotation, float scale) {
        if (canvas == null || path == null) {
            throw new NullPointerException();
        }
        canvas.save();
        canvas.translate((width / 2.0f) + x, (height / 2.0f) + y);
        canvas.rotate(rotation);
        canvas.scale(scale, scale);
        canvas.translate(-(width / 2.0f), -(height / 2.0f));
        canvas.drawPath(path, this.paint);
        canvas.restore();
    }
}
