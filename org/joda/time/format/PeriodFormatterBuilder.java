package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DurationFieldType;
import org.joda.time.MutableDateTime;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

public class PeriodFormatterBuilder {
    private static final int DAYS = 3;
    private static final int HOURS = 4;
    private static final int MAX_FIELD = 9;
    private static final int MILLIS = 7;
    private static final int MINUTES = 5;
    private static final int MONTHS = 1;
    private static final int PRINT_ZERO_ALWAYS = 4;
    private static final int PRINT_ZERO_IF_SUPPORTED = 3;
    private static final int PRINT_ZERO_NEVER = 5;
    private static final int PRINT_ZERO_RARELY_FIRST = 1;
    private static final int PRINT_ZERO_RARELY_LAST = 2;
    private static final int SECONDS = 6;
    private static final int SECONDS_MILLIS = 8;
    private static final int SECONDS_OPTIONAL_MILLIS = 9;
    private static final int WEEKS = 2;
    private static final int YEARS = 0;
    private List<Object> iElementPairs;
    private FieldFormatter[] iFieldFormatters;
    private int iMaxParsedDigits;
    private int iMinPrintedDigits;
    private boolean iNotParser;
    private boolean iNotPrinter;
    private PeriodFieldAffix iPrefix;
    private int iPrintZeroSetting;
    private boolean iRejectSignedValues;

    interface PeriodFieldAffix {
        int calculatePrintedLength(int i);

        int parse(String str, int i);

        void printTo(Writer writer, int i) throws IOException;

        void printTo(StringBuffer stringBuffer, int i);

        int scan(String str, int i);
    }

    static class Composite implements PeriodPrinter, PeriodParser {
        private final PeriodParser[] iParsers;
        private final PeriodPrinter[] iPrinters;

