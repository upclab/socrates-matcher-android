package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.maps.model.CameraPosition;

public class GoogleMapOptionsCreator implements Creator<GoogleMapOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m533a(GoogleMapOptions googleMapOptions, Parcel parcel, int i) {
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

    public GoogleMapOptions createFromParcel(Parcel parcel) {
        byte b = (byte) 0;
        int c = C0108a.m77c(parcel);
        CameraPosition cameraPosition = null;
        byte b2 = (byte) 0;
        byte b3 = (byte) 0;
        byte b4 = (byte) 0;
        byte b5 = (byte) 0;
        byte b6 = (byte) 0;
        int i = 0;
        byte b7 = (byte) 0;
        byte b8 = (byte) 0;
        int i2 = 0;
        while (parcel.dataPosition() < c) {
            int b9 = C0108a.m74b(parcel);
            switch (C0108a.m89m(b9)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b9);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    b8 = C0108a.m80d(parcel, b9);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    b7 = C0108a.m80d(parcel, b9);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i = C0108a.m82f(parcel, b9);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    cameraPosition = (CameraPosition) C0108a.m71a(parcel, b9, CameraPosition.CREATOR);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    b6 = C0108a.m80d(parcel, b9);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    b5 = C0108a.m80d(parcel, b9);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    b4 = C0108a.m80d(parcel, b9);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    b3 = C0108a.m80d(parcel, b9);
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    b2 = C0108a.m80d(parcel, b9);
                    break;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    b = C0108a.m80d(parcel, b9);
                    break;
                default:
                    C0108a.m75b(parcel, b9);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new GoogleMapOptions(i2, b8, b7, i, cameraPosition, b6, b5, b4, b3, b2, b);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public GoogleMapOptions[] newArray(int size) {
        return new GoogleMapOptions[size];
    }
}
