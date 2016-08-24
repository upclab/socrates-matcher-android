package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public final class MapConstraints {

    private static class InverseConstraint<K, V> implements MapConstraint<K, V> {
        final MapConstraint<? super V, ? super K> constraint;

        public InverseConstraint(MapConstraint<? super V, ? super K> constraint) {
            this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
        }

        public void checkKeyValue(K key, V value) {
            this.constraint.checkKeyValue(value, key);
        }
    }

    private enum NotNullMapConstraint implements MapConstraint<Object, Object> {
        INSTANCE;

        public void checkKeyValue(Object key, Object value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
        }

        public String toString() {
            return "Not null";
        }
    }

    /* renamed from: com.google.common.collect.MapConstraints.1 */
    static class C09761 extends ForwardingMapEntry<K, V> {
        final /* synthetic */ MapConstraint val$constraint;
        final /* synthetic */ Entry val$entry;

        C09761(Entry entry, MapConstraint mapConstraint) {
            this.val$entry = entry;
            this.val$constraint = mapConstraint;
        }

        protected Entry<K, V> delegate() {
            return this.val$entry;
        }

        public V setValue(V value) {
            this.val$constraint.checkKeyValue(getKey(), value);
            return this.val$entry.setValue(value);
        }
    }

    /* renamed from: com.google.common.collect.MapConstraints.2 */
    static class C09772 extends ForwardingMapEntry<K, Collection<V>> {
        final /* synthetic */ MapConstraint val$constraint;
        final /* synthetic */ Entry val$entry;

        /* renamed from: com.google.common.collect.MapConstraints.2.1 */
        class C06891 implements Constraint<V> {
            C06891() {
            }

            public V checkElement(V value) {
                C09772.this.val$constraint.checkKeyValue(C09772.this.getKey(), value);
                return value;
            }
        }

        C09772(Entry entry, MapConstraint mapConstraint) {
            this.val$entry = entry;
            this.val$constraint = mapConstraint;
        }

        protected Entry<K, Collection<V>> delegate() {
            return this.val$entry;
        }

        public Collection<V> getValue() {
            return Constraints.constrainedTypePreservingCollection((Collection) this.val$entry.getValue(), new C06891());
        }
    }

    private static class ConstrainedAsMapValues<K, V> extends ForwardingCollection<Collection<V>> {
        final Collection<Collection<V>> delegate;
        final Set<Entry<K, Collection<V>>> entrySet;

        /* renamed from: com.google.common.collect.MapConstraints.ConstrainedAsMapValues.1 */
        class C02481 implements Iterator<Collection<V>> {
            final /* synthetic */ Iterator val$iterator;

            C02481(Iterator it) {
                this.val$iterator = it;
            }

            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            public Collection<V> next() {
                return (Collection) ((Entry) this.val$iterator.next()).getValue();
            }

            public void remove() {
                this.val$iterator.remove();
            }
        }

        ConstrainedAsMapValues(Collection<Collection<V>> delegate, Set<Entry<K, Collection<V>>> entrySet) {
            this.delegate = delegate;
            this.entrySet = entrySet;
        }

        protected Collection<Collection<V>> delegate() {
            return this.delegate;
        }

        public Iterator<Collection<V>> iterator() {
            return new C02481(this.entrySet.iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }

        public boolean contains(Object o) {
            return standardContains(o);
        }

        public boolean containsAll(Collection<?> c) {
            return standardContainsAll(c);
        }

        public boolean remove(Object o) {
            return standardRemove(o);
        }

        public boolean removeAll(Collection<?> c) {
            return standardRemoveAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return standardRetainAll(c);
        }
    }

    private static class ConstrainedEntries<K, V> extends ForwardingCollection<Entry<K, V>> {
        final MapConstraint<? super K, ? super V> constraint;
        final Collection<Entry<K, V>> entries;

        /* renamed from: com.google.common.collect.MapConstraints.ConstrainedEntries.1 */
        class C09791 extends ForwardingIterator<Entry<K, V>> {
            final /* synthetic */ Iterator val$iterator;

            C09791(Iterator it) {
                this.val$iterator = it;
            }

            public Entry<K, V> next() {
                return MapConstraints.constrainedEntry((Entry) this.val$iterator.next(), ConstrainedEntries.this.constraint);
            }

            protected Iterator<Entry<K, V>> delegate() {
                return this.val$iterator;
            }
        }

        ConstrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
            this.entries = entries;
            this.constraint = constraint;
        }

        protected Collection<Entry<K, V>> delegate() {
            return this.entries;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new C09791(this.entries.iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }

        public boolean contains(Object o) {
            return Maps.containsEntryImpl(delegate(), o);
        }

        public boolean containsAll(Collection<?> c) {
            return standardContainsAll(c);
        }

        public boolean remove(Object o) {
            return Maps.removeEntryImpl(delegate(), o);
        }

        public boolean removeAll(Collection<?> c) {
            return standardRemoveAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return standardRetainAll(c);
        }
    }

    static class ConstrainedMap<K, V> extends ForwardingMap<K, V> {
        final MapConstraint<? super K, ? super V> constraint;
        private final Map<K, V> delegate;
        private transient Set<Entry<K, V>> entrySet;

        ConstrainedMap(Map<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
            this.delegate = (Map) Preconditions.checkNotNull(delegate);
            this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
        }

        protected Map<K, V> delegate() {
            return this.delegate;
        }

        public Set<Entry<K, V>> entrySet() {
            Set<Entry<K, V>> result = this.entrySet;
            if (result != null) {
                return result;
            }
            result = MapConstraints.constrainedEntrySet(this.delegate.entrySet(), this.constraint);
            this.entrySet = result;
            return result;
        }

        public V put(K key, V value) {
            this.constraint.checkKeyValue(key, value);
            return this.delegate.put(key, value);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            this.delegate.putAll(MapConstraints.checkMap(map, this.constraint));
        }
    }

    private static class ConstrainedMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        transient Map<K, Collection<V>> asMap;
        final MapConstraint<? super K, ? super V> constraint;
        final Multimap<K, V> delegate;
        transient Collection<Entry<K, V>> entries;

        /* renamed from: com.google.common.collect.MapConstraints.ConstrainedMultimap.2 */
        class C06902 implements Constraint<V> {
            final /* synthetic */ Object val$key;

            C06902(Object obj) {
                this.val$key = obj;
            }

            public V checkElement(V value) {
                ConstrainedMultimap.this.constraint.checkKeyValue(this.val$key, value);
                return value;
            }
        }

        /* renamed from: com.google.common.collect.MapConstraints.ConstrainedMultimap.1 */
        class C09801 extends ForwardingMap<K, Collection<V>> {
            Set<Entry<K, Collection<V>>> entrySet;
            final /* synthetic */ Map val$asMapDelegate;
            Collection<Collection<V>> values;

            C09801(Map map) {
                this.val$asMapDelegate = map;
            }

            protected Map<K, Collection<V>> delegate() {
                return this.val$asMapDelegate;
            }

            public Set<Entry<K, Collection<V>>> entrySet() {
                Set<Entry<K, Collection<V>>> result = this.entrySet;
                if (result != null) {
                    return result;
                }
                result = MapConstraints.constrainedAsMapEntries(this.val$asMapDelegate.entrySet(), ConstrainedMultimap.this.constraint);
                this.entrySet = result;
                return result;
            }

            public Collection<V> get(Object key) {
                try {
                    Collection<V> collection = ConstrainedMultimap.this.get(key);
                    if (collection.isEmpty()) {
                        return null;
                    }
                    return collection;
                } catch (ClassCastException e) {
                    return null;
                }
            }

            public Collection<Collection<V>> values() {
                Collection<Collection<V>> result = this.values;
                if (result != null) {
                    return result;
                }
                result = new ConstrainedAsMapValues(delegate().values(), entrySet());
                this.values = result;
                return result;
            }

            public boolean containsValue(Object o) {
                return values().contains(o);
            }
        }

        public ConstrainedMultimap(Multimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
            this.delegate = (Multimap) Preconditions.checkNotNull(delegate);
            this.constraint = (MapConstraint) Preconditions.checkNotNull(constraint);
        }

        protected Multimap<K, V> delegate() {
            return this.delegate;
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> result = this.asMap;
            if (result != null) {
                return result;
            }
            result = new C09801(this.delegate.asMap());
            this.asMap = result;
            return result;
        }

        public Collection<Entry<K, V>> entries() {
            Collection<Entry<K, V>> result = this.entries;
            if (result != null) {
                return result;
            }
            result = MapConstraints.constrainedEntries(this.delegate.entries(), this.constraint);
            this.entries = result;
            return result;
        }

        public Collection<V> get(K key) {
            return Constraints.constrainedTypePreservingCollection(this.delegate.get(key), new C06902(key));
        }

        public boolean put(K key, V value) {
            this.constraint.checkKeyValue(key, value);
            return this.delegate.put(key, value);
        }

        public boolean putAll(K key, Iterable<? extends V> values) {
            return this.delegate.putAll(key, MapConstraints.checkValues(key, values, this.constraint));
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean changed = false;
            for (Entry<? extends K, ? extends V> entry : multimap.entries()) {
                changed |= put(entry.getKey(), entry.getValue());
            }
            return changed;
        }

        public Collection<V> replaceValues(K key, Iterable<? extends V> values) {
            return this.delegate.replaceValues(key, MapConstraints.checkValues(key, values, this.constraint));
        }
    }

    static class ConstrainedAsMapEntries<K, V> extends ForwardingSet<Entry<K, Collection<V>>> {
        private final MapConstraint<? super K, ? super V> constraint;
        private final Set<Entry<K, Collection<V>>> entries;

        /* renamed from: com.google.common.collect.MapConstraints.ConstrainedAsMapEntries.1 */
        class C09781 extends ForwardingIterator<Entry<K, Collection<V>>> {
            final /* synthetic */ Iterator val$iterator;

            C09781(Iterator it) {
                this.val$iterator = it;
            }

            public Entry<K, Collection<V>> next() {
                return MapConstraints.constrainedAsMapEntry((Entry) this.val$iterator.next(), ConstrainedAsMapEntries.this.constraint);
            }

            protected Iterator<Entry<K, Collection<V>>> delegate() {
                return this.val$iterator;
            }
        }

        ConstrainedAsMapEntries(Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
            this.entries = entries;
            this.constraint = constraint;
        }

        protected Set<Entry<K, Collection<V>>> delegate() {
            return this.entries;
        }

        public Iterator<Entry<K, Collection<V>>> iterator() {
            return new C09781(this.entries.iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }

        public boolean contains(Object o) {
            return Maps.containsEntryImpl(delegate(), o);
        }

        public boolean containsAll(Collection<?> c) {
            return standardContainsAll(c);
        }

        public boolean equals(@Nullable Object object) {
            return standardEquals(object);
        }

        public int hashCode() {
            return standardHashCode();
        }

        public boolean remove(Object o) {
            return Maps.removeEntryImpl(delegate(), o);
        }

        public boolean removeAll(Collection<?> c) {
            return standardRemoveAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return standardRetainAll(c);
        }
    }

    private static class ConstrainedBiMap<K, V> extends ConstrainedMap<K, V> implements BiMap<K, V> {
        volatile BiMap<V, K> inverse;

        ConstrainedBiMap(BiMap<K, V> delegate, @Nullable BiMap<V, K> inverse, MapConstraint<? super K, ? super V> constraint) {
            super(delegate, constraint);
            this.inverse = inverse;
        }

        protected BiMap<K, V> delegate() {
            return (BiMap) super.delegate();
        }

        public V forcePut(K key, V value) {
            this.constraint.checkKeyValue(key, value);
            return delegate().forcePut(key, value);
        }

        public BiMap<V, K> inverse() {
            if (this.inverse == null) {
                this.inverse = new ConstrainedBiMap(delegate().inverse(), this, new InverseConstraint(this.constraint));
            }
            return this.inverse;
        }

        public Set<V> values() {
            return delegate().values();
        }
    }

    static class ConstrainedEntrySet<K, V> extends ConstrainedEntries<K, V> implements Set<Entry<K, V>> {
        ConstrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
            super(entries, constraint);
        }

        public boolean equals(@Nullable Object object) {
            return Sets.equalsImpl(this, object);
        }

        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private static class ConstrainedListMultimap<K, V> extends ConstrainedMultimap<K, V> implements ListMultimap<K, V> {
        ConstrainedListMultimap(ListMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
            super(delegate, constraint);
        }

        public List<V> get(K key) {
            return (List) super.get(key);
        }

        public List<V> removeAll(Object key) {
            return (List) super.removeAll(key);
        }

        public List<V> replaceValues(K key, Iterable<? extends V> values) {
            return (List) super.replaceValues(key, values);
        }
    }

    private static class ConstrainedSetMultimap<K, V> extends ConstrainedMultimap<K, V> implements SetMultimap<K, V> {
        ConstrainedSetMultimap(SetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
            super(delegate, constraint);
        }

        public Set<V> get(K key) {
            return (Set) super.get(key);
        }

        public Set<Entry<K, V>> entries() {
            return (Set) super.entries();
        }

        public Set<V> removeAll(Object key) {
            return (Set) super.removeAll(key);
        }

        public Set<V> replaceValues(K key, Iterable<? extends V> values) {
            return (Set) super.replaceValues(key, values);
        }
    }

    private static class ConstrainedSortedSetMultimap<K, V> extends ConstrainedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        ConstrainedSortedSetMultimap(SortedSetMultimap<K, V> delegate, MapConstraint<? super K, ? super V> constraint) {
            super(delegate, constraint);
        }

        public SortedSet<V> get(K key) {
            return (SortedSet) super.get((Object) key);
        }

        public SortedSet<V> removeAll(Object key) {
            return (SortedSet) super.removeAll(key);
        }

        public SortedSet<V> replaceValues(K key, Iterable<? extends V> values) {
            return (SortedSet) super.replaceValues((Object) key, (Iterable) values);
        }

        public Comparator<? super V> valueComparator() {
            return ((SortedSetMultimap) delegate()).valueComparator();
        }
    }

    private MapConstraints() {
    }

    public static MapConstraint<Object, Object> notNull() {
        return NotNullMapConstraint.INSTANCE;
    }

    public static <K, V> Map<K, V> constrainedMap(Map<K, V> map, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedMap(map, constraint);
    }

    public static <K, V> Multimap<K, V> constrainedMultimap(Multimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedMultimap(multimap, constraint);
    }

    public static <K, V> ListMultimap<K, V> constrainedListMultimap(ListMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedListMultimap(multimap, constraint);
    }

    public static <K, V> SetMultimap<K, V> constrainedSetMultimap(SetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedSetMultimap(multimap, constraint);
    }

    public static <K, V> SortedSetMultimap<K, V> constrainedSortedSetMultimap(SortedSetMultimap<K, V> multimap, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedSortedSetMultimap(multimap, constraint);
    }

    private static <K, V> Entry<K, V> constrainedEntry(Entry<K, V> entry, MapConstraint<? super K, ? super V> constraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(constraint);
        return new C09761(entry, constraint);
    }

    private static <K, V> Entry<K, Collection<V>> constrainedAsMapEntry(Entry<K, Collection<V>> entry, MapConstraint<? super K, ? super V> constraint) {
        Preconditions.checkNotNull(entry);
        Preconditions.checkNotNull(constraint);
        return new C09772(entry, constraint);
    }

    private static <K, V> Set<Entry<K, Collection<V>>> constrainedAsMapEntries(Set<Entry<K, Collection<V>>> entries, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedAsMapEntries(entries, constraint);
    }

    private static <K, V> Collection<Entry<K, V>> constrainedEntries(Collection<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        if (entries instanceof Set) {
            return constrainedEntrySet((Set) entries, constraint);
        }
        return new ConstrainedEntries(entries, constraint);
    }

    private static <K, V> Set<Entry<K, V>> constrainedEntrySet(Set<Entry<K, V>> entries, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedEntrySet(entries, constraint);
    }

    public static <K, V> BiMap<K, V> constrainedBiMap(BiMap<K, V> map, MapConstraint<? super K, ? super V> constraint) {
        return new ConstrainedBiMap(map, null, constraint);
    }

    private static <K, V> Collection<V> checkValues(K key, Iterable<? extends V> values, MapConstraint<? super K, ? super V> constraint) {
        Collection<V> copy = Lists.newArrayList((Iterable) values);
        for (V value : copy) {
            constraint.checkKeyValue(key, value);
        }
        return copy;
    }

    private static <K, V> Map<K, V> checkMap(Map<? extends K, ? extends V> map, MapConstraint<? super K, ? super V> constraint) {
        Map<K, V> copy = new LinkedHashMap(map);
        for (Entry<K, V> entry : copy.entrySet()) {
            constraint.checkKeyValue(entry.getKey(), entry.getValue());
        }
        return copy;
    }
}
