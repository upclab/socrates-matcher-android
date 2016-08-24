package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Beta
public final class CharStreams {
    private static final int BUF_SIZE = 2048;

    /* renamed from: com.google.common.io.CharStreams.2 */
    static class C02812 extends Reader {
        final /* synthetic */ Readable val$readable;

        C02812(Readable readable) {
            this.val$readable = readable;
        }

        public int read(char[] cbuf, int off, int len) throws IOException {
            return read(CharBuffer.wrap(cbuf, off, len));
        }

        public int read(CharBuffer target) throws IOException {
            return this.val$readable.read(target);
        }

        public void close() throws IOException {
            if (this.val$readable instanceof Closeable) {
                ((Closeable) this.val$readable).close();
            }
        }
    }

    private static final class NullWriter extends Writer {
        private static final NullWriter INSTANCE;

        private NullWriter() {
        }

        static {
            INSTANCE = new NullWriter();
        }

        public void write(int c) {
        }

        public void write(char[] cbuf) {
            Preconditions.checkNotNull(cbuf);
        }

        public void write(char[] cbuf, int off, int len) {
            Preconditions.checkPositionIndexes(off, off + len, cbuf.length);
        }

        public void write(String str) {
            Preconditions.checkNotNull(str);
        }

        public void write(String str, int off, int len) {
            Preconditions.checkPositionIndexes(off, off + len, str.length());
        }

        public Writer append(CharSequence csq) {
            Preconditions.checkNotNull(csq);
            return this;
        }

        public Writer append(CharSequence csq, int start, int end) {
            Preconditions.checkPositionIndexes(start, end, csq.length());
            return this;
        }

        public Writer append(char c) {
            return this;
        }

        public void flush() {
        }

        public void close() {
        }

        public String toString() {
            return "CharStreams.nullWriter()";
        }
    }

    /* renamed from: com.google.common.io.CharStreams.1 */
    static class C07721 implements Function<InputSupplier<? extends Reader>, CharSource> {
        C07721() {
        }

        public CharSource apply(InputSupplier<? extends Reader> input) {
            return CharStreams.asCharSource(input);
        }
    }

    /* renamed from: com.google.common.io.CharStreams.3 */
    static class C10343 extends CharSource {
        final /* synthetic */ InputSupplier val$supplier;

        C10343(InputSupplier inputSupplier) {
            this.val$supplier = inputSupplier;
        }

        public Reader openStream() throws IOException {
            return CharStreams.asReader((Readable) this.val$supplier.getInput());
        }

        public String toString() {
            return "CharStreams.asCharSource(" + this.val$supplier + ")";
        }
    }

    /* renamed from: com.google.common.io.CharStreams.4 */
    static class C10354 extends CharSink {
        final /* synthetic */ OutputSupplier val$supplier;

        C10354(OutputSupplier outputSupplier) {
            this.val$supplier = outputSupplier;
        }

        public Writer openStream() throws IOException {
            return CharStreams.asWriter((Appendable) this.val$supplier.getOutput());
        }

        public String toString() {
            return "CharStreams.asCharSink(" + this.val$supplier + ")";
        }
    }

    private CharStreams() {
    }

    @Deprecated
    public static InputSupplier<StringReader> newReaderSupplier(String value) {
        return asInputSupplier(CharSource.wrap(value));
    }

    @Deprecated
    public static InputSupplier<InputStreamReader> newReaderSupplier(InputSupplier<? extends InputStream> in, Charset charset) {
        return asInputSupplier(ByteStreams.asByteSource(in).asCharSource(charset));
    }

    @Deprecated
    public static OutputSupplier<OutputStreamWriter> newWriterSupplier(OutputSupplier<? extends OutputStream> out, Charset charset) {
        return asOutputSupplier(ByteStreams.asByteSink(out).asCharSink(charset));
    }

    @Deprecated
    public static <W extends Appendable & Closeable> void write(CharSequence from, OutputSupplier<W> to) throws IOException {
        asCharSink(to).write(from);
    }

    @Deprecated
    public static <R extends Readable & Closeable, W extends Appendable & Closeable> long copy(InputSupplier<R> from, OutputSupplier<W> to) throws IOException {
        return asCharSource(from).copyTo(asCharSink(to));
    }

    @Deprecated
    public static <R extends Readable & Closeable> long copy(InputSupplier<R> from, Appendable to) throws IOException {
        return asCharSource(from).copyTo(to);
    }

