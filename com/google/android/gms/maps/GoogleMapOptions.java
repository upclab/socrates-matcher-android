package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C0092R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0166a;
import com.google.android.gms.maps.internal.C0182q;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions implements SafeParcelable {
    public static final GoogleMapOptionsCreator CREATOR;
    private final int ab;
    private Boolean go;
    private Boolean gp;
    private int gq;
    private CameraPosition gr;
    private Boolean gs;
    private Boolean gt;
    private Boolean gu;
    private Boolean gv;
    private Boolean gw;
    private Boolean gx;

    static {
        CREATOR = new GoogleMapOptionsCreator();
    }

    public GoogleMapOptions() {
        this.gq = -1;
        this.ab = 1;
    }

    GoogleMapOptions(int versionCode, byte zOrderOnTop, byte useViewLifecycleInFragment, int mapType, CameraPosition camera, byte zoomControlsEnabled, byte compassEnabled, byte scrollGesturesEnabled, byte zoomGesturesEnabled, byte tiltGesturesEnabled, byte rotateGesturesEnabled) {
        this.gq = -1;
        this.ab = versionCode;
        this.go = C0166a.m535a(zOrderOnTop);
        this.gp = C0166a.m535a(useViewLifecycleInFragment);
        this.gq = mapType;
        this.gr = camera;
        this.gs = C0166a.m535a(zoomControlsEnabled);
        this.gt = C0166a.m535a(compassEnabled);
        this.gu = C0166a.m535a(scrollGesturesEnabled);
        this.gv = C0166a.m535a(zoomGesturesEnabled);
        this.gw = C0166a.m535a(tiltGesturesEnabled);
        this.gx = C0166a.m535a(rotateGesturesEnabled);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attrs, C0092R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(0)) {
            googleMapOptions.mapType(obtainAttributes.getInt(0, -1));
        }
        if (obtainAttributes.hasValue(13)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(13, false));
        }
        if (obtainAttributes.hasValue(12)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(12, false));
        }
        if (obtainAttributes.hasValue(6)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(6, true));
        }
        if (obtainAttributes.hasValue(7)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(7, true));
        }
        if (obtainAttributes.hasValue(8)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(8, true));
        }
        if (obtainAttributes.hasValue(9)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(9, true));
        }
        if (obtainAttributes.hasValue(11)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(11, true));
        }
        if (obtainAttributes.hasValue(10)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(10, true));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attrs));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    byte aZ() {
        return C0166a.m536b(this.go);
    }

    byte ba() {
        return C0166a.m536b(this.gp);
    }

    byte bb() {
        return C0166a.m536b(this.gs);
    }

    byte bc() {
        return C0166a.m536b(this.gt);
    }

    byte bd() {
        return C0166a.m536b(this.gu);
    }

    byte be() {
        return C0166a.m536b(this.gv);
    }

    byte bf() {
        return C0166a.m536b(this.gw);
    }

    byte bg() {
        return C0166a.m536b(this.gx);
    }

    public GoogleMapOptions camera(CameraPosition camera) {
        this.gr = camera;
        return this;
    }

    public GoogleMapOptions compassEnabled(boolean enabled) {
        this.gt = Boolean.valueOf(enabled);
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public CameraPosition getCamera() {
        return this.gr;
    }

    public Boolean getCompassEnabled() {
        return this.gt;
    }

    public int getMapType() {
        return this.gq;
    }

    public Boolean getRotateGesturesEnabled() {
        return this.gx;
    }

    public Boolean getScrollGesturesEnabled() {
        return this.gu;
    }

    public Boolean getTiltGesturesEnabled() {
        return this.gw;
    }

    public Boolean getUseViewLifecycleInFragment() {
        return this.gp;
    }

    public Boolean getZOrderOnTop() {
        return this.go;
    }

    public Boolean getZoomControlsEnabled() {
        return this.gs;
    }

    public Boolean getZoomGesturesEnabled() {
        return this.gv;
    }

    int m1050i() {
        return this.ab;
    }

    public GoogleMapOptions mapType(int mapType) {
        this.gq = mapType;
        return this;
    }

    public GoogleMapOptions rotateGesturesEnabled(boolean enabled) {
        this.gx = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions scrollGesturesEnabled(boolean enabled) {
        this.gu = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions tiltGesturesEnabled(boolean enabled) {
        this.gw = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions useViewLifecycleInFragment(boolean useViewLifecycleInFragment) {
        this.gp = Boolean.valueOf(useViewLifecycleInFragment);
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0165a.m534a(this, out, flags);
        } else {
            GoogleMapOptionsCreator.m533a(this, out, flags);
        }
    }

    public GoogleMapOptions zOrderOnTop(boolean zOrderOnTop) {
        this.go = Boolean.valueOf(zOrderOnTop);
        return this;
    }

    public GoogleMapOptions zoomControlsEnabled(boolean enabled) {
        this.gs = Boolean.valueOf(enabled);
        return this;
    }

    public GoogleMapOptions zoomGesturesEnabled(boolean enabled) {
        this.gv = Boolean.valueOf(enabled);
        return this;
    }
}
