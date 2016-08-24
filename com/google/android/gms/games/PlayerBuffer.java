package com.google.android.gms.games;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.DataBuffer;

public final class PlayerBuffer extends DataBuffer<Player> {
    public PlayerBuffer(C0468d dataHolder) {
        super(dataHolder);
    }

    public Player get(int position) {
        return new C0885d(this.S, position);
    }
}
