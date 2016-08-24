package org.joda.time.chrono;

import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.MillisDurationField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.PreciseDateTimeField;
import org.joda.time.field.PreciseDurationField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.ZeroIsMaxDateTimeField;

abstract class BasicChronology extends AssembledChronology {
    private static final int CACHE_MASK = 1023;
    private static final int CACHE_SIZE = 1024;
    private static final DateTimeField cClockhourOfDayField;
    private static final DateTimeField cClockhourOfHalfdayField;
    private static final DurationField cDaysField;
    private static final DateTimeField cHalfdayOfDayField;
    private static final DurationField cHalfdaysField;
    private static final DateTimeField cHourOfDayField;
    private static final DateTimeField cHourOfHalfdayField;
    private static final DurationField cHoursField;
    private static final DurationField cMillisField;
    private static final DateTimeField cMillisOfDayField;
    private static final DateTimeField cMillisOfSecondField;
    private static final DateTimeField cMinuteOfDayField;
    private static final DateTimeField cMinuteOfHourField;
    private static final DurationField cMinutesField;
    private static final DateTimeField cSecondOfDayField;
    private static final DateTimeField cSecondOfMinuteField;
    private static final DurationField cSecondsField;
    private static final DurationField cWeeksField;
    private static final long serialVersionUID = 8283225332206808863L;
    private final int iMinDaysInFirstWeek;
    private final transient YearInfo[] iYearInfoCache;

    private static class YearInfo {
        public final long iFirstDayMillis;
        public final int iYear;

        YearInfo(int i, long j) {
            this.iYear = i;
            this.iFirstDayMillis = j;
        }
    }

    private static class HalfdayField extends PreciseDateTimeField {
        private static final long serialVersionUID = 581601443656929254L;

        HalfdayField() {
            super(DateTimeFieldType.halfdayOfDay(), BasicChronology.cHalfdaysField, BasicChronology.cDaysField);
        }

        public String getAsText(int i, Locale locale) {
            return GJLocaleSymbols.forLocale(locale).halfdayValueToText(i);
        }

        public long set(long j, String str, Locale locale) {
            return set(j, GJLocaleSymbols.forLocale(locale).halfdayTextToValue(str));
        }

        public int getMaximumTextLength(Locale locale) {
            return GJLocaleSymbols.forLocale(locale).getHalfdayMaxTextLength();
        }
    }

    abstract long calculateFirstDayOfYearMillis(int i);

    abstract long getApproxMillisAtEpochDividedByTwo();

    abstract long getAverageMillisPerMonth();

    abstract long getAverageMillisPerYear();

    abstract long getAverageMillisPerYearDividedByTwo();

    abstract int getDaysInMonthMax(int i);

    abstract int getDaysInYearMonth(int i, int i2);

    abstract int getMaxYear();

    abstract int getMinYear();

    abstract int getMonthOfYear(long j, int i);

    abstract long getTotalMillisByYearMonth(int i, int i2);

    abstract long getYearDifference(long j, long j2);

    abstract boolean isLeapYear(int i);

    abstract long setYear(long j, int i);

