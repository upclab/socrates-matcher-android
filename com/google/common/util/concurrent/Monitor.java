package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class Monitor {
    @GuardedBy("lock")
    private Guard activeGuards;
    private final boolean fair;
    private final ReentrantLock lock;

    @Beta
    public static abstract class Guard {
        final Condition condition;
        final Monitor monitor;
        @GuardedBy("monitor.lock")
        Guard next;
        @GuardedBy("monitor.lock")
        int waiterCount;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor) {
            this.waiterCount = 0;
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor, "monitor");
            this.condition = monitor.lock.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean fair) {
        this.activeGuards = null;
        this.fair = fair;
        this.lock = new ReentrantLock(fair);
    }

    public void enter() {
        this.lock.lock();
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enter(long time, TimeUnit unit) {
        long timeoutNanos = unit.toNanos(time);
        ReentrantLock lock = this.lock;
        if (!this.fair && lock.tryLock()) {
            return true;
        }
        long deadline = System.nanoTime() + timeoutNanos;
        boolean interrupted = Thread.interrupted();
        while (true) {
            try {
                boolean tryLock = lock.tryLock(timeoutNanos, TimeUnit.NANOSECONDS);
                break;
            } catch (InterruptedException e) {
                interrupted = true;
                timeoutNanos = deadline - System.nanoTime();
            } catch (Throwable th) {
                if (1 != null) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        if (!interrupted) {
            return tryLock;
        }
        Thread.currentThread().interrupt();
        return tryLock;
    }

    public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        lock.lockInterruptibly();
        try {
            if (!guard.isSatisfied()) {
                await(guard, signalBeforeWaiting);
            }
            if (!true) {
                leave();
            }
        } catch (Throwable th) {
            if (!false) {
                leave();
            }
        }
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        lock.lock();
        try {
            if (!guard.isSatisfied()) {
                awaitUninterruptibly(guard, signalBeforeWaiting);
            }
            if (!true) {
                leave();
            }
        } catch (Throwable th) {
            if (!false) {
                leave();
            }
        }
    }

    public boolean enterWhen(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        long timeoutNanos = unit.toNanos(time);
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        boolean reentrant = lock.isHeldByCurrentThread();
        if (this.fair || !lock.tryLock()) {
            long deadline = System.nanoTime() + timeoutNanos;
            if (!lock.tryLock(time, unit)) {
                return false;
            }
            timeoutNanos = deadline - System.nanoTime();
        }
        try {
            boolean satisfied = guard.isSatisfied() || awaitNanos(guard, timeoutNanos, reentrant);
            if (!satisfied) {
                if (false && !reentrant) {
                    try {
                        signalNextWaiter();
                    } catch (Throwable th) {
                        lock.unlock();
                    }
                }
                lock.unlock();
            }
            return satisfied;
        } catch (Throwable th2) {
            lock.unlock();
        }
    }

    public boolean enterWhenUninterruptibly(Guard guard, long time, TimeUnit unit) {
        long timeoutNanos = unit.toNanos(time);
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        long deadline = System.nanoTime() + timeoutNanos;
        boolean signalBeforeWaiting = lock.isHeldByCurrentThread();
        boolean interrupted = Thread.interrupted();
        try {
            boolean z;
            if (this.fair || !lock.tryLock()) {
                boolean locked = false;
                do {
                    try {
                        locked = lock.tryLock(timeoutNanos, TimeUnit.NANOSECONDS);
                        if (!locked) {
                            z = false;
                            if (interrupted) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    } catch (InterruptedException e) {
                        interrupted = true;
                    }
                    timeoutNanos = deadline - System.nanoTime();
                } while (!locked);
                while (true) {
                    try {
                        break;
                    } catch (InterruptedException e2) {
                        interrupted = true;
                        signalBeforeWaiting = false;
                        timeoutNanos = deadline - System.nanoTime();
                    } catch (Throwable th) {
                        if (!false) {
                            lock.unlock();
                        }
                    }
                }
                z = guard.isSatisfied() || awaitNanos(guard, timeoutNanos, signalBeforeWaiting);
                if (!z) {
                    lock.unlock();
                }
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            } else {
                while (true) {
                    break;
                    if (!guard.isSatisfied()) {
                    }
                    if (z) {
                        lock.unlock();
                    }
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            return z;
        } catch (Throwable th2) {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        lock.lock();
        boolean satisfied = false;
        try {
            satisfied = guard.isSatisfied();
            return satisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        boolean satisfied = false;
        try {
            satisfied = guard.isSatisfied();
            return satisfied;
        } finally {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean enterIf(Guard guard, long time, TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(time, unit)) {
            return false;
        } else {
            boolean satisfied = false;
            try {
                satisfied = guard.isSatisfied();
                if (satisfied) {
                    return satisfied;
                }
                this.lock.unlock();
                return satisfied;
            } catch (Throwable th) {
                if (!satisfied) {
                    this.lock.unlock();
                }
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        if (!lock.tryLock(time, unit)) {
            return false;
        }
        boolean satisfied = false;
        try {
            satisfied = guard.isSatisfied();
            if (satisfied) {
                return satisfied;
            }
            lock.unlock();
            return satisfied;
        } catch (Throwable th) {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        ReentrantLock lock = this.lock;
        if (!lock.tryLock()) {
            return false;
        }
        boolean satisfied = false;
        try {
            satisfied = guard.isSatisfied();
            if (satisfied) {
                return satisfied;
            }
            lock.unlock();
            return satisfied;
        } catch (Throwable th) {
            if (!satisfied) {
                lock.unlock();
            }
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            await(guard, true);
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (((guard.monitor == this ? 1 : 0) & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            awaitUninterruptibly(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        int i;
        long timeoutNanos = unit.toNanos(time);
        if (guard.monitor == this) {
            i = 1;
        } else {
            i = 0;
        }
        if ((i & this.lock.isHeldByCurrentThread()) == 0) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied() || awaitNanos(guard, timeoutNanos, true)) {
            return true;
        } else {
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean waitForUninterruptibly(com.google.common.util.concurrent.Monitor.Guard r12, long r13, java.util.concurrent.TimeUnit r15) {
        /*
        r11 = this;
        r8 = 1;
        r5 = r15.toNanos(r13);
        r7 = r12.monitor;
        if (r7 != r11) goto L_0x0019;
    L_0x0009:
        r7 = r8;
    L_0x000a:
        r9 = r11.lock;
        r9 = r9.isHeldByCurrentThread();
        r7 = r7 & r9;
        if (r7 != 0) goto L_0x001b;
    L_0x0013:
        r7 = new java.lang.IllegalMonitorStateException;
        r7.<init>();
        throw r7;
    L_0x0019:
        r7 = 0;
        goto L_0x000a;
    L_0x001b:
        r7 = r12.isSatisfied();
        if (r7 == 0) goto L_0x0022;
    L_0x0021:
        return r8;
    L_0x0022:
        r4 = 1;
        r9 = java.lang.System.nanoTime();
        r0 = r9 + r5;
        r3 = java.lang.Thread.interrupted();
    L_0x002d:
        r8 = r11.awaitNanos(r12, r5, r4);	 Catch:{ InterruptedException -> 0x003b }
        if (r3 == 0) goto L_0x0021;
    L_0x0033:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
        goto L_0x0021;
    L_0x003b:
        r2 = move-exception;
        r3 = 1;
        r7 = r12.isSatisfied();	 Catch:{ all -> 0x0055 }
        if (r7 == 0) goto L_0x004d;
    L_0x0043:
        if (r3 == 0) goto L_0x0021;
    L_0x0045:
        r7 = java.lang.Thread.currentThread();
        r7.interrupt();
        goto L_0x0021;
    L_0x004d:
        r4 = 0;
        r9 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0055 }
        r5 = r0 - r9;
        goto L_0x002d;
    L_0x0055:
        r7 = move-exception;
        if (r3 == 0) goto L_0x005f;
    L_0x0058:
        r8 = java.lang.Thread.currentThread();
        r8.interrupt();
    L_0x005f:
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.waitForUninterruptibly(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void leave() {
        ReentrantLock lock = this.lock;
        try {
            if (lock.getHoldCount() == 1) {
                signalNextWaiter();
            }
            lock.unlock();
        } catch (Throwable th) {
            lock.unlock();
        }
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        }
        this.lock.lock();
        try {
            int i = guard.waiterCount;
            return i;
        } finally {
            this.lock.unlock();
        }
    }

    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
        }
    }

    @GuardedBy("lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable throwable) {
            signalAllWaiters();
            RuntimeException propagate = Throwables.propagate(throwable);
        }
    }

    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }

    @GuardedBy("lock")
    private void beginWaitingFor(Guard guard) {
        int waiters = guard.waiterCount;
        guard.waiterCount = waiters + 1;
        if (waiters == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy("lock")
    private void endWaitingFor(Guard guard) {
        int waiters = guard.waiterCount - 1;
        guard.waiterCount = waiters;
        if (waiters == 0) {
            Guard p = this.activeGuards;
            Guard pred = null;
            while (p != guard) {
                pred = p;
                p = p.next;
            }
            if (pred == null) {
                this.activeGuards = p.next;
            } else {
                pred.next = p.next;
            }
            p.next = null;
        }
    }

    @GuardedBy("lock")
    private void await(Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.await();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private void awaitUninterruptibly(Guard guard, boolean signalBeforeWaiting) {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (true) {
            try {
                guard.condition.awaitUninterruptibly();
                if (guard.isSatisfied()) {
                    break;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
    }

    @GuardedBy("lock")
    private boolean awaitNanos(Guard guard, long nanos, boolean signalBeforeWaiting) throws InterruptedException {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        while (nanos >= 0) {
            try {
                nanos = guard.condition.awaitNanos(nanos);
                if (guard.isSatisfied()) {
                    return true;
                }
            } finally {
                endWaitingFor(guard);
            }
        }
        endWaitingFor(guard);
        return false;
    }
}
