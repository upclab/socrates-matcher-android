package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class GroundOverlayOptionsCreator implements Creator<GroundOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m560a(GroundOverlayOptions groundOverlayOptions, Parcel parcel, int i) {
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

    public GroundOverlayOptions createFromParcel(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        int i = 0;
        IBinder iBinder = null;
        LatLng latLng = null;
        float f = 0.0f;
        float f2 = 0.0f;
        LatLngBounds latLngBounds = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        boolean z = false;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    iBinder = C0108a.m90m(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    latLng = (LatLng) C0108a.m71a(parcel, b, LatLng.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    f2 = C0108a.m85i(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    latLngBounds = (LatLngBounds) C0108a.m71a(parcel, b, LatLngBounds.CREATOR);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    f3 = C0108a.m85i(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    f4 = C0108a.m85i(parcel, b);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    f5 = C0108a.m85i(parcel, b);
                    break;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    f6 = C0108a.m85i(parcel, b);
                    break;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    f7 = C0108a.m85i(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new GroundOverlayOptions(i, iBinder, latLng, f, f2, latLngBounds, f3, f4, z, f5, f6, f7);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public GroundOverlayOptions[] newArray(int size) {
        return new GroundOverlayOptions[size];
    }
}
