package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.tagmanager.protobuf.CodedOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;

@Beta
public final class LineReader {
    private final char[] buf;
    private final CharBuffer cbuf;
    private final LineBuffer lineBuf;
    private final Queue<String> lines;
    private final Readable readable;
    private final Reader reader;

    /* renamed from: com.google.common.io.LineReader.1 */
    class C07791 extends LineBuffer {
        C07791() {
        }

        protected void handleLine(String line, String end) {
            LineReader.this.lines.add(line);
        }
    }

    public LineReader(Readable readable) {
        this.buf = new char[CodedOutputStream.DEFAULT_BUFFER_SIZE];
        this.cbuf = CharBuffer.wrap(this.buf);
        this.lines = new LinkedList();
        this.lineBuf = new C07791();
        this.readable = (Readable) Preconditions.checkNotNull(readable);
        this.reader = readable instanceof Reader ? (Reader) readable : null;
    }

    public String readLine() throws IOException {
        while (this.lines.peek() == null) {
            this.cbuf.clear();
            int read = this.reader != null ? this.reader.read(this.buf, 0, this.buf.length) : this.readable.read(this.cbuf);
            if (read == -1) {
                this.lineBuf.finish();
                break;
            }
            this.lineBuf.add(this.buf, 0, read);
        }
        return (String) this.lines.poll();
    }
}
