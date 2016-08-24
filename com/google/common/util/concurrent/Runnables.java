package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Runnables {
    private static final Runnable EMPTY_RUNNABLE;

    /* renamed from: com.google.common.util.concurrent.Runnables.1 */
    static class C03181 implements Runnable {
        C03181() {
        }

        public void run() {
        }
    }

    static {
        EMPTY_RUNNABLE = new C03181();
    }

    public static Runnable doNothing() {
        return EMPTY_RUNNABLE;
    }

    private Runnables() {
    }
}
