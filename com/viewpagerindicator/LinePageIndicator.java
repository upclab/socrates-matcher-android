package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.common.primitives.Ints;
import org.joda.time.MutableDateTime;

public class LinePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private boolean mCentered;
    private int mCurrentPage;
    private float mGapWidth;
    private boolean mIsDragging;
    private float mLastMotionX;
    private float mLineWidth;
    private OnPageChangeListener mListener;
    private final Paint mPaintSelected;
    private final Paint mPaintUnselected;
    private int mTouchSlop;
    private ViewPager mViewPager;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR;
        int currentPage;

        /* renamed from: com.viewpagerindicator.LinePageIndicator.SavedState.1 */
        class C03721 implements Creator<SavedState> {
            C03721() {
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
            CREATOR = new C03721();
        }
    }

    public LinePageIndicator(Context context) {
        this(context, null);
    }

    public LinePageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, C0373R.attr.vpiLinePageIndicatorStyle);
    }

    public LinePageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPaintUnselected = new Paint(1);
        this.mPaintSelected = new Paint(1);
        this.mLastMotionX = GroundOverlayOptions.NO_DIMENSION;
        this.mActivePointerId = INVALID_POINTER;
        if (!isInEditMode()) {
            Resources res = getResources();
            int defaultSelectedColor = res.getColor(C0373R.color.default_line_indicator_selected_color);
            int defaultUnselectedColor = res.getColor(C0373R.color.default_line_indicator_unselected_color);
            float defaultLineWidth = res.getDimension(C0373R.dimen.default_line_indicator_line_width);
            float defaultGapWidth = res.getDimension(C0373R.dimen.default_line_indicator_gap_width);
            float defaultStrokeWidth = res.getDimension(C0373R.dimen.default_line_indicator_stroke_width);
            boolean defaultCentered = res.getBoolean(C0373R.bool.default_line_indicator_centered);
            TypedArray a = context.obtainStyledAttributes(attrs, C0373R.styleable.LinePageIndicator, defStyle, 0);
            this.mCentered = a.getBoolean(0, defaultCentered);
            this.mLineWidth = a.getDimension(4, defaultLineWidth);
            this.mGapWidth = a.getDimension(5, defaultGapWidth);
            setStrokeWidth(a.getDimension(2, defaultStrokeWidth));
            this.mPaintUnselected.setColor(a.getColor(3, defaultUnselectedColor));
            this.mPaintSelected.setColor(a.getColor(1, defaultSelectedColor));
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

    public void setUnselectedColor(int unselectedColor) {
        this.mPaintUnselected.setColor(unselectedColor);
        invalidate();
    }

    public int getUnselectedColor() {
        return this.mPaintUnselected.getColor();
    }

    public void setSelectedColor(int selectedColor) {
        this.mPaintSelected.setColor(selectedColor);
        invalidate();
    }

    public int getSelectedColor() {
        return this.mPaintSelected.getColor();
    }

    public void setLineWidth(float lineWidth) {
        this.mLineWidth = lineWidth;
        invalidate();
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public void setStrokeWidth(float lineHeight) {
        this.mPaintSelected.setStrokeWidth(lineHeight);
        this.mPaintUnselected.setStrokeWidth(lineHeight);
        invalidate();
    }

    public float getStrokeWidth() {
        return this.mPaintSelected.getStrokeWidth();
    }

    public void setGapWidth(float gapWidth) {
        this.mGapWidth = gapWidth;
        invalidate();
    }

    public float getGapWidth() {
        return this.mGapWidth;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count == 0) {
                return;
            }
            if (this.mCurrentPage >= count) {
                setCurrentItem(count + INVALID_POINTER);
                return;
            }
            float lineWidthAndGap = this.mLineWidth + this.mGapWidth;
            float indicatorWidth = (((float) count) * lineWidthAndGap) - this.mGapWidth;
            float paddingTop = (float) getPaddingTop();
            float paddingLeft = (float) getPaddingLeft();
            float paddingRight = (float) getPaddingRight();
            float verticalOffset = paddingTop + (((((float) getHeight()) - paddingTop) - ((float) getPaddingBottom())) / 2.0f);
            float horizontalOffset = paddingLeft;
            if (this.mCentered) {
                horizontalOffset += (((((float) getWidth()) - paddingLeft) - paddingRight) / 2.0f) - (indicatorWidth / 2.0f);
            }
            int i = 0;
            while (i < count) {
                float dx1 = horizontalOffset + (((float) i) * lineWidthAndGap);
                canvas.drawLine(dx1, verticalOffset, dx1 + this.mLineWidth, verticalOffset, i == this.mCurrentPage ? this.mPaintSelected : this.mPaintUnselected);
                i++;
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

    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager != viewPager) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = viewPager;
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
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        this.mCurrentPage = position;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageSelected(position);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == Ints.MAX_POWER_OF_TWO || this.mViewPager == null) {
            result = (float) specSize;
        } else {
            int count = this.mViewPager.getAdapter().getCount();
            result = (((float) (getPaddingLeft() + getPaddingRight())) + (((float) count) * this.mLineWidth)) + (((float) (count + INVALID_POINTER)) * this.mGapWidth);
            if (specMode == Integer.MIN_VALUE) {
                result = Math.min(result, (float) specSize);
            }
        }
        return (int) FloatMath.ceil(result);
    }

    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == Ints.MAX_POWER_OF_TWO) {
            result = (float) specSize;
        } else {
            result = (this.mPaintSelected.getStrokeWidth() + ((float) getPaddingTop())) + ((float) getPaddingBottom());
            if (specMode == Integer.MIN_VALUE) {
                result = Math.min(result, (float) specSize);
            }
        }
        return (int) FloatMath.ceil(result);
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }
}
