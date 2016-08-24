package com.google.android.gms.internal;

import android.os.Looper;
import android.util.Log;

/* renamed from: com.google.android.gms.internal.h */
public final class C0143h {
    public static void m459a(boolean z) {
        if (!z) {
            throw new IllegalStateException();
        }
    }

    public static void m460a(boolean z, Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void m461b(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("null reference");
        }
    }

    public static void m462f(String str) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            Log.e("Asserts", "checkMainThread: current thread " + Thread.currentThread() + " IS NOT the main thread " + Looper.getMainLooper().getThread() + "!");
            throw new IllegalStateException(str);
        }
    }

    public static void m463g(String str) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            Log.e("Asserts", "checkNotMainThread: current thread " + Thread.currentThread() + " IS the main thread " + Looper.getMainLooper().getThread() + "!");
            throw new IllegalStateException(str);
        }
    }
}
