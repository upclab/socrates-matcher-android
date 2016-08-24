package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class VisibleRegionCreator implements Creator<VisibleRegion> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m569a(VisibleRegion visibleRegion, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, visibleRegion.m1109i());
        C0109b.m114a(parcel, 2, visibleRegion.nearLeft, i, false);
        C0109b.m114a(parcel, 3, visibleRegion.nearRight, i, false);
        C0109b.m114a(parcel, 4, visibleRegion.farLeft, i, false);
        C0109b.m114a(parcel, 5, visibleRegion.farRight, i, false);
        C0109b.m114a(parcel, 6, visibleRegion.latLngBounds, i, false);
        C0109b.m106C(parcel, d);
    }

    public VisibleRegion createFromParcel(Parcel parcel) {
        LatLngBounds latLngBounds = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        LatLng latLng = null;
        LatLng latLng2 = null;
        LatLng latLng3 = null;
        LatLng latLng4 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    latLng4 = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    latLng3 = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    latLng2 = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    latLng = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    latLngBounds = (LatLngBounds) C0108a.m71a(parcel, b, LatLngBounds.CREATOR);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new VisibleRegion(i, latLng4, latLng3, latLng2, latLng, latLngBounds);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public VisibleRegion[] newArray(int size) {
        return new VisibleRegion[size];
    }
}
