package com.google.common.collect;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;
import org.joda.time.MutableDateTime;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    private static final Entry<?, ?>[] EMPTY_ENTRY_ARRAY;
    private transient ImmutableSet<Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableSetMultimap<K, V> multimapView;
    private transient ImmutableCollection<V> values;

    public static class Builder<K, V> {
        TerminalEntry<K, V>[] entries;
        int size;

        public Builder() {
            this(4);
        }

        Builder(int initialCapacity) {
            this.entries = new TerminalEntry[initialCapacity];
            this.size = 0;
        }

        private void ensureCapacity(int minCapacity) {
            if (minCapacity > this.entries.length) {
                this.entries = (TerminalEntry[]) ObjectArrays.arraysCopyOf(this.entries, com.google.common.collect.ImmutableCollection.Builder.expandedCapacity(this.entries.length, minCapacity));
            }
        }

        public Builder<K, V> put(K key, V value) {
            ensureCapacity(this.size + 1);
            TerminalEntry<K, V> entry = ImmutableMap.entryOf(key, value);
            TerminalEntry[] terminalEntryArr = this.entries;
            int i = this.size;
            this.size = i + 1;
            terminalEntryArr[i] = entry;
            return this;
        }

        public Builder<K, V> put(Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            ensureCapacity(this.size + map.size());
            for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
                put(entry);
            }
            return this;
        }

        public ImmutableMap<K, V> build() {
            switch (this.size) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    return ImmutableMap.of();
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return ImmutableMap.of(this.entries[0].getKey(), this.entries[0].getValue());
                default:
                    return new RegularImmutableMap(this.size, this.entries);
            }
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] keys;
        private final Object[] values;

        SerializedForm(ImmutableMap<?, ?> map) {
            this.keys = new Object[map.size()];
            this.values = new Object[map.size()];
            int i = 0;
            Iterator i$ = map.entrySet().iterator();
            while (i$.hasNext()) {
                Entry<?, ?> entry = (Entry) i$.next();
                this.keys[i] = entry.getKey();
                this.values[i] = entry.getValue();
                i++;
            }
        }

        Object readResolve() {
            return createMap(new Builder());
        }

        Object createMap(Builder<Object, Object> builder) {
            for (int i = 0; i < this.keys.length; i++) {
                builder.put(this.keys[i], this.values[i]);
            }
            return builder.build();
        }
    }

    private static final class MapViewOfValuesAsSingletonSets<K, V> extends ImmutableMap<K, ImmutableSet<V>> {
        private final ImmutableMap<K, V> delegate;

        /* renamed from: com.google.common.collect.ImmutableMap.MapViewOfValuesAsSingletonSets.1 */
        class C10821 extends ImmutableMapEntrySet<K, ImmutableSet<V>> {

            /* renamed from: com.google.common.collect.ImmutableMap.MapViewOfValuesAsSingletonSets.1.1 */
            class C06721 extends UnmodifiableIterator<Entry<K, ImmutableSet<V>>> {
                final /* synthetic */ Iterator val$backingIterator;

                /* renamed from: com.google.common.collect.ImmutableMap.MapViewOfValuesAsSingletonSets.1.1.1 */
                class C06711 extends AbstractMapEntry<K, ImmutableSet<V>> {
                    final /* synthetic */ Entry val$backingEntry;

                    C06711(Entry entry) {
                        this.val$backingEntry = entry;
                    }

                    public K getKey() {
                        return this.val$backingEntry.getKey();
                    }

                    public ImmutableSet<V> getValue() {
                        return ImmutableSet.of(this.val$backingEntry.getValue());
                    }
                }

                C06721(Iterator it) {
                    this.val$backingIterator = it;
                }

                public boolean hasNext() {
                    return this.val$backingIterator.hasNext();
                }

                public Entry<K, ImmutableSet<V>> next() {
                    return new C06711((Entry) this.val$backingIterator.next());
                }
            }

            C10821() {
            }

            ImmutableMap<K, ImmutableSet<V>> map() {
                return MapViewOfValuesAsSingletonSets.this;
            }

            public UnmodifiableIterator<Entry<K, ImmutableSet<V>>> iterator() {
                return new C06721(MapViewOfValuesAsSingletonSets.this.delegate.entrySet().iterator());
            }
        }

        public /* bridge */ /* synthetic */ Set entrySet() {
            return super.entrySet();
        }

        public /* bridge */ /* synthetic */ Set keySet() {
            return super.keySet();
        }

        public /* bridge */ /* synthetic */ Collection values() {
            return super.values();
        }

        MapViewOfValuesAsSingletonSets(ImmutableMap<K, V> delegate) {
            this.delegate = (ImmutableMap) Preconditions.checkNotNull(delegate);
        }

        public int size() {
            return this.delegate.size();
        }

        public boolean containsKey(@Nullable Object key) {
            return this.delegate.containsKey(key);
        }

        public ImmutableSet<V> get(@Nullable Object key) {
            V outerValue = this.delegate.get(key);
            return outerValue == null ? null : ImmutableSet.of(outerValue);
        }

        boolean isPartialView() {
            return false;
        }

        ImmutableSet<Entry<K, ImmutableSet<V>>> createEntrySet() {
            return new C10821();
        }
    }

    abstract ImmutableSet<Entry<K, V>> createEntrySet();

    public abstract V get(@Nullable Object obj);

    abstract boolean isPartialView();

    public static <K, V> ImmutableMap<K, V> of() {
        return ImmutableBiMap.of();
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1) {
        return ImmutableBiMap.of(k1, v1);
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2) {
        return new RegularImmutableMap(entryOf(k1, v1), entryOf(k2, v2));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return new RegularImmutableMap(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return new RegularImmutableMap(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
    }

    public static <K, V> ImmutableMap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return new RegularImmutableMap(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
    }

    static <K, V> TerminalEntry<K, V> entryOf(K key, V value) {
        CollectPreconditions.checkEntryNotNull(key, value);
        return new TerminalEntry(key, value);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    static void checkNoConflict(boolean safe, String conflictDescription, Entry<?, ?> entry1, Entry<?, ?> entry2) {
        if (!safe) {
            throw new IllegalArgumentException("Multiple entries with same " + conflictDescription + ": " + entry1 + " and " + entry2);
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof ImmutableSortedMap)) {
            ImmutableMap<K, V> kvMap = (ImmutableMap) map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        } else if (map instanceof EnumMap) {
            return copyOfEnumMapUnsafe(map);
        }
        Entry[] entries = (Entry[]) map.entrySet().toArray(EMPTY_ENTRY_ARRAY);
        switch (entries.length) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return of();
            case Value.TYPE_FIELD_NUMBER /*1*/:
                Entry<K, V> onlyEntry = entries[0];
                return of(onlyEntry.getKey(), onlyEntry.getValue());
            default:
                return new RegularImmutableMap(entries);
        }
    }

    private static <K, V> ImmutableMap<K, V> copyOfEnumMapUnsafe(Map<? extends K, ? extends V> map) {
        return copyOfEnumMap((EnumMap) map);
    }

    private static <K extends Enum<K>, V> ImmutableMap<K, V> copyOfEnumMap(Map<K, ? extends V> original) {
        EnumMap<K, V> copy = new EnumMap(original);
        for (Entry<?, ?> entry : copy.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(copy);
    }

    static {
        EMPTY_ENTRY_ARRAY = new Entry[0];
    }

    ImmutableMap() {
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@Nullable Object key) {
        return get(key) != null;
    }

    public boolean containsValue(@Nullable Object value) {
        return values().contains(value);
    }

    public ImmutableSet<Entry<K, V>> entrySet() {
        ImmutableSet<Entry<K, V>> immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        immutableSet = createEntrySet();
        this.entrySet = immutableSet;
        return immutableSet;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        immutableSet = createKeySet();
        this.keySet = immutableSet;
        return immutableSet;
    }

    ImmutableSet<K> createKeySet() {
        return new ImmutableMapKeySet(this);
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        immutableCollection = new ImmutableMapValues(this);
        this.values = immutableCollection;
        return immutableCollection;
    }

    @Beta
    public ImmutableSetMultimap<K, V> asMultimap() {
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.multimapView;
        if (immutableSetMultimap != null) {
            return immutableSetMultimap;
        }
        immutableSetMultimap = createMultimapView();
        this.multimapView = immutableSetMultimap;
        return immutableSetMultimap;
    }

    private ImmutableSetMultimap<K, V> createMultimapView() {
        ImmutableMap<K, ImmutableSet<V>> map = viewMapValuesAsSingletonSets();
        return new ImmutableSetMultimap(map, map.size(), null);
    }

    private ImmutableMap<K, ImmutableSet<V>> viewMapValuesAsSingletonSets() {
        return new MapViewOfValuesAsSingletonSets(this);
    }

    public boolean equals(@Nullable Object object) {
        return Maps.equalsImpl(this, object);
    }

    public int hashCode() {
        return entrySet().hashCode();
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    Object writeReplace() {
        return new SerializedForm(this);
    }
}
