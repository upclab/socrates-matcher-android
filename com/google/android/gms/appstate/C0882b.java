package com.google.android.gms.appstate;

import com.google.android.gms.common.data.C0097b;
import com.google.android.gms.common.data.C0468d;

/* renamed from: com.google.android.gms.appstate.b */
public final class C0882b extends C0097b implements AppState {
    C0882b(C0468d c0468d, int i) {
        super(c0468d, i);
    }

    public AppState m1156a() {
        return new C0881a(this);
    }

    public boolean equals(Object obj) {
        return C0881a.m1153a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return m1156a();
    }

    public byte[] getConflictData() {
        return getByteArray("conflict_data");
    }

    public String getConflictVersion() {
        return getString("conflict_version");
    }

    public int getKey() {
        return getInteger("key");
    }

    public byte[] getLocalData() {
        return getByteArray("local_data");
    }

    public String getLocalVersion() {
        return getString("local_version");
    }

    public boolean hasConflict() {
        return !m32e("conflict_version");
    }

    public int hashCode() {
        return C0881a.m1152a(this);
    }

    public String toString() {
        return C0881a.m1154b(this);
    }
}
