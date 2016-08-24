package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0128b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public final class ab implements SafeParcelable, C0128b<String, Integer> {
    public static final ac CREATOR;
    private final int ab;
    private final HashMap<String, Integer> co;
    private final HashMap<Integer, String> cp;
    private final ArrayList<C0488a> cq;

    /* renamed from: com.google.android.gms.internal.ab.a */
    public static final class C0488a implements SafeParcelable {
        public static final ad CREATOR;
        final String cr;
        final int cs;
        final int versionCode;

        static {
            CREATOR = new ad();
        }

        C0488a(int i, String str, int i2) {
            this.versionCode = i;
            this.cr = str;
            this.cs = i2;
        }

        C0488a(String str, int i) {
            this.versionCode = 1;
            this.cr = str;
            this.cs = i;
        }

        public int describeContents() {
            ad adVar = CREATOR;
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            ad adVar = CREATOR;
            ad.m169a(this, out, flags);
        }
    }

    static {
        CREATOR = new ac();
    }

    public ab() {
        this.ab = 1;
        this.co = new HashMap();
        this.cp = new HashMap();
        this.cq = null;
    }

    ab(int i, ArrayList<C0488a> arrayList) {
        this.ab = i;
        this.co = new HashMap();
        this.cp = new HashMap();
        this.cq = null;
        m665a((ArrayList) arrayList);
    }

    private void m665a(ArrayList<C0488a> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C0488a c0488a = (C0488a) it.next();
            m670b(c0488a.cr, c0488a.cs);
        }
    }

    ArrayList<C0488a> m666Q() {
        ArrayList<C0488a> arrayList = new ArrayList();
        for (String str : this.co.keySet()) {
            arrayList.add(new C0488a(str, ((Integer) this.co.get(str)).intValue()));
        }
        return arrayList;
    }

    public int m667R() {
        return 7;
    }

    public int m668S() {
        return 0;
    }

    public String m669a(Integer num) {
        String str = (String) this.cp.get(num);
        return (str == null && this.co.containsKey("gms_unknown")) ? "gms_unknown" : str;
    }

    public ab m670b(String str, int i) {
        this.co.put(str, Integer.valueOf(i));
        this.cp.put(Integer.valueOf(i), str);
        return this;
    }

    public int describeContents() {
        ac acVar = CREATOR;
        return 0;
    }

    public /* synthetic */ Object m671e(Object obj) {
        return m669a((Integer) obj);
    }

    int m672i() {
        return this.ab;
    }

    public void writeToParcel(Parcel out, int flags) {
        ac acVar = CREATOR;
        ac.m166a(this, out, flags);
    }
}
