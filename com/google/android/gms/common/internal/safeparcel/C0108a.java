package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.google.android.gms.common.internal.safeparcel.a */
public class C0108a {

    /* renamed from: com.google.android.gms.common.internal.safeparcel.a.a */
    public static class C0107a extends RuntimeException {
        public C0107a(String str, Parcel parcel) {
            super(str + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }

    public static int m70a(Parcel parcel, int i) {
        return (i & -65536) != -65536 ? (i >> 16) & 65535 : parcel.readInt();
    }

    public static <T extends Parcelable> T m71a(Parcel parcel, int i, Creator<T> creator) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Parcelable parcelable = (Parcelable) creator.createFromParcel(parcel);
        parcel.setDataPosition(a + dataPosition);
        return parcelable;
    }

    private static void m72a(Parcel parcel, int i, int i2) {
        int a = C0108a.m70a(parcel, i);
        if (a != i2) {
            throw new C0107a("Expected size " + i2 + " got " + a + " (0x" + Integer.toHexString(a) + ")", parcel);
        }
    }

    public static void m73a(Parcel parcel, int i, List list, ClassLoader classLoader) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a != 0) {
            parcel.readList(list, classLoader);
            parcel.setDataPosition(a + dataPosition);
        }
    }

    public static int m74b(Parcel parcel) {
        return parcel.readInt();
    }

    public static void m75b(Parcel parcel, int i) {
        parcel.setDataPosition(C0108a.m70a(parcel, i) + parcel.dataPosition());
    }

    public static <T> T[] m76b(Parcel parcel, int i, Creator<T> creator) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        T[] createTypedArray = parcel.createTypedArray(creator);
        parcel.setDataPosition(a + dataPosition);
        return createTypedArray;
    }

    public static int m77c(Parcel parcel) {
        int b = C0108a.m74b(parcel);
        int a = C0108a.m70a(parcel, b);
        int dataPosition = parcel.dataPosition();
        if (C0108a.m89m(b) != 20293) {
            throw new C0107a("Expected object header. Got 0x" + Integer.toHexString(b), parcel);
        }
        b = dataPosition + a;
        if (b >= dataPosition && b <= parcel.dataSize()) {
            return b;
        }
        throw new C0107a("Size read is invalid start=" + dataPosition + " end=" + b, parcel);
    }

    public static <T> ArrayList<T> m78c(Parcel parcel, int i, Creator<T> creator) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        ArrayList<T> createTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(a + dataPosition);
        return createTypedArrayList;
    }

    public static boolean m79c(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 4);
        return parcel.readInt() != 0;
    }

    public static byte m80d(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 4);
        return (byte) parcel.readInt();
    }

    public static short m81e(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 4);
        return (short) parcel.readInt();
    }

    public static int m82f(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 4);
        return parcel.readInt();
    }

    public static long m83g(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 8);
        return parcel.readLong();
    }

    public static BigInteger m84h(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(a + dataPosition);
        return new BigInteger(createByteArray);
    }

    public static float m85i(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 4);
        return parcel.readFloat();
    }

    public static double m86j(Parcel parcel, int i) {
        C0108a.m72a(parcel, i, 8);
        return parcel.readDouble();
    }

    public static BigDecimal m87k(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        int readInt = parcel.readInt();
        parcel.setDataPosition(a + dataPosition);
        return new BigDecimal(new BigInteger(createByteArray), readInt);
    }

    public static String m88l(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        String readString = parcel.readString();
        parcel.setDataPosition(a + dataPosition);
        return readString;
    }

    public static int m89m(int i) {
        return 65535 & i;
    }

    public static IBinder m90m(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(a + dataPosition);
        return readStrongBinder;
    }

    public static Bundle m91n(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Bundle readBundle = parcel.readBundle();
        parcel.setDataPosition(a + dataPosition);
        return readBundle;
    }

    public static byte[] m92o(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        byte[] createByteArray = parcel.createByteArray();
        parcel.setDataPosition(a + dataPosition);
        return createByteArray;
    }

    public static boolean[] m93p(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        boolean[] createBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(a + dataPosition);
        return createBooleanArray;
    }

    public static int[] m94q(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int[] createIntArray = parcel.createIntArray();
        parcel.setDataPosition(a + dataPosition);
        return createIntArray;
    }

    public static long[] m95r(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        long[] createLongArray = parcel.createLongArray();
        parcel.setDataPosition(a + dataPosition);
        return createLongArray;
    }

    public static BigInteger[] m96s(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            bigIntegerArr[i2] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(dataPosition + a);
        return bigIntegerArr;
    }

    public static float[] m97t(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        float[] createFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(a + dataPosition);
        return createFloatArray;
    }

    public static double[] m98u(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        double[] createDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(a + dataPosition);
        return createDoubleArray;
    }

    public static BigDecimal[] m99v(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            byte[] createByteArray = parcel.createByteArray();
            bigDecimalArr[i2] = new BigDecimal(new BigInteger(createByteArray), parcel.readInt());
        }
        parcel.setDataPosition(dataPosition + a);
        return bigDecimalArr;
    }

    public static String[] m100w(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        String[] createStringArray = parcel.createStringArray();
        parcel.setDataPosition(a + dataPosition);
        return createStringArray;
    }

    public static ArrayList<String> m101x(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        ArrayList<String> createStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(a + dataPosition);
        return createStringArrayList;
    }

    public static Parcel m102y(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        Parcel obtain = Parcel.obtain();
        obtain.appendFrom(parcel, dataPosition, a);
        parcel.setDataPosition(a + dataPosition);
        return obtain;
    }

    public static Parcel[] m103z(Parcel parcel, int i) {
        int a = C0108a.m70a(parcel, i);
        int dataPosition = parcel.dataPosition();
        if (a == 0) {
            return null;
        }
        int readInt = parcel.readInt();
        Parcel[] parcelArr = new Parcel[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 0) {
                int dataPosition2 = parcel.dataPosition();
                Parcel obtain = Parcel.obtain();
                obtain.appendFrom(parcel, dataPosition2, readInt2);
                parcelArr[i2] = obtain;
                parcel.setDataPosition(readInt2 + dataPosition2);
            } else {
                parcelArr[i2] = null;
            }
        }
        parcel.setDataPosition(dataPosition + a);
        return parcelArr;
    }
}
