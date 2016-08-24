package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSetMultimap.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.State;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

@Beta
public final class ServiceManager {
    private static final Logger logger;
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;

    private static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    @Beta
    public static abstract class Listener {
        public void healthy() {
        }

        public void stopped() {
        }

        public void failure(Service service) {
        }
    }

    @Immutable
    private static final class ListenerExecutorPair {
        final Executor executor;
        final Listener listener;

        ListenerExecutorPair(Listener listener, Executor executor) {
            this.listener = listener;
            this.executor = executor;
        }
    }

    private static final class ServiceManagerState {
        final Guard awaitHealthGuard;
        @GuardedBy("monitor")
        final List<ListenerExecutorPair> listeners;
        final Monitor monitor;
        final int numberOfServices;
        @GuardedBy("monitor")
        final ExecutionQueue queuedListeners;
        @GuardedBy("monitor")
        boolean ready;
        @GuardedBy("monitor")
        final SetMultimap<State, Service> servicesByState;
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> startupTimers;
        @GuardedBy("monitor")
        final Multiset<State> states;
        final Guard stoppedGuard;
        @GuardedBy("monitor")
        boolean transitioned;

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.5 */
        class C03215 implements Runnable {
            final /* synthetic */ ListenerExecutorPair val$pair;

            C03215(ListenerExecutorPair listenerExecutorPair) {
                this.val$pair = listenerExecutorPair;
            }

            public void run() {
                this.val$pair.listener.stopped();
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.6 */
        class C03226 implements Runnable {
            final /* synthetic */ ListenerExecutorPair val$pair;

            C03226(ListenerExecutorPair listenerExecutorPair) {
                this.val$pair = listenerExecutorPair;
            }

            public void run() {
                this.val$pair.listener.healthy();
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.7 */
        class C03237 implements Runnable {
            final /* synthetic */ ListenerExecutorPair val$pair;
            final /* synthetic */ Service val$service;

            C03237(ListenerExecutorPair listenerExecutorPair, Service service) {
                this.val$pair = listenerExecutorPair;
                this.val$service = service;
            }

            public void run() {
                this.val$pair.listener.failure(this.val$service);
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.1 */
        class C08271 implements Supplier<Set<Service>> {
            C08271() {
            }

            public Set<Service> get() {
                return Sets.newLinkedHashSet();
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.2 */
        class C08282 extends Guard {
            C08282(Monitor x0) {
                super(x0);
            }

            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(State.RUNNING) == ServiceManagerState.this.numberOfServices || ServiceManagerState.this.states.contains(State.STOPPING) || ServiceManagerState.this.states.contains(State.TERMINATED) || ServiceManagerState.this.states.contains(State.FAILED);
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.3 */
        class C08293 extends Guard {
            C08293(Monitor x0) {
                super(x0);
            }

            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(State.TERMINATED) + ServiceManagerState.this.states.count(State.FAILED) == ServiceManagerState.this.numberOfServices;
            }
        }

        /* renamed from: com.google.common.util.concurrent.ServiceManager.ServiceManagerState.4 */
        class C08304 implements Function<Entry<Service, Long>, Long> {
            C08304() {
            }

            public Long apply(Entry<Service, Long> input) {
                return (Long) input.getValue();
            }
        }

        ServiceManagerState(ImmutableCollection<Service> services) {
            this.monitor = new Monitor();
            this.servicesByState = Multimaps.newSetMultimap(new EnumMap(State.class), new C08271());
            this.states = this.servicesByState.keys();
            this.startupTimers = Maps.newIdentityHashMap();
            this.awaitHealthGuard = new C08282(this.monitor);
            this.stoppedGuard = new C08293(this.monitor);
            this.listeners = Lists.newArrayList();
            this.queuedListeners = new ExecutionQueue();
            this.numberOfServices = services.size();
            this.servicesByState.putAll(State.NEW, services);
            Iterator i$ = services.iterator();
            while (i$.hasNext()) {
                this.startupTimers.put((Service) i$.next(), Stopwatch.createUnstarted());
            }
        }

        void markReady() {
            this.monitor.enter();
            try {
                if (this.transitioned) {
                    List<Service> servicesInBadStates = Lists.newArrayList();
                    Iterator i$ = servicesByState().values().iterator();
                    while (i$.hasNext()) {
                        Service service = (Service) i$.next();
                        if (service.state() != State.NEW) {
                            servicesInBadStates.add(service);
                        }
                    }
                    throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + servicesInBadStates);
                }
                this.ready = true;
            } finally {
                this.monitor.leave();
            }
        }

        void addListener(Listener listener, Executor executor) {
            Preconditions.checkNotNull(listener, "listener");
            Preconditions.checkNotNull(executor, "executor");
            this.monitor.enter();
            try {
                if (!this.stoppedGuard.isSatisfied()) {
                    this.listeners.add(new ListenerExecutorPair(listener, executor));
                }
                this.monitor.leave();
            } catch (Throwable th) {
                this.monitor.leave();
            }
        }

        void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
                    checkHealthy();
                    return;
                }
                throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(State.NEW, State.STARTING))));
            } finally {
                this.monitor.leave();
            }
        }

