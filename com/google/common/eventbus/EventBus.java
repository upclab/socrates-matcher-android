package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

@Beta
public class EventBus {
    private static final LoadingCache<Class<?>, Set<Class<?>>> flattenHierarchyCache;
    private final ThreadLocal<Queue<EventWithSubscriber>> eventsToDispatch;
    private final SubscriberFindingStrategy finder;
    private final ThreadLocal<Boolean> isDispatching;
    private SubscriberExceptionHandler subscriberExceptionHandler;
    private final SetMultimap<Class<?>, EventSubscriber> subscribersByType;
    private final ReadWriteLock subscribersByTypeLock;

    /* renamed from: com.google.common.eventbus.EventBus.2 */
    class C02712 extends ThreadLocal<Queue<EventWithSubscriber>> {
        C02712() {
        }

        protected Queue<EventWithSubscriber> initialValue() {
            return new LinkedList();
        }
    }

    /* renamed from: com.google.common.eventbus.EventBus.3 */
    class C02723 extends ThreadLocal<Boolean> {
        C02723() {
        }

        protected Boolean initialValue() {
            return Boolean.valueOf(false);
        }
    }

    static class EventWithSubscriber {
        final Object event;
        final EventSubscriber subscriber;

        public EventWithSubscriber(Object event, EventSubscriber subscriber) {
            this.event = Preconditions.checkNotNull(event);
            this.subscriber = (EventSubscriber) Preconditions.checkNotNull(subscriber);
        }
    }

    /* renamed from: com.google.common.eventbus.EventBus.1 */
    static class C07661 extends CacheLoader<Class<?>, Set<Class<?>>> {
        C07661() {
        }

        public Set<Class<?>> load(Class<?> concreteClass) {
            return TypeToken.of((Class) concreteClass).getTypes().rawTypes();
        }
    }

    private static final class LoggingSubscriberExceptionHandler implements SubscriberExceptionHandler {
        private final Logger logger;

        public LoggingSubscriberExceptionHandler(String identifier) {
            this.logger = Logger.getLogger(EventBus.class.getName() + "." + ((String) Preconditions.checkNotNull(identifier)));
        }

        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            this.logger.log(Level.SEVERE, "Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod(), exception.getCause());
        }
    }

    static {
        flattenHierarchyCache = CacheBuilder.newBuilder().weakKeys().build(new C07661());
    }

    public EventBus() {
        this("default");
    }

    public EventBus(String identifier) {
        this(new LoggingSubscriberExceptionHandler(identifier));
    }

    public EventBus(SubscriberExceptionHandler subscriberExceptionHandler) {
        this.subscribersByType = HashMultimap.create();
        this.subscribersByTypeLock = new ReentrantReadWriteLock();
        this.finder = new AnnotatedSubscriberFinder();
        this.eventsToDispatch = new C02712();
        this.isDispatching = new C02723();
        this.subscriberExceptionHandler = (SubscriberExceptionHandler) Preconditions.checkNotNull(subscriberExceptionHandler);
    }

    public void register(Object object) {
        Multimap<Class<?>, EventSubscriber> methodsInListener = this.finder.findAllSubscribers(object);
        this.subscribersByTypeLock.writeLock().lock();
        try {
            this.subscribersByType.putAll(methodsInListener);
        } finally {
            this.subscribersByTypeLock.writeLock().unlock();
        }
    }

    public void unregister(Object object) {
        for (Entry<Class<?>, Collection<EventSubscriber>> entry : this.finder.findAllSubscribers(object).asMap().entrySet()) {
            Class<?> eventType = (Class) entry.getKey();
            Collection<EventSubscriber> eventMethodsInListener = (Collection) entry.getValue();
            this.subscribersByTypeLock.writeLock().lock();
            try {
                Set<EventSubscriber> currentSubscribers = this.subscribersByType.get(eventType);
                if (currentSubscribers.containsAll(eventMethodsInListener)) {
                    currentSubscribers.removeAll(eventMethodsInListener);
                    this.subscribersByTypeLock.writeLock().unlock();
                } else {
                    throw new IllegalArgumentException("missing event subscriber for an annotated method. Is " + object + " registered?");
                }
            } catch (Throwable th) {
                this.subscribersByTypeLock.writeLock().unlock();
            }
        }
    }

    public void post(Object event) {
        boolean dispatched = false;
        for (Class<?> eventType : flattenHierarchy(event.getClass())) {
            this.subscribersByTypeLock.readLock().lock();
            try {
                Set<EventSubscriber> wrappers = this.subscribersByType.get(eventType);
                if (!wrappers.isEmpty()) {
                    dispatched = true;
                    for (EventSubscriber wrapper : wrappers) {
                        enqueueEvent(event, wrapper);
                    }
                }
                this.subscribersByTypeLock.readLock().unlock();
            } catch (Throwable th) {
                this.subscribersByTypeLock.readLock().unlock();
            }
        }
        if (!(dispatched || (event instanceof DeadEvent))) {
            post(new DeadEvent(this, event));
        }
        dispatchQueuedEvents();
    }

    void enqueueEvent(Object event, EventSubscriber subscriber) {
        ((Queue) this.eventsToDispatch.get()).offer(new EventWithSubscriber(event, subscriber));
    }

    void dispatchQueuedEvents() {
        if (!((Boolean) this.isDispatching.get()).booleanValue()) {
            this.isDispatching.set(Boolean.valueOf(true));
            try {
                Queue<EventWithSubscriber> events = (Queue) this.eventsToDispatch.get();
                while (true) {
                    EventWithSubscriber eventWithSubscriber = (EventWithSubscriber) events.poll();
                    if (eventWithSubscriber == null) {
                        break;
                    }
                    dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
                }
            } finally {
                this.isDispatching.remove();
                this.eventsToDispatch.remove();
            }
        }
    }

    void dispatch(Object event, EventSubscriber wrapper) {
        try {
            wrapper.handleEvent(event);
        } catch (InvocationTargetException e) {
            this.subscriberExceptionHandler.handleException(e.getCause(), new SubscriberExceptionContext(this, event, wrapper.getSubscriber(), wrapper.getMethod()));
        } catch (Throwable t) {
            Logger.getLogger(EventBus.class.getName()).log(Level.SEVERE, String.format("Exception %s thrown while handling exception: %s", new Object[]{t, e.getCause()}), t);
        }
    }

    @VisibleForTesting
    Set<Class<?>> flattenHierarchy(Class<?> concreteClass) {
        try {
            return (Set) flattenHierarchyCache.getUnchecked(concreteClass);
        } catch (UncheckedExecutionException e) {
            throw Throwables.propagate(e.getCause());
        }
    }
}
