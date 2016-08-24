package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ae.C0489a;

public class af implements Creator<C0489a> {
    static void m187a(C0489a c0489a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0489a.m690i());
        C0109b.m125c(parcel, 2, c0489a.m682R());
        C0109b.m118a(parcel, 3, c0489a.m685X());
        C0109b.m125c(parcel, 4, c0489a.m683S());
        C0109b.m118a(parcel, 5, c0489a.m686Y());
        C0109b.m115a(parcel, 6, c0489a.m687Z(), false);
        C0109b.m125c(parcel, 7, c0489a.aa());
        C0109b.m115a(parcel, 8, c0489a.ac(), false);
        C0109b.m114a(parcel, 9, c0489a.ae(), i, false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m188i(x0);
    }

    public C0489a m188i(Parcel parcel) {
        C0544z c0544z = null;
        int i = 0;
        int c = C0108a.m77c(parcel);
        String str = null;
        String str2 = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i4 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    z2 = C0108a.m79c(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    c0544z = (C0544z) C0108a.m71a(parcel, b, C0544z.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0489a(i4, i3, z2, i2, z, str2, i, str, c0544z);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m189r(x0);
    }

    public C0489a[] m189r(int i) {
        return new C0489a[i];
    }
}
