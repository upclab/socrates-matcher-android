package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.appstate.AppState;
import com.google.android.gms.appstate.AppStateBuffer;
import com.google.android.gms.appstate.AppStateClient;
import com.google.android.gms.appstate.OnSignOutCompleteListener;
import com.google.android.gms.appstate.OnStateDeletedListener;
import com.google.android.gms.appstate.OnStateListLoadedListener;
import com.google.android.gms.appstate.OnStateLoadedListener;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0136e.C0531a;
import com.google.android.gms.internal.C0535k.C0146b;
import com.google.android.gms.internal.C0535k.C0533c;
import com.google.android.gms.internal.C0535k.C0916d;

/* renamed from: com.google.android.gms.internal.c */
public final class C0906c extends C0535k<C0136e> {
    private final String f154g;

    /* renamed from: com.google.android.gms.internal.c.b */
    final class C0526b extends C0146b<OnStateDeletedListener> {
        final /* synthetic */ C0906c f94o;
        private final int f95p;
        private final int f96q;

        public C0526b(C0906c c0906c, OnStateDeletedListener onStateDeletedListener, int i, int i2) {
            this.f94o = c0906c;
            super(c0906c, onStateDeletedListener);
            this.f95p = i;
            this.f96q = i2;
        }

        public void m952a(OnStateDeletedListener onStateDeletedListener) {
            onStateDeletedListener.onStateDeleted(this.f95p, this.f96q);
        }

        protected void m954d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.c.h */
    final class C0527h extends C0146b<OnSignOutCompleteListener> {
        final /* synthetic */ C0906c f97o;

        public C0527h(C0906c c0906c, OnSignOutCompleteListener onSignOutCompleteListener) {
            this.f97o = c0906c;
            super(c0906c, onSignOutCompleteListener);
        }

        public void m955a(OnSignOutCompleteListener onSignOutCompleteListener) {
            onSignOutCompleteListener.onSignOutComplete();
        }

        protected void m957d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.c.d */
    final class C0904d extends C0533c<OnStateListLoadedListener> {
        final /* synthetic */ C0906c f151o;

        public C0904d(C0906c c0906c, OnStateListLoadedListener onStateListLoadedListener, C0468d c0468d) {
            this.f151o = c0906c;
            super(c0906c, onStateListLoadedListener, c0468d);
        }

