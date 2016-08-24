package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* renamed from: com.google.android.gms.internal.x */
public final class C0543x implements SafeParcelable {
    public static final C0163y CREATOR;
    private final int aJ;
    private final int ab;
    private final int ci;
    private final String cj;
    private final String ck;
    private final String cl;
    private final String cm;

    static {
        CREATOR = new C0163y();
    }

    public C0543x(int i, int i2, int i3, String str, String str2, String str3, String str4) {
        this.ab = i;
        this.aJ = i2;
        this.ci = i3;
        this.cj = str;
        this.ck = str2;
        this.cl = str3;
        this.cm = str4;
    }

    public int m1030I() {
        return this.ci;
    }

    public String m1031J() {
        return this.cj;
    }

    public String m1032K() {
        return this.ck;
    }

    public String m1033L() {
        return this.cm;
    }

    public boolean m1034M() {
        return this.aJ == 1 && this.ci == -1;
    }

    public boolean m1035N() {
        return this.aJ == 2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0543x)) {
            return false;
        }
        C0543x c0543x = (C0543x) obj;
        return this.ab == c0543x.ab && this.aJ == c0543x.aJ && this.ci == c0543x.ci && C0158r.m509a(this.cj, c0543x.cj) && C0158r.m509a(this.ck, c0543x.ck);
    }

    public String getDisplayName() {
        return this.cl;
    }

    public int getType() {
        return this.aJ;
    }

    public int hashCode() {
        return C0158r.hashCode(Integer.valueOf(this.ab), Integer.valueOf(this.aJ), Integer.valueOf(this.ci), this.cj, this.ck);
    }

    public int m1036i() {
        return this.ab;
    }

    public String toString() {
        if (m1035N()) {
            return String.format("Person [%s] %s", new Object[]{m1032K(), getDisplayName()});
        } else if (m1034M()) {
            return String.format("Circle [%s] %s", new Object[]{m1031J(), getDisplayName()});
        } else {
            return String.format("Group [%s] %s", new Object[]{m1031J(), getDisplayName()});
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        C0163y.m525a(this, out, flags);
    }
}
