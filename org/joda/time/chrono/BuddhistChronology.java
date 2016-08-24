package org.joda.time.chrono;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.joda.time.field.DelegatedDateTimeField;
import org.joda.time.field.DividedDateTimeField;
import org.joda.time.field.OffsetDateTimeField;
import org.joda.time.field.RemainderDateTimeField;
import org.joda.time.field.SkipUndoDateTimeField;

public final class BuddhistChronology extends AssembledChronology {
    public static final int BE = 1;
    private static final int BUDDHIST_OFFSET = 543;
    private static final DateTimeField ERA_FIELD;
    private static final BuddhistChronology INSTANCE_UTC;
    private static final Map<DateTimeZone, BuddhistChronology> cCache;
    private static final long serialVersionUID = -3474595157769370126L;

    static {
        ERA_FIELD = new BasicSingleEraDateTimeField("BE");
        cCache = new HashMap();
        INSTANCE_UTC = getInstance(DateTimeZone.UTC);
    }

    public static BuddhistChronology getInstanceUTC() {
        return INSTANCE_UTC;
    }

    public static BuddhistChronology getInstance() {
        return getInstance(DateTimeZone.getDefault());
    }

    public static synchronized BuddhistChronology getInstance(DateTimeZone dateTimeZone) {
        BuddhistChronology buddhistChronology;
        synchronized (BuddhistChronology.class) {
            if (dateTimeZone == null) {
                dateTimeZone = DateTimeZone.getDefault();
            }
            synchronized (cCache) {
                buddhistChronology = (BuddhistChronology) cCache.get(dateTimeZone);
                if (buddhistChronology == null) {
                    Chronology buddhistChronology2 = new BuddhistChronology(GJChronology.getInstance(dateTimeZone, null), null);
                    BuddhistChronology buddhistChronology3 = new BuddhistChronology(LimitChronology.getInstance(buddhistChronology2, new DateTime((int) BE, (int) BE, (int) BE, 0, 0, 0, 0, buddhistChronology2), null), StringUtils.EMPTY);
                    cCache.put(dateTimeZone, buddhistChronology3);
                    buddhistChronology = buddhistChronology3;
                }
            }
        }
        return buddhistChronology;
    }

    private BuddhistChronology(Chronology chronology, Object obj) {
        super(chronology, obj);
    }

    private Object readResolve() {
        Chronology base = getBase();
        return base == null ? getInstanceUTC() : getInstance(base.getZone());
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BuddhistChronology)) {
            return false;
        }
        return getZone().equals(((BuddhistChronology) obj).getZone());
    }

    public int hashCode() {
        return ("Buddhist".hashCode() * 11) + getZone().hashCode();
    }

    public String toString() {
        String str = "BuddhistChronology";
        DateTimeZone zone = getZone();
        if (zone != null) {
            return str + '[' + zone.getID() + ']';
        }
        return str;
    }

    protected void assemble(Fields fields) {
        if (getParam() == null) {
            fields.year = new OffsetDateTimeField(new SkipUndoDateTimeField(this, fields.year), BUDDHIST_OFFSET);
            DateTimeField dateTimeField = fields.yearOfEra;
            fields.yearOfEra = new DelegatedDateTimeField(fields.year, DateTimeFieldType.yearOfEra());
            fields.weekyear = new OffsetDateTimeField(new SkipUndoDateTimeField(this, fields.weekyear), BUDDHIST_OFFSET);
            fields.centuryOfEra = new DividedDateTimeField(new OffsetDateTimeField(fields.yearOfEra, 99), DateTimeFieldType.centuryOfEra(), 100);
            fields.yearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField((DividedDateTimeField) fields.centuryOfEra), DateTimeFieldType.yearOfCentury(), BE);
            fields.weekyearOfCentury = new OffsetDateTimeField(new RemainderDateTimeField(fields.weekyear, DateTimeFieldType.weekyearOfCentury(), 100), DateTimeFieldType.weekyearOfCentury(), BE);
            fields.era = ERA_FIELD;
        }
    }
}
