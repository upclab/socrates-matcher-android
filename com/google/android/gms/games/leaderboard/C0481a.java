package com.google.android.gms.games.leaderboard;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.internal.C0158r;
import java.util.ArrayList;

/* renamed from: com.google.android.gms.games.leaderboard.a */
public final class C0481a extends C0097b implements Leaderboard {
    private final int eo;

    C0481a(C0468d c0468d, int i, int i2) {
        super(c0468d, i);
        this.eo = i2;
    }

    public String getDisplayName() {
        return getString("name");
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        m30a("name", dataOut);
    }

    public Uri getIconImageUri() {
        return m31d("board_icon_image_uri");
    }

    public String getLeaderboardId() {
        return getString("external_leaderboard_id");
    }

    public int getScoreOrder() {
        return getInteger("score_order");
    }

    public ArrayList<LeaderboardVariant> getVariants() {
        ArrayList<LeaderboardVariant> arrayList = new ArrayList(this.eo);
        for (int i = 0; i < this.eo; i++) {
            arrayList.add(new C0482e(this.S, this.V + i));
        }
        return arrayList;
    }

    public String toString() {
        return C0158r.m510c(this).m508a("ID", getLeaderboardId()).m508a("DisplayName", getDisplayName()).m508a("IconImageURI", getIconImageUri()).m508a("ScoreOrder", Integer.valueOf(getScoreOrder())).toString();
    }
}
