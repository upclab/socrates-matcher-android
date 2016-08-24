package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;

/* renamed from: com.google.android.gms.internal.q */
public interface C0155q extends IInterface {

    /* renamed from: com.google.android.gms.internal.q.a */
    public static abstract class C0541a extends Binder implements C0155q {

        /* renamed from: com.google.android.gms.internal.q.a.a */
        private static class C0540a implements C0155q {
            private IBinder f104a;

            C0540a(IBinder iBinder) {
                this.f104a = iBinder;
            }

            public C0113b m1024a(C0113b c0113b, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.f104a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f104a;
            }
        }

        public static C0155q m1025i(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0155q)) ? new C0540a(iBinder) : (C0155q) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                    C0113b a = m507a(C0477a.m657l(data.readStrongBinder()), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(a != null ? a.asBinder() : null);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    C0113b m507a(C0113b c0113b, int i, int i2) throws RemoteException;
}
