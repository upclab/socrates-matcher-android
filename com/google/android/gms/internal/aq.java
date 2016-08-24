package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class aq {
    private static final Pattern cN;
    private static final Pattern cO;

    static {
        cN = Pattern.compile("\\\\.");
        cO = Pattern.compile("[\\\\\"/\b\f\n\r\t]");
    }

    public static String m213r(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = cO.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            switch (matcher.group().charAt(0)) {
                case Value.INTEGER_FIELD_NUMBER /*8*/:
                    matcher.appendReplacement(stringBuffer, "\\\\b");
                    break;
                case Value.CONTAINS_REFERENCES_FIELD_NUMBER /*9*/:
                    matcher.appendReplacement(stringBuffer, "\\\\t");
                    break;
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    matcher.appendReplacement(stringBuffer, "\\\\n");
                    break;
                case Value.BOOLEAN_FIELD_NUMBER /*12*/:
                    matcher.appendReplacement(stringBuffer, "\\\\f");
                    break;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    matcher.appendReplacement(stringBuffer, "\\\\r");
                    break;
                case '\"':
                    matcher.appendReplacement(stringBuffer, "\\\\\\\"");
                    break;
                case '/':
                    matcher.appendReplacement(stringBuffer, "\\\\/");
                    break;
                case '\\':
                    matcher.appendReplacement(stringBuffer, "\\\\\\\\");
                    break;
                default:
                    break;
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
