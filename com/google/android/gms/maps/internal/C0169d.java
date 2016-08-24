package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.model.internal.C0195d;
import com.google.android.gms.maps.model.internal.C0195d.C0604a;

/* renamed from: com.google.android.gms.maps.internal.d */
public interface C0169d extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.d.a */
    public static abstract class C0573a extends Binder implements C0169d {

        /* renamed from: com.google.android.gms.maps.internal.d.a.a */
        private static class C0572a implements C0169d {
            private IBinder f115a;

            C0572a(IBinder iBinder) {
                this.f115a = iBinder;
            }

            public IBinder asBinder() {
                return this.f115a;
            }

            public C0113b m1069f(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f115a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1070g(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f115a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0573a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
        }

        public static C0169d m1071x(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0169d)) ? new C0572a(iBinder) : (C0169d) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            C0113b f;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    f = m541f(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    if (f != null) {
                        iBinder = f.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    f = m542g(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    if (f != null) {
                        iBinder = f.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    C0113b m541f(C0195d c0195d) throws RemoteException;

    C0113b m542g(C0195d c0195d) throws RemoteException;
}
