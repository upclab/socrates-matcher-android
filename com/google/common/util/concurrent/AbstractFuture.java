package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import javax.annotation.Nullable;

public abstract class AbstractFuture<V> implements ListenableFuture<V> {
    private final ExecutionList executionList;
    private final Sync<V> sync;

    static final class Sync<V> extends AbstractQueuedSynchronizer {
        static final int CANCELLED = 4;
        static final int COMPLETED = 2;
        static final int COMPLETING = 1;
        static final int INTERRUPTED = 8;
        static final int RUNNING = 0;
        private static final long serialVersionUID = 0;
        private Throwable exception;
        private V value;

        Sync() {
        }

        protected int tryAcquireShared(int ignored) {
            if (isDone()) {
                return COMPLETING;
            }
            return -1;
        }

        protected boolean tryReleaseShared(int finalState) {
            setState(finalState);
            return true;
        }

        V get(long nanos) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
            if (tryAcquireSharedNanos(-1, nanos)) {
                return getValue();
            }
            throw new TimeoutException("Timeout waiting for task.");
        }

        V get() throws CancellationException, ExecutionException, InterruptedException {
            acquireSharedInterruptibly(-1);
            return getValue();
        }

        private V getValue() throws CancellationException, ExecutionException {
            int state = getState();
            switch (state) {
                case COMPLETED /*2*/:
                    if (this.exception == null) {
                        return this.value;
                    }
                    throw new ExecutionException(this.exception);
                case CANCELLED /*4*/:
                case INTERRUPTED /*8*/:
                    throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
                default:
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
            }
        }

        boolean isDone() {
            return (getState() & 14) != 0;
        }

        boolean isCancelled() {
            return (getState() & 12) != 0;
        }

        boolean wasInterrupted() {
            return getState() == INTERRUPTED;
        }

        boolean set(@Nullable V v) {
            return complete(v, null, COMPLETED);
        }

        boolean setException(Throwable t) {
            return complete(null, t, COMPLETED);
        }

        boolean cancel(boolean interrupt) {
            return complete(null, null, interrupt ? INTERRUPTED : CANCELLED);
        }

        private boolean complete(@Nullable V v, @Nullable Throwable t, int finalState) {
            boolean doCompletion = compareAndSetState(0, COMPLETING);
            if (doCompletion) {
                this.value = v;
                if ((finalState & 12) != 0) {
                    t = new CancellationException("Future.cancel() was called.");
                }
                this.exception = t;
                releaseShared(finalState);
            } else if (getState() == COMPLETING) {
                acquireShared(-1);
            }
            return doCompletion;
        }
    }

    protected AbstractFuture() {
        this.sync = new Sync();
        this.executionList = new ExecutionList();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        return this.sync.get(unit.toNanos(timeout));
    }

    public V get() throws InterruptedException, ExecutionException {
        return this.sync.get();
    }

    public boolean isDone() {
        return this.sync.isDone();
    }

    public boolean isCancelled() {
        return this.sync.isCancelled();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.sync.cancel(mayInterruptIfRunning)) {
            return false;
        }
        this.executionList.execute();
        if (mayInterruptIfRunning) {
            interruptTask();
        }
        return true;
    }

    protected void interruptTask() {
    }

    protected final boolean wasInterrupted() {
        return this.sync.wasInterrupted();
    }

    public void addListener(Runnable listener, Executor exec) {
        this.executionList.add(listener, exec);
    }

    protected boolean set(@Nullable V value) {
        boolean result = this.sync.set(value);
        if (result) {
            this.executionList.execute();
        }
        return result;
    }

    protected boolean setException(Throwable throwable) {
        boolean result = this.sync.setException((Throwable) Preconditions.checkNotNull(throwable));
        if (result) {
            this.executionList.execute();
        }
        return result;
    }

    static final CancellationException cancellationExceptionWithCause(@Nullable String message, @Nullable Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }
}
