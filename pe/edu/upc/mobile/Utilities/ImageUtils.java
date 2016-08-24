package pe.edu.upc.mobile.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import pe.edu.upc.mobile.C0386R;
import pe.edu.upc.mobile.Entities.PlaceInfo;

public class ImageUtils {
    public static Bitmap getMarkerImageForPlaceInfo(PlaceInfo placeInfo, Context context) {
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n A")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_a);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n B")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_b);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n C")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_c);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n D")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_d);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n E")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_e);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n F")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_f);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n G")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_g);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n H")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_h);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n I")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_i);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n J")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_j);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n K")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_k);
        }
        if (placeInfo.getName().equalsIgnoreCase("Pabell\u00f3n L")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.marker_l);
        }
        return null;
    }

    public static Bitmap getPinImageForPlaceInfoName(String placeInfoName, Context context) {
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n A")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_a);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n B")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_b);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n C")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_c);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n D")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_d);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n E")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_e);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n F")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_f);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n G")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_g);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n H")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_h);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n I")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_i);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n J")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_j);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n K")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_k);
        }
        if (placeInfoName.equalsIgnoreCase("Pabell\u00f3n L")) {
            return BitmapFactory.decodeResource(context.getResources(), C0386R.drawable.pin_l);
        }
        return null;
    }

    public static BitmapDescriptor getPinImageForPlaceInfo(PlaceInfo info) {
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n A")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_a);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n B")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_b);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n C")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_c);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n D")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_d);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n E")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_e);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n F")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_f);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n G")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_g);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n H")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_h);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n I")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_i);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n J")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_j);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n K")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_k);
        }
        if (info.getName().equalsIgnoreCase("Pabell\u00f3n L")) {
            return BitmapDescriptorFactory.fromResource(C0386R.drawable.pin_l);
        }
        return null;
    }
}
