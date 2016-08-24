package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;

public interface ay extends IInterface {

    /* renamed from: com.google.android.gms.internal.ay.a */
    public static abstract class C0500a extends Binder implements ay {

        /* renamed from: com.google.android.gms.internal.ay.a.a */
        private static class C0499a implements ay {
            private IBinder f82a;

            C0499a(IBinder iBinder) {
                this.f82a = iBinder;
            }

            public void m743B(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.f82a.transact(5013, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m744C(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.f82a.transact(5015, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m745D(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.f82a.transact(5036, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m746E(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    this.f82a.transact(5040, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m747a(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.f82a.transact(5033, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m748a(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.f82a.transact(5001, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m749a(int i, String str, boolean z) throws RemoteException {
                int i2 = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (z) {
                        i2 = 1;
                    }
                    obtain.writeInt(i2);
                    this.f82a.transact(5034, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m750a(C0468d c0468d, C0468d c0468d2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (c0468d2 != null) {
                        obtain.writeInt(1);
                        c0468d2.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5005, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m751a(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5026, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.f82a;
            }

            public void m752b(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m753b(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5027, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m754c(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5004, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m755c(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5028, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m756d(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5006, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m757d(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5029, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m758e(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5007, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m759e(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5030, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m760f(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5008, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m761f(C0468d c0468d, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringArray(strArr);
                    this.f82a.transact(5031, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m762g(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5009, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m763h(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5010, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m764i(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5011, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m765j(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5017, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m766k(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5037, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m767l(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5012, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m768m(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5014, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m769n(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5018, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m770o(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5019, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onAchievementUpdated(int statusCode, String achievementId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(statusCode);
                    obtain.writeString(achievementId);
                    this.f82a.transact(5003, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLeftRoom(int statusCode, String roomId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeInt(statusCode);
                    obtain.writeString(roomId);
                    this.f82a.transact(5020, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onP2PConnected(String participantId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(participantId);
                    this.f82a.transact(GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onP2PDisconnected(String participantId) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    obtain.writeString(participantId);
                    this.f82a.transact(6002, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onRealTimeMessageReceived(RealTimeMessage message) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (message != null) {
                        obtain.writeInt(1);
                        message.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5032, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSignOutComplete() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    this.f82a.transact(5016, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m771p(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5021, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m772q(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5022, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m773r(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5023, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m774s(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5024, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m775t(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5025, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m776u(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5038, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m777v(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5035, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void m778w(C0468d c0468d) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (c0468d != null) {
                        obtain.writeInt(1);
                        c0468d.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f82a.transact(5039, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public C0500a() {
            attachInterface(this, "com.google.android.gms.games.internal.IGamesCallbacks");
        }

        public static ay m779n(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.games.internal.IGamesCallbacks");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ay)) ? new C0499a(iBinder) : (ay) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            C0468d c0468d = null;
            switch (code) {
                case 5001:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m227a(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 5002:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m231b(c0468d);
                    reply.writeNoException();
                    return true;
                case 5003:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    onAchievementUpdated(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 5004:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m233c(c0468d);
                    reply.writeNoException();
                    return true;
                case 5005:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    C0468d a = data.readInt() != 0 ? C0468d.CREATOR.m36a(data) : null;
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m229a(a, c0468d);
                    reply.writeNoException();
                    return true;
                case 5006:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m235d(c0468d);
                    reply.writeNoException();
                    return true;
                case 5007:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m237e(c0468d);
                    reply.writeNoException();
                    return true;
                case 5008:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m239f(c0468d);
                    reply.writeNoException();
                    return true;
                case 5009:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m241g(c0468d);
                    reply.writeNoException();
                    return true;
                case 5010:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m242h(c0468d);
                    reply.writeNoException();
                    return true;
                case 5011:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m243i(c0468d);
                    reply.writeNoException();
                    return true;
                case 5012:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m246l(c0468d);
                    reply.writeNoException();
                    return true;
                case 5013:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m222B(data.readInt());
                    reply.writeNoException();
                    return true;
                case 5014:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m247m(c0468d);
                    reply.writeNoException();
                    return true;
                case 5015:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m223C(data.readInt());
                    reply.writeNoException();
                    return true;
                case 5016:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    onSignOutComplete();
                    reply.writeNoException();
                    return true;
                case 5017:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m244j(c0468d);
                    reply.writeNoException();
                    return true;
                case 5018:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m248n(c0468d);
                    reply.writeNoException();
                    return true;
                case 5019:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m249o(c0468d);
                    reply.writeNoException();
                    return true;
                case 5020:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    onLeftRoom(data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 5021:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m250p(c0468d);
                    reply.writeNoException();
                    return true;
                case 5022:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m251q(c0468d);
                    reply.writeNoException();
                    return true;
                case 5023:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m252r(c0468d);
                    reply.writeNoException();
                    return true;
                case 5024:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m253s(c0468d);
                    reply.writeNoException();
                    return true;
                case 5025:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m254t(c0468d);
                    reply.writeNoException();
                    return true;
                case 5026:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m230a(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5027:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m232b(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5028:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m234c(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5029:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m236d(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5030:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m238e(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5031:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m240f(c0468d, data.createStringArray());
                    reply.writeNoException();
                    return true;
                case 5032:
                    RealTimeMessage realTimeMessage;
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        realTimeMessage = (RealTimeMessage) RealTimeMessage.CREATOR.createFromParcel(data);
                    }
                    onRealTimeMessageReceived(realTimeMessage);
                    reply.writeNoException();
                    return true;
                case 5033:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m226a(data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case 5034:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m228a(data.readInt(), data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 5035:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m256v(c0468d);
                    reply.writeNoException();
                    return true;
                case 5036:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m224D(data.readInt());
                    reply.writeNoException();
                    return true;
                case 5037:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m245k(c0468d);
                    reply.writeNoException();
                    return true;
                case 5038:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m255u(c0468d);
                    reply.writeNoException();
                    return true;
                case 5039:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    if (data.readInt() != 0) {
                        c0468d = C0468d.CREATOR.m36a(data);
                    }
                    m257w(c0468d);
                    reply.writeNoException();
                    return true;
                case 5040:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    m225E(data.readInt());
                    reply.writeNoException();
                    return true;
                case GamesClient.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER /*6001*/:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    onP2PConnected(data.readString());
                    reply.writeNoException();
                    return true;
                case 6002:
                    data.enforceInterface("com.google.android.gms.games.internal.IGamesCallbacks");
                    onP2PDisconnected(data.readString());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString("com.google.android.gms.games.internal.IGamesCallbacks");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void m222B(int i) throws RemoteException;

    void m223C(int i) throws RemoteException;

    void m224D(int i) throws RemoteException;

    void m225E(int i) throws RemoteException;

    void m226a(int i, int i2, String str) throws RemoteException;

    void m227a(int i, String str) throws RemoteException;

    void m228a(int i, String str, boolean z) throws RemoteException;

    void m229a(C0468d c0468d, C0468d c0468d2) throws RemoteException;

    void m230a(C0468d c0468d, String[] strArr) throws RemoteException;

    void m231b(C0468d c0468d) throws RemoteException;

    void m232b(C0468d c0468d, String[] strArr) throws RemoteException;

    void m233c(C0468d c0468d) throws RemoteException;

    void m234c(C0468d c0468d, String[] strArr) throws RemoteException;

    void m235d(C0468d c0468d) throws RemoteException;

    void m236d(C0468d c0468d, String[] strArr) throws RemoteException;

    void m237e(C0468d c0468d) throws RemoteException;

    void m238e(C0468d c0468d, String[] strArr) throws RemoteException;

    void m239f(C0468d c0468d) throws RemoteException;

    void m240f(C0468d c0468d, String[] strArr) throws RemoteException;

    void m241g(C0468d c0468d) throws RemoteException;

    void m242h(C0468d c0468d) throws RemoteException;

    void m243i(C0468d c0468d) throws RemoteException;

    void m244j(C0468d c0468d) throws RemoteException;

    void m245k(C0468d c0468d) throws RemoteException;

    void m246l(C0468d c0468d) throws RemoteException;

    void m247m(C0468d c0468d) throws RemoteException;

    void m248n(C0468d c0468d) throws RemoteException;

    void m249o(C0468d c0468d) throws RemoteException;

    void onAchievementUpdated(int i, String str) throws RemoteException;

    void onLeftRoom(int i, String str) throws RemoteException;

    void onP2PConnected(String str) throws RemoteException;

    void onP2PDisconnected(String str) throws RemoteException;

    void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) throws RemoteException;

    void onSignOutComplete() throws RemoteException;

    void m250p(C0468d c0468d) throws RemoteException;

    void m251q(C0468d c0468d) throws RemoteException;

    void m252r(C0468d c0468d) throws RemoteException;

    void m253s(C0468d c0468d) throws RemoteException;

    void m254t(C0468d c0468d) throws RemoteException;

    void m255u(C0468d c0468d) throws RemoteException;

    void m256v(C0468d c0468d) throws RemoteException;

    void m257w(C0468d c0468d) throws RemoteException;
}
