package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate.C0555a;
import com.google.android.gms.maps.internal.IMapFragmentDelegate.C0561a;
import com.google.android.gms.maps.internal.IMapViewDelegate.C0563a;
import com.google.android.gms.maps.model.internal.C0192a;
import com.google.android.gms.maps.model.internal.C0192a.C0598a;

/* renamed from: com.google.android.gms.maps.internal.c */
public interface C0168c extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.c.a */
    public static abstract class C0571a extends Binder implements C0168c {

        /* renamed from: com.google.android.gms.maps.internal.c.a.a */
        private static class C0570a implements C0168c {
            private IBinder f114a;

            C0570a(IBinder iBinder) {
                this.f114a = iBinder;
            }

            public IMapViewDelegate m1064a(C0113b c0113b, GoogleMapOptions googleMapOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    if (googleMapOptions != null) {
                        obtain.writeInt(1);
                        googleMapOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f114a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    IMapViewDelegate A = C0563a.m1060A(obtain2.readStrongBinder());
                    return A;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1065a(C0113b c0113b, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    obtain.writeInt(i);
                    this.f114a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f114a;
            }

            public ICameraUpdateFactoryDelegate bk() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.f114a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    ICameraUpdateFactoryDelegate t = C0555a.m1056t(obtain2.readStrongBinder());
                    return t;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0192a bl() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    this.f114a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    C0192a N = C0598a.m1117N(obtain2.readStrongBinder());
                    return N;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1066c(C0113b c0113b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    this.f114a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IMapFragmentDelegate m1067d(C0113b c0113b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    this.f114a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    IMapFragmentDelegate z = C0561a.m1059z(obtain2.readStrongBinder());
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0168c m1068v(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0168c)) ? new C0570a(iBinder) : (C0168c) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    m539c(C0477a.m657l(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IMapFragmentDelegate d = m540d(C0477a.m657l(data.readStrongBinder()));
                    reply.writeNoException();
                    if (d != null) {
                        iBinder = d.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    IMapViewDelegate a = m537a(C0477a.m657l(data.readStrongBinder()), data.readInt() != 0 ? GoogleMapOptions.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    if (a != null) {
                        iBinder = a.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    ICameraUpdateFactoryDelegate bk = bk();
                    reply.writeNoException();
                    if (bk != null) {
                        iBinder = bk.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    C0192a bl = bl();
                    reply.writeNoException();
                    if (bl != null) {
                        iBinder = bl.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICreator");
                    m538a(C0477a.m657l(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.ICreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IMapViewDelegate m537a(C0113b c0113b, GoogleMapOptions googleMapOptions) throws RemoteException;

    void m538a(C0113b c0113b, int i) throws RemoteException;

    ICameraUpdateFactoryDelegate bk() throws RemoteException;

    C0192a bl() throws RemoteException;

    void m539c(C0113b c0113b) throws RemoteException;

    IMapFragmentDelegate m540d(C0113b c0113b) throws RemoteException;
}
