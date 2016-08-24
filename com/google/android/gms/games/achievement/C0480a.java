package com.google.android.gms.games.achievement;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.C0885d;
import com.google.android.gms.games.Player;
import com.google.android.gms.internal.C0143h;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0158r.C0157a;
import com.google.android.gms.plus.PlusShare;

/* renamed from: com.google.android.gms.games.achievement.a */
public final class C0480a extends C0097b implements Achievement {
    C0480a(C0468d c0468d, int i) {
        super(c0468d, i);
    }

    public String getAchievementId() {
        return getString("external_achievement_id");
    }

    public int getCurrentSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        return getInteger("current_steps");
    }

    public String getDescription() {
        return getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
    }

    public void getDescription(CharArrayBuffer dataOut) {
        m30a(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, dataOut);
    }

    public String getFormattedCurrentSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        return getString("formatted_current_steps");
    }

    public void getFormattedCurrentSteps(CharArrayBuffer dataOut) {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        m30a("formatted_current_steps", dataOut);
    }

    public String getFormattedTotalSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        return getString("formatted_total_steps");
    }

    public void getFormattedTotalSteps(CharArrayBuffer dataOut) {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        m30a("formatted_total_steps", dataOut);
    }

    public long getLastUpdatedTimestamp() {
        return getLong("last_updated_timestamp");
    }

    public String getName() {
        return getString("name");
    }

    public void getName(CharArrayBuffer dataOut) {
        m30a("name", dataOut);
    }

    public Player getPlayer() {
        return new C0885d(this.S, this.V);
    }

    public Uri getRevealedImageUri() {
        return m31d("revealed_icon_image_uri");
    }

    public int getState() {
        return getInteger("state");
    }

    public int getTotalSteps() {
        boolean z = true;
        if (getType() != 1) {
            z = false;
        }
        C0143h.m459a(z);
        return getInteger("total_steps");
    }

    public int getType() {
        return getInteger("type");
    }

    public Uri getUnlockedImageUri() {
        return m31d("unlocked_icon_image_uri");
    }

    public String toString() {
        C0157a a = C0158r.m510c(this).m508a("id", getAchievementId()).m508a("name", getName()).m508a("state", Integer.valueOf(getState())).m508a("type", Integer.valueOf(getType()));
        if (getType() == 1) {
            a.m508a("steps", getCurrentSteps() + "/" + getTotalSteps());
        }
        return a.toString();
    }
}
