package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.Map.Entry;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
    private final ImmutableMap<K, V> map;

    @GwtIncompatible("serialization")
    private static class SerializedForm<V> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<?, V> map;

        SerializedForm(ImmutableMap<?, V> map) {
            this.map = map;
        }

        Object readResolve() {
            return this.map.values();
        }
    }

    /* renamed from: com.google.common.collect.ImmutableMapValues.1 */
    class C10841 extends ImmutableAsList<V> {
        final /* synthetic */ ImmutableList val$entryList;

        C10841(ImmutableList immutableList) {
            this.val$entryList = immutableList;
        }

        public V get(int index) {
            return ((Entry) this.val$entryList.get(index)).getValue();
        }

        ImmutableCollection<V> delegateCollection() {
            return ImmutableMapValues.this;
        }
    }

    ImmutableMapValues(ImmutableMap<K, V> map) {
        this.map = map;
    }

    public int size() {
        return this.map.size();
    }

    public UnmodifiableIterator<V> iterator() {
        return Maps.valueIterator(this.map.entrySet().iterator());
    }

    public boolean contains(@Nullable Object object) {
        return object != null && Iterators.contains(iterator(), object);
    }

    boolean isPartialView() {
        return true;
    }

    ImmutableList<V> createAsList() {
        return new C10841(this.map.entrySet().asList());
    }

    @GwtIncompatible("serialization")
    Object writeReplace() {
        return new SerializedForm(this.map);
    }
}
