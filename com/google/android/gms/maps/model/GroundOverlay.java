package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.model.internal.C0194c;

public final class GroundOverlay {
    private final C0194c hd;

    public GroundOverlay(C0194c delegate) {
        this.hd = (C0194c) C0159s.m517d(delegate);
    }

    public boolean equals(Object other) {
        if (!(other instanceof GroundOverlay)) {
            return false;
        }
        try {
            return this.hd.m587a(((GroundOverlay) other).hd);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getBearing() {
        try {
            return this.hd.getBearing();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public LatLngBounds getBounds() {
        try {
            return this.hd.getBounds();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getHeight() {
        try {
            return this.hd.getHeight();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public String getId() {
        try {
            return this.hd.getId();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public LatLng getPosition() {
        try {
            return this.hd.getPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getTransparency() {
        try {
            return this.hd.getTransparency();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getWidth() {
        try {
            return this.hd.getWidth();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public float getZIndex() {
        try {
            return this.hd.getZIndex();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public int hashCode() {
        try {
            return this.hd.hashCodeRemote();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public boolean isVisible() {
        try {
            return this.hd.isVisible();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void remove() {
        try {
            this.hd.remove();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setBearing(float bearing) {
        try {
            this.hd.setBearing(bearing);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setDimensions(float width) {
        try {
            this.hd.setDimensions(width);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setDimensions(float width, float height) {
        try {
            this.hd.m586a(width, height);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPosition(LatLng latLng) {
        try {
            this.hd.setPosition(latLng);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setPositionFromBounds(LatLngBounds bounds) {
        try {
            this.hd.setPositionFromBounds(bounds);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setTransparency(float transparency) {
        try {
            this.hd.setTransparency(transparency);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setVisible(boolean visible) {
        try {
            this.hd.setVisible(visible);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public void setZIndex(float zIndex) {
        try {
            this.hd.setZIndex(zIndex);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
