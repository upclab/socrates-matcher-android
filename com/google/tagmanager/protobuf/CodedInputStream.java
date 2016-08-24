package com.google.tagmanager.protobuf;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.tagmanager.protobuf.MessageLite.Builder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.CharEncoding;
import org.joda.time.MutableDateTime;

public final class CodedInputStream {
    private static final int BUFFER_SIZE = 4096;
    private static final int DEFAULT_RECURSION_LIMIT = 64;
    private static final int DEFAULT_SIZE_LIMIT = 67108864;
    private final byte[] buffer;
    private final boolean bufferIsImmutable;
    private int bufferPos;
    private int bufferSize;
    private int bufferSizeAfterLimit;
    private int currentLimit;
    private boolean enableAliasing;
    private final InputStream input;
    private int lastTag;
    private int recursionDepth;
    private int recursionLimit;
    private RefillCallback refillCallback;
    private int sizeLimit;
    private int totalBytesRetired;

    private interface RefillCallback {
        void onRefill();
    }

    private class SkippedDataSink implements RefillCallback {
        private ByteArrayOutputStream byteArrayStream;
        private int lastPos;

        private SkippedDataSink() {
            this.lastPos = CodedInputStream.this.bufferPos;
        }

        public void onRefill() {
            if (this.byteArrayStream == null) {
                this.byteArrayStream = new ByteArrayOutputStream();
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            this.lastPos = 0;
        }

        ByteBuffer getSkippedData() {
            if (this.byteArrayStream == null) {
                return ByteBuffer.wrap(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos - this.lastPos);
            }
            this.byteArrayStream.write(CodedInputStream.this.buffer, this.lastPos, CodedInputStream.this.bufferPos);
            return ByteBuffer.wrap(this.byteArrayStream.toByteArray());
        }
    }

    public static CodedInputStream newInstance(InputStream input) {
        return new CodedInputStream(input);
    }

    public static CodedInputStream newInstance(byte[] buf) {
        return newInstance(buf, 0, buf.length);
    }

