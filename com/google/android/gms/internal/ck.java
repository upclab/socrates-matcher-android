package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0913f;
import java.util.HashSet;
import java.util.Set;

public class ck implements Creator<C0913f> {
    static void m434a(C0913f c0913f, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0913f.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0913f.m1350i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m115a(parcel, 2, c0913f.getDepartment(), true);
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m115a(parcel, 3, c0913f.getDescription(), true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m115a(parcel, 4, c0913f.getEndDate(), true);
        }
        if (bH.contains(Integer.valueOf(5))) {
            C0109b.m115a(parcel, 5, c0913f.getLocation(), true);
        }
        if (bH.contains(Integer.valueOf(6))) {
            C0109b.m115a(parcel, 6, c0913f.getName(), true);
        }
        if (bH.contains(Integer.valueOf(7))) {
            C0109b.m118a(parcel, 7, c0913f.isPrimary());
        }
        if (bH.contains(Integer.valueOf(8))) {
            C0109b.m115a(parcel, 8, c0913f.getStartDate(), true);
        }
        if (bH.contains(Integer.valueOf(9))) {
            C0109b.m115a(parcel, 9, c0913f.getTitle(), true);
        }
        if (bH.contains(Integer.valueOf(10))) {
            C0109b.m125c(parcel, 10, c0913f.getType());
        }
        C0109b.m106C(parcel, d);
    }

    public C0913f m435F(Parcel parcel) {
        int i = 0;
        String str = null;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        String str2 = null;
        boolean z = false;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        String str7 = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str7 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str6 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str5 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str4 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str3 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    z = C0108a.m79c(parcel, b);
                    hashSet.add(Integer.valueOf(7));
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    str2 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(8));
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(9));
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(10));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0913f(hashSet, i2, str7, str6, str5, str4, str3, z, str2, str, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0913f[] af(int i) {
        return new C0913f[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m435F(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return af(x0);
    }
}
