package org.joda.time.format;

import com.google.analytics.containertag.proto.MutableServing.ServingValue;
import com.google.android.gms.location.LocationRequest;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

public class DateTimeFormat {
    static final int DATE = 0;
    static final int DATETIME = 2;
    static final int FULL = 0;
    static final int LONG = 1;
    static final int MEDIUM = 2;
    static final int NONE = 4;
    private static final Map<String, DateTimeFormatter> PATTERN_CACHE;
    private static final int PATTERN_CACHE_SIZE = 500;
    static final int SHORT = 3;
    private static final DateTimeFormatter[] STYLE_CACHE;
    static final int TIME = 1;

    /* renamed from: org.joda.time.format.DateTimeFormat.1 */
    static class C03841 extends LinkedHashMap<String, DateTimeFormatter> {
        private static final long serialVersionUID = 23;

        protected boolean removeEldestEntry(Entry<String, DateTimeFormatter> entry) {
            return size() > DateTimeFormat.PATTERN_CACHE_SIZE;
        }

        C03841(int i) {
            super(i);
        }
    }

    static class StyleFormatter implements DateTimePrinter, DateTimeParser {
        private static final Map<String, DateTimeFormatter> cCache;
        private final int iDateStyle;
        private final int iTimeStyle;
        private final int iType;

        static {
            cCache = new HashMap();
        }

        StyleFormatter(int i, int i2, int i3) {
            this.iDateStyle = i;
            this.iTimeStyle = i2;
            this.iType = i3;
        }

        public int estimatePrintedLength() {
            return 40;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            getFormatter(locale).getPrinter().printTo(stringBuffer, j, chronology, i, dateTimeZone, locale);
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            getFormatter(locale).getPrinter().printTo(writer, j, chronology, i, dateTimeZone, locale);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            getFormatter(locale).getPrinter().printTo(stringBuffer, readablePartial, locale);
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            getFormatter(locale).getPrinter().printTo(writer, readablePartial, locale);
        }

        public int estimateParsedLength() {
            return 40;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            return getFormatter(dateTimeParserBucket.getLocale()).getParser().parseInto(dateTimeParserBucket, str, i);
        }

        private DateTimeFormatter getFormatter(Locale locale) {
            DateTimeFormatter dateTimeFormatter;
            if (locale == null) {
                locale = Locale.getDefault();
            }
            String str = Integer.toString((this.iType + (this.iDateStyle << DateTimeFormat.NONE)) + (this.iTimeStyle << 8)) + locale.toString();
            synchronized (cCache) {
                dateTimeFormatter = (DateTimeFormatter) cCache.get(str);
                if (dateTimeFormatter == null) {
                    dateTimeFormatter = DateTimeFormat.forPattern(getPattern(locale));
                    cCache.put(str, dateTimeFormatter);
                }
            }
            return dateTimeFormatter;
        }

        String getPattern(Locale locale) {
            DateFormat dateFormat = null;
            switch (this.iType) {
                case DateTimeFormat.FULL /*0*/:
                    dateFormat = DateFormat.getDateInstance(this.iDateStyle, locale);
                    break;
                case DateTimeFormat.TIME /*1*/:
                    dateFormat = DateFormat.getTimeInstance(this.iTimeStyle, locale);
                    break;
                case DateTimeFormat.MEDIUM /*2*/:
                    dateFormat = DateFormat.getDateTimeInstance(this.iDateStyle, this.iTimeStyle, locale);
                    break;
            }
            if (dateFormat instanceof SimpleDateFormat) {
                return ((SimpleDateFormat) dateFormat).toPattern();
            }
            throw new IllegalArgumentException("No datetime pattern for locale: " + locale);
        }
    }

    static {
        PATTERN_CACHE = new C03841(7);
        STYLE_CACHE = new DateTimeFormatter[25];
    }

    public static DateTimeFormatter forPattern(String str) {
        return createFormatterForPattern(str);
    }

