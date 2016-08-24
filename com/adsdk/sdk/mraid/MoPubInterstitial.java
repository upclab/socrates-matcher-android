package com.adsdk.sdk.mraid;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdListener;
import com.adsdk.sdk.mraid.BaseInterstitialAdapter.BaseInterstitialAdapterListener;
import com.adsdk.sdk.mraid.MoPubView.LocationAwareness;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.util.HashMap;

public class MoPubInterstitial implements AdListener {
    private static /* synthetic */ int[] f71xb84c64;
    private Activity mActivity;
    private String mAdUnitId;
    private BaseInterstitialAdapterListener mAdapterListener;
    private InterstitialState mCurrentInterstitialState;
    private DefaultInterstitialAdapterListener mDefaultAdapterListener;
    private BaseInterstitialAdapter mInterstitialAdapter;
    private MoPubInterstitialView mInterstitialView;
    private MoPubInterstitialListener mListener;

    private enum InterstitialState {
        HTML_AD_READY,
        NATIVE_AD_READY,
        NOT_READY
    }

    public interface MoPubInterstitialListener {
        void OnInterstitialFailed();

        void OnInterstitialLoaded();
    }

    public class DefaultInterstitialAdapterListener implements BaseInterstitialAdapterListener {
        public void onNativeInterstitialLoaded(BaseInterstitialAdapter adapter) {
            MoPubInterstitial.this.mCurrentInterstitialState = InterstitialState.NATIVE_AD_READY;
            if (MoPubInterstitial.this.mListener != null) {
                MoPubInterstitial.this.mListener.OnInterstitialLoaded();
            }
        }

        public void onNativeInterstitialFailed(BaseInterstitialAdapter adapter) {
            MoPubInterstitial.this.mCurrentInterstitialState = InterstitialState.NOT_READY;
            MoPubInterstitial.this.mInterstitialView.loadFailUrl();
        }

        public void onNativeInterstitialClicked(BaseInterstitialAdapter adapter) {
            MoPubInterstitial.this.mInterstitialView.registerClick();
        }

        public void onNativeInterstitialExpired(BaseInterstitialAdapter adapter) {
            MoPubInterstitial.this.mCurrentInterstitialState = InterstitialState.NOT_READY;
        }
    }

    public class MoPubInterstitialView extends MoPubView {
        public MoPubInterstitialView(Context context) {
            super(context);
            setAutorefreshEnabled(false);
        }

        protected void loadNativeSDK(HashMap<String, String> paramsHash) {
            if (paramsHash != null) {
                MoPubInterstitial interstitial = MoPubInterstitial.this;
                BaseInterstitialAdapterListener adapterListener = interstitial.getInterstitialAdapterListener();
                String type = (String) paramsHash.get("X-Adtype");
                if (type != null && (type.equals("interstitial") || type.equals("mraid"))) {
                    String interstitialType = type.equals("interstitial") ? (String) paramsHash.get("X-Fulladtype") : "mraid";
                    Log.i("MoPub", "Loading native adapter for interstitial type: " + interstitialType);
                    MoPubInterstitial.this.mInterstitialAdapter = BaseInterstitialAdapter.getAdapterForType(interstitialType);
                    if (MoPubInterstitial.this.mInterstitialAdapter != null) {
                        MoPubInterstitial.this.mInterstitialAdapter.init(interstitial, (String) paramsHash.get("X-Nativeparams"));
                        MoPubInterstitial.this.mInterstitialAdapter.setAdapterListener(adapterListener);
                        MoPubInterstitial.this.mInterstitialAdapter.loadInterstitial();
                        return;
                    }
                }
                Log.i("MoPub", "Couldn't load native adapter. Trying next ad...");
                adapterListener.onNativeInterstitialFailed(null);
            }
        }
    }

    /* renamed from: com.adsdk.sdk.mraid.MoPubInterstitial.1 */
    class C08631 extends DefaultInterstitialAdapterListener {
        final /* synthetic */ MoPubInterstitial this$0;

        C08631(MoPubInterstitial moPubInterstitial, MoPubInterstitial moPubInterstitial2) {
            this.this$0 = moPubInterstitial2;
            super();
        }

        public void onNativeInterstitialLoaded(BaseInterstitialAdapter adapter) {
            super.onNativeInterstitialLoaded(adapter);
            this.this$0.show();
        }
    }

