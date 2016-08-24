package com.google.android.gms.games.multiplayer;

import android.database.CharArrayBuffer;
import android.net.Uri;
import android.os.Parcel;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.C0885d;
import com.google.android.gms.games.Player;

/* renamed from: com.google.android.gms.games.multiplayer.d */
public final class C0889d extends C0097b implements Participant {
    private final C0885d eS;

    public C0889d(C0468d c0468d, int i) {
        super(c0468d, i);
        this.eS = new C0885d(c0468d, i);
    }

    public String aM() {
        return getString("client_address");
    }

    public int aN() {
        return getInteger("capabilities");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return ParticipantEntity.m1401a(this, obj);
    }

    public Participant freeze() {
        return new ParticipantEntity(this);
    }

    public String getDisplayName() {
        return m32e("external_player_id") ? getString("default_display_name") : this.eS.getDisplayName();
    }

    public void getDisplayName(CharArrayBuffer dataOut) {
        if (m32e("external_player_id")) {
            m30a("default_display_name", dataOut);
        } else {
            this.eS.getDisplayName(dataOut);
        }
    }

    public Uri getHiResImageUri() {
        return m32e("external_player_id") ? null : this.eS.getHiResImageUri();
    }

    public Uri getIconImageUri() {
        return m32e("external_player_id") ? m31d("default_display_image_uri") : this.eS.getIconImageUri();
    }

    public String getParticipantId() {
        return getString("external_participant_id");
    }

    public Player getPlayer() {
        return m32e("external_player_id") ? null : this.eS;
    }

    public int getStatus() {
        return getInteger("player_status");
    }

    public int hashCode() {
        return ParticipantEntity.m1400a(this);
    }

    public boolean isConnectedToRoom() {
        return getInteger("connected") > 0;
    }

    public String toString() {
        return ParticipantEntity.m1402b((Participant) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((ParticipantEntity) freeze()).writeToParcel(dest, flags);
    }
}
