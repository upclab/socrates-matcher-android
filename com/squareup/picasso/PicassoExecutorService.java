package com.squareup.picasso;

import android.net.NetworkInfo;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.joda.time.MutableDateTime;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    PicassoExecutorService() {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(DEFAULT_THREAD_COUNT);
            return;
        }
        switch (info.getType()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                switch (info.getSubtype()) {
                    case Value.TYPE_FIELD_NUMBER /*1*/:
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        setThreadCount(1);
                    case DEFAULT_THREAD_COUNT /*3*/:
                    case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                        setThreadCount(2);
                    case Resource.VERSION_FIELD_NUMBER /*13*/:
                    case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                    case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                    default:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                }
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                setThreadCount(4);
            default:
                setThreadCount(DEFAULT_THREAD_COUNT);
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }
}
