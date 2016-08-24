package com.jwetherell.augmented_reality.camera;

import com.jwetherell.augmented_reality.common.Vector;

public class CameraModel {
    public static final float DEFAULT_VIEW_ANGLE;
    private static final float[] tmp1;
    private static final float[] tmp2;
    private float distance;
    private int height;
    private float viewAngle;
    private int width;

    static {
        tmp1 = new float[3];
        tmp2 = new float[3];
        DEFAULT_VIEW_ANGLE = (float) Math.toRadians(45.0d);
    }

    public CameraModel(int width, int height) {
        this(width, height, true);
    }

    public CameraModel(int width, int height, boolean init) {
        this.width = 0;
        this.height = 0;
        this.viewAngle = 0.0f;
        this.distance = 0.0f;
        set(width, height, init);
    }

    public void set(int width, int height, boolean init) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setViewAngle(float viewAngle) {
        this.viewAngle = viewAngle;
        this.distance = ((float) (this.width / 2)) / ((float) Math.tan((double) (viewAngle / 2.0f)));
    }

    public void setViewAngle(int width, int height, float viewAngle) {
        this.viewAngle = viewAngle;
        this.distance = ((float) (width / 2)) / ((float) Math.tan((double) (viewAngle / 2.0f)));
    }

    public void projectPoint(Vector orgPoint, Vector prjPoint, float addX, float addY) {
        orgPoint.get(tmp1);
        tmp2[0] = (this.distance * tmp1[0]) / (-tmp1[2]);
        tmp2[1] = (this.distance * tmp1[1]) / (-tmp1[2]);
        tmp2[2] = tmp1[2];
        tmp2[0] = (tmp2[0] + addX) + ((float) (this.width / 2));
        tmp2[1] = ((-tmp2[1]) + addY) + ((float) (this.height / 2));
        prjPoint.set(tmp2);
    }

    public String toString() {
        return "CAM(" + this.width + "," + this.height + "," + this.viewAngle + "," + this.distance + ")";
    }
}