    static /* synthetic */ int[] m624xb84c64() {
        int[] iArr = f71xb84c64;
        if (iArr == null) {
            iArr = new int[InterstitialState.values().length];
            try {
                iArr[InterstitialState.HTML_AD_READY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[InterstitialState.NATIVE_AD_READY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[InterstitialState.NOT_READY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            f71xb84c64 = iArr;
        }
        return iArr;
    }

    public MoPubInterstitial(Activity activity, String id) {
        this.mActivity = activity;
        this.mAdUnitId = id;
        this.mInterstitialView = new MoPubInterstitialView(this.mActivity);
        this.mInterstitialView.setAdUnitId(this.mAdUnitId);
        this.mCurrentInterstitialState = InterstitialState.NOT_READY;
        this.mDefaultAdapterListener = new DefaultInterstitialAdapterListener();
        this.mAdapterListener = this.mDefaultAdapterListener;
    }

    public void load() {
        invalidateCurrentInterstitial();
        this.mInterstitialView.loadAd();
    }

    public void forceRefresh() {
        invalidateCurrentInterstitial();
        this.mInterstitialView.forceRefresh();
    }

    private void invalidateCurrentInterstitial() {
        this.mCurrentInterstitialState = InterstitialState.NOT_READY;
        if (this.mInterstitialAdapter != null) {
            this.mInterstitialAdapter.invalidate();
            this.mInterstitialAdapter = null;
        }
        this.mAdapterListener = this.mDefaultAdapterListener;
        this.mInterstitialView.setAdListener(this);
        this.mInterstitialView.loadAd();
    }

    public boolean isReady() {
        return this.mCurrentInterstitialState == InterstitialState.HTML_AD_READY || this.mCurrentInterstitialState == InterstitialState.NATIVE_AD_READY;
    }

    public boolean show() {
        switch (m624xb84c64()[this.mCurrentInterstitialState.ordinal()]) {
            case Value.STRING_FIELD_NUMBER /*2*/:
                showNativeInterstitial();
                return true;
            default:
                return false;
        }
    }

    private void showNativeInterstitial() {
        if (this.mInterstitialAdapter != null) {
            this.mInterstitialAdapter.showInterstitial();
        }
    }

    public void OnAdFailed(MoPubView m) {
        this.mCurrentInterstitialState = InterstitialState.NOT_READY;
        if (this.mListener != null) {
            this.mListener.OnInterstitialFailed();
        }
    }

    public void OnAdLoaded(MoPubView m) {
        this.mCurrentInterstitialState = InterstitialState.HTML_AD_READY;
        if (this.mInterstitialAdapter != null) {
            this.mInterstitialAdapter.invalidate();
            this.mInterstitialAdapter = null;
        }
        if (this.mListener != null) {
            this.mListener.OnInterstitialLoaded();
        }
    }

    public void customEventDidLoadAd() {
    }

    public void customEventDidFailToLoadAd() {
        if (this.mInterstitialView != null) {
            this.mInterstitialView.loadFailUrl();
        }
    }

    public void customEventActionWillBegin() {
        if (this.mInterstitialView != null) {
            this.mInterstitialView.registerClick();
        }
    }

    @Deprecated
    public void showAd() {
        this.mAdapterListener = new C08631(this, this);
        this.mInterstitialView.loadAd();
    }

    public void setKeywords(String keywords) {
        if (this.mInterstitialView != null) {
            this.mInterstitialView.setKeywords(keywords);
        }
    }

    public String getKeywords() {
        return this.mInterstitialView != null ? this.mInterstitialView.getKeywords() : null;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public void setListener(MoPubInterstitialListener listener) {
        this.mListener = listener;
    }

    public MoPubInterstitialListener getListener() {
        return this.mListener;
    }

    public Location getLocation() {
        return this.mInterstitialView.getLocation();
    }

    public void destroy() {
        this.mAdapterListener = null;
        if (this.mInterstitialAdapter != null) {
            this.mInterstitialAdapter.invalidate();
            this.mInterstitialAdapter = null;
        }
        this.mInterstitialView.setAdListener(null);
        this.mInterstitialView.destroy();
    }

    public void setLocationAwareness(LocationAwareness awareness) {
        this.mInterstitialView.setLocationAwareness(awareness);
    }

    public LocationAwareness getLocationAwareness() {
        return this.mInterstitialView.getLocationAwareness();
    }

    public void setLocationPrecision(int precision) {
        this.mInterstitialView.setLocationPrecision(precision);
    }

    public int getLocationPrecision() {
        return this.mInterstitialView.getLocationPrecision();
    }

    protected BaseInterstitialAdapterListener getInterstitialAdapterListener() {
        return this.mAdapterListener;
    }

    public void adClicked() {
    }

    public void adClosed(Ad ad, boolean completed) {
    }

    public void adLoadSucceeded(Ad ad) {
    }

    public void adShown(Ad ad, boolean succeeded) {
    }

    public void noAdFound() {
    }
}
