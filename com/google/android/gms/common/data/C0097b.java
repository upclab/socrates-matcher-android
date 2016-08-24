package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.internal.C0158r;
import com.google.android.gms.internal.C0159s;

/* renamed from: com.google.android.gms.common.data.b */
public abstract class C0097b {
    protected final C0468d f33S;
    protected final int f34V;
    private final int f35W;

    public C0097b(C0468d c0468d, int i) {
        this.f33S = (C0468d) C0159s.m517d(c0468d);
        boolean z = i >= 0 && i < c0468d.getCount();
        C0159s.m511a(z);
        this.f34V = i;
        this.f35W = c0468d.m636e(this.f34V);
    }

    protected void m30a(String str, CharArrayBuffer charArrayBuffer) {
        this.f33S.m632a(str, this.f34V, this.f35W, charArrayBuffer);
    }

    protected Uri m31d(String str) {
        return this.f33S.m638f(str, this.f34V, this.f35W);
    }

    protected boolean m32e(String str) {
        return this.f33S.m639g(str, this.f34V, this.f35W);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0097b)) {
            return false;
        }
        C0097b c0097b = (C0097b) obj;
        return C0158r.m509a(Integer.valueOf(c0097b.f34V), Integer.valueOf(this.f34V)) && C0158r.m509a(Integer.valueOf(c0097b.f35W), Integer.valueOf(this.f35W)) && c0097b.f33S == this.f33S;
    }

    protected boolean getBoolean(String column) {
        return this.f33S.m635d(column, this.f34V, this.f35W);
    }

    protected byte[] getByteArray(String column) {
        return this.f33S.m637e(column, this.f34V, this.f35W);
    }

    protected int getInteger(String column) {
        return this.f33S.m633b(column, this.f34V, this.f35W);
    }

    protected long getLong(String column) {
        return this.f33S.m631a(column, this.f34V, this.f35W);
    }

    protected String getString(String column) {
        return this.f33S.m634c(column, this.f34V, this.f35W);
    }

    public int hashCode() {
        return C0158r.hashCode(Integer.valueOf(this.f34V), Integer.valueOf(this.f35W), this.f33S);
    }

    public boolean isDataValid() {
        return !this.f33S.isClosed();
    }
}
