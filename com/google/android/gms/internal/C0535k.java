package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0153o.C0537a;
import com.google.android.gms.internal.C0154p.C0539a;
import java.util.ArrayList;
import org.joda.time.MutableDateTime;

/* renamed from: com.google.android.gms.internal.k */
public abstract class C0535k<T extends IInterface> implements GooglePlayServicesClient {
    public static final String[] bD;
    boolean bA;
    boolean bB;
    private final Object bC;
    private T bs;
    private ArrayList<ConnectionCallbacks> bt;
    final ArrayList<ConnectionCallbacks> bu;
    private boolean bv;
    private ArrayList<OnConnectionFailedListener> bw;
    private boolean bx;
    private final ArrayList<C0146b<?>> by;
    private C0147e bz;
    private final String[] f101f;
    private final Context mContext;
    final Handler mHandler;

    /* renamed from: com.google.android.gms.internal.k.a */
    final class C0145a extends Handler {
        final /* synthetic */ C0535k bE;

        public C0145a(C0535k c0535k, Looper looper) {
            this.bE = c0535k;
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (msg.what != 1 || this.bE.isConnecting()) {
                synchronized (this.bE.bC) {
                    this.bE.bB = false;
                }
                if (msg.what == 3) {
                    this.bE.m997a(new ConnectionResult(((Integer) msg.obj).intValue(), null));
                    return;
                } else if (msg.what == 4) {
                    synchronized (this.bE.bt) {
                        if (this.bE.bA && this.bE.isConnected() && this.bE.bt.contains(msg.obj)) {
                            ((ConnectionCallbacks) msg.obj).onConnected(this.bE.m1007z());
                        }
                    }
                    return;
                } else if (msg.what == 2 && !this.bE.isConnected()) {
                    C0146b c0146b = (C0146b) msg.obj;
                    c0146b.m467d();
                    c0146b.unregister();
                    return;
                } else if (msg.what == 2 || msg.what == 1) {
                    ((C0146b) msg.obj).m464D();
                    return;
                } else {
                    Log.wtf("GmsClient", "Don't know how to handle this message.");
                    return;
                }
            }
            c0146b = (C0146b) msg.obj;
            c0146b.m467d();
            c0146b.unregister();
        }
    }

    /* renamed from: com.google.android.gms.internal.k.b */
    protected abstract class C0146b<TListener> {
        final /* synthetic */ C0535k bE;
        private boolean bF;
        private TListener mListener;

        public C0146b(C0535k c0535k, TListener tListener) {
            this.bE = c0535k;
            this.mListener = tListener;
            this.bF = false;
        }

        public void m464D() {
            synchronized (this) {
                Object obj = this.mListener;
                if (this.bF) {
                    Log.w("GmsClient", "Callback proxy " + this + " being reused. This is not safe.");
                }
            }
            if (obj != null) {
                try {
                    m466a(obj);
                } catch (RuntimeException e) {
                    m467d();
                    throw e;
                }
            }
            m467d();
            synchronized (this) {
                this.bF = true;
            }
            unregister();
        }

        public void m465E() {
            synchronized (this) {
                this.mListener = null;
            }
        }

        protected abstract void m466a(TListener tListener);

        protected abstract void m467d();

