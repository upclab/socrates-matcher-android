package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class aw {
    public static final Intent m216b(Intent intent) {
        intent.setData(Uri.fromParts("version", Integer.toString(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_VERSION_CODE), null));
        return intent;
    }
}
