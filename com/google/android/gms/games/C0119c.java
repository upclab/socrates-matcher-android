package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.games.c */
public class C0119c implements Creator<PlayerEntity> {
    static void m143a(PlayerEntity playerEntity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, playerEntity.getPlayerId(), false);
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, playerEntity.m1393i());
        C0109b.m115a(parcel, 2, playerEntity.getDisplayName(), false);
        C0109b.m114a(parcel, 3, playerEntity.getIconImageUri(), i, false);
        C0109b.m114a(parcel, 4, playerEntity.getHiResImageUri(), i, false);
        C0109b.m110a(parcel, 5, playerEntity.getRetrievedTimestamp());
        C0109b.m106C(parcel, d);
    }

    public PlayerEntity[] m144A(int i) {
        return new PlayerEntity[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m145o(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m144A(x0);
    }

    public PlayerEntity m145o(Parcel parcel) {
        Uri uri = null;
        int c = C0108a.m77c(parcel);
        int i = 0;
        long j = 0;
        Uri uri2 = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    uri2 = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    uri = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    j = C0108a.m83g(parcel, b);
                    break;
                case DateTimeConstants.MILLIS_PER_SECOND /*1000*/:
                    i = C0108a.m82f(parcel, b);
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new PlayerEntity(i, str2, str, uri2, uri, j);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
