package com.squareup.picasso;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

class Dispatcher {
    static final int AIRPLANE_MODE_CHANGE = 10;
    private static final int AIRPLANE_MODE_OFF = 0;
    private static final int AIRPLANE_MODE_ON = 1;
    private static final int BATCH_DELAY = 200;
    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";
    static final int HUNTER_BATCH_COMPLETE = 8;
    static final int HUNTER_COMPLETE = 4;
    static final int HUNTER_DECODE_FAILED = 6;
    static final int HUNTER_DELAY_NEXT_BATCH = 7;
    static final int HUNTER_RETRY = 5;
    static final int NETWORK_STATE_CHANGE = 9;
    static final int REQUEST_CANCEL = 2;
    static final int REQUEST_GCED = 3;
    static final int REQUEST_SUBMIT = 1;
    private static final int RETRY_DELAY = 500;
    boolean airplaneMode;
    final List<BitmapHunter> batch;
    final Cache cache;
    final Context context;
    final DispatcherThread dispatcherThread;
    final Downloader downloader;
    final Handler handler;
    final Map<String, BitmapHunter> hunterMap;
    final Handler mainThreadHandler;
    NetworkInfo networkInfo;
    final NetworkBroadcastReceiver receiver;
    final ExecutorService service;
    final Stats stats;

    private static class DispatcherHandler extends Handler {
        private final Dispatcher dispatcher;

        /* renamed from: com.squareup.picasso.Dispatcher.DispatcherHandler.1 */
        class C03661 implements Runnable {
            final /* synthetic */ Message val$msg;

            C03661(Message message) {
                this.val$msg = message;
            }

            public void run() {
                throw new AssertionError("Unknown handler message received: " + this.val$msg.what);
            }
        }

        public DispatcherHandler(Looper looper, Dispatcher dispatcher) {
            super(looper);
            this.dispatcher = dispatcher;
        }

        public void handleMessage(Message msg) {
            boolean z = true;
            switch (msg.what) {
                case Dispatcher.REQUEST_SUBMIT /*1*/:
                    this.dispatcher.performSubmit(msg.obj);
                case Dispatcher.REQUEST_CANCEL /*2*/:
                    this.dispatcher.performCancel((Action) msg.obj);
                case Dispatcher.HUNTER_COMPLETE /*4*/:
                    this.dispatcher.performComplete(msg.obj);
                case Dispatcher.HUNTER_RETRY /*5*/:
                    this.dispatcher.performRetry((BitmapHunter) msg.obj);
                case Dispatcher.HUNTER_DECODE_FAILED /*6*/:
                    this.dispatcher.performError((BitmapHunter) msg.obj);
                case Dispatcher.HUNTER_DELAY_NEXT_BATCH /*7*/:
                    this.dispatcher.performBatchComplete();
                case Dispatcher.NETWORK_STATE_CHANGE /*9*/:
                    this.dispatcher.performNetworkStateChange(msg.obj);
                case Dispatcher.AIRPLANE_MODE_CHANGE /*10*/:
                    Dispatcher dispatcher = this.dispatcher;
                    if (msg.arg1 != Dispatcher.REQUEST_SUBMIT) {
                        z = false;
                    }
                    dispatcher.performAirplaneModeChange(z);
                default:
                    Picasso.HANDLER.post(new C03661(msg));
            }
        }
    }

    static class DispatcherThread extends HandlerThread {
        DispatcherThread() {
            super("Picasso-Dispatcher", Dispatcher.AIRPLANE_MODE_CHANGE);
        }
    }

    private class NetworkBroadcastReceiver extends BroadcastReceiver {
        private static final String EXTRA_AIRPLANE_STATE = "state";
        private final ConnectivityManager connectivityManager;

        NetworkBroadcastReceiver(Context context) {
            this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        }

        void register() {
            boolean shouldScanState = (Dispatcher.this.service instanceof PicassoExecutorService) && Utils.hasPermission(Dispatcher.this.context, "android.permission.ACCESS_NETWORK_STATE");
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.AIRPLANE_MODE");
            if (shouldScanState) {
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            }
            Dispatcher.this.context.registerReceiver(this, filter);
        }

