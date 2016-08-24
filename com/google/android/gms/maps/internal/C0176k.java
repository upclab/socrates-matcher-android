package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.internal.C0195d;
import com.google.android.gms.maps.model.internal.C0195d.C0604a;

/* renamed from: com.google.android.gms.maps.internal.k */
public interface C0176k extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.k.a */
    public static abstract class C0587a extends Binder implements C0176k {

        /* renamed from: com.google.android.gms.maps.internal.k.a.a */
        private static class C0586a implements C0176k {
            private IBinder f122a;

            C0586a(IBinder iBinder) {
                this.f122a = iBinder;
            }

            public IBinder asBinder() {
                return this.f122a;
            }

            public void m1081b(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f122a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1082c(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f122a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1083d(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f122a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0587a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnMarkerDragListener");
        }

        public static C0176k m1084H(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0176k)) ? new C0586a(iBinder) : (C0176k) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    m546b(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    m548d(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    m547c(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnMarkerDragListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m546b(C0195d c0195d) throws RemoteException;

    void m547c(C0195d c0195d) throws RemoteException;

    void m548d(C0195d c0195d) throws RemoteException;
}
