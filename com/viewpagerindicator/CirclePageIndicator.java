package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.common.primitives.Ints;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import org.joda.time.MutableDateTime;

public class CirclePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private boolean mCentered;
    private int mCurrentPage;
    private boolean mIsDragging;
    private float mLastMotionX;
    private OnPageChangeListener mListener;
    private int mOrientation;
    private float mPageOffset;
    private final Paint mPaintFill;
    private final Paint mPaintPageFill;
    private final Paint mPaintStroke;
    private float mRadius;
    private int mScrollState;
    private boolean mSnap;
    private int mSnapPage;
    private int mTouchSlop;
    private ViewPager mViewPager;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        int currentPage;

        /* renamed from: com.viewpagerindicator.CirclePageIndicator.SavedState.1 */
        class C03711 implements Creator<SavedState> {
            C03711() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPage);
        }

        static {
            CREATOR = new C03711();
        }
    }

    public CirclePageIndicator(Context context) {
        this(context, null);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, C0373R.attr.vpiCirclePageIndicatorStyle);
    }

    public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPaintPageFill = new Paint(1);
        this.mPaintStroke = new Paint(1);
        this.mPaintFill = new Paint(1);
        this.mLastMotionX = GroundOverlayOptions.NO_DIMENSION;
        this.mActivePointerId = INVALID_POINTER;
        if (!isInEditMode()) {
            Resources res = getResources();
            int defaultPageColor = res.getColor(C0373R.color.default_circle_indicator_page_color);
            int defaultFillColor = res.getColor(C0373R.color.default_circle_indicator_fill_color);
            int defaultOrientation = res.getInteger(C0373R.integer.default_circle_indicator_orientation);
            int defaultStrokeColor = res.getColor(C0373R.color.default_circle_indicator_stroke_color);
            float defaultStrokeWidth = res.getDimension(C0373R.dimen.default_circle_indicator_stroke_width);
            float defaultRadius = res.getDimension(C0373R.dimen.default_circle_indicator_radius);
            boolean defaultCentered = res.getBoolean(C0373R.bool.default_circle_indicator_centered);
            boolean defaultSnap = res.getBoolean(C0373R.bool.default_circle_indicator_snap);
            TypedArray a = context.obtainStyledAttributes(attrs, C0373R.styleable.CirclePageIndicator, defStyle, 0);
            this.mCentered = a.getBoolean(1, defaultCentered);
            this.mOrientation = a.getInt(0, defaultOrientation);
            this.mPaintPageFill.setStyle(Style.FILL);
            this.mPaintPageFill.setColor(a.getColor(4, defaultPageColor));
            this.mPaintStroke.setStyle(Style.STROKE);
            this.mPaintStroke.setColor(a.getColor(7, defaultStrokeColor));
            this.mPaintStroke.setStrokeWidth(a.getDimension(2, defaultStrokeWidth));
            this.mPaintFill.setStyle(Style.FILL);
            this.mPaintFill.setColor(a.getColor(3, defaultFillColor));
            this.mRadius = a.getDimension(5, defaultRadius);
            this.mSnap = a.getBoolean(6, defaultSnap);
            a.recycle();
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    public void setCentered(boolean centered) {
        this.mCentered = centered;
        invalidate();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public void setPageColor(int pageColor) {
        this.mPaintPageFill.setColor(pageColor);
        invalidate();
    }

    public int getPageColor() {
        return this.mPaintPageFill.getColor();
    }

    public void setFillColor(int fillColor) {
        this.mPaintFill.setColor(fillColor);
        invalidate();
    }

    public int getFillColor() {
        return this.mPaintFill.getColor();
    }

    public void setOrientation(int orientation) {
        switch (orientation) {
            case MutableDateTime.ROUND_NONE /*0*/:
            case Value.TYPE_FIELD_NUMBER /*1*/:
                this.mOrientation = orientation;
                requestLayout();
            default:
                throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setStrokeColor(int strokeColor) {
        this.mPaintStroke.setColor(strokeColor);
        invalidate();
    }

    public int getStrokeColor() {
        return this.mPaintStroke.getColor();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mPaintStroke.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public float getStrokeWidth() {
        return this.mPaintStroke.getStrokeWidth();
    }

    public void setRadius(float radius) {
        this.mRadius = radius;
        invalidate();
    }

    public float getRadius() {
        return this.mRadius;
    }

    public void setSnap(boolean snap) {
        this.mSnap = snap;
        invalidate();
    }

    public boolean isSnap() {
        return this.mSnap;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count != 0) {
                int i = this.mCurrentPage;
                if (r0 >= count) {
                    setCurrentItem(count + INVALID_POINTER);
                    return;
                }
                int longSize;
                int longPaddingBefore;
                int longPaddingAfter;
                int shortPaddingBefore;
                float dX;
                float dY;
                if (this.mOrientation == 0) {
                    longSize = getWidth();
                    longPaddingBefore = getPaddingLeft();
                    longPaddingAfter = getPaddingRight();
                    shortPaddingBefore = getPaddingTop();
                } else {
                    longSize = getHeight();
                    longPaddingBefore = getPaddingTop();
                    longPaddingAfter = getPaddingBottom();
                    shortPaddingBefore = getPaddingLeft();
                }
                float threeRadius = this.mRadius * 3.0f;
                float shortOffset = ((float) shortPaddingBefore) + this.mRadius;
                float longOffset = ((float) longPaddingBefore) + this.mRadius;
                if (this.mCentered) {
                    longOffset += (((float) ((longSize - longPaddingBefore) - longPaddingAfter)) / 2.0f) - ((((float) count) * threeRadius) / 2.0f);
                }
                float pageFillRadius = this.mRadius;
                if (this.mPaintStroke.getStrokeWidth() > 0.0f) {
                    pageFillRadius -= this.mPaintStroke.getStrokeWidth() / 2.0f;
                }
                for (int iLoop = 0; iLoop < count; iLoop++) {
                    float drawLong = longOffset + (((float) iLoop) * threeRadius);
                    if (this.mOrientation == 0) {
                        dX = drawLong;
                        dY = shortOffset;
                    } else {
                        dX = shortOffset;
                        dY = drawLong;
                    }
                    if (this.mPaintPageFill.getAlpha() > 0) {
                        canvas.drawCircle(dX, dY, pageFillRadius, this.mPaintPageFill);
                    }
                    if (pageFillRadius != this.mRadius) {
                        canvas.drawCircle(dX, dY, this.mRadius, this.mPaintStroke);
                    }
                }
                float cx = ((float) (this.mSnap ? this.mSnapPage : this.mCurrentPage)) * threeRadius;
                if (!this.mSnap) {
                    cx += this.mPageOffset * threeRadius;
                }
                if (this.mOrientation == 0) {
                    dX = longOffset + cx;
                    dY = shortOffset;
                } else {
                    dX = shortOffset;
                    dY = longOffset + cx;
                }
                canvas.drawCircle(dX, dY, this.mRadius, this.mPaintFill);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (super.onTouchEvent(ev)) {
            return true;
        }
        if (this.mViewPager == null || this.mViewPager.getAdapter().getCount() == 0) {
            return false;
        }
        switch (ev.getAction() & MotionEventCompat.ACTION_MASK) {
            case MutableDateTime.ROUND_NONE /*0*/:
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mLastMotionX = ev.getX();
                break;
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                if (!this.mIsDragging) {
                    int count = this.mViewPager.getAdapter().getCount();
                    int width = getWidth();
                    float halfWidth = ((float) width) / 2.0f;
                    float sixthWidth = ((float) width) / 6.0f;
                    if (this.mCurrentPage > 0 && ev.getX() < halfWidth - sixthWidth) {
                        this.mViewPager.setCurrentItem(this.mCurrentPage + INVALID_POINTER);
                        return true;
                    } else if (this.mCurrentPage < count + INVALID_POINTER && ev.getX() > halfWidth + sixthWidth) {
                        this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                        return true;
                    }
                }
                this.mIsDragging = false;
                this.mActivePointerId = INVALID_POINTER;
                if (this.mViewPager.isFakeDragging()) {
                    this.mViewPager.endFakeDrag();
                    break;
                }
                break;
            case Value.STRING_FIELD_NUMBER /*2*/:
                float x = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                float deltaX = x - this.mLastMotionX;
                if (!this.mIsDragging && Math.abs(deltaX) > ((float) this.mTouchSlop)) {
                    this.mIsDragging = true;
                }
                if (this.mIsDragging) {
                    this.mLastMotionX = x;
                    if (this.mViewPager.isFakeDragging() || this.mViewPager.beginFakeDrag()) {
                        this.mViewPager.fakeDragBy(deltaX);
                        break;
                    }
                }
                break;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                int index = MotionEventCompat.getActionIndex(ev);
                this.mLastMotionX = MotionEventCompat.getX(ev, index);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                int pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, pointerIndex == 0 ? 1 : 0);
                }
                this.mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                break;
        }
        return true;
    }

    public void setViewPager(ViewPager view) {
        if (this.mViewPager != view) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (view.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = view;
            this.mViewPager.setOnPageChangeListener(this);
            invalidate();
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(item);
        this.mCurrentPage = item;
        invalidate();
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.mCurrentPage = position;
        this.mPageOffset = positionOffset;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.mSnap || this.mScrollState == 0) {
            this.mCurrentPage = position;
            this.mSnapPage = position;
            invalidate();
        }
        if (this.mListener != null) {
            this.mListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 0) {
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
        }
    }

    private int measureLong(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == Ints.MAX_POWER_OF_TWO || this.mViewPager == null) {
            return specSize;
        }
        int count = this.mViewPager.getAdapter().getCount();
        int result = (int) (((((float) (getPaddingLeft() + getPaddingRight())) + (((float) (count * 2)) * this.mRadius)) + (((float) (count + INVALID_POINTER)) * this.mRadius)) + AugmentedReality.ONE_PERCENT);
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    private int measureShort(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == Ints.MAX_POWER_OF_TWO) {
            return specSize;
        }
        int result = (int) ((((2.0f * this.mRadius) + ((float) getPaddingTop())) + ((float) getPaddingBottom())) + AugmentedReality.ONE_PERCENT);
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.currentPage;
        this.mSnapPage = savedState.currentPage;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }
}
