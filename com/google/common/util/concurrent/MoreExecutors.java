package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class MoreExecutors {

    /* renamed from: com.google.common.util.concurrent.MoreExecutors.1 */
    static class C03141 implements Runnable {
        final /* synthetic */ ListenableFuture val$future;
        final /* synthetic */ BlockingQueue val$queue;

        C03141(BlockingQueue blockingQueue, ListenableFuture listenableFuture) {
            this.val$queue = blockingQueue;
            this.val$future = listenableFuture;
        }

        public void run() {
            this.val$queue.add(this.val$future);
        }
    }

    /* renamed from: com.google.common.util.concurrent.MoreExecutors.2 */
    static class C03152 implements Executor {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ Supplier val$nameSupplier;

        C03152(Executor executor, Supplier supplier) {
            this.val$executor = executor;
            this.val$nameSupplier = supplier;
        }

        public void execute(Runnable command) {
            this.val$executor.execute(Callables.threadRenaming(command, this.val$nameSupplier));
        }
    }

    @VisibleForTesting
    static class Application {

        /* renamed from: com.google.common.util.concurrent.MoreExecutors.Application.1 */
        class C03161 implements Runnable {
            final /* synthetic */ ExecutorService val$service;
            final /* synthetic */ long val$terminationTimeout;
            final /* synthetic */ TimeUnit val$timeUnit;

            C03161(ExecutorService executorService, long j, TimeUnit timeUnit) {
                this.val$service = executorService;
                this.val$terminationTimeout = j;
                this.val$timeUnit = timeUnit;
            }

            public void run() {
                try {
                    this.val$service.shutdown();
                    this.val$service.awaitTermination(this.val$terminationTimeout, this.val$timeUnit);
                } catch (InterruptedException e) {
                }
            }
        }

        Application() {
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(executor);
            ExecutorService service = Executors.unconfigurableExecutorService(executor);
            addDelayedShutdownHook(service, terminationTimeout, timeUnit);
            return service;
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(executor);
            ScheduledExecutorService service = Executors.unconfigurableScheduledExecutorService(executor);
            addDelayedShutdownHook(service, terminationTimeout, timeUnit);
            return service;
        }

        final void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
            Preconditions.checkNotNull(service);
            Preconditions.checkNotNull(timeUnit);
            addShutdownHook(MoreExecutors.newThread("DelayedShutdownHook-for-" + service, new C03161(service, terminationTimeout, timeUnit)));
        }

        final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
            return getExitingExecutorService(executor, 120, TimeUnit.SECONDS);
        }

        final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
            return getExitingScheduledExecutorService(executor, 120, TimeUnit.SECONDS);
        }

        @VisibleForTesting
        void addShutdownHook(Thread hook) {
            Runtime.getRuntime().addShutdownHook(hook);
        }
    }

    /* renamed from: com.google.common.util.concurrent.MoreExecutors.3 */
    static class C08203 extends WrappingExecutorService {
        final /* synthetic */ Supplier val$nameSupplier;

        C08203(ExecutorService x0, Supplier supplier) {
            this.val$nameSupplier = supplier;
            super(x0);
        }

        protected <T> Callable<T> wrapTask(Callable<T> callable) {
            return Callables.threadRenaming((Callable) callable, this.val$nameSupplier);
        }

        protected Runnable wrapTask(Runnable command) {
            return Callables.threadRenaming(command, this.val$nameSupplier);
        }
    }

    /* renamed from: com.google.common.util.concurrent.MoreExecutors.4 */
    static class C10494 extends WrappingScheduledExecutorService {
        final /* synthetic */ Supplier val$nameSupplier;

        C10494(ScheduledExecutorService x0, Supplier supplier) {
            this.val$nameSupplier = supplier;
            super(x0);
        }

        protected <T> Callable<T> wrapTask(Callable<T> callable) {
            return Callables.threadRenaming((Callable) callable, this.val$nameSupplier);
        }

        protected Runnable wrapTask(Runnable command) {
            return Callables.threadRenaming(command, this.val$nameSupplier);
        }
    }

    private static class ListeningDecorator extends AbstractListeningExecutorService {
        private final ExecutorService delegate;

        ListeningDecorator(ExecutorService delegate) {
            this.delegate = (ExecutorService) Preconditions.checkNotNull(delegate);
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout, unit);
        }

        public boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public void shutdown() {
            this.delegate.shutdown();
        }

        public List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }

        public void execute(Runnable command) {
            this.delegate.execute(command);
        }
    }

    private static class SameThreadExecutorService extends AbstractListeningExecutorService {
        private final Lock lock;
        private int runningTasks;
        private boolean shutdown;
        private final Condition termination;

        private SameThreadExecutorService() {
            this.lock = new ReentrantLock();
            this.termination = this.lock.newCondition();
            this.runningTasks = 0;
            this.shutdown = false;
        }

        public void execute(Runnable command) {
            startTask();
            try {
                command.run();
            } finally {
                endTask();
            }
        }

        public boolean isShutdown() {
            this.lock.lock();
            try {
                boolean z = this.shutdown;
                return z;
            } finally {
                this.lock.unlock();
            }
        }

        public void shutdown() {
            this.lock.lock();
            try {
                this.shutdown = true;
            } finally {
                this.lock.unlock();
            }
        }

        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        public boolean isTerminated() {
            this.lock.lock();
            try {
                boolean z = this.shutdown && this.runningTasks == 0;
                this.lock.unlock();
                return z;
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            long nanos = unit.toNanos(timeout);
            this.lock.lock();
            while (!isTerminated()) {
                if (nanos <= 0) {
                    return false;
                }
                try {
                    nanos = this.termination.awaitNanos(nanos);
                } finally {
                    this.lock.unlock();
                }
            }
            this.lock.unlock();
            return true;
        }

        private void startTask() {
            this.lock.lock();
            try {
                if (isShutdown()) {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
                this.runningTasks++;
            } finally {
                this.lock.unlock();
            }
        }

        private void endTask() {
            this.lock.lock();
            try {
                this.runningTasks--;
                if (isTerminated()) {
                    this.termination.signalAll();
                }
                this.lock.unlock();
            } catch (Throwable th) {
                this.lock.unlock();
            }
        }
    }

    private static class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService {
        final ScheduledExecutorService delegate;

        private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture<Void> implements Runnable {
            private final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable delegate) {
                this.delegate = (Runnable) Preconditions.checkNotNull(delegate);
            }

            public void run() {
                try {
                    this.delegate.run();
                } catch (Throwable t) {
                    setException(t);
                    RuntimeException propagate = Throwables.propagate(t);
                }
            }
        }

        private static final class ListenableScheduledTask<V> extends SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> scheduledDelegate;

            public ListenableScheduledTask(ListenableFuture<V> listenableDelegate, ScheduledFuture<?> scheduledDelegate) {
                super(listenableDelegate);
                this.scheduledDelegate = scheduledDelegate;
            }

            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancelled = super.cancel(mayInterruptIfRunning);
                if (cancelled) {
                    this.scheduledDelegate.cancel(mayInterruptIfRunning);
                }
                return cancelled;
            }

            public long getDelay(TimeUnit unit) {
                return this.scheduledDelegate.getDelay(unit);
            }

            public int compareTo(Delayed other) {
                return this.scheduledDelegate.compareTo(other);
            }
        }

        ScheduledListeningDecorator(ScheduledExecutorService delegate) {
            super(delegate);
            this.delegate = (ScheduledExecutorService) Preconditions.checkNotNull(delegate);
        }

        public ListenableScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            ListenableFutureTask<Void> task = ListenableFutureTask.create(command, null);
            return new ListenableScheduledTask(task, this.delegate.schedule(task, delay, unit));
        }

        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            ListenableFutureTask<V> task = ListenableFutureTask.create(callable);
            return new ListenableScheduledTask(task, this.delegate.schedule(task, delay, unit));
        }

        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
            return new ListenableScheduledTask(task, this.delegate.scheduleAtFixedRate(task, initialDelay, period, unit));
        }

        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
            return new ListenableScheduledTask(task, this.delegate.scheduleWithFixedDelay(task, initialDelay, delay, unit));
        }
    }

    private MoreExecutors() {
    }

    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(executor, terminationTimeout, timeUnit);
    }

    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(executor, terminationTimeout, timeUnit);
    }

    @Beta
    public static void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(service, terminationTimeout, timeUnit);
    }

    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
        return new Application().getExitingExecutorService(executor);
    }

    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
        return new Application().getExitingScheduledExecutorService(executor);
    }

    private static void useDaemonThreadFactory(ThreadPoolExecutor executor) {
        executor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
    }

    public static ListeningExecutorService sameThreadExecutor() {
        return new SameThreadExecutorService();
    }

    public static ListeningExecutorService listeningDecorator(ExecutorService delegate) {
        if (delegate instanceof ListeningExecutorService) {
            return (ListeningExecutorService) delegate;
        }
        return delegate instanceof ScheduledExecutorService ? new ScheduledListeningDecorator((ScheduledExecutorService) delegate) : new ListeningDecorator(delegate);
    }

    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService delegate) {
        return delegate instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService) delegate : new ScheduledListeningDecorator(delegate);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> T invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService r20, java.util.Collection<? extends java.util.concurrent.Callable<T>> r21, boolean r22, long r23) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
        com.google.common.base.Preconditions.checkNotNull(r20);
        r16 = r21.size();
        if (r16 <= 0) goto L_0x0079;
    L_0x0009:
        r18 = 1;
    L_0x000b:
        com.google.common.base.Preconditions.checkArgument(r18);
        r9 = com.google.common.collect.Lists.newArrayListWithCapacity(r16);
        r8 = com.google.common.collect.Queues.newLinkedBlockingQueue();
        r4 = 0;
        if (r22 == 0) goto L_0x007c;
    L_0x0019:
        r12 = java.lang.System.nanoTime();	 Catch:{ all -> 0x008d }
    L_0x001d:
        r11 = r21.iterator();	 Catch:{ all -> 0x008d }
        r18 = r11.next();	 Catch:{ all -> 0x008d }
        r18 = (java.util.concurrent.Callable) r18;	 Catch:{ all -> 0x008d }
        r0 = r20;
        r1 = r18;
        r18 = submitAndAddQueueListener(r0, r1, r8);	 Catch:{ all -> 0x008d }
        r0 = r18;
        r9.add(r0);	 Catch:{ all -> 0x008d }
        r16 = r16 + -1;
        r3 = 1;
        r5 = r4;
    L_0x0038:
        r7 = r8.poll();	 Catch:{ all -> 0x00bc }
        r7 = (java.util.concurrent.Future) r7;	 Catch:{ all -> 0x00bc }
        if (r7 != 0) goto L_0x0059;
    L_0x0040:
        if (r16 <= 0) goto L_0x007f;
    L_0x0042:
        r16 = r16 + -1;
        r18 = r11.next();	 Catch:{ all -> 0x00bc }
        r18 = (java.util.concurrent.Callable) r18;	 Catch:{ all -> 0x00bc }
        r0 = r20;
        r1 = r18;
        r18 = submitAndAddQueueListener(r0, r1, r8);	 Catch:{ all -> 0x00bc }
        r0 = r18;
        r9.add(r0);	 Catch:{ all -> 0x00bc }
        r3 = r3 + 1;
    L_0x0059:
        if (r7 == 0) goto L_0x00e2;
    L_0x005b:
        r3 = r3 + -1;
        r18 = r7.get();	 Catch:{ ExecutionException -> 0x00d0, RuntimeException -> 0x00d5 }
        r10 = r9.iterator();
    L_0x0065:
        r19 = r10.hasNext();
        if (r19 == 0) goto L_0x00df;
    L_0x006b:
        r7 = r10.next();
        r7 = (java.util.concurrent.Future) r7;
        r19 = 1;
        r0 = r19;
        r7.cancel(r0);
        goto L_0x0065;
    L_0x0079:
        r18 = 0;
        goto L_0x000b;
    L_0x007c:
        r12 = 0;
        goto L_0x001d;
    L_0x007f:
        if (r3 != 0) goto L_0x00a6;
    L_0x0081:
        if (r5 != 0) goto L_0x00e0;
    L_0x0083:
        r4 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x00bc }
        r18 = 0;
        r0 = r18;
        r4.<init>(r0);	 Catch:{ all -> 0x00bc }
    L_0x008c:
        throw r4;	 Catch:{ all -> 0x008d }
    L_0x008d:
        r18 = move-exception;
    L_0x008e:
        r10 = r9.iterator();
    L_0x0092:
        r19 = r10.hasNext();
        if (r19 == 0) goto L_0x00de;
    L_0x0098:
        r7 = r10.next();
        r7 = (java.util.concurrent.Future) r7;
        r19 = 1;
        r0 = r19;
        r7.cancel(r0);
        goto L_0x0092;
    L_0x00a6:
        if (r22 == 0) goto L_0x00c9;
    L_0x00a8:
        r18 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ all -> 0x00bc }
        r0 = r23;
        r2 = r18;
        r7 = r8.poll(r0, r2);	 Catch:{ all -> 0x00bc }
        r7 = (java.util.concurrent.Future) r7;	 Catch:{ all -> 0x00bc }
        if (r7 != 0) goto L_0x00bf;
    L_0x00b6:
        r18 = new java.util.concurrent.TimeoutException;	 Catch:{ all -> 0x00bc }
        r18.<init>();	 Catch:{ all -> 0x00bc }
        throw r18;	 Catch:{ all -> 0x00bc }
    L_0x00bc:
        r18 = move-exception;
        r4 = r5;
        goto L_0x008e;
    L_0x00bf:
        r14 = java.lang.System.nanoTime();	 Catch:{ all -> 0x00bc }
        r18 = r14 - r12;
        r23 = r23 - r18;
        r12 = r14;
        goto L_0x0059;
    L_0x00c9:
        r7 = r8.take();	 Catch:{ all -> 0x00bc }
        r7 = (java.util.concurrent.Future) r7;	 Catch:{ all -> 0x00bc }
        goto L_0x0059;
    L_0x00d0:
        r6 = move-exception;
        r4 = r6;
    L_0x00d2:
        r5 = r4;
        goto L_0x0038;
    L_0x00d5:
        r17 = move-exception;
        r4 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x00bc }
        r0 = r17;
        r4.<init>(r0);	 Catch:{ all -> 0x00bc }
        goto L_0x00d2;
    L_0x00de:
        throw r18;
    L_0x00df:
        return r18;
    L_0x00e0:
        r4 = r5;
        goto L_0x008c;
    L_0x00e2:
        r4 = r5;
        goto L_0x00d2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.MoreExecutors.invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService, java.util.Collection, boolean, long):T");
    }

    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService executorService, Callable<T> task, BlockingQueue<Future<T>> queue) {
        ListenableFuture<T> future = executorService.submit((Callable) task);
        future.addListener(new C03141(queue, future), sameThreadExecutor());
        return future;
    }

    @Beta
    public static ThreadFactory platformThreadFactory() {
        if (!isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e3);
        } catch (InvocationTargetException e4) {
            throw Throwables.propagate(e4.getCause());
        }
    }

    private static boolean isAppEngine() {
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            if (Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null) {
                return true;
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (InvocationTargetException e2) {
            return false;
        } catch (IllegalAccessException e3) {
            return false;
        } catch (NoSuchMethodException e4) {
            return false;
        }
    }

    static Thread newThread(String name, Runnable runnable) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(runnable);
        Thread result = platformThreadFactory().newThread(runnable);
        try {
            result.setName(name);
        } catch (SecurityException e) {
        }
        return result;
    }

    static Executor renamingDecorator(Executor executor, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(nameSupplier);
        return isAppEngine() ? executor : new C03152(executor, nameSupplier);
    }

    static ExecutorService renamingDecorator(ExecutorService service, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        return isAppEngine() ? service : new C08203(service, nameSupplier);
    }

    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService service, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        return isAppEngine() ? service : new C10494(service, nameSupplier);
    }
}
