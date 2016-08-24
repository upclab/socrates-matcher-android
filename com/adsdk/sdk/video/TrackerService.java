package com.adsdk.sdk.video;

import com.adsdk.sdk.Const;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public class TrackerService {
    private static Object sLock;
    private static Queue<TrackEvent> sRetryTrackEvents;
    private static boolean sStopped;
    private static Thread sThread;
    private static boolean sThreadRunning;
    private static Queue<TrackEvent> sTrackEvents;

    /* renamed from: com.adsdk.sdk.video.TrackerService.1 */
    class C00781 implements Runnable {
        C00781() {
        }

        public void run() {
            TrackerService.sStopped = false;
            while (!TrackerService.sStopped) {
                while (TrackerService.hasMoreUpdates() && !TrackerService.sStopped) {
                    TrackEvent event = TrackerService.getNextUpdate();
                    if (event != null) {
                        try {
                            URL u = new URL(event.url);
                            DefaultHttpClient client = new DefaultHttpClient();
                            HttpConnectionParams.setSoTimeout(client.getParams(), Const.SOCKET_TIMEOUT);
                            HttpConnectionParams.setConnectionTimeout(client.getParams(), Const.SOCKET_TIMEOUT);
                            try {
                                if (client.execute(new HttpGet(u.toString())).getStatusLine().getStatusCode() != 200) {
                                    TrackerService.requestRetry(event);
                                }
                            } catch (Throwable th) {
                                TrackerService.requestRetry(event);
                            }
                        } catch (MalformedURLException e) {
                        }
                    }
                }
                if (TrackerService.sStopped || TrackerService.sRetryTrackEvents.isEmpty()) {
                    TrackerService.sStopped = true;
                } else {
                    try {
                        Thread.sleep(30000);
                    } catch (Exception e2) {
                    }
                    synchronized (TrackerService.sLock) {
                        TrackerService.sTrackEvents.addAll(TrackerService.sRetryTrackEvents);
                        TrackerService.sRetryTrackEvents.clear();
                    }
                }
            }
            TrackerService.sStopped = false;
            TrackerService.sThreadRunning = false;
            TrackerService.sThread = null;
        }
    }

    /* renamed from: com.adsdk.sdk.video.TrackerService.2 */
    class C00792 implements UncaughtExceptionHandler {
        C00792() {
        }

        public void uncaughtException(Thread thread, Throwable ex) {
            TrackerService.sThreadRunning = false;
            TrackerService.sThread = null;
            TrackerService.startTracking();
        }
    }

    static {
        sLock = new Object();
        sThreadRunning = false;
        sTrackEvents = new LinkedList();
        sRetryTrackEvents = new LinkedList();
    }

    public static void requestTrack(TrackEvent[] trackEvents) {
        synchronized (sLock) {
            for (TrackEvent trackEvent : trackEvents) {
                if (!sTrackEvents.contains(trackEvent)) {
                    sTrackEvents.add(trackEvent);
                }
            }
        }
        if (!sThreadRunning) {
            startTracking();
        }
    }

    public static void requestTrack(TrackEvent trackEvent) {
        synchronized (sLock) {
            if (!sTrackEvents.contains(trackEvent)) {
                sTrackEvents.add(trackEvent);
            }
        }
        if (!sThreadRunning) {
            startTracking();
        }
    }

    public static void requestRetry(TrackEvent trackEvent) {
        synchronized (sLock) {
            if (!sRetryTrackEvents.contains(trackEvent)) {
                trackEvent.retries++;
                if (trackEvent.retries <= 5) {
                    sRetryTrackEvents.add(trackEvent);
                }
            }
        }
    }

    private static boolean hasMoreUpdates() {
        boolean hasMore;
        synchronized (sLock) {
            hasMore = !sTrackEvents.isEmpty();
        }
        return hasMore;
    }

    private static TrackEvent getNextUpdate() {
        synchronized (sLock) {
            if (sTrackEvents.peek() == null) {
                return null;
            }
            TrackEvent nextTrackEvent = (TrackEvent) sTrackEvents.poll();
            return nextTrackEvent;
        }
    }

    public static void startTracking() {
        synchronized (sLock) {
            if (!sThreadRunning) {
                sThreadRunning = true;
                sThread = new Thread(new C00781());
                sThread.setUncaughtExceptionHandler(new C00792());
                sThread.start();
            }
        }
    }

    public static void release() {
        if (sThread != null) {
            sStopped = true;
        }
    }
}
