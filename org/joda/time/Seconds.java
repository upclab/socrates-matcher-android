package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

public final class Seconds extends BaseSingleFieldPeriod {
    public static final Seconds MAX_VALUE;
    public static final Seconds MIN_VALUE;
    public static final Seconds ONE;
    private static final PeriodFormatter PARSER;
    public static final Seconds THREE;
    public static final Seconds TWO;
    public static final Seconds ZERO;
    private static final long serialVersionUID = 87525275727380862L;

    static {
        ZERO = new Seconds(0);
        ONE = new Seconds(1);
        TWO = new Seconds(2);
        THREE = new Seconds(3);
        MAX_VALUE = new Seconds(Integer.MAX_VALUE);
        MIN_VALUE = new Seconds(Integer.MIN_VALUE);
        PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.seconds());
    }

    public static Seconds seconds(int i) {
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
                return new Seconds(i);
        }
    }

    public static Seconds secondsBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return seconds(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.seconds()));
    }

    public static Seconds secondsBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalTime) && (readablePartial2 instanceof LocalTime)) {
            return seconds(DateTimeUtils.getChronology(readablePartial.getChronology()).seconds().getDifference(((LocalTime) readablePartial2).getLocalMillis(), ((LocalTime) readablePartial).getLocalMillis()));
        }
        return seconds(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }

    public static Seconds secondsIn(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ZERO;
        }
        return seconds(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.seconds()));
    }

    public static Seconds standardSecondsIn(ReadablePeriod readablePeriod) {
        return seconds(BaseSingleFieldPeriod.standardPeriodIn(readablePeriod, 1000));
    }

    @FromString
    public static Seconds parseSeconds(String str) {
        if (str == null) {
            return ZERO;
        }
        return seconds(PARSER.parsePeriod(str).getSeconds());
    }

    private Seconds(int i) {
        super(i);
    }

    private Object readResolve() {
        return seconds(getValue());
    }

    public DurationFieldType getFieldType() {
        return DurationFieldType.seconds();
    }

    public PeriodType getPeriodType() {
        return PeriodType.seconds();
    }

    public Weeks toStandardWeeks() {
        return Weeks.weeks(getValue() / DateTimeConstants.SECONDS_PER_WEEK);
    }

    public Days toStandardDays() {
        return Days.days(getValue() / DateTimeConstants.SECONDS_PER_DAY);
    }

    public Hours toStandardHours() {
        return Hours.hours(getValue() / DateTimeConstants.SECONDS_PER_HOUR);
    }

    public Minutes toStandardMinutes() {
        return Minutes.minutes(getValue() / 60);
    }

    public Duration toStandardDuration() {
        return new Duration(((long) getValue()) * 1000);
    }

    public int getSeconds() {
        return getValue();
    }

    public Seconds plus(int i) {
        return i == 0 ? this : seconds(FieldUtils.safeAdd(getValue(), i));
    }

    public Seconds plus(Seconds seconds) {
        return seconds == null ? this : plus(seconds.getValue());
    }

    public Seconds minus(int i) {
        return plus(FieldUtils.safeNegate(i));
    }

    public Seconds minus(Seconds seconds) {
        return seconds == null ? this : minus(seconds.getValue());
    }

    public Seconds multipliedBy(int i) {
        return seconds(FieldUtils.safeMultiply(getValue(), i));
    }

    public Seconds dividedBy(int i) {
        return i == 1 ? this : seconds(getValue() / i);
    }

    public Seconds negated() {
        return seconds(FieldUtils.safeNegate(getValue()));
    }

    public boolean isGreaterThan(Seconds seconds) {
        if (seconds == null) {
            if (getValue() > 0) {
                return true;
            }
            return false;
        } else if (getValue() <= seconds.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLessThan(Seconds seconds) {
        if (seconds == null) {
            if (getValue() < 0) {
                return true;
            }
            return false;
        } else if (getValue() >= seconds.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    @ToString
    public String toString() {
        return "PT" + String.valueOf(getValue()) + "S";
    }
}
