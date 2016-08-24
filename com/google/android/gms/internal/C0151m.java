package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesUtil;

/* renamed from: com.google.android.gms.internal.m */
public class C0151m {
    private static final Uri bV;
    private static final Uri bW;

    static {
        bV = Uri.parse("http://plus.google.com/");
        bW = bV.buildUpon().appendPath("circles").appendPath("find").build();
    }

    public static Intent m483i(String str) {
        Uri fromParts = Uri.fromParts("package", str, null);
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(fromParts);
        return intent;
    }

    private static Uri m484j(String str) {
        return Uri.parse("market://details").buildUpon().appendQueryParameter("id", str).build();
    }

    public static Intent m485k(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(C0151m.m484j(str));
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE);
        intent.addFlags(524288);
        return intent;
    }

    public static Intent m486l(String str) {
        Uri parse = Uri.parse("bazaar://search?q=pname:" + str);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(parse);
        intent.setFlags(524288);
        return intent;
    }
}
