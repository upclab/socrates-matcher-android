package com.google.common.base;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class Platform {
    private static final ThreadLocal<char[]> DEST_TL;

    /* renamed from: com.google.common.base.Platform.1 */
    static class C02141 extends ThreadLocal<char[]> {
        C02141() {
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

    static long systemNanoTime() {
        return System.nanoTime();
    }

    static {
        DEST_TL = new C02141();
    }

    static CharMatcher precomputeCharMatcher(CharMatcher matcher) {
        return matcher.precomputedInternal();
    }
}
