package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public final class Callables {

    /* renamed from: com.google.common.util.concurrent.Callables.1 */
    static class C03041 implements Callable<T> {
        final /* synthetic */ Object val$value;

        C03041(Object obj) {
            this.val$value = obj;
        }

        public T call() {
            return this.val$value;
        }
    }

    /* renamed from: com.google.common.util.concurrent.Callables.2 */
    static class C03052 implements Callable<T> {
        final /* synthetic */ Callable val$callable;
        final /* synthetic */ Supplier val$nameSupplier;

        C03052(Supplier supplier, Callable callable) {
            this.val$nameSupplier = supplier;
            this.val$callable = callable;
        }

        public T call() throws Exception {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName((String) this.val$nameSupplier.get(), currentThread);
            try {
                T call = this.val$callable.call();
                return call;
            } finally {
                if (restoreName) {
                    Callables.trySetName(oldName, currentThread);
                }
            }
        }
    }

    /* renamed from: com.google.common.util.concurrent.Callables.3 */
    static class C03063 implements Runnable {
        final /* synthetic */ Supplier val$nameSupplier;
        final /* synthetic */ Runnable val$task;

        C03063(Supplier supplier, Runnable runnable) {
            this.val$nameSupplier = supplier;
            this.val$task = runnable;
        }

        public void run() {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();
            boolean restoreName = Callables.trySetName((String) this.val$nameSupplier.get(), currentThread);
            try {
                this.val$task.run();
                if (restoreName) {
                    Callables.trySetName(oldName, currentThread);
                }
            } catch (Throwable th) {
                if (restoreName) {
                    Callables.trySetName(oldName, currentThread);
                }
            }
        }
    }

    private Callables() {
    }

    public static <T> Callable<T> returning(@Nullable T value) {
        return new C03041(value);
    }

    static <T> Callable<T> threadRenaming(Callable<T> callable, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(nameSupplier);
        Preconditions.checkNotNull(callable);
        return new C03052(nameSupplier, callable);
    }

    static Runnable threadRenaming(Runnable task, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(nameSupplier);
        Preconditions.checkNotNull(task);
        return new C03063(nameSupplier, task);
    }

    private static boolean trySetName(String threadName, Thread currentThread) {
        try {
            currentThread.setName(threadName);
            return true;
        } catch (SecurityException e) {
            return false;
        }
    }
}
