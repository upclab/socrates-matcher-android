package com.google.android.gms.common.data;

import java.util.ArrayList;

/* renamed from: com.google.android.gms.common.data.f */
public abstract class C0469f<T> extends DataBuffer<T> {
    private boolean ao;
    private ArrayList<Integer> ap;

    protected C0469f(C0468d c0468d) {
        super(c0468d);
        this.ao = false;
    }

    private int m645h(int i) {
        if (i >= 0 && i < this.ap.size()) {
            return ((Integer) this.ap.get(i)).intValue();
        }
        throw new IllegalArgumentException("Position " + i + " is out of bounds for this buffer");
    }

    private int m646i(int i) {
        return (i < 0 || i == this.ap.size()) ? 0 : i == this.ap.size() + -1 ? this.S.getCount() - ((Integer) this.ap.get(i)).intValue() : ((Integer) this.ap.get(i + 1)).intValue() - ((Integer) this.ap.get(i)).intValue();
    }

    private void m647m() {
        synchronized (this) {
            if (!this.ao) {
                int count = this.S.getCount();
                this.ap = new ArrayList();
                if (count > 0) {
                    this.ap.add(Integer.valueOf(0));
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    String c = this.S.m634c(primaryDataMarkerColumn, 0, this.S.m636e(0));
                    int i = 1;
                    while (i < count) {
                        String c2 = this.S.m634c(primaryDataMarkerColumn, i, this.S.m636e(i));
                        if (c2.equals(c)) {
                            c2 = c;
                        } else {
                            this.ap.add(Integer.valueOf(i));
                        }
                        i++;
                        c = c2;
                    }
                }
                this.ao = true;
            }
        }
    }

    protected abstract T m648a(int i, int i2);

    public final T get(int position) {
        m647m();
        return m648a(m645h(position), m646i(position));
    }

    public int getCount() {
        m647m();
        return this.ap.size();
    }

    protected abstract String getPrimaryDataMarkerColumn();
}
