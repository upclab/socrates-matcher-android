package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible(serializable = true)
public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    /* renamed from: com.google.common.base.Optional.1 */
    static class C02131 implements Iterable<T> {
        final /* synthetic */ Iterable val$optionals;

        /* renamed from: com.google.common.base.Optional.1.1 */
        class C06221 extends AbstractIterator<T> {
            private final Iterator<? extends Optional<? extends T>> iterator;

            C06221() {
                this.iterator = (Iterator) Preconditions.checkNotNull(C02131.this.val$optionals.iterator());
            }

            protected T computeNext() {
                while (this.iterator.hasNext()) {
                    Optional<? extends T> optional = (Optional) this.iterator.next();
                    if (optional.isPresent()) {
                        return optional.get();
                    }
                }
                return endOfData();
            }
        }

        C02131(Iterable iterable) {
            this.val$optionals = iterable;
        }

        public Iterator<T> iterator() {
            return new C06221();
        }
    }

    public abstract Set<T> asSet();

    public abstract boolean equals(@Nullable Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    public abstract Optional<T> or(Optional<? extends T> optional);

    @Beta
    public abstract T or(Supplier<? extends T> supplier);

    public abstract T or(T t);

    @Nullable
    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> function);

    public static <T> Optional<T> absent() {
        return Absent.withType();
    }

    public static <T> Optional<T> of(T reference) {
        return new Present(Preconditions.checkNotNull(reference));
    }

    public static <T> Optional<T> fromNullable(@Nullable T nullableReference) {
        return nullableReference == null ? absent() : new Present(nullableReference);
    }

    Optional() {
    }

    @Beta
    public static <T> Iterable<T> presentInstances(Iterable<? extends Optional<? extends T>> optionals) {
        Preconditions.checkNotNull(optionals);
        return new C02131(optionals);
    }
}
