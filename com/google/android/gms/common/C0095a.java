package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* renamed from: com.google.android.gms.common.a */
public class C0095a implements ServiceConnection {
    private final BlockingQueue<IBinder> f28A;
    boolean f29z;

    public C0095a() {
        this.f29z = false;
        this.f28A = new LinkedBlockingQueue();
    }

    public IBinder m29e() throws InterruptedException {
        if (this.f29z) {
            throw new IllegalStateException();
        }
        this.f29z = true;
        return (IBinder) this.f28A.take();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        try {
            this.f28A.put(service);
        } catch (InterruptedException e) {
        }
    }

    public void onServiceDisconnected(ComponentName name) {
    }
}
