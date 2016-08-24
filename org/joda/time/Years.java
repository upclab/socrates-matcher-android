package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

public final class Years extends BaseSingleFieldPeriod {
    public static final Years MAX_VALUE;
    public static final Years MIN_VALUE;
    public static final Years ONE;
    private static final PeriodFormatter PARSER;
    public static final Years THREE;
    public static final Years TWO;
    public static final Years ZERO;
    private static final long serialVersionUID = 87525275727380868L;

    static {
        ZERO = new Years(0);
        ONE = new Years(1);
        TWO = new Years(2);
        THREE = new Years(3);
        MAX_VALUE = new Years(Integer.MAX_VALUE);
        MIN_VALUE = new Years(Integer.MIN_VALUE);
        PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.years());
    }

    public static Years years(int i) {
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
                return new Years(i);
        }
    }

    public static Years yearsBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return years(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.years()));
    }

    public static Years yearsBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalDate) && (readablePartial2 instanceof LocalDate)) {
            return years(DateTimeUtils.getChronology(readablePartial.getChronology()).years().getDifference(((LocalDate) readablePartial2).getLocalMillis(), ((LocalDate) readablePartial).getLocalMillis()));
        }
        return years(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }

    public static Years yearsIn(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ZERO;
        }
        return years(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.years()));
    }

    @FromString
    public static Years parseYears(String str) {
        if (str == null) {
            return ZERO;
        }
        return years(PARSER.parsePeriod(str).getYears());
    }

    private Years(int i) {
        super(i);
    }

    private Object readResolve() {
        return years(getValue());
    }

    public DurationFieldType getFieldType() {
        return DurationFieldType.years();
    }

    public PeriodType getPeriodType() {
        return PeriodType.years();
    }

    public int getYears() {
        return getValue();
    }

    public Years plus(int i) {
        return i == 0 ? this : years(FieldUtils.safeAdd(getValue(), i));
    }

    public Years plus(Years years) {
        return years == null ? this : plus(years.getValue());
    }

    public Years minus(int i) {
        return plus(FieldUtils.safeNegate(i));
    }

    public Years minus(Years years) {
        return years == null ? this : minus(years.getValue());
    }

    public Years multipliedBy(int i) {
        return years(FieldUtils.safeMultiply(getValue(), i));
    }

    public Years dividedBy(int i) {
        return i == 1 ? this : years(getValue() / i);
    }

    public Years negated() {
        return years(FieldUtils.safeNegate(getValue()));
    }

    public boolean isGreaterThan(Years years) {
        if (years == null) {
            if (getValue() > 0) {
                return true;
            }
            return false;
        } else if (getValue() <= years.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLessThan(Years years) {
        if (years == null) {
            if (getValue() < 0) {
                return true;
            }
            return false;
        } else if (getValue() >= years.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    @ToString
    public String toString() {
        return "P" + String.valueOf(getValue()) + "Y";
    }
}
