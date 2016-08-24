package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.jwetherell.augmented_reality.activity.AugmentedReality;

/* renamed from: com.google.android.gms.internal.f */
public final class C0141f extends Drawable implements Callback {
    private boolean aP;
    private int aR;
    private long aS;
    private int aT;
    private int aU;
    private int aV;
    private int aW;
    private int aX;
    private boolean aY;
    private C0140b aZ;
    private Drawable ba;
    private Drawable bb;
    private boolean bc;
    private boolean bd;
    private boolean be;
    private int bf;

    /* renamed from: com.google.android.gms.internal.f.a */
    private static final class C0139a extends Drawable {
        private static final C0139a bg;
        private static final C0138a bh;

        /* renamed from: com.google.android.gms.internal.f.a.a */
        private static final class C0138a extends ConstantState {
            private C0138a() {
            }

            public int getChangingConfigurations() {
                return 0;
            }

            public Drawable newDrawable() {
                return C0139a.bg;
            }
        }

        static {
            bg = new C0139a();
            bh = new C0138a();
        }

        private C0139a() {
        }

        public void draw(Canvas canvas) {
        }

        public ConstantState getConstantState() {
            return bh;
        }

        public int getOpacity() {
            return -2;
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter cf) {
        }
    }

    /* renamed from: com.google.android.gms.internal.f.b */
    static final class C0140b extends ConstantState {
        int bi;
        int bj;

        C0140b(C0140b c0140b) {
            if (c0140b != null) {
                this.bi = c0140b.bi;
                this.bj = c0140b.bj;
            }
        }

        public int getChangingConfigurations() {
            return this.bi;
        }

        public Drawable newDrawable() {
            return new C0141f(this);
        }
    }

    public C0141f(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = C0139a.bg;
        }
        this.ba = drawable;
        drawable.setCallback(this);
        C0140b c0140b = this.aZ;
        c0140b.bj |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = C0139a.bg;
        }
        this.bb = drawable2;
        drawable2.setCallback(this);
        c0140b = this.aZ;
        c0140b.bj |= drawable2.getChangingConfigurations();
    }

    C0141f(C0140b c0140b) {
        this.aR = 0;
        this.aV = MotionEventCompat.ACTION_MASK;
        this.aX = 0;
        this.aP = true;
        this.aZ = new C0140b(c0140b);
    }

    public boolean canConstantState() {
        if (!this.bc) {
            boolean z = (this.ba.getConstantState() == null || this.bb.getConstantState() == null) ? false : true;
            this.bd = z;
            this.bc = true;
        }
        return this.bd;
    }

    public void draw(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        switch (this.aR) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                this.aS = SystemClock.uptimeMillis();
                this.aR = 2;
                break;
            case Value.STRING_FIELD_NUMBER /*2*/:
                if (this.aS >= 0) {
                    float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.aS)) / ((float) this.aW);
                    if (uptimeMillis < AugmentedReality.ONE_PERCENT) {
                        i = 0;
                    }
                    if (i != 0) {
                        this.aR = 0;
                    }
                    float min = Math.min(uptimeMillis, AugmentedReality.ONE_PERCENT);
                    this.aX = (int) ((min * ((float) (this.aU - this.aT))) + ((float) this.aT));
                    break;
                }
                break;
        }
        i2 = i;
        i = this.aX;
        boolean z = this.aP;
        Drawable drawable = this.ba;
        Drawable drawable2 = this.bb;
        if (i2 != 0) {
            if (!z || i == 0) {
                drawable.draw(canvas);
            }
            if (i == this.aV) {
                drawable2.setAlpha(this.aV);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z) {
            drawable.setAlpha(this.aV - i);
        }
        drawable.draw(canvas);
        if (z) {
            drawable.setAlpha(this.aV);
        }
        if (i > 0) {
            drawable2.setAlpha(i);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.aV);
        }
        invalidateSelf();
    }

    public int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.aZ.bi) | this.aZ.bj;
    }

    public ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.aZ.bi = getChangingConfigurations();
        return this.aZ;
    }

    public int getIntrinsicHeight() {
        return Math.max(this.ba.getIntrinsicHeight(), this.bb.getIntrinsicHeight());
    }

    public int getIntrinsicWidth() {
        return Math.max(this.ba.getIntrinsicWidth(), this.bb.getIntrinsicWidth());
    }

    public int getOpacity() {
        if (!this.be) {
            this.bf = Drawable.resolveOpacity(this.ba.getOpacity(), this.bb.getOpacity());
            this.be = true;
        }
        return this.bf;
    }

    public void invalidateDrawable(Drawable who) {
        if (as.an()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public Drawable mutate() {
        if (!this.aY && super.mutate() == this) {
            if (canConstantState()) {
                this.ba.mutate();
                this.bb.mutate();
                this.aY = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected void onBoundsChange(Rect bounds) {
        this.ba.setBounds(bounds);
        this.bb.setBounds(bounds);
    }

    public Drawable m455r() {
        return this.bb;
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (as.an()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, what, when);
            }
        }
    }

    public void setAlpha(int alpha) {
        if (this.aX == this.aV) {
            this.aX = alpha;
        }
        this.aV = alpha;
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter cf) {
        this.ba.setColorFilter(cf);
        this.bb.setColorFilter(cf);
    }

    public void startTransition(int durationMillis) {
        this.aT = 0;
        this.aU = this.aV;
        this.aX = 0;
        this.aW = durationMillis;
        this.aR = 1;
        invalidateSelf();
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (as.an()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, what);
            }
        }
    }
}
