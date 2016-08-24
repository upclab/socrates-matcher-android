package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;

@GwtCompatible
@Beta
public final class PercentEscaper extends UnicodeEscaper {
    private static final char[] PLUS_SIGN;
    private static final char[] UPPER_HEX_DIGITS;
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    static {
        PLUS_SIGN = new char[]{'+'};
        UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    }

    public PercentEscaper(String safeChars, boolean plusForSpace) {
        Preconditions.checkNotNull(safeChars);
        if (safeChars.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        safeChars = safeChars + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (plusForSpace && safeChars.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = plusForSpace;
        this.safeOctets = createSafeOctets(safeChars);
    }

    private static boolean[] createSafeOctets(String safeChars) {
        int maxChar = -1;
        char[] safeCharArray = safeChars.toCharArray();
        for (char c : safeCharArray) {
            maxChar = Math.max(c, maxChar);
        }
        boolean[] octets = new boolean[(maxChar + 1)];
        for (char c2 : safeCharArray) {
            octets[c2] = true;
        }
        return octets;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected int nextEscapeIndex(java.lang.CharSequence r3, int r4, int r5) {
        /*
        r2 = this;
        com.google.common.base.Preconditions.checkNotNull(r3);
    L_0x0003:
        if (r4 >= r5) goto L_0x0014;
    L_0x0005:
        r0 = r3.charAt(r4);
        r1 = r2.safeOctets;
        r1 = r1.length;
        if (r0 >= r1) goto L_0x0014;
    L_0x000e:
        r1 = r2.safeOctets;
        r1 = r1[r0];
        if (r1 != 0) goto L_0x0015;
    L_0x0014:
        return r4;
    L_0x0015:
        r4 = r4 + 1;
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.net.PercentEscaper.nextEscapeIndex(java.lang.CharSequence, int, int):int");
    }

    public String escape(String s) {
        Preconditions.checkNotNull(s);
        int slen = s.length();
        for (int index = 0; index < slen; index++) {
            char c = s.charAt(index);
            if (c >= this.safeOctets.length || !this.safeOctets[c]) {
                return escapeSlow(s, index);
            }
        }
        return s;
    }

    protected char[] escape(int cp) {
        if (cp < this.safeOctets.length && this.safeOctets[cp]) {
            return null;
        }
        if (cp == 32 && this.plusForSpace) {
            return PLUS_SIGN;
        }
        if (cp <= 127) {
            return new char[]{'%', UPPER_HEX_DIGITS[cp & 15], UPPER_HEX_DIGITS[cp >>> 4]};
        } else if (cp <= 2047) {
            dest = new char[6];
            dest[0] = '%';
            dest[3] = '%';
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[2] = UPPER_HEX_DIGITS[cp & 15];
            dest[1] = UPPER_HEX_DIGITS[(cp >>> 4) | 12];
            return dest;
        } else if (cp <= 65535) {
            dest = new char[9];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            dest[2] = UPPER_HEX_DIGITS[cp >>> 2];
            return dest;
        } else if (cp <= 1114111) {
            dest = new char[12];
            cp >>>= 4;
            dest[10] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[8] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            dest[2] = UPPER_HEX_DIGITS[(cp >>> 2) & 7];
            return dest;
        } else {
            throw new IllegalArgumentException("Invalid unicode character value " + cp);
        }
    }
}
