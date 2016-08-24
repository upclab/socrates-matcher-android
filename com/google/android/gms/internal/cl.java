package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0914g;
import java.util.HashSet;
import java.util.Set;

public class cl implements Creator<C0914g> {
    static void m436a(C0914g c0914g, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0914g.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0914g.m1356i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m118a(parcel, 2, c0914g.isPrimary());
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m115a(parcel, 3, c0914g.getValue(), true);
        }
        C0109b.m106C(parcel, d);
    }

    public C0914g m437G(Parcel parcel) {
        boolean z = false;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    z = C0108a.m79c(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(3));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0914g(hashSet, i, z, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0914g[] ag(int i) {
        return new C0914g[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m437G(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ag(x0);
    }
}
