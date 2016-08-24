package com.jwetherell.augmented_reality.data;

import android.location.Location;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.jwetherell.augmented_reality.common.Vector;

public class PhysicalLocation {
    private static float[] f56x;
    private static double f57y;
    private static float[] f58z;
    private double altitude;
    private double latitude;
    private double longitude;

    static {
        f56x = new float[1];
        f57y = 0.0d;
        f58z = new float[1];
    }

    public PhysicalLocation() {
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.altitude = 0.0d;
    }

    public PhysicalLocation(PhysicalLocation pl) {
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.altitude = 0.0d;
        if (pl == null) {
            throw new NullPointerException();
        }
        set(pl.latitude, pl.longitude, pl.altitude);
    }

    public void set(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public static synchronized void convLocationToVector(Location org, PhysicalLocation gp, Vector v) {
        synchronized (PhysicalLocation.class) {
            if (org == null || gp == null || v == null) {
                throw new NullPointerException("Location, PhysicalLocation, and Vector cannot be NULL.");
            }
            Location.distanceBetween(org.getLatitude(), org.getLongitude(), gp.getLatitude(), org.getLongitude(), f58z);
            Location.distanceBetween(org.getLatitude(), org.getLongitude(), org.getLatitude(), gp.getLongitude(), f56x);
            f57y = gp.getAltitude() - org.getAltitude();
            if (org.getLatitude() < gp.getLatitude()) {
                float[] fArr = f58z;
                fArr[0] = fArr[0] * GroundOverlayOptions.NO_DIMENSION;
            }
            if (org.getLongitude() > gp.getLongitude()) {
                fArr = f56x;
                fArr[0] = fArr[0] * GroundOverlayOptions.NO_DIMENSION;
            }
            v.set(f56x[0], (float) f57y, f58z[0]);
        }
    }

    public String toString() {
        return "(lat=" + this.latitude + ", lng=" + this.longitude + ", alt=" + this.altitude + ")";
    }
}
