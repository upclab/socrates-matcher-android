package com.jwetherell.augmented_reality.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.jwetherell.augmented_reality.ui.objects.PaintableIcon;

public class IconMarker extends Marker {
    private Bitmap bitmap;

    public IconMarker(String name, double latitude, double longitude, double altitude, int color, Bitmap bitmap) {
        super(name, latitude, longitude, altitude, color);
        this.bitmap = null;
        this.bitmap = bitmap;
    }

    public void drawIcon(Canvas canvas) {
        if (canvas == null || this.bitmap == null) {
            throw new NullPointerException();
        }
        if (this.gpsSymbol == null) {
            this.gpsSymbol = new PaintableIcon(this.bitmap, 96, 96);
        }
        super.drawIcon(canvas);
    }
}
