package com.google.android.gms.games.multiplayer.realtime;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.C0469f;

/* renamed from: com.google.android.gms.games.multiplayer.realtime.a */
public final class C0890a extends C0469f<Room> {
    public C0890a(C0468d c0468d) {
        super(c0468d);
    }

    protected /* synthetic */ Object m1164a(int i, int i2) {
        return m1165b(i, i2);
    }

    protected Room m1165b(int i, int i2) {
        return new C0891c(this.S, i, i2);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_match_id";
    }
}
