package pe.edu.upc.mobile.Entities;

import java.util.ArrayList;

public class PlaceInfo {
    private double lat;
    private double lon;
    private String name;
    private ArrayList<String> places;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public ArrayList<String> getPlaces() {
        return this.places;
    }

    public void setPlaces(ArrayList<String> places) {
        this.places = places;
    }
}
