package com.google.common.util.concurrent;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public final class ExecutionList {
    @VisibleForTesting
    static final Logger log;
    @GuardedBy("this")
    private boolean executed;
    @GuardedBy("this")
    private RunnableExecutorPair runnables;

    private static final class RunnableExecutorPair {
        final Executor executor;
        @Nullable
        RunnableExecutorPair next;
        final Runnable runnable;

        RunnableExecutorPair(Runnable runnable, Executor executor, RunnableExecutorPair next) {
            this.runnable = runnable;
            this.executor = executor;
            this.next = next;
        }
    }

    static {
        log = Logger.getLogger(ExecutionList.class.getName());
    }

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (this.executed) {
                executeListener(runnable, executor);
                return;
            }
            this.runnables = new RunnableExecutorPair(runnable, executor, this.runnables);
        }
    }

    public void execute() {
        synchronized (this) {
            if (this.executed) {
                return;
            }
            this.executed = true;
            RunnableExecutorPair list = this.runnables;
            this.runnables = null;
            RunnableExecutorPair reversedList = null;
            while (list != null) {
                RunnableExecutorPair tmp = list;
                list = list.next;
                tmp.next = reversedList;
                reversedList = tmp;
            }
            while (reversedList != null) {
                executeListener(reversedList.runnable, reversedList.executor);
                reversedList = reversedList.next;
            }
        }
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, e);
        }
    }
}
