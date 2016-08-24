package com.google.tagmanager;

public interface Logger {

    public enum LogLevel {
        VERBOSE,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        NONE
    }

    void m614d(String str);

    void m615d(String str, Throwable th);

    void m616e(String str);

    void m617e(String str, Throwable th);

    LogLevel getLogLevel();

    void m618i(String str);

    void m619i(String str, Throwable th);

    void setLogLevel(LogLevel logLevel);

    void m620v(String str);

    void m621v(String str, Throwable th);

    void m622w(String str);

    void m623w(String str, Throwable th);
}
