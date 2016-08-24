package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ah.C0490a;
import java.util.ArrayList;

public class ai implements Creator<ah> {
    static void m193a(ah ahVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, ahVar.m695i());
        C0109b.m124b(parcel, 2, ahVar.ai(), false);
        C0109b.m115a(parcel, 3, ahVar.aj(), false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m194k(x0);
    }

    public ah m194k(Parcel parcel) {
        String str = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        ArrayList arrayList = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    arrayList = C0108a.m78c(parcel, b, C0490a.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new ah(i, arrayList, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m195t(x0);
    }

    public ah[] m195t(int i) {
        return new ah[i];
    }
}
