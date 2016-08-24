package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

public class DetectedActivityCreator implements Creator<DetectedActivity> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m529a(DetectedActivity detectedActivity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, detectedActivity.fs);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, detectedActivity.m1043i());
        C0109b.m125c(parcel, 2, detectedActivity.ft);
        C0109b.m106C(parcel, d);
    }

    public DetectedActivity createFromParcel(Parcel parcel) {
        int i = 0;
        int c = C0108a.m77c(parcel);
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new DetectedActivity(i3, i2, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public DetectedActivity[] newArray(int size) {
        return new DetectedActivity[size];
    }
}
