package pe.edu.upc.mobile.UPCMovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.Iterator;
import pe.edu.upc.mobile.Entities.Place;
import pe.edu.upc.mobile.Entities.PlaceInfo;
import pe.edu.upc.mobile.Utilities.ImageUtils;

public class PlaceFragment extends SupportMapFragment {
    private Place place;

    /* renamed from: pe.edu.upc.mobile.UPCMovil.PlaceFragment.1 */
    class C08621 implements OnMarkerClickListener {
        C08621() {
        }

        public boolean onMarkerClick(Marker arg0) {
            String title = arg0.getTitle();
            Iterator it = PlaceFragment.this.place.getInfo().iterator();
            while (it.hasNext()) {
                PlaceInfo placeInfo = (PlaceInfo) it.next();
                if (placeInfo.getName().equalsIgnoreCase(title)) {
                    Intent intent = new Intent(PlaceFragment.this.getActivity(), DetalleCampusActivity.class);
                    intent.putExtra("placeInfoName", placeInfo.getName());
                    intent.putExtra("placeName", PlaceFragment.this.place.getName());
                    ArrayList<String> places = placeInfo.getPlaces();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Lugares:\n");
                    it = places.iterator();
                    while (it.hasNext()) {
                        stringBuilder.append(new StringBuilder(String.valueOf((String) it.next())).append("\n").toString());
                    }
                    intent.putExtra("places", stringBuilder.toString());
                    PlaceFragment.this.startActivity(intent);
                    return true;
                }
            }
            return true;
        }
    }

    public static PlaceFragment newInstance(Place place) {
        PlaceFragment placeFragment = new PlaceFragment();
        placeFragment.place = place;
        return placeFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        setupMap();
        return view;
    }

    private void setupMap() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.place.getLat(), this.place.getLon()), 18.0f));
        getMap().setMyLocationEnabled(true);
        getMap().setOnMarkerClickListener(new C08621());
        Iterator it = this.place.getInfo().iterator();
        while (it.hasNext()) {
            PlaceInfo placeInfo = (PlaceInfo) it.next();
            getMap().addMarker(new MarkerOptions().icon(ImageUtils.getPinImageForPlaceInfo(placeInfo)).title(placeInfo.getName()).position(new LatLng(placeInfo.getLat(), placeInfo.getLon())));
        }
    }
}
