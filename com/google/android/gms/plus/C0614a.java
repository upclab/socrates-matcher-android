package com.google.android.gms.plus;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.C0158r;

/* renamed from: com.google.android.gms.plus.a */
public class C0614a implements SafeParcelable {
    public static final C0204b CREATOR;
    private final int ab;
    private final String f135g;
    private final String[] hY;
    private final String hZ;
    private final String ia;
    private final String ib;
    private final String[] ik;
    private final String[] il;

    static {
        CREATOR = new C0204b();
    }

    public C0614a(int i, String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4) {
        this.ab = i;
        this.f135g = str;
        this.ik = strArr;
        this.il = strArr2;
        this.hY = strArr3;
        this.hZ = str2;
        this.ia = str3;
        this.ib = str4;
    }

    public C0614a(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4) {
        this.ab = 1;
        this.f135g = str;
        this.ik = strArr;
        this.il = strArr2;
        this.hY = strArr3;
        this.hZ = str2;
        this.ia = str3;
        this.ib = str4;
    }

    public String[] bA() {
        return this.hY;
    }

    public String bB() {
        return this.hZ;
    }

    public String bC() {
        return this.ia;
    }

    public String bD() {
        return this.ib;
    }

    public String[] by() {
        return this.ik;
    }

    public String[] bz() {
        return this.il;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0614a)) {
            return false;
        }
        C0614a c0614a = (C0614a) obj;
        return this.ab == c0614a.ab && C0158r.m509a(this.f135g, c0614a.f135g) && C0158r.m509a(this.ik, c0614a.ik) && C0158r.m509a(this.il, c0614a.il) && C0158r.m509a(this.hY, c0614a.hY) && C0158r.m509a(this.hZ, c0614a.hZ) && C0158r.m509a(this.ia, c0614a.ia) && C0158r.m509a(this.ib, c0614a.ib);
    }

    public String getAccountName() {
        return this.f135g;
    }

    public int hashCode() {
        return C0158r.hashCode(Integer.valueOf(this.ab), this.f135g, this.ik, this.il, this.hY, this.hZ, this.ia, this.ib);
    }

    public int m1131i() {
        return this.ab;
    }

    public String toString() {
        return C0158r.m510c(this).m508a("versionCode", Integer.valueOf(this.ab)).m508a("accountName", this.f135g).m508a("requestedScopes", this.ik).m508a("visibleActivities", this.il).m508a("requiredFeatures", this.hY).m508a("packageNameForAuth", this.hZ).m508a("callingPackageName", this.ia).m508a("applicationName", this.ib).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        C0204b.m601a(this, out, flags);
    }
}
