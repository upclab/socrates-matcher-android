package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.C0468d;

/* renamed from: com.google.android.gms.internal.d */
public interface C0135d extends IInterface {

    /* renamed from: com.google.android.gms.internal.d.a */
    public static abstract class C0529a extends Binder implements C0135d {

        /* renamed from: com.google.android.gms.internal.d.a.a */
        private static class C0528a implements C0135d {
            private IBinder f98a;

            C0528a(IBinder iBinder) {
                this.f98a = iBinder;
            }

            public void m960a(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    obtain.writeInt(i);
                    this.f98a.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m961a(int i, C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    obtain.writeInt(i);
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f98a.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m962a(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f98a.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f98a;
            }

            public void onSignOutComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    this.f98a.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStateDeleted(int statusCode, int stateKey) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    obtain.writeInt(statusCode);
                    obtain.writeInt(stateKey);
                    this.f98a.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0529a() {
            attachInterface(this, "com.google.android.gms.appstate.internal.IAppStateCallbacks");
        }

        public static C0135d m963d(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0135d)) ? new C0528a(iBinder) : (C0135d) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            C0468d c0468d = null;
            switch (code) {
                case 5001:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    int readInt = data.readInt();
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m445a(readInt, c0468d);
                    reply.writeNoException();
                    return true;
                case 5002:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m446a(c0468d);
                    reply.writeNoException();
                    return true;
                case 5003:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    onStateDeleted(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 5004:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    onSignOutComplete();
                    reply.writeNoException();
                    return true;
                case 5005:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    m444a(data.readInt());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.appstate.internal.IAppStateCallbacks");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m444a(int i) throws RemoteException;

    void m445a(int i, C0468d c0468d) throws RemoteException;

    void m446a(C0468d c0468d) throws RemoteException;

    void onSignOutComplete() throws RemoteException;

    void onStateDeleted(int i, int i2) throws RemoteException;
}
