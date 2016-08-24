package pe.edu.upc.mobile.Utilities;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.analytics.containertag.proto.MutableServing.ServingValue;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.android.gms.location.LocationRequest;
import com.google.common.base.Ascii;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.joda.time.DateTimeConstants;

public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final int DECODE = 0;
    public static final int DONT_GUNZIP = 4;
    public static final int DO_BREAK_LINES = 8;
    public static final int ENCODE = 1;
    private static final byte EQUALS_SIGN = (byte) 61;
    private static final byte EQUALS_SIGN_ENC = (byte) -1;
    public static final int GZIP = 2;
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte NEW_LINE = (byte) 10;
    public static final int NO_OPTIONS = 0;
    public static final int ORDERED = 32;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    public static final int URL_SAFE = 16;
    private static final byte WHITE_SPACE_ENC = (byte) -5;
    private static final byte[] _ORDERED_ALPHABET;
    private static final byte[] _ORDERED_DECODABET;
    private static final byte[] _STANDARD_ALPHABET;
    private static final byte[] _STANDARD_DECODABET;
    private static final byte[] _URL_SAFE_ALPHABET;
    private static final byte[] _URL_SAFE_DECODABET;

    /* renamed from: pe.edu.upc.mobile.Utilities.Base64.1 */
    class C04271 extends ObjectInputStream {
        private final /* synthetic */ ClassLoader val$loader;

        C04271(java.io.InputStream $anonymous0, ClassLoader classLoader) throws StreamCorruptedException, IOException {
            this.val$loader = classLoader;
            super($anonymous0);
        }

        public Class<?> resolveClass(ObjectStreamClass streamClass) throws IOException, ClassNotFoundException {
            Class<?> c = Class.forName(streamClass.getName(), Base64.$assertionsDisabled, this.val$loader);
            if (c == null) {
                return super.resolveClass(streamClass);
            }
            return c;
        }
    }

    public static class InputStream extends FilterInputStream {
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int numSigBytes;
        private int options;
        private int position;

        public InputStream(java.io.InputStream in) {
            this(in, Base64.NO_OPTIONS);
        }

        public InputStream(java.io.InputStream in, int options) {
            boolean z = true;
            super(in);
            this.options = options;
            this.breakLines = (options & Base64.DO_BREAK_LINES) > 0 ? true : Base64.$assertionsDisabled;
            if ((options & Base64.ENCODE) <= 0) {
                z = Base64.$assertionsDisabled;
            }
            this.encode = z;
            this.bufferLength = this.encode ? Base64.DONT_GUNZIP : 3;
            this.buffer = new byte[this.bufferLength];
            this.position = -1;
            this.lineLength = Base64.NO_OPTIONS;
            this.decodabet = Base64.getDecodabet(options);
        }

        public int read() throws IOException {
            int b;
            if (this.position < 0) {
                int i;
                if (this.encode) {
                    byte[] b3 = new byte[3];
                    int numBinaryBytes = Base64.NO_OPTIONS;
                    for (i = Base64.NO_OPTIONS; i < 3; i += Base64.ENCODE) {
                        b = this.in.read();
                        if (b < 0) {
                            break;
                        }
                        b3[i] = (byte) b;
                        numBinaryBytes += Base64.ENCODE;
                    }
                    if (numBinaryBytes <= 0) {
                        return -1;
                    }
                    Base64.encode3to4(b3, Base64.NO_OPTIONS, numBinaryBytes, this.buffer, Base64.NO_OPTIONS, this.options);
                    this.position = Base64.NO_OPTIONS;
                    this.numSigBytes = Base64.DONT_GUNZIP;
                } else {
                    byte[] b4 = new byte[Base64.DONT_GUNZIP];
                    i = Base64.NO_OPTIONS;
                    while (i < Base64.DONT_GUNZIP) {
                        do {
                            b = this.in.read();
                            if (b < 0) {
                                break;
                            }
                        } while (this.decodabet[b & 127] <= -5);
                        if (b < 0) {
                            break;
                        }
                        b4[i] = (byte) b;
                        i += Base64.ENCODE;
                    }
                    if (i == Base64.DONT_GUNZIP) {
                        this.numSigBytes = Base64.decode4to3(b4, Base64.NO_OPTIONS, this.buffer, Base64.NO_OPTIONS, this.options);
                        this.position = Base64.NO_OPTIONS;
                    } else if (i == 0) {
                        return -1;
                    } else {
                        throw new IOException("Improperly padded Base64 input.");
                    }
                }
            }
            if (this.position < 0) {
                throw new IOException("Error in Base64 code reading stream.");
            } else if (this.position >= this.numSigBytes) {
                return -1;
            } else {
                if (this.encode && this.breakLines && this.lineLength >= Base64.MAX_LINE_LENGTH) {
                    this.lineLength = Base64.NO_OPTIONS;
                    return 10;
                }
                this.lineLength += Base64.ENCODE;
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + Base64.ENCODE;
                b = bArr[i2];
                if (this.position >= this.bufferLength) {
                    this.position = -1;
                }
                return b & MotionEventCompat.ACTION_MASK;
            }
        }

        public int read(byte[] dest, int off, int len) throws IOException {
            int i = Base64.NO_OPTIONS;
            while (i < len) {
                int b = read();
                if (b >= 0) {
                    dest[off + i] = (byte) b;
                    i += Base64.ENCODE;
                } else if (i == 0) {
                    return -1;
                } else {
                    return i;
                }
            }
            return i;
        }
    }

    public static class OutputStream extends FilterOutputStream {
        private byte[] b4;
        private boolean breakLines;
        private byte[] buffer;
        private int bufferLength;
        private byte[] decodabet;
        private boolean encode;
        private int lineLength;
        private int options;
        private int position;
        private boolean suspendEncoding;

        public OutputStream(java.io.OutputStream out) {
            this(out, Base64.ENCODE);
        }

        public OutputStream(java.io.OutputStream out, int options) {
            int i;
            boolean z = true;
            super(out);
            this.breakLines = (options & Base64.DO_BREAK_LINES) != 0 ? true : Base64.$assertionsDisabled;
            if ((options & Base64.ENCODE) == 0) {
                z = Base64.$assertionsDisabled;
            }
            this.encode = z;
            if (this.encode) {
                i = 3;
            } else {
                i = Base64.DONT_GUNZIP;
            }
            this.bufferLength = i;
            this.buffer = new byte[this.bufferLength];
            this.position = Base64.NO_OPTIONS;
            this.lineLength = Base64.NO_OPTIONS;
            this.suspendEncoding = Base64.$assertionsDisabled;
            this.b4 = new byte[Base64.DONT_GUNZIP];
            this.options = options;
            this.decodabet = Base64.getDecodabet(options);
        }

        public void write(int theByte) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theByte);
            } else if (this.encode) {
                r1 = this.buffer;
                r2 = this.position;
                this.position = r2 + Base64.ENCODE;
                r1[r2] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(Base64.encode3to4(this.b4, this.buffer, this.bufferLength, this.options));
                    this.lineLength += Base64.DONT_GUNZIP;
                    if (this.breakLines && this.lineLength >= Base64.MAX_LINE_LENGTH) {
                        this.out.write(10);
                        this.lineLength = Base64.NO_OPTIONS;
                    }
                    this.position = Base64.NO_OPTIONS;
                }
            } else if (this.decodabet[theByte & 127] > Base64.WHITE_SPACE_ENC) {
                r1 = this.buffer;
                r2 = this.position;
                this.position = r2 + Base64.ENCODE;
                r1[r2] = (byte) theByte;
                if (this.position >= this.bufferLength) {
                    this.out.write(this.b4, Base64.NO_OPTIONS, Base64.decode4to3(this.buffer, Base64.NO_OPTIONS, this.b4, Base64.NO_OPTIONS, this.options));
                    this.position = Base64.NO_OPTIONS;
                }
            } else if (this.decodabet[theByte & 127] != Base64.WHITE_SPACE_ENC) {
                throw new IOException("Invalid character in Base64 data.");
            }
        }

        public void write(byte[] theBytes, int off, int len) throws IOException {
            if (this.suspendEncoding) {
                this.out.write(theBytes, off, len);
                return;
            }
            for (int i = Base64.NO_OPTIONS; i < len; i += Base64.ENCODE) {
                write(theBytes[off + i]);
            }
        }

        public void flushBase64() throws IOException {
            if (this.position <= 0) {
                return;
            }
            if (this.encode) {
                this.out.write(Base64.encode3to4(this.b4, this.buffer, this.position, this.options));
                this.position = Base64.NO_OPTIONS;
                return;
            }
            throw new IOException("Base64 input not properly padded.");
        }

        public void close() throws IOException {
            flushBase64();
            super.close();
            this.buffer = null;
            this.out = null;
        }

        public void suspendEncoding() throws IOException {
            flushBase64();
            this.suspendEncoding = true;
        }

        public void resumeEncoding() {
            this.suspendEncoding = Base64.$assertionsDisabled;
        }
    }

    static {
        boolean z;
        if (Base64.class.desiredAssertionStatus()) {
            z = $assertionsDisabled;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        _STANDARD_ALPHABET = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
        byte[] bArr = new byte[AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT];
        bArr[NO_OPTIONS] = (byte) -9;
        bArr[ENCODE] = (byte) -9;
        bArr[GZIP] = (byte) -9;
        bArr[3] = (byte) -9;
        bArr[DONT_GUNZIP] = (byte) -9;
        bArr[5] = (byte) -9;
        bArr[6] = (byte) -9;
        bArr[7] = (byte) -9;
        bArr[DO_BREAK_LINES] = (byte) -9;
        bArr[9] = WHITE_SPACE_ENC;
        bArr[10] = WHITE_SPACE_ENC;
        bArr[11] = (byte) -9;
        bArr[12] = (byte) -9;
        bArr[13] = WHITE_SPACE_ENC;
        bArr[14] = (byte) -9;
        bArr[15] = (byte) -9;
        bArr[URL_SAFE] = (byte) -9;
        bArr[17] = (byte) -9;
        bArr[18] = (byte) -9;
        bArr[19] = (byte) -9;
        bArr[20] = (byte) -9;
        bArr[21] = (byte) -9;
        bArr[22] = (byte) -9;
        bArr[23] = (byte) -9;
        bArr[24] = (byte) -9;
        bArr[25] = (byte) -9;
        bArr[26] = (byte) -9;
        bArr[27] = (byte) -9;
        bArr[28] = (byte) -9;
        bArr[29] = (byte) -9;
        bArr[30] = (byte) -9;
        bArr[31] = (byte) -9;
        bArr[ORDERED] = WHITE_SPACE_ENC;
        bArr[33] = (byte) -9;
        bArr[34] = (byte) -9;
        bArr[35] = (byte) -9;
        bArr[36] = (byte) -9;
        bArr[37] = (byte) -9;
        bArr[38] = (byte) -9;
        bArr[39] = (byte) -9;
        bArr[40] = (byte) -9;
        bArr[41] = (byte) -9;
        bArr[42] = (byte) -9;
        bArr[43] = (byte) 62;
        bArr[44] = (byte) -9;
        bArr[45] = (byte) -9;
        bArr[46] = (byte) -9;
        bArr[47] = (byte) 63;
        bArr[48] = (byte) 52;
        bArr[49] = (byte) 53;
        bArr[50] = (byte) 54;
        bArr[51] = (byte) 55;
        bArr[52] = (byte) 56;
        bArr[53] = (byte) 57;
        bArr[54] = (byte) 58;
        bArr[55] = (byte) 59;
        bArr[56] = (byte) 60;
        bArr[57] = EQUALS_SIGN;
        bArr[58] = (byte) -9;
        bArr[59] = (byte) -9;
        bArr[60] = (byte) -9;
        bArr[61] = EQUALS_SIGN_ENC;
        bArr[62] = (byte) -9;
        bArr[63] = (byte) -9;
        bArr[64] = (byte) -9;
        bArr[66] = (byte) 1;
        bArr[67] = (byte) 2;
        bArr[68] = (byte) 3;
        bArr[69] = (byte) 4;
        bArr[70] = (byte) 5;
        bArr[71] = (byte) 6;
        bArr[72] = (byte) 7;
        bArr[73] = (byte) 8;
        bArr[74] = (byte) 9;
        bArr[75] = NEW_LINE;
        bArr[MAX_LINE_LENGTH] = Ascii.VT;
        bArr[77] = Ascii.FF;
        bArr[78] = Ascii.CR;
        bArr[79] = Ascii.SO;
        bArr[80] = Ascii.SI;
        bArr[81] = Ascii.DLE;
        bArr[82] = Ascii.XON;
        bArr[83] = Ascii.DC2;
        bArr[84] = Ascii.XOFF;
        bArr[85] = Ascii.DC4;
        bArr[86] = Ascii.NAK;
        bArr[87] = Ascii.SYN;
        bArr[88] = Ascii.ETB;
        bArr[89] = Ascii.CAN;
        bArr[90] = Ascii.EM;
        bArr[91] = (byte) -9;
        bArr[92] = (byte) -9;
        bArr[93] = (byte) -9;
        bArr[94] = (byte) -9;
        bArr[95] = (byte) -9;
        bArr[96] = (byte) -9;
        bArr[97] = Ascii.SUB;
        bArr[98] = Ascii.ESC;
        bArr[99] = Ascii.FS;
        bArr[100] = Ascii.GS;
        bArr[ServingValue.EXT_FIELD_NUMBER] = Ascii.RS;
        bArr[LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY] = Ascii.US;
        bArr[103] = Ascii.SPACE;
        bArr[LocationRequest.PRIORITY_LOW_POWER] = (byte) 33;
        bArr[LocationRequest.PRIORITY_NO_POWER] = (byte) 34;
        bArr[106] = (byte) 35;
        bArr[107] = (byte) 36;
        bArr[108] = (byte) 37;
        bArr[109] = (byte) 38;
        bArr[110] = (byte) 39;
        bArr[111] = (byte) 40;
        bArr[112] = (byte) 41;
        bArr[113] = (byte) 42;
        bArr[114] = (byte) 43;
        bArr[115] = (byte) 44;
        bArr[116] = (byte) 45;
        bArr[117] = (byte) 46;
        bArr[118] = (byte) 47;
        bArr[119] = (byte) 48;
        bArr[120] = (byte) 49;
        bArr[121] = (byte) 50;
        bArr[122] = (byte) 51;
        bArr[123] = (byte) -9;
        bArr[124] = (byte) -9;
        bArr[125] = (byte) -9;
        bArr[126] = (byte) -9;
        bArr[127] = (byte) -9;
        bArr[AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER] = (byte) -9;
        bArr[129] = (byte) -9;
        bArr[130] = (byte) -9;
        bArr[131] = (byte) -9;
        bArr[132] = (byte) -9;
        bArr[133] = (byte) -9;
        bArr[134] = (byte) -9;
        bArr[135] = (byte) -9;
        bArr[136] = (byte) -9;
        bArr[137] = (byte) -9;
        bArr[138] = (byte) -9;
        bArr[139] = (byte) -9;
        bArr[140] = (byte) -9;
        bArr[141] = (byte) -9;
        bArr[142] = (byte) -9;
        bArr[143] = (byte) -9;
        bArr[144] = (byte) -9;
        bArr[145] = (byte) -9;
        bArr[146] = (byte) -9;
        bArr[147] = (byte) -9;
        bArr[148] = (byte) -9;
        bArr[149] = (byte) -9;
        bArr[150] = (byte) -9;
        bArr[151] = (byte) -9;
        bArr[152] = (byte) -9;
        bArr[153] = (byte) -9;
        bArr[154] = (byte) -9;
        bArr[155] = (byte) -9;
        bArr[156] = (byte) -9;
        bArr[157] = (byte) -9;
        bArr[158] = (byte) -9;
        bArr[159] = (byte) -9;
        bArr[160] = (byte) -9;
        bArr[161] = (byte) -9;
        bArr[162] = (byte) -9;
        bArr[163] = (byte) -9;
        bArr[164] = (byte) -9;
        bArr[165] = (byte) -9;
        bArr[166] = (byte) -9;
        bArr[167] = (byte) -9;
        bArr[DateTimeConstants.HOURS_PER_WEEK] = (byte) -9;
        bArr[169] = (byte) -9;
        bArr[170] = (byte) -9;
        bArr[171] = (byte) -9;
        bArr[172] = (byte) -9;
        bArr[173] = (byte) -9;
        bArr[174] = (byte) -9;
        bArr[175] = (byte) -9;
        bArr[176] = (byte) -9;
        bArr[177] = (byte) -9;
        bArr[178] = (byte) -9;
        bArr[179] = (byte) -9;
        bArr[180] = (byte) -9;
        bArr[181] = (byte) -9;
        bArr[182] = (byte) -9;
        bArr[183] = (byte) -9;
        bArr[184] = (byte) -9;
        bArr[185] = (byte) -9;
        bArr[186] = (byte) -9;
        bArr[187] = (byte) -9;
        bArr[188] = (byte) -9;
        bArr[189] = (byte) -9;
        bArr[190] = (byte) -9;
        bArr[191] = (byte) -9;
        bArr[192] = (byte) -9;
        bArr[193] = (byte) -9;
        bArr[194] = (byte) -9;
        bArr[195] = (byte) -9;
        bArr[196] = (byte) -9;
        bArr[197] = (byte) -9;
        bArr[198] = (byte) -9;
        bArr[199] = (byte) -9;
        bArr[200] = (byte) -9;
        bArr[201] = (byte) -9;
        bArr[202] = (byte) -9;
        bArr[203] = (byte) -9;
        bArr[204] = (byte) -9;
        bArr[205] = (byte) -9;
        bArr[206] = (byte) -9;
        bArr[207] = (byte) -9;
        bArr[208] = (byte) -9;
        bArr[209] = (byte) -9;
        bArr[210] = (byte) -9;
        bArr[211] = (byte) -9;
        bArr[212] = (byte) -9;
        bArr[213] = (byte) -9;
        bArr[214] = (byte) -9;
        bArr[215] = (byte) -9;
        bArr[216] = (byte) -9;
        bArr[217] = (byte) -9;
        bArr[218] = (byte) -9;
        bArr[219] = (byte) -9;
        bArr[220] = (byte) -9;
        bArr[221] = (byte) -9;
        bArr[222] = (byte) -9;
        bArr[223] = (byte) -9;
        bArr[224] = (byte) -9;
        bArr[225] = (byte) -9;
        bArr[226] = (byte) -9;
        bArr[227] = (byte) -9;
        bArr[228] = (byte) -9;
        bArr[229] = (byte) -9;
        bArr[230] = (byte) -9;
        bArr[231] = (byte) -9;
        bArr[232] = (byte) -9;
        bArr[233] = (byte) -9;
        bArr[234] = (byte) -9;
        bArr[235] = (byte) -9;
        bArr[236] = (byte) -9;
        bArr[237] = (byte) -9;
        bArr[238] = (byte) -9;
        bArr[239] = (byte) -9;
        bArr[240] = (byte) -9;
        bArr[241] = (byte) -9;
        bArr[242] = (byte) -9;
        bArr[243] = (byte) -9;
        bArr[244] = (byte) -9;
        bArr[245] = (byte) -9;
        bArr[246] = (byte) -9;
        bArr[247] = (byte) -9;
        bArr[248] = (byte) -9;
        bArr[249] = (byte) -9;
        bArr[250] = (byte) -9;
        bArr[251] = (byte) -9;
        bArr[252] = (byte) -9;
        bArr[253] = (byte) -9;
        bArr[254] = (byte) -9;
        bArr[MotionEventCompat.ACTION_MASK] = (byte) -9;
        _STANDARD_DECODABET = bArr;
        _URL_SAFE_ALPHABET = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 45, (byte) 95};
        bArr = new byte[AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT];
        bArr[NO_OPTIONS] = (byte) -9;
        bArr[ENCODE] = (byte) -9;
        bArr[GZIP] = (byte) -9;
        bArr[3] = (byte) -9;
        bArr[DONT_GUNZIP] = (byte) -9;
        bArr[5] = (byte) -9;
        bArr[6] = (byte) -9;
        bArr[7] = (byte) -9;
        bArr[DO_BREAK_LINES] = (byte) -9;
        bArr[9] = WHITE_SPACE_ENC;
        bArr[10] = WHITE_SPACE_ENC;
        bArr[11] = (byte) -9;
        bArr[12] = (byte) -9;
        bArr[13] = WHITE_SPACE_ENC;
        bArr[14] = (byte) -9;
        bArr[15] = (byte) -9;
        bArr[URL_SAFE] = (byte) -9;
        bArr[17] = (byte) -9;
        bArr[18] = (byte) -9;
        bArr[19] = (byte) -9;
        bArr[20] = (byte) -9;
        bArr[21] = (byte) -9;
        bArr[22] = (byte) -9;
        bArr[23] = (byte) -9;
        bArr[24] = (byte) -9;
        bArr[25] = (byte) -9;
        bArr[26] = (byte) -9;
        bArr[27] = (byte) -9;
        bArr[28] = (byte) -9;
        bArr[29] = (byte) -9;
        bArr[30] = (byte) -9;
        bArr[31] = (byte) -9;
        bArr[ORDERED] = WHITE_SPACE_ENC;
        bArr[33] = (byte) -9;
        bArr[34] = (byte) -9;
        bArr[35] = (byte) -9;
        bArr[36] = (byte) -9;
        bArr[37] = (byte) -9;
        bArr[38] = (byte) -9;
        bArr[39] = (byte) -9;
        bArr[40] = (byte) -9;
        bArr[41] = (byte) -9;
        bArr[42] = (byte) -9;
        bArr[43] = (byte) -9;
        bArr[44] = (byte) -9;
        bArr[45] = (byte) 62;
        bArr[46] = (byte) -9;
        bArr[47] = (byte) -9;
        bArr[48] = (byte) 52;
        bArr[49] = (byte) 53;
        bArr[50] = (byte) 54;
        bArr[51] = (byte) 55;
        bArr[52] = (byte) 56;
        bArr[53] = (byte) 57;
        bArr[54] = (byte) 58;
        bArr[55] = (byte) 59;
        bArr[56] = (byte) 60;
        bArr[57] = EQUALS_SIGN;
        bArr[58] = (byte) -9;
        bArr[59] = (byte) -9;
        bArr[60] = (byte) -9;
        bArr[61] = EQUALS_SIGN_ENC;
        bArr[62] = (byte) -9;
        bArr[63] = (byte) -9;
        bArr[64] = (byte) -9;
        bArr[66] = (byte) 1;
        bArr[67] = (byte) 2;
        bArr[68] = (byte) 3;
        bArr[69] = (byte) 4;
        bArr[70] = (byte) 5;
        bArr[71] = (byte) 6;
        bArr[72] = (byte) 7;
        bArr[73] = (byte) 8;
        bArr[74] = (byte) 9;
        bArr[75] = NEW_LINE;
        bArr[MAX_LINE_LENGTH] = Ascii.VT;
        bArr[77] = Ascii.FF;
        bArr[78] = Ascii.CR;
        bArr[79] = Ascii.SO;
        bArr[80] = Ascii.SI;
        bArr[81] = Ascii.DLE;
        bArr[82] = Ascii.XON;
        bArr[83] = Ascii.DC2;
        bArr[84] = Ascii.XOFF;
        bArr[85] = Ascii.DC4;
        bArr[86] = Ascii.NAK;
        bArr[87] = Ascii.SYN;
        bArr[88] = Ascii.ETB;
        bArr[89] = Ascii.CAN;
        bArr[90] = Ascii.EM;
        bArr[91] = (byte) -9;
        bArr[92] = (byte) -9;
        bArr[93] = (byte) -9;
        bArr[94] = (byte) -9;
        bArr[95] = (byte) 63;
        bArr[96] = (byte) -9;
        bArr[97] = Ascii.SUB;
        bArr[98] = Ascii.ESC;
        bArr[99] = Ascii.FS;
        bArr[100] = Ascii.GS;
        bArr[ServingValue.EXT_FIELD_NUMBER] = Ascii.RS;
        bArr[LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY] = Ascii.US;
        bArr[103] = Ascii.SPACE;
        bArr[LocationRequest.PRIORITY_LOW_POWER] = (byte) 33;
        bArr[LocationRequest.PRIORITY_NO_POWER] = (byte) 34;
        bArr[106] = (byte) 35;
        bArr[107] = (byte) 36;
        bArr[108] = (byte) 37;
        bArr[109] = (byte) 38;
        bArr[110] = (byte) 39;
        bArr[111] = (byte) 40;
        bArr[112] = (byte) 41;
        bArr[113] = (byte) 42;
        bArr[114] = (byte) 43;
        bArr[115] = (byte) 44;
        bArr[116] = (byte) 45;
        bArr[117] = (byte) 46;
        bArr[118] = (byte) 47;
        bArr[119] = (byte) 48;
        bArr[120] = (byte) 49;
        bArr[121] = (byte) 50;
        bArr[122] = (byte) 51;
        bArr[123] = (byte) -9;
        bArr[124] = (byte) -9;
        bArr[125] = (byte) -9;
        bArr[126] = (byte) -9;
        bArr[127] = (byte) -9;
        bArr[AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER] = (byte) -9;
        bArr[129] = (byte) -9;
        bArr[130] = (byte) -9;
        bArr[131] = (byte) -9;
        bArr[132] = (byte) -9;
        bArr[133] = (byte) -9;
        bArr[134] = (byte) -9;
        bArr[135] = (byte) -9;
        bArr[136] = (byte) -9;
        bArr[137] = (byte) -9;
        bArr[138] = (byte) -9;
        bArr[139] = (byte) -9;
        bArr[140] = (byte) -9;
        bArr[141] = (byte) -9;
        bArr[142] = (byte) -9;
        bArr[143] = (byte) -9;
        bArr[144] = (byte) -9;
        bArr[145] = (byte) -9;
        bArr[146] = (byte) -9;
        bArr[147] = (byte) -9;
        bArr[148] = (byte) -9;
        bArr[149] = (byte) -9;
        bArr[150] = (byte) -9;
        bArr[151] = (byte) -9;
        bArr[152] = (byte) -9;
        bArr[153] = (byte) -9;
        bArr[154] = (byte) -9;
        bArr[155] = (byte) -9;
        bArr[156] = (byte) -9;
        bArr[157] = (byte) -9;
        bArr[158] = (byte) -9;
        bArr[159] = (byte) -9;
        bArr[160] = (byte) -9;
        bArr[161] = (byte) -9;
        bArr[162] = (byte) -9;
        bArr[163] = (byte) -9;
        bArr[164] = (byte) -9;
        bArr[165] = (byte) -9;
        bArr[166] = (byte) -9;
        bArr[167] = (byte) -9;
        bArr[DateTimeConstants.HOURS_PER_WEEK] = (byte) -9;
        bArr[169] = (byte) -9;
        bArr[170] = (byte) -9;
        bArr[171] = (byte) -9;
        bArr[172] = (byte) -9;
        bArr[173] = (byte) -9;
        bArr[174] = (byte) -9;
        bArr[175] = (byte) -9;
        bArr[176] = (byte) -9;
        bArr[177] = (byte) -9;
        bArr[178] = (byte) -9;
        bArr[179] = (byte) -9;
        bArr[180] = (byte) -9;
        bArr[181] = (byte) -9;
        bArr[182] = (byte) -9;
        bArr[183] = (byte) -9;
        bArr[184] = (byte) -9;
        bArr[185] = (byte) -9;
        bArr[186] = (byte) -9;
        bArr[187] = (byte) -9;
        bArr[188] = (byte) -9;
        bArr[189] = (byte) -9;
        bArr[190] = (byte) -9;
        bArr[191] = (byte) -9;
        bArr[192] = (byte) -9;
        bArr[193] = (byte) -9;
        bArr[194] = (byte) -9;
        bArr[195] = (byte) -9;
        bArr[196] = (byte) -9;
        bArr[197] = (byte) -9;
        bArr[198] = (byte) -9;
        bArr[199] = (byte) -9;
        bArr[200] = (byte) -9;
        bArr[201] = (byte) -9;
        bArr[202] = (byte) -9;
        bArr[203] = (byte) -9;
        bArr[204] = (byte) -9;
        bArr[205] = (byte) -9;
        bArr[206] = (byte) -9;
        bArr[207] = (byte) -9;
        bArr[208] = (byte) -9;
        bArr[209] = (byte) -9;
        bArr[210] = (byte) -9;
        bArr[211] = (byte) -9;
        bArr[212] = (byte) -9;
        bArr[213] = (byte) -9;
        bArr[214] = (byte) -9;
        bArr[215] = (byte) -9;
        bArr[216] = (byte) -9;
        bArr[217] = (byte) -9;
        bArr[218] = (byte) -9;
        bArr[219] = (byte) -9;
        bArr[220] = (byte) -9;
        bArr[221] = (byte) -9;
        bArr[222] = (byte) -9;
        bArr[223] = (byte) -9;
        bArr[224] = (byte) -9;
        bArr[225] = (byte) -9;
        bArr[226] = (byte) -9;
        bArr[227] = (byte) -9;
        bArr[228] = (byte) -9;
        bArr[229] = (byte) -9;
        bArr[230] = (byte) -9;
        bArr[231] = (byte) -9;
        bArr[232] = (byte) -9;
        bArr[233] = (byte) -9;
        bArr[234] = (byte) -9;
        bArr[235] = (byte) -9;
        bArr[236] = (byte) -9;
        bArr[237] = (byte) -9;
        bArr[238] = (byte) -9;
        bArr[239] = (byte) -9;
        bArr[240] = (byte) -9;
        bArr[241] = (byte) -9;
        bArr[242] = (byte) -9;
        bArr[243] = (byte) -9;
        bArr[244] = (byte) -9;
        bArr[245] = (byte) -9;
        bArr[246] = (byte) -9;
        bArr[247] = (byte) -9;
        bArr[248] = (byte) -9;
        bArr[249] = (byte) -9;
        bArr[250] = (byte) -9;
        bArr[251] = (byte) -9;
        bArr[252] = (byte) -9;
        bArr[253] = (byte) -9;
        bArr[254] = (byte) -9;
        bArr[MotionEventCompat.ACTION_MASK] = (byte) -9;
        _URL_SAFE_DECODABET = bArr;
        _ORDERED_ALPHABET = new byte[]{(byte) 45, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 95, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122};
        bArr = new byte[257];
        bArr[NO_OPTIONS] = (byte) -9;
        bArr[ENCODE] = (byte) -9;
        bArr[GZIP] = (byte) -9;
        bArr[3] = (byte) -9;
        bArr[DONT_GUNZIP] = (byte) -9;
        bArr[5] = (byte) -9;
        bArr[6] = (byte) -9;
        bArr[7] = (byte) -9;
        bArr[DO_BREAK_LINES] = (byte) -9;
        bArr[9] = WHITE_SPACE_ENC;
        bArr[10] = WHITE_SPACE_ENC;
        bArr[11] = (byte) -9;
        bArr[12] = (byte) -9;
        bArr[13] = WHITE_SPACE_ENC;
        bArr[14] = (byte) -9;
        bArr[15] = (byte) -9;
        bArr[URL_SAFE] = (byte) -9;
        bArr[17] = (byte) -9;
        bArr[18] = (byte) -9;
        bArr[19] = (byte) -9;
        bArr[20] = (byte) -9;
        bArr[21] = (byte) -9;
        bArr[22] = (byte) -9;
        bArr[23] = (byte) -9;
        bArr[24] = (byte) -9;
        bArr[25] = (byte) -9;
        bArr[26] = (byte) -9;
        bArr[27] = (byte) -9;
        bArr[28] = (byte) -9;
        bArr[29] = (byte) -9;
        bArr[30] = (byte) -9;
        bArr[31] = (byte) -9;
        bArr[ORDERED] = WHITE_SPACE_ENC;
        bArr[33] = (byte) -9;
        bArr[34] = (byte) -9;
        bArr[35] = (byte) -9;
        bArr[36] = (byte) -9;
        bArr[37] = (byte) -9;
        bArr[38] = (byte) -9;
        bArr[39] = (byte) -9;
        bArr[40] = (byte) -9;
        bArr[41] = (byte) -9;
        bArr[42] = (byte) -9;
        bArr[43] = (byte) -9;
        bArr[44] = (byte) -9;
        bArr[46] = (byte) -9;
        bArr[47] = (byte) -9;
        bArr[48] = (byte) 1;
        bArr[49] = (byte) 2;
        bArr[50] = (byte) 3;
        bArr[51] = (byte) 4;
        bArr[52] = (byte) 5;
        bArr[53] = (byte) 6;
        bArr[54] = (byte) 7;
        bArr[55] = (byte) 8;
        bArr[56] = (byte) 9;
        bArr[57] = NEW_LINE;
        bArr[58] = (byte) -9;
        bArr[59] = (byte) -9;
        bArr[60] = (byte) -9;
        bArr[61] = EQUALS_SIGN_ENC;
        bArr[62] = (byte) -9;
        bArr[63] = (byte) -9;
        bArr[64] = (byte) -9;
        bArr[65] = Ascii.VT;
        bArr[66] = Ascii.FF;
        bArr[67] = Ascii.CR;
        bArr[68] = Ascii.SO;
        bArr[69] = Ascii.SI;
        bArr[70] = Ascii.DLE;
        bArr[71] = Ascii.XON;
        bArr[72] = Ascii.DC2;
        bArr[73] = Ascii.XOFF;
        bArr[74] = Ascii.DC4;
        bArr[75] = Ascii.NAK;
        bArr[MAX_LINE_LENGTH] = Ascii.SYN;
        bArr[77] = Ascii.ETB;
        bArr[78] = Ascii.CAN;
        bArr[79] = Ascii.EM;
        bArr[80] = Ascii.SUB;
        bArr[81] = Ascii.ESC;
        bArr[82] = Ascii.FS;
        bArr[83] = Ascii.GS;
        bArr[84] = Ascii.RS;
        bArr[85] = Ascii.US;
        bArr[86] = Ascii.SPACE;
        bArr[87] = (byte) 33;
        bArr[88] = (byte) 34;
        bArr[89] = (byte) 35;
        bArr[90] = (byte) 36;
        bArr[91] = (byte) -9;
        bArr[92] = (byte) -9;
        bArr[93] = (byte) -9;
        bArr[94] = (byte) -9;
        bArr[95] = (byte) 37;
        bArr[96] = (byte) -9;
        bArr[97] = (byte) 38;
        bArr[98] = (byte) 39;
        bArr[99] = (byte) 40;
        bArr[100] = (byte) 41;
        bArr[ServingValue.EXT_FIELD_NUMBER] = (byte) 42;
        bArr[LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY] = (byte) 43;
        bArr[103] = (byte) 44;
        bArr[LocationRequest.PRIORITY_LOW_POWER] = (byte) 45;
        bArr[LocationRequest.PRIORITY_NO_POWER] = (byte) 46;
        bArr[106] = (byte) 47;
        bArr[107] = (byte) 48;
        bArr[108] = (byte) 49;
        bArr[109] = (byte) 50;
        bArr[110] = (byte) 51;
        bArr[111] = (byte) 52;
        bArr[112] = (byte) 53;
        bArr[113] = (byte) 54;
        bArr[114] = (byte) 55;
        bArr[115] = (byte) 56;
        bArr[116] = (byte) 57;
        bArr[117] = (byte) 58;
        bArr[118] = (byte) 59;
        bArr[119] = (byte) 60;
        bArr[120] = EQUALS_SIGN;
        bArr[121] = (byte) 62;
        bArr[122] = (byte) 63;
        bArr[123] = (byte) -9;
        bArr[124] = (byte) -9;
        bArr[125] = (byte) -9;
        bArr[126] = (byte) -9;
        bArr[127] = (byte) -9;
        bArr[AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER] = (byte) -9;
        bArr[129] = (byte) -9;
        bArr[130] = (byte) -9;
        bArr[131] = (byte) -9;
        bArr[132] = (byte) -9;
        bArr[133] = (byte) -9;
        bArr[134] = (byte) -9;
        bArr[135] = (byte) -9;
        bArr[136] = (byte) -9;
        bArr[137] = (byte) -9;
        bArr[138] = (byte) -9;
        bArr[139] = (byte) -9;
        bArr[140] = (byte) -9;
        bArr[141] = (byte) -9;
        bArr[142] = (byte) -9;
        bArr[143] = (byte) -9;
        bArr[144] = (byte) -9;
        bArr[145] = (byte) -9;
        bArr[146] = (byte) -9;
        bArr[147] = (byte) -9;
        bArr[148] = (byte) -9;
        bArr[149] = (byte) -9;
        bArr[150] = (byte) -9;
        bArr[151] = (byte) -9;
        bArr[152] = (byte) -9;
        bArr[153] = (byte) -9;
        bArr[154] = (byte) -9;
        bArr[155] = (byte) -9;
        bArr[156] = (byte) -9;
        bArr[157] = (byte) -9;
        bArr[158] = (byte) -9;
        bArr[159] = (byte) -9;
        bArr[160] = (byte) -9;
        bArr[161] = (byte) -9;
        bArr[162] = (byte) -9;
        bArr[163] = (byte) -9;
        bArr[164] = (byte) -9;
        bArr[165] = (byte) -9;
        bArr[166] = (byte) -9;
        bArr[167] = (byte) -9;
        bArr[DateTimeConstants.HOURS_PER_WEEK] = (byte) -9;
        bArr[169] = (byte) -9;
        bArr[170] = (byte) -9;
        bArr[171] = (byte) -9;
        bArr[172] = (byte) -9;
        bArr[173] = (byte) -9;
        bArr[174] = (byte) -9;
        bArr[175] = (byte) -9;
        bArr[176] = (byte) -9;
        bArr[177] = (byte) -9;
        bArr[178] = (byte) -9;
        bArr[179] = (byte) -9;
        bArr[180] = (byte) -9;
        bArr[181] = (byte) -9;
        bArr[182] = (byte) -9;
        bArr[183] = (byte) -9;
        bArr[184] = (byte) -9;
        bArr[185] = (byte) -9;
        bArr[186] = (byte) -9;
        bArr[187] = (byte) -9;
        bArr[188] = (byte) -9;
        bArr[189] = (byte) -9;
        bArr[190] = (byte) -9;
        bArr[191] = (byte) -9;
        bArr[192] = (byte) -9;
        bArr[193] = (byte) -9;
        bArr[194] = (byte) -9;
        bArr[195] = (byte) -9;
        bArr[196] = (byte) -9;
        bArr[197] = (byte) -9;
        bArr[198] = (byte) -9;
        bArr[199] = (byte) -9;
        bArr[200] = (byte) -9;
        bArr[201] = (byte) -9;
        bArr[202] = (byte) -9;
        bArr[203] = (byte) -9;
        bArr[204] = (byte) -9;
        bArr[205] = (byte) -9;
        bArr[206] = (byte) -9;
        bArr[207] = (byte) -9;
        bArr[208] = (byte) -9;
        bArr[209] = (byte) -9;
        bArr[210] = (byte) -9;
        bArr[211] = (byte) -9;
        bArr[212] = (byte) -9;
        bArr[213] = (byte) -9;
        bArr[214] = (byte) -9;
        bArr[215] = (byte) -9;
        bArr[216] = (byte) -9;
        bArr[217] = (byte) -9;
        bArr[218] = (byte) -9;
        bArr[219] = (byte) -9;
        bArr[220] = (byte) -9;
        bArr[221] = (byte) -9;
        bArr[222] = (byte) -9;
        bArr[223] = (byte) -9;
        bArr[224] = (byte) -9;
        bArr[225] = (byte) -9;
        bArr[226] = (byte) -9;
        bArr[227] = (byte) -9;
        bArr[228] = (byte) -9;
        bArr[229] = (byte) -9;
        bArr[230] = (byte) -9;
        bArr[231] = (byte) -9;
        bArr[232] = (byte) -9;
        bArr[233] = (byte) -9;
        bArr[234] = (byte) -9;
        bArr[235] = (byte) -9;
        bArr[236] = (byte) -9;
        bArr[237] = (byte) -9;
        bArr[238] = (byte) -9;
        bArr[239] = (byte) -9;
        bArr[240] = (byte) -9;
        bArr[241] = (byte) -9;
        bArr[242] = (byte) -9;
        bArr[243] = (byte) -9;
        bArr[244] = (byte) -9;
        bArr[245] = (byte) -9;
        bArr[246] = (byte) -9;
        bArr[247] = (byte) -9;
        bArr[248] = (byte) -9;
        bArr[249] = (byte) -9;
        bArr[250] = (byte) -9;
        bArr[251] = (byte) -9;
        bArr[252] = (byte) -9;
        bArr[253] = (byte) -9;
        bArr[254] = (byte) -9;
        bArr[MotionEventCompat.ACTION_MASK] = (byte) -9;
        bArr[AccessibilityEventCompat.TYPE_VIEW_HOVER_EXIT] = (byte) -9;
        _ORDERED_DECODABET = bArr;
    }

    private static final byte[] getAlphabet(int options) {
        if ((options & URL_SAFE) == URL_SAFE) {
            return _URL_SAFE_ALPHABET;
        }
        if ((options & ORDERED) == ORDERED) {
            return _ORDERED_ALPHABET;
        }
        return _STANDARD_ALPHABET;
    }

    private static final byte[] getDecodabet(int options) {
        if ((options & URL_SAFE) == URL_SAFE) {
            return _URL_SAFE_DECODABET;
        }
        if ((options & ORDERED) == ORDERED) {
            return _ORDERED_DECODABET;
        }
        return _STANDARD_DECODABET;
    }

    private Base64() {
    }

    private static byte[] encode3to4(byte[] b4, byte[] threeBytes, int numSigBytes, int options) {
        encode3to4(threeBytes, NO_OPTIONS, numSigBytes, b4, NO_OPTIONS, options);
        return b4;
    }

    private static byte[] encode3to4(byte[] source, int srcOffset, int numSigBytes, byte[] destination, int destOffset, int options) {
        int i;
        int i2;
        int i3 = NO_OPTIONS;
        byte[] ALPHABET = getAlphabet(options);
        if (numSigBytes > 0) {
            i = (source[srcOffset] << 24) >>> DO_BREAK_LINES;
        } else {
            i = NO_OPTIONS;
        }
        if (numSigBytes > ENCODE) {
            i2 = (source[srcOffset + ENCODE] << 24) >>> URL_SAFE;
        } else {
            i2 = NO_OPTIONS;
        }
        i2 |= i;
        if (numSigBytes > GZIP) {
            i3 = (source[srcOffset + GZIP] << 24) >>> 24;
        }
        int inBuff = i2 | i3;
        switch (numSigBytes) {
            case ENCODE /*1*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + ENCODE] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + GZIP] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case GZIP /*2*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + ENCODE] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + GZIP] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = EQUALS_SIGN;
                break;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                destination[destOffset] = ALPHABET[inBuff >>> 18];
                destination[destOffset + ENCODE] = ALPHABET[(inBuff >>> 12) & 63];
                destination[destOffset + GZIP] = ALPHABET[(inBuff >>> 6) & 63];
                destination[destOffset + 3] = ALPHABET[inBuff & 63];
                break;
        }
        return destination;
    }

    public static void encode(ByteBuffer raw, ByteBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[DONT_GUNZIP];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, NO_OPTIONS, rem);
            encode3to4(enc4, raw3, rem, NO_OPTIONS);
            encoded.put(enc4);
        }
    }

    public static void encode(ByteBuffer raw, CharBuffer encoded) {
        byte[] raw3 = new byte[3];
        byte[] enc4 = new byte[DONT_GUNZIP];
        while (raw.hasRemaining()) {
            int rem = Math.min(3, raw.remaining());
            raw.get(raw3, NO_OPTIONS, rem);
            encode3to4(enc4, raw3, rem, NO_OPTIONS);
            for (int i = NO_OPTIONS; i < DONT_GUNZIP; i += ENCODE) {
                encoded.put((char) (enc4[i] & MotionEventCompat.ACTION_MASK));
            }
        }
    }

    public static String encodeObject(Serializable serializableObject) throws IOException {
        return encodeObject(serializableObject, NO_OPTIONS);
    }

    public static String encodeObject(Serializable serializableObject, int options) throws IOException {
        GZIPOutputStream gzos;
        IOException e;
        Throwable th;
        if (serializableObject == null) {
            throw new NullPointerException("Cannot serialize a null object.");
        }
        ByteArrayOutputStream baos = null;
        java.io.OutputStream b64os = null;
        GZIPOutputStream gzos2 = null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            try {
                java.io.OutputStream b64os2 = new OutputStream(baos2, options | ENCODE);
                if ((options & GZIP) != 0) {
                    try {
                        gzos = new GZIPOutputStream(b64os2);
                    } catch (IOException e2) {
                        e = e2;
                        b64os = b64os2;
                        baos = baos2;
                        try {
                            throw e;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        b64os = b64os2;
                        baos = baos2;
                        try {
                            oos.close();
                        } catch (Exception e3) {
                        }
                        try {
                            gzos2.close();
                        } catch (Exception e4) {
                        }
                        try {
                            b64os.close();
                        } catch (Exception e5) {
                        }
                        try {
                            baos.close();
                        } catch (Exception e6) {
                        }
                        throw th;
                    }
                    try {
                        oos = new ObjectOutputStream(gzos);
                        gzos2 = gzos;
                    } catch (IOException e7) {
                        e = e7;
                        gzos2 = gzos;
                        b64os = b64os2;
                        baos = baos2;
                        throw e;
                    } catch (Throwable th4) {
                        th = th4;
                        gzos2 = gzos;
                        b64os = b64os2;
                        baos = baos2;
                        oos.close();
                        gzos2.close();
                        b64os.close();
                        baos.close();
                        throw th;
                    }
                }
                oos = new ObjectOutputStream(b64os2);
                oos.writeObject(serializableObject);
                try {
                    oos.close();
                } catch (Exception e8) {
                }
                try {
                    gzos2.close();
                } catch (Exception e9) {
                }
                try {
                    b64os2.close();
                } catch (Exception e10) {
                }
                try {
                    baos2.close();
                } catch (Exception e11) {
                }
                try {
                    return new String(baos2.toByteArray(), PREFERRED_ENCODING);
                } catch (UnsupportedEncodingException e12) {
                    return new String(baos2.toByteArray());
                }
            } catch (IOException e13) {
                e = e13;
                baos = baos2;
                throw e;
            } catch (Throwable th5) {
                th = th5;
                baos = baos2;
                oos.close();
                gzos2.close();
                b64os.close();
                baos.close();
                throw th;
            }
        } catch (IOException e14) {
            e = e14;
            throw e;
        }
    }

    public static String encodeBytes(byte[] source) {
        String encoded = null;
        try {
            encoded = encodeBytes(source, NO_OPTIONS, source.length, NO_OPTIONS);
        } catch (IOException ex) {
            if (!$assertionsDisabled) {
                throw new AssertionError(ex.getMessage());
            }
        }
        if ($assertionsDisabled || encoded != null) {
            return encoded;
        }
        throw new AssertionError();
    }

    public static String encodeBytes(byte[] source, int options) throws IOException {
        return encodeBytes(source, NO_OPTIONS, source.length, options);
    }

    public static String encodeBytes(byte[] source, int off, int len) {
        String encoded = null;
        try {
            encoded = encodeBytes(source, off, len, NO_OPTIONS);
        } catch (IOException ex) {
            if (!$assertionsDisabled) {
                throw new AssertionError(ex.getMessage());
            }
        }
        if ($assertionsDisabled || encoded != null) {
            return encoded;
        }
        throw new AssertionError();
    }

    public static String encodeBytes(byte[] source, int off, int len, int options) throws IOException {
        byte[] encoded = encodeBytesToBytes(source, off, len, options);
        try {
            return new String(encoded, PREFERRED_ENCODING);
        } catch (UnsupportedEncodingException e) {
            return new String(encoded);
        }
    }

    public static byte[] encodeBytesToBytes(byte[] source) {
        byte[] encoded = null;
        try {
            encoded = encodeBytesToBytes(source, NO_OPTIONS, source.length, NO_OPTIONS);
        } catch (IOException ex) {
            if (!$assertionsDisabled) {
                throw new AssertionError("IOExceptions only come from GZipping, which is turned off: " + ex.getMessage());
            }
        }
        return encoded;
    }

    public static byte[] encodeBytesToBytes(byte[] source, int off, int len, int options) throws IOException {
        IOException e;
        Throwable th;
        if (source == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (off < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + off);
        } else if (len < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + len);
        } else if (off + len > source.length) {
            Object[] objArr = new Object[3];
            objArr[NO_OPTIONS] = Integer.valueOf(off);
            objArr[ENCODE] = Integer.valueOf(len);
            objArr[GZIP] = Integer.valueOf(source.length);
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", objArr));
        } else if ((options & GZIP) != 0) {
            ByteArrayOutputStream baos = null;
            GZIPOutputStream gzos = null;
            OutputStream b64os = null;
            try {
                OutputStream b64os2;
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                try {
                    b64os2 = new OutputStream(baos2, options | ENCODE);
                } catch (IOException e2) {
                    e = e2;
                    baos = baos2;
                    try {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    baos = baos2;
                    try {
                        gzos.close();
                    } catch (Exception e3) {
                    }
                    try {
                        b64os.close();
                    } catch (Exception e4) {
                    }
                    try {
                        baos.close();
                    } catch (Exception e5) {
                    }
                    throw th;
                }
                try {
                    GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(b64os2);
                    try {
                        gZIPOutputStream.write(source, off, len);
                        gZIPOutputStream.close();
                        try {
                            gZIPOutputStream.close();
                        } catch (Exception e6) {
                        }
                        try {
                            b64os2.close();
                        } catch (Exception e7) {
                        }
                        try {
                            baos2.close();
                        } catch (Exception e8) {
                        }
                        return baos2.toByteArray();
                    } catch (IOException e9) {
                        e = e9;
                        b64os = b64os2;
                        gzos = gZIPOutputStream;
                        baos = baos2;
                        throw e;
                    } catch (Throwable th4) {
                        th = th4;
                        b64os = b64os2;
                        gzos = gZIPOutputStream;
                        baos = baos2;
                        gzos.close();
                        b64os.close();
                        baos.close();
                        throw th;
                    }
                } catch (IOException e10) {
                    e = e10;
                    b64os = b64os2;
                    baos = baos2;
                    throw e;
                } catch (Throwable th5) {
                    th = th5;
                    b64os = b64os2;
                    baos = baos2;
                    gzos.close();
                    b64os.close();
                    baos.close();
                    throw th;
                }
            } catch (IOException e11) {
                e = e11;
                throw e;
            }
        } else {
            boolean breakLines = (options & DO_BREAK_LINES) != 0 ? true : $assertionsDisabled;
            int encLen = ((len / 3) * DONT_GUNZIP) + (len % 3 > 0 ? DONT_GUNZIP : NO_OPTIONS);
            if (breakLines) {
                encLen += encLen / MAX_LINE_LENGTH;
            }
            byte[] outBuff = new byte[encLen];
            int d = NO_OPTIONS;
            int e12 = NO_OPTIONS;
            int len2 = len - 2;
            int lineLength = NO_OPTIONS;
            while (d < len2) {
                encode3to4(source, d + off, 3, outBuff, e12, options);
                lineLength += DONT_GUNZIP;
                if (breakLines && lineLength >= MAX_LINE_LENGTH) {
                    outBuff[e12 + DONT_GUNZIP] = NEW_LINE;
                    e12 += ENCODE;
                    lineLength = NO_OPTIONS;
                }
                d += 3;
                e12 += DONT_GUNZIP;
            }
            if (d < len) {
                encode3to4(source, d + off, len - d, outBuff, e12, options);
                e12 += DONT_GUNZIP;
            }
            if (e12 > outBuff.length - 1) {
                return outBuff;
            }
            Object finalOut = new byte[e12];
            System.arraycopy(outBuff, NO_OPTIONS, finalOut, NO_OPTIONS, e12);
            return finalOut;
        }
    }

    private static int decode4to3(byte[] source, int srcOffset, byte[] destination, int destOffset, int options) {
        if (source == null) {
            throw new NullPointerException("Source array was null.");
        } else if (destination == null) {
            throw new NullPointerException("Destination array was null.");
        } else if (srcOffset < 0 || srcOffset + 3 >= source.length) {
            r3 = new Object[GZIP];
            r3[NO_OPTIONS] = Integer.valueOf(source.length);
            r3[ENCODE] = Integer.valueOf(srcOffset);
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and still process four bytes.", r3));
        } else if (destOffset < 0 || destOffset + GZIP >= destination.length) {
            r3 = new Object[GZIP];
            r3[NO_OPTIONS] = Integer.valueOf(destination.length);
            r3[ENCODE] = Integer.valueOf(destOffset);
            throw new IllegalArgumentException(String.format("Destination array with length %d cannot have offset of %d and still store three bytes.", r3));
        } else {
            byte[] DECODABET = getDecodabet(options);
            if (source[srcOffset + GZIP] == EQUALS_SIGN) {
                destination[destOffset] = (byte) ((((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + ENCODE]] & MotionEventCompat.ACTION_MASK) << 12)) >>> URL_SAFE);
                return ENCODE;
            } else if (source[srcOffset + 3] == EQUALS_SIGN) {
                outBuff = (((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + ENCODE]] & MotionEventCompat.ACTION_MASK) << 12)) | ((DECODABET[source[srcOffset + GZIP]] & MotionEventCompat.ACTION_MASK) << 6);
                destination[destOffset] = (byte) (outBuff >>> URL_SAFE);
                destination[destOffset + ENCODE] = (byte) (outBuff >>> DO_BREAK_LINES);
                return GZIP;
            } else {
                outBuff = ((((DECODABET[source[srcOffset]] & MotionEventCompat.ACTION_MASK) << 18) | ((DECODABET[source[srcOffset + ENCODE]] & MotionEventCompat.ACTION_MASK) << 12)) | ((DECODABET[source[srcOffset + GZIP]] & MotionEventCompat.ACTION_MASK) << 6)) | (DECODABET[source[srcOffset + 3]] & MotionEventCompat.ACTION_MASK);
                destination[destOffset] = (byte) (outBuff >> URL_SAFE);
                destination[destOffset + ENCODE] = (byte) (outBuff >> DO_BREAK_LINES);
                destination[destOffset + GZIP] = (byte) outBuff;
                return 3;
            }
        }
    }

    public static byte[] decode(byte[] source) throws IOException {
        return decode(source, NO_OPTIONS, source.length, NO_OPTIONS);
    }

    public static byte[] decode(byte[] source, int off, int len, int options) throws IOException {
        if (source == null) {
            throw new NullPointerException("Cannot decode null source array.");
        } else if (off < 0 || off + len > source.length) {
            throw new IllegalArgumentException(String.format("Source array with length %d cannot have offset of %d and process %d bytes.", new Object[]{Integer.valueOf(source.length), Integer.valueOf(off), Integer.valueOf(len)}));
        } else if (len == 0) {
            return new byte[NO_OPTIONS];
        } else {
            if (len < DONT_GUNZIP) {
                throw new IllegalArgumentException("Base64-encoded string must have at least four characters, but length specified was " + len);
            }
            int b4Posn;
            byte[] DECODABET = getDecodabet(options);
            byte[] outBuff = new byte[((len * 3) / DONT_GUNZIP)];
            int outBuffPosn = NO_OPTIONS;
            byte[] b4 = new byte[DONT_GUNZIP];
            int i = off;
            int b4Posn2 = NO_OPTIONS;
            while (i < off + len) {
                byte sbiDecode = DECODABET[source[i] & MotionEventCompat.ACTION_MASK];
                if (sbiDecode >= -5) {
                    if (sbiDecode >= -1) {
                        b4Posn = b4Posn2 + ENCODE;
                        b4[b4Posn2] = source[i];
                        if (b4Posn > 3) {
                            outBuffPosn += decode4to3(b4, NO_OPTIONS, outBuff, outBuffPosn, options);
                            b4Posn = NO_OPTIONS;
                            if (source[i] == 61) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        b4Posn = b4Posn2;
                    }
                    i += ENCODE;
                    b4Posn2 = b4Posn;
                } else {
                    Object[] objArr = new Object[GZIP];
                    objArr[NO_OPTIONS] = Integer.valueOf(source[i] & MotionEventCompat.ACTION_MASK);
                    objArr[ENCODE] = Integer.valueOf(i);
                    throw new IOException(String.format("Bad Base64 input character decimal %d in array position %d", objArr));
                }
            }
            b4Posn = b4Posn2;
            byte[] out = new byte[outBuffPosn];
            System.arraycopy(outBuff, NO_OPTIONS, out, NO_OPTIONS, outBuffPosn);
            return out;
        }
    }

    public static byte[] decode(String s) throws IOException {
        return decode(s, NO_OPTIONS);
    }

    public static byte[] decode(String s, int options) throws IOException {
        IOException e;
        Throwable th;
        if (s == null) {
            throw new NullPointerException("Input string was null.");
        }
        byte[] bytes;
        try {
            bytes = s.getBytes(PREFERRED_ENCODING);
        } catch (UnsupportedEncodingException e2) {
            bytes = s.getBytes();
        }
        bytes = decode(bytes, NO_OPTIONS, bytes.length, options);
        boolean dontGunzip = (options & DONT_GUNZIP) != 0 ? true : $assertionsDisabled;
        if (bytes != null && bytes.length >= DONT_GUNZIP && !dontGunzip && 35615 == ((bytes[NO_OPTIONS] & MotionEventCompat.ACTION_MASK) | ((bytes[ENCODE] << DO_BREAK_LINES) & MotionEventCompat.ACTION_POINTER_INDEX_MASK))) {
            ByteArrayInputStream bais = null;
            GZIPInputStream gzis = null;
            ByteArrayOutputStream baos = null;
            byte[] buffer = new byte[AccessibilityEventCompat.TYPE_WINDOW_CONTENT_CHANGED];
            try {
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                try {
                    ByteArrayInputStream bais2 = new ByteArrayInputStream(bytes);
                    try {
                        GZIPInputStream gzis2 = new GZIPInputStream(bais2);
                        while (true) {
                            try {
                                int length = gzis2.read(buffer);
                                if (length < 0) {
                                    break;
                                }
                                baos2.write(buffer, NO_OPTIONS, length);
                            } catch (IOException e3) {
                                e = e3;
                                baos = baos2;
                                gzis = gzis2;
                                bais = bais2;
                            } catch (Throwable th2) {
                                th = th2;
                                baos = baos2;
                                gzis = gzis2;
                                bais = bais2;
                            }
                        }
                        bytes = baos2.toByteArray();
                        try {
                            baos2.close();
                        } catch (Exception e4) {
                        }
                        try {
                            gzis2.close();
                        } catch (Exception e5) {
                        }
                        try {
                            bais2.close();
                        } catch (Exception e6) {
                        }
                    } catch (IOException e7) {
                        e = e7;
                        baos = baos2;
                        bais = bais2;
                        try {
                            e.printStackTrace();
                            try {
                                baos.close();
                            } catch (Exception e8) {
                            }
                            try {
                                gzis.close();
                            } catch (Exception e9) {
                            }
                            try {
                                bais.close();
                            } catch (Exception e10) {
                            }
                            return bytes;
                        } catch (Throwable th3) {
                            th = th3;
                            try {
                                baos.close();
                            } catch (Exception e11) {
                            }
                            try {
                                gzis.close();
                            } catch (Exception e12) {
                            }
                            try {
                                bais.close();
                            } catch (Exception e13) {
                            }
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        baos = baos2;
                        bais = bais2;
                        baos.close();
                        gzis.close();
                        bais.close();
                        throw th;
                    }
                } catch (IOException e14) {
                    e = e14;
                    baos = baos2;
                    e.printStackTrace();
                    baos.close();
                    gzis.close();
                    bais.close();
                    return bytes;
                } catch (Throwable th5) {
                    th = th5;
                    baos = baos2;
                    baos.close();
                    gzis.close();
                    bais.close();
                    throw th;
                }
            } catch (IOException e15) {
                e = e15;
                e.printStackTrace();
                baos.close();
                gzis.close();
                bais.close();
                return bytes;
            }
        }
        return bytes;
    }

    public static Object decodeToObject(String encodedObject) throws IOException, ClassNotFoundException {
        return decodeToObject(encodedObject, NO_OPTIONS, null);
    }

    public static Object decodeToObject(String encodedObject, int options, ClassLoader loader) throws IOException, ClassNotFoundException {
        IOException e;
        Throwable th;
        ClassNotFoundException e2;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(decode(encodedObject, options));
            if (loader == null) {
                try {
                    ois = new ObjectInputStream(bais);
                } catch (IOException e3) {
                    e = e3;
                    byteArrayInputStream = bais;
                    try {
                        throw e;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (ClassNotFoundException e4) {
                    e2 = e4;
                    byteArrayInputStream = bais;
                    throw e2;
                } catch (Throwable th3) {
                    th = th3;
                    byteArrayInputStream = bais;
                    try {
                        byteArrayInputStream.close();
                    } catch (Exception e5) {
                    }
                    try {
                        ois.close();
                    } catch (Exception e6) {
                    }
                    throw th;
                }
            }
            ois = new C04271(bais, loader);
            Object obj = ois.readObject();
            try {
                bais.close();
            } catch (Exception e7) {
            }
            try {
                ois.close();
            } catch (Exception e8) {
            }
            return obj;
        } catch (IOException e9) {
            e = e9;
            throw e;
        } catch (ClassNotFoundException e10) {
            e2 = e10;
            throw e2;
        }
    }

    public static void encodeToFile(byte[] dataToEncode, String filename) throws IOException {
        IOException e;
        Throwable th;
        if (dataToEncode == null) {
            throw new NullPointerException("Data to encode was null.");
        }
        OutputStream bos = null;
        try {
            OutputStream bos2 = new OutputStream(new FileOutputStream(filename), ENCODE);
            try {
                bos2.write(dataToEncode);
                try {
                    bos2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                bos = bos2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                try {
                    bos.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static void decodeToFile(String dataToDecode, String filename) throws IOException {
        IOException e;
        Throwable th;
        OutputStream bos = null;
        try {
            OutputStream bos2 = new OutputStream(new FileOutputStream(filename), NO_OPTIONS);
            try {
                bos2.write(dataToDecode.getBytes(PREFERRED_ENCODING));
                try {
                    bos2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                bos = bos2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                bos = bos2;
                try {
                    bos.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decodeFromFile(java.lang.String r12) throws java.io.IOException {
        /*
        r3 = 0;
        r0 = 0;
        r5 = new java.io.File;	 Catch:{ IOException -> 0x0034 }
        r5.<init>(r12);	 Catch:{ IOException -> 0x0034 }
        r2 = 0;
        r6 = 0;
        r7 = 0;
        r8 = r5.length();	 Catch:{ IOException -> 0x0034 }
        r10 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r8 <= 0) goto L_0x003b;
    L_0x0015:
        r8 = new java.io.IOException;	 Catch:{ IOException -> 0x0034 }
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0034 }
        r10 = "File is too big for this convenience method (";
        r9.<init>(r10);	 Catch:{ IOException -> 0x0034 }
        r10 = r5.length();	 Catch:{ IOException -> 0x0034 }
        r9 = r9.append(r10);	 Catch:{ IOException -> 0x0034 }
        r10 = " bytes).";
        r9 = r9.append(r10);	 Catch:{ IOException -> 0x0034 }
        r9 = r9.toString();	 Catch:{ IOException -> 0x0034 }
        r8.<init>(r9);	 Catch:{ IOException -> 0x0034 }
        throw r8;	 Catch:{ IOException -> 0x0034 }
    L_0x0034:
        r4 = move-exception;
    L_0x0035:
        throw r4;	 Catch:{ all -> 0x0036 }
    L_0x0036:
        r8 = move-exception;
    L_0x0037:
        r0.close();	 Catch:{ Exception -> 0x0067 }
    L_0x003a:
        throw r8;
    L_0x003b:
        r8 = r5.length();	 Catch:{ IOException -> 0x0034 }
        r8 = (int) r8;	 Catch:{ IOException -> 0x0034 }
        r2 = new byte[r8];	 Catch:{ IOException -> 0x0034 }
        r1 = new pe.edu.upc.mobile.Utilities.Base64$InputStream;	 Catch:{ IOException -> 0x0034 }
        r8 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0034 }
        r9 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0034 }
        r9.<init>(r5);	 Catch:{ IOException -> 0x0034 }
        r8.<init>(r9);	 Catch:{ IOException -> 0x0034 }
        r9 = 0;
        r1.<init>(r8, r9);	 Catch:{ IOException -> 0x0034 }
    L_0x0052:
        r8 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r7 = r1.read(r2, r6, r8);	 Catch:{ IOException -> 0x006e, all -> 0x006b }
        if (r7 >= 0) goto L_0x0065;
    L_0x005a:
        r3 = new byte[r6];	 Catch:{ IOException -> 0x006e, all -> 0x006b }
        r8 = 0;
        r9 = 0;
        java.lang.System.arraycopy(r2, r8, r3, r9, r6);	 Catch:{ IOException -> 0x006e, all -> 0x006b }
        r1.close();	 Catch:{ Exception -> 0x0069 }
    L_0x0064:
        return r3;
    L_0x0065:
        r6 = r6 + r7;
        goto L_0x0052;
    L_0x0067:
        r9 = move-exception;
        goto L_0x003a;
    L_0x0069:
        r8 = move-exception;
        goto L_0x0064;
    L_0x006b:
        r8 = move-exception;
        r0 = r1;
        goto L_0x0037;
    L_0x006e:
        r4 = move-exception;
        r0 = r1;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: pe.edu.upc.mobile.Utilities.Base64.decodeFromFile(java.lang.String):byte[]");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String encodeFromFile(java.lang.String r12) throws java.io.IOException {
        /*
        r4 = 0;
        r0 = 0;
        r5 = new java.io.File;	 Catch:{ IOException -> 0x0046 }
        r5.<init>(r12);	 Catch:{ IOException -> 0x0046 }
        r8 = r5.length();	 Catch:{ IOException -> 0x0046 }
        r8 = (double) r8;	 Catch:{ IOException -> 0x0046 }
        r10 = 4608983858650965606; // 0x3ff6666666666666 float:2.720083E23 double:1.4;
        r8 = r8 * r10;
        r10 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r8 = r8 + r10;
        r8 = (int) r8;	 Catch:{ IOException -> 0x0046 }
        r9 = 40;
        r8 = java.lang.Math.max(r8, r9);	 Catch:{ IOException -> 0x0046 }
        r2 = new byte[r8];	 Catch:{ IOException -> 0x0046 }
        r6 = 0;
        r7 = 0;
        r1 = new pe.edu.upc.mobile.Utilities.Base64$InputStream;	 Catch:{ IOException -> 0x0046 }
        r8 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0046 }
        r9 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0046 }
        r9.<init>(r5);	 Catch:{ IOException -> 0x0046 }
        r8.<init>(r9);	 Catch:{ IOException -> 0x0046 }
        r9 = 1;
        r1.<init>(r8, r9);	 Catch:{ IOException -> 0x0046 }
    L_0x0030:
        r8 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r7 = r1.read(r2, r6, r8);	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        if (r7 >= 0) goto L_0x0044;
    L_0x0038:
        r4 = new java.lang.String;	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        r8 = 0;
        r9 = "US-ASCII";
        r4.<init>(r2, r8, r6, r9);	 Catch:{ IOException -> 0x0054, all -> 0x0051 }
        r1.close();	 Catch:{ Exception -> 0x004f }
    L_0x0043:
        return r4;
    L_0x0044:
        r6 = r6 + r7;
        goto L_0x0030;
    L_0x0046:
        r3 = move-exception;
    L_0x0047:
        throw r3;	 Catch:{ all -> 0x0048 }
    L_0x0048:
        r8 = move-exception;
    L_0x0049:
        r0.close();	 Catch:{ Exception -> 0x004d }
    L_0x004c:
        throw r8;
    L_0x004d:
        r9 = move-exception;
        goto L_0x004c;
    L_0x004f:
        r8 = move-exception;
        goto L_0x0043;
    L_0x0051:
        r8 = move-exception;
        r0 = r1;
        goto L_0x0049;
    L_0x0054:
        r3 = move-exception;
        r0 = r1;
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: pe.edu.upc.mobile.Utilities.Base64.encodeFromFile(java.lang.String):java.lang.String");
    }

    public static void encodeFileToFile(String infile, String outfile) throws IOException {
        IOException e;
        Throwable th;
        String encoded = encodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            java.io.OutputStream out2 = new BufferedOutputStream(new FileOutputStream(outfile));
            try {
                out2.write(encoded.getBytes(PREFERRED_ENCODING));
                try {
                    out2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                out = out2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                try {
                    out.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }

    public static void decodeFileToFile(String infile, String outfile) throws IOException {
        IOException e;
        Throwable th;
        byte[] decoded = decodeFromFile(infile);
        java.io.OutputStream out = null;
        try {
            java.io.OutputStream out2 = new BufferedOutputStream(new FileOutputStream(outfile));
            try {
                out2.write(decoded);
                try {
                    out2.close();
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
                e = e3;
                out = out2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                try {
                    out.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            throw e;
        }
    }
}
