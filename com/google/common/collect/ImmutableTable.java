package com.google.common.collect;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table.Cell;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.joda.time.MutableDateTime;

@GwtCompatible
public abstract class ImmutableTable<R, C, V> extends AbstractTable<R, C, V> {
    private static final ImmutableTable<Object, Object, Object> EMPTY;

    public static final class Builder<R, C, V> {
        private final List<Cell<R, C, V>> cells;
        private Comparator<? super C> columnComparator;
        private Comparator<? super R> rowComparator;

        public Builder() {
            this.cells = Lists.newArrayList();
        }

        public Builder<R, C, V> orderRowsBy(Comparator<? super R> rowComparator) {
            this.rowComparator = (Comparator) Preconditions.checkNotNull(rowComparator);
            return this;
        }

        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> columnComparator) {
            this.columnComparator = (Comparator) Preconditions.checkNotNull(columnComparator);
            return this;
        }

        public Builder<R, C, V> put(R rowKey, C columnKey, V value) {
            this.cells.add(ImmutableTable.cellOf(rowKey, columnKey, value));
            return this;
        }

        public Builder<R, C, V> put(Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey());
                Preconditions.checkNotNull(cell.getColumnKey());
                Preconditions.checkNotNull(cell.getValue());
                this.cells.add(cell);
            } else {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }

        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
                put(cell);
            }
            return this;
        }

        public ImmutableTable<R, C, V> build() {
            switch (this.cells.size()) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    return ImmutableTable.of();
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return new SingletonImmutableTable((Cell) Iterables.getOnlyElement(this.cells));
                default:
                    return RegularImmutableTable.forCells(this.cells, this.rowComparator, this.columnComparator);
            }
        }
    }

    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    abstract ImmutableSet<Cell<R, C, V>> createCellSet();

    abstract ImmutableCollection<V> createValues();

    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    public /* bridge */ /* synthetic */ boolean containsColumn(Object x0) {
        return super.containsColumn(x0);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(Object x0) {
        return super.containsRow(x0);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object x0) {
        return super.equals(x0);
    }

    public /* bridge */ /* synthetic */ Object get(Object x0, Object x1) {
        return super.get(x0, x1);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    static {
        EMPTY = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return EMPTY;
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R rowKey, C columnKey, V value) {
        return new SingletonImmutableTable(rowKey, columnKey, value);
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable) table;
        }
        switch (table.size()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return of();
            case Value.TYPE_FIELD_NUMBER /*1*/:
                Cell<? extends R, ? extends C, ? extends V> onlyCell = (Cell) Iterables.getOnlyElement(table.cellSet());
                return of(onlyCell.getRowKey(), onlyCell.getColumnKey(), onlyCell.getValue());
            default:
                com.google.common.collect.ImmutableSet.Builder<Cell<R, C, V>> cellSetBuilder = ImmutableSet.builder();
                for (Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
                    cellSetBuilder.add(cellOf(cell.getRowKey(), cell.getColumnKey(), cell.getValue()));
                }
                return RegularImmutableTable.forCells(cellSetBuilder.build());
        }
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder();
    }

    static <R, C, V> Cell<R, C, V> cellOf(R rowKey, C columnKey, V value) {
        return Tables.immutableCell(Preconditions.checkNotNull(rowKey), Preconditions.checkNotNull(columnKey), Preconditions.checkNotNull(value));
    }

    ImmutableTable() {
    }

    public ImmutableSet<Cell<R, C, V>> cellSet() {
        return (ImmutableSet) super.cellSet();
    }

    final UnmodifiableIterator<Cell<R, C, V>> cellIterator() {
        throw new AssertionError("should never be called");
    }

    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    final Iterator<V> valuesIterator() {
        throw new AssertionError("should never be called");
    }

    public ImmutableMap<R, V> column(C columnKey) {
        Preconditions.checkNotNull(columnKey);
        return (ImmutableMap) Objects.firstNonNull((ImmutableMap) columnMap().get(columnKey), ImmutableMap.of());
    }

    public ImmutableSet<C> columnKeySet() {
        return columnMap().keySet();
    }

    public ImmutableMap<C, V> row(R rowKey) {
        Preconditions.checkNotNull(rowKey);
        return (ImmutableMap) Objects.firstNonNull((ImmutableMap) rowMap().get(rowKey), ImmutableMap.of());
    }

    public ImmutableSet<R> rowKeySet() {
        return rowMap().keySet();
    }

    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey) {
        return get(rowKey, columnKey) != null;
    }

    public boolean containsValue(@Nullable Object value) {
        return values().contains(value);
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object rowKey, Object columnKey) {
        throw new UnsupportedOperationException();
    }
}
