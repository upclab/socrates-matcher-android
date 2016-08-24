package com.google.tagmanager.protobuf;

import com.google.tagmanager.protobuf.FieldSet.FieldDescriptorLite;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

class SmallSortedMap<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private List<Entry> entryList;
    private boolean isImmutable;
    private volatile EntrySet lazyEntrySet;
    private final int maxArraySize;
    private Map<K, V> overflowEntries;

    private static class EmptySet {
        private static final Iterable<Object> ITERABLE;
        private static final Iterator<Object> ITERATOR;

        /* renamed from: com.google.tagmanager.protobuf.SmallSortedMap.EmptySet.1 */
        static class C03541 implements Iterator<Object> {
            C03541() {
            }

            public boolean hasNext() {
                return false;
            }

            public Object next() {
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        /* renamed from: com.google.tagmanager.protobuf.SmallSortedMap.EmptySet.2 */
        static class C03552 implements Iterable<Object> {
            C03552() {
            }

            public Iterator<Object> iterator() {
                return EmptySet.ITERATOR;
            }
        }

        private EmptySet() {
        }

        static {
            ITERATOR = new C03541();
            ITERABLE = new C03552();
        }

        static <T> Iterable<T> iterable() {
            return ITERABLE;
        }
    }

    private class Entry implements java.util.Map.Entry<K, V>, Comparable<Entry> {
        private final K key;
        private V value;

        Entry(SmallSortedMap smallSortedMap, java.util.Map.Entry<K, V> copy) {
            this((Comparable) copy.getKey(), copy.getValue());
        }

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public int compareTo(Entry other) {
            return getKey().compareTo(other.getKey());
        }

        public V setValue(V newValue) {
            SmallSortedMap.this.checkMutable();
            V oldValue = this.value;
            this.value = newValue;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof java.util.Map.Entry)) {
                return false;
            }
            java.util.Map.Entry<?, ?> other = (java.util.Map.Entry) o;
            if (equals(this.key, other.getKey()) && equals(this.value, other.getValue())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.key == null ? 0 : this.key.hashCode();
            if (this.value != null) {
                i = this.value.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.key + "=" + this.value;
        }

        private boolean equals(Object o1, Object o2) {
            if (o1 == null) {
                return o2 == null;
            } else {
                return o1.equals(o2);
            }
        }
    }

    private class EntryIterator implements Iterator<java.util.Map.Entry<K, V>> {
        private Iterator<java.util.Map.Entry<K, V>> lazyOverflowIterator;
        private boolean nextCalledBeforeRemove;
        private int pos;

        private EntryIterator() {
            this.pos = -1;
        }

        public boolean hasNext() {
            return this.pos + 1 < SmallSortedMap.this.entryList.size() || getOverflowIterator().hasNext();
        }

        public java.util.Map.Entry<K, V> next() {
            this.nextCalledBeforeRemove = true;
            int i = this.pos + 1;
            this.pos = i;
            if (i < SmallSortedMap.this.entryList.size()) {
                return (java.util.Map.Entry) SmallSortedMap.this.entryList.get(this.pos);
            }
            return (java.util.Map.Entry) getOverflowIterator().next();
        }

        public void remove() {
            if (this.nextCalledBeforeRemove) {
                this.nextCalledBeforeRemove = false;
                SmallSortedMap.this.checkMutable();
                if (this.pos < SmallSortedMap.this.entryList.size()) {
                    SmallSortedMap smallSortedMap = SmallSortedMap.this;
                    int i = this.pos;
                    this.pos = i - 1;
                    smallSortedMap.removeArrayEntryAt(i);
                    return;
                }
                getOverflowIterator().remove();
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }

        private Iterator<java.util.Map.Entry<K, V>> getOverflowIterator() {
            if (this.lazyOverflowIterator == null) {
                this.lazyOverflowIterator = SmallSortedMap.this.overflowEntries.entrySet().iterator();
            }
            return this.lazyOverflowIterator;
        }
    }

    private class EntrySet extends AbstractSet<java.util.Map.Entry<K, V>> {
        private EntrySet() {
        }

        public Iterator<java.util.Map.Entry<K, V>> iterator() {
            return new EntryIterator(null);
        }

        public int size() {
            return SmallSortedMap.this.size();
        }

        public boolean contains(Object o) {
            java.util.Map.Entry<K, V> entry = (java.util.Map.Entry) o;
            V existing = SmallSortedMap.this.get(entry.getKey());
            V value = entry.getValue();
            return existing == value || (existing != null && existing.equals(value));
        }

        public boolean add(java.util.Map.Entry<K, V> entry) {
            if (contains(entry)) {
                return false;
            }
            SmallSortedMap.this.put((Comparable) entry.getKey(), entry.getValue());
            return true;
        }

        public boolean remove(Object o) {
            java.util.Map.Entry<K, V> entry = (java.util.Map.Entry) o;
            if (!contains(entry)) {
                return false;
            }
            SmallSortedMap.this.remove(entry.getKey());
            return true;
        }

        public void clear() {
            SmallSortedMap.this.clear();
        }
    }

    /* renamed from: com.google.tagmanager.protobuf.SmallSortedMap.1 */
    static class C08531 extends SmallSortedMap<FieldDescriptorType, Object> {
        C08531(int x0) {
            super(null);
        }

        public /* bridge */ /* synthetic */ Object put(Object x0, Object x1) {
            return super.put((FieldDescriptorLite) x0, x1);
        }

        public void makeImmutable() {
            if (!isImmutable()) {
                java.util.Map.Entry<FieldDescriptorType, Object> entry;
                for (int i = 0; i < getNumArrayEntries(); i++) {
                    entry = getArrayEntryAt(i);
                    if (((FieldDescriptorLite) entry.getKey()).isRepeated()) {
                        entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                    }
                }
                for (java.util.Map.Entry<FieldDescriptorType, Object> entry2 : getOverflowEntries()) {
                    if (((FieldDescriptorLite) entry2.getKey()).isRepeated()) {
                        entry2.setValue(Collections.unmodifiableList((List) entry2.getValue()));
                    }
                }
            }
            super.makeImmutable();
        }
    }

    static <FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>> SmallSortedMap<FieldDescriptorType, Object> newFieldMap(int arraySize) {
        return new C08531(arraySize);
    }

    static <K extends Comparable<K>, V> SmallSortedMap<K, V> newInstanceForTest(int arraySize) {
        return new SmallSortedMap(arraySize);
    }

    private SmallSortedMap(int arraySize) {
        this.maxArraySize = arraySize;
        this.entryList = Collections.emptyList();
        this.overflowEntries = Collections.emptyMap();
    }

    public void makeImmutable() {
        if (!this.isImmutable) {
            this.overflowEntries = this.overflowEntries.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.overflowEntries);
            this.isImmutable = true;
        }
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public int getNumArrayEntries() {
        return this.entryList.size();
    }

    public java.util.Map.Entry<K, V> getArrayEntryAt(int index) {
        return (java.util.Map.Entry) this.entryList.get(index);
    }

    public int getNumOverflowEntries() {
        return this.overflowEntries.size();
    }

    public Iterable<java.util.Map.Entry<K, V>> getOverflowEntries() {
        return this.overflowEntries.isEmpty() ? EmptySet.iterable() : this.overflowEntries.entrySet();
    }

    public int size() {
        return this.entryList.size() + this.overflowEntries.size();
    }

    public boolean containsKey(Object o) {
        Comparable key = (Comparable) o;
        return binarySearchInArray(key) >= 0 || this.overflowEntries.containsKey(key);
    }

    public V get(Object o) {
        Comparable key = (Comparable) o;
        int index = binarySearchInArray(key);
        if (index >= 0) {
            return ((Entry) this.entryList.get(index)).getValue();
        }
        return this.overflowEntries.get(key);
    }

    public V put(K key, V value) {
        checkMutable();
        int index = binarySearchInArray(key);
        if (index >= 0) {
            return ((Entry) this.entryList.get(index)).setValue(value);
        }
        ensureEntryArrayMutable();
        int insertionPoint = -(index + 1);
        if (insertionPoint >= this.maxArraySize) {
            return getOverflowEntriesMutable().put(key, value);
        }
        if (this.entryList.size() == this.maxArraySize) {
            Entry lastEntryInArray = (Entry) this.entryList.remove(this.maxArraySize - 1);
            getOverflowEntriesMutable().put(lastEntryInArray.getKey(), lastEntryInArray.getValue());
        }
        this.entryList.add(insertionPoint, new Entry(key, value));
        return null;
    }

    public void clear() {
        checkMutable();
        if (!this.entryList.isEmpty()) {
            this.entryList.clear();
        }
        if (!this.overflowEntries.isEmpty()) {
            this.overflowEntries.clear();
        }
    }

    public V remove(Object o) {
        checkMutable();
        Comparable key = (Comparable) o;
        int index = binarySearchInArray(key);
        if (index >= 0) {
            return removeArrayEntryAt(index);
        }
        if (this.overflowEntries.isEmpty()) {
            return null;
        }
        return this.overflowEntries.remove(key);
    }

    private V removeArrayEntryAt(int index) {
        checkMutable();
        V removed = ((Entry) this.entryList.remove(index)).getValue();
        if (!this.overflowEntries.isEmpty()) {
            Iterator<java.util.Map.Entry<K, V>> iterator = getOverflowEntriesMutable().entrySet().iterator();
            this.entryList.add(new Entry(this, (java.util.Map.Entry) iterator.next()));
            iterator.remove();
        }
        return removed;
    }

    private int binarySearchInArray(K key) {
        int cmp;
        int left = 0;
        int right = this.entryList.size() - 1;
        if (right >= 0) {
            cmp = key.compareTo(((Entry) this.entryList.get(right)).getKey());
            if (cmp > 0) {
                return -(right + 2);
            }
            if (cmp == 0) {
                return right;
            }
        }
        while (left <= right) {
            int mid = (left + right) / 2;
            cmp = key.compareTo(((Entry) this.entryList.get(mid)).getKey());
            if (cmp < 0) {
                right = mid - 1;
            } else if (cmp <= 0) {
                return mid;
            } else {
                left = mid + 1;
            }
        }
        return -(left + 1);
    }

    public Set<java.util.Map.Entry<K, V>> entrySet() {
        if (this.lazyEntrySet == null) {
            this.lazyEntrySet = new EntrySet();
        }
        return this.lazyEntrySet;
    }

    private void checkMutable() {
        if (this.isImmutable) {
            throw new UnsupportedOperationException();
        }
    }

    private SortedMap<K, V> getOverflowEntriesMutable() {
        checkMutable();
        if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
            this.overflowEntries = new TreeMap();
        }
        return (SortedMap) this.overflowEntries;
    }

    private void ensureEntryArrayMutable() {
        checkMutable();
        if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
            this.entryList = new ArrayList(this.maxArraySize);
        }
    }
}
