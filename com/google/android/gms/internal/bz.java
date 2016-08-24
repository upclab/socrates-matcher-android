package com.google.android.gms.internal;

import android.os.Parcel;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.ae.C0489a;
import com.google.android.gms.plus.model.moments.ItemScope;
import com.google.android.gms.plus.model.moments.Moment;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class bz extends ae implements SafeParcelable, Moment {
    public static final ca CREATOR;
    private static final HashMap<String, C0489a<?, ?>> iC;
    private final int ab;
    private final Set<Integer> iD;
    private bx jB;
    private bx jC;
    private String jh;
    private String js;
    private String jy;

    static {
        CREATOR = new ca();
        iC = new HashMap();
        iC.put("id", C0489a.m680f("id", 2));
        iC.put("result", C0489a.m674a("result", 4, bx.class));
        iC.put("startDate", C0489a.m680f("startDate", 5));
        iC.put("target", C0489a.m674a("target", 6, bx.class));
        iC.put("type", C0489a.m680f("type", 7));
    }

    public bz() {
        this.ab = 1;
        this.iD = new HashSet();
    }

    bz(Set<Integer> set, int i, String str, bx bxVar, String str2, bx bxVar2, String str3) {
        this.iD = set;
        this.ab = i;
        this.jh = str;
        this.jB = bxVar;
        this.js = str2;
        this.jC = bxVar2;
        this.jy = str3;
    }

    public bz(Set<Integer> set, String str, bx bxVar, String str2, bx bxVar2, String str3) {
        this.iD = set;
        this.ab = 1;
        this.jh = str;
        this.jB = bxVar;
        this.js = str2;
        this.jC = bxVar2;
        this.jy = str3;
    }

    public HashMap<String, C0489a<?, ?>> m1294T() {
        return iC;
    }

    protected boolean m1295a(C0489a c0489a) {
        return this.iD.contains(Integer.valueOf(c0489a.aa()));
    }

    protected Object m1296b(C0489a c0489a) {
        switch (c0489a.aa()) {
            case Value.STRING_FIELD_NUMBER /*2*/:
                return this.jh;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return this.jB;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return this.js;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return this.jC;
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                return this.jy;
            default:
                throw new IllegalStateException("Unknown safe parcelable id=" + c0489a.aa());
        }
    }

    Set<Integer> bH() {
        return this.iD;
    }

    bx bY() {
        return this.jB;
    }

    bx bZ() {
        return this.jC;
    }

    public bz ca() {
        return this;
    }

    public int describeContents() {
        ca caVar = CREATOR;
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof bz)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        bz bzVar = (bz) obj;
        for (C0489a c0489a : iC.values()) {
            if (m1295a(c0489a)) {
                if (!bzVar.m1295a(c0489a)) {
                    return false;
                }
                if (!m1296b(c0489a).equals(bzVar.m1296b(c0489a))) {
                    return false;
                }
            } else if (bzVar.m1295a(c0489a)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return ca();
    }

    public String getId() {
        return this.jh;
    }

    public ItemScope getResult() {
        return this.jB;
    }

    public String getStartDate() {
        return this.js;
    }

    public ItemScope getTarget() {
        return this.jC;
    }

    public String getType() {
        return this.jy;
    }

    public boolean hasId() {
        return this.iD.contains(Integer.valueOf(2));
    }

    public boolean hasResult() {
        return this.iD.contains(Integer.valueOf(4));
    }

    public boolean hasStartDate() {
        return this.iD.contains(Integer.valueOf(5));
    }

    public boolean hasTarget() {
        return this.iD.contains(Integer.valueOf(6));
    }

    public boolean hasType() {
        return this.iD.contains(Integer.valueOf(7));
    }

    public int hashCode() {
        int i = 0;
        for (C0489a c0489a : iC.values()) {
            int hashCode;
            if (m1295a(c0489a)) {
                hashCode = m1296b(c0489a).hashCode() + (i + c0489a.aa());
            } else {
                hashCode = i;
            }
            i = hashCode;
        }
        return i;
    }

    int m1297i() {
        return this.ab;
    }

    public boolean isDataValid() {
        return true;
    }

    protected Object m1298m(String str) {
        return null;
    }

    protected boolean m1299n(String str) {
        return false;
    }

    public void writeToParcel(Parcel out, int flags) {
        ca caVar = CREATOR;
        ca.m414a(this, out, flags);
    }
}
