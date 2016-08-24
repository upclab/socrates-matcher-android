package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class aa implements Creator<C0544z> {
    static void m163a(C0544z c0544z, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, c0544z.m1040i());
        C0109b.m114a(parcel, 2, c0544z.m1038O(), i, false);
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m164f(x0);
    }

    public C0544z m164f(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        int i = 0;
        ab abVar = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    abVar = (ab) C0108a.m71a(parcel, b, ab.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new C0544z(i, abVar);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m165o(x0);
    }

    public C0544z[] m165o(int i) {
        return new C0544z[i];
    }
}
