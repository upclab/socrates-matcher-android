package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.ArrayList;
import org.joda.time.DateTimeConstants;

public class cr implements Creator<cq> {
    static void m442a(cq cqVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, cqVar.m959i());
        C0109b.m124b(parcel, 2, cqVar.cK(), false);
        C0109b.m124b(parcel, 3, cqVar.cL(), false);
        C0109b.m111a(parcel, 4, cqVar.cM(), false);
        C0109b.m118a(parcel, 5, cqVar.cN());
        C0109b.m125c(parcel, 6, cqVar.cJ());
        C0109b.m106C(parcel, d);
    }

    public cq m443J(Parcel parcel) {
        Bundle bundle = null;
        int i = 0;
        int c = C0108a.m77c(parcel);
        boolean z = false;
        ArrayList arrayList = null;
        ArrayList arrayList2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    arrayList2 = C0108a.m78c(parcel, b, C0543x.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    arrayList = C0108a.m78c(parcel, b, C0543x.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    bundle = C0108a.m91n(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new cq(i2, arrayList2, arrayList, bundle, z, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public cq[] aj(int i) {
        return new cq[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m443J(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aj(x0);
    }
}
