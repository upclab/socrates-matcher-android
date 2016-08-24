package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.k */
public class C0200k {
    static void m593a(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, visibleRegion.m1109i());
        C0109b.m114a(parcel, 2, visibleRegion.nearLeft, i, false);
        C0109b.m114a(parcel, 3, visibleRegion.nearRight, i, false);
        C0109b.m114a(parcel, 4, visibleRegion.farLeft, i, false);
        C0109b.m114a(parcel, 5, visibleRegion.farRight, i, false);
        C0109b.m114a(parcel, 6, visibleRegion.latLngBounds, i, false);
        C0109b.m106C(parcel, d);
    }
}
