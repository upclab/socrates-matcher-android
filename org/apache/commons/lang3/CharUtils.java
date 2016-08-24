package org.apache.commons.lang3;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.common.base.Ascii;

public class CharUtils {
    private static final String[] CHAR_STRING_ARRAY;
    public static final char CR = '\r';
    public static final char LF = '\n';

    static {
        CHAR_STRING_ARRAY = new String[AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER];
        for (char c = '\u0000'; c < CHAR_STRING_ARRAY.length; c = (char) (c + 1)) {
            CHAR_STRING_ARRAY[c] = String.valueOf(c);
        }
    }

    @Deprecated
    public static Character toCharacterObject(char ch) {
        return Character.valueOf(ch);
    }

    public static Character toCharacterObject(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return Character.valueOf(str.charAt(0));
    }

    public static char toChar(Character ch) {
        if (ch != null) {
            return ch.charValue();
        }
        throw new IllegalArgumentException("The Character must not be null");
    }

    public static char toChar(Character ch, char defaultValue) {
        return ch == null ? defaultValue : ch.charValue();
    }

    public static char toChar(String str) {
        if (!StringUtils.isEmpty(str)) {
            return str.charAt(0);
        }
        throw new IllegalArgumentException("The String must not be empty");
    }

    public static char toChar(String str, char defaultValue) {
        return StringUtils.isEmpty(str) ? defaultValue : str.charAt(0);
    }

    public static int toIntValue(char ch) {
        if (isAsciiNumeric(ch)) {
            return ch - 48;
        }
        throw new IllegalArgumentException("The character " + ch + " is not in the range '0' - '9'");
    }

    public static int toIntValue(char ch, int defaultValue) {
        return !isAsciiNumeric(ch) ? defaultValue : ch - 48;
    }

    public static int toIntValue(Character ch) {
        if (ch != null) {
            return toIntValue(ch.charValue());
        }
        throw new IllegalArgumentException("The character must not be null");
    }

    public static int toIntValue(Character ch, int defaultValue) {
        return ch == null ? defaultValue : toIntValue(ch.charValue(), defaultValue);
    }

    public static String toString(char ch) {
        if (ch < '\u0080') {
            return CHAR_STRING_ARRAY[ch];
        }
        return new String(new char[]{ch});
    }

    public static String toString(Character ch) {
        if (ch == null) {
            return null;
        }
        return toString(ch.charValue());
    }

    public static String unicodeEscaped(char ch) {
        if (ch < '\u0010') {
            return "\\u000" + Integer.toHexString(ch);
        }
        if (ch < '\u0100') {
            return "\\u00" + Integer.toHexString(ch);
        }
        if (ch < '\u1000') {
            return "\\u0" + Integer.toHexString(ch);
        }
        return "\\u" + Integer.toHexString(ch);
    }

    public static String unicodeEscaped(Character ch) {
        if (ch == null) {
            return null;
        }
        return unicodeEscaped(ch.charValue());
    }

    public static boolean isAscii(char ch) {
        return ch < '\u0080';
    }

    public static boolean isAsciiPrintable(char ch) {
        return ch >= ' ' && ch < Ascii.MAX;
    }

    public static boolean isAsciiControl(char ch) {
        return ch < ' ' || ch == Ascii.MAX;
    }

    public static boolean isAsciiAlpha(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    public static boolean isAsciiAlphaUpper(char ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    public static boolean isAsciiAlphaLower(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    public static boolean isAsciiNumeric(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isAsciiAlphanumeric(char ch) {
        return (ch >= 'A' && ch <= 'Z') || ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9'));
    }
}
