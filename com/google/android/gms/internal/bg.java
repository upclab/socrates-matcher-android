package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.location.C0164a;
import com.google.android.gms.location.C0164a.C0546a;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.util.HashMap;

public class bg {
    private final bk<bf> fG;
    private ContentProviderClient fH;
    private boolean fI;
    private HashMap<LocationListener, C0899b> fJ;
    private final ContentResolver mContentResolver;

    /* renamed from: com.google.android.gms.internal.bg.a */
    private static class C0131a extends Handler {
        private final LocationListener fK;

        public C0131a(LocationListener locationListener) {
            this.fK = locationListener;
        }

        public C0131a(LocationListener locationListener, Looper looper) {
            super(looper);
            this.fK = locationListener;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    this.fK.onLocationChanged(new Location((Location) msg.obj));
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.bg.b */
    private static class C0899b extends C0546a {
        private Handler fL;

        C0899b(LocationListener locationListener, Looper looper) {
            this.fL = looper == null ? new C0131a(locationListener) : new C0131a(locationListener, looper);
        }

        public void onLocationChanged(Location location) {
            if (this.fL == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.fL.sendMessage(obtain);
        }

        public void release() {
            this.fL = null;
        }
    }

    public bg(Context context, bk<bf> bkVar) {
        this.fH = null;
        this.fI = false;
        this.fJ = new HashMap();
        this.fG = bkVar;
        this.mContentResolver = context.getContentResolver();
    }

    public void aR() {
        if (this.fI) {
            setMockMode(false);
        }
    }

    public Location getLastLocation() {
        this.fG.m347B();
        try {
            return ((bf) this.fG.m348C()).aQ();
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.fJ) {
                for (C0164a c0164a : this.fJ.values()) {
                    if (c0164a != null) {
                        ((bf) this.fG.m348C()).m341a(c0164a);
                    }
                }
                this.fJ.clear();
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(PendingIntent callbackIntent) {
        this.fG.m347B();
        try {
            ((bf) this.fG.m348C()).m336a(callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeLocationUpdates(LocationListener listener) {
        this.fG.m347B();
        C0159s.m514b((Object) listener, (Object) "Invalid null listener");
        synchronized (this.fJ) {
            C0164a c0164a = (C0899b) this.fJ.remove(listener);
            if (this.fH != null && this.fJ.isEmpty()) {
                this.fH.release();
                this.fH = null;
            }
            if (c0164a != null) {
                c0164a.release();
                try {
                    ((bf) this.fG.m348C()).m341a(c0164a);
                } catch (Throwable e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    public void requestLocationUpdates(LocationRequest request, PendingIntent callbackIntent) {
        this.fG.m347B();
        try {
            ((bf) this.fG.m348C()).m339a(request, callbackIntent);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void requestLocationUpdates(LocationRequest request, LocationListener listener, Looper looper) {
        this.fG.m347B();
        if (looper == null) {
            C0159s.m514b(Looper.myLooper(), (Object) "Can't create handler inside thread that has not called Looper.prepare()");
        }
        synchronized (this.fJ) {
            C0164a c0899b;
            C0899b c0899b2 = (C0899b) this.fJ.get(listener);
            if (c0899b2 == null) {
                c0899b = new C0899b(listener, looper);
            } else {
                Object obj = c0899b2;
            }
            this.fJ.put(listener, c0899b);
            try {
                ((bf) this.fG.m348C()).m340a(request, c0899b);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void setMockLocation(Location mockLocation) {
        this.fG.m347B();
        try {
            ((bf) this.fG.m348C()).setMockLocation(mockLocation);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void setMockMode(boolean isMockMode) {
        this.fG.m347B();
        try {
            ((bf) this.fG.m348C()).setMockMode(isMockMode);
            this.fI = isMockMode;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }
}
