package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.C0108a;
import com.google.android.gms.common.internal.safeparcel.C0108a.C0107a;
import com.google.android.gms.common.internal.safeparcel.C0109b;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0489a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.joda.time.MutableDateTime;

public class ak extends ae implements SafeParcelable {
    public static final al CREATOR;
    private final int ab;
    private final ah cB;
    private final Parcel cJ;
    private final int cK;
    private int cL;
    private int cM;
    private final String mClassName;

    static {
        CREATOR = new al();
    }

    ak(int i, Parcel parcel, ah ahVar) {
        this.ab = i;
        this.cJ = (Parcel) C0159s.m517d(parcel);
        this.cK = 2;
        this.cB = ahVar;
        if (this.cB == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.cB.aj();
        }
        this.cL = 2;
    }

    private ak(SafeParcelable safeParcelable, ah ahVar, String str) {
        this.ab = 1;
        this.cJ = Parcel.obtain();
        safeParcelable.writeToParcel(this.cJ, 0);
        this.cK = 1;
        this.cB = (ah) C0159s.m517d(ahVar);
        this.mClassName = (String) C0159s.m517d(str);
        this.cL = 2;
    }

    public static <T extends ae & SafeParcelable> ak m697a(T t) {
        String canonicalName = t.getClass().getCanonicalName();
        return new ak((SafeParcelable) t, m704b((ae) t), canonicalName);
    }

