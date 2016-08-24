package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@Beta
public class AsyncEventBus extends EventBus {
    private final ConcurrentLinkedQueue<EventWithSubscriber> eventsToDispatch;
    private final Executor executor;

    /* renamed from: com.google.common.eventbus.AsyncEventBus.1 */
    class C02701 implements Runnable {
        final /* synthetic */ Object val$event;
        final /* synthetic */ EventSubscriber val$subscriber;

        C02701(Object obj, EventSubscriber eventSubscriber) {
            this.val$event = obj;
            this.val$subscriber = eventSubscriber;
        }

        public void run() {
            super.dispatch(this.val$event, this.val$subscriber);
        }
    }

    public AsyncEventBus(String identifier, Executor executor) {
        super(identifier);
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(subscriberExceptionHandler);
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    public AsyncEventBus(Executor executor) {
        super("default");
        this.eventsToDispatch = new ConcurrentLinkedQueue();
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    void enqueueEvent(Object event, EventSubscriber subscriber) {
        this.eventsToDispatch.offer(new EventWithSubscriber(event, subscriber));
    }

    protected void dispatchQueuedEvents() {
        while (true) {
            EventWithSubscriber eventWithSubscriber = (EventWithSubscriber) this.eventsToDispatch.poll();
            if (eventWithSubscriber != null) {
                dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
            } else {
                return;
            }
        }
    }

    void dispatch(Object event, EventSubscriber subscriber) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(subscriber);
        this.executor.execute(new C02701(event, subscriber));
    }
}
