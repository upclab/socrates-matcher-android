package com.google.common.escape;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class Platform {
    private static final ThreadLocal<char[]> DEST_TL;

    /* renamed from: com.google.common.escape.Platform.1 */
    static class C02691 extends ThreadLocal<char[]> {
        C02691() {
        }

        protected char[] initialValue() {
            return new char[AccessibilityEventCompat.TYPE_TOUCH_EXPLORATION_GESTURE_END];
        }
    }

    private Platform() {
    }

    static char[] charBufferFromThreadLocal() {
        return (char[]) DEST_TL.get();
    }

    static {
        DEST_TL = new C02691();
    }
}
