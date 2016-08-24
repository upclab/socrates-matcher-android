package com.jwetherell.augmented_reality.data;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.gms.plus.PlusShare;
import com.jwetherell.augmented_reality.C0359R;
import com.jwetherell.augmented_reality.ui.IconMarker;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WikipediaDataSource extends NetworkDataSource {
    private static final String BASE_URL = "http://ws.geonames.org/findNearbyWikipediaJSON";
    private static Bitmap icon;

    static {
        icon = null;
    }

    public WikipediaDataSource(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        createIcon(res);
    }

    protected void createIcon(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        icon = BitmapFactory.decodeResource(res, C0359R.drawable.wikipedia);
    }

    public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
        return "http://ws.geonames.org/findNearbyWikipediaJSON?lat=" + lat + "&lng=" + lon + "&radius=" + radius + "&maxRows=40" + "&lang=" + locale;
    }

    public List<Marker> parse(JSONObject root) {
        if (root == null) {
            return null;
        }
        JSONArray dataArray = null;
        List<Marker> markers = new ArrayList();
        try {
            if (root.has("geonames")) {
                dataArray = root.getJSONArray("geonames");
            }
            if (dataArray == null) {
                return markers;
            }
            int top = Math.min(5, dataArray.length());
            for (int i = 0; i < top; i++) {
                Marker ma = processJSONObject(dataArray.getJSONObject(i));
                if (ma != null) {
                    markers.add(ma);
                }
            }
            return markers;
        } catch (JSONException e) {
            e.printStackTrace();
            return markers;
        }
    }

    private Marker processJSONObject(JSONObject jo) {
        if (jo == null) {
            return null;
        }
        if (jo.has(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE) && jo.has("lat") && jo.has("lng") && jo.has("elevation")) {
            try {
                return new IconMarker(jo.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE), jo.getDouble("lat"), jo.getDouble("lng"), jo.getDouble("elevation"), -1, icon);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
