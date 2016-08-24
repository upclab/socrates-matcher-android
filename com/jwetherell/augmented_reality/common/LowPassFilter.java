package com.jwetherell.augmented_reality.common;

import android.util.FloatMath;

public class LowPassFilter {
    private static final float ALPHA_DEFAULT = 0.333f;
    private static final float ALPHA_MOVING = 0.6f;
    private static final float ALPHA_START_MOVING = 0.3f;
    private static final float ALPHA_STEADY = 0.001f;

    private LowPassFilter() {
    }

    public static float[] filter(float low, float high, float[] current, float[] previous) {
        if (current == null || previous == null) {
            throw new NullPointerException("input and prev float arrays must be non-NULL");
        } else if (current.length != previous.length) {
            throw new IllegalArgumentException("input and prev must be the same length");
        } else {
            float alpha = computeAlpha(low, high, current, previous);
            for (int i = 0; i < current.length; i++) {
                previous[i] = previous[i] + ((current[i] - previous[i]) * alpha);
            }
            return previous;
        }
    }

    private static final float computeAlpha(float low, float high, float[] current, float[] previous) {
        if (previous.length != 3 || current.length != 3) {
            return ALPHA_DEFAULT;
        }
        float x1 = current[0];
        float y1 = current[1];
        float z1 = current[2];
        float distance = FloatMath.sqrt((float) ((Math.pow((double) (previous[0] - x1), 2.0d) + Math.pow((double) (previous[1] - y1), 2.0d)) + Math.pow((double) (previous[2] - z1), 2.0d)));
        if (distance < low) {
            return ALPHA_STEADY;
        }
        if (distance >= low || distance < high) {
            return ALPHA_START_MOVING;
        }
        return ALPHA_MOVING;
    }
}
