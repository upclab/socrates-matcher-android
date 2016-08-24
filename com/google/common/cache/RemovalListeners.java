package com.google.common.cache;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;

@Beta
public final class RemovalListeners {

    /* renamed from: com.google.common.cache.RemovalListeners.1 */
    static class C06521 implements RemovalListener<K, V> {
        final /* synthetic */ Executor val$executor;
        final /* synthetic */ RemovalListener val$listener;

        /* renamed from: com.google.common.cache.RemovalListeners.1.1 */
        class C02211 implements Runnable {
            final /* synthetic */ RemovalNotification val$notification;

            C02211(RemovalNotification removalNotification) {
                this.val$notification = removalNotification;
            }

            public void run() {
                C06521.this.val$listener.onRemoval(this.val$notification);
            }
        }

        C06521(Executor executor, RemovalListener removalListener) {
            this.val$executor = executor;
            this.val$listener = removalListener;
        }

        public void onRemoval(RemovalNotification<K, V> notification) {
            this.val$executor.execute(new C02211(notification));
        }
    }

    private RemovalListeners() {
    }

    public static <K, V> RemovalListener<K, V> asynchronous(RemovalListener<K, V> listener, Executor executor) {
        Preconditions.checkNotNull(listener);
        Preconditions.checkNotNull(executor);
        return new C06521(executor, listener);
    }
}
