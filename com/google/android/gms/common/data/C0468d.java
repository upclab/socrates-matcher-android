package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.C0159s;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.google.android.gms.common.data.d */
public final class C0468d implements SafeParcelable {
    public static final C0099e CREATOR;
    private static final HashMap<CursorWindow, Throwable> f75Z;
    private static final Object aa;
    private static final C0098a ai;
    private final int ab;
    private final String[] ac;
    Bundle ad;
    private final CursorWindow[] ae;
    private final Bundle af;
    int[] ag;
    int ah;
    boolean mClosed;
    private final int f76p;

    /* renamed from: com.google.android.gms.common.data.d.a */
    public static class C0098a {
        private final String[] ac;
        private final ArrayList<HashMap<String, Object>> aj;
        private final String ak;
        private final HashMap<Object, Integer> al;
        private boolean am;
        private String an;

        private C0098a(String[] strArr, String str) {
            this.ac = (String[]) C0159s.m517d(strArr);
            this.aj = new ArrayList();
            this.ak = str;
            this.al = new HashMap();
            this.am = false;
            this.an = null;
        }
    }

    /* renamed from: com.google.android.gms.common.data.d.1 */
    static class C04671 extends C0098a {
        C04671(String[] strArr, String str) {
            super(str, null);
        }
    }

    static {
        CREATOR = new C0099e();
        f75Z = (HashMap) null;
        aa = new Object();
        ai = new C04671(new String[0], null);
    }

    C0468d(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.ab = i;
        this.ac = strArr;
        this.ae = cursorWindowArr;
        this.f76p = i2;
        this.af = bundle;
    }

    private C0468d(C0098a c0098a, int i, Bundle bundle) {
        this(c0098a.ac, C0468d.m629a(c0098a), i, bundle);
    }

    public C0468d(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.ab = 1;
        this.ac = (String[]) C0159s.m517d(strArr);
        this.ae = (CursorWindow[]) C0159s.m517d(cursorWindowArr);
        this.f76p = i;
        this.af = bundle;
        m640h();
    }

    public static C0468d m626a(int i, Bundle bundle) {
        return new C0468d(ai, i, bundle);
    }

    private static void m627a(CursorWindow cursorWindow) {
    }

    private void m628a(String str, int i) {
        if (this.ad == null || !this.ad.containsKey(str)) {
            throw new IllegalArgumentException("No such column: " + str);
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.ah) {
            throw new CursorIndexOutOfBoundsException(i, this.ah);
        }
    }

    private static CursorWindow[] m629a(C0098a c0098a) {
        if (c0098a.ac.length == 0) {
            return new CursorWindow[0];
        }
        ArrayList c = c0098a.aj;
        int size = c.size();
        CursorWindow cursorWindow = new CursorWindow(false);
        CursorWindow[] cursorWindowArr = new CursorWindow[]{cursorWindow};
        cursorWindow.setNumColumns(c0098a.ac.length);
        int i = 0;
        while (i < size) {
            try {
                if (cursorWindow.allocRow()) {
                    Map map = (Map) c.get(i);
                    for (int i2 = 0; i2 < c0098a.ac.length; i2++) {
                        String str = c0098a.ac[i2];
                        Object obj = map.get(str);
                        if (obj == null) {
                            cursorWindow.putNull(i, i2);
                        } else if (obj instanceof String) {
                            cursorWindow.putString((String) obj, i, i2);
                        } else if (obj instanceof Long) {
                            cursorWindow.putLong(((Long) obj).longValue(), i, i2);
                        } else if (obj instanceof Integer) {
                            cursorWindow.putLong((long) ((Integer) obj).intValue(), i, i2);
                        } else if (obj instanceof Boolean) {
                            cursorWindow.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i, i2);
                        } else if (obj instanceof byte[]) {
                            cursorWindow.putBlob((byte[]) obj, i, i2);
                        } else {
                            throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                        }
                    }
                    i++;
                } else {
                    throw new RuntimeException("Cursor window out of memory");
                }
            } catch (RuntimeException e) {
                cursorWindow.close();
                throw e;
            }
        }
        return cursorWindowArr;
    }

    public static C0468d m630f(int i) {
        return C0468d.m626a(i, null);
    }

    public long m631a(String str, int i, int i2) {
        m628a(str, i);
        return this.ae[i2].getLong(i - this.ag[i2], this.ad.getInt(str));
    }

    public void m632a(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        m628a(str, i);
        this.ae[i2].copyStringToBuffer(i - this.ag[i2], this.ad.getInt(str), charArrayBuffer);
    }

    public int m633b(String str, int i, int i2) {
        m628a(str, i);
        return this.ae[i2].getInt(i - this.ag[i2], this.ad.getInt(str));
    }

    public String m634c(String str, int i, int i2) {
        m628a(str, i);
        return this.ae[i2].getString(i - this.ag[i2], this.ad.getInt(str));
    }

    public void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (int i = 0; i < this.ae.length; i++) {
                    this.ae[i].close();
                    C0468d.m627a(this.ae[i]);
                }
            }
        }
    }

    public boolean m635d(String str, int i, int i2) {
        m628a(str, i);
        return Long.valueOf(this.ae[i2].getLong(i - this.ag[i2], this.ad.getInt(str))).longValue() == 1;
    }

    public int describeContents() {
        return 0;
    }

    public int m636e(int i) {
        int i2 = 0;
        boolean z = i >= 0 && i < this.ah;
        C0159s.m511a(z);
        while (i2 < this.ag.length) {
            if (i < this.ag[i2]) {
                i2--;
                break;
            }
            i2++;
        }
        return i2 == this.ag.length ? i2 - 1 : i2;
    }

    public byte[] m637e(String str, int i, int i2) {
        m628a(str, i);
        return this.ae[i2].getBlob(i - this.ag[i2], this.ad.getInt(str));
    }

    public Uri m638f(String str, int i, int i2) {
        String c = m634c(str, i, i2);
        return c == null ? null : Uri.parse(c);
    }

    public boolean m639g(String str, int i, int i2) {
        m628a(str, i);
        return this.ae[i2].isNull(i - this.ag[i2], this.ad.getInt(str));
    }

    public int getCount() {
        return this.ah;
    }

    public int getStatusCode() {
        return this.f76p;
    }

    public void m640h() {
        int i;
        int i2 = 0;
        this.ad = new Bundle();
        for (i = 0; i < this.ac.length; i++) {
            this.ad.putInt(this.ac[i], i);
        }
        this.ag = new int[this.ae.length];
        i = 0;
        while (i2 < this.ae.length) {
            this.ag[i2] = i;
            i += this.ae[i2].getNumRows();
            i2++;
        }
        this.ah = i;
    }

    int m641i() {
        return this.ab;
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    String[] m642j() {
        return this.ac;
    }

    CursorWindow[] m643k() {
        return this.ae;
    }

    public Bundle m644l() {
        return this.af;
    }

    public void writeToParcel(Parcel dest, int flags) {
        C0099e.m35a(this, dest, flags);
    }
}
