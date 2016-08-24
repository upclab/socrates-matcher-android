package org.joda.time;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.field.FieldUtils;

public class PeriodType implements Serializable {
    static int DAY_INDEX = 0;
    static int HOUR_INDEX = 0;
    static int MILLI_INDEX = 0;
    static int MINUTE_INDEX = 0;
    static int MONTH_INDEX = 0;
    static int SECOND_INDEX = 0;
    static int WEEK_INDEX = 0;
    static int YEAR_INDEX = 0;
    private static PeriodType cDTime = null;
    private static PeriodType cDays = null;
    private static PeriodType cHours = null;
    private static PeriodType cMillis = null;
    private static PeriodType cMinutes = null;
    private static PeriodType cMonths = null;
    private static PeriodType cSeconds = null;
    private static PeriodType cStandard = null;
    private static PeriodType cTime = null;
    private static final Map<PeriodType, Object> cTypes;
    private static PeriodType cWeeks = null;
    private static PeriodType cYD = null;
    private static PeriodType cYDTime = null;
    private static PeriodType cYMD = null;
    private static PeriodType cYMDTime = null;
    private static PeriodType cYWD = null;
    private static PeriodType cYWDTime = null;
    private static PeriodType cYears = null;
    private static final long serialVersionUID = 2274324892792009998L;
    private final int[] iIndices;
    private final String iName;
    private final DurationFieldType[] iTypes;

    static {
        cTypes = new HashMap(32);
        YEAR_INDEX = 0;
        MONTH_INDEX = 1;
        WEEK_INDEX = 2;
        DAY_INDEX = 3;
        HOUR_INDEX = 4;
        MINUTE_INDEX = 5;
        SECOND_INDEX = 6;
        MILLI_INDEX = 7;
    }

    public static PeriodType standard() {
        PeriodType periodType = cStandard;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Standard", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        cStandard = periodType;
        return periodType;
    }

