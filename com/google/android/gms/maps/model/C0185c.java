package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.c */
public class C0185c {
    static void m572a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, groundOverlayOptions.m1092i());
        C0109b.m112a(parcel, 2, groundOverlayOptions.bp(), false);
        C0109b.m114a(parcel, 3, groundOverlayOptions.getLocation(), i, false);
        C0109b.m109a(parcel, 4, groundOverlayOptions.getWidth());
        C0109b.m109a(parcel, 5, groundOverlayOptions.getHeight());
        C0109b.m114a(parcel, 6, groundOverlayOptions.getBounds(), i, false);
        C0109b.m109a(parcel, 7, groundOverlayOptions.getBearing());
        C0109b.m109a(parcel, 8, groundOverlayOptions.getZIndex());
        C0109b.m118a(parcel, 9, groundOverlayOptions.isVisible());
        C0109b.m109a(parcel, 10, groundOverlayOptions.getTransparency());
        C0109b.m109a(parcel, 11, groundOverlayOptions.getAnchorU());
        C0109b.m109a(parcel, 12, groundOverlayOptions.getAnchorV());
        C0109b.m106C(parcel, d);
    }
}
