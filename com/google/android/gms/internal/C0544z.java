package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0128b;

/* renamed from: com.google.android.gms.internal.z */
public class C0544z implements SafeParcelable {
    public static final aa CREATOR;
    private final int ab;
    private final ab cn;

    static {
        CREATOR = new aa();
    }

    C0544z(int i, ab abVar) {
        this.ab = i;
        this.cn = abVar;
    }

    private C0544z(ab abVar) {
        this.ab = 1;
        this.cn = abVar;
    }

    public static C0544z m1037a(C0128b<?, ?> c0128b) {
        if (c0128b instanceof ab) {
            return new C0544z((ab) c0128b);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    ab m1038O() {
        return this.cn;
    }

    public C0128b<?, ?> m1039P() {
        if (this.cn != null) {
            return this.cn;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }

    public int describeContents() {
        aa aaVar = CREATOR;
        return 0;
    }

    int m1040i() {
        return this.ab;
    }

    public void writeToParcel(Parcel out, int flags) {
        aa aaVar = CREATOR;
        aa.m163a(this, out, flags);
    }
}
