package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.DataBuffer;

public final class LeaderboardScoreBuffer extends DataBuffer<LeaderboardScore> {
    private final C0120b ep;

    public LeaderboardScoreBuffer(C0468d dataHolder) {
        super(dataHolder);
        this.ep = new C0120b(dataHolder.m644l());
    }

    public C0120b aF() {
        return this.ep;
    }

    public LeaderboardScore get(int position) {
        return new C0887d(this.S, position);
    }
}
