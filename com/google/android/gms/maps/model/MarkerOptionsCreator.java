package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class MarkerOptionsCreator implements Creator<MarkerOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m564a(MarkerOptions markerOptions, Parcel parcel, int i) {
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
        C0109b.m118a(parcel, 10, markerOptions.isFlat());
        C0109b.m109a(parcel, 11, markerOptions.getRotation());
        C0109b.m109a(parcel, 12, markerOptions.getInfoWindowAnchorU());
        C0109b.m109a(parcel, 13, markerOptions.getInfoWindowAnchorV());
        C0109b.m106C(parcel, d);
    }

    public MarkerOptions createFromParcel(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        int i = 0;
        LatLng latLng = null;
        String str = null;
        String str2 = null;
        IBinder iBinder = null;
        float f = 0.0f;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        float f3 = 0.0f;
        float f4 = 0.5f;
        float f5 = 0.0f;
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
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    iBinder = C0108a.m90m(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    f2 = C0108a.m85i(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    z2 = C0108a.m79c(parcel, b);
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    z3 = C0108a.m79c(parcel, b);
                    break;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    f3 = C0108a.m85i(parcel, b);
                    break;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    f4 = C0108a.m85i(parcel, b);
                    break;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    f5 = C0108a.m85i(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new MarkerOptions(i, latLng, str, str2, iBinder, f, f2, z, z2, z3, f3, f4, f5);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public MarkerOptions[] newArray(int size) {
        return new MarkerOptions[size];
    }
}
