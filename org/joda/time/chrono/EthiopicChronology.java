package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.SkipDateTimeField;

public final class EthiopicChronology extends BasicFixedMonthChronology {
    public static final int EE = 1;
    private static final DateTimeField ERA_FIELD;
    private static final EthiopicChronology INSTANCE_UTC;
    private static final int MAX_YEAR = 292272984;
    private static final int MIN_YEAR = -292269337;
    private static final Map<DateTimeZone, EthiopicChronology[]> cCache;
    private static final long serialVersionUID = -5972804258688333942L;

    static {
        ERA_FIELD = new BasicSingleEraDateTimeField("EE");
        cCache = new HashMap();
        INSTANCE_UTC = getInstance(DateTimeZone.UTC);
    }

    public static EthiopicChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    public static EthiopicChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static EthiopicChronology getInstance(DateTimeZone dateTimeZone, int i) {
        EthiopicChronology ethiopicChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        synchronized (cCache) {
            EthiopicChronology[] ethiopicChronologyArr = (EthiopicChronology[]) cCache.get(dateTimeZone);
            if (ethiopicChronologyArr == null) {
                ethiopicChronologyArr = new EthiopicChronology[7];
                cCache.put(dateTimeZone, ethiopicChronologyArr);
            }
            EthiopicChronology[] ethiopicChronologyArr2 = ethiopicChronologyArr;
            try {
                ethiopicChronology = ethiopicChronologyArr2[i - 1];
                if (ethiopicChronology == null) {
                    if (dateTimeZone == DateTimeZone.UTC) {
                        Chronology ethiopicChronology2 = new EthiopicChronology(null, null, i);
                        ethiopicChronology = new EthiopicChronology(LimitChronology.getInstance(ethiopicChronology2, new DateTime((int) EE, (int) EE, (int) EE, 0, 0, 0, 0, ethiopicChronology2), null), null, i);
                    } else {
                        ethiopicChronology = new EthiopicChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                    }
                    ethiopicChronologyArr2[i - 1] = ethiopicChronology;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i);
            }
        }
        return ethiopicChronology;
    }

    EthiopicChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    private Object readResolve() {
        Chronology base = getBase();
        return base == null ? getInstance(DateTimeZone.UTC, getMinimumDaysInFirstWeek()) : getInstance(base.getZone(), getMinimumDaysInFirstWeek());
    }

    public Chronology withUTC() {
        return INSTANCE_UTC;
    }

    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getZone() ? this : getInstance(dateTimeZone);
    }

    long calculateFirstDayOfYearMillis(int i) {
        int i2;
        int i3 = i - 1963;
        if (i3 <= 0) {
            i2 = (i3 + 3) >> 2;
        } else {
            i2 = i3 >> 2;
            if (!isLeapYear(i)) {
                i2 += EE;
            }
        }
        return (((((long) i3) * 365) + ((long) i2)) * DateUtils.MILLIS_PER_DAY) + 21859200000L;
    }

    int getMinYear() {
        return MIN_YEAR;
    }

    int getMaxYear() {
        return MAX_YEAR;
    }

    long getApproxMillisAtEpochDividedByTwo() {
        return 30962844000000L;
    }

    protected void assemble(Fields fields) {
        if (getBase() == null) {
            super.assemble(fields);
            fields.year = new SkipDateTimeField(this, fields.year);
            fields.weekyear = new SkipDateTimeField(this, fields.weekyear);
            fields.era = ERA_FIELD;
            fields.monthOfYear = new BasicMonthOfYearDateTimeField(this, 13);
            fields.months = fields.monthOfYear.getDurationField();
        }
    }
}
