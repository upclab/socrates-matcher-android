package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.g */
public class C0189g {
    static void m576a(PolygonOptions polygonOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, polygonOptions.m1102i());
        C0109b.m124b(parcel, 2, polygonOptions.getPoints(), false);
        C0109b.m126c(parcel, 3, polygonOptions.br(), false);
        C0109b.m109a(parcel, 4, polygonOptions.getStrokeWidth());
        C0109b.m125c(parcel, 5, polygonOptions.getStrokeColor());
        C0109b.m125c(parcel, 6, polygonOptions.getFillColor());
        C0109b.m109a(parcel, 7, polygonOptions.getZIndex());
        C0109b.m118a(parcel, 8, polygonOptions.isVisible());
        C0109b.m118a(parcel, 9, polygonOptions.isGeodesic());
        C0109b.m106C(parcel, d);
    }
}
