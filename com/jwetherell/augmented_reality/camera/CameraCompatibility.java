package com.jwetherell.augmented_reality.camera;

import android.app.Activity;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CameraCompatibility {
    private static Method getSupportedPreviewSizes;
    private static Method mDefaultDisplay_getRotation;

    static {
        getSupportedPreviewSizes = null;
        mDefaultDisplay_getRotation = null;
        initCompatibility();
    }

    private static void initCompatibility() {
        try {
            getSupportedPreviewSizes = Parameters.class.getMethod("getSupportedPreviewSizes", new Class[0]);
            mDefaultDisplay_getRotation = Display.class.getMethod("getRotation", new Class[0]);
        } catch (NoSuchMethodException e) {
        }
    }

    public static int getRotation(Activity activity) {
        int result = 1;
        try {
            Object retObj = mDefaultDisplay_getRotation.invoke(((WindowManager) activity.getSystemService("window")).getDefaultDisplay(), new Object[0]);
            if (retObj != null) {
                result = ((Integer) retObj).intValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static List<Size> getSupportedPreviewSizes(Parameters params) {
        try {
            Object retObj = getSupportedPreviewSizes.invoke(params, new Object[0]);
            if (retObj != null) {
                return (List) retObj;
            }
            return null;
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException(ite);
            }
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
            return null;
        }
    }
}
