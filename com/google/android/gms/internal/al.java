package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class al implements Creator<ak> {
    static void m199a(ak akVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, akVar.m710i());
        C0109b.m113a(parcel, 2, akVar.al(), false);
        C0109b.m114a(parcel, 3, akVar.am(), i, false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m200m(x0);
    }

    public ak m200m(Parcel parcel) {
        ah ahVar = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        Parcel parcel2 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    parcel2 = C0108a.m102y(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    ahVar = (ah) C0108a.m71a(parcel, b, ah.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new ak(i, parcel2, ahVar);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m201v(x0);
    }

    public ak[] m201v(int i) {
        return new ak[i];
    }
}
