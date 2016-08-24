package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0910b.C0908a;
import java.util.HashSet;
import java.util.Set;

public class cg implements Creator<C0908a> {
    static void m426a(C0908a c0908a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0908a.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0908a.m1320i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m125c(parcel, 2, c0908a.getLeftImageOffset());
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m125c(parcel, 3, c0908a.getTopImageOffset());
        }
        C0109b.m106C(parcel, d);
    }

    public C0908a m427B(Parcel parcel) {
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
            return new C0908a(hashSet, i3, i2, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0908a[] ab(int i) {
        return new C0908a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m427B(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ab(x0);
    }
}
