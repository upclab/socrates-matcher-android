package com.google.analytics.tracking.android;

import com.google.analytics.tracking.android.Logger.LogLevel;
import com.google.android.gms.common.util.VisibleForTesting;

public class Log {
    private static GoogleAnalytics sGaInstance;

    private Log() {
    }

    public static void m1e(String msg) {
        Logger l = getLogger();
        if (l != null) {
            l.error(msg);
        }
    }

    public static void m0e(Exception e) {
        Logger l = getLogger();
        if (l != null) {
            l.error(e);
        }
    }

    public static void m2i(String msg) {
        Logger l = getLogger();
        if (l != null) {
            l.info(msg);
        }
    }

    public static void m3v(String msg) {
        Logger l = getLogger();
        if (l != null) {
            l.verbose(msg);
        }
    }

    public static void m4w(String msg) {
        Logger l = getLogger();
        if (l != null) {
            l.warn(msg);
        }
    }

    public static boolean isVerbose() {
        if (getLogger() != null) {
            return LogLevel.VERBOSE.equals(getLogger().getLogLevel());
        }
        return false;
    }

    private static Logger getLogger() {
        if (sGaInstance == null) {
            sGaInstance = GoogleAnalytics.getInstance();
        }
        if (sGaInstance != null) {
            return sGaInstance.getLogger();
        }
        return null;
    }

    @VisibleForTesting
    static void clearGaInstance() {
        sGaInstance = null;
    }
}
