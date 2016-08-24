package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.VisibleRegion;

public final class Projection {
    private final IProjectionDelegate gJ;

    Projection(IProjectionDelegate delegate) {
        this.gJ = delegate;
    }

    public LatLng fromScreenLocation(Point point) {
        try {
            return this.gJ.fromScreenLocation(C0883c.m1158f(point));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public VisibleRegion getVisibleRegion() {
        try {
            return this.gJ.getVisibleRegion();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public Point toScreenLocation(LatLng location) {
        try {
            return (Point) C0883c.m1157a(this.gJ.toScreenLocation(location));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
