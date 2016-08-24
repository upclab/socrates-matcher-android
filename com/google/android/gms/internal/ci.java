package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0911c;
import java.util.HashSet;
import java.util.Set;

public class ci implements Creator<C0911c> {
    static void m430a(C0911c c0911c, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0911c.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0911c.m1338i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m115a(parcel, 2, c0911c.getUrl(), true);
        }
        C0109b.m106C(parcel, d);
    }

    public C0911c m431D(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0911c(hashSet, i, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0911c[] ad(int i) {
        return new C0911c[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m431D(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ad(x0);
    }
}