    public static CodedInputStream newInstance(byte[] buf, int off, int len) {
        CodedInputStream result = new CodedInputStream(buf, off, len);
        try {
            result.pushLimit(len);
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static CodedInputStream newInstance(ByteBuffer buf) {
        if (buf.hasArray()) {
            return newInstance(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
        }
        ByteBuffer temp = buf.duplicate();
        byte[] buffer = new byte[temp.remaining()];
        temp.get(buffer);
        return newInstance(buffer);
    }

    static CodedInputStream newInstance(LiteralByteString byteString) {
        CodedInputStream result = new CodedInputStream(byteString);
        try {
            result.pushLimit(byteString.size());
            return result;
        } catch (InvalidProtocolBufferException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public int readTag() throws IOException {
        if (isAtEnd()) {
            this.lastTag = 0;
            return 0;
        }
        this.lastTag = readRawVarint32();
        if (WireFormat.getTagFieldNumber(this.lastTag) != 0) {
            return this.lastTag;
        }
        throw InvalidProtocolBufferException.invalidTag();
    }

    public void checkLastTagWas(int value) throws InvalidProtocolBufferException {
        if (this.lastTag != value) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    public int getLastTag() {
        return this.lastTag;
    }

    public boolean skipField(int tag) throws IOException {
        switch (WireFormat.getTagWireType(tag)) {
            case MutableDateTime.ROUND_NONE /*0*/:
                skipRawVarint();
                return true;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                skipRawBytes(8);
                return true;
            case Value.STRING_FIELD_NUMBER /*2*/:
                skipRawBytes(readRawVarint32());
                return true;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                skipMessage();
                checkLastTagWas(WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4));
                return true;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return false;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                skipRawBytes(4);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public boolean skipField(int tag, CodedOutputStream output) throws IOException {
        long value;
        switch (WireFormat.getTagWireType(tag)) {
            case MutableDateTime.ROUND_NONE /*0*/:
                value = readInt64();
                output.writeRawVarint32(tag);
                output.writeUInt64NoTag(value);
                return true;
            case Value.TYPE_FIELD_NUMBER /*1*/:
                value = readRawLittleEndian64();
                output.writeRawVarint32(tag);
                output.writeFixed64NoTag(value);
                return true;
            case Value.STRING_FIELD_NUMBER /*2*/:
                ByteString value2 = readBytes();
                output.writeRawVarint32(tag);
                output.writeBytesNoTag(value2);
                return true;
            case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                output.writeRawVarint32(tag);
                skipMessage(output);
                int endtag = WireFormat.makeTag(WireFormat.getTagFieldNumber(tag), 4);
                checkLastTagWas(endtag);
                output.writeRawVarint32(endtag);
                return true;
            case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                return false;
            case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                int value3 = readRawLittleEndian32();
                output.writeRawVarint32(tag);
                output.writeFixed32NoTag(value3);
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public void skipMessage() throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag));
    }

    public void skipMessage(CodedOutputStream output) throws IOException {
        int tag;
        do {
            tag = readTag();
            if (tag == 0) {
                return;
            }
        } while (skipField(tag, output));
    }

    public void mergeToMessage(MutableMessageLite message) throws IOException {
        SkippedDataSink dataSink = new SkippedDataSink();
        this.refillCallback = dataSink;
        skipMessage();
        this.refillCallback = null;
        ByteBuffer data = dataSink.getSkippedData();
        if (!message.mergeFrom(data.array(), data.position(), data.remaining())) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readRawLittleEndian64());
    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readRawLittleEndian32());
    }

    public long readUInt64() throws IOException {
        return readRawVarint64();
    }

    public long readInt64() throws IOException {
        return readRawVarint64();
    }

    public int readInt32() throws IOException {
        return readRawVarint32();
    }

    public long readFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public boolean readBool() throws IOException {
        return readRawVarint32() != 0;
    }

    public String readString() throws IOException {
        int size = readRawVarint32();
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return new String(readRawBytes(size), CharEncoding.UTF_8);
        }
        String result = new String(this.buffer, this.bufferPos, size, CharEncoding.UTF_8);
        this.bufferPos += size;
        return result;
    }

    public String readStringRequireUtf8() throws IOException {
        ByteString bs;
        int size = readRawVarint32();
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            bs = new LiteralByteString(readRawBytes(size));
        } else {
            bs = ByteString.copyFrom(this.buffer, this.bufferPos, size);
            this.bufferPos += size;
        }
        if (bs.isValidUtf8()) {
            return bs.toStringUtf8();
        }
        throw InvalidProtocolBufferException.invalidUtf8();
    }

    public void readGroup(int fieldNumber, Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
    }

    public void readGroup(int fieldNumber, MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        message.mergeFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
    }

    public <T extends MessageLite> T readGroup(int fieldNumber, Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        this.recursionDepth++;
        MessageLite result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(WireFormat.makeTag(fieldNumber, 4));
        this.recursionDepth--;
        return result;
    }

    @Deprecated
    public void readUnknownGroup(int fieldNumber, Builder builder) throws IOException {
        readGroup(fieldNumber, builder, null);
    }

    public void readMessage(Builder builder, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        builder.mergeFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
    }

    public void readMessage(MutableMessageLite message, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        message.mergeFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
    }

    public <T extends MessageLite> T readMessage(Parser<T> parser, ExtensionRegistryLite extensionRegistry) throws IOException {
        int length = readRawVarint32();
        if (this.recursionDepth >= this.recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
        int oldLimit = pushLimit(length);
        this.recursionDepth++;
        MessageLite result = (MessageLite) parser.parsePartialFrom(this, extensionRegistry);
        checkLastTagWas(0);
        this.recursionDepth--;
        popLimit(oldLimit);
        return result;
    }

    public ByteString readBytes() throws IOException {
        int size = readRawVarint32();
        if (size == 0) {
            return ByteString.EMPTY;
        }
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return new LiteralByteString(readRawBytes(size));
        }
        ByteString boundedByteString = (this.bufferIsImmutable && this.enableAliasing) ? new BoundedByteString(this.buffer, this.bufferPos, size) : ByteString.copyFrom(this.buffer, this.bufferPos, size);
        this.bufferPos += size;
        return boundedByteString;
    }

    public byte[] readByteArray() throws IOException {
        int size = readRawVarint32();
        if (size == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        }
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return readRawBytes(size);
        }
        byte[] result = Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size);
        this.bufferPos += size;
        return result;
    }

