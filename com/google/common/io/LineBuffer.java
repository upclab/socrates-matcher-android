package com.google.common.io;

import com.google.analytics.containertag.proto.MutableServing.Resource;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

abstract class LineBuffer {
    private StringBuilder line;
    private boolean sawReturn;

    protected abstract void handleLine(String str, String str2) throws IOException;

    LineBuffer() {
        this.line = new StringBuilder();
    }

    protected void add(char[] cbuf, int off, int len) throws IOException {
        int pos = off;
        if (this.sawReturn && len > 0) {
            boolean z;
            if (cbuf[pos] == '\n') {
                z = true;
            } else {
                z = false;
            }
            if (finishLine(z)) {
                pos++;
            }
        }
        int start = pos;
        int end = off + len;
        while (pos < end) {
            switch (cbuf[pos]) {
                case Value.ESCAPING_FIELD_NUMBER /*10*/:
                    this.line.append(cbuf, start, pos - start);
                    finishLine(true);
                    start = pos + 1;
                    break;
                case Resource.VERSION_FIELD_NUMBER /*13*/:
                    this.line.append(cbuf, start, pos - start);
                    this.sawReturn = true;
                    if (pos + 1 < end) {
                        if (finishLine(cbuf[pos + 1] == '\n')) {
                            pos++;
                        }
                    }
                    start = pos + 1;
                    break;
                default:
                    break;
            }
            pos++;
        }
        this.line.append(cbuf, start, (off + len) - start);
    }

    private boolean finishLine(boolean sawNewline) throws IOException {
        String stringBuilder = this.line.toString();
        String str = this.sawReturn ? sawNewline ? "\r\n" : "\r" : sawNewline ? "\n" : StringUtils.EMPTY;
        handleLine(stringBuilder, str);
        this.line = new StringBuilder();
        this.sawReturn = false;
        return sawNewline;
    }

    protected void finish() throws IOException {
        if (this.sawReturn || this.line.length() > 0) {
            finishLine(false);
        }
    }
}
