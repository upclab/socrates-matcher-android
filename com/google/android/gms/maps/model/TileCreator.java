package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;

public class TileCreator implements Creator<Tile> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m567a(Tile tile, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, tile.m1104i());
        C0109b.m125c(parcel, 2, tile.width);
        C0109b.m125c(parcel, 3, tile.height);
        C0109b.m119a(parcel, 4, tile.data, false);
        C0109b.m106C(parcel, d);
    }

    public Tile createFromParcel(Parcel parcel) {
        int i = 0;
        int c = C0108a.m77c(parcel);
        byte[] bArr = null;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    bArr = C0108a.m92o(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new Tile(i3, i2, i, bArr);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public Tile[] newArray(int size) {
        return new Tile[size];
    }
}
