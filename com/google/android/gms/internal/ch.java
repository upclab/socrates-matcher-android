package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0910b.C0909b;
import java.util.HashSet;
import java.util.Set;

public class ch implements Creator<C0909b> {
    static void m428a(C0909b c0909b, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0909b.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0909b.m1326i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m125c(parcel, 2, c0909b.getHeight());
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m115a(parcel, 3, c0909b.getUrl(), true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m125c(parcel, 4, c0909b.getWidth());
        }
        C0109b.m106C(parcel, d);
    }

    public C0909b m429C(Parcel parcel) {
        int i = 0;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        String str = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i3 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(3));
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
            return new C0909b(hashSet, i3, i2, str, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0909b[] ac(int i) {
        return new C0909b[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m429C(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ac(x0);
    }
}
