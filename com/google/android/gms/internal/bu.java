package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.internal.bq.C0520a;
import com.google.android.gms.plus.PlusOneDummyView;

public final class bu {
    private static Context gN;
    private static bq iy;

    /* renamed from: com.google.android.gms.internal.bu.a */
    public static class C0133a extends Exception {
        public C0133a(String str) {
            super(str);
        }
    }

    public static View m405a(Context context, int i, int i2, String str, int i3) {
        if (str != null) {
            return (View) C0883c.m1157a(m407m(context).m368a(C0883c.m1158f(context), i, i2, str, i3));
        }
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }

    public static View m406a(Context context, int i, int i2, String str, String str2) {
        if (str != null) {
            return (View) C0883c.m1157a(m407m(context).m369a(C0883c.m1158f(context), i, i2, str, str2));
        }
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            return new PlusOneDummyView(context, i);
        }
    }

    private static bq m407m(Context context) throws C0133a {
        C0159s.m517d(context);
        if (iy == null) {
            if (gN == null) {
                gN = GooglePlayServicesUtil.getRemoteContext(context);
                if (gN == null) {
                    throw new C0133a("Could not get remote context.");
                }
            }
            try {
                iy = C0520a.m912Z((IBinder) gN.getClassLoader().loadClass("com.google.android.gms.plus.plusone.PlusOneButtonCreatorImpl").newInstance());
            } catch (ClassNotFoundException e) {
                throw new C0133a("Could not load creator class.");
            } catch (InstantiationException e2) {
                throw new C0133a("Could not instantiate creator.");
            } catch (IllegalAccessException e3) {
                throw new C0133a("Could not access creator.");
            }
        }
        return iy;
    }
}
