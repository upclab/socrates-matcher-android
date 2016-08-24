package com.google.android.gms.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;

/* renamed from: com.google.android.gms.internal.o */
public interface C0153o extends IInterface {

    /* renamed from: com.google.android.gms.internal.o.a */
    public static abstract class C0537a extends Binder implements C0153o {

        /* renamed from: com.google.android.gms.internal.o.a.a */
        private static class C0536a implements C0153o {
            private IBinder f102a;

            C0536a(IBinder iBinder) {
                this.f102a = iBinder;
            }

            public IBinder asBinder() {
                return this.f102a;
            }

            public void m1008b(int i, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.IGmsCallbacks");
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f102a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0537a() {
            attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public static C0153o m1009g(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0153o)) ? new C0536a(iBinder) : (C0153o) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                    m493b(data.readInt(), data.readStrongBinder(), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.common.internal.IGmsCallbacks");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m493b(int i, IBinder iBinder, Bundle bundle) throws RemoteException;
}
