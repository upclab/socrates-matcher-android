package com.google.common.hash;

import com.google.common.base.Preconditions;
import java.nio.charset.Charset;

abstract class AbstractCompositeHashFunction extends AbstractStreamingHashFunction {
    private static final long serialVersionUID = 0;
    final HashFunction[] functions;

    /* renamed from: com.google.common.hash.AbstractCompositeHashFunction.1 */
    class C10251 implements Hasher {
        final /* synthetic */ Hasher[] val$hashers;

        C10251(Hasher[] hasherArr) {
            this.val$hashers = hasherArr;
        }

        public Hasher putByte(byte b) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putByte(b);
            }
            return this;
        }

        public Hasher putBytes(byte[] bytes) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putBytes(bytes);
            }
            return this;
        }

        public Hasher putBytes(byte[] bytes, int off, int len) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putBytes(bytes, off, len);
            }
            return this;
        }

        public Hasher putShort(short s) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putShort(s);
            }
            return this;
        }

        public Hasher putInt(int i) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putInt(i);
            }
            return this;
        }

        public Hasher putLong(long l) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putLong(l);
            }
            return this;
        }

        public Hasher putFloat(float f) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putFloat(f);
            }
            return this;
        }

        public Hasher putDouble(double d) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putDouble(d);
            }
            return this;
        }

        public Hasher putBoolean(boolean b) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putBoolean(b);
            }
            return this;
        }

        public Hasher putChar(char c) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putChar(c);
            }
            return this;
        }

        public Hasher putUnencodedChars(CharSequence chars) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putUnencodedChars(chars);
            }
            return this;
        }

        public Hasher putString(CharSequence chars, Charset charset) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putString(chars, charset);
            }
            return this;
        }

        public <T> Hasher putObject(T instance, Funnel<? super T> funnel) {
            for (Hasher hasher : this.val$hashers) {
                hasher.putObject(instance, funnel);
            }
            return this;
        }

        public HashCode hash() {
            return AbstractCompositeHashFunction.this.makeHash(this.val$hashers);
        }
    }

    abstract HashCode makeHash(Hasher[] hasherArr);

    AbstractCompositeHashFunction(HashFunction... functions) {
        for (HashFunction function : functions) {
            Preconditions.checkNotNull(function);
        }
        this.functions = functions;
    }

    public Hasher newHasher() {
        Hasher[] hashers = new Hasher[this.functions.length];
        for (int i = 0; i < hashers.length; i++) {
            hashers[i] = this.functions[i].newHasher();
        }
        return new C10251(hashers);
    }
}
