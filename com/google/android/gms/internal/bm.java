package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.internal.bl.C0512a;

public interface bm extends IInterface {

    /* renamed from: com.google.android.gms.internal.bm.a */
    public static abstract class C0514a extends Binder implements bm {

        /* renamed from: com.google.android.gms.internal.bm.a.a */
        private static class C0513a implements bm {
            private IBinder f89a;

            C0513a(IBinder iBinder) {
                this.f89a = iBinder;
            }

            public void m884a(bl blVar, Uri uri, Bundle bundle, boolean z) throws RemoteException {
                IBinder iBinder = null;
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.panorama.internal.IPanoramaService");
                    if (blVar != null) {
                        iBinder = blVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.f89a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f89a;
            }
        }

        public static bm m885W(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.panorama.internal.IPanoramaService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof bm)) ? new C0513a(iBinder) : (bm) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.panorama.internal.IPanoramaService");
                    m350a(C0512a.m883V(data.readStrongBinder()), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null, data.readInt() != 0);
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.panorama.internal.IPanoramaService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m350a(bl blVar, Uri uri, Bundle bundle, boolean z) throws RemoteException;
}
