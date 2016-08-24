package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;

/* renamed from: com.google.android.gms.maps.internal.m */
public interface C0178m extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.m.a */
    public static abstract class C0591a extends Binder implements C0178m {

        /* renamed from: com.google.android.gms.maps.internal.m.a.a */
        private static class C0590a implements C0178m {
            private IBinder f124a;

            C0590a(IBinder iBinder) {
                this.f124a = iBinder;
            }

            public IBinder asBinder() {
                return this.f124a;
            }

            public void m1086b(C0113b c0113b) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                    obtain.writeStrongBinder(c0113b != null ? c0113b.asBinder() : null);
                    this.f124a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0591a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
        }

        public static C0178m m1087J(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0178m)) ? new C0590a(iBinder) : (C0178m) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                    m549b(C0477a.m657l(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnMyLocationChangeListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m549b(C0113b c0113b) throws RemoteException;
}
