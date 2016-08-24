package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.b */
public class C0184b {
    static void m571a(CircleOptions circleOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, circleOptions.m1090i());
        C0109b.m114a(parcel, 2, circleOptions.getCenter(), i, false);
        C0109b.m108a(parcel, 3, circleOptions.getRadius());
        C0109b.m109a(parcel, 4, circleOptions.getStrokeWidth());
        C0109b.m125c(parcel, 5, circleOptions.getStrokeColor());
        C0109b.m125c(parcel, 6, circleOptions.getFillColor());
        C0109b.m109a(parcel, 7, circleOptions.getZIndex());
        C0109b.m118a(parcel, 8, circleOptions.isVisible());
        C0109b.m106C(parcel, d);
    }
}
