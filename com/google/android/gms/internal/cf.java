package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0910b;
import com.google.android.gms.internal.cc.C0910b.C0908a;
import com.google.android.gms.internal.cc.C0910b.C0909b;
import java.util.HashSet;
import java.util.Set;

public class cf implements Creator<C0910b> {
    static void m424a(C0910b c0910b, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0910b.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0910b.m1332i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m114a(parcel, 2, c0910b.cl(), i, true);
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m114a(parcel, 3, c0910b.cm(), i, true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m125c(parcel, 4, c0910b.getLayout());
        }
        C0109b.m106C(parcel, d);
    }

    public C0910b m425A(Parcel parcel) {
        C0909b c0909b = null;
        int i = 0;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        C0908a c0908a = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    C0908a c0908a2 = (C0908a) C0108a.m71a(parcel, b, C0908a.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    c0908a = c0908a2;
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    C0909b c0909b2 = (C0909b) C0108a.m71a(parcel, b, C0909b.CREATOR);
                    hashSet.add(Integer.valueOf(3));
                    c0909b = c0909b2;
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(4));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0910b(hashSet, i2, c0908a, c0909b, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0910b[] aa(int i) {
        return new C0910b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m425A(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return aa(x0);
    }
}
