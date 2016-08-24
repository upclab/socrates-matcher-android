package com.google.common.hash;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.Nullable;

@Beta
public final class Hashing {
    private static final int GOOD_FAST_HASH_SEED;

    private static class Adler32Holder {
        static final HashFunction ADLER_32;

        private Adler32Holder() {
        }

        static {
            ADLER_32 = Hashing.checksumHashFunction(ChecksumType.ADLER_32, "Hashing.adler32()");
        }
    }

    private static class Crc32Holder {
        static final HashFunction CRC_32;

        private Crc32Holder() {
        }

        static {
            CRC_32 = Hashing.checksumHashFunction(ChecksumType.CRC_32, "Hashing.crc32()");
        }
    }

    private static final class LinearCongruentialGenerator {
        private long state;

        public LinearCongruentialGenerator(long seed) {
            this.state = seed;
        }

        public double nextDouble() {
            this.state = (2862933555777941757L * this.state) + 1;
            return ((double) (((int) (this.state >>> 33)) + 1)) / 2.147483648E9d;
        }
    }

    private static class Md5Holder {
        static final HashFunction MD5;

        private Md5Holder() {
        }

        static {
            MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");
        }
    }

    private static class Murmur3_128Holder {
        static final HashFunction GOOD_FAST_HASH_FUNCTION_128;
        static final HashFunction MURMUR3_128;

        private Murmur3_128Holder() {
        }

        static {
            MURMUR3_128 = new Murmur3_128HashFunction(0);
            GOOD_FAST_HASH_FUNCTION_128 = Hashing.murmur3_128(Hashing.GOOD_FAST_HASH_SEED);
        }
    }

    private static class Murmur3_32Holder {
        static final HashFunction GOOD_FAST_HASH_FUNCTION_32;
        static final HashFunction MURMUR3_32;

        private Murmur3_32Holder() {
        }

        static {
            MURMUR3_32 = new Murmur3_32HashFunction(0);
            GOOD_FAST_HASH_FUNCTION_32 = Hashing.murmur3_32(Hashing.GOOD_FAST_HASH_SEED);
        }
    }

    private static class Sha1Holder {
        static final HashFunction SHA_1;

        private Sha1Holder() {
        }

        static {
            SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");
        }
    }

    private static class Sha256Holder {
        static final HashFunction SHA_256;

        private Sha256Holder() {
        }

        static {
            SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");
        }
    }

    private static class Sha512Holder {
        static final HashFunction SHA_512;

        private Sha512Holder() {
        }

        static {
            SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");
        }
    }

    private static class SipHash24Holder {
        static final HashFunction SIP_HASH_24;

        private SipHash24Holder() {
        }

