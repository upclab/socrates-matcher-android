package org.joda.time.format;

import com.adsdk.sdk.Const;
import java.io.IOException;
import java.io.Writer;
import org.joda.time.DateTimeConstants;

public class FormatUtils {
    private static final double LOG_10;

    static {
        LOG_10 = Math.log(10.0d);
    }

    private FormatUtils() {
    }

    public static void appendPaddedInteger(StringBuffer stringBuffer, int i, int i2) {
        if (i < 0) {
            stringBuffer.append('-');
            if (i != Integer.MIN_VALUE) {
                i = -i;
            } else {
                while (i2 > 10) {
                    stringBuffer.append('0');
                    i2--;
                }
                stringBuffer.append("2147483648");
                return;
            }
        }
        if (i < 10) {
            while (i2 > 1) {
                stringBuffer.append('0');
                i2--;
            }
            stringBuffer.append((char) (i + 48));
        } else if (i < 100) {
            while (i2 > 2) {
                stringBuffer.append('0');
                i2--;
            }
            r0 = ((i + 1) * 13421772) >> 27;
            stringBuffer.append((char) (r0 + 48));
            stringBuffer.append((char) (((i - (r0 << 3)) - (r0 << 1)) + 48));
        } else {
            if (i < DateTimeConstants.MILLIS_PER_SECOND) {
                r0 = 3;
            } else if (i < Const.SOCKET_TIMEOUT) {
                r0 = 4;
            } else {
                r0 = ((int) (Math.log((double) i) / LOG_10)) + 1;
            }
            while (i2 > r0) {
                stringBuffer.append('0');
                i2--;
            }
            stringBuffer.append(Integer.toString(i));
        }
    }

    public static void appendPaddedInteger(StringBuffer stringBuffer, long j, int i) {
        int i2 = (int) j;
        if (((long) i2) == j) {
            appendPaddedInteger(stringBuffer, i2, i);
        } else if (i <= 19) {
            stringBuffer.append(Long.toString(j));
        } else {
            if (j < 0) {
                stringBuffer.append('-');
                if (j != Long.MIN_VALUE) {
                    j = -j;
                } else {
                    while (i > 19) {
                        stringBuffer.append('0');
                        i--;
                    }
                    stringBuffer.append("9223372036854775808");
                    return;
                }
            }
            i2 = ((int) (Math.log((double) j) / LOG_10)) + 1;
            while (i > i2) {
                stringBuffer.append('0');
                i--;
            }
            stringBuffer.append(Long.toString(j));
        }
    }

    public static void writePaddedInteger(Writer writer, int i, int i2) throws IOException {
        if (i < 0) {
            writer.write(45);
            if (i != Integer.MIN_VALUE) {
                i = -i;
            } else {
                while (i2 > 10) {
                    writer.write(48);
                    i2--;
                }
                writer.write("2147483648");
                return;
            }
        }
        if (i < 10) {
            while (i2 > 1) {
                writer.write(48);
                i2--;
            }
            writer.write(i + 48);
        } else if (i < 100) {
            while (i2 > 2) {
                writer.write(48);
                i2--;
            }
            r0 = ((i + 1) * 13421772) >> 27;
            writer.write(r0 + 48);
            writer.write(((i - (r0 << 3)) - (r0 << 1)) + 48);
        } else {
            if (i < DateTimeConstants.MILLIS_PER_SECOND) {
                r0 = 3;
            } else if (i < Const.SOCKET_TIMEOUT) {
                r0 = 4;
            } else {
                r0 = ((int) (Math.log((double) i) / LOG_10)) + 1;
            }
            while (i2 > r0) {
                writer.write(48);
                i2--;
            }
            writer.write(Integer.toString(i));
        }
    }

    public static void writePaddedInteger(Writer writer, long j, int i) throws IOException {
        int i2 = (int) j;
        if (((long) i2) == j) {
            writePaddedInteger(writer, i2, i);
        } else if (i <= 19) {
            writer.write(Long.toString(j));
        } else {
            if (j < 0) {
                writer.write(45);
                if (j != Long.MIN_VALUE) {
                    j = -j;
                } else {
                    while (i > 19) {
                        writer.write(48);
                        i--;
                    }
                    writer.write("9223372036854775808");
                    return;
                }
            }
            i2 = ((int) (Math.log((double) j) / LOG_10)) + 1;
            while (i > i2) {
                writer.write(48);
                i--;
            }
            writer.write(Long.toString(j));
        }
    }

    public static void appendUnpaddedInteger(StringBuffer stringBuffer, int i) {
        if (i < 0) {
            stringBuffer.append('-');
            if (i != Integer.MIN_VALUE) {
                i = -i;
            } else {
                stringBuffer.append("2147483648");
                return;
            }
        }
        if (i < 10) {
            stringBuffer.append((char) (i + 48));
        } else if (i < 100) {
            int i2 = ((i + 1) * 13421772) >> 27;
            stringBuffer.append((char) (i2 + 48));
            stringBuffer.append((char) (((i - (i2 << 3)) - (i2 << 1)) + 48));
        } else {
            stringBuffer.append(Integer.toString(i));
        }
    }

    public static void appendUnpaddedInteger(StringBuffer stringBuffer, long j) {
        int i = (int) j;
        if (((long) i) == j) {
            appendUnpaddedInteger(stringBuffer, i);
        } else {
            stringBuffer.append(Long.toString(j));
        }
    }

    public static void writeUnpaddedInteger(Writer writer, int i) throws IOException {
        if (i < 0) {
            writer.write(45);
            if (i != Integer.MIN_VALUE) {
                i = -i;
            } else {
                writer.write("2147483648");
                return;
            }
        }
        if (i < 10) {
            writer.write(i + 48);
        } else if (i < 100) {
            int i2 = ((i + 1) * 13421772) >> 27;
            writer.write(i2 + 48);
            writer.write(((i - (i2 << 3)) - (i2 << 1)) + 48);
        } else {
            writer.write(Integer.toString(i));
        }
    }

    public static void writeUnpaddedInteger(Writer writer, long j) throws IOException {
        int i = (int) j;
        if (((long) i) == j) {
            writeUnpaddedInteger(writer, i);
        } else {
            writer.write(Long.toString(j));
        }
    }

    public static int calculateDigitCount(long j) {
        if (j < 0) {
            if (j != Long.MIN_VALUE) {
                return calculateDigitCount(-j) + 1;
            }
            return 20;
        } else if (j < 10) {
            return 1;
        } else {
            if (j < 100) {
                return 2;
            }
            if (j < 1000) {
                return 3;
            }
            return j < 10000 ? 4 : ((int) (Math.log((double) j) / LOG_10)) + 1;
        }
    }

    static int parseTwoDigits(String str, int i) {
        int charAt = str.charAt(i) - 48;
        return (((charAt << 1) + (charAt << 3)) + str.charAt(i + 1)) - 48;
    }

    static String createErrorMessage(String str, int i) {
        String str2;
        int i2 = i + 32;
        if (str.length() <= i2 + 3) {
            str2 = str;
        } else {
            str2 = str.substring(0, i2).concat("...");
        }
        if (i <= 0) {
            return "Invalid format: \"" + str2 + '\"';
        }
        if (i >= str.length()) {
            return "Invalid format: \"" + str2 + "\" is too short";
        }
        return "Invalid format: \"" + str2 + "\" is malformed at \"" + str2.substring(i) + '\"';
    }
}
