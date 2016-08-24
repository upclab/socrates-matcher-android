package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;

public interface bq extends IInterface {

    /* renamed from: com.google.android.gms.internal.bq.a */
    public static abstract class C0520a extends Binder implements bq {

        /* renamed from: com.google.android.gms.internal.bq.a.a */
        private static class C0519a implements bq {
            private IBinder f91a;

            C0519a(IBinder iBinder) {
                this.f91a = iBinder;
            }

            public C0113b m910a(C0113b c0113b, int i, int i2, String str, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.f91a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b m911a(C0113b c0113b, int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f91a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f91a;
            }
        }

        public static bq m912Z(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof bq)) ? new C0519a(iBinder) : (bq) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            C0113b a;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    a = m368a(C0477a.m657l(data.readStrongBinder()), data.readInt(), data.readInt(), data.readString(), data.readInt());
                    reply.writeNoException();
                    reply.writeStrongBinder(a != null ? a.asBinder() : null);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    a = m369a(C0477a.m657l(data.readStrongBinder()), data.readInt(), data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    if (a != null) {
                        iBinder = a.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.plus.internal.IPlusOneButtonCreator");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    C0113b m368a(C0113b c0113b, int i, int i2, String str, int i3) throws RemoteException;

    C0113b m369a(C0113b c0113b, int i, int i2, String str, String str2) throws RemoteException;
}
