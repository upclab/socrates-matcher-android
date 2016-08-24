package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ab.C0488a;

public class ad implements Creator<C0488a> {
    static void m169a(C0488a c0488a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0488a.versionCode);
        C0109b.m115a(parcel, 2, c0488a.cr, false);
        C0109b.m125c(parcel, 3, c0488a.cs);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m170h(x0);
    }

    public C0488a m170h(Parcel parcel) {
        int i = 0;
        int c = C0108a.m77c(parcel);
        String str = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0488a(i2, str, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m171q(x0);
    }

    public C0488a[] m171q(int i) {
        return new C0488a[i];
    }
}
