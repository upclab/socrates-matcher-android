package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class CameraPositionCreator implements Creator<CameraPosition> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m558a(CameraPosition cameraPosition, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, cameraPosition.m1089i());
        C0109b.m114a(parcel, 2, cameraPosition.target, i, false);
        C0109b.m109a(parcel, 3, cameraPosition.zoom);
        C0109b.m109a(parcel, 4, cameraPosition.tilt);
        C0109b.m109a(parcel, 5, cameraPosition.bearing);
        C0109b.m106C(parcel, d);
    }

    public CameraPosition createFromParcel(Parcel parcel) {
        float f = 0.0f;
        int c = C0108a.m77c(parcel);
        int i = 0;
        LatLng latLng = null;
        float f2 = 0.0f;
        float f3 = 0.0f;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    latLng = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    f3 = C0108a.m85i(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    f2 = C0108a.m85i(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new CameraPosition(i, latLng, f3, f2, f);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public CameraPosition[] newArray(int size) {
        return new CameraPosition[size];
    }
}
