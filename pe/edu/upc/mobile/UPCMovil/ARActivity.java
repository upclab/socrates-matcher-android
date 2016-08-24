package pe.edu.upc.mobile.UPCMovil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import pe.edu.upc.mobile.AR.ARDataSource;
import pe.edu.upc.mobile.Entities.PlaceInfo;
import pe.edu.upc.mobile.Entities.Session;
import pe.edu.upc.mobile.Utilities.ExceptionUtils;
import pe.edu.upc.mobile.Utilities.MessageUtils;

public class ARActivity extends AugmentedReality {
    private static String PLACE_NAME;
    private ARDataSource dataSource;

    static {
        PLACE_NAME = "Monterrico";
    }

    public void onCreate(Bundle savedInstanceState) {
        int i = 0;
        try {
            super.onCreate(savedInstanceState);
            showRadar = false;
            showZoomBar = false;
            LinearLayout linearLayout = zoomLayout;
            if (!showZoomBar) {
                i = 8;
            }
            linearLayout.setVisibility(i);
            this.dataSource = new ARDataSource(this);
            ARData.addMarkers(this.dataSource.getMarkers(PLACE_NAME));
        } catch (Exception e) {
            prepareHandleException(e);
        }
    }

    private void prepareHandleException(Exception e) {
        Session session = new Session();
        SharedPreferences preferences = getSharedPreferences("pe.edu.upc.UPCMovil", 0);
        String codAlumno = preferences.getString("CodAlumno", StringUtils.EMPTY);
        String token = preferences.getString("Token", StringUtils.EMPTY);
        session.setCodAlumno(codAlumno);
        session.setToken(token);
        MessageUtils.showErrorAlert(this, "Se encontr\u00f3 un error en la aplicaci\u00f3n.");
        ExceptionUtils.handleException(e, session);
    }

    protected void markerTouched(Marker marker) {
        PlaceInfo placeInfo = this.dataSource.getPlaceInfoWithMarker(marker);
        Intent intent = new Intent(this, DetalleCampusActivity.class);
        intent.putExtra("placeInfoName", placeInfo.getName());
        intent.putExtra("placeName", PLACE_NAME);
        ArrayList<String> places = placeInfo.getPlaces();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lugares:\n");
        Iterator it = places.iterator();
        while (it.hasNext()) {
            stringBuilder.append(new StringBuilder(String.valueOf((String) it.next())).append("\n").toString());
        }
        intent.putExtra("places", stringBuilder.toString());
        startActivity(intent);
    }
}
