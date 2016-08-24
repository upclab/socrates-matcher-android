package com.jwetherell.augmented_reality.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.jwetherell.augmented_reality.C0359R;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.GooglePlacesDataSource;
import com.jwetherell.augmented_reality.data.LocalDataSource;
import com.jwetherell.augmented_reality.data.NetworkDataSource;
import com.jwetherell.augmented_reality.data.TwitterDataSource;
import com.jwetherell.augmented_reality.data.WikipediaDataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.widget.VerticalTextView;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo extends AugmentedReality {
    private static final String TAG = "Demo";
    private static final ThreadPoolExecutor exeService;
    private static final String locale;
    private static Toast myToast;
    private static final BlockingQueue<Runnable> queue;
    private static final Map<String, NetworkDataSource> sources;
    private static VerticalTextView text;

    /* renamed from: com.jwetherell.augmented_reality.activity.Demo.1 */
    class C03611 implements Runnable {
        private final /* synthetic */ double val$alt;
        private final /* synthetic */ double val$lat;
        private final /* synthetic */ double val$lon;

        C03611(double d, double d2, double d3) {
            this.val$lat = d;
            this.val$lon = d2;
            this.val$alt = d3;
        }

        public void run() {
            for (NetworkDataSource source : Demo.sources.values()) {
                Demo.download(source, this.val$lat, this.val$lon, this.val$alt);
            }
        }
    }

    static {
        locale = Locale.getDefault().getLanguage();
        queue = new ArrayBlockingQueue(1);
        exeService = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, queue);
        sources = new ConcurrentHashMap();
        myToast = null;
        text = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myToast = new Toast(getApplicationContext());
        myToast.setGravity(17, 0, 0);
        text = new VerticalTextView(getApplicationContext());
        text.setLayoutParams(new LayoutParams(-2, -2));
        text.setBackgroundResource(17301654);
        text.setTextAppearance(getApplicationContext(), 16973894);
        text.setShadowLayer(2.75f, 0.0f, 0.0f, Color.parseColor("#BB000000"));
        myToast.setView(text);
        myToast.setDuration(0);
        ARData.addMarkers(new LocalDataSource(getResources()).getMarkers());
        sources.put("twitter", new TwitterDataSource(getResources()));
        sources.put("wiki", new WikipediaDataSource(getResources()));
        sources.put("googlePlaces", new GooglePlacesDataSource(getResources()));
    }

    public void onStart() {
        super.onStart();
        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0359R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int i = 0;
        Log.v(TAG, "onOptionsItemSelected() item=" + item);
        if (item.getItemId() == C0359R.id.showRadar) {
            boolean z;
            if (!showRadar) {
                z = true;
            }
            showRadar = z;
            item.setTitle(new StringBuilder(String.valueOf(showRadar ? "Hide" : "Show")).append(" Radar").toString());
        } else if (item.getItemId() == C0359R.id.showZoomBar) {
            boolean z2;
            if (showZoomBar) {
                z2 = false;
            } else {
                z2 = true;
            }
            showZoomBar = z2;
            item.setTitle(new StringBuilder(String.valueOf(showZoomBar ? "Hide" : "Show")).append(" Zoom Bar").toString());
            LinearLayout linearLayout = zoomLayout;
            if (!showZoomBar) {
                i = 8;
            }
            linearLayout.setVisibility(i);
        } else if (item.getItemId() == C0359R.id.exit) {
            finish();
        }
        return true;
    }

    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        updateData(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    protected void markerTouched(Marker marker) {
        text.setText(marker.getName());
        myToast.show();
    }

    protected void updateDataOnZoom() {
        super.updateDataOnZoom();
        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
    }

    private void updateData(double lat, double lon, double alt) {
        try {
            exeService.execute(new C03611(lat, lon, alt));
        } catch (RejectedExecutionException e) {
            Log.w(TAG, "Not running new download Runnable, queue is full.");
        } catch (Exception e2) {
            Log.e(TAG, "Exception running download Runnable.", e2);
        }
    }

    private static boolean download(NetworkDataSource source, double lat, double lon, double alt) {
        if (source == null) {
            return false;
        }
        try {
            try {
                ARData.addMarkers(source.parse(source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale)));
                return true;
            } catch (NullPointerException e) {
                return false;
            }
        } catch (NullPointerException e2) {
            return false;
        }
    }
}
