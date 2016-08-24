package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0912d;
import java.util.HashSet;
import java.util.Set;

public class cj implements Creator<C0912d> {
    static void m432a(C0912d c0912d, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0912d.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0912d.m1344i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m115a(parcel, 2, c0912d.getFamilyName(), true);
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m115a(parcel, 3, c0912d.getFormatted(), true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m115a(parcel, 4, c0912d.getGivenName(), true);
        }
        if (bH.contains(Integer.valueOf(5))) {
            C0109b.m115a(parcel, 5, c0912d.getHonorificPrefix(), true);
        }
        if (bH.contains(Integer.valueOf(6))) {
            C0109b.m115a(parcel, 6, c0912d.getHonorificSuffix(), true);
        }
        if (bH.contains(Integer.valueOf(7))) {
            C0109b.m115a(parcel, 7, c0912d.getMiddleName(), true);
        }
        C0109b.m106C(parcel, d);
    }

    public C0912d m433E(Parcel parcel) {
        String str = null;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str6 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str5 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str4 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str3 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str2 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(7));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0912d(hashSet, i, str6, str5, str4, str3, str2, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0912d[] ae(int i) {
        return new C0912d[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m433E(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ae(x0);
    }
}
