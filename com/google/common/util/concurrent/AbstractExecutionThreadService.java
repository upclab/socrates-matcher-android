package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@Beta
public abstract class AbstractExecutionThreadService implements Service {
    private static final Logger logger;
    private final Service delegate;

    /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService.2 */
    class C02922 implements Executor {
        C02922() {
        }

        public void execute(Runnable command) {
            MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), command).start();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService.1 */
    class C10431 extends AbstractService {

        /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService.1.2 */
        class C02912 implements Runnable {
            C02912() {
            }

            public void run() {
                try {
                    AbstractExecutionThreadService.this.startUp();
                    C10431.this.notifyStarted();
                    if (C10431.this.isRunning()) {
                        AbstractExecutionThreadService.this.run();
                    }
                    AbstractExecutionThreadService.this.shutDown();
                    C10431.this.notifyStopped();
                } catch (Throwable t) {
                    C10431.this.notifyFailed(t);
                    RuntimeException propagate = Throwables.propagate(t);
                }
            }
        }

        /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService.1.1 */
        class C08011 implements Supplier<String> {
            C08011() {
            }

            public String get() {
                return AbstractExecutionThreadService.this.serviceName();
            }
        }

        C10431() {
        }

        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new C08011()).execute(new C02912());
        }

        protected void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }
    }

    protected abstract void run() throws Exception;

    static {
        logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    }

    protected AbstractExecutionThreadService() {
        this.delegate = new C10431();
    }

    protected void startUp() throws Exception {
    }

    protected void shutDown() throws Exception {
    }

    protected void triggerShutdown() {
    }

    protected Executor executor() {
        return new C02922();
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
