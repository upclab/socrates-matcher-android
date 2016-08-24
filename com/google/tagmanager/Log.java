package com.google.tagmanager;

import com.google.android.gms.common.util.VisibleForTesting;
import com.google.tagmanager.Logger.LogLevel;

final class Log {
    @VisibleForTesting
    static Logger sLogger;

    Log() {
    }

    static {
        sLogger = new DefaultLogger();
    }

    public static void setLogger(Logger logger) {
        if (logger == null) {
            sLogger = new NoOpLogger();
        } else {
            sLogger = logger;
        }
    }

    public static Logger getLogger() {
        return sLogger.getClass() == NoOpLogger.class ? null : sLogger;
    }

    public static void m606e(String message) {
        sLogger.m616e(message);
    }

    public static void m607e(String message, Throwable t) {
        sLogger.m617e(message, t);
    }

    public static void m612w(String message) {
        sLogger.m622w(message);
    }

    public static void m613w(String message, Throwable t) {
        sLogger.m623w(message, t);
    }

    public static void m608i(String message) {
        sLogger.m618i(message);
    }

    public static void m609i(String message, Throwable t) {
        sLogger.m619i(message, t);
    }

    public static void m604d(String message) {
        sLogger.m614d(message);
    }

    public static void m605d(String message, Throwable t) {
        sLogger.m615d(message, t);
    }

    public static void m610v(String message) {
        sLogger.m620v(message);
    }

    public static void m611v(String message, Throwable t) {
        sLogger.m621v(message, t);
    }

    public static LogLevel getLogLevel() {
        return sLogger.getLogLevel();
    }
}
