package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0113b.C0477a;
import com.google.android.gms.maps.internal.IGoogleMapDelegate.C0557a;

public interface IMapViewDelegate extends IInterface {

    /* renamed from: com.google.android.gms.maps.internal.IMapViewDelegate.a */
    public static abstract class C0563a extends Binder implements IMapViewDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IMapViewDelegate.a.a */
        private static class C0562a implements IMapViewDelegate {
            private IBinder f110a;

            C0562a(IBinder iBinder) {
                this.f110a = iBinder;
            }

            public IBinder asBinder() {
                return this.f110a;
            }

            public IGoogleMapDelegate getMap() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    IGoogleMapDelegate w = C0557a.m1057w(obtain2.readStrongBinder());
                    return w;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public C0113b getView() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    C0113b l = C0477a.m657l(obtain2.readStrongBinder());
                    return l;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onCreate(Bundle savedInstanceState) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (savedInstanceState != null) {
                        obtain.writeInt(1);
                        savedInstanceState.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f110a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLowMemory() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onPause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onResume() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    this.f110a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSaveInstanceState(Bundle outState) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (outState != null) {
                        obtain.writeInt(1);
                        outState.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f110a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        outState.readFromParcel(obtain2);
                    }
                    obtain2.recycle();
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IMapViewDelegate m1060A(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMapViewDelegate)) ? new C0562a(iBinder) : (IMapViewDelegate) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            IBinder iBinder = null;
            Bundle bundle;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    IGoogleMapDelegate map = getMap();
                    reply.writeNoException();
                    if (map != null) {
                        iBinder = map.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    onCreate(bundle);
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    onResume();
                    reply.writeNoException();
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    onPause();
                    reply.writeNoException();
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    onDestroy();
                    reply.writeNoException();
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    onLowMemory();
                    reply.writeNoException();
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    if (data.readInt() != 0) {
                        bundle = (Bundle) Bundle.CREATOR.createFromParcel(data);
                    }
                    onSaveInstanceState(bundle);
                    reply.writeNoException();
                    if (bundle != null) {
                        reply.writeInt(1);
                        bundle.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.maps.internal.IMapViewDelegate");
                    C0113b view = getView();
                    reply.writeNoException();
                    if (view != null) {
                        iBinder = view.asBinder();
                    }
                    reply.writeStrongBinder(iBinder);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.internal.IMapViewDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    IGoogleMapDelegate getMap() throws RemoteException;

    C0113b getView() throws RemoteException;

    void onCreate(Bundle bundle) throws RemoteException;

    void onDestroy() throws RemoteException;

    void onLowMemory() throws RemoteException;

    void onPause() throws RemoteException;

    void onResume() throws RemoteException;

    void onSaveInstanceState(Bundle bundle) throws RemoteException;
}
