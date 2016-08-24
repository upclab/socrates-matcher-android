package com.google.android.gms.games;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;

/* renamed from: com.google.android.gms.games.d */
public final class C0885d extends C0097b implements Player {
    public C0885d(C0468d c0468d, int i) {
        super(c0468d, i);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return PlayerEntity.m1389a(this, obj);
    }

    public Player freeze() {
        return new PlayerEntity(this);
    }

    public String getDisplayName() {
        return getString("profile_name");
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        m30a("profile_name", dataOut);
    }

    public Uri getHiResImageUri() {
        return m31d("profile_hi_res_image_uri");
    }

    public Uri getIconImageUri() {
        return m31d("profile_icon_image_uri");
    }

    public String getPlayerId() {
        return getString("external_player_id");
    }

    public long getRetrievedTimestamp() {
        return getLong("last_updated");
    }

    public boolean hasHiResImage() {
        return getHiResImageUri() != null;
    }

    public boolean hasIconImage() {
        return getIconImageUri() != null;
    }

    public int hashCode() {
        return PlayerEntity.m1388a(this);
    }

    public String toString() {
        return PlayerEntity.m1390b((Player) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((PlayerEntity) freeze()).writeToParcel(dest, flags);
    }
}
