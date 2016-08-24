package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.C0135d.C0529a;

/* renamed from: com.google.android.gms.internal.e */
public interface C0136e extends IInterface {

    /* renamed from: com.google.android.gms.internal.e.a */
    public static abstract class C0531a extends Binder implements C0136e {

        /* renamed from: com.google.android.gms.internal.e.a.a */
        private static class C0530a implements C0136e {
            private IBinder f99a;

            C0530a(IBinder iBinder) {
                this.f99a = iBinder;
            }

            public void m964a(C0135d c0135d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    this.f99a.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m965a(C0135d c0135d, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    obtain.writeInt(i);
                    this.f99a.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m966a(C0135d c0135d, int i, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.f99a.transact(5006, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m967a(C0135d c0135d, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.f99a.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f99a;
            }

            public void m968b(C0135d c0135d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    this.f99a.transact(5008, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m969b(C0135d c0135d, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    obtain.writeInt(i);
                    this.f99a.transact(5007, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m970c(C0135d c0135d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    obtain.writeStrongBinder(c0135d != null ? c0135d.asBinder() : null);
                    this.f99a.transact(5009, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMaxNumKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.f99a.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMaxStateSize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.appstate.internal.IAppStateService");
                    this.f99a.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0136e m971e(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.appstate.internal.IAppStateService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0136e)) ? new C0530a(iBinder) : (C0136e) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int maxStateSize;
            switch (code) {
                case 5001:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    maxStateSize = getMaxStateSize();
                    reply.writeNoException();
                    reply.writeInt(maxStateSize);
                    return true;
                case 5002:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    maxStateSize = getMaxNumKeys();
                    reply.writeNoException();
                    reply.writeInt(maxStateSize);
                    return true;
                case 5003:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m450a(C0529a.m963d(data.readStrongBinder()), data.readInt(), data.createByteArray());
                    reply.writeNoException();
                    return true;
                case 5004:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m448a(C0529a.m963d(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case 5005:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m447a(C0529a.m963d(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5006:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m449a(C0529a.m963d(data.readStrongBinder()), data.readInt(), data.readString(), data.createByteArray());
                    reply.writeNoException();
                    return true;
                case 5007:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m452b(C0529a.m963d(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case 5008:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m451b(C0529a.m963d(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5009:
                    data.enforceInterface("com.google.android.gms.appstate.internal.IAppStateService");
                    m453c(C0529a.m963d(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.appstate.internal.IAppStateService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m447a(C0135d c0135d) throws RemoteException;

    void m448a(C0135d c0135d, int i) throws RemoteException;

    void m449a(C0135d c0135d, int i, String str, byte[] bArr) throws RemoteException;

    void m450a(C0135d c0135d, int i, byte[] bArr) throws RemoteException;

    void m451b(C0135d c0135d) throws RemoteException;

    void m452b(C0135d c0135d, int i) throws RemoteException;

    void m453c(C0135d c0135d) throws RemoteException;

    int getMaxNumKeys() throws RemoteException;

    int getMaxStateSize() throws RemoteException;
}