        public void unregister() {
            m465E();
            synchronized (this.bE.by) {
                this.bE.by.remove(this);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.k.e */
    final class C0147e implements ServiceConnection {
        final /* synthetic */ C0535k bE;

        C0147e(C0535k c0535k) {
            this.bE = c0535k;
        }

        public void onServiceConnected(ComponentName component, IBinder binder) {
            this.bE.m1004f(binder);
        }

        public void onServiceDisconnected(ComponentName component) {
            this.bE.bs = null;
            this.bE.m993A();
        }
    }

    /* renamed from: com.google.android.gms.internal.k.c */
    public abstract class C0533c<TListener> extends C0146b<TListener> {
        private final C0468d f100S;
        final /* synthetic */ C0535k bE;

        public C0533c(C0535k c0535k, TListener tListener, C0468d c0468d) {
            this.bE = c0535k;
            super(c0535k, tListener);
            this.f100S = c0468d;
        }

        public /* bridge */ /* synthetic */ void m977D() {
            super.m464D();
        }

        public /* bridge */ /* synthetic */ void m978E() {
            super.m465E();
        }

        protected final void m979a(TListener tListener) {
            m980a(tListener, this.f100S);
        }

        protected abstract void m980a(TListener tListener, C0468d c0468d);

        protected void m981d() {
            if (this.f100S != null) {
                this.f100S.close();
            }
        }

        public /* bridge */ /* synthetic */ void unregister() {
            super.unregister();
        }
    }

    /* renamed from: com.google.android.gms.internal.k.f */
    protected final class C0534f extends C0146b<Boolean> {
        final /* synthetic */ C0535k bE;
        public final Bundle bH;
        public final IBinder bI;
        public final int statusCode;

        public C0534f(C0535k c0535k, int i, IBinder iBinder, Bundle bundle) {
            this.bE = c0535k;
            super(c0535k, Boolean.valueOf(true));
            this.statusCode = i;
            this.bI = iBinder;
            this.bH = bundle;
        }

        protected void m982a(Boolean bool) {
            if (bool != null) {
                switch (this.statusCode) {
                    case MutableDateTime.ROUND_NONE /*0*/:
                        try {
                            if (this.bE.m1003c().equals(this.bI.getInterfaceDescriptor())) {
                                this.bE.bs = this.bE.m1002c(this.bI);
                                if (this.bE.bs != null) {
                                    this.bE.m1006y();
                                    return;
                                }
                            }
                        } catch (RemoteException e) {
                        }
                        C0150l.m480g(this.bE.mContext).m482b(this.bE.m1001b(), this.bE.bz);
                        this.bE.bz = null;
                        this.bE.bs = null;
                        this.bE.m997a(new ConnectionResult(8, null));
                    case Value.ESCAPING_FIELD_NUMBER /*10*/:
                        throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                    default:
                        PendingIntent pendingIntent = this.bH != null ? (PendingIntent) this.bH.getParcelable("pendingIntent") : null;
                        if (this.bE.bz != null) {
                            C0150l.m480g(this.bE.mContext).m482b(this.bE.m1001b(), this.bE.bz);
                            this.bE.bz = null;
                        }
                        this.bE.bs = null;
                        this.bE.m997a(new ConnectionResult(this.statusCode, pendingIntent));
                }
            }
        }

        protected void m984d() {
        }
    }

    /* renamed from: com.google.android.gms.internal.k.d */
    public static final class C0916d extends C0537a {
        private C0535k bG;

        public C0916d(C0535k c0535k) {
            this.bG = c0535k;
        }

        public void m1372b(int i, IBinder iBinder, Bundle bundle) {
            C0159s.m514b((Object) "onPostInitComplete can be called only once per call to getServiceFromBroker", this.bG);
            this.bG.m996a(i, iBinder, bundle);
            this.bG = null;
        }
    }

    static {
        bD = new String[]{"service_esmobile", "service_googleme"};
    }

    protected C0535k(Context context, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String... strArr) {
        this.bu = new ArrayList();
        this.bv = false;
        this.bx = false;
        this.by = new ArrayList();
        this.bA = false;
        this.bB = false;
        this.bC = new Object();
        this.mContext = (Context) C0159s.m517d(context);
        this.bt = new ArrayList();
        this.bt.add(C0159s.m517d(connectionCallbacks));
        this.bw = new ArrayList();
        this.bw.add(C0159s.m517d(onConnectionFailedListener));
        this.mHandler = new C0145a(this, context.getMainLooper());
        m1000a(strArr);
        this.f101f = strArr;
    }

    protected final void m993A() {
        this.mHandler.removeMessages(4);
        synchronized (this.bt) {
            this.bv = true;
            ArrayList arrayList = this.bt;
            int size = arrayList.size();
            for (int i = 0; i < size && this.bA; i++) {
                if (this.bt.contains(arrayList.get(i))) {
                    ((ConnectionCallbacks) arrayList.get(i)).onDisconnected();
                }
            }
            this.bv = false;
        }
    }

    protected final void m994B() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    protected final T m995C() {
        m994B();
        return this.bs;
    }

    protected void m996a(int i, IBinder iBinder, Bundle bundle) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new C0534f(this, i, iBinder, bundle)));
    }

