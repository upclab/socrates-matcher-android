package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0159s;

/* renamed from: com.google.android.gms.dynamic.e */
public abstract class C0116e<T> {
    private final String dd;
    private T de;

    /* renamed from: com.google.android.gms.dynamic.e.a */
    public static class C0115a extends Exception {
        public C0115a(String str) {
            super(str);
        }

        public C0115a(String str, Throwable th) {
            super(str, th);
        }
    }

    protected C0116e(String str) {
        this.dd = str;
    }

    protected final T m138h(Context context) throws C0115a {
        if (this.de == null) {
            C0159s.m517d(context);
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                throw new C0115a("Could not get remote context.");
            }
            try {
                this.de = m139k((IBinder) remoteContext.getClassLoader().loadClass(this.dd).newInstance());
            } catch (ClassNotFoundException e) {
                throw new C0115a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new C0115a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new C0115a("Could not access creator.");
            }
        }
        return this.de;
    }

    protected abstract T m139k(IBinder iBinder);
}
