package org.apache.commons.lang3.concurrent;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AtomicSafeInitializer<T> implements ConcurrentInitializer<T> {
    private final AtomicReference<AtomicSafeInitializer<T>> factory;
    private final AtomicReference<T> reference;

    protected abstract T initialize() throws ConcurrentException;

    public AtomicSafeInitializer() {
        this.factory = new AtomicReference();
        this.reference = new AtomicReference();
    }

    public final T get() throws ConcurrentException {
        while (true) {
            T result = this.reference.get();
            if (result != null) {
                return result;
            }
            if (this.factory.compareAndSet(null, this)) {
                this.reference.set(initialize());
            }
        }
    }
}
