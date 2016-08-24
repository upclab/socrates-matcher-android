package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.maps.internal.C0182q;

public final class VisibleRegion implements SafeParcelable {
    public static final VisibleRegionCreator CREATOR;
    private final int ab;
    public final LatLng farLeft;
    public final LatLng farRight;
    public final LatLngBounds latLngBounds;
    public final LatLng nearLeft;
    public final LatLng nearRight;

    static {
        CREATOR = new VisibleRegionCreator();
    }

    VisibleRegion(int versionCode, LatLng nearLeft, LatLng nearRight, LatLng farLeft, LatLng farRight, LatLngBounds latLngBounds) {
        this.ab = versionCode;
        this.nearLeft = nearLeft;
        this.nearRight = nearRight;
        this.farLeft = farLeft;
        this.farRight = farRight;
        this.latLngBounds = latLngBounds;
    }

    public VisibleRegion(LatLng nearLeft, LatLng nearRight, LatLng farLeft, LatLng farRight, LatLngBounds latLngBounds) {
        this(1, nearLeft, nearRight, farLeft, farRight, latLngBounds);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisibleRegion)) {
            return false;
        }
        VisibleRegion visibleRegion = (VisibleRegion) o;
        return this.nearLeft.equals(visibleRegion.nearLeft) && this.nearRight.equals(visibleRegion.nearRight) && this.farLeft.equals(visibleRegion.farLeft) && this.farRight.equals(visibleRegion.farRight) && this.latLngBounds.equals(visibleRegion.latLngBounds);
    }

    public int hashCode() {
        return C0158r.hashCode(this.nearLeft, this.nearRight, this.farLeft, this.farRight, this.latLngBounds);
    }

    int m1109i() {
        return this.ab;
    }

    public String toString() {
        return C0158r.m510c(this).m508a("nearLeft", this.nearLeft).m508a("nearRight", this.nearRight).m508a("farLeft", this.farLeft).m508a("farRight", this.farRight).m508a("latLngBounds", this.latLngBounds).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0200k.m593a(this, out, flags);
        } else {
            VisibleRegionCreator.m569a(this, out, flags);
        }
    }
}
