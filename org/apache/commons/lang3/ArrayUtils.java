package org.apache.commons.lang3;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.mutable.MutableInt;

public class ArrayUtils {
    public static final boolean[] EMPTY_BOOLEAN_ARRAY;
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY;
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY;
    public static final char[] EMPTY_CHAR_ARRAY;
    public static final Class<?>[] EMPTY_CLASS_ARRAY;
    public static final double[] EMPTY_DOUBLE_ARRAY;
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY;
    public static final float[] EMPTY_FLOAT_ARRAY;
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY;
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY;
    public static final int[] EMPTY_INT_ARRAY;
    public static final long[] EMPTY_LONG_ARRAY;
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY;
    public static final Object[] EMPTY_OBJECT_ARRAY;
    public static final short[] EMPTY_SHORT_ARRAY;
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY;
    public static final String[] EMPTY_STRING_ARRAY;
    public static final int INDEX_NOT_FOUND = -1;

    static {
        EMPTY_OBJECT_ARRAY = new Object[0];
        EMPTY_CLASS_ARRAY = new Class[0];
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_LONG_OBJECT_ARRAY = new Long[0];
        EMPTY_INT_ARRAY = new int[0];
        EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
        EMPTY_SHORT_ARRAY = new short[0];
        EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
        EMPTY_BYTE_ARRAY = new byte[0];
        EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
        EMPTY_DOUBLE_ARRAY = new double[0];
        EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
        EMPTY_FLOAT_ARRAY = new float[0];
        EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
        EMPTY_BOOLEAN_ARRAY = new boolean[0];
        EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
        EMPTY_CHAR_ARRAY = new char[0];
        EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
    }

    public static String toString(Object array) {
        return toString(array, "{}");
    }

