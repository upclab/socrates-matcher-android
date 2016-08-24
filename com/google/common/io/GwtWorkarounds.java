package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

@GwtCompatible(emulated = true)
final class GwtWorkarounds {

    /* renamed from: com.google.common.io.GwtWorkarounds.3 */
    static class C02823 extends InputStream {
        final /* synthetic */ ByteInput val$input;

        C02823(ByteInput byteInput) {
            this.val$input = byteInput;
        }

        public int read() throws IOException {
            return this.val$input.read();
        }

        public int read(byte[] b, int off, int len) throws IOException {
            Preconditions.checkNotNull(b);
            Preconditions.checkPositionIndexes(off, off + len, b.length);
            if (len == 0) {
                return 0;
            }
            int firstByte = read();
            if (firstByte == -1) {
                return -1;
            }
            b[off] = (byte) firstByte;
            for (int dst = 1; dst < len; dst++) {
                int readByte = read();
                if (readByte == -1) {
                    return dst;
                }
                b[off + dst] = (byte) readByte;
            }
            return len;
        }

        public void close() throws IOException {
            this.val$input.close();
        }
    }

    /* renamed from: com.google.common.io.GwtWorkarounds.4 */
    static class C02834 extends OutputStream {
        final /* synthetic */ ByteOutput val$output;

        C02834(ByteOutput byteOutput) {
            this.val$output = byteOutput;
        }

        public void write(int b) throws IOException {
            this.val$output.write((byte) b);
        }

        public void flush() throws IOException {
            this.val$output.flush();
        }

        public void close() throws IOException {
            this.val$output.close();
        }
    }

    interface ByteInput {
        void close() throws IOException;

        int read() throws IOException;
    }

    interface ByteOutput {
        void close() throws IOException;

        void flush() throws IOException;

        void write(byte b) throws IOException;
    }

    interface CharInput {
        void close() throws IOException;

        int read() throws IOException;
    }

    interface CharOutput {
        void close() throws IOException;

        void flush() throws IOException;

        void write(char c) throws IOException;
    }

    /* renamed from: com.google.common.io.GwtWorkarounds.1 */
    static class C07751 implements CharInput {
        final /* synthetic */ Reader val$reader;

        C07751(Reader reader) {
            this.val$reader = reader;
        }

        public int read() throws IOException {
            return this.val$reader.read();
        }

        public void close() throws IOException {
            this.val$reader.close();
        }
    }

    /* renamed from: com.google.common.io.GwtWorkarounds.2 */
    static class C07762 implements CharInput {
        int index;
        final /* synthetic */ CharSequence val$chars;

        C07762(CharSequence charSequence) {
            this.val$chars = charSequence;
            this.index = 0;
        }

        public int read() {
            if (this.index >= this.val$chars.length()) {
                return -1;
            }
            CharSequence charSequence = this.val$chars;
            int i = this.index;
            this.index = i + 1;
            return charSequence.charAt(i);
        }

        public void close() {
            this.index = this.val$chars.length();
        }
    }

    /* renamed from: com.google.common.io.GwtWorkarounds.5 */
    static class C07775 implements CharOutput {
        final /* synthetic */ Writer val$writer;

        C07775(Writer writer) {
            this.val$writer = writer;
        }

        public void write(char c) throws IOException {
            this.val$writer.append(c);
        }

        public void flush() throws IOException {
            this.val$writer.flush();
        }

        public void close() throws IOException {
            this.val$writer.close();
        }
    }

    /* renamed from: com.google.common.io.GwtWorkarounds.6 */
    static class C07786 implements CharOutput {
        final /* synthetic */ StringBuilder val$builder;

        C07786(StringBuilder stringBuilder) {
            this.val$builder = stringBuilder;
        }

        public void write(char c) {
            this.val$builder.append(c);
        }

        public void flush() {
        }

        public void close() {
        }

        public String toString() {
            return this.val$builder.toString();
        }
    }

    private GwtWorkarounds() {
    }

    @GwtIncompatible("Reader")
    static CharInput asCharInput(Reader reader) {
        Preconditions.checkNotNull(reader);
        return new C07751(reader);
    }

    static CharInput asCharInput(CharSequence chars) {
        Preconditions.checkNotNull(chars);
        return new C07762(chars);
    }

    @GwtIncompatible("InputStream")
    static InputStream asInputStream(ByteInput input) {
        Preconditions.checkNotNull(input);
        return new C02823(input);
    }

    @GwtIncompatible("OutputStream")
    static OutputStream asOutputStream(ByteOutput output) {
        Preconditions.checkNotNull(output);
        return new C02834(output);
    }

    @GwtIncompatible("Writer")
    static CharOutput asCharOutput(Writer writer) {
        Preconditions.checkNotNull(writer);
        return new C07775(writer);
    }

    static CharOutput stringBuilderOutput(int initialSize) {
        return new C07786(new StringBuilder(initialSize));
    }
}
