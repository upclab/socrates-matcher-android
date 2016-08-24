package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0535k.C0146b;
import com.google.android.gms.internal.C0535k.C0916d;
import com.google.android.gms.internal.be.C0505a;
import com.google.android.gms.internal.bf.C0507a;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.List;

public class bh extends C0535k<bf> {
    private final bk<bf> fG;
    private final bg fM;
    private final String fN;

    /* renamed from: com.google.android.gms.internal.bh.a */
    private final class C0508a extends C0146b<OnAddGeofencesResultListener> {
        private final String[] fO;
        final /* synthetic */ bh fP;
        private final int f86p;

        public C0508a(bh bhVar, OnAddGeofencesResultListener onAddGeofencesResultListener, int i, String[] strArr) {
            this.fP = bhVar;
            super(bhVar, onAddGeofencesResultListener);
            this.f86p = LocationStatusCodes.m531O(i);
            this.fO = strArr;
        }

        protected void m867a(OnAddGeofencesResultListener onAddGeofencesResultListener) {
            if (onAddGeofencesResultListener != null) {
                onAddGeofencesResultListener.onAddGeofencesResult(this.f86p, this.fO);
            }
        }

        protected void m869d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.bh.c */
    private final class C0509c implements bk<bf> {
        final /* synthetic */ bh fP;

        private C0509c(bh bhVar) {
            this.fP = bhVar;
        }

        public void m870B() {
            this.fP.m994B();
        }

        public /* synthetic */ IInterface m871C() {
            return aS();
        }

        public bf aS() {
            return (bf) this.fP.m995C();
        }
    }

    /* renamed from: com.google.android.gms.internal.bh.d */
    private final class C0510d extends C0146b<OnRemoveGeofencesResultListener> {
        private final String[] fO;
        final /* synthetic */ bh fP;
        private final int fT;
        private final PendingIntent mPendingIntent;
        private final int f87p;

        public C0510d(bh bhVar, int i, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, PendingIntent pendingIntent) {
            boolean z = true;
            this.fP = bhVar;
            super(bhVar, onRemoveGeofencesResultListener);
            if (i != 1) {
                z = false;
            }
            C0143h.m459a(z);
            this.fT = i;
            this.f87p = LocationStatusCodes.m531O(i2);
            this.mPendingIntent = pendingIntent;
            this.fO = null;
        }

        public C0510d(bh bhVar, int i, OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, int i2, String[] strArr) {
            this.fP = bhVar;
            super(bhVar, onRemoveGeofencesResultListener);
            C0143h.m459a(i == 2);
            this.fT = i;
            this.f87p = LocationStatusCodes.m531O(i2);
            this.fO = strArr;
            this.mPendingIntent = null;
        }

        protected void m872a(OnRemoveGeofencesResultListener onRemoveGeofencesResultListener) {
            if (onRemoveGeofencesResultListener != null) {
                switch (this.fT) {
                    case Value.TYPE_FIELD_NUMBER /*1*/:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByPendingIntentResult(this.f87p, this.mPendingIntent);
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        onRemoveGeofencesResultListener.onRemoveGeofencesByRequestIdsResult(this.f87p, this.fO);
                    default:
                        Log.wtf("LocationClientImpl", "Unsupported action: " + this.fT);
                }
            }
        }

        protected void m874d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.bh.b */
    private static final class C0900b extends C0505a {
        private OnAddGeofencesResultListener fQ;
        private OnRemoveGeofencesResultListener fR;
        private bh fS;

        public C0900b(OnAddGeofencesResultListener onAddGeofencesResultListener, bh bhVar) {
            this.fQ = onAddGeofencesResultListener;
            this.fR = null;
            this.fS = bhVar;
        }

        public C0900b(OnRemoveGeofencesResultListener onRemoveGeofencesResultListener, bh bhVar) {
            this.fR = onRemoveGeofencesResultListener;
            this.fQ = null;
            this.fS = bhVar;
        }

        public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds) throws RemoteException {
            if (this.fS == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            bh bhVar = this.fS;
            bh bhVar2 = this.fS;
            bhVar2.getClass();
            bhVar.m998a(new C0508a(bhVar2, this.fQ, statusCode, geofenceRequestIds));
            this.fS = null;
            this.fQ = null;
            this.fR = null;
        }

        public void onRemoveGeofencesByPendingIntentResult(int statusCode, PendingIntent pendingIntent) {
            if (this.fS == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
                return;
            }
            bh bhVar = this.fS;
            bh bhVar2 = this.fS;
            bhVar2.getClass();
            bhVar.m998a(new C0510d(bhVar2, 1, this.fR, statusCode, pendingIntent));
            this.fS = null;
            this.fQ = null;
            this.fR = null;
        }

        public void onRemoveGeofencesByRequestIdsResult(int statusCode, String[] geofenceRequestIds) {
            if (this.fS == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
                return;
            }
            bh bhVar = this.fS;
            bh bhVar2 = this.fS;
            bhVar2.getClass();
            bhVar.m998a(new C0510d(bhVar2, 2, this.fR, statusCode, geofenceRequestIds));
            this.fS = null;
            this.fQ = null;
            this.fR = null;
        }
    }

