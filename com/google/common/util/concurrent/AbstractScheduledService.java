package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
public abstract class AbstractScheduledService implements Service {
    private static final Logger logger;
    private final AbstractService delegate;

    /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.2 */
    class C02992 implements ThreadFactory {
        C02992() {
        }

        public Thread newThread(Runnable runnable) {
            return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
        }
    }

    public static abstract class Scheduler {

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.1 */
        static class C08051 extends Scheduler {
            final /* synthetic */ long val$delay;
            final /* synthetic */ long val$initialDelay;
            final /* synthetic */ TimeUnit val$unit;

            C08051(long j, long j2, TimeUnit timeUnit) {
                this.val$initialDelay = j;
                this.val$delay = j2;
                this.val$unit = timeUnit;
                super();
            }

            public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                return executor.scheduleWithFixedDelay(task, this.val$initialDelay, this.val$delay, this.val$unit);
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.Scheduler.2 */
        static class C08062 extends Scheduler {
            final /* synthetic */ long val$initialDelay;
            final /* synthetic */ long val$period;
            final /* synthetic */ TimeUnit val$unit;

            C08062(long j, long j2, TimeUnit timeUnit) {
                this.val$initialDelay = j;
                this.val$period = j2;
                this.val$unit = timeUnit;
                super();
            }

            public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                return executor.scheduleAtFixedRate(task, this.val$initialDelay, this.val$period, this.val$unit);
            }
        }

        abstract Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);

        public static Scheduler newFixedDelaySchedule(long initialDelay, long delay, TimeUnit unit) {
            return new C08051(initialDelay, delay, unit);
        }

        public static Scheduler newFixedRateSchedule(long initialDelay, long period, TimeUnit unit) {
            return new C08062(initialDelay, period, unit);
        }

