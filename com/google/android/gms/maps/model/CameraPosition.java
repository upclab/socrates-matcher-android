package com.google.android.gms.maps.model;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.C0092R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0182q;

public final class CameraPosition implements SafeParcelable {
    public static final CameraPositionCreator CREATOR;
    private final int ab;
    public final float bearing;
    public final LatLng target;
    public final float tilt;
    public final float zoom;

    public static final class Builder {
        private LatLng gR;
        private float gS;
        private float gT;
        private float gU;

        public Builder(CameraPosition previous) {
            this.gR = previous.target;
            this.gS = previous.zoom;
            this.gT = previous.tilt;
            this.gU = previous.bearing;
        }

        public Builder bearing(float bearing) {
            this.gU = bearing;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(this.gR, this.gS, this.gT, this.gU);
        }

        public Builder target(LatLng location) {
            this.gR = location;
            return this;
        }

        public Builder tilt(float tilt) {
            this.gT = tilt;
            return this;
        }

        public Builder zoom(float zoom) {
            this.gS = zoom;
            return this;
        }
    }

    static {
        CREATOR = new CameraPositionCreator();
    }

    CameraPosition(int versionCode, LatLng target, float zoom, float tilt, float bearing) {
        C0159s.m514b((Object) target, (Object) "null camera target");
        boolean z = 0.0f <= tilt && tilt <= 90.0f;
        C0159s.m515b(z, (Object) "Tilt needs to be between 0 and 90 inclusive");
        this.ab = versionCode;
        this.target = target;
        this.zoom = zoom;
        this.tilt = tilt + 0.0f;
        if (((double) bearing) <= 0.0d) {
            bearing = (bearing % 360.0f) + 360.0f;
        }
        this.bearing = bearing % 360.0f;
    }

    public CameraPosition(LatLng target, float zoom, float tilt, float bearing) {
        this(1, target, zoom, tilt, bearing);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition camera) {
        return new Builder(camera);
    }

    public static CameraPosition createFromAttributes(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attrs, C0092R.styleable.MapAttrs);
        LatLng latLng = new LatLng((double) (obtainAttributes.hasValue(2) ? obtainAttributes.getFloat(2, 0.0f) : 0.0f), (double) (obtainAttributes.hasValue(3) ? obtainAttributes.getFloat(3, 0.0f) : 0.0f));
        Builder builder = builder();
        builder.target(latLng);
        if (obtainAttributes.hasValue(5)) {
            builder.zoom(obtainAttributes.getFloat(5, 0.0f));
        }
        if (obtainAttributes.hasValue(1)) {
            builder.bearing(obtainAttributes.getFloat(1, 0.0f));
        }
        if (obtainAttributes.hasValue(4)) {
            builder.tilt(obtainAttributes.getFloat(4, 0.0f));
        }
        return builder.build();
    }

    public static final CameraPosition fromLatLngZoom(LatLng target, float zoom) {
        return new CameraPosition(target, zoom, 0.0f, 0.0f);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) o;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    public int hashCode() {
        return C0158r.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    int m1089i() {
        return this.ab;
    }

    public String toString() {
        return C0158r.m510c(this).m508a("target", this.target).m508a("zoom", Float.valueOf(this.zoom)).m508a("tilt", Float.valueOf(this.tilt)).m508a("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0183a.m570a(this, out, flags);
        } else {
            CameraPositionCreator.m558a(this, out, flags);
        }
    }
}
