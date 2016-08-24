package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0182q;

public final class LatLngBounds implements SafeParcelable {
    public static final LatLngBoundsCreator CREATOR;
    private final int ab;
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double hm;
        private double hn;
        private double ho;
        private double hp;

        public Builder() {
            this.hm = Double.POSITIVE_INFINITY;
            this.hn = Double.NEGATIVE_INFINITY;
            this.ho = Double.NaN;
            this.hp = Double.NaN;
        }

        private boolean m561b(double d) {
            boolean z = false;
            if (this.ho <= this.hp) {
                return this.ho <= d && d <= this.hp;
            } else {
                if (this.ho <= d || d <= this.hp) {
                    z = true;
                }
                return z;
            }
        }

        public LatLngBounds build() {
            C0159s.m512a(!Double.isNaN(this.ho), "no included points");
            return new LatLngBounds(new LatLng(this.hm, this.ho), new LatLng(this.hn, this.hp));
        }

        public Builder include(LatLng point) {
            this.hm = Math.min(this.hm, point.latitude);
            this.hn = Math.max(this.hn, point.latitude);
            double d = point.longitude;
            if (Double.isNaN(this.ho)) {
                this.ho = d;
                this.hp = d;
            } else if (!m561b(d)) {
                if (LatLngBounds.m1095b(this.ho, d) < LatLngBounds.m1097c(this.hp, d)) {
                    this.ho = d;
                } else {
                    this.hp = d;
                }
            }
            return this;
        }
    }

    static {
        CREATOR = new LatLngBoundsCreator();
    }

    LatLngBounds(int versionCode, LatLng southwest, LatLng northeast) {
        C0159s.m514b((Object) southwest, (Object) "null southwest");
        C0159s.m514b((Object) northeast, (Object) "null northeast");
        C0159s.m513a(northeast.latitude >= southwest.latitude, "southern latitude exceeds northern latitude (%s > %s)", Double.valueOf(southwest.latitude), Double.valueOf(northeast.latitude));
        this.ab = versionCode;
        this.southwest = southwest;
        this.northeast = northeast;
    }

    public LatLngBounds(LatLng southwest, LatLng northeast) {
        this(1, southwest, northeast);
    }

    private boolean m1094a(double d) {
        return this.southwest.latitude <= d && d <= this.northeast.latitude;
    }

    private static double m1095b(double d, double d2) {
        return ((d - d2) + 360.0d) % 360.0d;
    }

    private boolean m1096b(double d) {
        boolean z = false;
        if (this.southwest.longitude <= this.northeast.longitude) {
            return this.southwest.longitude <= d && d <= this.northeast.longitude;
        } else {
            if (this.southwest.longitude <= d || d <= this.northeast.longitude) {
                z = true;
            }
            return z;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private static double m1097c(double d, double d2) {
        return ((d2 - d) + 360.0d) % 360.0d;
    }

    public boolean contains(LatLng point) {
        return m1094a(point.latitude) && m1096b(point.longitude);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LatLngBounds)) {
            return false;
        }
        LatLngBounds latLngBounds = (LatLngBounds) o;
        return this.southwest.equals(latLngBounds.southwest) && this.northeast.equals(latLngBounds.northeast);
    }

    public LatLng getCenter() {
        double d = (this.southwest.latitude + this.northeast.latitude) / 2.0d;
        double d2 = this.northeast.longitude;
        double d3 = this.southwest.longitude;
        return new LatLng(d, d3 <= d2 ? (d2 + d3) / 2.0d : ((d2 + 360.0d) + d3) / 2.0d);
    }

    public int hashCode() {
        return C0158r.hashCode(this.southwest, this.northeast);
    }

    int m1100i() {
        return this.ab;
    }

    public LatLngBounds including(LatLng point) {
        double min = Math.min(this.southwest.latitude, point.latitude);
        double max = Math.max(this.northeast.latitude, point.latitude);
        double d = this.northeast.longitude;
        double d2 = this.southwest.longitude;
        double d3 = point.longitude;
        if (m1096b(d3)) {
            d3 = d2;
            d2 = d;
        } else if (m1095b(d2, d3) < m1097c(d, d3)) {
            d2 = d;
        } else {
            double d4 = d2;
            d2 = d3;
            d3 = d4;
        }
        return new LatLngBounds(new LatLng(min, d3), new LatLng(max, d2));
    }

    public String toString() {
        return C0158r.m510c(this).m508a("southwest", this.southwest).m508a("northeast", this.northeast).toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0186d.m573a(this, out, flags);
        } else {
            LatLngBoundsCreator.m562a(this, out, flags);
        }
    }
}
