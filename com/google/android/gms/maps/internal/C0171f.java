package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.internal.C0195d;
import com.google.android.gms.maps.model.internal.C0195d.C0604a;

/* renamed from: com.google.android.gms.maps.internal.f */
public interface C0171f extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.f.a */
    public static abstract class C0577a extends Binder implements C0171f {

        /* renamed from: com.google.android.gms.maps.internal.f.a.a */
        private static class C0576a implements C0171f {
            private IBinder f117a;

            C0576a(IBinder iBinder) {
                this.f117a = iBinder;
            }

            public IBinder asBinder() {
                return this.f117a;
            }

            public void m1073e(C0195d c0195d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                    obtain.writeStrongBinder(c0195d != null ? c0195d.asBinder() : null);
                    this.f117a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0577a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
        }

        public static C0171f m1074C(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0171f)) ? new C0576a(iBinder) : (C0171f) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                    m543e(C0604a.m1125Q(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnInfoWindowClickListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m543e(C0195d c0195d) throws RemoteException;
}
