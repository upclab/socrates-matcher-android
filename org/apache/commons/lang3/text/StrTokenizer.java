package org.apache.commons.lang3.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class StrTokenizer implements ListIterator<String>, Cloneable {
    private static final StrTokenizer CSV_TOKENIZER_PROTOTYPE;
    private static final StrTokenizer TSV_TOKENIZER_PROTOTYPE;
    private char[] chars;
    private StrMatcher delimMatcher;
    private boolean emptyAsNull;
    private boolean ignoreEmptyTokens;
    private StrMatcher ignoredMatcher;
    private StrMatcher quoteMatcher;
    private int tokenPos;
    private String[] tokens;
    private StrMatcher trimmerMatcher;

    static {
        CSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        CSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.commaMatcher());
        CSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        CSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        CSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        CSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        CSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
        TSV_TOKENIZER_PROTOTYPE = new StrTokenizer();
        TSV_TOKENIZER_PROTOTYPE.setDelimiterMatcher(StrMatcher.tabMatcher());
        TSV_TOKENIZER_PROTOTYPE.setQuoteMatcher(StrMatcher.doubleQuoteMatcher());
        TSV_TOKENIZER_PROTOTYPE.setIgnoredMatcher(StrMatcher.noneMatcher());
        TSV_TOKENIZER_PROTOTYPE.setTrimmerMatcher(StrMatcher.trimMatcher());
        TSV_TOKENIZER_PROTOTYPE.setEmptyTokenAsNull(false);
        TSV_TOKENIZER_PROTOTYPE.setIgnoreEmptyTokens(false);
    }

    private static StrTokenizer getCSVClone() {
        return (StrTokenizer) CSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getCSVInstance() {
        return getCSVClone();
    }

    public static StrTokenizer getCSVInstance(String input) {
        StrTokenizer tok = getCSVClone();
        tok.reset(input);
        return tok;
    }

    public static StrTokenizer getCSVInstance(char[] input) {
        StrTokenizer tok = getCSVClone();
        tok.reset(input);
        return tok;
    }

    private static StrTokenizer getTSVClone() {
        return (StrTokenizer) TSV_TOKENIZER_PROTOTYPE.clone();
    }

    public static StrTokenizer getTSVInstance() {
        return getTSVClone();
    }

    public static StrTokenizer getTSVInstance(String input) {
        StrTokenizer tok = getTSVClone();
        tok.reset(input);
        return tok;
    }

    public static StrTokenizer getTSVInstance(char[] input) {
        StrTokenizer tok = getTSVClone();
        tok.reset(input);
        return tok;
    }

    public StrTokenizer() {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = null;
    }

    public StrTokenizer(String input) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        if (input != null) {
            this.chars = input.toCharArray();
        } else {
            this.chars = null;
        }
    }

    public StrTokenizer(String input, char delim) {
        this(input);
        setDelimiterChar(delim);
    }

    public StrTokenizer(String input, String delim) {
        this(input);
        setDelimiterString(delim);
    }

    public StrTokenizer(String input, StrMatcher delim) {
        this(input);
        setDelimiterMatcher(delim);
    }

    public StrTokenizer(String input, char delim, char quote) {
        this(input, delim);
        setQuoteChar(quote);
    }

    public StrTokenizer(String input, StrMatcher delim, StrMatcher quote) {
        this(input, delim);
        setQuoteMatcher(quote);
    }

    public StrTokenizer(char[] input) {
        this.delimMatcher = StrMatcher.splitMatcher();
        this.quoteMatcher = StrMatcher.noneMatcher();
        this.ignoredMatcher = StrMatcher.noneMatcher();
        this.trimmerMatcher = StrMatcher.noneMatcher();
        this.emptyAsNull = false;
        this.ignoreEmptyTokens = true;
        this.chars = ArrayUtils.clone(input);
    }

    public StrTokenizer(char[] input, char delim) {
        this(input);
        setDelimiterChar(delim);
    }

    public StrTokenizer(char[] input, String delim) {
        this(input);
        setDelimiterString(delim);
    }

    public StrTokenizer(char[] input, StrMatcher delim) {
        this(input);
        setDelimiterMatcher(delim);
    }

    public StrTokenizer(char[] input, char delim, char quote) {
        this(input, delim);
        setQuoteChar(quote);
    }

    public StrTokenizer(char[] input, StrMatcher delim, StrMatcher quote) {
        this(input, delim);
        setQuoteMatcher(quote);
    }

    public int size() {
        checkTokenized();
        return this.tokens.length;
    }

    public String nextToken() {
        if (!hasNext()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i = this.tokenPos;
        this.tokenPos = i + 1;
        return strArr[i];
    }

    public String previousToken() {
        if (!hasPrevious()) {
            return null;
        }
        String[] strArr = this.tokens;
        int i = this.tokenPos - 1;
        this.tokenPos = i;
        return strArr[i];
    }

    public String[] getTokenArray() {
        checkTokenized();
        return (String[]) this.tokens.clone();
    }

    public List<String> getTokenList() {
        checkTokenized();
        List<String> list = new ArrayList(this.tokens.length);
        for (String element : this.tokens) {
            list.add(element);
        }
        return list;
    }

    public StrTokenizer reset() {
        this.tokenPos = 0;
        this.tokens = null;
        return this;
    }

    public StrTokenizer reset(String input) {
        reset();
        if (input != null) {
            this.chars = input.toCharArray();
        } else {
            this.chars = null;
        }
        return this;
    }

    public StrTokenizer reset(char[] input) {
        reset();
        this.chars = ArrayUtils.clone(input);
        return this;
    }

    public boolean hasNext() {
        checkTokenized();
        return this.tokenPos < this.tokens.length;
    }

    public String next() {
        if (hasNext()) {
            String[] strArr = this.tokens;
            int i = this.tokenPos;
            this.tokenPos = i + 1;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    public int nextIndex() {
        return this.tokenPos;
    }

    public boolean hasPrevious() {
        checkTokenized();
        return this.tokenPos > 0;
    }

    public String previous() {
        if (hasPrevious()) {
            String[] strArr = this.tokens;
            int i = this.tokenPos - 1;
            this.tokenPos = i;
            return strArr[i];
        }
        throw new NoSuchElementException();
    }

    public int previousIndex() {
        return this.tokenPos - 1;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is unsupported");
    }

    public void set(String obj) {
        throw new UnsupportedOperationException("set() is unsupported");
    }

    public void add(String obj) {
        throw new UnsupportedOperationException("add() is unsupported");
    }

    private void checkTokenized() {
        if (this.tokens != null) {
            return;
        }
        if (this.chars == null) {
            List<String> split = tokenize(null, 0, 0);
            this.tokens = (String[]) split.toArray(new String[split.size()]);
            return;
        }
        split = tokenize(this.chars, 0, this.chars.length);
        this.tokens = (String[]) split.toArray(new String[split.size()]);
    }

    protected List<String> tokenize(char[] chars, int offset, int count) {
        if (chars == null || count == 0) {
            return Collections.emptyList();
        }
        StrBuilder buf = new StrBuilder();
        List<String> tokens = new ArrayList();
        int pos = offset;
        while (pos >= 0 && pos < count) {
            pos = readNextToken(chars, pos, count, buf, tokens);
            if (pos >= count) {
                addToken(tokens, StringUtils.EMPTY);
            }
        }
        return tokens;
    }

    private void addToken(List<String> list, String tok) {
        if (tok == null || tok.length() == 0) {
            if (!isIgnoreEmptyTokens()) {
                if (isEmptyTokenAsNull()) {
                    tok = null;
                }
            } else {
                return;
            }
        }
        list.add(tok);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int readNextToken(char[] r23, int r24, int r25, org.apache.commons.lang3.text.StrBuilder r26, java.util.List<java.lang.String> r27) {
        /*
        r22 = this;
    L_0x0000:
        r0 = r24;
        r1 = r25;
        if (r0 >= r1) goto L_0x0050;
    L_0x0006:
        r4 = r22.getIgnoredMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r4 = r4.isMatch(r0, r1, r2, r3);
        r5 = r22.getTrimmerMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r5 = r5.isMatch(r0, r1, r2, r3);
        r21 = java.lang.Math.max(r4, r5);
        if (r21 == 0) goto L_0x0050;
    L_0x002c:
        r4 = r22.getDelimiterMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r4 = r4.isMatch(r0, r1, r2, r3);
        if (r4 > 0) goto L_0x0050;
    L_0x003e:
        r4 = r22.getQuoteMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r4 = r4.isMatch(r0, r1, r2, r3);
        if (r4 <= 0) goto L_0x0061;
    L_0x0050:
        r0 = r24;
        r1 = r25;
        if (r0 < r1) goto L_0x0064;
    L_0x0056:
        r4 = "";
        r0 = r22;
        r1 = r27;
        r0.addToken(r1, r4);
        r4 = -1;
    L_0x0060:
        return r4;
    L_0x0061:
        r24 = r24 + r21;
        goto L_0x0000;
    L_0x0064:
        r4 = r22.getDelimiterMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r20 = r4.isMatch(r0, r1, r2, r3);
        if (r20 <= 0) goto L_0x0082;
    L_0x0076:
        r4 = "";
        r0 = r22;
        r1 = r27;
        r0.addToken(r1, r4);
        r4 = r24 + r20;
        goto L_0x0060;
    L_0x0082:
        r4 = r22.getQuoteMatcher();
        r0 = r23;
        r1 = r24;
        r2 = r24;
        r3 = r25;
        r11 = r4.isMatch(r0, r1, r2, r3);
        if (r11 <= 0) goto L_0x00a7;
    L_0x0094:
        r6 = r24 + r11;
        r4 = r22;
        r5 = r23;
        r7 = r25;
        r8 = r26;
        r9 = r27;
        r10 = r24;
        r4 = r4.readWithQuotes(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0060;
    L_0x00a7:
        r18 = 0;
        r19 = 0;
        r12 = r22;
        r13 = r23;
        r14 = r24;
        r15 = r25;
        r16 = r26;
        r17 = r27;
        r4 = r12.readWithQuotes(r13, r14, r15, r16, r17, r18, r19);
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.text.StrTokenizer.readNextToken(char[], int, int, org.apache.commons.lang3.text.StrBuilder, java.util.List):int");
    }

    private int readWithQuotes(char[] chars, int start, int len, StrBuilder workArea, List<String> tokens, int quoteStart, int quoteLen) {
        workArea.clear();
        int pos = start;
        boolean quoting = quoteLen > 0;
        int trimStart = 0;
        while (pos < len) {
            int pos2;
            if (!quoting) {
                int delimLen = getDelimiterMatcher().isMatch(chars, pos, start, len);
                if (delimLen > 0) {
                    addToken(tokens, workArea.substring(0, trimStart));
                    return pos + delimLen;
                } else if (quoteLen <= 0 || !isQuote(chars, pos, len, quoteStart, quoteLen)) {
                    int ignoredLen = getIgnoredMatcher().isMatch(chars, pos, start, len);
                    if (ignoredLen > 0) {
                        pos += ignoredLen;
                    } else {
                        int trimmedLen = getTrimmerMatcher().isMatch(chars, pos, start, len);
                        if (trimmedLen > 0) {
                            workArea.append(chars, pos, trimmedLen);
                            pos += trimmedLen;
                        } else {
                            pos2 = pos + 1;
                            workArea.append(chars[pos]);
                            trimStart = workArea.size();
                            pos = pos2;
                        }
                    }
                } else {
                    quoting = true;
                    pos += quoteLen;
                }
            } else if (isQuote(chars, pos, len, quoteStart, quoteLen)) {
                if (isQuote(chars, pos + quoteLen, len, quoteStart, quoteLen)) {
                    workArea.append(chars, pos, quoteLen);
                    pos += quoteLen * 2;
                    trimStart = workArea.size();
                } else {
                    quoting = false;
                    pos += quoteLen;
                }
            } else {
                pos2 = pos + 1;
                workArea.append(chars[pos]);
                trimStart = workArea.size();
                pos = pos2;
            }
        }
        addToken(tokens, workArea.substring(0, trimStart));
        return -1;
    }

    private boolean isQuote(char[] chars, int pos, int len, int quoteStart, int quoteLen) {
        int i = 0;
        while (i < quoteLen) {
            if (pos + i >= len || chars[pos + i] != chars[quoteStart + i]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public StrMatcher getDelimiterMatcher() {
        return this.delimMatcher;
    }

    public StrTokenizer setDelimiterMatcher(StrMatcher delim) {
        if (delim == null) {
            this.delimMatcher = StrMatcher.noneMatcher();
        } else {
            this.delimMatcher = delim;
        }
        return this;
    }

    public StrTokenizer setDelimiterChar(char delim) {
        return setDelimiterMatcher(StrMatcher.charMatcher(delim));
    }

    public StrTokenizer setDelimiterString(String delim) {
        return setDelimiterMatcher(StrMatcher.stringMatcher(delim));
    }

    public StrMatcher getQuoteMatcher() {
        return this.quoteMatcher;
    }

    public StrTokenizer setQuoteMatcher(StrMatcher quote) {
        if (quote != null) {
            this.quoteMatcher = quote;
        }
        return this;
    }

    public StrTokenizer setQuoteChar(char quote) {
        return setQuoteMatcher(StrMatcher.charMatcher(quote));
    }

    public StrMatcher getIgnoredMatcher() {
        return this.ignoredMatcher;
    }

    public StrTokenizer setIgnoredMatcher(StrMatcher ignored) {
        if (ignored != null) {
            this.ignoredMatcher = ignored;
        }
        return this;
    }

    public StrTokenizer setIgnoredChar(char ignored) {
        return setIgnoredMatcher(StrMatcher.charMatcher(ignored));
    }

    public StrMatcher getTrimmerMatcher() {
        return this.trimmerMatcher;
    }

    public StrTokenizer setTrimmerMatcher(StrMatcher trimmer) {
        if (trimmer != null) {
            this.trimmerMatcher = trimmer;
        }
        return this;
    }

    public boolean isEmptyTokenAsNull() {
        return this.emptyAsNull;
    }

    public StrTokenizer setEmptyTokenAsNull(boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
        return this;
    }

    public boolean isIgnoreEmptyTokens() {
        return this.ignoreEmptyTokens;
    }

    public StrTokenizer setIgnoreEmptyTokens(boolean ignoreEmptyTokens) {
        this.ignoreEmptyTokens = ignoreEmptyTokens;
        return this;
    }

    public String getContent() {
        if (this.chars == null) {
            return null;
        }
        return new String(this.chars);
    }

    public Object clone() {
        try {
            return cloneReset();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    Object cloneReset() throws CloneNotSupportedException {
        StrTokenizer cloned = (StrTokenizer) super.clone();
        if (cloned.chars != null) {
            cloned.chars = (char[]) cloned.chars.clone();
        }
        cloned.reset();
        return cloned;
    }

    public String toString() {
        if (this.tokens == null) {
            return "StrTokenizer[not tokenized yet]";
        }
        return "StrTokenizer" + getTokenList();
    }
}
