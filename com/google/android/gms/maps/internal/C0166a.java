package com.google.android.gms.maps.internal;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.time.MutableDateTime;

/* renamed from: com.google.android.gms.maps.internal.a */
public final class C0166a {
    public static Boolean m535a(byte b) {
        switch (b) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return Boolean.FALSE;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return Boolean.TRUE;
            default:
                return null;
        }
    }

    public static byte m536b(Boolean bool) {
        return bool != null ? bool.booleanValue() ? (byte) 1 : (byte) 0 : (byte) -1;
    }
}