    public static HashMap<String, String> m698a(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    private static void m699a(ah ahVar, ae aeVar) {
        Class cls = aeVar.getClass();
        if (!ahVar.m694b(cls)) {
            HashMap T = aeVar.m177T();
            ahVar.m693a(cls, aeVar.m177T());
            for (String str : T.keySet()) {
                C0489a c0489a = (C0489a) T.get(str);
                Class ab = c0489a.ab();
                if (ab != null) {
                    try {
                        m699a(ahVar, (ae) ab.newInstance());
                    } catch (Throwable e) {
                        throw new IllegalStateException("Could not instantiate an object of type " + c0489a.ab().getCanonicalName(), e);
                    } catch (Throwable e2) {
                        throw new IllegalStateException("Could not access object of type " + c0489a.ab().getCanonicalName(), e2);
                    }
                }
            }
        }
    }

    private void m700a(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case MutableDateTime.ROUND_NONE /*0*/:
            case Value.TYPE_FIELD_NUMBER /*1*/:
            case Value.STRING_FIELD_NUMBER /*2*/:
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                stringBuilder.append(obj);
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                stringBuilder.append("\"").append(aq.m213r(obj.toString())).append("\"");
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                stringBuilder.append("\"").append(an.m209a((byte[]) obj)).append("\"");
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                stringBuilder.append("\"").append(an.m210b((byte[]) obj));
                stringBuilder.append("\"");
            case Value.ESCAPING_FIELD_NUMBER /*10*/:
                ar.m214a(stringBuilder, (HashMap) obj);
            case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private void m701a(StringBuilder stringBuilder, C0489a<?, ?> c0489a, Parcel parcel, int i) {
        switch (c0489a.m683S()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, Integer.valueOf(C0108a.m82f(parcel, i))));
            case Value.TYPE_FIELD_NUMBER /*1*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, C0108a.m84h(parcel, i)));
            case Value.STRING_FIELD_NUMBER /*2*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, Long.valueOf(C0108a.m83g(parcel, i))));
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, Float.valueOf(C0108a.m85i(parcel, i))));
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, Double.valueOf(C0108a.m86j(parcel, i))));
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, C0108a.m87k(parcel, i)));
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, Boolean.valueOf(C0108a.m79c(parcel, i))));
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, C0108a.m88l(parcel, i)));
            case Value.INTEGER_FIELD_NUMBER /*8*/:
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, C0108a.m92o(parcel, i)));
            case Value.ESCAPING_FIELD_NUMBER /*10*/:
                m707b(stringBuilder, (C0489a) c0489a, m180a(c0489a, m698a(C0108a.m91n(parcel, i))));
            case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + c0489a.m683S());
        }
    }

    private void m702a(StringBuilder stringBuilder, String str, C0489a<?, ?> c0489a, Parcel parcel, int i) {
        stringBuilder.append("\"").append(str).append("\":");
        if (c0489a.ad()) {
            m701a(stringBuilder, c0489a, parcel, i);
        } else {
            m706b(stringBuilder, c0489a, parcel, i);
        }
    }

    private void m703a(StringBuilder stringBuilder, HashMap<String, C0489a<?, ?>> hashMap, Parcel parcel) {
        HashMap b = m705b((HashMap) hashMap);
        stringBuilder.append('{');
        int c = C0108a.m77c(parcel);
        Object obj = null;
        while (parcel.dataPosition() < c) {
            int b2 = C0108a.m74b(parcel);
            Entry entry = (Entry) b.get(Integer.valueOf(C0108a.m89m(b2)));
            if (entry != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                m702a(stringBuilder, (String) entry.getKey(), (C0489a) entry.getValue(), parcel, b2);
                obj = 1;
            }
        }
        if (parcel.dataPosition() != c) {
            throw new C0107a("Overread allowed size end=" + c, parcel);
        }
        stringBuilder.append('}');
    }

    private static ah m704b(ae aeVar) {
        ah ahVar = new ah(aeVar.getClass());
        m699a(ahVar, aeVar);
        ahVar.ah();
        ahVar.ag();
        return ahVar;
    }

    private static HashMap<Integer, Entry<String, C0489a<?, ?>>> m705b(HashMap<String, C0489a<?, ?>> hashMap) {
        HashMap<Integer, Entry<String, C0489a<?, ?>>> hashMap2 = new HashMap();
        for (Entry entry : hashMap.entrySet()) {
            hashMap2.put(Integer.valueOf(((C0489a) entry.getValue()).aa()), entry);
        }
        return hashMap2;
    }

    private void m706b(StringBuilder stringBuilder, C0489a<?, ?> c0489a, Parcel parcel, int i) {
        if (c0489a.m686Y()) {
            stringBuilder.append("[");
            switch (c0489a.m683S()) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    am.m204a(stringBuilder, C0108a.m94q(parcel, i));
                    break;
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    am.m206a(stringBuilder, C0108a.m96s(parcel, i));
                    break;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    am.m205a(stringBuilder, C0108a.m95r(parcel, i));
                    break;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    am.m203a(stringBuilder, C0108a.m97t(parcel, i));
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    am.m202a(stringBuilder, C0108a.m98u(parcel, i));
                    break;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    am.m206a(stringBuilder, C0108a.m99v(parcel, i));
                    break;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    am.m208a(stringBuilder, C0108a.m93p(parcel, i));
                    break;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    am.m207a(stringBuilder, C0108a.m100w(parcel, i));
                    break;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    Parcel[] z = C0108a.m103z(parcel, i);
                    int length = z.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 > 0) {
                            stringBuilder.append(",");
                        }
                        z[i2].setDataPosition(0);
                        m703a(stringBuilder, c0489a.af(), z[i2]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (c0489a.m683S()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                stringBuilder.append(C0108a.m82f(parcel, i));
            case Value.TYPE_FIELD_NUMBER /*1*/:
                stringBuilder.append(C0108a.m84h(parcel, i));
            case Value.STRING_FIELD_NUMBER /*2*/:
                stringBuilder.append(C0108a.m83g(parcel, i));
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                stringBuilder.append(C0108a.m85i(parcel, i));
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                stringBuilder.append(C0108a.m86j(parcel, i));
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                stringBuilder.append(C0108a.m87k(parcel, i));
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                stringBuilder.append(C0108a.m79c(parcel, i));
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                stringBuilder.append("\"").append(aq.m213r(C0108a.m88l(parcel, i))).append("\"");
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                stringBuilder.append("\"").append(an.m209a(C0108a.m92o(parcel, i))).append("\"");
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                stringBuilder.append("\"").append(an.m210b(C0108a.m92o(parcel, i)));
                stringBuilder.append("\"");
            case Value.ESCAPING_FIELD_NUMBER /*10*/:
                Bundle n = C0108a.m91n(parcel, i);
                Set<String> keySet = n.keySet();
                keySet.size();
                stringBuilder.append("{");
                int i3 = 1;
                for (String str : keySet) {
                    if (i3 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(aq.m213r(n.getString(str))).append("\"");
                    i3 = 0;
                }
                stringBuilder.append("}");
            case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                Parcel y = C0108a.m102y(parcel, i);
                y.setDataPosition(0);
                m703a(stringBuilder, c0489a.af(), y);
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void m707b(StringBuilder stringBuilder, C0489a<?, ?> c0489a, Object obj) {
        if (c0489a.m685X()) {
            m708b(stringBuilder, (C0489a) c0489a, (ArrayList) obj);
        } else {
            m700a(stringBuilder, c0489a.m682R(), obj);
        }
    }

    private void m708b(StringBuilder stringBuilder, C0489a<?, ?> c0489a, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            m700a(stringBuilder, c0489a.m682R(), arrayList.get(i));
        }
        stringBuilder.append("]");
    }

    public HashMap<String, C0489a<?, ?>> m709T() {
        return this.cB == null ? null : this.cB.m696q(this.mClassName);
    }

    public Parcel al() {
        switch (this.cL) {
            case MutableDateTime.ROUND_NONE /*0*/:
                this.cM = C0109b.m127d(this.cJ);
                C0109b.m106C(this.cJ, this.cM);
                this.cL = 2;
                break;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                C0109b.m106C(this.cJ, this.cM);
                this.cL = 2;
                break;
        }
        return this.cJ;
    }

    ah am() {
        switch (this.cK) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return null;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return this.cB;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return this.cB;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.cK);
        }
    }

    public int describeContents() {
        al alVar = CREATOR;
        return 0;
    }

    public int m710i() {
        return this.ab;
    }

    protected Object m711m(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    protected boolean m712n(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public String toString() {
        C0159s.m514b(this.cB, (Object) "Cannot convert to JSON on client side.");
        Parcel al = al();
        al.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        m703a(stringBuilder, this.cB.m696q(this.mClassName), al);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        al alVar = CREATOR;
        al.m199a(this, out, flags);
    }
}