    public static long copy(Readable from, Appendable to) throws IOException {
        Preconditions.checkNotNull(from);
        Preconditions.checkNotNull(to);
        CharBuffer buf = CharBuffer.allocate(BUF_SIZE);
        long total = 0;
        while (from.read(buf) != -1) {
            buf.flip();
            to.append(buf);
            total += (long) buf.remaining();
            buf.clear();
        }
        return total;
    }

    public static String toString(Readable r) throws IOException {
        return toStringBuilder(r).toString();
    }

    @Deprecated
    public static <R extends Readable & Closeable> String toString(InputSupplier<R> supplier) throws IOException {
        return asCharSource(supplier).read();
    }

    private static StringBuilder toStringBuilder(Readable r) throws IOException {
        Appendable sb = new StringBuilder();
        copy(r, sb);
        return sb;
    }

    @Deprecated
    public static <R extends Readable & Closeable> String readFirstLine(InputSupplier<R> supplier) throws IOException {
        return asCharSource(supplier).readFirstLine();
    }

    @Deprecated
    public static <R extends Readable & Closeable> List<String> readLines(InputSupplier<R> supplier) throws IOException {
        Closer closer = Closer.create();
        try {
            List<String> readLines = readLines((Readable) closer.register((Closeable) supplier.getInput()));
            closer.close();
            return readLines;
        } catch (Throwable th) {
            closer.close();
        }
    }

    public static List<String> readLines(Readable r) throws IOException {
        List<String> result = new ArrayList();
        LineReader lineReader = new LineReader(r);
        while (true) {
            String line = lineReader.readLine();
            if (line == null) {
                return result;
            }
            result.add(line);
        }
    }

    public static <T> T readLines(Readable readable, LineProcessor<T> processor) throws IOException {
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(processor);
        LineReader lineReader = new LineReader(readable);
        String line;
        do {
            line = lineReader.readLine();
            if (line == null) {
                break;
            }
        } while (processor.processLine(line));
        return processor.getResult();
    }

    @Deprecated
    public static <R extends Readable & Closeable, T> T readLines(InputSupplier<R> supplier, LineProcessor<T> callback) throws IOException {
        Preconditions.checkNotNull(supplier);
        Preconditions.checkNotNull(callback);
        Closer closer = Closer.create();
        try {
            T readLines = readLines((Readable) closer.register((Closeable) supplier.getInput()), (LineProcessor) callback);
            closer.close();
            return readLines;
        } catch (Throwable th) {
            closer.close();
        }
    }

    @Deprecated
    public static InputSupplier<Reader> join(Iterable<? extends InputSupplier<? extends Reader>> suppliers) {
        Preconditions.checkNotNull(suppliers);
        return asInputSupplier(CharSource.concat(Iterables.transform(suppliers, new C07721())));
    }

    @Deprecated
    public static InputSupplier<Reader> join(InputSupplier<? extends Reader>... suppliers) {
        return join(Arrays.asList(suppliers));
    }

    public static void skipFully(Reader reader, long n) throws IOException {
        Preconditions.checkNotNull(reader);
        while (n > 0) {
            long amt = reader.skip(n);
            if (amt != 0) {
                n -= amt;
            } else if (reader.read() == -1) {
                throw new EOFException();
            } else {
                n--;
            }
        }
    }

    public static Writer nullWriter() {
        return NullWriter.INSTANCE;
    }

    public static Writer asWriter(Appendable target) {
        if (target instanceof Writer) {
            return (Writer) target;
        }
        return new AppendableWriter(target);
    }

    static Reader asReader(Readable readable) {
        Preconditions.checkNotNull(readable);
        if (readable instanceof Reader) {
            return (Reader) readable;
        }
        return new C02812(readable);
    }

    @Deprecated
    public static CharSource asCharSource(InputSupplier<? extends Readable> supplier) {
        Preconditions.checkNotNull(supplier);
        return new C10343(supplier);
    }

    @Deprecated
    public static CharSink asCharSink(OutputSupplier<? extends Appendable> supplier) {
        Preconditions.checkNotNull(supplier);
        return new C10354(supplier);
    }

    static <R extends Reader> InputSupplier<R> asInputSupplier(CharSource source) {
        return (InputSupplier) Preconditions.checkNotNull(source);
    }

    static <W extends Writer> OutputSupplier<W> asOutputSupplier(CharSink sink) {
        return (OutputSupplier) Preconditions.checkNotNull(sink);
    }
}
