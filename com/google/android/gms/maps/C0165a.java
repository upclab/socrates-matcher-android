package com.google.android.gms.maps;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.a */
public class C0165a {
    static void m534a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, googleMapOptions.m1050i());
        C0109b.m107a(parcel, 2, googleMapOptions.aZ());
        C0109b.m107a(parcel, 3, googleMapOptions.ba());
        C0109b.m125c(parcel, 4, googleMapOptions.getMapType());
        C0109b.m114a(parcel, 5, googleMapOptions.getCamera(), i, false);
        C0109b.m107a(parcel, 6, googleMapOptions.bb());
        C0109b.m107a(parcel, 7, googleMapOptions.bc());
        C0109b.m107a(parcel, 8, googleMapOptions.bd());
        C0109b.m107a(parcel, 9, googleMapOptions.be());
        C0109b.m107a(parcel, 10, googleMapOptions.bf());
        C0109b.m107a(parcel, 11, googleMapOptions.bg());
        C0109b.m106C(parcel, d);
    }
}
