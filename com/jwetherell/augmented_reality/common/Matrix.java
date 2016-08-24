package com.jwetherell.augmented_reality.common;

import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.jwetherell.augmented_reality.activity.AugmentedReality;

public class Matrix {
    private static final Vector dir;
    private static final float[] dirArray;
    private static final Vector right;
    private static final float[] rightArray;
    private static final Matrix tmp;
    private static final Vector up;
    private static final float[] upArray;
    private static final Vector worldUp;
    private volatile float a1;
    private volatile float a2;
    private volatile float a3;
    private volatile float b1;
    private volatile float b2;
    private volatile float b3;
    private volatile float c1;
    private volatile float c2;
    private volatile float c3;

    static {
        worldUp = new Vector(0.0f, AugmentedReality.ONE_PERCENT, 0.0f);
        dir = new Vector();
        right = new Vector();
        up = new Vector();
        tmp = new Matrix();
        dirArray = new float[3];
        rightArray = new float[3];
        upArray = new float[3];
    }

    public Matrix() {
        this.a1 = 0.0f;
        this.a2 = 0.0f;
        this.a3 = 0.0f;
        this.b1 = 0.0f;
        this.b2 = 0.0f;
        this.b3 = 0.0f;
        this.c1 = 0.0f;
        this.c2 = 0.0f;
        this.c3 = 0.0f;
    }

    public Matrix(Matrix m) {
        this.a1 = 0.0f;
        this.a2 = 0.0f;
        this.a3 = 0.0f;
        this.b1 = 0.0f;
        this.b2 = 0.0f;
        this.b3 = 0.0f;
        this.c1 = 0.0f;
        this.c2 = 0.0f;
        this.c3 = 0.0f;
        if (m == null) {
            throw new NullPointerException();
        }
        set(m.a1, m.a2, m.a3, m.b1, m.b2, m.b3, m.c1, m.c2, m.c3);
    }

    public synchronized float getA1() {
        return this.a1;
    }

    public synchronized void setA1(float a1) {
        this.a1 = a1;
    }

    public synchronized float getA2() {
        return this.a2;
    }

    public synchronized void setA2(float a2) {
        this.a2 = a2;
    }

    public synchronized float getA3() {
        return this.a3;
    }

    public synchronized void setA3(float a3) {
        this.a3 = a3;
    }

    public synchronized float getB1() {
        return this.b1;
    }

    public synchronized void setB1(float b1) {
        this.b1 = b1;
    }

    public synchronized float getB2() {
        return this.b2;
    }

    public synchronized void setB2(float b2) {
        this.b2 = b2;
    }

    public synchronized float getB3() {
        return this.b3;
    }

    public synchronized void setB3(float b3) {
        this.b3 = b3;
    }

    public synchronized float getC1() {
        return this.c1;
    }

    public synchronized void setC1(float c1) {
        this.c1 = c1;
    }

    public synchronized float getC2() {
        return this.c2;
    }

    public synchronized void setC2(float c2) {
        this.c2 = c2;
    }

    public synchronized float getC3() {
        return this.c3;
    }

    public synchronized void setC3(float c3) {
        this.c3 = c3;
    }

    public synchronized void get(float[] array) {
        if (array != null) {
            if (array.length == 9) {
                array[0] = this.a1;
                array[1] = this.a2;
                array[2] = this.a3;
                array[3] = this.b1;
                array[4] = this.b2;
                array[5] = this.b3;
                array[6] = this.c1;
                array[7] = this.c2;
                array[8] = this.c3;
            }
        }
        throw new IllegalArgumentException("get() array must be non-NULL and size of 9");
    }

    public void set(Matrix m) {
        if (m == null) {
            throw new NullPointerException();
        }
        set(m.a1, m.a2, m.a3, m.b1, m.b2, m.b3, m.c1, m.c2, m.c3);
    }

