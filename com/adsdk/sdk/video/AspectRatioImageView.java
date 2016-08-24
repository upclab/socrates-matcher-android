package com.adsdk.sdk.video;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class AspectRatioImageView extends ImageView {
    private boolean mFill;
    private int mMaxH;
    private int mMinW;

    public AspectRatioImageView(Context context) {
        super(context);
        this.mFill = false;
        this.mMinW = -1;
        this.mMaxH = -1;
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mFill = false;
        this.mMinW = -1;
        this.mMaxH = -1;
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mFill = false;
        this.mMinW = -1;
        this.mMaxH = -1;
    }

    public void fillParent(boolean fill, int minWidthDip, int maxHeightDip) {
        this.mFill = fill;
        this.mMaxH = maxHeightDip;
        this.mMinW = minWidthDip;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (!this.mFill || getDrawable() == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int drawableH = getDrawable().getIntrinsicHeight();
        int drawableW = getDrawable().getIntrinsicWidth();
        if (drawableW > drawableH) {
            height = (width * drawableH) / drawableW;
        } else {
            height = width;
            width = (height * drawableW) / drawableH;
        }
        ensureConstraintMetAndSet(width, height, drawableW, drawableH);
    }

    protected int getMeasuredHeight(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (!this.mFill || getDrawable() == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return 0;
        }
        int drawableH = getDrawable().getIntrinsicHeight();
        int drawableW = getDrawable().getIntrinsicWidth();
        if (drawableW > drawableH) {
            height = (width * drawableH) / drawableW;
        } else {
            height = width;
            width = (height * drawableW) / drawableH;
        }
        return getConstrainedHeight(width, height, drawableW, drawableH);
    }

    void ensureConstraintMetAndSet(int measuredWidth, int measuredHeight, int drawableW, int drawableH) {
        float minW;
        float maxH;
        if (drawableW < drawableH) {
            if (this.mMinW > 0) {
                minW = dip2pixel(this.mMinW, getContext());
                if (((float) measuredWidth) < minW) {
                    measuredWidth = (int) minW;
                    measuredHeight = (drawableH / drawableW) * measuredWidth;
                }
            }
            if (this.mMaxH > 0) {
                maxH = dip2pixel(this.mMaxH, getContext());
                if (((float) measuredHeight) > maxH) {
                    measuredHeight = (int) maxH;
                    measuredWidth = (measuredHeight * drawableW) / drawableH;
                }
            }
        } else {
            if (this.mMaxH > 0) {
                maxH = dip2pixel(this.mMaxH, getContext());
                if (((float) measuredHeight) > maxH) {
                    measuredHeight = (int) maxH;
                    measuredWidth = (measuredHeight * drawableW) / drawableH;
                }
            }
            if (this.mMinW > 0) {
                minW = dip2pixel(this.mMinW, getContext());
                if (((float) measuredWidth) < minW) {
                    measuredWidth = (int) minW;
                    measuredHeight = (drawableH / drawableW) * measuredWidth;
                }
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    int getConstrainedHeight(int measuredWidth, int measuredHeight, int drawableW, int drawableH) {
        float minW;
        float maxH;
        if (drawableW < drawableH) {
            if (this.mMinW > 0) {
                minW = dip2pixel(this.mMinW, getContext());
                if (((float) measuredWidth) < minW) {
                    measuredHeight = (drawableH / drawableW) * ((int) minW);
                }
            }
            if (this.mMaxH <= 0) {
                return measuredHeight;
            }
            maxH = dip2pixel(this.mMaxH, getContext());
            if (((float) measuredHeight) <= maxH) {
                return measuredHeight;
            }
            measuredHeight = (int) maxH;
            measuredWidth = (measuredHeight * drawableW) / drawableH;
            return measuredHeight;
        }
        if (this.mMaxH > 0) {
            maxH = dip2pixel(this.mMaxH, getContext());
            if (((float) measuredHeight) > maxH) {
                measuredHeight = (int) maxH;
                measuredWidth = (measuredHeight * drawableW) / drawableH;
            }
        }
        if (this.mMinW <= 0) {
            return measuredHeight;
        }
        minW = dip2pixel(this.mMinW, getContext());
        if (((float) measuredWidth) >= minW) {
            return measuredHeight;
        }
        return (drawableH / drawableW) * ((int) minW);
    }

    public static float dip2pixel(int dip, Context context) {
        return TypedValue.applyDimension(1, (float) dip, context.getResources().getDisplayMetrics());
    }
}
