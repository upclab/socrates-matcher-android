package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.ab.C0488a;
import java.util.ArrayList;

public class ac implements Creator<ab> {
    static void m166a(ab abVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, abVar.m672i());
        C0109b.m124b(parcel, 2, abVar.m666Q(), false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m167g(x0);
    }

    public ab m167g(Parcel parcel) {
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
                    arrayList = C0108a.m78c(parcel, b, C0488a.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new ab(i, arrayList);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m168p(x0);
    }

    public ab[] m168p(int i) {
        return new ab[i];
    }
}
