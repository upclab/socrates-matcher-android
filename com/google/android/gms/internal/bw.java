package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.ArrayList;
import org.joda.time.DateTimeConstants;

public class bw implements Creator<bv> {
    static void m408a(bv bvVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, bvVar.getDescription(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, bvVar.m951i());
        C0109b.m124b(parcel, 2, bvVar.bE(), false);
        C0109b.m124b(parcel, 3, bvVar.bF(), false);
        C0109b.m118a(parcel, 4, bvVar.bG());
        C0109b.m106C(parcel, d);
    }

    public bv[] m409V(int i) {
        return new bv[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m410v(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m409V(x0);
    }

    public bv m410v(Parcel parcel) {
        boolean z = false;
        ArrayList arrayList = null;
        int c = C0108a.m77c(parcel);
        ArrayList arrayList2 = null;
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    arrayList2 = C0108a.m78c(parcel, b, C0543x.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    arrayList = C0108a.m78c(parcel, b, C0543x.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new bv(i, str, arrayList2, arrayList, z);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
