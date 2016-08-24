package com.google.android.gms.common;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.C0116e.C0115a;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.internal.C0160u;
import com.google.android.gms.internal.C0542t;

public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int f24O;
    private int f25P;
    private View f26Q;
    private OnClickListener f27R;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attrs) {
        this(context, attrs, SIZE_STANDARD);
    }

    public SignInButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.f27R = null;
        setStyle(SIZE_STANDARD, SIZE_STANDARD);
    }

    private static Button m27c(Context context, int i, int i2) {
        Button c0160u = new C0160u(context);
        c0160u.m522a(context.getResources(), i, i2);
        return c0160u;
    }

    private void m28d(Context context) {
        if (this.f26Q != null) {
            removeView(this.f26Q);
        }
        try {
            this.f26Q = C0542t.m1026d(context, this.f24O, this.f25P);
        } catch (C0115a e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            this.f26Q = m27c(context, this.f24O, this.f25P);
        }
        addView(this.f26Q);
        this.f26Q.setEnabled(isEnabled());
        this.f26Q.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (this.f27R != null && view == this.f26Q) {
            this.f27R.onClick(this);
        }
    }

    public void setColorScheme(int colorScheme) {
        setStyle(this.f24O, colorScheme);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.f26Q.setEnabled(enabled);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.f27R = listener;
        if (this.f26Q != null) {
            this.f26Q.setOnClickListener(this);
        }
    }

    public void setSize(int buttonSize) {
        setStyle(buttonSize, this.f25P);
    }

    public void setStyle(int buttonSize, int colorScheme) {
        boolean z = true;
        boolean z2 = buttonSize >= 0 && buttonSize < 3;
        C0159s.m512a(z2, "Unknown button size " + buttonSize);
        if (colorScheme < 0 || colorScheme >= SIZE_ICON_ONLY) {
            z = false;
        }
        C0159s.m512a(z, "Unknown color scheme " + colorScheme);
        this.f24O = buttonSize;
        this.f25P = colorScheme;
        m28d(getContext());
    }
}
