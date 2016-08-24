package com.google.android.gms.auth;

import android.content.Intent;

public class GooglePlayServicesAvailabilityException extends UserRecoverableAuthException {
    private final int f149y;

    GooglePlayServicesAvailabilityException(int connectionStatusCode, String msg, Intent intent) {
        super(msg, intent);
        this.f149y = connectionStatusCode;
    }

    public int getConnectionStatusCode() {
        return this.f149y;
    }
}
