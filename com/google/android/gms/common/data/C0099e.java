package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.common.data.e */
public class C0099e implements Creator<C0468d> {
    static void m35a(C0468d c0468d, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m121a(parcel, 1, c0468d.m642j(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, c0468d.m641i());
        C0109b.m120a(parcel, 2, c0468d.m643k(), i, false);
        C0109b.m125c(parcel, 3, c0468d.getStatusCode());
        C0109b.m111a(parcel, 4, c0468d.m644l(), false);
        C0109b.m106C(parcel, d);
    }

    public C0468d m36a(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int c = C0108a.m77c(parcel);
        CursorWindow[] cursorWindowArr = null;
        String[] strArr = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    strArr = C0108a.m100w(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    cursorWindowArr = (CursorWindow[]) C0108a.m76b(parcel, b, CursorWindow.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    bundle = C0108a.m91n(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() != c) {
            throw new C0107a("Overread allowed size end=" + c, parcel);
        }
        C0468d c0468d = new C0468d(i2, strArr, cursorWindowArr, i, bundle);
        c0468d.m640h();
        return c0468d;
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m36a(x0);
    }

    public C0468d[] m37g(int i) {
        return new C0468d[i];
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m37g(x0);
    }
}
