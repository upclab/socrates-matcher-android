package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.d */
public class C0186d {
    static void m573a(LatLngBounds latLngBounds, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, latLngBounds.m1100i());
        C0109b.m114a(parcel, 2, latLngBounds.southwest, i, false);
        C0109b.m114a(parcel, 3, latLngBounds.northeast, i, false);
        C0109b.m106C(parcel, d);
    }
}
