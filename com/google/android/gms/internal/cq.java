package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

public class cq implements SafeParcelable {
    public static final cr CREATOR;
    private final int ab;
    private final ArrayList<C0543x> kA;
    private final Bundle kB;
    private final boolean kC;
    private final int ky;
    private final ArrayList<C0543x> kz;

    static {
        CREATOR = new cr();
    }

    public cq(int i, ArrayList<C0543x> arrayList, ArrayList<C0543x> arrayList2, Bundle bundle, boolean z, int i2) {
        this.ab = i;
        this.kz = arrayList;
        this.kA = arrayList2;
        this.kB = bundle;
        this.kC = z;
        this.ky = i2;
    }

    public int cJ() {
        return this.ky;
    }

    public ArrayList<C0543x> cK() {
        return this.kz;
    }

    public ArrayList<C0543x> cL() {
        return this.kA;
    }

    public Bundle cM() {
        return this.kB;
    }

    public boolean cN() {
        return this.kC;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof cq)) {
            return false;
        }
        cq cqVar = (cq) obj;
        return this.ab == cqVar.ab && C0158r.m509a(this.kz, cqVar.kz) && C0158r.m509a(this.kA, cqVar.kA) && C0158r.m509a(this.kB, cqVar.kB) && C0158r.m509a(Integer.valueOf(this.ky), Integer.valueOf(cqVar.ky));
    }

    public int hashCode() {
        return C0158r.hashCode(Integer.valueOf(this.ab), this.kz, this.kA, this.kB, Integer.valueOf(this.ky));
    }

    public int m959i() {
        return this.ab;
    }

    public void writeToParcel(Parcel out, int flags) {
        cr.m442a(this, out, flags);
    }
}
