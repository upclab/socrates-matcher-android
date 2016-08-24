package org.apache.commons.lang3.text.translate;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.analytics.containertag.proto.MutableServing.ServingValue;
import com.google.android.gms.location.LocationRequest;
import java.lang.reflect.Array;

public class EntityArrays {
    private static final String[][] APOS_ESCAPE;
    private static final String[][] APOS_UNESCAPE;
    private static final String[][] BASIC_ESCAPE;
    private static final String[][] BASIC_UNESCAPE;
    private static final String[][] HTML40_EXTENDED_ESCAPE;
    private static final String[][] HTML40_EXTENDED_UNESCAPE;
    private static final String[][] ISO8859_1_ESCAPE;
    private static final String[][] ISO8859_1_UNESCAPE;
    private static final String[][] JAVA_CTRL_CHARS_ESCAPE;
    private static final String[][] JAVA_CTRL_CHARS_UNESCAPE;

    public static String[][] ISO8859_1_ESCAPE() {
        return (String[][]) ISO8859_1_ESCAPE.clone();
    }

    static {
        r0 = new String[96][];
        r0[0] = new String[]{"\u00a0", "&nbsp;"};
        r0[1] = new String[]{"\u00a1", "&iexcl;"};
        r0[2] = new String[]{"\u00a2", "&cent;"};
        r0[3] = new String[]{"\u00a3", "&pound;"};
        r0[4] = new String[]{"\u00a4", "&curren;"};
        r0[5] = new String[]{"\u00a5", "&yen;"};
        r0[6] = new String[]{"\u00a6", "&brvbar;"};
        r0[7] = new String[]{"\u00a7", "&sect;"};
        r0[8] = new String[]{"\u00a8", "&uml;"};
        r0[9] = new String[]{"\u00a9", "&copy;"};
        r0[10] = new String[]{"\u00aa", "&ordf;"};
        r0[11] = new String[]{"\u00ab", "&laquo;"};
        r0[12] = new String[]{"\u00ac", "&not;"};
        r0[13] = new String[]{"\u00ad", "&shy;"};
        r0[14] = new String[]{"\u00ae", "&reg;"};
        r0[15] = new String[]{"\u00af", "&macr;"};
        r0[16] = new String[]{"\u00b0", "&deg;"};
        r0[17] = new String[]{"\u00b1", "&plusmn;"};
        r0[18] = new String[]{"\u00b2", "&sup2;"};
        r0[19] = new String[]{"\u00b3", "&sup3;"};
        r0[20] = new String[]{"\u00b4", "&acute;"};
        r0[21] = new String[]{"\u00b5", "&micro;"};
        r0[22] = new String[]{"\u00b6", "&para;"};
        r0[23] = new String[]{"\u00b7", "&middot;"};
        r0[24] = new String[]{"\u00b8", "&cedil;"};
        r0[25] = new String[]{"\u00b9", "&sup1;"};
        r0[26] = new String[]{"\u00ba", "&ordm;"};
        r0[27] = new String[]{"\u00bb", "&raquo;"};
        r0[28] = new String[]{"\u00bc", "&frac14;"};
        r0[29] = new String[]{"\u00bd", "&frac12;"};
        r0[30] = new String[]{"\u00be", "&frac34;"};
        r0[31] = new String[]{"\u00bf", "&iquest;"};
        r0[32] = new String[]{"\u00c0", "&Agrave;"};
        r0[33] = new String[]{"\u00c1", "&Aacute;"};
        r0[34] = new String[]{"\u00c2", "&Acirc;"};
        r0[35] = new String[]{"\u00c3", "&Atilde;"};
        r0[36] = new String[]{"\u00c4", "&Auml;"};
        r0[37] = new String[]{"\u00c5", "&Aring;"};
        r0[38] = new String[]{"\u00c6", "&AElig;"};
        r0[39] = new String[]{"\u00c7", "&Ccedil;"};
        r0[40] = new String[]{"\u00c8", "&Egrave;"};
        r0[41] = new String[]{"\u00c9", "&Eacute;"};
        r0[42] = new String[]{"\u00ca", "&Ecirc;"};
        r0[43] = new String[]{"\u00cb", "&Euml;"};
        r0[44] = new String[]{"\u00cc", "&Igrave;"};
        r0[45] = new String[]{"\u00cd", "&Iacute;"};
        r0[46] = new String[]{"\u00ce", "&Icirc;"};
        r0[47] = new String[]{"\u00cf", "&Iuml;"};
        r0[48] = new String[]{"\u00d0", "&ETH;"};
        r0[49] = new String[]{"\u00d1", "&Ntilde;"};
        r0[50] = new String[]{"\u00d2", "&Ograve;"};
        r0[51] = new String[]{"\u00d3", "&Oacute;"};
        r0[52] = new String[]{"\u00d4", "&Ocirc;"};
        r0[53] = new String[]{"\u00d5", "&Otilde;"};
        r0[54] = new String[]{"\u00d6", "&Ouml;"};
        r0[55] = new String[]{"\u00d7", "&times;"};
        r0[56] = new String[]{"\u00d8", "&Oslash;"};
        r0[57] = new String[]{"\u00d9", "&Ugrave;"};
        r0[58] = new String[]{"\u00da", "&Uacute;"};
        r0[59] = new String[]{"\u00db", "&Ucirc;"};
        r0[60] = new String[]{"\u00dc", "&Uuml;"};
        r0[61] = new String[]{"\u00dd", "&Yacute;"};
        r0[62] = new String[]{"\u00de", "&THORN;"};
        r0[63] = new String[]{"\u00df", "&szlig;"};
        r0[64] = new String[]{"\u00e0", "&agrave;"};
        r0[65] = new String[]{"\u00e1", "&aacute;"};
        r0[66] = new String[]{"\u00e2", "&acirc;"};
        r0[67] = new String[]{"\u00e3", "&atilde;"};
        r0[68] = new String[]{"\u00e4", "&auml;"};
        r0[69] = new String[]{"\u00e5", "&aring;"};
        r0[70] = new String[]{"\u00e6", "&aelig;"};
        r0[71] = new String[]{"\u00e7", "&ccedil;"};
        r0[72] = new String[]{"\u00e8", "&egrave;"};
        r0[73] = new String[]{"\u00e9", "&eacute;"};
        r0[74] = new String[]{"\u00ea", "&ecirc;"};
        r0[75] = new String[]{"\u00eb", "&euml;"};
        r0[76] = new String[]{"\u00ec", "&igrave;"};
        r0[77] = new String[]{"\u00ed", "&iacute;"};
        r0[78] = new String[]{"\u00ee", "&icirc;"};
        r0[79] = new String[]{"\u00ef", "&iuml;"};
        r0[80] = new String[]{"\u00f0", "&eth;"};
        r0[81] = new String[]{"\u00f1", "&ntilde;"};
        r0[82] = new String[]{"\u00f2", "&ograve;"};
        r0[83] = new String[]{"\u00f3", "&oacute;"};
        r0[84] = new String[]{"\u00f4", "&ocirc;"};
        r0[85] = new String[]{"\u00f5", "&otilde;"};
        r0[86] = new String[]{"\u00f6", "&ouml;"};
        r0[87] = new String[]{"\u00f7", "&divide;"};
        r0[88] = new String[]{"\u00f8", "&oslash;"};
        r0[89] = new String[]{"\u00f9", "&ugrave;"};
        r0[90] = new String[]{"\u00fa", "&uacute;"};
        r0[91] = new String[]{"\u00fb", "&ucirc;"};
        r0[92] = new String[]{"\u00fc", "&uuml;"};
        r0[93] = new String[]{"\u00fd", "&yacute;"};
        r0[94] = new String[]{"\u00fe", "&thorn;"};
        r0[95] = new String[]{"\u00ff", "&yuml;"};
        ISO8859_1_ESCAPE = r0;
        ISO8859_1_UNESCAPE = invert(ISO8859_1_ESCAPE);
        r0 = new String[151][];
        r0[0] = new String[]{"\u0192", "&fnof;"};
        r0[1] = new String[]{"\u0391", "&Alpha;"};
        r0[2] = new String[]{"\u0392", "&Beta;"};
        r0[3] = new String[]{"\u0393", "&Gamma;"};
        r0[4] = new String[]{"\u0394", "&Delta;"};
        r0[5] = new String[]{"\u0395", "&Epsilon;"};
        r0[6] = new String[]{"\u0396", "&Zeta;"};
        r0[7] = new String[]{"\u0397", "&Eta;"};
        r0[8] = new String[]{"\u0398", "&Theta;"};
        r0[9] = new String[]{"\u0399", "&Iota;"};
        r0[10] = new String[]{"\u039a", "&Kappa;"};
        r0[11] = new String[]{"\u039b", "&Lambda;"};
        r0[12] = new String[]{"\u039c", "&Mu;"};
        r0[13] = new String[]{"\u039d", "&Nu;"};
        r0[14] = new String[]{"\u039e", "&Xi;"};
        r0[15] = new String[]{"\u039f", "&Omicron;"};
        r0[16] = new String[]{"\u03a0", "&Pi;"};
        r0[17] = new String[]{"\u03a1", "&Rho;"};
        r0[18] = new String[]{"\u03a3", "&Sigma;"};
        r0[19] = new String[]{"\u03a4", "&Tau;"};
        r0[20] = new String[]{"\u03a5", "&Upsilon;"};
        r0[21] = new String[]{"\u03a6", "&Phi;"};
        r0[22] = new String[]{"\u03a7", "&Chi;"};
        r0[23] = new String[]{"\u03a8", "&Psi;"};
        r0[24] = new String[]{"\u03a9", "&Omega;"};
        r0[25] = new String[]{"\u03b1", "&alpha;"};
        r0[26] = new String[]{"\u03b2", "&beta;"};
        r0[27] = new String[]{"\u03b3", "&gamma;"};
        r0[28] = new String[]{"\u03b4", "&delta;"};
        r0[29] = new String[]{"\u03b5", "&epsilon;"};
        r0[30] = new String[]{"\u03b6", "&zeta;"};
        r0[31] = new String[]{"\u03b7", "&eta;"};
        r0[32] = new String[]{"\u03b8", "&theta;"};
        r0[33] = new String[]{"\u03b9", "&iota;"};
        r0[34] = new String[]{"\u03ba", "&kappa;"};
        r0[35] = new String[]{"\u03bb", "&lambda;"};
        r0[36] = new String[]{"\u03bc", "&mu;"};
        r0[37] = new String[]{"\u03bd", "&nu;"};
        r0[38] = new String[]{"\u03be", "&xi;"};
        r0[39] = new String[]{"\u03bf", "&omicron;"};
        r0[40] = new String[]{"\u03c0", "&pi;"};
        r0[41] = new String[]{"\u03c1", "&rho;"};
        r0[42] = new String[]{"\u03c2", "&sigmaf;"};
        r0[43] = new String[]{"\u03c3", "&sigma;"};
        r0[44] = new String[]{"\u03c4", "&tau;"};
        r0[45] = new String[]{"\u03c5", "&upsilon;"};
        r0[46] = new String[]{"\u03c6", "&phi;"};
        r0[47] = new String[]{"\u03c7", "&chi;"};
        r0[48] = new String[]{"\u03c8", "&psi;"};
        r0[49] = new String[]{"\u03c9", "&omega;"};
        r0[50] = new String[]{"\u03d1", "&thetasym;"};
        r0[51] = new String[]{"\u03d2", "&upsih;"};
        r0[52] = new String[]{"\u03d6", "&piv;"};
        r0[53] = new String[]{"\u2022", "&bull;"};
        r0[54] = new String[]{"\u2026", "&hellip;"};
        r0[55] = new String[]{"\u2032", "&prime;"};
        r0[56] = new String[]{"\u2033", "&Prime;"};
        r0[57] = new String[]{"\u203e", "&oline;"};
        r0[58] = new String[]{"\u2044", "&frasl;"};
        r0[59] = new String[]{"\u2118", "&weierp;"};
        r0[60] = new String[]{"\u2111", "&image;"};
        r0[61] = new String[]{"\u211c", "&real;"};
        r0[62] = new String[]{"\u2122", "&trade;"};
        r0[63] = new String[]{"\u2135", "&alefsym;"};
        r0[64] = new String[]{"\u2190", "&larr;"};
        r0[65] = new String[]{"\u2191", "&uarr;"};
        r0[66] = new String[]{"\u2192", "&rarr;"};
        r0[67] = new String[]{"\u2193", "&darr;"};
        r0[68] = new String[]{"\u2194", "&harr;"};
        r0[69] = new String[]{"\u21b5", "&crarr;"};
        r0[70] = new String[]{"\u21d0", "&lArr;"};
        r0[71] = new String[]{"\u21d1", "&uArr;"};
        r0[72] = new String[]{"\u21d2", "&rArr;"};
        r0[73] = new String[]{"\u21d3", "&dArr;"};
        r0[74] = new String[]{"\u21d4", "&hArr;"};
        r0[75] = new String[]{"\u2200", "&forall;"};
        r0[76] = new String[]{"\u2202", "&part;"};
        r0[77] = new String[]{"\u2203", "&exist;"};
        r0[78] = new String[]{"\u2205", "&empty;"};
        r0[79] = new String[]{"\u2207", "&nabla;"};
        r0[80] = new String[]{"\u2208", "&isin;"};
        r0[81] = new String[]{"\u2209", "&notin;"};
        r0[82] = new String[]{"\u220b", "&ni;"};
        r0[83] = new String[]{"\u220f", "&prod;"};
        r0[84] = new String[]{"\u2211", "&sum;"};
        r0[85] = new String[]{"\u2212", "&minus;"};
        r0[86] = new String[]{"\u2217", "&lowast;"};
        r0[87] = new String[]{"\u221a", "&radic;"};
        r0[88] = new String[]{"\u221d", "&prop;"};
        r0[89] = new String[]{"\u221e", "&infin;"};
        r0[90] = new String[]{"\u2220", "&ang;"};
        r0[91] = new String[]{"\u2227", "&and;"};
        r0[92] = new String[]{"\u2228", "&or;"};
        r0[93] = new String[]{"\u2229", "&cap;"};
        r0[94] = new String[]{"\u222a", "&cup;"};
        r0[95] = new String[]{"\u222b", "&int;"};
        r0[96] = new String[]{"\u2234", "&there4;"};
        r0[97] = new String[]{"\u223c", "&sim;"};
        r0[98] = new String[]{"\u2245", "&cong;"};
        r0[99] = new String[]{"\u2248", "&asymp;"};
        r0[100] = new String[]{"\u2260", "&ne;"};
        r0[ServingValue.EXT_FIELD_NUMBER] = new String[]{"\u2261", "&equiv;"};
        r0[LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY] = new String[]{"\u2264", "&le;"};
        r0[103] = new String[]{"\u2265", "&ge;"};
        r0[LocationRequest.PRIORITY_LOW_POWER] = new String[]{"\u2282", "&sub;"};
        r0[LocationRequest.PRIORITY_NO_POWER] = new String[]{"\u2283", "&sup;"};
        r0[106] = new String[]{"\u2286", "&sube;"};
        r0[107] = new String[]{"\u2287", "&supe;"};
        r0[108] = new String[]{"\u2295", "&oplus;"};
        r0[109] = new String[]{"\u2297", "&otimes;"};
        r0[110] = new String[]{"\u22a5", "&perp;"};
        r0[111] = new String[]{"\u22c5", "&sdot;"};
        r0[112] = new String[]{"\u2308", "&lceil;"};
        r0[113] = new String[]{"\u2309", "&rceil;"};
        r0[114] = new String[]{"\u230a", "&lfloor;"};
        r0[115] = new String[]{"\u230b", "&rfloor;"};
        r0[116] = new String[]{"\u2329", "&lang;"};
        r0[117] = new String[]{"\u232a", "&rang;"};
        r0[118] = new String[]{"\u25ca", "&loz;"};
        r0[119] = new String[]{"\u2660", "&spades;"};
        r0[120] = new String[]{"\u2663", "&clubs;"};
        r0[121] = new String[]{"\u2665", "&hearts;"};
        r0[122] = new String[]{"\u2666", "&diams;"};
        r0[123] = new String[]{"\u0152", "&OElig;"};
        r0[124] = new String[]{"\u0153", "&oelig;"};
        r0[125] = new String[]{"\u0160", "&Scaron;"};
        r0[126] = new String[]{"\u0161", "&scaron;"};
        r0[127] = new String[]{"\u0178", "&Yuml;"};
        r0[AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER] = new String[]{"\u02c6", "&circ;"};
        r0[129] = new String[]{"\u02dc", "&tilde;"};
        r0[130] = new String[]{"\u2002", "&ensp;"};
        r0[131] = new String[]{"\u2003", "&emsp;"};
        r0[132] = new String[]{"\u2009", "&thinsp;"};
        r0[133] = new String[]{"\u200c", "&zwnj;"};
        r0[134] = new String[]{"\u200d", "&zwj;"};
        r0[135] = new String[]{"\u200e", "&lrm;"};
        r0[136] = new String[]{"\u200f", "&rlm;"};
        r0[137] = new String[]{"\u2013", "&ndash;"};
        r0[138] = new String[]{"\u2014", "&mdash;"};
        r0[139] = new String[]{"\u2018", "&lsquo;"};
        r0[140] = new String[]{"\u2019", "&rsquo;"};
        r0[141] = new String[]{"\u201a", "&sbquo;"};
        r0[142] = new String[]{"\u201c", "&ldquo;"};
        r0[143] = new String[]{"\u201d", "&rdquo;"};
        r0[144] = new String[]{"\u201e", "&bdquo;"};
        r0[145] = new String[]{"\u2020", "&dagger;"};
        r0[146] = new String[]{"\u2021", "&Dagger;"};
        r0[147] = new String[]{"\u2030", "&permil;"};
        r0[148] = new String[]{"\u2039", "&lsaquo;"};
        r0[149] = new String[]{"\u203a", "&rsaquo;"};
        r0[150] = new String[]{"\u20ac", "&euro;"};
        HTML40_EXTENDED_ESCAPE = r0;
        HTML40_EXTENDED_UNESCAPE = invert(HTML40_EXTENDED_ESCAPE);
        r0 = new String[4][];
        r0[0] = new String[]{"\"", "&quot;"};
        r0[1] = new String[]{"&", "&amp;"};
        r0[2] = new String[]{"<", "&lt;"};
        r0[3] = new String[]{">", "&gt;"};
        BASIC_ESCAPE = r0;
        BASIC_UNESCAPE = invert(BASIC_ESCAPE);
        r0 = new String[1][];
        r0[0] = new String[]{"'", "&apos;"};
        APOS_ESCAPE = r0;
        APOS_UNESCAPE = invert(APOS_ESCAPE);
        r0 = new String[5][];
        r0[0] = new String[]{"\b", "\\b"};
        r0[1] = new String[]{"\n", "\\n"};
        r0[2] = new String[]{"\t", "\\t"};
        r0[3] = new String[]{"\f", "\\f"};
        r0[4] = new String[]{"\r", "\\r"};
        JAVA_CTRL_CHARS_ESCAPE = r0;
        JAVA_CTRL_CHARS_UNESCAPE = invert(JAVA_CTRL_CHARS_ESCAPE);
    }

