package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.C0109b;

/* renamed from: com.google.android.gms.maps.model.i */
public class C0191i {
    static void m578a(Tile tile, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, tile.m1104i());
        C0109b.m125c(parcel, 2, tile.width);
        C0109b.m125c(parcel, 3, tile.height);
        C0109b.m119a(parcel, 4, tile.data, false);
        C0109b.m106C(parcel, d);
    }
}
