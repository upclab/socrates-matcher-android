package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.internal.C0159s;
import com.google.android.gms.maps.internal.C0168c;
import com.google.android.gms.maps.internal.C0181p;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class MapsInitializer {
    private MapsInitializer() {
    }

    public static void initialize(Context context) throws GooglePlayServicesNotAvailableException {
        C0159s.m517d(context);
        C0168c i = C0181p.m553i(context);
        try {
            CameraUpdateFactory.m532a(i.bk());
            BitmapDescriptorFactory.m557a(i.bl());
        } catch (RemoteException e) {
            throw new RuntimeRemoteException(e);
        }
    }
}
