package com.google.android.gms.internal;

import android.os.Parcel;
import android.support.v4.util.TimeUtils;
import com.adsdk.sdk.BannerAd;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0489a;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.AgeRange;
import com.google.android.gms.plus.model.people.Person.Cover;
import com.google.android.gms.plus.model.people.Person.Cover.CoverInfo;
import com.google.android.gms.plus.model.people.Person.Cover.CoverPhoto;
import com.google.android.gms.plus.model.people.Person.Emails;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;
import com.google.android.gms.plus.model.people.Person.Organizations;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.Person.Urls;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTimeConstants;

public final class cc extends ae implements SafeParcelable, Person {
    public static final cd CREATOR;
    private static final HashMap<String, C0489a<?, ?>> iC;
    private final int ab;
    private String cl;
    private final Set<Integer> iD;
    private String ie;
    private String jE;
    private C0907a jF;
    private String jG;
    private String jH;
    private int jI;
    private C0910b jJ;
    private String jK;
    private int jL;
    private C0911c jM;
    private boolean jN;
    private String jO;
    private C0912d jP;
    private String jQ;
    private int jR;
    private List<C0913f> jS;
    private List<C0914g> jT;
    private int jU;
    private int jV;
    private String jW;
    private List<C0915h> jX;
    private boolean jY;
    private String jh;

