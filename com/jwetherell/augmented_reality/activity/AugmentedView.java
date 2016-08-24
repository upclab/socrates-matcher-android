package com.jwetherell.augmented_reality.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import com.jwetherell.augmented_reality.ui.Radar;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class AugmentedView extends View {
    private static final int COLLISION_ADJUSTMENT = 100;
    private static final List<Marker> cache;
    private static final AtomicBoolean drawing;
    private static final float[] locationArray;
    private static final Radar radar;
    private static final TreeSet<Marker> updated;

    static {
        drawing = new AtomicBoolean(false);
        radar = new Radar();
        locationArray = new float[3];
        cache = new ArrayList();
        updated = new TreeSet();
    }

    public AugmentedView(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        if (canvas != null && drawing.compareAndSet(false, true)) {
            List<Marker> collection = ARData.getMarkers();
            cache.clear();
            for (Marker m : collection) {
                m.update(canvas, 0.0f, 0.0f);
                if (m.isOnRadar() && m.isInView()) {
                    cache.add(m);
                }
            }
            collection = cache;
            if (AugmentedReality.useCollisionDetection) {
                adjustForCollisions(canvas, collection);
            }
            ListIterator<Marker> iter = collection.listIterator(collection.size());
            while (iter.hasPrevious()) {
                ((Marker) iter.previous()).draw(canvas);
            }
            if (AugmentedReality.showRadar) {
                radar.draw(canvas);
            }
            drawing.set(false);
        }
    }

    private static void adjustForCollisions(Canvas canvas, List<Marker> collection) {
        updated.clear();
        for (Marker marker1 : collection) {
            if (!updated.contains(marker1) && marker1.isInView()) {
                int collisions = 1;
                for (Marker marker2 : collection) {
                    if (!marker1.equals(marker2) && !updated.contains(marker2) && marker2.isInView() && marker1.isMarkerOnMarker(marker2)) {
                        marker2.getLocation().get(locationArray);
                        locationArray[1] = locationArray[1] + ((float) (collisions * COLLISION_ADJUSTMENT));
                        marker2.getLocation().set(locationArray);
                        marker2.update(canvas, 0.0f, 0.0f);
                        collisions++;
                        updated.add(marker2);
                    }
                }
                updated.add(marker1);
            }
        }
    }
}
