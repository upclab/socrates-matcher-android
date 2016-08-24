package com.jwetherell.augmented_reality.data;

import com.jwetherell.augmented_reality.ui.Marker;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class NetworkDataSource extends DataSource {
    protected static final int CONNECT_TIMEOUT = 10000;
    protected static final int MAX = 5;
    protected static final int READ_TIMEOUT = 10000;
    protected List<Marker> markersCache;

    public abstract String createRequestURL(double d, double d2, double d3, float f, String str);

    public abstract List<Marker> parse(JSONObject jSONObject);

    public NetworkDataSource() {
        this.markersCache = null;
    }

    public List<Marker> getMarkers() {
        return this.markersCache;
    }

    protected static InputStream getHttpGETInputStream(String urlStr) {
        if (urlStr == null) {
            throw new NullPointerException();
        }
        InputStream is = null;
        try {
            if (urlStr.startsWith("file://")) {
                return new FileInputStream(urlStr.replace("file://", StringUtils.EMPTY));
            }
            URLConnection conn = new URL(urlStr).openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(READ_TIMEOUT);
            return conn.getInputStream();
        } catch (Exception ex) {
            try {
                is.close();
            } catch (Exception e) {
            }
            try {
                if (null instanceof HttpURLConnection) {
                    ((HttpURLConnection) null).disconnect();
                }
            } catch (Exception e2) {
            }
            ex.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.String getHttpInputString(java.io.InputStream r7) {
        /*
        r6 = this;
        if (r7 != 0) goto L_0x0008;
    L_0x0002:
        r4 = new java.lang.NullPointerException;
        r4.<init>();
        throw r4;
    L_0x0008:
        r2 = new java.io.BufferedReader;
        r4 = new java.io.InputStreamReader;
        r4.<init>(r7);
        r5 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r2.<init>(r4, r5);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
    L_0x0019:
        r1 = r2.readLine();	 Catch:{ IOException -> 0x003e }
        if (r1 != 0) goto L_0x0027;
    L_0x001f:
        r7.close();	 Catch:{ IOException -> 0x0055 }
    L_0x0022:
        r4 = r3.toString();
        return r4;
    L_0x0027:
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x003e }
        r5 = java.lang.String.valueOf(r1);	 Catch:{ IOException -> 0x003e }
        r4.<init>(r5);	 Catch:{ IOException -> 0x003e }
        r5 = "\n";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x003e }
        r4 = r4.toString();	 Catch:{ IOException -> 0x003e }
        r3.append(r4);	 Catch:{ IOException -> 0x003e }
        goto L_0x0019;
    L_0x003e:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x004b }
        r7.close();	 Catch:{ IOException -> 0x0046 }
        goto L_0x0022;
    L_0x0046:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0022;
    L_0x004b:
        r4 = move-exception;
        r7.close();	 Catch:{ IOException -> 0x0050 }
    L_0x004f:
        throw r4;
    L_0x0050:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x004f;
    L_0x0055:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jwetherell.augmented_reality.data.NetworkDataSource.getHttpInputString(java.io.InputStream):java.lang.String");
    }

    public List<Marker> parse(String url) {
        if (url == null) {
            throw new NullPointerException();
        }
        InputStream stream = getHttpGETInputStream(url);
        if (stream == null) {
            throw new NullPointerException();
        }
        String string = getHttpInputString(stream);
        if (string == null) {
            throw new NullPointerException();
        }
        JSONObject json = null;
        try {
            json = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json != null) {
            return parse(json);
        }
        throw new NullPointerException();
    }
}
