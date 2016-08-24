package com.google.android.gms.appstate;

import com.google.android.gms.internal.C0158r;

/* renamed from: com.google.android.gms.appstate.a */
public final class C0881a implements AppState {
    private final int f143h;
    private final String f144i;
    private final byte[] f145j;
    private final boolean f146k;
    private final String f147l;
    private final byte[] f148m;

    public C0881a(AppState appState) {
        this.f143h = appState.getKey();
        this.f144i = appState.getLocalVersion();
        this.f145j = appState.getLocalData();
        this.f146k = appState.hasConflict();
        this.f147l = appState.getConflictVersion();
        this.f148m = appState.getConflictData();
    }

    static int m1152a(AppState appState) {
        return C0158r.hashCode(Integer.valueOf(appState.getKey()), appState.getLocalVersion(), appState.getLocalData(), Boolean.valueOf(appState.hasConflict()), appState.getConflictVersion(), appState.getConflictData());
    }

    static boolean m1153a(AppState appState, Object obj) {
        if (!(obj instanceof AppState)) {
            return false;
        }
        if (appState == obj) {
            return true;
        }
        AppState appState2 = (AppState) obj;
        return C0158r.m509a(Integer.valueOf(appState2.getKey()), Integer.valueOf(appState.getKey())) && C0158r.m509a(appState2.getLocalVersion(), appState.getLocalVersion()) && C0158r.m509a(appState2.getLocalData(), appState.getLocalData()) && C0158r.m509a(Boolean.valueOf(appState2.hasConflict()), Boolean.valueOf(appState.hasConflict())) && C0158r.m509a(appState2.getConflictVersion(), appState.getConflictVersion()) && C0158r.m509a(appState2.getConflictData(), appState.getConflictData());
    }

    static String m1154b(AppState appState) {
        return C0158r.m510c(appState).m508a("Key", Integer.valueOf(appState.getKey())).m508a("LocalVersion", appState.getLocalVersion()).m508a("LocalData", appState.getLocalData()).m508a("HasConflict", Boolean.valueOf(appState.hasConflict())).m508a("ConflictVersion", appState.getConflictVersion()).m508a("ConflictData", appState.getConflictData()).toString();
    }

    public AppState m1155a() {
        return this;
    }

    public boolean equals(Object obj) {
        return C0881a.m1153a(this, obj);
    }

    public /* synthetic */ Object freeze() {
        return m1155a();
    }

    public byte[] getConflictData() {
        return this.f148m;
    }

    public String getConflictVersion() {
        return this.f147l;
    }

    public int getKey() {
        return this.f143h;
    }

    public byte[] getLocalData() {
        return this.f145j;
    }

    public String getLocalVersion() {
        return this.f144i;
    }

    public boolean hasConflict() {
        return this.f146k;
    }

    public int hashCode() {
        return C0881a.m1152a(this);
    }

    public boolean isDataValid() {
        return true;
    }

    public String toString() {
        return C0881a.m1154b(this);
    }
}
