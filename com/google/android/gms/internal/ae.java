package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ae {

    /* renamed from: com.google.android.gms.internal.ae.b */
    public interface C0128b<I, O> {
        int m172R();

        int m173S();

        I m174e(O o);
    }

    /* renamed from: com.google.android.gms.internal.ae.a */
    public static class C0489a<I, O> implements SafeParcelable {
        public static final af CREATOR;
        private final int ab;
        protected final String cA;
        private ah cB;
        private C0128b<I, O> cC;
        protected final int ct;
        protected final boolean cu;
        protected final int cv;
        protected final boolean cw;
        protected final String cx;
        protected final int cy;
        protected final Class<? extends ae> cz;

        static {
            CREATOR = new af();
        }

        C0489a(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, C0544z c0544z) {
            this.ab = i;
            this.ct = i2;
            this.cu = z;
            this.cv = i3;
            this.cw = z2;
            this.cx = str;
            this.cy = i4;
            if (str2 == null) {
                this.cz = null;
                this.cA = null;
            } else {
                this.cz = ak.class;
                this.cA = str2;
            }
            if (c0544z == null) {
                this.cC = null;
            } else {
                this.cC = c0544z.m1039P();
            }
        }

        protected C0489a(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends ae> cls, C0128b<I, O> c0128b) {
            this.ab = 1;
            this.ct = i;
            this.cu = z;
            this.cv = i2;
            this.cw = z2;
            this.cx = str;
            this.cy = i3;
            this.cz = cls;
            if (cls == null) {
                this.cA = null;
            } else {
                this.cA = cls.getCanonicalName();
            }
            this.cC = c0128b;
        }

        public static C0489a m673a(String str, int i, C0128b<?, ?> c0128b, boolean z) {
            return new C0489a(c0128b.m172R(), z, c0128b.m173S(), false, str, i, null, c0128b);
        }

        public static <T extends ae> C0489a<T, T> m674a(String str, int i, Class<T> cls) {
            return new C0489a(11, false, 11, false, str, i, cls, null);
        }

        public static <T extends ae> C0489a<ArrayList<T>, ArrayList<T>> m675b(String str, int i, Class<T> cls) {
            return new C0489a(11, true, 11, true, str, i, cls, null);
        }

        public static C0489a<Integer, Integer> m676c(String str, int i) {
            return new C0489a(0, false, 0, false, str, i, null, null);
        }

        public static C0489a<Double, Double> m678d(String str, int i) {
            return new C0489a(4, false, 4, false, str, i, null, null);
        }

        public static C0489a<Boolean, Boolean> m679e(String str, int i) {
            return new C0489a(6, false, 6, false, str, i, null, null);
        }

        public static C0489a<String, String> m680f(String str, int i) {
            return new C0489a(7, false, 7, false, str, i, null, null);
        }

        public static C0489a<ArrayList<String>, ArrayList<String>> m681g(String str, int i) {
            return new C0489a(7, true, 7, true, str, i, null, null);
        }

        public int m682R() {
            return this.ct;
        }

        public int m683S() {
            return this.cv;
        }

        public C0489a<I, O> m684W() {
            return new C0489a(this.ab, this.ct, this.cu, this.cv, this.cw, this.cx, this.cy, this.cA, ae());
        }

        public boolean m685X() {
            return this.cu;
        }

        public boolean m686Y() {
            return this.cw;
        }

        public String m687Z() {
            return this.cx;
        }

        public void m688a(ah ahVar) {
            this.cB = ahVar;
        }

        public int aa() {
            return this.cy;
        }

        public Class<? extends ae> ab() {
            return this.cz;
        }

        String ac() {
            return this.cA == null ? null : this.cA;
        }

        public boolean ad() {
            return this.cC != null;
        }

        C0544z ae() {
            return this.cC == null ? null : C0544z.m1037a(this.cC);
        }

        public HashMap<String, C0489a<?, ?>> af() {
            C0159s.m517d(this.cA);
            C0159s.m517d(this.cB);
            return this.cB.m696q(this.cA);
        }

        public int describeContents() {
            af afVar = CREATOR;
            return 0;
        }

        public I m689e(O o) {
            return this.cC.m174e(o);
        }

        public int m690i() {
            return this.ab;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Field\n");
            stringBuilder.append("            versionCode=").append(this.ab).append('\n');
            stringBuilder.append("                 typeIn=").append(this.ct).append('\n');
            stringBuilder.append("            typeInArray=").append(this.cu).append('\n');
            stringBuilder.append("                typeOut=").append(this.cv).append('\n');
            stringBuilder.append("           typeOutArray=").append(this.cw).append('\n');
            stringBuilder.append("        outputFieldName=").append(this.cx).append('\n');
            stringBuilder.append("      safeParcelFieldId=").append(this.cy).append('\n');
            stringBuilder.append("       concreteTypeName=").append(ac()).append('\n');
            if (ab() != null) {
                stringBuilder.append("     concreteType.class=").append(ab().getCanonicalName()).append('\n');
            }
            stringBuilder.append("          converterName=").append(this.cC == null ? "null" : this.cC.getClass().getCanonicalName()).append('\n');
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel out, int flags) {
            af afVar = CREATOR;
            af.m187a(this, out, flags);
        }
    }

    private void m175a(StringBuilder stringBuilder, C0489a c0489a, Object obj) {
        if (c0489a.m682R() == 11) {
            stringBuilder.append(((ae) c0489a.ab().cast(obj)).toString());
        } else if (c0489a.m682R() == 7) {
            stringBuilder.append("\"");
            stringBuilder.append(aq.m213r((String) obj));
            stringBuilder.append("\"");
        } else {
            stringBuilder.append(obj);
        }
    }

    private void m176a(StringBuilder stringBuilder, C0489a c0489a, ArrayList<Object> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            Object obj = arrayList.get(i);
            if (obj != null) {
                m175a(stringBuilder, c0489a, obj);
            }
        }
        stringBuilder.append("]");
    }

    public abstract HashMap<String, C0489a<?, ?>> m177T();

    public HashMap<String, Object> m178U() {
        return null;
    }

    public HashMap<String, Object> m179V() {
        return null;
    }

    protected <O, I> I m180a(C0489a<I, O> c0489a, Object obj) {
        return c0489a.cC != null ? c0489a.m689e(obj) : obj;
    }

    protected boolean m181a(C0489a c0489a) {
        return c0489a.m683S() == 11 ? c0489a.m686Y() ? m186p(c0489a.m687Z()) : m185o(c0489a.m687Z()) : m184n(c0489a.m687Z());
    }

    protected Object m182b(C0489a c0489a) {
        boolean z = true;
        String Z = c0489a.m687Z();
        if (c0489a.ab() == null) {
            return m183m(c0489a.m687Z());
        }
        if (m183m(c0489a.m687Z()) != null) {
            z = false;
        }
        C0159s.m512a(z, "Concrete field shouldn't be value object: " + c0489a.m687Z());
        Map V = c0489a.m686Y() ? m179V() : m178U();
        if (V != null) {
            return V.get(Z);
        }
        try {
            return getClass().getMethod("get" + Character.toUpperCase(Z.charAt(0)) + Z.substring(1), new Class[0]).invoke(this, new Object[0]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Object m183m(String str);

    protected abstract boolean m184n(String str);

    protected boolean m185o(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean m186p(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    public String toString() {
        HashMap T = m177T();
        StringBuilder stringBuilder = new StringBuilder(100);
        for (String str : T.keySet()) {
            C0489a c0489a = (C0489a) T.get(str);
            if (m181a(c0489a)) {
                Object a = m180a(c0489a, m182b(c0489a));
                if (stringBuilder.length() == 0) {
                    stringBuilder.append("{");
                } else {
                    stringBuilder.append(",");
                }
                stringBuilder.append("\"").append(str).append("\":");
                if (a != null) {
                    switch (c0489a.m683S()) {
                        case Value.INTEGER_FIELD_NUMBER /*8*/:
                            stringBuilder.append("\"").append(an.m209a((byte[]) a)).append("\"");
                            break;
                        case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                            stringBuilder.append("\"").append(an.m210b((byte[]) a)).append("\"");
                            break;
                        case Value.ESCAPING_FIELD_NUMBER /*10*/:
                            ar.m214a(stringBuilder, (HashMap) a);
                            break;
                        default:
                            if (!c0489a.m685X()) {
                                m175a(stringBuilder, c0489a, a);
                                break;
                            }
                            m176a(stringBuilder, c0489a, (ArrayList) a);
                            break;
                    }
                }
                stringBuilder.append("null");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append("}");
        } else {
            stringBuilder.append("{}");
        }
        return stringBuilder.toString();
    }
}