    public static String[][] ISO8859_1_UNESCAPE() {
        return (String[][]) ISO8859_1_UNESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_ESCAPE() {
        return (String[][]) HTML40_EXTENDED_ESCAPE.clone();
    }

    public static String[][] HTML40_EXTENDED_UNESCAPE() {
        return (String[][]) HTML40_EXTENDED_UNESCAPE.clone();
    }

    public static String[][] BASIC_ESCAPE() {
        return (String[][]) BASIC_ESCAPE.clone();
    }

    public static String[][] BASIC_UNESCAPE() {
        return (String[][]) BASIC_UNESCAPE.clone();
    }

    public static String[][] APOS_ESCAPE() {
        return (String[][]) APOS_ESCAPE.clone();
    }

    public static String[][] APOS_UNESCAPE() {
        return (String[][]) APOS_UNESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_ESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_ESCAPE.clone();
    }

    public static String[][] JAVA_CTRL_CHARS_UNESCAPE() {
        return (String[][]) JAVA_CTRL_CHARS_UNESCAPE.clone();
    }

    public static String[][] invert(String[][] array) {
        String[][] newarray = (String[][]) Array.newInstance(String.class, new int[]{array.length, 2});
        for (int i = 0; i < array.length; i++) {
            newarray[i][0] = array[i][1];
            newarray[i][1] = array[i][0];
        }
        return newarray;
    }
}
