package org.joda.time.format;

import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.MutableDateTime;
import org.joda.time.MutableDateTime.Property;
import org.joda.time.ReadablePartial;

public class DateTimeFormatterBuilder {
    private ArrayList<Object> iElementPairs;
    private Object iFormatter;

    static class CharacterLiteral implements DateTimePrinter, DateTimeParser {
        private final char iValue;

        CharacterLiteral(char c) {
            this.iValue = c;
        }

        public int estimatePrintedLength() {
            return 1;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        public int estimateParsedLength() {
            return 1;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            if (i >= str.length()) {
                return i ^ -1;
            }
            char charAt = str.charAt(i);
            char c = this.iValue;
            if (charAt != c) {
                charAt = Character.toUpperCase(charAt);
                c = Character.toUpperCase(c);
                if (!(charAt == c || Character.toLowerCase(charAt) == Character.toLowerCase(c))) {
                    return i ^ -1;
                }
            }
            return i + 1;
        }
    }

    static class Composite implements DateTimePrinter, DateTimeParser {
        private final int iParsedLengthEstimate;
        private final DateTimeParser[] iParsers;
        private final int iPrintedLengthEstimate;
        private final DateTimePrinter[] iPrinters;

        Composite(List<Object> list) {
            int i;
            int i2;
            int i3 = 0;
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.contains(null) || arrayList.isEmpty()) {
                this.iPrinters = null;
                this.iPrintedLengthEstimate = 0;
            } else {
                int size = arrayList.size();
                this.iPrinters = new DateTimePrinter[size];
                i = 0;
                for (i2 = 0; i2 < size; i2++) {
                    DateTimePrinter dateTimePrinter = (DateTimePrinter) arrayList.get(i2);
                    i += dateTimePrinter.estimatePrintedLength();
                    this.iPrinters[i2] = dateTimePrinter;
                }
                this.iPrintedLengthEstimate = i;
            }
            if (arrayList2.contains(null) || arrayList2.isEmpty()) {
                this.iParsers = null;
                this.iParsedLengthEstimate = 0;
                return;
            }
            i = arrayList2.size();
            this.iParsers = new DateTimeParser[i];
            i2 = 0;
            while (i3 < i) {
                DateTimeParser dateTimeParser = (DateTimeParser) arrayList2.get(i3);
                i2 += dateTimeParser.estimateParsedLength();
                this.iParsers[i3] = dateTimeParser;
                i3++;
            }
            this.iParsedLengthEstimate = i2;
        }

        public int estimatePrintedLength() {
            return this.iPrintedLengthEstimate;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            Locale locale2;
            if (locale == null) {
                locale2 = Locale.getDefault();
            } else {
                locale2 = locale;
            }
            for (DateTimePrinter printTo : dateTimePrinterArr) {
                printTo.printTo(stringBuffer, j, chronology, i, dateTimeZone, locale2);
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            Locale locale2;
            if (locale == null) {
                locale2 = Locale.getDefault();
            } else {
                locale2 = locale;
            }
            for (DateTimePrinter printTo : dateTimePrinterArr) {
                printTo.printTo(writer, j, chronology, i, dateTimeZone, locale2);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            for (DateTimePrinter printTo : dateTimePrinterArr) {
                printTo.printTo(stringBuffer, readablePartial, locale);
            }
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            DateTimePrinter[] dateTimePrinterArr = this.iPrinters;
            if (dateTimePrinterArr == null) {
                throw new UnsupportedOperationException();
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            for (DateTimePrinter printTo : dateTimePrinterArr) {
                printTo.printTo(writer, readablePartial, locale);
            }
        }

        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            DateTimeParser[] dateTimeParserArr = this.iParsers;
            if (dateTimeParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = dateTimeParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2++) {
                i = dateTimeParserArr[i2].parseInto(dateTimeParserBucket, str, i);
            }
            return i;
        }

        boolean isPrinter() {
            return this.iPrinters != null;
        }

        boolean isParser() {
            return this.iParsers != null;
        }

        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += 2) {
                Object obj = list.get(i);
                if (obj instanceof Composite) {
                    addArrayToList(list2, ((Composite) obj).iPrinters);
                } else {
                    list2.add(obj);
                }
                obj = list.get(i + 1);
                if (obj instanceof Composite) {
                    addArrayToList(list3, ((Composite) obj).iParsers);
                } else {
                    list3.add(obj);
                }
            }
        }