    public static DateTimeFormatter forStyle(String str) {
        return createFormatterForStyle(str);
    }

    public static String patternForStyle(String str, Locale locale) {
        DateTimeFormatter createFormatterForStyle = createFormatterForStyle(str);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return ((StyleFormatter) createFormatterForStyle.getPrinter()).getPattern(locale);
    }

    public static DateTimeFormatter shortDate() {
        return createFormatterForStyleIndex(SHORT, NONE);
    }

    public static DateTimeFormatter shortTime() {
        return createFormatterForStyleIndex(NONE, SHORT);
    }

    public static DateTimeFormatter shortDateTime() {
        return createFormatterForStyleIndex(SHORT, SHORT);
    }

    public static DateTimeFormatter mediumDate() {
        return createFormatterForStyleIndex(MEDIUM, NONE);
    }

    public static DateTimeFormatter mediumTime() {
        return createFormatterForStyleIndex(NONE, MEDIUM);
    }

    public static DateTimeFormatter mediumDateTime() {
        return createFormatterForStyleIndex(MEDIUM, MEDIUM);
    }

    public static DateTimeFormatter longDate() {
        return createFormatterForStyleIndex(TIME, NONE);
    }

    public static DateTimeFormatter longTime() {
        return createFormatterForStyleIndex(NONE, TIME);
    }

    public static DateTimeFormatter longDateTime() {
        return createFormatterForStyleIndex(TIME, TIME);
    }

    public static DateTimeFormatter fullDate() {
        return createFormatterForStyleIndex(FULL, NONE);
    }

    public static DateTimeFormatter fullTime() {
        return createFormatterForStyleIndex(NONE, FULL);
    }

    public static DateTimeFormatter fullDateTime() {
        return createFormatterForStyleIndex(FULL, FULL);
    }

    static void appendPatternTo(DateTimeFormatterBuilder dateTimeFormatterBuilder, String str) {
        parsePatternTo(dateTimeFormatterBuilder, str);
    }

    protected DateTimeFormat() {
    }

