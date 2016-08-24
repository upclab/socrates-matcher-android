package com.squareup.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.widget.ImageView;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.squareup.picasso.Picasso.LoadedFrom;

final class PicassoDrawable extends Drawable {
    private static final Paint DEBUG_PAINT;
    private static final float FADE_DURATION = 200.0f;
    int alpha;
    boolean animating;
    private final boolean debugging;
    private final float density;
    final BitmapDrawable image;
    private final LoadedFrom loadedFrom;
    Drawable placeholder;
    long startTimeMillis;

    static {
        DEBUG_PAINT = new Paint();
    }

    static void setBitmap(ImageView target, Context context, Bitmap bitmap, LoadedFrom loadedFrom, boolean noFade, boolean debugging) {
        Drawable placeholder = target.getDrawable();
        if (placeholder instanceof AnimationDrawable) {
            ((AnimationDrawable) placeholder).stop();
        }
        target.setImageDrawable(new PicassoDrawable(context, placeholder, bitmap, loadedFrom, noFade, debugging));
    }

    static void setPlaceholder(ImageView target, int placeholderResId, Drawable placeholderDrawable) {
        if (placeholderResId != 0) {
            target.setImageResource(placeholderResId);
        } else {
            target.setImageDrawable(placeholderDrawable);
        }
        if (target.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) target.getDrawable()).start();
        }
    }

    PicassoDrawable(Context context, Drawable placeholder, Bitmap bitmap, LoadedFrom loadedFrom, boolean noFade, boolean debugging) {
        this.alpha = MotionEventCompat.ACTION_MASK;
        Resources res = context.getResources();
        this.debugging = debugging;
        this.density = res.getDisplayMetrics().density;
        this.loadedFrom = loadedFrom;
        this.image = new BitmapDrawable(res, bitmap);
        boolean fade = (loadedFrom == LoadedFrom.MEMORY || noFade) ? false : true;
        if (fade) {
            this.placeholder = placeholder;
            this.animating = true;
            this.startTimeMillis = SystemClock.uptimeMillis();
        }
    }

    public void draw(Canvas canvas) {
        if (this.animating) {
            float normalized = ((float) (SystemClock.uptimeMillis() - this.startTimeMillis)) / FADE_DURATION;
            if (normalized >= AugmentedReality.ONE_PERCENT) {
                this.animating = false;
                this.placeholder = null;
                this.image.draw(canvas);
            } else {
                if (this.placeholder != null) {
                    this.placeholder.draw(canvas);
                }
                this.image.setAlpha((int) (((float) this.alpha) * normalized));
                this.image.draw(canvas);
                this.image.setAlpha(this.alpha);
                invalidateSelf();
            }
        } else {
            this.image.draw(canvas);
        }
        if (this.debugging) {
            drawDebugIndicator(canvas);
        }
    }

    public int getIntrinsicWidth() {
        return this.image.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() {
        return this.image.getIntrinsicHeight();
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
        if (this.placeholder != null) {
            this.placeholder.setAlpha(alpha);
        }
        this.image.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        if (this.placeholder != null) {
            this.placeholder.setColorFilter(cf);
        }
        this.image.setColorFilter(cf);
    }

    public int getOpacity() {
        return this.image.getOpacity();
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.image.setBounds(bounds);
        if (this.placeholder != null) {
            this.placeholder.setBounds(bounds);
        }
    }

    private void drawDebugIndicator(Canvas canvas) {
        DEBUG_PAINT.setColor(-1);
        canvas.drawPath(getTrianglePath(new Point(0, 0), (int) (16.0f * this.density)), DEBUG_PAINT);
        DEBUG_PAINT.setColor(this.loadedFrom.debugColor);
        canvas.drawPath(getTrianglePath(new Point(0, 0), (int) (15.0f * this.density)), DEBUG_PAINT);
    }

    private static Path getTrianglePath(Point p1, int width) {
        Point p2 = new Point(p1.x + width, p1.y);
        Point p3 = new Point(p1.x, p1.y + width);
        Path path = new Path();
        path.moveTo((float) p1.x, (float) p1.y);
        path.lineTo((float) p2.x, (float) p2.y);
        path.lineTo((float) p3.x, (float) p3.y);
        return path;
    }
}
