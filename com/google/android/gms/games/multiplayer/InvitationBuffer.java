package com.google.android.gms.games.multiplayer;

import com.google.android.gms.common.data.C0468d;
import com.google.android.gms.common.data.C0469f;

public final class InvitationBuffer extends C0469f<Invitation> {
    public InvitationBuffer(C0468d dataHolder) {
        super(dataHolder);
    }

    protected /* synthetic */ Object m1163a(int i, int i2) {
        return getEntry(i, i2);
    }

    protected Invitation getEntry(int rowIndex, int numChildren) {
        return new C0888b(this.S, rowIndex, numChildren);
    }

    protected String getPrimaryDataMarkerColumn() {
        return "external_invitation_id";
    }
}