        private Scheduler() {
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.3 */
    class C08043 extends Listener {
        final /* synthetic */ ScheduledExecutorService val$executor;

        C08043(ScheduledExecutorService scheduledExecutorService) {
            this.val$executor = scheduledExecutorService;
        }

        public void terminated(State from) {
            this.val$executor.shutdown();
        }

        public void failed(State from, Throwable failure) {
            this.val$executor.shutdown();
        }
    }

    @Beta
    public static abstract class CustomScheduler extends Scheduler {

        @Beta
        protected static final class Schedule {
            private final long delay;
            private final TimeUnit unit;

            public Schedule(long delay, TimeUnit unit) {
                this.delay = delay;
                this.unit = (TimeUnit) Preconditions.checkNotNull(unit);
            }
        }

        private class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {
            @GuardedBy("lock")
            private Future<Void> currentFuture;
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock;
            private final AbstractService service;
            private final Runnable wrappedRunnable;

            ReschedulableCallable(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
                this.lock = new ReentrantLock();
                this.wrappedRunnable = runnable;
                this.executor = executor;
                this.service = service;
            }

            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void reschedule() {
                /*
                r6 = this;
                r2 = r6.lock;
                r2.lock();
                r2 = r6.currentFuture;	 Catch:{ Throwable -> 0x002d }
                if (r2 == 0) goto L_0x0011;
            L_0x0009:
                r2 = r6.currentFuture;	 Catch:{ Throwable -> 0x002d }
                r2 = r2.isCancelled();	 Catch:{ Throwable -> 0x002d }
                if (r2 != 0) goto L_0x0027;
            L_0x0011:
                r2 = com.google.common.util.concurrent.AbstractScheduledService.CustomScheduler.this;	 Catch:{ Throwable -> 0x002d }
                r1 = r2.getNextSchedule();	 Catch:{ Throwable -> 0x002d }
                r2 = r6.executor;	 Catch:{ Throwable -> 0x002d }
                r3 = r1.delay;	 Catch:{ Throwable -> 0x002d }
                r5 = r1.unit;	 Catch:{ Throwable -> 0x002d }
                r2 = r2.schedule(r6, r3, r5);	 Catch:{ Throwable -> 0x002d }
                r6.currentFuture = r2;	 Catch:{ Throwable -> 0x002d }
            L_0x0027:
                r2 = r6.lock;
                r2.unlock();
            L_0x002c:
                return;
            L_0x002d:
                r0 = move-exception;
                r2 = r6.service;	 Catch:{ all -> 0x0039 }
                r2.notifyFailed(r0);	 Catch:{ all -> 0x0039 }
                r2 = r6.lock;
                r2.unlock();
                goto L_0x002c;
            L_0x0039:
                r2 = move-exception;
                r3 = r6.lock;
                r3.unlock();
                throw r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractScheduledService.CustomScheduler.ReschedulableCallable.reschedule():void");
            }

            public boolean cancel(boolean mayInterruptIfRunning) {
                this.lock.lock();
                try {
                    boolean cancel = this.currentFuture.cancel(mayInterruptIfRunning);
                    return cancel;
                } finally {
                    this.lock.unlock();
                }
            }

            protected Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel is supported by this future");
            }
        }

        protected abstract Schedule getNextSchedule() throws Exception;

        public CustomScheduler() {
            super();
        }

        final Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
            ReschedulableCallable task = new ReschedulableCallable(service, executor, runnable);
            task.reschedule();
            return task;
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.1 */
    class C10451 extends AbstractService {
        private volatile ScheduledExecutorService executorService;
        private final ReentrantLock lock;
        private volatile Future<?> runningTask;
        private final Runnable task;

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.1.1 */
        class C02961 implements Runnable {
            C02961() {
            }

            public void run() {
                C10451.this.lock.lock();
                try {
                    AbstractScheduledService.this.runOneIteration();
                    C10451.this.lock.unlock();
                } catch (Throwable th) {
                    C10451.this.lock.unlock();
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.1.3 */
        class C02973 implements Runnable {
            C02973() {
            }

            public void run() {
                C10451.this.lock.lock();
                try {
                    AbstractScheduledService.this.startUp();
                    C10451.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, C10451.this.executorService, C10451.this.task);
                    C10451.this.notifyStarted();
                    C10451.this.lock.unlock();
                } catch (Throwable th) {
                    C10451.this.lock.unlock();
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.1.4 */
        class C02984 implements Runnable {
            C02984() {
            }

            public void run() {
                try {
                    C10451.this.lock.lock();
                    if (C10451.this.state() != State.STOPPING) {
                        C10451.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.shutDown();
                    C10451.this.lock.unlock();
                    C10451.this.notifyStopped();
                } catch (Throwable t) {
                    C10451.this.notifyFailed(t);
                    RuntimeException propagate = Throwables.propagate(t);
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractScheduledService.1.2 */
        class C08032 implements Supplier<String> {
            C08032() {
            }

            public String get() {
                return AbstractScheduledService.this.serviceName() + " " + C10451.this.state();
            }
        }

        C10451() {
            this.lock = new ReentrantLock();
            this.task = new C02961();
        }

        protected final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new C08032());
            this.executorService.execute(new C02973());
        }

        protected final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new C02984());
        }
    }

    protected abstract void runOneIteration() throws Exception;

    protected abstract Scheduler scheduler();

    static {
        logger = Logger.getLogger(AbstractScheduledService.class.getName());
    }

    protected AbstractScheduledService() {
        this.delegate = new C10451();
    }

    protected void startUp() throws Exception {
    }

    protected void shutDown() throws Exception {
    }

    protected ScheduledExecutorService executor() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new C02992());
        addListener(new C08043(executor), MoreExecutors.sameThreadExecutor());
        return executor;
    }

    protected String serviceName() {
        return getClass().getSimpleName();
    }

    public String toString() {
        return serviceName() + " [" + state() + "]";
    }

    @Deprecated
    public final ListenableFuture<State> start() {
        return this.delegate.start();
    }

    @Deprecated
    public final State startAndWait() {
        return this.delegate.startAndWait();
    }

    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public final State state() {
        return this.delegate.state();
    }

    @Deprecated
    public final ListenableFuture<State> stop() {
        return this.delegate.stop();
    }

    @Deprecated
    public final State stopAndWait() {
        return this.delegate.stopAndWait();
    }

    public final void addListener(Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitRunning(timeout, unit);
    }

    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitTerminated(timeout, unit);
    }
}
