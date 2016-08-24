package com.google.android.gms.internal;

/* renamed from: com.google.android.gms.internal.s */
public final class C0159s {
    public static void m511a(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void m512a(boolean z, Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void m513a(boolean z, String str, Object... objArr) {
        if (!z) {
            throw new IllegalArgumentException(String.format(str, objArr));
        }
    }

    public static <T> T m514b(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }

    public static void m515b(boolean z, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    public static void m516c(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T m517d(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null reference");
    }
}
