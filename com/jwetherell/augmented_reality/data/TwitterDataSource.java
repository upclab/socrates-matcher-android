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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterDataSource extends NetworkDataSource {
    private static final String URL = "http://search.twitter.com/search.json";
    private static Bitmap icon;

    static {
        icon = null;
    }

    public TwitterDataSource(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        createIcon(res);
    }

    protected void createIcon(Resources res) {
        if (res == null) {
            throw new NullPointerException();
        }
        icon = BitmapFactory.decodeResource(res, C0359R.drawable.twitter);
    }

    public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
        return "http://search.twitter.com/search.json?geocode=" + lat + "%2C" + lon + "%2C" + Math.max((double) radius, 1.0d) + "km";
    }

    public List<Marker> parse(String url) {
        if (url == null) {
            throw new NullPointerException();
        }
        InputStream stream = NetworkDataSource.getHttpGETInputStream(url);
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
        if (jo.has("geo")) {
            Double lat = null;
            Double lon = null;
            try {
                if (jo.isNull("geo")) {
                    if (jo.has("location")) {
                        Matcher matcher = Pattern.compile("\\D*([0-9.]+),\\s?([0-9.]+)").matcher(jo.getString("location"));
                        if (matcher.find()) {
                            lat = Double.valueOf(Double.parseDouble(matcher.group(1)));
                            lon = Double.valueOf(Double.parseDouble(matcher.group(2)));
                        }
                    }
                } else {
                    JSONArray coordinates = jo.getJSONObject("geo").getJSONArray("coordinates");
                    lat = Double.valueOf(Double.parseDouble(coordinates.getString(0)));
                    lon = Double.valueOf(Double.parseDouble(coordinates.getString(1)));
                }
                if (lat != null) {
                    return new IconMarker(jo.getString("from_user") + ": " + jo.getString("text"), lat.doubleValue(), lon.doubleValue(), 0.0d, -65536, icon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        throw new NullPointerException();
    }
}
