package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.LatLng;

/* renamed from: com.google.android.gms.maps.internal.h */
public interface C0173h extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.h.a */
    public static abstract class C0581a extends Binder implements C0173h {

        /* renamed from: com.google.android.gms.maps.internal.h.a.a */
        private static class C0580a implements C0173h {
            private IBinder f119a;

            C0580a(IBinder iBinder) {
                this.f119a = iBinder;
            }

            public IBinder asBinder() {
                return this.f119a;
            }

            public void onMapClick(LatLng point) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IOnMapClickListener");
                    if (point != null) {
                        obtain.writeInt(1);
                        point.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f119a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0581a() {
            attachInterface(this, "com.google.android.gms.maps.internal.IOnMapClickListener");
        }

        public static C0173h m1077E(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnMapClickListener");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0173h)) ? new C0580a(iBinder) : (C0173h) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IOnMapClickListener");
                    onMapClick(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IOnMapClickListener");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onMapClick(LatLng latLng) throws RemoteException;
}
