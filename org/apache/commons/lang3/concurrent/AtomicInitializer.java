package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicInitializer<T> implements ConcurrentInitializer<T> {
    private final AtomicReference<T> reference;

    protected abstract T initialize() throws ConcurrentException;

    public AtomicInitializer() {
        this.reference = new AtomicReference();
    }

    public T get() throws ConcurrentException {
        T result = this.reference.get();
        if (result != null) {
            return result;
        }
        result = initialize();
        if (this.reference.compareAndSet(null, result)) {
            return result;
        }
        return this.reference.get();
    }
}
