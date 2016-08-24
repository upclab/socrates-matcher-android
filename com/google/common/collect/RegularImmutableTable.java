package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Table.Cell;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

@GwtCompatible
abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {

    /* renamed from: com.google.common.collect.RegularImmutableTable.1 */
    static class C02561 implements Comparator<Cell<R, C, V>> {
        final /* synthetic */ Comparator val$columnComparator;
        final /* synthetic */ Comparator val$rowComparator;

        C02561(Comparator comparator, Comparator comparator2) {
            this.val$rowComparator = comparator;
            this.val$columnComparator = comparator2;
        }

        public int compare(Cell<R, C, V> cell1, Cell<R, C, V> cell2) {
            int i = 0;
            int rowCompare = this.val$rowComparator == null ? 0 : this.val$rowComparator.compare(cell1.getRowKey(), cell2.getRowKey());
            if (rowCompare != 0) {
                return rowCompare;
            }
            if (this.val$columnComparator != null) {
                i = this.val$columnComparator.compare(cell1.getColumnKey(), cell2.getColumnKey());
            }
            return i;
        }
    }

    private final class CellSet extends ImmutableSet<Cell<R, C, V>> {

        /* renamed from: com.google.common.collect.RegularImmutableTable.CellSet.1 */
        class C10911 extends ImmutableAsList<Cell<R, C, V>> {
            C10911() {
            }

            public Cell<R, C, V> get(int index) {
                return RegularImmutableTable.this.getCell(index);
            }

            ImmutableCollection<Cell<R, C, V>> delegateCollection() {
                return CellSet.this;
            }
        }

        private CellSet() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        public UnmodifiableIterator<Cell<R, C, V>> iterator() {
            return asList().iterator();
        }

        ImmutableList<Cell<R, C, V>> createAsList() {
            return new C10911();
        }

        public boolean contains(@Nullable Object object) {
            if (!(object instanceof Cell)) {
                return false;
            }
            Cell<?, ?, ?> cell = (Cell) object;
            Object value = RegularImmutableTable.this.get(cell.getRowKey(), cell.getColumnKey());
            if (value == null || !value.equals(cell.getValue())) {
                return false;
            }
            return true;
        }

        boolean isPartialView() {
            return false;
        }
    }

    private final class Values extends ImmutableList<V> {
        private Values() {
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }

        public V get(int index) {
            return RegularImmutableTable.this.getValue(index);
        }

        boolean isPartialView() {
            return true;
        }
    }

    abstract Cell<R, C, V> getCell(int i);

    abstract V getValue(int i);

    RegularImmutableTable() {
    }

    final ImmutableSet<Cell<R, C, V>> createCellSet() {
        return isEmpty() ? ImmutableSet.of() : new CellSet();
    }

    final ImmutableCollection<V> createValues() {
        return isEmpty() ? ImmutableList.of() : new Values();
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(List<Cell<R, C, V>> cells, @Nullable Comparator<? super R> rowComparator, @Nullable Comparator<? super C> columnComparator) {
        Preconditions.checkNotNull(cells);
        if (!(rowComparator == null && columnComparator == null)) {
            Collections.sort(cells, new C02561(rowComparator, columnComparator));
        }
        return forCellsInternal(cells, rowComparator, columnComparator);
    }

    static <R, C, V> RegularImmutableTable<R, C, V> forCells(Iterable<Cell<R, C, V>> cells) {
        return forCellsInternal(cells, null, null);
    }

    private static final <R, C, V> RegularImmutableTable<R, C, V> forCellsInternal(Iterable<Cell<R, C, V>> cells, @Nullable Comparator<? super R> rowComparator, @Nullable Comparator<? super C> columnComparator) {
        Builder<R> rowSpaceBuilder = ImmutableSet.builder();
        Builder<C> columnSpaceBuilder = ImmutableSet.builder();
        ImmutableList<Cell<R, C, V>> cellList = ImmutableList.copyOf((Iterable) cells);
        Iterator i$ = cellList.iterator();
        while (i$.hasNext()) {
            Cell<R, C, V> cell = (Cell) i$.next();
            rowSpaceBuilder.add(cell.getRowKey());
            columnSpaceBuilder.add(cell.getColumnKey());
        }
        ImmutableSet<R> rowSpace = rowSpaceBuilder.build();
        if (rowComparator != null) {
            Collection rowList = Lists.newArrayList((Iterable) rowSpace);
            Collections.sort(rowList, rowComparator);
            rowSpace = ImmutableSet.copyOf(rowList);
        }
        ImmutableSet<C> columnSpace = columnSpaceBuilder.build();
        if (columnComparator != null) {
            Collection columnList = Lists.newArrayList((Iterable) columnSpace);
            Collections.sort(columnList, columnComparator);
            columnSpace = ImmutableSet.copyOf(columnList);
        }
        return ((long) cellList.size()) > (((long) rowSpace.size()) * ((long) columnSpace.size())) / 2 ? new DenseImmutableTable(cellList, rowSpace, columnSpace) : new SparseImmutableTable(cellList, rowSpace, columnSpace);
    }
}
