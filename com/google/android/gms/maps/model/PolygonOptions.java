package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0182q;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions implements SafeParcelable {
    public static final PolygonOptionsCreator CREATOR;
    private final int ab;
    private float gY;
    private int gZ;
    private final List<LatLng> hB;
    private final List<List<LatLng>> hC;
    private boolean hD;
    private int ha;
    private float hb;
    private boolean hc;

    static {
        CREATOR = new PolygonOptionsCreator();
    }

    public PolygonOptions() {
        this.gY = AugmentedReality.TEN_PERCENT;
        this.gZ = -16777216;
        this.ha = 0;
        this.hb = 0.0f;
        this.hc = true;
        this.hD = false;
        this.ab = 1;
        this.hB = new ArrayList();
        this.hC = new ArrayList();
    }

    PolygonOptions(int versionCode, List<LatLng> points, List holes, float strokeWidth, int strokeColor, int fillColor, float zIndex, boolean visible, boolean geodesic) {
        this.gY = AugmentedReality.TEN_PERCENT;
        this.gZ = -16777216;
        this.ha = 0;
        this.hb = 0.0f;
        this.hc = true;
        this.hD = false;
        this.ab = versionCode;
        this.hB = points;
        this.hC = holes;
        this.gY = strokeWidth;
        this.gZ = strokeColor;
        this.ha = fillColor;
        this.hb = zIndex;
        this.hc = visible;
        this.hD = geodesic;
    }

    public PolygonOptions add(LatLng point) {
        this.hB.add(point);
        return this;
    }

    public PolygonOptions add(LatLng... points) {
        this.hB.addAll(Arrays.asList(points));
        return this;
    }

    public PolygonOptions addAll(Iterable<LatLng> points) {
        for (LatLng add : points) {
            this.hB.add(add);
        }
        return this;
    }

    public PolygonOptions addHole(Iterable<LatLng> points) {
        ArrayList arrayList = new ArrayList();
        for (LatLng add : points) {
            arrayList.add(add);
        }
        this.hC.add(arrayList);
        return this;
    }

    List br() {
        return this.hC;
    }

    public int describeContents() {
        return 0;
    }

    public PolygonOptions fillColor(int color) {
        this.ha = color;
        return this;
    }

    public PolygonOptions geodesic(boolean geodesic) {
        this.hD = geodesic;
        return this;
    }

    public int getFillColor() {
        return this.ha;
    }

    public List<List<LatLng>> getHoles() {
        return this.hC;
    }

    public List<LatLng> getPoints() {
        return this.hB;
    }

    public int getStrokeColor() {
        return this.gZ;
    }

    public float getStrokeWidth() {
        return this.gY;
    }

    public float getZIndex() {
        return this.hb;
    }

    int m1102i() {
        return this.ab;
    }

    public boolean isGeodesic() {
        return this.hD;
    }

    public boolean isVisible() {
        return this.hc;
    }

    public PolygonOptions strokeColor(int color) {
        this.gZ = color;
        return this;
    }

    public PolygonOptions strokeWidth(float width) {
        this.gY = width;
        return this;
    }

    public PolygonOptions visible(boolean visible) {
        this.hc = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0189g.m576a(this, out, flags);
        } else {
            PolygonOptionsCreator.m565a(this, out, flags);
        }
    }

    public PolygonOptions zIndex(float zIndex) {
        this.hb = zIndex;
        return this;
    }
}
