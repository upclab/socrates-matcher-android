package com.google.android.gms.games.multiplayer.realtime;

import android.database.CharArrayBuffer;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.C0889d;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;

/* renamed from: com.google.android.gms.games.multiplayer.realtime.c */
public final class C0891c extends C0097b implements Room {
    private final int eo;

    C0891c(C0468d c0468d, int i, int i2) {
        super(c0468d, i);
        this.eo = i2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return RoomEntity.m1407a(this, obj);
    }

    public Room freeze() {
        return new RoomEntity(this);
    }

    public Bundle getAutoMatchCriteria() {
        return !getBoolean("has_automatch_criteria") ? null : RoomConfig.createAutoMatchCriteria(getInteger("automatch_min_players"), getInteger("automatch_max_players"), getLong("automatch_bit_mask"));
    }

    public int getAutoMatchWaitEstimateSeconds() {
        return getInteger("automatch_wait_estimate_sec");
    }

    public long getCreationTimestamp() {
        return getLong("creation_timestamp");
    }

    public String getCreatorId() {
        return getString("creator_external");
    }

    public String getDescription() {
        return getString(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION);
    }

    public void getDescription(CharArrayBuffer dataOut) {
        m30a(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, dataOut);
    }

    public String getParticipantId(String playerId) {
        ArrayList participants = getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            Player player = participant.getPlayer();
            if (player != null && player.getPlayerId().equals(playerId)) {
                return participant.getParticipantId();
            }
        }
        return null;
    }

    public ArrayList<String> getParticipantIds() {
        ArrayList participants = getParticipants();
        ArrayList<String> arrayList = new ArrayList(this.eo);
        for (int i = 0; i < this.eo; i++) {
            arrayList.add(((Participant) participants.get(i)).getParticipantId());
        }
        return arrayList;
    }

    public int getParticipantStatus(String participantId) {
        ArrayList participants = getParticipants();
        int size = participants.size();
        for (int i = 0; i < size; i++) {
            Participant participant = (Participant) participants.get(i);
            if (participant.getParticipantId().equals(participantId)) {
                return participant.getStatus();
            }
        }
        throw new IllegalStateException("Participant " + participantId + " is not in room " + getRoomId());
    }

    public ArrayList<Participant> getParticipants() {
        ArrayList<Participant> arrayList = new ArrayList(this.eo);
        for (int i = 0; i < this.eo; i++) {
            arrayList.add(new C0889d(this.S, this.V + i));
        }
        return arrayList;
    }

    public String getRoomId() {
        return getString("external_match_id");
    }

    public int getStatus() {
        return getInteger("status");
    }

    public int getVariant() {
        return getInteger("variant");
    }

    public int hashCode() {
        return RoomEntity.m1406a(this);
    }

    public String toString() {
        return RoomEntity.m1408b((Room) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((RoomEntity) freeze()).writeToParcel(dest, flags);
    }
}
