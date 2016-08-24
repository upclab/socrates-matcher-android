package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.List;

public class PolylineOptionsCreator implements Creator<PolylineOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m566a(PolylineOptions polylineOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, polylineOptions.m1103i());
        C0109b.m124b(parcel, 2, polylineOptions.getPoints(), false);
        C0109b.m109a(parcel, 3, polylineOptions.getWidth());
        C0109b.m125c(parcel, 4, polylineOptions.getColor());
        C0109b.m109a(parcel, 5, polylineOptions.getZIndex());
        C0109b.m118a(parcel, 6, polylineOptions.isVisible());
        C0109b.m118a(parcel, 7, polylineOptions.isGeodesic());
        C0109b.m106C(parcel, d);
    }

    public PolylineOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int c = C0108a.m77c(parcel);
        List list = null;
        boolean z2 = false;
        int i = 0;
        float f2 = 0.0f;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    list = C0108a.m78c(parcel, b, LatLng.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    f2 = C0108a.m85i(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    z2 = C0108a.m79c(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new PolylineOptions(i2, list, f2, i, f, z2, z);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public PolylineOptions[] newArray(int size) {
        return new PolylineOptions[size];
    }
}
