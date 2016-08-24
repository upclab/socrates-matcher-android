package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.data.C0468d;
import java.util.List;

public interface bp extends IInterface {

    /* renamed from: com.google.android.gms.internal.bp.a */
    public static abstract class C0518a extends Binder implements bp {

        /* renamed from: com.google.android.gms.internal.bp.a.a */
        private static class C0517a implements bp {
            private IBinder f90a;

            C0517a(IBinder iBinder) {
                this.f90a = iBinder;
            }

            public void m892E(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeString(str);
                    this.f90a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m893a(int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i2);
                    this.f90a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m894a(int i, Bundle bundle, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m895a(int i, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (parcelFileDescriptor != null) {
                        obtain.writeInt(1);
                        parcelFileDescriptor.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m896a(int i, Bundle bundle, ak akVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (akVar != null) {
                        obtain.writeInt(1);
                        akVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m897a(int i, Bundle bundle, co coVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (coVar != null) {
                        obtain.writeInt(1);
                        coVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m898a(int i, Bundle bundle, cq cqVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (cqVar != null) {
                        obtain.writeInt(1);
                        cqVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m899a(int i, Bundle bundle, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.f90a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m900a(int i, Bundle bundle, String str, bv bvVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    if (bvVar != null) {
                        obtain.writeInt(1);
                        bvVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m901a(int i, Bundle bundle, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    this.f90a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m902a(int i, Bundle bundle, List<C0543x> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedList(list);
                    this.f90a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m903a(C0468d c0468d, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.f90a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m904a(C0468d c0468d, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f90a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f90a;
            }

            public void m905b(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m906b(int i, Bundle bundle, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle2 != null) {
                        obtain.writeInt(1);
                        bundle2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m907b(C0468d c0468d, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.f90a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m908c(int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusCallbacks");
                    obtain.writeInt(i);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f90a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0518a() {
            attachInterface(this, "com.google.android.gms.plus.internal.IPlusCallbacks");
        }

        public static bp m909Y(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof bp)) ? new C0517a(iBinder) : (bp) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            cq cqVar = null;
            C0468d a;
            int readInt;
            Bundle bundle;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m353a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m354a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m351E(data.readString());
                    reply.writeNoException();
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (data.readInt() != 0) {
                        a = C0468d.CREATOR.m36a(data);
                    }
                    m362a(a, data.readString());
                    reply.writeNoException();
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    ak m;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    readInt = data.readInt();
                    bundle = data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null;
                    if (data.readInt() != 0) {
                        m = ak.CREATOR.m200m(data);
                    }
                    m355a(readInt, bundle, m);
                    reply.writeNoException();
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (data.readInt() != 0) {
                        a = C0468d.CREATOR.m36a(data);
                    }
                    m363a(a, data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m364b(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    if (data.readInt() != 0) {
                        a = C0468d.CREATOR.m36a(data);
                    }
                    m366b(a, data.readString());
                    reply.writeNoException();
                    return true;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m365b(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    Bundle bundle2;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    readInt = data.readInt();
                    if (data.readInt() != 0) {
                        bundle2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    m360a(readInt, bundle2, data.readString(), data.createStringArrayList(), data.createStringArrayList(), data.createStringArrayList());
                    reply.writeNoException();
                    return true;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    bv v;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    readInt = data.readInt();
                    bundle = data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null;
                    String readString = data.readString();
                    if (data.readInt() != 0) {
                        v = bv.CREATOR.m410v(data);
                    }
                    m359a(readInt, bundle, readString, v);
                    reply.writeNoException();
                    return true;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m367c(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    co I;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    readInt = data.readInt();
                    bundle = data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null;
                    if (data.readInt() != 0) {
                        I = co.CREATOR.m441I(data);
                    }
                    m356a(readInt, bundle, I);
                    reply.writeNoException();
                    return true;
                case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    readInt = data.readInt();
                    bundle = data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null;
                    if (data.readInt() != 0) {
                        cqVar = cq.CREATOR.m443J(data);
                    }
                    m357a(readInt, bundle, cqVar);
                    reply.writeNoException();
                    return true;
                case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m358a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readString());
                    reply.writeNoException();
                    return true;
                case Resource.USAGE_CONTEXT_FIELD_NUMBER /*16*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m361a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.createTypedArrayList(C0543x.CREATOR));
                    reply.writeNoException();
                    return true;
                case Resource.RESOURCE_FORMAT_VERSION_FIELD_NUMBER /*17*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusCallbacks");
                    m352a(data.readInt(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.plus.internal.IPlusCallbacks");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m351E(String str) throws RemoteException;

    void m352a(int i, Bundle bundle, int i2) throws RemoteException;

    void m353a(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

    void m354a(int i, Bundle bundle, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void m355a(int i, Bundle bundle, ak akVar) throws RemoteException;

    void m356a(int i, Bundle bundle, co coVar) throws RemoteException;

    void m357a(int i, Bundle bundle, cq cqVar) throws RemoteException;

    void m358a(int i, Bundle bundle, String str) throws RemoteException;

    void m359a(int i, Bundle bundle, String str, bv bvVar) throws RemoteException;

    void m360a(int i, Bundle bundle, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException;

    void m361a(int i, Bundle bundle, List<C0543x> list) throws RemoteException;

    void m362a(C0468d c0468d, String str) throws RemoteException;

    void m363a(C0468d c0468d, String str, String str2) throws RemoteException;

    void m364b(int i, Bundle bundle) throws RemoteException;

    void m365b(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

    void m366b(C0468d c0468d, String str) throws RemoteException;

    void m367c(int i, Bundle bundle) throws RemoteException;
}
