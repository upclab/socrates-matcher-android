package com.adsdk.sdk.mraid;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String sha1(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                hexString.append(Integer.toHexString((b & MotionEventCompat.ACTION_MASK) | AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT).substring(1));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return StringUtils.EMPTY;
        }
    }
}