        void unregister() {
            Dispatcher.this.context.unregisterReceiver(this);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                Bundle extras = intent.getExtras();
                if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                    Dispatcher.this.dispatchAirplaneModeChange(extras.getBoolean(EXTRA_AIRPLANE_STATE, false));
                } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                    Dispatcher.this.dispatchNetworkStateChange(this.connectivityManager.getActiveNetworkInfo());
                }
            }
        }
    }

    Dispatcher(Context context, ExecutorService service, Handler mainThreadHandler, Downloader downloader, Cache cache, Stats stats) {
        this.dispatcherThread = new DispatcherThread();
        this.dispatcherThread.start();
        this.context = context;
        this.service = service;
        this.hunterMap = new LinkedHashMap();
        this.handler = new DispatcherHandler(this.dispatcherThread.getLooper(), this);
        this.downloader = downloader;
        this.mainThreadHandler = mainThreadHandler;
        this.cache = cache;
        this.stats = stats;
        this.batch = new ArrayList(HUNTER_COMPLETE);
        this.airplaneMode = Utils.isAirplaneModeOn(this.context);
        this.receiver = new NetworkBroadcastReceiver(this.context);
        this.receiver.register();
    }

    void shutdown() {
        this.service.shutdown();
        this.dispatcherThread.quit();
        this.receiver.unregister();
    }

    void dispatchSubmit(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(REQUEST_SUBMIT, action));
    }

    void dispatchCancel(Action action) {
        this.handler.sendMessage(this.handler.obtainMessage(REQUEST_CANCEL, action));
    }

    void dispatchComplete(BitmapHunter hunter) {
        this.handler.sendMessage(this.handler.obtainMessage(HUNTER_COMPLETE, hunter));
    }

    void dispatchRetry(BitmapHunter hunter) {
        this.handler.sendMessageDelayed(this.handler.obtainMessage(HUNTER_RETRY, hunter), 500);
    }

    void dispatchFailed(BitmapHunter hunter) {
        this.handler.sendMessage(this.handler.obtainMessage(HUNTER_DECODE_FAILED, hunter));
    }

    void dispatchNetworkStateChange(NetworkInfo info) {
        this.handler.sendMessage(this.handler.obtainMessage(NETWORK_STATE_CHANGE, info));
    }

    void dispatchAirplaneModeChange(boolean airplaneMode) {
        int i;
        Handler handler = this.handler;
        Handler handler2 = this.handler;
        if (airplaneMode) {
            i = REQUEST_SUBMIT;
        } else {
            i = AIRPLANE_MODE_OFF;
        }
        handler.sendMessage(handler2.obtainMessage(AIRPLANE_MODE_CHANGE, i, AIRPLANE_MODE_OFF));
    }

    void performSubmit(Action action) {
        BitmapHunter hunter = (BitmapHunter) this.hunterMap.get(action.getKey());
        if (hunter != null) {
            hunter.attach(action);
        } else if (!this.service.isShutdown()) {
            hunter = BitmapHunter.forRequest(this.context, action.getPicasso(), this, this.cache, this.stats, action, this.downloader);
            hunter.future = this.service.submit(hunter);
            this.hunterMap.put(action.getKey(), hunter);
        }
    }

    void performCancel(Action action) {
        String key = action.getKey();
        BitmapHunter hunter = (BitmapHunter) this.hunterMap.get(key);
        if (hunter != null) {
            hunter.detach(action);
            if (hunter.cancel()) {
                this.hunterMap.remove(key);
            }
        }
    }

    void performRetry(BitmapHunter hunter) {
        if (!hunter.isCancelled()) {
            if (this.service.isShutdown()) {
                performError(hunter);
            } else if (hunter.shouldRetry(this.airplaneMode, this.networkInfo)) {
                hunter.future = this.service.submit(hunter);
            } else {
                performError(hunter);
            }
        }
    }

    void performComplete(BitmapHunter hunter) {
        if (!hunter.shouldSkipMemoryCache()) {
            this.cache.set(hunter.getKey(), hunter.getResult());
        }
        this.hunterMap.remove(hunter.getKey());
        batch(hunter);
    }

    void performBatchComplete() {
        List<BitmapHunter> copy = new ArrayList(this.batch);
        this.batch.clear();
        this.mainThreadHandler.sendMessage(this.mainThreadHandler.obtainMessage(HUNTER_BATCH_COMPLETE, copy));
    }

    void performError(BitmapHunter hunter) {
        this.hunterMap.remove(hunter.getKey());
        batch(hunter);
    }

    void performAirplaneModeChange(boolean airplaneMode) {
        this.airplaneMode = airplaneMode;
    }

    void performNetworkStateChange(NetworkInfo info) {
        this.networkInfo = info;
        if (this.service instanceof PicassoExecutorService) {
            ((PicassoExecutorService) this.service).adjustThreadCount(info);
        }
    }

    private void batch(BitmapHunter hunter) {
        if (!hunter.isCancelled()) {
            this.batch.add(hunter);
            if (!this.handler.hasMessages(HUNTER_DELAY_NEXT_BATCH)) {
                this.handler.sendEmptyMessageDelayed(HUNTER_DELAY_NEXT_BATCH, 200);
            }
        }
    }
}
