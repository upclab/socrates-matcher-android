package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.v4.util.TimeUtils;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.internal.cc.C0907a;
import com.google.android.gms.internal.cc.C0910b;
import com.google.android.gms.internal.cc.C0911c;
import com.google.android.gms.internal.cc.C0912d;
import com.google.android.gms.internal.cc.C0913f;
import com.google.android.gms.internal.cc.C0914g;
import com.google.android.gms.internal.cc.C0915h;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTimeConstants;

public class cd implements Creator<cc> {
    static void m418a(cc ccVar, Parcel parcel, int i) {
        int d = C0109b.m127d(parcel);
        Set bH = ccVar.bH();
        if (bH.contains(Integer.valueOf(1))) {
            C0109b.m125c(parcel, 1, ccVar.m1369i());
        }
        if (bH.contains(Integer.valueOf(2))) {
            C0109b.m115a(parcel, 2, ccVar.getAboutMe(), true);
        }
        if (bH.contains(Integer.valueOf(3))) {
            C0109b.m114a(parcel, 3, ccVar.cc(), i, true);
        }
        if (bH.contains(Integer.valueOf(4))) {
            C0109b.m115a(parcel, 4, ccVar.getBirthday(), true);
        }
        if (bH.contains(Integer.valueOf(5))) {
            C0109b.m115a(parcel, 5, ccVar.getBraggingRights(), true);
        }
        if (bH.contains(Integer.valueOf(6))) {
            C0109b.m125c(parcel, 6, ccVar.getCircledByCount());
        }
        if (bH.contains(Integer.valueOf(7))) {
            C0109b.m114a(parcel, 7, ccVar.cd(), i, true);
        }
        if (bH.contains(Integer.valueOf(8))) {
            C0109b.m115a(parcel, 8, ccVar.getCurrentLocation(), true);
        }
        if (bH.contains(Integer.valueOf(9))) {
            C0109b.m115a(parcel, 9, ccVar.getDisplayName(), true);
        }
        if (bH.contains(Integer.valueOf(12))) {
            C0109b.m125c(parcel, 12, ccVar.getGender());
        }
        if (bH.contains(Integer.valueOf(14))) {
            C0109b.m115a(parcel, 14, ccVar.getId(), true);
        }
        if (bH.contains(Integer.valueOf(15))) {
            C0109b.m114a(parcel, 15, ccVar.ce(), i, true);
        }
        if (bH.contains(Integer.valueOf(16))) {
            C0109b.m118a(parcel, 16, ccVar.isPlusUser());
        }
        if (bH.contains(Integer.valueOf(19))) {
            C0109b.m114a(parcel, 19, ccVar.cf(), i, true);
        }
        if (bH.contains(Integer.valueOf(18))) {
            C0109b.m115a(parcel, 18, ccVar.getLanguage(), true);
        }
        if (bH.contains(Integer.valueOf(21))) {
            C0109b.m125c(parcel, 21, ccVar.getObjectType());
        }
        if (bH.contains(Integer.valueOf(20))) {
            C0109b.m115a(parcel, 20, ccVar.getNickname(), true);
        }
        if (bH.contains(Integer.valueOf(23))) {
            C0109b.m124b(parcel, 23, ccVar.ch(), true);
        }
        if (bH.contains(Integer.valueOf(22))) {
            C0109b.m124b(parcel, 22, ccVar.cg(), true);
        }
        if (bH.contains(Integer.valueOf(25))) {
            C0109b.m125c(parcel, 25, ccVar.getRelationshipStatus());
        }
        if (bH.contains(Integer.valueOf(24))) {
            C0109b.m125c(parcel, 24, ccVar.getPlusOneCount());
        }
        if (bH.contains(Integer.valueOf(27))) {
            C0109b.m115a(parcel, 27, ccVar.getUrl(), true);
        }
        if (bH.contains(Integer.valueOf(26))) {
            C0109b.m115a(parcel, 26, ccVar.getTagline(), true);
        }
        if (bH.contains(Integer.valueOf(29))) {
            C0109b.m118a(parcel, 29, ccVar.isVerified());
        }
        if (bH.contains(Integer.valueOf(28))) {
            C0109b.m124b(parcel, 28, ccVar.ci(), true);
        }
        C0109b.m106C(parcel, d);
    }

