package com.jwetherell.augmented_reality.common;

import com.jwetherell.augmented_reality.activity.AugmentedReality;

public class Calculator {
    private static volatile float azimuth;
    private static final Vector looking;
    private static final float[] lookingArray;
    private static volatile float pitch;
    private static volatile float roll;
    private static final Matrix tempMatrix;

    static {
        looking = new Vector();
        lookingArray = new float[3];
        tempMatrix = new Matrix();
        azimuth = 0.0f;
        pitch = 0.0f;
        roll = 0.0f;
    }

    private Calculator() {
    }

    public static final float getAngle(float center_x, float center_y, float post_x, float post_y) {
        return (float) ((Math.atan2((double) (post_y - center_y), (double) (post_x - center_x)) * 180.0d) / 3.141592653589793d);
    }

    public static synchronized float getAzimuth() {
        float f;
        synchronized (Calculator.class) {
            f = azimuth;
        }
        return f;
    }

    public static synchronized float getPitch() {
        float f;
        synchronized (Calculator.class) {
            f = pitch;
        }
        return f;
    }

    public static synchronized float getRoll() {
        float f;
        synchronized (Calculator.class) {
            f = roll;
        }
        return f;
    }

    public static synchronized void calcPitchBearing(Matrix rotationMatrix) {
        synchronized (Calculator.class) {
            if (rotationMatrix != null) {
                tempMatrix.set(rotationMatrix);
                tempMatrix.transpose();
                if (AugmentedReality.portrait) {
                    looking.set(0.0f, AugmentedReality.ONE_PERCENT, 0.0f);
                } else {
                    looking.set(AugmentedReality.ONE_PERCENT, 0.0f, 0.0f);
                }
                looking.prod(tempMatrix);
                looking.get(lookingArray);
                azimuth = (getAngle(0.0f, 0.0f, lookingArray[0], lookingArray[2]) + 360.0f) % 360.0f;
                roll = -(90.0f - Math.abs(getAngle(0.0f, 0.0f, lookingArray[1], lookingArray[2])));
                looking.set(0.0f, 0.0f, AugmentedReality.ONE_PERCENT);
                looking.prod(tempMatrix);
                looking.get(lookingArray);
                pitch = -(90.0f - Math.abs(getAngle(0.0f, 0.0f, lookingArray[1], lookingArray[2])));
            }
        }
    }
}
