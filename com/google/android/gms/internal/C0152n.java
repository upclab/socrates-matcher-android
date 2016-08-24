package com.google.android.gms.internal;

import android.util.Log;

/* renamed from: com.google.android.gms.internal.n */
public final class C0152n {
    private final String bX;

    public C0152n(String str) {
        this.bX = (String) C0159s.m517d(str);
    }

    public void m487a(String str, String str2) {
        if (m492l(3)) {
            Log.d(str, str2);
        }
    }

    public void m488a(String str, String str2, Throwable th) {
        if (m492l(6)) {
            Log.e(str, str2, th);
        }
    }

    public void m489b(String str, String str2) {
        if (m492l(5)) {
            Log.w(str, str2);
        }
    }

    public void m490c(String str, String str2) {
        if (m492l(6)) {
            Log.e(str, str2);
        }
    }

    public void m491d(String str, String str2) {
        if (!m492l(4)) {
        }
    }

    public boolean m492l(int i) {
        return Log.isLoggable(this.bX, i);
    }
}
