package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.C0092R;
import com.jwetherell.augmented_reality.ui.Radar;
import org.joda.time.MutableDateTime;

/* renamed from: com.google.android.gms.internal.u */
public final class C0160u extends Button {
    public C0160u(Context context) {
        this(context, null);
    }

    public C0160u(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842824);
    }

    private int m518a(int i, int i2, int i3) {
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return i2;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return i3;
            default:
                throw new IllegalStateException("Unknown color scheme: " + i);
        }
    }

    private void m519b(Resources resources, int i, int i2) {
        int a;
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
            case Value.TYPE_FIELD_NUMBER /*1*/:
                a = m518a(i2, C0092R.drawable.common_signin_btn_text_dark, C0092R.drawable.common_signin_btn_text_light);
                break;
            case Value.STRING_FIELD_NUMBER /*2*/:
                a = m518a(i2, C0092R.drawable.common_signin_btn_icon_dark, C0092R.drawable.common_signin_btn_icon_light);
                break;
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
        if (a == -1) {
            throw new IllegalStateException("Could not find background resource!");
        }
        setBackgroundDrawable(resources.getDrawable(a));
    }

    private void m520c(Resources resources) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        float f = resources.getDisplayMetrics().density;
        setMinHeight((int) ((f * Radar.RADIUS) + 0.5f));
        setMinWidth((int) ((f * Radar.RADIUS) + 0.5f));
    }

    private void m521c(Resources resources, int i, int i2) {
        setTextColor(resources.getColorStateList(m518a(i2, C0092R.color.common_signin_btn_text_dark, C0092R.color.common_signin_btn_text_light)));
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
                setText(resources.getString(C0092R.string.common_signin_button_text));
            case Value.TYPE_FIELD_NUMBER /*1*/:
                setText(resources.getString(C0092R.string.common_signin_button_text_long));
            case Value.STRING_FIELD_NUMBER /*2*/:
                setText(null);
            default:
                throw new IllegalStateException("Unknown button size: " + i);
        }
    }

    public void m522a(Resources resources, int i, int i2) {
        boolean z = true;
        boolean z2 = i >= 0 && i < 3;
        C0159s.m512a(z2, "Unknown button size " + i);
        if (i2 < 0 || i2 >= 2) {
            z = false;
        }
        C0159s.m512a(z, "Unknown color scheme " + i2);
        m520c(resources);
        m519b(resources, i, i2);
        m521c(resources, i, i2);
    }
}
