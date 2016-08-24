package com.jwetherell.augmented_reality.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.time.MutableDateTime;

public class VerticalSeekBar extends SeekBar {
    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90.0f);
        c.translate((float) (-getHeight()), 0.0f);
        super.onDraw(c);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MutableDateTime.ROUND_NONE /*0*/:
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.STRING_FIELD_NUMBER /*2*/:
                setProgress(getMax() - ((int) ((((float) getMax()) * event.getY()) / ((float) getHeight()))));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
        }
        return true;
    }
}
