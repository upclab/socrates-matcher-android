package com.adsdk.sdk.mraid;

import android.util.Log;
import java.util.HashMap;

public abstract class BaseAdapter {
    private static final HashMap<String, String> sAdapterMap;
    protected boolean mInvalidated;
    protected String mJsonParams;
    protected MoPubView mMoPubView;

    public abstract void loadAd();

    static {
        sAdapterMap = new HashMap();
        sAdapterMap.put("admob_native", "com.adsdk.sdk.mraid.GoogleAdMobAdapter");
        sAdapterMap.put("millennial_native", "com.adsdk.sdk.mraid.MillennialAdapter");
        sAdapterMap.put("mraid", "com.adsdk.sdk.mraid.MraidAdapter");
    }

    public void init(MoPubView view, String jsonParams) {
        this.mMoPubView = view;
        this.mJsonParams = jsonParams;
        this.mInvalidated = false;
    }

    public void invalidate() {
        this.mMoPubView = null;
        this.mInvalidated = true;
    }

    public boolean isInvalidated() {
        return this.mInvalidated;
    }

    public static BaseAdapter getAdapterForType(String type) {
        if (type == null) {
            return null;
        }
        Class<?> adapterClass = classForAdapterType(type);
        if (adapterClass == null) {
            return null;
        }
        try {
            return (BaseAdapter) adapterClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            Log.d("MoPub", "Couldn't create native adapter for type: " + type);
            return null;
        }
    }

    private static String classStringForAdapterType(String type) {
        return (String) sAdapterMap.get(type);
    }

    private static Class<?> classForAdapterType(String type) {
        Class<?> cls = null;
        String className = classStringForAdapterType(type);
        if (className == null) {
            Log.d("MoPub", "Couldn't find a handler for this ad type: " + type + "." + " MoPub for Android does not support it at this time.");
        } else {
            try {
                cls = Class.forName(className);
            } catch (ClassNotFoundException e) {
                Log.d("MoPub", "Couldn't find " + className + " class." + " Make sure the project includes the adapter library for " + className + " from the extras folder");
            }
        }
        return cls;
    }
}
