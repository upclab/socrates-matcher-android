package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.C0116e;
import com.google.android.gms.dynamic.C0116e.C0115a;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.internal.C0155q.C0541a;

/* renamed from: com.google.android.gms.internal.t */
public final class C0542t extends C0116e<C0155q> {
    private static final C0542t ca;

    static {
        ca = new C0542t();
    }

    private C0542t() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View m1026d(Context context, int i, int i2) throws C0115a {
        return ca.m1027e(context, i, i2);
    }

    private View m1027e(Context context, int i, int i2) throws C0115a {
        try {
            return (View) C0883c.m1157a(((C0155q) m138h(context)).m507a(C0883c.m1158f(context), i, i2));
        } catch (Throwable e) {
            throw new C0115a("Could not get button with size " + i + " and color " + i2, e);
        }
    }

    public C0155q m1028j(IBinder iBinder) {
        return C0541a.m1025i(iBinder);
    }

    public /* synthetic */ Object m1029k(IBinder iBinder) {
        return m1028j(iBinder);
    }
}
