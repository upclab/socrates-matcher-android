package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.games.C0884b;
import com.google.android.gms.games.Game;
import com.google.android.gms.internal.C0159s;
import java.util.ArrayList;

/* renamed from: com.google.android.gms.games.multiplayer.b */
public final class C0888b extends C0097b implements Invitation {
    private final ArrayList<Participant> eJ;
    private final Game eL;
    private final C0889d eM;

    C0888b(C0468d c0468d, int i, int i2) {
        super(c0468d, i);
        this.eL = new C0884b(c0468d, i);
        this.eJ = new ArrayList(i2);
        String string = getString("external_inviter_id");
        Object obj = null;
        for (int i3 = 0; i3 < i2; i3++) {
            C0889d c0889d = new C0889d(this.S, this.V + i3);
            if (c0889d.getParticipantId().equals(string)) {
                obj = c0889d;
            }
            this.eJ.add(c0889d);
        }
        this.eM = (C0889d) C0159s.m514b(obj, (Object) "Must have a valid inviter!");
    }

    public int aL() {
        return getInteger("type");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return InvitationEntity.m1395a(this, obj);
    }

    public Invitation freeze() {
        return new InvitationEntity(this);
    }

    public long getCreationTimestamp() {
        return getLong("creation_timestamp");
    }

    public Game getGame() {
        return this.eL;
    }

    public String getInvitationId() {
        return getString("external_invitation_id");
    }

    public Participant getInviter() {
        return this.eM;
    }

    public ArrayList<Participant> getParticipants() {
        return this.eJ;
    }

    public int getVariant() {
        return getInteger("variant");
    }

    public int hashCode() {
        return InvitationEntity.m1394a(this);
    }

    public String toString() {
        return InvitationEntity.m1396b((Invitation) this);
    }

    public void writeToParcel(Parcel dest, int flags) {
        ((InvitationEntity) freeze()).writeToParcel(dest, flags);
    }
}
