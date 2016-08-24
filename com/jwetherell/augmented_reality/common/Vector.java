package com.jwetherell.augmented_reality.common;

public class Vector {
    private final float[] matrixArray;
    private volatile float f53x;
    private volatile float f54y;
    private volatile float f55z;

    public Vector() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector(Vector v) {
        this(v.f53x, v.f54y, v.f55z);
    }

    public synchronized float getX() {
        return this.f53x;
    }

    public synchronized void setX(float x) {
        this.f53x = x;
    }

    public synchronized float getY() {
        return this.f54y;
    }

    public synchronized void setY(float y) {
        this.f54y = y;
    }

    public synchronized float getZ() {
        return this.f55z;
    }

    public synchronized void setZ(float z) {
        this.f55z = z;
    }

    public Vector(float x, float y, float z) {
        this.matrixArray = new float[9];
        this.f53x = 0.0f;
        this.f54y = 0.0f;
        this.f55z = 0.0f;
        set(x, y, z);
    }

    public synchronized void get(float[] array) {
        if (array != null) {
            if (array.length == 3) {
                array[0] = this.f53x;
                array[1] = this.f54y;
                array[2] = this.f55z;
            }
        }
        throw new IllegalArgumentException("get() array must be non-NULL and size of 3");
    }

    public void set(Vector v) {
        if (v != null) {
            set(v.f53x, v.f54y, v.f55z);
        }
    }

    public void set(float[] array) {
        if (array == null || array.length != 3) {
            throw new IllegalArgumentException("get() array must be non-NULL and size of 3");
        }
        set(array[0], array[1], array[2]);
    }

    public synchronized void set(float x, float y, float z) {
        this.f53x = x;
        this.f54y = y;
        this.f55z = z;
    }

    public synchronized boolean equals(Object obj) {
        boolean z = false;
        synchronized (this) {
            if (obj != null) {
                Vector v = (Vector) obj;
                if (v.f53x == this.f53x && v.f54y == this.f54y && v.f55z == this.f55z) {
                    z = true;
                }
            }
        }
        return z;
    }

    public synchronized boolean equals(float x, float y, float z) {
        boolean z2;
        z2 = this.f53x == x && this.f54y == y && this.f55z == z;
        return z2;
    }

    public synchronized void add(float x, float y, float z) {
        this.f53x += x;
        this.f54y += y;
        this.f55z += z;
    }

    public void add(Vector v) {
        if (v != null) {
            add(v.f53x, v.f54y, v.f55z);
        }
    }

    public void sub(float x, float y, float z) {
        add(-x, -y, -z);
    }

    public void sub(Vector v) {
        if (v != null) {
            add(-v.f53x, -v.f54y, -v.f55z);
        }
    }

    public synchronized void mult(float s) {
        this.f53x *= s;
        this.f54y *= s;
        this.f55z *= s;
    }

    public synchronized void divide(float s) {
        this.f53x /= s;
        this.f54y /= s;
        this.f55z /= s;
    }

    public synchronized float length() {
        return (float) Math.sqrt((double) (((this.f53x * this.f53x) + (this.f54y * this.f54y)) + (this.f55z * this.f55z)));
    }

    public synchronized float length2D() {
        return (float) Math.sqrt((double) ((this.f53x * this.f53x) + (this.f55z * this.f55z)));
    }

    public void norm() {
        divide(length());
    }

    public synchronized float dot(Vector v) {
        float f;
        if (v == null) {
            f = 0.0f;
        } else {
            f = ((this.f53x * v.f53x) + (this.f54y * v.f54y)) + (this.f55z * v.f55z);
        }
        return f;
    }

    public synchronized void cross(Vector u, Vector v) {
        if (!(v == null || u == null)) {
            float y = (u.f55z * v.f53x) - (u.f53x * v.f55z);
            float z = (u.f53x * v.f54y) - (u.f54y * v.f53x);
            this.f53x = (u.f54y * v.f55z) - (u.f55z * v.f54y);
            this.f54y = y;
            this.f55z = z;
        }
    }

    public synchronized void prod(Matrix m) {
        if (m != null) {
            m.get(this.matrixArray);
            float yTemp = ((this.matrixArray[3] * this.f53x) + (this.matrixArray[4] * this.f54y)) + (this.matrixArray[5] * this.f55z);
            float zTemp = ((this.matrixArray[6] * this.f53x) + (this.matrixArray[7] * this.f54y)) + (this.matrixArray[8] * this.f55z);
            this.f53x = ((this.matrixArray[0] * this.f53x) + (this.matrixArray[1] * this.f54y)) + (this.matrixArray[2] * this.f55z);
            this.f54y = yTemp;
            this.f55z = zTemp;
        }
    }

    public synchronized String toString() {
        return "<" + this.f53x + ", " + this.f54y + ", " + this.f55z + ">";
    }
}
