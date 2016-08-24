package com.adsdk.sdk.mraid;

import android.util.Log;
import java.util.HashMap;

public abstract class BaseInterstitialAdapter {
    private static final HashMap<String, String> sInterstitialAdapterMap;
    protected BaseInterstitialAdapterListener mAdapterListener;
    protected MoPubInterstitial mInterstitial;
    protected boolean mInvalidated;
    protected String mJsonParams;

    public interface BaseInterstitialAdapterListener {
        void onNativeInterstitialClicked(BaseInterstitialAdapter baseInterstitialAdapter);

        void onNativeInterstitialExpired(BaseInterstitialAdapter baseInterstitialAdapter);

        void onNativeInterstitialFailed(BaseInterstitialAdapter baseInterstitialAdapter);

        void onNativeInterstitialLoaded(BaseInterstitialAdapter baseInterstitialAdapter);
    }

    public abstract void loadInterstitial();

    public abstract void showInterstitial();

    static {
        sInterstitialAdapterMap = new HashMap();
        sInterstitialAdapterMap.put("mraid", "com.adsdk.sdk.mraid.MraidInterstitialAdapter");
        sInterstitialAdapterMap.put("admob_full", "com.adsdk.sdk.mraid.GoogleAdMobInterstitialAdapter");
        sInterstitialAdapterMap.put("millennial_full", "com.adsdk.sdk.mraid.MillennialInterstitialAdapter");
    }

    public void init(MoPubInterstitial interstitial, String jsonParams) {
        this.mInterstitial = interstitial;
        this.mJsonParams = jsonParams;
        this.mInvalidated = false;
    }

    public void invalidate() {
        this.mInterstitial = null;
        this.mAdapterListener = null;
        this.mInvalidated = true;
    }

    public boolean isInvalidated() {
        return this.mInvalidated;
    }

    public void setAdapterListener(BaseInterstitialAdapterListener listener) {
        this.mAdapterListener = listener;
    }

    public static BaseInterstitialAdapter getAdapterForType(String type) {
        if (type == null) {
            return null;
        }
        Class<?> adapterClass = classForAdapterType(type);
        if (adapterClass == null) {
            return null;
        }
        try {
            return (BaseInterstitialAdapter) adapterClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            Log.d("MoPub", "Couldn't create native interstitial adapter for type: " + type);
            return null;
        }
    }

    private static String classStringForAdapterType(String type) {
        return (String) sInterstitialAdapterMap.get(type);
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
                Log.d("MoPub", "Couldn't find " + className + "class." + " Make sure the project includes the adapter library for " + className + " from the extras folder");
            }
        }
        return cls;
    }
}