        static {
            SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);
        }
    }

    enum ChecksumType implements Supplier<Checksum> {
        CRC_32(32) {
            public Checksum get() {
                return new CRC32();
            }
        },
        ADLER_32(32) {
            public Checksum get() {
                return new Adler32();
            }
        };
        
        private final int bits;

        public abstract Checksum get();

        private ChecksumType(int bits) {
            this.bits = bits;
        }
    }

    @VisibleForTesting
    static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
        private final int bits;

        ConcatenatedHashFunction(HashFunction... functions) {
            super(functions);
            int bitSum = 0;
            for (HashFunction function : functions) {
                bitSum += function.bits();
            }
            this.bits = bitSum;
        }

        HashCode makeHash(Hasher[] hashers) {
            byte[] bytes = new byte[(this.bits / 8)];
            int i = 0;
            for (Hasher hasher : hashers) {
                HashCode newHash = hasher.hash();
                i += newHash.writeBytesTo(bytes, i, newHash.bits() / 8);
            }
            return HashCode.fromBytesNoCopy(bytes);
        }

        public int bits() {
            return this.bits;
        }

        public boolean equals(@Nullable Object object) {
            if (!(object instanceof ConcatenatedHashFunction)) {
                return false;
            }
            ConcatenatedHashFunction other = (ConcatenatedHashFunction) object;
            if (this.bits != other.bits || this.functions.length != other.functions.length) {
                return false;
            }
            for (int i = 0; i < this.functions.length; i++) {
                if (!this.functions[i].equals(other.functions[i])) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int hash = this.bits;
            for (HashFunction function : this.functions) {
                hash ^= function.hashCode();
            }
            return hash;
        }
    }

    public static HashFunction goodFastHash(int minimumBits) {
        int bits = checkPositiveAndMakeMultipleOf32(minimumBits);
        if (bits == 32) {
            return Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
        }
        if (bits <= AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER) {
            return Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        }
        int hashFunctionsNeeded = (bits + 127) / AccessibilityEventCompat.TYPE_VIEW_HOVER_ENTER;
        HashFunction[] hashFunctions = new HashFunction[hashFunctionsNeeded];
        hashFunctions[0] = Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        int seed = GOOD_FAST_HASH_SEED;
        for (int i = 1; i < hashFunctionsNeeded; i++) {
            seed += 1500450271;
            hashFunctions[i] = murmur3_128(seed);
        }
        return new ConcatenatedHashFunction(hashFunctions);
    }

    static {
        GOOD_FAST_HASH_SEED = (int) System.currentTimeMillis();
    }

    public static HashFunction murmur3_32(int seed) {
        return new Murmur3_32HashFunction(seed);
    }

    public static HashFunction murmur3_32() {
        return Murmur3_32Holder.MURMUR3_32;
    }

    public static HashFunction murmur3_128(int seed) {
        return new Murmur3_128HashFunction(seed);
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128Holder.MURMUR3_128;
    }

    public static HashFunction sipHash24() {
        return SipHash24Holder.SIP_HASH_24;
    }

    public static HashFunction sipHash24(long k0, long k1) {
        return new SipHashFunction(2, 4, k0, k1);
    }

    public static HashFunction md5() {
        return Md5Holder.MD5;
    }

    public static HashFunction sha1() {
        return Sha1Holder.SHA_1;
    }

    public static HashFunction sha256() {
        return Sha256Holder.SHA_256;
    }

    public static HashFunction sha512() {
        return Sha512Holder.SHA_512;
    }

    public static HashFunction crc32() {
        return Crc32Holder.CRC_32;
    }

    public static HashFunction adler32() {
        return Adler32Holder.ADLER_32;
    }

    private static HashFunction checksumHashFunction(ChecksumType type, String toString) {
        return new ChecksumHashFunction(type, type.bits, toString);
    }

    public static int consistentHash(HashCode hashCode, int buckets) {
        return consistentHash(hashCode.padToLong(), buckets);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int consistentHash(long r8, int r10) {
        /*
        r4 = 1;
        r5 = 0;
        if (r10 <= 0) goto L_0x0027;
    L_0x0004:
        r3 = r4;
    L_0x0005:
        r6 = "buckets must be positive: %s";
        r4 = new java.lang.Object[r4];
        r7 = java.lang.Integer.valueOf(r10);
        r4[r5] = r7;
        com.google.common.base.Preconditions.checkArgument(r3, r6, r4);
        r1 = new com.google.common.hash.Hashing$LinearCongruentialGenerator;
        r1.<init>(r8);
        r0 = 0;
    L_0x0018:
        r3 = r0 + 1;
        r3 = (double) r3;
        r5 = r1.nextDouble();
        r3 = r3 / r5;
        r2 = (int) r3;
        if (r2 < 0) goto L_0x0029;
    L_0x0023:
        if (r2 >= r10) goto L_0x0029;
    L_0x0025:
        r0 = r2;
        goto L_0x0018;
    L_0x0027:
        r3 = r5;
        goto L_0x0005;
    L_0x0029:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.Hashing.consistentHash(long, int):int");
    }

    public static HashCode combineOrdered(Iterable<HashCode> hashCodes) {
        Iterator<HashCode> iterator = hashCodes.iterator();
        Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] resultBytes = new byte[(((HashCode) iterator.next()).bits() / 8)];
        for (HashCode hashCode : hashCodes) {
            byte[] nextBytes = hashCode.asBytes();
            Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < nextBytes.length; i++) {
                resultBytes[i] = (byte) ((resultBytes[i] * 37) ^ nextBytes[i]);
            }
        }
        return HashCode.fromBytesNoCopy(resultBytes);
    }

    public static HashCode combineUnordered(Iterable<HashCode> hashCodes) {
        Iterator<HashCode> iterator = hashCodes.iterator();
        Preconditions.checkArgument(iterator.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] resultBytes = new byte[(((HashCode) iterator.next()).bits() / 8)];
        for (HashCode hashCode : hashCodes) {
            byte[] nextBytes = hashCode.asBytes();
            Preconditions.checkArgument(nextBytes.length == resultBytes.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < nextBytes.length; i++) {
                resultBytes[i] = (byte) (resultBytes[i] + nextBytes[i]);
            }
        }
        return HashCode.fromBytesNoCopy(resultBytes);
    }

    static int checkPositiveAndMakeMultipleOf32(int bits) {
        Preconditions.checkArgument(bits > 0, "Number of bits must be positive");
        return (bits + 31) & -32;
    }

    private Hashing() {
    }
}
