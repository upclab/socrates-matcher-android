package com.google.android.gms.games.leaderboard;

import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.bc;
import com.google.android.gms.internal.bd;

/* renamed from: com.google.android.gms.games.leaderboard.e */
public final class C0482e extends C0097b implements LeaderboardVariant {
    C0482e(C0468d c0468d, int i) {
        super(c0468d, i);
    }

    public String aI() {
        return getString("top_page_token_next");
    }

    public String aJ() {
        return getString("window_page_token_prev");
    }

    public String aK() {
        return getString("window_page_token_next");
    }

    public int getCollection() {
        return getInteger("collection");
    }

    public String getDisplayPlayerRank() {
        return getString("player_display_rank");
    }

    public String getDisplayPlayerScore() {
        return getString("player_display_score");
    }

    public long getNumScores() {
        return m32e("total_scores") ? -1 : getLong("total_scores");
    }

    public long getPlayerRank() {
        return m32e("player_rank") ? -1 : getLong("player_rank");
    }

    public long getRawPlayerScore() {
        return m32e("player_raw_score") ? -1 : getLong("player_raw_score");
    }

    public int getTimeSpan() {
        return getInteger("timespan");
    }

    public boolean hasPlayerInfo() {
        return !m32e("player_raw_score");
    }

    public String toString() {
        return C0158r.m510c(this).m508a("TimeSpan", bd.m334G(getTimeSpan())).m508a("Collection", bc.m333G(getCollection())).m508a("RawPlayerScore", hasPlayerInfo() ? Long.valueOf(getRawPlayerScore()) : "none").m508a("DisplayPlayerScore", hasPlayerInfo() ? getDisplayPlayerScore() : "none").m508a("PlayerRank", hasPlayerInfo() ? Long.valueOf(getPlayerRank()) : "none").m508a("DisplayPlayerRank", hasPlayerInfo() ? getDisplayPlayerRank() : "none").m508a("NumScores", Long.valueOf(getNumScores())).m508a("TopPageNextToken", aI()).m508a("WindowPageNextToken", aK()).m508a("WindowPagePrevToken", aJ()).toString();
    }
}
