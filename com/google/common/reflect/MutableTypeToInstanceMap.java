package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@Beta
public final class MutableTypeToInstanceMap<B> extends ForwardingMap<TypeToken<? extends B>, B> implements TypeToInstanceMap<B> {
    private final Map<TypeToken<? extends B>, B> backingMap;

    private static final class UnmodifiableEntry<K, V> extends ForwardingMapEntry<K, V> {
        private final Entry<K, V> delegate;

        /* renamed from: com.google.common.reflect.MutableTypeToInstanceMap.UnmodifiableEntry.2 */
        static class C07842 implements Function<Entry<K, V>, Entry<K, V>> {
            C07842() {
            }

            public Entry<K, V> apply(Entry<K, V> entry) {
                return new UnmodifiableEntry(null);
            }
        }

        /* renamed from: com.google.common.reflect.MutableTypeToInstanceMap.UnmodifiableEntry.1 */
        static class C10941 extends ForwardingSet<Entry<K, V>> {
            final /* synthetic */ Set val$entries;

            C10941(Set set) {
                this.val$entries = set;
            }

            protected Set<Entry<K, V>> delegate() {
                return this.val$entries;
            }

            public Iterator<Entry<K, V>> iterator() {
                return UnmodifiableEntry.transformEntries(super.iterator());
            }

            public Object[] toArray() {
                return standardToArray();
            }

            public <T> T[] toArray(T[] array) {
                return standardToArray(array);
            }
        }

        static <K, V> Set<Entry<K, V>> transformEntries(Set<Entry<K, V>> entries) {
            return new C10941(entries);
        }

        private static <K, V> Iterator<Entry<K, V>> transformEntries(Iterator<Entry<K, V>> entries) {
            return Iterators.transform(entries, new C07842());
        }

        private UnmodifiableEntry(Entry<K, V> delegate) {
            this.delegate = (Entry) Preconditions.checkNotNull(delegate);
        }

        protected Entry<K, V> delegate() {
            return this.delegate;
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }
    }

    public MutableTypeToInstanceMap() {
        this.backingMap = Maps.newHashMap();
    }

    @Nullable
    public <T extends B> T getInstance(Class<T> type) {
        return trustedGet(TypeToken.of((Class) type));
    }

    @Nullable
    public <T extends B> T putInstance(Class<T> type, @Nullable T value) {
        return trustedPut(TypeToken.of((Class) type), value);
    }

    @Nullable
    public <T extends B> T getInstance(TypeToken<T> type) {
        return trustedGet(type.rejectTypeVariables());
    }

    @Nullable
    public <T extends B> T putInstance(TypeToken<T> type, @Nullable T value) {
        return trustedPut(type.rejectTypeVariables(), value);
    }

    public B put(TypeToken<? extends B> typeToken, B b) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    public void putAll(Map<? extends TypeToken<? extends B>, ? extends B> map) {
        throw new UnsupportedOperationException("Please use putInstance() instead.");
    }

    public Set<Entry<TypeToken<? extends B>, B>> entrySet() {
        return UnmodifiableEntry.transformEntries(super.entrySet());
    }

    protected Map<TypeToken<? extends B>, B> delegate() {
        return this.backingMap;
    }

    @Nullable
    private <T extends B> T trustedPut(TypeToken<T> type, @Nullable T value) {
        return this.backingMap.put(type, value);
    }

    @Nullable
    private <T extends B> T trustedGet(TypeToken<T> type) {
        return this.backingMap.get(type);
    }
}