    public static PeriodType yearMonthDayTime() {
        PeriodType periodType = cYMDTime;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearMonthDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, 1, -1, 2, 3, 4, 5, 6});
        cYMDTime = periodType;
        return periodType;
    }

    public static PeriodType yearMonthDay() {
        PeriodType periodType = cYMD;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearMonthDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.months(), DurationFieldType.days()}, new int[]{0, 1, -1, 2, -1, -1, -1, -1});
        cYMD = periodType;
        return periodType;
    }

    public static PeriodType yearWeekDayTime() {
        PeriodType periodType = cYWDTime;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearWeekDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, -1, 1, 2, 3, 4, 5, 6});
        cYWDTime = periodType;
        return periodType;
    }

    public static PeriodType yearWeekDay() {
        PeriodType periodType = cYWD;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearWeekDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.weeks(), DurationFieldType.days()}, new int[]{0, -1, 1, 2, -1, -1, -1, -1});
        cYWD = periodType;
        return periodType;
    }

    public static PeriodType yearDayTime() {
        PeriodType periodType = cYDTime;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearDayTime", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{0, -1, -1, 1, 2, 3, 4, 5});
        cYDTime = periodType;
        return periodType;
    }

    public static PeriodType yearDay() {
        PeriodType periodType = cYD;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("YearDay", new DurationFieldType[]{DurationFieldType.years(), DurationFieldType.days()}, new int[]{0, -1, -1, 1, -1, -1, -1, -1});
        cYD = periodType;
        return periodType;
    }

    public static PeriodType dayTime() {
        PeriodType periodType = cDTime;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("DayTime", new DurationFieldType[]{DurationFieldType.days(), DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{-1, -1, -1, 0, 1, 2, 3, 4});
        cDTime = periodType;
        return periodType;
    }

    public static PeriodType time() {
        PeriodType periodType = cTime;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Time", new DurationFieldType[]{DurationFieldType.hours(), DurationFieldType.minutes(), DurationFieldType.seconds(), DurationFieldType.millis()}, new int[]{-1, -1, -1, -1, 0, 1, 2, 3});
        cTime = periodType;
        return periodType;
    }

    public static PeriodType years() {
        PeriodType periodType = cYears;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Years", new DurationFieldType[]{DurationFieldType.years()}, new int[]{0, -1, -1, -1, -1, -1, -1, -1});
        cYears = periodType;
        return periodType;
    }

    public static PeriodType months() {
        PeriodType periodType = cMonths;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Months", new DurationFieldType[]{DurationFieldType.months()}, new int[]{-1, 0, -1, -1, -1, -1, -1, -1});
        cMonths = periodType;
        return periodType;
    }

    public static PeriodType weeks() {
        PeriodType periodType = cWeeks;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Weeks", new DurationFieldType[]{DurationFieldType.weeks()}, new int[]{-1, -1, 0, -1, -1, -1, -1, -1});
        cWeeks = periodType;
        return periodType;
    }

    public static PeriodType days() {
        PeriodType periodType = cDays;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Days", new DurationFieldType[]{DurationFieldType.days()}, new int[]{-1, -1, -1, 0, -1, -1, -1, -1});
        cDays = periodType;
        return periodType;
    }

    public static PeriodType hours() {
        PeriodType periodType = cHours;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Hours", new DurationFieldType[]{DurationFieldType.hours()}, new int[]{-1, -1, -1, -1, 0, -1, -1, -1});
        cHours = periodType;
        return periodType;
    }

    public static PeriodType minutes() {
        PeriodType periodType = cMinutes;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Minutes", new DurationFieldType[]{DurationFieldType.minutes()}, new int[]{-1, -1, -1, -1, -1, 0, -1, -1});
        cMinutes = periodType;
        return periodType;
    }

    public static PeriodType seconds() {
        PeriodType periodType = cSeconds;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Seconds", new DurationFieldType[]{DurationFieldType.seconds()}, new int[]{-1, -1, -1, -1, -1, -1, 0, -1});
        cSeconds = periodType;
        return periodType;
    }

    public static PeriodType millis() {
        PeriodType periodType = cMillis;
        if (periodType != null) {
            return periodType;
        }
        periodType = new PeriodType("Millis", new DurationFieldType[]{DurationFieldType.millis()}, new int[]{-1, -1, -1, -1, -1, -1, -1, 0});
        cMillis = periodType;
        return periodType;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized org.joda.time.PeriodType forFields(org.joda.time.DurationFieldType[] r7) {
        /*
        r2 = org.joda.time.PeriodType.class;
        monitor-enter(r2);
        if (r7 == 0) goto L_0x0008;
    L_0x0005:
        r0 = r7.length;	 Catch:{ all -> 0x0010 }
        if (r0 != 0) goto L_0x0013;
    L_0x0008:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0010 }
        r1 = "Types array must not be null or empty";
        r0.<init>(r1);	 Catch:{ all -> 0x0010 }
        throw r0;	 Catch:{ all -> 0x0010 }
    L_0x0010:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
    L_0x0013:
        r0 = 0;
    L_0x0014:
        r1 = r7.length;	 Catch:{ all -> 0x0010 }
        if (r0 >= r1) goto L_0x0026;
    L_0x0017:
        r1 = r7[r0];	 Catch:{ all -> 0x0010 }
        if (r1 != 0) goto L_0x0023;
    L_0x001b:
        r0 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0010 }
        r1 = "Types array must not contain null";
        r0.<init>(r1);	 Catch:{ all -> 0x0010 }
        throw r0;	 Catch:{ all -> 0x0010 }
    L_0x0023:
        r0 = r0 + 1;
        goto L_0x0014;
    L_0x0026:
        r3 = cTypes;	 Catch:{ all -> 0x0010 }
        r0 = r3.isEmpty();	 Catch:{ all -> 0x0010 }
        if (r0 == 0) goto L_0x00e9;
    L_0x002e:
        r0 = standard();	 Catch:{ all -> 0x0010 }
        r1 = standard();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearMonthDayTime();	 Catch:{ all -> 0x0010 }
        r1 = yearMonthDayTime();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearMonthDay();	 Catch:{ all -> 0x0010 }
        r1 = yearMonthDay();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearWeekDayTime();	 Catch:{ all -> 0x0010 }
        r1 = yearWeekDayTime();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearWeekDay();	 Catch:{ all -> 0x0010 }
        r1 = yearWeekDay();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearDayTime();	 Catch:{ all -> 0x0010 }
        r1 = yearDayTime();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = yearDay();	 Catch:{ all -> 0x0010 }
        r1 = yearDay();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = dayTime();	 Catch:{ all -> 0x0010 }
        r1 = dayTime();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = time();	 Catch:{ all -> 0x0010 }
        r1 = time();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = years();	 Catch:{ all -> 0x0010 }
        r1 = years();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = months();	 Catch:{ all -> 0x0010 }
        r1 = months();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = weeks();	 Catch:{ all -> 0x0010 }
        r1 = weeks();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = days();	 Catch:{ all -> 0x0010 }
        r1 = days();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = hours();	 Catch:{ all -> 0x0010 }
        r1 = hours();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = minutes();	 Catch:{ all -> 0x0010 }
        r1 = minutes();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = seconds();	 Catch:{ all -> 0x0010 }
        r1 = seconds();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
        r0 = millis();	 Catch:{ all -> 0x0010 }
        r1 = millis();	 Catch:{ all -> 0x0010 }
        r3.put(r0, r1);	 Catch:{ all -> 0x0010 }
    L_0x00e9:
        r4 = new org.joda.time.PeriodType;	 Catch:{ all -> 0x0010 }
        r0 = 0;
        r1 = 0;
        r4.<init>(r0, r7, r1);	 Catch:{ all -> 0x0010 }
        r0 = r3.get(r4);	 Catch:{ all -> 0x0010 }
        r1 = r0 instanceof org.joda.time.PeriodType;	 Catch:{ all -> 0x0010 }
        if (r1 == 0) goto L_0x00fc;
    L_0x00f8:
        r0 = (org.joda.time.PeriodType) r0;	 Catch:{ all -> 0x0010 }
    L_0x00fa:
        monitor-exit(r2);
        return r0;
    L_0x00fc:
        if (r0 == 0) goto L_0x0117;
    L_0x00fe:
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0010 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0010 }
        r3.<init>();	 Catch:{ all -> 0x0010 }
        r4 = "PeriodType does not support fields: ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0010 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0010 }
        r0 = r0.toString();	 Catch:{ all -> 0x0010 }
        r1.<init>(r0);	 Catch:{ all -> 0x0010 }
        throw r1;	 Catch:{ all -> 0x0010 }
    L_0x0117:
        r1 = standard();	 Catch:{ all -> 0x0010 }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x0010 }
        r5 = java.util.Arrays.asList(r7);	 Catch:{ all -> 0x0010 }
        r0.<init>(r5);	 Catch:{ all -> 0x0010 }
        r5 = org.joda.time.DurationFieldType.years();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x0132;
    L_0x012e:
        r1 = r1.withYearsRemoved();	 Catch:{ all -> 0x0010 }
    L_0x0132:
        r5 = org.joda.time.DurationFieldType.months();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x0140;
    L_0x013c:
        r1 = r1.withMonthsRemoved();	 Catch:{ all -> 0x0010 }
    L_0x0140:
        r5 = org.joda.time.DurationFieldType.weeks();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x014e;
    L_0x014a:
        r1 = r1.withWeeksRemoved();	 Catch:{ all -> 0x0010 }
    L_0x014e:
        r5 = org.joda.time.DurationFieldType.days();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x015c;
    L_0x0158:
        r1 = r1.withDaysRemoved();	 Catch:{ all -> 0x0010 }
    L_0x015c:
        r5 = org.joda.time.DurationFieldType.hours();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x016a;
    L_0x0166:
        r1 = r1.withHoursRemoved();	 Catch:{ all -> 0x0010 }
    L_0x016a:
        r5 = org.joda.time.DurationFieldType.minutes();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x0178;
    L_0x0174:
        r1 = r1.withMinutesRemoved();	 Catch:{ all -> 0x0010 }
    L_0x0178:
        r5 = org.joda.time.DurationFieldType.seconds();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x0186;
    L_0x0182:
        r1 = r1.withSecondsRemoved();	 Catch:{ all -> 0x0010 }
    L_0x0186:
        r5 = org.joda.time.DurationFieldType.millis();	 Catch:{ all -> 0x0010 }
        r5 = r0.remove(r5);	 Catch:{ all -> 0x0010 }
        if (r5 != 0) goto L_0x0194;
    L_0x0190:
        r1 = r1.withMillisRemoved();	 Catch:{ all -> 0x0010 }
    L_0x0194:
        r5 = r0.size();	 Catch:{ all -> 0x0010 }
        if (r5 <= 0) goto L_0x01b6;
    L_0x019a:
        r3.put(r4, r0);	 Catch:{ all -> 0x0010 }
        r1 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x0010 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0010 }
        r3.<init>();	 Catch:{ all -> 0x0010 }
        r4 = "PeriodType does not support fields: ";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0010 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0010 }
        r0 = r0.toString();	 Catch:{ all -> 0x0010 }
        r1.<init>(r0);	 Catch:{ all -> 0x0010 }
        throw r1;	 Catch:{ all -> 0x0010 }
    L_0x01b6:
        r4 = new org.joda.time.PeriodType;	 Catch:{ all -> 0x0010 }
        r0 = 0;
        r5 = r1.iTypes;	 Catch:{ all -> 0x0010 }
        r6 = 0;
        r4.<init>(r0, r5, r6);	 Catch:{ all -> 0x0010 }
        r0 = r3.get(r4);	 Catch:{ all -> 0x0010 }
        r0 = (org.joda.time.PeriodType) r0;	 Catch:{ all -> 0x0010 }
        if (r0 == 0) goto L_0x01cc;
    L_0x01c7:
        r3.put(r4, r0);	 Catch:{ all -> 0x0010 }
        goto L_0x00fa;
    L_0x01cc:
        r3.put(r4, r1);	 Catch:{ all -> 0x0010 }
        r0 = r1;
        goto L_0x00fa;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.PeriodType.forFields(org.joda.time.DurationFieldType[]):org.joda.time.PeriodType");
    }

    protected PeriodType(String str, DurationFieldType[] durationFieldTypeArr, int[] iArr) {
        this.iName = str;
        this.iTypes = durationFieldTypeArr;
        this.iIndices = iArr;
    }

    public String getName() {
        return this.iName;
    }

    public int size() {
        return this.iTypes.length;
    }

    public DurationFieldType getFieldType(int i) {
        return this.iTypes[i];
    }

    public boolean isSupported(DurationFieldType durationFieldType) {
        return indexOf(durationFieldType) >= 0;
    }

    public int indexOf(DurationFieldType durationFieldType) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (this.iTypes[i] == durationFieldType) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        return "PeriodType[" + getName() + "]";
    }

    int getIndexedField(ReadablePeriod readablePeriod, int i) {
        int i2 = this.iIndices[i];
        return i2 == -1 ? 0 : readablePeriod.getValue(i2);
    }

    boolean setIndexedField(ReadablePeriod readablePeriod, int i, int[] iArr, int i2) {
        int i3 = this.iIndices[i];
        if (i3 == -1) {
            throw new UnsupportedOperationException("Field is not supported");
        }
        iArr[i3] = i2;
        return true;
    }

    boolean addIndexedField(ReadablePeriod readablePeriod, int i, int[] iArr, int i2) {
        if (i2 == 0) {
            return false;
        }
        int i3 = this.iIndices[i];
        if (i3 == -1) {
            throw new UnsupportedOperationException("Field is not supported");
        }
        iArr[i3] = FieldUtils.safeAdd(iArr[i3], i2);
        return true;
    }

    public PeriodType withYearsRemoved() {
        return withFieldRemoved(0, "NoYears");
    }

    public PeriodType withMonthsRemoved() {
        return withFieldRemoved(1, "NoMonths");
    }

    public PeriodType withWeeksRemoved() {
        return withFieldRemoved(2, "NoWeeks");
    }

    public PeriodType withDaysRemoved() {
        return withFieldRemoved(3, "NoDays");
    }

    public PeriodType withHoursRemoved() {
        return withFieldRemoved(4, "NoHours");
    }

    public PeriodType withMinutesRemoved() {
        return withFieldRemoved(5, "NoMinutes");
    }

    public PeriodType withSecondsRemoved() {
        return withFieldRemoved(6, "NoSeconds");
    }

    public PeriodType withMillisRemoved() {
        return withFieldRemoved(7, "NoMillis");
    }

    private PeriodType withFieldRemoved(int i, String str) {
        int i2 = 0;
        int i3 = this.iIndices[i];
        if (i3 == -1) {
            return this;
        }
        DurationFieldType[] durationFieldTypeArr = new DurationFieldType[(size() - 1)];
        for (int i4 = 0; i4 < this.iTypes.length; i4++) {
            if (i4 < i3) {
                durationFieldTypeArr[i4] = this.iTypes[i4];
            } else if (i4 > i3) {
                durationFieldTypeArr[i4 - 1] = this.iTypes[i4];
            }
        }
        int[] iArr = new int[8];
        while (i2 < iArr.length) {
            if (i2 < i) {
                iArr[i2] = this.iIndices[i2];
            } else if (i2 > i) {
                iArr[i2] = this.iIndices[i2] == -1 ? -1 : this.iIndices[i2] - 1;
            } else {
                iArr[i2] = -1;
            }
            i2++;
        }
        return new PeriodType(getName() + str, durationFieldTypeArr, iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PeriodType)) {
            return false;
        }
        return Arrays.equals(this.iTypes, ((PeriodType) obj).iTypes);
    }

    public int hashCode() {
        int i = 0;
        int i2 = 0;
        while (i < this.iTypes.length) {
            i2 += this.iTypes[i].hashCode();
            i++;
        }
        return i2;
    }
}
