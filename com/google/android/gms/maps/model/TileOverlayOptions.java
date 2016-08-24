package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.C0182q;
import com.google.android.gms.maps.model.internal.C0198g;
import com.google.android.gms.maps.model.internal.C0198g.C0610a;

public final class TileOverlayOptions implements SafeParcelable {
    public static final TileOverlayOptionsCreator CREATOR;
    private final int ab;
    private C0198g hG;
    private TileProvider hH;
    private float hb;
    private boolean hc;

    /* renamed from: com.google.android.gms.maps.model.TileOverlayOptions.1 */
    class C05941 implements TileProvider {
        private final C0198g hI;
        final /* synthetic */ TileOverlayOptions hJ;

        C05941(TileOverlayOptions tileOverlayOptions) {
            this.hJ = tileOverlayOptions;
            this.hI = this.hJ.hG;
        }

        public Tile getTile(int x, int y, int zoom) {
            try {
                return this.hI.getTile(x, y, zoom);
            } catch (RemoteException e) {
                return null;
            }
        }
    }

    /* renamed from: com.google.android.gms.maps.model.TileOverlayOptions.2 */
    class C09272 extends C0610a {
        final /* synthetic */ TileOverlayOptions hJ;
        final /* synthetic */ TileProvider hK;

        C09272(TileOverlayOptions tileOverlayOptions, TileProvider tileProvider) {
            this.hJ = tileOverlayOptions;
            this.hK = tileProvider;
        }

        public Tile getTile(int x, int y, int zoom) {
            return this.hK.getTile(x, y, zoom);
        }
    }

    static {
        CREATOR = new TileOverlayOptionsCreator();
    }

    public TileOverlayOptions() {
        this.hc = true;
        this.ab = 1;
    }

    TileOverlayOptions(int versionCode, IBinder delegate, boolean visible, float zIndex) {
        this.hc = true;
        this.ab = versionCode;
        this.hG = C0610a.m1130U(delegate);
        this.hH = this.hG == null ? null : new C05941(this);
        this.hc = visible;
        this.hb = zIndex;
    }

    IBinder bs() {
        return this.hG.asBinder();
    }

    public int describeContents() {
        return 0;
    }

    public TileProvider getTileProvider() {
        return this.hH;
    }

    public float getZIndex() {
        return this.hb;
    }

    int m1106i() {
        return this.ab;
    }

    public boolean isVisible() {
        return this.hc;
    }

    public TileOverlayOptions tileProvider(TileProvider tileProvider) {
        this.hH = tileProvider;
        this.hG = this.hH == null ? null : new C09272(this, tileProvider);
        return this;
    }

    public TileOverlayOptions visible(boolean visible) {
        this.hc = visible;
        return this;
    }

    public void writeToParcel(Parcel out, int flags) {
        if (C0182q.bn()) {
            C0199j.m592a(this, out, flags);
        } else {
            TileOverlayOptionsCreator.m568a(this, out, flags);
        }
    }

    public TileOverlayOptions zIndex(float zIndex) {
        this.hb = zIndex;
        return this;
    }
}
