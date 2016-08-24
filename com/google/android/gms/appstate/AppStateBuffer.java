package com.google.android.gms.appstate;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.DataBuffer;

public final class AppStateBuffer extends DataBuffer<AppState> {
    public AppStateBuffer(C0468d dataHolder) {
        super(dataHolder);
    }

    public AppState get(int position) {
        return new C0882b(this.S, position);
    }
}
