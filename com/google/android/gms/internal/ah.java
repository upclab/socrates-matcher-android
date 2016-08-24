package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0489a;
import java.util.ArrayList;
import java.util.HashMap;

public class ah implements SafeParcelable {
    public static final ai CREATOR;
    private final int ab;
    private final HashMap<String, HashMap<String, C0489a<?, ?>>> cD;
    private final ArrayList<C0490a> cE;
    private final String cF;

    /* renamed from: com.google.android.gms.internal.ah.a */
    public static class C0490a implements SafeParcelable {
        public static final aj CREATOR;
        final ArrayList<C0491b> cG;
        final String className;
        final int versionCode;

        static {
            CREATOR = new aj();
        }

        C0490a(int i, String str, ArrayList<C0491b> arrayList) {
            this.versionCode = i;
            this.className = str;
            this.cG = arrayList;
        }

        C0490a(String str, HashMap<String, C0489a<?, ?>> hashMap) {
            this.versionCode = 1;
            this.className = str;
            this.cG = C0490a.m691a(hashMap);
        }

        private static ArrayList<C0491b> m691a(HashMap<String, C0489a<?, ?>> hashMap) {
            if (hashMap == null) {
                return null;
            }
            ArrayList<C0491b> arrayList = new ArrayList();
            for (String str : hashMap.keySet()) {
                arrayList.add(new C0491b(str, (C0489a) hashMap.get(str)));
            }
            return arrayList;
        }

        HashMap<String, C0489a<?, ?>> ak() {
            HashMap<String, C0489a<?, ?>> hashMap = new HashMap();
            int size = this.cG.size();
            for (int i = 0; i < size; i++) {
                C0491b c0491b = (C0491b) this.cG.get(i);
                hashMap.put(c0491b.cH, c0491b.cI);
            }
            return hashMap;
        }

        public int describeContents() {
            aj ajVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            aj ajVar = CREATOR;
            aj.m196a(this, out, flags);
        }
    }

    /* renamed from: com.google.android.gms.internal.ah.b */
    public static class C0491b implements SafeParcelable {
        public static final ag CREATOR;
        final String cH;
        final C0489a<?, ?> cI;
        final int versionCode;

        static {
            CREATOR = new ag();
        }

        C0491b(int i, String str, C0489a<?, ?> c0489a) {
            this.versionCode = i;
            this.cH = str;
            this.cI = c0489a;
        }

        C0491b(String str, C0489a<?, ?> c0489a) {
            this.versionCode = 1;
            this.cH = str;
            this.cI = c0489a;
        }

        public int describeContents() {
            ag agVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            ag agVar = CREATOR;
            ag.m190a(this, out, flags);
        }
    }

    static {
        CREATOR = new ai();
    }

    ah(int i, ArrayList<C0490a> arrayList, String str) {
        this.ab = i;
        this.cE = null;
        this.cD = m692b((ArrayList) arrayList);
        this.cF = (String) C0159s.m517d(str);
        ag();
    }

    public ah(Class<? extends ae> cls) {
        this.ab = 1;
        this.cE = null;
        this.cD = new HashMap();
        this.cF = cls.getCanonicalName();
    }

    private static HashMap<String, HashMap<String, C0489a<?, ?>>> m692b(ArrayList<C0490a> arrayList) {
        HashMap<String, HashMap<String, C0489a<?, ?>>> hashMap = new HashMap();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            C0490a c0490a = (C0490a) arrayList.get(i);
            hashMap.put(c0490a.className, c0490a.ak());
        }
        return hashMap;
    }

    public void m693a(Class<? extends ae> cls, HashMap<String, C0489a<?, ?>> hashMap) {
        this.cD.put(cls.getCanonicalName(), hashMap);
    }

    public void ag() {
        for (String str : this.cD.keySet()) {
            HashMap hashMap = (HashMap) this.cD.get(str);
            for (String str2 : hashMap.keySet()) {
                ((C0489a) hashMap.get(str2)).m688a(this);
            }
        }
    }

    public void ah() {
        for (String str : this.cD.keySet()) {
            HashMap hashMap = (HashMap) this.cD.get(str);
            HashMap hashMap2 = new HashMap();
            for (String str2 : hashMap.keySet()) {
                hashMap2.put(str2, ((C0489a) hashMap.get(str2)).m684W());
            }
            this.cD.put(str, hashMap2);
        }
    }

    ArrayList<C0490a> ai() {
        ArrayList<C0490a> arrayList = new ArrayList();
        for (String str : this.cD.keySet()) {
            arrayList.add(new C0490a(str, (HashMap) this.cD.get(str)));
        }
        return arrayList;
    }

    public String aj() {
        return this.cF;
    }

    public boolean m694b(Class<? extends ae> cls) {
        return this.cD.containsKey(cls.getCanonicalName());
    }

    public int describeContents() {
        ai aiVar = CREATOR;
        return 0;
    }

    int m695i() {
        return this.ab;
    }

    public HashMap<String, C0489a<?, ?>> m696q(String str) {
        return (HashMap) this.cD.get(str);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.cD.keySet()) {
            stringBuilder.append(str).append(":\n");
            HashMap hashMap = (HashMap) this.cD.get(str);
            for (String str2 : hashMap.keySet()) {
                stringBuilder.append("  ").append(str2).append(": ");
                stringBuilder.append(hashMap.get(str2));
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        ai aiVar = CREATOR;
        ai.m193a(this, out, flags);
    }
}
