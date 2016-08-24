package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class TileOverlayOptionsCreator implements Creator<TileOverlayOptions> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m568a(TileOverlayOptions tileOverlayOptions, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, tileOverlayOptions.m1106i());
        C0109b.m112a(parcel, 2, tileOverlayOptions.bs(), false);
        C0109b.m118a(parcel, 3, tileOverlayOptions.isVisible());
        C0109b.m109a(parcel, 4, tileOverlayOptions.getZIndex());
        C0109b.m106C(parcel, d);
    }

    public TileOverlayOptions createFromParcel(Parcel parcel) {
        boolean z = false;
        int c = C0108a.m77c(parcel);
        IBinder iBinder = null;
        float f = 0.0f;
        int i = 0;
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
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    f = C0108a.m85i(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new TileOverlayOptions(i, iBinder, z, f);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public TileOverlayOptions[] newArray(int size) {
        return new TileOverlayOptions[size];
    }
}
