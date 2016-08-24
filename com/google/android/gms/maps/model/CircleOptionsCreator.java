package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class CircleOptionsCreator implements Creator<CircleOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m559a(CircleOptions circleOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, circleOptions.m1090i());
        C0109b.m114a(parcel, 2, circleOptions.getCenter(), i, false);
        C0109b.m108a(parcel, 3, circleOptions.getRadius());
        C0109b.m109a(parcel, 4, circleOptions.getStrokeWidth());
        C0109b.m125c(parcel, 5, circleOptions.getStrokeColor());
        C0109b.m125c(parcel, 6, circleOptions.getFillColor());
        C0109b.m109a(parcel, 7, circleOptions.getZIndex());
        C0109b.m118a(parcel, 8, circleOptions.isVisible());
        C0109b.m106C(parcel, d);
    }

    public CircleOptions createFromParcel(Parcel parcel) {
        float f = 0.0f;
        boolean z = false;
        int c = C0108a.m77c(parcel);
        LatLng latLng = null;
        double d = 0.0d;
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    latLng = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    d = C0108a.m86j(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    f2 = C0108a.m85i(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new CircleOptions(i3, latLng, d, f2, i2, i, f, z);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public CircleOptions[] newArray(int size) {
        return new CircleOptions[size];
    }
}
