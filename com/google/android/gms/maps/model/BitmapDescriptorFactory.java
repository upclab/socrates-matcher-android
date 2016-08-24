package com.google.android.gms.maps.model;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.model.internal.C0192a;

public final class BitmapDescriptorFactory {
    public static final float HUE_AZURE = 210.0f;
    public static final float HUE_BLUE = 240.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_MAGENTA = 300.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_RED = 0.0f;
    public static final float HUE_ROSE = 330.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static final float HUE_YELLOW = 60.0f;
    private static C0192a gQ;

    private BitmapDescriptorFactory() {
    }

    public static void m557a(C0192a c0192a) {
        if (gQ == null) {
            gQ = (C0192a) C0159s.m517d(c0192a);
        }
    }

    private static C0192a bo() {
        return (C0192a) C0159s.m514b(gQ, (Object) "IBitmapDescriptorFactory is not initialized");
    }

    public static BitmapDescriptor defaultMarker() {
        try {
            return new BitmapDescriptor(bo().bt());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor defaultMarker(float hue) {
        try {
            return new BitmapDescriptor(bo().m584c(hue));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromAsset(String assetName) {
        try {
            return new BitmapDescriptor(bo().m579B(assetName));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromBitmap(Bitmap image) {
        try {
            return new BitmapDescriptor(bo().m583a(image));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromFile(String fileName) {
        try {
            return new BitmapDescriptor(bo().m580C(fileName));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromPath(String absolutePath) {
        try {
            return new BitmapDescriptor(bo().m581D(absolutePath));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public static BitmapDescriptor fromResource(int resourceId) {
        try {
            return new BitmapDescriptor(bo().m582S(resourceId));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
