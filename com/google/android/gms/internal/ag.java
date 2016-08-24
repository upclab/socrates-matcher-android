package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ae.C0489a;
import com.google.android.gms.internal.ah.C0491b;

public class ag implements Creator<C0491b> {
    static void m190a(C0491b c0491b, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0491b.versionCode);
        C0109b.m115a(parcel, 2, c0491b.cH, false);
        C0109b.m114a(parcel, 3, c0491b.cI, i, false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m191j(x0);
    }

    public C0491b m191j(Parcel parcel) {
        C0489a c0489a = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        String str = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    c0489a = (C0489a) C0108a.m71a(parcel, b, C0489a.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0491b(i, str, c0489a);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m192s(x0);
    }

    public C0491b[] m192s(int i) {
        return new C0491b[i];
    }
}
