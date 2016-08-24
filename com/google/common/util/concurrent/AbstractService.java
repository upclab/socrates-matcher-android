package com.google.common.util.concurrent;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.Listener;
import com.google.common.util.concurrent.Service.State;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@Beta
public abstract class AbstractService implements Service {
    private final Guard hasReachedRunning;
    private final Guard isStartable;
    private final Guard isStoppable;
    private final Guard isStopped;
    @GuardedBy("monitor")
    private final List<ListenerExecutorPair> listeners;
    private final Monitor monitor;
    private final ExecutionQueue queuedListeners;
    private final Transition shutdown;
    @GuardedBy("monitor")
    private volatile StateSnapshot snapshot;
    private final Transition startup;

    /* renamed from: com.google.common.util.concurrent.AbstractService.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ Throwable val$cause;
        final /* synthetic */ State val$from;
        final /* synthetic */ ListenerExecutorPair val$pair;

        AnonymousClass10(ListenerExecutorPair listenerExecutorPair, State state, Throwable th) {
            this.val$pair = listenerExecutorPair;
            this.val$from = state;
            this.val$cause = th;
        }

        public void run() {
            this.val$pair.listener.failed(this.val$from, this.val$cause);
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.11 */
    static /* synthetic */ class AnonymousClass11 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$util$concurrent$Service$State;

        static {
            $SwitchMap$com$google$common$util$concurrent$Service$State = new int[State.values().length];
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.STARTING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.RUNNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.STOPPING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.TERMINATED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.FAILED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$common$util$concurrent$Service$State[State.NEW.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.6 */
    class C03006 implements Runnable {
        final /* synthetic */ ListenerExecutorPair val$pair;

        C03006(ListenerExecutorPair listenerExecutorPair) {
            this.val$pair = listenerExecutorPair;
        }

        public void run() {
            this.val$pair.listener.starting();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.7 */
    class C03017 implements Runnable {
        final /* synthetic */ ListenerExecutorPair val$pair;

        C03017(ListenerExecutorPair listenerExecutorPair) {
            this.val$pair = listenerExecutorPair;
        }

        public void run() {
            this.val$pair.listener.running();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.8 */
    class C03028 implements Runnable {
        final /* synthetic */ State val$from;
        final /* synthetic */ ListenerExecutorPair val$pair;

        C03028(ListenerExecutorPair listenerExecutorPair, State state) {
            this.val$pair = listenerExecutorPair;
            this.val$from = state;
        }

        public void run() {
            this.val$pair.listener.stopping(this.val$from);
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.9 */
    class C03039 implements Runnable {
        final /* synthetic */ State val$from;
        final /* synthetic */ ListenerExecutorPair val$pair;

        C03039(ListenerExecutorPair listenerExecutorPair, State state) {
            this.val$pair = listenerExecutorPair;
            this.val$from = state;
        }

        public void run() {
            this.val$pair.listener.terminated(this.val$from);
        }
    }

    private static class ListenerExecutorPair {
        final Executor executor;
        final Listener listener;

        ListenerExecutorPair(Listener listener, Executor executor) {
            this.listener = listener;
            this.executor = executor;
        }
    }

    @Immutable
    private static final class StateSnapshot {
        @Nullable
        final Throwable failure;
        final boolean shutdownWhenStartupFinishes;
        final State state;

        StateSnapshot(State internalState) {
            this(internalState, false, null);
        }

        StateSnapshot(State internalState, boolean shutdownWhenStartupFinishes, @Nullable Throwable failure) {
            int i;
            boolean z = !shutdownWhenStartupFinishes || internalState == State.STARTING;
            Preconditions.checkArgument(z, "shudownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", internalState);
            if (failure != null) {
                i = 1;
            } else {
                i = 0;
            }
            Preconditions.checkArgument((i ^ (internalState == State.FAILED ? 1 : 0)) == 0, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", internalState, failure);
            this.state = internalState;
            this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes;
            this.failure = failure;
        }

        State externalState() {
            if (this.shutdownWhenStartupFinishes && this.state == State.STARTING) {
                return State.STOPPING;
            }
            return this.state;
        }

        Throwable failureCause() {
            boolean z;
            if (this.state == State.FAILED) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "failureCause() is only valid if the service has failed, service is %s", this.state);
            return this.failure;
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.1 */
    class C08071 extends Guard {
        C08071(Monitor x0) {
            super(x0);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state() == State.NEW;
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.2 */
    class C08082 extends Guard {
        C08082(Monitor x0) {
            super(x0);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) <= 0;
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.3 */
    class C08093 extends Guard {
        C08093(Monitor x0) {
            super(x0);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(State.RUNNING) >= 0;
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.4 */
    class C08104 extends Guard {
        C08104(Monitor x0) {
            super(x0);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    /* renamed from: com.google.common.util.concurrent.AbstractService.5 */
    class C08115 extends Listener {
        C08115() {
        }

        public void running() {
            AbstractService.this.startup.set(State.RUNNING);
        }

        public void stopping(State from) {
            if (from == State.STARTING) {
                AbstractService.this.startup.set(State.STOPPING);
            }
        }

        public void terminated(State from) {
            if (from == State.NEW) {
                AbstractService.this.startup.set(State.TERMINATED);
            }
            AbstractService.this.shutdown.set(State.TERMINATED);
        }

        public void failed(State from, Throwable failure) {
            switch (AnonymousClass11.$SwitchMap$com$google$common$util$concurrent$Service$State[from.ordinal()]) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                    AbstractService.this.startup.setException(failure);
                    AbstractService.this.shutdown.setException(new Exception("Service failed to start.", failure));
                case Value.STRING_FIELD_NUMBER /*2*/:
                    AbstractService.this.shutdown.setException(new Exception("Service failed while running", failure));
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    AbstractService.this.shutdown.setException(failure);
                default:
                    throw new AssertionError("Unexpected from state: " + from);
            }
        }
    }

    private class Transition extends AbstractFuture<State> {
        private Transition() {
        }

        public State get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
            try {
                return (State) super.get(timeout, unit);
            } catch (TimeoutException e) {
                throw new TimeoutException(AbstractService.this.toString());
            }
        }
    }

    protected abstract void doStart();

    protected abstract void doStop();

    protected AbstractService() {
        this.monitor = new Monitor();
        this.startup = new Transition();
        this.shutdown = new Transition();
        this.isStartable = new C08071(this.monitor);
        this.isStoppable = new C08082(this.monitor);
        this.hasReachedRunning = new C08093(this.monitor);
        this.isStopped = new C08104(this.monitor);
        this.listeners = Lists.newArrayList();
        this.queuedListeners = new ExecutionQueue();
        this.snapshot = new StateSnapshot(State.NEW);
        addListener(new C08115(), MoreExecutors.sameThreadExecutor());
    }

    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new StateSnapshot(State.STARTING);
                starting();
                doStart();
            } catch (Throwable startupFailure) {
                notifyFailed(startupFailure);
            } finally {
                this.monitor.leave();
                executeListeners();
            }
            return this;
        }
        throw new IllegalStateException("Service " + this + " has already been started");
    }

    @Deprecated
    public final ListenableFuture<State> start() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new StateSnapshot(State.STARTING);
                starting();
                doStart();
            } catch (Throwable startupFailure) {
                notifyFailed(startupFailure);
            } finally {
                this.monitor.leave();
                executeListeners();
            }
        }
        return this.startup;
    }

    public final Service stopAsync() {
        stop();
        return this;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @java.lang.Deprecated
    public final com.google.common.util.concurrent.ListenableFuture<com.google.common.util.concurrent.Service.State> stop() {
        /*
        r6 = this;
        r2 = r6.monitor;
        r3 = r6.isStoppable;
        r2 = r2.enterIf(r3);
        if (r2 == 0) goto L_0x003e;
    L_0x000a:
        r0 = r6.state();	 Catch:{ Throwable -> 0x0032 }
        r2 = com.google.common.util.concurrent.AbstractService.AnonymousClass11.$SwitchMap$com$google$common$util$concurrent$Service$State;	 Catch:{ Throwable -> 0x0032 }
        r3 = r0.ordinal();	 Catch:{ Throwable -> 0x0032 }
        r2 = r2[r3];	 Catch:{ Throwable -> 0x0032 }
        switch(r2) {
            case 1: goto L_0x0058;
            case 2: goto L_0x0073;
            case 3: goto L_0x0085;
            case 4: goto L_0x0085;
            case 5: goto L_0x0085;
            case 6: goto L_0x0041;
            default: goto L_0x0019;
        };	 Catch:{ Throwable -> 0x0032 }
    L_0x0019:
        r2 = new java.lang.AssertionError;	 Catch:{ Throwable -> 0x0032 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0032 }
        r3.<init>();	 Catch:{ Throwable -> 0x0032 }
        r4 = "Unexpected state: ";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0032 }
        r3 = r3.append(r0);	 Catch:{ Throwable -> 0x0032 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0032 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0032 }
        throw r2;	 Catch:{ Throwable -> 0x0032 }
    L_0x0032:
        r1 = move-exception;
        r6.notifyFailed(r1);	 Catch:{ all -> 0x0069 }
        r2 = r6.monitor;
        r2.leave();
        r6.executeListeners();
    L_0x003e:
        r2 = r6.shutdown;
        return r2;
    L_0x0041:
        r2 = new com.google.common.util.concurrent.AbstractService$StateSnapshot;	 Catch:{ Throwable -> 0x0032 }
        r3 = com.google.common.util.concurrent.Service.State.TERMINATED;	 Catch:{ Throwable -> 0x0032 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0032 }
        r6.snapshot = r2;	 Catch:{ Throwable -> 0x0032 }
        r2 = com.google.common.util.concurrent.Service.State.NEW;	 Catch:{ Throwable -> 0x0032 }
        r6.terminated(r2);	 Catch:{ Throwable -> 0x0032 }
    L_0x004f:
        r2 = r6.monitor;
        r2.leave();
        r6.executeListeners();
        goto L_0x003e;
    L_0x0058:
        r2 = new com.google.common.util.concurrent.AbstractService$StateSnapshot;	 Catch:{ Throwable -> 0x0032 }
        r3 = com.google.common.util.concurrent.Service.State.STARTING;	 Catch:{ Throwable -> 0x0032 }
        r4 = 1;
        r5 = 0;
        r2.<init>(r3, r4, r5);	 Catch:{ Throwable -> 0x0032 }
        r6.snapshot = r2;	 Catch:{ Throwable -> 0x0032 }
        r2 = com.google.common.util.concurrent.Service.State.STARTING;	 Catch:{ Throwable -> 0x0032 }
        r6.stopping(r2);	 Catch:{ Throwable -> 0x0032 }
        goto L_0x004f;
    L_0x0069:
        r2 = move-exception;
        r3 = r6.monitor;
        r3.leave();
        r6.executeListeners();
        throw r2;
    L_0x0073:
        r2 = new com.google.common.util.concurrent.AbstractService$StateSnapshot;	 Catch:{ Throwable -> 0x0032 }
        r3 = com.google.common.util.concurrent.Service.State.STOPPING;	 Catch:{ Throwable -> 0x0032 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0032 }
        r6.snapshot = r2;	 Catch:{ Throwable -> 0x0032 }
        r2 = com.google.common.util.concurrent.Service.State.RUNNING;	 Catch:{ Throwable -> 0x0032 }
        r6.stopping(r2);	 Catch:{ Throwable -> 0x0032 }
        r6.doStop();	 Catch:{ Throwable -> 0x0032 }
        goto L_0x004f;
    L_0x0085:
        r2 = new java.lang.AssertionError;	 Catch:{ Throwable -> 0x0032 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0032 }
        r3.<init>();	 Catch:{ Throwable -> 0x0032 }
        r4 = "isStoppable is incorrectly implemented, saw: ";
        r3 = r3.append(r4);	 Catch:{ Throwable -> 0x0032 }
        r3 = r3.append(r0);	 Catch:{ Throwable -> 0x0032 }
        r3 = r3.toString();	 Catch:{ Throwable -> 0x0032 }
        r2.<init>(r3);	 Catch:{ Throwable -> 0x0032 }
        throw r2;	 Catch:{ Throwable -> 0x0032 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractService.stop():com.google.common.util.concurrent.ListenableFuture<com.google.common.util.concurrent.Service$State>");
    }

    @Deprecated
    public State startAndWait() {
        return (State) Futures.getUnchecked(start());
    }

    @Deprecated
    public State stopAndWait() {
        return (State) Futures.getUnchecked(stop());
    }

    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
            try {
                checkCurrentState(State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach the RUNNING state. " + "Current state: " + state());
        }
    }

    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
            try {
                State state = state();
                checkCurrentState(State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            throw new TimeoutException("Timed out waiting for " + this + " to reach a terminal state. " + "Current state: " + state());
        }
    }

    @GuardedBy("monitor")
    private void checkCurrentState(State expected) {
        State actual = state();
        if (actual == expected) {
            return;
        }
        if (actual == State.FAILED) {
            throw new IllegalStateException("Expected the service to be " + expected + ", but the service has FAILED", failureCause());
        }
        throw new IllegalStateException("Expected the service to be " + expected + ", but was " + actual);
    }

    protected final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state != State.STARTING) {
                IllegalStateException failure = new IllegalStateException("Cannot notifyStarted() when the service is " + this.snapshot.state);
                notifyFailed(failure);
                throw failure;
            }
            if (this.snapshot.shutdownWhenStartupFinishes) {
                this.snapshot = new StateSnapshot(State.STOPPING);
                doStop();
            } else {
                this.snapshot = new StateSnapshot(State.RUNNING);
                running();
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
        }
    }

    protected final void notifyStopped() {
        this.monitor.enter();
        try {
            State previous = this.snapshot.state;
            if (previous == State.STOPPING || previous == State.RUNNING) {
                this.snapshot = new StateSnapshot(State.TERMINATED);
                terminated(previous);
                return;
            }
            IllegalStateException failure = new IllegalStateException("Cannot notifyStopped() when the service is " + previous);
            notifyFailed(failure);
            throw failure;
        } finally {
            this.monitor.leave();
            executeListeners();
        }
    }

    protected final void notifyFailed(Throwable cause) {
        Preconditions.checkNotNull(cause);
        this.monitor.enter();
        try {
            State previous = state();
            switch (AnonymousClass11.$SwitchMap$com$google$common$util$concurrent$Service$State[previous.ordinal()]) {
                case Value.TYPE_FIELD_NUMBER /*1*/:
                case Value.STRING_FIELD_NUMBER /*2*/:
                case Value.LIST_ITEM_FIELD_NUMBER /*3*/:
                    this.snapshot = new StateSnapshot(State.FAILED, false, cause);
                    failed(previous, cause);
                    break;
                case Value.MAP_KEY_FIELD_NUMBER /*4*/:
                case Value.MACRO_REFERENCE_FIELD_NUMBER /*6*/:
                    throw new IllegalStateException("Failed while in state:" + previous, cause);
                case Value.MAP_VALUE_FIELD_NUMBER /*5*/:
                    break;
                default:
                    throw new AssertionError("Unexpected state: " + previous);
            }
            this.monitor.leave();
            executeListeners();
        } catch (Throwable th) {
            this.monitor.leave();
            executeListeners();
        }
    }

    public final boolean isRunning() {
        return state() == State.RUNNING;
    }

    public final State state() {
        return this.snapshot.externalState();
    }

    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    public final void addListener(Listener listener, Executor executor) {
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.monitor.enter();
        try {
            State currentState = state();
            if (!(currentState == State.TERMINATED || currentState == State.FAILED)) {
                this.listeners.add(new ListenerExecutorPair(listener, executor));
            }
            this.monitor.leave();
        } catch (Throwable th) {
            this.monitor.leave();
        }
    }

    public String toString() {
        return getClass().getSimpleName() + " [" + state() + "]";
    }

    private void executeListeners() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            this.queuedListeners.execute();
        }
    }

    @GuardedBy("monitor")
    private void starting() {
        for (ListenerExecutorPair pair : this.listeners) {
            this.queuedListeners.add(new C03006(pair), pair.executor);
        }
    }

    @GuardedBy("monitor")
    private void running() {
        for (ListenerExecutorPair pair : this.listeners) {
            this.queuedListeners.add(new C03017(pair), pair.executor);
        }
    }

    @GuardedBy("monitor")
    private void stopping(State from) {
        for (ListenerExecutorPair pair : this.listeners) {
            this.queuedListeners.add(new C03028(pair, from), pair.executor);
        }
    }

    @GuardedBy("monitor")
    private void terminated(State from) {
        for (ListenerExecutorPair pair : this.listeners) {
            this.queuedListeners.add(new C03039(pair, from), pair.executor);
        }
        this.listeners.clear();
    }

    @GuardedBy("monitor")
    private void failed(State from, Throwable cause) {
        for (ListenerExecutorPair pair : this.listeners) {
            this.queuedListeners.add(new AnonymousClass10(pair, from, cause), pair.executor);
        }
        this.listeners.clear();
    }
}