    public void set(float[] array) {
        if (array == null || array.length != 9) {
            throw new IllegalArgumentException("get() array must be non-NULL and size of 9");
        }
        set(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7], array[8]);
    }

    public synchronized void set(float a1, float a2, float a3, float b1, float b2, float b3, float c1, float c2, float c3) {
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }

    public void toIdentity() {
        set(AugmentedReality.ONE_PERCENT, 0.0f, 0.0f, 0.0f, AugmentedReality.ONE_PERCENT, 0.0f, 0.0f, 0.0f, AugmentedReality.ONE_PERCENT);
    }

    public void toXRot(float angleX) {
        set(AugmentedReality.ONE_PERCENT, 0.0f, 0.0f, 0.0f, (float) Math.cos((double) angleX), (float) (-Math.sin((double) angleX)), 0.0f, (float) Math.sin((double) angleX), (float) Math.cos((double) angleX));
    }

    public void toYRot(float angleY) {
        set((float) Math.cos((double) angleY), 0.0f, (float) Math.sin((double) angleY), 0.0f, AugmentedReality.ONE_PERCENT, 0.0f, (float) (-Math.sin((double) angleY)), 0.0f, (float) Math.cos((double) angleY));
    }

    public void toZRot(float angleZ) {
        set((float) Math.cos((double) angleZ), (float) (-Math.sin((double) angleZ)), 0.0f, (float) Math.sin((double) angleZ), (float) Math.cos((double) angleZ), 0.0f, 0.0f, 0.0f, AugmentedReality.ONE_PERCENT);
    }

    public void toScale(float scale) {
        set(scale, 0.0f, 0.0f, 0.0f, scale, 0.0f, 0.0f, 0.0f, scale);
    }

    public void toAt(Vector cam, Vector obj) {
        if (cam == null || obj == null) {
            throw new NullPointerException();
        }
        dir.set(0.0f, 0.0f, 0.0f);
        dir.set(obj);
        dir.sub(cam);
        dir.mult(GroundOverlayOptions.NO_DIMENSION);
        dir.norm();
        dir.get(dirArray);
        right.set(0.0f, 0.0f, 0.0f);
        right.cross(worldUp, dir);
        right.norm();
        right.get(rightArray);
        up.set(0.0f, 0.0f, 0.0f);
        up.cross(dir, right);
        up.norm();
        up.get(upArray);
        set(rightArray[0], rightArray[1], rightArray[2], upArray[0], upArray[1], upArray[2], dirArray[0], dirArray[1], dirArray[2]);
    }

    public synchronized void adj() {
        float a11 = this.a1;
        float a12 = this.a2;
        float a13 = this.a3;
        float a21 = this.b1;
        float a22 = this.b2;
        float a23 = this.b3;
        float a31 = this.c1;
        float a32 = this.c2;
        float a33 = this.c3;
        this.a1 = det2x2(a22, a23, a32, a33);
        this.a2 = det2x2(a13, a12, a33, a32);
        this.a3 = det2x2(a12, a13, a22, a23);
        this.b1 = det2x2(a23, a21, a33, a31);
        this.b2 = det2x2(a11, a13, a31, a33);
        this.b3 = det2x2(a13, a11, a23, a21);
        this.c1 = det2x2(a21, a22, a31, a32);
        this.c2 = det2x2(a12, a11, a32, a31);
        this.c3 = det2x2(a11, a12, a21, a22);
    }

    public void invert() {
        float det = det();
        adj();
        mult(AugmentedReality.ONE_PERCENT / det);
    }

    public synchronized void transpose() {
        float a11 = this.a1;
        float a12 = this.a2;
        float a13 = this.a3;
        float a21 = this.b1;
        float a22 = this.b2;
        float a23 = this.b3;
        float a31 = this.c1;
        float a32 = this.c2;
        float a33 = this.c3;
        this.b1 = a12;
        this.a2 = a21;
        this.b3 = a32;
        this.c2 = a23;
        this.c1 = a13;
        this.a3 = a31;
        this.a1 = a11;
        this.b2 = a22;
        this.c3 = a33;
    }

    private float det2x2(float a, float b, float c, float d) {
        return (a * d) - (b * c);
    }

    public synchronized float det() {
        return ((((((this.a1 * this.b2) * this.c3) - ((this.a1 * this.b3) * this.c2)) - ((this.a2 * this.b1) * this.c3)) + ((this.a2 * this.b3) * this.c1)) + ((this.a3 * this.b1) * this.c2)) - ((this.a3 * this.b2) * this.c1);
    }

    public synchronized void mult(float c) {
        this.a1 *= c;
        this.a2 *= c;
        this.a3 *= c;
        this.b1 *= c;
        this.b2 *= c;
        this.b3 *= c;
        this.c1 *= c;
        this.c2 *= c;
        this.c3 *= c;
    }

    public synchronized void add(Matrix n) {
        if (n == null) {
            throw new NullPointerException();
        }
        this.a1 += n.a1;
        this.a2 += n.a2;
        this.a3 += n.a3;
        this.b1 += n.b1;
        this.b2 += n.b2;
        this.b3 += n.b3;
        this.c1 += n.c1;
        this.c2 += n.c2;
        this.c3 += n.c3;
    }

    public synchronized void prod(Matrix n) {
        if (n == null) {
            throw new NullPointerException();
        }
        tmp.set(this);
        this.a1 = ((tmp.a1 * n.a1) + (tmp.a2 * n.b1)) + (tmp.a3 * n.c1);
        this.a2 = ((tmp.a1 * n.a2) + (tmp.a2 * n.b2)) + (tmp.a3 * n.c2);
        this.a3 = ((tmp.a1 * n.a3) + (tmp.a2 * n.b3)) + (tmp.a3 * n.c3);
        this.b1 = ((tmp.b1 * n.a1) + (tmp.b2 * n.b1)) + (tmp.b3 * n.c1);
        this.b2 = ((tmp.b1 * n.a2) + (tmp.b2 * n.b2)) + (tmp.b3 * n.c2);
        this.b3 = ((tmp.b1 * n.a3) + (tmp.b2 * n.b3)) + (tmp.b3 * n.c3);
        this.c1 = ((tmp.c1 * n.a1) + (tmp.c2 * n.b1)) + (tmp.c3 * n.c1);
        this.c2 = ((tmp.c1 * n.a2) + (tmp.c2 * n.b2)) + (tmp.c3 * n.c2);
        this.c3 = ((tmp.c1 * n.a3) + (tmp.c2 * n.b3)) + (tmp.c3 * n.c3);
    }

    public synchronized boolean equals(Matrix n) {
        boolean z = false;
        synchronized (this) {
            if (n != null) {
                if (this.a1 == n.a1 && this.a2 == n.a2 && this.a3 == n.a3 && this.b1 == n.b1 && this.b2 == n.b2 && this.b3 == n.b3 && this.c1 == n.c1 && this.c2 == n.c2 && this.c3 == n.c3) {
                    z = true;
                }
            }
        }
        return z;
    }

    public synchronized String toString() {
        return "[ (" + this.a1 + "," + this.a2 + "," + this.a3 + ")" + " (" + this.b1 + "," + this.b2 + "," + this.b3 + ")" + " (" + this.c1 + "," + this.c2 + "," + this.c3 + ") ]";
    }
}
