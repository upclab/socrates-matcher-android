package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface ICameraUpdateFactoryDelegate extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate.a */
    public static abstract class C0555a extends Binder implements ICameraUpdateFactoryDelegate {

        /* renamed from: com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate.a.a */
        private static class C0554a implements ICameraUpdateFactoryDelegate {
            private IBinder f106a;

            C0554a(IBinder iBinder) {
                this.f106a = iBinder;
            }

            public IBinder asBinder() {
                return this.f106a;
            }

            public C0113b newCameraPosition(CameraPosition cameraPosition) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (cameraPosition != null) {
                        obtain.writeInt(1);
                        cameraPosition.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f106a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b newLatLng(LatLng latLng) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f106a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b newLatLngBounds(LatLngBounds bounds, int padding) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (bounds != null) {
                        obtain.writeInt(1);
                        bounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(padding);
                    this.f106a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b newLatLngBoundsWithSize(LatLngBounds bounds, int width, int height, int padding) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (bounds != null) {
                        obtain.writeInt(1);
                        bounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(width);
                    obtain.writeInt(height);
                    obtain.writeInt(padding);
                    this.f106a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b newLatLngZoom(LatLng latLng, float zoom) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeFloat(zoom);
                    this.f106a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b scrollBy(float xPixel, float yPixel) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(xPixel);
                    obtain.writeFloat(yPixel);
                    this.f106a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b zoomBy(float amount) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(amount);
                    this.f106a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b zoomByWithFocus(float amount, int screenFocusX, int screenFocusY) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(amount);
                    obtain.writeInt(screenFocusX);
                    obtain.writeInt(screenFocusY);
                    this.f106a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b zoomIn() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    this.f106a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b zoomOut() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    this.f106a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b zoomTo(float zoom) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    obtain.writeFloat(zoom);
                    this.f106a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static ICameraUpdateFactoryDelegate m1056t(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICameraUpdateFactoryDelegate)) ? new C0554a(iBinder) : (ICameraUpdateFactoryDelegate) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            C0113b zoomIn;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomIn();
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomOut();
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = scrollBy(data.readFloat(), data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomTo(data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomBy(data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = zoomByWithFocus(data.readFloat(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newCameraPosition(data.readInt() != 0 ? CameraPosition.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLng(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngZoom(data.readInt() != 0 ? LatLng.CREATOR.createFromParcel(data) : null, data.readFloat());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngBounds(data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    zoomIn = newLatLngBoundsWithSize(data.readInt() != 0 ? LatLngBounds.CREATOR.createFromParcel(data) : null, data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    if (zoomIn != null) {
                        iBinder = zoomIn.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    C0113b newCameraPosition(CameraPosition cameraPosition) throws RemoteException;

    C0113b newLatLng(LatLng latLng) throws RemoteException;

    C0113b newLatLngBounds(LatLngBounds latLngBounds, int i) throws RemoteException;

    C0113b newLatLngBoundsWithSize(LatLngBounds latLngBounds, int i, int i2, int i3) throws RemoteException;

    C0113b newLatLngZoom(LatLng latLng, float f) throws RemoteException;

    C0113b scrollBy(float f, float f2) throws RemoteException;

    C0113b zoomBy(float f) throws RemoteException;

    C0113b zoomByWithFocus(float f, int i, int i2) throws RemoteException;

    C0113b zoomIn() throws RemoteException;

    C0113b zoomOut() throws RemoteException;

    C0113b zoomTo(float f) throws RemoteException;
}
