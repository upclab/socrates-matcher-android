package com.jwetherell.augmented_reality.ui.objects;

import android.graphics.Canvas;

public class PaintablePosition extends PaintableObject {
    private float height;
    private PaintableObject obj;
    private float objRotation;
    private float objScale;
    private float objX;
    private float objY;
    private float width;

    public PaintablePosition(PaintableObject drawObj, float x, float y, float rotation, float scale) {
        this.width = 0.0f;
        this.height = 0.0f;
        this.objX = 0.0f;
        this.objY = 0.0f;
        this.objRotation = 0.0f;
        this.objScale = 0.0f;
        this.obj = null;
        set(drawObj, x, y, rotation, scale);
    }

    public void set(PaintableObject drawObj, float x, float y, float rotation, float scale) {
        if (drawObj == null) {
            throw new NullPointerException();
        }
        this.obj = drawObj;
        this.objX = x;
        this.objY = y;
        this.objRotation = rotation;
        this.objScale = scale;
        this.width = this.obj.getWidth();
        this.height = this.obj.getHeight();
    }

    public void move(float x, float y) {
        this.objX = x;
        this.objY = y;
    }

    public float getObjectsX() {
        return this.objX;
    }

    public float getObjectsY() {
        return this.objY;
    }

    public void paint(Canvas canvas) {
        if (canvas == null || this.obj == null) {
            throw new NullPointerException();
        }
        paintObj(canvas, this.obj, this.objX, this.objY, this.objRotation, this.objScale);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public String toString() {
        return "< objX=" + this.objX + " objY=" + this.objY + " width=" + this.width + " height=" + this.height + " >";
    }
}
