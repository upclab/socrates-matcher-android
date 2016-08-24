package com.adsdk.sdk;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import com.adsdk.sdk.mraid.AdView;
import org.apache.commons.lang3.StringUtils;

public class AdRequest {
    public static final int BANNER = 0;
    private static final String REQUEST_TYPE_ANDROID = "android_app";
    public static final int VAD = 1;
    private String connectionType;
    private String deviceId;
    private String deviceId2;
    private String headers;
    private String ipAddress;
    private double latitude;
    private String listAds;
    private double longitude;
    private String protocolVersion;
    private String publisherId;
    private String requestURL;
    private long timestamp;
    private int type;
    private String userAgent;
    private String userAgent2;

    public AdRequest() {
        this.longitude = 0.0d;
        this.latitude = 0.0d;
        this.type = -1;
    }

    public String getAndroidVersion() {
        return VERSION.RELEASE;
    }

    public String getConnectionType() {
        return this.connectionType;
    }

    public String getDeviceId() {
        if (this.deviceId == null) {
            return StringUtils.EMPTY;
        }
        return this.deviceId;
    }

    public String getDeviceId2() {
        return this.deviceId2;
    }

    public String getDeviceMode() {
        return Build.MODEL;
    }

    public String getHeaders() {
        if (this.headers == null) {
            return StringUtils.EMPTY;
        }
        return this.headers;
    }

    public String getIpAddress() {
        if (this.ipAddress == null) {
            return StringUtils.EMPTY;
        }
        return this.ipAddress;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getListAds() {
        if (this.listAds != null) {
            return this.listAds;
        }
        return StringUtils.EMPTY;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getProtocolVersion() {
        if (this.protocolVersion == null) {
            return Const.VERSION;
        }
        return this.protocolVersion;
    }

    public String getPublisherId() {
        if (this.publisherId == null) {
            return StringUtils.EMPTY;
        }
        return this.publisherId;
    }

    public String getRequestType() {
        return REQUEST_TYPE_ANDROID;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getUserAgent() {
        if (this.userAgent == null) {
            return StringUtils.EMPTY;
        }
        return this.userAgent;
    }

    public String getUserAgent2() {
        if (this.userAgent2 == null) {
            return StringUtils.EMPTY;
        }
        return this.userAgent2;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setDeviceId2(String deviceId2) {
        this.deviceId2 = deviceId2;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setListAds(String listAds) {
        this.listAds = listAds;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setUserAgent2(String userAgent) {
        this.userAgent2 = userAgent;
    }

    public String toString() {
        return toUri().toString();
    }

    public Uri toUri() {
        Builder b = Uri.parse(getRequestURL()).buildUpon();
        b.appendQueryParameter("rt", getRequestType());
        b.appendQueryParameter("v", getProtocolVersion());
        b.appendQueryParameter("i", getIpAddress());
        b.appendQueryParameter(AdView.DEVICE_ORIENTATION_UNKNOWN, getUserAgent());
        b.appendQueryParameter("u2", getUserAgent2());
        b.appendQueryParameter(AdView.DEVICE_ORIENTATION_SQUARE, getPublisherId());
        b.appendQueryParameter("o", getDeviceId());
        b.appendQueryParameter("o2", getDeviceId2());
        b.appendQueryParameter("t", Long.toString(getTimestamp()));
        b.appendQueryParameter("connection_type", getConnectionType());
        b.appendQueryParameter("listads", getListAds());
        switch (getType()) {
            case BANNER /*0*/:
                b.appendQueryParameter("c.mraid", "1");
                b.appendQueryParameter("sdk", "banner");
                break;
            case VAD /*1*/:
                b.appendQueryParameter("c.mraid", "0");
                b.appendQueryParameter("sdk", "vad");
                break;
        }
        b.appendQueryParameter("u_wv", getUserAgent());
        return b.build();
    }

    public String getRequestURL() {
        return this.requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
