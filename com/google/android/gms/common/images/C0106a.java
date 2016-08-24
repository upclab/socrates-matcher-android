package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.internal.C0141f;
import com.google.android.gms.internal.C0142g;
import com.google.android.gms.internal.C0143h;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.as;
import java.lang.ref.WeakReference;

/* renamed from: com.google.android.gms.common.images.a */
public final class C0106a {
    final C0105a aG;
    private int aH;
    private int aI;
    int aJ;
    private int aK;
    private WeakReference<OnImageLoadedListener> aL;
    private WeakReference<ImageView> aM;
    private WeakReference<TextView> aN;
    private int aO;
    private boolean aP;
    private boolean aQ;

    /* renamed from: com.google.android.gms.common.images.a.a */
    public static final class C0105a {
        public final Uri uri;

        public C0105a(Uri uri) {
            this.uri = uri;
        }

        public boolean equals(Object obj) {
            if (obj instanceof C0105a) {
                return this == obj || ((C0105a) obj).hashCode() == hashCode();
            } else {
                return false;
            }
        }

        public int hashCode() {
            return C0158r.hashCode(this.uri);
        }
    }

    public C0106a(int i) {
        this.aH = 0;
        this.aI = 0;
        this.aO = -1;
        this.aP = true;
        this.aQ = false;
        this.aG = new C0105a(null);
        this.aI = i;
    }

    public C0106a(Uri uri) {
        this.aH = 0;
        this.aI = 0;
        this.aO = -1;
        this.aP = true;
        this.aQ = false;
        this.aG = new C0105a(uri);
        this.aI = 0;
    }

    private C0141f m59a(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof C0141f) {
            drawable = ((C0141f) drawable).m455r();
        }
        return new C0141f(drawable, drawable2);
    }

    private void m60a(Drawable drawable, boolean z, boolean z2, boolean z3) {
        switch (this.aJ) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                if (!z2) {
                    OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.aL.get();
                    if (onImageLoadedListener != null) {
                        onImageLoadedListener.onImageLoaded(this.aG.uri, drawable);
                    }
                }
            case Value.STRING_FIELD_NUMBER /*2*/:
                ImageView imageView = (ImageView) this.aM.get();
                if (imageView != null) {
                    m61a(imageView, drawable, z, z2, z3);
                }
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                TextView textView = (TextView) this.aN.get();
                if (textView != null) {
                    m62a(textView, this.aO, drawable, z, z2);
                }
            default:
        }
    }

    private void m61a(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
        Object obj = (z2 || z3) ? null : 1;
        if (obj != null && (imageView instanceof C0142g)) {
            int t = ((C0142g) imageView).m458t();
            if (this.aI != 0 && t == this.aI) {
                return;
            }
        }
        boolean a = m63a(z, z2);
        Drawable a2 = a ? m59a(imageView.getDrawable(), drawable) : drawable;
        imageView.setImageDrawable(a2);
        if (imageView instanceof C0142g) {
            C0142g c0142g = (C0142g) imageView;
            c0142g.m456a(z3 ? this.aG.uri : null);
            c0142g.m457k(obj != null ? this.aI : 0);
        }
        if (a) {
            ((C0141f) a2).startTransition(250);
        }
    }

    private void m62a(TextView textView, int i, Drawable drawable, boolean z, boolean z2) {
        boolean a = m63a(z, z2);
        Drawable[] compoundDrawablesRelative = as.as() ? textView.getCompoundDrawablesRelative() : textView.getCompoundDrawables();
        Drawable a2 = a ? m59a(compoundDrawablesRelative[i], drawable) : drawable;
        Drawable drawable2 = i == 0 ? a2 : compoundDrawablesRelative[0];
        Drawable drawable3 = i == 1 ? a2 : compoundDrawablesRelative[1];
        Drawable drawable4 = i == 2 ? a2 : compoundDrawablesRelative[2];
        Drawable drawable5 = i == 3 ? a2 : compoundDrawablesRelative[3];
        if (as.as()) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
        } else {
            textView.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
        }
        if (a) {
            ((C0141f) a2).startTransition(250);
        }
    }

    private boolean m63a(boolean z, boolean z2) {
        return this.aP && !z2 && (!z || this.aQ);
    }

    void m64a(Context context, Bitmap bitmap, boolean z) {
        C0143h.m461b(bitmap);
        m60a(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    public void m65a(ImageView imageView) {
        C0143h.m461b(imageView);
        this.aL = null;
        this.aM = new WeakReference(imageView);
        this.aN = null;
        this.aO = -1;
        this.aJ = 2;
        this.aK = imageView.hashCode();
    }

    public void m66a(OnImageLoadedListener onImageLoadedListener) {
        C0143h.m461b(onImageLoadedListener);
        this.aL = new WeakReference(onImageLoadedListener);
        this.aM = null;
        this.aN = null;
        this.aO = -1;
        this.aJ = 1;
        this.aK = C0158r.hashCode(onImageLoadedListener, this.aG);
    }

    void m67b(Context context, boolean z) {
        Drawable drawable = null;
        if (this.aI != 0) {
            drawable = context.getResources().getDrawable(this.aI);
        }
        m60a(drawable, z, false, false);
    }

    public boolean equals(Object obj) {
        if (obj instanceof C0106a) {
            return this == obj || ((C0106a) obj).hashCode() == hashCode();
        } else {
            return false;
        }
    }

    void m68f(Context context) {
        Drawable drawable = null;
        if (this.aH != 0) {
            drawable = context.getResources().getDrawable(this.aH);
        }
        m60a(drawable, false, true, false);
    }

    public int hashCode() {
        return this.aK;
    }

    public void m69j(int i) {
        this.aI = i;
    }
}
