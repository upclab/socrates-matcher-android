package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0182q;

public final class Tile implements SafeParcelable {
    public static final TileCreator CREATOR;
    private final int ab;
    public final byte[] data;
    public final int height;
    public final int width;

    static {
        CREATOR = new TileCreator();
    }

    Tile(int versionCode, int width, int height, byte[] data) {
        this.ab = versionCode;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public Tile(int width, int height, byte[] data) {
        this(1, width, height, data);
    }

    public int describeContents() {
        return 0;
    }

    int m1104i() {
        return this.ab;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0191i.m578a(this, out, flags);
        } else {
            TileCreator.m567a(this, out, flags);
        }
    }
}
