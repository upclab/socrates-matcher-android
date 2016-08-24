package com.google.android.gms.internal;

import android.os.Build.VERSION;

public final class as {
    public static boolean an() {
        return m215x(11);
    }

    public static boolean ao() {
        return m215x(12);
    }

    public static boolean ap() {
        return m215x(13);
    }

    public static boolean aq() {
        return m215x(14);
    }

    public static boolean ar() {
        return m215x(16);
    }

    public static boolean as() {
        return m215x(17);
    }

    private static boolean m215x(int i) {
        return VERSION.SDK_INT >= i;
    }
}
