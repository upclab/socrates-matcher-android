package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.internal.C0195d;
import com.google.android.gms.maps.model.internal.C0195d.C0604a;

/* renamed from: com.google.android.gms.maps.internal.j */
public interface C0175j extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.j.a */
    public static abstract class C0585a extends Binder implements C0175j {

        /* renamed from: com.google.android.gms.maps.internal.j.a.a */
        private static class C0584a implements C0175j {
            private IBinder f121a;

            C0584a(IBinder iBinder) {
                this.f121a = iBinder;
            }

            public boolean m1079a(C0195d c0195d) throws RemoteException {
                boolean z = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f121a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f121a;
            }
        }

        public C0585a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnMarkerClickListener");
        }

        public static C0175j m1080G(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0175j)) ? new C0584a(iBinder) : (C0175j) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    boolean a = m545a(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    reply.writeInt(a ? 1 : 0);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnMarkerClickListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    boolean m545a(C0195d c0195d) throws RemoteException;
}
