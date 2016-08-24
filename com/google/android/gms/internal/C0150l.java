package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0535k.C0147e;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.joda.time.MutableDateTime;

/* renamed from: com.google.android.gms.internal.l */
public final class C0150l implements Callback {
    private static final Object bJ;
    private static C0150l bK;
    private final Context bL;
    private final HashMap<String, C0149a> bM;
    private final Handler mHandler;

    /* renamed from: com.google.android.gms.internal.l.a */
    final class C0149a {
        private final String bN;
        private final C0148a bO;
        private final HashSet<C0147e> bP;
        private boolean bQ;
        private IBinder bR;
        private ComponentName bS;
        final /* synthetic */ C0150l bT;
        private int mState;

        /* renamed from: com.google.android.gms.internal.l.a.a */
        public class C0148a implements ServiceConnection {
            final /* synthetic */ C0149a bU;

            public C0148a(C0149a c0149a) {
                this.bU = c0149a;
            }

            public void onServiceConnected(ComponentName component, IBinder binder) {
                synchronized (this.bU.bT.bM) {
                    this.bU.bR = binder;
                    this.bU.bS = component;
                    Iterator it = this.bU.bP.iterator();
                    while (it.hasNext()) {
                        ((C0147e) it.next()).onServiceConnected(component, binder);
                    }
                    this.bU.mState = 1;
                }
            }

            public void onServiceDisconnected(ComponentName component) {
                synchronized (this.bU.bT.bM) {
                    this.bU.bR = null;
                    this.bU.bS = component;
                    Iterator it = this.bU.bP.iterator();
                    while (it.hasNext()) {
                        ((C0147e) it.next()).onServiceDisconnected(component);
                    }
                    this.bU.mState = 2;
                }
            }
        }

        public C0149a(C0150l c0150l, String str) {
            this.bT = c0150l;
            this.bN = str;
            this.bO = new C0148a(this);
            this.bP = new HashSet();
            this.mState = 0;
        }

        public C0148a m472F() {
            return this.bO;
        }

        public String m473G() {
            return this.bN;
        }

        public boolean m474H() {
            return this.bP.isEmpty();
        }

        public void m475a(C0147e c0147e) {
            this.bP.add(c0147e);
        }

        public void m476b(C0147e c0147e) {
            this.bP.remove(c0147e);
        }

        public void m477b(boolean z) {
            this.bQ = z;
        }

        public boolean m478c(C0147e c0147e) {
            return this.bP.contains(c0147e);
        }

        public IBinder getBinder() {
            return this.bR;
        }

        public ComponentName getComponentName() {
            return this.bS;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.bQ;
        }
    }

    static {
        bJ = new Object();
    }

    private C0150l(Context context) {
        this.mHandler = new Handler(context.getMainLooper(), this);
        this.bM = new HashMap();
        this.bL = context.getApplicationContext();
    }

    public static C0150l m480g(Context context) {
        synchronized (bJ) {
            if (bK == null) {
                bK = new C0150l(context.getApplicationContext());
            }
        }
        return bK;
    }

    public boolean m481a(String str, C0147e c0147e) {
        boolean isBound;
        synchronized (this.bM) {
            C0149a c0149a = (C0149a) this.bM.get(str);
            if (c0149a != null) {
                this.mHandler.removeMessages(0, c0149a);
                if (!c0149a.m478c(c0147e)) {
                    c0149a.m475a((C0147e) c0147e);
                    switch (c0149a.getState()) {
                        case Value.TYPE_FIELD_NUMBER /*1*/:
                            c0147e.onServiceConnected(c0149a.getComponentName(), c0149a.getBinder());
                            break;
                        case Value.STRING_FIELD_NUMBER /*2*/:
                            c0149a.m477b(this.bL.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), c0149a.m472F(), 129));
                            break;
                        default:
                            break;
                    }
                }
                throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  startServiceAction=" + str);
            }
            c0149a = new C0149a(this, str);
            c0149a.m475a((C0147e) c0147e);
            c0149a.m477b(this.bL.bindService(new Intent(str).setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE), c0149a.m472F(), 129));
            this.bM.put(str, c0149a);
            isBound = c0149a.isBound();
        }
        return isBound;
    }

    public void m482b(String str, C0147e c0147e) {
        synchronized (this.bM) {
            C0149a c0149a = (C0149a) this.bM.get(str);
            if (c0149a == null) {
                throw new IllegalStateException("Nonexistent connection status for service action: " + str);
            } else if (c0149a.m478c(c0147e)) {
                c0149a.m476b((C0147e) c0147e);
                if (c0149a.m474H()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(0, c0149a), 5000);
                }
            } else {
                throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  startServiceAction=" + str);
            }
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MutableDateTime.ROUND_NONE /*0*/:
                C0149a c0149a = (C0149a) msg.obj;
                synchronized (this.bM) {
                    if (c0149a.m474H()) {
                        this.bL.unbindService(c0149a.m472F());
                        this.bM.remove(c0149a.m473G());
                    }
                    break;
                }
                return true;
            default:
                return false;
        }
    }
}
