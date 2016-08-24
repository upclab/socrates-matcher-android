package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0182q;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions implements SafeParcelable {
    public static final PolylineOptionsCreator CREATOR;
    private int f126P;
    private final int ab;
    private final List<LatLng> hB;
    private boolean hD;
    private float hb;
    private boolean hc;
    private float hg;

    static {
        CREATOR = new PolylineOptionsCreator();
    }

    public PolylineOptions() {
        this.hg = AugmentedReality.TEN_PERCENT;
        this.f126P = -16777216;
        this.hb = 0.0f;
        this.hc = true;
        this.hD = false;
        this.ab = 1;
        this.hB = new ArrayList();
    }

    PolylineOptions(int versionCode, List points, float width, int color, float zIndex, boolean visible, boolean geodesic) {
        this.hg = AugmentedReality.TEN_PERCENT;
        this.f126P = -16777216;
        this.hb = 0.0f;
        this.hc = true;
        this.hD = false;
        this.ab = versionCode;
        this.hB = points;
        this.hg = width;
        this.f126P = color;
        this.hb = zIndex;
        this.hc = visible;
        this.hD = geodesic;
    }

    public PolylineOptions add(LatLng point) {
        this.hB.add(point);
        return this;
    }

    public PolylineOptions add(LatLng... points) {
        this.hB.addAll(Arrays.asList(points));
        return this;
    }

    public PolylineOptions addAll(Iterable<LatLng> points) {
        for (LatLng add : points) {
            this.hB.add(add);
        }
        return this;
    }

    public PolylineOptions color(int color) {
        this.f126P = color;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public PolylineOptions geodesic(boolean geodesic) {
        this.hD = geodesic;
        return this;
    }

    public int getColor() {
        return this.f126P;
    }

    public List<LatLng> getPoints() {
        return this.hB;
    }

    public float getWidth() {
        return this.hg;
    }

    public float getZIndex() {
        return this.hb;
    }

    int m1103i() {
        return this.ab;
    }

    public boolean isGeodesic() {
        return this.hD;
    }

    public boolean isVisible() {
        return this.hc;
    }

    public PolylineOptions visible(boolean visible) {
        this.hc = visible;
        return this;
    }

    public PolylineOptions width(float width) {
        this.hg = width;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0190h.m577a(this, out, flags);
        } else {
            PolylineOptionsCreator.m566a(this, out, flags);
        }
    }

    public PolylineOptions zIndex(float zIndex) {
        this.hb = zIndex;
        return this;
    }
}
