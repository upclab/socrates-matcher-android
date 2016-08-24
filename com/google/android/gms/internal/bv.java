package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

public class bv implements SafeParcelable {
    public static final bw CREATOR;
    private final int ab;
    private final String di;
    private final ArrayList<C0543x> iA;
    private final boolean iB;
    private final ArrayList<C0543x> iz;

    static {
        CREATOR = new bw();
    }

    public bv(int i, String str, ArrayList<C0543x> arrayList, ArrayList<C0543x> arrayList2, boolean z) {
        this.ab = i;
        this.di = str;
        this.iz = arrayList;
        this.iA = arrayList2;
        this.iB = z;
    }

    public ArrayList<C0543x> bE() {
        return this.iz;
    }

    public ArrayList<C0543x> bF() {
        return this.iA;
    }

    public boolean bG() {
        return this.iB;
    }

    public int describeContents() {
        return 0;
    }

    public String getDescription() {
        return this.di;
    }

    public int m951i() {
        return this.ab;
    }

    public void writeToParcel(Parcel out, int flags) {
        bw.m408a(this, out, flags);
    }
}