    public static String toString(Object array, String stringIfNull) {
        return array == null ? stringIfNull : new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE).append(array).toString();
    }

    public static int hashCode(Object array) {
        return new HashCodeBuilder().append(array).toHashCode();
    }

    public static boolean isEquals(Object array1, Object array2) {
        return new EqualsBuilder().append(array1, array2).isEquals();
    }

    public static Map<Object, Object> toMap(Object[] array) {
        if (array == null) {
            return null;
        }
        Map<Object, Object> map = new HashMap((int) (((double) array.length) * 1.5d));
        for (int i = 0; i < array.length; i++) {
            Entry<?, ?> object = array[i];
            if (object instanceof Entry) {
                Entry<?, ?> entry = object;
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                Object[] entry2 = (Object[]) object;
                if (entry2.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                }
                map.put(entry2[0], entry2[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    public static <T> T[] toArray(T... items) {
        return items;
    }

    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return (Object[]) array.clone();
    }

    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }
        return (long[]) array.clone();
    }

    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }
        return (int[]) array.clone();
    }

    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }
        return (short[]) array.clone();
    }

    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }
        return (char[]) array.clone();
    }

    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }

    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }
        return (double[]) array.clone();
    }

    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }
        return (float[]) array.clone();
    }

    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }
        return (boolean[]) array.clone();
    }

    public static Object[] nullToEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        return array;
    }

    public static String[] nullToEmpty(String[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }

    public static long[] nullToEmpty(long[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }

    public static int[] nullToEmpty(int[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }

    public static short[] nullToEmpty(short[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        return array;
    }

    public static char[] nullToEmpty(char[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }

    public static byte[] nullToEmpty(byte[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        return array;
    }

    public static double[] nullToEmpty(double[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }

    public static float[] nullToEmpty(float[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array;
    }

    public static boolean[] nullToEmpty(boolean[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }

    public static Long[] nullToEmpty(Long[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }

    public static Integer[] nullToEmpty(Integer[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }

    public static Short[] nullToEmpty(Short[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }

    public static Character[] nullToEmpty(Character[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }

    public static Byte[] nullToEmpty(Byte[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }

    public static Double[] nullToEmpty(Double[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }

    public static Float[] nullToEmpty(Float[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }

    public static Boolean[] nullToEmpty(Boolean[] array) {
        if (array == null || array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }

    public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class<?> type = array.getClass().getComponentType();
        if (newSize <= 0) {
            return (Object[]) ((Object[]) Array.newInstance(type, 0));
        }
        T[] subarray = (Object[]) ((Object[]) Array.newInstance(type, newSize));
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] subarray = new long[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] subarray = new short[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] subarray = new char[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] subarray = new double[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] subarray = new float[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] subarray = new boolean[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    public static boolean isSameLength(Object[] array1, Object[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(long[] array1, long[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(int[] array1, int[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(short[] array1, short[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(char[] array1, char[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(byte[] array1, byte[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(double[] array1, double[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(float[] array1, float[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        if ((array1 != null || array2 == null || array2.length <= 0) && ((array2 != null || array1 == null || array1.length <= 0) && (array1 == null || array2 == null || array1.length == array2.length))) {
            return true;
        }
        return false;
    }

    public static int getLength(Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static boolean isSameType(Object array1, Object array2) {
        if (array1 != null && array2 != null) {
            return array1.getClass().getName().equals(array2.getClass().getName());
        }
        throw new IllegalArgumentException("The Array must not be null");
    }

    public static void reverse(Object[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                Object tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(long[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                long tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(int[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                int tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(short[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                short tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(char[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                char tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(byte[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                byte tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(double[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                double tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(float[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                float tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static void reverse(boolean[] array) {
        if (array != null) {
            int j = array.length + INDEX_NOT_FOUND;
            for (int i = 0; j > i; i++) {
                boolean tmp = array[j];
                array[j] = array[i];
                array[i] = tmp;
                j += INDEX_NOT_FOUND;
            }
        }
    }

    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        int i;
        if (objectToFind == null) {
            for (i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        int i;
        if (objectToFind == null) {
            for (i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(int[] array, int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(short[] array, short valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(char[] array, char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        int i = startIndex;
        while (i < array.length) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
            i++;
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE, tolerance);
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        int i = startIndex;
        while (i >= 0) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
            i += INDEX_NOT_FOUND;
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static boolean contains(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != INDEX_NOT_FOUND;
    }

    public static int indexOf(float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static int indexOf(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, Integer.MAX_VALUE);
    }

    public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (isEmpty(array)) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length + INDEX_NOT_FOUND;
        }
        for (int i = startIndex; i >= 0; i += INDEX_NOT_FOUND) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    public static char[] toPrimitive(Character[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    public static char[] toPrimitive(Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            Character b = array[i];
            result[i] = b == null ? valueForNull : b.charValue();
        }
        return result;
    }

    public static Character[] toObject(char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }

    public static long[] toPrimitive(Long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    public static long[] toPrimitive(Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            Long b = array[i];
            result[i] = b == null ? valueForNull : b.longValue();
        }
        return result;
    }

    public static Long[] toObject(long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    public static int[] toPrimitive(Integer[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    public static int[] toPrimitive(Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Integer b = array[i];
            result[i] = b == null ? valueForNull : b.intValue();
        }
        return result;
    }

    public static Integer[] toObject(int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    public static short[] toPrimitive(Short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    public static short[] toPrimitive(Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            Short b = array[i];
            result[i] = b == null ? valueForNull : b.shortValue();
        }
        return result;
    }

    public static Short[] toObject(short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }

    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = b == null ? valueForNull : b.byteValue();
        }
        return result;
    }

    public static Byte[] toObject(byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    public static double[] toPrimitive(Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            Double b = array[i];
            result[i] = b == null ? valueForNull : b.doubleValue();
        }
        return result;
    }

    public static Double[] toObject(double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    public static float[] toPrimitive(Float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    public static float[] toPrimitive(Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            Float b = array[i];
            result[i] = b == null ? valueForNull : b.floatValue();
        }
        return result;
    }

    public static Float[] toObject(float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            Boolean b = array[i];
            result[i] = b == null ? valueForNull : b.booleanValue();
        }
        return result;
    }

    public static Boolean[] toObject(boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
        }
        return result;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(long[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(int[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(short[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(char[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(byte[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(double[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(float[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static boolean isNotEmpty(boolean[] array) {
        return (array == null || array.length == 0) ? false : true;
    }

    public static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return clone((Object[]) array2);
        }
        if (array2 == null) {
            return clone((Object[]) array1);
        }
        Class<?> type1 = array1.getClass().getComponentType();
        Object[] joinedArray = (Object[]) ((Object[]) Array.newInstance(type1, array1.length + array2.length));
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
            return joinedArray;
        } catch (ArrayStoreException ase) {
            Class<?> type2 = array2.getClass().getComponentType();
            if (type1.isAssignableFrom(type2)) {
                throw ase;
            }
            throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
        }
    }

    public static boolean[] addAll(boolean[] array1, boolean... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        boolean[] joinedArray = new boolean[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static char[] addAll(char[] array1, char... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        char[] joinedArray = new char[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] addAll(byte[] array1, byte... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static short[] addAll(short[] array1, short... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        short[] joinedArray = new short[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static int[] addAll(int[] array1, int... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        int[] joinedArray = new int[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static long[] addAll(long[] array1, long... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        long[] joinedArray = new long[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static float[] addAll(float[] array1, float... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        float[] joinedArray = new float[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static double[] addAll(double[] array1, double... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        double[] joinedArray = new double[(array1.length + array2.length)];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static <T> T[] add(T[] array, T element) {
        Class<?> type;
        if (array != null) {
            type = array.getClass();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        Object[] newArray = (Object[]) ((Object[]) copyArrayGrow1(array, type));
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static boolean[] add(boolean[] array, boolean element) {
        boolean[] newArray = (boolean[]) copyArrayGrow1(array, Boolean.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static byte[] add(byte[] array, byte element) {
        byte[] newArray = (byte[]) copyArrayGrow1(array, Byte.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static char[] add(char[] array, char element) {
        char[] newArray = (char[]) copyArrayGrow1(array, Character.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static double[] add(double[] array, double element) {
        double[] newArray = (double[]) copyArrayGrow1(array, Double.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static float[] add(float[] array, float element) {
        float[] newArray = (float[]) copyArrayGrow1(array, Float.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static int[] add(int[] array, int element) {
        int[] newArray = (int[]) copyArrayGrow1(array, Integer.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static long[] add(long[] array, long element) {
        long[] newArray = (long[]) copyArrayGrow1(array, Long.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    public static short[] add(short[] array, short element) {
        short[] newArray = (short[]) copyArrayGrow1(array, Short.TYPE);
        newArray[newArray.length + INDEX_NOT_FOUND] = element;
        return newArray;
    }

    private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
        if (array == null) {
            return Array.newInstance(newArrayComponentType, 1);
        }
        int arrayLength = Array.getLength(array);
        Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
        System.arraycopy(array, 0, newArray, 0, arrayLength);
        return newArray;
    }

    public static <T> T[] add(T[] array, int index, T element) {
        Class<?> clss;
        if (array != null) {
            clss = array.getClass().getComponentType();
        } else if (element != null) {
            clss = element.getClass();
        } else {
            throw new IllegalArgumentException("Array and element cannot both be null");
        }
        return (Object[]) ((Object[]) add(array, index, element, clss));
    }

    public static boolean[] add(boolean[] array, int index, boolean element) {
        return (boolean[]) add(array, index, Boolean.valueOf(element), Boolean.TYPE);
    }

    public static char[] add(char[] array, int index, char element) {
        return (char[]) add(array, index, Character.valueOf(element), Character.TYPE);
    }

    public static byte[] add(byte[] array, int index, byte element) {
        return (byte[]) add(array, index, Byte.valueOf(element), Byte.TYPE);
    }

    public static short[] add(short[] array, int index, short element) {
        return (short[]) add(array, index, Short.valueOf(element), Short.TYPE);
    }

    public static int[] add(int[] array, int index, int element) {
        return (int[]) add(array, index, Integer.valueOf(element), Integer.TYPE);
    }

    public static long[] add(long[] array, int index, long element) {
        return (long[]) add(array, index, Long.valueOf(element), Long.TYPE);
    }

    public static float[] add(float[] array, int index, float element) {
        return (float[]) add(array, index, Float.valueOf(element), Float.TYPE);
    }

    public static double[] add(double[] array, int index, double element) {
        return (double[]) add(array, index, Double.valueOf(element), Double.TYPE);
    }

    private static Object add(Object array, int index, Object element, Class<?> clss) {
        if (array != null) {
            int length = Array.getLength(array);
            if (index > length || index < 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
            }
            Object result = Array.newInstance(clss, length + 1);
            System.arraycopy(array, 0, result, 0, index);
            Array.set(result, index, element);
            if (index < length) {
                System.arraycopy(array, index, result, index + 1, length - index);
            }
            return result;
        } else if (index != 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
        } else {
            Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
    }

    public static <T> T[] remove(T[] array, int index) {
        return (Object[]) remove((Object) array, index);
    }

    public static <T> T[] removeElement(T[] array, Object element) {
        int index = indexOf((Object[]) array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone((Object[]) array);
        }
        return remove((Object[]) array, index);
    }

    public static boolean[] remove(boolean[] array, int index) {
        return (boolean[]) remove((Object) array, index);
    }

    public static boolean[] removeElement(boolean[] array, boolean element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static byte[] remove(byte[] array, int index) {
        return (byte[]) remove((Object) array, index);
    }

    public static byte[] removeElement(byte[] array, byte element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static char[] remove(char[] array, int index) {
        return (char[]) remove((Object) array, index);
    }

    public static char[] removeElement(char[] array, char element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static double[] remove(double[] array, int index) {
        return (double[]) remove((Object) array, index);
    }

    public static double[] removeElement(double[] array, double element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static float[] remove(float[] array, int index) {
        return (float[]) remove((Object) array, index);
    }

    public static float[] removeElement(float[] array, float element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static int[] remove(int[] array, int index) {
        return (int[]) remove((Object) array, index);
    }

    public static int[] removeElement(int[] array, int element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static long[] remove(long[] array, int index) {
        return (long[]) remove((Object) array, index);
    }

    public static long[] removeElement(long[] array, long element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    public static short[] remove(short[] array, int index) {
        return (short[]) remove((Object) array, index);
    }

    public static short[] removeElement(short[] array, short element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }
        return remove(array, index);
    }

    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(array.getClass().getComponentType(), length + INDEX_NOT_FOUND);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length + INDEX_NOT_FOUND) {
            System.arraycopy(array, index + 1, result, index, (length - index) + INDEX_NOT_FOUND);
        }
        return result;
    }

    public static <T> T[] removeAll(T[] array, int... indices) {
        return (Object[]) removeAll((Object) array, clone(indices));
    }

    public static <T> T[] removeElements(T[] array, T... values) {
        if (isEmpty((Object[]) array) || isEmpty((Object[]) values)) {
            return clone((Object[]) array);
        }
        HashMap<T, MutableInt> occurrences = new HashMap(values.length);
        for (T v : values) {
            MutableInt count = (MutableInt) occurrences.get(v);
            if (count == null) {
                occurrences.put(v, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<T, MutableInt> e : occurrences.entrySet()) {
            Object v2 = e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf((Object[]) array, v2, found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll((Object[]) array, extractIndices(toRemove));
    }

    public static byte[] removeAll(byte[] array, int... indices) {
        return (byte[]) removeAll((Object) array, clone(indices));
    }

    public static byte[] removeElements(byte[] array, byte... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Byte, MutableInt> occurrences = new HashMap(values.length);
        for (byte v : values) {
            Byte boxed = Byte.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Byte, MutableInt> e : occurrences.entrySet()) {
            Byte v2 = (Byte) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.byteValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static short[] removeAll(short[] array, int... indices) {
        return (short[]) removeAll((Object) array, clone(indices));
    }

    public static short[] removeElements(short[] array, short... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Short, MutableInt> occurrences = new HashMap(values.length);
        for (short v : values) {
            Short boxed = Short.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Short, MutableInt> e : occurrences.entrySet()) {
            Short v2 = (Short) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.shortValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static int[] removeAll(int[] array, int... indices) {
        return (int[]) removeAll((Object) array, clone(indices));
    }

    public static int[] removeElements(int[] array, int... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Integer, MutableInt> occurrences = new HashMap(values.length);
        for (int v : values) {
            Integer boxed = Integer.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Integer, MutableInt> e : occurrences.entrySet()) {
            Integer v2 = (Integer) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.intValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static char[] removeAll(char[] array, int... indices) {
        return (char[]) removeAll((Object) array, clone(indices));
    }

    public static char[] removeElements(char[] array, char... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Character, MutableInt> occurrences = new HashMap(values.length);
        for (char v : values) {
            Character boxed = Character.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Character, MutableInt> e : occurrences.entrySet()) {
            Character v2 = (Character) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.charValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static long[] removeAll(long[] array, int... indices) {
        return (long[]) removeAll((Object) array, clone(indices));
    }

    public static long[] removeElements(long[] array, long... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Long, MutableInt> occurrences = new HashMap(values.length);
        for (long v : values) {
            Long boxed = Long.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Long, MutableInt> e : occurrences.entrySet()) {
            Long v2 = (Long) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.longValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static float[] removeAll(float[] array, int... indices) {
        return (float[]) removeAll((Object) array, clone(indices));
    }

    public static float[] removeElements(float[] array, float... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Float, MutableInt> occurrences = new HashMap(values.length);
        for (float v : values) {
            Float boxed = Float.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Float, MutableInt> e : occurrences.entrySet()) {
            Float v2 = (Float) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.floatValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static double[] removeAll(double[] array, int... indices) {
        return (double[]) removeAll((Object) array, clone(indices));
    }

    public static double[] removeElements(double[] array, double... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Double, MutableInt> occurrences = new HashMap(values.length);
        for (double v : values) {
            Double boxed = Double.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Double, MutableInt> e : occurrences.entrySet()) {
            Double v2 = (Double) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.doubleValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    public static boolean[] removeAll(boolean[] array, int... indices) {
        return (boolean[]) removeAll((Object) array, clone(indices));
    }

    public static boolean[] removeElements(boolean[] array, boolean... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        HashMap<Boolean, MutableInt> occurrences = new HashMap(values.length);
        for (boolean v : values) {
            Boolean boxed = Boolean.valueOf(v);
            MutableInt count = (MutableInt) occurrences.get(boxed);
            if (count == null) {
                occurrences.put(boxed, new MutableInt(1));
            } else {
                count.increment();
            }
        }
        HashSet<Integer> toRemove = new HashSet();
        for (Entry<Boolean, MutableInt> e : occurrences.entrySet()) {
            Boolean v2 = (Boolean) e.getKey();
            int found = 0;
            int i = 0;
            int ct = ((MutableInt) e.getValue()).intValue();
            while (i < ct) {
                found = indexOf(array, v2.booleanValue(), found);
                if (found < 0) {
                    break;
                }
                int found2 = found + 1;
                toRemove.add(Integer.valueOf(found));
                i++;
                found = found2;
            }
        }
        return removeAll(array, extractIndices(toRemove));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object removeAll(java.lang.Object r12, int... r13) {
        /*
        r11 = 0;
        r6 = getLength(r12);
        r2 = 0;
        r9 = isNotEmpty(r13);
        if (r9 == 0) goto L_0x0044;
    L_0x000c:
        java.util.Arrays.sort(r13);
        r4 = r13.length;
        r7 = r6;
    L_0x0011:
        r4 = r4 + -1;
        if (r4 < 0) goto L_0x0044;
    L_0x0015:
        r5 = r13[r4];
        if (r5 < 0) goto L_0x001b;
    L_0x0019:
        if (r5 < r6) goto L_0x003e;
    L_0x001b:
        r9 = new java.lang.IndexOutOfBoundsException;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Index: ";
        r10 = r10.append(r11);
        r10 = r10.append(r5);
        r11 = ", Length: ";
        r10 = r10.append(r11);
        r10 = r10.append(r6);
        r10 = r10.toString();
        r9.<init>(r10);
        throw r9;
    L_0x003e:
        if (r5 >= r7) goto L_0x0011;
    L_0x0040:
        r2 = r2 + 1;
        r7 = r5;
        goto L_0x0011;
    L_0x0044:
        r9 = r12.getClass();
        r9 = r9.getComponentType();
        r10 = r6 - r2;
        r8 = java.lang.reflect.Array.newInstance(r9, r10);
        if (r2 >= r6) goto L_0x0076;
    L_0x0054:
        r3 = r6;
        r1 = r6 - r2;
        r9 = r13.length;
        r4 = r9 + -1;
    L_0x005a:
        if (r4 < 0) goto L_0x0071;
    L_0x005c:
        r5 = r13[r4];
        r9 = r3 - r5;
        r10 = 1;
        if (r9 <= r10) goto L_0x006d;
    L_0x0063:
        r9 = r3 - r5;
        r0 = r9 + -1;
        r1 = r1 - r0;
        r9 = r5 + 1;
        java.lang.System.arraycopy(r12, r9, r8, r1, r0);
    L_0x006d:
        r3 = r5;
        r4 = r4 + -1;
        goto L_0x005a;
    L_0x0071:
        if (r3 <= 0) goto L_0x0076;
    L_0x0073:
        java.lang.System.arraycopy(r12, r11, r8, r11, r3);
    L_0x0076:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.ArrayUtils.removeAll(java.lang.Object, int[]):java.lang.Object");
    }

    private static int[] extractIndices(HashSet<Integer> coll) {
        int[] result = new int[coll.size()];
        int i = 0;
        Iterator i$ = coll.iterator();
        while (i$.hasNext()) {
            int i2 = i + 1;
            result[i] = ((Integer) i$.next()).intValue();
            i = i2;
        }
        return result;
    }
}
