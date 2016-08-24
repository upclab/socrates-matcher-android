package org.joda.time.tz;

import com.google.android.gms.appstate.AppStateClient;
import com.google.common.net.HttpHeaders;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.LenientChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class ZoneInfoCompiler {
    static Chronology cLenientISO;
    static DateTimeOfYear cStartOfYear;
    static ThreadLocal<Boolean> cVerbose;
    private List<String> iLinks;
    private Map<String, RuleSet> iRuleSets;
    private List<Zone> iZones;

    /* renamed from: org.joda.time.tz.ZoneInfoCompiler.1 */
    static class C03851 extends ThreadLocal<Boolean> {
        C03851() {
        }

        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    }

    static class DateTimeOfYear {
        public final boolean iAdvanceDayOfWeek;
        public final int iDayOfMonth;
        public final int iDayOfWeek;
        public final int iMillisOfDay;
        public final int iMonthOfYear;
        public final char iZoneChar;

        DateTimeOfYear() {
            this.iMonthOfYear = 1;
            this.iDayOfMonth = 1;
            this.iDayOfWeek = 0;
            this.iAdvanceDayOfWeek = false;
            this.iMillisOfDay = 0;
            this.iZoneChar = 'w';
        }

        DateTimeOfYear(StringTokenizer stringTokenizer) {
            int parseDayOfWeek;
            int i;
            int i2;
            char c;
            int i3;
            boolean z = true;
            boolean z2 = false;
            if (stringTokenizer.hasMoreTokens()) {
                int parseMonth = ZoneInfoCompiler.parseMonth(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    boolean z3;
                    String nextToken = stringTokenizer.nextToken();
                    if (nextToken.startsWith("last")) {
                        parseDayOfWeek = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(4));
                        i = -1;
                        z3 = false;
                    } else {
                        try {
                            parseDayOfWeek = 0;
                            i = Integer.parseInt(nextToken);
                            z3 = false;
                        } catch (NumberFormatException e) {
                            i = nextToken.indexOf(">=");
                            if (i > 0) {
                                i = Integer.parseInt(nextToken.substring(i + 2));
                                parseDayOfWeek = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(0, i));
                                z3 = true;
                            } else {
                                i = nextToken.indexOf("<=");
                                if (i > 0) {
                                    i = Integer.parseInt(nextToken.substring(i + 2));
                                    parseDayOfWeek = ZoneInfoCompiler.parseDayOfWeek(nextToken.substring(0, i));
                                    z3 = false;
                                } else {
                                    throw new IllegalArgumentException(nextToken);
                                }
                            }
                        }
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        String nextToken2 = stringTokenizer.nextToken();
                        char parseZoneChar = ZoneInfoCompiler.parseZoneChar(nextToken2.charAt(nextToken2.length() - 1));
                        if (nextToken2.equals("24:00")) {
                            LocalDate plusMonths = i == -1 ? new LocalDate(AppStateClient.STATUS_WRITE_SIZE_EXCEEDED, parseMonth, 1).plusMonths(1) : new LocalDate(AppStateClient.STATUS_WRITE_SIZE_EXCEEDED, parseMonth, i).plusDays(1);
                            if (i == -1) {
                                z = false;
                            }
                            int monthOfYear = plusMonths.getMonthOfYear();
                            i = plusMonths.getDayOfMonth();
                            parseDayOfWeek = (((parseDayOfWeek - 1) + 1) % 7) + 1;
                            i2 = monthOfYear;
                            c = parseZoneChar;
                            z2 = z;
                            i3 = false;
                        } else {
                            i3 = ZoneInfoCompiler.parseTime(nextToken2);
                            i2 = parseMonth;
                            z2 = z3;
                            c = parseZoneChar;
                        }
                    } else {
                        i3 = 0;
                        i2 = parseMonth;
                        z2 = z3;
                        c = 'w';
                    }
                } else {
                    c = 'w';
                    parseDayOfWeek = 0;
                    i = 1;
                    i2 = parseMonth;
                    i3 = 0;
                }
            } else {
                c = 'w';
                parseDayOfWeek = 0;
                i = 1;
                i2 = 1;
                i3 = 0;
            }
            this.iMonthOfYear = i2;
            this.iDayOfMonth = i;
            this.iDayOfWeek = parseDayOfWeek;
            this.iAdvanceDayOfWeek = z2;
            this.iMillisOfDay = i3;
            this.iZoneChar = c;
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str, int i, int i2, int i3) {
            dateTimeZoneBuilder.addRecurringSavings(str, i, i2, i3, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public void addCutover(DateTimeZoneBuilder dateTimeZoneBuilder, int i) {
            dateTimeZoneBuilder.addCutover(i, this.iZoneChar, this.iMonthOfYear, this.iDayOfMonth, this.iDayOfWeek, this.iAdvanceDayOfWeek, this.iMillisOfDay);
        }

        public String toString() {
            return "MonthOfYear: " + this.iMonthOfYear + "\n" + "DayOfMonth: " + this.iDayOfMonth + "\n" + "DayOfWeek: " + this.iDayOfWeek + "\n" + "AdvanceDayOfWeek: " + this.iAdvanceDayOfWeek + "\n" + "MillisOfDay: " + this.iMillisOfDay + "\n" + "ZoneChar: " + this.iZoneChar + "\n";
        }
    }

    private static class Rule {
        public final DateTimeOfYear iDateTimeOfYear;
        public final int iFromYear;
        public final String iLetterS;
        public final String iName;
        public final int iSaveMillis;
        public final int iToYear;
        public final String iType;

        Rule(StringTokenizer stringTokenizer) {
            this.iName = stringTokenizer.nextToken().intern();
            this.iFromYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), 0);
            this.iToYear = ZoneInfoCompiler.parseYear(stringTokenizer.nextToken(), this.iFromYear);
            if (this.iToYear < this.iFromYear) {
                throw new IllegalArgumentException();
            }
            this.iType = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iDateTimeOfYear = new DateTimeOfYear(stringTokenizer);
            this.iSaveMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iLetterS = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str) {
            DateTimeZoneBuilder dateTimeZoneBuilder2 = dateTimeZoneBuilder;
            this.iDateTimeOfYear.addRecurring(dateTimeZoneBuilder2, formatName(str), this.iSaveMillis, this.iFromYear, this.iToYear);
        }

        private String formatName(String str) {
            int indexOf = str.indexOf(47);
            if (indexOf <= 0) {
                indexOf = str.indexOf("%s");
                if (indexOf < 0) {
                    return str;
                }
                String substring = str.substring(0, indexOf);
                String substring2 = str.substring(indexOf + 2);
                if (this.iLetterS == null) {
                    substring2 = substring.concat(substring2);
                } else {
                    substring2 = substring + this.iLetterS + substring2;
                }
                return substring2.intern();
            } else if (this.iSaveMillis == 0) {
                return str.substring(0, indexOf).intern();
            } else {
                return str.substring(indexOf + 1).intern();
            }
        }

        public String toString() {
            return "[Rule]\nName: " + this.iName + "\n" + "FromYear: " + this.iFromYear + "\n" + "ToYear: " + this.iToYear + "\n" + "Type: " + this.iType + "\n" + this.iDateTimeOfYear + "SaveMillis: " + this.iSaveMillis + "\n" + "LetterS: " + this.iLetterS + "\n";
        }
    }

    private static class RuleSet {
        private List<Rule> iRules;

        RuleSet(Rule rule) {
            this.iRules = new ArrayList();
            this.iRules.add(rule);
        }

        void addRule(Rule rule) {
            if (rule.iName.equals(((Rule) this.iRules.get(0)).iName)) {
                this.iRules.add(rule);
                return;
            }
            throw new IllegalArgumentException("Rule name mismatch");
        }

        public void addRecurring(DateTimeZoneBuilder dateTimeZoneBuilder, String str) {
            for (int i = 0; i < this.iRules.size(); i++) {
                ((Rule) this.iRules.get(i)).addRecurring(dateTimeZoneBuilder, str);
            }
        }
    }

    private static class Zone {
        public final String iFormat;
        public final String iName;
        private Zone iNext;
        public final int iOffsetMillis;
        public final String iRules;
        public final DateTimeOfYear iUntilDateTimeOfYear;
        public final int iUntilYear;

        Zone(StringTokenizer stringTokenizer) {
            this(stringTokenizer.nextToken(), stringTokenizer);
        }

        private Zone(String str, StringTokenizer stringTokenizer) {
            this.iName = str.intern();
            this.iOffsetMillis = ZoneInfoCompiler.parseTime(stringTokenizer.nextToken());
            this.iRules = ZoneInfoCompiler.parseOptional(stringTokenizer.nextToken());
            this.iFormat = stringTokenizer.nextToken().intern();
            int i = Integer.MAX_VALUE;
            DateTimeOfYear startOfYear = ZoneInfoCompiler.getStartOfYear();
            if (stringTokenizer.hasMoreTokens()) {
                i = Integer.parseInt(stringTokenizer.nextToken());
                if (stringTokenizer.hasMoreTokens()) {
                    startOfYear = new DateTimeOfYear(stringTokenizer);
                }
            }
            this.iUntilYear = i;
            this.iUntilDateTimeOfYear = startOfYear;
        }

        void chain(StringTokenizer stringTokenizer) {
            if (this.iNext != null) {
                this.iNext.chain(stringTokenizer);
            } else {
                this.iNext = new Zone(this.iName, stringTokenizer);
            }
        }

        public void addToBuilder(DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            addToBuilder(this, dateTimeZoneBuilder, map);
        }

        private static void addToBuilder(Zone zone, DateTimeZoneBuilder dateTimeZoneBuilder, Map<String, RuleSet> map) {
            while (zone != null) {
                dateTimeZoneBuilder.setStandardOffset(zone.iOffsetMillis);
                if (zone.iRules == null) {
                    dateTimeZoneBuilder.setFixedSavings(zone.iFormat, 0);
                } else {
                    try {
                        dateTimeZoneBuilder.setFixedSavings(zone.iFormat, ZoneInfoCompiler.parseTime(zone.iRules));
                    } catch (Exception e) {
                        RuleSet ruleSet = (RuleSet) map.get(zone.iRules);
                        if (ruleSet == null) {
                            throw new IllegalArgumentException("Rules not found: " + zone.iRules);
                        }
                        ruleSet.addRecurring(dateTimeZoneBuilder, zone.iFormat);
                    }
                }
                if (zone.iUntilYear != Integer.MAX_VALUE) {
                    zone.iUntilDateTimeOfYear.addCutover(dateTimeZoneBuilder, zone.iUntilYear);
                    zone = zone.iNext;
                } else {
                    return;
                }
            }
        }

        public String toString() {
            String str = "[Zone]\nName: " + this.iName + "\n" + "OffsetMillis: " + this.iOffsetMillis + "\n" + "Rules: " + this.iRules + "\n" + "Format: " + this.iFormat + "\n" + "UntilYear: " + this.iUntilYear + "\n" + this.iUntilDateTimeOfYear;
            return this.iNext == null ? str : str + "...\n" + this.iNext.toString();
        }
    }

    static {
        cVerbose = new C03851();
    }

    public static boolean verbose() {
        return ((Boolean) cVerbose.get()).booleanValue();
    }

    public static void main(String[] strArr) throws Exception {
        File file = null;
        int i = 0;
        if (strArr.length == 0) {
            printUsage();
            return;
        }
        int i2 = 0;
        boolean z = false;
        File file2 = null;
        while (i2 < strArr.length) {
            File[] fileArr;
            if ("-src".equals(strArr[i2])) {
                i2++;
                file2 = new File(strArr[i2]);
            } else if ("-dst".equals(strArr[i2])) {
                i2++;
                file = new File(strArr[i2]);
            } else {
                try {
                    if ("-verbose".equals(strArr[i2])) {
                        z = true;
                    } else {
                        if ("-?".equals(strArr[i2])) {
                            printUsage();
                            return;
                        }
                        if (i2 < strArr.length) {
                            printUsage();
                        }
                        fileArr = new File[(strArr.length - i2)];
                        while (i2 < strArr.length) {
                            fileArr[i] = file2 != null ? new File(strArr[i2]) : new File(file2, strArr[i2]);
                            i2++;
                            i++;
                        }
                        cVerbose.set(Boolean.valueOf(z));
                        new ZoneInfoCompiler().compile(file, fileArr);
                        return;
                    }
                } catch (IndexOutOfBoundsException e) {
                    printUsage();
                    return;
                }
            }
            i2++;
        }
        if (i2 < strArr.length) {
            fileArr = new File[(strArr.length - i2)];
            while (i2 < strArr.length) {
                if (file2 != null) {
                }
                fileArr[i] = file2 != null ? new File(strArr[i2]) : new File(file2, strArr[i2]);
                i2++;
                i++;
            }
            cVerbose.set(Boolean.valueOf(z));
            new ZoneInfoCompiler().compile(file, fileArr);
            return;
        }
        printUsage();
    }

    private static void printUsage() {
        System.out.println("Usage: java org.joda.time.tz.ZoneInfoCompiler <options> <source files>");
        System.out.println("where possible options include:");
        System.out.println("  -src <directory>    Specify where to read source files");
        System.out.println("  -dst <directory>    Specify where to write generated files");
        System.out.println("  -verbose            Output verbosely (default false)");
    }

    static DateTimeOfYear getStartOfYear() {
        if (cStartOfYear == null) {
            cStartOfYear = new DateTimeOfYear();
        }
        return cStartOfYear;
    }

    static Chronology getLenientISOChronology() {
        if (cLenientISO == null) {
            cLenientISO = LenientChronology.getInstance(ISOChronology.getInstanceUTC());
        }
        return cLenientISO;
    }

    static void writeZoneInfoMap(DataOutputStream dataOutputStream, Map<String, DateTimeZone> map) throws IOException {
        Map hashMap = new HashMap(map.size());
        TreeMap treeMap = new TreeMap();
        short s = (short) 0;
        for (Entry entry : map.entrySet()) {
            short s2;
            String str = (String) entry.getKey();
            if (!hashMap.containsKey(str)) {
                Short valueOf = Short.valueOf(s);
                hashMap.put(str, valueOf);
                treeMap.put(valueOf, str);
                s = (short) (s + 1);
                if (s == (short) 0) {
                    throw new InternalError("Too many time zone ids");
                }
            }
            String id = ((DateTimeZone) entry.getValue()).getID();
            if (hashMap.containsKey(id)) {
                s2 = s;
            } else {
                Short valueOf2 = Short.valueOf(s);
                hashMap.put(id, valueOf2);
                treeMap.put(valueOf2, id);
                s2 = (short) (s + 1);
                if (s2 == (short) 0) {
                    throw new InternalError("Too many time zone ids");
                }
            }
            s = s2;
        }
        dataOutputStream.writeShort(treeMap.size());
        for (String id2 : treeMap.values()) {
            dataOutputStream.writeUTF(id2);
        }
        dataOutputStream.writeShort(map.size());
        for (Entry entry2 : map.entrySet()) {
            dataOutputStream.writeShort(((Short) hashMap.get((String) entry2.getKey())).shortValue());
            dataOutputStream.writeShort(((Short) hashMap.get(((DateTimeZone) entry2.getValue()).getID())).shortValue());
        }
    }

    static int parseYear(String str, int i) {
        String toLowerCase = str.toLowerCase();
        if (toLowerCase.equals("minimum") || toLowerCase.equals("min")) {
            return Integer.MIN_VALUE;
        }
        if (toLowerCase.equals("maximum") || toLowerCase.equals("max")) {
            return Integer.MAX_VALUE;
        }
        return !toLowerCase.equals("only") ? Integer.parseInt(toLowerCase) : i;
    }

    static int parseMonth(String str) {
        DateTimeField monthOfYear = ISOChronology.getInstanceUTC().monthOfYear();
        return monthOfYear.get(monthOfYear.set(0, str, Locale.ENGLISH));
    }

    static int parseDayOfWeek(String str) {
        DateTimeField dayOfWeek = ISOChronology.getInstanceUTC().dayOfWeek();
        return dayOfWeek.get(dayOfWeek.set(0, str, Locale.ENGLISH));
    }

    static String parseOptional(String str) {
        return str.equals("-") ? null : str;
    }

    static int parseTime(String str) {
        DateTimeFormatter hourMinuteSecondFraction = ISODateTimeFormat.hourMinuteSecondFraction();
        Object mutableDateTime = new MutableDateTime(0, getLenientISOChronology());
        int i = 0;
        if (str.startsWith("-")) {
            i = 1;
        }
        if (hourMinuteSecondFraction.parseInto(mutableDateTime, str, i) == (i ^ -1)) {
            throw new IllegalArgumentException(str);
        }
        int millis = (int) mutableDateTime.getMillis();
        if (i == 1) {
            return -millis;
        }
        return millis;
    }

    static char parseZoneChar(char c) {
        switch (c) {
            case 'G':
            case 'U':
            case 'Z':
            case 'g':
            case 'u':
            case 'z':
                return 'u';
            case 'S':
            case 's':
                return 's';
            default:
                return 'w';
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean test(java.lang.String r14, org.joda.time.DateTimeZone r15) {
        /*
        r0 = r15.getID();
        r0 = r14.equals(r0);
        if (r0 != 0) goto L_0x000c;
    L_0x000a:
        r0 = 1;
    L_0x000b:
        return r0;
    L_0x000c:
        r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r0 = r0.year();
        r1 = 0;
        r3 = 1850; // 0x73a float:2.592E-42 double:9.14E-321;
        r2 = r0.set(r1, r3);
        r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r0 = r0.year();
        r4 = 0;
        r1 = 2050; // 0x802 float:2.873E-42 double:1.013E-320;
        r7 = r0.set(r4, r1);
        r1 = r15.getOffset(r2);
        r0 = r15.getNameKey(r2);
        r9 = new java.util.ArrayList;
        r9.<init>();
        r12 = r2;
        r3 = r12;
        r2 = r1;
    L_0x003c:
        r5 = r15.nextTransition(r3);
        r1 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r1 == 0) goto L_0x0048;
    L_0x0044:
        r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r1 <= 0) goto L_0x007f;
    L_0x0048:
        r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r0 = r0.year();
        r1 = 0;
        r3 = 2050; // 0x802 float:2.873E-42 double:1.013E-320;
        r1 = r0.set(r1, r3);
        r0 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r0 = r0.year();
        r3 = 0;
        r5 = 1850; // 0x73a float:2.592E-42 double:9.14E-321;
        r6 = r0.set(r3, r5);
        r0 = r9.size();
        r2 = r1;
    L_0x006d:
        r1 = r0 + -1;
        if (r1 < 0) goto L_0x007d;
    L_0x0071:
        r4 = r15.previousTransition(r2);
        r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r0 == 0) goto L_0x007d;
    L_0x0079:
        r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r0 >= 0) goto L_0x011a;
    L_0x007d:
        r0 = 1;
        goto L_0x000b;
    L_0x007f:
        r3 = r15.getOffset(r5);
        r1 = r15.getNameKey(r5);
        if (r2 != r3) goto L_0x00c1;
    L_0x0089:
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x00c1;
    L_0x008f:
        r0 = java.lang.System.out;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "*d* Error in ";
        r1 = r1.append(r2);
        r2 = r15.getID();
        r1 = r1.append(r2);
        r2 = " ";
        r1 = r1.append(r2);
        r2 = new org.joda.time.DateTime;
        r3 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r2.<init>(r5, r3);
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.println(r1);
        r0 = 0;
        goto L_0x000b;
    L_0x00c1:
        if (r1 == 0) goto L_0x00d2;
    L_0x00c3:
        r0 = r1.length();
        r2 = 3;
        if (r0 >= r2) goto L_0x010e;
    L_0x00ca:
        r0 = "??";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x010e;
    L_0x00d2:
        r0 = java.lang.System.out;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "*s* Error in ";
        r2 = r2.append(r3);
        r3 = r15.getID();
        r2 = r2.append(r3);
        r3 = " ";
        r2 = r2.append(r3);
        r3 = new org.joda.time.DateTime;
        r4 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r3.<init>(r5, r4);
        r2 = r2.append(r3);
        r3 = ", nameKey=";
        r2 = r2.append(r3);
        r1 = r2.append(r1);
        r1 = r1.toString();
        r0.println(r1);
        r0 = 0;
        goto L_0x000b;
    L_0x010e:
        r0 = java.lang.Long.valueOf(r5);
        r9.add(r0);
        r0 = r1;
        r2 = r3;
        r3 = r5;
        goto L_0x003c;
    L_0x011a:
        r0 = r9.get(r1);
        r0 = (java.lang.Long) r0;
        r2 = r0.longValue();
        r10 = 1;
        r10 = r2 - r10;
        r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x0174;
    L_0x012c:
        r0 = java.lang.System.out;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r6 = "*r* Error in ";
        r1 = r1.append(r6);
        r6 = r15.getID();
        r1 = r1.append(r6);
        r6 = " ";
        r1 = r1.append(r6);
        r6 = new org.joda.time.DateTime;
        r7 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r6.<init>(r4, r7);
        r1 = r1.append(r6);
        r4 = " != ";
        r1 = r1.append(r4);
        r4 = new org.joda.time.DateTime;
        r5 = 1;
        r2 = r2 - r5;
        r5 = org.joda.time.chrono.ISOChronology.getInstanceUTC();
        r4.<init>(r2, r5);
        r1 = r1.append(r4);
        r1 = r1.toString();
        r0.println(r1);
        r0 = 0;
        goto L_0x000b;
    L_0x0174:
        r0 = r1;
        r2 = r4;
        goto L_0x006d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.ZoneInfoCompiler.test(java.lang.String, org.joda.time.DateTimeZone):boolean");
    }

    public ZoneInfoCompiler() {
        this.iRuleSets = new HashMap();
        this.iZones = new ArrayList();
        this.iLinks = new ArrayList();
    }

    public Map<String, DateTimeZone> compile(File file, File[] fileArr) throws IOException {
        if (fileArr != null) {
            for (File fileReader : fileArr) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileReader));
                parseDataFile(bufferedReader);
                bufferedReader.close();
            }
        }
        if (file != null) {
            if (!file.exists() && !file.mkdirs()) {
                throw new IOException("Destination directory doesn't exist and cannot be created: " + file);
            } else if (!file.isDirectory()) {
                throw new IOException("Destination is not a directory: " + file);
            }
        }
        Map<String, DateTimeZone> treeMap = new TreeMap();
        System.out.println("Writing zoneinfo files");
        for (int i = 0; i < this.iZones.size(); i++) {
            DateTimeZone readFrom;
            Zone zone = (Zone) this.iZones.get(i);
            DateTimeZoneBuilder dateTimeZoneBuilder = new DateTimeZoneBuilder();
            zone.addToBuilder(dateTimeZoneBuilder, this.iRuleSets);
            DateTimeZone toDateTimeZone = dateTimeZoneBuilder.toDateTimeZone(zone.iName, true);
            if (test(toDateTimeZone.getID(), toDateTimeZone)) {
                treeMap.put(toDateTimeZone.getID(), toDateTimeZone);
                if (file == null) {
                    continue;
                } else {
                    if (verbose()) {
                        System.out.println("Writing " + toDateTimeZone.getID());
                    }
                    File file2 = new File(file, toDateTimeZone.getID());
                    if (!file2.getParentFile().exists()) {
                        file2.getParentFile().mkdirs();
                    }
                    OutputStream fileOutputStream = new FileOutputStream(file2);
                    try {
                        dateTimeZoneBuilder.writeTo(zone.iName, fileOutputStream);
                        InputStream fileInputStream = new FileInputStream(file2);
                        readFrom = DateTimeZoneBuilder.readFrom(fileInputStream, toDateTimeZone.getID());
                        fileInputStream.close();
                        if (!toDateTimeZone.equals(readFrom)) {
                            System.out.println("*e* Error in " + toDateTimeZone.getID() + ": Didn't read properly from file");
                        }
                    } finally {
                        fileOutputStream.close();
                    }
                }
            }
        }
        for (int i2 = 0; i2 < 2; i2++) {
            for (int i3 = 0; i3 < this.iLinks.size(); i3 += 2) {
                String str = (String) this.iLinks.get(i3);
                String str2 = (String) this.iLinks.get(i3 + 1);
                readFrom = (DateTimeZone) treeMap.get(str);
                if (readFrom != null) {
                    treeMap.put(str2, readFrom);
                } else if (i2 > 0) {
                    System.out.println("Cannot find time zone '" + str + "' to link alias '" + str2 + "' to");
                }
            }
        }
        if (file != null) {
            System.out.println("Writing ZoneInfoMap");
            File file3 = new File(file, "ZoneInfoMap");
            if (!file3.getParentFile().exists()) {
                file3.getParentFile().mkdirs();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file3));
            try {
                Map treeMap2 = new TreeMap(String.CASE_INSENSITIVE_ORDER);
                treeMap2.putAll(treeMap);
                writeZoneInfoMap(dataOutputStream, treeMap2);
            } finally {
                dataOutputStream.close();
            }
        }
        return treeMap;
    }

    public void parseDataFile(BufferedReader bufferedReader) throws IOException {
        Zone zone = null;
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                break;
            }
            String trim = readLine.trim();
            if (!(trim.length() == 0 || trim.charAt(0) == '#')) {
                int indexOf = readLine.indexOf(35);
                if (indexOf >= 0) {
                    readLine = readLine.substring(0, indexOf);
                }
                StringTokenizer stringTokenizer = new StringTokenizer(readLine, " \t");
                if (!Character.isWhitespace(readLine.charAt(0)) || !stringTokenizer.hasMoreTokens()) {
                    if (zone != null) {
                        this.iZones.add(zone);
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        String nextToken = stringTokenizer.nextToken();
                        if (nextToken.equalsIgnoreCase("Rule")) {
                            Rule rule = new Rule(stringTokenizer);
                            RuleSet ruleSet = (RuleSet) this.iRuleSets.get(rule.iName);
                            if (ruleSet == null) {
                                this.iRuleSets.put(rule.iName, new RuleSet(rule));
                            } else {
                                ruleSet.addRule(rule);
                            }
                            zone = null;
                        } else if (nextToken.equalsIgnoreCase("Zone")) {
                            zone = new Zone(stringTokenizer);
                        } else if (nextToken.equalsIgnoreCase(HttpHeaders.LINK)) {
                            this.iLinks.add(stringTokenizer.nextToken());
                            this.iLinks.add(stringTokenizer.nextToken());
                            zone = null;
                        } else {
                            System.out.println("Unknown line: " + readLine);
                        }
                    }
                    zone = null;
                } else if (zone != null) {
                    zone.chain(stringTokenizer);
                }
            }
        }
        if (zone != null) {
            this.iZones.add(zone);
        }
    }
}
