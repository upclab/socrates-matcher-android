package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.dynamic.C0113b;
import com.google.android.gms.dynamic.C0883c;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.internal.C0167b.C0569a;
import com.google.android.gms.maps.internal.C0169d.C0573a;
import com.google.android.gms.maps.internal.C0170e.C0575a;
import com.google.android.gms.maps.internal.C0171f.C0577a;
import com.google.android.gms.maps.internal.C0172g;
import com.google.android.gms.maps.internal.C0173h.C0581a;
import com.google.android.gms.maps.internal.C0174i.C0583a;
import com.google.android.gms.maps.internal.C0175j.C0585a;
import com.google.android.gms.maps.internal.C0176k.C0587a;
import com.google.android.gms.maps.internal.C0177l.C0589a;
import com.google.android.gms.maps.internal.C0178m.C0591a;
import com.google.android.gms.maps.internal.C0179n.C0593a;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.ILocationSourceDelegate.C0559a;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.C0194c;
import com.google.android.gms.maps.model.internal.C0195d;
import com.google.android.gms.maps.model.internal.C0197f;

public final class GoogleMap {
    public static final int MAP_TYPE_HYBRID = 4;
    public static final int MAP_TYPE_NONE = 0;
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_TERRAIN = 3;
    private final IGoogleMapDelegate fX;
    private UiSettings fY;

    public interface CancelableCallback {
        void onCancel();

        void onFinish();
    }

    public interface InfoWindowAdapter {
        View getInfoContents(Marker marker);