        public void m1300a(OnStateListLoadedListener onStateListLoadedListener, C0468d c0468d) {
            onStateListLoadedListener.onStateListLoaded(c0468d.getStatusCode(), new AppStateBuffer(c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.c.f */
    final class C0905f extends C0533c<OnStateLoadedListener> {
        final /* synthetic */ C0906c f152o;
        private final int f153q;

        public C0905f(C0906c c0906c, OnStateLoadedListener onStateLoadedListener, int i, C0468d c0468d) {
            this.f152o = c0906c;
            super(c0906c, onStateLoadedListener, c0468d);
            this.f153q = i;
        }

        public void m1302a(OnStateLoadedListener onStateLoadedListener, C0468d c0468d) {
            byte[] bArr = null;
            AppStateBuffer appStateBuffer = new AppStateBuffer(c0468d);
            try {
                String conflictVersion;
                byte[] localData;
                if (appStateBuffer.getCount() > 0) {
                    AppState appState = appStateBuffer.get(0);
                    conflictVersion = appState.getConflictVersion();
                    localData = appState.getLocalData();
                    bArr = appState.getConflictData();
                } else {
                    localData = null;
                    conflictVersion = null;
                }
                appStateBuffer.close();
                int statusCode = c0468d.getStatusCode();
                if (statusCode == AppStateClient.STATUS_WRITE_OUT_OF_DATE_VERSION) {
                    onStateLoadedListener.onStateConflict(this.f153q, conflictVersion, localData, bArr);
                } else {
                    onStateLoadedListener.onStateLoaded(statusCode, this.f153q, localData);
                }
            } catch (Throwable th) {
                appStateBuffer.close();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.c.a */
    final class C1069a extends C0898b {
        private final OnStateDeletedListener f162n;
        final /* synthetic */ C0906c f163o;

        public C1069a(C0906c c0906c, OnStateDeletedListener onStateDeletedListener) {
            this.f163o = c0906c;
            this.f162n = (OnStateDeletedListener) C0159s.m514b((Object) onStateDeletedListener, (Object) "Listener must not be null");
        }

        public void onStateDeleted(int statusCode, int stateKey) {
            this.f163o.m998a(new C0526b(this.f163o, this.f162n, statusCode, stateKey));
        }
    }

    /* renamed from: com.google.android.gms.internal.c.c */
    final class C1070c extends C0898b {
        final /* synthetic */ C0906c f164o;
        private final OnStateListLoadedListener f165r;

        public C1070c(C0906c c0906c, OnStateListLoadedListener onStateListLoadedListener) {
            this.f164o = c0906c;
            this.f165r = (OnStateListLoadedListener) C0159s.m514b((Object) onStateListLoadedListener, (Object) "Listener must not be null");
        }

        public void m1446a(C0468d c0468d) {
            this.f164o.m998a(new C0904d(this.f164o, this.f165r, c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.c.e */
    final class C1071e extends C0898b {
        final /* synthetic */ C0906c f166o;
        private final OnStateLoadedListener f167s;

        public C1071e(C0906c c0906c, OnStateLoadedListener onStateLoadedListener) {
            this.f166o = c0906c;
            this.f167s = (OnStateLoadedListener) C0159s.m514b((Object) onStateLoadedListener, (Object) "Listener must not be null");
        }

        public void m1447a(int i, C0468d c0468d) {
            this.f166o.m998a(new C0905f(this.f166o, this.f167s, i, c0468d));
        }
    }

    /* renamed from: com.google.android.gms.internal.c.g */
    final class C1072g extends C0898b {
        final /* synthetic */ C0906c f168o;
        private final OnSignOutCompleteListener f169t;

        public C1072g(C0906c c0906c, OnSignOutCompleteListener onSignOutCompleteListener) {
            this.f168o = c0906c;
            this.f169t = (OnSignOutCompleteListener) C0159s.m514b((Object) onSignOutCompleteListener, (Object) "Listener must not be null");
        }

        public void onSignOutComplete() {
            this.f168o.m998a(new C0527h(this.f168o, this.f169t));
        }
    }

    public C0906c(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, String[] strArr) {
        super(context, connectionCallbacks, onConnectionFailedListener, strArr);
        this.f154g = (String) C0159s.m517d(str);
    }

    public void m1304a(OnStateLoadedListener onStateLoadedListener, int i, byte[] bArr) {
        if (onStateLoadedListener == null) {
            C0135d c0135d = null;
        } else {
            Object c1071e = new C1071e(this, onStateLoadedListener);
        }
        try {
            ((C0136e) m995C()).m450a(c0135d, i, bArr);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    protected void m1305a(C0154p c0154p, C0916d c0916d) throws RemoteException {
        c0154p.m497a(c0916d, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), this.f154g, m1005x());
    }

    protected void m1306a(String... strArr) {
        boolean z = false;
        for (String equals : strArr) {
            if (equals.equals(Scopes.APP_STATE)) {
                z = true;
            }
        }
        C0159s.m512a(z, String.format("AppStateClient requires %s to function.", new Object[]{Scopes.APP_STATE}));
    }

    protected C0136e m1307b(IBinder iBinder) {
        return C0531a.m971e(iBinder);
    }

    protected String m1308b() {
        return "com.google.android.gms.appstate.service.START";
    }

    protected /* synthetic */ IInterface m1309c(IBinder iBinder) {
        return m1307b(iBinder);
    }

    protected String m1310c() {
        return "com.google.android.gms.appstate.internal.IAppStateService";
    }

    public void deleteState(OnStateDeletedListener listener, int stateKey) {
        try {
            ((C0136e) m995C()).m452b(new C1069a(this, listener), stateKey);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public int getMaxNumKeys() {
        try {
            return ((C0136e) m995C()).getMaxNumKeys();
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    public int getMaxStateSize() {
        try {
            return ((C0136e) m995C()).getMaxStateSize();
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
            return 2;
        }
    }

    public void listStates(OnStateListLoadedListener listener) {
        try {
            ((C0136e) m995C()).m447a(new C1070c(this, listener));
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void loadState(OnStateLoadedListener listener, int stateKey) {
        try {
            ((C0136e) m995C()).m448a(new C1071e(this, listener), stateKey);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void resolveState(OnStateLoadedListener listener, int stateKey, String resolvedVersion, byte[] resolvedData) {
        try {
            ((C0136e) m995C()).m449a(new C1071e(this, listener), stateKey, resolvedVersion, resolvedData);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }

    public void signOut(OnSignOutCompleteListener listener) {
        if (listener == null) {
            C0135d c0135d = null;
        } else {
            Object c1072g = new C1072g(this, listener);
        }
        try {
            ((C0136e) m995C()).m451b(c0135d);
        } catch (RemoteException e) {
            Log.w("AppStateClient", "service died");
        }
    }
}
