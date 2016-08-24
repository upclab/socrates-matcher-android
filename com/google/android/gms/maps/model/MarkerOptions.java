package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.internal.C0182q;
import com.jwetherell.augmented_reality.activity.AugmentedReality;

public final class MarkerOptions implements SafeParcelable {
    public static final MarkerOptionsCreator CREATOR;
    private final int ab;
    private boolean hc;
    private float hk;
    private float hl;
    private LatLng hr;
    private String hs;
    private String ht;
    private BitmapDescriptor hu;
    private boolean hv;
    private boolean hw;
    private float hx;
    private float hy;
    private float hz;

    static {
        CREATOR = new MarkerOptionsCreator();
    }

    public MarkerOptions() {
        this.hk = 0.5f;
        this.hl = AugmentedReality.ONE_PERCENT;
        this.hc = true;
        this.hw = false;
        this.hx = 0.0f;
        this.hy = 0.5f;
        this.hz = 0.0f;
        this.ab = 1;
    }

    MarkerOptions(int versionCode, LatLng position, String title, String snippet, IBinder wrappedIcon, float anchorU, float anchorV, boolean draggable, boolean visible, boolean flat, float rotation, float infoWindowAnchorU, float infoWindowAnchorV) {
        this.hk = 0.5f;
        this.hl = AugmentedReality.ONE_PERCENT;
        this.hc = true;
        this.hw = false;
        this.hx = 0.0f;
        this.hy = 0.5f;
        this.hz = 0.0f;
        this.ab = versionCode;
        this.hr = position;
        this.hs = title;
        this.ht = snippet;
        this.hu = wrappedIcon == null ? null : new BitmapDescriptor(C0477a.m657l(wrappedIcon));
        this.hk = anchorU;
        this.hl = anchorV;
        this.hv = draggable;
        this.hc = visible;
        this.hw = flat;
        this.hx = rotation;
        this.hy = infoWindowAnchorU;
        this.hz = infoWindowAnchorV;
    }

    public MarkerOptions anchor(float u, float v) {
        this.hk = u;
        this.hl = v;
        return this;
    }

    IBinder bq() {
        return this.hu == null ? null : this.hu.aW().asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public MarkerOptions draggable(boolean draggable) {
        this.hv = draggable;
        return this;
    }

    public MarkerOptions flat(boolean flat) {
        this.hw = flat;
        return this;
    }

    public float getAnchorU() {
        return this.hk;
    }

    public float getAnchorV() {
        return this.hl;
    }

    public BitmapDescriptor getIcon() {
        return this.hu;
    }

    public float getInfoWindowAnchorU() {
        return this.hy;
    }

    public float getInfoWindowAnchorV() {
        return this.hz;
    }

    public LatLng getPosition() {
        return this.hr;
    }

    public float getRotation() {
        return this.hx;
    }

    public String getSnippet() {
        return this.ht;
    }

    public String getTitle() {
        return this.hs;
    }

    int m1101i() {
        return this.ab;
    }

    public MarkerOptions icon(BitmapDescriptor icon) {
        this.hu = icon;
        return this;
    }

    public MarkerOptions infoWindowAnchor(float u, float v) {
        this.hy = u;
        this.hz = v;
        return this;
    }

    public boolean isDraggable() {
        return this.hv;
    }

    public boolean isFlat() {
        return this.hw;
    }

    public boolean isVisible() {
        return this.hc;
    }

    public MarkerOptions position(LatLng position) {
        this.hr = position;
        return this;
    }

    public MarkerOptions rotation(float rotation) {
        this.hx = rotation;
        return this;
    }

    public MarkerOptions snippet(String snippet) {
        this.ht = snippet;
        return this;
    }

    public MarkerOptions title(String title) {
        this.hs = title;
        return this;
    }

    public MarkerOptions visible(boolean visible) {
        this.hc = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0188f.m575a(this, out, flags);
        } else {
            MarkerOptionsCreator.m564a(this, out, flags);
        }
    }
}
