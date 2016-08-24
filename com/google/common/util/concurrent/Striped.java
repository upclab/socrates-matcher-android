package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Beta
public abstract class Striped<L> {
    private static final int ALL_SET = -1;
    private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER;

    private static class PaddedLock extends ReentrantLock {
        long q1;
        long q2;
        long q3;

        PaddedLock() {
            super(false);
        }
    }

    private static class PaddedSemaphore extends Semaphore {
        long q1;
        long q2;
        long q3;

        PaddedSemaphore(int permits) {
            super(permits, false);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped.1 */
    static class C08311 implements Supplier<Lock> {
        C08311() {
        }

        public Lock get() {
            return new PaddedLock();
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped.2 */
    static class C08322 implements Supplier<Lock> {
        C08322() {
        }

        public Lock get() {
            return new ReentrantLock(false);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped.3 */
    static class C08333 implements Supplier<Semaphore> {
        final /* synthetic */ int val$permits;

        C08333(int i) {
            this.val$permits = i;
        }

        public Semaphore get() {
            return new PaddedSemaphore(this.val$permits);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped.4 */
    static class C08344 implements Supplier<Semaphore> {
        final /* synthetic */ int val$permits;

        C08344(int i) {
            this.val$permits = i;
        }

        public Semaphore get() {
            return new Semaphore(this.val$permits, false);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped.5 */
    static class C08355 implements Supplier<ReadWriteLock> {
        C08355() {
        }

        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    }

    private static abstract class PowerOfTwoStriped<L> extends Striped<L> {
        final int mask;

        PowerOfTwoStriped(int stripes) {
            super();
            Preconditions.checkArgument(stripes > 0, "Stripes must be positive");
            this.mask = stripes > Ints.MAX_POWER_OF_TWO ? Striped.ALL_SET : Striped.ceilToPowerOfTwo(stripes) + Striped.ALL_SET;
        }

        final int indexFor(Object key) {
            return this.mask & Striped.smear(key.hashCode());
        }

        public final L get(Object key) {
            return getAt(indexFor(key));
        }
    }

    private static class CompactStriped<L> extends PowerOfTwoStriped<L> {
        private final Object[] array;

        private CompactStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            Preconditions.checkArgument(stripes <= Ints.MAX_POWER_OF_TWO, "Stripes must be <= 2^30)");
            this.array = new Object[(this.mask + 1)];
            for (int i = 0; i < this.array.length; i++) {
                this.array[i] = supplier.get();
            }
        }

        public L getAt(int index) {
            return this.array[index];
        }

        public int size() {
            return this.array.length;
        }
    }

    private static class LazyStriped<L> extends PowerOfTwoStriped<L> {
        final ConcurrentMap<Integer, L> locks;
        final int size;
        final Supplier<L> supplier;

        LazyStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            this.size = this.mask == Striped.ALL_SET ? Integer.MAX_VALUE : this.mask + 1;
            this.supplier = supplier;
            this.locks = new MapMaker().weakValues().makeMap();
        }

        public L getAt(int index) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(index, size());
            }
            L existing = this.locks.get(Integer.valueOf(index));
            if (existing != null) {
                return existing;
            }
            L created = this.supplier.get();
            return Objects.firstNonNull(this.locks.putIfAbsent(Integer.valueOf(index), created), created);
        }

        public int size() {
            return this.size;
        }
    }

    public abstract L get(Object obj);

    public abstract L getAt(int i);

    abstract int indexFor(Object obj);

    public abstract int size();

    private Striped() {
    }

    public Iterable<L> bulkGet(Iterable<?> keys) {
        int i;
        Object[] array = Iterables.toArray(keys, Object.class);
        int[] stripes = new int[array.length];
        for (i = 0; i < array.length; i++) {
            stripes[i] = indexFor(array[i]);
        }
        Arrays.sort(stripes);
        for (i = 0; i < array.length; i++) {
            array[i] = getAt(stripes[i]);
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    public static Striped<Lock> lock(int stripes) {
        return new CompactStriped(new C08311(), null);
    }

    public static Striped<Lock> lazyWeakLock(int stripes) {
        return new LazyStriped(stripes, new C08322());
    }

    public static Striped<Semaphore> semaphore(int stripes, int permits) {
        return new CompactStriped(new C08333(permits), null);
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int stripes, int permits) {
        return new LazyStriped(stripes, new C08344(permits));
    }

    public static Striped<ReadWriteLock> readWriteLock(int stripes) {
        return new CompactStriped(READ_WRITE_LOCK_SUPPLIER, null);
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int stripes) {
        return new LazyStriped(stripes, READ_WRITE_LOCK_SUPPLIER);
    }

    static {
        READ_WRITE_LOCK_SUPPLIER = new C08355();
    }

    private static int ceilToPowerOfTwo(int x) {
        return 1 << IntMath.log2(x, RoundingMode.CEILING);
    }

    private static int smear(int hashCode) {
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return ((hashCode >>> 7) ^ hashCode) ^ (hashCode >>> 4);
    }
}
