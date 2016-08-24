package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

public final class Hours extends BaseSingleFieldPeriod {
    public static final Hours EIGHT;
    public static final Hours FIVE;
    public static final Hours FOUR;
    public static final Hours MAX_VALUE;
    public static final Hours MIN_VALUE;
    public static final Hours ONE;
    private static final PeriodFormatter PARSER;
    public static final Hours SEVEN;
    public static final Hours SIX;
    public static final Hours THREE;
    public static final Hours TWO;
    public static final Hours ZERO;
    private static final long serialVersionUID = 87525275727380864L;

    static {
        ZERO = new Hours(0);
        ONE = new Hours(1);
        TWO = new Hours(2);
        THREE = new Hours(3);
        FOUR = new Hours(4);
        FIVE = new Hours(5);
        SIX = new Hours(6);
        SEVEN = new Hours(7);
        EIGHT = new Hours(8);
        MAX_VALUE = new Hours(Integer.MAX_VALUE);
        MIN_VALUE = new Hours(Integer.MIN_VALUE);
        PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.hours());
    }

    public static Hours hours(int i) {
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
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return FOUR;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                return FIVE;
            case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                return SIX;
            case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                return SEVEN;
            case Value.INTEGER_FIELD_NUMBER /*8*/:
                return EIGHT;
            case Integer.MAX_VALUE:
                return MAX_VALUE;
            default:
                return new Hours(i);
        }
    }

    public static Hours hoursBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return hours(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.hours()));
    }

    public static Hours hoursBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalTime) && (readablePartial2 instanceof LocalTime)) {
            return hours(DateTimeUtils.getChronology(readablePartial.getChronology()).hours().getDifference(((LocalTime) readablePartial2).getLocalMillis(), ((LocalTime) readablePartial).getLocalMillis()));
        }
        return hours(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }

    public static Hours hoursIn(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ZERO;
        }
        return hours(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.hours()));
    }

    public static Hours standardHoursIn(ReadablePeriod readablePeriod) {
        return hours(BaseSingleFieldPeriod.standardPeriodIn(readablePeriod, DateUtils.MILLIS_PER_HOUR));
    }

    @FromString
    public static Hours parseHours(String str) {
        if (str == null) {
            return ZERO;
        }
        return hours(PARSER.parsePeriod(str).getHours());
    }

    private Hours(int i) {
        super(i);
    }

    private Object readResolve() {
        return hours(getValue());
    }

    public DurationFieldType getFieldType() {
        return DurationFieldType.hours();
    }

    public PeriodType getPeriodType() {
        return PeriodType.hours();
    }

    public Weeks toStandardWeeks() {
        return Weeks.weeks(getValue() / DateTimeConstants.HOURS_PER_WEEK);
    }

    public Days toStandardDays() {
        return Days.days(getValue() / 24);
    }

    public Minutes toStandardMinutes() {
        return Minutes.minutes(FieldUtils.safeMultiply(getValue(), 60));
    }

    public Seconds toStandardSeconds() {
        return Seconds.seconds(FieldUtils.safeMultiply(getValue(), (int) DateTimeConstants.SECONDS_PER_HOUR));
    }

    public Duration toStandardDuration() {
        return new Duration(((long) getValue()) * DateUtils.MILLIS_PER_HOUR);
    }

    public int getHours() {
        return getValue();
    }

    public Hours plus(int i) {
        return i == 0 ? this : hours(FieldUtils.safeAdd(getValue(), i));
    }

    public Hours plus(Hours hours) {
        return hours == null ? this : plus(hours.getValue());
    }

    public Hours minus(int i) {
        return plus(FieldUtils.safeNegate(i));
    }

    public Hours minus(Hours hours) {
        return hours == null ? this : minus(hours.getValue());
    }

    public Hours multipliedBy(int i) {
        return hours(FieldUtils.safeMultiply(getValue(), i));
    }

    public Hours dividedBy(int i) {
        return i == 1 ? this : hours(getValue() / i);
    }

    public Hours negated() {
        return hours(FieldUtils.safeNegate(getValue()));
    }

    public boolean isGreaterThan(Hours hours) {
        if (hours == null) {
            if (getValue() > 0) {
                return true;
            }
            return false;
        } else if (getValue() <= hours.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLessThan(Hours hours) {
        if (hours == null) {
            if (getValue() < 0) {
                return true;
            }
            return false;
        } else if (getValue() >= hours.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    @ToString
    public String toString() {
        return "PT" + String.valueOf(getValue()) + "H";
    }
}