    private static void parsePatternTo(DateTimeFormatterBuilder dateTimeFormatterBuilder, String str) {
        int length = str.length();
        int[] iArr = new int[TIME];
        int i = FULL;
        while (i < length) {
            iArr[FULL] = i;
            String parseToken = parseToken(str, iArr);
            int i2 = iArr[FULL];
            int length2 = parseToken.length();
            if (length2 != 0) {
                char charAt = parseToken.charAt(FULL);
                switch (charAt) {
                    case '\'':
                        parseToken = parseToken.substring(TIME);
                        if (parseToken.length() != TIME) {
                            dateTimeFormatterBuilder.appendLiteral(new String(parseToken));
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendLiteral(parseToken.charAt(FULL));
                            break;
                        }
                    case 'C':
                        dateTimeFormatterBuilder.appendCenturyOfEra(length2, length2);
                        break;
                    case 'D':
                        dateTimeFormatterBuilder.appendDayOfYear(length2);
                        break;
                    case 'E':
                        if (length2 < NONE) {
                            dateTimeFormatterBuilder.appendDayOfWeekShortText();
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendDayOfWeekText();
                            break;
                        }
                    case 'G':
                        dateTimeFormatterBuilder.appendEraText();
                        break;
                    case 'H':
                        dateTimeFormatterBuilder.appendHourOfDay(length2);
                        break;
                    case 'K':
                        dateTimeFormatterBuilder.appendHourOfHalfday(length2);
                        break;
                    case 'M':
                        if (length2 >= SHORT) {
                            if (length2 < NONE) {
                                dateTimeFormatterBuilder.appendMonthOfYearShortText();
                                break;
                            } else {
                                dateTimeFormatterBuilder.appendMonthOfYearText();
                                break;
                            }
                        }
                        dateTimeFormatterBuilder.appendMonthOfYear(length2);
                        break;
                    case 'S':
                        dateTimeFormatterBuilder.appendFractionOfSecond(length2, length2);
                        break;
                    case 'Y':
                    case 'x':
                    case 'y':
                        if (length2 != MEDIUM) {
                            i = 9;
                            if (i2 + TIME < length) {
                                iArr[FULL] = iArr[FULL] + TIME;
                                if (isNumericToken(parseToken(str, iArr))) {
                                    i = length2;
                                }
                                iArr[FULL] = iArr[FULL] - 1;
                            }
                            switch (charAt) {
                                case 'Y':
                                    dateTimeFormatterBuilder.appendYearOfEra(length2, i);
                                    break;
                                case 'x':
                                    dateTimeFormatterBuilder.appendWeekyear(length2, i);
                                    break;
                                case 'y':
                                    dateTimeFormatterBuilder.appendYear(length2, i);
                                    break;
                                default:
                                    break;
                            }
                        }
                        boolean z = true;
                        if (i2 + TIME < length) {
                            iArr[FULL] = iArr[FULL] + TIME;
                            if (isNumericToken(parseToken(str, iArr))) {
                                z = false;
                            }
                            iArr[FULL] = iArr[FULL] - 1;
                        }
                        switch (charAt) {
                            case 'x':
                                dateTimeFormatterBuilder.appendTwoDigitWeekyear(new DateTime().getWeekyear() - 30, z);
                                break;
                            default:
                                dateTimeFormatterBuilder.appendTwoDigitYear(new DateTime().getYear() - 30, z);
                                break;
                        }
                    case 'Z':
                        if (length2 != TIME) {
                            if (length2 != MEDIUM) {
                                dateTimeFormatterBuilder.appendTimeZoneId();
                                break;
                            }
                            dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", true, MEDIUM, MEDIUM);
                            break;
                        }
                        dateTimeFormatterBuilder.appendTimeZoneOffset(null, "Z", false, MEDIUM, MEDIUM);
                        break;
                    case 'a':
                        dateTimeFormatterBuilder.appendHalfdayOfDayText();
                        break;
                    case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                        dateTimeFormatterBuilder.appendDayOfMonth(length2);
                        break;
                    case ServingValue.EXT_FIELD_NUMBER /*101*/:
                        dateTimeFormatterBuilder.appendDayOfWeek(length2);
                        break;
                    case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                        dateTimeFormatterBuilder.appendClockhourOfHalfday(length2);
                        break;
                    case 'k':
                        dateTimeFormatterBuilder.appendClockhourOfDay(length2);
                        break;
                    case 'm':
                        dateTimeFormatterBuilder.appendMinuteOfHour(length2);
                        break;
                    case 's':
                        dateTimeFormatterBuilder.appendSecondOfMinute(length2);
                        break;
                    case 'w':
                        dateTimeFormatterBuilder.appendWeekOfWeekyear(length2);
                        break;
                    case 'z':
                        if (length2 < NONE) {
                            dateTimeFormatterBuilder.appendTimeZoneShortName(null);
                            break;
                        } else {
                            dateTimeFormatterBuilder.appendTimeZoneName();
                            break;
                        }
                    default:
                        throw new IllegalArgumentException("Illegal pattern component: " + parseToken);
                }
                i = i2 + TIME;
            } else {
                return;
            }
        }
    }

