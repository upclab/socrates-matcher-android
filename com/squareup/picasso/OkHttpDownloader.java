package com.squareup.picasso;

import android.content.Context;
import android.net.Uri;
import com.google.common.net.HttpHeaders;
import com.squareup.okhttp.HttpResponseCache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Downloader.Response;
import com.squareup.picasso.Downloader.ResponseException;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OkHttpDownloader implements Downloader {
    static final String RESPONSE_SOURCE_ANDROID = "X-Android-Response-Source";
    static final String RESPONSE_SOURCE_OKHTTP = "OkHttp-Response-Source";
    private final OkHttpClient client;

    public OkHttpDownloader(Context context) {
        this(Utils.createDefaultCacheDir(context));
    }

    public OkHttpDownloader(File cacheDir) {
        this(cacheDir, Utils.calculateDiskCacheSize(cacheDir));
    }

    public OkHttpDownloader(Context context, long maxSize) {
        this(Utils.createDefaultCacheDir(context), maxSize);
    }

    public OkHttpDownloader(File cacheDir, long maxSize) {
        this(new OkHttpClient());
        try {
            this.client.setResponseCache(new HttpResponseCache(cacheDir, maxSize));
        } catch (IOException e) {
        }
    }

    public OkHttpDownloader(OkHttpClient client) {
        this.client = client;
    }

    protected HttpURLConnection openConnection(Uri uri) throws IOException {
        HttpURLConnection connection = this.client.open(new URL(uri.toString()));
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(NetworkClient.DEFAULT_SOCKET_TIMEOUT_MILLIS);
        return connection;
    }

    protected OkHttpClient getClient() {
        return this.client;
    }

    public Response load(Uri uri, boolean localCacheOnly) throws IOException {
        HttpURLConnection connection = openConnection(uri);
        connection.setUseCaches(true);
        if (localCacheOnly) {
            connection.setRequestProperty(HttpHeaders.CACHE_CONTROL, "only-if-cached,max-age=2147483647");
        }
        int responseCode = connection.getResponseCode();
        if (responseCode >= 300) {
            connection.disconnect();
            throw new ResponseException(responseCode + " " + connection.getResponseMessage());
        }
        String responseSource = connection.getHeaderField(RESPONSE_SOURCE_OKHTTP);
        if (responseSource == null) {
            responseSource = connection.getHeaderField(RESPONSE_SOURCE_ANDROID);
        }
        return new Response(connection.getInputStream(), Utils.parseResponseSourceHeader(responseSource));
    }
}
