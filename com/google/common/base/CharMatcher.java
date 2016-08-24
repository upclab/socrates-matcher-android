package com.google.common.base;

import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.Arrays;
import java.util.BitSet;
import javax.annotation.CheckReturnValue;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.MutableDateTime;
import pe.edu.upc.mobile.Utilities.Base64;

@GwtCompatible(emulated = true)
@Beta
public abstract class CharMatcher implements Predicate<Character> {
    public static final CharMatcher ANY;
    public static final CharMatcher ASCII;
    public static final CharMatcher BREAKING_WHITESPACE;
    public static final CharMatcher DIGIT;
    private static final int DISTINCT_CHARS = 65536;
    public static final CharMatcher INVISIBLE;
    public static final CharMatcher JAVA_DIGIT;
    public static final CharMatcher JAVA_ISO_CONTROL;
    public static final CharMatcher JAVA_LETTER;
    public static final CharMatcher JAVA_LETTER_OR_DIGIT;
    public static final CharMatcher JAVA_LOWER_CASE;
    public static final CharMatcher JAVA_UPPER_CASE;
    private static final String NINES;
    public static final CharMatcher NONE;
    public static final CharMatcher SINGLE_WIDTH;
    public static final CharMatcher WHITESPACE;
    private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";
    final String description;

    /* renamed from: com.google.common.base.CharMatcher.11 */
    static class AnonymousClass11 extends CharMatcher {
        final /* synthetic */ char[] val$chars;

