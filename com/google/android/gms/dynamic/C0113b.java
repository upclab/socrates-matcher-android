package com.google.android.gms.dynamic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* renamed from: com.google.android.gms.dynamic.b */
public interface C0113b extends IInterface {

    /* renamed from: com.google.android.gms.dynamic.b.a */
    public static abstract class C0477a extends Binder implements C0113b {

        /* renamed from: com.google.android.gms.dynamic.b.a.a */
        private static class C0476a implements C0113b {
            private IBinder f77a;

            C0476a(IBinder iBinder) {
                this.f77a = iBinder;
            }

            public IBinder asBinder() {
                return this.f77a;
            }
        }

        public C0477a() {
            attachInterface(this, "com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static C0113b m657l(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0113b)) ? new C0476a(iBinder) : (C0113b) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1598968902:
                    reply.writeString("com.google.android.gms.dynamic.IObjectWrapper");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }
}
