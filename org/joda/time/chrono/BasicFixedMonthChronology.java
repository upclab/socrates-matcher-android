package org.joda.time.chrono;

import org.joda.time.Chronology;

abstract class BasicFixedMonthChronology extends BasicChronology {
    static final long MILLIS_PER_MONTH = 2592000000L;
    static final long MILLIS_PER_YEAR = 31557600000L;
    static final int MONTH_LENGTH = 30;
    private static final long serialVersionUID = 261387371998L;

    BasicFixedMonthChronology(Chronology chronology, Object obj, int i) {
        super(chronology, obj, i);
    }

    long setYear(long j, int i) {
        int dayOfYear = getDayOfYear(j, getYear(j));
        int millisOfDay = getMillisOfDay(j);
        if (dayOfYear > 365 && !isLeapYear(i)) {
            dayOfYear--;
        }
        return ((long) millisOfDay) + getYearMonthDayMillis(i, 1, dayOfYear);
    }

    long getYearDifference(long j, long j2) {
        int year = getYear(j);
        int year2 = getYear(j2);
        year -= year2;
        if (j - getYearMillis(year) < j2 - getYearMillis(year2)) {
            year--;
        }
        return (long) year;
    }

    long getTotalMillisByYearMonth(int i, int i2) {
        return ((long) (i2 - 1)) * MILLIS_PER_MONTH;
    }

    int getDayOfMonth(long j) {
        return ((getDayOfYear(j) - 1) % MONTH_LENGTH) + 1;
    }

    boolean isLeapYear(int i) {
        return (i & 3) == 3;
    }

    int getDaysInYearMonth(int i, int i2) {
        if (i2 != 13) {
            return MONTH_LENGTH;
        }
        return isLeapYear(i) ? 6 : 5;
    }

    int getDaysInMonthMax() {
        return MONTH_LENGTH;
    }

    int getDaysInMonthMax(int i) {
        return i != 13 ? MONTH_LENGTH : 6;
    }

    int getMonthOfYear(long j) {
        return ((getDayOfYear(j) - 1) / MONTH_LENGTH) + 1;
    }

    int getMonthOfYear(long j, int i) {
        return ((int) ((j - getYearMillis(i)) / MILLIS_PER_MONTH)) + 1;
    }

    int getMaxMonth() {
        return 13;
    }

    long getAverageMillisPerYear() {
        return MILLIS_PER_YEAR;
    }

    long getAverageMillisPerYearDividedByTwo() {
        return 15778800000L;
    }

    long getAverageMillisPerMonth() {
        return MILLIS_PER_MONTH;
    }
}
