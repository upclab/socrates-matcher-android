package org.apache.commons.lang3.math;

import java.math.BigInteger;

public final class Fraction extends Number implements Comparable<Fraction> {
    public static final Fraction FOUR_FIFTHS;
    public static final Fraction ONE;
    public static final Fraction ONE_FIFTH;
    public static final Fraction ONE_HALF;
    public static final Fraction ONE_QUARTER;
    public static final Fraction ONE_THIRD;
    public static final Fraction THREE_FIFTHS;
    public static final Fraction THREE_QUARTERS;
    public static final Fraction TWO_FIFTHS;
    public static final Fraction TWO_QUARTERS;
    public static final Fraction TWO_THIRDS;
    public static final Fraction ZERO;
    private static final long serialVersionUID = 65382027393090L;
    private final int denominator;
    private transient int hashCode;
    private final int numerator;
    private transient String toProperString;
    private transient String toString;

    static {
        ZERO = new Fraction(0, 1);
        ONE = new Fraction(1, 1);
        ONE_HALF = new Fraction(1, 2);
        ONE_THIRD = new Fraction(1, 3);
        TWO_THIRDS = new Fraction(2, 3);
        ONE_QUARTER = new Fraction(1, 4);
        TWO_QUARTERS = new Fraction(2, 4);
        THREE_QUARTERS = new Fraction(3, 4);
        ONE_FIFTH = new Fraction(1, 5);
        TWO_FIFTHS = new Fraction(2, 5);
        THREE_FIFTHS = new Fraction(3, 5);
        FOUR_FIFTHS = new Fraction(4, 5);
    }

    private Fraction(int numerator, int denominator) {
        this.hashCode = 0;
        this.toString = null;
        this.toProperString = null;
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public static Fraction getFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (denominator < 0) {
            if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            numerator = -numerator;
            denominator = -denominator;
        }
        return new Fraction(numerator, denominator);
    }

