package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0907a;
import java.util.HashSet;
import java.util.Set;

public class ce implements Creator<C0907a> {
    static void m421a(C0907a c0907a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0907a.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0907a.m1314i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m125c(parcel, 2, c0907a.getMax());
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m125c(parcel, 3, c0907a.getMin());
        }
        C0109b.m106C(parcel, d);
    }

    public C0907a[] m422Z(int i) {
        return new C0907a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m423z(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m422Z(x0);
    }

    public C0907a m423z(Parcel parcel) {
        int i = 0;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i3 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(3));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0907a(hashSet, i3, i2, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
