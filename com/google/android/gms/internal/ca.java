package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.HashSet;
import java.util.Set;

public class ca implements Creator<bz> {
    static void m414a(bz bzVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = bzVar.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, bzVar.m1297i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m115a(parcel, 2, bzVar.getId(), true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m114a(parcel, 4, bzVar.bY(), i, true);
        }
        if (bH.contains(Integer.valueOf(5))) {
            C0109b.m115a(parcel, 5, bzVar.getStartDate(), true);
        }
        if (bH.contains(Integer.valueOf(6))) {
            C0109b.m114a(parcel, 6, bzVar.bZ(), i, true);
        }
        if (bH.contains(Integer.valueOf(7))) {
            C0109b.m115a(parcel, 7, bzVar.getType(), true);
        }
        C0109b.m106C(parcel, d);
    }

    public bz[] m415X(int i) {
        return new bz[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m416x(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m415X(x0);
    }

    public bz m416x(Parcel parcel) {
        String str = null;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        bx bxVar = null;
        String str2 = null;
        bx bxVar2 = null;
        String str3 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            bx bxVar3;
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str3 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    bxVar3 = (bx) C0108a.m71a(parcel, b, bx.CREATOR);
                    hashSet.add(Integer.valueOf(4));
                    bxVar2 = bxVar3;
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str2 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    bxVar3 = (bx) C0108a.m71a(parcel, b, bx.CREATOR);
                    hashSet.add(Integer.valueOf(6));
                    bxVar = bxVar3;
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
            return new bz(hashSet, i, str3, bxVar2, str2, bxVar, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
