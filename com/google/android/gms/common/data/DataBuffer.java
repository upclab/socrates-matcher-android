package com.google.android.gms.common.data;

import java.util.Iterator;

public abstract class DataBuffer<T> implements Iterable<T> {
    protected final C0468d f30S;

    protected DataBuffer(C0468d dataHolder) {
        this.f30S = dataHolder;
    }

    public void close() {
        this.f30S.close();
    }

    public int describeContents() {
        return 0;
    }

    public abstract T get(int i);

    public int getCount() {
        return this.f30S.getCount();
    }

    public boolean isClosed() {
        return this.f30S.isClosed();
    }

    public Iterator<T> iterator() {
        return new C0096a(this);
    }
}
