package com.google.android.gms.plus;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.plus.b */
public class C0204b implements Creator<C0614a> {
    static void m601a(C0614a c0614a, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, c0614a.getAccountName(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, c0614a.m1131i());
        C0109b.m121a(parcel, 2, c0614a.by(), false);
        C0109b.m121a(parcel, 3, c0614a.bz(), false);
        C0109b.m121a(parcel, 4, c0614a.bA(), false);
        C0109b.m115a(parcel, 5, c0614a.bB(), false);
        C0109b.m115a(parcel, 6, c0614a.bC(), false);
        C0109b.m115a(parcel, 7, c0614a.bD(), false);
        C0109b.m106C(parcel, d);
    }

    public C0614a[] m602U(int i) {
        return new C0614a[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m603u(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m602U(x0);
    }

    public C0614a m603u(Parcel parcel) {
        String str = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        String str2 = null;
        String str3 = null;
        String[] strArr = null;
        String[] strArr2 = null;
        String[] strArr3 = null;
        String str4 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str4 = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    strArr3 = C0108a.m100w(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    strArr2 = C0108a.m100w(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    strArr = C0108a.m100w(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str3 = C0108a.m88l(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    str = C0108a.m88l(parcel, b);
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
            return new C0614a(i, str4, strArr3, strArr2, strArr, str3, str2, str);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
