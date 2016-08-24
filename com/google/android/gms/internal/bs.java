package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.util.TimeUtils;
import com.adsdk.sdk.Const;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.internal.bp.C0518a;
import java.util.List;
import org.joda.time.DateTimeConstants;
import pe.edu.upc.mobile.Utilities.Base64;

public interface bs extends IInterface {

    /* renamed from: com.google.android.gms.internal.bs.a */
    public static abstract class C0524a extends Binder implements bs {

        /* renamed from: com.google.android.gms.internal.bs.a.a */
        private static class C0523a implements bs {
            private IBinder f93a;

            C0523a(IBinder iBinder) {
                this.f93a = iBinder;
            }

            public void m913a(ak akVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    if (akVar != null) {
                        obtain.writeInt(1);
                        akVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f93a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m914a(bp bpVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    this.f93a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m915a(bp bpVar, int i, int i2, int i3, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.f93a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m916a(bp bpVar, int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.f93a.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m917a(bp bpVar, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.f93a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m918a(bp bpVar, int i, String str, Uri uri, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str2);
                    this.f93a.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m919a(bp bpVar, int i, String str, Uri uri, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (uri != null) {
                        obtain.writeInt(1);
                        uri.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.f93a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m920a(bp bpVar, Uri uri, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
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
                    this.f93a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m921a(bp bpVar, co coVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    if (coVar != null) {
                        obtain.writeInt(1);
                        coVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f93a.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m922a(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m923a(bp bpVar, String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.f93a.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m924a(bp bpVar, String str, bv bvVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    if (bvVar != null) {
                        obtain.writeInt(1);
                        bvVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f93a.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m925a(bp bpVar, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f93a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m926a(bp bpVar, String str, String str2, int i, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.f93a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m927a(bp bpVar, String str, String str2, boolean z, String str3) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.f93a.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m928a(bp bpVar, String str, List<C0543x> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeTypedList(list);
                    this.f93a.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m929a(bp bpVar, String str, List<C0543x> list, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeTypedList(list);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f93a.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m930a(bp bpVar, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStringList(list3);
                    this.f93a.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m931a(bp bpVar, String str, List<C0543x> list, boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    obtain.writeTypedList(list);
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.f93a.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m932a(bp bpVar, String str, boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.f93a.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m933a(bp bpVar, String str, boolean z, String str2) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.f93a.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m934a(bp bpVar, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeStringList(list);
                    this.f93a.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m935a(bp bpVar, boolean z, boolean z2) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeInt(z ? 1 : 0);
                    if (!z2) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.f93a.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f93a;
            }

            public void m936b(bp bpVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    this.f93a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m937b(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m938c(bp bpVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    this.f93a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m939c(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void clearDefaultAccount() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.f93a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m940d(bp bpVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    this.f93a.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m941d(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m942e(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m943f(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m944f(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.f93a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m945g(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getAccountName() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    this.f93a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m946h(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m947i(bp bpVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeStrongBinder(bpVar != null ? bpVar.asBinder() : null);
                    obtain.writeString(str);
                    this.f93a.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void removeMoment(String momentId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.plus.internal.IPlusService");
                    obtain.writeString(momentId);
                    this.f93a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static bs ab(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.plus.internal.IPlusService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof bs)) ? new C0523a(iBinder) : (bs) queryLocalInterface;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean z = false;
            Uri uri = null;
            bp Y;
            int readInt;
            String readString;
            String readString2;
            bp Y2;
            switch (code) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m379a(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m382a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m394b(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m370a(data.readInt() != 0 ? ak.CREATOR.m200m(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    String accountName = getAccountName();
                    reply.writeNoException();
                    reply.writeString(accountName);
                    return true;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    clearDefaultAccount();
                    reply.writeNoException();
                    return true;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m396c(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m371a(C0518a.m909Y(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m377a(C0518a.m909Y(data.readStrongBinder()), data.readInt() != 0 ? (Uri) Uri.CREATOR.createFromParcel(data) : null, data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m398d(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m401f(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m383a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.readString(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m393b(C0518a.m909Y(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y = C0518a.m909Y(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        uri = (Uri) Uri.CREATOR.createFromParcel(data);
                    }
                    m376a(Y, readInt, readString, uri, data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case Resource.USAGE_CONTEXT_FIELD_NUMBER /*16*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m372a(C0518a.m909Y(data.readStrongBinder()), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case Resource.RESOURCE_FORMAT_VERSION_FIELD_NUMBER /*17*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    removeMoment(data.readString());
                    reply.writeNoException();
                    return true;
                case Resource.ENABLE_AUTO_EVENT_TRACKING_FIELD_NUMBER /*18*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m399e(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case TimeUtils.HUNDRED_DAY_FIELD_LEN /*19*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m395c(C0518a.m909Y(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 20:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m374a(C0518a.m909Y(data.readStrongBinder()), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 21:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y = C0518a.m909Y(data.readStrongBinder());
                    readString2 = data.readString();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m389a(Y, readString2, z);
                    reply.writeNoException();
                    return true;
                case 22:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    bp Y3 = C0518a.m909Y(data.readStrongBinder());
                    boolean z2 = data.readInt() != 0;
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m392a(Y3, z2, z);
                    reply.writeNoException();
                    return true;
                case 23:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m387a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.createStringArrayList(), data.createStringArrayList(), data.createStringArrayList());
                    reply.writeNoException();
                    return true;
                case DateTimeConstants.HOURS_PER_DAY /*24*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m400f(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 25:
                    bv v;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y2 = C0518a.m909Y(data.readStrongBinder());
                    String readString3 = data.readString();
                    if (data.readInt() != 0) {
                        v = bv.CREATOR.m410v(data);
                    }
                    m381a(Y2, readString3, v);
                    reply.writeNoException();
                    return true;
                case 26:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m402g(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 27:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y = C0518a.m909Y(data.readStrongBinder());
                    readString2 = data.readString();
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m390a(Y, readString2, z, data.readString());
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m385a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.createTypedArrayList(C0543x.CREATOR));
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y = C0518a.m909Y(data.readStrongBinder());
                    readString2 = data.readString();
                    List createTypedArrayList = data.createTypedArrayList(C0543x.CREATOR);
                    if (data.readInt() != 0) {
                        z = true;
                    }
                    m388a(Y, readString2, createTypedArrayList, z);
                    reply.writeNoException();
                    return true;
                case Const.TOUCH_DISTANCE /*30*/:
                    co I;
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y2 = C0518a.m909Y(data.readStrongBinder());
                    if (data.readInt() != 0) {
                        I = co.CREATOR.m441I(data);
                    }
                    m378a(Y2, I);
                    reply.writeNoException();
                    return true;
                case 31:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m386a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.createTypedArrayList(C0543x.CREATOR), data.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(data) : null);
                    reply.writeNoException();
                    return true;
                case Base64.ORDERED /*32*/:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    Y = C0518a.m909Y(data.readStrongBinder());
                    readInt = data.readInt();
                    readString = data.readString();
                    if (data.readInt() != 0) {
                        uri = (Uri) Uri.CREATOR.createFromParcel(data);
                    }
                    m375a(Y, readInt, readString, uri, data.readString());
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m403h(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m391a(C0518a.m909Y(data.readStrongBinder()), data.createStringArrayList());
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m404i(C0518a.m909Y(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m380a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 37:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m384a(C0518a.m909Y(data.readStrongBinder()), data.readString(), data.readString(), data.readInt() != 0, data.readString());
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m397d(C0518a.m909Y(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 39:
                    data.enforceInterface("com.google.android.gms.plus.internal.IPlusService");
                    m373a(C0518a.m909Y(data.readStrongBinder()), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.plus.internal.IPlusService");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m370a(ak akVar) throws RemoteException;

    void m371a(bp bpVar) throws RemoteException;

    void m372a(bp bpVar, int i, int i2, int i3, String str) throws RemoteException;

    void m373a(bp bpVar, int i, int i2, String str) throws RemoteException;

    void m374a(bp bpVar, int i, String str) throws RemoteException;

    void m375a(bp bpVar, int i, String str, Uri uri, String str2) throws RemoteException;

    void m376a(bp bpVar, int i, String str, Uri uri, String str2, String str3) throws RemoteException;

    void m377a(bp bpVar, Uri uri, Bundle bundle) throws RemoteException;

    void m378a(bp bpVar, co coVar) throws RemoteException;

    void m379a(bp bpVar, String str) throws RemoteException;

    void m380a(bp bpVar, String str, int i, String str2) throws RemoteException;

    void m381a(bp bpVar, String str, bv bvVar) throws RemoteException;

    void m382a(bp bpVar, String str, String str2) throws RemoteException;

    void m383a(bp bpVar, String str, String str2, int i, String str3) throws RemoteException;

    void m384a(bp bpVar, String str, String str2, boolean z, String str3) throws RemoteException;

    void m385a(bp bpVar, String str, List<C0543x> list) throws RemoteException;

    void m386a(bp bpVar, String str, List<C0543x> list, Bundle bundle) throws RemoteException;

    void m387a(bp bpVar, String str, List<String> list, List<String> list2, List<String> list3) throws RemoteException;

    void m388a(bp bpVar, String str, List<C0543x> list, boolean z) throws RemoteException;

    void m389a(bp bpVar, String str, boolean z) throws RemoteException;

    void m390a(bp bpVar, String str, boolean z, String str2) throws RemoteException;

    void m391a(bp bpVar, List<String> list) throws RemoteException;

    void m392a(bp bpVar, boolean z, boolean z2) throws RemoteException;

    void m393b(bp bpVar) throws RemoteException;

    void m394b(bp bpVar, String str) throws RemoteException;

    void m395c(bp bpVar) throws RemoteException;

    void m396c(bp bpVar, String str) throws RemoteException;

    void clearDefaultAccount() throws RemoteException;

    void m397d(bp bpVar) throws RemoteException;

    void m398d(bp bpVar, String str) throws RemoteException;

    void m399e(bp bpVar, String str) throws RemoteException;

    void m400f(bp bpVar, String str) throws RemoteException;

    void m401f(String str, String str2) throws RemoteException;

    void m402g(bp bpVar, String str) throws RemoteException;

    String getAccountName() throws RemoteException;

    void m403h(bp bpVar, String str) throws RemoteException;

    void m404i(bp bpVar, String str) throws RemoteException;

    void removeMoment(String str) throws RemoteException;
}
