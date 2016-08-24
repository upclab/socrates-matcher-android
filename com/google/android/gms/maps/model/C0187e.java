package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.e */
public class C0187e {
    static void m574a(LatLng latLng, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, latLng.m1093i());
        C0109b.m108a(parcel, 2, latLng.latitude);
        C0109b.m108a(parcel, 3, latLng.longitude);
        C0109b.m106C(parcel, d);
    }
}