    private static String parseToken(String str, int[] iArr) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = iArr[FULL];
        int length = str.length();
        char charAt = str.charAt(i);
        if ((charAt < 'A' || charAt > 'Z') && (charAt < 'a' || charAt > 'z')) {
            stringBuilder.append('\'');
            int i2 = FULL;
            while (i < length) {
                char charAt2 = str.charAt(i);
                if (charAt2 != '\'') {
                    if (i2 == 0 && ((charAt2 >= 'A' && charAt2 <= 'Z') || (charAt2 >= 'a' && charAt2 <= 'z'))) {
                        i--;
                        break;
                    }
                    stringBuilder.append(charAt2);
                } else if (i + TIME >= length || str.charAt(i + TIME) != '\'') {
                    i2 = i2 == 0 ? TIME : FULL;
                } else {
                    i += TIME;
                    stringBuilder.append(charAt2);
                }
                i += TIME;
            }
        } else {
            stringBuilder.append(charAt);
            while (i + TIME < length && str.charAt(i + TIME) == charAt) {
                stringBuilder.append(charAt);
                i += TIME;
            }
        }
        iArr[FULL] = i;
        return stringBuilder.toString();
    }

    private static boolean isNumericToken(String str) {
        int length = str.length();
        if (length > 0) {
            switch (str.charAt(FULL)) {
                case 'C':
                case 'D':
                case 'F':
                case 'H':
                case 'K':
                case 'S':
                case 'W':
                case 'Y':
                case 'c':
                case LocationRequest.PRIORITY_HIGH_ACCURACY /*100*/:
                case ServingValue.EXT_FIELD_NUMBER /*101*/:
                case LocationRequest.PRIORITY_LOW_POWER /*104*/:
                case 'k':
                case 'm':
                case 's':
                case 'w':
                case 'x':
                case 'y':
                    return true;
                case 'M':
                    if (length <= MEDIUM) {
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    private static DateTimeFormatter createFormatterForPattern(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }
        DateTimeFormatter dateTimeFormatter;
        synchronized (PATTERN_CACHE) {
            dateTimeFormatter = (DateTimeFormatter) PATTERN_CACHE.get(str);
            if (dateTimeFormatter == null) {
                DateTimeFormatterBuilder dateTimeFormatterBuilder = new DateTimeFormatterBuilder();
                parsePatternTo(dateTimeFormatterBuilder, str);
                dateTimeFormatter = dateTimeFormatterBuilder.toFormatter();
                PATTERN_CACHE.put(str, dateTimeFormatter);
            }
        }
        return dateTimeFormatter;
    }

    private static DateTimeFormatter createFormatterForStyle(String str) {
        if (str == null || str.length() != MEDIUM) {
            throw new IllegalArgumentException("Invalid style specification: " + str);
        }
        int selectStyle = selectStyle(str.charAt(FULL));
        int selectStyle2 = selectStyle(str.charAt(TIME));
        if (selectStyle != NONE || selectStyle2 != NONE) {
            return createFormatterForStyleIndex(selectStyle, selectStyle2);
        }
        throw new IllegalArgumentException("Style '--' is invalid");
    }

    private static DateTimeFormatter createFormatterForStyleIndex(int i, int i2) {
        int i3 = ((i << MEDIUM) + i) + i2;
        if (i3 >= STYLE_CACHE.length) {
            return createDateTimeFormatter(i, i2);
        }
        DateTimeFormatter dateTimeFormatter;
        synchronized (STYLE_CACHE) {
            dateTimeFormatter = STYLE_CACHE[i3];
            if (dateTimeFormatter == null) {
                dateTimeFormatter = createDateTimeFormatter(i, i2);
                STYLE_CACHE[i3] = dateTimeFormatter;
            }
        }
        return dateTimeFormatter;
    }

    private static DateTimeFormatter createDateTimeFormatter(int i, int i2) {
        int i3 = MEDIUM;
        if (i == NONE) {
            i3 = TIME;
        } else if (i2 == NONE) {
            i3 = FULL;
        }
        Object styleFormatter = new StyleFormatter(i, i2, i3);
        return new DateTimeFormatter(styleFormatter, styleFormatter);
    }

    private static int selectStyle(char c) {
        switch (c) {
            case '-':
                return NONE;
            case 'F':
                return FULL;
            case 'L':
                return TIME;
            case 'M':
                return MEDIUM;
            case 'S':
                return SHORT;
            default:
                throw new IllegalArgumentException("Invalid style character: " + c);
        }
    }
}
