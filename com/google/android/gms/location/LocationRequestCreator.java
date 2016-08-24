package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.adsdk.sdk.Const;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeConstants;

public class LocationRequestCreator implements Creator<LocationRequest> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m530a(LocationRequest locationRequest, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m125c(parcel, 1, locationRequest.mPriority);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, locationRequest.m1048i());
        C0109b.m110a(parcel, 2, locationRequest.fB);
        C0109b.m110a(parcel, 3, locationRequest.fC);
        C0109b.m118a(parcel, 4, locationRequest.fD);
        C0109b.m110a(parcel, 5, locationRequest.fw);
        C0109b.m125c(parcel, 6, locationRequest.fE);
        C0109b.m109a(parcel, 7, locationRequest.fF);
        C0109b.m106C(parcel, d);
    }

    public LocationRequest createFromParcel(Parcel parcel) {
        boolean z = false;
        int c = C0108a.m77c(parcel);
        int i = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
        long j = DateUtils.MILLIS_PER_HOUR;
        long j2 = Const.CACHE_DOWNLOAD_PERIOD;
        long j3 = Long.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        float f = 0.0f;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    j = C0108a.m83g(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    j2 = C0108a.m83g(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    j3 = C0108a.m83g(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    f = C0108a.m85i(parcel, b);
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
            return new LocationRequest(i3, i, j, j2, z, j3, i2, f);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public LocationRequest[] newArray(int size) {
        return new LocationRequest[size];
    }
}
