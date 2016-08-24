package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.CheckReturnValue;
import org.apache.commons.lang3.StringUtils;

@GwtCompatible(emulated = true)
public final class Splitter {
    private final int limit;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final CharMatcher trimmer;

    /* renamed from: com.google.common.base.Splitter.5 */
    class C02165 implements Iterable<String> {
        final /* synthetic */ CharSequence val$sequence;

        C02165(CharSequence charSequence) {
            this.val$sequence = charSequence;
        }

        public Iterator<String> iterator() {
            return Splitter.this.splittingIterator(this.val$sequence);
        }

        public String toString() {
            return Joiner.on(", ").appendTo(new StringBuilder().append('['), (Iterable) this).append(']').toString();
        }
    }

    @Beta
    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter entrySplitter;
        private final Splitter outerSplitter;

        private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
            this.outerSplitter = outerSplitter;
            this.entrySplitter = (Splitter) Preconditions.checkNotNull(entrySplitter);
        }

        public Map<String, String> split(CharSequence sequence) {
            Map<String, String> map = new LinkedHashMap();
            for (String entry : this.outerSplitter.split(sequence)) {
                boolean z;
                Iterator<String> entryFields = this.entrySplitter.splittingIterator(entry);
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                String key = (String) entryFields.next();
                if (map.containsKey(key)) {
                    z = false;
                } else {
                    z = true;
                }
                Preconditions.checkArgument(z, "Duplicate key [%s] found.", key);
                Preconditions.checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                map.put(key, (String) entryFields.next());
                if (entryFields.hasNext()) {
                    z = false;
                } else {
                    z = true;
                }
                Preconditions.checkArgument(z, INVALID_ENTRY_MESSAGE, entry);
            }
            return Collections.unmodifiableMap(map);
        }
    }

    private interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence charSequence);
    }

    /* renamed from: com.google.common.base.Splitter.1 */
    static class C06231 implements Strategy {
        final /* synthetic */ CharMatcher val$separatorMatcher;

        /* renamed from: com.google.common.base.Splitter.1.1 */
        class C09381 extends SplittingIterator {
            C09381(Splitter x0, CharSequence x1) {
                super(x0, x1);
            }

            int separatorStart(int start) {
                return C06231.this.val$separatorMatcher.indexIn(this.toSplit, start);
            }

            int separatorEnd(int separatorPosition) {
                return separatorPosition + 1;
            }
        }

        C06231(CharMatcher charMatcher) {
            this.val$separatorMatcher = charMatcher;
        }

        public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
            return new C09381(splitter, toSplit);
        }
    }

    /* renamed from: com.google.common.base.Splitter.2 */
    static class C06242 implements Strategy {
        final /* synthetic */ String val$separator;

        /* renamed from: com.google.common.base.Splitter.2.1 */
        class C09391 extends SplittingIterator {
            C09391(Splitter x0, CharSequence x1) {
                super(x0, x1);
            }

            public int separatorStart(int start) {
                int separatorLength = C06242.this.val$separator.length();
                int p = start;
                int last = this.toSplit.length() - separatorLength;
                while (p <= last) {
                    int i = 0;
                    while (i < separatorLength) {
                        if (this.toSplit.charAt(i + p) != C06242.this.val$separator.charAt(i)) {
                            p++;
                        } else {
                            i++;
                        }
                    }
                    return p;
                }
                return -1;
            }

            public int separatorEnd(int separatorPosition) {
                return C06242.this.val$separator.length() + separatorPosition;
            }
        }

        C06242(String str) {
            this.val$separator = str;
        }

        public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
            return new C09391(splitter, toSplit);
        }
    }

    /* renamed from: com.google.common.base.Splitter.3 */
    static class C06253 implements Strategy {
        final /* synthetic */ Pattern val$separatorPattern;

        /* renamed from: com.google.common.base.Splitter.3.1 */
        class C09401 extends SplittingIterator {
            final /* synthetic */ Matcher val$matcher;

            C09401(Splitter x0, CharSequence x1, Matcher matcher) {
                this.val$matcher = matcher;
                super(x0, x1);
            }

            public int separatorStart(int start) {
                return this.val$matcher.find(start) ? this.val$matcher.start() : -1;
            }

            public int separatorEnd(int separatorPosition) {
                return this.val$matcher.end();
            }
        }

        C06253(Pattern pattern) {
            this.val$separatorPattern = pattern;
        }

        public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
            return new C09401(splitter, toSplit, this.val$separatorPattern.matcher(toSplit));
        }
    }

    /* renamed from: com.google.common.base.Splitter.4 */
    static class C06264 implements Strategy {
        final /* synthetic */ int val$length;

        /* renamed from: com.google.common.base.Splitter.4.1 */
        class C09411 extends SplittingIterator {
            C09411(Splitter x0, CharSequence x1) {
                super(x0, x1);
            }

            public int separatorStart(int start) {
                int nextChunkStart = start + C06264.this.val$length;
                return nextChunkStart < this.toSplit.length() ? nextChunkStart : -1;
            }

            public int separatorEnd(int separatorPosition) {
                return separatorPosition;
            }
        }

        C06264(int i) {
            this.val$length = i;
        }

        public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
            return new C09411(splitter, toSplit);
        }
    }

    private static abstract class SplittingIterator extends AbstractIterator<String> {
        int limit;
        int offset;
        final boolean omitEmptyStrings;
        final CharSequence toSplit;
        final CharMatcher trimmer;

        abstract int separatorEnd(int i);

        abstract int separatorStart(int i);

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.offset = 0;
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        protected String computeNext() {
            int nextStart = this.offset;
            while (this.offset != -1) {
                int end;
                int start = nextStart;
                int separatorPosition = separatorStart(this.offset);
                if (separatorPosition == -1) {
                    end = this.toSplit.length();
                    this.offset = -1;
                } else {
                    end = separatorPosition;
                    this.offset = separatorEnd(separatorPosition);
                }
                if (this.offset == nextStart) {
                    this.offset++;
                    if (this.offset >= this.toSplit.length()) {
                        this.offset = -1;
                    }
                } else {
                    while (start < end && this.trimmer.matches(this.toSplit.charAt(start))) {
                        start++;
                    }
                    while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                        end--;
                    }
                    if (this.omitEmptyStrings && start == end) {
                        nextStart = this.offset;
                    } else {
                        if (this.limit == 1) {
                            end = this.toSplit.length();
                            this.offset = -1;
                            while (end > start && this.trimmer.matches(this.toSplit.charAt(end - 1))) {
                                end--;
                            }
                        } else {
                            this.limit--;
                        }
                        return this.toSplit.subSequence(start, end).toString();
                    }
                }
            }
            return (String) endOfData();
        }
    }

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.NONE, Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }

    public static Splitter on(char separator) {
        return on(CharMatcher.is(separator));
    }

    public static Splitter on(CharMatcher separatorMatcher) {
        Preconditions.checkNotNull(separatorMatcher);
        return new Splitter(new C06231(separatorMatcher));
    }

    public static Splitter on(String separator) {
        Preconditions.checkArgument(separator.length() != 0, "The separator may not be the empty string.");
        return new Splitter(new C06242(separator));
    }

    @GwtIncompatible("java.util.regex")
    public static Splitter on(Pattern separatorPattern) {
        boolean z;
        Preconditions.checkNotNull(separatorPattern);
        if (separatorPattern.matcher(StringUtils.EMPTY).matches()) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "The pattern may not match the empty string: %s", separatorPattern);
        return new Splitter(new C06253(separatorPattern));
    }

    @GwtIncompatible("java.util.regex")
    public static Splitter onPattern(String separatorPattern) {
        return on(Pattern.compile(separatorPattern));
    }

    public static Splitter fixedLength(int length) {
        Preconditions.checkArgument(length > 0, "The length may not be less than 1");
        return new Splitter(new C06264(length));
    }

    @CheckReturnValue
    public Splitter omitEmptyStrings() {
        return new Splitter(this.strategy, true, this.trimmer, this.limit);
    }

    @CheckReturnValue
    public Splitter limit(int limit) {
        boolean z;
        if (limit > 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "must be greater than zero: %s", Integer.valueOf(limit));
        return new Splitter(this.strategy, this.omitEmptyStrings, this.trimmer, limit);
    }

    @CheckReturnValue
    public Splitter trimResults() {
        return trimResults(CharMatcher.WHITESPACE);
    }

    @CheckReturnValue
    public Splitter trimResults(CharMatcher trimmer) {
        Preconditions.checkNotNull(trimmer);
        return new Splitter(this.strategy, this.omitEmptyStrings, trimmer, this.limit);
    }

    public Iterable<String> split(CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        return new C02165(sequence);
    }

    private Iterator<String> splittingIterator(CharSequence sequence) {
        return this.strategy.iterator(this, sequence);
    }

    @Beta
    public List<String> splitToList(CharSequence sequence) {
        Preconditions.checkNotNull(sequence);
        Iterator<String> iterator = splittingIterator(sequence);
        List<String> result = new ArrayList();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return Collections.unmodifiableList(result);
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(String separator) {
        return withKeyValueSeparator(on(separator));
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(char separator) {
        return withKeyValueSeparator(on(separator));
    }

    @CheckReturnValue
    @Beta
    public MapSplitter withKeyValueSeparator(Splitter keyValueSplitter) {
        return new MapSplitter(keyValueSplitter, null);
    }
}
