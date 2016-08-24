package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ah.C0490a;
import com.google.android.gms.internal.ah.C0491b;
import java.util.ArrayList;

public class aj implements Creator<C0490a> {
    static void m196a(C0490a c0490a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0490a.versionCode);
        C0109b.m115a(parcel, 2, c0490a.className, false);
        C0109b.m124b(parcel, 3, c0490a.cG, false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m197l(x0);
    }

    public C0490a m197l(Parcel parcel) {
        ArrayList arrayList = null;
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
                    arrayList = C0108a.m78c(parcel, b, C0491b.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0490a(i, str, arrayList);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m198u(x0);
    }

    public C0490a[] m198u(int i) {
        return new C0490a[i];
    }
}
