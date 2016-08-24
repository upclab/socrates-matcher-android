package org.apache.commons.lang3;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeSet;
import org.apache.commons.lang3.exception.CloneFailedException;
import org.apache.commons.lang3.mutable.MutableInt;

public class ObjectUtils {
    public static final Null NULL;

    public static class Null implements Serializable {
        private static final long serialVersionUID = 7092611880189329093L;

        Null() {
        }

        private Object readResolve() {
            return ObjectUtils.NULL;
        }
    }

    static {
        NULL = new Null();
    }

    public static <T> T defaultIfNull(T object, T defaultValue) {
        return object != null ? object : defaultValue;
    }

    public static <T> T firstNonNull(T... values) {
        if (values != null) {
            for (T val : values) {
                if (val != null) {
                    return val;
                }
            }
        }
        return null;
    }

    public static boolean equals(Object object1, Object object2) {
        if (object1 == object2) {
            return true;
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        return object1.equals(object2);
    }

    public static boolean notEqual(Object object1, Object object2) {
        return !equals(object1, object2);
    }

    public static int hashCode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public static int hashCodeMulti(Object... objects) {
        int hash = 1;
        if (objects != null) {
            for (Object object : objects) {
                hash = (hash * 31) + hashCode(object);
            }
        }
        return hash;
    }

    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        identityToString(buffer, object);
        return buffer.toString();
    }

    public static void identityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            throw new NullPointerException("Cannot get the toString of a null identity");
        }
        buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
    }

    public static String toString(Object obj) {
        return obj == null ? StringUtils.EMPTY : obj.toString();
    }

    public static String toString(Object obj, String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }

    public static <T extends Comparable<? super T>> T min(T... values) {
        T result = null;
        if (values != null) {
            for (T value : values) {
                if (compare(value, result, true) < 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    public static <T extends Comparable<? super T>> T max(T... values) {
        T result = null;
        if (values != null) {
            for (T value : values) {
                if (compare(value, result, false) > 0) {
                    result = value;
                }
            }
        }
        return result;
    }

    public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
        return compare(c1, c2, false);
    }

    public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
        int i = -1;
        if (c1 == c2) {
            return 0;
        }
        if (c1 == null) {
            if (nullGreater) {
                return 1;
            }
            return -1;
        } else if (c2 != null) {
            return c1.compareTo(c2);
        } else {
            if (!nullGreater) {
                i = 1;
            }
            return i;
        }
    }

    public static <T extends Comparable<? super T>> T median(T... items) {
        Validate.notEmpty((Object[]) items);
        Validate.noNullElements((Object[]) items);
        TreeSet<T> sort = new TreeSet();
        Collections.addAll(sort, items);
        return (Comparable) sort.toArray()[(sort.size() - 1) / 2];
    }

    public static <T> T median(Comparator<T> comparator, T... items) {
        Validate.notEmpty((Object[]) items, "null/empty items", new Object[0]);
        Validate.noNullElements((Object[]) items);
        Validate.notNull(comparator, "null comparator", new Object[0]);
        TreeSet<T> sort = new TreeSet(comparator);
        Collections.addAll(sort, items);
        return sort.toArray()[(sort.size() - 1) / 2];
    }

    public static <T> T mode(T... items) {
        if (!ArrayUtils.isNotEmpty((Object[]) items)) {
            return null;
        }
        HashMap<T, MutableInt> occurrences = new HashMap(items.length);
        for (T t : items) {
            MutableInt count = (MutableInt) occurrences.get(t);
            if (count == null) {
                occurrences.put(t, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        T result = null;
        int max = 0;
        for (Entry<T, MutableInt> e : occurrences.entrySet()) {
            int cmp = ((MutableInt) e.getValue()).intValue();
            if (cmp == max) {
                result = null;
            } else if (cmp > max) {
                max = cmp;
                result = e.getKey();
            }
        }
        return result;
    }

    public static <T> T clone(T obj) {
        if (!(obj instanceof Cloneable)) {
            return null;
        }
        T result;
        if (obj.getClass().isArray()) {
            Class<?> componentType = obj.getClass().getComponentType();
            if (componentType.isPrimitive()) {
                int length = Array.getLength(obj);
                result = Array.newInstance(componentType, length);
                int length2 = length;
                while (true) {
                    length = length2 - 1;
                    if (length2 <= 0) {
                        break;
                    }
                    Array.set(result, length, Array.get(obj, length));
                    length2 = length;
                }
            } else {
                result = ((Object[]) obj).clone();
            }
        } else {
            try {
                result = obj.getClass().getMethod("clone", new Class[0]).invoke(obj, new Object[0]);
            } catch (NoSuchMethodException e) {
                throw new CloneFailedException("Cloneable type " + obj.getClass().getName() + " has no clone method", e);
            } catch (IllegalAccessException e2) {
                throw new CloneFailedException("Cannot clone Cloneable type " + obj.getClass().getName(), e2);
            } catch (InvocationTargetException e3) {
                throw new CloneFailedException("Exception cloning Cloneable type " + obj.getClass().getName(), e3.getCause());
            }
        }
        return result;
    }

    public static <T> T cloneIfPossible(T obj) {
        T clone = clone(obj);
        return clone == null ? obj : clone;
    }
}
