package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0915h;
import java.util.HashSet;
import java.util.Set;

public class cm implements Creator<C0915h> {
    static void m438a(C0915h c0915h, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = c0915h.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, c0915h.m1362i());
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m125c(parcel, 3, c0915h.cu());
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m115a(parcel, 4, c0915h.getValue(), true);
        }
        if (bH.contains(Integer.valueOf(5))) {
            C0109b.m115a(parcel, 5, c0915h.getLabel(), true);
        }
        if (bH.contains(Integer.valueOf(6))) {
            C0109b.m125c(parcel, 6, c0915h.getType());
        }
        C0109b.m106C(parcel, d);
    }

    public C0915h m439H(Parcel parcel) {
        String str = null;
        int i = 0;
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i2 = 0;
        String str2 = null;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i3 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str2 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(6));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0915h(hashSet, i3, str2, i2, str, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0915h[] ah(int i) {
        return new C0915h[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m439H(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ah(x0);
    }
}
