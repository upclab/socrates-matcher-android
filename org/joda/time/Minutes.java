package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

public final class Minutes extends BaseSingleFieldPeriod {
    public static final Minutes MAX_VALUE;
    public static final Minutes MIN_VALUE;
    public static final Minutes ONE;
    private static final PeriodFormatter PARSER;
    public static final Minutes THREE;
    public static final Minutes TWO;
    public static final Minutes ZERO;
    private static final long serialVersionUID = 87525275727380863L;

    static {
        ZERO = new Minutes(0);
        ONE = new Minutes(1);
        TWO = new Minutes(2);
        THREE = new Minutes(3);
        MAX_VALUE = new Minutes(Integer.MAX_VALUE);
        MIN_VALUE = new Minutes(Integer.MIN_VALUE);
        PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.minutes());
    }

    public static Minutes minutes(int i) {
        switch (i) {
            case Integer.MIN_VALUE:
                return MIN_VALUE;
            case MutableDateTime.ROUND_NONE /*0*/:
                return ZERO;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return ONE;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return TWO;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                return THREE;
            case Integer.MAX_VALUE:
                return MAX_VALUE;
            default:
                return new Minutes(i);
        }
    }

    public static Minutes minutesBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return minutes(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.minutes()));
    }

    public static Minutes minutesBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalTime) && (readablePartial2 instanceof LocalTime)) {
            return minutes(DateTimeUtils.getChronology(readablePartial.getChronology()).minutes().getDifference(((LocalTime) readablePartial2).getLocalMillis(), ((LocalTime) readablePartial).getLocalMillis()));
        }
        return minutes(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }

    public static Minutes minutesIn(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ZERO;
        }
        return minutes(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.minutes()));
    }

    public static Minutes standardMinutesIn(ReadablePeriod readablePeriod) {
        return minutes(BaseSingleFieldPeriod.standardPeriodIn(readablePeriod, DateUtils.MILLIS_PER_MINUTE));
    }

    @FromString
    public static Minutes parseMinutes(String str) {
        if (str == null) {
            return ZERO;
        }
        return minutes(PARSER.parsePeriod(str).getMinutes());
    }

    private Minutes(int i) {
        super(i);
    }

    private Object readResolve() {
        return minutes(getValue());
    }

    public DurationFieldType getFieldType() {
        return DurationFieldType.minutes();
    }

    public PeriodType getPeriodType() {
        return PeriodType.minutes();
    }

    public Weeks toStandardWeeks() {
        return Weeks.weeks(getValue() / DateTimeConstants.MINUTES_PER_WEEK);
    }

    public Days toStandardDays() {
        return Days.days(getValue() / DateTimeConstants.MINUTES_PER_DAY);
    }

    public Hours toStandardHours() {
        return Hours.hours(getValue() / 60);
    }

    public Seconds toStandardSeconds() {
        return Seconds.seconds(FieldUtils.safeMultiply(getValue(), 60));
    }

    public Duration toStandardDuration() {
        return new Duration(((long) getValue()) * DateUtils.MILLIS_PER_MINUTE);
    }

    public int getMinutes() {
        return getValue();
    }

    public Minutes plus(int i) {
        return i == 0 ? this : minutes(FieldUtils.safeAdd(getValue(), i));
    }

    public Minutes plus(Minutes minutes) {
        return minutes == null ? this : plus(minutes.getValue());
    }

    public Minutes minus(int i) {
        return plus(FieldUtils.safeNegate(i));
    }

    public Minutes minus(Minutes minutes) {
        return minutes == null ? this : minus(minutes.getValue());
    }

    public Minutes multipliedBy(int i) {
        return minutes(FieldUtils.safeMultiply(getValue(), i));
    }

    public Minutes dividedBy(int i) {
        return i == 1 ? this : minutes(getValue() / i);
    }

    public Minutes negated() {
        return minutes(FieldUtils.safeNegate(getValue()));
    }

    public boolean isGreaterThan(Minutes minutes) {
        if (minutes == null) {
            if (getValue() > 0) {
                return true;
            }
            return false;
        } else if (getValue() <= minutes.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLessThan(Minutes minutes) {
        if (minutes == null) {
            if (getValue() < 0) {
                return true;
            }
            return false;
        } else if (getValue() >= minutes.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    @ToString
    public String toString() {
        return "PT" + String.valueOf(getValue()) + "M";
    }
}
