package org.joda.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import org.apache.commons.lang3.ClassUtils;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.chrono.BaseChronology;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.FormatUtils;
import org.joda.time.tz.DefaultNameProvider;
import org.joda.time.tz.FixedDateTimeZone;
import org.joda.time.tz.NameProvider;
import org.joda.time.tz.Provider;
import org.joda.time.tz.UTCProvider;
import org.joda.time.tz.ZoneInfoProvider;

public abstract class DateTimeZone implements Serializable {
    private static final int MAX_MILLIS = 86399999;
    public static final DateTimeZone UTC;
    private static Set<String> cAvailableIDs = null;
    private static volatile DateTimeZone cDefault = null;
    private static NameProvider cNameProvider = null;
    private static DateTimeFormatter cOffsetFormatter = null;
    private static Provider cProvider = null;
    private static Map<String, String> cZoneIdConversion = null;
    private static Map<String, SoftReference<DateTimeZone>> iFixedOffsetCache = null;
    private static final long serialVersionUID = 5546345482340108586L;
    private final String iID;

    private static final class Stub implements Serializable {
        private static final long serialVersionUID = -6471952376487863581L;
        private transient String iID;

        Stub(String str) {
            this.iID = str;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeUTF(this.iID);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            this.iID = objectInputStream.readUTF();
        }

        private Object readResolve() throws ObjectStreamException {
            return DateTimeZone.forID(this.iID);
        }
    }

    /* renamed from: org.joda.time.DateTimeZone.1 */
    static class C10531 extends BaseChronology {
        private static final long serialVersionUID = -3128740902654445468L;

        public DateTimeZone getZone() {
            return null;
        }

        public Chronology withUTC() {
            return this;
        }

        public Chronology withZone(DateTimeZone dateTimeZone) {
            return this;
        }

        C10531() {
        }

        public String toString() {
            return getClass().getName();
        }
    }

    public abstract boolean equals(Object obj);

    public abstract String getNameKey(long j);

    public abstract int getOffset(long j);

    public abstract int getStandardOffset(long j);

    public abstract boolean isFixed();

    public abstract long nextTransition(long j);

    public abstract long previousTransition(long j);

    static {
        UTC = new FixedDateTimeZone("UTC", "UTC", 0, 0);
        setProvider0(null);
        setNameProvider0(null);
    }

    public static DateTimeZone getDefault() {
        DateTimeZone dateTimeZone = cDefault;
        if (dateTimeZone == null) {
            synchronized (DateTimeZone.class) {
                dateTimeZone = cDefault;
                if (dateTimeZone == null) {
                    dateTimeZone = null;
                    try {
                        String property = System.getProperty("user.timezone");
                        if (property != null) {
                            dateTimeZone = forID(property);
                        }
                    } catch (RuntimeException e) {
                    }
                    if (dateTimeZone == null) {
                        try {
                            dateTimeZone = forTimeZone(TimeZone.getDefault());
                        } catch (IllegalArgumentException e2) {
                        }
                    }
                    if (dateTimeZone == null) {
                        dateTimeZone = UTC;
                    }
                    cDefault = dateTimeZone;
                }
            }
        }
        return dateTimeZone;
    }

