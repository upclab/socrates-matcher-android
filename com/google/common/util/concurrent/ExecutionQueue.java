package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class ExecutionQueue {
    private static final Logger logger;
    private final ReentrantLock lock;
    private final ConcurrentLinkedQueue<RunnableExecutorPair> queuedListeners;

    private final class RunnableExecutorPair implements Runnable {
        private final Executor executor;
        @GuardedBy("lock")
        private boolean hasBeenExecuted;
        private final Runnable runnable;

        RunnableExecutorPair(Runnable runnable, Executor executor) {
            this.hasBeenExecuted = false;
            this.runnable = (Runnable) Preconditions.checkNotNull(runnable);
            this.executor = (Executor) Preconditions.checkNotNull(executor);
        }

        private void submit() {
            ExecutionQueue.this.lock.lock();
            try {
                if (!this.hasBeenExecuted) {
                    this.executor.execute(this);
                }
            } catch (Exception e) {
                ExecutionQueue.logger.log(Level.SEVERE, "Exception while executing listener " + this.runnable + " with executor " + this.executor, e);
            } catch (Throwable th) {
                if (ExecutionQueue.this.lock.isHeldByCurrentThread()) {
                    this.hasBeenExecuted = true;
                    ExecutionQueue.this.lock.unlock();
                }
            }
            if (ExecutionQueue.this.lock.isHeldByCurrentThread()) {
                this.hasBeenExecuted = true;
                ExecutionQueue.this.lock.unlock();
            }
        }

        public final void run() {
            if (ExecutionQueue.this.lock.isHeldByCurrentThread()) {
                this.hasBeenExecuted = true;
                ExecutionQueue.this.lock.unlock();
            }
            this.runnable.run();
        }
    }

    ExecutionQueue() {
        this.queuedListeners = Queues.newConcurrentLinkedQueue();
        this.lock = new ReentrantLock();
    }

    static {
        logger = Logger.getLogger(ExecutionQueue.class.getName());
    }

    void add(Runnable runnable, Executor executor) {
        this.queuedListeners.add(new RunnableExecutorPair(runnable, executor));
    }

    void execute() {
        Iterator<RunnableExecutorPair> iterator = this.queuedListeners.iterator();
        while (iterator.hasNext()) {
            ((RunnableExecutorPair) iterator.next()).submit();
            iterator.remove();
        }
    }
}
