package com.adsdk.sdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;

public class Util {
    private static int sFadeInAnimationId;
    private static int sFadeOutAnimationId;
    private static int sSlideInBottomAnimationId;
    private static int sSlideInLeftAnimationId;
    private static int sSlideInRightAnimationId;
    private static int sSlideInTopAnimationId;
    private static int sSlideOutBottomAnimationId;
    private static int sSlideOutLeftAnimationId;
    private static int sSlideOutRightAnimationId;
    private static int sSlideOutTopAnimationId;

    static {
        sFadeInAnimationId = 0;
        sFadeOutAnimationId = 0;
        sSlideInRightAnimationId = 0;
        sSlideOutRightAnimationId = 0;
        sSlideInLeftAnimationId = 0;
        sSlideOutLeftAnimationId = 0;
        sSlideInTopAnimationId = 0;
        sSlideOutTopAnimationId = 0;
        sSlideInBottomAnimationId = 0;
        sSlideOutBottomAnimationId = 0;
    }

    public static boolean isNetworkAvailable(Context ctx) {
        if (ctx.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return true;
        }
        NetworkInfo info = ((ConnectivityManager) ctx.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        int netType = info.getType();
        if (netType == 1 || netType == 0) {
            return info.isConnected();
        }
        return false;
    }

    public static String getConnectionType(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return Const.CONNECTION_TYPE_UNKNOWN;
        }
        NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null) {
            return Const.CONNECTION_TYPE_UNKNOWN;
        }
        int netType = info.getType();
        int netSubtype = info.getSubtype();
        if (netType == 1) {
            return Const.CONNECTION_TYPE_WIFI;
        }
        if (netType == 6) {
            return Const.CONNECTION_TYPE_WIMAX;
        }
        if (netType != 0) {
            return Const.CONNECTION_TYPE_UNKNOWN;
        }
        switch (netSubtype) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return Const.CONNECTION_TYPE_MOBILE_GPRS;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return Const.CONNECTION_TYPE_MOBILE_EDGE;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return Const.CONNECTION_TYPE_MOBILE_UMTS;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return Const.CONNECTION_TYPE_MOBILE_CDMA;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return Const.CONNECTION_TYPE_MOBILE_EVDO_0;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return Const.CONNECTION_TYPE_MOBILE_EVDO_A;
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                return Const.CONNECTION_TYPE_MOBILE_1xRTT;
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                return Const.CONNECTION_TYPE_MOBILE_HSDPA;
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                return Const.CONNECTION_TYPE_MOBILE_HSUPA;
            case Value.ESCAPING_FIELD_NUMBER /*10*/:
                return Const.CONNECTION_TYPE_MOBILE_HSPA;
            case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                return Const.CONNECTION_TYPE_MOBILE_IDEN;
            case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                return Const.CONNECTION_TYPE_MOBILE_EVDO_B;
            case Resource.VERSION_FIELD_NUMBER /*13*/:
                return Const.CONNECTION_TYPE_MOBILE_LTE;
            case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                return Const.CONNECTION_TYPE_MOBILE_EHRPD;
            case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                return Const.CONNECTION_TYPE_MOBILE_HSPAP;
            default:
                return Const.CONNECTION_TYPE_MOBILE_UNKNOWN;
        }
    }

    public static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    public static String getTelephonyDeviceId(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        }
        return StringUtils.EMPTY;
    }

    public static String getDeviceId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), "android_id");
        if (androidId == null || androidId.equals("9774d56d682e549c") || androidId.equals("0000000000000000")) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            androidId = prefs.getString(Const.PREFS_DEVICE_ID, null);
            if (androidId == null) {
                try {
                    String uuid = UUID.randomUUID().toString();
                    MessageDigest.getInstance("MD5").update(uuid.getBytes(), 0, uuid.length());
                    androidId = String.format("%032X", new Object[]{new BigInteger(1, digest.digest())}).substring(0, 16);
                } catch (Exception e) {
                    androidId = "9774d56d682e549c";
                }
                prefs.edit().putString(Const.PREFS_DEVICE_ID, androidId).commit();
            }
        }
        return androidId;
    }

    public static Location getLocation(Context context) {
        int isAccessFineLocation = context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
        int isAccessCoarseLocation = context.checkCallingOrSelfPermission("android.permission.ACCESS_COARSE_LOCATION");
        if (isAccessFineLocation == 0 || isAccessCoarseLocation == 0) {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager != null) {
                if (isAccessFineLocation == 0 && locationManager.isProviderEnabled("gps")) {
                    return locationManager.getLastKnownLocation("gps");
                }
                if (isAccessCoarseLocation == 0 && locationManager.isProviderEnabled("network")) {
                    return locationManager.getLastKnownLocation("network");
                }
            }
        }
        return null;
    }

    public static String getDefaultUserAgentString(Context context) {
        Constructor<WebSettings> constructor;
        try {
            constructor = WebSettings.class.getDeclaredConstructor(new Class[]{Context.class, WebView.class});
            constructor.setAccessible(true);
            String userAgentString = ((WebSettings) constructor.newInstance(new Object[]{context, null})).getUserAgentString();
            constructor.setAccessible(false);
            return userAgentString;
        } catch (Exception e) {
            return new WebView(context).getSettings().getUserAgentString();
        } catch (Throwable th) {
            constructor.setAccessible(false);
        }
    }

    public static String buildUserAgent() {
        String androidVersion = VERSION.RELEASE;
        String model = Build.MODEL;
        String androidBuild = Build.ID;
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String locale = "en";
        if (language != null) {
            locale = language.toLowerCase();
            String country = l.getCountry();
            if (country != null) {
                locale = new StringBuilder(String.valueOf(locale)).append("-").append(country.toLowerCase()).toString();
            }
        }
        return String.format(Const.USER_AGENT_PATTERN, new Object[]{androidVersion, locale, model, androidBuild});
    }

    public static int getMemoryClass(Context context) {
        try {
            return ((Integer) ActivityManager.class.getMethod("getMemoryClass", new Class[0]).invoke((ActivityManager) context.getSystemService("activity"), new Object[0])).intValue();
        } catch (Exception e) {
            return 16;
        }
    }

    public static void initializeAnimations(Context ctx) {
        Resources r = ctx.getResources();
        sFadeInAnimationId = r.getIdentifier("fade_in", "anim", ctx.getPackageName());
        sFadeOutAnimationId = r.getIdentifier("fade_out", "anim", ctx.getPackageName());
        sSlideInBottomAnimationId = r.getIdentifier("slide_bottom_in", "anim", ctx.getPackageName());
        sSlideOutBottomAnimationId = r.getIdentifier("slide_bottom_out", "anim", ctx.getPackageName());
        sSlideInTopAnimationId = r.getIdentifier("slide_top_in", "anim", ctx.getPackageName());
        sSlideOutTopAnimationId = r.getIdentifier("slide_top_out", "anim", ctx.getPackageName());
        sSlideInLeftAnimationId = r.getIdentifier("slide_left_in", "anim", ctx.getPackageName());
        sSlideOutLeftAnimationId = r.getIdentifier("slide_left_out", "anim", ctx.getPackageName());
        sSlideInRightAnimationId = r.getIdentifier("slide_right_in", "anim", ctx.getPackageName());
        sSlideOutRightAnimationId = r.getIdentifier("slide_right_out", "anim", ctx.getPackageName());
    }

    public static AnimationSet getEnterAnimationSet(int animation) {
        AnimationSet set = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, AugmentedReality.ONE_PERCENT);
        alphaAnimation.setDuration(3000);
        set.addAnimation(alphaAnimation);
        TranslateAnimation translateAnimation;
        switch (animation) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return set;
            case Value.STRING_FIELD_NUMBER /*2*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, GroundOverlayOptions.NO_DIMENSION, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, AugmentedReality.ONE_PERCENT, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                translateAnimation = new TranslateAnimation(1, GroundOverlayOptions.NO_DIMENSION, 1, 0.0f, 1, 0.0f, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                translateAnimation = new TranslateAnimation(1, AugmentedReality.ONE_PERCENT, 1, 0.0f, 1, 0.0f, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            default:
                return null;
        }
    }

    public static AnimationSet getExitAnimationSet(int animation) {
        AnimationSet set = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(AugmentedReality.ONE_PERCENT, 0.0f);
        alphaAnimation.setDuration(3000);
        set.addAnimation(alphaAnimation);
        TranslateAnimation translateAnimation;
        switch (animation) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return set;
            case Value.STRING_FIELD_NUMBER /*2*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, GroundOverlayOptions.NO_DIMENSION);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, AugmentedReality.ONE_PERCENT);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, GroundOverlayOptions.NO_DIMENSION, 1, 0.0f, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, AugmentedReality.ONE_PERCENT, 1, 0.0f, 1, 0.0f);
                translateAnimation.setDuration(1000);
                set.addAnimation(translateAnimation);
                return set;
            default:
                return null;
        }
    }

    public static int getEnterAnimation(int animation) {
        switch (animation) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return sFadeInAnimationId;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return sSlideInTopAnimationId;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return sSlideInBottomAnimationId;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return sSlideInLeftAnimationId;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return sSlideInRightAnimationId;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return sFadeInAnimationId;
            default:
                return 0;
        }
    }

    public static int getExitAnimation(int animation) {
        switch (animation) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return sFadeOutAnimationId;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return sSlideOutTopAnimationId;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return sSlideOutBottomAnimationId;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return sSlideOutLeftAnimationId;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return sSlideOutRightAnimationId;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return sFadeOutAnimationId;
            default:
                return 0;
        }
    }
}
