package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.j */
public class C0199j {
    static void m592a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, tileOverlayOptions.m1106i());
        C0109b.m112a(parcel, 2, tileOverlayOptions.bs(), false);
        C0109b.m118a(parcel, 3, tileOverlayOptions.isVisible());
        C0109b.m109a(parcel, 4, tileOverlayOptions.getZIndex());
        C0109b.m106C(parcel, d);
    }
}
