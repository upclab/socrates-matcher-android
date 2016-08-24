package com.jwetherell.augmented_reality.ui;

import android.graphics.Canvas;
import android.location.Location;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.camera.CameraModel;
import com.jwetherell.augmented_reality.common.Vector;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.PhysicalLocation;
import com.jwetherell.augmented_reality.ui.objects.PaintableBox;
import com.jwetherell.augmented_reality.ui.objects.PaintableBoxedText;
import com.jwetherell.augmented_reality.ui.objects.PaintableGps;
import com.jwetherell.augmented_reality.ui.objects.PaintableObject;
import com.jwetherell.augmented_reality.ui.objects.PaintablePoint;
import com.jwetherell.augmented_reality.ui.objects.PaintablePosition;
import java.text.DecimalFormat;

public class Marker implements Comparable<Marker> {
    private static final DecimalFormat DECIMAL_FORMAT;
    private static volatile CameraModel cam;
    private static boolean debugGpsPosition;
    private static boolean debugTouchZone;
    private static final Vector locationVector;
    private int color;
    private double distance;
    private final float[] distanceArray;
    protected PaintableObject gpsSymbol;
    private float initialY;
    private volatile boolean isInView;
    private volatile boolean isOnRadar;
    private final float[] locationArray;
    private final Vector locationXyzRelativeToCameraView;
    private final Vector locationXyzRelativeToPhysicalLocation;
    private String name;
    private boolean noAltitude;
    private final PhysicalLocation physicalLocation;
    private volatile PaintablePosition positionContainer;
    private PaintablePoint positionPoint;
    private final Vector screenPositionVector;
    private volatile PaintablePosition symbolContainer;
    private PaintableBoxedText textBox;
    private volatile PaintablePosition textContainer;
    private final Vector tmpLocationVector;
    private final Vector tmpVector;
    private PaintableBox touchBox;
    private volatile PaintablePosition touchPosition;

    static {
        DECIMAL_FORMAT = new DecimalFormat("@#");
        locationVector = new Vector(0.0f, 0.0f, 0.0f);
        cam = null;
        debugGpsPosition = false;
        debugTouchZone = false;
    }

    public Marker(String name, double latitude, double longitude, double altitude, int color) {
        this.screenPositionVector = new Vector();
        this.tmpVector = new Vector();
        this.tmpLocationVector = new Vector();
        this.locationXyzRelativeToCameraView = new Vector();
        this.distanceArray = new float[1];
        this.locationArray = new float[3];
        this.initialY = 0.0f;
        this.gpsSymbol = null;
        this.symbolContainer = null;
        this.textBox = null;
        this.textContainer = null;
        this.name = null;
        this.physicalLocation = new PhysicalLocation();
        this.distance = 0.0d;
        this.isOnRadar = false;
        this.isInView = false;
        this.locationXyzRelativeToPhysicalLocation = new Vector();
        this.color = -1;
        this.noAltitude = false;
        this.positionPoint = null;
        this.positionContainer = null;
        this.touchBox = null;
        this.touchPosition = null;
        set(name, latitude, longitude, altitude, color);
    }

    public synchronized void set(String name, double latitude, double longitude, double altitude, int color) {
        if (name == null) {
            throw new NullPointerException();
        }
        this.name = name;
        this.physicalLocation.set(latitude, longitude, altitude);
        this.color = color;
        this.isOnRadar = false;
        this.isInView = false;
        this.locationXyzRelativeToPhysicalLocation.set(0.0f, 0.0f, 0.0f);
        this.initialY = 0.0f;
        if (altitude == 0.0d) {
            this.noAltitude = true;
        } else {
            this.noAltitude = false;
        }
    }

    public synchronized String getName() {
        return this.name;
    }

    public synchronized int getColor() {
        return this.color;
    }

    public synchronized double getDistance() {
        return this.distance;
    }

    public synchronized float getInitialY() {
        return this.initialY;
    }

    public synchronized boolean isOnRadar() {
        return this.isOnRadar;
    }

    public synchronized boolean isInView() {
        return this.isInView;
    }

    public synchronized Vector getScreenPosition() {
        this.screenPositionVector.set(this.locationXyzRelativeToCameraView);
        return this.screenPositionVector;
    }

    public synchronized Vector getLocation() {
        return this.locationXyzRelativeToPhysicalLocation;
    }

