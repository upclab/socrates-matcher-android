package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.internal.C0161v;
import com.google.android.gms.internal.bu;

public final class PlusOneButton extends FrameLayout {
    public static final int ANNOTATION_BUBBLE = 1;
    public static final int ANNOTATION_INLINE = 2;
    public static final int ANNOTATION_NONE = 0;
    public static final int DEFAULT_ACTIVITY_REQUEST_CODE = -1;
    public static final int SIZE_MEDIUM = 1;
    public static final int SIZE_SMALL = 0;
    public static final int SIZE_STANDARD = 3;
    public static final int SIZE_TALL = 2;
    private int f43O;
    private View ic;
    private int id;
    private String ie;
    private int f44if;
    private OnPlusOneClickListener ig;

    public interface OnPlusOneClickListener {
        void onPlusOneClick(Intent intent);
    }

    protected class DefaultOnPlusOneClickListener implements OnClickListener, OnPlusOneClickListener {
        private final OnPlusOneClickListener ih;
        final /* synthetic */ PlusOneButton ii;

        public DefaultOnPlusOneClickListener(PlusOneButton plusOneButton, OnPlusOneClickListener proxy) {
            this.ii = plusOneButton;
            this.ih = proxy;
        }

        public void onClick(View view) {
            Intent intent = (Intent) this.ii.ic.getTag();
            if (this.ih != null) {
                this.ih.onPlusOneClick(intent);
            } else {
                onPlusOneClick(intent);
            }
        }

        public void onPlusOneClick(Intent intent) {
            Context context = this.ii.getContext();
            if ((context instanceof Activity) && intent != null) {
                ((Activity) context).startActivityForResult(intent, this.ii.f44if);
            }
        }
    }

    public PlusOneButton(Context context) {
        this(context, null);
    }

    public PlusOneButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f43O = getSize(context, attrs);
        this.id = getAnnotation(context, attrs);
        this.f44if = DEFAULT_ACTIVITY_REQUEST_CODE;
        m597d(getContext());
        if (!isInEditMode()) {
        }
    }

    private void m597d(Context context) {
        if (this.ic != null) {
            removeView(this.ic);
        }
        this.ic = bu.m405a(context, this.f43O, this.id, this.ie, this.f44if);
        setOnPlusOneClickListener(this.ig);
        addView(this.ic);
    }

    protected static int getAnnotation(Context context, AttributeSet attrs) {
        String a = C0161v.m523a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "annotation", context, attrs, true, false, "PlusOneButton");
        if ("INLINE".equalsIgnoreCase(a)) {
            return SIZE_TALL;
        }
        return !"NONE".equalsIgnoreCase(a) ? SIZE_MEDIUM : SIZE_SMALL;
    }

    protected static int getSize(Context context, AttributeSet attrs) {
        String a = C0161v.m523a("http://schemas.android.com/apk/lib/com.google.android.gms.plus", "size", context, attrs, true, false, "PlusOneButton");
        if ("SMALL".equalsIgnoreCase(a)) {
            return SIZE_SMALL;
        }
        if ("MEDIUM".equalsIgnoreCase(a)) {
            return SIZE_MEDIUM;
        }
        return "TALL".equalsIgnoreCase(a) ? SIZE_TALL : SIZE_STANDARD;
    }

    public void initialize(String url, int activityRequestCode) {
        C0159s.m512a(getContext() instanceof Activity, "To use this method, the PlusOneButton must be placed in an Activity. Use initialize(PlusClient, String, OnPlusOneClickListener).");
        this.ie = url;
        this.f44if = activityRequestCode;
        m597d(getContext());
    }

    public void initialize(String url, OnPlusOneClickListener plusOneClickListener) {
        this.ie = url;
        this.f44if = SIZE_SMALL;
        m597d(getContext());
        setOnPlusOneClickListener(plusOneClickListener);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.ic.layout(SIZE_SMALL, SIZE_SMALL, right - left, bottom - top);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View view = this.ic;
        measureChild(view, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public void setAnnotation(int annotation) {
        this.id = annotation;
        m597d(getContext());
    }

    public void setOnPlusOneClickListener(OnPlusOneClickListener listener) {
        this.ig = listener;
        this.ic.setOnClickListener(new DefaultOnPlusOneClickListener(this, listener));
    }

    public void setSize(int size) {
        this.f43O = size;
        m597d(getContext());
    }
}
