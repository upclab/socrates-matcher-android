package org.apache.commons.lang3;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.EntityArrays;
import org.apache.commons.lang3.text.translate.LookupTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper.OPTION;
import org.apache.commons.lang3.text.translate.OctalUnescaper;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.commons.lang3.text.translate.UnicodeUnescaper;

public class StringEscapeUtils {
    public static final CharSequenceTranslator ESCAPE_CSV;
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator ESCAPE_HTML3;
    public static final CharSequenceTranslator ESCAPE_HTML4;
    public static final CharSequenceTranslator ESCAPE_JAVA;
    public static final CharSequenceTranslator ESCAPE_XML;
    public static final CharSequenceTranslator UNESCAPE_CSV;
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT;
    public static final CharSequenceTranslator UNESCAPE_HTML3;
    public static final CharSequenceTranslator UNESCAPE_HTML4;
    public static final CharSequenceTranslator UNESCAPE_JAVA;
    public static final CharSequenceTranslator UNESCAPE_XML;

    static class CsvEscaper extends CharSequenceTranslator {
        private static final char CSV_DELIMITER = ',';
        private static final char CSV_QUOTE = '\"';
        private static final String CSV_QUOTE_STR;
        private static final char[] CSV_SEARCH_CHARS;

        CsvEscaper() {
        }

        static {
            CSV_QUOTE_STR = String.valueOf(CSV_QUOTE);
            CSV_SEARCH_CHARS = new char[]{CSV_DELIMITER, CSV_QUOTE, CharUtils.CR, '\n'};
        }

        public int translate(CharSequence input, int index, Writer out) throws IOException {
            if (index != 0) {
                throw new IllegalStateException("CsvEscaper should never reach the [1] index");
            }
            if (StringUtils.containsNone(input.toString(), CSV_SEARCH_CHARS)) {
                out.write(input.toString());
            } else {
                out.write(34);
                out.write(StringUtils.replace(input.toString(), CSV_QUOTE_STR, CSV_QUOTE_STR + CSV_QUOTE_STR));
                out.write(34);
            }
            return input.length();
        }
    }

    static class CsvUnescaper extends CharSequenceTranslator {
        private static final char CSV_DELIMITER = ',';
        private static final char CSV_QUOTE = '\"';
        private static final String CSV_QUOTE_STR;
        private static final char[] CSV_SEARCH_CHARS;

        CsvUnescaper() {
        }

        static {
            CSV_QUOTE_STR = String.valueOf(CSV_QUOTE);
            CSV_SEARCH_CHARS = new char[]{CSV_DELIMITER, CSV_QUOTE, CharUtils.CR, '\n'};
        }

        public int translate(CharSequence input, int index, Writer out) throws IOException {
            if (index != 0) {
                throw new IllegalStateException("CsvUnescaper should never reach the [1] index");
            } else if (input.charAt(0) == CSV_QUOTE && input.charAt(input.length() - 1) == CSV_QUOTE) {
                CharSequence quoteless = input.subSequence(1, input.length() - 1).toString();
                if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS)) {
                    out.write(StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR));
                } else {
                    out.write(input.toString());
                }
                return input.length();
            } else {
                out.write(input.toString());
                return input.length();
            }
        }
    }

    static {
        r1 = new String[2][];
        r1[0] = new String[]{"\"", "\\\""};
        r1[1] = new String[]{"\\", "\\\\"};
        ESCAPE_JAVA = new LookupTranslator(r1).with(new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE())).with(UnicodeEscaper.outsideOf(32, 127));
        r1 = new CharSequenceTranslator[3];
        r3 = new String[4][];
        r3[0] = new String[]{"'", "\\'"};
        r3[1] = new String[]{"\"", "\\\""};
        r3[2] = new String[]{"\\", "\\\\"};
        r3[3] = new String[]{"/", "\\/"};
        r1[0] = new LookupTranslator(r3);
        r1[1] = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE());
        r1[2] = UnicodeEscaper.outsideOf(32, 127);
        ESCAPE_ECMASCRIPT = new AggregateTranslator(r1);
        ESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()));
        ESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()));
        ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()));
        ESCAPE_CSV = new CsvEscaper();
        r1 = new CharSequenceTranslator[4];
        r1[0] = new OctalUnescaper();
        r1[1] = new UnicodeUnescaper();
        r1[2] = new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE());
        r3 = new String[4][];
        r3[0] = new String[]{"\\\\", "\\"};
        r3[1] = new String[]{"\\\"", "\""};
        r3[2] = new String[]{"\\'", "'"};
        r3[3] = new String[]{"\\", StringUtils.EMPTY};
        r1[3] = new LookupTranslator(r3);
        UNESCAPE_JAVA = new AggregateTranslator(r1);
        UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;
        UNESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new NumericEntityUnescaper(new OPTION[0]));
        UNESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE()), new NumericEntityUnescaper(new OPTION[0]));
        UNESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.APOS_UNESCAPE()), new NumericEntityUnescaper(new OPTION[0]));
        UNESCAPE_CSV = new CsvUnescaper();
    }

    public static final String escapeJava(String input) {
        return ESCAPE_JAVA.translate(input);
    }

    public static final String escapeEcmaScript(String input) {
        return ESCAPE_ECMASCRIPT.translate(input);
    }

    public static final String unescapeJava(String input) {
        return UNESCAPE_JAVA.translate(input);
    }

    public static final String unescapeEcmaScript(String input) {
        return UNESCAPE_ECMASCRIPT.translate(input);
    }

    public static final String escapeHtml4(String input) {
        return ESCAPE_HTML4.translate(input);
    }

    public static final String escapeHtml3(String input) {
        return ESCAPE_HTML3.translate(input);
    }

    public static final String unescapeHtml4(String input) {
        return UNESCAPE_HTML4.translate(input);
    }

    public static final String unescapeHtml3(String input) {
        return UNESCAPE_HTML3.translate(input);
    }

    public static final String escapeXml(String input) {
        return ESCAPE_XML.translate(input);
    }

    public static final String unescapeXml(String input) {
        return UNESCAPE_XML.translate(input);
    }

    public static final String escapeCsv(String input) {
        return ESCAPE_CSV.translate(input);
    }

    public static final String unescapeCsv(String input) {
        return UNESCAPE_CSV.translate(input);
    }
}
