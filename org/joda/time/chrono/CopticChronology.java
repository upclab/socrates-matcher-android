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

public final class CopticChronology extends BasicFixedMonthChronology {
    public static final int AM = 1;
    private static final DateTimeField ERA_FIELD;
    private static final CopticChronology INSTANCE_UTC;
    private static final int MAX_YEAR = 292272708;
    private static final int MIN_YEAR = -292269337;
    private static final Map<DateTimeZone, CopticChronology[]> cCache;
    private static final long serialVersionUID = -5972804258688333942L;

    static {
        ERA_FIELD = new BasicSingleEraDateTimeField("AM");
        cCache = new HashMap();
        INSTANCE_UTC = getInstance(DateTimeZone.UTC);
    }

    public static CopticChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    public static CopticChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), 4);
    }

    public static CopticChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, 4);
    }

    public static CopticChronology getInstance(DateTimeZone dateTimeZone, int i) {
        CopticChronology copticChronology;
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        synchronized (cCache) {
            CopticChronology[] copticChronologyArr = (CopticChronology[]) cCache.get(dateTimeZone);
            if (copticChronologyArr == null) {
                copticChronologyArr = new CopticChronology[7];
                cCache.put(dateTimeZone, copticChronologyArr);
            }
            CopticChronology[] copticChronologyArr2 = copticChronologyArr;
            try {
                copticChronology = copticChronologyArr2[i - 1];
                if (copticChronology == null) {
                    if (dateTimeZone == DateTimeZone.UTC) {
                        Chronology copticChronology2 = new CopticChronology(null, null, i);
                        copticChronology = new CopticChronology(LimitChronology.getInstance(copticChronology2, new DateTime((int) AM, (int) AM, (int) AM, 0, 0, 0, 0, copticChronology2), null), null, i);
                    } else {
                        copticChronology = new CopticChronology(ZonedChronology.getInstance(getInstance(DateTimeZone.UTC, i), dateTimeZone), null, i);
                    }
                    copticChronologyArr2[i - 1] = copticChronology;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid min days in first week: " + i);
            }
        }
        return copticChronology;
    }

    CopticChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    private Object readResolve() {
        Chronology base = getBase();
        int minimumDaysInFirstWeek = getMinimumDaysInFirstWeek();
        if (minimumDaysInFirstWeek == 0) {
            minimumDaysInFirstWeek = 4;
        }
        return base == null ? getInstance(DateTimeZone.UTC, minimumDaysInFirstWeek) : getInstance(base.getZone(), minimumDaysInFirstWeek);
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
        int i3 = i - 1687;
        if (i3 <= 0) {
            i2 = (i3 + 3) >> 2;
        } else {
            i2 = i3 >> 2;
            if (!isLeapYear(i)) {
                i2 += AM;
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
        return 26607895200000L;
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
