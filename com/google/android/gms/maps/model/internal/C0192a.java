package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;

/* renamed from: com.google.android.gms.maps.model.internal.a */
public interface C0192a extends IInterface {

    /* renamed from: com.google.android.gms.maps.model.internal.a.a */
    public static abstract class C0598a extends Binder implements C0192a {

        /* renamed from: com.google.android.gms.maps.model.internal.a.a.a */
        private static class C0597a implements C0192a {
            private IBinder f128a;

            C0597a(IBinder iBinder) {
                this.f128a = iBinder;
            }

            public C0113b m1111B(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    obtain.writeString(str);
                    this.f128a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1112C(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    obtain.writeString(str);
                    this.f128a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1113D(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    obtain.writeString(str);
                    this.f128a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1114S(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    obtain.writeInt(i);
                    this.f128a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1115a(Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    if (bitmap != null) {
                        obtain.writeInt(1);
                        bitmap.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f128a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f128a;
            }

            public C0113b bt() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    this.f128a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m1116c(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    obtain.writeFloat(f);
                    this.f128a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0192a m1117N(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0192a)) ? new C0597a(iBinder) : (C0192a) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            C0113b S;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m582S(data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(S != null ? S.asBinder() : null);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m579B(data.readString());
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m580C(data.readString());
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = bt();
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m584c(data.readFloat());
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m583a(data.readInt() != 0 ? (Bitmap) Bitmap.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    S = m581D(data.readString());
                    reply.writeNoException();
                    if (S != null) {
                        iBinder = S.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    C0113b m579B(String str) throws RemoteException;

    C0113b m580C(String str) throws RemoteException;

    C0113b m581D(String str) throws RemoteException;

    C0113b m582S(int i) throws RemoteException;

    C0113b m583a(Bitmap bitmap) throws RemoteException;

    C0113b bt() throws RemoteException;

    C0113b m584c(float f) throws RemoteException;
}