    /* renamed from: com.google.android.gms.internal.cc.e */
    public static class C0134e {
        public static int m417G(String str) {
            if (str.equals("person")) {
                return 0;
            }
            if (str.equals("page")) {
                return 1;
            }
            throw new IllegalArgumentException("Unknown objectType string: " + str);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.a */
    public static final class C0907a extends ae implements SafeParcelable, AgeRange {
        public static final ce CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private final int ab;
        private final Set<Integer> iD;
        private int jZ;
        private int ka;

        static {
            CREATOR = new ce();
            iC = new HashMap();
            iC.put("max", C0489a.m676c("max", 2));
            iC.put("min", C0489a.m676c("min", 3));
        }

        public C0907a() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        C0907a(Set<Integer> set, int i, int i2, int i3) {
            this.iD = set;
            this.ab = i;
            this.jZ = i2;
            this.ka = i3;
        }

        public HashMap<String, C0489a<?, ?>> m1311T() {
            return iC;
        }

        protected boolean m1312a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1313b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return Integer.valueOf(this.jZ);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return Integer.valueOf(this.ka);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        public C0907a ck() {
            return this;
        }

        public int describeContents() {
            ce ceVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0907a)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0907a c0907a = (C0907a) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1312a(c0489a)) {
                    if (!c0907a.m1312a(c0489a)) {
                        return false;
                    }
                    if (!m1313b(c0489a).equals(c0907a.m1313b(c0489a))) {
                        return false;
                    }
                } else if (c0907a.m1312a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return ck();
        }

        public int getMax() {
            return this.jZ;
        }

        public int getMin() {
            return this.ka;
        }

        public boolean hasMax() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public boolean hasMin() {
            return this.iD.contains(Integer.valueOf(3));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1312a(c0489a)) {
                    hashCode = m1313b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1314i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        protected Object m1315m(String str) {
            return null;
        }

        protected boolean m1316n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            ce ceVar = CREATOR;
            ce.m421a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.b */
    public static final class C0910b extends ae implements SafeParcelable, Cover {
        public static final cf CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private final int ab;
        private final Set<Integer> iD;
        private C0908a kb;
        private C0909b kc;
        private int kd;

        /* renamed from: com.google.android.gms.internal.cc.b.a */
        public static final class C0908a extends ae implements SafeParcelable, CoverInfo {
            public static final cg CREATOR;
            private static final HashMap<String, C0489a<?, ?>> iC;
            private final int ab;
            private final Set<Integer> iD;
            private int ke;
            private int kf;

            static {
                CREATOR = new cg();
                iC = new HashMap();
                iC.put("leftImageOffset", C0489a.m676c("leftImageOffset", 2));
                iC.put("topImageOffset", C0489a.m676c("topImageOffset", 3));
            }

            public C0908a() {
                this.ab = 1;
                this.iD = new HashSet();
            }

            C0908a(Set<Integer> set, int i, int i2, int i3) {
                this.iD = set;
                this.ab = i;
                this.ke = i2;
                this.kf = i3;
            }

            public HashMap<String, C0489a<?, ?>> m1317T() {
                return iC;
            }

            protected boolean m1318a(C0489a c0489a) {
                return this.iD.contains(Integer.valueOf(c0489a.aa()));
            }

            protected Object m1319b(C0489a c0489a) {
                switch (c0489a.aa()) {
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        return Integer.valueOf(this.ke);
                    case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                        return Integer.valueOf(this.kf);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
                }
            }

            Set<Integer> bH() {
                return this.iD;
            }

            public C0908a co() {
                return this;
            }

            public int describeContents() {
                cg cgVar = CREATOR;
                return 0;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C0908a)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0908a c0908a = (C0908a) obj;
                for (C0489a c0489a : iC.values()) {
                    if (m1318a(c0489a)) {
                        if (!c0908a.m1318a(c0489a)) {
                            return false;
                        }
                        if (!m1319b(c0489a).equals(c0908a.m1319b(c0489a))) {
                            return false;
                        }
                    } else if (c0908a.m1318a(c0489a)) {
                        return false;
                    }
                }
                return true;
            }

            public /* synthetic */ Object freeze() {
                return co();
            }

            public int getLeftImageOffset() {
                return this.ke;
            }

            public int getTopImageOffset() {
                return this.kf;
            }

            public boolean hasLeftImageOffset() {
                return this.iD.contains(Integer.valueOf(2));
            }

            public boolean hasTopImageOffset() {
                return this.iD.contains(Integer.valueOf(3));
            }

            public int hashCode() {
                int i = 0;
                for (C0489a c0489a : iC.values()) {
                    int hashCode;
                    if (m1318a(c0489a)) {
                        hashCode = m1319b(c0489a).hashCode() + (i + c0489a.aa());
                    } else {
                        hashCode = i;
                    }
                    i = hashCode;
                }
                return i;
            }

            int m1320i() {
                return this.ab;
            }

            public boolean isDataValid() {
                return true;
            }

            protected Object m1321m(String str) {
                return null;
            }

            protected boolean m1322n(String str) {
                return false;
            }

            public void writeToParcel(Parcel out, int flags) {
                cg cgVar = CREATOR;
                cg.m426a(this, out, flags);
            }
        }

        /* renamed from: com.google.android.gms.internal.cc.b.b */
        public static final class C0909b extends ae implements SafeParcelable, CoverPhoto {
            public static final ch CREATOR;
            private static final HashMap<String, C0489a<?, ?>> iC;
            private final int ab;
            private int hL;
            private int hM;
            private final Set<Integer> iD;
            private String ie;

            static {
                CREATOR = new ch();
                iC = new HashMap();
                iC.put("height", C0489a.m676c("height", 2));
                iC.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0489a.m680f(PlusShare.KEY_CALL_TO_ACTION_URL, 3));
                iC.put("width", C0489a.m676c("width", 4));
            }

            public C0909b() {
                this.ab = 1;
                this.iD = new HashSet();
            }

            C0909b(Set<Integer> set, int i, int i2, String str, int i3) {
                this.iD = set;
                this.ab = i;
                this.hM = i2;
                this.ie = str;
                this.hL = i3;
            }

            public HashMap<String, C0489a<?, ?>> m1323T() {
                return iC;
            }

            protected boolean m1324a(C0489a c0489a) {
                return this.iD.contains(Integer.valueOf(c0489a.aa()));
            }

            protected Object m1325b(C0489a c0489a) {
                switch (c0489a.aa()) {
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        return Integer.valueOf(this.hM);
                    case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                        return this.ie;
                    case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                        return Integer.valueOf(this.hL);
                    default:
                        throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
                }
            }

            Set<Integer> bH() {
                return this.iD;
            }

            public C0909b cp() {
                return this;
            }

            public int describeContents() {
                ch chVar = CREATOR;
                return 0;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof C0909b)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                C0909b c0909b = (C0909b) obj;
                for (C0489a c0489a : iC.values()) {
                    if (m1324a(c0489a)) {
                        if (!c0909b.m1324a(c0489a)) {
                            return false;
                        }
                        if (!m1325b(c0489a).equals(c0909b.m1325b(c0489a))) {
                            return false;
                        }
                    } else if (c0909b.m1324a(c0489a)) {
                        return false;
                    }
                }
                return true;
            }

            public /* synthetic */ Object freeze() {
                return cp();
            }

            public int getHeight() {
                return this.hM;
            }

            public String getUrl() {
                return this.ie;
            }

            public int getWidth() {
                return this.hL;
            }

            public boolean hasHeight() {
                return this.iD.contains(Integer.valueOf(2));
            }

            public boolean hasUrl() {
                return this.iD.contains(Integer.valueOf(3));
            }

            public boolean hasWidth() {
                return this.iD.contains(Integer.valueOf(4));
            }

            public int hashCode() {
                int i = 0;
                for (C0489a c0489a : iC.values()) {
                    int hashCode;
                    if (m1324a(c0489a)) {
                        hashCode = m1325b(c0489a).hashCode() + (i + c0489a.aa());
                    } else {
                        hashCode = i;
                    }
                    i = hashCode;
                }
                return i;
            }

            int m1326i() {
                return this.ab;
            }

            public boolean isDataValid() {
                return true;
            }

            protected Object m1327m(String str) {
                return null;
            }

            protected boolean m1328n(String str) {
                return false;
            }

            public void writeToParcel(Parcel out, int flags) {
                ch chVar = CREATOR;
                ch.m428a(this, out, flags);
            }
        }