    public bh(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str) {
        super(context, connectionCallbacks, onConnectionFailedListener, new String[0]);
        this.fG = new C0509c();
        this.fM = new bg(context, this.fG);
        this.fN = str;
    }

    protected void m1246a(C0154p c0154p, C0916d c0916d) throws RemoteException {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.fN);
        c0154p.m503e(c0916d, GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE, getContext().getPackageName(), bundle);
    }

    public void addGeofences(List<bi> geofences, PendingIntent pendingIntent, OnAddGeofencesResultListener listener) {
        m994B();
        boolean z = geofences != null && geofences.size() > 0;
        C0159s.m515b(z, (Object) "At least one geofence must be specified.");
        C0159s.m514b((Object) pendingIntent, (Object) "PendingIntent must be specified.");
        C0159s.m514b((Object) listener, (Object) "OnAddGeofencesResultListener not provided.");
        if (listener == null) {
            be beVar = null;
        } else {
            Object c0900b = new C0900b(listener, this);
        }
        try {
            ((bf) m995C()).m342a(geofences, pendingIntent, beVar, getContext().getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    protected String m1247b() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    protected /* synthetic */ IInterface m1248c(IBinder iBinder) {
        return m1250s(iBinder);
    }

    protected String m1249c() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    public void disconnect() {
        synchronized (this.fM) {
            if (isConnected()) {
                this.fM.removeAllListeners();
                this.fM.aR();
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.fM.getLastLocation();
    }

    public void removeActivityUpdates(PendingIntent callbackIntent) {
        m994B();
        C0159s.m517d(callbackIntent);
        try {
            ((bf) m995C()).removeActivityUpdates(callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeGeofences(PendingIntent pendingIntent, OnRemoveGeofencesResultListener listener) {
        m994B();
        C0159s.m514b((Object) pendingIntent, (Object) "PendingIntent must be specified.");
        C0159s.m514b((Object) listener, (Object) "OnRemoveGeofencesResultListener not provided.");
        if (listener == null) {
            be beVar = null;
        } else {
            Object c0900b = new C0900b(listener, this);
        }
        try {
            ((bf) m995C()).m337a(pendingIntent, beVar, getContext().getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeGeofences(List<String> geofenceRequestIds, OnRemoveGeofencesResultListener listener) {
        m994B();
        boolean z = geofenceRequestIds != null && geofenceRequestIds.size() > 0;
        C0159s.m515b(z, (Object) "geofenceRequestIds can't be null nor empty.");
        C0159s.m514b((Object) listener, (Object) "OnRemoveGeofencesResultListener not provided.");
        String[] strArr = (String[]) geofenceRequestIds.toArray(new String[0]);
        if (listener == null) {
            be beVar = null;
        } else {
            Object c0900b = new C0900b(listener, this);
        }
        try {
            ((bf) m995C()).m343a(strArr, beVar, getContext().getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent callbackIntent) {
        this.fM.removeLocationUpdates(callbackIntent);
    }

    public void removeLocationUpdates(LocationListener listener) {
        this.fM.removeLocationUpdates(listener);
    }

    public void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent) {
        boolean z = true;
        m994B();
        C0159s.m517d(callbackIntent);
        if (detectionIntervalMillis < 0) {
            z = false;
        }
        C0159s.m515b(z, (Object) "detectionIntervalMillis must be >= 0");
        try {
            ((bf) m995C()).m335a(detectionIntervalMillis, true, callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        this.fM.requestLocationUpdates(request, callbackIntent);
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener) {
        requestLocationUpdates(request, listener, null);
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
        synchronized (this.fM) {
            this.fM.requestLocationUpdates(request, listener, looper);
        }
    }

    protected bf m1250s(IBinder iBinder) {
        return C0507a.m866r(iBinder);
    }

    public void setMockLocation(Location mockLocation) {
        this.fM.setMockLocation(mockLocation);
    }

    public void setMockMode(boolean isMockMode) {
        this.fM.setMockMode(isMockMode);
    }
}