        private void addArrayToList(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (Object add : objArr) {
                    list.add(add);
                }
            }
        }
    }

    static class Fraction implements DateTimePrinter, DateTimeParser {
        private final DateTimeFieldType iFieldType;
        protected int iMaxDigits;
        protected int iMinDigits;

        protected Fraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
            this.iFieldType = dateTimeFieldType;
            if (i2 > 18) {
                i2 = 18;
            }
            this.iMinDigits = i;
            this.iMaxDigits = i2;
        }

        public int estimatePrintedLength() {
            return this.iMaxDigits;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                printTo(stringBuffer, null, j, chronology);
            } catch (IOException e) {
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            printTo(null, writer, j, chronology);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            try {
                printTo(stringBuffer, null, readablePartial.getChronology().set(readablePartial, 0), readablePartial.getChronology());
            } catch (IOException e) {
            }
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            printTo(null, writer, readablePartial.getChronology().set(readablePartial, 0), readablePartial.getChronology());
        }

        protected void printTo(StringBuffer stringBuffer, Writer writer, long j, Chronology chronology) throws IOException {
            DateTimeField field = this.iFieldType.getField(chronology);
            int i = this.iMinDigits;
            try {
                long remainder = field.remainder(j);
                if (remainder != 0) {
                    String num;
                    long[] fractionData = getFractionData(remainder, field);
                    long j2 = fractionData[0];
                    int i2 = (int) fractionData[1];
                    if ((2147483647L & j2) == j2) {
                        num = Integer.toString((int) j2);
                    } else {
                        num = Long.toString(j2);
                    }
                    int length = num.length();
                    while (length < i2) {
                        if (stringBuffer != null) {
                            stringBuffer.append('0');
                        } else {
                            writer.write(48);
                        }
                        i--;
                        i2--;
                    }
                    if (i < i2) {
                        while (i < i2 && length > 1 && num.charAt(length - 1) == '0') {
                            i2--;
                            length--;
                        }
                        if (length < num.length()) {
                            if (stringBuffer != null) {
                                for (i2 = 0; i2 < length; i2++) {
                                    stringBuffer.append(num.charAt(i2));
                                }
                                return;
                            }
                            for (i2 = 0; i2 < length; i2++) {
                                writer.write(num.charAt(i2));
                            }
                            return;
                        }
                    }
                    if (stringBuffer != null) {
                        stringBuffer.append(num);
                    } else {
                        writer.write(num);
                    }
                } else if (stringBuffer != null) {
                    while (true) {
                        i--;
                        if (i >= 0) {
                            stringBuffer.append('0');
                        } else {
                            return;
                        }
                    }
                } else {
                    while (true) {
                        i--;
                        if (i >= 0) {
                            writer.write(48);
                        } else {
                            return;
                        }
                    }
                }
            } catch (RuntimeException e) {
                if (stringBuffer != null) {
                    DateTimeFormatterBuilder.appendUnknownString(stringBuffer, i);
                } else {
                    DateTimeFormatterBuilder.printUnknownString(writer, i);
                }
            }
        }

        private long[] getFractionData(long j, DateTimeField dateTimeField) {
            long unitMillis = dateTimeField.getDurationField().getUnitMillis();
            int i = this.iMaxDigits;
            while (true) {
                long j2;
                switch (i) {
                    case Value.TYPE_FIELD_NUMBER /*1*/:
                        j2 = 10;
                        break;
                    case Value.STRING_FIELD_NUMBER /*2*/:
                        j2 = 100;
                        break;
                    case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                        j2 = 1000;
                        break;
                    case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                        j2 = 10000;
                        break;
                    case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                        j2 = 100000;
                        break;
                    case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                        j2 = 1000000;
                        break;
                    case Value.FUNCTION_ID_FIELD_NUMBER /*7*/:
                        j2 = 10000000;
                        break;
                    case Value.INTEGER_FIELD_NUMBER /*8*/:
                        j2 = 100000000;
                        break;
                    case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                        j2 = 1000000000;
                        break;
                    case Value.ESCAPING_FIELD_NUMBER /*10*/:
                        j2 = 10000000000L;
                        break;
                    case Value.TEMPLATE_TOKEN_FIELD_NUMBER /*11*/:
                        j2 = 100000000000L;
                        break;
                    case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                        j2 = 1000000000000L;
                        break;
                    case Resource.VERSION_FIELD_NUMBER /*13*/:
                        j2 = 10000000000000L;
                        break;
                    case Resource.LIVE_JS_CACHE_OPTION_FIELD_NUMBER /*14*/:
                        j2 = 100000000000000L;
                        break;
                    case Resource.REPORTING_SAMPLE_RATE_FIELD_NUMBER /*15*/:
                        j2 = 1000000000000000L;
                        break;
                    case Resource.USAGE_CONTEXT_FIELD_NUMBER /*16*/:
                        j2 = 10000000000000000L;
                        break;
                    case Resource.RESOURCE_FORMAT_VERSION_FIELD_NUMBER /*17*/:
                        j2 = 100000000000000000L;
                        break;
                    case Resource.ENABLE_AUTO_EVENT_TRACKING_FIELD_NUMBER /*18*/:
                        j2 = 1000000000000000000L;
                        break;
                    default:
                        j2 = 1;
                        break;
                }
                if ((unitMillis * j2) / j2 == unitMillis) {
                    return new long[]{(j2 * j) / unitMillis, (long) i};
                }
                i--;
            }
        }

        public int estimateParsedLength() {
            return this.iMaxDigits;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r12, java.lang.String r13, int r14) {
            /*
            r11 = this;
            r9 = 10;
            r0 = r11.iFieldType;
            r1 = r12.getChronology();
            r5 = r0.getField(r1);
            r0 = r11.iMaxDigits;
            r1 = r13.length();
            r1 = r1 - r14;
            r6 = java.lang.Math.min(r0, r1);
            r3 = 0;
            r0 = r5.getDurationField();
            r0 = r0.getUnitMillis();
            r1 = r0 * r9;
            r0 = 0;
        L_0x0024:
            if (r0 >= r6) goto L_0x0034;
        L_0x0026:
            r7 = r14 + r0;
            r7 = r13.charAt(r7);
            r8 = 48;
            if (r7 < r8) goto L_0x0034;
        L_0x0030:
            r8 = 57;
            if (r7 <= r8) goto L_0x003b;
        L_0x0034:
            r1 = r3 / r9;
            if (r0 != 0) goto L_0x0044;
        L_0x0038:
            r0 = r14 ^ -1;
        L_0x003a:
            return r0;
        L_0x003b:
            r0 = r0 + 1;
            r1 = r1 / r9;
            r7 = r7 + -48;
            r7 = (long) r7;
            r7 = r7 * r1;
            r3 = r3 + r7;
            goto L_0x0024;
        L_0x0044:
            r3 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
            r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));
            if (r3 <= 0) goto L_0x004e;
        L_0x004b:
            r0 = r14 ^ -1;
            goto L_0x003a;
        L_0x004e:
            r3 = new org.joda.time.field.PreciseDateTimeField;
            r4 = org.joda.time.DateTimeFieldType.millisOfSecond();
            r6 = org.joda.time.field.MillisDurationField.INSTANCE;
            r5 = r5.getDurationField();
            r3.<init>(r4, r6, r5);
            r1 = (int) r1;
            r12.saveField(r3, r1);
            r0 = r0 + r14;
            goto L_0x003a;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.Fraction.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.String, int):int");
        }
    }

    static class MatchingParser implements DateTimeParser {
        private final int iParsedLengthEstimate;
        private final DateTimeParser[] iParsers;

        MatchingParser(DateTimeParser[] dateTimeParserArr) {
            this.iParsers = dateTimeParserArr;
            int i = 0;
            int length = dateTimeParserArr.length;
            while (true) {
                int i2 = length - 1;
                if (i2 >= 0) {
                    DateTimeParser dateTimeParser = dateTimeParserArr[i2];
                    if (dateTimeParser != null) {
                        length = dateTimeParser.estimateParsedLength();
                        if (length > i) {
                            i = length;
                            length = i2;
                        }
                    }
                    length = i;
                    i = length;
                    length = i2;
                } else {
                    this.iParsedLengthEstimate = i;
                    return;
                }
            }
        }

        public int estimateParsedLength() {
            return this.iParsedLengthEstimate;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            Object saveState;
            DateTimeParser[] dateTimeParserArr = this.iParsers;
            int length = dateTimeParserArr.length;
            Object saveState2 = dateTimeParserBucket.saveState();
            Object obj = null;
            int i2 = 0;
            int i3 = i;
            int i4 = i;
            while (i2 < length) {
                DateTimeParser dateTimeParser = dateTimeParserArr[i2];
                if (dateTimeParser != null) {
                    int i5;
                    int parseInto = dateTimeParser.parseInto(dateTimeParserBucket, str, i);
                    if (parseInto >= i) {
                        if (parseInto > i4) {
                            if (parseInto >= str.length() || i2 + 1 >= length || dateTimeParserArr[i2 + 1] == null) {
                                return parseInto;
                            }
                            saveState = dateTimeParserBucket.saveState();
                            i5 = parseInto;
                        }
                        saveState = obj;
                        i5 = i4;
                    } else {
                        if (parseInto < 0) {
                            int i6 = parseInto ^ -1;
                            if (i6 > i3) {
                                i3 = i6;
                                saveState = obj;
                                i5 = i4;
                            }
                        }
                        saveState = obj;
                        i5 = i4;
                    }
                    dateTimeParserBucket.restoreState(saveState2);
                    i2++;
                    i4 = i5;
                    obj = saveState;
                } else if (i4 <= i) {
                    return i;
                } else {
                    saveState = 1;
                    if (i4 > i && (i4 != i || r1 == null)) {
                        return i3 ^ -1;
                    }
                    if (obj != null) {
                        dateTimeParserBucket.restoreState(obj);
                    }
                    return i4;
                }
            }
            saveState = null;
            if (i4 > i) {
            }
            if (obj != null) {
                dateTimeParserBucket.restoreState(obj);
            }
            return i4;
        }
    }

    static abstract class NumberFormatter implements DateTimePrinter, DateTimeParser {
        protected final DateTimeFieldType iFieldType;
        protected final int iMaxParsedDigits;
        protected final boolean iSigned;

        NumberFormatter(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.iFieldType = dateTimeFieldType;
            this.iMaxParsedDigits = i;
            this.iSigned = z;
        }

        public int estimateParsedLength() {
            return this.iMaxParsedDigits;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            Object obj;
            Object obj2 = null;
            int min = Math.min(this.iMaxParsedDigits, str.length() - i);
            int i2 = i;
            int i3 = 0;
            while (i3 < min) {
                char charAt = str.charAt(i2 + i3);
                if (i3 != 0 || ((charAt != '-' && charAt != '+') || !this.iSigned)) {
                    if (charAt < '0') {
                        break;
                    } else if (charAt > '9') {
                        obj = obj2;
                        break;
                    } else {
                        i3++;
                    }
                } else {
                    obj2 = charAt == '-' ? 1 : null;
                    if (i3 + 1 >= min) {
                        break;
                    }
                    charAt = str.charAt((i2 + i3) + 1);
                    if (charAt < '0') {
                        break;
                    } else if (charAt > '9') {
                        obj = obj2;
                        break;
                    } else {
                        if (obj2 != null) {
                            i3++;
                        } else {
                            i2++;
                        }
                        min = Math.min(min + 1, str.length() - i2);
                    }
                }
            }
            obj = obj2;
            if (i3 == 0) {
                return i2 ^ -1;
            }
            int i4;
            if (i3 >= 9) {
                i4 = i2 + i3;
                i3 = Integer.parseInt(str.substring(i2, i4));
                i2 = i4;
            } else {
                int i5;
                if (obj != null) {
                    i5 = i2 + 1;
                } else {
                    i5 = i2;
                }
                try {
                    i2 += i3;
                    i3 = str.charAt(i5) - 48;
                    for (i4 = i5 + 1; i4 < i2; i4++) {
                        i3 = (str.charAt(i4) + ((i3 << 3) + (i3 << 1))) - 48;
                    }
                    if (obj != null) {
                        i3 = -i3;
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    return i2 ^ -1;
                }
            }
            dateTimeParserBucket.saveField(this.iFieldType, i3);
            return i2;
        }
    }

    static class StringLiteral implements DateTimePrinter, DateTimeParser {
        private final String iValue;

        StringLiteral(String str) {
            this.iValue = str;
        }

        public int estimatePrintedLength() {
            return this.iValue.length();
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            stringBuffer.append(this.iValue);
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            writer.write(this.iValue);
        }

        public int estimateParsedLength() {
            return this.iValue.length();
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            if (str.regionMatches(true, i, this.iValue, 0, this.iValue.length())) {
                return this.iValue.length() + i;
            }
            return i ^ -1;
        }
    }

    static class TextField implements DateTimePrinter, DateTimeParser {
        private static Map<Locale, Map<DateTimeFieldType, Object[]>> cParseCache;
        private final DateTimeFieldType iFieldType;
        private final boolean iShort;

        static {
            cParseCache = new HashMap();
        }

        TextField(DateTimeFieldType dateTimeFieldType, boolean z) {
            this.iFieldType = dateTimeFieldType;
            this.iShort = z;
        }

        public int estimatePrintedLength() {
            return this.iShort ? 6 : 20;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                stringBuffer.append(print(j, chronology, locale));
            } catch (RuntimeException e) {
                stringBuffer.append('\ufffd');
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                writer.write(print(j, chronology, locale));
            } catch (RuntimeException e) {
                writer.write(65533);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            try {
                stringBuffer.append(print(readablePartial, locale));
            } catch (RuntimeException e) {
                stringBuffer.append('\ufffd');
            }
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            try {
                writer.write(print(readablePartial, locale));
            } catch (RuntimeException e) {
                writer.write(65533);
            }
        }

        private String print(long j, Chronology chronology, Locale locale) {
            DateTimeField field = this.iFieldType.getField(chronology);
            if (this.iShort) {
                return field.getAsShortText(j, locale);
            }
            return field.getAsText(j, locale);
        }

        private String print(ReadablePartial readablePartial, Locale locale) {
            if (!readablePartial.isSupported(this.iFieldType)) {
                return "\ufffd";
            }
            DateTimeField field = this.iFieldType.getField(readablePartial.getChronology());
            if (this.iShort) {
                return field.getAsShortText(readablePartial, locale);
            }
            return field.getAsText(readablePartial, locale);
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            Locale locale = dateTimeParserBucket.getLocale();
            synchronized (cParseCache) {
                Map map;
                Set hashSet;
                int i2;
                Map map2 = (Map) cParseCache.get(locale);
                if (map2 == null) {
                    HashMap hashMap = new HashMap();
                    cParseCache.put(locale, hashMap);
                    map = hashMap;
                } else {
                    map = map2;
                }
                Object[] objArr = (Object[]) map.get(this.iFieldType);
                if (objArr == null) {
                    hashSet = new HashSet(32);
                    Property property = new MutableDateTime(0, DateTimeZone.UTC).property(this.iFieldType);
                    int minimumValueOverall = property.getMinimumValueOverall();
                    int maximumValueOverall = property.getMaximumValueOverall();
                    if (maximumValueOverall - minimumValueOverall > 32) {
                        i2 = i ^ -1;
                        return i2;
                    }
                    i2 = property.getMaximumTextLength(locale);
                    while (minimumValueOverall <= maximumValueOverall) {
                        property.set(minimumValueOverall);
                        hashSet.add(property.getAsShortText(locale));
                        hashSet.add(property.getAsShortText(locale).toLowerCase(locale));
                        hashSet.add(property.getAsShortText(locale).toUpperCase(locale));
                        hashSet.add(property.getAsText(locale));
                        hashSet.add(property.getAsText(locale).toLowerCase(locale));
                        hashSet.add(property.getAsText(locale).toUpperCase(locale));
                        minimumValueOverall++;
                    }
                    if ("en".equals(locale.getLanguage()) && this.iFieldType == DateTimeFieldType.era()) {
                        hashSet.add("BCE");
                        hashSet.add("bce");
                        hashSet.add("CE");
                        hashSet.add("ce");
                        i2 = 3;
                    }
                    map.put(this.iFieldType, new Object[]{hashSet, Integer.valueOf(i2)});
                } else {
                    hashSet = (Set) objArr[0];
                    i2 = ((Integer) objArr[1]).intValue();
                }
                for (i2 = Math.min(str.length(), i2 + i); i2 > i; i2--) {
                    String substring = str.substring(i, i2);
                    if (hashSet.contains(substring)) {
                        dateTimeParserBucket.saveField(this.iFieldType, substring, locale);
                        return i2;
                    }
                }
                return i ^ -1;
            }
        }
    }

    enum TimeZoneId implements DateTimePrinter, DateTimeParser {
        INSTANCE;
        
        static final Set<String> ALL_IDS;
        static final int MAX_LENGTH;

        static {
            ALL_IDS = DateTimeZone.getAvailableIDs();
            int i = 0;
            for (String length : ALL_IDS) {
                i = Math.max(i, length.length());
            }
            MAX_LENGTH = i;
        }

        public int estimatePrintedLength() {
            return MAX_LENGTH;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(dateTimeZone != null ? dateTimeZone.getID() : StringUtils.EMPTY);
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(dateTimeZone != null ? dateTimeZone.getID() : StringUtils.EMPTY);
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return MAX_LENGTH;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            String substring = str.substring(i);
            String str2 = null;
            for (String str3 : ALL_IDS) {
                String str32;
                if (!substring.startsWith(str32) || (str2 != null && str32.length() <= str2.length())) {
                    str32 = str2;
                }
                str2 = str32;
            }
            if (str2 == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone(DateTimeZone.forID(str2));
            return str2.length() + i;
        }
    }

    static class TimeZoneName implements DateTimePrinter, DateTimeParser {
        static final int LONG_NAME = 0;
        static final int SHORT_NAME = 1;
        private final Map<String, DateTimeZone> iParseLookup;
        private final int iType;

        TimeZoneName(int i, Map<String, DateTimeZone> map) {
            this.iType = i;
            this.iParseLookup = map;
        }

        public int estimatePrintedLength() {
            return this.iType == SHORT_NAME ? 4 : 20;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            stringBuffer.append(print(j - ((long) i), dateTimeZone, locale));
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            writer.write(print(j - ((long) i), dateTimeZone, locale));
        }

        private String print(long j, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone == null) {
                return StringUtils.EMPTY;
            }
            switch (this.iType) {
                case LONG_NAME /*0*/:
                    return dateTimeZone.getName(j, locale);
                case SHORT_NAME /*1*/:
                    return dateTimeZone.getShortName(j, locale);
                default:
                    return StringUtils.EMPTY;
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return this.iType == SHORT_NAME ? 4 : 20;
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            Map map = this.iParseLookup;
            Map defaultTimeZoneNames = map != null ? map : DateTimeUtils.getDefaultTimeZoneNames();
            String substring = str.substring(i);
            String str2 = null;
            for (String str3 : defaultTimeZoneNames.keySet()) {
                String str32;
                if (!substring.startsWith(str32) || (str2 != null && str32.length() <= str2.length())) {
                    str32 = str2;
                }
                str2 = str32;
            }
            if (str2 == null) {
                return i ^ -1;
            }
            dateTimeParserBucket.setZone((DateTimeZone) defaultTimeZoneNames.get(str2));
            return str2.length() + i;
        }
    }

    static class TimeZoneOffset implements DateTimePrinter, DateTimeParser {
        private final int iMaxFields;
        private final int iMinFields;
        private final boolean iShowSeparators;
        private final String iZeroOffsetParseText;
        private final String iZeroOffsetPrintText;

        TimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
            int i3 = 4;
            this.iZeroOffsetPrintText = str;
            this.iZeroOffsetParseText = str2;
            this.iShowSeparators = z;
            if (i <= 0 || i2 < i) {
                throw new IllegalArgumentException();
            }
            if (i > 4) {
                i2 = 4;
            } else {
                i3 = i;
            }
            this.iMinFields = i3;
            this.iMaxFields = i2;
        }

        public int estimatePrintedLength() {
            int i = (this.iMinFields + 1) << 1;
            if (this.iShowSeparators) {
                i += this.iMinFields - 1;
            }
            if (this.iZeroOffsetPrintText == null || this.iZeroOffsetPrintText.length() <= i) {
                return i;
            }
            return this.iZeroOffsetPrintText.length();
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            if (dateTimeZone != null) {
                if (i != 0 || this.iZeroOffsetPrintText == null) {
                    if (i >= 0) {
                        stringBuffer.append('+');
                    } else {
                        stringBuffer.append('-');
                        i = -i;
                    }
                    int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
                    FormatUtils.appendPaddedInteger(stringBuffer, i2, 2);
                    if (this.iMaxFields != 1) {
                        i2 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
                        if (i2 != 0 || this.iMinFields > 1) {
                            int i3 = i2 / DateTimeConstants.MILLIS_PER_MINUTE;
                            if (this.iShowSeparators) {
                                stringBuffer.append(':');
                            }
                            FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
                            if (this.iMaxFields != 2) {
                                i2 -= i3 * DateTimeConstants.MILLIS_PER_MINUTE;
                                if (i2 != 0 || this.iMinFields > 2) {
                                    i3 = i2 / DateTimeConstants.MILLIS_PER_SECOND;
                                    if (this.iShowSeparators) {
                                        stringBuffer.append(':');
                                    }
                                    FormatUtils.appendPaddedInteger(stringBuffer, i3, 2);
                                    if (this.iMaxFields != 3) {
                                        i2 -= i3 * DateTimeConstants.MILLIS_PER_SECOND;
                                        if (i2 != 0 || this.iMinFields > 3) {
                                            if (this.iShowSeparators) {
                                                stringBuffer.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                                            }
                                            FormatUtils.appendPaddedInteger(stringBuffer, i2, 3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                stringBuffer.append(this.iZeroOffsetPrintText);
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            if (dateTimeZone != null) {
                if (i != 0 || this.iZeroOffsetPrintText == null) {
                    if (i >= 0) {
                        writer.write(43);
                    } else {
                        writer.write(45);
                        i = -i;
                    }
                    int i2 = i / DateTimeConstants.MILLIS_PER_HOUR;
                    FormatUtils.writePaddedInteger(writer, i2, 2);
                    if (this.iMaxFields != 1) {
                        i2 = i - (i2 * DateTimeConstants.MILLIS_PER_HOUR);
                        if (i2 != 0 || this.iMinFields != 1) {
                            int i3 = i2 / DateTimeConstants.MILLIS_PER_MINUTE;
                            if (this.iShowSeparators) {
                                writer.write(58);
                            }
                            FormatUtils.writePaddedInteger(writer, i3, 2);
                            if (this.iMaxFields != 2) {
                                i2 -= i3 * DateTimeConstants.MILLIS_PER_MINUTE;
                                if (i2 != 0 || this.iMinFields != 2) {
                                    i3 = i2 / DateTimeConstants.MILLIS_PER_SECOND;
                                    if (this.iShowSeparators) {
                                        writer.write(58);
                                    }
                                    FormatUtils.writePaddedInteger(writer, i3, 2);
                                    if (this.iMaxFields != 3) {
                                        i2 -= i3 * DateTimeConstants.MILLIS_PER_SECOND;
                                        if (i2 != 0 || this.iMinFields != 3) {
                                            if (this.iShowSeparators) {
                                                writer.write(46);
                                            }
                                            FormatUtils.writePaddedInteger(writer, i2, 3);
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                writer.write(this.iZeroOffsetPrintText);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
        }

        public int estimateParsedLength() {
            return estimatePrintedLength();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r11, java.lang.String r12, int r13) {
            /*
            r10 = this;
            r9 = 45;
            r7 = 43;
            r4 = 0;
            r1 = 1;
            r8 = 2;
            r0 = r12.length();
            r6 = r0 - r13;
            r0 = r10.iZeroOffsetParseText;
            if (r0 == 0) goto L_0x0023;
        L_0x0011:
            r0 = r10.iZeroOffsetParseText;
            r0 = r0.length();
            if (r0 != 0) goto L_0x0030;
        L_0x0019:
            if (r6 <= 0) goto L_0x0028;
        L_0x001b:
            r0 = r12.charAt(r13);
            if (r0 == r9) goto L_0x0023;
        L_0x0021:
            if (r0 != r7) goto L_0x0028;
        L_0x0023:
            if (r6 > r1) goto L_0x004f;
        L_0x0025:
            r13 = r13 ^ -1;
        L_0x0027:
            return r13;
        L_0x0028:
            r0 = java.lang.Integer.valueOf(r4);
            r11.setOffset(r0);
            goto L_0x0027;
        L_0x0030:
            r3 = r10.iZeroOffsetParseText;
            r0 = r10.iZeroOffsetParseText;
            r5 = r0.length();
            r0 = r12;
            r2 = r13;
            r0 = r0.regionMatches(r1, r2, r3, r4, r5);
            if (r0 == 0) goto L_0x0023;
        L_0x0040:
            r0 = java.lang.Integer.valueOf(r4);
            r11.setOffset(r0);
            r0 = r10.iZeroOffsetParseText;
            r0 = r0.length();
            r13 = r13 + r0;
            goto L_0x0027;
        L_0x004f:
            r0 = r12.charAt(r13);
            if (r0 != r9) goto L_0x0063;
        L_0x0055:
            r0 = r1;
        L_0x0056:
            r2 = r6 + -1;
            r3 = r13 + 1;
            r5 = r10.digitCount(r12, r3, r8);
            if (r5 >= r8) goto L_0x006a;
        L_0x0060:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x0063:
            if (r0 != r7) goto L_0x0067;
        L_0x0065:
            r0 = r4;
            goto L_0x0056;
        L_0x0067:
            r13 = r13 ^ -1;
            goto L_0x0027;
        L_0x006a:
            r5 = org.joda.time.format.FormatUtils.parseTwoDigits(r12, r3);
            r6 = 23;
            if (r5 <= r6) goto L_0x0075;
        L_0x0072:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x0075:
            r6 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
            r5 = r5 * r6;
            r2 = r2 + -2;
            r3 = r3 + 2;
            if (r2 > 0) goto L_0x008c;
        L_0x007f:
            r1 = r5;
            r13 = r3;
        L_0x0081:
            if (r0 == 0) goto L_0x015d;
        L_0x0083:
            r0 = -r1;
        L_0x0084:
            r0 = java.lang.Integer.valueOf(r0);
            r11.setOffset(r0);
            goto L_0x0027;
        L_0x008c:
            r6 = r12.charAt(r3);
            r7 = 58;
            if (r6 != r7) goto L_0x00a4;
        L_0x0094:
            r2 = r2 + -1;
            r3 = r3 + 1;
            r4 = r1;
        L_0x0099:
            r6 = r10.digitCount(r12, r3, r8);
            if (r6 != 0) goto L_0x00af;
        L_0x009f:
            if (r4 != 0) goto L_0x00af;
        L_0x00a1:
            r1 = r5;
            r13 = r3;
            goto L_0x0081;
        L_0x00a4:
            r7 = 48;
            if (r6 < r7) goto L_0x00ac;
        L_0x00a8:
            r7 = 57;
            if (r6 <= r7) goto L_0x0099;
        L_0x00ac:
            r1 = r5;
            r13 = r3;
            goto L_0x0081;
        L_0x00af:
            if (r6 >= r8) goto L_0x00b5;
        L_0x00b1:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x00b5:
            r6 = org.joda.time.format.FormatUtils.parseTwoDigits(r12, r3);
            r7 = 59;
            if (r6 <= r7) goto L_0x00c1;
        L_0x00bd:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x00c1:
            r7 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
            r6 = r6 * r7;
            r5 = r5 + r6;
            r2 = r2 + -2;
            r3 = r3 + 2;
            if (r2 > 0) goto L_0x00cf;
        L_0x00cc:
            r1 = r5;
            r13 = r3;
            goto L_0x0081;
        L_0x00cf:
            if (r4 == 0) goto L_0x00e0;
        L_0x00d1:
            r6 = r12.charAt(r3);
            r7 = 58;
            if (r6 == r7) goto L_0x00dc;
        L_0x00d9:
            r1 = r5;
            r13 = r3;
            goto L_0x0081;
        L_0x00dc:
            r2 = r2 + -1;
            r3 = r3 + 1;
        L_0x00e0:
            r6 = r10.digitCount(r12, r3, r8);
            if (r6 != 0) goto L_0x00eb;
        L_0x00e6:
            if (r4 != 0) goto L_0x00eb;
        L_0x00e8:
            r1 = r5;
            r13 = r3;
            goto L_0x0081;
        L_0x00eb:
            if (r6 >= r8) goto L_0x00f1;
        L_0x00ed:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x00f1:
            r6 = org.joda.time.format.FormatUtils.parseTwoDigits(r12, r3);
            r7 = 59;
            if (r6 <= r7) goto L_0x00fd;
        L_0x00f9:
            r13 = r3 ^ -1;
            goto L_0x0027;
        L_0x00fd:
            r6 = r6 * 1000;
            r5 = r5 + r6;
            r6 = r2 + -2;
            r2 = r3 + 2;
            if (r6 > 0) goto L_0x010a;
        L_0x0106:
            r1 = r5;
            r13 = r2;
            goto L_0x0081;
        L_0x010a:
            if (r4 == 0) goto L_0x0124;
        L_0x010c:
            r3 = r12.charAt(r2);
            r7 = 46;
            if (r3 == r7) goto L_0x0120;
        L_0x0114:
            r3 = r12.charAt(r2);
            r7 = 44;
            if (r3 == r7) goto L_0x0120;
        L_0x011c:
            r1 = r5;
            r13 = r2;
            goto L_0x0081;
        L_0x0120:
            r3 = r6 + -1;
            r2 = r2 + 1;
        L_0x0124:
            r3 = 3;
            r6 = r10.digitCount(r12, r2, r3);
            if (r6 != 0) goto L_0x0131;
        L_0x012b:
            if (r4 != 0) goto L_0x0131;
        L_0x012d:
            r1 = r5;
            r13 = r2;
            goto L_0x0081;
        L_0x0131:
            if (r6 >= r1) goto L_0x0137;
        L_0x0133:
            r13 = r2 ^ -1;
            goto L_0x0027;
        L_0x0137:
            r3 = r2 + 1;
            r2 = r12.charAt(r2);
            r2 = r2 + -48;
            r2 = r2 * 100;
            r2 = r2 + r5;
            if (r6 <= r1) goto L_0x0160;
        L_0x0144:
            r13 = r3 + 1;
            r1 = r12.charAt(r3);
            r1 = r1 + -48;
            r1 = r1 * 10;
            r1 = r1 + r2;
            if (r6 <= r8) goto L_0x0081;
        L_0x0151:
            r3 = r13 + 1;
            r2 = r12.charAt(r13);
            r2 = r2 + -48;
            r1 = r1 + r2;
            r13 = r3;
            goto L_0x0081;
        L_0x015d:
            r0 = r1;
            goto L_0x0084;
        L_0x0160:
            r1 = r2;
            r13 = r3;
            goto L_0x0081;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.TimeZoneOffset.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.String, int):int");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private int digitCount(java.lang.String r5, int r6, int r7) {
            /*
            r4 = this;
            r0 = r5.length();
            r0 = r0 - r6;
            r0 = java.lang.Math.min(r0, r7);
            r1 = 0;
        L_0x000a:
            if (r0 <= 0) goto L_0x001a;
        L_0x000c:
            r2 = r6 + r1;
            r2 = r5.charAt(r2);
            r3 = 48;
            if (r2 < r3) goto L_0x001a;
        L_0x0016:
            r3 = 57;
            if (r2 <= r3) goto L_0x001b;
        L_0x001a:
            return r1;
        L_0x001b:
            r1 = r1 + 1;
            r0 = r0 + -1;
            goto L_0x000a;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.TimeZoneOffset.digitCount(java.lang.String, int, int):int");
        }
    }

    static class TwoDigitYear implements DateTimePrinter, DateTimeParser {
        private final boolean iLenientParse;
        private final int iPivot;
        private final DateTimeFieldType iType;

        TwoDigitYear(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            this.iType = dateTimeFieldType;
            this.iPivot = i;
            this.iLenientParse = z;
        }

        public int estimateParsedLength() {
            return this.iLenientParse ? 4 : 2;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.format.DateTimeParserBucket r13, java.lang.String r14, int r15) {
            /*
            r12 = this;
            r2 = 1;
            r10 = 57;
            r9 = 48;
            r8 = 2;
            r0 = 0;
            r1 = r14.length();
            r1 = r1 - r15;
            r3 = r12.iLenientParse;
            if (r3 != 0) goto L_0x0019;
        L_0x0010:
            r1 = java.lang.Math.min(r8, r1);
            if (r1 >= r8) goto L_0x0098;
        L_0x0016:
            r1 = r15 ^ -1;
        L_0x0018:
            return r1;
        L_0x0019:
            r3 = r0;
            r4 = r0;
            r5 = r0;
            r6 = r1;
        L_0x001d:
            if (r3 >= r6) goto L_0x0049;
        L_0x001f:
            r1 = r15 + r3;
            r1 = r14.charAt(r1);
            if (r3 != 0) goto L_0x0045;
        L_0x0027:
            r7 = 45;
            if (r1 == r7) goto L_0x002f;
        L_0x002b:
            r7 = 43;
            if (r1 != r7) goto L_0x0045;
        L_0x002f:
            r4 = 45;
            if (r1 != r4) goto L_0x003b;
        L_0x0033:
            r1 = r2;
        L_0x0034:
            if (r1 == 0) goto L_0x003d;
        L_0x0036:
            r3 = r3 + 1;
            r4 = r1;
            r5 = r2;
            goto L_0x001d;
        L_0x003b:
            r1 = r0;
            goto L_0x0034;
        L_0x003d:
            r15 = r15 + 1;
            r4 = r6 + -1;
            r5 = r2;
            r6 = r4;
            r4 = r1;
            goto L_0x001d;
        L_0x0045:
            if (r1 < r9) goto L_0x0049;
        L_0x0047:
            if (r1 <= r10) goto L_0x004e;
        L_0x0049:
            if (r3 != 0) goto L_0x0052;
        L_0x004b:
            r1 = r15 ^ -1;
            goto L_0x0018;
        L_0x004e:
            r1 = r3 + 1;
            r3 = r1;
            goto L_0x001d;
        L_0x0052:
            if (r5 != 0) goto L_0x0056;
        L_0x0054:
            if (r3 == r8) goto L_0x0098;
        L_0x0056:
            r0 = 9;
            if (r3 < r0) goto L_0x006a;
        L_0x005a:
            r1 = r15 + r3;
            r0 = r14.substring(r15, r1);
            r0 = java.lang.Integer.parseInt(r0);
        L_0x0064:
            r2 = r12.iType;
            r13.saveField(r2, r0);
            goto L_0x0018;
        L_0x006a:
            if (r4 == 0) goto L_0x00e9;
        L_0x006c:
            r0 = r15 + 1;
            r1 = r0;
        L_0x006f:
            r0 = r1 + 1;
            r1 = r14.charAt(r1);	 Catch:{ StringIndexOutOfBoundsException -> 0x0090 }
            r2 = r1 + -48;
            r1 = r15 + r3;
            r11 = r0;
            r0 = r2;
            r2 = r11;
        L_0x007c:
            if (r2 >= r1) goto L_0x0094;
        L_0x007e:
            r3 = r0 << 3;
            r0 = r0 << 1;
            r3 = r3 + r0;
            r0 = r2 + 1;
            r2 = r14.charAt(r2);
            r2 = r2 + r3;
            r2 = r2 + -48;
            r11 = r0;
            r0 = r2;
            r2 = r11;
            goto L_0x007c;
        L_0x0090:
            r0 = move-exception;
            r1 = r15 ^ -1;
            goto L_0x0018;
        L_0x0094:
            if (r4 == 0) goto L_0x0064;
        L_0x0096:
            r0 = -r0;
            goto L_0x0064;
        L_0x0098:
            r1 = r14.charAt(r15);
            if (r1 < r9) goto L_0x00a0;
        L_0x009e:
            if (r1 <= r10) goto L_0x00a4;
        L_0x00a0:
            r1 = r15 ^ -1;
            goto L_0x0018;
        L_0x00a4:
            r1 = r1 + -48;
            r2 = r15 + 1;
            r2 = r14.charAt(r2);
            if (r2 < r9) goto L_0x00b0;
        L_0x00ae:
            if (r2 <= r10) goto L_0x00b4;
        L_0x00b0:
            r1 = r15 ^ -1;
            goto L_0x0018;
        L_0x00b4:
            r3 = r1 << 3;
            r1 = r1 << 1;
            r1 = r1 + r3;
            r1 = r1 + r2;
            r2 = r1 + -48;
            r1 = r12.iPivot;
            r3 = r13.getPivotYear();
            if (r3 == 0) goto L_0x00cc;
        L_0x00c4:
            r1 = r13.getPivotYear();
            r1 = r1.intValue();
        L_0x00cc:
            r3 = r1 + -50;
            if (r3 < 0) goto L_0x00e2;
        L_0x00d0:
            r1 = r3 % 100;
        L_0x00d2:
            if (r2 >= r1) goto L_0x00d6;
        L_0x00d4:
            r0 = 100;
        L_0x00d6:
            r0 = r0 + r3;
            r0 = r0 - r1;
            r0 = r0 + r2;
            r1 = r12.iType;
            r13.saveField(r1, r0);
            r1 = r15 + 2;
            goto L_0x0018;
        L_0x00e2:
            r1 = r3 + 1;
            r1 = r1 % 100;
            r1 = r1 + 99;
            goto L_0x00d2;
        L_0x00e9:
            r1 = r15;
            goto L_0x006f;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.DateTimeFormatterBuilder.TwoDigitYear.parseInto(org.joda.time.format.DateTimeParserBucket, java.lang.String, int):int");
        }

        public int estimatePrintedLength() {
            return 2;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            int twoDigitYear = getTwoDigitYear(j, chronology);
            if (twoDigitYear < 0) {
                stringBuffer.append('\ufffd');
                stringBuffer.append('\ufffd');
                return;
            }
            FormatUtils.appendPaddedInteger(stringBuffer, twoDigitYear, 2);
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(j, chronology);
            if (twoDigitYear < 0) {
                writer.write(65533);
                writer.write(65533);
                return;
            }
            FormatUtils.writePaddedInteger(writer, twoDigitYear, 2);
        }

        private int getTwoDigitYear(long j, Chronology chronology) {
            try {
                int i = this.iType.getField(chronology).get(j);
                if (i < 0) {
                    i = -i;
                }
                return i % 100;
            } catch (RuntimeException e) {
                return -1;
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            int twoDigitYear = getTwoDigitYear(readablePartial);
            if (twoDigitYear < 0) {
                stringBuffer.append('\ufffd');
                stringBuffer.append('\ufffd');
                return;
            }
            FormatUtils.appendPaddedInteger(stringBuffer, twoDigitYear, 2);
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            int twoDigitYear = getTwoDigitYear(readablePartial);
            if (twoDigitYear < 0) {
                writer.write(65533);
                writer.write(65533);
                return;
            }
            FormatUtils.writePaddedInteger(writer, twoDigitYear, 2);
        }

        private int getTwoDigitYear(ReadablePartial readablePartial) {
            if (readablePartial.isSupported(this.iType)) {
                try {
                    int i = readablePartial.get(this.iType);
                    if (i < 0) {
                        i = -i;
                    }
                    return i % 100;
                } catch (RuntimeException e) {
                }
            }
            return -1;
        }
    }

    static class PaddedNumber extends NumberFormatter {
        protected final int iMinPrintedDigits;

        protected PaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z, int i2) {
            super(dateTimeFieldType, i, z);
            this.iMinPrintedDigits = i2;
        }

        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendPaddedInteger(stringBuffer, this.iFieldType.getField(chronology).get(j), this.iMinPrintedDigits);
            } catch (RuntimeException e) {
                DateTimeFormatterBuilder.appendUnknownString(stringBuffer, this.iMinPrintedDigits);
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.writePaddedInteger(writer, this.iFieldType.getField(chronology).get(j), this.iMinPrintedDigits);
            } catch (RuntimeException e) {
                DateTimeFormatterBuilder.printUnknownString(writer, this.iMinPrintedDigits);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.appendPaddedInteger(stringBuffer, readablePartial.get(this.iFieldType), this.iMinPrintedDigits);
                    return;
                } catch (RuntimeException e) {
                    DateTimeFormatterBuilder.appendUnknownString(stringBuffer, this.iMinPrintedDigits);
                    return;
                }
            }
            DateTimeFormatterBuilder.appendUnknownString(stringBuffer, this.iMinPrintedDigits);
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.writePaddedInteger(writer, readablePartial.get(this.iFieldType), this.iMinPrintedDigits);
                    return;
                } catch (RuntimeException e) {
                    DateTimeFormatterBuilder.printUnknownString(writer, this.iMinPrintedDigits);
                    return;
                }
            }
            DateTimeFormatterBuilder.printUnknownString(writer, this.iMinPrintedDigits);
        }
    }

    static class UnpaddedNumber extends NumberFormatter {
        protected UnpaddedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z);
        }

        public int estimatePrintedLength() {
            return this.iMaxParsedDigits;
        }

        public void printTo(StringBuffer stringBuffer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) {
            try {
                FormatUtils.appendUnpaddedInteger(stringBuffer, this.iFieldType.getField(chronology).get(j));
            } catch (RuntimeException e) {
                stringBuffer.append('\ufffd');
            }
        }

        public void printTo(Writer writer, long j, Chronology chronology, int i, DateTimeZone dateTimeZone, Locale locale) throws IOException {
            try {
                FormatUtils.writeUnpaddedInteger(writer, this.iFieldType.getField(chronology).get(j));
            } catch (RuntimeException e) {
                writer.write(65533);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale) {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.appendUnpaddedInteger(stringBuffer, readablePartial.get(this.iFieldType));
                    return;
                } catch (RuntimeException e) {
                    stringBuffer.append('\ufffd');
                    return;
                }
            }
            stringBuffer.append('\ufffd');
        }

        public void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException {
            if (readablePartial.isSupported(this.iFieldType)) {
                try {
                    FormatUtils.writeUnpaddedInteger(writer, readablePartial.get(this.iFieldType));
                    return;
                } catch (RuntimeException e) {
                    writer.write(65533);
                    return;
                }
            }
            writer.write(65533);
        }
    }

    static class FixedNumber extends PaddedNumber {
        protected FixedNumber(DateTimeFieldType dateTimeFieldType, int i, boolean z) {
            super(dateTimeFieldType, i, z, i);
        }

        public int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i) {
            int parseInto = super.parseInto(dateTimeParserBucket, str, i);
            if (parseInto < 0) {
                return parseInto;
            }
            int i2 = this.iMaxParsedDigits + i;
            if (parseInto == i2) {
                return parseInto;
            }
            if (this.iSigned) {
                char charAt = str.charAt(i);
                if (charAt == '-' || charAt == '+') {
                    i2++;
                }
            }
            if (parseInto > i2) {
                return (i2 + 1) ^ -1;
            }
            if (parseInto < i2) {
                return parseInto ^ -1;
            }
            return parseInto;
        }
    }

    public DateTimeFormatterBuilder() {
        this.iElementPairs = new ArrayList();
    }

    public DateTimeFormatter toFormatter() {
        DateTimePrinter dateTimePrinter;
        DateTimeParser dateTimeParser;
        Object formatter = getFormatter();
        if (isPrinter(formatter)) {
            dateTimePrinter = (DateTimePrinter) formatter;
        } else {
            dateTimePrinter = null;
        }
        if (isParser(formatter)) {
            dateTimeParser = (DateTimeParser) formatter;
        } else {
            dateTimeParser = null;
        }
        if (dateTimePrinter != null || dateTimeParser != null) {
            return new DateTimeFormatter(dateTimePrinter, dateTimeParser);
        }
        throw new UnsupportedOperationException("Both printing and parsing not supported");
    }

    public DateTimePrinter toPrinter() {
        Object formatter = getFormatter();
        if (isPrinter(formatter)) {
            return (DateTimePrinter) formatter;
        }
        throw new UnsupportedOperationException("Printing is not supported");
    }

    public DateTimeParser toParser() {
        Object formatter = getFormatter();
        if (isParser(formatter)) {
            return (DateTimeParser) formatter;
        }
        throw new UnsupportedOperationException("Parsing is not supported");
    }

    public boolean canBuildFormatter() {
        return isFormatter(getFormatter());
    }

    public boolean canBuildPrinter() {
        return isPrinter(getFormatter());
    }

    public boolean canBuildParser() {
        return isParser(getFormatter());
    }

    public void clear() {
        this.iFormatter = null;
        this.iElementPairs.clear();
    }

    public DateTimeFormatterBuilder append(DateTimeFormatter dateTimeFormatter) {
        if (dateTimeFormatter != null) {
            return append0(dateTimeFormatter.getPrinter(), dateTimeFormatter.getParser());
        }
        throw new IllegalArgumentException("No formatter supplied");
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter) {
        checkPrinter(dateTimePrinter);
        return append0(dateTimePrinter, null);
    }

    public DateTimeFormatterBuilder append(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0(null, dateTimeParser);
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        checkPrinter(dateTimePrinter);
        checkParser(dateTimeParser);
        return append0(dateTimePrinter, dateTimeParser);
    }

    public DateTimeFormatterBuilder append(DateTimePrinter dateTimePrinter, DateTimeParser[] dateTimeParserArr) {
        int i = 0;
        if (dateTimePrinter != null) {
            checkPrinter(dateTimePrinter);
        }
        if (dateTimeParserArr == null) {
            throw new IllegalArgumentException("No parsers supplied");
        }
        int length = dateTimeParserArr.length;
        if (length != 1) {
            DateTimeParser[] dateTimeParserArr2 = new DateTimeParser[length];
            while (i < length - 1) {
                DateTimeParser dateTimeParser = dateTimeParserArr[i];
                dateTimeParserArr2[i] = dateTimeParser;
                if (dateTimeParser == null) {
                    throw new IllegalArgumentException("Incomplete parser array");
                }
                i++;
            }
            dateTimeParserArr2[i] = dateTimeParserArr[i];
            return append0(dateTimePrinter, new MatchingParser(dateTimeParserArr2));
        } else if (dateTimeParserArr[0] != null) {
            return append0(dateTimePrinter, dateTimeParserArr[0]);
        } else {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    public DateTimeFormatterBuilder appendOptional(DateTimeParser dateTimeParser) {
        checkParser(dateTimeParser);
        return append0(null, new MatchingParser(new DateTimeParser[]{dateTimeParser, null}));
    }

    private void checkParser(DateTimeParser dateTimeParser) {
        if (dateTimeParser == null) {
            throw new IllegalArgumentException("No parser supplied");
        }
    }

    private void checkPrinter(DateTimePrinter dateTimePrinter) {
        if (dateTimePrinter == null) {
            throw new IllegalArgumentException("No printer supplied");
        }
    }

    private DateTimeFormatterBuilder append0(Object obj) {
        this.iFormatter = null;
        this.iElementPairs.add(obj);
        this.iElementPairs.add(obj);
        return this;
    }

    private DateTimeFormatterBuilder append0(DateTimePrinter dateTimePrinter, DateTimeParser dateTimeParser) {
        this.iFormatter = null;
        this.iElementPairs.add(dateTimePrinter);
        this.iElementPairs.add(dateTimeParser);
        return this;
    }

    public DateTimeFormatterBuilder appendLiteral(char c) {
        return append0(new CharacterLiteral(c));
    }

    public DateTimeFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        switch (str.length()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                return this;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return append0(new CharacterLiteral(str.charAt(0)));
            default:
                return append0(new StringLiteral(str));
        }
    }

    public DateTimeFormatterBuilder appendDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return append0(new UnpaddedNumber(dateTimeFieldType, i2, false));
        } else {
            return append0(new PaddedNumber(dateTimeFieldType, i2, false, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i, false));
        } else {
            throw new IllegalArgumentException("Illegal number of digits: " + i);
        }
    }

    public DateTimeFormatterBuilder appendSignedDecimal(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i < 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        } else if (i <= 1) {
            return append0(new UnpaddedNumber(dateTimeFieldType, i2, true));
        } else {
            return append0(new PaddedNumber(dateTimeFieldType, i2, true, i));
        }
    }

    public DateTimeFormatterBuilder appendFixedSignedDecimal(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        } else if (i > 0) {
            return append0(new FixedNumber(dateTimeFieldType, i, true));
        } else {
            throw new IllegalArgumentException("Illegal number of digits: " + i);
        }
    }

    public DateTimeFormatterBuilder appendText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, false));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendShortText(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType != null) {
            return append0(new TextField(dateTimeFieldType, true));
        }
        throw new IllegalArgumentException("Field type must not be null");
    }

    public DateTimeFormatterBuilder appendFraction(DateTimeFieldType dateTimeFieldType, int i, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field type must not be null");
        }
        if (i2 < i) {
            i2 = i;
        }
        if (i >= 0 && i2 > 0) {
            return append0(new Fraction(dateTimeFieldType, i, i2));
        }
        throw new IllegalArgumentException();
    }

    public DateTimeFormatterBuilder appendFractionOfSecond(int i, int i2) {
        return appendFraction(DateTimeFieldType.secondOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfMinute(int i, int i2) {
        return appendFraction(DateTimeFieldType.minuteOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfHour(int i, int i2) {
        return appendFraction(DateTimeFieldType.hourOfDay(), i, i2);
    }

    public DateTimeFormatterBuilder appendFractionOfDay(int i, int i2) {
        return appendFraction(DateTimeFieldType.dayOfYear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMillisOfSecond(int i) {
        return appendDecimal(DateTimeFieldType.millisOfSecond(), i, 3);
    }

    public DateTimeFormatterBuilder appendMillisOfDay(int i) {
        return appendDecimal(DateTimeFieldType.millisOfDay(), i, 8);
    }

    public DateTimeFormatterBuilder appendSecondOfMinute(int i) {
        return appendDecimal(DateTimeFieldType.secondOfMinute(), i, 2);
    }

    public DateTimeFormatterBuilder appendSecondOfDay(int i) {
        return appendDecimal(DateTimeFieldType.secondOfDay(), i, 5);
    }

    public DateTimeFormatterBuilder appendMinuteOfHour(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfHour(), i, 2);
    }

    public DateTimeFormatterBuilder appendMinuteOfDay(int i) {
        return appendDecimal(DateTimeFieldType.minuteOfDay(), i, 4);
    }

    public DateTimeFormatterBuilder appendHourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.hourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfDay(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfDay(), i, 2);
    }

    public DateTimeFormatterBuilder appendHourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.hourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendClockhourOfHalfday(int i) {
        return appendDecimal(DateTimeFieldType.clockhourOfHalfday(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfWeek(int i) {
        return appendDecimal(DateTimeFieldType.dayOfWeek(), i, 1);
    }

    public DateTimeFormatterBuilder appendDayOfMonth(int i) {
        return appendDecimal(DateTimeFieldType.dayOfMonth(), i, 2);
    }

    public DateTimeFormatterBuilder appendDayOfYear(int i) {
        return appendDecimal(DateTimeFieldType.dayOfYear(), i, 3);
    }

    public DateTimeFormatterBuilder appendWeekOfWeekyear(int i) {
        return appendDecimal(DateTimeFieldType.weekOfWeekyear(), i, 2);
    }

    public DateTimeFormatterBuilder appendWeekyear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.weekyear(), i, i2);
    }

    public DateTimeFormatterBuilder appendMonthOfYear(int i) {
        return appendDecimal(DateTimeFieldType.monthOfYear(), i, 2);
    }

    public DateTimeFormatterBuilder appendYear(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.year(), i, i2);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i) {
        return appendTwoDigitYear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitYear(int i, boolean z) {
        return append0(new TwoDigitYear(DateTimeFieldType.year(), i, z));
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i) {
        return appendTwoDigitWeekyear(i, false);
    }

    public DateTimeFormatterBuilder appendTwoDigitWeekyear(int i, boolean z) {
        return append0(new TwoDigitYear(DateTimeFieldType.weekyear(), i, z));
    }

    public DateTimeFormatterBuilder appendYearOfEra(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendYearOfCentury(int i, int i2) {
        return appendDecimal(DateTimeFieldType.yearOfCentury(), i, i2);
    }

    public DateTimeFormatterBuilder appendCenturyOfEra(int i, int i2) {
        return appendSignedDecimal(DateTimeFieldType.centuryOfEra(), i, i2);
    }

    public DateTimeFormatterBuilder appendHalfdayOfDayText() {
        return appendText(DateTimeFieldType.halfdayOfDay());
    }

    public DateTimeFormatterBuilder appendDayOfWeekText() {
        return appendText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendDayOfWeekShortText() {
        return appendShortText(DateTimeFieldType.dayOfWeek());
    }

    public DateTimeFormatterBuilder appendMonthOfYearText() {
        return appendText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendMonthOfYearShortText() {
        return appendShortText(DateTimeFieldType.monthOfYear());
    }

    public DateTimeFormatterBuilder appendEraText() {
        return appendText(DateTimeFieldType.era());
    }

    public DateTimeFormatterBuilder appendTimeZoneName() {
        return append0(new TimeZoneName(0, null), null);
    }

    public DateTimeFormatterBuilder appendTimeZoneName(Map<String, DateTimeZone> map) {
        Object timeZoneName = new TimeZoneName(0, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName() {
        return append0(new TimeZoneName(1, null), null);
    }

    public DateTimeFormatterBuilder appendTimeZoneShortName(Map<String, DateTimeZone> map) {
        Object timeZoneName = new TimeZoneName(1, map);
        return append0(timeZoneName, timeZoneName);
    }

    public DateTimeFormatterBuilder appendTimeZoneId() {
        return append0(TimeZoneId.INSTANCE, TimeZoneId.INSTANCE);
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, boolean z, int i, int i2) {
        return append0(new TimeZoneOffset(str, str, z, i, i2));
    }

    public DateTimeFormatterBuilder appendTimeZoneOffset(String str, String str2, boolean z, int i, int i2) {
        return append0(new TimeZoneOffset(str, str2, z, i, i2));
    }

    public DateTimeFormatterBuilder appendPattern(String str) {
        DateTimeFormat.appendPatternTo(this, str);
        return this;
    }

    private Object getFormatter() {
        Object obj = this.iFormatter;
        if (obj == null) {
            if (this.iElementPairs.size() == 2) {
                Object obj2 = this.iElementPairs.get(0);
                Object obj3 = this.iElementPairs.get(1);
                if (obj2 == null) {
                    obj = obj3;
                } else if (obj2 == obj3 || obj3 == null) {
                    obj = obj2;
                }
            }
            if (obj == null) {
                obj = new Composite(this.iElementPairs);
            }
            this.iFormatter = obj;
        }
        return obj;
    }

    private boolean isPrinter(Object obj) {
        if (!(obj instanceof DateTimePrinter)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).isPrinter();
        }
        return true;
    }

    private boolean isParser(Object obj) {
        if (!(obj instanceof DateTimeParser)) {
            return false;
        }
        if (obj instanceof Composite) {
            return ((Composite) obj).isParser();
        }
        return true;
    }

    private boolean isFormatter(Object obj) {
        return isPrinter(obj) || isParser(obj);
    }

    static void appendUnknownString(StringBuffer stringBuffer, int i) {
        while (true) {
            i--;
            if (i >= 0) {
                stringBuffer.append('\ufffd');
            } else {
                return;
            }
        }
    }

    static void printUnknownString(Writer writer, int i) throws IOException {
        while (true) {
            i--;
            if (i >= 0) {
                writer.write(65533);
            } else {
                return;
            }
        }
    }
}