        static {
            CREATOR = new cf();
            iC = new HashMap();
            iC.put("coverInfo", C0489a.m674a("coverInfo", 2, C0908a.class));
            iC.put("coverPhoto", C0489a.m674a("coverPhoto", 3, C0909b.class));
            iC.put("layout", C0489a.m673a("layout", 4, new ab().m670b("banner", 0), false));
        }

        public C0910b() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        C0910b(Set<Integer> set, int i, C0908a c0908a, C0909b c0909b, int i2) {
            this.iD = set;
            this.ab = i;
            this.kb = c0908a;
            this.kc = c0909b;
            this.kd = i2;
        }

        public HashMap<String, C0489a<?, ?>> m1329T() {
            return iC;
        }

        protected boolean m1330a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1331b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return this.kb;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return this.kc;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return Integer.valueOf(this.kd);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        C0908a cl() {
            return this.kb;
        }

        C0909b cm() {
            return this.kc;
        }

        public C0910b cn() {
            return this;
        }

        public int describeContents() {
            cf cfVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0910b)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0910b c0910b = (C0910b) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1330a(c0489a)) {
                    if (!c0910b.m1330a(c0489a)) {
                        return false;
                    }
                    if (!m1331b(c0489a).equals(c0910b.m1331b(c0489a))) {
                        return false;
                    }
                } else if (c0910b.m1330a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return cn();
        }

        public CoverInfo getCoverInfo() {
            return this.kb;
        }

        public CoverPhoto getCoverPhoto() {
            return this.kc;
        }

        public int getLayout() {
            return this.kd;
        }

        public boolean hasCoverInfo() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public boolean hasCoverPhoto() {
            return this.iD.contains(Integer.valueOf(3));
        }

        public boolean hasLayout() {
            return this.iD.contains(Integer.valueOf(4));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1330a(c0489a)) {
                    hashCode = m1331b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1332i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        protected Object m1333m(String str) {
            return null;
        }

        protected boolean m1334n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            cf cfVar = CREATOR;
            cf.m424a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.c */
    public static final class C0911c extends ae implements SafeParcelable, Image {
        public static final ci CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private final int ab;
        private final Set<Integer> iD;
        private String ie;

        static {
            CREATOR = new ci();
            iC = new HashMap();
            iC.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0489a.m680f(PlusShare.KEY_CALL_TO_ACTION_URL, 2));
        }

        public C0911c() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        public C0911c(String str) {
            this.iD = new HashSet();
            this.ab = 1;
            this.ie = str;
            this.iD.add(Integer.valueOf(2));
        }

        C0911c(Set<Integer> set, int i, String str) {
            this.iD = set;
            this.ab = i;
            this.ie = str;
        }

        public HashMap<String, C0489a<?, ?>> m1335T() {
            return iC;
        }

        protected boolean m1336a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1337b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return this.ie;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        public C0911c cq() {
            return this;
        }

        public int describeContents() {
            ci ciVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0911c)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0911c c0911c = (C0911c) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1336a(c0489a)) {
                    if (!c0911c.m1336a(c0489a)) {
                        return false;
                    }
                    if (!m1337b(c0489a).equals(c0911c.m1337b(c0489a))) {
                        return false;
                    }
                } else if (c0911c.m1336a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return cq();
        }

        public String getUrl() {
            return this.ie;
        }

