package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class LatLngBoundsCreator implements Creator<LatLngBounds> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m562a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, latLngBounds.m1100i());
        C0109b.m114a(parcel, 2, latLngBounds.southwest, i, false);
        C0109b.m114a(parcel, 3, latLngBounds.northeast, i, false);
        C0109b.m106C(parcel, d);
    }

    public LatLngBounds createFromParcel(Parcel parcel) {
        LatLng latLng = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        LatLng latLng2 = null;
        while (parcel.dataPosition() < c) {
            int f;
            LatLng latLng3;
            int b = C0108a.m74b(parcel);
            LatLng latLng4;
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    latLng4 = latLng;
                    latLng = latLng2;
                    f = C0108a.m82f(parcel, b);
                    latLng3 = latLng4;
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    f = i;
                    latLng4 = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    latLng3 = latLng;
                    latLng = latLng4;
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    latLng3 = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    latLng = latLng2;
                    f = i;
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    latLng3 = latLng;
                    latLng = latLng2;
                    f = i;
                    break;
            }
            i = f;
            latLng2 = latLng;
            latLng = latLng3;
        }
        if (parcel.dataPosition() == c) {
            return new LatLngBounds(i, latLng2, latLng);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public LatLngBounds[] newArray(int size) {
        return new LatLngBounds[size];
    }
}
