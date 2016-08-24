package com.google.android.gms.common.data;

import com.google.android.gms.internal.C0159s;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: com.google.android.gms.common.data.a */
public final class C0096a<T> implements Iterator<T> {
    private final DataBuffer<T> f31T;
    private int f32U;

    public C0096a(DataBuffer<T> dataBuffer) {
        this.f31T = (DataBuffer) C0159s.m517d(dataBuffer);
        this.f32U = -1;
    }

    public boolean hasNext() {
        return this.f32U < this.f31T.getCount() + -1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer dataBuffer = this.f31T;
            int i = this.f32U + 1;
            this.f32U = i;
            return dataBuffer.get(i);
        }
        throw new NoSuchElementException("Cannot advance the iterator beyond " + this.f32U);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
