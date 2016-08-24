package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;

/* renamed from: com.google.android.gms.maps.model.internal.f */
public interface C0197f extends IInterface {

    /* renamed from: com.google.android.gms.maps.model.internal.f.a */
    public static abstract class C0608a extends Binder implements C0197f {

        /* renamed from: com.google.android.gms.maps.model.internal.f.a.a */
        private static class C0607a implements C0197f {
            private IBinder f133a;

            C0607a(IBinder iBinder) {
                this.f133a = iBinder;
            }

            public boolean m1128a(C0197f c0197f) throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    obtain.writeStrongBinder(c0197f != null ? c0197f.asBinder() : null);
                    this.f133a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
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
                return this.f133a;
            }

            public void clearTileCache() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getZIndex() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int hashCodeRemote() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isVisible() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void remove() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    this.f133a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setVisible(boolean visible) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    if (visible) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.f133a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setZIndex(float zIndex) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    obtain.writeFloat(zIndex);
                    this.f133a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static C0197f m1129T(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof C0197f)) ? new C0607a(iBinder) : (C0197f) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            boolean isVisible;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    remove();
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    clearTileCache();
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    String id = getId();
                    reply.writeNoException();
                    reply.writeString(id);
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    setZIndex(data.readFloat());
                    reply.writeNoException();
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    float zIndex = getZIndex();
                    reply.writeNoException();
                    reply.writeFloat(zIndex);
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    boolean z;
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    setVisible(z);
                    reply.writeNoException();
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    isVisible = isVisible();
                    reply.writeNoException();
                    if (isVisible) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    isVisible = m591a(C0608a.m1129T(data.readStrongBinder()));
                    reply.writeNoException();
                    if (isVisible) {
                        i = 1;
                    }
                    reply.writeInt(i);
                    return true;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    data.enforceInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    i = hashCodeRemote();
                    reply.writeNoException();
                    reply.writeInt(i);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    boolean m591a(C0197f c0197f) throws RemoteException;

    void clearTileCache() throws RemoteException;

    String getId() throws RemoteException;

    float getZIndex() throws RemoteException;

    int hashCodeRemote() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void remove() throws RemoteException;

    void setVisible(boolean z) throws RemoteException;

    void setZIndex(float f) throws RemoteException;
}
