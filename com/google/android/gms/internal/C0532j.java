package com.google.android.gms.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* renamed from: com.google.android.gms.internal.j */
public abstract class C0532j implements SafeParcelable {
    private static final Object bo;
    private static ClassLoader bp;
    private static Integer bq;
    private boolean br;

    static {
        bo = new Object();
        bp = null;
        bq = null;
    }

    public C0532j() {
        this.br = false;
    }

    private static boolean m972a(Class<?> cls) {
        boolean z = false;
        try {
            z = SafeParcelable.NULL.equals(cls.getField("NULL").get(null));
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e2) {
        }
        return z;
    }

    protected static boolean m973h(String str) {
        ClassLoader u = C0532j.m974u();
        if (u == null) {
            return true;
        }
        try {
            return C0532j.m972a(u.loadClass(str));
        } catch (Exception e) {
            return false;
        }
    }

    protected static ClassLoader m974u() {
        ClassLoader classLoader;
        synchronized (bo) {
            classLoader = bp;
        }
        return classLoader;
    }

    protected static Integer m975v() {
        Integer num;
        synchronized (bo) {
            num = bq;
        }
        return num;
    }

    protected boolean m976w() {
        return this.br;
    }
}
