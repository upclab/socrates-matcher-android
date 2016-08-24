package com.jwetherell.augmented_reality.activity;

import android.app.Activity;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import com.jwetherell.augmented_reality.common.LowPassFilter;
import com.jwetherell.augmented_reality.common.Matrix;
import com.jwetherell.augmented_reality.data.ARData;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SensorsActivity extends Activity implements SensorEventListener, LocationListener {
    private static final int MIN_DISTANCE = 10;
    private static final int MIN_TIME = 30000;
    private static final String TAG = "SensorsActivity";
    private static final AtomicBoolean computing;
    private static GeomagneticField gmf;
    private static final float[] grav;
    private static LocationManager locationMgr;
    private static final float[] mag;
    private static final Matrix mageticNorthCompensation;
    private static final Matrix magneticCompensatedCoord;
    private static final float[] rotation;
    private static Sensor sensorGrav;
    private static Sensor sensorMag;
    private static SensorManager sensorMgr;
    private static List<Sensor> sensors;
    private static float[] smooth;
    private static final float[] temp;
    private static final Matrix worldCoord;
    private static final Matrix xAxisRotation;
    private static final Matrix yAxisRotation;

    static {
        computing = new AtomicBoolean(false);
        temp = new float[9];
        rotation = new float[9];
        grav = new float[3];
        mag = new float[3];
        worldCoord = new Matrix();
        magneticCompensatedCoord = new Matrix();
        xAxisRotation = new Matrix();
        yAxisRotation = new Matrix();
        mageticNorthCompensation = new Matrix();
        gmf = null;
        smooth = new float[3];
        sensorMgr = null;
        sensors = null;
        sensorGrav = null;
        sensorMag = null;
        locationMgr = null;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        float neg90rads = (float) Math.toRadians(-90.0d);
        xAxisRotation.set(AugmentedReality.ONE_PERCENT, 0.0f, 0.0f, 0.0f, FloatMath.cos(neg90rads), -FloatMath.sin(neg90rads), 0.0f, FloatMath.sin(neg90rads), FloatMath.cos(neg90rads));
        yAxisRotation.set(FloatMath.cos(neg90rads), 0.0f, FloatMath.sin(neg90rads), 0.0f, AugmentedReality.ONE_PERCENT, 0.0f, -FloatMath.sin(neg90rads), 0.0f, FloatMath.cos(neg90rads));
        try {
            sensorMgr = (SensorManager) getSystemService("sensor");
            sensors = sensorMgr.getSensorList(1);
            if (sensors.size() > 0) {
                sensorGrav = (Sensor) sensors.get(0);
            }
            sensors = sensorMgr.getSensorList(2);
            if (sensors.size() > 0) {
                sensorMag = (Sensor) sensors.get(0);
            }
            sensorMgr.registerListener(this, sensorGrav, 3);
            sensorMgr.registerListener(this, sensorMag, 3);
            locationMgr = (LocationManager) getSystemService("location");
            locationMgr.requestLocationUpdates("gps", 30000, AugmentedReality.TEN_PERCENT, this);
            try {
                Location gps = locationMgr.getLastKnownLocation("gps");
                Location network = locationMgr.getLastKnownLocation("network");
                if (gps != null) {
                    onLocationChanged(gps);
                } else if (network != null) {
                    onLocationChanged(network);
                } else {
                    onLocationChanged(ARData.hardFix);
                }
            } catch (Exception e) {
                onLocationChanged(ARData.hardFix);
            }
            try {
                gmf = new GeomagneticField((float) ARData.getCurrentLocation().getLatitude(), (float) ARData.getCurrentLocation().getLongitude(), (float) ARData.getCurrentLocation().getAltitude(), System.currentTimeMillis());
                float dec = (float) Math.toRadians((double) (-gmf.getDeclination()));
                synchronized (mageticNorthCompensation) {
                    mageticNorthCompensation.toIdentity();
                    mageticNorthCompensation.set(FloatMath.cos(dec), 0.0f, FloatMath.sin(dec), 0.0f, AugmentedReality.ONE_PERCENT, 0.0f, -FloatMath.sin(dec), 0.0f, FloatMath.cos(dec));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e2) {
            try {
                if (sensorMgr != null) {
                    sensorMgr.unregisterListener(this, sensorGrav);
                    sensorMgr.unregisterListener(this, sensorMag);
                    sensorMgr = null;
                }
                if (locationMgr != null) {
                    locationMgr.removeUpdates(this);
                    locationMgr = null;
                }
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }
        }
    }

    protected void onStop() {
        super.onStop();
        try {
            sensorMgr.unregisterListener(this, sensorGrav);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            sensorMgr.unregisterListener(this, sensorMag);
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        try {
            sensorMgr = null;
            try {
                locationMgr.removeUpdates(this);
            } catch (Exception ex22) {
                ex22.printStackTrace();
            }
            locationMgr = null;
        } catch (Exception ex222) {
            ex222.printStackTrace();
        }
    }

    public void onSensorChanged(SensorEvent evt) {
        if (computing.compareAndSet(false, true)) {
            if (evt.sensor.getType() == 1) {
                smooth = LowPassFilter.filter(0.5f, AugmentedReality.ONE_PERCENT, evt.values, grav);
                grav[0] = smooth[0];
                grav[1] = smooth[1];
                grav[2] = smooth[2];
            } else if (evt.sensor.getType() == 2) {
                smooth = LowPassFilter.filter(2.0f, 4.0f, evt.values, mag);
                mag[0] = smooth[0];
                mag[1] = smooth[1];
                mag[2] = smooth[2];
            }
            SensorManager.getRotationMatrix(temp, null, grav, mag);
            SensorManager.remapCoordinateSystem(temp, 2, 131, rotation);
            worldCoord.set(rotation[0], rotation[1], rotation[2], rotation[3], rotation[4], rotation[5], rotation[6], rotation[7], rotation[8]);
            magneticCompensatedCoord.toIdentity();
            synchronized (mageticNorthCompensation) {
                magneticCompensatedCoord.prod(mageticNorthCompensation);
            }
            magneticCompensatedCoord.prod(xAxisRotation);
            magneticCompensatedCoord.prod(worldCoord);
            magneticCompensatedCoord.prod(yAxisRotation);
            magneticCompensatedCoord.invert();
            ARData.setRotationMatrix(magneticCompensatedCoord);
            computing.set(false);
        }
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onLocationChanged(Location location) {
        ARData.setCurrentLocation(location);
        gmf = new GeomagneticField((float) ARData.getCurrentLocation().getLatitude(), (float) ARData.getCurrentLocation().getLongitude(), (float) ARData.getCurrentLocation().getAltitude(), System.currentTimeMillis());
        float dec = (float) Math.toRadians((double) (-gmf.getDeclination()));
        synchronized (mageticNorthCompensation) {
            mageticNorthCompensation.toIdentity();
            mageticNorthCompensation.set(FloatMath.cos(dec), 0.0f, FloatMath.sin(dec), 0.0f, AugmentedReality.ONE_PERCENT, 0.0f, -FloatMath.sin(dec), 0.0f, FloatMath.cos(dec));
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor == null) {
            throw new NullPointerException();
        } else if (sensor.getType() == 2 && accuracy == 0) {
            Log.e(TAG, "Compass data unreliable");
        }
    }
}
