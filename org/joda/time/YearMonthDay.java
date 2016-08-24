package org.joda.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.time.base.BasePartial;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractPartialFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISODateTimeFormat;

@Deprecated
public final class YearMonthDay extends BasePartial implements ReadablePartial, Serializable {
    public static final int DAY_OF_MONTH = 2;
    private static final DateTimeFieldType[] FIELD_TYPES;
    public static final int MONTH_OF_YEAR = 1;
    public static final int YEAR = 0;
    private static final long serialVersionUID = 797544782896179L;

    @Deprecated
    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 5727734012190224363L;
        private final int iFieldIndex;
        private final YearMonthDay iYearMonthDay;

        Property(YearMonthDay yearMonthDay, int i) {
            this.iYearMonthDay = yearMonthDay;
            this.iFieldIndex = i;
        }

        public DateTimeField getField() {
            return this.iYearMonthDay.getField(this.iFieldIndex);
        }

        protected ReadablePartial getReadablePartial() {
            return this.iYearMonthDay;
        }

        public YearMonthDay getYearMonthDay() {
            return this.iYearMonthDay;
        }

        public int get() {
            return this.iYearMonthDay.getValue(this.iFieldIndex);
        }

        public YearMonthDay addToCopy(int i) {
            return new YearMonthDay(this.iYearMonthDay, getField().add(this.iYearMonthDay, this.iFieldIndex, this.iYearMonthDay.getValues(), i));
        }

        public YearMonthDay addWrapFieldToCopy(int i) {
            return new YearMonthDay(this.iYearMonthDay, getField().addWrapField(this.iYearMonthDay, this.iFieldIndex, this.iYearMonthDay.getValues(), i));
        }

        public YearMonthDay setCopy(int i) {
            return new YearMonthDay(this.iYearMonthDay, getField().set(this.iYearMonthDay, this.iFieldIndex, this.iYearMonthDay.getValues(), i));
        }

        public YearMonthDay setCopy(String str, Locale locale) {
            return new YearMonthDay(this.iYearMonthDay, getField().set(this.iYearMonthDay, this.iFieldIndex, this.iYearMonthDay.getValues(), str, locale));
        }

        public YearMonthDay setCopy(String str) {
            return setCopy(str, null);
        }

