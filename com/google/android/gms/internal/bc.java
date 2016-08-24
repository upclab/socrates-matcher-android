package com.google.android.gms.internal;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.time.MutableDateTime;

public final class bc {
    public static String m333G(int i) {
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return "PUBLIC";
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return "SOCIAL";
            default:
                throw new IllegalArgumentException("Unknown leaderboard collection: " + i);
        }
    }
}
