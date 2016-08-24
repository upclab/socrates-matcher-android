package pe.edu.upc.mobile.Utilities;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;

public class MyHorizontalScrollView extends HorizontalScrollView {

    class MyOnGlobalLayoutListener implements OnGlobalLayoutListener {
        View[] children;
        ViewGroup parent;
        int scrollToViewIdx;
        int scrollToViewPos;
        SizeCallback sizeCallback;

        /* renamed from: pe.edu.upc.mobile.Utilities.MyHorizontalScrollView.MyOnGlobalLayoutListener.1 */
        class C04291 implements Runnable {
            private final /* synthetic */ HorizontalScrollView val$me;

            C04291(HorizontalScrollView horizontalScrollView) {
                this.val$me = horizontalScrollView;
            }

            public void run() {
                this.val$me.scrollBy(MyOnGlobalLayoutListener.this.scrollToViewPos, 0);
            }
        }

        public MyOnGlobalLayoutListener(ViewGroup parent, View[] children, int scrollToViewIdx, SizeCallback sizeCallback) {
            this.scrollToViewPos = 0;
            this.parent = parent;
            this.children = children;
            this.scrollToViewIdx = scrollToViewIdx;
            this.sizeCallback = sizeCallback;
        }

        public void onGlobalLayout() {
            HorizontalScrollView me = MyHorizontalScrollView.this;
            me.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            this.sizeCallback.onGlobalLayout();
            this.parent.removeViewsInLayout(0, this.children.length);
            int w = me.getMeasuredWidth();
            int h = me.getMeasuredHeight();
            int[] dims = new int[2];
            this.scrollToViewPos = 0;
            for (int i = 0; i < this.children.length; i++) {
                this.sizeCallback.getViewSize(i, w, h, dims);
                this.children[i].setVisibility(0);
                this.parent.addView(this.children[i], dims[0], dims[1]);
                if (i < this.scrollToViewIdx) {
                    this.scrollToViewPos += dims[0];
                }
            }
            new Handler().post(new C04291(me));
        }
    }

    public interface SizeCallback {
        void getViewSize(int i, int i2, int i3, int[] iArr);

        void onGlobalLayout();
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    void init(Context context) {
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
    }

    public void initViews(View[] children, int scrollToViewIdx, SizeCallback sizeCallback) {
        ViewGroup parent = (ViewGroup) getChildAt(0);
        for (int i = 0; i < children.length; i++) {
            children[i].setVisibility(4);
            parent.addView(children[i]);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener(parent, children, scrollToViewIdx, sizeCallback));
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
