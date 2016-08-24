package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.Parcelable;

/* renamed from: com.google.android.gms.maps.internal.o */
public final class C0180o {
    private C0180o() {
    }

    public static void m550a(Bundle bundle, String str, Parcelable parcelable) {
        bundle.setClassLoader(C0180o.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        bundle2.setClassLoader(C0180o.class.getClassLoader());
        bundle2.putParcelable(str, parcelable);
        bundle.putBundle("map_state", bundle2);
    }
}
