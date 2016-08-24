package org.joda.time.tz;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import org.joda.time.DateTimeZone;

public class CachedDateTimeZone extends DateTimeZone {
    private static final int cInfoCacheMask;
    private static final long serialVersionUID = 5472298452022250685L;
    private final Info[] iInfoCache;
    private final DateTimeZone iZone;

    private static final class Info {
        private String iNameKey;
        Info iNextInfo;
        private int iOffset;
        public final long iPeriodStart;
        private int iStandardOffset;
        public final DateTimeZone iZoneRef;

        Info(DateTimeZone dateTimeZone, long j) {
            this.iOffset = Integer.MIN_VALUE;
            this.iStandardOffset = Integer.MIN_VALUE;
            this.iPeriodStart = j;
            this.iZoneRef = dateTimeZone;
        }

        public String getNameKey(long j) {
            if (this.iNextInfo != null && j >= this.iNextInfo.iPeriodStart) {
                return this.iNextInfo.getNameKey(j);
            }
            if (this.iNameKey == null) {
                this.iNameKey = this.iZoneRef.getNameKey(this.iPeriodStart);
            }
            return this.iNameKey;
        }

        public int getOffset(long j) {
            if (this.iNextInfo != null && j >= this.iNextInfo.iPeriodStart) {
                return this.iNextInfo.getOffset(j);
            }
            if (this.iOffset == Integer.MIN_VALUE) {
                this.iOffset = this.iZoneRef.getOffset(this.iPeriodStart);
            }
            return this.iOffset;
        }

        public int getStandardOffset(long j) {
            if (this.iNextInfo != null && j >= this.iNextInfo.iPeriodStart) {
                return this.iNextInfo.getStandardOffset(j);
            }
            if (this.iStandardOffset == Integer.MIN_VALUE) {
                this.iStandardOffset = this.iZoneRef.getStandardOffset(this.iPeriodStart);
            }
            return this.iStandardOffset;
        }
    }

    static {
        Integer integer;
        int i;
        try {
            integer = Integer.getInteger("org.joda.time.tz.CachedDateTimeZone.size");
        } catch (SecurityException e) {
            integer = null;
        }
        if (integer == null) {
            i = AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_START;
        } else {
            i = cInfoCacheMask;
            for (int intValue = integer.intValue() - 1; intValue > 0; intValue >>= 1) {
                i++;
            }
            i = 1 << i;
        }
        cInfoCacheMask = i - 1;
    }

    public static CachedDateTimeZone forZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone instanceof CachedDateTimeZone) {
            return (CachedDateTimeZone) dateTimeZone;
        }
        return new CachedDateTimeZone(dateTimeZone);
    }

    private CachedDateTimeZone(DateTimeZone dateTimeZone) {
        super(dateTimeZone.getID());
        this.iInfoCache = new Info[(cInfoCacheMask + 1)];
        this.iZone = dateTimeZone;
    }

    public DateTimeZone getUncachedZone() {
        return this.iZone;
    }

    public String getNameKey(long j) {
        return getInfo(j).getNameKey(j);
    }

    public int getOffset(long j) {
        return getInfo(j).getOffset(j);
    }

    public int getStandardOffset(long j) {
        return getInfo(j).getStandardOffset(j);
    }

    public boolean isFixed() {
        return this.iZone.isFixed();
    }

    public long nextTransition(long j) {
        return this.iZone.nextTransition(j);
    }

    public long previousTransition(long j) {
        return this.iZone.previousTransition(j);
    }

    public int hashCode() {
        return this.iZone.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CachedDateTimeZone) {
            return this.iZone.equals(((CachedDateTimeZone) obj).iZone);
        }
        return false;
    }

    private Info getInfo(long j) {
        int i = (int) (j >> 32);
        Info[] infoArr = this.iInfoCache;
        int i2 = i & cInfoCacheMask;
        Info info = infoArr[i2];
        if (info != null && ((int) (info.iPeriodStart >> 32)) == i) {
            return info;
        }
        info = createInfo(j);
        infoArr[i2] = info;
        return info;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.joda.time.tz.CachedDateTimeZone.Info createInfo(long r9) {
        /*
        r8 = this;
        r0 = -4294967296; // 0xffffffff00000000 float:0.0 double:NaN;
        r2 = r9 & r0;
        r1 = new org.joda.time.tz.CachedDateTimeZone$Info;
        r0 = r8.iZone;
        r1.<init>(r0, r2);
        r4 = 4294967295; // 0xffffffff float:NaN double:2.1219957905E-314;
        r6 = r2 | r4;
        r0 = r1;
    L_0x0016:
        r4 = r8.iZone;
        r4 = r4.nextTransition(r2);
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x0024;
    L_0x0020:
        r2 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r2 <= 0) goto L_0x0025;
    L_0x0024:
        return r1;
    L_0x0025:
        r2 = new org.joda.time.tz.CachedDateTimeZone$Info;
        r3 = r8.iZone;
        r2.<init>(r3, r4);
        r0.iNextInfo = r2;
        r0 = r2;
        r2 = r4;
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.tz.CachedDateTimeZone.createInfo(long):org.joda.time.tz.CachedDateTimeZone$Info");
    }
}
