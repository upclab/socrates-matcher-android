package pe.edu.upc.mobile.Utilities;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.commons.lang3.CharEncoding;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pe.edu.upc.mobile.Entities.Place;
import pe.edu.upc.mobile.Entities.PlaceInfo;

public class LocationUtils {
    public static ArrayList<Place> getPlaces(Context context) throws IOException, JSONException {
        ArrayList<Place> places = new ArrayList();
        JSONArray jsonData = new JSONArray(loadData(context));
        for (int i = 0; i < jsonData.length(); i++) {
            JSONObject jsonPlace = jsonData.getJSONObject(i);
            Place place = new Place();
            place.setName(jsonPlace.getString("place"));
            place.setLat(jsonPlace.getDouble("lat"));
            place.setLon(jsonPlace.getDouble("lon"));
            JSONArray jsonInfoArray = jsonPlace.getJSONArray("info");
            ArrayList<PlaceInfo> info = new ArrayList();
            for (int j = 0; j < jsonInfoArray.length(); j++) {
                JSONObject jsonInfo = jsonInfoArray.getJSONObject(j);
                PlaceInfo placeInfo = new PlaceInfo();
                placeInfo.setName(jsonInfo.getString("name"));
                placeInfo.setLat(jsonInfo.getDouble("lat"));
                placeInfo.setLon(jsonInfo.getDouble("lon"));
                JSONArray jsonPlaces = jsonInfo.getJSONArray("places");
                ArrayList<String> placesList = new ArrayList();
                for (int k = 0; k < jsonPlaces.length(); k++) {
                    placesList.add((String) jsonPlaces.get(k));
                }
                placeInfo.setPlaces(placesList);
                info.add(placeInfo);
            }
            place.setInfo(info);
            places.add(place);
        }
        return places;
    }

    private static String loadData(Context context) throws IOException {
        InputStream is = context.getAssets().open("data/ubicuidad.json");
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();
        return new String(buffer, CharEncoding.UTF_8);
    }
}
