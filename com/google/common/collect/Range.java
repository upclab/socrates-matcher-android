package com.google.common.collect;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Range<C extends Comparable> implements Predicate<C>, Serializable {
    private static final Range<Comparable> ALL;
    private static final Function<Range, Cut> LOWER_BOUND_FN;
    static final Ordering<Range<?>> RANGE_LEX_ORDERING;
    private static final Function<Range, Cut> UPPER_BOUND_FN;
    private static final long serialVersionUID = 0;
    final Cut<C> lowerBound;
    final Cut<C> upperBound;

    /* renamed from: com.google.common.collect.Range.4 */
    static /* synthetic */ class C02534 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType;

        static {
            $SwitchMap$com$google$common$collect$BoundType = new int[BoundType.values().length];
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* renamed from: com.google.common.collect.Range.1 */
    static class C07311 implements Function<Range, Cut> {
        C07311() {
        }

        public Cut apply(Range range) {
            return range.lowerBound;
        }
    }

    /* renamed from: com.google.common.collect.Range.2 */
    static class C07322 implements Function<Range, Cut> {
        C07322() {
        }

        public Cut apply(Range range) {
            return range.upperBound;
        }
    }

    /* renamed from: com.google.common.collect.Range.3 */
    static class C07333 extends Ordering<Range<?>> {
        C07333() {
        }

        public int compare(Range<?> left, Range<?> right) {
            return ComparisonChain.start().compare(left.lowerBound, right.lowerBound).compare(left.upperBound, right.upperBound).result();
        }
    }

    static {
        LOWER_BOUND_FN = new C07311();
        UPPER_BOUND_FN = new C07322();
        RANGE_LEX_ORDERING = new C07333();
        ALL = new Range(Cut.belowAll(), Cut.aboveAll());
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
        return LOWER_BOUND_FN;
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> upperBoundFn() {
        return UPPER_BOUND_FN;
    }

    static <C extends Comparable<?>> Range<C> create(Cut<C> lowerBound, Cut<C> upperBound) {
        return new Range(lowerBound, upperBound);
    }

    public static <C extends Comparable<?>> Range<C> open(C lower, C upper) {
        return create(Cut.aboveValue(lower), Cut.belowValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> closed(C lower, C upper) {
        return create(Cut.belowValue(lower), Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C lower, C upper) {
        return create(Cut.belowValue(lower), Cut.belowValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C lower, C upper) {
        return create(Cut.aboveValue(lower), Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> range(C lower, BoundType lowerType, C upper, BoundType upperType) {
        Preconditions.checkNotNull(lowerType);
        Preconditions.checkNotNull(upperType);
        return create(lowerType == BoundType.OPEN ? Cut.aboveValue(lower) : Cut.belowValue(lower), upperType == BoundType.OPEN ? Cut.belowValue(upper) : Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C endpoint) {
        return create(Cut.belowAll(), Cut.belowValue(endpoint));
    }

    public static <C extends Comparable<?>> Range<C> atMost(C endpoint) {
        return create(Cut.belowAll(), Cut.aboveValue(endpoint));
    }

    public static <C extends Comparable<?>> Range<C> upTo(C endpoint, BoundType boundType) {
        switch (C02534.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return lessThan(endpoint);
            case Value.STRING_FIELD_NUMBER /*2*/:
                return atMost(endpoint);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C endpoint) {
        return create(Cut.aboveValue(endpoint), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C endpoint) {
        return create(Cut.belowValue(endpoint), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> downTo(C endpoint, BoundType boundType) {
        switch (C02534.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()]) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return greaterThan(endpoint);
            case Value.STRING_FIELD_NUMBER /*2*/:
                return atLeast(endpoint);
            default:
                throw new AssertionError();
        }
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return ALL;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C value) {
        return closed(value, value);
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> values) {
        Preconditions.checkNotNull(values);
        if (values instanceof ContiguousSet) {
            return ((ContiguousSet) values).range();
        }
        Iterator<C> valueIterator = values.iterator();
        C min = (Comparable) Preconditions.checkNotNull(valueIterator.next());
        C max = min;
        while (valueIterator.hasNext()) {
            Comparable value = (Comparable) Preconditions.checkNotNull(valueIterator.next());
            Comparable min2 = (Comparable) Ordering.natural().min(min, value);
            Comparable max2 = (Comparable) Ordering.natural().max(max, value);
        }
        return closed(min, max);
    }

    private Range(Cut<C> lowerBound, Cut<C> upperBound) {
        if (lowerBound.compareTo((Cut) upperBound) > 0 || lowerBound == Cut.aboveAll() || upperBound == Cut.belowAll()) {
            throw new IllegalArgumentException("Invalid range: " + toString(lowerBound, upperBound));
        }
        this.lowerBound = (Cut) Preconditions.checkNotNull(lowerBound);
        this.upperBound = (Cut) Preconditions.checkNotNull(upperBound);
    }

    public boolean hasLowerBound() {
        return this.lowerBound != Cut.belowAll();
    }

    public C lowerEndpoint() {
        return this.lowerBound.endpoint();
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }

    public boolean hasUpperBound() {
        return this.upperBound != Cut.aboveAll();
    }

    public C upperEndpoint() {
        return this.upperBound.endpoint();
    }

    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public boolean contains(C value) {
        Preconditions.checkNotNull(value);
        return this.lowerBound.isLessThan(value) && !this.upperBound.isLessThan(value);
    }

    @Deprecated
    public boolean apply(C input) {
        return contains(input);
    }

    public boolean containsAll(Iterable<? extends C> values) {
        if (Iterables.isEmpty(values)) {
            return true;
        }
        if (values instanceof SortedSet) {
            SortedSet<? extends C> set = cast(values);
            Comparator<?> comparator = set.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                boolean z = contains((Comparable) set.first()) && contains((Comparable) set.last());
                return z;
            }
        }
        Iterator i$ = values.iterator();
        while (i$.hasNext()) {
            if (!contains((Comparable) i$.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> other) {
        return this.lowerBound.compareTo(other.lowerBound) <= 0 && this.upperBound.compareTo(other.upperBound) >= 0;
    }

    public boolean isConnected(Range<C> other) {
        return this.lowerBound.compareTo(other.upperBound) <= 0 && other.lowerBound.compareTo(this.upperBound) <= 0;
    }

    public Range<C> intersection(Range<C> connectedRange) {
        int lowerCmp = this.lowerBound.compareTo(connectedRange.lowerBound);
        int upperCmp = this.upperBound.compareTo(connectedRange.upperBound);
        if (lowerCmp >= 0 && upperCmp <= 0) {
            return this;
        }
        if (lowerCmp <= 0 && upperCmp >= 0) {
            return connectedRange;
        }
        return create(lowerCmp >= 0 ? this.lowerBound : connectedRange.lowerBound, upperCmp <= 0 ? this.upperBound : connectedRange.upperBound);
    }

    public Range<C> span(Range<C> other) {
        int lowerCmp = this.lowerBound.compareTo(other.lowerBound);
        int upperCmp = this.upperBound.compareTo(other.upperBound);
        if (lowerCmp <= 0 && upperCmp >= 0) {
            return this;
        }
        if (lowerCmp >= 0 && upperCmp <= 0) {
            return other;
        }
        return create(lowerCmp <= 0 ? this.lowerBound : other.lowerBound, upperCmp >= 0 ? this.upperBound : other.upperBound);
    }

    public Range<C> canonical(DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(domain);
        Cut<C> lower = this.lowerBound.canonical(domain);
        Cut<C> upper = this.upperBound.canonical(domain);
        return (lower == this.lowerBound && upper == this.upperBound) ? this : create(lower, upper);
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Range)) {
            return false;
        }
        Range<?> other = (Range) object;
        if (this.lowerBound.equals(other.lowerBound) && this.upperBound.equals(other.upperBound)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }

    public String toString() {
        return toString(this.lowerBound, this.upperBound);
    }

    private static String toString(Cut<?> lowerBound, Cut<?> upperBound) {
        StringBuilder sb = new StringBuilder(16);
        lowerBound.describeAsLowerBound(sb);
        sb.append('\u2025');
        upperBound.describeAsUpperBound(sb);
        return sb.toString();
    }

    private static <T> SortedSet<T> cast(Iterable<T> iterable) {
        return (SortedSet) iterable;
    }

    Object readResolve() {
        if (equals(ALL)) {
            return all();
        }
        return this;
    }

    static int compareOrThrow(Comparable left, Comparable right) {
        return left.compareTo(right);
    }
}
