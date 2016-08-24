package com.google.common.escape;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
public final class Escapers {
    private static final Escaper NULL_ESCAPER;

    @Beta
    public static final class Builder {
        private final Map<Character, String> replacementMap;
        private char safeMax;
        private char safeMin;
        private String unsafeReplacement;

        /* renamed from: com.google.common.escape.Escapers.Builder.1 */
        class C10931 extends ArrayBasedCharEscaper {
            private final char[] replacementChars;

            C10931(Map x0, char x1, char x2) {
                super(x0, x1, x2);
                this.replacementChars = Builder.this.unsafeReplacement != null ? Builder.this.unsafeReplacement.toCharArray() : null;
            }

            protected char[] escapeUnsafe(char c) {
                return this.replacementChars;
            }
        }

        private Builder() {
            this.replacementMap = new HashMap();
            this.safeMin = '\u0000';
            this.safeMax = '\uffff';
            this.unsafeReplacement = null;
        }

        public Builder setSafeRange(char safeMin, char safeMax) {
            this.safeMin = safeMin;
            this.safeMax = safeMax;
            return this;
        }

        public Builder setUnsafeReplacement(@Nullable String unsafeReplacement) {
            this.unsafeReplacement = unsafeReplacement;
            return this;
        }

        public Builder addEscape(char c, String replacement) {
            Preconditions.checkNotNull(replacement);
            this.replacementMap.put(Character.valueOf(c), replacement);
            return this;
        }

        public Escaper build() {
            return new C10931(this.replacementMap, this.safeMin, this.safeMax);
        }
    }

    /* renamed from: com.google.common.escape.Escapers.1 */
    static class C10231 extends CharEscaper {
        C10231() {
        }

        public String escape(String string) {
            return (String) Preconditions.checkNotNull(string);
        }

        protected char[] escape(char c) {
            return null;
        }
    }

    /* renamed from: com.google.common.escape.Escapers.2 */
    static class C10242 extends UnicodeEscaper {
        final /* synthetic */ CharEscaper val$escaper;

        C10242(CharEscaper charEscaper) {
            this.val$escaper = charEscaper;
        }

        protected char[] escape(int cp) {
            if (cp < 65536) {
                return this.val$escaper.escape((char) cp);
            }
            char[] surrogateChars = new char[2];
            Character.toChars(cp, surrogateChars, 0);
            char[] hiChars = this.val$escaper.escape(surrogateChars[0]);
            char[] loChars = this.val$escaper.escape(surrogateChars[1]);
            if (hiChars == null && loChars == null) {
                return null;
            }
            int hiCount;
            int loCount;
            int n;
            if (hiChars != null) {
                hiCount = hiChars.length;
            } else {
                hiCount = 1;
            }
            if (loChars != null) {
                loCount = loChars.length;
            } else {
                loCount = 1;
            }
            char[] output = new char[(hiCount + loCount)];
            if (hiChars != null) {
                for (n = 0; n < hiChars.length; n++) {
                    output[n] = hiChars[n];
                }
            } else {
                output[0] = surrogateChars[0];
            }
            if (loChars != null) {
                for (n = 0; n < loChars.length; n++) {
                    output[hiCount + n] = loChars[n];
                }
                return output;
            }
            output[hiCount] = surrogateChars[1];
            return output;
        }
    }

    private Escapers() {
    }

    public static Escaper nullEscaper() {
        return NULL_ESCAPER;
    }

    static {
        NULL_ESCAPER = new C10231();
    }

    public static Builder builder() {
        return new Builder();
    }

    static UnicodeEscaper asUnicodeEscaper(Escaper escaper) {
        Preconditions.checkNotNull(escaper);
        if (escaper instanceof UnicodeEscaper) {
            return (UnicodeEscaper) escaper;
        }
        if (escaper instanceof CharEscaper) {
            return wrap((CharEscaper) escaper);
        }
        throw new IllegalArgumentException("Cannot create a UnicodeEscaper from: " + escaper.getClass().getName());
    }

    public static String computeReplacement(CharEscaper escaper, char c) {
        return stringOrNull(escaper.escape(c));
    }

    public static String computeReplacement(UnicodeEscaper escaper, int cp) {
        return stringOrNull(escaper.escape(cp));
    }

    private static String stringOrNull(char[] in) {
        return in == null ? null : new String(in);
    }

    private static UnicodeEscaper wrap(CharEscaper escaper) {
        return new C10242(escaper);
    }
}
