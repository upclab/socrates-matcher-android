package com.jwetherell.augmented_reality.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.jwetherell.augmented_reality.C0359R;
import com.jwetherell.augmented_reality.ui.IconMarker;
import com.jwetherell.augmented_reality.ui.Marker;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlacesDataSource extends NetworkDataSource {
    private static final String TYPES = "airport|amusement_park|aquarium|art_gallery|bus_station|campground|car_rental|city_hall|embassy|establishment|hindu_temple|local_governemnt_office|mosque|museum|night_club|park|place_of_worship|police|post_office|stadium|spa|subway_station|synagogue|taxi_stand|train_station|travel_agency|University|zoo";
    private static final String URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private static Bitmap icon;
    private static String key;

    static {
        key = null;
        icon = null;
    }

    public GooglePlacesDataSource(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        key = res.getString(C0359R.string.google_places_api_key);
        createIcon(res);
    }

    protected void createIcon(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        icon = BitmapFactory.decodeResource(res, C0359R.drawable.buzz);
    }

    public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
        try {
            return "https://maps.googleapis.com/maps/api/place/search/json?location=" + lat + "," + lon + "&radius=" + (1000.0f * radius) + "&types=" + TYPES + "&sensor=true&key=" + key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Marker> parse(String URL) {
        if (URL == null) {
            throw new NullPointerException();
        }
        InputStream stream = NetworkDataSource.getHttpGETInputStream(URL);
        if (stream == null) {
            throw new NullPointerException();
        }
        String string = getHttpInputString(stream);
        if (string == null) {
            throw new NullPointerException();
        }
        JSONObject json = null;
        try {
            json = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json != null) {
            return parse(json);
        }
        throw new NullPointerException();
    }

    public List<Marker> parse(JSONObject root) {
        if (root == null) {
            throw new NullPointerException();
        }
        JSONArray dataArray = null;
        List<Marker> markers = new ArrayList();
        try {
            if (root.has("results")) {
                dataArray = root.getJSONArray("results");
            }
            if (dataArray != null) {
                int top = Math.min(5, dataArray.length());
                for (int i = 0; i < top; i++) {
                    Marker ma = processJSONObject(dataArray.getJSONObject(i));
                    if (ma != null) {
                        markers.add(ma);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return markers;
    }

    private Marker processJSONObject(JSONObject jo) {
        if (jo == null) {
            throw new NullPointerException();
        }
        if (jo.has("geometry")) {
            Double lat = null;
            Double lon = null;
            try {
                if (!jo.isNull("geometry")) {
                    JSONObject coordinates = jo.getJSONObject("geometry").getJSONObject("location");
                    lat = Double.valueOf(Double.parseDouble(coordinates.getString("lat")));
                    lon = Double.valueOf(Double.parseDouble(coordinates.getString("lng")));
                }
                if (lat != null) {
                    return new IconMarker(jo.getString("name") + ": " + jo.getString("name"), lat.doubleValue(), lon.doubleValue(), 0.0d, -65536, icon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        throw new NullPointerException();
    }
}