        Composite(List<Object> list) {
            List arrayList = new ArrayList();
            List arrayList2 = new ArrayList();
            decompose(list, arrayList, arrayList2);
            if (arrayList.size() <= 0) {
                this.iPrinters = null;
            } else {
                this.iPrinters = (PeriodPrinter[]) arrayList.toArray(new PeriodPrinter[arrayList.size()]);
            }
            if (arrayList2.size() <= 0) {
                this.iParsers = null;
            } else {
                this.iParsers = (PeriodParser[]) arrayList2.toArray(new PeriodParser[arrayList2.size()]);
            }
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            int i2 = 0;
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            while (i2 < i) {
                length--;
                if (length < 0) {
                    break;
                }
                i2 += periodPrinterArr[length].countFieldsToPrint(readablePeriod, Integer.MAX_VALUE, locale);
            }
            return i2;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            int i = 0;
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            while (true) {
                length--;
                if (length < 0) {
                    return i;
                }
                i += periodPrinterArr[length].calculatePrintedLength(readablePeriod, locale);
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            for (int i = 0; i < length; i += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                periodPrinterArr[i].printTo(stringBuffer, readablePeriod, locale);
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            PeriodPrinter[] periodPrinterArr = this.iPrinters;
            int length = periodPrinterArr.length;
            for (int i = 0; i < length; i += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                periodPrinterArr[i].printTo(writer, readablePeriod, locale);
            }
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            PeriodParser[] periodParserArr = this.iParsers;
            if (periodParserArr == null) {
                throw new UnsupportedOperationException();
            }
            int length = periodParserArr.length;
            for (int i2 = 0; i2 < length && i >= 0; i2 += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                i = periodParserArr[i2].parseInto(readWritablePeriod, str, i, locale);
            }
            return i;
        }

        private void decompose(List<Object> list, List<Object> list2, List<Object> list3) {
            int size = list.size();
            for (int i = 0; i < size; i += PeriodFormatterBuilder.WEEKS) {
                Object obj = list.get(i);
                if (obj instanceof PeriodPrinter) {
                    if (obj instanceof Composite) {
                        addArrayToList(list2, ((Composite) obj).iPrinters);
                    } else {
                        list2.add(obj);
                    }
                }
                obj = list.get(i + PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST);
                if (obj instanceof PeriodParser) {
                    if (obj instanceof Composite) {
                        addArrayToList(list3, ((Composite) obj).iParsers);
                    } else {
                        list3.add(obj);
                    }
                }
            }
        }

        private void addArrayToList(List<Object> list, Object[] objArr) {
            if (objArr != null) {
                for (int i = 0; i < objArr.length; i += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                    list.add(objArr[i]);
                }
            }
        }
    }

    static class CompositeAffix implements PeriodFieldAffix {
        private final PeriodFieldAffix iLeft;
        private final PeriodFieldAffix iRight;

        CompositeAffix(PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iLeft = periodFieldAffix;
            this.iRight = periodFieldAffix2;
        }

        public int calculatePrintedLength(int i) {
            return this.iLeft.calculatePrintedLength(i) + this.iRight.calculatePrintedLength(i);
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            this.iLeft.printTo(stringBuffer, i);
            this.iRight.printTo(stringBuffer, i);
        }

        public void printTo(Writer writer, int i) throws IOException {
            this.iLeft.printTo(writer, i);
            this.iRight.printTo(writer, i);
        }

        public int parse(String str, int i) {
            int parse = this.iLeft.parse(str, i);
            if (parse >= 0) {
                return this.iRight.parse(str, parse);
            }
            return parse;
        }

        public int scan(String str, int i) {
            int scan = this.iLeft.scan(str, i);
            if (scan >= 0) {
                return this.iRight.scan(str, scan);
            }
            return i ^ -1;
        }
    }

    static class FieldFormatter implements PeriodPrinter, PeriodParser {
        private final FieldFormatter[] iFieldFormatters;
        private final int iFieldType;
        private final int iMaxParsedDigits;
        private final int iMinPrintedDigits;
        private final PeriodFieldAffix iPrefix;
        private final int iPrintZeroSetting;
        private final boolean iRejectSignedValues;
        private final PeriodFieldAffix iSuffix;

        FieldFormatter(int i, int i2, int i3, boolean z, int i4, FieldFormatter[] fieldFormatterArr, PeriodFieldAffix periodFieldAffix, PeriodFieldAffix periodFieldAffix2) {
            this.iMinPrintedDigits = i;
            this.iPrintZeroSetting = i2;
            this.iMaxParsedDigits = i3;
            this.iRejectSignedValues = z;
            this.iFieldType = i4;
            this.iFieldFormatters = fieldFormatterArr;
            this.iPrefix = periodFieldAffix;
            this.iSuffix = periodFieldAffix2;
        }

        FieldFormatter(FieldFormatter fieldFormatter, PeriodFieldAffix periodFieldAffix) {
            this.iMinPrintedDigits = fieldFormatter.iMinPrintedDigits;
            this.iPrintZeroSetting = fieldFormatter.iPrintZeroSetting;
            this.iMaxParsedDigits = fieldFormatter.iMaxParsedDigits;
            this.iRejectSignedValues = fieldFormatter.iRejectSignedValues;
            this.iFieldType = fieldFormatter.iFieldType;
            this.iFieldFormatters = fieldFormatter.iFieldFormatters;
            this.iPrefix = fieldFormatter.iPrefix;
            if (fieldFormatter.iSuffix != null) {
                periodFieldAffix = new CompositeAffix(fieldFormatter.iSuffix, periodFieldAffix);
            }
            this.iSuffix = periodFieldAffix;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            if (i <= 0) {
                return 0;
            }
            if (this.iPrintZeroSetting == PeriodFormatterBuilder.PRINT_ZERO_ALWAYS || getFieldValue(readablePeriod) != Long.MAX_VALUE) {
                return PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
            }
            return 0;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue == Long.MAX_VALUE) {
                return 0;
            }
            int max = Math.max(FormatUtils.calculateDigitCount(fieldValue), this.iMinPrintedDigits);
            if (this.iFieldType >= PeriodFormatterBuilder.SECONDS_MILLIS) {
                max = (fieldValue < 0 ? Math.max(max, PeriodFormatterBuilder.PRINT_ZERO_NEVER) : Math.max(max, PeriodFormatterBuilder.PRINT_ZERO_ALWAYS)) + PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                if (this.iFieldType == PeriodFormatterBuilder.SECONDS_OPTIONAL_MILLIS && Math.abs(fieldValue) % 1000 == 0) {
                    max -= 4;
                }
                fieldValue /= 1000;
            }
            int i = (int) fieldValue;
            if (this.iPrefix != null) {
                max += this.iPrefix.calculatePrintedLength(i);
            }
            if (this.iSuffix != null) {
                return max + this.iSuffix.calculatePrintedLength(i);
            }
            return max;
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue != Long.MAX_VALUE) {
                int i = (int) fieldValue;
                if (this.iFieldType >= PeriodFormatterBuilder.SECONDS_MILLIS) {
                    i = (int) (fieldValue / 1000);
                }
                if (this.iPrefix != null) {
                    this.iPrefix.printTo(stringBuffer, i);
                }
                int length = stringBuffer.length();
                int i2 = this.iMinPrintedDigits;
                if (i2 <= PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                    FormatUtils.appendUnpaddedInteger(stringBuffer, i);
                } else {
                    FormatUtils.appendPaddedInteger(stringBuffer, i, i2);
                }
                if (this.iFieldType >= PeriodFormatterBuilder.SECONDS_MILLIS) {
                    i2 = (int) (Math.abs(fieldValue) % 1000);
                    if (this.iFieldType == PeriodFormatterBuilder.SECONDS_MILLIS || i2 > 0) {
                        if (fieldValue < 0 && fieldValue > -1000) {
                            stringBuffer.insert(length, '-');
                        }
                        stringBuffer.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                        FormatUtils.appendPaddedInteger(stringBuffer, i2, (int) PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED);
                    }
                }
                if (this.iSuffix != null) {
                    this.iSuffix.printTo(stringBuffer, i);
                }
            }
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            long fieldValue = getFieldValue(readablePeriod);
            if (fieldValue != Long.MAX_VALUE) {
                int i = (int) fieldValue;
                if (this.iFieldType >= PeriodFormatterBuilder.SECONDS_MILLIS) {
                    i = (int) (fieldValue / 1000);
                }
                if (this.iPrefix != null) {
                    this.iPrefix.printTo(writer, i);
                }
                int i2 = this.iMinPrintedDigits;
                if (i2 <= PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                    FormatUtils.writeUnpaddedInteger(writer, i);
                } else {
                    FormatUtils.writePaddedInteger(writer, i, i2);
                }
                if (this.iFieldType >= PeriodFormatterBuilder.SECONDS_MILLIS) {
                    int abs = (int) (Math.abs(fieldValue) % 1000);
                    if (this.iFieldType == PeriodFormatterBuilder.SECONDS_MILLIS || abs > 0) {
                        writer.write(46);
                        FormatUtils.writePaddedInteger(writer, abs, (int) PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED);
                    }
                }
                if (this.iSuffix != null) {
                    this.iSuffix.printTo(writer, i);
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int parseInto(org.joda.time.ReadWritablePeriod r10, java.lang.String r11, int r12, java.util.Locale r13) {
            /*
            r9 = this;
            r0 = r9.iPrintZeroSetting;
            r1 = 4;
            if (r0 != r1) goto L_0x0011;
        L_0x0005:
            r0 = 1;
        L_0x0006:
            r1 = r11.length();
            if (r12 < r1) goto L_0x0013;
        L_0x000c:
            if (r0 == 0) goto L_0x0010;
        L_0x000e:
            r12 = r12 ^ -1;
        L_0x0010:
            return r12;
        L_0x0011:
            r0 = 0;
            goto L_0x0006;
        L_0x0013:
            r1 = r9.iPrefix;
            if (r1 == 0) goto L_0x0020;
        L_0x0017:
            r1 = r9.iPrefix;
            r12 = r1.parse(r11, r12);
            if (r12 < 0) goto L_0x0083;
        L_0x001f:
            r0 = 1;
        L_0x0020:
            r1 = -1;
            r2 = r9.iSuffix;
            if (r2 == 0) goto L_0x0150;
        L_0x0025:
            if (r0 != 0) goto L_0x0150;
        L_0x0027:
            r1 = r9.iSuffix;
            r1 = r1.scan(r11, r12);
            if (r1 < 0) goto L_0x0088;
        L_0x002f:
            r0 = 1;
            r6 = r1;
        L_0x0031:
            if (r0 != 0) goto L_0x003f;
        L_0x0033:
            r0 = r10.getPeriodType();
            r1 = r9.iFieldType;
            r0 = r9.isSupported(r0, r1);
            if (r0 == 0) goto L_0x0010;
        L_0x003f:
            if (r6 <= 0) goto L_0x008f;
        L_0x0041:
            r0 = r9.iMaxParsedDigits;
            r1 = r6 - r12;
            r0 = java.lang.Math.min(r0, r1);
        L_0x0049:
            r1 = 0;
            r3 = -1;
            r2 = 0;
            r4 = r0;
            r0 = r1;
            r1 = r12;
        L_0x004f:
            if (r0 >= r4) goto L_0x007e;
        L_0x0051:
            r5 = r1 + r0;
            r5 = r11.charAt(r5);
            if (r0 != 0) goto L_0x00b0;
        L_0x0059:
            r7 = 45;
            if (r5 == r7) goto L_0x0061;
        L_0x005d:
            r7 = 43;
            if (r5 != r7) goto L_0x00b0;
        L_0x0061:
            r7 = r9.iRejectSignedValues;
            if (r7 != 0) goto L_0x00b0;
        L_0x0065:
            r7 = 45;
            if (r5 != r7) goto L_0x009b;
        L_0x0069:
            r5 = 1;
        L_0x006a:
            r7 = r0 + 1;
            if (r7 >= r4) goto L_0x007e;
        L_0x006e:
            r7 = r1 + r0;
            r7 = r7 + 1;
            r7 = r11.charAt(r7);
            r8 = 48;
            if (r7 < r8) goto L_0x007e;
        L_0x007a:
            r8 = 57;
            if (r7 <= r8) goto L_0x009d;
        L_0x007e:
            if (r2 != 0) goto L_0x00e2;
        L_0x0080:
            r12 = r1 ^ -1;
            goto L_0x0010;
        L_0x0083:
            if (r0 != 0) goto L_0x0010;
        L_0x0085:
            r12 = r12 ^ -1;
            goto L_0x0010;
        L_0x0088:
            if (r0 != 0) goto L_0x008d;
        L_0x008a:
            r12 = r1 ^ -1;
            goto L_0x0010;
        L_0x008d:
            r12 = r1;
            goto L_0x0010;
        L_0x008f:
            r0 = r9.iMaxParsedDigits;
            r1 = r11.length();
            r1 = r1 - r12;
            r0 = java.lang.Math.min(r0, r1);
            goto L_0x0049;
        L_0x009b:
            r5 = 0;
            goto L_0x006a;
        L_0x009d:
            if (r5 == 0) goto L_0x00ad;
        L_0x009f:
            r0 = r0 + 1;
        L_0x00a1:
            r4 = r4 + 1;
            r5 = r11.length();
            r5 = r5 - r1;
            r4 = java.lang.Math.min(r4, r5);
            goto L_0x004f;
        L_0x00ad:
            r1 = r1 + 1;
            goto L_0x00a1;
        L_0x00b0:
            r7 = 48;
            if (r5 < r7) goto L_0x00bc;
        L_0x00b4:
            r7 = 57;
            if (r5 > r7) goto L_0x00bc;
        L_0x00b8:
            r2 = 1;
        L_0x00b9:
            r0 = r0 + 1;
            goto L_0x004f;
        L_0x00bc:
            r7 = 46;
            if (r5 == r7) goto L_0x00c4;
        L_0x00c0:
            r7 = 44;
            if (r5 != r7) goto L_0x007e;
        L_0x00c4:
            r5 = r9.iFieldType;
            r7 = 8;
            if (r5 == r7) goto L_0x00d0;
        L_0x00ca:
            r5 = r9.iFieldType;
            r7 = 9;
            if (r5 != r7) goto L_0x007e;
        L_0x00d0:
            if (r3 >= 0) goto L_0x007e;
        L_0x00d2:
            r3 = r1 + r0;
            r3 = r3 + 1;
            r4 = r4 + 1;
            r5 = r11.length();
            r5 = r5 - r1;
            r4 = java.lang.Math.min(r4, r5);
            goto L_0x00b9;
        L_0x00e2:
            if (r6 < 0) goto L_0x00eb;
        L_0x00e4:
            r2 = r1 + r0;
            if (r2 == r6) goto L_0x00eb;
        L_0x00e8:
            r12 = r1;
            goto L_0x0010;
        L_0x00eb:
            r2 = r9.iFieldType;
            r4 = 8;
            if (r2 == r4) goto L_0x0110;
        L_0x00f1:
            r2 = r9.iFieldType;
            r4 = 9;
            if (r2 == r4) goto L_0x0110;
        L_0x00f7:
            r2 = r9.iFieldType;
            r3 = r9.parseInt(r11, r1, r0);
            r9.setFieldValue(r10, r2, r3);
        L_0x0100:
            r0 = r0 + r1;
            if (r0 < 0) goto L_0x010d;
        L_0x0103:
            r1 = r9.iSuffix;
            if (r1 == 0) goto L_0x010d;
        L_0x0107:
            r1 = r9.iSuffix;
            r0 = r1.parse(r11, r0);
        L_0x010d:
            r12 = r0;
            goto L_0x0010;
        L_0x0110:
            if (r3 >= 0) goto L_0x0120;
        L_0x0112:
            r2 = 6;
            r3 = r9.parseInt(r11, r1, r0);
            r9.setFieldValue(r10, r2, r3);
            r2 = 7;
            r3 = 0;
            r9.setFieldValue(r10, r2, r3);
            goto L_0x0100;
        L_0x0120:
            r2 = r3 - r1;
            r2 = r2 + -1;
            r4 = r9.parseInt(r11, r1, r2);
            r2 = 6;
            r9.setFieldValue(r10, r2, r4);
            r2 = r1 + r0;
            r2 = r2 - r3;
            if (r2 > 0) goto L_0x0137;
        L_0x0131:
            r2 = 0;
        L_0x0132:
            r3 = 7;
            r9.setFieldValue(r10, r3, r2);
            goto L_0x0100;
        L_0x0137:
            r5 = 3;
            if (r2 < r5) goto L_0x0143;
        L_0x013a:
            r2 = 3;
            r2 = r9.parseInt(r11, r3, r2);
        L_0x013f:
            if (r4 >= 0) goto L_0x0132;
        L_0x0141:
            r2 = -r2;
            goto L_0x0132;
        L_0x0143:
            r3 = r9.parseInt(r11, r3, r2);
            r5 = 1;
            if (r2 != r5) goto L_0x014d;
        L_0x014a:
            r2 = r3 * 100;
            goto L_0x013f;
        L_0x014d:
            r2 = r3 * 10;
            goto L_0x013f;
        L_0x0150:
            r6 = r1;
            goto L_0x0031;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.FieldFormatter.parseInto(org.joda.time.ReadWritablePeriod, java.lang.String, int, java.util.Locale):int");
        }

        private int parseInt(String str, int i, int i2) {
            int i3 = 0;
            if (i2 >= 10) {
                return Integer.parseInt(str.substring(i, i + i2));
            }
            if (i2 <= 0) {
                return 0;
            }
            int i4;
            int i5 = i + PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
            int charAt = str.charAt(i);
            int i6 = i2 - 1;
            if (charAt == 45) {
                i6--;
                if (i6 < 0) {
                    return 0;
                }
                i3 = PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                i4 = i5 + PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                charAt = str.charAt(i5);
            } else {
                i4 = i5;
            }
            charAt -= 48;
            i5 = i4;
            while (true) {
                i4 = i6 - 1;
                if (i6 <= 0) {
                    break;
                }
                charAt = (((charAt << PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) + (charAt << PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED)) + str.charAt(i5)) - 48;
                i5 += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                i6 = i4;
            }
            return i3 != 0 ? -charAt : charAt;
        }

        long getFieldValue(ReadablePeriod readablePeriod) {
            PeriodType periodType;
            if (this.iPrintZeroSetting == PeriodFormatterBuilder.PRINT_ZERO_ALWAYS) {
                periodType = null;
            } else {
                periodType = readablePeriod.getPeriodType();
            }
            if (periodType != null && !isSupported(periodType, this.iFieldType)) {
                return Long.MAX_VALUE;
            }
            long j;
            switch (this.iFieldType) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    j = (long) readablePeriod.get(DurationFieldType.years());
                    break;
                case PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST /*1*/:
                    j = (long) readablePeriod.get(DurationFieldType.months());
                    break;
                case PeriodFormatterBuilder.WEEKS /*2*/:
                    j = (long) readablePeriod.get(DurationFieldType.weeks());
                    break;
                case PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED /*3*/:
                    j = (long) readablePeriod.get(DurationFieldType.days());
                    break;
                case PeriodFormatterBuilder.PRINT_ZERO_ALWAYS /*4*/:
                    j = (long) readablePeriod.get(DurationFieldType.hours());
                    break;
                case PeriodFormatterBuilder.PRINT_ZERO_NEVER /*5*/:
                    j = (long) readablePeriod.get(DurationFieldType.minutes());
                    break;
                case PeriodFormatterBuilder.SECONDS /*6*/:
                    j = (long) readablePeriod.get(DurationFieldType.seconds());
                    break;
                case PeriodFormatterBuilder.MILLIS /*7*/:
                    j = (long) readablePeriod.get(DurationFieldType.millis());
                    break;
                case PeriodFormatterBuilder.SECONDS_MILLIS /*8*/:
                case PeriodFormatterBuilder.SECONDS_OPTIONAL_MILLIS /*9*/:
                    j = (long) readablePeriod.get(DurationFieldType.millis());
                    j += ((long) readablePeriod.get(DurationFieldType.seconds())) * 1000;
                    break;
                default:
                    return Long.MAX_VALUE;
            }
            if (j == 0) {
                int min;
                switch (this.iPrintZeroSetting) {
                    case PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST /*1*/:
                        if (!isZero(readablePeriod) || this.iFieldFormatters[this.iFieldType] != this) {
                            return Long.MAX_VALUE;
                        }
                        min = Math.min(this.iFieldType, PeriodFormatterBuilder.SECONDS_MILLIS) - 1;
                        while (min >= 0 && min <= PeriodFormatterBuilder.SECONDS_OPTIONAL_MILLIS) {
                            if (isSupported(periodType, min) && this.iFieldFormatters[min] != null) {
                                return Long.MAX_VALUE;
                            }
                            min--;
                        }
                        break;
                    case PeriodFormatterBuilder.WEEKS /*2*/:
                        if (isZero(readablePeriod) && this.iFieldFormatters[this.iFieldType] == this) {
                            min = this.iFieldType + PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                            while (min <= PeriodFormatterBuilder.SECONDS_OPTIONAL_MILLIS) {
                                if (isSupported(periodType, min) && this.iFieldFormatters[min] != null) {
                                    return Long.MAX_VALUE;
                                }
                                min += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                            }
                            break;
                        }
                        return Long.MAX_VALUE;
                        break;
                    case PeriodFormatterBuilder.PRINT_ZERO_NEVER /*5*/:
                        return Long.MAX_VALUE;
                }
            }
            return j;
        }

        boolean isZero(ReadablePeriod readablePeriod) {
            int size = readablePeriod.size();
            for (int i = 0; i < size; i += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST) {
                if (readablePeriod.getValue(i) != 0) {
                    return false;
                }
            }
            return true;
        }

        boolean isSupported(PeriodType periodType, int i) {
            switch (i) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    return periodType.isSupported(DurationFieldType.years());
                case PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST /*1*/:
                    return periodType.isSupported(DurationFieldType.months());
                case PeriodFormatterBuilder.WEEKS /*2*/:
                    return periodType.isSupported(DurationFieldType.weeks());
                case PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED /*3*/:
                    return periodType.isSupported(DurationFieldType.days());
                case PeriodFormatterBuilder.PRINT_ZERO_ALWAYS /*4*/:
                    return periodType.isSupported(DurationFieldType.hours());
                case PeriodFormatterBuilder.PRINT_ZERO_NEVER /*5*/:
                    return periodType.isSupported(DurationFieldType.minutes());
                case PeriodFormatterBuilder.SECONDS /*6*/:
                    return periodType.isSupported(DurationFieldType.seconds());
                case PeriodFormatterBuilder.MILLIS /*7*/:
                    return periodType.isSupported(DurationFieldType.millis());
                case PeriodFormatterBuilder.SECONDS_MILLIS /*8*/:
                case PeriodFormatterBuilder.SECONDS_OPTIONAL_MILLIS /*9*/:
                    return periodType.isSupported(DurationFieldType.seconds()) || periodType.isSupported(DurationFieldType.millis());
                default:
                    return false;
            }
        }

        void setFieldValue(ReadWritablePeriod readWritablePeriod, int i, int i2) {
            switch (i) {
                case MutableDateTime.ROUND_NONE /*0*/:
                    readWritablePeriod.setYears(i2);
                case PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST /*1*/:
                    readWritablePeriod.setMonths(i2);
                case PeriodFormatterBuilder.WEEKS /*2*/:
                    readWritablePeriod.setWeeks(i2);
                case PeriodFormatterBuilder.PRINT_ZERO_IF_SUPPORTED /*3*/:
                    readWritablePeriod.setDays(i2);
                case PeriodFormatterBuilder.PRINT_ZERO_ALWAYS /*4*/:
                    readWritablePeriod.setHours(i2);
                case PeriodFormatterBuilder.PRINT_ZERO_NEVER /*5*/:
                    readWritablePeriod.setMinutes(i2);
                case PeriodFormatterBuilder.SECONDS /*6*/:
                    readWritablePeriod.setSeconds(i2);
                case PeriodFormatterBuilder.MILLIS /*7*/:
                    readWritablePeriod.setMillis(i2);
                default:
            }
        }

        int getFieldType() {
            return this.iFieldType;
        }
    }

