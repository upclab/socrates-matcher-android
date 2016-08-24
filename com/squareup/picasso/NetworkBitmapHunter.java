package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Picasso.LoadedFrom;
import java.io.IOException;
import java.io.InputStream;

class NetworkBitmapHunter extends BitmapHunter {
    static final int DEFAULT_RETRY_COUNT = 2;
    private static final int MARKER = 65536;
    private final Downloader downloader;
    int retryCount;

    public NetworkBitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, Downloader downloader) {
        super(picasso, dispatcher, cache, stats, action);
        this.downloader = downloader;
        this.retryCount = DEFAULT_RETRY_COUNT;
    }

    Bitmap decode(Request data) throws IOException {
        Response response = this.downloader.load(data.uri, this.retryCount == 0);
        if (response == null) {
            return null;
        }
        this.loadedFrom = response.cached ? LoadedFrom.DISK : LoadedFrom.NETWORK;
        Bitmap bitmap = response.getBitmap();
        if (bitmap != null) {
            return bitmap;
        }
        InputStream is = response.getInputStream();
        try {
            bitmap = decodeStream(is, data);
            return bitmap;
        } finally {
            Utils.closeQuietly(is);
        }
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        boolean hasRetries;
        if (this.retryCount > 0) {
            hasRetries = true;
        } else {
            hasRetries = false;
        }
        if (!hasRetries) {
            return false;
        }
        this.retryCount--;
        if (info == null || info.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private Bitmap decodeStream(InputStream stream, Request data) throws IOException {
        if (stream == null) {
            return null;
        }
        InputStream markStream = new MarkableInputStream(stream);
        stream = markStream;
        long mark = markStream.savePosition(MARKER);
        boolean isWebPFile = Utils.isWebPFile(stream);
        markStream.reset(mark);
        Options options;
        if (isWebPFile) {
            byte[] bytes = Utils.toByteArray(stream);
            options = BitmapHunter.createBitmapOptions(data);
            if (data.hasSize()) {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                BitmapHunter.calculateInSampleSize(data.targetWidth, data.targetHeight, options);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
        options = BitmapHunter.createBitmapOptions(data);
        if (data.hasSize()) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(stream, null, options);
            BitmapHunter.calculateInSampleSize(data.targetWidth, data.targetHeight, options);
            markStream.reset(mark);
        }
        return BitmapFactory.decodeStream(stream, null, options);
    }
}
