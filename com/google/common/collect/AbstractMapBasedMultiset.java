package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset.Entry;
import com.google.common.primitives.Ints;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
abstract class AbstractMapBasedMultiset<E> extends AbstractMultiset<E> implements Serializable {
    @GwtIncompatible("not needed in emulated source.")
    private static final long serialVersionUID = -2250766705698539974L;
    private transient Map<E, Count> backingMap;
    private transient long size;

    /* renamed from: com.google.common.collect.AbstractMapBasedMultiset.1 */
    class C02271 implements Iterator<Entry<E>> {
        Map.Entry<E, Count> toRemove;
        final /* synthetic */ Iterator val$backingEntries;

        /* renamed from: com.google.common.collect.AbstractMapBasedMultiset.1.1 */
        class C09471 extends AbstractEntry<E> {
            final /* synthetic */ Map.Entry val$mapEntry;

            C09471(Map.Entry entry) {
                this.val$mapEntry = entry;
            }

            public E getElement() {
                return this.val$mapEntry.getKey();
            }

            public int getCount() {
                Count count = (Count) this.val$mapEntry.getValue();
                if (count == null || count.get() == 0) {
                    Count frequency = (Count) AbstractMapBasedMultiset.this.backingMap.get(getElement());
                    if (frequency != null) {
                        return frequency.get();
                    }
                }
                return count == null ? 0 : count.get();
            }
        }

        C02271(Iterator it) {
            this.val$backingEntries = it;
        }

        public boolean hasNext() {
            return this.val$backingEntries.hasNext();
        }

        public Entry<E> next() {
            Map.Entry<E, Count> mapEntry = (Map.Entry) this.val$backingEntries.next();
            this.toRemove = mapEntry;
            return new C09471(mapEntry);
        }

        public void remove() {
            boolean z;
            if (this.toRemove != null) {
                z = true;
            } else {
                z = false;
            }
            CollectPreconditions.checkRemove(z);
            AbstractMapBasedMultiset.access$122(AbstractMapBasedMultiset.this, (long) ((Count) this.toRemove.getValue()).getAndSet(0));
            this.val$backingEntries.remove();
            this.toRemove = null;
        }
    }

    private class MapBasedMultisetIterator implements Iterator<E> {
        boolean canRemove;
        Map.Entry<E, Count> currentEntry;
        final Iterator<Map.Entry<E, Count>> entryIterator;
        int occurrencesLeft;

        MapBasedMultisetIterator() {
            this.entryIterator = AbstractMapBasedMultiset.this.backingMap.entrySet().iterator();
        }

        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }

        public E next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = (Map.Entry) this.entryIterator.next();
                this.occurrencesLeft = ((Count) this.currentEntry.getValue()).get();
            }
            this.occurrencesLeft--;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (((Count) this.currentEntry.getValue()).get() <= 0) {
                throw new ConcurrentModificationException();
            }
            if (((Count) this.currentEntry.getValue()).addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.this.size = AbstractMapBasedMultiset.this.size - 1;
            this.canRemove = false;
        }
    }

    static /* synthetic */ long access$122(AbstractMapBasedMultiset x0, long x1) {
        long j = x0.size - x1;
        x0.size = j;
        return j;
    }

    protected AbstractMapBasedMultiset(Map<E, Count> backingMap) {
        this.backingMap = (Map) Preconditions.checkNotNull(backingMap);
        this.size = (long) super.size();
    }

    void setBackingMap(Map<E, Count> backingMap) {
        this.backingMap = backingMap;
    }

    public Set<Entry<E>> entrySet() {
        return super.entrySet();
    }

    Iterator<Entry<E>> entryIterator() {
        return new C02271(this.backingMap.entrySet().iterator());
    }

    public void clear() {
        for (Count frequency : this.backingMap.values()) {
            frequency.set(0);
        }
        this.backingMap.clear();
        this.size = 0;
    }

    int distinctElements() {
        return this.backingMap.size();
    }

    public int size() {
        return Ints.saturatedCast(this.size);
    }

    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator();
    }

    public int count(@Nullable Object element) {
        Count frequency = (Count) Maps.safeGet(this.backingMap, element);
        return frequency == null ? 0 : frequency.get();
    }

    public int add(@Nullable E element, int occurrences) {
        if (occurrences == 0) {
            return count(element);
        }
        boolean z;
        int oldCount;
        if (occurrences > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "occurrences cannot be negative: %s", Integer.valueOf(occurrences));
        Count frequency = (Count) this.backingMap.get(element);
        if (frequency == null) {
            oldCount = 0;
            this.backingMap.put(element, new Count(occurrences));
        } else {
            oldCount = frequency.get();
            if (((long) oldCount) + ((long) occurrences) <= 2147483647L) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "too many occurrences: %s", Long.valueOf(((long) oldCount) + ((long) occurrences)));
            frequency.getAndAdd(occurrences);
        }
        this.size += (long) occurrences;
        return oldCount;
    }

    public int remove(@Nullable Object element, int occurrences) {
        if (occurrences == 0) {
            return count(element);
        }
        boolean z;
        if (occurrences > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "occurrences cannot be negative: %s", Integer.valueOf(occurrences));
        Count frequency = (Count) this.backingMap.get(element);
        if (frequency == null) {
            return 0;
        }
        int numberRemoved;
        int oldCount = frequency.get();
        if (oldCount > occurrences) {
            numberRemoved = occurrences;
        } else {
            numberRemoved = oldCount;
            this.backingMap.remove(element);
        }
        frequency.addAndGet(-numberRemoved);
        this.size -= (long) numberRemoved;
        return oldCount;
    }

    public int setCount(@Nullable E element, int count) {
        int oldCount;
        CollectPreconditions.checkNonnegative(count, "count");
        if (count == 0) {
            oldCount = getAndSet((Count) this.backingMap.remove(element), count);
        } else {
            Count existingCounter = (Count) this.backingMap.get(element);
            oldCount = getAndSet(existingCounter, count);
            if (existingCounter == null) {
                this.backingMap.put(element, new Count(count));
            }
        }
        this.size += (long) (count - oldCount);
        return oldCount;
    }

    private static int getAndSet(Count i, int count) {
        if (i == null) {
            return 0;
        }
        return i.getAndSet(count);
    }

    @GwtIncompatible("java.io.ObjectStreamException")
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
}