    static class Literal implements PeriodPrinter, PeriodParser {
        static final Literal EMPTY;
        private final String iText;

        static {
            EMPTY = new Literal(StringUtils.EMPTY);
        }

        Literal(String str) {
            this.iText = str;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            return 0;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            return this.iText.length();
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            stringBuffer.append(this.iText);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            writer.write(this.iText);
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            if (str.regionMatches(true, i, this.iText, 0, this.iText.length())) {
                return this.iText.length() + i;
            }
            return i ^ -1;
        }
    }

    static class PluralAffix implements PeriodFieldAffix {
        private final String iPluralText;
        private final String iSingularText;

        PluralAffix(String str, String str2) {
            this.iSingularText = str;
            this.iPluralText = str2;
        }

        public int calculatePrintedLength(int i) {
            return (i == PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iSingularText : this.iPluralText).length();
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            stringBuffer.append(i == PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iSingularText : this.iPluralText);
        }

        public void printTo(Writer writer, int i) throws IOException {
            writer.write(i == PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iSingularText : this.iPluralText);
        }

        public int parse(String str, int i) {
            String str2;
            String str3;
            String str4 = this.iPluralText;
            String str5 = this.iSingularText;
            if (str4.length() < str5.length()) {
                str2 = str4;
                str3 = str5;
            } else {
                str2 = str5;
                str3 = str4;
            }
            if (str.regionMatches(true, i, str3, 0, str3.length())) {
                return str3.length() + i;
            }
            if (str.regionMatches(true, i, str2, 0, str2.length())) {
                return str2.length() + i;
            }
            return i ^ -1;
        }

