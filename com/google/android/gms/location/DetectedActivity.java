package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class DetectedActivity implements SafeParcelable {
    public static final DetectedActivityCreator CREATOR;
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    private final int ab;
    int fs;
    int ft;

    static {
        CREATOR = new DetectedActivityCreator();
    }

    public DetectedActivity(int activityType, int confidence) {
        this.ab = ON_BICYCLE;
        this.fs = activityType;
        this.ft = confidence;
    }

    public DetectedActivity(int versionCode, int activityType, int confidence) {
        this.ab = versionCode;
        this.fs = activityType;
        this.ft = confidence;
    }

    private int m1042L(int i) {
        return i > TILTING ? UNKNOWN : i;
    }

    public int describeContents() {
        return IN_VEHICLE;
    }

    public int getConfidence() {
        return this.ft;
    }

    public int getType() {
        return m1042L(this.fs);
    }

    public int m1043i() {
        return this.ab;
    }

    public String toString() {
        return "DetectedActivity [type=" + getType() + ", confidence=" + this.ft + "]";
    }

    public void writeToParcel(Parcel out, int flags) {
        DetectedActivityCreator.m529a(this, out, flags);
    }
}
