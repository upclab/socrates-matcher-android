package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* renamed from: com.google.android.gms.common.data.c */
public class C0466c<T extends SafeParcelable> extends DataBuffer<T> {
    private static final String[] f73X;
    private final Creator<T> f74Y;

    static {
        f73X = new String[]{"data"};
    }

    public C0466c(C0468d c0468d, Creator<T> creator) {
        super(c0468d);
        this.f74Y = creator;
    }

    public T m625d(int i) {
        byte[] e = this.S.m637e("data", i, 0);
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(e, 0, e.length);
        obtain.setDataPosition(0);
        SafeParcelable safeParcelable = (SafeParcelable) this.f74Y.createFromParcel(obtain);
        obtain.recycle();
        return safeParcelable;
    }

    public /* synthetic */ Object get(int x0) {
        return m625d(x0);
    }
}
