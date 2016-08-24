package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class LatLngCreator implements Creator<LatLng> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m563a(LatLng latLng, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, latLng.m1093i());
        C0109b.m108a(parcel, 2, latLng.latitude);
        C0109b.m108a(parcel, 3, latLng.longitude);
        C0109b.m106C(parcel, d);
    }

    public LatLng createFromParcel(Parcel parcel) {
        double d = 0.0d;
        int c = C0108a.m77c(parcel);
        int i = 0;
        double d2 = 0.0d;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    d2 = C0108a.m86j(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    d = C0108a.m86j(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new LatLng(i, d2, d);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public LatLng[] newArray(int size) {
        return new LatLng[size];
    }
}