    public static Fraction getFraction(int whole, int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        } else if (denominator < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        } else if (numerator < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        } else {
            long numeratorValue;
            if (whole < 0) {
                numeratorValue = (((long) whole) * ((long) denominator)) - ((long) numerator);
            } else {
                numeratorValue = (((long) whole) * ((long) denominator)) + ((long) numerator);
            }
            if (numeratorValue >= -2147483648L && numeratorValue <= 2147483647L) {
                return new Fraction((int) numeratorValue, denominator);
            }
            throw new ArithmeticException("Numerator too large to represent as an Integer.");
        }
    }

    public static Fraction getReducedFraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        } else if (numerator == 0) {
            return ZERO;
        } else {
            if (denominator == Integer.MIN_VALUE && (numerator & 1) == 0) {
                numerator /= 2;
                denominator /= 2;
            }
            if (denominator < 0) {
                if (numerator == Integer.MIN_VALUE || denominator == Integer.MIN_VALUE) {
                    throw new ArithmeticException("overflow: can't negate");
                }
                numerator = -numerator;
                denominator = -denominator;
            }
            int gcd = greatestCommonDivisor(numerator, denominator);
            return new Fraction(numerator / gcd, denominator / gcd);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.apache.commons.lang3.math.Fraction getFraction(double r31) {
        /*
        r27 = 0;
        r27 = (r31 > r27 ? 1 : (r31 == r27 ? 0 : -1));
        if (r27 >= 0) goto L_0x0023;
    L_0x0006:
        r17 = -1;
    L_0x0008:
        r31 = java.lang.Math.abs(r31);
        r27 = 4746794007244308480; // 0x41dfffffffc00000 float:NaN double:2.147483647E9;
        r27 = (r31 > r27 ? 1 : (r31 == r27 ? 0 : -1));
        if (r27 > 0) goto L_0x001b;
    L_0x0015:
        r27 = java.lang.Double.isNaN(r31);
        if (r27 == 0) goto L_0x0026;
    L_0x001b:
        r27 = new java.lang.ArithmeticException;
        r28 = "The value must not be greater than Integer.MAX_VALUE or NaN";
        r27.<init>(r28);
        throw r27;
    L_0x0023:
        r17 = 1;
        goto L_0x0008;
    L_0x0026:
        r0 = r31;
        r0 = (int) r0;
        r18 = r0;
        r0 = r18;
        r0 = (double) r0;
        r27 = r0;
        r31 = r31 - r27;
        r14 = 0;
        r8 = 1;
        r15 = 1;
        r9 = 0;
        r16 = 0;
        r10 = 0;
        r0 = r31;
        r2 = (int) r0;
        r3 = 0;
        r19 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r21 = 0;
        r0 = (double) r2;
        r27 = r0;
        r23 = r31 - r27;
        r25 = 0;
        r6 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r13 = 1;
    L_0x004e:
        r4 = r6;
        r27 = r19 / r23;
        r0 = r27;
        r3 = (int) r0;
        r21 = r23;
        r0 = (double) r3;
        r27 = r0;
        r27 = r27 * r23;
        r25 = r19 - r27;
        r27 = r2 * r15;
        r16 = r27 + r14;
        r27 = r2 * r9;
        r10 = r27 + r8;
        r0 = r16;
        r0 = (double) r0;
        r27 = r0;
        r0 = (double) r10;
        r29 = r0;
        r11 = r27 / r29;
        r27 = r31 - r11;
        r6 = java.lang.Math.abs(r27);
        r2 = r3;
        r19 = r21;
        r23 = r25;
        r14 = r15;
        r8 = r9;
        r15 = r16;
        r9 = r10;
        r13 = r13 + 1;
        r27 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r27 <= 0) goto L_0x0093;
    L_0x0085:
        r27 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = r27;
        if (r10 > r0) goto L_0x0093;
    L_0x008b:
        if (r10 <= 0) goto L_0x0093;
    L_0x008d:
        r27 = 25;
        r0 = r27;
        if (r13 < r0) goto L_0x004e;
    L_0x0093:
        r27 = 25;
        r0 = r27;
        if (r13 != r0) goto L_0x00a1;
    L_0x0099:
        r27 = new java.lang.ArithmeticException;
        r28 = "Unable to convert double to fraction";
        r27.<init>(r28);
        throw r27;
    L_0x00a1:
        r27 = r18 * r8;
        r27 = r27 + r14;
        r27 = r27 * r17;
        r0 = r27;
        r27 = getReducedFraction(r0, r8);
        return r27;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.math.Fraction.getFraction(double):org.apache.commons.lang3.math.Fraction");
    }

    public static Fraction getFraction(String str) {
        if (str == null) {
            throw new IllegalArgumentException("The string must not be null");
        } else if (str.indexOf(46) >= 0) {
            return getFraction(Double.parseDouble(str));
        } else {
            int pos = str.indexOf(32);
            if (pos > 0) {
                int whole = Integer.parseInt(str.substring(0, pos));
                str = str.substring(pos + 1);
                pos = str.indexOf(47);
                if (pos >= 0) {
                    return getFraction(whole, Integer.parseInt(str.substring(0, pos)), Integer.parseInt(str.substring(pos + 1)));
                }
                throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
            }
            pos = str.indexOf(47);
            if (pos < 0) {
                return getFraction(Integer.parseInt(str), 1);
            }
            return getFraction(Integer.parseInt(str.substring(0, pos)), Integer.parseInt(str.substring(pos + 1)));
        }
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getProperNumerator() {
        return Math.abs(this.numerator % this.denominator);
    }

    public int getProperWhole() {
        return this.numerator / this.denominator;
    }

    public int intValue() {
        return this.numerator / this.denominator;
    }

    public long longValue() {
        return ((long) this.numerator) / ((long) this.denominator);
    }

    public float floatValue() {
        return ((float) this.numerator) / ((float) this.denominator);
    }

    public double doubleValue() {
        return ((double) this.numerator) / ((double) this.denominator);
    }

    public Fraction reduce() {
        if (this.numerator != 0) {
            int gcd = greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
            return gcd != 1 ? getFraction(this.numerator / gcd, this.denominator / gcd) : this;
        } else if (equals(ZERO)) {
            return this;
        } else {
            return ZERO;
        }
    }

    public Fraction invert() {
        if (this.numerator == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        } else if (this.numerator == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow: can't negate numerator");
        } else if (this.numerator < 0) {
            return new Fraction(-this.denominator, -this.numerator);
        } else {
            return new Fraction(this.denominator, this.numerator);
        }
    }

    public Fraction negate() {
        if (this.numerator != Integer.MIN_VALUE) {
            return new Fraction(-this.numerator, this.denominator);
        }
        throw new ArithmeticException("overflow: too large to negate");
    }

    public Fraction abs() {
        return this.numerator >= 0 ? this : negate();
    }

    public Fraction pow(int power) {
        if (power == 1) {
            return this;
        }
        if (power == 0) {
            return ONE;
        }
        if (power >= 0) {
            Fraction f = multiplyBy(this);
            if (power % 2 == 0) {
                return f.pow(power / 2);
            }
            return f.pow(power / 2).multiplyBy(this);
        } else if (power == Integer.MIN_VALUE) {
            return invert().pow(2).pow(-(power / 2));
        } else {
            return invert().pow(-power);
        }
    }

    private static int greatestCommonDivisor(int u, int v) {
        if (u == 0 || v == 0) {
            if (u != Integer.MIN_VALUE && v != Integer.MIN_VALUE) {
                return Math.abs(u) + Math.abs(v);
            }
            throw new ArithmeticException("overflow: gcd is 2^31");
        } else if (Math.abs(u) == 1 || Math.abs(v) == 1) {
            return 1;
        } else {
            if (u > 0) {
                u = -u;
            }
            if (v > 0) {
                v = -v;
            }
            int k = 0;
            while ((u & 1) == 0 && (v & 1) == 0 && k < 31) {
                u /= 2;
                v /= 2;
                k++;
            }
            if (k == 31) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            int t = (u & 1) == 1 ? v : -(u / 2);
            while (true) {
                if ((t & 1) == 0) {
                    t /= 2;
                } else {
                    if (t > 0) {
                        u = -t;
                    } else {
                        v = t;
                    }
                    t = (v - u) / 2;
                    if (t == 0) {
                        return (1 << k) * (-u);
                    }
                }
            }
        }
    }

    private static int mulAndCheck(int x, int y) {
        long m = ((long) x) * ((long) y);
        if (m >= -2147483648L && m <= 2147483647L) {
            return (int) m;
        }
        throw new ArithmeticException("overflow: mul");
    }

    private static int mulPosAndCheck(int x, int y) {
        long m = ((long) x) * ((long) y);
        if (m <= 2147483647L) {
            return (int) m;
        }
        throw new ArithmeticException("overflow: mulPos");
    }

    private static int addAndCheck(int x, int y) {
        long s = ((long) x) + ((long) y);
        if (s >= -2147483648L && s <= 2147483647L) {
            return (int) s;
        }
        throw new ArithmeticException("overflow: add");
    }

    private static int subAndCheck(int x, int y) {
        long s = ((long) x) - ((long) y);
        if (s >= -2147483648L && s <= 2147483647L) {
            return (int) s;
        }
        throw new ArithmeticException("overflow: add");
    }

    public Fraction add(Fraction fraction) {
        return addSub(fraction, true);
    }

    public Fraction subtract(Fraction fraction) {
        return addSub(fraction, false);
    }

    private Fraction addSub(Fraction fraction, boolean isAdd) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        } else if (this.numerator == 0) {
            if (isAdd) {
                return fraction;
            }
            return fraction.negate();
        } else if (fraction.numerator == 0) {
            return this;
        } else {
            int d1 = greatestCommonDivisor(this.denominator, fraction.denominator);
            if (d1 == 1) {
                int uvp = mulAndCheck(this.numerator, fraction.denominator);
                int upv = mulAndCheck(fraction.numerator, this.denominator);
                return new Fraction(isAdd ? addAndCheck(uvp, upv) : subAndCheck(uvp, upv), mulPosAndCheck(this.denominator, fraction.denominator));
            }
            BigInteger uvp2 = BigInteger.valueOf((long) this.numerator).multiply(BigInteger.valueOf((long) (fraction.denominator / d1)));
            BigInteger upv2 = BigInteger.valueOf((long) fraction.numerator).multiply(BigInteger.valueOf((long) (this.denominator / d1)));
            BigInteger t = isAdd ? uvp2.add(upv2) : uvp2.subtract(upv2);
            int tmodd1 = t.mod(BigInteger.valueOf((long) d1)).intValue();
            int d2 = tmodd1 == 0 ? d1 : greatestCommonDivisor(tmodd1, d1);
            BigInteger w = t.divide(BigInteger.valueOf((long) d2));
            if (w.bitLength() <= 31) {
                return new Fraction(w.intValue(), mulPosAndCheck(this.denominator / d1, fraction.denominator / d2));
            }
            throw new ArithmeticException("overflow: numerator too large after multiply");
        }
    }

    public Fraction multiplyBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        } else if (this.numerator == 0 || fraction.numerator == 0) {
            return ZERO;
        } else {
            int d1 = greatestCommonDivisor(this.numerator, fraction.denominator);
            int d2 = greatestCommonDivisor(fraction.numerator, this.denominator);
            return getReducedFraction(mulAndCheck(this.numerator / d1, fraction.numerator / d2), mulPosAndCheck(this.denominator / d2, fraction.denominator / d1));
        }
    }

    public Fraction divideBy(Fraction fraction) {
        if (fraction == null) {
            throw new IllegalArgumentException("The fraction must not be null");
        } else if (fraction.numerator != 0) {
            return multiplyBy(fraction.invert());
        } else {
            throw new ArithmeticException("The fraction to divide by must not be zero");
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction other = (Fraction) obj;
        if (getNumerator() == other.getNumerator() && getDenominator() == other.getDenominator()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((getNumerator() + 629) * 37) + getDenominator();
        }
        return this.hashCode;
    }

    public int compareTo(Fraction other) {
        if (this == other) {
            return 0;
        }
        if (this.numerator == other.numerator && this.denominator == other.denominator) {
            return 0;
        }
        long first = ((long) this.numerator) * ((long) other.denominator);
        long second = ((long) other.numerator) * ((long) this.denominator);
        if (first == second) {
            return 0;
        }
        if (first < second) {
            return -1;
        }
        return 1;
    }

    public String toString() {
        if (this.toString == null) {
            this.toString = getNumerator() + '/' + getDenominator();
        }
        return this.toString;
    }

    public String toProperString() {
        if (this.toProperString == null) {
            if (this.numerator == 0) {
                this.toProperString = "0";
            } else if (this.numerator == this.denominator) {
                this.toProperString = "1";
            } else if (this.numerator == this.denominator * -1) {
                this.toProperString = "-1";
            } else {
                if ((this.numerator > 0 ? -this.numerator : this.numerator) < (-this.denominator)) {
                    int properNumerator = getProperNumerator();
                    if (properNumerator == 0) {
                        this.toProperString = Integer.toString(getProperWhole());
                    } else {
                        this.toProperString = getProperWhole() + ' ' + properNumerator + '/' + getDenominator();
                    }
                } else {
                    this.toProperString = getNumerator() + '/' + getDenominator();
                }
            }
        }
        return this.toProperString;
    }
}
