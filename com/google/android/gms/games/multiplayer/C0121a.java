package com.google.android.gms.games.multiplayer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.games.GameEntity;
import java.util.ArrayList;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.games.multiplayer.a */
public class C0121a implements Creator<InvitationEntity> {
    static void m148a(InvitationEntity invitationEntity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m114a(parcel, 1, invitationEntity.getGame(), i, false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, invitationEntity.m1399i());
        C0109b.m115a(parcel, 2, invitationEntity.getInvitationId(), false);
        C0109b.m110a(parcel, 3, invitationEntity.getCreationTimestamp());
        C0109b.m125c(parcel, 4, invitationEntity.aL());
        C0109b.m114a(parcel, 5, invitationEntity.getInviter(), i, false);
        C0109b.m124b(parcel, 6, invitationEntity.getParticipants(), false);
        C0109b.m125c(parcel, 7, invitationEntity.getVariant());
        C0109b.m106C(parcel, d);
    }

    public InvitationEntity[] m149H(int i) {
        return new InvitationEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m150p(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m149H(x0);
    }

    public InvitationEntity m150p(Parcel parcel) {
        int i = 0;
        ArrayList arrayList = null;
        int c = C0108a.m77c(parcel);
        long j = 0;
        ParticipantEntity participantEntity = null;
        int i2 = 0;
        String str = null;
        GameEntity gameEntity = null;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    gameEntity = (GameEntity) C0108a.m71a(parcel, b, GameEntity.CREATOR);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    j = C0108a.m83g(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    participantEntity = (ParticipantEntity) C0108a.m71a(parcel, b, ParticipantEntity.CREATOR);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    arrayList = C0108a.m78c(parcel, b, ParticipantEntity.CREATOR);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new InvitationEntity(i3, gameEntity, str, j, i2, participantEntity, arrayList, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
