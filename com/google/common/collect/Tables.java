package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Table.Cell;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible
public final class Tables {
    private static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> UNMODIFIABLE_WRAPPER;

    /* renamed from: com.google.common.collect.Tables.1 */
    static class C07491 implements Function<Map<Object, Object>, Map<Object, Object>> {
        C07491() {
        }

        public Map<Object, Object> apply(Map<Object, Object> input) {
            return Collections.unmodifiableMap(input);
        }
    }

    static abstract class AbstractCell<R, C, V> implements Cell<R, C, V> {
        AbstractCell() {
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell<?, ?, ?> other = (Cell) obj;
            if (Objects.equal(getRowKey(), other.getRowKey()) && Objects.equal(getColumnKey(), other.getColumnKey()) && Objects.equal(getValue(), other.getValue())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(getRowKey(), getColumnKey(), getValue());
        }

        public String toString() {
            return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
        }
    }

    static final class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final C columnKey;
        private final R rowKey;
        private final V value;

        ImmutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.value = value;
        }

        public R getRowKey() {
            return this.rowKey;
        }

        public C getColumnKey() {
            return this.columnKey;
        }

        public V getValue() {
            return this.value;
        }
    }

    private static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {
        final Table<R, C, V1> fromTable;
        final Function<? super V1, V2> function;

        /* renamed from: com.google.common.collect.Tables.TransformedTable.1 */
        class C07501 implements Function<Cell<R, C, V1>, Cell<R, C, V2>> {
            C07501() {
            }

            public Cell<R, C, V2> apply(Cell<R, C, V1> cell) {
                return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.function.apply(cell.getValue()));
            }
        }

        /* renamed from: com.google.common.collect.Tables.TransformedTable.2 */
        class C07512 implements Function<Map<C, V1>, Map<C, V2>> {
            C07512() {
            }

            public Map<C, V2> apply(Map<C, V1> row) {
                return Maps.transformValues((Map) row, TransformedTable.this.function);
            }
        }

        /* renamed from: com.google.common.collect.Tables.TransformedTable.3 */
        class C07523 implements Function<Map<R, V1>, Map<R, V2>> {
            C07523() {
            }

            public Map<R, V2> apply(Map<R, V1> column) {
                return Maps.transformValues((Map) column, TransformedTable.this.function);
            }
        }

        TransformedTable(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
            this.fromTable = (Table) Preconditions.checkNotNull(fromTable);
            this.function = (Function) Preconditions.checkNotNull(function);
        }

        public boolean contains(Object rowKey, Object columnKey) {
            return this.fromTable.contains(rowKey, columnKey);
        }

        public V2 get(Object rowKey, Object columnKey) {
            return contains(rowKey, columnKey) ? this.function.apply(this.fromTable.get(rowKey, columnKey)) : null;
        }

        public int size() {
            return this.fromTable.size();
        }

        public void clear() {
            this.fromTable.clear();
        }

        public V2 put(R r, C c, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        public V2 remove(Object rowKey, Object columnKey) {
            return contains(rowKey, columnKey) ? this.function.apply(this.fromTable.remove(rowKey, columnKey)) : null;
        }

        public Map<C, V2> row(R rowKey) {
            return Maps.transformValues(this.fromTable.row(rowKey), this.function);
        }

        public Map<R, V2> column(C columnKey) {
            return Maps.transformValues(this.fromTable.column(columnKey), this.function);
        }

        Function<Cell<R, C, V1>, Cell<R, C, V2>> cellFunction() {
            return new C07501();
        }

        Iterator<Cell<R, C, V2>> cellIterator() {
            return Iterators.transform(this.fromTable.cellSet().iterator(), cellFunction());
        }

        public Set<R> rowKeySet() {
            return this.fromTable.rowKeySet();
        }

        public Set<C> columnKeySet() {
            return this.fromTable.columnKeySet();
        }

        Collection<V2> createValues() {
            return Collections2.transform(this.fromTable.values(), this.function);
        }

        public Map<R, Map<C, V2>> rowMap() {
            return Maps.transformValues(this.fromTable.rowMap(), new C07512());
        }

        public Map<C, Map<R, V2>> columnMap() {
            return Maps.transformValues(this.fromTable.columnMap(), new C07523());
        }
    }

    private static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {
        private static final Function<Cell<?, ?, ?>, Cell<?, ?, ?>> TRANSPOSE_CELL;
        final Table<R, C, V> original;

        /* renamed from: com.google.common.collect.Tables.TransposeTable.1 */
        static class C07531 implements Function<Cell<?, ?, ?>, Cell<?, ?, ?>> {
            C07531() {
            }

            public Cell<?, ?, ?> apply(Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        }

        TransposeTable(Table<R, C, V> original) {
            this.original = (Table) Preconditions.checkNotNull(original);
        }

        public void clear() {
            this.original.clear();
        }

        public Map<C, V> column(R columnKey) {
            return this.original.row(columnKey);
        }

        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }

        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }

        public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
            return this.original.contains(columnKey, rowKey);
        }

        public boolean containsColumn(@Nullable Object columnKey) {
            return this.original.containsRow(columnKey);
        }

        public boolean containsRow(@Nullable Object rowKey) {
            return this.original.containsColumn(rowKey);
        }

        public boolean containsValue(@Nullable Object value) {
            return this.original.containsValue(value);
        }

        public V get(@Nullable Object rowKey, @Nullable Object columnKey) {
            return this.original.get(columnKey, rowKey);
        }

        public V put(C rowKey, R columnKey, V value) {
            return this.original.put(columnKey, rowKey, value);
        }

        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }

        public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
            return this.original.remove(columnKey, rowKey);
        }

        public Map<R, V> row(C rowKey) {
            return this.original.column(rowKey);
        }

        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }

        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }

        public int size() {
            return this.original.size();
        }

        public Collection<V> values() {
            return this.original.values();
        }

        static {
            TRANSPOSE_CELL = new C07531();
        }

        Iterator<Cell<C, R, V>> cellIterator() {
            return Iterators.transform(this.original.cellSet().iterator(), TRANSPOSE_CELL);
        }
    }

    private static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final Table<? extends R, ? extends C, ? extends V> delegate;

        UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> delegate) {
            this.delegate = (Table) Preconditions.checkNotNull(delegate);
        }

        protected Table<R, C, V> delegate() {
            return this.delegate;
        }

        public Set<Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> column(@Nullable C columnKey) {
            return Collections.unmodifiableMap(super.column(columnKey));
        }

        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        public Map<C, Map<R, V>> columnMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), Tables.unmodifiableWrapper()));
        }

        public V put(@Nullable R r, @Nullable C c, @Nullable V v) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        public V remove(@Nullable Object rowKey, @Nullable Object columnKey) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> row(@Nullable R rowKey) {
            return Collections.unmodifiableMap(super.row(rowKey));
        }

        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        public Map<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), Tables.unmodifiableWrapper()));
        }

        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }
    }

    static final class UnmodifiableRowSortedMap<R, C, V> extends UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> delegate) {
            super(delegate);
        }

        protected RowSortedTable<R, C, V> delegate() {
            return (RowSortedTable) super.delegate();
        }

        public SortedMap<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableSortedMap(Maps.transformValues(delegate().rowMap(), Tables.unmodifiableWrapper()));
        }

        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(delegate().rowKeySet());
        }
    }

    private Tables() {
    }

    public static <R, C, V> Cell<R, C, V> immutableCell(@Nullable R rowKey, @Nullable C columnKey, @Nullable V value) {
        return new ImmutableCell(rowKey, columnKey, value);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof TransposeTable ? ((TransposeTable) table).original : new TransposeTable(table);
    }

    @Beta
    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> backingMap, Supplier<? extends Map<C, V>> factory) {
        Preconditions.checkArgument(backingMap.isEmpty());
        Preconditions.checkNotNull(factory);
        return new StandardTable(backingMap, factory);
    }

    @Beta
    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> fromTable, Function<? super V1, V2> function) {
        return new TransformedTable(fromTable, function);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable(table);
    }

    @Beta
    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> table) {
        return new UnmodifiableRowSortedMap(table);
    }

    private static <K, V> Function<Map<K, V>, Map<K, V>> unmodifiableWrapper() {
        return UNMODIFIABLE_WRAPPER;
    }

    static {
        UNMODIFIABLE_WRAPPER = new C07491();
    }

    static boolean equalsImpl(Table<?, ?, ?> table, @Nullable Object obj) {
        if (obj == table) {
            return true;
        }
        if (!(obj instanceof Table)) {
            return false;
        }
        return table.cellSet().equals(((Table) obj).cellSet());
    }
}
