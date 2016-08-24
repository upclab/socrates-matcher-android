package com.jwetherell.augmented_reality.data;

import com.jwetherell.augmented_reality.ui.Marker;
import java.util.List;

public abstract class DataSource {
    public abstract List<Marker> getMarkers();
}
