package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0535k.C0146b;
import com.google.android.gms.internal.C0535k.C0916d;
import com.google.android.gms.internal.bl.C0512a;
import com.google.android.gms.internal.bm.C0514a;
import com.google.android.gms.panorama.PanoramaClient.C0201a;
import com.google.android.gms.panorama.PanoramaClient.OnPanoramaInfoLoadedListener;

public class bn extends C0535k<bm> {

    /* renamed from: com.google.android.gms.internal.bn.a */
    final class C0515a extends C0146b<C0201a> {
        public final ConnectionResult hO;
        public final Intent hP;
        final /* synthetic */ bn hQ;
        public final int type;

        public C0515a(bn bnVar, C0201a c0201a, ConnectionResult connectionResult, int i, Intent intent) {
            this.hQ = bnVar;
            super(bnVar, c0201a);
            this.hO = connectionResult;
            this.type = i;
            this.hP = intent;
        }

        protected void m886a(C0201a c0201a) {
            if (c0201a != null) {
                c0201a.m594a(this.hO, this.type, this.hP);
            }
        }

        protected void m888d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.bn.c */
    final class C0516c extends C0146b<OnPanoramaInfoLoadedListener> {
        private final ConnectionResult hO;
        private final Intent hP;
        final /* synthetic */ bn hQ;

        public C0516c(bn bnVar, OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, ConnectionResult connectionResult, Intent intent) {
            this.hQ = bnVar;
            super(bnVar, onPanoramaInfoLoadedListener);
            this.hO = connectionResult;
            this.hP = intent;
        }

        protected void m889a(OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener) {
            if (onPanoramaInfoLoadedListener != null) {
                onPanoramaInfoLoadedListener.onPanoramaInfoLoaded(this.hO, this.hP);
            }
        }

        protected void m891d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.bn.b */
    final class C0901b extends C0512a {
        final /* synthetic */ bn hQ;
        private final C0201a hR;
        private final OnPanoramaInfoLoadedListener hS;
        private final Uri hT;

        public C0901b(bn bnVar, OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri) {
            this.hQ = bnVar;
            this.hR = null;
            this.hS = onPanoramaInfoLoadedListener;
            this.hT = uri;
        }

        public void m1251a(int i, Bundle bundle, int i2, Intent intent) {
            if (this.hT != null) {
                this.hQ.getContext().revokeUriPermission(this.hT, 1);
            }
            PendingIntent pendingIntent = null;
            if (bundle != null) {
                pendingIntent = (PendingIntent) bundle.getParcelable("pendingIntent");
            }
            ConnectionResult connectionResult = new ConnectionResult(i, pendingIntent);
            if (this.hR != null) {
                this.hQ.m998a(new C0515a(this.hQ, this.hR, connectionResult, i2, intent));
            } else {
                this.hQ.m998a(new C0516c(this.hQ, this.hS, connectionResult, intent));
            }
        }
    }

    public bn(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, connectionCallbacks, onConnectionFailedListener, (String[]) null);
    }

    public bm m1252X(IBinder iBinder) {
        return C0514a.m885W(iBinder);
    }

    public void m1253a(C0901b c0901b, Uri uri, Bundle bundle, boolean z) {
        m994B();
        if (z) {
            getContext().grantUriPermission(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, uri, 1);
        }
        try {
            ((bm) m995C()).m350a(c0901b, uri, bundle, z);
        } catch (RemoteException e) {
            c0901b.m1251a(8, null, 0, null);
        }
    }

    protected void m1254a(C0154p c0154p, C0916d c0916d) throws RemoteException {
        c0154p.m496a(c0916d, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), new Bundle());
    }

    public void m1255a(OnPanoramaInfoLoadedListener onPanoramaInfoLoadedListener, Uri uri, boolean z) {
        m1253a(new C0901b(this, onPanoramaInfoLoadedListener, z ? uri : null), uri, null, z);
    }

    protected String m1256b() {
        return "com.google.android.gms.panorama.service.START";
    }

    public /* synthetic */ IInterface m1257c(IBinder iBinder) {
        return m1252X(iBinder);
    }

    protected String m1258c() {
        return "com.google.android.gms.panorama.internal.IPanoramaService";
    }
}
