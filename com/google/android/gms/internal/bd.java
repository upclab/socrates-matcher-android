package com.google.android.gms.internal;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.time.MutableDateTime;

public final class bd {
    public static String m334G(int i) {
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return "DAILY";
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return "WEEKLY";
            case Value.STRING_FIELD_NUMBER /*2*/:
                return "ALL_TIME";
            default:
                throw new IllegalArgumentException("Unknown time span " + i);
        }
    }
}