        AnonymousClass11(String x0, char[] cArr) {
            this.val$chars = cArr;
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Arrays.binarySearch(this.val$chars, c) >= 0;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            for (char c : this.val$chars) {
                table.set(c);
            }
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.14 */
    static class AnonymousClass14 extends CharMatcher {
        final /* synthetic */ Predicate val$predicate;

        AnonymousClass14(String x0, Predicate predicate) {
            this.val$predicate = predicate;
            super(x0);
        }

        public boolean matches(char c) {
            return this.val$predicate.apply(Character.valueOf(c));
        }

        public boolean apply(Character character) {
            return this.val$predicate.apply(Preconditions.checkNotNull(character));
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.1 */
    static class C09281 extends CharMatcher {
        C09281() {
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            switch (c) {
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                case Base64.ORDERED /*32*/:
                case '\u0085':
                case '\u1680':
                case '\u2028':
                case '\u2029':
                case '\u205f':
                case '\u3000':
                    return true;
                case '\u2007':
                    return false;
                default:
                    if (c < '\u2000' || c > '\u200a') {
                        return false;
                    }
                    return true;
            }
        }

        public String toString() {
            return "CharMatcher.BREAKING_WHITESPACE";
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.2 */
    static class C09292 extends CharMatcher {
        C09292(String x0) {
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.3 */
    static class C09303 extends CharMatcher {
        C09303(String x0) {
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.4 */
    static class C09314 extends CharMatcher {
        C09314(String x0) {
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLetterOrDigit(c);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.5 */
    static class C09325 extends CharMatcher {
        C09325(String x0) {
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isUpperCase(c);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.6 */
    static class C09336 extends CharMatcher {
        C09336(String x0) {
            super(x0);
        }

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        public boolean matches(char c) {
            return Character.isLowerCase(c);
        }
    }

    private static class And extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        And(CharMatcher a, CharMatcher b) {
            this(a, b, "CharMatcher.and(" + a + ", " + b + ")");
        }

        And(CharMatcher a, CharMatcher b, String description) {
            super(description);
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        public boolean matches(char c) {
            return this.first.matches(c) && this.second.matches(c);
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            BitSet tmp1 = new BitSet();
            this.first.setBits(tmp1);
            BitSet tmp2 = new BitSet();
            this.second.setBits(tmp2);
            tmp1.and(tmp2);
            table.or(tmp1);
        }

        CharMatcher withToString(String description) {
            return new And(this.first, this.second, description);
        }
    }

    static abstract class FastMatcher extends CharMatcher {
        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        FastMatcher() {
        }

        FastMatcher(String description) {
            super(description);
        }

        public final CharMatcher precomputed() {
            return this;
        }

        public CharMatcher negate() {
            return new NegatedFastMatcher(this);
        }
    }

    private static class NegatedMatcher extends CharMatcher {
        final CharMatcher original;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        NegatedMatcher(String toString, CharMatcher original) {
            super(toString);
            this.original = original;
        }

        NegatedMatcher(CharMatcher original) {
            this(original + ".negate()", original);
        }

        public boolean matches(char c) {
            return !this.original.matches(c);
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return this.original.matchesNoneOf(sequence);
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return this.original.matchesAllOf(sequence);
        }

        public int countIn(CharSequence sequence) {
            return sequence.length() - this.original.countIn(sequence);
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            BitSet tmp = new BitSet();
            this.original.setBits(tmp);
            tmp.flip(0, CharMatcher.DISTINCT_CHARS);
            table.or(tmp);
        }

        public CharMatcher negate() {
            return this.original;
        }

        CharMatcher withToString(String description) {
            return new NegatedMatcher(description, this.original);
        }
    }

    private static class Or extends CharMatcher {
        final CharMatcher first;
        final CharMatcher second;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        Or(CharMatcher a, CharMatcher b, String description) {
            super(description);
            this.first = (CharMatcher) Preconditions.checkNotNull(a);
            this.second = (CharMatcher) Preconditions.checkNotNull(b);
        }

        Or(CharMatcher a, CharMatcher b) {
            this(a, b, "CharMatcher.or(" + a + ", " + b + ")");
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            this.first.setBits(table);
            this.second.setBits(table);
        }

        public boolean matches(char c) {
            return this.first.matches(c) || this.second.matches(c);
        }

        CharMatcher withToString(String description) {
            return new Or(this.first, this.second, description);
        }
    }

    private static class RangesMatcher extends CharMatcher {
        private final char[] rangeEnds;
        private final char[] rangeStarts;

        public /* bridge */ /* synthetic */ boolean apply(Object x0) {
            return super.apply((Character) x0);
        }

        RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
            boolean z;
            super(description);
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            if (rangeStarts.length == rangeEnds.length) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z);
            for (int i = 0; i < rangeStarts.length; i++) {
                if (rangeStarts[i] <= rangeEnds[i]) {
                    z = true;
                } else {
                    z = false;
                }
                Preconditions.checkArgument(z);
                if (i + 1 < rangeStarts.length) {
                    if (rangeEnds[i] < rangeStarts[i + 1]) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Preconditions.checkArgument(z);
                }
            }
        }

        public boolean matches(char c) {
            int index = Arrays.binarySearch(this.rangeStarts, c);
            if (index >= 0) {
                return true;
            }
            index = (index ^ -1) - 1;
            if (index < 0 || c > this.rangeEnds[index]) {
                return false;
            }
            return true;
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.10 */
    static class AnonymousClass10 extends FastMatcher {
        final /* synthetic */ char val$match;

        AnonymousClass10(String x0, char c) {
            this.val$match = c;
            super(x0);
        }

        public boolean matches(char c) {
            return c != this.val$match;
        }

        public CharMatcher and(CharMatcher other) {
            return other.matches(this.val$match) ? super.and(other) : other;
        }

        public CharMatcher or(CharMatcher other) {
            return other.matches(this.val$match) ? ANY : this;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            table.set(0, this.val$match);
            table.set(this.val$match + 1, CharMatcher.DISTINCT_CHARS);
        }

        public CharMatcher negate() {
            return CharMatcher.is(this.val$match);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.12 */
    static class AnonymousClass12 extends FastMatcher {
        final /* synthetic */ char val$match1;
        final /* synthetic */ char val$match2;

        AnonymousClass12(String x0, char c, char c2) {
            this.val$match1 = c;
            this.val$match2 = c2;
            super(x0);
        }

        public boolean matches(char c) {
            return c == this.val$match1 || c == this.val$match2;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            table.set(this.val$match1);
            table.set(this.val$match2);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.13 */
    static class AnonymousClass13 extends FastMatcher {
        final /* synthetic */ char val$endInclusive;
        final /* synthetic */ char val$startInclusive;

        AnonymousClass13(String x0, char c, char c2) {
            this.val$startInclusive = c;
            this.val$endInclusive = c2;
            super(x0);
        }

        public boolean matches(char c) {
            return this.val$startInclusive <= c && c <= this.val$endInclusive;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            table.set(this.val$startInclusive, this.val$endInclusive + 1);
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.15 */
    static class AnonymousClass15 extends FastMatcher {
        private static final String TABLE = "\t\u3000\n\t\t\t\u202f\t\t\u2001\u2006\t\t\t\t\t\u180e\t\u2029\t\t\t\u2000\u2005\u200a\t\t\t\r\t\t\u2028\u1680\t\u00a0\t\u2004\u2009\t\t\t\f\u205f\t\t \t\t\u2003\u2008\t\t\t\u000b\u0085\t\t\t\t\t\t\u2002\u2007\t";

        AnonymousClass15(String x0) {
            super(x0);
        }

        public boolean matches(char c) {
            return TABLE.charAt((-844444961 * c) >>> 26) == c;
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            for (int i = 0; i < TABLE.length(); i++) {
                table.set(TABLE.charAt(i));
            }
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.7 */
    static class C10737 extends FastMatcher {
        C10737(String x0) {
            super(x0);
        }

        public boolean matches(char c) {
            return true;
        }

        public int indexIn(CharSequence sequence) {
            return sequence.length() == 0 ? -1 : 0;
        }

        public int indexIn(CharSequence sequence, int start) {
            int length = sequence.length();
            Preconditions.checkPositionIndex(start, length);
            return start == length ? -1 : start;
        }

        public int lastIndexIn(CharSequence sequence) {
            return sequence.length() - 1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public String removeFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return StringUtils.EMPTY;
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            char[] array = new char[sequence.length()];
            Arrays.fill(array, replacement);
            return new String(array);
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
            for (int i = 0; i < sequence.length(); i++) {
                retval.append(replacement);
            }
            return retval.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.length() == 0 ? StringUtils.EMPTY : String.valueOf(replacement);
        }

        public String trimFrom(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return StringUtils.EMPTY;
        }

        public int countIn(CharSequence sequence) {
            return sequence.length();
        }

        public CharMatcher and(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        public CharMatcher or(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        public CharMatcher negate() {
            return NONE;
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.8 */
    static class C10748 extends FastMatcher {
        C10748(String x0) {
            super(x0);
        }

        public boolean matches(char c) {
            return false;
        }

        public int indexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public int indexIn(CharSequence sequence, int start) {
            Preconditions.checkPositionIndex(start, sequence.length());
            return -1;
        }

        public int lastIndexIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return -1;
        }

        public boolean matchesAllOf(CharSequence sequence) {
            return sequence.length() == 0;
        }

        public boolean matchesNoneOf(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return true;
        }

        public String removeFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            Preconditions.checkNotNull(replacement);
            return sequence.toString();
        }

        public String collapseFrom(CharSequence sequence, char replacement) {
            return sequence.toString();
        }

        public String trimFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimLeadingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public String trimTrailingFrom(CharSequence sequence) {
            return sequence.toString();
        }

        public int countIn(CharSequence sequence) {
            Preconditions.checkNotNull(sequence);
            return 0;
        }

        public CharMatcher and(CharMatcher other) {
            Preconditions.checkNotNull(other);
            return this;
        }

        public CharMatcher or(CharMatcher other) {
            return (CharMatcher) Preconditions.checkNotNull(other);
        }

        public CharMatcher negate() {
            return ANY;
        }
    }

    /* renamed from: com.google.common.base.CharMatcher.9 */
    static class C10759 extends FastMatcher {
        final /* synthetic */ char val$match;

        C10759(String x0, char c) {
            this.val$match = c;
            super(x0);
        }

        public boolean matches(char c) {
            return c == this.val$match;
        }

        public String replaceFrom(CharSequence sequence, char replacement) {
            return sequence.toString().replace(this.val$match, replacement);
        }

        public CharMatcher and(CharMatcher other) {
            return other.matches(this.val$match) ? this : NONE;
        }

        public CharMatcher or(CharMatcher other) {
            return other.matches(this.val$match) ? other : super.or(other);
        }

        public CharMatcher negate() {
            return CharMatcher.isNot(this.val$match);
        }

        @GwtIncompatible("java.util.BitSet")
        void setBits(BitSet table) {
            table.set(this.val$match);
        }
    }

    @GwtIncompatible("java.util.BitSet")
    private static class BitSetMatcher extends FastMatcher {
        private final BitSet table;

        private BitSetMatcher(BitSet table, String description) {
            super(description);
            if (table.length() + 64 < table.size()) {
                table = (BitSet) table.clone();
            }
            this.table = table;
        }

        public boolean matches(char c) {
            return this.table.get(c);
        }

        void setBits(BitSet bitSet) {
            bitSet.or(this.table);
        }
    }

    static final class NegatedFastMatcher extends NegatedMatcher {
        NegatedFastMatcher(CharMatcher original) {
            super(original);
        }

        NegatedFastMatcher(String toString, CharMatcher original) {
            super(toString, original);
        }

        public final CharMatcher precomputed() {
            return this;
        }

        CharMatcher withToString(String description) {
            return new NegatedFastMatcher(description, this.original);
        }
    }

    public abstract boolean matches(char c);

    static {
        BREAKING_WHITESPACE = new C09281();
        ASCII = inRange('\u0000', Ascii.MAX, "CharMatcher.ASCII");
        StringBuilder builder = new StringBuilder(ZEROES.length());
        for (int i = 0; i < ZEROES.length(); i++) {
            builder.append((char) (ZEROES.charAt(i) + 9));
        }
        NINES = builder.toString();
        DIGIT = new RangesMatcher("CharMatcher.DIGIT", ZEROES.toCharArray(), NINES.toCharArray());
        JAVA_DIGIT = new C09292("CharMatcher.JAVA_DIGIT");
        JAVA_LETTER = new C09303("CharMatcher.JAVA_LETTER");
        JAVA_LETTER_OR_DIGIT = new C09314("CharMatcher.JAVA_LETTER_OR_DIGIT");
        JAVA_UPPER_CASE = new C09325("CharMatcher.JAVA_UPPER_CASE");
        JAVA_LOWER_CASE = new C09336("CharMatcher.JAVA_LOWER_CASE");
        JAVA_ISO_CONTROL = inRange('\u0000', '\u001f').or(inRange(Ascii.MAX, '\u009f')).withToString("CharMatcher.JAVA_ISO_CONTROL");
        INVISIBLE = new RangesMatcher("CharMatcher.INVISIBLE", "\u0000\u007f\u00ad\u0600\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u206a\u3000\ud800\ufeff\ufff9\ufffa".toCharArray(), " \u00a0\u00ad\u0604\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u206f\u3000\uf8ff\ufeff\ufff9\ufffb".toCharArray());
        SINGLE_WIDTH = new RangesMatcher("CharMatcher.SINGLE_WIDTH", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());
        ANY = new C10737("CharMatcher.ANY");
        NONE = new C10748("CharMatcher.NONE");
        WHITESPACE = new AnonymousClass15("WHITESPACE");
    }

    private static String showCharacter(char c) {
        String hex = "0123456789ABCDEF";
        char[] tmp = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    public static CharMatcher is(char match) {
        return new C10759("CharMatcher.is('" + showCharacter(match) + "')", match);
    }

    public static CharMatcher isNot(char match) {
        return new AnonymousClass10("CharMatcher.isNot('" + showCharacter(match) + "')", match);
    }

    public static CharMatcher anyOf(CharSequence sequence) {
        switch (sequence.length()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return NONE;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return is(sequence.charAt(0));
            case Value.STRING_FIELD_NUMBER /*2*/:
                return isEither(sequence.charAt(0), sequence.charAt(1));
            default:
                char[] chars = sequence.toString().toCharArray();
                Arrays.sort(chars);
                StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
                for (char c : chars) {
                    description.append(showCharacter(c));
                }
                description.append("\")");
                return new AnonymousClass11(description.toString(), chars);
        }
    }

    private static CharMatcher isEither(char match1, char match2) {
        return new AnonymousClass12("CharMatcher.anyOf(\"" + showCharacter(match1) + showCharacter(match2) + "\")", match1, match2);
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return anyOf(sequence).negate();
    }

    public static CharMatcher inRange(char startInclusive, char endInclusive) {
        Preconditions.checkArgument(endInclusive >= startInclusive);
        return inRange(startInclusive, endInclusive, "CharMatcher.inRange('" + showCharacter(startInclusive) + "', '" + showCharacter(endInclusive) + "')");
    }

    static CharMatcher inRange(char startInclusive, char endInclusive, String description) {
        return new AnonymousClass13(description, startInclusive, endInclusive);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        Preconditions.checkNotNull(predicate);
        if (predicate instanceof CharMatcher) {
            return (CharMatcher) predicate;
        }
        return new AnonymousClass14("CharMatcher.forPredicate(" + predicate + ")", predicate);
    }

    CharMatcher(String description) {
        this.description = description;
    }

    protected CharMatcher() {
        this.description = super.toString();
    }

    public CharMatcher negate() {
        return new NegatedMatcher(this);
    }

    public CharMatcher and(CharMatcher other) {
        return new And(this, (CharMatcher) Preconditions.checkNotNull(other));
    }

    public CharMatcher or(CharMatcher other) {
        return new Or(this, (CharMatcher) Preconditions.checkNotNull(other));
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    CharMatcher withToString(String description) {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible("java.util.BitSet")
    CharMatcher precomputedInternal() {
        BitSet table = new BitSet();
        setBits(table);
        int totalCharacters = table.cardinality();
        if (totalCharacters * 2 <= DISTINCT_CHARS) {
            return precomputedPositive(totalCharacters, table, this.description);
        }
        table.flip(0, DISTINCT_CHARS);
        String suffix = ".negate()";
        return new NegatedFastMatcher(toString(), precomputedPositive(DISTINCT_CHARS - totalCharacters, table, this.description.endsWith(suffix) ? this.description.substring(0, this.description.length() - suffix.length()) : this.description + suffix));
    }

    @GwtIncompatible("java.util.BitSet")
    private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
        switch (totalCharacters) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return NONE;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return is((char) table.nextSetBit(0));
            case Value.STRING_FIELD_NUMBER /*2*/:
                char c1 = (char) table.nextSetBit(0);
                return isEither(c1, (char) table.nextSetBit(c1 + 1));
            default:
                if (isSmall(totalCharacters, table.length())) {
                    return SmallCharMatcher.from(table, description);
                }
                return new BitSetMatcher(description, null);
        }
    }

    @GwtIncompatible("SmallCharMatcher")
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= 1023 && tableLength > (totalCharacters * 4) * 16;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        for (int c = 65535; c >= 0; c--) {
            if (matches((char) c)) {
                table.set(c);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence sequence) {
        return !matchesNoneOf(sequence);
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return indexIn(sequence) == -1;
    }

    public int indexIn(CharSequence sequence) {
        int length = sequence.length();
        for (int i = 0; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    @CheckReturnValue
    public String removeFrom(CharSequence sequence) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        int spread = 1;
        while (true) {
            pos++;
            while (pos != chars.length) {
                if (matches(chars[pos])) {
                    spread++;
                } else {
                    chars[pos - spread] = chars[pos];
                    pos++;
                }
            }
            return new String(chars, 0, pos - spread);
        }
    }

    @CheckReturnValue
    public String retainFrom(CharSequence sequence) {
        return negate().removeFrom(sequence);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(0));
        }
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        int len = string.length();
        StringBuilder buf = new StringBuilder(((len * 3) / 2) + 16);
        int oldpos = 0;
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);
        buf.append(string, oldpos, len);
        return buf.toString();
    }

    @CheckReturnValue
    public String trimFrom(CharSequence sequence) {
        int len = sequence.length();
        int first = 0;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        int last = len - 1;
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        return sequence.subSequence(first, last + 1).toString();
    }

    @CheckReturnValue
    public String trimLeadingFrom(CharSequence sequence) {
        int len = sequence.length();
        for (int first = 0; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                return sequence.subSequence(first, len).toString();
            }
        }
        return StringUtils.EMPTY;
    }

    @CheckReturnValue
    public String trimTrailingFrom(CharSequence sequence) {
        for (int last = sequence.length() - 1; last >= 0; last--) {
            if (!matches(sequence.charAt(last))) {
                return sequence.subSequence(0, last + 1).toString();
            }
        }
        return StringUtils.EMPTY;
    }

    @CheckReturnValue
    public String collapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int i = 0;
        while (i < len) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c != replacement || (i != len - 1 && matches(sequence.charAt(i + 1)))) {
                    return finishCollapseFrom(sequence, i + 1, len, replacement, new StringBuilder(len).append(sequence.subSequence(0, i)).append(replacement), true);
                }
                i++;
            }
            i++;
        }
        return sequence.toString();
    }

    @CheckReturnValue
    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int first = 0;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        int last = len - 1;
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        if (first == 0 && last == len - 1) {
            return collapseFrom(sequence, replacement);
        }
        return finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder((last + 1) - first), false);
    }

    private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (!matches(c)) {
                builder.append(c);
                inMatchingGroup = false;
            } else if (!inMatchingGroup) {
                builder.append(replacement);
                inMatchingGroup = true;
            }
        }
        return builder.toString();
    }

    @Deprecated
    public boolean apply(Character character) {
        return matches(character.charValue());
    }

    public String toString() {
        return this.description;
    }
}
