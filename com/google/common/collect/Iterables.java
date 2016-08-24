package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public final class Iterables {

    /* renamed from: com.google.common.collect.Iterables.10 */
    static class AnonymousClass10 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$iterable;
        final /* synthetic */ int val$numberToSkip;

        /* renamed from: com.google.common.collect.Iterables.10.1 */
        class C02361 implements Iterator<T> {
            boolean atStart;
            final /* synthetic */ Iterator val$iterator;

            C02361(Iterator it) {
                this.val$iterator = it;
                this.atStart = true;
            }

            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            public T next() {
                T result = this.val$iterator.next();
                this.atStart = false;
                return result;
            }

            public void remove() {
                CollectPreconditions.checkRemove(!this.atStart);
                this.val$iterator.remove();
            }
        }

        AnonymousClass10(Iterable iterable, int i) {
            this.val$iterable = iterable;
            this.val$numberToSkip = i;
        }

        public Iterator<T> iterator() {
            Iterator<T> iterator = this.val$iterable.iterator();
            Iterators.advance(iterator, this.val$numberToSkip);
            return new C02361(iterator);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.11 */
    static class AnonymousClass11 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$iterable;
        final /* synthetic */ int val$limitSize;

        AnonymousClass11(Iterable iterable, int i) {
            this.val$iterable = iterable;
            this.val$limitSize = i;
        }

        public Iterator<T> iterator() {
            return Iterators.limit(this.val$iterable.iterator(), this.val$limitSize);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.12 */
    static class AnonymousClass12 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$iterable;

        AnonymousClass12(Iterable iterable) {
            this.val$iterable = iterable;
        }

        public Iterator<T> iterator() {
            return new ConsumingQueueIterator(null);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.13 */
    static class AnonymousClass13 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$iterable;

        AnonymousClass13(Iterable iterable) {
            this.val$iterable = iterable;
        }

        public Iterator<T> iterator() {
            return Iterators.consumingIterator(this.val$iterable.iterator());
        }
    }

    /* renamed from: com.google.common.collect.Iterables.14 */
    static class AnonymousClass14 extends FluentIterable<T> {
        final /* synthetic */ Comparator val$comparator;
        final /* synthetic */ Iterable val$iterables;

        AnonymousClass14(Iterable iterable, Comparator comparator) {
            this.val$iterables = iterable;
            this.val$comparator = comparator;
        }

        public Iterator<T> iterator() {
            return Iterators.mergeSorted(Iterables.transform(this.val$iterables, Iterables.toIterator()), this.val$comparator);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.1 */
    static class C06751 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$iterable;

        C06751(Iterable iterable) {
            this.val$iterable = iterable;
        }

        public Iterator<T> iterator() {
            return Iterators.cycle(this.val$iterable);
        }

        public String toString() {
            return this.val$iterable.toString() + " (cycled)";
        }
    }

    /* renamed from: com.google.common.collect.Iterables.2 */
    static class C06762 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$inputs;

        C06762(Iterable iterable) {
            this.val$inputs = iterable;
        }

        public Iterator<T> iterator() {
            return Iterators.concat(Iterables.iterators(this.val$inputs));
        }
    }

    /* renamed from: com.google.common.collect.Iterables.3 */
    static class C06773 extends TransformedIterator<Iterable<? extends T>, Iterator<? extends T>> {
        C06773(Iterator x0) {
            super(x0);
        }

        Iterator<? extends T> transform(Iterable<? extends T> from) {
            return from.iterator();
        }
    }

    /* renamed from: com.google.common.collect.Iterables.4 */
    static class C06784 extends FluentIterable<List<T>> {
        final /* synthetic */ Iterable val$iterable;
        final /* synthetic */ int val$size;

        C06784(Iterable iterable, int i) {
            this.val$iterable = iterable;
            this.val$size = i;
        }

        public Iterator<List<T>> iterator() {
            return Iterators.partition(this.val$iterable.iterator(), this.val$size);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.5 */
    static class C06795 extends FluentIterable<List<T>> {
        final /* synthetic */ Iterable val$iterable;
        final /* synthetic */ int val$size;

        C06795(Iterable iterable, int i) {
            this.val$iterable = iterable;
            this.val$size = i;
        }

        public Iterator<List<T>> iterator() {
            return Iterators.paddedPartition(this.val$iterable.iterator(), this.val$size);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.6 */
    static class C06806 extends FluentIterable<T> {
        final /* synthetic */ Predicate val$predicate;
        final /* synthetic */ Iterable val$unfiltered;

        C06806(Iterable iterable, Predicate predicate) {
            this.val$unfiltered = iterable;
            this.val$predicate = predicate;
        }

        public Iterator<T> iterator() {
            return Iterators.filter(this.val$unfiltered.iterator(), this.val$predicate);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.7 */
    static class C06817 extends FluentIterable<T> {
        final /* synthetic */ Class val$type;
        final /* synthetic */ Iterable val$unfiltered;

        C06817(Iterable iterable, Class cls) {
            this.val$unfiltered = iterable;
            this.val$type = cls;
        }

        public Iterator<T> iterator() {
            return Iterators.filter(this.val$unfiltered.iterator(), this.val$type);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.8 */
    static class C06828 extends FluentIterable<T> {
        final /* synthetic */ Iterable val$fromIterable;
        final /* synthetic */ Function val$function;

        C06828(Iterable iterable, Function function) {
            this.val$fromIterable = iterable;
            this.val$function = function;
        }

        public Iterator<T> iterator() {
            return Iterators.transform(this.val$fromIterable.iterator(), this.val$function);
        }
    }

    /* renamed from: com.google.common.collect.Iterables.9 */
    static class C06839 extends FluentIterable<T> {
        final /* synthetic */ List val$list;
        final /* synthetic */ int val$numberToSkip;

        C06839(List list, int i) {
            this.val$list = list;
            this.val$numberToSkip = i;
        }

        public Iterator<T> iterator() {
            return this.val$list.subList(Math.min(this.val$list.size(), this.val$numberToSkip), this.val$list.size()).iterator();
        }
    }

    private static final class UnmodifiableIterable<T> extends FluentIterable<T> {
        private final Iterable<T> iterable;

        private UnmodifiableIterable(Iterable<T> iterable) {
            this.iterable = iterable;
        }

        public Iterator<T> iterator() {
            return Iterators.unmodifiableIterator(this.iterable.iterator());
        }

        public String toString() {
            return this.iterable.toString();
        }
    }

    private static class ConsumingQueueIterator<T> extends AbstractIterator<T> {
        private final Queue<T> queue;

        private ConsumingQueueIterator(Queue<T> queue) {
            this.queue = queue;
        }

        public T computeNext() {
            try {
                return this.queue.remove();
            } catch (NoSuchElementException e) {
                return endOfData();
            }
        }
    }

    private Iterables() {
    }

    public static <T> Iterable<T> unmodifiableIterable(Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return ((iterable instanceof UnmodifiableIterable) || (iterable instanceof ImmutableCollection)) ? iterable : new UnmodifiableIterable(null);
    }

    @Deprecated
    public static <E> Iterable<E> unmodifiableIterable(ImmutableCollection<E> iterable) {
        return (Iterable) Preconditions.checkNotNull(iterable);
    }

    public static int size(Iterable<?> iterable) {
        return iterable instanceof Collection ? ((Collection) iterable).size() : Iterators.size(iterable.iterator());
    }

    public static boolean contains(Iterable<?> iterable, @Nullable Object element) {
        if (iterable instanceof Collection) {
            return Collections2.safeContains((Collection) iterable, element);
        }
        return Iterators.contains(iterable.iterator(), element);
    }

    public static boolean removeAll(Iterable<?> removeFrom, Collection<?> elementsToRemove) {
        return removeFrom instanceof Collection ? ((Collection) removeFrom).removeAll((Collection) Preconditions.checkNotNull(elementsToRemove)) : Iterators.removeAll(removeFrom.iterator(), elementsToRemove);
    }

    public static boolean retainAll(Iterable<?> removeFrom, Collection<?> elementsToRetain) {
        return removeFrom instanceof Collection ? ((Collection) removeFrom).retainAll((Collection) Preconditions.checkNotNull(elementsToRetain)) : Iterators.retainAll(removeFrom.iterator(), elementsToRetain);
    }

    public static <T> boolean removeIf(Iterable<T> removeFrom, Predicate<? super T> predicate) {
        if ((removeFrom instanceof RandomAccess) && (removeFrom instanceof List)) {
            return removeIfFromRandomAccessList((List) removeFrom, (Predicate) Preconditions.checkNotNull(predicate));
        }
        return Iterators.removeIf(removeFrom.iterator(), predicate);
    }

    private static <T> boolean removeIfFromRandomAccessList(List<T> list, Predicate<? super T> predicate) {
        int from = 0;
        int to = 0;
        while (from < list.size()) {
            T element = list.get(from);
            if (!predicate.apply(element)) {
                if (from > to) {
                    try {
                        list.set(to, element);
                    } catch (UnsupportedOperationException e) {
                        slowRemoveIfForRemainingElements(list, predicate, to, from);
                        return true;
                    }
                }
                to++;
            }
            from++;
        }
        list.subList(to, list.size()).clear();
        if (from == to) {
            return false;
        }
        return true;
    }

    private static <T> void slowRemoveIfForRemainingElements(List<T> list, Predicate<? super T> predicate, int to, int from) {
        int n;
        for (n = list.size() - 1; n > from; n--) {
            if (predicate.apply(list.get(n))) {
                list.remove(n);
            }
        }
        for (n = from - 1; n >= to; n--) {
            list.remove(n);
        }
    }

    @Nullable
    static <T> T removeFirstMatching(Iterable<T> removeFrom, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        Iterator<T> iterator = removeFrom.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (predicate.apply(next)) {
                iterator.remove();
                return next;
            }
        }
        return null;
    }

    public static boolean elementsEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
        if ((iterable1 instanceof Collection) && (iterable2 instanceof Collection)) {
            if (((Collection) iterable1).size() != ((Collection) iterable2).size()) {
                return false;
            }
        }
        return Iterators.elementsEqual(iterable1.iterator(), iterable2.iterator());
    }

    public static String toString(Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator());
    }

    public static <T> T getOnlyElement(Iterable<T> iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }

    @Nullable
    public static <T> T getOnlyElement(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        return Iterators.getOnlyElement(iterable.iterator(), defaultValue);
    }

    @GwtIncompatible("Array.newInstance(Class, int)")
    public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> type) {
        Collection<? extends T> collection = toCollection(iterable);
        return collection.toArray(ObjectArrays.newArray((Class) type, collection.size()));
    }

    static Object[] toArray(Iterable<?> iterable) {
        return toCollection(iterable).toArray();
    }

    private static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ? (Collection) iterable : Lists.newArrayList(iterable.iterator());
    }

    public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
        if (elementsToAdd instanceof Collection) {
            return addTo.addAll(Collections2.cast(elementsToAdd));
        }
        return Iterators.addAll(addTo, ((Iterable) Preconditions.checkNotNull(elementsToAdd)).iterator());
    }

    public static int frequency(Iterable<?> iterable, @Nullable Object element) {
        if (iterable instanceof Multiset) {
            return ((Multiset) iterable).count(element);
        }
        if (iterable instanceof Set) {
            return ((Set) iterable).contains(element) ? 1 : 0;
        } else {
            return Iterators.frequency(iterable.iterator(), element);
        }
    }

    public static <T> Iterable<T> cycle(Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new C06751(iterable);
    }

    public static <T> Iterable<T> cycle(T... elements) {
        return cycle(Lists.newArrayList((Object[]) elements));
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b) {
        return concat(ImmutableList.of(a, b));
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c) {
        return concat(ImmutableList.of(a, b, c));
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b, Iterable<? extends T> c, Iterable<? extends T> d) {
        return concat(ImmutableList.of(a, b, c, d));
    }

    public static <T> Iterable<T> concat(Iterable<? extends T>... inputs) {
        return concat(ImmutableList.copyOf((Object[]) inputs));
    }

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> inputs) {
        Preconditions.checkNotNull(inputs);
        return new C06762(inputs);
    }

    private static <T> Iterator<Iterator<? extends T>> iterators(Iterable<? extends Iterable<? extends T>> iterables) {
        return new C06773(iterables.iterator());
    }

    public static <T> Iterable<List<T>> partition(Iterable<T> iterable, int size) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(size > 0);
        return new C06784(iterable, size);
    }

    public static <T> Iterable<List<T>> paddedPartition(Iterable<T> iterable, int size) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(size > 0);
        return new C06795(iterable, size);
    }

    public static <T> Iterable<T> filter(Iterable<T> unfiltered, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(predicate);
        return new C06806(unfiltered, predicate);
    }

    @GwtIncompatible("Class.isInstance")
    public static <T> Iterable<T> filter(Iterable<?> unfiltered, Class<T> type) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(type);
        return new C06817(unfiltered, type);
    }

    public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }

    public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.all(iterable.iterator(), predicate);
    }

    public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.find(iterable.iterator(), predicate);
    }

    @Nullable
    public static <T> T find(Iterable<? extends T> iterable, Predicate<? super T> predicate, @Nullable T defaultValue) {
        return Iterators.find(iterable.iterator(), predicate, defaultValue);
    }

    public static <T> Optional<T> tryFind(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.tryFind(iterable.iterator(), predicate);
    }

    public static <T> int indexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.indexOf(iterable.iterator(), predicate);
    }

    public static <F, T> Iterable<T> transform(Iterable<F> fromIterable, Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(fromIterable);
        Preconditions.checkNotNull(function);
        return new C06828(fromIterable, function);
    }

    public static <T> T get(Iterable<T> iterable, int position) {
        Preconditions.checkNotNull(iterable);
        return iterable instanceof List ? ((List) iterable).get(position) : Iterators.get(iterable.iterator(), position);
    }

    @Nullable
    public static <T> T get(Iterable<? extends T> iterable, int position, @Nullable T defaultValue) {
        Preconditions.checkNotNull(iterable);
        Iterators.checkNonnegative(position);
        if (iterable instanceof List) {
            List<? extends T> list = Lists.cast(iterable);
            if (position < list.size()) {
                return list.get(position);
            }
            return defaultValue;
        }
        Iterator<? extends T> iterator = iterable.iterator();
        Iterators.advance(iterator, position);
        return Iterators.getNext(iterator, defaultValue);
    }

    @Nullable
    public static <T> T getFirst(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        return Iterators.getNext(iterable.iterator(), defaultValue);
    }

    public static <T> T getLast(Iterable<T> iterable) {
        if (!(iterable instanceof List)) {
            return Iterators.getLast(iterable.iterator());
        }
        List<T> list = (List) iterable;
        if (!list.isEmpty()) {
            return getLastInNonemptyList(list);
        }
        throw new NoSuchElementException();
    }

    @Nullable
    public static <T> T getLast(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        if (iterable instanceof Collection) {
            if (Collections2.cast(iterable).isEmpty()) {
                return defaultValue;
            }
            if (iterable instanceof List) {
                return getLastInNonemptyList(Lists.cast(iterable));
            }
        }
        return Iterators.getLast(iterable.iterator(), defaultValue);
    }

    private static <T> T getLastInNonemptyList(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> Iterable<T> skip(Iterable<T> iterable, int numberToSkip) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(numberToSkip >= 0, "number to skip cannot be negative");
        if (iterable instanceof List) {
            return new C06839((List) iterable, numberToSkip);
        }
        return new AnonymousClass10(iterable, numberToSkip);
    }

    public static <T> Iterable<T> limit(Iterable<T> iterable, int limitSize) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkArgument(limitSize >= 0, "limit is negative");
        return new AnonymousClass11(iterable, limitSize);
    }

    public static <T> Iterable<T> consumingIterable(Iterable<T> iterable) {
        if (iterable instanceof Queue) {
            return new AnonymousClass12(iterable);
        }
        Preconditions.checkNotNull(iterable);
        return new AnonymousClass13(iterable);
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    @Beta
    public static <T> Iterable<T> mergeSorted(Iterable<? extends Iterable<? extends T>> iterables, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterables, "iterables");
        Preconditions.checkNotNull(comparator, "comparator");
        return new UnmodifiableIterable(null);
    }

    private static <T> Function<Iterable<? extends T>, Iterator<? extends T>> toIterator() {
        return new Function<Iterable<? extends T>, Iterator<? extends T>>() {
            public Iterator<? extends T> apply(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }
        };
    }
}
