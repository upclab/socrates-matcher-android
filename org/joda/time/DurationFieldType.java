package org.joda.time;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.io.Serializable;

public abstract class DurationFieldType implements Serializable {
    static final byte CENTURIES = (byte) 2;
    static final DurationFieldType CENTURIES_TYPE;
    static final byte DAYS = (byte) 7;
    static final DurationFieldType DAYS_TYPE;
    static final byte ERAS = (byte) 1;
    static final DurationFieldType ERAS_TYPE;
    static final byte HALFDAYS = (byte) 8;
    static final DurationFieldType HALFDAYS_TYPE;
    static final byte HOURS = (byte) 9;
    static final DurationFieldType HOURS_TYPE;
    static final byte MILLIS = (byte) 12;
    static final DurationFieldType MILLIS_TYPE;
    static final byte MINUTES = (byte) 10;
    static final DurationFieldType MINUTES_TYPE;
    static final byte MONTHS = (byte) 5;
    static final DurationFieldType MONTHS_TYPE;
    static final byte SECONDS = (byte) 11;
    static final DurationFieldType SECONDS_TYPE;
    static final byte WEEKS = (byte) 6;
    static final DurationFieldType WEEKS_TYPE;
    static final byte WEEKYEARS = (byte) 3;
    static final DurationFieldType WEEKYEARS_TYPE;
    static final byte YEARS = (byte) 4;
    static final DurationFieldType YEARS_TYPE;
    private static final long serialVersionUID = 8765135187319L;
    private final String iName;

    private static class StandardDurationFieldType extends DurationFieldType {
        private static final long serialVersionUID = 31156755687123L;
        private final byte iOrdinal;

        StandardDurationFieldType(String str, byte b) {
            super(str);
            this.iOrdinal = b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StandardDurationFieldType)) {
                return false;
            }
            if (this.iOrdinal != ((StandardDurationFieldType) obj).iOrdinal) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 1 << this.iOrdinal;
        }

        public DurationField getField(Chronology chronology) {
            Chronology chronology2 = DateTimeUtils.getChronology(chronology);
            switch (this.iOrdinal) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return chronology2.eras();
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return chronology2.centuries();
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return chronology2.weekyears();
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return chronology2.years();
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return chronology2.months();
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return chronology2.weeks();
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return chronology2.days();
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return chronology2.halfdays();
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    return chronology2.hours();
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    return chronology2.minutes();
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    return chronology2.seconds();
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    return chronology2.millis();
                default:
                    throw new InternalError();
            }
        }

        private Object readResolve() {
            switch (this.iOrdinal) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    return ERAS_TYPE;
                case Value.STRING_FIELD_NUMBER /*2*/:
                    return CENTURIES_TYPE;
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    return WEEKYEARS_TYPE;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                    return YEARS_TYPE;
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    return MONTHS_TYPE;
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    return WEEKS_TYPE;
                case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                    return DAYS_TYPE;
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    return HALFDAYS_TYPE;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    return HOURS_TYPE;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    return MINUTES_TYPE;
                case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                    return SECONDS_TYPE;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    return MILLIS_TYPE;
                default:
                    return this;
            }
        }
    }

    public abstract DurationField getField(Chronology chronology);

    static {
        ERAS_TYPE = new StandardDurationFieldType("eras", ERAS);
        CENTURIES_TYPE = new StandardDurationFieldType("centuries", CENTURIES);
        WEEKYEARS_TYPE = new StandardDurationFieldType("weekyears", WEEKYEARS);
        YEARS_TYPE = new StandardDurationFieldType("years", YEARS);
        MONTHS_TYPE = new StandardDurationFieldType("months", MONTHS);
        WEEKS_TYPE = new StandardDurationFieldType("weeks", WEEKS);
        DAYS_TYPE = new StandardDurationFieldType("days", DAYS);
        HALFDAYS_TYPE = new StandardDurationFieldType("halfdays", HALFDAYS);
        HOURS_TYPE = new StandardDurationFieldType("hours", HOURS);
        MINUTES_TYPE = new StandardDurationFieldType("minutes", MINUTES);
        SECONDS_TYPE = new StandardDurationFieldType("seconds", SECONDS);
        MILLIS_TYPE = new StandardDurationFieldType("millis", MILLIS);
    }

    protected DurationFieldType(String str) {
        this.iName = str;
    }

    public static DurationFieldType millis() {
        return MILLIS_TYPE;
    }

    public static DurationFieldType seconds() {
        return SECONDS_TYPE;
    }

    public static DurationFieldType minutes() {
        return MINUTES_TYPE;
    }

    public static DurationFieldType hours() {
        return HOURS_TYPE;
    }

    public static DurationFieldType halfdays() {
        return HALFDAYS_TYPE;
    }

    public static DurationFieldType days() {
        return DAYS_TYPE;
    }

    public static DurationFieldType weeks() {
        return WEEKS_TYPE;
    }

    public static DurationFieldType weekyears() {
        return WEEKYEARS_TYPE;
    }

    public static DurationFieldType months() {
        return MONTHS_TYPE;
    }

    public static DurationFieldType years() {
        return YEARS_TYPE;
    }

    public static DurationFieldType centuries() {
        return CENTURIES_TYPE;
    }

    public static DurationFieldType eras() {
        return ERAS_TYPE;
    }

    public String getName() {
        return this.iName;
    }

    public boolean isSupported(Chronology chronology) {
        return getField(chronology).isSupported();
    }

    public String toString() {
        return getName();
    }
}