        public boolean hasUrl() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1336a(c0489a)) {
                    hashCode = m1337b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1338i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        protected Object m1339m(String str) {
            return null;
        }

        protected boolean m1340n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            ci ciVar = CREATOR;
            ci.m430a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.d */
    public static final class C0912d extends ae implements SafeParcelable, Name {
        public static final cj CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private final int ab;
        private final Set<Integer> iD;
        private String jc;
        private String jf;
        private String kg;
        private String kh;
        private String ki;
        private String kj;

        static {
            CREATOR = new cj();
            iC = new HashMap();
            iC.put("familyName", C0489a.m680f("familyName", 2));
            iC.put("formatted", C0489a.m680f("formatted", 3));
            iC.put("givenName", C0489a.m680f("givenName", 4));
            iC.put("honorificPrefix", C0489a.m680f("honorificPrefix", 5));
            iC.put("honorificSuffix", C0489a.m680f("honorificSuffix", 6));
            iC.put("middleName", C0489a.m680f("middleName", 7));
        }

        public C0912d() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        C0912d(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, String str6) {
            this.iD = set;
            this.ab = i;
            this.jc = str;
            this.kg = str2;
            this.jf = str3;
            this.kh = str4;
            this.ki = str5;
            this.kj = str6;
        }

        public HashMap<String, C0489a<?, ?>> m1341T() {
            return iC;
        }

        protected boolean m1342a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1343b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return this.jc;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return this.kg;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return this.jf;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return this.kh;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return this.ki;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return this.kj;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        public C0912d cr() {
            return this;
        }

        public int describeContents() {
            cj cjVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0912d)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0912d c0912d = (C0912d) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1342a(c0489a)) {
                    if (!c0912d.m1342a(c0489a)) {
                        return false;
                    }
                    if (!m1343b(c0489a).equals(c0912d.m1343b(c0489a))) {
                        return false;
                    }
                } else if (c0912d.m1342a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return cr();
        }

        public String getFamilyName() {
            return this.jc;
        }

        public String getFormatted() {
            return this.kg;
        }

        public String getGivenName() {
            return this.jf;
        }

        public String getHonorificPrefix() {
            return this.kh;
        }

        public String getHonorificSuffix() {
            return this.ki;
        }

        public String getMiddleName() {
            return this.kj;
        }

        public boolean hasFamilyName() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public boolean hasFormatted() {
            return this.iD.contains(Integer.valueOf(3));
        }

        public boolean hasGivenName() {
            return this.iD.contains(Integer.valueOf(4));
        }

        public boolean hasHonorificPrefix() {
            return this.iD.contains(Integer.valueOf(5));
        }

        public boolean hasHonorificSuffix() {
            return this.iD.contains(Integer.valueOf(6));
        }

        public boolean hasMiddleName() {
            return this.iD.contains(Integer.valueOf(7));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1342a(c0489a)) {
                    hashCode = m1343b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1344i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        protected Object m1345m(String str) {
            return null;
        }

        protected boolean m1346n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            cj cjVar = CREATOR;
            cj.m432a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.f */
    public static final class C0913f extends ae implements SafeParcelable, Organizations {
        public static final ck CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private int aJ;
        private final int ab;
        private String di;
        private String hs;
        private final Set<Integer> iD;
        private String jb;
        private String js;
        private String kk;
        private String kl;
        private boolean km;
        private String mName;

        static {
            CREATOR = new ck();
            iC = new HashMap();
            iC.put("department", C0489a.m680f("department", 2));
            iC.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, C0489a.m680f(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 3));
            iC.put("endDate", C0489a.m680f("endDate", 4));
            iC.put("location", C0489a.m680f("location", 5));
            iC.put("name", C0489a.m680f("name", 6));
            iC.put("primary", C0489a.m679e("primary", 7));
            iC.put("startDate", C0489a.m680f("startDate", 8));
            iC.put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, C0489a.m680f(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, 9));
            iC.put("type", C0489a.m673a("type", 10, new ab().m670b("work", 0).m670b("school", 1), false));
        }

        public C0913f() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        C0913f(Set<Integer> set, int i, String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7, int i2) {
            this.iD = set;
            this.ab = i;
            this.kk = str;
            this.di = str2;
            this.jb = str3;
            this.kl = str4;
            this.mName = str5;
            this.km = z;
            this.js = str6;
            this.hs = str7;
            this.aJ = i2;
        }

        public HashMap<String, C0489a<?, ?>> m1347T() {
            return iC;
        }

        protected boolean m1348a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1349b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return this.kk;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return this.di;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return this.jb;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return this.kl;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return this.mName;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return Boolean.valueOf(this.km);
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return this.js;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    return this.hs;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    return Integer.valueOf(this.aJ);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        public C0913f cs() {
            return this;
        }

        public int describeContents() {
            ck ckVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0913f)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0913f c0913f = (C0913f) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1348a(c0489a)) {
                    if (!c0913f.m1348a(c0489a)) {
                        return false;
                    }
                    if (!m1349b(c0489a).equals(c0913f.m1349b(c0489a))) {
                        return false;
                    }
                } else if (c0913f.m1348a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return cs();
        }

        public String getDepartment() {
            return this.kk;
        }

        public String getDescription() {
            return this.di;
        }

        public String getEndDate() {
            return this.jb;
        }

        public String getLocation() {
            return this.kl;
        }

        public String getName() {
            return this.mName;
        }

        public String getStartDate() {
            return this.js;
        }

        public String getTitle() {
            return this.hs;
        }

        public int getType() {
            return this.aJ;
        }

        public boolean hasDepartment() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public boolean hasDescription() {
            return this.iD.contains(Integer.valueOf(3));
        }

        public boolean hasEndDate() {
            return this.iD.contains(Integer.valueOf(4));
        }

        public boolean hasLocation() {
            return this.iD.contains(Integer.valueOf(5));
        }

        public boolean hasName() {
            return this.iD.contains(Integer.valueOf(6));
        }

        public boolean hasPrimary() {
            return this.iD.contains(Integer.valueOf(7));
        }

        public boolean hasStartDate() {
            return this.iD.contains(Integer.valueOf(8));
        }

        public boolean hasTitle() {
            return this.iD.contains(Integer.valueOf(9));
        }

        public boolean hasType() {
            return this.iD.contains(Integer.valueOf(10));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1348a(c0489a)) {
                    hashCode = m1349b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1350i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.km;
        }

        protected Object m1351m(String str) {
            return null;
        }

        protected boolean m1352n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            ck ckVar = CREATOR;
            ck.m434a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.g */
    public static final class C0914g extends ae implements SafeParcelable, PlacesLived {
        public static final cl CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private final int ab;
        private final Set<Integer> iD;
        private boolean km;
        private String mValue;

        static {
            CREATOR = new cl();
            iC = new HashMap();
            iC.put("primary", C0489a.m679e("primary", 2));
            iC.put("value", C0489a.m680f("value", 3));
        }

        public C0914g() {
            this.ab = 1;
            this.iD = new HashSet();
        }

        C0914g(Set<Integer> set, int i, boolean z, String str) {
            this.iD = set;
            this.ab = i;
            this.km = z;
            this.mValue = str;
        }

        public HashMap<String, C0489a<?, ?>> m1353T() {
            return iC;
        }

        protected boolean m1354a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1355b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return Boolean.valueOf(this.km);
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return this.mValue;
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        public C0914g ct() {
            return this;
        }

        public int describeContents() {
            cl clVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0914g)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0914g c0914g = (C0914g) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1354a(c0489a)) {
                    if (!c0914g.m1354a(c0489a)) {
                        return false;
                    }
                    if (!m1355b(c0489a).equals(c0914g.m1355b(c0489a))) {
                        return false;
                    }
                } else if (c0914g.m1354a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return ct();
        }

        public String getValue() {
            return this.mValue;
        }

        public boolean hasPrimary() {
            return this.iD.contains(Integer.valueOf(2));
        }

        public boolean hasValue() {
            return this.iD.contains(Integer.valueOf(3));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1354a(c0489a)) {
                    hashCode = m1355b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1356i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        public boolean isPrimary() {
            return this.km;
        }

        protected Object m1357m(String str) {
            return null;
        }

        protected boolean m1358n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            cl clVar = CREATOR;
            cl.m436a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.cc.h */
    public static final class C0915h extends ae implements SafeParcelable, Urls {
        public static final cm CREATOR;
        private static final HashMap<String, C0489a<?, ?>> iC;
        private int aJ;
        private final int ab;
        private final Set<Integer> iD;
        private String kn;
        private final int ko;
        private String mValue;

        static {
            CREATOR = new cm();
            iC = new HashMap();
            iC.put(PlusShare.KEY_CALL_TO_ACTION_LABEL, C0489a.m680f(PlusShare.KEY_CALL_TO_ACTION_LABEL, 5));
            iC.put("type", C0489a.m673a("type", 6, new ab().m670b("home", 0).m670b("work", 1).m670b("blog", 2).m670b("profile", 3).m670b(BannerAd.OTHER, 4).m670b("otherProfile", 5).m670b("contributor", 6).m670b("website", 7), false));
            iC.put("value", C0489a.m680f("value", 4));
        }

        public C0915h() {
            this.ko = 4;
            this.ab = 2;
            this.iD = new HashSet();
        }

        C0915h(Set<Integer> set, int i, String str, int i2, String str2, int i3) {
            this.ko = 4;
            this.iD = set;
            this.ab = i;
            this.kn = str;
            this.aJ = i2;
            this.mValue = str2;
        }

        public HashMap<String, C0489a<?, ?>> m1359T() {
            return iC;
        }

        protected boolean m1360a(C0489a c0489a) {
            return this.iD.contains(Integer.valueOf(c0489a.aa()));
        }

        protected Object m1361b(C0489a c0489a) {
            switch (c0489a.aa()) {
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return this.mValue;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return this.kn;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return Integer.valueOf(this.aJ);
                default:
                    throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
            }
        }

        Set<Integer> bH() {
            return this.iD;
        }

        @Deprecated
        public int cu() {
            return 4;
        }

        public C0915h cv() {
            return this;
        }

        public int describeContents() {
            cm cmVar = CREATOR;
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof C0915h)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            C0915h c0915h = (C0915h) obj;
            for (C0489a c0489a : iC.values()) {
                if (m1360a(c0489a)) {
                    if (!c0915h.m1360a(c0489a)) {
                        return false;
                    }
                    if (!m1361b(c0489a).equals(c0915h.m1361b(c0489a))) {
                        return false;
                    }
                } else if (c0915h.m1360a(c0489a)) {
                    return false;
                }
            }
            return true;
        }

        public /* synthetic */ Object freeze() {
            return cv();
        }

        public String getLabel() {
            return this.kn;
        }

        public int getType() {
            return this.aJ;
        }

        public String getValue() {
            return this.mValue;
        }

        public boolean hasLabel() {
            return this.iD.contains(Integer.valueOf(5));
        }

        public boolean hasType() {
            return this.iD.contains(Integer.valueOf(6));
        }

        public boolean hasValue() {
            return this.iD.contains(Integer.valueOf(4));
        }

        public int hashCode() {
            int i = 0;
            for (C0489a c0489a : iC.values()) {
                int hashCode;
                if (m1360a(c0489a)) {
                    hashCode = m1361b(c0489a).hashCode() + (i + c0489a.aa());
                } else {
                    hashCode = i;
                }
                i = hashCode;
            }
            return i;
        }

        int m1362i() {
            return this.ab;
        }

        public boolean isDataValid() {
            return true;
        }

        protected Object m1363m(String str) {
            return null;
        }

        protected boolean m1364n(String str) {
            return false;
        }

        public void writeToParcel(Parcel out, int flags) {
            cm cmVar = CREATOR;
            cm.m438a(this, out, flags);
        }
    }

    static {
        CREATOR = new cd();
        iC = new HashMap();
        iC.put("aboutMe", C0489a.m680f("aboutMe", 2));
        iC.put("ageRange", C0489a.m674a("ageRange", 3, C0907a.class));
        iC.put("birthday", C0489a.m680f("birthday", 4));
        iC.put("braggingRights", C0489a.m680f("braggingRights", 5));
        iC.put("circledByCount", C0489a.m676c("circledByCount", 6));
        iC.put("cover", C0489a.m674a("cover", 7, C0910b.class));
        iC.put("currentLocation", C0489a.m680f("currentLocation", 8));
        iC.put("displayName", C0489a.m680f("displayName", 9));
        iC.put("gender", C0489a.m673a("gender", 12, new ab().m670b("male", 0).m670b("female", 1).m670b(BannerAd.OTHER, 2), false));
        iC.put("id", C0489a.m680f("id", 14));
        iC.put("image", C0489a.m674a("image", 15, C0911c.class));
        iC.put("isPlusUser", C0489a.m679e("isPlusUser", 16));
        iC.put("language", C0489a.m680f("language", 18));
        iC.put("name", C0489a.m674a("name", 19, C0912d.class));
        iC.put("nickname", C0489a.m680f("nickname", 20));
        iC.put("objectType", C0489a.m673a("objectType", 21, new ab().m670b("person", 0).m670b("page", 1), false));
        iC.put("organizations", C0489a.m675b("organizations", 22, C0913f.class));
        iC.put("placesLived", C0489a.m675b("placesLived", 23, C0914g.class));
        iC.put("plusOneCount", C0489a.m676c("plusOneCount", 24));
        iC.put("relationshipStatus", C0489a.m673a("relationshipStatus", 25, new ab().m670b("single", 0).m670b("in_a_relationship", 1).m670b("engaged", 2).m670b("married", 3).m670b("its_complicated", 4).m670b("open_relationship", 5).m670b("widowed", 6).m670b("in_domestic_partnership", 7).m670b("in_civil_union", 8), false));
        iC.put("tagline", C0489a.m680f("tagline", 26));
        iC.put(PlusShare.KEY_CALL_TO_ACTION_URL, C0489a.m680f(PlusShare.KEY_CALL_TO_ACTION_URL, 27));
        iC.put("urls", C0489a.m675b("urls", 28, C0915h.class));
        iC.put("verified", C0489a.m679e("verified", 29));
    }

    public cc() {
        this.ab = 2;
        this.iD = new HashSet();
    }

    public cc(String str, String str2, C0911c c0911c, int i, String str3) {
        this.ab = 2;
        this.iD = new HashSet();
        this.cl = str;
        this.iD.add(Integer.valueOf(9));
        this.jh = str2;
        this.iD.add(Integer.valueOf(14));
        this.jM = c0911c;
        this.iD.add(Integer.valueOf(15));
        this.jR = i;
        this.iD.add(Integer.valueOf(21));
        this.ie = str3;
        this.iD.add(Integer.valueOf(27));
    }

    cc(Set<Integer> set, int i, String str, C0907a c0907a, String str2, String str3, int i2, C0910b c0910b, String str4, String str5, int i3, String str6, C0911c c0911c, boolean z, String str7, C0912d c0912d, String str8, int i4, List<C0913f> list, List<C0914g> list2, int i5, int i6, String str9, String str10, List<C0915h> list3, boolean z2) {
        this.iD = set;
        this.ab = i;
        this.jE = str;
        this.jF = c0907a;
        this.jG = str2;
        this.jH = str3;
        this.jI = i2;
        this.jJ = c0910b;
        this.jK = str4;
        this.cl = str5;
        this.jL = i3;
        this.jh = str6;
        this.jM = c0911c;
        this.jN = z;
        this.jO = str7;
        this.jP = c0912d;
        this.jQ = str8;
        this.jR = i4;
        this.jS = list;
        this.jT = list2;
        this.jU = i5;
        this.jV = i6;
        this.jW = str9;
        this.ie = str10;
        this.jX = list3;
        this.jY = z2;
    }

    public static cc m1365d(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        cc y = CREATOR.m420y(obtain);
        obtain.recycle();
        return y;
    }

    public HashMap<String, C0489a<?, ?>> m1366T() {
        return iC;
    }

    protected boolean m1367a(C0489a c0489a) {
        return this.iD.contains(Integer.valueOf(c0489a.aa()));
    }

    protected Object m1368b(C0489a c0489a) {
        switch (c0489a.aa()) {
            case Value.STRING_FIELD_NUMBER /*2*/:
                return this.jE;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return this.jF;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return this.jG;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return this.jH;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return Integer.valueOf(this.jI);
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                return this.jJ;
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                return this.jK;
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                return this.cl;
            case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                return Integer.valueOf(this.jL);
            case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                return this.jh;
            case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                return this.jM;
            case Resource.USAGE_CONTEXT_FIELD_NUMBER /*16*/:
                return Boolean.valueOf(this.jN);
            case Resource.ENABLE_AUTO_EVENT_TRACKING_FIELD_NUMBER /*18*/:
                return this.jO;
            case TimeUtils.HUNDRED_DAY_FIELD_LEN /*19*/:
                return this.jP;
            case 20:
                return this.jQ;
            case 21:
                return Integer.valueOf(this.jR);
            case 22:
                return this.jS;
            case 23:
                return this.jT;
            case DateTimeConstants.HOURS_PER_DAY /*24*/:
                return Integer.valueOf(this.jU);
            case 25:
                return Integer.valueOf(this.jV);
            case 26:
                return this.jW;
            case 27:
                return this.ie;
            case 28:
                return this.jX;
            case 29:
                return Boolean.valueOf(this.jY);
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
        }
    }

    Set<Integer> bH() {
        return this.iD;
    }

    C0907a cc() {
        return this.jF;
    }

    C0910b cd() {
        return this.jJ;
    }

    C0911c ce() {
        return this.jM;
    }

    C0912d cf() {
        return this.jP;
    }

    List<C0913f> cg() {
        return this.jS;
    }

    List<C0914g> ch() {
        return this.jT;
    }

    List<C0915h> ci() {
        return this.jX;
    }

    public cc cj() {
        return this;
    }

    public int describeContents() {
        cd cdVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof cc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        cc ccVar = (cc) obj;
        for (C0489a c0489a : iC.values()) {
            if (m1367a(c0489a)) {
                if (!ccVar.m1367a(c0489a)) {
                    return false;
                }
                if (!m1368b(c0489a).equals(ccVar.m1368b(c0489a))) {
                    return false;
                }
            } else if (ccVar.m1367a(c0489a)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return cj();
    }

    public String getAboutMe() {
        return this.jE;
    }

    public AgeRange getAgeRange() {
        return this.jF;
    }

    public String getBirthday() {
        return this.jG;
    }

    public String getBraggingRights() {
        return this.jH;
    }

    public int getCircledByCount() {
        return this.jI;
    }

    public Cover getCover() {
        return this.jJ;
    }

    public String getCurrentLocation() {
        return this.jK;
    }

    public String getDisplayName() {
        return this.cl;
    }

    @Deprecated
    public List<Emails> getEmails() {
        return null;
    }

    public int getGender() {
        return this.jL;
    }

    public String getId() {
        return this.jh;
    }

    public Image getImage() {
        return this.jM;
    }

    public String getLanguage() {
        return this.jO;
    }

    public Name getName() {
        return this.jP;
    }

    public String getNickname() {
        return this.jQ;
    }

    public int getObjectType() {
        return this.jR;
    }

    public List<Organizations> getOrganizations() {
        return (ArrayList) this.jS;
    }

    public List<PlacesLived> getPlacesLived() {
        return (ArrayList) this.jT;
    }

    public int getPlusOneCount() {
        return this.jU;
    }

    public int getRelationshipStatus() {
        return this.jV;
    }

    public String getTagline() {
        return this.jW;
    }

    public String getUrl() {
        return this.ie;
    }

    public List<Urls> getUrls() {
        return (ArrayList) this.jX;
    }

    public boolean hasAboutMe() {
        return this.iD.contains(Integer.valueOf(2));
    }

    public boolean hasAgeRange() {
        return this.iD.contains(Integer.valueOf(3));
    }

    public boolean hasBirthday() {
        return this.iD.contains(Integer.valueOf(4));
    }

    public boolean hasBraggingRights() {
        return this.iD.contains(Integer.valueOf(5));
    }

    public boolean hasCircledByCount() {
        return this.iD.contains(Integer.valueOf(6));
    }

    public boolean hasCover() {
        return this.iD.contains(Integer.valueOf(7));
    }

    public boolean hasCurrentLocation() {
        return this.iD.contains(Integer.valueOf(8));
    }

    public boolean hasDisplayName() {
        return this.iD.contains(Integer.valueOf(9));
    }

    @Deprecated
    public boolean hasEmails() {
        return false;
    }

    public boolean hasGender() {
        return this.iD.contains(Integer.valueOf(12));
    }

    public boolean hasId() {
        return this.iD.contains(Integer.valueOf(14));
    }

    public boolean hasImage() {
        return this.iD.contains(Integer.valueOf(15));
    }

    public boolean hasIsPlusUser() {
        return this.iD.contains(Integer.valueOf(16));
    }

    public boolean hasLanguage() {
        return this.iD.contains(Integer.valueOf(18));
    }

    public boolean hasName() {
        return this.iD.contains(Integer.valueOf(19));
    }

    public boolean hasNickname() {
        return this.iD.contains(Integer.valueOf(20));
    }

    public boolean hasObjectType() {
        return this.iD.contains(Integer.valueOf(21));
    }

    public boolean hasOrganizations() {
        return this.iD.contains(Integer.valueOf(22));
    }

    public boolean hasPlacesLived() {
        return this.iD.contains(Integer.valueOf(23));
    }

    public boolean hasPlusOneCount() {
        return this.iD.contains(Integer.valueOf(24));
    }

    public boolean hasRelationshipStatus() {
        return this.iD.contains(Integer.valueOf(25));
    }

    public boolean hasTagline() {
        return this.iD.contains(Integer.valueOf(26));
    }

    public boolean hasUrl() {
        return this.iD.contains(Integer.valueOf(27));
    }

    public boolean hasUrls() {
        return this.iD.contains(Integer.valueOf(28));
    }

    public boolean hasVerified() {
        return this.iD.contains(Integer.valueOf(29));
    }

    public int hashCode() {
        int i = 0;
        for (C0489a c0489a : iC.values()) {
            int hashCode;
            if (m1367a(c0489a)) {
                hashCode = m1368b(c0489a).hashCode() + (i + c0489a.aa());
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    int m1369i() {
        return this.ab;
    }

    public boolean isDataValid() {
        return true;
    }

    public boolean isPlusUser() {
        return this.jN;
    }

    public boolean isVerified() {
        return this.jY;
    }

    protected Object m1370m(String str) {
        return null;
    }

    protected boolean m1371n(String str) {
        return false;
    }

    public void writeToParcel(Parcel out, int flags) {
        cd cdVar = CREATOR;
        cd.m418a(this, out, flags);
    }
}
