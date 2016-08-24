package com.jwetherell.augmented_reality.data;

import android.location.Location;
import android.util.Log;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.common.Matrix;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ARData {
    private static final String TAG = "ARData";
    private static float azimuth;
    private static final Object azimuthLock;
    private static final List<Marker> cache;
    private static final Comparator<Marker> comparator;
    private static Location currentLocation;
    private static final AtomicBoolean dirty;
    public static final Location hardFix;
    private static final float[] locationArray;
    private static final Map<String, Marker> markerList;
    private static float radius;
    private static final Object radiusLock;
    private static float roll;
    private static final Object rollLock;
    private static Matrix rotationMatrix;
    private static String zoomLevel;
    private static int zoomProgress;
    private static final Object zoomProgressLock;

    /* renamed from: com.jwetherell.augmented_reality.data.ARData.1 */
    class C03621 implements Comparator<Marker> {
        C03621() {
        }

        public int compare(Marker arg0, Marker arg1) {
            return Double.compare(arg0.getDistance(), arg1.getDistance());
        }
    }

    static {
        markerList = new ConcurrentHashMap();
        cache = new CopyOnWriteArrayList();
        dirty = new AtomicBoolean(false);
        locationArray = new float[3];
        hardFix = new Location("ATL");
        hardFix.setLatitude(39.931261d);
        hardFix.setLongitude(-75.051267d);
        hardFix.setAltitude(1.0d);
        radiusLock = new Object();
        radius = new Float(AugmentedReality.TWENTY_PERCENT).floatValue();
        zoomLevel = new String();
        zoomProgressLock = new Object();
        zoomProgress = 0;
        currentLocation = hardFix;
        rotationMatrix = new Matrix();
        azimuthLock = new Object();
        azimuth = 0.0f;
        rollLock = new Object();
        roll = 0.0f;
        comparator = new C03621();
    }

    public static void setZoomLevel(String zoomLevel) {
        if (zoomLevel == null) {
            throw new NullPointerException();
        }
        synchronized (zoomLevel) {
            zoomLevel = zoomLevel;
        }
    }

    public static String getZoomLevel() {
        String str;
        synchronized (zoomLevel) {
            str = zoomLevel;
        }
        return str;
    }

    public static void setZoomProgress(int zoomProgress) {
        synchronized (zoomProgressLock) {
            if (zoomProgress != zoomProgress) {
                zoomProgress = zoomProgress;
                if (dirty.compareAndSet(false, true)) {
                    Log.v(TAG, "Setting DIRTY flag!");
                    cache.clear();
                }
            }
        }
    }

    public static int getZoomProgress() {
        int i;
        synchronized (zoomProgressLock) {
            i = zoomProgress;
        }
        return i;
    }

    public static void setRadius(float radius) {
        synchronized (radiusLock) {
            radius = radius;
        }
    }

    public static float getRadius() {
        float f;
        synchronized (radiusLock) {
            f = radius;
        }
        return f;
    }

    public static void setCurrentLocation(Location currentLocation) {
        if (currentLocation == null) {
            throw new NullPointerException();
        }
        Log.d(TAG, "current location. location=" + currentLocation.toString());
        synchronized (currentLocation) {
            currentLocation = currentLocation;
        }
        onLocationChanged(currentLocation);
    }

    private static void onLocationChanged(Location location) {
        Log.d(TAG, "New location, updating markers. location=" + location.toString());
        for (Marker ma : markerList.values()) {
            ma.calcRelativePosition(location);
        }
        if (dirty.compareAndSet(false, true)) {
            Log.v(TAG, "Setting DIRTY flag!");
            cache.clear();
        }
    }

    public static Location getCurrentLocation() {
        Location location;
        synchronized (currentLocation) {
            location = currentLocation;
        }
        return location;
    }

    public static void setRotationMatrix(Matrix rotationMatrix) {
        synchronized (rotationMatrix) {
            rotationMatrix = rotationMatrix;
        }
    }

    public static Matrix getRotationMatrix() {
        Matrix matrix;
        synchronized (rotationMatrix) {
            matrix = rotationMatrix;
        }
        return matrix;
    }

    public static void addMarkers(Collection<Marker> markers) {
        if (markers == null) {
            throw new NullPointerException();
        } else if (markers.size() > 0) {
            Log.d(TAG, "New markers, updating markers. new markers=" + markers.toString());
            for (Marker marker : markers) {
                if (!markerList.containsKey(marker.getName())) {
                    marker.calcRelativePosition(getCurrentLocation());
                    markerList.put(marker.getName(), marker);
                }
            }
            if (dirty.compareAndSet(false, true)) {
                Log.v(TAG, "Setting DIRTY flag!");
                cache.clear();
            }
        }
    }

    public static List<Marker> getMarkers() {
        if (dirty.compareAndSet(true, false)) {
            Log.v(TAG, "DIRTY flag found, resetting all marker heights to zero.");
            for (Marker ma : markerList.values()) {
                ma.getLocation().get(locationArray);
                locationArray[1] = ma.getInitialY();
                ma.getLocation().set(locationArray);
            }
            Log.v(TAG, "Populating the cache.");
            List<Marker> copy = new ArrayList();
            copy.addAll(markerList.values());
            Collections.sort(copy, comparator);
            cache.clear();
            cache.addAll(copy);
        }
        return Collections.unmodifiableList(cache);
    }

    public static void setAzimuth(float azimuth) {
        synchronized (azimuthLock) {
            azimuth = azimuth;
        }
    }

    public static float getAzimuth() {
        float f;
        synchronized (azimuthLock) {
            f = azimuth;
        }
        return f;
    }

    public static void setRoll(float roll) {
        synchronized (rollLock) {
            roll = roll;
        }
    }

    public static float getRoll() {
        float f;
        synchronized (rollLock) {
            f = roll;
        }
        return f;
    }
}
