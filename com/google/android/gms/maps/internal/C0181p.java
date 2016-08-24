package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0168c.C0571a;
import com.google.android.gms.maps.model.RuntimeRemoteException;

/* renamed from: com.google.android.gms.maps.internal.p */
public class C0181p {
    private static Context gN;
    private static C0168c gO;

    private static <T> T m551a(ClassLoader classLoader, String str) {
        try {
            return C0181p.m552c(((ClassLoader) C0159s.m517d(classLoader)).loadClass(str));
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find dynamic class " + str);
        }
    }

    private static Class<?> bm() {
        try {
            return Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static <T> T m552c(Class<?> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("Unable to instantiate the dynamic class " + cls.getName());
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Unable to call the default constructor of " + cls.getName());
        }
    }

    private static Context getRemoteContext(Context context) {
        if (gN == null) {
            if (C0181p.bm() != null) {
                gN = context;
            } else {
                gN = GooglePlayServicesUtil.getRemoteContext(context);
            }
        }
        return gN;
    }

    public static C0168c m553i(Context context) throws GooglePlayServicesNotAvailableException {
        C0159s.m517d(context);
        C0181p.m555k(context);
        if (gO == null) {
            C0181p.m556l(context);
        }
        if (gO != null) {
            return gO;
        }
        gO = C0571a.m1068v((IBinder) C0181p.m551a(C0181p.getRemoteContext(context).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
        C0181p.m554j(context);
        return gO;
    }

    private static void m554j(Context context) {
        try {
            gO.m538a(C0883c.m1158f(C0181p.getRemoteContext(context).getResources()), (int) GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static void m555k(Context context) throws GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
        }
    }

    private static void m556l(Context context) {
        Class bm = C0181p.bm();
        if (bm != null) {
            Log.i(C0181p.class.getSimpleName(), "Making Creator statically");
            gO = (C0168c) C0181p.m552c(bm);
            C0181p.m554j(context);
        }
    }
}
