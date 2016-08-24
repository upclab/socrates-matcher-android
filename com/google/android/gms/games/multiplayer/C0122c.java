package com.google.android.gms.games.multiplayer;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.games.PlayerEntity;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.games.multiplayer.c */
public class C0122c implements Creator<ParticipantEntity> {
    static void m151a(ParticipantEntity participantEntity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, participantEntity.getParticipantId(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, participantEntity.m1405i());
        C0109b.m115a(parcel, 2, participantEntity.getDisplayName(), false);
        C0109b.m114a(parcel, 3, participantEntity.getIconImageUri(), i, false);
        C0109b.m114a(parcel, 4, participantEntity.getHiResImageUri(), i, false);
        C0109b.m125c(parcel, 5, participantEntity.getStatus());
        C0109b.m115a(parcel, 6, participantEntity.aM(), false);
        C0109b.m118a(parcel, 7, participantEntity.isConnectedToRoom());
        C0109b.m114a(parcel, 8, participantEntity.getPlayer(), i, false);
        C0109b.m125c(parcel, 9, participantEntity.aN());
        C0109b.m106C(parcel, d);
    }

    public ParticipantEntity[] m152I(int i) {
        return new ParticipantEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m153q(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m152I(x0);
    }

    public ParticipantEntity m153q(Parcel parcel) {
        int i = 0;
        PlayerEntity playerEntity = null;
        int c = C0108a.m77c(parcel);
        boolean z = false;
        String str = null;
        int i2 = 0;
        Uri uri = null;
        Uri uri2 = null;
        String str2 = null;
        String str3 = null;
        int i3 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str3 = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    uri2 = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    uri = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    playerEntity = (PlayerEntity) C0108a.m71a(parcel, b, PlayerEntity.CREATOR);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
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
            return new ParticipantEntity(i3, str3, str2, uri2, uri, i2, str, z, playerEntity, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
