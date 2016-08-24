package org.apache.commons.lang3;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;
    private static final Pattern WHITESPACE_BLOCK;

    private static class InitStripAccents {
        private static final Throwable java6Exception;
        private static final Method java6NormalizeMethod;
        private static final Object java6NormalizerFormNFD;
        private static final Pattern java6Pattern;
        private static final Method sunDecomposeMethod;
        private static final Throwable sunException;
        private static final Pattern sunPattern;

        private InitStripAccents() {
        }

        static {
            sunPattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            java6Pattern = sunPattern;
            Object obj = null;
            Method _java6NormalizeMethod = null;
            Method _sunDecomposeMethod = null;
            Throwable _java6Exception = null;
            Throwable _sunException = null;
            try {
                obj = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer$Form").getField("NFD").get(null);
                _java6NormalizeMethod = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer").getMethod("normalize", new Class[]{CharSequence.class, normalizerFormClass});
            } catch (Throwable e1) {
                _java6Exception = e1;
                try {
                    _sunDecomposeMethod = Thread.currentThread().getContextClassLoader().loadClass("sun.text.Normalizer").getMethod("decompose", new Class[]{String.class, Boolean.TYPE, Integer.TYPE});
                } catch (Throwable e2) {
                    _sunException = e2;
                }
            }
            java6Exception = _java6Exception;
            java6NormalizerFormNFD = obj;
            java6NormalizeMethod = _java6NormalizeMethod;
            sunException = _sunException;
            sunDecomposeMethod = _sunDecomposeMethod;
        }
    }

    static {
        WHITESPACE_BLOCK = Pattern.compile("\\s+");
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int strLen = cs.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        str = strip(str, null);
        if (str.length() == 0) {
            str = null;
        }
        return str;
    }

    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    public static String strip(String str, String stripChars) {
        return isEmpty(str) ? str : stripEnd(stripStart(str, stripChars), stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);
    }

    public static String stripEnd(String str, String stripChars) {
        if (str == null) {
            return str;
        }
        int end = str.length();
        if (end == 0) {
            return str;
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end + INDEX_NOT_FOUND))) {
                end += INDEX_NOT_FOUND;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end + INDEX_NOT_FOUND)) != INDEX_NOT_FOUND) {
                end += INDEX_NOT_FOUND;
            }
        }
        return str.substring(0, end);
    }

    public static String[] stripAll(String... strs) {
        return stripAll(strs, null);
    }

    public static String[] stripAll(String[] strs, String stripChars) {
        if (strs != null) {
            int strsLen = strs.length;
            if (strsLen != 0) {
                String[] newArr = new String[strsLen];
                for (int i = 0; i < strsLen; i++) {
                    newArr[i] = strip(strs[i], stripChars);
                }
                return newArr;
            }
        }
        return strs;
    }

    public static String stripAccents(String input) {
        if (input == null) {
            return null;
        }
        try {
            if (InitStripAccents.java6NormalizeMethod != null) {
                return removeAccentsJava6(input);
            }
            if (InitStripAccents.sunDecomposeMethod != null) {
                return removeAccentsSUN(input);
            }
            throw new UnsupportedOperationException("The stripAccents(CharSequence) method requires at least Java6, but got: " + InitStripAccents.java6Exception + "; or a Sun JVM: " + InitStripAccents.sunException);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("IllegalArgumentException occurred", iae);
        } catch (IllegalAccessException iae2) {
            throw new RuntimeException("IllegalAccessException occurred", iae2);
        } catch (InvocationTargetException ite) {
            throw new RuntimeException("InvocationTargetException occurred", ite);
        } catch (SecurityException se) {
            throw new RuntimeException("SecurityException occurred", se);
        }
    }

    private static String removeAccentsJava6(CharSequence text) throws IllegalAccessException, InvocationTargetException {
        if (InitStripAccents.java6NormalizeMethod == null || InitStripAccents.java6NormalizerFormNFD == null) {
            throw new IllegalStateException("java.text.Normalizer is not available", InitStripAccents.java6Exception);
        }
        return InitStripAccents.java6Pattern.matcher((String) InitStripAccents.java6NormalizeMethod.invoke(null, new Object[]{text, InitStripAccents.java6NormalizerFormNFD})).replaceAll(EMPTY);
    }

    private static String removeAccentsSUN(CharSequence text) throws IllegalAccessException, InvocationTargetException {
        if (InitStripAccents.sunDecomposeMethod == null) {
            throw new IllegalStateException("sun.text.Normalizer is not available", InitStripAccents.sunException);
        }
        return InitStripAccents.sunPattern.matcher((String) InitStripAccents.sunDecomposeMethod.invoke(null, new Object[]{text, Boolean.FALSE, Integer.valueOf(0)})).replaceAll(EMPTY);
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        if (cs1 == null) {
            return cs2 == null;
        } else {
            return cs1.equals(cs2);
        }
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        if (str1 != null && str2 != null) {
            return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, Math.max(str1.length(), str2.length()));
        } else if (str1 == str2) {
            return true;
        } else {
            return false;
        }
    }

    public static int indexOf(CharSequence seq, int searchChar) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, 0);
    }

    public static int indexOf(CharSequence seq, int searchChar, int startPos) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchChar, startPos);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchSeq, 0);
    }

    public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.indexOf(seq, searchSeq, startPos);
    }

    public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }

    private static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal, boolean lastIndex) {
        int index = INDEX_NOT_FOUND;
        if (str == null || searchStr == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return lastIndex ? str.length() : 0;
        }
        int found = 0;
        if (lastIndex) {
            index = str.length();
        }
        do {
            if (lastIndex) {
                index = CharSequenceUtils.lastIndexOf(str, searchStr, index + INDEX_NOT_FOUND);
            } else {
                index = CharSequenceUtils.indexOf(str, searchStr, index + 1);
            }
            if (index < 0) {
                return index;
            }
            found++;
        } while (found < ordinal);
        return index;
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos < 0) {
            startPos = 0;
        }
        int endLimit = (str.length() - searchStr.length()) + 1;
        if (startPos > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i < endLimit; i++) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int lastIndexOf(CharSequence seq, int searchChar) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
    }

    public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
        if (isEmpty(seq)) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length());
    }

    public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, true);
    }

    public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
        if (seq == null || searchSeq == null) {
            return INDEX_NOT_FOUND;
        }
        return CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos);
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        return lastIndexOfIgnoreCase(str, searchStr, str.length());
    }

    public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return INDEX_NOT_FOUND;
        }
        if (startPos > str.length() - searchStr.length()) {
            startPos = str.length() - searchStr.length();
        }
        if (startPos < 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchStr.length() == 0) {
            return startPos;
        }
        for (int i = startPos; i >= 0; i += INDEX_NOT_FOUND) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(CharSequence seq, int searchChar) {
        if (!isEmpty(seq) && CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0) {
            return true;
        }
        return false;
    }

    public static boolean contains(CharSequence seq, CharSequence searchSeq) {
        if (seq == null || searchSeq == null || CharSequenceUtils.indexOf(seq, searchSeq, 0) < 0) {
            return false;
        }
        return true;
    }

    public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsWhitespace(CharSequence seq) {
        if (isEmpty(seq)) {
            return false;
        }
        int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(seq.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int indexOfAny(CharSequence cs, char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        int csLen = cs.length();
        int csLast = csLen + INDEX_NOT_FOUND;
        int searchLen = searchChars.length;
        int searchLast = searchLen + INDEX_NOT_FOUND;
        int i = 0;
        while (i < csLen) {
            char ch = cs.charAt(i);
            int j = 0;
            while (j < searchLen) {
                if (searchChars[j] == ch && (i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || searchChars[j + 1] == cs.charAt(i + 1))) {
                    return i;
                }
                j++;
            }
            i++;
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOfAny(CharSequence cs, String searchChars) {
        if (isEmpty(cs) || isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        return indexOfAny(cs, searchChars.toCharArray());
    }

    public static boolean containsAny(CharSequence cs, char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return false;
        }
        int csLength = cs.length();
        int searchLength = searchChars.length;
        int csLast = csLength + INDEX_NOT_FOUND;
        int searchLast = searchLength + INDEX_NOT_FOUND;
        int i = 0;
        while (i < csLength) {
            char ch = cs.charAt(i);
            int j = 0;
            while (j < searchLength) {
                if (searchChars[j] == ch) {
                    if (!Character.isHighSurrogate(ch) || j == searchLast) {
                        return true;
                    }
                    if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                        return true;
                    }
                }
                j++;
            }
            i++;
        }
        return false;
    }

    public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
        if (searchChars == null) {
            return false;
        }
        return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
    }

    public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
        if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        int csLen = cs.length();
        int csLast = csLen + INDEX_NOT_FOUND;
        int searchLen = searchChars.length;
        int searchLast = searchLen + INDEX_NOT_FOUND;
        int i = 0;
        while (i < csLen) {
            char ch = cs.charAt(i);
            int j = 0;
            while (j < searchLen) {
                if (searchChars[j] != ch || (i < csLast && j < searchLast && Character.isHighSurrogate(ch) && searchChars[j + 1] != cs.charAt(i + 1))) {
                    j++;
                } else {
                    i++;
                }
            }
            return i;
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
        if (isEmpty(seq) || isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        int strLen = seq.length();
        for (int i = 0; i < strLen; i++) {
            boolean chFound;
            int ch = seq.charAt(i);
            if (CharSequenceUtils.indexOf(searchChars, ch, 0) >= 0) {
                chFound = true;
            } else {
                chFound = false;
            }
            if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
                int ch2 = seq.charAt(i + 1);
                if (chFound && CharSequenceUtils.indexOf(searchChars, ch2, 0) < 0) {
                    return i;
                }
            } else if (!chFound) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean containsOnly(CharSequence cs, char... valid) {
        if (valid == null || cs == null) {
            return false;
        }
        if (cs.length() == 0) {
            return true;
        }
        if (valid.length == 0) {
            return false;
        }
        if (indexOfAnyBut(cs, valid) != INDEX_NOT_FOUND) {
            return false;
        }
        return true;
    }

    public static boolean containsOnly(CharSequence cs, String validChars) {
        if (cs == null || validChars == null) {
            return false;
        }
        return containsOnly(cs, validChars.toCharArray());
    }

    public static boolean containsNone(CharSequence cs, char... searchChars) {
        if (cs == null || searchChars == null) {
            return true;
        }
        int csLen = cs.length();
        int csLast = csLen + INDEX_NOT_FOUND;
        int searchLen = searchChars.length;
        int searchLast = searchLen + INDEX_NOT_FOUND;
        int i = 0;
        while (i < csLen) {
            char ch = cs.charAt(i);
            int j = 0;
            while (j < searchLen) {
                if (searchChars[j] == ch) {
                    if (!Character.isHighSurrogate(ch) || j == searchLast) {
                        return false;
                    }
                    if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                        return false;
                    }
                }
                j++;
            }
            i++;
        }
        return true;
    }

    public static boolean containsNone(CharSequence cs, String invalidChars) {
        if (cs == null || invalidChars == null) {
            return true;
        }
        return containsNone(cs, invalidChars.toCharArray());
    }

    public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND;
        }
        int ret = Integer.MAX_VALUE;
        for (CharSequence search : searchStrs) {
            if (search != null) {
                int tmp = CharSequenceUtils.indexOf(str, search, 0);
                if (tmp != INDEX_NOT_FOUND && tmp < ret) {
                    ret = tmp;
                }
            }
        }
        return ret == Integer.MAX_VALUE ? INDEX_NOT_FOUND : ret;
    }

    public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
        if (str == null || searchStrs == null) {
            return INDEX_NOT_FOUND;
        }
        int ret = INDEX_NOT_FOUND;
        for (CharSequence search : searchStrs) {
            if (search != null) {
                int tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
                if (tmp > ret) {
                    ret = tmp;
                }
            }
        }
        return ret;
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }
        if (start < 0) {
            start += str.length();
        }
        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY;
        }
        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end += str.length();
        }
        if (start < 0) {
            start += str.length();
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return EMPTY;
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        return str.length() > len ? str.substring(0, len) : str;
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        return str.length() > len ? str.substring(str.length() - len) : str;
    }

    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || pos > str.length()) {
            return EMPTY;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (str.length() <= pos + len) {
            return str.substring(pos);
        }
        return str.substring(pos, pos + len);
    }

    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        return pos != INDEX_NOT_FOUND ? str.substring(0, pos) : str;
    }

    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str.substring(separator.length() + pos);
    }

    public static String substringBeforeLast(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        return pos != INDEX_NOT_FOUND ? str.substring(0, pos) : str;
    }

    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == INDEX_NOT_FOUND || pos == str.length() - separator.length()) {
            return EMPTY;
        }
        return str.substring(separator.length() + pos);
    }

    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start == INDEX_NOT_FOUND) {
            return null;
        }
        int end = str.indexOf(close, open.length() + start);
        if (end != INDEX_NOT_FOUND) {
            return str.substring(open.length() + start, end);
        }
        return null;
    }

    public static String[] substringsBetween(String str, String open, String close) {
        if (str == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int closeLen = close.length();
        int openLen = open.length();
        List<String> list = new ArrayList();
        int pos = 0;
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos);
            if (start >= 0) {
                start += openLen;
                int end = str.indexOf(close, start);
                if (end < 0) {
                    break;
                }
                list.add(str.substring(start, end));
                pos = end + closeLen;
            } else {
                break;
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static String[] split(String str) {
        return split(str, null, INDEX_NOT_FOUND);
    }

    public static String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    public static String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, INDEX_NOT_FOUND, false);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, INDEX_NOT_FOUND, false);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, INDEX_NOT_FOUND, true);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        if (separator == null || EMPTY.equals(separator)) {
            return splitWorker(str, null, max, preserveAllTokens);
        }
        int separatorLength = separator.length();
        ArrayList<String> substrings = new ArrayList();
        int numberOfSubstrings = 0;
        int beg = 0;
        int end = 0;
        while (end < len) {
            end = str.indexOf(separator, beg);
            if (end <= INDEX_NOT_FOUND) {
                substrings.add(str.substring(beg));
                end = len;
            } else if (end > beg) {
                numberOfSubstrings++;
                if (numberOfSubstrings == max) {
                    end = len;
                    substrings.add(str.substring(beg));
                } else {
                    substrings.add(str.substring(beg, end));
                    beg = end + separatorLength;
                }
            } else {
                if (preserveAllTokens) {
                    numberOfSubstrings++;
                    if (numberOfSubstrings == max) {
                        end = len;
                        substrings.add(str.substring(beg));
                    } else {
                        substrings.add(EMPTY);
                    }
                }
                beg = end + separatorLength;
            }
        }
        return (String[]) substrings.toArray(new String[substrings.size()]);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return splitWorker(str, null, INDEX_NOT_FOUND, true);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                    lastMatch = true;
                }
                i++;
                start = i;
            } else {
                lastMatch = false;
                match = true;
                i++;
            }
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, INDEX_NOT_FOUND, true);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, true);
    }

    private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        List<String> list = new ArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;
        boolean lastMatch = false;
        int sizePlus1;
        int sizePlus12;
        if (separatorChars == null) {
            sizePlus1 = 1;
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        sizePlus12 = sizePlus1 + 1;
                        if (sizePlus1 == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    } else {
                        sizePlus12 = sizePlus1;
                    }
                    i++;
                    start = i;
                    sizePlus1 = sizePlus12;
                } else {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);
            sizePlus1 = 1;
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        sizePlus12 = sizePlus1 + 1;
                        if (sizePlus1 == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    } else {
                        sizePlus12 = sizePlus1;
                    }
                    i++;
                    start = i;
                    sizePlus1 = sizePlus12;
                } else {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
            sizePlus12 = sizePlus1;
        } else {
            sizePlus1 = 1;
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match || preserveAllTokens) {
                        lastMatch = true;
                        sizePlus12 = sizePlus1 + 1;
                        if (sizePlus1 == max) {
                            i = len;
                            lastMatch = false;
                        }
                        list.add(str.substring(start, i));
                        match = false;
                    } else {
                        sizePlus12 = sizePlus1;
                    }
                    i++;
                    start = i;
                    sizePlus1 = sizePlus12;
                } else {
                    lastMatch = false;
                    match = true;
                    i++;
                }
            }
        }
        if (match || (preserveAllTokens && lastMatch)) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static String[] splitByCharacterType(String str) {
        return splitByCharacterType(str, false);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return splitByCharacterType(str, true);
    }

    private static String[] splitByCharacterType(String str, boolean camelCase) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        char[] c = str.toCharArray();
        List<String> list = new ArrayList();
        int tokenStart = 0;
        int currentType = Character.getType(c[0]);
        for (int pos = 0 + 1; pos < c.length; pos++) {
            int type = Character.getType(c[pos]);
            if (type != currentType) {
                if (camelCase && type == 2 && currentType == 1) {
                    int newTokenStart = pos + INDEX_NOT_FOUND;
                    if (newTokenStart != tokenStart) {
                        list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                        tokenStart = newTokenStart;
                    }
                } else {
                    list.add(new String(c, tokenStart, pos - tokenStart));
                    tokenStart = pos;
                }
                currentType = type;
            }
        }
        list.add(new String(c, tokenStart, c.length - tokenStart));
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static <T> String join(T... elements) {
        return join((Object[]) elements, null);
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }
        int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return EMPTY;
        }
        StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(Iterator<?> iterator, char separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }
        StringBuilder buf = new StringBuilder(AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT);
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }
        StringBuilder buf = new StringBuilder(AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT);
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String join(Iterable<?> iterable, char separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    public static String join(Iterable<?> iterable, String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    public static String deleteWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int i = 0;
        int count = 0;
        while (i < sz) {
            int count2;
            if (Character.isWhitespace(str.charAt(i))) {
                count2 = count;
            } else {
                count2 = count + 1;
                chs[count] = str.charAt(i);
            }
            i++;
            count = count2;
        }
        return count != sz ? new String(chs, 0, count) : str;
    }

    public static String removeStart(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove) || !str.startsWith(remove)) {
            return str;
        }
        return str.substring(remove.length());
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove) || !startsWithIgnoreCase(str, remove)) {
            return str;
        }
        return str.substring(remove.length());
    }

    public static String removeEnd(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove) || !str.endsWith(remove)) {
            return str;
        }
        return str.substring(0, str.length() - remove.length());
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        if (isEmpty(str) || isEmpty(remove) || !endsWithIgnoreCase(str, remove)) {
            return str;
        }
        return str.substring(0, str.length() - remove.length());
    }

    public static String remove(String str, String remove) {
        return (isEmpty(str) || isEmpty(remove)) ? str : replace(str, remove, EMPTY, INDEX_NOT_FOUND);
    }

    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == INDEX_NOT_FOUND) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                int pos2 = pos + 1;
                chars[pos] = chars[i];
                pos = pos2;
            }
        }
        return new String(chars, 0, pos);
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, INDEX_NOT_FOUND);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        int i = 64;
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, 0);
        if (end == INDEX_NOT_FOUND) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        if (increase < 0) {
            increase = 0;
        }
        if (max < 0) {
            i = 16;
        } else if (max <= 64) {
            i = max;
        }
        StringBuilder buf = new StringBuilder(text.length() + (increase * i));
        while (end != INDEX_NOT_FOUND) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            max += INDEX_NOT_FOUND;
            if (max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        return replaceEach(text, searchList, replacementList, false, 0);
    }

    public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return replaceEach(text, searchList, replacementList, true, searchList == null ? 0 : searchList.length);
    }

    private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
        if (text == null || text.length() == 0 || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0) {
            return text;
        }
        if (timeToLive < 0) {
            throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
        }
        int searchLength = searchList.length;
        int replacementLength = replacementList.length;
        if (searchLength != replacementLength) {
            throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
        }
        int tempIndex;
        boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
        int textIndex = INDEX_NOT_FOUND;
        int replaceIndex = INDEX_NOT_FOUND;
        int i = 0;
        while (i < searchLength) {
            if (!(noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null)) {
                tempIndex = text.indexOf(searchList[i]);
                if (tempIndex == INDEX_NOT_FOUND) {
                    noMoreMatchesForReplIndex[i] = true;
                } else if (textIndex == INDEX_NOT_FOUND || tempIndex < textIndex) {
                    textIndex = tempIndex;
                    replaceIndex = i;
                }
            }
            i++;
        }
        if (textIndex == INDEX_NOT_FOUND) {
            return text;
        }
        int start = 0;
        int increase = 0;
        i = 0;
        while (true) {
            int length = searchList.length;
            if (i >= r0) {
                break;
            }
            if (!(searchList[i] == null || replacementList[i] == null)) {
                int greater = replacementList[i].length() - searchList[i].length();
                if (greater > 0) {
                    increase += greater * 3;
                }
            }
            i++;
        }
        StringBuilder buf = new StringBuilder(text.length() + Math.min(increase, text.length() / 5));
        while (textIndex != INDEX_NOT_FOUND) {
            for (i = start; i < textIndex; i++) {
                buf.append(text.charAt(i));
            }
            buf.append(replacementList[replaceIndex]);
            start = textIndex + searchList[replaceIndex].length();
            textIndex = INDEX_NOT_FOUND;
            replaceIndex = INDEX_NOT_FOUND;
            i = 0;
            while (i < searchLength) {
                if (!(noMoreMatchesForReplIndex[i] || searchList[i] == null || searchList[i].length() == 0 || replacementList[i] == null)) {
                    tempIndex = text.indexOf(searchList[i], start);
                    if (tempIndex == INDEX_NOT_FOUND) {
                        noMoreMatchesForReplIndex[i] = true;
                    } else if (textIndex == INDEX_NOT_FOUND || tempIndex < textIndex) {
                        textIndex = tempIndex;
                        replaceIndex = i;
                    }
                }
                i++;
            }
        }
        int textLength = text.length();
        for (i = start; i < textLength; i++) {
            buf.append(text.charAt(i));
        }
        String result = buf.toString();
        if (!repeat) {
            return result;
        }
        return replaceEach(result, searchList, replacementList, repeat, timeToLive + INDEX_NOT_FOUND);
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }
        return str.replace(searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (isEmpty(str) || isEmpty(searchChars)) {
            return str;
        }
        if (replaceChars == null) {
            replaceChars = EMPTY;
        }
        boolean modified = false;
        int replaceCharsLength = replaceChars.length();
        int strLength = str.length();
        StringBuilder buf = new StringBuilder(strLength);
        for (int i = 0; i < strLength; i++) {
            char ch = str.charAt(i);
            int index = searchChars.indexOf(ch);
            if (index >= 0) {
                modified = true;
                if (index < replaceCharsLength) {
                    buf.append(replaceChars.charAt(index));
                }
            } else {
                buf.append(ch);
            }
        }
        if (modified) {
            return buf.toString();
        }
        return str;
    }

    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }
        if (overlay == null) {
            overlay = EMPTY;
        }
        int len = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > len) {
            start = len;
        }
        if (end < 0) {
            end = 0;
        }
        if (end > len) {
            end = len;
        }
        if (start > end) {
            int temp = start;
            start = end;
            end = temp;
        }
        return new StringBuilder((((len + start) - end) + overlay.length()) + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
    }

    public static String chomp(String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            char ch = str.charAt(0);
            if (ch == CharUtils.CR || ch == '\n') {
                return EMPTY;
            }
            return str;
        }
        int lastIdx = str.length() + INDEX_NOT_FOUND;
        char last = str.charAt(lastIdx);
        if (last == '\n') {
            if (str.charAt(lastIdx + INDEX_NOT_FOUND) == CharUtils.CR) {
                lastIdx += INDEX_NOT_FOUND;
            }
        } else if (last != CharUtils.CR) {
            lastIdx++;
        }
        return str.substring(0, lastIdx);
    }

    @Deprecated
    public static String chomp(String str, String separator) {
        return removeEnd(str, separator);
    }

    public static String chop(String str) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        if (strLen < 2) {
            return EMPTY;
        }
        int lastIdx = strLen + INDEX_NOT_FOUND;
        String ret = str.substring(0, lastIdx);
        if (str.charAt(lastIdx) == '\n' && ret.charAt(lastIdx + INDEX_NOT_FOUND) == CharUtils.CR) {
            return ret.substring(0, lastIdx + INDEX_NOT_FOUND);
        }
        return ret;
    }

    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return repeat(str.charAt(0), repeat);
        }
        int outputLength = inputLength * repeat;
        int i;
        switch (inputLength) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return repeat(str.charAt(0), repeat);
            case Value.STRING_FIELD_NUMBER /*2*/:
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];
                for (i = (repeat * 2) - 2; i >= 0; i = (i + INDEX_NOT_FOUND) + INDEX_NOT_FOUND) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default:
                StringBuilder buf = new StringBuilder(outputLength);
                for (i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    public static String repeat(String str, String separator, int repeat) {
        if (str == null || separator == null) {
            return repeat(str, repeat);
        }
        return removeEnd(repeat(str + separator, repeat), separator);
    }

    public static String repeat(char ch, int repeat) {
        char[] buf = new char[repeat];
        for (int i = repeat + INDEX_NOT_FOUND; i >= 0; i += INDEX_NOT_FOUND) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(repeat(padChar, pads));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return str.concat(new String(padding));
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return new String(padding).concat(str);
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar) {
        if (str == null || size <= 0) {
            return str;
        }
        int strLen = str.length();
        int pads = size - strLen;
        if (pads > 0) {
            return rightPad(leftPad(str, (pads / 2) + strLen, padChar), size, padChar);
        }
        return str;
    }

    public static String center(String str, int size, String padStr) {
        if (str == null || size <= 0) {
            return str;
        }
        if (isEmpty(padStr)) {
            padStr = " ";
        }
        int strLen = str.length();
        int pads = size - strLen;
        if (pads > 0) {
            return rightPad(leftPad(str, (pads / 2) + strLen, padStr), size, padStr);
        }
        return str;
    }

    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    public static String upperCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(locale);
    }

    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String lowerCase(String str, Locale locale) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(locale);
    }

    public static String capitalize(String str) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        return strLen == 0 ? str : Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }

    public static String uncapitalize(String str) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        return strLen == 0 ? str : Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String swapCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] buffer = str.toCharArray();
        for (int i = 0; i < buffer.length; i++) {
            char ch = buffer[i];
            if (Character.isUpperCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                buffer[i] = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                buffer[i] = Character.toUpperCase(ch);
            }
        }
        return new String(buffer);
    }

    public static int countMatches(CharSequence str, CharSequence sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while (true) {
            idx = CharSequenceUtils.indexOf(str, sub, idx);
            if (idx == INDEX_NOT_FOUND) {
                return count;
            }
            count++;
            idx += sub.length();
        }
    }

    public static boolean isAlpha(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetter(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        int i = 0;
        while (i < sz) {
            if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isAlphanumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLetterOrDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphanumericSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        int i = 0;
        while (i < sz) {
            if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isAsciiPrintable(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumericSpace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        int i = 0;
        while (i < sz) {
            if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isWhitespace(CharSequence cs) {
        if (cs == null) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllLowerCase(CharSequence cs) {
        if (cs == null || isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isLowerCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllUpperCase(CharSequence cs) {
        if (cs == null || isEmpty(cs)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isUpperCase(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String defaultString(String str) {
        return str == null ? EMPTY : str;
    }

    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    public static String reverseDelimited(String str, char separatorChar) {
        if (str == null) {
            return null;
        }
        Object[] strs = split(str, separatorChar);
        ArrayUtils.reverse(strs);
        return join(strs, separatorChar);
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        }
        if (maxWidth < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if (str.length() <= maxWidth) {
            return str;
        } else {
            if (offset > str.length()) {
                offset = str.length();
            }
            if (str.length() - offset < maxWidth - 3) {
                offset = str.length() - (maxWidth - 3);
            }
            String abrevMarker = "...";
            if (offset <= 4) {
                return str.substring(0, maxWidth - 3) + "...";
            }
            if (maxWidth < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else if ((offset + maxWidth) - 3 < str.length()) {
                return "..." + abbreviate(str.substring(offset), maxWidth - 3);
            } else {
                return "..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public static String abbreviateMiddle(String str, String middle, int length) {
        if (isEmpty(str) || isEmpty(middle) || length >= str.length() || length < middle.length() + 2) {
            return str;
        }
        int targetSting = length - middle.length();
        int startOffset = (targetSting / 2) + (targetSting % 2);
        int endOffset = str.length() - (targetSting / 2);
        StringBuilder builder = new StringBuilder(length);
        builder.append(str.substring(0, startOffset));
        builder.append(middle);
        builder.append(str.substring(endOffset));
        return builder.toString();
    }

    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }
        if (str2 == null) {
            return str1;
        }
        int at = indexOfDifference(str1, str2);
        if (at == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return str2.substring(at);
    }

    public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return INDEX_NOT_FOUND;
        }
        if (cs1 == null || cs2 == null) {
            return 0;
        }
        int i = 0;
        while (i < cs1.length() && i < cs2.length() && cs1.charAt(i) == cs2.charAt(i)) {
            i++;
        }
        if (i < cs2.length() || i < cs1.length()) {
            return i;
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOfDifference(CharSequence... css) {
        if (css == null || css.length <= 1) {
            return INDEX_NOT_FOUND;
        }
        boolean anyStringNull = false;
        boolean allStringsNull = true;
        int arrayLen = css.length;
        int shortestStrLen = Integer.MAX_VALUE;
        int longestStrLen = 0;
        for (int i = 0; i < arrayLen; i++) {
            if (css[i] == null) {
                anyStringNull = true;
                shortestStrLen = 0;
            } else {
                allStringsNull = false;
                shortestStrLen = Math.min(css[i].length(), shortestStrLen);
                longestStrLen = Math.max(css[i].length(), longestStrLen);
            }
        }
        if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
            return INDEX_NOT_FOUND;
        }
        if (shortestStrLen == 0) {
            return 0;
        }
        int firstDiff = INDEX_NOT_FOUND;
        for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
            char comparisonChar = css[0].charAt(stringPos);
            for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
                if (css[arrayPos].charAt(stringPos) != comparisonChar) {
                    firstDiff = stringPos;
                    break;
                }
            }
            if (firstDiff != INDEX_NOT_FOUND) {
                break;
            }
        }
        if (firstDiff != INDEX_NOT_FOUND || shortestStrLen == longestStrLen) {
            return firstDiff;
        }
        return shortestStrLen;
    }

    public static String getCommonPrefix(String... strs) {
        if (strs == null || strs.length == 0) {
            return EMPTY;
        }
        int smallestIndexOfDiff = indexOfDifference(strs);
        if (smallestIndexOfDiff == INDEX_NOT_FOUND) {
            if (strs[0] == null) {
                return EMPTY;
            }
            return strs[0];
        } else if (smallestIndexOfDiff == 0) {
            return EMPTY;
        } else {
            return strs[0].substring(0, smallestIndexOfDiff);
        }
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        int n = s.length();
        int m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        int i;
        if (n > m) {
            CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }
        int[] p = new int[(n + 1)];
        int[] d = new int[(n + 1)];
        for (i = 0; i <= n; i++) {
            p[i] = i;
        }
        for (int j = 1; j <= m; j++) {
            char t_j = t.charAt(j + INDEX_NOT_FOUND);
            d[0] = j;
            for (i = 1; i <= n; i++) {
                d[i] = Math.min(Math.min(d[i + INDEX_NOT_FOUND] + 1, p[i] + 1), p[i + INDEX_NOT_FOUND] + (s.charAt(i + INDEX_NOT_FOUND) == t_j ? 0 : 1));
            }
            int[] _d = p;
            p = d;
            d = _d;
        }
        return p[n];
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        } else if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        } else {
            int n = s.length();
            int m = t.length();
            if (n == 0) {
                if (m <= threshold) {
                    return m;
                }
                return INDEX_NOT_FOUND;
            } else if (m == 0) {
                return n <= threshold ? n : INDEX_NOT_FOUND;
            } else {
                int i;
                if (n > m) {
                    CharSequence tmp = s;
                    s = t;
                    t = tmp;
                    n = m;
                    m = t.length();
                }
                int[] p = new int[(n + 1)];
                int[] d = new int[(n + 1)];
                int boundary = Math.min(n, threshold) + 1;
                for (i = 0; i < boundary; i++) {
                    p[i] = i;
                }
                Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
                Arrays.fill(d, Integer.MAX_VALUE);
                for (int j = 1; j <= m; j++) {
                    char t_j = t.charAt(j + INDEX_NOT_FOUND);
                    d[0] = j;
                    int min = Math.max(1, j - threshold);
                    int max = Math.min(n, j + threshold);
                    if (min > max) {
                        return INDEX_NOT_FOUND;
                    }
                    if (min > 1) {
                        d[min + INDEX_NOT_FOUND] = Integer.MAX_VALUE;
                    }
                    for (i = min; i <= max; i++) {
                        if (s.charAt(i + INDEX_NOT_FOUND) == t_j) {
                            d[i] = p[i + INDEX_NOT_FOUND];
                        } else {
                            d[i] = Math.min(Math.min(d[i + INDEX_NOT_FOUND], p[i]), p[i + INDEX_NOT_FOUND]) + 1;
                        }
                    }
                    int[] _d = p;
                    p = d;
                    d = _d;
                }
                if (p[n] <= threshold) {
                    return p[n];
                }
                return INDEX_NOT_FOUND;
            }
        }
    }

    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        return startsWith(str, prefix, false);
    }

    public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
        return startsWith(str, prefix, true);
    }

    private static boolean startsWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            if (str == null && prefix == null) {
                return true;
            }
            return false;
        } else if (prefix.length() > str.length()) {
            return false;
        } else {
            return CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
        }
    }

    public static boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
        if (isEmpty(string) || ArrayUtils.isEmpty((Object[]) searchStrings)) {
            return false;
        }
        for (CharSequence searchString : searchStrings) {
            if (startsWith(string, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return endsWith(str, suffix, true);
    }

    private static boolean endsWith(CharSequence str, CharSequence suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            if (str == null && suffix == null) {
                return true;
            }
            return false;
        } else if (suffix.length() > str.length()) {
            return false;
        } else {
            return CharSequenceUtils.regionMatches(str, ignoreCase, str.length() - suffix.length(), suffix, 0, suffix.length());
        }
    }

    public static String normalizeSpace(String str) {
        if (str == null) {
            return null;
        }
        return WHITESPACE_BLOCK.matcher(trim(str)).replaceAll(" ");
    }

    public static boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
        if (isEmpty(string) || ArrayUtils.isEmpty((Object[]) searchStrings)) {
            return false;
        }
        for (CharSequence searchString : searchStrings) {
            if (endsWith(string, searchString)) {
                return true;
            }
        }
        return false;
    }

    public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return charsetName == null ? new String(bytes) : new String(bytes, charsetName);
    }
}
