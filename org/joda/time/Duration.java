package org.joda.time;

import java.io.Serializable;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.convert.FromString;
import org.joda.time.base.BaseDuration;
import org.joda.time.field.FieldUtils;

public final class Duration extends BaseDuration implements ReadableDuration, Serializable {
    public static final Duration ZERO;
    private static final long serialVersionUID = 2471658376918L;

    static {
        ZERO = new Duration(0);
    }

    @FromString
    public static Duration parse(String str) {
        return new Duration((Object) str);
    }

    public static Duration standardDays(long j) {
        if (j == 0) {
            return ZERO;
        }
        return new Duration(FieldUtils.safeMultiply(j, (int) DateTimeConstants.MILLIS_PER_DAY));
    }

    public static Duration standardHours(long j) {
        if (j == 0) {
            return ZERO;
        }
        return new Duration(FieldUtils.safeMultiply(j, (int) DateTimeConstants.MILLIS_PER_HOUR));
    }

    public static Duration standardMinutes(long j) {
        if (j == 0) {
            return ZERO;
        }
        return new Duration(FieldUtils.safeMultiply(j, (int) DateTimeConstants.MILLIS_PER_MINUTE));
    }

    public static Duration standardSeconds(long j) {
        if (j == 0) {
            return ZERO;
        }
        return new Duration(FieldUtils.safeMultiply(j, (int) DateTimeConstants.MILLIS_PER_SECOND));
    }

    public static Duration millis(long j) {
        if (j == 0) {
            return ZERO;
        }
        return new Duration(j);
    }

    public Duration(long j) {
        super(j);
    }

    public Duration(long j, long j2) {
        super(j, j2);
    }

    public Duration(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        super(readableInstant, readableInstant2);
    }

    public Duration(Object obj) {
        super(obj);
    }

    public long getStandardDays() {
        return getMillis() / DateUtils.MILLIS_PER_DAY;
    }

    public long getStandardHours() {
        return getMillis() / DateUtils.MILLIS_PER_HOUR;
    }

    public long getStandardMinutes() {
        return getMillis() / DateUtils.MILLIS_PER_MINUTE;
    }

    public long getStandardSeconds() {
        return getMillis() / 1000;
    }

    public Duration toDuration() {
        return this;
    }

    public Days toStandardDays() {
        return Days.days(FieldUtils.safeToInt(getStandardDays()));
    }

    public Hours toStandardHours() {
        return Hours.hours(FieldUtils.safeToInt(getStandardHours()));
    }

    public Minutes toStandardMinutes() {
        return Minutes.minutes(FieldUtils.safeToInt(getStandardMinutes()));
    }

    public Seconds toStandardSeconds() {
        return Seconds.seconds(FieldUtils.safeToInt(getStandardSeconds()));
    }

    public Duration withMillis(long j) {
        if (j == getMillis()) {
            return this;
        }
        this(j);
        return this;
    }

    public Duration withDurationAdded(long j, int i) {
        if (j == 0 || i == 0) {
            return this;
        }
        this(FieldUtils.safeAdd(getMillis(), FieldUtils.safeMultiply(j, i)));
        return this;
    }

    public Duration withDurationAdded(ReadableDuration readableDuration, int i) {
        return (readableDuration == null || i == 0) ? this : withDurationAdded(readableDuration.getMillis(), i);
    }

    public Duration plus(long j) {
        return withDurationAdded(j, 1);
    }

    public Duration plus(ReadableDuration readableDuration) {
        return readableDuration == null ? this : withDurationAdded(readableDuration.getMillis(), 1);
    }

    public Duration minus(long j) {
        return withDurationAdded(j, -1);
    }

    public Duration minus(ReadableDuration readableDuration) {
        return readableDuration == null ? this : withDurationAdded(readableDuration.getMillis(), -1);
    }
}
