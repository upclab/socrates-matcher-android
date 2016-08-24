package com.jwetherell.augmented_reality.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jwetherell.augmented_reality.C0359R;
import com.jwetherell.augmented_reality.ui.IconMarker;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.List;

public class LocalDataSource extends DataSource {
    private static Bitmap icon;
    private List<Marker> cachedMarkers;

    static {
        icon = null;
    }

    public LocalDataSource(Resources res) {
        this.cachedMarkers = new ArrayList();
        if (res == null) {
            throw new NullPointerException();
        }
        createIcon(res);
    }

    protected void createIcon(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        icon = BitmapFactory.decodeResource(res, C0359R.drawable.icon);
    }

    public List<Marker> getMarkers() {
        this.cachedMarkers.add(new IconMarker("ATL ICON", 39.931228d, -75.051262d, 0.0d, -12303292, icon));
        this.cachedMarkers.add(new Marker("ATL CIRCLE", 39.931269d, -75.051231d, 0.0d, -256));
        return this.cachedMarkers;
    }
}
