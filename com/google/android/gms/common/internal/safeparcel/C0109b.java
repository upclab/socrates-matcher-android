package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* renamed from: com.google.android.gms.common.internal.safeparcel.b */
public class C0109b {
    private static int m104A(Parcel parcel, int i) {
        parcel.writeInt(-65536 | i);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void m105B(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        int i2 = dataPosition - i;
        parcel.setDataPosition(i - 4);
        parcel.writeInt(i2);
        parcel.setDataPosition(dataPosition);
    }

    public static void m106C(Parcel parcel, int i) {
        C0109b.m105B(parcel, i);
    }

    public static void m107a(Parcel parcel, int i, byte b) {
        C0109b.m123b(parcel, i, 4);
        parcel.writeInt(b);
    }

    public static void m108a(Parcel parcel, int i, double d) {
        C0109b.m123b(parcel, i, 8);
        parcel.writeDouble(d);
    }

    public static void m109a(Parcel parcel, int i, float f) {
        C0109b.m123b(parcel, i, 4);
        parcel.writeFloat(f);
    }

    public static void m110a(Parcel parcel, int i, long j) {
        C0109b.m123b(parcel, i, 8);
        parcel.writeLong(j);
    }

    public static void m111a(Parcel parcel, int i, Bundle bundle, boolean z) {
        if (bundle != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeBundle(bundle);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m112a(Parcel parcel, int i, IBinder iBinder, boolean z) {
        if (iBinder != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeStrongBinder(iBinder);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m113a(Parcel parcel, int i, Parcel parcel2, boolean z) {
        if (parcel2 != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m114a(Parcel parcel, int i, Parcelable parcelable, int i2, boolean z) {
        if (parcelable != null) {
            int A = C0109b.m104A(parcel, i);
            parcelable.writeToParcel(parcel, i2);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m115a(Parcel parcel, int i, String str, boolean z) {
        if (str != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeString(str);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m116a(Parcel parcel, int i, List<String> list, boolean z) {
        if (list != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeStringList(list);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m117a(Parcel parcel, int i, short s) {
        C0109b.m123b(parcel, i, 4);
        parcel.writeInt(s);
    }

    public static void m118a(Parcel parcel, int i, boolean z) {
        C0109b.m123b(parcel, i, 4);
        parcel.writeInt(z ? 1 : 0);
    }

    public static void m119a(Parcel parcel, int i, byte[] bArr, boolean z) {
        if (bArr != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeByteArray(bArr);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static <T extends Parcelable> void m120a(Parcel parcel, int i, T[] tArr, int i2, boolean z) {
        if (tArr != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeInt(r3);
            for (Parcelable parcelable : tArr) {
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    C0109b.m122a(parcel, parcelable, i2);
                }
            }
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m121a(Parcel parcel, int i, String[] strArr, boolean z) {
        if (strArr != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeStringArray(strArr);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    private static <T extends Parcelable> void m122a(Parcel parcel, T t, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int dataPosition2 = parcel.dataPosition();
        t.writeToParcel(parcel, i);
        int dataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition3 - dataPosition2);
        parcel.setDataPosition(dataPosition3);
    }

    private static void m123b(Parcel parcel, int i, int i2) {
        if (i2 >= 65535) {
            parcel.writeInt(-65536 | i);
            parcel.writeInt(i2);
            return;
        }
        parcel.writeInt((i2 << 16) | i);
    }

    public static <T extends Parcelable> void m124b(Parcel parcel, int i, List<T> list, boolean z) {
        if (list != null) {
            int A = C0109b.m104A(parcel, i);
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                Parcelable parcelable = (Parcelable) list.get(i2);
                if (parcelable == null) {
                    parcel.writeInt(0);
                } else {
                    C0109b.m122a(parcel, parcelable, 0);
                }
            }
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static void m125c(Parcel parcel, int i, int i2) {
        C0109b.m123b(parcel, i, 4);
        parcel.writeInt(i2);
    }

    public static void m126c(Parcel parcel, int i, List list, boolean z) {
        if (list != null) {
            int A = C0109b.m104A(parcel, i);
            parcel.writeList(list);
            C0109b.m105B(parcel, A);
        } else if (z) {
            C0109b.m123b(parcel, i, 0);
        }
    }

    public static int m127d(Parcel parcel) {
        return C0109b.m104A(parcel, 20293);
    }
}
