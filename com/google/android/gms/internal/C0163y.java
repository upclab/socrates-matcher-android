package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.internal.y */
public class C0163y implements Creator<C0543x> {
    static void m525a(C0543x c0543x, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0543x.getType());
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, c0543x.m1036i());
        C0109b.m125c(parcel, 2, c0543x.m1030I());
        C0109b.m115a(parcel, 3, c0543x.m1031J(), false);
        C0109b.m115a(parcel, 4, c0543x.m1032K(), false);
        C0109b.m115a(parcel, 5, c0543x.getDisplayName(), false);
        C0109b.m115a(parcel, 6, c0543x.m1033L(), false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m526e(x0);
    }

    public C0543x m526e(Parcel parcel) {
        int i = 0;
        String str = null;
        int c = C0108a.m77c(parcel);
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str4 = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str3 = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0543x(i3, i2, i, str4, str3, str2, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public C0543x[] m527n(int i) {
        return new C0543x[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m527n(x0);
    }
}
