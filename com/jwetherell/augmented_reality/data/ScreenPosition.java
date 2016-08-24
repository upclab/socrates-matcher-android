package com.jwetherell.augmented_reality.data;

import android.util.FloatMath;

public class ScreenPosition {
    private float f59x;
    private float f60y;

    public ScreenPosition() {
        this.f59x = 0.0f;
        this.f60y = 0.0f;
        set(0.0f, 0.0f);
    }

    public void set(float x, float y) {
        this.f59x = x;
        this.f60y = y;
    }

    public float getX() {
        return this.f59x;
    }

    public void setX(float x) {
        this.f59x = x;
    }

    public float getY() {
        return this.f60y;
    }

    public void setY(float y) {
        this.f60y = y;
    }

    public void rotate(float t) {
        float yp = (FloatMath.sin(t) * this.f59x) + (FloatMath.cos(t) * this.f60y);
        this.f59x = (FloatMath.cos(t) * this.f59x) - (FloatMath.sin(t) * this.f60y);
        this.f60y = yp;
    }

    public void add(float x, float y) {
        this.f59x += x;
        this.f60y += y;
    }

    public String toString() {
        return "< x=" + this.f59x + " y=" + this.f60y + " >";
    }
}
