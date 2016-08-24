package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.C0885d;
import com.google.android.gms.games.Player;

/* renamed from: com.google.android.gms.games.leaderboard.d */
public final class C0887d extends C0097b implements LeaderboardScore {
    private final C0885d eA;

    C0887d(C0468d c0468d, int i) {
        super(c0468d, i);
        this.eA = new C0885d(c0468d, i);
    }

    public LeaderboardScore aH() {
        return new C0886c(this);
    }

    public boolean equals(Object obj) {
        return C0886c.m1161a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return aH();
    }

    public String getDisplayRank() {
        return getString("display_rank");
    }

    public void getDisplayRank(CharArrayBuffer dataOut) {
        m30a("display_rank", dataOut);
    }

    public String getDisplayScore() {
        return getString("display_score");
    }

    public void getDisplayScore(CharArrayBuffer dataOut) {
        m30a("display_score", dataOut);
    }

    public long getRank() {
        return getLong("rank");
    }

    public long getRawScore() {
        return getLong("raw_score");
    }

    public Player getScoreHolder() {
        return m32e("external_player_id") ? null : this.eA;
    }

    public String getScoreHolderDisplayName() {
        return m32e("external_player_id") ? getString("default_display_name") : this.eA.getDisplayName();
    }

    public void getScoreHolderDisplayName(CharArrayBuffer dataOut) {
        if (m32e("external_player_id")) {
            m30a("default_display_name", dataOut);
        } else {
            this.eA.getDisplayName(dataOut);
        }
    }

    public Uri getScoreHolderHiResImageUri() {
        return m32e("external_player_id") ? null : this.eA.getHiResImageUri();
    }

    public Uri getScoreHolderIconImageUri() {
        return m32e("external_player_id") ? m31d("default_display_image_uri") : this.eA.getIconImageUri();
    }

    public long getTimestampMillis() {
        return getLong("achieved_timestamp");
    }

    public int hashCode() {
        return C0886c.m1160a(this);
    }

    public String toString() {
        return C0886c.m1162b(this);
    }
}
