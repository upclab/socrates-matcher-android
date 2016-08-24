package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;

/* renamed from: com.google.android.gms.maps.internal.g */
public interface C0172g extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.g.a */
    public static abstract class C0579a extends Binder implements C0172g {

        /* renamed from: com.google.android.gms.maps.internal.g.a.a */
        private static class C0578a implements C0172g {
            private IBinder f118a;

            C0578a(IBinder iBinder) {
                this.f118a = iBinder;
            }

            public IBinder asBinder() {
                return this.f118a;
            }

            public void m1075e(C0113b c0113b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnLocationChangeListener");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    this.f118a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0172g m1076D(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnLocationChangeListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0172g)) ? new C0578a(iBinder) : (C0172g) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnLocationChangeListener");
                    m544e(C0477a.m657l(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnLocationChangeListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m544e(C0113b c0113b) throws RemoteException;
}
