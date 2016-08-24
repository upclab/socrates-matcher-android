package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0535k.C0146b;
import com.google.android.gms.internal.C0535k.C0533c;
import com.google.android.gms.internal.C0535k.C0916d;
import com.google.android.gms.internal.bs.C0524a;
import com.google.android.gms.plus.C0614a;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.PlusClient.OnAccessRevokedListener;
import com.google.android.gms.plus.PlusClient.OnMomentsLoadedListener;
import com.google.android.gms.plus.PlusClient.OnPeopleLoadedListener;
import com.google.android.gms.plus.model.moments.Moment;
import com.google.android.gms.plus.model.moments.MomentBuffer;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class bt extends C0535k<bs> {
    private Person ip;
    private C0614a iq;

    /* renamed from: com.google.android.gms.internal.bt.f */
    final class C0525f extends C0146b<OnAccessRevokedListener> {
        final /* synthetic */ bt is;
        private final ConnectionResult it;

        public C0525f(bt btVar, OnAccessRevokedListener onAccessRevokedListener, ConnectionResult connectionResult) {
            this.is = btVar;
            super(btVar, onAccessRevokedListener);
            this.it = connectionResult;
        }

        protected void m948a(OnAccessRevokedListener onAccessRevokedListener) {
            this.is.disconnect();
            if (onAccessRevokedListener != null) {
                onAccessRevokedListener.onAccessRevoked(this.it);
            }
        }

        protected void m950d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.bt.b */
    final class C0902b extends C0533c<OnMomentsLoadedListener> {
        final /* synthetic */ bt is;
        private final ConnectionResult it;
        private final String iu;
        private final String iv;

        public C0902b(bt btVar, OnMomentsLoadedListener onMomentsLoadedListener, ConnectionResult connectionResult, C0468d c0468d, String str, String str2) {
            this.is = btVar;
            super(btVar, onMomentsLoadedListener, c0468d);
            this.it = connectionResult;
            this.iu = str;
            this.iv = str2;
        }

        protected void m1276a(OnMomentsLoadedListener onMomentsLoadedListener, C0468d c0468d) {
            onMomentsLoadedListener.onMomentsLoaded(this.it, c0468d != null ? new MomentBuffer(c0468d) : null, this.iu, this.iv);
        }
    }

    /* renamed from: com.google.android.gms.internal.bt.d */
    final class C0903d extends C0533c<OnPeopleLoadedListener> {
        final /* synthetic */ bt is;
        private final ConnectionResult it;
        private final String iu;

        public C0903d(bt btVar, OnPeopleLoadedListener onPeopleLoadedListener, ConnectionResult connectionResult, C0468d c0468d, String str) {
            this.is = btVar;
            super(btVar, onPeopleLoadedListener, c0468d);
            this.it = connectionResult;
            this.iu = str;
        }

        protected void m1278a(OnPeopleLoadedListener onPeopleLoadedListener, C0468d c0468d) {
            onPeopleLoadedListener.onPeopleLoaded(this.it, c0468d != null ? new PersonBuffer(c0468d) : null, this.iu);
        }
    }

    /* renamed from: com.google.android.gms.internal.bt.a */
    final class C1066a extends bo {
        private final OnMomentsLoadedListener ir;
        final /* synthetic */ bt is;

        public C1066a(bt btVar, OnMomentsLoadedListener onMomentsLoadedListener) {
            this.is = btVar;
            this.ir = onMomentsLoadedListener;
        }

        public void m1443a(C0468d c0468d, String str, String str2) {
            C0468d c0468d2;
            ConnectionResult connectionResult = new ConnectionResult(c0468d.getStatusCode(), c0468d.m644l() != null ? (PendingIntent) c0468d.m644l().getParcelable("pendingIntent") : null);
            if (connectionResult.isSuccess() || c0468d == null) {
                c0468d2 = c0468d;
            } else {
                if (!c0468d.isClosed()) {
                    c0468d.close();
                }
                c0468d2 = null;
            }
            this.is.m998a(new C0902b(this.is, this.ir, connectionResult, c0468d2, str, str2));
        }
    }

    /* renamed from: com.google.android.gms.internal.bt.c */
    final class C1067c extends bo {
        final /* synthetic */ bt is;
        private final OnPeopleLoadedListener iw;

        public C1067c(bt btVar, OnPeopleLoadedListener onPeopleLoadedListener) {
            this.is = btVar;
            this.iw = onPeopleLoadedListener;
        }

        public void m1444a(C0468d c0468d, String str) {
            C0468d c0468d2;
            ConnectionResult connectionResult = new ConnectionResult(c0468d.getStatusCode(), c0468d.m644l() != null ? (PendingIntent) c0468d.m644l().getParcelable("pendingIntent") : null);
            if (connectionResult.isSuccess() || c0468d == null) {
                c0468d2 = c0468d;
            } else {
                if (!c0468d.isClosed()) {
                    c0468d.close();
                }
                c0468d2 = null;
            }
            this.is.m998a(new C0903d(this.is, this.iw, connectionResult, c0468d2, str));
        }
    }

    /* renamed from: com.google.android.gms.internal.bt.e */
    final class C1068e extends bo {
        final /* synthetic */ bt is;
        private final OnAccessRevokedListener ix;

        public C1068e(bt btVar, OnAccessRevokedListener onAccessRevokedListener) {
            this.is = btVar;
            this.ix = onAccessRevokedListener;
        }

        public void m1445b(int i, Bundle bundle) {
            PendingIntent pendingIntent = null;
            if (bundle != null) {
                pendingIntent = (PendingIntent) bundle.getParcelable("pendingIntent");
            }
            this.is.m998a(new C0525f(this.is, this.ix, new ConnectionResult(i, pendingIntent)));
        }
    }

    public bt(Context context, C0614a c0614a, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, connectionCallbacks, onConnectionFailedListener, c0614a.by());
        this.iq = c0614a;
    }

    public boolean m1280F(String str) {
        return Arrays.asList(m1005x()).contains(str);
    }

    protected void m1281a(int i, IBinder iBinder, Bundle bundle) {
        if (i == 0 && bundle != null && bundle.containsKey("loaded_person")) {
            this.ip = cc.m1365d(bundle.getByteArray("loaded_person"));
        }
        super.m996a(i, iBinder, bundle);
    }

    protected void m1282a(C0154p c0154p, C0916d c0916d) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("skip_oob", false);
        bundle.putStringArray(PlusClient.KEY_REQUEST_VISIBLE_ACTIVITIES, this.iq.bz());
        if (this.iq.bA() != null) {
            bundle.putStringArray("required_features", this.iq.bA());
        }
        if (this.iq.bD() != null) {
            bundle.putString("application_name", this.iq.bD());
        }
        c0154p.m498a(c0916d, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, this.iq.bC(), this.iq.bB(), m1005x(), this.iq.getAccountName(), bundle);
    }

    public void m1283a(OnPeopleLoadedListener onPeopleLoadedListener, Collection<String> collection) {
        m994B();
        bp c1067c = new C1067c(this, onPeopleLoadedListener);
        try {
            ((bs) m995C()).m391a(c1067c, new ArrayList(collection));
        } catch (RemoteException e) {
            c1067c.m1444a(C0468d.m630f(8), null);
        }
    }

    public void m1284a(OnPeopleLoadedListener onPeopleLoadedListener, String[] strArr) {
        m1283a(onPeopleLoadedListener, Arrays.asList(strArr));
    }

    protected bs ac(IBinder iBinder) {
        return C0524a.ab(iBinder);
    }

    protected String m1285b() {
        return "com.google.android.gms.plus.service.START";
    }

    protected /* synthetic */ IInterface m1286c(IBinder iBinder) {
        return ac(iBinder);
    }

    protected String m1287c() {
        return "com.google.android.gms.plus.internal.IPlusService";
    }

    public void clearDefaultAccount() {
        m994B();
        try {
            this.ip = null;
            ((bs) m995C()).clearDefaultAccount();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public String getAccountName() {
        m994B();
        try {
            return ((bs) m995C()).getAccountName();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public Person getCurrentPerson() {
        m994B();
        return this.ip;
    }

    public void loadMoments(OnMomentsLoadedListener listener) {
        loadMoments(listener, 20, null, null, null, "me");
    }

    public void loadMoments(OnMomentsLoadedListener listener, int maxResults, String pageToken, Uri targetUrl, String type, String userId) {
        m994B();
        Object c1066a = listener != null ? new C1066a(this, listener) : null;
        try {
            ((bs) m995C()).m376a(c1066a, maxResults, pageToken, targetUrl, type, userId);
        } catch (RemoteException e) {
            c1066a.m1443a(C0468d.m630f(8), null, null);
        }
    }

    public void loadVisiblePeople(OnPeopleLoadedListener listener, int orderBy, String pageToken) {
        m994B();
        bp c1067c = new C1067c(this, listener);
        try {
            ((bs) m995C()).m372a(c1067c, 1, orderBy, -1, pageToken);
        } catch (RemoteException e) {
            c1067c.m1444a(C0468d.m630f(8), null);
        }
    }

    public void loadVisiblePeople(OnPeopleLoadedListener listener, String pageToken) {
        loadVisiblePeople(listener, 0, pageToken);
    }

    public void removeMoment(String momentId) {
        m994B();
        try {
            ((bs) m995C()).removeMoment(momentId);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void revokeAccessAndDisconnect(OnAccessRevokedListener listener) {
        m994B();
        clearDefaultAccount();
        Object c1068e = new C1068e(this, listener);
        try {
            ((bs) m995C()).m395c(c1068e);
        } catch (RemoteException e) {
            c1068e.m1445b(8, null);
        }
    }

    public void writeMoment(Moment moment) {
        m994B();
        try {
            ((bs) m995C()).m370a(ak.m697a((bz) moment));
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
