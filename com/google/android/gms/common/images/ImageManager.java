package com.google.android.gms.common.images;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.ImageView;
import com.google.android.gms.common.images.C0106a.C0105a;
import com.google.android.gms.internal.C0143h;
import com.google.android.gms.internal.C0162w;
import com.google.android.gms.internal.as;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ImageManager {
    private static final Object aq;
    private static HashSet<Uri> ar;
    private static ImageManager as;
    private static ImageManager at;
    private final ExecutorService au;
    private final C0470b av;
    private final Map<C0106a, ImageReceiver> aw;
    private final Map<Uri, ImageReceiver> ax;
    private final Context mContext;
    private final Handler mHandler;

    private final class ImageReceiver extends ResultReceiver {
        final /* synthetic */ ImageManager aA;
        private final ArrayList<C0106a> ay;
        boolean az;
        private final Uri mUri;

        ImageReceiver(ImageManager imageManager, Uri uri) {
            this.aA = imageManager;
            super(new Handler(Looper.getMainLooper()));
            this.az = false;
            this.mUri = uri;
            this.ay = new ArrayList();
        }

        public void m39c(C0106a c0106a) {
            C0143h.m460a(!this.az, "Cannot add an ImageRequest when mHandlingRequests is true");
            C0143h.m462f("ImageReceiver.addImageRequest() must be called in the main thread");
            this.ay.add(c0106a);
        }

        public void m40d(C0106a c0106a) {
            C0143h.m460a(!this.az, "Cannot remove an ImageRequest when mHandlingRequests is true");
            C0143h.m462f("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.ay.remove(c0106a);
        }

        public void onReceiveResult(int resultCode, Bundle resultData) {
            this.aA.au.execute(new C0101c(this.aA, this.mUri, (ParcelFileDescriptor) resultData.getParcelable("com.google.android.gms.extra.fileDescriptor")));
        }

        public void m41q() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.putExtra("com.google.android.gms.extras.uri", this.mUri);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            this.aA.mContext.sendBroadcast(intent);
        }
    }

    public interface OnImageLoadedListener {
        void onImageLoaded(Uri uri, Drawable drawable);
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.a */
    private static final class C0100a {
        static int m42a(ActivityManager activityManager) {
            return activityManager.getLargeMemoryClass();
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.c */
    private final class C0101c implements Runnable {
        final /* synthetic */ ImageManager aA;
        private final ParcelFileDescriptor aB;
        private final Uri mUri;

        public C0101c(ImageManager imageManager, Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.aA = imageManager;
            this.mUri = uri;
            this.aB = parcelFileDescriptor;
        }

        public void run() {
            C0143h.m463g("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            boolean z = false;
            Bitmap bitmap = null;
            if (this.aB != null) {
                try {
                    bitmap = BitmapFactory.decodeFileDescriptor(this.aB.getFileDescriptor());
                } catch (Throwable e) {
                    Log.e("ImageManager", "OOM while loading bitmap for uri: " + this.mUri, e);
                    z = true;
                }
                try {
                    this.aB.close();
                } catch (Throwable e2) {
                    Log.e("ImageManager", "closed failed", e2);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            this.aA.mHandler.post(new C0104f(this.aA, this.mUri, bitmap, z, countDownLatch));
            try {
                countDownLatch.await();
            } catch (InterruptedException e3) {
                Log.w("ImageManager", "Latch interrupted while posting " + this.mUri);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.d */
    private final class C0102d implements Runnable {
        final /* synthetic */ ImageManager aA;
        private final C0106a aC;

        public C0102d(ImageManager imageManager, C0106a c0106a) {
            this.aA = imageManager;
            this.aC = c0106a;
        }

        public void run() {
            C0143h.m462f("LoadImageRunnable must be executed on the main thread");
            this.aA.m50b(this.aC);
            C0105a c0105a = this.aC.aG;
            if (c0105a.uri == null) {
                this.aC.m67b(this.aA.mContext, true);
                return;
            }
            Bitmap a = this.aA.m46a(c0105a);
            if (a != null) {
                this.aC.m64a(this.aA.mContext, a, true);
                return;
            }
            this.aC.m68f(this.aA.mContext);
            ImageReceiver imageReceiver = (ImageReceiver) this.aA.ax.get(c0105a.uri);
            if (imageReceiver == null) {
                imageReceiver = new ImageReceiver(this.aA, c0105a.uri);
                this.aA.ax.put(c0105a.uri, imageReceiver);
            }
            imageReceiver.m39c(this.aC);
            if (this.aC.aJ != 1) {
                this.aA.aw.put(this.aC, imageReceiver);
            }
            synchronized (ImageManager.aq) {
                if (!ImageManager.ar.contains(c0105a.uri)) {
                    ImageManager.ar.add(c0105a.uri);
                    imageReceiver.m41q();
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.e */
    private static final class C0103e implements ComponentCallbacks2 {
        private final C0470b av;

        public C0103e(C0470b c0470b) {
            this.av = c0470b;
        }

        public void onConfigurationChanged(Configuration newConfig) {
        }

        public void onLowMemory() {
            this.av.evictAll();
        }

        public void onTrimMemory(int level) {
            if (level >= 60) {
                this.av.evictAll();
            } else if (level >= 20) {
                this.av.trimToSize(this.av.size() / 2);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.f */
    private final class C0104f implements Runnable {
        final /* synthetic */ ImageManager aA;
        private final Bitmap aD;
        private final CountDownLatch aE;
        private boolean aF;
        private final Uri mUri;

        public C0104f(ImageManager imageManager, Uri uri, Bitmap bitmap, boolean z, CountDownLatch countDownLatch) {
            this.aA = imageManager;
            this.mUri = uri;
            this.aD = bitmap;
            this.aF = z;
            this.aE = countDownLatch;
        }

        private void m43a(ImageReceiver imageReceiver, boolean z) {
            imageReceiver.az = true;
            ArrayList a = imageReceiver.ay;
            int size = a.size();
            for (int i = 0; i < size; i++) {
                C0106a c0106a = (C0106a) a.get(i);
                if (z) {
                    c0106a.m64a(this.aA.mContext, this.aD, false);
                } else {
                    c0106a.m67b(this.aA.mContext, false);
                }
                if (c0106a.aJ != 1) {
                    this.aA.aw.remove(c0106a);
                }
            }
            imageReceiver.az = false;
        }

        public void run() {
            C0143h.m462f("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean z = this.aD != null;
            if (this.aA.av != null) {
                if (this.aF) {
                    this.aA.av.evictAll();
                    System.gc();
                    this.aF = false;
                    this.aA.mHandler.post(this);
                    return;
                } else if (z) {
                    this.aA.av.put(new C0105a(this.mUri), this.aD);
                }
            }
            ImageReceiver imageReceiver = (ImageReceiver) this.aA.ax.remove(this.mUri);
            if (imageReceiver != null) {
                m43a(imageReceiver, z);
            }
            this.aE.countDown();
            synchronized (ImageManager.aq) {
                ImageManager.ar.remove(this.mUri);
            }
        }
    }

    /* renamed from: com.google.android.gms.common.images.ImageManager.b */
    private static final class C0470b extends C0162w<C0105a, Bitmap> {
        public C0470b(Context context) {
            super(C0470b.m649e(context));
        }

        private static int m649e(Context context) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            int memoryClass = (((context.getApplicationInfo().flags & 1048576) != 0 ? 1 : null) == null || !as.an()) ? activityManager.getMemoryClass() : C0100a.m42a(activityManager);
            return (int) (((float) (memoryClass * 1048576)) * 0.33f);
        }

        protected int m650a(C0105a c0105a, Bitmap bitmap) {
            return bitmap.getHeight() * bitmap.getRowBytes();
        }

        protected void m651a(boolean z, C0105a c0105a, Bitmap bitmap, Bitmap bitmap2) {
            super.entryRemoved(z, c0105a, bitmap, bitmap2);
        }

        protected /* synthetic */ void entryRemoved(boolean x0, Object x1, Object x2, Object x3) {
            m651a(x0, (C0105a) x1, (Bitmap) x2, (Bitmap) x3);
        }

        protected /* synthetic */ int sizeOf(Object x0, Object x1) {
            return m650a((C0105a) x0, (Bitmap) x1);
        }
    }

    static {
        aq = new Object();
        ar = new HashSet();
    }

    private ImageManager(Context context, boolean withMemoryCache) {
        this.mContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.au = Executors.newFixedThreadPool(4);
        if (withMemoryCache) {
            this.av = new C0470b(this.mContext);
            if (as.aq()) {
                m55n();
            }
        } else {
            this.av = null;
        }
        this.aw = new HashMap();
        this.ax = new HashMap();
    }

    private Bitmap m46a(C0105a c0105a) {
        return this.av == null ? null : (Bitmap) this.av.get(c0105a);
    }

    public static ImageManager m47a(Context context, boolean z) {
        if (z) {
            if (at == null) {
                at = new ImageManager(context, true);
            }
            return at;
        }
        if (as == null) {
            as = new ImageManager(context, false);
        }
        return as;
    }

    private boolean m50b(C0106a c0106a) {
        C0143h.m462f("ImageManager.cleanupHashMaps() must be called in the main thread");
        if (c0106a.aJ == 1) {
            return true;
        }
        ImageReceiver imageReceiver = (ImageReceiver) this.aw.get(c0106a);
        if (imageReceiver == null) {
            return true;
        }
        if (imageReceiver.az) {
            return false;
        }
        this.aw.remove(c0106a);
        imageReceiver.m40d(c0106a);
        return true;
    }

    public static ImageManager create(Context context) {
        return m47a(context, false);
    }

    private void m55n() {
        this.mContext.registerComponentCallbacks(new C0103e(this.av));
    }

    public void m58a(C0106a c0106a) {
        C0143h.m462f("ImageManager.loadImage() must be called in the main thread");
        boolean b = m50b(c0106a);
        Runnable c0102d = new C0102d(this, c0106a);
        if (b) {
            c0102d.run();
        } else {
            this.mHandler.post(c0102d);
        }
    }

    public void loadImage(ImageView imageView, int resId) {
        C0106a c0106a = new C0106a(resId);
        c0106a.m65a(imageView);
        m58a(c0106a);
    }

    public void loadImage(ImageView imageView, Uri uri) {
        C0106a c0106a = new C0106a(uri);
        c0106a.m65a(imageView);
        m58a(c0106a);
    }

    public void loadImage(ImageView imageView, Uri uri, int defaultResId) {
        C0106a c0106a = new C0106a(uri);
        c0106a.m69j(defaultResId);
        c0106a.m65a(imageView);
        m58a(c0106a);
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri) {
        C0106a c0106a = new C0106a(uri);
        c0106a.m66a(listener);
        m58a(c0106a);
    }

    public void loadImage(OnImageLoadedListener listener, Uri uri, int defaultResId) {
        C0106a c0106a = new C0106a(uri);
        c0106a.m69j(defaultResId);
        c0106a.m66a(listener);
        m58a(c0106a);
    }
}
