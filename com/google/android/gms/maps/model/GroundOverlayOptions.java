package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0182q;
import com.jwetherell.augmented_reality.activity.AugmentedReality;

public final class GroundOverlayOptions implements SafeParcelable {
    public static final GroundOverlayOptionsCreator CREATOR;
    public static final float NO_DIMENSION = -1.0f;
    private final int ab;
    private float gU;
    private float hb;
    private boolean hc;
    private BitmapDescriptor he;
    private LatLng hf;
    private float hg;
    private float hh;
    private LatLngBounds hi;
    private float hj;
    private float hk;
    private float hl;

    static {
        CREATOR = new GroundOverlayOptionsCreator();
    }

    public GroundOverlayOptions() {
        this.hc = true;
        this.hj = 0.0f;
        this.hk = 0.5f;
        this.hl = 0.5f;
        this.ab = 1;
    }

    GroundOverlayOptions(int versionCode, IBinder wrappedImage, LatLng location, float width, float height, LatLngBounds bounds, float bearing, float zIndex, boolean visible, float transparency, float anchorU, float anchorV) {
        this.hc = true;
        this.hj = 0.0f;
        this.hk = 0.5f;
        this.hl = 0.5f;
        this.ab = versionCode;
        this.he = new BitmapDescriptor(C0477a.m657l(wrappedImage));
        this.hf = location;
        this.hg = width;
        this.hh = height;
        this.hi = bounds;
        this.gU = bearing;
        this.hb = zIndex;
        this.hc = visible;
        this.hj = transparency;
        this.hk = anchorU;
        this.hl = anchorV;
    }

    private GroundOverlayOptions m1091a(LatLng latLng, float f, float f2) {
        this.hf = latLng;
        this.hg = f;
        this.hh = f2;
        return this;
    }

    public GroundOverlayOptions anchor(float u, float v) {
        this.hk = u;
        this.hl = v;
        return this;
    }

    public GroundOverlayOptions bearing(float bearing) {
        this.gU = ((bearing % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    IBinder bp() {
        return this.he.aW().asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public float getAnchorU() {
        return this.hk;
    }

    public float getAnchorV() {
        return this.hl;
    }

    public float getBearing() {
        return this.gU;
    }

    public LatLngBounds getBounds() {
        return this.hi;
    }

    public float getHeight() {
        return this.hh;
    }

    public BitmapDescriptor getImage() {
        return this.he;
    }

    public LatLng getLocation() {
        return this.hf;
    }

    public float getTransparency() {
        return this.hj;
    }

    public float getWidth() {
        return this.hg;
    }

    public float getZIndex() {
        return this.hb;
    }

    int m1092i() {
        return this.ab;
    }

    public GroundOverlayOptions image(BitmapDescriptor image) {
        this.he = image;
        return this;
    }

    public boolean isVisible() {
        return this.hc;
    }

    public GroundOverlayOptions position(LatLng location, float width) {
        boolean z = true;
        C0159s.m512a(this.hi == null, "Position has already been set using positionFromBounds");
        C0159s.m515b(location != null, (Object) "Location must be specified");
        if (width < 0.0f) {
            z = false;
        }
        C0159s.m515b(z, (Object) "Width must be non-negative");
        return m1091a(location, width, NO_DIMENSION);
    }

    public GroundOverlayOptions position(LatLng location, float width, float height) {
        boolean z = true;
        C0159s.m512a(this.hi == null, "Position has already been set using positionFromBounds");
        C0159s.m515b(location != null, (Object) "Location must be specified");
        C0159s.m515b(width >= 0.0f, (Object) "Width must be non-negative");
        if (height < 0.0f) {
            z = false;
        }
        C0159s.m515b(z, (Object) "Height must be non-negative");
        return m1091a(location, width, height);
    }

    public GroundOverlayOptions positionFromBounds(LatLngBounds bounds) {
        C0159s.m512a(this.hf == null, "Position has already been set using position: " + this.hf);
        this.hi = bounds;
        return this;
    }

    public GroundOverlayOptions transparency(float transparency) {
        boolean z = transparency >= 0.0f && transparency <= AugmentedReality.ONE_PERCENT;
        C0159s.m515b(z, (Object) "Transparency must be in the range [0..1]");
        this.hj = transparency;
        return this;
    }

    public GroundOverlayOptions visible(boolean visible) {
        this.hc = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0185c.m572a(this, out, flags);
        } else {
            GroundOverlayOptionsCreator.m560a(this, out, flags);
        }
    }

    public GroundOverlayOptions zIndex(float zIndex) {
        this.hb = zIndex;
        return this;
    }
}
