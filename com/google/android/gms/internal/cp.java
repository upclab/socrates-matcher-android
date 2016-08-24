package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.List;
import org.joda.time.DateTimeConstants;

public class cp implements Creator<co> {
    static void m440a(co coVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, coVar.getId(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, coVar.m958i());
        C0109b.m124b(parcel, 2, coVar.cB(), false);
        C0109b.m124b(parcel, 3, coVar.cC(), false);
        C0109b.m114a(parcel, 4, coVar.cD(), i, false);
        C0109b.m115a(parcel, 5, coVar.cE(), false);
        C0109b.m115a(parcel, 6, coVar.cF(), false);
        C0109b.m115a(parcel, 7, coVar.cG(), false);
        C0109b.m111a(parcel, 8, coVar.cH(), false);
        C0109b.m111a(parcel, 9, coVar.cI(), false);
        C0109b.m125c(parcel, 10, coVar.cJ());
        C0109b.m106C(parcel, d);
    }

    public co m441I(Parcel parcel) {
        int i = 0;
        Bundle bundle = null;
        int c = C0108a.m77c(parcel);
        Bundle bundle2 = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        Uri uri = null;
        List list = null;
        List list2 = null;
        String str4 = null;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str4 = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    list2 = C0108a.m78c(parcel, b, C0543x.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    list = C0108a.m78c(parcel, b, Uri.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    uri = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
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
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    bundle2 = C0108a.m91n(parcel, b);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    bundle = C0108a.m91n(parcel, b);
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
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
            return new co(i2, str4, list2, list, uri, str3, str2, str, bundle2, bundle, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public co[] ai(int i) {
        return new co[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m441I(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return ai(x0);
    }
}
