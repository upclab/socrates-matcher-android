package pe.edu.upc.mobile.AR;

import android.content.Context;
import com.jwetherell.augmented_reality.data.DataSource;
import com.jwetherell.augmented_reality.ui.IconMarker;
import com.jwetherell.augmented_reality.ui.Marker;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import pe.edu.upc.mobile.Entities.Place;
import pe.edu.upc.mobile.Entities.PlaceInfo;
import pe.edu.upc.mobile.Utilities.ImageUtils;
import pe.edu.upc.mobile.Utilities.LocationUtils;

public class ARDataSource extends DataSource {
    private List<Marker> cachedMarkers;
    private Context context;
    private ArrayList<Place> places;

    public ARDataSource(Context context) throws IOException, JSONException, NullPointerException {
        this.cachedMarkers = new ArrayList();
        if (context == null) {
            throw new NullPointerException();
        }
        this.places = new ArrayList();
        this.places.addAll(LocationUtils.getPlaces(context));
        this.context = context;
    }

    public List<Marker> getMarkers() {
        Iterator it = this.places.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Place) it.next()).getInfo().iterator();
            while (it2.hasNext()) {
                PlaceInfo placeInfo = (PlaceInfo) it2.next();
                this.cachedMarkers.add(new IconMarker(placeInfo.getName(), placeInfo.getLat(), placeInfo.getLon(), 0.0d, -12303292, ImageUtils.getMarkerImageForPlaceInfo(placeInfo, this.context)));
            }
        }
        return this.cachedMarkers;
    }

    public List<Marker> getMarkers(String placeName) {
        Iterator it = this.places.iterator();
        while (it.hasNext()) {
            Place place = (Place) it.next();
            if (place.getName().equalsIgnoreCase(placeName)) {
                Iterator it2 = place.getInfo().iterator();
                while (it2.hasNext()) {
                    PlaceInfo placeInfo = (PlaceInfo) it2.next();
                    this.cachedMarkers.add(new IconMarker(placeInfo.getName(), placeInfo.getLat(), placeInfo.getLon(), 0.0d, -12303292, ImageUtils.getMarkerImageForPlaceInfo(placeInfo, this.context)));
                }
            }
        }
        return this.cachedMarkers;
    }

    public PlaceInfo getPlaceInfoWithMarker(Marker marker) {
        Iterator it = this.places.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Place) it.next()).getInfo().iterator();
            while (it2.hasNext()) {
                PlaceInfo placeInfo = (PlaceInfo) it2.next();
                if (placeInfo.getName().equalsIgnoreCase(marker.getName())) {
                    return placeInfo;
                }
            }
        }
        return null;
    }
}
