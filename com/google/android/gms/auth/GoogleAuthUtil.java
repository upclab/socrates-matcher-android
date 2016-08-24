package com.google.android.gms.auth;

import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.C0092R;
import com.google.android.gms.common.C0095a;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.C0127a.C0487a;
import java.io.IOException;
import java.net.URISyntaxException;

public final class GoogleAuthUtil {
    public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
    public static final String KEY_ANDROID_PACKAGE_NAME;
    public static final String KEY_CALLER_UID;
    public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";
    @Deprecated
    public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
    public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
    private static final ComponentName f6u;
    private static final ComponentName f7v;
    private static final Intent f8w;
    private static final Intent f9x;

    static {
        KEY_CALLER_UID = VERSION.SDK_INT >= 11 ? "callerUid" : "callerUid";
        KEY_ANDROID_PACKAGE_NAME = VERSION.SDK_INT >= 14 ? "androidPackageName" : "androidPackageName";
        f6u = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.auth.GetToken");
        f7v = new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.recovery.RecoveryService");
        f8w = new Intent().setComponent(f6u);
        f9x = new Intent().setComponent(f7v);
    }

    private GoogleAuthUtil() {
    }

    private static String m5a(Context context, String str, String str2, Bundle bundle) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        if (bundle == null) {
            bundle = new Bundle();
        }
        try {
            return getToken(context, str, str2, bundle);
        } catch (GooglePlayServicesAvailabilityException e) {
            int i;
            PendingIntent errorPendingIntent = GooglePlayServicesUtil.getErrorPendingIntent(e.getConnectionStatusCode(), context, 0);
            Resources resources = context.getResources();
            Notification notification = new Notification(17301642, resources.getString(C0092R.string.auth_client_play_services_err_notification_msg), System.currentTimeMillis());
            notification.flags |= 16;
            CharSequence charSequence = context.getApplicationInfo().name;
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = context.getPackageName();
            }
            CharSequence string = resources.getString(C0092R.string.auth_client_requested_by_msg, new Object[]{charSequence});
            switch (e.getConnectionStatusCode()) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0092R.string.auth_client_needs_installation_title;
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    i = C0092R.string.auth_client_needs_update_title;
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    i = C0092R.string.auth_client_needs_enabling_title;
                    break;
                default:
                    i = C0092R.string.auth_client_using_bad_version_title;
                    break;
            }
            notification.setLatestEventInfo(context, resources.getString(i), string, errorPendingIntent);
            ((NotificationManager) context.getSystemService("notification")).notify(39789, notification);
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        } catch (UserRecoverableAuthException e2) {
            throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
        }
    }

    private static void m6a(Context context) throws GooglePlayServicesAvailabilityException, GoogleAuthException {
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            Intent a = GooglePlayServicesUtil.m15a(context, isGooglePlayServicesAvailable, -1);
            String str = "GooglePlayServices not available due to error " + isGooglePlayServicesAvailable;
            Log.e("GoogleAuthUtil", str);
            if (a == null) {
                throw new GoogleAuthException(str);
            }
            throw new GooglePlayServicesAvailabilityException(isGooglePlayServicesAvailable, "GooglePlayServicesNotAvailable", a);
        }
    }

    private static void m7a(Intent intent) {
        if (intent == null) {
            throw new IllegalArgumentException("Callack cannot be null.");
        }
        try {
            Intent.parseUri(intent.toUri(1), 1);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
        }
    }

    private static boolean m8a(String str) {
        return "NetworkError".equals(str) || "ServiceUnavailable".equals(str) || "Timeout".equals(str);
    }

    private static void m9b(Context context) {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == context.getMainLooper()) {
            Throwable illegalStateException = new IllegalStateException("calling this from your main thread can lead to deadlock");
            Log.e("GoogleAuthUtil", "Calling this from your main thread can lead to deadlock and/or ANRs", illegalStateException);
            throw illegalStateException;
        }
    }

    private static boolean m10b(String str) {
        return "BadAuthentication".equals(str) || "CaptchaRequired".equals(str) || "DeviceManagementRequiredOrSyncDisabled".equals(str) || "NeedPermission".equals(str) || "NeedsBrowser".equals(str) || "UserCancel".equals(str) || "AppDownloadRequired".equals(str);
    }

    public static String getToken(Context context, String accountName, String scope) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        return getToken(context, accountName, scope, new Bundle());
    }

    public static String getToken(Context context, String accountName, String scope, Bundle extras) throws IOException, UserRecoverableAuthException, GoogleAuthException {
        Context applicationContext = context.getApplicationContext();
        m9b(applicationContext);
        m6a(applicationContext);
        extras = extras == null ? new Bundle() : new Bundle(extras);
        if (!extras.containsKey(KEY_ANDROID_PACKAGE_NAME)) {
            extras.putString(KEY_ANDROID_PACKAGE_NAME, context.getPackageName());
        }
        ServiceConnection c0095a = new C0095a();
        if (context.bindService(f8w, c0095a, 1)) {
            try {
                Bundle a = C0487a.m664a(c0095a.m29e()).m162a(accountName, scope, extras);
                Object string = a.getString("authtoken");
                if (TextUtils.isEmpty(string)) {
                    String string2 = a.getString("Error");
                    Intent intent = (Intent) a.getParcelable("userRecoveryIntent");
                    if (m10b(string2)) {
                        throw new UserRecoverableAuthException(string2, intent);
                    } else if (m8a(string2)) {
                        throw new IOException(string2);
                    } else {
                        throw new GoogleAuthException(string2);
                    }
                }
                context.unbindService(c0095a);
                return string;
            } catch (Throwable e) {
                Log.i("GoogleAuthUtil", "GMS remote exception ", e);
                throw new IOException("remote exception");
            } catch (InterruptedException e2) {
                throw new GoogleAuthException("Interrupted");
            } catch (Throwable th) {
                context.unbindService(c0095a);
            }
        } else {
            throw new UserRecoverableAuthException("AppDownloadRequired", null);
        }
    }

    public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putBoolean("handle_notification", true);
        return m5a(context, accountName, scope, extras);
    }

    public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras, Intent callback) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        m7a(callback);
        if (extras == null) {
            extras = new Bundle();
        }
        extras.putParcelable("callback_intent", callback);
        extras.putBoolean("handle_notification", true);
        return m5a(context, accountName, scope, extras);
    }

    public static String getTokenWithNotification(Context context, String accountName, String scope, Bundle extras, String authority, Bundle syncBundle) throws IOException, UserRecoverableNotifiedException, GoogleAuthException {
        if (TextUtils.isEmpty(authority)) {
            throw new IllegalArgumentException("Authority cannot be empty or null.");
        }
        if (extras == null) {
            extras = new Bundle();
        }
        if (syncBundle == null) {
            syncBundle = new Bundle();
        }
        ContentResolver.validateSyncExtrasBundle(syncBundle);
        extras.putString("authority", authority);
        extras.putBundle("sync_extras", syncBundle);
        extras.putBoolean("handle_notification", true);
        return m5a(context, accountName, scope, extras);
    }

    public static void invalidateToken(Context context, String token) {
        AccountManager.get(context).invalidateAuthToken(GOOGLE_ACCOUNT_TYPE, token);
    }
}
