package com.google.android.gms.games;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.DataBuffer;

public final class GameBuffer extends DataBuffer<Game> {
    public GameBuffer(C0468d dataHolder) {
        super(dataHolder);
    }

    public Game get(int position) {
        return new C0884b(this.S, position);
    }
}