    public synchronized float getHeight() {
        float f;
        if (this.symbolContainer == null || this.textContainer == null) {
            f = 0.0f;
        } else {
            f = this.symbolContainer.getHeight() + this.textContainer.getHeight();
        }
        return f;
    }

    public synchronized float getWidth() {
        float f;
        if (this.symbolContainer == null || this.textContainer == null) {
            f = 0.0f;
        } else {
            float symbolWidth = this.symbolContainer.getWidth();
            f = this.textContainer.getWidth();
            if (f <= symbolWidth) {
                f = symbolWidth;
            }
        }
        return f;
    }

    public synchronized void update(Canvas canvas, float addX, float addY) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (cam == null) {
            cam = new CameraModel(canvas.getWidth(), canvas.getHeight(), true);
        }
        cam.set(canvas.getWidth(), canvas.getHeight(), false);
        cam.setViewAngle(CameraModel.DEFAULT_VIEW_ANGLE);
        populateMatrices(cam, addX, addY);
        updateRadar();
        updateView();
    }

    private synchronized void populateMatrices(CameraModel cam, float addX, float addY) {
        if (cam == null) {
            throw new NullPointerException();
        }
        this.tmpLocationVector.set(locationVector);
        this.tmpLocationVector.add(this.locationXyzRelativeToPhysicalLocation);
        this.tmpLocationVector.prod(ARData.getRotationMatrix());
        cam.projectPoint(this.tmpLocationVector, this.tmpVector, addX, addY);
        this.locationXyzRelativeToCameraView.set(this.tmpVector);
    }

    private synchronized void updateRadar() {
        this.isOnRadar = false;
        float scale = (ARData.getRadius() * 1000.0f) / Radar.RADIUS;
        this.locationXyzRelativeToPhysicalLocation.get(this.locationArray);
        float x = this.locationArray[0] / scale;
        float y = this.locationArray[2] / scale;
        if ((x * x) + (y * y) < 2304.0f) {
            this.isOnRadar = true;
        }
    }

    private synchronized void updateView() {
        this.isInView = false;
        if (this.isOnRadar) {
            this.locationXyzRelativeToCameraView.get(this.locationArray);
            if (this.locationArray[2] < GroundOverlayOptions.NO_DIMENSION) {
                this.locationXyzRelativeToCameraView.get(this.locationArray);
                float x = this.locationArray[0];
                float y = this.locationArray[1];
                float width = getWidth();
                float height = getHeight();
                if (AugmentedReality.portrait) {
                    x -= height / 2.0f;
                    y += width / 2.0f;
                } else {
                    x -= width / 2.0f;
                    y -= height / 2.0f;
                }
                float ulX = x;
                float ulY = y;
                float lrX = x;
                float lrY = y;
                if (AugmentedReality.portrait) {
                    lrX += height;
                    lrY -= width;
                } else {
                    lrX += width;
                    lrY += height;
                }
                if (AugmentedReality.portrait && lrX >= GroundOverlayOptions.NO_DIMENSION && ulX <= ((float) cam.getWidth()) && ulY >= GroundOverlayOptions.NO_DIMENSION && lrY <= ((float) cam.getHeight())) {
                    this.isInView = true;
                } else if (lrX >= GroundOverlayOptions.NO_DIMENSION) {
                    if (ulX <= ((float) cam.getWidth()) && lrY >= GroundOverlayOptions.NO_DIMENSION && ulY <= ((float) cam.getHeight())) {
                        this.isInView = true;
                    }
                }
            }
        }
    }

    public synchronized void calcRelativePosition(Location location) {
        if (location == null) {
            throw new NullPointerException();
        }
        updateDistance(location);
        if (this.noAltitude) {
            this.physicalLocation.setAltitude(location.getAltitude());
        }
        PhysicalLocation.convLocationToVector(location, this.physicalLocation, this.locationXyzRelativeToPhysicalLocation);
        this.initialY = this.locationXyzRelativeToPhysicalLocation.getY();
        updateRadar();
    }

    private synchronized void updateDistance(Location location) {
        if (location == null) {
            throw new NullPointerException();
        }
        Location.distanceBetween(this.physicalLocation.getLatitude(), this.physicalLocation.getLongitude(), location.getLatitude(), location.getLongitude(), this.distanceArray);
        this.distance = (double) this.distanceArray[0];
    }

    public synchronized boolean handleClick(float x, float y) {
        boolean result;
        if (this.isOnRadar && this.isInView) {
            result = isPointOnMarker(x, y, this);
        } else {
            result = false;
        }
        return result;
    }

    public synchronized boolean isMarkerOnMarker(Marker marker) {
        return isMarkerOnMarker(marker, true);
    }

    private synchronized boolean isMarkerOnMarker(Marker marker, boolean reflect) {
        boolean z;
        if (marker == null) {
            z = false;
        } else {
            float middleX;
            float middleY;
            marker.getScreenPosition().get(this.locationArray);
            float x = this.locationArray[0];
            float y = this.locationArray[1];
            float width = marker.getWidth();
            float height = marker.getHeight();
            if (AugmentedReality.portrait) {
                x -= height / 2.0f;
                y += width / 2.0f;
            } else {
                x -= width / 2.0f;
                y -= height / 2.0f;
            }
            if (AugmentedReality.portrait) {
                middleX = x + (height / 2.0f);
                middleY = y - (width / 2.0f);
            } else {
                middleX = x + (width / 2.0f);
                middleY = y + (height / 2.0f);
            }
            if (isPointOnMarker(middleX, middleY, this)) {
                z = true;
            } else {
                float ulX = x;
                float ulY = y;
                float urX = x;
                float urY = y;
                if (AugmentedReality.portrait) {
                    urX += height;
                } else {
                    urX += width;
                }
                float llX = x;
                float llY = y;
                if (AugmentedReality.portrait) {
                    llY -= width;
                } else {
                    llY += height;
                }
                float lrX = x;
                float lrY = y;
                if (AugmentedReality.portrait) {
                    lrX += height;
                    lrY -= width;
                } else {
                    lrX += width;
                    lrY += height;
                }
                z = isPointOnMarker(ulX, ulY, this) ? true : isPointOnMarker(urX, urY, this) ? true : isPointOnMarker(llX, llY, this) ? true : isPointOnMarker(lrX, lrY, this) ? true : reflect ? marker.isMarkerOnMarker(this, false) : false;
            }
        }
        return z;
    }

    private synchronized boolean isPointOnMarker(float xPoint, float yPoint, Marker marker) {
        boolean z;
        if (marker == null) {
            z = false;
        } else {
            marker.getScreenPosition().get(this.locationArray);
            float x = this.locationArray[0];
            float y = this.locationArray[1];
            float width = marker.getWidth();
            float height = marker.getHeight();
            if (AugmentedReality.portrait) {
                x -= height / 2.0f;
                y += width / 2.0f;
            } else {
                x -= width / 2.0f;
                y -= height / 2.0f;
            }
            float ulX = x;
            float ulY = y;
            float lrX = x;
            float lrY = y;
            if (AugmentedReality.portrait) {
                lrX += height;
                lrY -= width;
            } else {
                lrX += width;
                lrY += height;
            }
            if (AugmentedReality.portrait) {
                if (xPoint >= ulX && xPoint <= lrX && yPoint <= ulY && yPoint >= lrY) {
                    z = true;
                }
            } else if (xPoint >= ulX && xPoint <= lrX && yPoint >= ulY && yPoint <= lrY) {
                z = true;
            }
            z = false;
        }
        return z;
    }

    public synchronized void draw(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        } else if (this.isOnRadar && this.isInView) {
            if (debugTouchZone) {
                drawTouchZone(canvas);
            }
            drawIcon(canvas);
            drawText(canvas);
            if (debugGpsPosition) {
                drawPosition(canvas);
            }
        }
    }

    private synchronized void drawPosition(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (this.positionPoint == null) {
            this.positionPoint = new PaintablePoint(-65281, true);
        }
        getScreenPosition().get(this.locationArray);
        float currentAngle = 0.0f;
        if (AugmentedReality.portrait) {
            currentAngle = -90.0f;
        }
        if (this.positionContainer == null) {
            this.positionContainer = new PaintablePosition(this.positionPoint, this.locationArray[0], this.locationArray[1], currentAngle, AugmentedReality.ONE_PERCENT);
        } else {
            this.positionContainer.set(this.positionPoint, this.locationArray[0], this.locationArray[1], currentAngle, AugmentedReality.ONE_PERCENT);
        }
        this.positionContainer.paint(canvas);
    }

    private synchronized void drawTouchZone(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        } else if (this.gpsSymbol != null) {
            if (this.touchBox == null) {
                this.touchBox = new PaintableBox(getWidth(), getHeight(), -1, -16711936);
            } else {
                this.touchBox.set(getWidth(), getHeight());
            }
            getScreenPosition().get(this.locationArray);
            float x = this.locationArray[0];
            float y = this.locationArray[1];
            if (AugmentedReality.portrait) {
                x -= this.textBox.getWidth() / 2.0f;
                y = (y - (this.textBox.getWidth() / 2.0f)) + (this.gpsSymbol.getHeight() / 2.0f);
            } else {
                x -= this.textBox.getWidth() / 2.0f;
                y -= this.gpsSymbol.getHeight();
            }
            float currentAngle = 0.0f;
            if (AugmentedReality.portrait) {
                currentAngle = -90.0f;
            }
            if (this.touchPosition == null) {
                this.touchPosition = new PaintablePosition(this.touchBox, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
            } else {
                this.touchPosition.set(this.touchBox, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
            }
            this.touchPosition.paint(canvas);
        }
    }

    protected synchronized void drawIcon(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        if (this.gpsSymbol == null) {
            this.gpsSymbol = new PaintableGps(36.0f, 8.0f, true, getColor());
        }
        getScreenPosition().get(this.locationArray);
        float x = this.locationArray[0];
        float y = this.locationArray[1];
        if (AugmentedReality.portrait) {
            x -= this.gpsSymbol.getWidth() / 2.0f;
            y -= this.gpsSymbol.getHeight();
        } else {
            y -= this.gpsSymbol.getHeight() / 2.0f;
        }
        float currentAngle = 0.0f;
        if (AugmentedReality.portrait) {
            currentAngle = -90.0f;
        }
        if (this.symbolContainer == null) {
            this.symbolContainer = new PaintablePosition(this.gpsSymbol, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
        } else {
            this.symbolContainer.set(this.gpsSymbol, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
        }
        this.symbolContainer.paint(canvas);
    }

    private synchronized void drawText(Canvas canvas) {
        if (canvas == null) {
            throw new NullPointerException();
        }
        String textStr;
        if (this.distance < 1000.0d) {
            textStr = this.name + " (" + DECIMAL_FORMAT.format(this.distance) + "m)";
        } else {
            textStr = this.name + " (" + DECIMAL_FORMAT.format(this.distance / 1000.0d) + "km)";
        }
        float maxHeight = (float) (Math.round(((float) canvas.getHeight()) / AugmentedReality.TEN_PERCENT) + 1);
        if (this.textBox == null) {
            this.textBox = new PaintableBoxedText(textStr, (float) (Math.round(maxHeight / 2.0f) + 1), BitmapDescriptorFactory.HUE_MAGENTA);
        } else {
            this.textBox.set(textStr, (float) (Math.round(maxHeight / 2.0f) + 1), BitmapDescriptorFactory.HUE_MAGENTA);
        }
        getScreenPosition().get(this.locationArray);
        float x = this.locationArray[0];
        float y = this.locationArray[1];
        if (AugmentedReality.portrait) {
            x = (x - (this.textBox.getWidth() / 2.0f)) + (this.textBox.getHeight() / 2.0f);
            y -= this.textBox.getHeight() / 2.0f;
        } else {
            x -= this.textBox.getWidth() / 2.0f;
        }
        float currentAngle = 0.0f;
        if (AugmentedReality.portrait) {
            currentAngle = -90.0f;
        }
        if (this.textContainer == null) {
            this.textContainer = new PaintablePosition(this.textBox, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
        } else {
            this.textContainer.set(this.textBox, x, y, currentAngle, AugmentedReality.ONE_PERCENT);
        }
        this.textContainer.paint(canvas);
    }

    public synchronized int compareTo(Marker another) {
        if (another == null) {
            throw new NullPointerException();
        }
        return this.name.compareTo(another.getName());
    }

    public synchronized boolean equals(Object marker) {
        if (marker != null) {
            if (this.name != null) {
            }
        }
        throw new NullPointerException();
        return this.name.equals(((Marker) marker).getName());
    }
}
