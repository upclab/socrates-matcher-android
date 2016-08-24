package com.google.android.gms.games;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import org.joda.time.DateTimeConstants;

/* renamed from: com.google.android.gms.games.a */
public class C0118a implements Creator<GameEntity> {
    static void m140a(GameEntity gameEntity, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        C0109b.m115a(parcel, 1, gameEntity.getApplicationId(), false);
        C0109b.m115a(parcel, 2, gameEntity.getDisplayName(), false);
        C0109b.m115a(parcel, 3, gameEntity.getPrimaryCategory(), false);
        C0109b.m115a(parcel, 4, gameEntity.getSecondaryCategory(), false);
        C0109b.m115a(parcel, 5, gameEntity.getDescription(), false);
        C0109b.m115a(parcel, 6, gameEntity.getDeveloperName(), false);
        C0109b.m114a(parcel, 7, gameEntity.getIconImageUri(), i, false);
        C0109b.m114a(parcel, 8, gameEntity.getHiResImageUri(), i, false);
        C0109b.m114a(parcel, 9, gameEntity.getFeaturedImageUri(), i, false);
        C0109b.m118a(parcel, 10, gameEntity.isPlayEnabledGame());
        C0109b.m118a(parcel, 11, gameEntity.isInstanceInstalled());
        C0109b.m115a(parcel, 12, gameEntity.getInstancePackageName(), false);
        C0109b.m125c(parcel, 13, gameEntity.getGameplayAclStatus());
        C0109b.m125c(parcel, 14, gameEntity.getAchievementTotalCount());
        C0109b.m125c(parcel, 15, gameEntity.getLeaderboardCount());
        C0109b.m125c(parcel, DateTimeConstants.MILLIS_PER_SECOND, gameEntity.m1387i());
        C0109b.m106C(parcel, d);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m141n(x0);
    }

    public GameEntity m141n(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        Uri uri = null;
        Uri uri2 = null;
        Uri uri3 = null;
        boolean z = false;
        boolean z2 = false;
        String str7 = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    str = C0108a.m88l(parcel, b);
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str2 = C0108a.m88l(parcel, b);
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    str3 = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str4 = C0108a.m88l(parcel, b);
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str5 = C0108a.m88l(parcel, b);
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    str6 = C0108a.m88l(parcel, b);
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    uri = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    uri2 = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    uri3 = (Uri) C0108a.m71a(parcel, b, Uri.CREATOR);
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    z = C0108a.m79c(parcel, b);
                    break;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    z2 = C0108a.m79c(parcel, b);
                    break;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    str7 = C0108a.m88l(parcel, b);
                    break;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    i2 = C0108a.m82f(parcel, b);
                    break;
                case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                    i3 = C0108a.m82f(parcel, b);
                    break;
                case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                    i4 = C0108a.m82f(parcel, b);
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
            return new GameEntity(i, str, str2, str3, str4, str5, str6, uri, uri2, uri3, z, z2, str7, i2, i3, i4);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m142z(x0);
    }

    public GameEntity[] m142z(int i) {
        return new GameEntity[i];
    }
}
