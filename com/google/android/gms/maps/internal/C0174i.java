package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.LatLng;

/* renamed from: com.google.android.gms.maps.internal.i */
public interface C0174i extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.i.a */
    public static abstract class C0583a extends Binder implements C0174i {

        /* renamed from: com.google.android.gms.maps.internal.i.a.a */
        private static class C0582a implements C0174i {
            private IBinder f120a;

            C0582a(IBinder iBinder) {
                this.f120a = iBinder;
            }

            public IBinder asBinder() {
                return this.f120a;
            }

            public void onMapLongClick(LatLng point) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                    if (point != null) {
                        obtain.writeInt(1);
                        point.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f120a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0583a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnMapLongClickListener");
        }

        public static C0174i m1078F(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMapLongClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0174i)) ? new C0582a(iBinder) : (C0174i) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                    onMapLongClick(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnMapLongClickListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onMapLongClick(LatLng latLng) throws RemoteException;
}