    public cc[] m419Y(int i) {
        return new cc[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return m420y(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return m419Y(x0);
    }

    public cc m420y(Parcel parcel) {
        int c = C0108a.m77c(parcel);
        Set hashSet = new HashSet();
        int i = 0;
        String str = null;
        C0907a c0907a = null;
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        C0910b c0910b = null;
        String str4 = null;
        String str5 = null;
        int i3 = 0;
        String str6 = null;
        C0911c c0911c = null;
        boolean z = false;
        String str7 = null;
        C0912d c0912d = null;
        String str8 = null;
        int i4 = 0;
        List list = null;
        List list2 = null;
        int i5 = 0;
        int i6 = 0;
        String str9 = null;
        String str10 = null;
        List list3 = null;
        boolean z2 = false;
        while (parcel.dataPosition() < c) {
            int b = C0108a.m74b(parcel);
            switch (C0108a.m89m(b)) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    i = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    str = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    C0907a c0907a2 = (C0907a) C0108a.m71a(parcel, b, C0907a.CREATOR);
                    hashSet.add(Integer.valueOf(3));
                    c0907a = c0907a2;
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    str2 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(4));
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    str3 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(5));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    i2 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(6));
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    C0910b c0910b2 = (C0910b) C0108a.m71a(parcel, b, C0910b.CREATOR);
                    hashSet.add(Integer.valueOf(7));
                    c0910b = c0910b2;
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    str4 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(8));
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    str5 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(9));
                    break;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    i3 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(12));
                    break;
                case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                    str6 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(14));
                    break;
                case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                    C0911c c0911c2 = (C0911c) C0108a.m71a(parcel, b, C0911c.CREATOR);
                    hashSet.add(Integer.valueOf(15));
                    c0911c = c0911c2;
                    break;
                case Resource.USAGE_CONTEXT_FIELD_NUMBER /*16*/:
                    z = C0108a.m79c(parcel, b);
                    hashSet.add(Integer.valueOf(16));
                    break;
                case Resource.ENABLE_AUTO_EVENT_TRACKING_FIELD_NUMBER /*18*/:
                    str7 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(18));
                    break;
                case TimeUtils.HUNDRED_DAY_FIELD_LEN /*19*/:
                    C0912d c0912d2 = (C0912d) C0108a.m71a(parcel, b, C0912d.CREATOR);
                    hashSet.add(Integer.valueOf(19));
                    c0912d = c0912d2;
                    break;
                case 20:
                    str8 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(20));
                    break;
                case 21:
                    i4 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(21));
                    break;
                case 22:
                    list = C0108a.m78c(parcel, b, C0913f.CREATOR);
                    hashSet.add(Integer.valueOf(22));
                    break;
                case 23:
                    list2 = C0108a.m78c(parcel, b, C0914g.CREATOR);
                    hashSet.add(Integer.valueOf(23));
                    break;
                case DateTimeConstants.HOURS_PER_DAY /*24*/:
                    i5 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(24));
                    break;
                case 25:
                    i6 = C0108a.m82f(parcel, b);
                    hashSet.add(Integer.valueOf(25));
                    break;
                case 26:
                    str9 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(26));
                    break;
                case 27:
                    str10 = C0108a.m88l(parcel, b);
                    hashSet.add(Integer.valueOf(27));
                    break;
                case 28:
                    list3 = C0108a.m78c(parcel, b, C0915h.CREATOR);
                    hashSet.add(Integer.valueOf(28));
                    break;
                case 29:
                    z2 = C0108a.m79c(parcel, b);
                    hashSet.add(Integer.valueOf(29));
                    break;
                default:
                    C0108a.m75b(parcel, b);
                    break;
            }
        }
        if (parcel.dataPosition() == c) {
            return new cc(hashSet, i, str, c0907a, str2, str3, i2, c0910b, str4, str5, i3, str6, c0911c, z, str7, c0912d, str8, i4, list, list2, i5, i6, str9, str10, list3, z2);
        }
        throw new C0107a("Overread allowed size end=" + c, parcel);
    }
}
