package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Beta
public abstract class AbstractIdleService implements Service {
    private final Service delegate;
    private final Supplier<String> threadNameSupplier;

    /* renamed from: com.google.common.util.concurrent.AbstractIdleService.3 */
    class C02953 implements Executor {
        C02953() {
        }

        public void execute(Runnable command) {
            MoreExecutors.newThread((String) AbstractIdleService.this.threadNameSupplier.get(), command).start();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractIdleService.1 */
    class C08021 implements Supplier<String> {
        C08021() {
        }

        public String get() {
            return AbstractIdleService.this.serviceName() + " " + AbstractIdleService.this.state();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractIdleService.2 */
    class C10442 extends AbstractService {

        /* renamed from: com.google.common.util.concurrent.AbstractIdleService.2.1 */
        class C02931 implements Runnable {
            C02931() {
            }

            public void run() {
                try {
                    AbstractIdleService.this.startUp();
                    C10442.this.notifyStarted();
                } catch (Throwable t) {
                    C10442.this.notifyFailed(t);
                    RuntimeException propagate = Throwables.propagate(t);
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractIdleService.2.2 */
        class C02942 implements Runnable {
            C02942() {
            }

            public void run() {
                try {
                    AbstractIdleService.this.shutDown();
                    C10442.this.notifyStopped();
                } catch (Throwable t) {
                    C10442.this.notifyFailed(t);
                    RuntimeException propagate = Throwables.propagate(t);
                }
            }
        }

        C10442() {
        }

        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new C02931());
        }

        protected final void doStop() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new C02942());
        }
    }

    protected abstract void shutDown() throws Exception;

    protected abstract void startUp() throws Exception;

    protected AbstractIdleService() {
        this.threadNameSupplier = new C08021();
        this.delegate = new C10442();
    }

    protected Executor executor() {
        return new C02953();
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

    protected String serviceName() {
        return getClass().getSimpleName();
    }
}
