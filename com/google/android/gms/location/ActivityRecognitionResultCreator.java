package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import java.util.List;
import org.joda.time.DateTimeConstants;

public class ActivityRecognitionResultCreator implements Creator<ActivityRecognitionResult> {
    public static final int CONTENT_DESCRIPTION = 0;

    static void m528a(ActivityRecognitionResult activityRecognitionResult, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m124b(parcel, 1, activityRecognitionResult.fp, false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, activityRecognitionResult.m1041i());
        C0109b.m110a(parcel, 2, activityRecognitionResult.fq);
        C0109b.m110a(parcel, 3, activityRecognitionResult.fr);
        C0109b.m106C(parcel, d);
    }

    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        long j = 0;
        int c = C0108a.m77c(parcel);
        int i = 0;
        List list = null;
        long j2 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    list = C0108a.m78c(parcel, b, DetectedActivity.CREATOR);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    j2 = C0108a.m83g(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    j = C0108a.m83g(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new ActivityRecognitionResult(i, list, j2, j);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public ActivityRecognitionResult[] newArray(int size) {
        return new ActivityRecognitionResult[size];
    }
}
