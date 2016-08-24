package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.f */
public class C0188f {
    static void m575a(MarkerOptions markerOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, markerOptions.m1101i());
        C0109b.m114a(parcel, 2, markerOptions.getPosition(), i, false);
        C0109b.m115a(parcel, 3, markerOptions.getTitle(), false);
        C0109b.m115a(parcel, 4, markerOptions.getSnippet(), false);
        C0109b.m112a(parcel, 5, markerOptions.bq(), false);
        C0109b.m109a(parcel, 6, markerOptions.getAnchorU());
        C0109b.m109a(parcel, 7, markerOptions.getAnchorV());
        C0109b.m118a(parcel, 8, markerOptions.isDraggable());
        C0109b.m118a(parcel, 9, markerOptions.isVisible());
        C0109b.m106C(parcel, d);
    }
}
