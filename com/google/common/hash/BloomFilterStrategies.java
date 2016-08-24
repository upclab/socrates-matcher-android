package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;

enum BloomFilterStrategies implements Strategy {
    MURMUR128_MITZ_32 {
        public <T> boolean put(T object, Funnel<? super T> funnel, int numHashFunctions, BitArray bits) {
            long hash64 = Hashing.murmur3_128().hashObject(object, funnel).asLong();
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);
            boolean bitsChanged = false;
            for (int i = 1; i <= numHashFunctions; i++) {
                int nextHash = hash1 + (i * hash2);
                if (nextHash < 0) {
                    nextHash ^= -1;
                }
                bitsChanged |= bits.set(nextHash % bits.bitSize());
            }
            return bitsChanged;
        }

        public <T> boolean mightContain(T object, Funnel<? super T> funnel, int numHashFunctions, BitArray bits) {
            long hash64 = Hashing.murmur3_128().hashObject(object, funnel).asLong();
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);
            for (int i = 1; i <= numHashFunctions; i++) {
                int nextHash = hash1 + (i * hash2);
                if (nextHash < 0) {
                    nextHash ^= -1;
                }
                if (!bits.get(nextHash % bits.bitSize())) {
                    return false;
                }
            }
            return true;
        }
    };

    static class BitArray {
        int bitCount;
        final long[] data;

        BitArray(long bits) {
            this(new long[Ints.checkedCast(LongMath.divide(bits, 64, RoundingMode.CEILING))]);
        }

        BitArray(long[] data) {
            Preconditions.checkArgument(data.length > 0, "data length is zero!");
            this.data = data;
            int bitCount = 0;
            for (long value : data) {
                bitCount += Long.bitCount(value);
            }
            this.bitCount = bitCount;
        }

        boolean set(int index) {
            if (get(index)) {
                return false;
            }
            long[] jArr = this.data;
            int i = index >> 6;
            jArr[i] = jArr[i] | (1 << index);
            this.bitCount++;
            return true;
        }

        boolean get(int index) {
            return (this.data[index >> 6] & (1 << index)) != 0;
        }

        int bitSize() {
            return this.data.length * 64;
        }

        int bitCount() {
            return this.bitCount;
        }

        BitArray copy() {
            return new BitArray((long[]) this.data.clone());
        }

        void putAll(BitArray array) {
            boolean z;
            if (this.data.length == array.data.length) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "BitArrays must be of equal length (%s != %s)", Integer.valueOf(this.data.length), Integer.valueOf(array.data.length));
            this.bitCount = 0;
            for (int i = 0; i < this.data.length; i++) {
                long[] jArr = this.data;
                jArr[i] = jArr[i] | array.data[i];
                this.bitCount += Long.bitCount(this.data[i]);
            }
        }

        public boolean equals(Object o) {
            if (!(o instanceof BitArray)) {
                return false;
            }
            return Arrays.equals(this.data, ((BitArray) o).data);
        }

        public int hashCode() {
            return Arrays.hashCode(this.data);
        }
    }
}