        public YearMonthDay withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public YearMonthDay withMinimumValue() {
            return setCopy(getMinimumValue());
        }
    }

    static {
        FIELD_TYPES = new DateTimeFieldType[]{DateTimeFieldType.year(), DateTimeFieldType.monthOfYear(), DateTimeFieldType.dayOfMonth()};
    }

    public static YearMonthDay fromCalendarFields(Calendar calendar) {
        if (calendar != null) {
            return new YearMonthDay(calendar.get(MONTH_OF_YEAR), calendar.get(DAY_OF_MONTH) + MONTH_OF_YEAR, calendar.get(5));
        }
        throw new IllegalArgumentException("The calendar must not be null");
    }

    public static YearMonthDay fromDateFields(Date date) {
        if (date != null) {
            return new YearMonthDay(date.getYear() + 1900, date.getMonth() + MONTH_OF_YEAR, date.getDate());
        }
        throw new IllegalArgumentException("The date must not be null");
    }

    public YearMonthDay(DateTimeZone dateTimeZone) {
        super(ISOChronology.getInstance(dateTimeZone));
    }

    public YearMonthDay(Chronology chronology) {
        super(chronology);
    }

    public YearMonthDay(long j) {
        super(j);
    }

    public YearMonthDay(long j, Chronology chronology) {
        super(j, chronology);
    }

    public YearMonthDay(Object obj) {
        super(obj, null, ISODateTimeFormat.dateOptionalTimeParser());
    }

    public YearMonthDay(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology), ISODateTimeFormat.dateOptionalTimeParser());
    }

    public YearMonthDay(int i, int i2, int i3) {
        this(i, i2, i3, null);
    }

    public YearMonthDay(int i, int i2, int i3, Chronology chronology) {
        super(new int[]{i, i2, i3}, chronology);
    }

    YearMonthDay(YearMonthDay yearMonthDay, int[] iArr) {
        super((BasePartial) yearMonthDay, iArr);
    }

    YearMonthDay(YearMonthDay yearMonthDay, Chronology chronology) {
        super((BasePartial) yearMonthDay, chronology);
    }

    public int size() {
        return 3;
    }

    protected DateTimeField getField(int i, Chronology chronology) {
        switch (i) {
            case YEAR /*0*/:
                return chronology.year();
            case MONTH_OF_YEAR /*1*/:
                return chronology.monthOfYear();
            case DAY_OF_MONTH /*2*/:
                return chronology.dayOfMonth();
            default:
                throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
    }

    public DateTimeFieldType getFieldType(int i) {
        return FIELD_TYPES[i];
    }

    public DateTimeFieldType[] getFieldTypes() {
        return (DateTimeFieldType[]) FIELD_TYPES.clone();
    }

    public YearMonthDay withChronologyRetainFields(Chronology chronology) {
        Chronology withUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (withUTC == getChronology()) {
            return this;
        }
        ReadablePartial yearMonthDay = new YearMonthDay(this, withUTC);
        withUTC.validate(yearMonthDay, getValues());
        return yearMonthDay;
    }

    public YearMonthDay withField(DateTimeFieldType dateTimeFieldType, int i) {
        int indexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i == getValue(indexOfSupported)) {
            return this;
        }
        return new YearMonthDay(this, getField(indexOfSupported).set(this, indexOfSupported, getValues(), i));
    }

    public YearMonthDay withFieldAdded(DurationFieldType durationFieldType, int i) {
        int indexOfSupported = indexOfSupported(durationFieldType);
        if (i == 0) {
            return this;
        }
        return new YearMonthDay(this, getField(indexOfSupported).add(this, indexOfSupported, getValues(), i));
    }

    public YearMonthDay withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        if (readablePeriod == null || i == 0) {
            return this;
        }
        int[] values = getValues();
        for (int i2 = YEAR; i2 < readablePeriod.size(); i2 += MONTH_OF_YEAR) {
            int indexOf = indexOf(readablePeriod.getFieldType(i2));
            if (indexOf >= 0) {
                values = getField(indexOf).add(this, indexOf, values, FieldUtils.safeMultiply(readablePeriod.getValue(i2), i));
            }
        }
        return new YearMonthDay(this, values);
    }

    public YearMonthDay plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, MONTH_OF_YEAR);
    }

    public YearMonthDay plusYears(int i) {
        return withFieldAdded(DurationFieldType.years(), i);
    }

    public YearMonthDay plusMonths(int i) {
        return withFieldAdded(DurationFieldType.months(), i);
    }

    public YearMonthDay plusDays(int i) {
        return withFieldAdded(DurationFieldType.days(), i);
    }

    public YearMonthDay minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public YearMonthDay minusYears(int i) {
        return withFieldAdded(DurationFieldType.years(), FieldUtils.safeNegate(i));
    }

    public YearMonthDay minusMonths(int i) {
        return withFieldAdded(DurationFieldType.months(), FieldUtils.safeNegate(i));
    }

    public YearMonthDay minusDays(int i) {
        return withFieldAdded(DurationFieldType.days(), FieldUtils.safeNegate(i));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getYear(), getMonthOfYear(), getDayOfMonth(), getChronology());
    }

    public DateTime toDateTimeAtMidnight() {
        return toDateTimeAtMidnight(null);
    }

    public DateTime toDateTimeAtMidnight(DateTimeZone dateTimeZone) {
        return new DateTime(getYear(), getMonthOfYear(), getDayOfMonth(), (int) YEAR, YEAR, YEAR, YEAR, getChronology().withZone(dateTimeZone));
    }

    public DateTime toDateTimeAtCurrentTime() {
        return toDateTimeAtCurrentTime(null);
    }

    public DateTime toDateTimeAtCurrentTime(DateTimeZone dateTimeZone) {
        Chronology withZone = getChronology().withZone(dateTimeZone);
        return new DateTime(withZone.set(this, DateTimeUtils.currentTimeMillis()), withZone);
    }

    public DateMidnight toDateMidnight() {
        return toDateMidnight(null);
    }

    public DateMidnight toDateMidnight(DateTimeZone dateTimeZone) {
        return new DateMidnight(getYear(), getMonthOfYear(), getDayOfMonth(), getChronology().withZone(dateTimeZone));
    }

    public DateTime toDateTime(TimeOfDay timeOfDay) {
        return toDateTime(timeOfDay, null);
    }

    public DateTime toDateTime(TimeOfDay timeOfDay, DateTimeZone dateTimeZone) {
        Chronology withZone = getChronology().withZone(dateTimeZone);
        long j = withZone.set(this, DateTimeUtils.currentTimeMillis());
        if (timeOfDay != null) {
            j = withZone.set(timeOfDay, j);
        }
        return new DateTime(j, withZone);
    }

    public Interval toInterval() {
        return toInterval(null);
    }

    public Interval toInterval(DateTimeZone dateTimeZone) {
        return toDateMidnight(DateTimeUtils.getZone(dateTimeZone)).toInterval();
    }

    public int getYear() {
        return getValue(YEAR);
    }

    public int getMonthOfYear() {
        return getValue(MONTH_OF_YEAR);
    }

    public int getDayOfMonth() {
        return getValue(DAY_OF_MONTH);
    }

    public YearMonthDay withYear(int i) {
        return new YearMonthDay(this, getChronology().year().set(this, YEAR, getValues(), i));
    }

    public YearMonthDay withMonthOfYear(int i) {
        return new YearMonthDay(this, getChronology().monthOfYear().set(this, MONTH_OF_YEAR, getValues(), i));
    }

    public YearMonthDay withDayOfMonth(int i) {
        return new YearMonthDay(this, getChronology().dayOfMonth().set(this, DAY_OF_MONTH, getValues(), i));
    }

    public Property year() {
        return new Property(this, YEAR);
    }

    public Property monthOfYear() {
        return new Property(this, MONTH_OF_YEAR);
    }

    public Property dayOfMonth() {
        return new Property(this, DAY_OF_MONTH);
    }

    public String toString() {
        return ISODateTimeFormat.yearMonthDay().print((ReadablePartial) this);
    }
}
