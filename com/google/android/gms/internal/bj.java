package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

public class bj implements Creator<bi> {
    static void m344a(bi biVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, biVar.getRequestId(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, biVar.m881i());
        C0109b.m110a(parcel, 2, biVar.getExpirationTime());
        C0109b.m117a(parcel, 3, biVar.aT());
        C0109b.m108a(parcel, 4, biVar.getLatitude());
        C0109b.m108a(parcel, 5, biVar.getLongitude());
        C0109b.m109a(parcel, 6, biVar.aU());
        C0109b.m125c(parcel, 7, biVar.aV());
        C0109b.m106C(parcel, d);
    }

    public bi[] m345R(int i) {
        return new bi[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m346t(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m345R(x0);
    }

    public bi m346t(Parcel parcel) {
        double d = 0.0d;
        short s = (short) 0;
        int c = C0108a.m77c(parcel);
        String str = null;
        float f = 0.0f;
        long j = 0;
        double d2 = 0.0d;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    j = C0108a.m83g(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    s = C0108a.m81e(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    d2 = C0108a.m86j(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    d = C0108a.m86j(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
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
            return new bi(i2, str, i, s, d2, d, f, j);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
