package com.google.analytics.tracking.android;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.text.TextUtils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

class NoopDispatcher implements Dispatcher {
    NoopDispatcher() {
    }

    public boolean okToDispatch() {
        return true;
    }

    public int dispatchHits(List<Hit> hits) {
        if (hits == null) {
            return 0;
        }
        int maxHits = Math.min(hits.size(), 40);
        if (!Log.isVerbose()) {
            return maxHits;
        }
        Log.m3v("Hits not actually being sent as dispatch is false...");
        for (int i = 0; i < maxHits; i++) {
            String logMessage;
            String modifiedHit = TextUtils.isEmpty(((Hit) hits.get(i)).getHitParams()) ? StringUtils.EMPTY : HitBuilder.postProcessHit((Hit) hits.get(i), System.currentTimeMillis());
            if (TextUtils.isEmpty(modifiedHit)) {
                logMessage = "Hit couldn't be read, wouldn't be sent:";
            } else if (modifiedHit.length() <= 2036) {
                logMessage = "GET would be sent:";
            } else if (modifiedHit.length() > AccessibilityEventCompat.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                logMessage = "Would be too big:";
            } else {
                logMessage = "POST would be sent:";
            }
            Log.m3v(logMessage + modifiedHit);
        }
        return maxHits;
    }

    public void overrideHostUrl(String hostOverride) {
    }

    public void close() {
    }
}