    public static void setDefault(DateTimeZone dateTimeZone) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setDefault"));
        }
        if (dateTimeZone == null) {
            throw new IllegalArgumentException("The datetime zone must not be null");
        }
        synchronized (DateTimeZone.class) {
            cDefault = dateTimeZone;
        }
    }

    @FromString
    public static DateTimeZone forID(String str) {
        if (str == null) {
            return getDefault();
        }
        if (str.equals("UTC")) {
            return UTC;
        }
        DateTimeZone zone = cProvider.getZone(str);
        if (zone != null) {
            return zone;
        }
        if (str.startsWith("+") || str.startsWith("-")) {
            int parseOffset = parseOffset(str);
            if (((long) parseOffset) == 0) {
                return UTC;
            }
            return fixedOffsetZone(printOffset(parseOffset), parseOffset);
        }
        throw new IllegalArgumentException("The datetime zone id '" + str + "' is not recognised");
    }

    public static DateTimeZone forOffsetHours(int i) throws IllegalArgumentException {
        return forOffsetHoursMinutes(i, 0);
    }

    public static DateTimeZone forOffsetHoursMinutes(int i, int i2) throws IllegalArgumentException {
        if (i == 0 && i2 == 0) {
            return UTC;
        }
        if (i < -23 || i > 23) {
            throw new IllegalArgumentException("Hours out of range: " + i);
        } else if (i2 < -59 || i2 > 59) {
            throw new IllegalArgumentException("Minutes out of range: " + i2);
        } else if (i <= 0 || i2 >= 0) {
            int i3 = i * 60;
            if (i3 < 0) {
                try {
                    i3 -= Math.abs(i2);
                } catch (ArithmeticException e) {
                    throw new IllegalArgumentException("Offset is too large");
                }
            }
            i3 += i2;
            return forOffsetMillis(FieldUtils.safeMultiply(i3, (int) DateTimeConstants.MILLIS_PER_MINUTE));
        } else {
            throw new IllegalArgumentException("Positive hours must not have negative minutes: " + i2);
        }
    }

    public static DateTimeZone forOffsetMillis(int i) {
        if (i >= -86399999 && i <= MAX_MILLIS) {
            return fixedOffsetZone(printOffset(i), i);
        }
        throw new IllegalArgumentException("Millis out of range: " + i);
    }

    public static DateTimeZone forTimeZone(TimeZone timeZone) {
        if (timeZone == null) {
            return getDefault();
        }
        String id = timeZone.getID();
        if (id.equals("UTC")) {
            return UTC;
        }
        DateTimeZone dateTimeZone = null;
        String convertedId = getConvertedId(id);
        if (convertedId != null) {
            dateTimeZone = cProvider.getZone(convertedId);
        }
        if (dateTimeZone == null) {
            dateTimeZone = cProvider.getZone(id);
        }
        if (dateTimeZone != null) {
            return dateTimeZone;
        }
        if (convertedId == null) {
            String id2 = timeZone.getID();
            if (id2.startsWith("GMT+") || id2.startsWith("GMT-")) {
                int parseOffset = parseOffset(id2.substring(3));
                if (((long) parseOffset) == 0) {
                    return UTC;
                }
                return fixedOffsetZone(printOffset(parseOffset), parseOffset);
            }
        }
        throw new IllegalArgumentException("The datetime zone id '" + id + "' is not recognised");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized org.joda.time.DateTimeZone fixedOffsetZone(java.lang.String r4, int r5) {
        /*
        r1 = org.joda.time.DateTimeZone.class;
        monitor-enter(r1);
        if (r5 != 0) goto L_0x0009;
    L_0x0005:
        r0 = UTC;	 Catch:{ all -> 0x0037 }
    L_0x0007:
        monitor-exit(r1);
        return r0;
    L_0x0009:
        r0 = iFixedOffsetCache;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x0014;
    L_0x000d:
        r0 = new java.util.HashMap;	 Catch:{ all -> 0x0037 }
        r0.<init>();	 Catch:{ all -> 0x0037 }
        iFixedOffsetCache = r0;	 Catch:{ all -> 0x0037 }
    L_0x0014:
        r0 = iFixedOffsetCache;	 Catch:{ all -> 0x0037 }
        r0 = r0.get(r4);	 Catch:{ all -> 0x0037 }
        r0 = (java.lang.ref.Reference) r0;	 Catch:{ all -> 0x0037 }
        if (r0 == 0) goto L_0x0026;
    L_0x001e:
        r0 = r0.get();	 Catch:{ all -> 0x0037 }
        r0 = (org.joda.time.DateTimeZone) r0;	 Catch:{ all -> 0x0037 }
        if (r0 != 0) goto L_0x0007;
    L_0x0026:
        r0 = new org.joda.time.tz.FixedDateTimeZone;	 Catch:{ all -> 0x0037 }
        r2 = 0;
        r0.<init>(r4, r2, r5, r5);	 Catch:{ all -> 0x0037 }
        r2 = iFixedOffsetCache;	 Catch:{ all -> 0x0037 }
        r3 = new java.lang.ref.SoftReference;	 Catch:{ all -> 0x0037 }
        r3.<init>(r0);	 Catch:{ all -> 0x0037 }
        r2.put(r4, r3);	 Catch:{ all -> 0x0037 }
        goto L_0x0007;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.DateTimeZone.fixedOffsetZone(java.lang.String, int):org.joda.time.DateTimeZone");
    }

    public static Set<String> getAvailableIDs() {
        return cAvailableIDs;
    }

    public static Provider getProvider() {
        return cProvider;
    }

    public static void setProvider(Provider provider) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setProvider"));
        }
        setProvider0(provider);
    }

    private static void setProvider0(Provider provider) {
        if (provider == null) {
            provider = getDefaultProvider();
        }
        Set availableIDs = provider.getAvailableIDs();
        if (availableIDs == null || availableIDs.size() == 0) {
            throw new IllegalArgumentException("The provider doesn't have any available ids");
        } else if (!availableIDs.contains("UTC")) {
            throw new IllegalArgumentException("The provider doesn't support UTC");
        } else if (UTC.equals(provider.getZone("UTC"))) {
            cProvider = provider;
            cAvailableIDs = availableIDs;
        } else {
            throw new IllegalArgumentException("Invalid UTC zone provided");
        }
    }

    private static Provider getDefaultProvider() {
        Provider provider;
        Provider provider2 = null;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.Provider");
            if (property != null) {
                try {
                    provider = (Provider) Class.forName(property).newInstance();
                } catch (Throwable e) {
                    Thread currentThread = Thread.currentThread();
                    currentThread.getThreadGroup().uncaughtException(currentThread, e);
                }
                provider2 = provider;
                if (provider2 == null) {
                    try {
                        provider = new ZoneInfoProvider("org/joda/time/tz/data");
                    } catch (Throwable e2) {
                        currentThread = Thread.currentThread();
                        currentThread.getThreadGroup().uncaughtException(currentThread, e2);
                    }
                    if (provider != null) {
                        return new UTCProvider();
                    }
                    return provider;
                }
                provider = provider2;
                if (provider != null) {
                    return provider;
                }
                return new UTCProvider();
            }
            provider = null;
            provider2 = provider;
        } catch (SecurityException e3) {
        }
        if (provider2 == null) {
            provider = new ZoneInfoProvider("org/joda/time/tz/data");
            if (provider != null) {
                return new UTCProvider();
            }
            return provider;
        }
        provider = provider2;
        if (provider != null) {
            return provider;
        }
        return new UTCProvider();
    }

    public static NameProvider getNameProvider() {
        return cNameProvider;
    }

    public static void setNameProvider(NameProvider nameProvider) throws SecurityException {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new JodaTimePermission("DateTimeZone.setNameProvider"));
        }
        setNameProvider0(nameProvider);
    }

    private static void setNameProvider0(NameProvider nameProvider) {
        if (nameProvider == null) {
            nameProvider = getDefaultNameProvider();
        }
        cNameProvider = nameProvider;
    }

    private static NameProvider getDefaultNameProvider() {
        NameProvider nameProvider;
        try {
            String property = System.getProperty("org.joda.time.DateTimeZone.NameProvider");
            if (property != null) {
                try {
                    nameProvider = (NameProvider) Class.forName(property).newInstance();
                } catch (Throwable e) {
                    Thread currentThread = Thread.currentThread();
                    currentThread.getThreadGroup().uncaughtException(currentThread, e);
                }
                if (nameProvider != null) {
                    return new DefaultNameProvider();
                }
                return nameProvider;
            }
            nameProvider = null;
        } catch (SecurityException e2) {
            nameProvider = null;
        }
        if (nameProvider != null) {
            return nameProvider;
        }
        return new DefaultNameProvider();
    }

    private static synchronized String getConvertedId(String str) {
        String str2;
        synchronized (DateTimeZone.class) {
            Map map = cZoneIdConversion;
            if (map == null) {
                map = new HashMap();
                map.put("GMT", "UTC");
                map.put("WET", "WET");
                map.put("CET", "CET");
                map.put("MET", "CET");
                map.put("ECT", "CET");
                map.put("EET", "EET");
                map.put("MIT", "Pacific/Apia");
                map.put("HST", "Pacific/Honolulu");
                map.put("AST", "America/Anchorage");
                map.put("PST", "America/Los_Angeles");
                map.put("MST", "America/Denver");
                map.put("PNT", "America/Phoenix");
                map.put("CST", "America/Chicago");
                map.put("EST", "America/New_York");
                map.put("IET", "America/Indiana/Indianapolis");
                map.put("PRT", "America/Puerto_Rico");
                map.put("CNT", "America/St_Johns");
                map.put("AGT", "America/Argentina/Buenos_Aires");
                map.put("BET", "America/Sao_Paulo");
                map.put("ART", "Africa/Cairo");
                map.put("CAT", "Africa/Harare");
                map.put("EAT", "Africa/Addis_Ababa");
                map.put("NET", "Asia/Yerevan");
                map.put("PLT", "Asia/Karachi");
                map.put("IST", "Asia/Kolkata");
                map.put("BST", "Asia/Dhaka");
                map.put("VST", "Asia/Ho_Chi_Minh");
                map.put("CTT", "Asia/Shanghai");
                map.put("JST", "Asia/Tokyo");
                map.put("ACT", "Australia/Darwin");
                map.put("AET", "Australia/Sydney");
                map.put("SST", "Pacific/Guadalcanal");
                map.put("NST", "Pacific/Auckland");
                cZoneIdConversion = map;
            }
            str2 = (String) map.get(str);
        }
        return str2;
    }

    private static int parseOffset(String str) {
        return -((int) offsetFormatter().withChronology(new C10531()).parseMillis(str));
    }

    private static String printOffset(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        if (i >= 0) {
            stringBuffer.append('+');
        } else {
            stringBuffer.append('-');
            i = -i;
        }
        int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
        FormatUtils.appendPaddedInteger(stringBuffer, i2, 2);
        i2 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
        int i3 = i2 / DateTimeConstants.MILLIS_PER_MINUTE;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
        i2 -= i3 * DateTimeConstants.MILLIS_PER_MINUTE;
        if (i2 == 0) {
            return stringBuffer.toString();
        }
        i3 = i2 / DateTimeConstants.MILLIS_PER_SECOND;
        stringBuffer.append(':');
        FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
        i2 -= i3 * DateTimeConstants.MILLIS_PER_SECOND;
        if (i2 == 0) {
            return stringBuffer.toString();
        }
        stringBuffer.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
        FormatUtils.appendPaddedInteger(stringBuffer, i2, 3);
        return stringBuffer.toString();
    }

    private static synchronized DateTimeFormatter offsetFormatter() {
        DateTimeFormatter dateTimeFormatter;
        synchronized (DateTimeZone.class) {
            if (cOffsetFormatter == null) {
                cOffsetFormatter = new DateTimeFormatterBuilder().appendTimeZoneOffset(null, true, 2, 4).toFormatter();
            }
            dateTimeFormatter = cOffsetFormatter;
        }
        return dateTimeFormatter;
    }

    protected DateTimeZone(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.iID = str;
    }

    @ToString
    public final String getID() {
        return this.iID;
    }

    public final String getShortName(long j) {
        return getShortName(j, null);
    }

    public String getShortName(long j, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j);
        if (nameKey == null) {
            return this.iID;
        }
        nameKey = cNameProvider.getShortName(locale, this.iID, nameKey);
        return nameKey == null ? printOffset(getOffset(j)) : nameKey;
    }

    public final String getName(long j) {
        return getName(j, null);
    }

    public String getName(long j, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String nameKey = getNameKey(j);
        if (nameKey == null) {
            return this.iID;
        }
        nameKey = cNameProvider.getName(locale, this.iID, nameKey);
        return nameKey == null ? printOffset(getOffset(j)) : nameKey;
    }

    public final int getOffset(ReadableInstant readableInstant) {
        if (readableInstant == null) {
            return getOffset(DateTimeUtils.currentTimeMillis());
        }
        return getOffset(readableInstant.getMillis());
    }

    public boolean isStandardOffset(long j) {
        return getOffset(j) == getStandardOffset(j);
    }

    public int getOffsetFromLocal(long j) {
        int offset = getOffset(j);
        long j2 = j - ((long) offset);
        int offset2 = getOffset(j2);
        if (offset != offset2) {
            if (offset - offset2 < 0 && nextTransition(j2) != nextTransition(j - ((long) offset2))) {
                return offset;
            }
        } else if (offset >= 0) {
            long previousTransition = previousTransition(j2);
            if (previousTransition < j2) {
                int offset3 = getOffset(previousTransition);
                if (j2 - previousTransition <= ((long) (offset3 - offset))) {
                    return offset3;
                }
            }
        }
        return offset2;
    }

    public long convertUTCToLocal(long j) {
        int offset = getOffset(j);
        long j2 = ((long) offset) + j;
        if ((j ^ j2) >= 0 || (((long) offset) ^ j) < 0) {
            return j2;
        }
        throw new ArithmeticException("Adding time zone offset caused overflow");
    }

    public long convertLocalToUTC(long j, boolean z, long j2) {
        int offset = getOffset(j2);
        long j3 = j - ((long) offset);
        return getOffset(j3) == offset ? j3 : convertLocalToUTC(j, z);
    }

    public long convertLocalToUTC(long j, boolean z) {
        int i;
        long j2;
        long j3 = Long.MAX_VALUE;
        int offset = getOffset(j);
        int offset2 = getOffset(j - ((long) offset));
        if (offset != offset2 && (z || offset < 0)) {
            long nextTransition = nextTransition(j - ((long) offset));
            if (nextTransition == j - ((long) offset)) {
                nextTransition = Long.MAX_VALUE;
            }
            long nextTransition2 = nextTransition(j - ((long) offset2));
            if (nextTransition2 != j - ((long) offset2)) {
                j3 = nextTransition2;
            }
            if (nextTransition != j3) {
                if (z) {
                    throw new IllegalInstantException(j, getID());
                }
                i = offset;
                j2 = j - ((long) i);
                if ((j ^ j2) < 0 || (((long) i) ^ j) >= 0) {
                    return j2;
                }
                throw new ArithmeticException("Subtracting time zone offset caused overflow");
            }
        }
        i = offset2;
        j2 = j - ((long) i);
        if ((j ^ j2) < 0) {
        }
        return j2;
    }

    public long getMillisKeepLocal(DateTimeZone dateTimeZone, long j) {
        DateTimeZone dateTimeZone2;
        if (dateTimeZone == null) {
            dateTimeZone2 = getDefault();
        } else {
            dateTimeZone2 = dateTimeZone;
        }
        return dateTimeZone2 == this ? j : dateTimeZone2.convertLocalToUTC(convertUTCToLocal(j), false, j);
    }

    public boolean isLocalDateTimeGap(LocalDateTime localDateTime) {
        if (isFixed()) {
            return false;
        }
        try {
            localDateTime.toDateTime(this);
            return false;
        } catch (IllegalInstantException e) {
            return true;
        }
    }

    public long adjustOffset(long j, boolean z) {
        long j2 = j - 10800000;
        long offset = (long) getOffset(j2);
        long offset2 = (long) getOffset(10800000 + j);
        if (offset <= offset2) {
            return j;
        }
        offset2 = offset - offset2;
        j2 = nextTransition(j2);
        offset = j2 - offset2;
        j2 += offset2;
        if (j < offset || j >= j2) {
            return j;
        }
        if (j - offset < offset2) {
            return z ? j + offset2 : j;
        } else {
            if (z) {
                return j;
            }
            return j - offset2;
        }
    }

    public TimeZone toTimeZone() {
        return TimeZone.getTimeZone(this.iID);
    }

    public int hashCode() {
        return getID().hashCode() + 57;
    }

    public String toString() {
        return getID();
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new Stub(this.iID);
    }
}
