package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Iterators {
    static final UnmodifiableListIterator<Object> EMPTY_LIST_ITERATOR;
    private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR;

    /* renamed from: com.google.common.collect.Iterators.14 */
    static class AnonymousClass14 implements Enumeration<T> {
        final /* synthetic */ Iterator val$iterator;

        AnonymousClass14(Iterator it) {
            this.val$iterator = it;
        }

        public boolean hasMoreElements() {
            return this.val$iterator.hasNext();
        }

        public T nextElement() {
            return this.val$iterator.next();
        }
    }

    /* renamed from: com.google.common.collect.Iterators.2 */
    static class C02372 implements Iterator<Object> {
        C02372() {
        }

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(false);
        }
    }

    /* renamed from: com.google.common.collect.Iterators.4 */
    static class C02384 implements Iterator<T> {
        Iterator<T> iterator;
        Iterator<T> removeFrom;
        final /* synthetic */ Iterable val$iterable;

        C02384(Iterable iterable) {
            this.val$iterable = iterable;
            this.iterator = Iterators.emptyIterator();
        }

        public boolean hasNext() {
            if (!this.iterator.hasNext()) {
                this.iterator = this.val$iterable.iterator();
            }
            return this.iterator.hasNext();
        }

        public T next() {
            if (hasNext()) {
                this.removeFrom = this.iterator;
                return this.iterator.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.removeFrom != null);
            this.removeFrom.remove();
            this.removeFrom = null;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.5 */
    static class C02395 implements Iterator<T> {
        Iterator<? extends T> current;
        Iterator<? extends T> removeFrom;
        final /* synthetic */ Iterator val$inputs;

        C02395(Iterator it) {
            this.val$inputs = it;
            this.current = Iterators.emptyIterator();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean hasNext() {
            /*
            r2 = this;
        L_0x0000:
            r1 = r2.current;
            r1 = com.google.common.base.Preconditions.checkNotNull(r1);
            r1 = (java.util.Iterator) r1;
            r0 = r1.hasNext();
            if (r0 != 0) goto L_0x0021;
        L_0x000e:
            r1 = r2.val$inputs;
            r1 = r1.hasNext();
            if (r1 == 0) goto L_0x0021;
        L_0x0016:
            r1 = r2.val$inputs;
            r1 = r1.next();
            r1 = (java.util.Iterator) r1;
            r2.current = r1;
            goto L_0x0000;
        L_0x0021:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Iterators.5.hasNext():boolean");
        }

        public T next() {
            if (hasNext()) {
                this.removeFrom = this.current;
                return this.current.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            CollectPreconditions.checkRemove(this.removeFrom != null);
            this.removeFrom.remove();
            this.removeFrom = null;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.9 */
    static class C02409 implements Iterator<T> {
        private int count;
        final /* synthetic */ Iterator val$iterator;
        final /* synthetic */ int val$limitSize;

        C02409(int i, Iterator it) {
            this.val$limitSize = i;
            this.val$iterator = it;
        }

        public boolean hasNext() {
            return this.count < this.val$limitSize && this.val$iterator.hasNext();
        }

        public T next() {
            if (hasNext()) {
                this.count++;
                return this.val$iterator.next();
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            this.val$iterator.remove();
        }
    }

    /* renamed from: com.google.common.collect.Iterators.10 */
    static class AnonymousClass10 extends UnmodifiableIterator<T> {
        final /* synthetic */ Iterator val$iterator;

        AnonymousClass10(Iterator it) {
            this.val$iterator = it;
        }

        public boolean hasNext() {
            return this.val$iterator.hasNext();
        }

        public T next() {
            T next = this.val$iterator.next();
            this.val$iterator.remove();
            return next;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.12 */
    static class AnonymousClass12 extends UnmodifiableIterator<T> {
        boolean done;
        final /* synthetic */ Object val$value;

        AnonymousClass12(Object obj) {
            this.val$value = obj;
        }

        public boolean hasNext() {
            return !this.done;
        }

        public T next() {
            if (this.done) {
                throw new NoSuchElementException();
            }
            this.done = true;
            return this.val$value;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.13 */
    static class AnonymousClass13 extends UnmodifiableIterator<T> {
        final /* synthetic */ Enumeration val$enumeration;

        AnonymousClass13(Enumeration enumeration) {
            this.val$enumeration = enumeration;
        }

        public boolean hasNext() {
            return this.val$enumeration.hasMoreElements();
        }

        public T next() {
            return this.val$enumeration.nextElement();
        }
    }

    /* renamed from: com.google.common.collect.Iterators.3 */
    static class C06843 extends UnmodifiableIterator<T> {
        final /* synthetic */ Iterator val$iterator;

        C06843(Iterator it) {
            this.val$iterator = it;
        }

        public boolean hasNext() {
            return this.val$iterator.hasNext();
        }

        public T next() {
            return this.val$iterator.next();
        }
    }

    /* renamed from: com.google.common.collect.Iterators.6 */
    static class C06856 extends UnmodifiableIterator<List<T>> {
        final /* synthetic */ Iterator val$iterator;
        final /* synthetic */ boolean val$pad;
        final /* synthetic */ int val$size;

        C06856(Iterator it, int i, boolean z) {
            this.val$iterator = it;
            this.val$size = i;
            this.val$pad = z;
        }

        public boolean hasNext() {
            return this.val$iterator.hasNext();
        }

        public List<T> next() {
            if (hasNext()) {
                Object[] array = new Object[this.val$size];
                int count = 0;
                while (count < this.val$size && this.val$iterator.hasNext()) {
                    array[count] = this.val$iterator.next();
                    count++;
                }
                for (int i = count; i < this.val$size; i++) {
                    array[i] = null;
                }
                List<T> list = Collections.unmodifiableList(Arrays.asList(array));
                return (this.val$pad || count == this.val$size) ? list : list.subList(0, count);
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    /* renamed from: com.google.common.collect.Iterators.8 */
    static class C06868 extends TransformedIterator<F, T> {
        final /* synthetic */ Function val$function;

        C06868(Iterator x0, Function function) {
            this.val$function = function;
            super(x0);
        }

        T transform(F from) {
            return this.val$function.apply(from);
        }
    }

    private static class MergingIterator<T> extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> queue;

        /* renamed from: com.google.common.collect.Iterators.MergingIterator.1 */
        class C02411 implements Comparator<PeekingIterator<T>> {
            final /* synthetic */ Comparator val$itemComparator;

            C02411(Comparator comparator) {
                this.val$itemComparator = comparator;
            }

            public int compare(PeekingIterator<T> o1, PeekingIterator<T> o2) {
                return this.val$itemComparator.compare(o1.peek(), o2.peek());
            }
        }

        public MergingIterator(Iterable<? extends Iterator<? extends T>> iterators, Comparator<? super T> itemComparator) {
            this.queue = new PriorityQueue(2, new C02411(itemComparator));
            for (Iterator iterator : iterators) {
                if (iterator.hasNext()) {
                    this.queue.add(Iterators.peekingIterator(iterator));
                }
            }
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public T next() {
            PeekingIterator<T> nextIter = (PeekingIterator) this.queue.remove();
            T next = nextIter.next();
            if (nextIter.hasNext()) {
                this.queue.add(nextIter);
            }
            return next;
        }
    }

    private static class PeekingImpl<E> implements PeekingIterator<E> {
        private boolean hasPeeked;
        private final Iterator<? extends E> iterator;
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> iterator) {
            this.iterator = (Iterator) Preconditions.checkNotNull(iterator);
        }

        public boolean hasNext() {
            return this.hasPeeked || this.iterator.hasNext();
        }

        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E result = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return result;
        }

        public void remove() {
            Preconditions.checkState(!this.hasPeeked, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }

        public E peek() {
            if (!this.hasPeeked) {
                this.peekedElement = this.iterator.next();
                this.hasPeeked = true;
            }
            return this.peekedElement;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.1 */
    static class C09701 extends UnmodifiableListIterator<Object> {
        C09701() {
        }

        public boolean hasNext() {
            return false;
        }

        public Object next() {
            throw new NoSuchElementException();
        }

        public boolean hasPrevious() {
            return false;
        }

        public Object previous() {
            throw new NoSuchElementException();
        }

        public int nextIndex() {
            return 0;
        }

        public int previousIndex() {
            return -1;
        }
    }

    /* renamed from: com.google.common.collect.Iterators.7 */
    static class C09717 extends AbstractIterator<T> {
        final /* synthetic */ Predicate val$predicate;
        final /* synthetic */ Iterator val$unfiltered;

        C09717(Iterator it, Predicate predicate) {
            this.val$unfiltered = it;
            this.val$predicate = predicate;
        }

        protected T computeNext() {
            while (this.val$unfiltered.hasNext()) {
                T element = this.val$unfiltered.next();
                if (this.val$predicate.apply(element)) {
                    return element;
                }
            }
            return endOfData();
        }
    }

    /* renamed from: com.google.common.collect.Iterators.11 */
    static class AnonymousClass11 extends AbstractIndexedListIterator<T> {
        final /* synthetic */ Object[] val$array;
        final /* synthetic */ int val$offset;

        AnonymousClass11(int x0, int x1, Object[] objArr, int i) {
            this.val$array = objArr;
            this.val$offset = i;
            super(x0, x1);
        }

        protected T get(int index) {
            return this.val$array[this.val$offset + index];
        }
    }

    private Iterators() {
    }

    static {
        EMPTY_LIST_ITERATOR = new C09701();
        EMPTY_MODIFIABLE_ITERATOR = new C02372();
    }

    public static <T> UnmodifiableIterator<T> emptyIterator() {
        return emptyListIterator();
    }

    static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return EMPTY_LIST_ITERATOR;
    }

    static <T> Iterator<T> emptyModifiableIterator() {
        return EMPTY_MODIFIABLE_ITERATOR;
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        if (iterator instanceof UnmodifiableIterator) {
            return (UnmodifiableIterator) iterator;
        }
        return new C06843(iterator);
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> iterator) {
        return (UnmodifiableIterator) Preconditions.checkNotNull(iterator);
    }

    public static int size(Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static boolean contains(Iterator<?> iterator, @Nullable Object element) {
        return any(iterator, Predicates.equalTo(element));
    }

    public static boolean removeAll(Iterator<?> removeFrom, Collection<?> elementsToRemove) {
        return removeIf(removeFrom, Predicates.in(elementsToRemove));
    }

    public static <T> boolean removeIf(Iterator<T> removeFrom, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean modified = false;
        while (removeFrom.hasNext()) {
            if (predicate.apply(removeFrom.next())) {
                removeFrom.remove();
                modified = true;
            }
        }
        return modified;
    }

    public static boolean retainAll(Iterator<?> removeFrom, Collection<?> elementsToRetain) {
        return removeIf(removeFrom, Predicates.not(Predicates.in(elementsToRetain)));
    }

    public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
        while (iterator1.hasNext()) {
            if (!iterator2.hasNext()) {
                return false;
            }
            if (!Objects.equal(iterator1.next(), iterator2.next())) {
                return false;
            }
        }
        if (iterator2.hasNext()) {
            return false;
        }
        return true;
    }

    public static String toString(Iterator<?> iterator) {
        return Collections2.STANDARD_JOINER.appendTo(new StringBuilder().append('['), (Iterator) iterator).append(']').toString();
    }

    public static <T> T getOnlyElement(Iterator<T> iterator) {
        T first = iterator.next();
        if (!iterator.hasNext()) {
            return first;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected one element but was: <" + first);
        for (int i = 0; i < 4 && iterator.hasNext(); i++) {
            sb.append(", " + iterator.next());
        }
        if (iterator.hasNext()) {
            sb.append(", ...");
        }
        sb.append('>');
        throw new IllegalArgumentException(sb.toString());
    }

    @Nullable
    public static <T> T getOnlyElement(Iterator<? extends T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? getOnlyElement(iterator) : defaultValue;
    }

    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] toArray(Iterator<? extends T> iterator, Class<T> type) {
        return Iterables.toArray(Lists.newArrayList((Iterator) iterator), type);
    }

    public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        Preconditions.checkNotNull(addTo);
        Preconditions.checkNotNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    public static int frequency(Iterator<?> iterator, @Nullable Object element) {
        return size(filter((Iterator) iterator, Predicates.equalTo(element)));
    }

    public static <T> Iterator<T> cycle(Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new C02384(iterable);
    }

    public static <T> Iterator<T> cycle(T... elements) {
        return cycle(Lists.newArrayList((Object[]) elements));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b) {
        return concat(ImmutableList.of(a, b).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c) {
        return concat(ImmutableList.of(a, b, c).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b, Iterator<? extends T> c, Iterator<? extends T> d) {
        return concat(ImmutableList.of(a, b, c, d).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends T>... inputs) {
        return concat(ImmutableList.copyOf((Object[]) inputs).iterator());
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> inputs) {
        Preconditions.checkNotNull(inputs);
        return new C02395(inputs);
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator, int size) {
        return partitionImpl(iterator, size, false);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator, int size) {
        return partitionImpl(iterator, size, true);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(Iterator<T> iterator, int size, boolean pad) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(size > 0);
        return new C06856(iterator, size, pad);
    }

    public static <T> UnmodifiableIterator<T> filter(Iterator<T> unfiltered, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(predicate);
        return new C09717(unfiltered, predicate);
    }

    @GwtIncompatible("Class.isInstance")
    public static <T> UnmodifiableIterator<T> filter(Iterator<?> unfiltered, Class<T> type) {
        return filter((Iterator) unfiltered, Predicates.instanceOf(type));
    }

    public static <T> boolean any(Iterator<T> iterator, Predicate<? super T> predicate) {
        return indexOf(iterator, predicate) != -1;
    }

    public static <T> boolean all(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.apply(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> T find(Iterator<T> iterator, Predicate<? super T> predicate) {
        return filter((Iterator) iterator, (Predicate) predicate).next();
    }

    @Nullable
    public static <T> T find(Iterator<? extends T> iterator, Predicate<? super T> predicate, @Nullable T defaultValue) {
        return getNext(filter((Iterator) iterator, (Predicate) predicate), defaultValue);
    }

    public static <T> Optional<T> tryFind(Iterator<T> iterator, Predicate<? super T> predicate) {
        UnmodifiableIterator<T> filteredIterator = filter((Iterator) iterator, (Predicate) predicate);
        return filteredIterator.hasNext() ? Optional.of(filteredIterator.next()) : Optional.absent();
    }

    public static <T> int indexOf(Iterator<T> iterator, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int i = 0;
        while (iterator.hasNext()) {
            if (predicate.apply(iterator.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <F, T> Iterator<T> transform(Iterator<F> fromIterator, Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new C06868(fromIterator, function);
    }

    public static <T> T get(Iterator<T> iterator, int position) {
        checkNonnegative(position);
        int skipped = advance(iterator, position);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new IndexOutOfBoundsException("position (" + position + ") must be less than the number of elements that remained (" + skipped + ")");
    }

    static void checkNonnegative(int position) {
        if (position < 0) {
            throw new IndexOutOfBoundsException("position (" + position + ") must not be negative");
        }
    }

    @Nullable
    public static <T> T get(Iterator<? extends T> iterator, int position, @Nullable T defaultValue) {
        checkNonnegative(position);
        advance(iterator, position);
        return getNext(iterator, defaultValue);
    }

    @Nullable
    public static <T> T getNext(Iterator<? extends T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    public static <T> T getLast(Iterator<T> iterator) {
        T current;
        do {
            current = iterator.next();
        } while (iterator.hasNext());
        return current;
    }

    @Nullable
    public static <T> T getLast(Iterator<? extends T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? getLast(iterator) : defaultValue;
    }

    public static int advance(Iterator<?> iterator, int numberToAdvance) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(numberToAdvance >= 0, "numberToAdvance must be nonnegative");
        int i = 0;
        while (i < numberToAdvance && iterator.hasNext()) {
            iterator.next();
            i++;
        }
        return i;
    }

    public static <T> Iterator<T> limit(Iterator<T> iterator, int limitSize) {
        Preconditions.checkNotNull(iterator);
        Preconditions.checkArgument(limitSize >= 0, "limit is negative");
        return new C02409(limitSize, iterator);
    }

    public static <T> Iterator<T> consumingIterator(Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new AnonymousClass10(iterator);
    }

    @Nullable
    static <T> T pollNext(Iterator<T> iterator) {
        if (!iterator.hasNext()) {
            return null;
        }
        T result = iterator.next();
        iterator.remove();
        return result;
    }

    static void clear(Iterator<?> iterator) {
        Preconditions.checkNotNull(iterator);
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }

    public static <T> UnmodifiableIterator<T> forArray(T... array) {
        return forArray(array, 0, array.length, 0);
    }

    static <T> UnmodifiableListIterator<T> forArray(T[] array, int offset, int length, int index) {
        Preconditions.checkArgument(length >= 0);
        Preconditions.checkPositionIndexes(offset, offset + length, array.length);
        Preconditions.checkPositionIndex(index, length);
        if (length == 0) {
            return emptyListIterator();
        }
        return new AnonymousClass11(length, index, array, offset);
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(@Nullable T value) {
        return new AnonymousClass12(value);
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new AnonymousClass13(enumeration);
    }

    public static <T> Enumeration<T> asEnumeration(Iterator<T> iterator) {
        Preconditions.checkNotNull(iterator);
        return new AnonymousClass14(iterator);
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator) {
        if (iterator instanceof PeekingImpl) {
            return (PeekingImpl) iterator;
        }
        return new PeekingImpl(iterator);
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> iterator) {
        return (PeekingIterator) Preconditions.checkNotNull(iterator);
    }

    @Beta
    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterators, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterators, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator(iterators, comparator);
    }

    static <T> ListIterator<T> cast(Iterator<T> iterator) {
        return (ListIterator) iterator;
    }
}