        void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
                    throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(ImmutableSet.of(State.TERMINATED, State.FAILED)))));
                }
            } finally {
                this.monitor.leave();
            }
        }

        ImmutableMultimap<State, Service> servicesByState() {
            Builder<State, Service> builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Entry<State, Service> entry : this.servicesByState.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put(entry.getKey(), entry.getValue());
                    }
                }
                return builder.build();
            } finally {
                this.monitor.leave();
            }
        }

        ImmutableMap<Service, Long> startupTimes() {
            this.monitor.enter();
            try {
                List<Entry<Service, Long>> loadTimes = Lists.newArrayListWithCapacity((this.states.size() - this.states.count(State.NEW)) + this.states.count(State.STARTING));
                for (Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopWatch = (Stopwatch) entry.getValue();
                    if (!(stopWatch.isRunning() || this.servicesByState.containsEntry(State.NEW, service) || (service instanceof NoOpService))) {
                        loadTimes.add(Maps.immutableEntry(service, Long.valueOf(stopWatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                Collections.sort(loadTimes, Ordering.natural().onResultOf(new C08304()));
                ImmutableMap.Builder<Service, Long> builder = ImmutableMap.builder();
                for (Entry<Service, Long> entry2 : loadTimes) {
                    builder.put(entry2);
                }
                return builder.build();
            } finally {
                this.monitor.leave();
            }
        }

        void transitionService(Service service, State from, State to) {
            boolean z = true;
            Preconditions.checkNotNull(service);
            if (from == to) {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (this.ready) {
                    Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", service, from);
                    Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", service, to);
                    Stopwatch stopwatch = (Stopwatch) this.startupTimers.get(service);
                    if (from == State.NEW) {
                        stopwatch.start();
                    }
                    if (to.compareTo(State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (to == State.FAILED) {
                        fireFailedListeners(service);
                    }
                    if (this.states.count(State.RUNNING) == this.numberOfServices) {
                        fireHealthyListeners();
                    } else if (this.states.count(State.TERMINATED) + this.states.count(State.FAILED) == this.numberOfServices) {
                        fireStoppedListeners();
                        this.listeners.clear();
                    }
                    this.monitor.leave();
                    executeListeners();
                }
            } finally {
                this.monitor.leave();
                executeListeners();
            }
        }

        @GuardedBy("monitor")
        void fireStoppedListeners() {
            for (ListenerExecutorPair pair : this.listeners) {
                this.queuedListeners.add(new C03215(pair), pair.executor);
            }
        }

        @GuardedBy("monitor")
        void fireHealthyListeners() {
            for (ListenerExecutorPair pair : this.listeners) {
                this.queuedListeners.add(new C03226(pair), pair.executor);
            }
        }

        @GuardedBy("monitor")
        void fireFailedListeners(Service service) {
            for (ListenerExecutorPair pair : this.listeners) {
                this.queuedListeners.add(new C03237(pair, service), pair.executor);
            }
        }

        void executeListeners() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            this.queuedListeners.execute();
        }

        @GuardedBy("monitor")
        void checkHealthy() {
            if (this.states.count(State.RUNNING) != this.numberOfServices) {
                throw new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(State.RUNNING))));
            }
        }
    }

    private static final class SynchronizedExecutor implements Executor {
        private SynchronizedExecutor() {
        }

        public synchronized void execute(Runnable command) {
            command.run();
        }
    }

    private static final class ServiceListener extends com.google.common.util.concurrent.Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service, WeakReference<ServiceManagerState> state) {
            this.service = service;
            this.state = state;
        }

        public void starting() {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, State.NEW, State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
                }
            }
        }

        public void running() {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, State.STARTING, State.RUNNING);
            }
        }

        public void stopping(State from) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, from, State.STOPPING);
            }
        }

        public void terminated(State from) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, from});
                }
                state.transitionService(this.service, from, State.TERMINATED);
            }
        }

        public void failed(State from, Throwable failure) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.SEVERE, "Service " + this.service + " has failed in the " + from + " state.", failure);
                }
                state.transitionService(this.service, from, State.FAILED);
            }
        }
    }

    private static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        protected void doStart() {
            notifyStarted();
        }

        protected void doStop() {
            notifyStopped();
        }
    }

    static {
        logger = Logger.getLogger(ServiceManager.class.getName());
    }

    public ServiceManager(Iterable<? extends Service> services) {
        ImmutableList<Service> copy = ImmutableList.copyOf((Iterable) services);
        if (copy.isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
            copy = ImmutableList.of(new NoOpService());
        }
        this.state = new ServiceManagerState(copy);
        this.services = copy;
        WeakReference<ServiceManagerState> stateReference = new WeakReference(this.state);
        Iterator i$ = copy.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            service.addListener(new ServiceListener(service, stateReference), new SynchronizedExecutor());
            Preconditions.checkArgument(service.state() == State.NEW, "Can only manage NEW services, %s", service);
        }
        this.state.markReady();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.sameThreadExecutor());
    }

    public ServiceManager startAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            Preconditions.checkState(((Service) i$.next()).state() == State.NEW, "Service %s is %s, cannot start it.", (Service) i$.next(), ((Service) i$.next()).state());
        }
        i$ = this.services.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            try {
                service.startAsync();
            } catch (IllegalStateException e) {
                logger.log(Level.WARNING, "Unable to start Service " + service, e);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitHealthy(timeout, unit);
    }

    public ServiceManager stopAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            ((Service) i$.next()).stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitStopped(timeout, unit);
    }

    public boolean isHealthy() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            if (!((Service) i$.next()).isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public String toString() {
        return Objects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }
}
