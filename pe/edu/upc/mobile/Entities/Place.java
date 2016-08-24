package pe.edu.upc.mobile.Entities;

import java.util.ArrayList;

public class Place {
    private ArrayList<PlaceInfo> info;
    private double lat;
    private double lon;
    private String name;

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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PlaceInfo> getInfo() {
        return this.info;
    }

    public void setInfo(ArrayList<PlaceInfo> info) {
        this.info = info;
    }
}