    static {
        cMillisField = MillisDurationField.INSTANCE;
        cSecondsField = new PreciseDurationField(DurationFieldType.seconds(), 1000);
        cMinutesField = new PreciseDurationField(DurationFieldType.minutes(), DateUtils.MILLIS_PER_MINUTE);
        cHoursField = new PreciseDurationField(DurationFieldType.hours(), DateUtils.MILLIS_PER_HOUR);
        cHalfdaysField = new PreciseDurationField(DurationFieldType.halfdays(), 43200000);
        cDaysField = new PreciseDurationField(DurationFieldType.days(), DateUtils.MILLIS_PER_DAY);
        cWeeksField = new PreciseDurationField(DurationFieldType.weeks(), 604800000);
        cMillisOfSecondField = new PreciseDateTimeField(DateTimeFieldType.millisOfSecond(), cMillisField, cSecondsField);
        cMillisOfDayField = new PreciseDateTimeField(DateTimeFieldType.millisOfDay(), cMillisField, cDaysField);
        cSecondOfMinuteField = new PreciseDateTimeField(DateTimeFieldType.secondOfMinute(), cSecondsField, cMinutesField);
        cSecondOfDayField = new PreciseDateTimeField(DateTimeFieldType.secondOfDay(), cSecondsField, cDaysField);
        cMinuteOfHourField = new PreciseDateTimeField(DateTimeFieldType.minuteOfHour(), cMinutesField, cHoursField);
        cMinuteOfDayField = new PreciseDateTimeField(DateTimeFieldType.minuteOfDay(), cMinutesField, cDaysField);
        cHourOfDayField = new PreciseDateTimeField(DateTimeFieldType.hourOfDay(), cHoursField, cDaysField);
        cHourOfHalfdayField = new PreciseDateTimeField(DateTimeFieldType.hourOfHalfday(), cHoursField, cHalfdaysField);
        cClockhourOfDayField = new ZeroIsMaxDateTimeField(cHourOfDayField, DateTimeFieldType.clockhourOfDay());
        cClockhourOfHalfdayField = new ZeroIsMaxDateTimeField(cHourOfHalfdayField, DateTimeFieldType.clockhourOfHalfday());
        cHalfdayOfDayField = new HalfdayField();
    }

    BasicChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj);
        this.iYearInfoCache = new YearInfo[CACHE_SIZE];
        if (i < 1 || i > 7) {
            throw new IllegalArgumentException("Invalid min days in first week: " + i);
        }
        this.iMinDaysInFirstWeek = i;
    }

    public DateTimeZone getZone() {
        Chronology base = getBase();
        if (base != null) {
            return base.getZone();
        }
        return DateTimeZone.UTC;
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfDay(), i4, 0, 86399999);
        return getDateMidnightMillis(i, i2, i3) + ((long) i4);
    }

    public long getDateTimeMillis(int i, int i2, int i3, int i4, int i5, int i6, int i7) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i, i2, i3, i4, i5, i6, i7);
        }
        FieldUtils.verifyValueBounds(DateTimeFieldType.hourOfDay(), i4, 0, 23);
        FieldUtils.verifyValueBounds(DateTimeFieldType.minuteOfHour(), i5, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.secondOfMinute(), i6, 0, 59);
        FieldUtils.verifyValueBounds(DateTimeFieldType.millisOfSecond(), i7, 0, 999);
        return (((getDateMidnightMillis(i, i2, i3) + ((long) (DateTimeConstants.MILLIS_PER_HOUR * i4))) + ((long) (DateTimeConstants.MILLIS_PER_MINUTE * i5))) + ((long) (i6 * DateTimeConstants.MILLIS_PER_SECOND))) + ((long) i7);
    }

    public int getMinimumDaysInFirstWeek() {
        return this.iMinDaysInFirstWeek;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BasicChronology basicChronology = (BasicChronology) obj;
        if (getMinimumDaysInFirstWeek() == basicChronology.getMinimumDaysInFirstWeek() && getZone().equals(basicChronology.getZone())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((getClass().getName().hashCode() * 11) + getZone().hashCode()) + getMinimumDaysInFirstWeek();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(60);
        String name = getClass().getName();
        int lastIndexOf = name.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            name = name.substring(lastIndexOf + 1);
        }
        stringBuilder.append(name);
        stringBuilder.append('[');
        DateTimeZone zone = getZone();
        if (zone != null) {
            stringBuilder.append(zone.getID());
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            stringBuilder.append(",mdfw=");
            stringBuilder.append(getMinimumDaysInFirstWeek());
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    protected void assemble(Fields fields) {
        fields.millis = cMillisField;
        fields.seconds = cSecondsField;
        fields.minutes = cMinutesField;
        fields.hours = cHoursField;
        fields.halfdays = cHalfdaysField;
        fields.days = cDaysField;
        fields.weeks = cWeeksField;
        fields.millisOfSecond = cMillisOfSecondField;
        fields.millisOfDay = cMillisOfDayField;
        fields.secondOfMinute = cSecondOfMinuteField;
        fields.secondOfDay = cSecondOfDayField;
        fields.minuteOfHour = cMinuteOfHourField;
        fields.minuteOfDay = cMinuteOfDayField;
        fields.hourOfDay = cHourOfDayField;
        fields.hourOfHalfday = cHourOfHalfdayField;
        fields.clockhourOfDay = cClockhourOfDayField;
        fields.clockhourOfHalfday = cClockhourOfHalfdayField;
        fields.halfdayOfDay = cHalfdayOfDayField;
        fields.year = new BasicYearDateTimeField(this);
        fields.yearOfEra = new GJYearOfEraDateTimeField(fields.year, this);
        fields.centuryOfEra = new DividedDateTimeField(new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
        fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra), DateTimeFieldType.yearOfCentury(), 1);
        fields.era = new GJEraDateTimeField(this);
        fields.dayOfWeek = new GJDayOfWeekDateTimeField(this, fields.days);
        fields.dayOfMonth = new BasicDayOfMonthDateTimeField(this, fields.days);
        fields.dayOfYear = new BasicDayOfYearDateTimeField(this, fields.days);
        fields.monthOfYear = new GJMonthOfYearDateTimeField(this);
        fields.weekyear = new BasicWeekyearDateTimeField(this);
        fields.weekOfWeekyear = new BasicWeekOfWeekyearDateTimeField(this, fields.weeks);
        fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), 1);
        fields.years = fields.year.getDurationField();
        fields.centuries = fields.centuryOfEra.getDurationField();
        fields.months = fields.monthOfYear.getDurationField();
        fields.weekyears = fields.weekyear.getDurationField();
    }

    int getDaysInYearMax() {
        return 366;
    }

    int getDaysInYear(int i) {
        return isLeapYear(i) ? 366 : 365;
    }

    int getWeeksInYear(int i) {
        return (int) ((getFirstWeekOfYearMillis(i + 1) - getFirstWeekOfYearMillis(i)) / 604800000);
    }

    long getFirstWeekOfYearMillis(int i) {
        long yearMillis = getYearMillis(i);
        int dayOfWeek = getDayOfWeek(yearMillis);
        if (dayOfWeek > 8 - this.iMinDaysInFirstWeek) {
            return yearMillis + (((long) (8 - dayOfWeek)) * DateUtils.MILLIS_PER_DAY);
        }
        return yearMillis - (((long) (dayOfWeek - 1)) * DateUtils.MILLIS_PER_DAY);
    }

    long getYearMillis(int i) {
        return getYearInfo(i).iFirstDayMillis;
    }

    long getYearMonthMillis(int i, int i2) {
        return getYearMillis(i) + getTotalMillisByYearMonth(i, i2);
    }

    long getYearMonthDayMillis(int i, int i2, int i3) {
        return (getYearMillis(i) + getTotalMillisByYearMonth(i, i2)) + (((long) (i3 - 1)) * DateUtils.MILLIS_PER_DAY);
    }

    int getYear(long j) {
        long averageMillisPerYearDividedByTwo = getAverageMillisPerYearDividedByTwo();
        long approxMillisAtEpochDividedByTwo = (j >> 1) + getApproxMillisAtEpochDividedByTwo();
        if (approxMillisAtEpochDividedByTwo < 0) {
            approxMillisAtEpochDividedByTwo = (approxMillisAtEpochDividedByTwo - averageMillisPerYearDividedByTwo) + 1;
        }
        int i = (int) (approxMillisAtEpochDividedByTwo / averageMillisPerYearDividedByTwo);
        averageMillisPerYearDividedByTwo = getYearMillis(i);
        long j2 = j - averageMillisPerYearDividedByTwo;
        if (j2 < 0) {
            return i - 1;
        }
        if (j2 < 31536000000L) {
            return i;
        }
        long j3;
        if (isLeapYear(i)) {
            j3 = 31622400000L;
        } else {
            j3 = 31536000000L;
        }
        if (j3 + averageMillisPerYearDividedByTwo <= j) {
            return i + 1;
        }
        return i;
    }

    int getMonthOfYear(long j) {
        return getMonthOfYear(j, getYear(j));
    }

    int getDayOfMonth(long j) {
        int year = getYear(j);
        return getDayOfMonth(j, year, getMonthOfYear(j, year));
    }

    int getDayOfMonth(long j, int i) {
        return getDayOfMonth(j, i, getMonthOfYear(j, i));
    }

    int getDayOfMonth(long j, int i, int i2) {
        return ((int) ((j - (getYearMillis(i) + getTotalMillisByYearMonth(i, i2))) / DateUtils.MILLIS_PER_DAY)) + 1;
    }

    int getDayOfYear(long j) {
        return getDayOfYear(j, getYear(j));
    }

    int getDayOfYear(long j, int i) {
        return ((int) ((j - getYearMillis(i)) / DateUtils.MILLIS_PER_DAY)) + 1;
    }

    int getWeekyear(long j) {
        int year = getYear(j);
        int weekOfWeekyear = getWeekOfWeekyear(j, year);
        if (weekOfWeekyear == 1) {
            return getYear(604800000 + j);
        }
        if (weekOfWeekyear > 51) {
            return getYear(j - 1209600000);
        }
        return year;
    }

    int getWeekOfWeekyear(long j) {
        return getWeekOfWeekyear(j, getYear(j));
    }

    int getWeekOfWeekyear(long j, int i) {
        long firstWeekOfYearMillis = getFirstWeekOfYearMillis(i);
        if (j < firstWeekOfYearMillis) {
            return getWeeksInYear(i - 1);
        }
        if (j >= getFirstWeekOfYearMillis(i + 1)) {
            return 1;
        }
        return ((int) ((j - firstWeekOfYearMillis) / 604800000)) + 1;
    }

    int getDayOfWeek(long j) {
        long j2;
        if (j >= 0) {
            j2 = j / DateUtils.MILLIS_PER_DAY;
        } else {
            j2 = (j - 86399999) / DateUtils.MILLIS_PER_DAY;
            if (j2 < -3) {
                return ((int) ((j2 + 4) % 7)) + 7;
            }
        }
        return ((int) ((j2 + 3) % 7)) + 1;
    }

    int getMillisOfDay(long j) {
        if (j >= 0) {
            return (int) (j % DateUtils.MILLIS_PER_DAY);
        }
        return 86399999 + ((int) ((1 + j) % DateUtils.MILLIS_PER_DAY));
    }

    int getDaysInMonthMax() {
        return 31;
    }

    int getDaysInMonthMax(long j) {
        int year = getYear(j);
        return getDaysInYearMonth(year, getMonthOfYear(j, year));
    }

    int getDaysInMonthMaxForSet(long j, int i) {
        return getDaysInMonthMax(j);
    }

    long getDateMidnightMillis(int i, int i2, int i3) {
        FieldUtils.verifyValueBounds(DateTimeFieldType.year(), i, getMinYear(), getMaxYear());
        FieldUtils.verifyValueBounds(DateTimeFieldType.monthOfYear(), i2, 1, getMaxMonth(i));
        FieldUtils.verifyValueBounds(DateTimeFieldType.dayOfMonth(), i3, 1, getDaysInYearMonth(i, i2));
        return getYearMonthDayMillis(i, i2, i3);
    }

    int getMaxMonth(int i) {
        return getMaxMonth();
    }

    int getMaxMonth() {
        return 12;
    }

    private YearInfo getYearInfo(int i) {
        YearInfo yearInfo = this.iYearInfoCache[i & CACHE_MASK];
        if (yearInfo != null && yearInfo.iYear == i) {
            return yearInfo;
        }
        yearInfo = new YearInfo(i, calculateFirstDayOfYearMillis(i));
        this.iYearInfoCache[i & CACHE_MASK] = yearInfo;
        return yearInfo;
    }
}
