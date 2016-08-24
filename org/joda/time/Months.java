package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseSingleFieldPeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

public final class Months extends BaseSingleFieldPeriod {
    public static final Months EIGHT;
    public static final Months ELEVEN;
    public static final Months FIVE;
    public static final Months FOUR;
    public static final Months MAX_VALUE;
    public static final Months MIN_VALUE;
    public static final Months NINE;
    public static final Months ONE;
    private static final PeriodFormatter PARSER;
    public static final Months SEVEN;
    public static final Months SIX;
    public static final Months TEN;
    public static final Months THREE;
    public static final Months TWELVE;
    public static final Months TWO;
    public static final Months ZERO;
    private static final long serialVersionUID = 87525275727380867L;

    static {
        ZERO = new Months(0);
        ONE = new Months(1);
        TWO = new Months(2);
        THREE = new Months(3);
        FOUR = new Months(4);
        FIVE = new Months(5);
        SIX = new Months(6);
        SEVEN = new Months(7);
        EIGHT = new Months(8);
        NINE = new Months(9);
        TEN = new Months(10);
        ELEVEN = new Months(11);
        TWELVE = new Months(12);
        MAX_VALUE = new Months(Integer.MAX_VALUE);
        MIN_VALUE = new Months(Integer.MIN_VALUE);
        PARSER = ISOPeriodFormat.standard().withParseType(PeriodType.months());
    }

    public static Months months(int i) {
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
            case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                return NINE;
            case Value.ESCAPING_FIELD_NUMBER /*10*/:
                return TEN;
            case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                return ELEVEN;
            case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                return TWELVE;
            case Integer.MAX_VALUE:
                return MAX_VALUE;
            default:
                return new Months(i);
        }
    }

    public static Months monthsBetween(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        return months(BaseSingleFieldPeriod.between(readableInstant, readableInstant2, DurationFieldType.months()));
    }

    public static Months monthsBetween(ReadablePartial readablePartial, ReadablePartial readablePartial2) {
        if ((readablePartial instanceof LocalDate) && (readablePartial2 instanceof LocalDate)) {
            return months(DateTimeUtils.getChronology(readablePartial.getChronology()).months().getDifference(((LocalDate) readablePartial2).getLocalMillis(), ((LocalDate) readablePartial).getLocalMillis()));
        }
        return months(BaseSingleFieldPeriod.between(readablePartial, readablePartial2, ZERO));
    }

    public static Months monthsIn(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            return ZERO;
        }
        return months(BaseSingleFieldPeriod.between(readableInterval.getStart(), readableInterval.getEnd(), DurationFieldType.months()));
    }

    @FromString
    public static Months parseMonths(String str) {
        if (str == null) {
            return ZERO;
        }
        return months(PARSER.parsePeriod(str).getMonths());
    }

    private Months(int i) {
        super(i);
    }

    private Object readResolve() {
        return months(getValue());
    }

    public DurationFieldType getFieldType() {
        return DurationFieldType.months();
    }

    public PeriodType getPeriodType() {
        return PeriodType.months();
    }

    public int getMonths() {
        return getValue();
    }

    public Months plus(int i) {
        return i == 0 ? this : months(FieldUtils.safeAdd(getValue(), i));
    }

    public Months plus(Months months) {
        return months == null ? this : plus(months.getValue());
    }

    public Months minus(int i) {
        return plus(FieldUtils.safeNegate(i));
    }

    public Months minus(Months months) {
        return months == null ? this : minus(months.getValue());
    }

    public Months multipliedBy(int i) {
        return months(FieldUtils.safeMultiply(getValue(), i));
    }

    public Months dividedBy(int i) {
        return i == 1 ? this : months(getValue() / i);
    }

    public Months negated() {
        return months(FieldUtils.safeNegate(getValue()));
    }

    public boolean isGreaterThan(Months months) {
        if (months == null) {
            if (getValue() > 0) {
                return true;
            }
            return false;
        } else if (getValue() <= months.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isLessThan(Months months) {
        if (months == null) {
            if (getValue() < 0) {
                return true;
            }
            return false;
        } else if (getValue() >= months.getValue()) {
            return false;
        } else {
            return true;
        }
    }

    @ToString
    public String toString() {
        return "P" + String.valueOf(getValue()) + "M";
    }
}
