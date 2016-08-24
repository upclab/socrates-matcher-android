package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
    private final char[][] replacements;
    private final int replacementsLength;
    private final int safeMax;
    private final char safeMaxChar;
    private final int safeMin;
    private final char safeMinChar;

    protected abstract char[] escapeUnsafe(int i);

    protected ArrayBasedUnicodeEscaper(Map<Character, String> replacementMap, int safeMin, int safeMax, @Nullable String unsafeReplacement) {
        this(ArrayBasedEscaperMap.create(replacementMap), safeMin, safeMax, unsafeReplacement);
    }

    protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap escaperMap, int safeMin, int safeMax, @Nullable String unsafeReplacement) {
        Preconditions.checkNotNull(escaperMap);
        this.replacements = escaperMap.getReplacementArray();
        this.replacementsLength = this.replacements.length;
        if (safeMax < safeMin) {
            safeMax = -1;
            safeMin = Integer.MAX_VALUE;
        }
        this.safeMin = safeMin;
        this.safeMax = safeMax;
        if (safeMin >= 55296) {
            this.safeMinChar = '\uffff';
            this.safeMaxChar = '\u0000';
            return;
        }
        this.safeMinChar = (char) safeMin;
        this.safeMaxChar = (char) Math.min(safeMax, 55295);
    }

    public final String escape(String s) {
        Preconditions.checkNotNull(s);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c < this.replacementsLength && this.replacements[c] != null) || c > this.safeMaxChar || c < this.safeMinChar) {
                return escapeSlow(s, i);
            }
        }
        return s;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected final int nextEscapeIndex(java.lang.CharSequence r3, int r4, int r5) {
        /*
        r2 = this;
    L_0x0000:
        if (r4 >= r5) goto L_0x0018;
    L_0x0002:
        r0 = r3.charAt(r4);
        r1 = r2.replacementsLength;
        if (r0 >= r1) goto L_0x0010;
    L_0x000a:
        r1 = r2.replacements;
        r1 = r1[r0];
        if (r1 != 0) goto L_0x0018;
    L_0x0010:
        r1 = r2.safeMaxChar;
        if (r0 > r1) goto L_0x0018;
    L_0x0014:
        r1 = r2.safeMinChar;
        if (r0 >= r1) goto L_0x0019;
    L_0x0018:
        return r4;
    L_0x0019:
        r4 = r4 + 1;
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.escape.ArrayBasedUnicodeEscaper.nextEscapeIndex(java.lang.CharSequence, int, int):int");
    }

    protected final char[] escape(int cp) {
        if (cp < this.replacementsLength) {
            char[] chars = this.replacements[cp];
            if (chars != null) {
                return chars;
            }
        }
        if (cp < this.safeMin || cp > this.safeMax) {
            return escapeUnsafe(cp);
        }
        return null;
    }
}
