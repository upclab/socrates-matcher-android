package com.google.android.gms.games.multiplayer.realtime;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.games.multiplayer.ParticipantEntity;
import java.util.ArrayList;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.games.multiplayer.realtime.b */
public class C0125b implements Creator<RoomEntity> {
    static void m156a(RoomEntity roomEntity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, roomEntity.getRoomId(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, roomEntity.m1411i());
        C0109b.m115a(parcel, 2, roomEntity.getCreatorId(), false);
        C0109b.m110a(parcel, 3, roomEntity.getCreationTimestamp());
        C0109b.m125c(parcel, 4, roomEntity.getStatus());
        C0109b.m115a(parcel, 5, roomEntity.getDescription(), false);
        C0109b.m125c(parcel, 6, roomEntity.getVariant());
        C0109b.m111a(parcel, 7, roomEntity.getAutoMatchCriteria(), false);
        C0109b.m124b(parcel, 8, roomEntity.getParticipants(), false);
        C0109b.m125c(parcel, 9, roomEntity.getAutoMatchWaitEstimateSeconds());
        C0109b.m106C(parcel, d);
    }

    public RoomEntity[] m157K(int i) {
        return new RoomEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m158s(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m157K(x0);
    }

    public RoomEntity m158s(Parcel parcel) {
        int i = 0;
        ArrayList arrayList = null;
        int c = C0108a.m77c(parcel);
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        String str2 = null;
        String str3 = null;
        int i4 = 0;
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
                    j = C0108a.m83g(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    bundle = C0108a.m91n(parcel, b);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    arrayList = C0108a.m78c(parcel, b, ParticipantEntity.CREATOR);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i4 = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new RoomEntity(i4, str3, str2, j, i3, str, i2, bundle, arrayList, i);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
