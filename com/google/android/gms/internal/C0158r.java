package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.google.android.gms.internal.r */
public final class C0158r {

    /* renamed from: com.google.android.gms.internal.r.a */
    public static final class C0157a {
        private final List<String> bY;
        private final Object bZ;

        private C0157a(Object obj) {
            this.bZ = C0159s.m517d(obj);
            this.bY = new ArrayList();
        }

        public C0157a m508a(String str, Object obj) {
            this.bY.add(((String) C0159s.m517d(str)) + "=" + String.valueOf(obj));
            return this;
        }

        public String toString() {
            StringBuilder append = new StringBuilder(100).append(this.bZ.getClass().getSimpleName()).append('{');
            int size = this.bY.size();
            for (int i = 0; i < size; i++) {
                append.append((String) this.bY.get(i));
                if (i < size - 1) {
                    append.append(", ");
                }
            }
            return append.append('}').toString();
        }
    }

    public static boolean m509a(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public static C0157a m510c(Object obj) {
        return new C0157a(null);
    }

    public static int hashCode(Object... objects) {
        return Arrays.hashCode(objects);
    }
}
