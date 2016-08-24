package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;

public interface br extends IInterface {

    /* renamed from: com.google.android.gms.internal.br.a */
    public static abstract class C0522a extends Binder implements br {

        /* renamed from: com.google.android.gms.internal.br.a.a */
        private static class C0521a implements br {
            private IBinder f92a;

            C0521a(IBinder iBinder) {
                this.f92a = iBinder;
            }

            public IBinder asBinder() {
                return this.f92a;
            }

            public void cancelClick() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.f92a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public PendingIntent getResolution() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.f92a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    PendingIntent pendingIntent = obtain2.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return pendingIntent;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void reinitialize() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    this.f92a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static br aa(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof br)) ? new C0521a(iBinder) : (br) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    PendingIntent resolution = getResolution();
                    reply.writeNoException();
                    if (resolution != null) {
                        reply.writeInt(1);
                        resolution.writeToParcel(reply, 1);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    cancelClick();
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    reinitialize();
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.plus.internal.IPlusOneDelegate");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void cancelClick() throws RemoteException;

    PendingIntent getResolution() throws RemoteException;

    void reinitialize() throws RemoteException;
}