        public int scan(String str, int i) {
            String str2;
            String str3 = this.iPluralText;
            String str4 = this.iSingularText;
            if (str3.length() < str4.length()) {
                str2 = str4;
            } else {
                str2 = str3;
                str3 = str4;
            }
            int length = str2.length();
            int length2 = str3.length();
            int length3 = str.length();
            int i2 = i;
            while (i2 < length3) {
                if (str.regionMatches(true, i2, str2, 0, length) || str.regionMatches(true, i2, str3, 0, length2)) {
                    return i2;
                }
                i2 += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
            }
            return i ^ -1;
        }
    }

    static class Separator implements PeriodPrinter, PeriodParser {
        private volatile PeriodParser iAfterParser;
        private volatile PeriodPrinter iAfterPrinter;
        private final PeriodParser iBeforeParser;
        private final PeriodPrinter iBeforePrinter;
        private final String iFinalText;
        private final String[] iParsedForms;
        private final String iText;
        private final boolean iUseAfter;
        private final boolean iUseBefore;

        Separator(String str, String str2, String[] strArr, PeriodPrinter periodPrinter, PeriodParser periodParser, boolean z, boolean z2) {
            this.iText = str;
            this.iFinalText = str2;
            if ((str2 == null || str.equals(str2)) && (strArr == null || strArr.length == 0)) {
                String[] strArr2 = new String[PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST];
                strArr2[0] = str;
                this.iParsedForms = strArr2;
            } else {
                Collection treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                treeSet.add(str);
                treeSet.add(str2);
                if (strArr != null) {
                    int length = strArr.length;
                    while (true) {
                        length--;
                        if (length < 0) {
                            break;
                        }
                        treeSet.add(strArr[length]);
                    }
                }
                ArrayList arrayList = new ArrayList(treeSet);
                Collections.reverse(arrayList);
                this.iParsedForms = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            this.iBeforePrinter = periodPrinter;
            this.iBeforeParser = periodParser;
            this.iUseBefore = z;
            this.iUseAfter = z2;
        }

        public int countFieldsToPrint(ReadablePeriod readablePeriod, int i, Locale locale) {
            int countFieldsToPrint = this.iBeforePrinter.countFieldsToPrint(readablePeriod, i, locale);
            if (countFieldsToPrint < i) {
                return countFieldsToPrint + this.iAfterPrinter.countFieldsToPrint(readablePeriod, i, locale);
            }
            return countFieldsToPrint;
        }

        public int calculatePrintedLength(ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            int calculatePrintedLength = periodPrinter.calculatePrintedLength(readablePeriod, locale) + periodPrinter2.calculatePrintedLength(readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) <= 0) {
                    return calculatePrintedLength;
                }
                if (!this.iUseAfter) {
                    return calculatePrintedLength + this.iText.length();
                }
                int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.WEEKS, locale);
                if (countFieldsToPrint > 0) {
                    countFieldsToPrint = (countFieldsToPrint > PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iText : this.iFinalText).length() + calculatePrintedLength;
                } else {
                    countFieldsToPrint = calculatePrintedLength;
                }
                return countFieldsToPrint;
            } else if (!this.iUseAfter || periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) <= 0) {
                return calculatePrintedLength;
            } else {
                return calculatePrintedLength + this.iText.length();
            }
        }

        public void printTo(StringBuffer stringBuffer, ReadablePeriod readablePeriod, Locale locale) {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(stringBuffer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) > 0) {
                    if (this.iUseAfter) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.WEEKS, locale);
                        if (countFieldsToPrint > 0) {
                            stringBuffer.append(countFieldsToPrint > PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iText : this.iFinalText);
                        }
                    } else {
                        stringBuffer.append(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) > 0) {
                stringBuffer.append(this.iText);
            }
            periodPrinter2.printTo(stringBuffer, readablePeriod, locale);
        }

        public void printTo(Writer writer, ReadablePeriod readablePeriod, Locale locale) throws IOException {
            PeriodPrinter periodPrinter = this.iBeforePrinter;
            PeriodPrinter periodPrinter2 = this.iAfterPrinter;
            periodPrinter.printTo(writer, readablePeriod, locale);
            if (this.iUseBefore) {
                if (periodPrinter.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) > 0) {
                    if (this.iUseAfter) {
                        int countFieldsToPrint = periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.WEEKS, locale);
                        if (countFieldsToPrint > 0) {
                            writer.write(countFieldsToPrint > PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST ? this.iText : this.iFinalText);
                        }
                    } else {
                        writer.write(this.iText);
                    }
                }
            } else if (this.iUseAfter && periodPrinter2.countFieldsToPrint(readablePeriod, PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST, locale) > 0) {
                writer.write(this.iText);
            }
            periodPrinter2.printTo(writer, readablePeriod, locale);
        }

        public int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i, Locale locale) {
            boolean z = true;
            int i2 = 0;
            int parseInto = this.iBeforeParser.parseInto(readWritablePeriod, str, i, locale);
            if (parseInto < 0) {
                return parseInto;
            }
            int parseInto2;
            if (parseInto > i) {
                String[] strArr = this.iParsedForms;
                int length = strArr.length;
                int i3 = 0;
                while (i3 < length) {
                    String str2 = strArr[i3];
                    if (!(str2 == null || str2.length() == 0)) {
                        if (!str.regionMatches(true, parseInto, str2, 0, str2.length())) {
                            i3 += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                        }
                    }
                    if (str2 != null) {
                        i2 = str2.length();
                    }
                    parseInto += i2;
                    parseInto2 = this.iAfterParser.parseInto(readWritablePeriod, str, parseInto, locale);
                    if (parseInto2 < 0) {
                        return parseInto2;
                    }
                    if (z || parseInto2 != parseInto || r4 <= 0) {
                        return (parseInto2 > parseInto || z || this.iUseBefore) ? parseInto2 : parseInto ^ -1;
                    } else {
                        return parseInto ^ -1;
                    }
                }
            }
            z = false;
            i2 = -1;
            parseInto2 = this.iAfterParser.parseInto(readWritablePeriod, str, parseInto, locale);
            if (parseInto2 < 0) {
                return parseInto2;
            }
            if (z) {
            }
            if (parseInto2 > parseInto) {
            }
        }

        Separator finish(PeriodPrinter periodPrinter, PeriodParser periodParser) {
            this.iAfterPrinter = periodPrinter;
            this.iAfterParser = periodParser;
            return this;
        }
    }

    static class SimpleAffix implements PeriodFieldAffix {
        private final String iText;

        SimpleAffix(String str) {
            this.iText = str;
        }

        public int calculatePrintedLength(int i) {
            return this.iText.length();
        }

        public void printTo(StringBuffer stringBuffer, int i) {
            stringBuffer.append(this.iText);
        }

        public void printTo(Writer writer, int i) throws IOException {
            writer.write(this.iText);
        }

        public int parse(String str, int i) {
            String str2 = this.iText;
            int length = str2.length();
            if (str.regionMatches(true, i, str2, 0, length)) {
                return i + length;
            }
            return i ^ -1;
        }

        public int scan(String str, int i) {
            String str2 = this.iText;
            int length = str2.length();
            int length2 = str.length();
            int i2 = i;
            while (i2 < length2) {
                if (str.regionMatches(true, i2, str2, 0, length)) {
                    return i2;
                }
                switch (str.charAt(i2)) {
                    case '+':
                    case ',':
                    case '-':
                    case '.':
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        i2 += PeriodFormatterBuilder.PRINT_ZERO_RARELY_FIRST;
                    default:
                        break;
                }
                return i ^ -1;
            }
            return i ^ -1;
        }
    }

    public PeriodFormatterBuilder() {
        clear();
    }

    public PeriodFormatter toFormatter() {
        PeriodFormatter toFormatter = toFormatter(this.iElementPairs, this.iNotPrinter, this.iNotParser);
        this.iFieldFormatters = (FieldFormatter[]) this.iFieldFormatters.clone();
        return toFormatter;
    }

    public PeriodPrinter toPrinter() {
        if (this.iNotPrinter) {
            return null;
        }
        return toFormatter().getPrinter();
    }

    public PeriodParser toParser() {
        if (this.iNotParser) {
            return null;
        }
        return toFormatter().getParser();
    }

    public void clear() {
        this.iMinPrintedDigits = PRINT_ZERO_RARELY_FIRST;
        this.iPrintZeroSetting = WEEKS;
        this.iMaxParsedDigits = 10;
        this.iRejectSignedValues = false;
        this.iPrefix = null;
        if (this.iElementPairs == null) {
            this.iElementPairs = new ArrayList();
        } else {
            this.iElementPairs.clear();
        }
        this.iNotPrinter = false;
        this.iNotParser = false;
        this.iFieldFormatters = new FieldFormatter[10];
    }

    public PeriodFormatterBuilder append(PeriodFormatter periodFormatter) {
        if (periodFormatter == null) {
            throw new IllegalArgumentException("No formatter supplied");
        }
        clearPrefix();
        append0(periodFormatter.getPrinter(), periodFormatter.getParser());
        return this;
    }

    public PeriodFormatterBuilder append(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        if (periodPrinter == null && periodParser == null) {
            throw new IllegalArgumentException("No printer or parser supplied");
        }
        clearPrefix();
        append0(periodPrinter, periodParser);
        return this;
    }

    public PeriodFormatterBuilder appendLiteral(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Literal must not be null");
        }
        clearPrefix();
        Object literal = new Literal(str);
        append0(literal, literal);
        return this;
    }

    public PeriodFormatterBuilder minimumPrintedDigits(int i) {
        this.iMinPrintedDigits = i;
        return this;
    }

    public PeriodFormatterBuilder maximumParsedDigits(int i) {
        this.iMaxParsedDigits = i;
        return this;
    }

    public PeriodFormatterBuilder rejectSignedValues(boolean z) {
        this.iRejectSignedValues = z;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyLast() {
        this.iPrintZeroSetting = WEEKS;
        return this;
    }

    public PeriodFormatterBuilder printZeroRarelyFirst() {
        this.iPrintZeroSetting = PRINT_ZERO_RARELY_FIRST;
        return this;
    }

    public PeriodFormatterBuilder printZeroIfSupported() {
        this.iPrintZeroSetting = PRINT_ZERO_IF_SUPPORTED;
        return this;
    }

    public PeriodFormatterBuilder printZeroAlways() {
        this.iPrintZeroSetting = PRINT_ZERO_ALWAYS;
        return this;
    }

    public PeriodFormatterBuilder printZeroNever() {
        this.iPrintZeroSetting = PRINT_ZERO_NEVER;
        return this;
    }

    public PeriodFormatterBuilder appendPrefix(String str) {
        if (str != null) {
            return appendPrefix(new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendPrefix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendPrefix(new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendPrefix(PeriodFieldAffix periodFieldAffix) {
        if (periodFieldAffix == null) {
            throw new IllegalArgumentException();
        }
        if (this.iPrefix != null) {
            periodFieldAffix = new CompositeAffix(this.iPrefix, periodFieldAffix);
        }
        this.iPrefix = periodFieldAffix;
        return this;
    }

    public PeriodFormatterBuilder appendYears() {
        appendField(0);
        return this;
    }

    public PeriodFormatterBuilder appendMonths() {
        appendField(PRINT_ZERO_RARELY_FIRST);
        return this;
    }

    public PeriodFormatterBuilder appendWeeks() {
        appendField(WEEKS);
        return this;
    }

    public PeriodFormatterBuilder appendDays() {
        appendField(PRINT_ZERO_IF_SUPPORTED);
        return this;
    }

    public PeriodFormatterBuilder appendHours() {
        appendField(PRINT_ZERO_ALWAYS);
        return this;
    }

    public PeriodFormatterBuilder appendMinutes() {
        appendField(PRINT_ZERO_NEVER);
        return this;
    }

    public PeriodFormatterBuilder appendSeconds() {
        appendField(SECONDS);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithMillis() {
        appendField(SECONDS_MILLIS);
        return this;
    }

    public PeriodFormatterBuilder appendSecondsWithOptionalMillis() {
        appendField(SECONDS_OPTIONAL_MILLIS);
        return this;
    }

    public PeriodFormatterBuilder appendMillis() {
        appendField(MILLIS);
        return this;
    }

    public PeriodFormatterBuilder appendMillis3Digit() {
        appendField(MILLIS, PRINT_ZERO_IF_SUPPORTED);
        return this;
    }

    private void appendField(int i) {
        appendField(i, this.iMinPrintedDigits);
    }

    private void appendField(int i, int i2) {
        Object fieldFormatter = new FieldFormatter(i2, this.iPrintZeroSetting, this.iMaxParsedDigits, this.iRejectSignedValues, i, this.iFieldFormatters, this.iPrefix, null);
        append0(fieldFormatter, fieldFormatter);
        this.iFieldFormatters[i] = fieldFormatter;
        this.iPrefix = null;
    }

    public PeriodFormatterBuilder appendSuffix(String str) {
        if (str != null) {
            return appendSuffix(new SimpleAffix(str));
        }
        throw new IllegalArgumentException();
    }

    public PeriodFormatterBuilder appendSuffix(String str, String str2) {
        if (str != null && str2 != null) {
            return appendSuffix(new PluralAffix(str, str2));
        }
        throw new IllegalArgumentException();
    }

    private PeriodFormatterBuilder appendSuffix(PeriodFieldAffix periodFieldAffix) {
        Object obj = null;
        Object obj2;
        if (this.iElementPairs.size() > 0) {
            obj2 = this.iElementPairs.get(this.iElementPairs.size() - 2);
            obj = obj2;
            obj2 = this.iElementPairs.get(this.iElementPairs.size() - 1);
        } else {
            obj2 = null;
        }
        if (obj == null || r1 == null || obj != r1 || !(obj instanceof FieldFormatter)) {
            throw new IllegalStateException("No field to apply suffix to");
        }
        clearPrefix();
        FieldFormatter fieldFormatter = new FieldFormatter((FieldFormatter) obj, periodFieldAffix);
        this.iElementPairs.set(this.iElementPairs.size() - 2, fieldFormatter);
        this.iElementPairs.set(this.iElementPairs.size() - 1, fieldFormatter);
        this.iFieldFormatters[fieldFormatter.getFieldType()] = fieldFormatter;
        return this;
    }

    public PeriodFormatterBuilder appendSeparator(String str) {
        return appendSeparator(str, str, null, true, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsAfter(String str) {
        return appendSeparator(str, str, null, false, true);
    }

    public PeriodFormatterBuilder appendSeparatorIfFieldsBefore(String str) {
        return appendSeparator(str, str, null, true, false);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2) {
        return appendSeparator(str, str2, null, true, true);
    }

    public PeriodFormatterBuilder appendSeparator(String str, String str2, String[] strArr) {
        return appendSeparator(str, str2, strArr, true, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.joda.time.format.PeriodFormatterBuilder appendSeparator(java.lang.String r10, java.lang.String r11, java.lang.String[] r12, boolean r13, boolean r14) {
        /*
        r9 = this;
        if (r10 == 0) goto L_0x0004;
    L_0x0002:
        if (r11 != 0) goto L_0x000a;
    L_0x0004:
        r0 = new java.lang.IllegalArgumentException;
        r0.<init>();
        throw r0;
    L_0x000a:
        r9.clearPrefix();
        r1 = r9.iElementPairs;
        r0 = r1.size();
        if (r0 != 0) goto L_0x002b;
    L_0x0015:
        if (r14 == 0) goto L_0x002a;
    L_0x0017:
        if (r13 != 0) goto L_0x002a;
    L_0x0019:
        r0 = new org.joda.time.format.PeriodFormatterBuilder$Separator;
        r4 = org.joda.time.format.PeriodFormatterBuilder.Literal.EMPTY;
        r5 = org.joda.time.format.PeriodFormatterBuilder.Literal.EMPTY;
        r1 = r10;
        r2 = r11;
        r3 = r12;
        r6 = r13;
        r7 = r14;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r9.append0(r0, r0);
    L_0x002a:
        return r9;
    L_0x002b:
        r0 = 0;
        r2 = r1.size();
    L_0x0030:
        r2 = r2 + -1;
        if (r2 < 0) goto L_0x0082;
    L_0x0034:
        r3 = r1.get(r2);
        r3 = r3 instanceof org.joda.time.format.PeriodFormatterBuilder.Separator;
        if (r3 == 0) goto L_0x005d;
    L_0x003c:
        r0 = r1.get(r2);
        r0 = (org.joda.time.format.PeriodFormatterBuilder.Separator) r0;
        r2 = r2 + 1;
        r3 = r1.size();
        r1 = r1.subList(r2, r3);
        r8 = r1;
    L_0x004d:
        if (r0 == 0) goto L_0x0060;
    L_0x004f:
        r0 = r8.size();
        if (r0 != 0) goto L_0x0060;
    L_0x0055:
        r0 = new java.lang.IllegalStateException;
        r1 = "Cannot have two adjacent separators";
        r0.<init>(r1);
        throw r0;
    L_0x005d:
        r2 = r2 + -1;
        goto L_0x0030;
    L_0x0060:
        r1 = createComposite(r8);
        r8.clear();
        r0 = new org.joda.time.format.PeriodFormatterBuilder$Separator;
        r2 = 0;
        r4 = r1[r2];
        r4 = (org.joda.time.format.PeriodPrinter) r4;
        r2 = 1;
        r5 = r1[r2];
        r5 = (org.joda.time.format.PeriodParser) r5;
        r1 = r10;
        r2 = r11;
        r3 = r12;
        r6 = r13;
        r7 = r14;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r8.add(r0);
        r8.add(r0);
        goto L_0x002a;
    L_0x0082:
        r8 = r1;
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.format.PeriodFormatterBuilder.appendSeparator(java.lang.String, java.lang.String, java.lang.String[], boolean, boolean):org.joda.time.format.PeriodFormatterBuilder");
    }

    private void clearPrefix() throws IllegalStateException {
        if (this.iPrefix != null) {
            throw new IllegalStateException("Prefix not followed by field");
        }
        this.iPrefix = null;
    }

    private PeriodFormatterBuilder append0(PeriodPrinter periodPrinter, PeriodParser periodParser) {
        int i;
        int i2 = PRINT_ZERO_RARELY_FIRST;
        this.iElementPairs.add(periodPrinter);
        this.iElementPairs.add(periodParser);
        boolean z = this.iNotPrinter;
        if (periodPrinter == null) {
            i = PRINT_ZERO_RARELY_FIRST;
        } else {
            i = 0;
        }
        this.iNotPrinter = i | z;
        boolean z2 = this.iNotParser;
        if (periodParser != null) {
            i2 = 0;
        }
        this.iNotParser = z2 | i2;
        return this;
    }

    private static PeriodFormatter toFormatter(List<Object> list, boolean z, boolean z2) {
        if (z && z2) {
            throw new IllegalStateException("Builder has created neither a printer nor a parser");
        }
        int size = list.size();
        if (size >= WEEKS && (list.get(0) instanceof Separator)) {
            Separator separator = (Separator) list.get(0);
            if (separator.iAfterParser == null && separator.iAfterPrinter == null) {
                PeriodFormatter toFormatter = toFormatter(list.subList(WEEKS, size), z, z2);
                Object finish = separator.finish(toFormatter.getPrinter(), toFormatter.getParser());
                return new PeriodFormatter(finish, finish);
            }
        }
        Object[] createComposite = createComposite(list);
        if (z) {
            return new PeriodFormatter(null, (PeriodParser) createComposite[PRINT_ZERO_RARELY_FIRST]);
        }
        if (z2) {
            return new PeriodFormatter((PeriodPrinter) createComposite[0], null);
        }
        return new PeriodFormatter((PeriodPrinter) createComposite[0], (PeriodParser) createComposite[PRINT_ZERO_RARELY_FIRST]);
    }

    private static Object[] createComposite(List<Object> list) {
        Object[] objArr;
        switch (list.size()) {
            case MutableDateTime.ROUND_NONE /*0*/:
                objArr = new Object[WEEKS];
                objArr[0] = Literal.EMPTY;
                objArr[PRINT_ZERO_RARELY_FIRST] = Literal.EMPTY;
                return objArr;
            case PRINT_ZERO_RARELY_FIRST /*1*/:
                objArr = new Object[WEEKS];
                objArr[0] = list.get(0);
                objArr[PRINT_ZERO_RARELY_FIRST] = list.get(PRINT_ZERO_RARELY_FIRST);
                return objArr;
            default:
                Composite composite = new Composite(list);
                objArr = new Object[WEEKS];
                objArr[0] = composite;
                objArr[PRINT_ZERO_RARELY_FIRST] = composite;
                return objArr;
        }
    }
}
