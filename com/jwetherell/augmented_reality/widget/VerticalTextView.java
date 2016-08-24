package com.jwetherell.augmented_reality.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;
import com.jwetherell.augmented_reality.activity.AugmentedReality;

public class VerticalTextView extends TextView {
    public VerticalTextView(Context context) {
        super(context);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (AugmentedReality.portrait) {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
    }

    protected void onDraw(Canvas canvas) {
        if (AugmentedReality.portrait) {
            TextPaint textPaint = getPaint();
            textPaint.setColor(getCurrentTextColor());
            textPaint.drawableState = getDrawableState();
            canvas.save();
            canvas.translate(0.0f, (float) getHeight());
            canvas.rotate(-90.0f);
            canvas.translate((float) getCompoundPaddingLeft(), (float) getExtendedPaddingTop());
            getLayout().draw(canvas);
            canvas.restore();
            return;
        }
        super.onDraw(canvas);
    }
}
