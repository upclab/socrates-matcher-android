package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.C0469f;

public final class LeaderboardBuffer extends C0469f<Leaderboard> {
    public LeaderboardBuffer(C0468d dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m1159a(int i, int i2) {
        return getEntry(i, i2);
    }

    protected Leaderboard getEntry(int rowIndex, int numChildren) {
        return new C0481a(this.S, rowIndex, numChildren);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_leaderboard_id";
    }
}
