package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.h */
public class C0190h {
    static void m577a(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, polylineOptions.m1103i());
        C0109b.m124b(parcel, 2, polylineOptions.getPoints(), false);
        C0109b.m109a(parcel, 3, polylineOptions.getWidth());
        C0109b.m125c(parcel, 4, polylineOptions.getColor());
        C0109b.m109a(parcel, 5, polylineOptions.getZIndex());
        C0109b.m118a(parcel, 6, polylineOptions.isVisible());
        C0109b.m118a(parcel, 7, polylineOptions.isGeodesic());
        C0109b.m106C(parcel, d);
    }
}
