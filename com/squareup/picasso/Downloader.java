package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public interface Downloader {

    public static class Response {
        final Bitmap bitmap;
        final boolean cached;
        final InputStream stream;

        public Response(Bitmap bitmap, boolean loadedFromCache) {
            if (bitmap == null) {
                throw new IllegalArgumentException("Bitmap may not be null.");
            }
            this.stream = null;
            this.bitmap = bitmap;
            this.cached = loadedFromCache;
        }

        public Response(InputStream stream, boolean loadedFromCache) {
            if (stream == null) {
                throw new IllegalArgumentException("Stream may not be null.");
            }
            this.stream = stream;
            this.bitmap = null;
            this.cached = loadedFromCache;
        }

        public InputStream getInputStream() {
            return this.stream;
        }

        public Bitmap getBitmap() {
            return this.bitmap;
        }
    }

    public static class ResponseException extends IOException {
        public ResponseException(String message) {
            super(message);
        }
    }

    Response load(Uri uri, boolean z) throws IOException;
}
