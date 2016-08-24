package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public interface IProjectionDelegate extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.IProjectionDelegate.a */
    public static abstract class C0565a extends Binder implements IProjectionDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IProjectionDelegate.a.a */
        private static class C0564a implements IProjectionDelegate {
            private IBinder f111a;

            C0564a(IBinder iBinder) {
                this.f111a = iBinder;
            }

            public IBinder asBinder() {
                return this.f111a;
            }

            public LatLng fromScreenLocation(C0113b point) throws RemoteException {
                LatLng latLng = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    obtain.writeStrongBinder(point != null ? point.asBinder() : null);
                    this.f111a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        latLng = LatLng.CREATOR.createFromParcel(obtain2);
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return latLng;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public VisibleRegion getVisibleRegion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    this.f111a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    VisibleRegion createFromParcel = obtain2.readInt() != 0 ? VisibleRegion.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return createFromParcel;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b toScreenLocation(LatLng location) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IProjectionDelegate");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f111a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IProjectionDelegate m1061K(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IProjectionDelegate)) ? new C0564a(iBinder) : (IProjectionDelegate) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                    LatLng fromScreenLocation = fromScreenLocation(C0477a.m657l(data.readStrongBinder()));
                    reply.writeNoException();
                    if (fromScreenLocation != null) {
                        reply.writeInt(1);
                        fromScreenLocation.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                    C0113b toScreenLocation = toScreenLocation(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    if (toScreenLocation != null) {
                        iBinder = toScreenLocation.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IProjectionDelegate");
                    VisibleRegion visibleRegion = getVisibleRegion();
                    reply.writeNoException();
                    if (visibleRegion != null) {
                        reply.writeInt(1);
                        visibleRegion.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IProjectionDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    LatLng fromScreenLocation(C0113b c0113b) throws RemoteException;

    VisibleRegion getVisibleRegion() throws RemoteException;

    C0113b toScreenLocation(LatLng latLng) throws RemoteException;
}
