package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Supplier;
import java.util.concurrent.atomic.AtomicLong;

@GwtCompatible(emulated = true)
final class LongAddables {
    private static final Supplier<LongAddable> SUPPLIER;

    /* renamed from: com.google.common.cache.LongAddables.1 */
    static class C06451 implements Supplier<LongAddable> {
        C06451() {
        }

        public LongAddable get() {
            return new LongAdder();
        }
    }

    /* renamed from: com.google.common.cache.LongAddables.2 */
    static class C06462 implements Supplier<LongAddable> {
        C06462() {
        }

        public LongAddable get() {
            return new PureJavaLongAddable();
        }
    }

    private static final class PureJavaLongAddable extends AtomicLong implements LongAddable {
        private PureJavaLongAddable() {
        }

        public void increment() {
            getAndIncrement();
        }

        public void add(long x) {
            getAndAdd(x);
        }

        public long sum() {
            return get();
        }
    }

    LongAddables() {
    }

    static {
        Supplier<LongAddable> supplier;
        try {
            LongAdder longAdder = new LongAdder();
            supplier = new C06451();
        } catch (Throwable th) {
            supplier = new C06462();
        }
        SUPPLIER = supplier;
    }

    public static LongAddable create() {
        return (LongAddable) SUPPLIER.get();
    }
}
