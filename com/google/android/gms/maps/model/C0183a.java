package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.a */
public class C0183a {
    static void m570a(CameraPosition cameraPosition, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, cameraPosition.m1089i());
        C0109b.m114a(parcel, 2, cameraPosition.target, i, false);
        C0109b.m109a(parcel, 3, cameraPosition.zoom);
        C0109b.m109a(parcel, 4, cameraPosition.tilt);
        C0109b.m109a(parcel, 5, cameraPosition.bearing);
        C0109b.m106C(parcel, d);
    }
}
