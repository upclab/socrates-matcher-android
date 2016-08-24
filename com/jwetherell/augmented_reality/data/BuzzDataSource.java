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

public class BuzzDataSource extends NetworkDataSource {
    private static final String BASE_URL = "https://www.googleapis.com/buzz/v1/activities/search?alt=json&max-results=40";
    private static Bitmap icon;

    static {
        icon = null;
    }

    public BuzzDataSource(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        createIcon(res);
    }

    public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
        return "https://www.googleapis.com/buzz/v1/activities/search?alt=json&max-results=40&lat=" + lat + "&lon=" + lon + "&radius=" + (1000.0f * radius);
    }

    public List<Marker> parse(JSONObject root) {
        if (root == null) {
            throw new NullPointerException();
        }
        JSONArray dataArray = null;
        List<Marker> markers = new ArrayList();
        try {
            if (root.has("data") && root.getJSONObject("data").has("items")) {
                dataArray = root.getJSONObject("data").getJSONArray("items");
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

    private void createIcon(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        icon = BitmapFactory.decodeResource(res, C0359R.drawable.buzz);
    }

    private Marker processJSONObject(JSONObject jo) {
        if (jo == null) {
            throw new NullPointerException();
        }
        if (jo.has(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE) && jo.has("geocode")) {
            try {
                return new IconMarker(jo.getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE), Double.valueOf(jo.getString("geocode").split(" ")[0]).doubleValue(), Double.valueOf(jo.getString("geocode").split(" ")[1]).doubleValue(), 0.0d, -16711936, icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
