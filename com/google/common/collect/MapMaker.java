package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.base.Ticker;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class MapMaker extends GenericMapMaker<Object, Object> {
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int UNSET_INT = -1;
    int concurrencyLevel;
    long expireAfterAccessNanos;
    long expireAfterWriteNanos;
    int initialCapacity;
    Equivalence<Object> keyEquivalence;
    Strength keyStrength;
    int maximumSize;
    RemovalCause nullRemovalCause;
    Ticker ticker;
    boolean useCustomMap;
    Strength valueStrength;

    static class NullConcurrentMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        private final RemovalCause removalCause;
        private final RemovalListener<K, V> removalListener;

        NullConcurrentMap(MapMaker mapMaker) {
            this.removalListener = mapMaker.getRemovalListener();
            this.removalCause = mapMaker.nullRemovalCause;
        }

        public boolean containsKey(@Nullable Object key) {
            return false;
        }

        public boolean containsValue(@Nullable Object value) {
            return false;
        }

        public V get(@Nullable Object key) {
            return null;
        }

        void notifyRemoval(K key, V value) {
            this.removalListener.onRemoval(new RemovalNotification(key, value, this.removalCause));
        }

        public V put(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            notifyRemoval(key, value);
            return null;
        }

        public V putIfAbsent(K key, V value) {
            return put(key, value);
        }

        public V remove(@Nullable Object key) {
            return null;
        }

        public boolean remove(@Nullable Object key, @Nullable Object value) {
            return false;
        }

        public V replace(K key, V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            return null;
        }

        public boolean replace(K key, @Nullable V v, V newValue) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(newValue);
            return false;
        }

        public Set<Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }
    }

    enum RemovalCause {
        EXPLICIT {
            boolean wasEvicted() {
                return false;
            }
        },
        REPLACED {
            boolean wasEvicted() {
                return false;
            }
        },
        COLLECTED {
            boolean wasEvicted() {
                return true;
            }
        },
        EXPIRED {
            boolean wasEvicted() {
                return true;
            }
        },
        SIZE {
            boolean wasEvicted() {
                return true;
            }
        };

        abstract boolean wasEvicted();
    }

    interface RemovalListener<K, V> {
        void onRemoval(RemovalNotification<K, V> removalNotification);
    }

    static final class NullComputingConcurrentMap<K, V> extends NullConcurrentMap<K, V> {
        private static final long serialVersionUID = 0;
        final Function<? super K, ? extends V> computingFunction;

        NullComputingConcurrentMap(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
            super(mapMaker);
            this.computingFunction = (Function) Preconditions.checkNotNull(computingFunction);
        }

        public V get(Object k) {
            K key = k;
            V value = compute(key);
            Preconditions.checkNotNull(value, this.computingFunction + " returned null for key " + key + ".");
            notifyRemoval(key, value);
            return value;
        }

        private V compute(K key) {
            Preconditions.checkNotNull(key);
            try {
                return this.computingFunction.apply(key);
            } catch (ComputationException e) {
                throw e;
            } catch (Throwable t) {
                ComputationException computationException = new ComputationException(t);
            }
        }
    }

    static final class ComputingMapAdapter<K, V> extends ComputingConcurrentHashMap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;

        ComputingMapAdapter(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
            super(mapMaker, computingFunction);
        }

        public V get(Object key) {
            try {
                V value = getOrCompute(key);
                if (value != null) {
                    return value;
                }
                throw new NullPointerException(this.computingFunction + " returned null for key " + key + ".");
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                Throwables.propagateIfInstanceOf(cause, ComputationException.class);
                throw new ComputationException(cause);
            }
        }
    }

    static final class RemovalNotification<K, V> extends ImmutableEntry<K, V> {
        private static final long serialVersionUID = 0;
        private final RemovalCause cause;

        RemovalNotification(@Nullable K key, @Nullable V value, RemovalCause cause) {
            super(key, value);
            this.cause = cause;
        }

        public RemovalCause getCause() {
            return this.cause;
        }

        public boolean wasEvicted() {
            return this.cause.wasEvicted();
        }
    }

    public MapMaker() {
        this.initialCapacity = UNSET_INT;
        this.concurrencyLevel = UNSET_INT;
        this.maximumSize = UNSET_INT;
        this.expireAfterWriteNanos = -1;
        this.expireAfterAccessNanos = -1;
    }

    @GwtIncompatible("To be supported")
    MapMaker keyEquivalence(Equivalence<Object> equivalence) {
        boolean z;
        if (this.keyEquivalence == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }

    Equivalence<Object> getKeyEquivalence() {
        return (Equivalence) Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
    }

    public MapMaker initialCapacity(int initialCapacity) {
        boolean z;
        boolean z2 = true;
        if (this.initialCapacity == UNSET_INT) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "initial capacity was already set to %s", Integer.valueOf(this.initialCapacity));
        if (initialCapacity < 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        this.initialCapacity = initialCapacity;
        return this;
    }

    int getInitialCapacity() {
        return this.initialCapacity == UNSET_INT ? DEFAULT_INITIAL_CAPACITY : this.initialCapacity;
    }

    @Deprecated
    MapMaker maximumSize(int size) {
        boolean z = false;
        Preconditions.checkState(this.maximumSize == UNSET_INT, "maximum size was already set to %s", Integer.valueOf(this.maximumSize));
        if (size >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "maximum size must not be negative");
        this.maximumSize = size;
        this.useCustomMap = true;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }

    public MapMaker concurrencyLevel(int concurrencyLevel) {
        boolean z;
        boolean z2 = true;
        if (this.concurrencyLevel == UNSET_INT) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "concurrency level was already set to %s", Integer.valueOf(this.concurrencyLevel));
        if (concurrencyLevel <= 0) {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }

    int getConcurrencyLevel() {
        return this.concurrencyLevel == UNSET_INT ? DEFAULT_CONCURRENCY_LEVEL : this.concurrencyLevel;
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakKeys() {
        return setKeyStrength(Strength.WEAK);
    }

    MapMaker setKeyStrength(Strength strength) {
        boolean z = false;
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = (Strength) Preconditions.checkNotNull(strength);
        if (this.keyStrength != Strength.SOFT) {
            z = true;
        }
        Preconditions.checkArgument(z, "Soft keys are not supported");
        if (strength != Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    Strength getKeyStrength() {
        return (Strength) Objects.firstNonNull(this.keyStrength, Strength.STRONG);
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakValues() {
        return setValueStrength(Strength.WEAK);
    }

    @GwtIncompatible("java.lang.ref.SoftReference")
    @Deprecated
    public MapMaker softValues() {
        return setValueStrength(Strength.SOFT);
    }

    MapMaker setValueStrength(Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = (Strength) Preconditions.checkNotNull(strength);
        if (strength != Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }

    Strength getValueStrength() {
        return (Strength) Objects.firstNonNull(this.valueStrength, Strength.STRONG);
    }

    @Deprecated
    MapMaker expireAfterWrite(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    private void checkExpiration(long duration, TimeUnit unit) {
        boolean z;
        Preconditions.checkState(this.expireAfterWriteNanos == -1, "expireAfterWrite was already set to %s ns", Long.valueOf(this.expireAfterWriteNanos));
        if (this.expireAfterAccessNanos == -1) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z, "expireAfterAccess was already set to %s ns", Long.valueOf(this.expireAfterAccessNanos));
        if (duration >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "duration cannot be negative: %s %s", Long.valueOf(duration), unit);
    }

    long getExpireAfterWriteNanos() {
        return this.expireAfterWriteNanos == -1 ? 0 : this.expireAfterWriteNanos;
    }

    @GwtIncompatible("To be supported")
    @Deprecated
    MapMaker expireAfterAccess(long duration, TimeUnit unit) {
        checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0 && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }

    long getExpireAfterAccessNanos() {
        return this.expireAfterAccessNanos == -1 ? 0 : this.expireAfterAccessNanos;
    }

    Ticker getTicker() {
        return (Ticker) Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }

    @GwtIncompatible("To be supported")
    @Deprecated
    <K, V> GenericMapMaker<K, V> removalListener(RemovalListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = (RemovalListener) Preconditions.checkNotNull(listener);
        this.useCustomMap = true;
        return me;
    }

    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap(getInitialCapacity(), 0.75f, getConcurrencyLevel());
        }
        return this.nullRemovalCause == null ? new MapMakerInternalMap(this) : new NullConcurrentMap(this);
    }

    @GwtIncompatible("MapMakerInternalMap")
    <K, V> MapMakerInternalMap<K, V> makeCustomMap() {
        return new MapMakerInternalMap(this);
    }

    @Deprecated
    <K, V> ConcurrentMap<K, V> makeComputingMap(Function<? super K, ? extends V> computingFunction) {
        return this.nullRemovalCause == null ? new ComputingMapAdapter(this, computingFunction) : new NullComputingConcurrentMap(this, computingFunction);
    }

    public String toString() {
        ToStringHelper s = Objects.toStringHelper((Object) this);
        if (this.initialCapacity != UNSET_INT) {
            s.add("initialCapacity", this.initialCapacity);
        }
        if (this.concurrencyLevel != UNSET_INT) {
            s.add("concurrencyLevel", this.concurrencyLevel);
        }
        if (this.maximumSize != UNSET_INT) {
            s.add("maximumSize", this.maximumSize);
        }
        if (this.expireAfterWriteNanos != -1) {
            s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
        }
        if (this.expireAfterAccessNanos != -1) {
            s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
        }
        if (this.keyStrength != null) {
            s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
        }
        if (this.valueStrength != null) {
            s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
        }
        if (this.keyEquivalence != null) {
            s.addValue((Object) "keyEquivalence");
        }
        if (this.removalListener != null) {
            s.addValue((Object) "removalListener");
        }
        return s.toString();
    }
}