        View getInfoWindow(Marker marker);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition cameraPosition);
    }

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(Marker marker);
    }

    public interface OnMapClickListener {
        void onMapClick(LatLng latLng);
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng latLng);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface OnMarkerDragListener {
        void onMarkerDrag(Marker marker);

        void onMarkerDragEnd(Marker marker);

        void onMarkerDragStart(Marker marker);
    }

    public interface OnMyLocationButtonClickListener {
        boolean onMyLocationButtonClick();
    }

    @Deprecated
    public interface OnMyLocationChangeListener {
        void onMyLocationChange(Location location);
    }

    public interface SnapshotReadyCallback {
        void onSnapshotReady(Bitmap bitmap);
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.10 */
    class AnonymousClass10 extends C0573a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ InfoWindowAdapter gl;

        AnonymousClass10(GoogleMap googleMap, InfoWindowAdapter infoWindowAdapter) {
            this.ga = googleMap;
            this.gl = infoWindowAdapter;
        }

        public C0113b m1373f(C0195d c0195d) {
            return C0883c.m1158f(this.gl.getInfoWindow(new Marker(c0195d)));
        }

        public C0113b m1374g(C0195d c0195d) {
            return C0883c.m1158f(this.gl.getInfoContents(new Marker(c0195d)));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.11 */
    class AnonymousClass11 extends C0591a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMyLocationChangeListener gm;

        AnonymousClass11(GoogleMap googleMap, OnMyLocationChangeListener onMyLocationChangeListener) {
            this.ga = googleMap;
            this.gm = onMyLocationChangeListener;
        }

        public void m1375b(C0113b c0113b) {
            this.gm.onMyLocationChange((Location) C0883c.m1157a(c0113b));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.1 */
    class C09171 extends C0559a {
        final /* synthetic */ LocationSource fZ;
        final /* synthetic */ GoogleMap ga;

        /* renamed from: com.google.android.gms.maps.GoogleMap.1.1 */
        class C05471 implements OnLocationChangedListener {
            final /* synthetic */ C0172g gb;
            final /* synthetic */ C09171 gc;

            C05471(C09171 c09171, C0172g c0172g) {
                this.gc = c09171;
                this.gb = c0172g;
            }

            public void onLocationChanged(Location location) {
                try {
                    this.gb.m544e(C0883c.m1158f(location));
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                }
            }
        }

        C09171(GoogleMap googleMap, LocationSource locationSource) {
            this.ga = googleMap;
            this.fZ = locationSource;
        }

        public void activate(C0172g listener) {
            this.fZ.activate(new C05471(this, listener));
        }

        public void deactivate() {
            this.fZ.deactivate();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.2 */
    class C09182 extends C0589a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMyLocationButtonClickListener gd;

        C09182(GoogleMap googleMap, OnMyLocationButtonClickListener onMyLocationButtonClickListener) {
            this.ga = googleMap;
            this.gd = onMyLocationButtonClickListener;
        }

        public boolean onMyLocationButtonClick() throws RemoteException {
            return this.gd.onMyLocationButtonClick();
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.3 */
    class C09193 extends C0593a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ SnapshotReadyCallback ge;

        C09193(GoogleMap googleMap, SnapshotReadyCallback snapshotReadyCallback) {
            this.ga = googleMap;
            this.ge = snapshotReadyCallback;
        }

        public void onSnapshotReady(Bitmap snapshot) throws RemoteException {
            this.ge.onSnapshotReady(snapshot);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.4 */
    class C09204 extends C0575a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnCameraChangeListener gf;

        C09204(GoogleMap googleMap, OnCameraChangeListener onCameraChangeListener) {
            this.ga = googleMap;
            this.gf = onCameraChangeListener;
        }

        public void onCameraChange(CameraPosition position) {
            this.gf.onCameraChange(position);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.5 */
    class C09215 extends C0581a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMapClickListener gg;

        C09215(GoogleMap googleMap, OnMapClickListener onMapClickListener) {
            this.ga = googleMap;
            this.gg = onMapClickListener;
        }

        public void onMapClick(LatLng point) {
            this.gg.onMapClick(point);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.6 */
    class C09226 extends C0583a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMapLongClickListener gh;

        C09226(GoogleMap googleMap, OnMapLongClickListener onMapLongClickListener) {
            this.ga = googleMap;
            this.gh = onMapLongClickListener;
        }

        public void onMapLongClick(LatLng point) {
            this.gh.onMapLongClick(point);
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.7 */
    class C09237 extends C0585a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMarkerClickListener gi;

        C09237(GoogleMap googleMap, OnMarkerClickListener onMarkerClickListener) {
            this.ga = googleMap;
            this.gi = onMarkerClickListener;
        }

        public boolean m1376a(C0195d c0195d) {
            return this.gi.onMarkerClick(new Marker(c0195d));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.8 */
    class C09248 extends C0587a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnMarkerDragListener gj;

        C09248(GoogleMap googleMap, OnMarkerDragListener onMarkerDragListener) {
            this.ga = googleMap;
            this.gj = onMarkerDragListener;
        }

        public void m1377b(C0195d c0195d) {
            this.gj.onMarkerDragStart(new Marker(c0195d));
        }

        public void m1378c(C0195d c0195d) {
            this.gj.onMarkerDragEnd(new Marker(c0195d));
        }

        public void m1379d(C0195d c0195d) {
            this.gj.onMarkerDrag(new Marker(c0195d));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.9 */
    class C09259 extends C0577a {
        final /* synthetic */ GoogleMap ga;
        final /* synthetic */ OnInfoWindowClickListener gk;

        C09259(GoogleMap googleMap, OnInfoWindowClickListener onInfoWindowClickListener) {
            this.ga = googleMap;
            this.gk = onInfoWindowClickListener;
        }

        public void m1380e(C0195d c0195d) {
            this.gk.onInfoWindowClick(new Marker(c0195d));
        }
    }

    /* renamed from: com.google.android.gms.maps.GoogleMap.a */
    private static final class C0926a extends C0569a {
        private final CancelableCallback gn;

        C0926a(CancelableCallback cancelableCallback) {
            this.gn = cancelableCallback;
        }

        public void onCancel() {
            this.gn.onCancel();
        }

        public void onFinish() {
            this.gn.onFinish();
        }
    }

    protected GoogleMap(IGoogleMapDelegate map) {
        this.fX = (IGoogleMapDelegate) C0159s.m517d(map);
    }

    IGoogleMapDelegate aY() {
        return this.fX;
    }

    public final Circle addCircle(CircleOptions options) {
        try {
            return new Circle(this.fX.addCircle(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final GroundOverlay addGroundOverlay(GroundOverlayOptions options) {
        try {
            C0194c addGroundOverlay = this.fX.addGroundOverlay(options);
            return addGroundOverlay != null ? new GroundOverlay(addGroundOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Marker addMarker(MarkerOptions options) {
        try {
            C0195d addMarker = this.fX.addMarker(options);
            return addMarker != null ? new Marker(addMarker) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polygon addPolygon(PolygonOptions options) {
        try {
            return new Polygon(this.fX.addPolygon(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Polyline addPolyline(PolylineOptions options) {
        try {
            return new Polyline(this.fX.addPolyline(options));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final TileOverlay addTileOverlay(TileOverlayOptions options) {
        try {
            C0197f addTileOverlay = this.fX.addTileOverlay(options);
            return addTileOverlay != null ? new TileOverlay(addTileOverlay) : null;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update) {
        try {
            this.fX.animateCamera(update.aW());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, int durationMs, CancelableCallback callback) {
        try {
            this.fX.animateCameraWithDurationAndCallback(update.aW(), durationMs, callback == null ? null : new C0926a(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void animateCamera(CameraUpdate update, CancelableCallback callback) {
        try {
            this.fX.animateCameraWithCallback(update.aW(), callback == null ? null : new C0926a(callback));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void clear() {
        try {
            this.fX.clear();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final CameraPosition getCameraPosition() {
        try {
            return this.fX.getCameraPosition();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final int getMapType() {
        try {
            return this.fX.getMapType();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMaxZoomLevel() {
        try {
            return this.fX.getMaxZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final float getMinZoomLevel() {
        try {
            return this.fX.getMinZoomLevel();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    @Deprecated
    public final Location getMyLocation() {
        try {
            return this.fX.getMyLocation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final Projection getProjection() {
        try {
            return new Projection(this.fX.getProjection());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final UiSettings getUiSettings() {
        try {
            if (this.fY == null) {
                this.fY = new UiSettings(this.fX.getUiSettings());
            }
            return this.fY;
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isIndoorEnabled() {
        try {
            return this.fX.isIndoorEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isMyLocationEnabled() {
        try {
            return this.fX.isMyLocationEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean isTrafficEnabled() {
        try {
            return this.fX.isTrafficEnabled();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void moveCamera(CameraUpdate update) {
        try {
            this.fX.moveCamera(update.aW());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final boolean setIndoorEnabled(boolean enabled) {
        try {
            return this.fX.setIndoorEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setInfoWindowAdapter(InfoWindowAdapter adapter) {
        if (adapter == null) {
            try {
                this.fX.setInfoWindowAdapter(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setInfoWindowAdapter(new AnonymousClass10(this, adapter));
    }

    public final void setLocationSource(LocationSource source) {
        if (source == null) {
            try {
                this.fX.setLocationSource(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setLocationSource(new C09171(this, source));
    }

    public final void setMapType(int type) {
        try {
            this.fX.setMapType(type);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setMyLocationEnabled(boolean enabled) {
        try {
            this.fX.setMyLocationEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setOnCameraChangeListener(OnCameraChangeListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnCameraChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnCameraChangeListener(new C09204(this, listener));
    }

    public final void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnInfoWindowClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnInfoWindowClickListener(new C09259(this, listener));
    }

    public final void setOnMapClickListener(OnMapClickListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMapClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMapClickListener(new C09215(this, listener));
    }

    public final void setOnMapLongClickListener(OnMapLongClickListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMapLongClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMapLongClickListener(new C09226(this, listener));
    }

    public final void setOnMarkerClickListener(OnMarkerClickListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMarkerClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMarkerClickListener(new C09237(this, listener));
    }

    public final void setOnMarkerDragListener(OnMarkerDragListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMarkerDragListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMarkerDragListener(new C09248(this, listener));
    }

    public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMyLocationButtonClickListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMyLocationButtonClickListener(new C09182(this, listener));
    }

    @Deprecated
    public final void setOnMyLocationChangeListener(OnMyLocationChangeListener listener) {
        if (listener == null) {
            try {
                this.fX.setOnMyLocationChangeListener(null);
                return;
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
        this.fX.setOnMyLocationChangeListener(new AnonymousClass11(this, listener));
    }

    public final void setPadding(int left, int top, int right, int bottom) {
        try {
            this.fX.setPadding(left, top, right, bottom);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void setTrafficEnabled(boolean enabled) {
        try {
            this.fX.setTrafficEnabled(enabled);
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void snapshot(SnapshotReadyCallback callback) {
        snapshot(callback, null);
    }

    public final void snapshot(SnapshotReadyCallback callback, Bitmap bitmap) {
        try {
            this.fX.snapshot(new C09193(this, callback), (C0883c) (bitmap != null ? C0883c.m1158f(bitmap) : null));
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }

    public final void stopAnimation() {
        try {
            this.fX.stopAnimation();
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
