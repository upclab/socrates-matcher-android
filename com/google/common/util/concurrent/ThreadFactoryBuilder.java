package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
    private ThreadFactory backingThreadFactory;
    private Boolean daemon;
    private String nameFormat;
    private Integer priority;
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    /* renamed from: com.google.common.util.concurrent.ThreadFactoryBuilder.1 */
    static class C03261 implements ThreadFactory {
        final /* synthetic */ ThreadFactory val$backingThreadFactory;
        final /* synthetic */ AtomicLong val$count;
        final /* synthetic */ Boolean val$daemon;
        final /* synthetic */ String val$nameFormat;
        final /* synthetic */ Integer val$priority;
        final /* synthetic */ UncaughtExceptionHandler val$uncaughtExceptionHandler;

        C03261(ThreadFactory threadFactory, String str, AtomicLong atomicLong, Boolean bool, Integer num, UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.val$backingThreadFactory = threadFactory;
            this.val$nameFormat = str;
            this.val$count = atomicLong;
            this.val$daemon = bool;
            this.val$priority = num;
            this.val$uncaughtExceptionHandler = uncaughtExceptionHandler;
        }

        public Thread newThread(Runnable runnable) {
            Thread thread = this.val$backingThreadFactory.newThread(runnable);
            if (this.val$nameFormat != null) {
                thread.setName(String.format(this.val$nameFormat, new Object[]{Long.valueOf(this.val$count.getAndIncrement())}));
            }
            if (this.val$daemon != null) {
                thread.setDaemon(this.val$daemon.booleanValue());
            }
            if (this.val$priority != null) {
                thread.setPriority(this.val$priority.intValue());
            }
            if (this.val$uncaughtExceptionHandler != null) {
                thread.setUncaughtExceptionHandler(this.val$uncaughtExceptionHandler);
            }
            return thread;
        }
    }

    public ThreadFactoryBuilder() {
        this.nameFormat = null;
        this.daemon = null;
        this.priority = null;
        this.uncaughtExceptionHandler = null;
        this.backingThreadFactory = null;
    }

    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        String.format(nameFormat, new Object[]{Integer.valueOf(0)});
        this.nameFormat = nameFormat;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = Boolean.valueOf(daemon);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority) {
        boolean z;
        Preconditions.checkArgument(priority >= 1, "Thread priority (%s) must be >= %s", Integer.valueOf(priority), Integer.valueOf(1));
        if (priority <= 10) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Thread priority (%s) must be <= %s", Integer.valueOf(priority), Integer.valueOf(10));
        this.priority = Integer.valueOf(priority);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = (UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = (ThreadFactory) Preconditions.checkNotNull(backingThreadFactory);
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        String nameFormat = builder.nameFormat;
        return new C03261(builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory(), nameFormat, nameFormat != null ? new AtomicLong(0) : null, builder.daemon, builder.priority, builder.uncaughtExceptionHandler);
    }
}
