package org.apache.commons.lang3.time;

import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTimeConstants;

public class FastDateFormat extends Format {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = null;
    private static final FormatCache<FastDateFormat> cache;
    private static final long serialVersionUID = 1;
    private final Locale mLocale;
    private transient int mMaxLengthEstimate;
    private final String mPattern;
    private transient Rule[] mRules;
    private final TimeZone mTimeZone;

    private interface Rule {
        void appendTo(StringBuffer stringBuffer, Calendar calendar);

        int estimateLength();
    }

    private static class TimeZoneDisplayKey {
        private final Locale mLocale;
        private final int mStyle;
        private final TimeZone mTimeZone;

        TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale) {
            this.mTimeZone = timeZone;
            if (daylight) {
                style |= Integer.MIN_VALUE;
            }
            this.mStyle = style;
            this.mLocale = locale;
        }

        public int hashCode() {
            return (((this.mStyle * 31) + this.mLocale.hashCode()) * 31) + this.mTimeZone.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeZoneDisplayKey)) {
                return false;
            }
            TimeZoneDisplayKey other = (TimeZoneDisplayKey) obj;
            if (this.mTimeZone.equals(other.mTimeZone) && this.mStyle == other.mStyle && this.mLocale.equals(other.mLocale)) {
                return true;
            }
            return false;
        }
    }

    /* renamed from: org.apache.commons.lang3.time.FastDateFormat.1 */
    static class C08611 extends FormatCache<FastDateFormat> {
        C08611() {
        }

        protected FastDateFormat createInstance(String pattern, TimeZone timeZone, Locale locale) {
            return new FastDateFormat(pattern, timeZone, locale);
        }
    }

    private static class CharacterLiteral implements Rule {
        private final char mValue;

        CharacterLiteral(char value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return FastDateFormat.LONG;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private interface NumberRule extends Rule {
        void appendTo(StringBuffer stringBuffer, int i);
    }

    private static class StringLiteral implements Rule {
        private final String mValue;

        StringLiteral(String value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return this.mValue.length();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int field, String[] values) {
            this.mField = field;
            this.mValues = values;
        }

        public int estimateLength() {
            int max = FastDateFormat.FULL;
            int i = this.mValues.length;
            while (true) {
                i--;
                if (i < 0) {
                    return max;
                }
                int len = this.mValues[i].length();
                if (len > max) {
                    max = len;
                }
            }
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class TimeZoneNameRule implements Rule {
        private final String mDaylight;
        private final String mStandard;
        private final TimeZone mTimeZone;

        TimeZoneNameRule(TimeZone timeZone, Locale locale, int style) {
            this.mTimeZone = timeZone;
            this.mStandard = FastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
            this.mDaylight = FastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
        }

        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            if (!this.mTimeZone.useDaylightTime() || calendar.get(16) == 0) {
                buffer.append(this.mStandard);
            } else {
                buffer.append(this.mDaylight);
            }
        }
    }

    private static class TimeZoneNumberRule implements Rule {
        static final TimeZoneNumberRule INSTANCE_COLON;
        static final TimeZoneNumberRule INSTANCE_NO_COLON;
        final boolean mColon;

        static {
            INSTANCE_COLON = new TimeZoneNumberRule(true);
            INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
        }

        TimeZoneNumberRule(boolean colon) {
            this.mColon = colon;
        }

        public int estimateLength() {
            return 5;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int offset = calendar.get(15) + calendar.get(16);
            if (offset < 0) {
                buffer.append('-');
                offset = -offset;
            } else {
                buffer.append('+');
            }
            int hours = offset / DateTimeConstants.MILLIS_PER_HOUR;
            buffer.append((char) ((hours / 10) + 48));
            buffer.append((char) ((hours % 10) + 48));
            if (this.mColon) {
                buffer.append(':');
            }
            int minutes = (offset / DateTimeConstants.MILLIS_PER_MINUTE) - (hours * 60);
            buffer.append((char) ((minutes / 10) + 48));
            buffer.append((char) ((minutes % 10) + 48));
        }
    }

    private static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int field, int size) {
            if (size < FastDateFormat.SHORT) {
                throw new IllegalArgumentException();
            }
            this.mField = field;
            this.mSize = size;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            int i;
            if (value < 100) {
                i = this.mSize;
                while (true) {
                    i--;
                    if (i >= FastDateFormat.MEDIUM) {
                        buffer.append('0');
                    } else {
                        buffer.append((char) ((value / 10) + 48));
                        buffer.append((char) ((value % 10) + 48));
                        return;
                    }
                }
            }
            int digits;
            if (value < DateTimeConstants.MILLIS_PER_SECOND) {
                digits = FastDateFormat.SHORT;
            } else {
                Validate.isTrue(value > -1, "Negative values should not be possible", (long) value);
                digits = Integer.toString(value).length();
            }
            i = this.mSize;
            while (true) {
                i--;
                if (i >= digits) {
                    buffer.append('0');
                } else {
                    buffer.append(Integer.toString(value));
                    return;
                }
            }
        }
    }

    private static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(10);
            if (value == 0) {
                value = calendar.getLeastMaximum(10) + FastDateFormat.LONG;
            }
            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule rule) {
            this.mRule = rule;
        }

        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(11);
            if (value == 0) {
                value = calendar.getMaximum(11) + FastDateFormat.LONG;
            }
            this.mRule.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.mRule.appendTo(buffer, value);
        }
    }

    private static class TwoDigitMonthField implements NumberRule {
        static final TwoDigitMonthField INSTANCE;

        static {
            INSTANCE = new TwoDigitMonthField();
        }

        TwoDigitMonthField() {
        }

        public int estimateLength() {
            return FastDateFormat.MEDIUM;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(FastDateFormat.MEDIUM) + FastDateFormat.LONG);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        TwoDigitNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return FastDateFormat.MEDIUM;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 100) {
                buffer.append((char) ((value / 10) + 48));
                buffer.append((char) ((value % 10) + 48));
                return;
            }
            buffer.append(Integer.toString(value));
        }
    }

    private static class TwoDigitYearField implements NumberRule {
        static final TwoDigitYearField INSTANCE;

        static {
            INSTANCE = new TwoDigitYearField();
        }

        TwoDigitYearField() {
        }

        public int estimateLength() {
            return FastDateFormat.MEDIUM;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(FastDateFormat.LONG) % 100);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class UnpaddedMonthField implements NumberRule {
        static final UnpaddedMonthField INSTANCE;

        static {
            INSTANCE = new UnpaddedMonthField();
        }

        UnpaddedMonthField() {
        }

        public int estimateLength() {
            return FastDateFormat.MEDIUM;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(FastDateFormat.MEDIUM) + FastDateFormat.LONG);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append((char) (value + 48));
                return;
            }
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        UnpaddedNumberField(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append((char) (value + 48));
            } else if (value < 100) {
                buffer.append((char) ((value / 10) + 48));
                buffer.append((char) ((value % 10) + 48));
            } else {
                buffer.append(Integer.toString(value));
            }
        }
    }

    static {
        cache = new C08611();
        cTimeZoneDisplayCache = new ConcurrentHashMap(7);
    }

    public static FastDateFormat getInstance() {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(SHORT), Integer.valueOf(SHORT), null, null);
    }

    public static FastDateFormat getInstance(String pattern) {
        return (FastDateFormat) cache.getInstance(pattern, null, null);
    }

    public static FastDateFormat getInstance(String pattern, TimeZone timeZone) {
        return (FastDateFormat) cache.getInstance(pattern, timeZone, null);
    }

    public static FastDateFormat getInstance(String pattern, Locale locale) {
        return (FastDateFormat) cache.getInstance(pattern, null, locale);
    }

    public static FastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) cache.getInstance(pattern, timeZone, locale);
    }

    public static FastDateFormat getDateInstance(int style) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, null, null);
    }

    public static FastDateFormat getDateInstance(int style, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, null, locale);
    }

    public static FastDateFormat getDateInstance(int style, TimeZone timeZone) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, timeZone, null);
    }

    public static FastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, timeZone, locale);
    }

    public static FastDateFormat getTimeInstance(int style) {
        return (FastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), null, null);
    }

    public static FastDateFormat getTimeInstance(int style, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), null, locale);
    }

    public static FastDateFormat getTimeInstance(int style, TimeZone timeZone) {
        return (FastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), timeZone, null);
    }

    public static FastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), timeZone, locale);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), null, null);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone) {
        return getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
    }

    public static FastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale) {
        return (FastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), timeZone, locale);
    }

    static String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) {
        TimeZoneDisplayKey key = new TimeZoneDisplayKey(tz, daylight, style, locale);
        String value = (String) cTimeZoneDisplayCache.get(key);
        if (value != null) {
            return value;
        }
        value = tz.getDisplayName(daylight, style, locale);
        String prior = (String) cTimeZoneDisplayCache.putIfAbsent(key, value);
        if (prior != null) {
            return prior;
        }
        return value;
    }

    protected FastDateFormat(String pattern, TimeZone timeZone, Locale locale) {
        this.mPattern = pattern;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        init();
    }

    private void init() {
        List<Rule> rulesList = parsePattern();
        this.mRules = (Rule[]) rulesList.toArray(new Rule[rulesList.size()]);
        int len = FULL;
        int i = this.mRules.length;
        while (true) {
            i--;
            if (i >= 0) {
                len += this.mRules[i].estimateLength();
            } else {
                this.mMaxLengthEstimate = len;
                return;
            }
        }
    }

    protected List<Rule> parsePattern() {
        DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
        List<Rule> rules = new ArrayList();
        String[] ERAs = symbols.getEras();
        String[] months = symbols.getMonths();
        String[] shortMonths = symbols.getShortMonths();
        String[] weekdays = symbols.getWeekdays();
        String[] shortWeekdays = symbols.getShortWeekdays();
        String[] AmPmStrings = symbols.getAmPmStrings();
        int length = this.mPattern.length();
        int[] indexRef = new int[LONG];
        int i = FULL;
        while (i < length) {
            indexRef[FULL] = i;
            String token = parseToken(this.mPattern, indexRef);
            i = indexRef[FULL];
            int tokenLen = token.length();
            if (tokenLen == 0) {
                return rules;
            }
            Rule rule;
            switch (token.charAt(FULL)) {
                case '\'':
                    String sub = token.substring(LONG);
                    if (sub.length() != LONG) {
                        rule = new StringLiteral(sub);
                        break;
                    }
                    rule = new CharacterLiteral(sub.charAt(FULL));
                    break;
                case 'D':
                    rule = selectNumberRule(6, tokenLen);
                    break;
                case 'E':
                    String[] strArr;
                    if (tokenLen < 4) {
                        strArr = shortWeekdays;
                    } else {
                        strArr = weekdays;
                    }
                    rule = new TextField(7, strArr);
                    break;
                case 'F':
                    rule = selectNumberRule(8, tokenLen);
                    break;
                case 'G':
                    rule = new TextField(FULL, ERAs);
                    break;
                case 'H':
                    rule = selectNumberRule(11, tokenLen);
                    break;
                case 'K':
                    rule = selectNumberRule(10, tokenLen);
                    break;
                case 'M':
                    if (tokenLen < 4) {
                        if (tokenLen != SHORT) {
                            if (tokenLen != MEDIUM) {
                                rule = UnpaddedMonthField.INSTANCE;
                                break;
                            }
                            rule = TwoDigitMonthField.INSTANCE;
                            break;
                        }
                        rule = new TextField(MEDIUM, shortMonths);
                        break;
                    }
                    rule = new TextField(MEDIUM, months);
                    break;
                case 'S':
                    rule = selectNumberRule(14, tokenLen);
                    break;
                case 'W':
                    rule = selectNumberRule(4, tokenLen);
                    break;
                case 'Z':
                    if (tokenLen != LONG) {
                        rule = TimeZoneNumberRule.INSTANCE_COLON;
                        break;
                    }
                    rule = TimeZoneNumberRule.INSTANCE_NO_COLON;
                    break;
                case 'a':
                    rule = new TextField(9, AmPmStrings);
                    break;
                case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                    rule = selectNumberRule(5, tokenLen);
                    break;
                case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                    rule = new TwelveHourField(selectNumberRule(10, tokenLen));
                    break;
                case 'k':
                    rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
                    break;
                case 'm':
                    rule = selectNumberRule(12, tokenLen);
                    break;
                case 's':
                    rule = selectNumberRule(13, tokenLen);
                    break;
                case 'w':
                    rule = selectNumberRule(SHORT, tokenLen);
                    break;
                case 'y':
                    if (tokenLen != MEDIUM) {
                        if (tokenLen < 4) {
                            tokenLen = 4;
                        }
                        rule = selectNumberRule(LONG, tokenLen);
                        break;
                    }
                    rule = TwoDigitYearField.INSTANCE;
                    break;
                case 'z':
                    if (tokenLen < 4) {
                        rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, FULL);
                        break;
                    }
                    rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, LONG);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
            }
            rules.add(rule);
            i += LONG;
        }
        return rules;
    }

    protected String parseToken(String pattern, int[] indexRef) {
        StringBuilder buf = new StringBuilder();
        int i = indexRef[FULL];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
            buf.append('\'');
            boolean inLiteral = false;
            while (i < length) {
                c = pattern.charAt(i);
                if (c != '\'') {
                    if (!inLiteral && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                        i--;
                        break;
                    }
                    buf.append(c);
                } else if (i + LONG >= length || pattern.charAt(i + LONG) != '\'') {
                    inLiteral = !inLiteral;
                } else {
                    i += LONG;
                    buf.append(c);
                }
                i += LONG;
            }
        } else {
            buf.append(c);
            while (i + LONG < length && pattern.charAt(i + LONG) == c) {
                buf.append(c);
                i += LONG;
            }
        }
        indexRef[FULL] = i;
        return buf.toString();
    }

    protected NumberRule selectNumberRule(int field, int padding) {
        switch (padding) {
            case LONG /*1*/:
                return new UnpaddedNumberField(field);
            case MEDIUM /*2*/:
                return new TwoDigitNumberField(field);
            default:
                return new PaddedNumberField(field, padding);
        }
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Date) {
            return format((Date) obj, toAppendTo);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, toAppendTo);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), toAppendTo);
        }
        throw new IllegalArgumentException("Unknown class: " + (obj == null ? "<null>" : obj.getClass().getName()));
    }

    public String format(long millis) {
        return format(new Date(millis));
    }

    public String format(Date date) {
        Calendar c = new GregorianCalendar(this.mTimeZone, this.mLocale);
        c.setTime(date);
        return applyRules(c, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public String format(Calendar calendar) {
        return format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public StringBuffer format(long millis, StringBuffer buf) {
        return format(new Date(millis), buf);
    }

    public StringBuffer format(Date date, StringBuffer buf) {
        Calendar c = new GregorianCalendar(this.mTimeZone, this.mLocale);
        c.setTime(date);
        return applyRules(c, buf);
    }

    public StringBuffer format(Calendar calendar, StringBuffer buf) {
        return applyRules(calendar, buf);
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
        Rule[] arr$ = this.mRules;
        int len$ = arr$.length;
        for (int i$ = FULL; i$ < len$; i$ += LONG) {
            arr$[i$].appendTo(buf, calendar);
        }
        return buf;
    }

    public Object parseObject(String source, ParsePosition pos) {
        pos.setIndex(FULL);
        pos.setErrorIndex(FULL);
        return null;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FastDateFormat)) {
            return false;
        }
        FastDateFormat other = (FastDateFormat) obj;
        if (this.mPattern.equals(other.mPattern) && this.mTimeZone.equals(other.mTimeZone) && this.mLocale.equals(other.mLocale)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mPattern.hashCode() + ((this.mTimeZone.hashCode() + (this.mLocale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDateFormat[" + this.mPattern + "]";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        init();
    }
}
