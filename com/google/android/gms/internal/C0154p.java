package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.internal.C0153o.C0537a;

/* renamed from: com.google.android.gms.internal.p */
public interface C0154p extends IInterface {

    /* renamed from: com.google.android.gms.internal.p.a */
    public static abstract class C0539a extends Binder implements C0154p {

        /* renamed from: com.google.android.gms.internal.p.a.a */
        private static class C0538a implements C0154p {
            private IBinder f103a;

            C0538a(IBinder iBinder) {
                this.f103a = iBinder;
            }

            public void m1010a(C0153o c0153o, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    this.f103a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1011a(C0153o c0153o, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.f103a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1012a(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1013a(C0153o c0153o, int i, String str, String str2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    this.f103a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1014a(C0153o c0153o, int i, String str, String str2, String[] strArr, String str3, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1015a(C0153o c0153o, int i, String str, String str2, String[] strArr, String str3, IBinder iBinder, String str4, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str4);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f103a;
            }

            public void m1016b(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1017c(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1018d(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1019e(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1020f(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1021g(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m1022h(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsServiceBroker");
                    obtain.writeStrongBinder(c0153o != null ? c0153o.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f103a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0154p m1023h(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0154p)) ? new C0538a(iBinder) : (C0154p) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bundle bundle = null;
            C0153o g;
            int readInt;
            String readString;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    m498a(C0537a.m1009g(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m496a(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    m495a(C0537a.m1009g(data.readStrongBinder()), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    m494a(C0537a.m1009g(data.readStrongBinder()), data.readInt());
                    reply.writeNoException();
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m500b(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m501c(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m502d(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m503e(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    m499a(C0537a.m1009g(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray(), data.readString(), data.readStrongBinder(), data.readString(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    m497a(C0537a.m1009g(data.readStrongBinder()), data.readInt(), data.readString(), data.readString(), data.createStringArray());
                    reply.writeNoException();
                    return true;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m504f(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m505g(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    g = C0537a.m1009g(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m506h(g, readInt, readString, bundle);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.common.internal.IGmsServiceBroker");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m494a(C0153o c0153o, int i) throws RemoteException;

    void m495a(C0153o c0153o, int i, String str) throws RemoteException;

    void m496a(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m497a(C0153o c0153o, int i, String str, String str2, String[] strArr) throws RemoteException;

    void m498a(C0153o c0153o, int i, String str, String str2, String[] strArr, String str3, Bundle bundle) throws RemoteException;

    void m499a(C0153o c0153o, int i, String str, String str2, String[] strArr, String str3, IBinder iBinder, String str4, Bundle bundle) throws RemoteException;

    void m500b(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m501c(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m502d(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m503e(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m504f(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m505g(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;

    void m506h(C0153o c0153o, int i, String str, Bundle bundle) throws RemoteException;
}
