package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

abstract class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK;
    final List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    LoadedFrom loadedFrom;
    final Picasso picasso;
    Bitmap result;
    final boolean skipMemoryCache;
    final Stats stats;

    /* renamed from: com.squareup.picasso.BitmapHunter.1 */
    static class C03631 implements Runnable {
        final /* synthetic */ StringBuilder val$builder;

        C03631(StringBuilder stringBuilder) {
            this.val$builder = stringBuilder;
        }

        public void run() {
            throw new NullPointerException(this.val$builder.toString());
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.2 */
    static class C03642 implements Runnable {
        final /* synthetic */ Transformation val$transformation;

        C03642(Transformation transformation) {
            this.val$transformation = transformation;
        }

        public void run() {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " returned input Bitmap but recycled it.");
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter.3 */
    static class C03653 implements Runnable {
        final /* synthetic */ Transformation val$transformation;

        C03653(Transformation transformation) {
            this.val$transformation = transformation;
        }

        public void run() {
            throw new IllegalStateException("Transformation " + this.val$transformation.key() + " mutated input Bitmap but failed to recycle the original.");
        }
    }

    abstract Bitmap decode(Request request) throws IOException;

    static {
        DECODE_LOCK = new Object();
    }

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.key = action.getKey();
        this.data = action.getData();
        this.skipMemoryCache = action.skipCache;
        this.actions = new ArrayList(4);
        attach(action);
    }

    protected void setExifRotation(int exifRotation) {
        this.exifRotation = exifRotation;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r5 = this;
        r2 = java.lang.Thread.currentThread();	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r3 = new java.lang.StringBuilder;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r3.<init>();	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r4 = "Picasso-";
        r3 = r3.append(r4);	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r4 = r5.data;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r4 = r4.getName();	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r3 = r3.append(r4);	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r3 = r3.toString();	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r2.setName(r3);	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r2 = r5.hunt();	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r5.result = r2;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r2 = r5.result;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        if (r2 != 0) goto L_0x0039;
    L_0x002a:
        r2 = r5.dispatcher;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r2.dispatchFailed(r5);	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
    L_0x002f:
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
    L_0x0038:
        return;
    L_0x0039:
        r2 = r5.dispatcher;	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        r2.dispatchComplete(r5);	 Catch:{ ResponseException -> 0x003f, IOException -> 0x0051, OutOfMemoryError -> 0x0063, Exception -> 0x0091 }
        goto L_0x002f;
    L_0x003f:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00a3 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00a3 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00a3 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x0038;
    L_0x0051:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00a3 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00a3 }
        r2.dispatchRetry(r5);	 Catch:{ all -> 0x00a3 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x0038;
    L_0x0063:
        r0 = move-exception;
        r1 = new java.io.StringWriter;	 Catch:{ all -> 0x00a3 }
        r1.<init>();	 Catch:{ all -> 0x00a3 }
        r2 = r5.stats;	 Catch:{ all -> 0x00a3 }
        r2 = r2.createSnapshot();	 Catch:{ all -> 0x00a3 }
        r3 = new java.io.PrintWriter;	 Catch:{ all -> 0x00a3 }
        r3.<init>(r1);	 Catch:{ all -> 0x00a3 }
        r2.dump(r3);	 Catch:{ all -> 0x00a3 }
        r2 = new java.lang.RuntimeException;	 Catch:{ all -> 0x00a3 }
        r3 = r1.toString();	 Catch:{ all -> 0x00a3 }
        r2.<init>(r3, r0);	 Catch:{ all -> 0x00a3 }
        r5.exception = r2;	 Catch:{ all -> 0x00a3 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00a3 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00a3 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x0038;
    L_0x0091:
        r0 = move-exception;
        r5.exception = r0;	 Catch:{ all -> 0x00a3 }
        r2 = r5.dispatcher;	 Catch:{ all -> 0x00a3 }
        r2.dispatchFailed(r5);	 Catch:{ all -> 0x00a3 }
        r2 = java.lang.Thread.currentThread();
        r3 = "Picasso-Idle";
        r2.setName(r3);
        goto L_0x0038;
    L_0x00a3:
        r2 = move-exception;
        r3 = java.lang.Thread.currentThread();
        r4 = "Picasso-Idle";
        r3.setName(r4);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.BitmapHunter.run():void");
    }

    Bitmap hunt() throws IOException {
        Bitmap bitmap;
        if (!this.skipMemoryCache) {
            bitmap = this.cache.get(this.key);
            if (bitmap != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = LoadedFrom.MEMORY;
                return bitmap;
            }
        }
        bitmap = decode(this.data);
        if (bitmap != null) {
            this.stats.dispatchBitmapDecoded(bitmap);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                        bitmap = transformResult(this.data, bitmap, this.exifRotation);
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmap = applyCustomTransformations(this.data.transformations, bitmap);
                    }
                }
                this.stats.dispatchBitmapTransformed(bitmap);
            }
        }
        return bitmap;
    }

    void attach(Action action) {
        this.actions.add(action);
    }

    void detach(Action action) {
        this.actions.remove(action);
    }

    boolean cancel() {
        return this.actions.isEmpty() && this.future != null && this.future.cancel(false);
    }

    boolean isCancelled() {
        return this.future != null && this.future.isCancelled();
    }

    boolean shouldSkipMemoryCache() {
        return this.skipMemoryCache;
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        return false;
    }

    Bitmap getResult() {
        return this.result;
    }

    String getKey() {
        return this.key;
    }

    Request getData() {
        return this.data;
    }

    List<Action> getActions() {
        return this.actions;
    }

    Exception getException() {
        return this.exception;
    }

    LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    static BitmapHunter forRequest(Context context, Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        if (action.getData().resourceId != 0) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        }
        Uri uri = action.getData().uri;
        String scheme = uri.getScheme();
        if ("content".equals(scheme)) {
            if (Contacts.CONTENT_URI.getHost().equals(uri.getHost()) && !uri.getPathSegments().contains("photo")) {
                return new ContactsPhotoBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            if ("media".equals(uri.getAuthority())) {
                return new MediaStoreBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new ContentStreamBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        } else if ("file".equals(scheme)) {
            if (uri.getPathSegments().isEmpty() || !"android_asset".equals(uri.getPathSegments().get(0))) {
                return new FileBitmapHunter(context, picasso, dispatcher, cache, stats, action);
            }
            return new AssetBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        } else if ("android.resource".equals(scheme)) {
            return new ResourceBitmapHunter(context, picasso, dispatcher, cache, stats, action);
        } else {
            return new NetworkBitmapHunter(picasso, dispatcher, cache, stats, action, downloader);
        }
    }

    static Options createBitmapOptions(Request data) {
        Options options = new Options();
        if (data.config != null) {
            options.inPreferredConfig = data.config;
        }
        return options;
    }

    static void calculateInSampleSize(int reqWidth, int reqHeight, Options options) {
        calculateInSampleSize(reqWidth, reqHeight, options.outWidth, options.outHeight, options);
    }

    static void calculateInSampleSize(int reqWidth, int reqHeight, int width, int height, Options options) {
        int sampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                sampleSize = heightRatio;
            } else {
                sampleSize = widthRatio;
            }
        }
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
    }

    static Bitmap applyCustomTransformations(List<Transformation> transformations, Bitmap result) {
        int i = 0;
        int count = transformations.size();
        while (i < count) {
            Transformation transformation = (Transformation) transformations.get(i);
            Bitmap newResult = transformation.transform(result);
            if (newResult == null) {
                StringBuilder builder = new StringBuilder().append("Transformation ").append(transformation.key()).append(" returned null after ").append(i).append(" previous transformation(s).\n\nTransformation list:\n");
                for (Transformation t : transformations) {
                    builder.append(t.key()).append('\n');
                }
                Picasso.HANDLER.post(new C03631(builder));
                return null;
            } else if (newResult == result && result.isRecycled()) {
                Picasso.HANDLER.post(new C03642(transformation));
                return null;
            } else if (newResult == result || result.isRecycled()) {
                result = newResult;
                i++;
            } else {
                Picasso.HANDLER.post(new C03653(transformation));
                return null;
            }
        }
        return result;
    }

    static Bitmap transformResult(Request data, Bitmap result, int exifRotation) {
        int inWidth = result.getWidth();
        int inHeight = result.getHeight();
        int drawX = 0;
        int drawY = 0;
        int drawWidth = inWidth;
        int drawHeight = inHeight;
        Matrix matrix = new Matrix();
        if (data.needsMatrixTransform()) {
            int targetWidth = data.targetWidth;
            int targetHeight = data.targetHeight;
            float targetRotation = data.rotationDegrees;
            if (targetRotation != 0.0f) {
                if (data.hasRotationPivot) {
                    matrix.setRotate(targetRotation, data.rotationPivotX, data.rotationPivotY);
                } else {
                    matrix.setRotate(targetRotation);
                }
            }
            float widthRatio;
            float heightRatio;
            float scale;
            if (data.centerCrop) {
                widthRatio = ((float) targetWidth) / ((float) inWidth);
                heightRatio = ((float) targetHeight) / ((float) inHeight);
                int newSize;
                if (widthRatio > heightRatio) {
                    scale = widthRatio;
                    newSize = (int) Math.ceil((double) (((float) inHeight) * (heightRatio / widthRatio)));
                    drawY = (inHeight - newSize) / 2;
                    drawHeight = newSize;
                } else {
                    scale = heightRatio;
                    newSize = (int) Math.ceil((double) (((float) inWidth) * (widthRatio / heightRatio)));
                    drawX = (inWidth - newSize) / 2;
                    drawWidth = newSize;
                }
                matrix.preScale(scale, scale);
            } else if (data.centerInside) {
                widthRatio = ((float) targetWidth) / ((float) inWidth);
                heightRatio = ((float) targetHeight) / ((float) inHeight);
                if (widthRatio < heightRatio) {
                    scale = widthRatio;
                } else {
                    scale = heightRatio;
                }
                matrix.preScale(scale, scale);
            } else if (!(targetWidth == 0 || targetHeight == 0 || (targetWidth == inWidth && targetHeight == inHeight))) {
                matrix.preScale(((float) targetWidth) / ((float) inWidth), ((float) targetHeight) / ((float) inHeight));
            }
        }
        if (exifRotation != 0) {
            matrix.preRotate((float) exifRotation);
        }
        Bitmap newResult = Bitmap.createBitmap(result, drawX, drawY, drawWidth, drawHeight, matrix, true);
        if (newResult == result) {
            return result;
        }
        result.recycle();
        return newResult;
    }
}
