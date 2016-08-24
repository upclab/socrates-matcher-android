package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;

@GwtCompatible
@Beta
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    protected abstract char[] escape(int i);

    protected UnicodeEscaper() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected int nextEscapeIndex(java.lang.CharSequence r4, int r5, int r6) {
        /*
        r3 = this;
        r1 = r5;
    L_0x0001:
        if (r1 >= r6) goto L_0x000f;
    L_0x0003:
        r0 = codePointAt(r4, r1, r6);
        if (r0 < 0) goto L_0x000f;
    L_0x0009:
        r2 = r3.escape(r0);
        if (r2 == 0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r2 = java.lang.Character.isSupplementaryCodePoint(r0);
        if (r2 == 0) goto L_0x0019;
    L_0x0016:
        r2 = 2;
    L_0x0017:
        r1 = r1 + r2;
        goto L_0x0001;
    L_0x0019:
        r2 = 1;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.escape.UnicodeEscaper.nextEscapeIndex(java.lang.CharSequence, int, int):int");
    }

    public String escape(String string) {
        Preconditions.checkNotNull(string);
        int end = string.length();
        int index = nextEscapeIndex(string, 0, end);
        return index == end ? string : escapeSlow(string, index);
    }

    protected final String escapeSlow(String s, int index) {
        int charsSkipped;
        int end = s.length();
        char[] dest = Platform.charBufferFromThreadLocal();
        int destIndex = 0;
        int unescapedChunkStart = 0;
        while (index < end) {
            int cp = codePointAt(s, index, end);
            if (cp < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] escaped = escape(cp);
            int nextIndex = index + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
            if (escaped != null) {
                charsSkipped = index - unescapedChunkStart;
                int sizeNeeded = (destIndex + charsSkipped) + escaped.length;
                if (dest.length < sizeNeeded) {
                    dest = growBuffer(dest, destIndex, ((end - index) + sizeNeeded) + DEST_PAD);
                }
                if (charsSkipped > 0) {
                    s.getChars(unescapedChunkStart, index, dest, destIndex);
                    destIndex += charsSkipped;
                }
                if (escaped.length > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
                    destIndex += escaped.length;
                }
                unescapedChunkStart = nextIndex;
            }
            index = nextEscapeIndex(s, nextIndex, end);
        }
        charsSkipped = end - unescapedChunkStart;
        if (charsSkipped > 0) {
            int endIndex = destIndex + charsSkipped;
            if (dest.length < endIndex) {
                dest = growBuffer(dest, destIndex, endIndex);
            }
            s.getChars(unescapedChunkStart, end, dest, destIndex);
            destIndex = endIndex;
        }
        return new String(dest, 0, destIndex);
    }

    protected static int codePointAt(CharSequence seq, int index, int end) {
        Preconditions.checkNotNull(seq);
        if (index < end) {
            int index2 = index + 1;
            char c1 = seq.charAt(index);
            if (c1 < '\ud800' || c1 > '\udfff') {
                return c1;
            }
            if (c1 > '\udbff') {
                throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index2 - 1) + " in '" + seq + "'");
            } else if (index2 == end) {
                return -c1;
            } else {
                char c2 = seq.charAt(index2);
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c1, c2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index2 + " in '" + seq + "'");
            }
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static char[] growBuffer(char[] dest, int index, int size) {
        char[] copy = new char[size];
        if (index > 0) {
            System.arraycopy(dest, 0, copy, 0, index);
        }
        return copy;
    }
}