    public ByteBuffer readByteBuffer() throws IOException {
        int size = readRawVarint32();
        if (size == 0) {
            return Internal.EMPTY_BYTE_BUFFER;
        }
        if (size > this.bufferSize - this.bufferPos || size <= 0) {
            return ByteBuffer.wrap(readRawBytes(size));
        }
        ByteBuffer slice = (this.input == null && !this.bufferIsImmutable && this.enableAliasing) ? ByteBuffer.wrap(this.buffer, this.bufferPos, size).slice() : ByteBuffer.wrap(Arrays.copyOfRange(this.buffer, this.bufferPos, this.bufferPos + size));
        this.bufferPos += size;
        return slice;
    }

    public int readUInt32() throws IOException {
        return readRawVarint32();
    }

    public int readEnum() throws IOException {
        return readRawVarint32();
    }

    public int readSFixed32() throws IOException {
        return readRawLittleEndian32();
    }

    public long readSFixed64() throws IOException {
        return readRawLittleEndian64();
    }

    public int readSInt32() throws IOException {
        return decodeZigZag32(readRawVarint32());
    }

    public long readSInt64() throws IOException {
        return decodeZigZag64(readRawVarint64());
    }

    public int readRawVarint32() throws IOException {
        byte tmp = readRawByte();
        if (tmp >= null) {
            return tmp;
        }
        int result = tmp & 127;
        tmp = readRawByte();
        if (tmp >= null) {
            return result | (tmp << 7);
        }
        result |= (tmp & 127) << 7;
        tmp = readRawByte();
        if (tmp >= null) {
            return result | (tmp << 14);
        }
        result |= (tmp & 127) << 14;
        tmp = readRawByte();
        if (tmp >= null) {
            return result | (tmp << 21);
        }
        result |= (tmp & 127) << 21;
        tmp = readRawByte();
        result |= tmp << 28;
        if (tmp >= null) {
            return result;
        }
        for (int i = 0; i < 5; i++) {
            if (readRawByte() >= null) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    private void skipRawVarint() throws IOException {
        int i = 0;
        while (i < 10) {
            if (readRawByte() < null) {
                i++;
            } else {
                return;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    static int readRawVarint32(InputStream input) throws IOException {
        int firstByte = input.read();
        if (firstByte != -1) {
            return readRawVarint32(firstByte, input);
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    public static int readRawVarint32(int firstByte, InputStream input) throws IOException {
        if ((firstByte & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == 0) {
            return firstByte;
        }
        int result = firstByte & 127;
        int offset = 7;
        while (offset < 32) {
            int b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
            result |= (b & 127) << offset;
            if ((b & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == 0) {
                return result;
            }
            offset += 7;
        }
        while (offset < DEFAULT_RECURSION_LIMIT) {
            b = input.read();
            if (b == -1) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } else if ((b & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == 0) {
                return result;
            } else {
                offset += 7;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public long readRawVarint64() throws IOException {
        long result = 0;
        for (int shift = 0; shift < DEFAULT_RECURSION_LIMIT; shift += 7) {
            byte b = readRawByte();
            result |= ((long) (b & 127)) << shift;
            if ((b & AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) == 0) {
                return result;
            }
        }
        throw InvalidProtocolBufferException.malformedVarint();
    }

    public int readRawLittleEndian32() throws IOException {
        return (((readRawByte() & MotionEventCompat.ACTION_MASK) | ((readRawByte() & MotionEventCompat.ACTION_MASK) << 8)) | ((readRawByte() & MotionEventCompat.ACTION_MASK) << 16)) | ((readRawByte() & MotionEventCompat.ACTION_MASK) << 24);
    }

    public long readRawLittleEndian64() throws IOException {
        return (((((((((long) readRawByte()) & 255) | ((((long) readRawByte()) & 255) << 8)) | ((((long) readRawByte()) & 255) << 16)) | ((((long) readRawByte()) & 255) << 24)) | ((((long) readRawByte()) & 255) << 32)) | ((((long) readRawByte()) & 255) << 40)) | ((((long) readRawByte()) & 255) << 48)) | ((((long) readRawByte()) & 255) << 56);
    }

    public static int decodeZigZag32(int n) {
        return (n >>> 1) ^ (-(n & 1));
    }

    public static long decodeZigZag64(long n) {
        return (n >>> 1) ^ (-(1 & n));
    }

    private CodedInputStream(byte[] buffer, int off, int len) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = DEFAULT_RECURSION_LIMIT;
        this.sizeLimit = DEFAULT_SIZE_LIMIT;
        this.refillCallback = null;
        this.buffer = buffer;
        this.bufferSize = off + len;
        this.bufferPos = off;
        this.totalBytesRetired = -off;
        this.input = null;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(InputStream input) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = DEFAULT_RECURSION_LIMIT;
        this.sizeLimit = DEFAULT_SIZE_LIMIT;
        this.refillCallback = null;
        this.buffer = new byte[BUFFER_SIZE];
        this.bufferSize = 0;
        this.bufferPos = 0;
        this.totalBytesRetired = 0;
        this.input = input;
        this.bufferIsImmutable = false;
    }

    private CodedInputStream(LiteralByteString byteString) {
        this.enableAliasing = false;
        this.currentLimit = Integer.MAX_VALUE;
        this.recursionLimit = DEFAULT_RECURSION_LIMIT;
        this.sizeLimit = DEFAULT_SIZE_LIMIT;
        this.refillCallback = null;
        this.buffer = byteString.bytes;
        this.bufferPos = byteString.getOffsetIntoBytes();
        this.bufferSize = byteString.getOffsetIntoBytes() + byteString.size();
        this.totalBytesRetired = -this.bufferPos;
        this.input = null;
        this.bufferIsImmutable = true;
    }

    public void enableAliasing(boolean enabled) {
        this.enableAliasing = enabled;
    }

    public int setRecursionLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Recursion limit cannot be negative: " + limit);
        }
        int oldLimit = this.recursionLimit;
        this.recursionLimit = limit;
        return oldLimit;
    }

    public int setSizeLimit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Size limit cannot be negative: " + limit);
        }
        int oldLimit = this.sizeLimit;
        this.sizeLimit = limit;
        return oldLimit;
    }

    public void resetSizeCounter() {
        this.totalBytesRetired = -this.bufferPos;
    }

    public int pushLimit(int byteLimit) throws InvalidProtocolBufferException {
        if (byteLimit < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        }
        byteLimit += this.totalBytesRetired + this.bufferPos;
        int oldLimit = this.currentLimit;
        if (byteLimit > oldLimit) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
        this.currentLimit = byteLimit;
        recomputeBufferSizeAfterLimit();
        return oldLimit;
    }

    private void recomputeBufferSizeAfterLimit() {
        this.bufferSize += this.bufferSizeAfterLimit;
        int bufferEnd = this.totalBytesRetired + this.bufferSize;
        if (bufferEnd > this.currentLimit) {
            this.bufferSizeAfterLimit = bufferEnd - this.currentLimit;
            this.bufferSize -= this.bufferSizeAfterLimit;
            return;
        }
        this.bufferSizeAfterLimit = 0;
    }

    public void popLimit(int oldLimit) {
        this.currentLimit = oldLimit;
        recomputeBufferSizeAfterLimit();
    }

    public int getBytesUntilLimit() {
        if (this.currentLimit == Integer.MAX_VALUE) {
            return -1;
        }
        return this.currentLimit - (this.totalBytesRetired + this.bufferPos);
    }

    public boolean isAtEnd() throws IOException {
        return this.bufferPos == this.bufferSize && !refillBuffer(false);
    }

    public int getTotalBytesRead() {
        return this.totalBytesRetired + this.bufferPos;
    }

    private boolean refillBuffer(boolean mustSucceed) throws IOException {
        if (this.bufferPos < this.bufferSize) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        } else if (this.totalBytesRetired + this.bufferSize != this.currentLimit) {
            if (this.refillCallback != null) {
                this.refillCallback.onRefill();
            }
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            this.bufferSize = this.input == null ? -1 : this.input.read(this.buffer);
            if (this.bufferSize == 0 || this.bufferSize < -1) {
                throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.bufferSize + "\nThe InputStream implementation is buggy.");
            } else if (this.bufferSize == -1) {
                this.bufferSize = 0;
                if (!mustSucceed) {
                    return false;
                }
                throw InvalidProtocolBufferException.truncatedMessage();
            } else {
                recomputeBufferSizeAfterLimit();
                int totalBytesRead = (this.totalBytesRetired + this.bufferSize) + this.bufferSizeAfterLimit;
                if (totalBytesRead <= this.sizeLimit && totalBytesRead >= 0) {
                    return true;
                }
                throw InvalidProtocolBufferException.sizeLimitExceeded();
            }
        } else if (!mustSucceed) {
            return false;
        } else {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    public byte readRawByte() throws IOException {
        if (this.bufferPos == this.bufferSize) {
            refillBuffer(true);
        }
        byte[] bArr = this.buffer;
        int i = this.bufferPos;
        this.bufferPos = i + 1;
        return bArr[i];
    }

    public byte[] readRawBytes(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if ((this.totalBytesRetired + this.bufferPos) + size > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (size <= this.bufferSize - this.bufferPos) {
            bytes = new byte[size];
            System.arraycopy(this.buffer, this.bufferPos, bytes, 0, size);
            this.bufferPos += size;
            return bytes;
        } else if (size < BUFFER_SIZE) {
            bytes = new byte[size];
            pos = this.bufferSize - this.bufferPos;
            System.arraycopy(this.buffer, this.bufferPos, bytes, 0, pos);
            this.bufferPos = this.bufferSize;
            refillBuffer(true);
            while (size - pos > this.bufferSize) {
                System.arraycopy(this.buffer, 0, bytes, pos, this.bufferSize);
                pos += this.bufferSize;
                this.bufferPos = this.bufferSize;
                refillBuffer(true);
            }
            System.arraycopy(this.buffer, 0, bytes, pos, size - pos);
            this.bufferPos = size - pos;
            return bytes;
        } else {
            byte[] chunk;
            int originalBufferPos = this.bufferPos;
            int originalBufferSize = this.bufferSize;
            this.totalBytesRetired += this.bufferSize;
            this.bufferPos = 0;
            this.bufferSize = 0;
            int sizeLeft = size - (originalBufferSize - originalBufferPos);
            List<byte[]> chunks = new ArrayList();
            while (sizeLeft > 0) {
                chunk = new byte[Math.min(sizeLeft, BUFFER_SIZE)];
                pos = 0;
                while (pos < chunk.length) {
                    int n = this.input == null ? -1 : this.input.read(chunk, pos, chunk.length - pos);
                    if (n == -1) {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                    this.totalBytesRetired += n;
                    pos += n;
                }
                sizeLeft -= chunk.length;
                chunks.add(chunk);
            }
            bytes = new byte[size];
            pos = originalBufferSize - originalBufferPos;
            System.arraycopy(this.buffer, originalBufferPos, bytes, 0, pos);
            for (byte[] chunk2 : chunks) {
                System.arraycopy(chunk2, 0, bytes, pos, chunk2.length);
                pos += chunk2.length;
            }
            return bytes;
        }
    }

    public void skipRawBytes(int size) throws IOException {
        if (size < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if ((this.totalBytesRetired + this.bufferPos) + size > this.currentLimit) {
            skipRawBytes((this.currentLimit - this.totalBytesRetired) - this.bufferPos);
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (size <= this.bufferSize - this.bufferPos) {
            this.bufferPos += size;
        } else {
            int pos = this.bufferSize - this.bufferPos;
            this.bufferPos = this.bufferSize;
            refillBuffer(true);
            while (size - pos > this.bufferSize) {
                pos += this.bufferSize;
                this.bufferPos = this.bufferSize;
                refillBuffer(true);
            }
            this.bufferPos = size - pos;
        }
    }
}