    protected void m997a(ConnectionResult connectionResult) {
        this.mHandler.removeMessages(4);
        synchronized (this.bw) {
            this.bx = true;
            ArrayList arrayList = this.bw;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                if (this.bA) {
                    if (this.bw.contains(arrayList.get(i))) {
                        ((OnConnectionFailedListener) arrayList.get(i)).onConnectionFailed(connectionResult);
                    }
                    i++;
                } else {
                    return;
                }
            }
            this.bx = false;
        }
    }

    public final void m998a(C0146b<?> c0146b) {
        synchronized (this.by) {
            this.by.add(c0146b);
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2, c0146b));
    }

    protected abstract void m999a(C0154p c0154p, C0916d c0916d) throws RemoteException;

    protected void m1000a(String... strArr) {
    }

    protected abstract String m1001b();

    protected abstract T m1002c(IBinder iBinder);

    protected abstract String m1003c();

    public void connect() {
        this.bA = true;
        synchronized (this.bC) {
            this.bB = true;
        }
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(isGooglePlayServicesAvailable)));
            return;
        }
        if (this.bz != null) {
            Log.e("GmsClient", "Calling connect() while still connected, missing disconnect().");
            this.bs = null;
            C0150l.m480g(this.mContext).m482b(m1001b(), this.bz);
        }
        this.bz = new C0147e(this);
        if (!C0150l.m480g(this.mContext).m481a(m1001b(), this.bz)) {
            Log.e("GmsClient", "unable to connect to service: " + m1001b());
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(9)));
        }
    }

    public void disconnect() {
        this.bA = false;
        synchronized (this.bC) {
            this.bB = false;
        }
        synchronized (this.by) {
            int size = this.by.size();
            for (int i = 0; i < size; i++) {
                ((C0146b) this.by.get(i)).m465E();
            }
            this.by.clear();
        }
        this.bs = null;
        if (this.bz != null) {
            C0150l.m480g(this.mContext).m482b(m1001b(), this.bz);
            this.bz = null;
        }
    }

    protected final void m1004f(IBinder iBinder) {
        try {
            m999a(C0539a.m1023h(iBinder), new C0916d(this));
        } catch (RemoteException e) {
            Log.w("GmsClient", "service died");
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public boolean isConnected() {
        return this.bs != null;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.bC) {
            z = this.bB;
        }
        return z;
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        boolean contains;
        C0159s.m517d(listener);
        synchronized (this.bt) {
            contains = this.bt.contains(listener);
        }
        return contains;
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        boolean contains;
        C0159s.m517d(listener);
        synchronized (this.bw) {
            contains = this.bw.contains(listener);
        }
        return contains;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        C0159s.m517d(listener);
        synchronized (this.bt) {
            if (this.bt.contains(listener)) {
                Log.w("GmsClient", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                if (this.bv) {
                    this.bt = new ArrayList(this.bt);
                }
                this.bt.add(listener);
            }
        }
        if (isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, listener));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        C0159s.m517d(listener);
        synchronized (this.bw) {
            if (this.bw.contains(listener)) {
                Log.w("GmsClient", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                if (this.bx) {
                    this.bw = new ArrayList(this.bw);
                }
                this.bw.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        C0159s.m517d(listener);
        synchronized (this.bt) {
            if (this.bt != null) {
                if (this.bv) {
                    this.bt = new ArrayList(this.bt);
                }
                if (!this.bt.remove(listener)) {
                    Log.w("GmsClient", "unregisterConnectionCallbacks(): listener " + listener + " not found");
                } else if (this.bv && !this.bu.contains(listener)) {
                    this.bu.add(listener);
                }
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        C0159s.m517d(listener);
        synchronized (this.bw) {
            if (this.bw != null) {
                if (this.bx) {
                    this.bw = new ArrayList(this.bw);
                }
                if (!this.bw.remove(listener)) {
                    Log.w("GmsClient", "unregisterConnectionFailedListener(): listener " + listener + " not found");
                }
            }
        }
    }

    public final String[] m1005x() {
        return this.f101f;
    }

    protected void m1006y() {
        boolean z = true;
        synchronized (this.bt) {
            C0159s.m511a(!this.bv);
            this.mHandler.removeMessages(4);
            this.bv = true;
            if (this.bu.size() != 0) {
                z = false;
            }
            C0159s.m511a(z);
            Bundle z2 = m1007z();
            ArrayList arrayList = this.bt;
            int size = arrayList.size();
            for (int i = 0; i < size && this.bA && isConnected(); i++) {
                this.bu.size();
                if (!this.bu.contains(arrayList.get(i))) {
                    ((ConnectionCallbacks) arrayList.get(i)).onConnected(z2);
                }
            }
            this.bu.clear();
            this.bv = false;
        }
    }

    protected Bundle m1007z() {
        return null;
    }
}
