package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

@Beta
public final class Futures {
    private static final AsyncFunction<ListenableFuture<Object>, Object> DEREFERENCER;
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST;

    /* renamed from: com.google.common.util.concurrent.Futures.2 */
    static class C03082 implements Future<O> {
        final /* synthetic */ Function val$function;
        final /* synthetic */ Future val$input;

        C03082(Future future, Function function) {
            this.val$input = future;
            this.val$function = function;
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            return this.val$input.cancel(mayInterruptIfRunning);
        }

        public boolean isCancelled() {
            return this.val$input.isCancelled();
        }

        public boolean isDone() {
            return this.val$input.isDone();
        }

        public O get() throws InterruptedException, ExecutionException {
            return applyTransformation(this.val$input.get());
        }

        public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return applyTransformation(this.val$input.get(timeout, unit));
        }

        private O applyTransformation(I input) throws ExecutionException {
            try {
                return this.val$function.apply(input);
            } catch (Throwable t) {
                ExecutionException executionException = new ExecutionException(t);
            }
        }
    }

    /* renamed from: com.google.common.util.concurrent.Futures.4 */
    static class C03094 implements Runnable {
        final /* synthetic */ FutureCallback val$callback;
        final /* synthetic */ ListenableFuture val$future;

        C03094(ListenableFuture listenableFuture, FutureCallback futureCallback) {
            this.val$future = listenableFuture;
            this.val$callback = futureCallback;
        }

        public void run() {
            try {
                this.val$callback.onSuccess(Uninterruptibles.getUninterruptibly(this.val$future));
            } catch (ExecutionException e) {
                this.val$callback.onFailure(e.getCause());
            } catch (RuntimeException e2) {
                this.val$callback.onFailure(e2);
            } catch (Error e3) {
                this.val$callback.onFailure(e3);
            }
        }
    }

    private interface FutureCombiner<V, C> {
        C combine(List<Optional<V>> list);
    }

    /* renamed from: com.google.common.util.concurrent.Futures.1 */
    static class C08131 implements AsyncFunction<I, O> {
        final /* synthetic */ Function val$function;

        C08131(Function function) {
            this.val$function = function;
        }

        public ListenableFuture<O> apply(I input) {
            return Futures.immediateFuture(this.val$function.apply(input));
        }
    }

    /* renamed from: com.google.common.util.concurrent.Futures.3 */
    static class C08143 implements AsyncFunction<ListenableFuture<Object>, Object> {
        C08143() {
        }

        public ListenableFuture<Object> apply(ListenableFuture<Object> input) {
            return input;
        }
    }

    /* renamed from: com.google.common.util.concurrent.Futures.5 */
    static class C08155 implements Function<Constructor<?>, Boolean> {
        C08155() {
        }

        public Boolean apply(Constructor<?> input) {
            return Boolean.valueOf(Arrays.asList(input.getParameterTypes()).contains(String.class));
        }
    }

    /* renamed from: com.google.common.util.concurrent.Futures.6 */
    static class C08166 implements FutureCombiner<V, List<V>> {
        C08166() {
        }

        public List<V> combine(List<Optional<V>> values) {
            List<V> result = Lists.newArrayList();
            for (Optional<V> element : values) {
                result.add(element != null ? element.orNull() : null);
            }
            return Collections.unmodifiableList(result);
        }
    }

    private static abstract class ImmediateFuture<V> implements ListenableFuture<V> {
        private static final Logger log;

        public abstract V get() throws ExecutionException;

        private ImmediateFuture() {
        }

        static {
            log = Logger.getLogger(ImmediateFuture.class.getName());
        }

        public void addListener(Runnable listener, Executor executor) {
            Preconditions.checkNotNull(listener, "Runnable was null.");
            Preconditions.checkNotNull(executor, "Executor was null.");
            try {
                executor.execute(listener);
            } catch (RuntimeException e) {
                log.log(Level.SEVERE, "RuntimeException while executing runnable " + listener + " with executor " + executor, e);
            }
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        public V get(long timeout, TimeUnit unit) throws ExecutionException {
            Preconditions.checkNotNull(unit);
            return get();
        }

        public boolean isCancelled() {
            return false;
        }

        public boolean isDone() {
            return true;
        }
    }

    private static class ChainingListenableFuture<I, O> extends AbstractFuture<O> implements Runnable {
        private AsyncFunction<? super I, ? extends O> function;
        private ListenableFuture<? extends I> inputFuture;
        private final CountDownLatch outputCreated;
        private volatile ListenableFuture<? extends O> outputFuture;

        /* renamed from: com.google.common.util.concurrent.Futures.ChainingListenableFuture.1 */
        class C03101 implements Runnable {
            final /* synthetic */ ListenableFuture val$outputFuture;

            C03101(ListenableFuture listenableFuture) {
                this.val$outputFuture = listenableFuture;
            }

            public void run() {
                try {
                    ChainingListenableFuture.this.set(Uninterruptibles.getUninterruptibly(this.val$outputFuture));
                } catch (CancellationException e) {
                    ChainingListenableFuture.this.cancel(false);
                } catch (ExecutionException e2) {
                    ChainingListenableFuture.this.setException(e2.getCause());
                } finally {
                    ChainingListenableFuture.this.outputFuture = null;
                }
            }
        }

        private ChainingListenableFuture(AsyncFunction<? super I, ? extends O> function, ListenableFuture<? extends I> inputFuture) {
            this.outputCreated = new CountDownLatch(1);
            this.function = (AsyncFunction) Preconditions.checkNotNull(function);
            this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(inputFuture);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            if (!super.cancel(mayInterruptIfRunning)) {
                return false;
            }
            cancel(this.inputFuture, mayInterruptIfRunning);
            cancel(this.outputFuture, mayInterruptIfRunning);
            return true;
        }

        private void cancel(@Nullable Future<?> future, boolean mayInterruptIfRunning) {
            if (future != null) {
                future.cancel(mayInterruptIfRunning);
            }
        }

        public void run() {
            try {
                try {
                    ListenableFuture<? extends O> outputFuture = this.function.apply(Uninterruptibles.getUninterruptibly(this.inputFuture));
                    this.outputFuture = outputFuture;
                    if (isCancelled()) {
                        outputFuture.cancel(wasInterrupted());
                        this.outputFuture = null;
                        return;
                    }
                    outputFuture.addListener(new C03101(outputFuture), MoreExecutors.sameThreadExecutor());
                    this.function = null;
                    this.inputFuture = null;
                    this.outputCreated.countDown();
                } catch (UndeclaredThrowableException e) {
                    setException(e.getCause());
                } catch (Throwable t) {
                    setException(t);
                } finally {
                    this.function = null;
                    this.inputFuture = null;
                    this.outputCreated.countDown();
                }
            } catch (CancellationException e2) {
                cancel(false);
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
            } catch (ExecutionException e3) {
                setException(e3.getCause());
                this.function = null;
                this.inputFuture = null;
                this.outputCreated.countDown();
            }
        }
    }

    private static class CombinedFuture<V, C> extends AbstractFuture<C> {
        private static final Logger logger;
        final boolean allMustSucceed;
        FutureCombiner<V, C> combiner;
        ImmutableCollection<? extends ListenableFuture<? extends V>> futures;
        final AtomicInteger remaining;
        Set<Throwable> seenExceptions;
        final Object seenExceptionsLock;
        List<Optional<V>> values;

        /* renamed from: com.google.common.util.concurrent.Futures.CombinedFuture.1 */
        class C03111 implements Runnable {
            C03111() {
            }

            public void run() {
                if (CombinedFuture.this.isCancelled()) {
                    Iterator i$ = CombinedFuture.this.futures.iterator();
                    while (i$.hasNext()) {
                        ((ListenableFuture) i$.next()).cancel(CombinedFuture.this.wasInterrupted());
                    }
                }
                CombinedFuture.this.futures = null;
                CombinedFuture.this.values = null;
                CombinedFuture.this.combiner = null;
            }
        }

        /* renamed from: com.google.common.util.concurrent.Futures.CombinedFuture.2 */
        class C03122 implements Runnable {
            final /* synthetic */ int val$index;
            final /* synthetic */ ListenableFuture val$listenable;

            C03122(int i, ListenableFuture listenableFuture) {
                this.val$index = i;
                this.val$listenable = listenableFuture;
            }

            public void run() {
                CombinedFuture.this.setOneValue(this.val$index, this.val$listenable);
            }
        }

        static {
            logger = Logger.getLogger(CombinedFuture.class.getName());
        }

        CombinedFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed, Executor listenerExecutor, FutureCombiner<V, C> combiner) {
            this.seenExceptionsLock = new Object();
            this.futures = futures;
            this.allMustSucceed = allMustSucceed;
            this.remaining = new AtomicInteger(futures.size());
            this.combiner = combiner;
            this.values = Lists.newArrayListWithCapacity(futures.size());
            init(listenerExecutor);
        }

        protected void init(Executor listenerExecutor) {
            addListener(new C03111(), MoreExecutors.sameThreadExecutor());
            if (this.futures.isEmpty()) {
                set(this.combiner.combine(ImmutableList.of()));
                return;
            }
            int i;
            for (i = 0; i < this.futures.size(); i++) {
                this.values.add(null);
            }
            i = 0;
            Iterator i$ = this.futures.iterator();
            while (i$.hasNext()) {
                ListenableFuture<? extends V> listenable = (ListenableFuture) i$.next();
                int i2 = i + 1;
                listenable.addListener(new C03122(i, listenable), listenerExecutor);
                i = i2;
            }
        }

        private void setExceptionAndMaybeLog(Throwable throwable) {
            boolean visibleFromOutputFuture = false;
            boolean firstTimeSeeingThisException = true;
            if (this.allMustSucceed) {
                visibleFromOutputFuture = super.setException(throwable);
                synchronized (this.seenExceptionsLock) {
                    if (this.seenExceptions == null) {
                        this.seenExceptions = Sets.newHashSet();
                    }
                    firstTimeSeeingThisException = this.seenExceptions.add(throwable);
                }
            }
            if ((throwable instanceof Error) || (this.allMustSucceed && !visibleFromOutputFuture && firstTimeSeeingThisException)) {
                logger.log(Level.SEVERE, "input future failed.", throwable);
            }
        }

        private void setOneValue(int index, Future<? extends V> future) {
            int newRemaining;
            FutureCombiner<V, C> localCombiner;
            boolean z = true;
            List<Optional<V>> localValues = this.values;
            if (isDone() || localValues == null) {
                boolean z2;
                if (this.allMustSucceed || isCancelled()) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                Preconditions.checkState(z2, "Future was done before all dependencies completed");
            }
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                V returnValue = Uninterruptibles.getUninterruptibly(future);
                if (localValues != null) {
                    localValues.set(index, Optional.fromNullable(returnValue));
                }
                newRemaining = this.remaining.decrementAndGet();
                if (newRemaining < 0) {
                    z = false;
                }
                Preconditions.checkState(z, "Less than 0 remaining futures");
                if (newRemaining == 0) {
                    localCombiner = this.combiner;
                    if (localCombiner == null || localValues == null) {
                        Preconditions.checkState(isDone());
                    } else {
                        set(localCombiner.combine(localValues));
                    }
                }
            } catch (CancellationException e) {
                if (this.allMustSucceed) {
                    cancel(false);
                }
                newRemaining = this.remaining.decrementAndGet();
                if (newRemaining < 0) {
                    z = false;
                }
                Preconditions.checkState(z, "Less than 0 remaining futures");
                if (newRemaining == 0) {
                    localCombiner = this.combiner;
                    if (localCombiner == null || localValues == null) {
                        Preconditions.checkState(isDone());
                    } else {
                        set(localCombiner.combine(localValues));
                    }
                }
            } catch (ExecutionException e2) {
                setExceptionAndMaybeLog(e2.getCause());
                newRemaining = this.remaining.decrementAndGet();
                if (newRemaining < 0) {
                    z = false;
                }
                Preconditions.checkState(z, "Less than 0 remaining futures");
                if (newRemaining == 0) {
                    localCombiner = this.combiner;
                    if (localCombiner == null || localValues == null) {
                        Preconditions.checkState(isDone());
                    } else {
                        set(localCombiner.combine(localValues));
                    }
                }
            } catch (Throwable th) {
                newRemaining = this.remaining.decrementAndGet();
                if (newRemaining < 0) {
                    z = false;
                }
                Preconditions.checkState(z, "Less than 0 remaining futures");
                if (newRemaining == 0) {
                    localCombiner = this.combiner;
                    if (localCombiner == null || localValues == null) {
                        Preconditions.checkState(isDone());
                    } else {
                        set(localCombiner.combine(localValues));
                    }
                }
            }
        }
    }

    private static class FallbackFuture<V> extends AbstractFuture<V> {
        private volatile ListenableFuture<? extends V> running;

        /* renamed from: com.google.common.util.concurrent.Futures.FallbackFuture.1 */
        class C08181 implements FutureCallback<V> {
            final /* synthetic */ FutureFallback val$fallback;

            /* renamed from: com.google.common.util.concurrent.Futures.FallbackFuture.1.1 */
            class C08171 implements FutureCallback<V> {
                C08171() {
                }

                public void onSuccess(V value) {
                    FallbackFuture.this.set(value);
                }

                public void onFailure(Throwable t) {
                    if (FallbackFuture.this.running.isCancelled()) {
                        FallbackFuture.this.cancel(false);
                    } else {
                        FallbackFuture.this.setException(t);
                    }
                }
            }

            C08181(FutureFallback futureFallback) {
                this.val$fallback = futureFallback;
            }

            public void onSuccess(V value) {
                FallbackFuture.this.set(value);
            }

            public void onFailure(Throwable t) {
                if (!FallbackFuture.this.isCancelled()) {
                    try {
                        FallbackFuture.this.running = this.val$fallback.create(t);
                        if (FallbackFuture.this.isCancelled()) {
                            FallbackFuture.this.running.cancel(FallbackFuture.this.wasInterrupted());
                        } else {
                            Futures.addCallback(FallbackFuture.this.running, new C08171(), MoreExecutors.sameThreadExecutor());
                        }
                    } catch (Throwable e) {
                        FallbackFuture.this.setException(e);
                    }
                }
            }
        }

        FallbackFuture(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback, Executor executor) {
            this.running = input;
            Futures.addCallback(this.running, new C08181(fallback), executor);
        }

        public boolean cancel(boolean mayInterruptIfRunning) {
            if (!super.cancel(mayInterruptIfRunning)) {
                return false;
            }
            this.running.cancel(mayInterruptIfRunning);
            return true;
        }
    }

    private static class ImmediateCancelledFuture<V> extends ImmediateFuture<V> {
        private final CancellationException thrown;

        ImmediateCancelledFuture() {
            super();
            this.thrown = new CancellationException("Immediate cancelled future.");
        }

        public boolean isCancelled() {
            return true;
        }

        public V get() {
            throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.thrown);
        }
    }

    private static class ImmediateFailedCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        private final X thrown;

        ImmediateFailedCheckedFuture(X thrown) {
            super();
            this.thrown = thrown;
        }

        public V get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }

        public V checkedGet() throws Exception {
            throw this.thrown;
        }

        public V checkedGet(long timeout, TimeUnit unit) throws Exception {
            Preconditions.checkNotNull(unit);
            throw this.thrown;
        }
    }

    private static class ImmediateFailedFuture<V> extends ImmediateFuture<V> {
        private final Throwable thrown;

        ImmediateFailedFuture(Throwable thrown) {
            super();
            this.thrown = thrown;
        }

        public V get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }
    }

    private static class ImmediateSuccessfulCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        @Nullable
        private final V value;

        ImmediateSuccessfulCheckedFuture(@Nullable V value) {
            super();
            this.value = value;
        }

        public V get() {
            return this.value;
        }

        public V checkedGet() {
            return this.value;
        }

        public V checkedGet(long timeout, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            return this.value;
        }
    }

    private static class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        @Nullable
        private final V value;

        ImmediateSuccessfulFuture(@Nullable V value) {
            super();
            this.value = value;
        }

        public V get() {
            return this.value;
        }
    }

    private static class NonCancellationPropagatingFuture<V> extends AbstractFuture<V> {

        /* renamed from: com.google.common.util.concurrent.Futures.NonCancellationPropagatingFuture.1 */
        class C08191 implements FutureCallback<V> {
            final /* synthetic */ ListenableFuture val$delegate;

            C08191(ListenableFuture listenableFuture) {
                this.val$delegate = listenableFuture;
            }

            public void onSuccess(V result) {
                NonCancellationPropagatingFuture.this.set(result);
            }

            public void onFailure(Throwable t) {
                if (this.val$delegate.isCancelled()) {
                    NonCancellationPropagatingFuture.this.cancel(false);
                } else {
                    NonCancellationPropagatingFuture.this.setException(t);
                }
            }
        }

        NonCancellationPropagatingFuture(ListenableFuture<V> delegate) {
            Preconditions.checkNotNull(delegate);
            Futures.addCallback(delegate, new C08191(delegate), MoreExecutors.sameThreadExecutor());
        }
    }

    private static class MappingCheckedFuture<V, X extends Exception> extends AbstractCheckedFuture<V, X> {
        final Function<Exception, X> mapper;

        MappingCheckedFuture(ListenableFuture<V> delegate, Function<Exception, X> mapper) {
            super(delegate);
            this.mapper = (Function) Preconditions.checkNotNull(mapper);
        }

        protected X mapException(Exception e) {
            return (Exception) this.mapper.apply(e);
        }
    }

    private Futures() {
    }

    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<Exception, X> mapper) {
        return new MappingCheckedFuture((ListenableFuture) Preconditions.checkNotNull(future), mapper);
    }

    public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
        return new ImmediateSuccessfulFuture(value);
    }

    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable V value) {
        return new ImmediateSuccessfulCheckedFuture(value);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new ImmediateFailedFuture(throwable);
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new ImmediateCancelledFuture();
    }

    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X exception) {
        Preconditions.checkNotNull(exception);
        return new ImmediateFailedCheckedFuture(exception);
    }

    public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback) {
        return withFallback(input, fallback, MoreExecutors.sameThreadExecutor());
    }

    public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback, Executor executor) {
        Preconditions.checkNotNull(fallback);
        return new FallbackFuture(input, fallback, executor);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
        return transform((ListenableFuture) input, (AsyncFunction) function, MoreExecutors.sameThreadExecutor());
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
        ChainingListenableFuture<I, O> output = new ChainingListenableFuture(input, null);
        input.addListener(output, executor);
        return output;
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function) {
        return transform((ListenableFuture) input, (Function) function, MoreExecutors.sameThreadExecutor());
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        return transform((ListenableFuture) input, new C08131(function), executor);
    }

    public static <I, O> Future<O> lazyTransform(Future<I> input, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(function);
        return new C03082(input, function);
    }

    public static <V> ListenableFuture<V> dereference(ListenableFuture<? extends ListenableFuture<? extends V>> nested) {
        return transform((ListenableFuture) nested, DEREFERENCER);
    }

    static {
        DEREFERENCER = new C08143();
        WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new C08155()).reverse();
    }

    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
        return listFuture(ImmutableList.copyOf((Object[]) futures), true, MoreExecutors.sameThreadExecutor());
    }

    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return listFuture(ImmutableList.copyOf((Iterable) futures), true, MoreExecutors.sameThreadExecutor());
    }

    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> future) {
        return new NonCancellationPropagatingFuture(future);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
        return listFuture(ImmutableList.copyOf((Object[]) futures), false, MoreExecutors.sameThreadExecutor());
    }

    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return listFuture(ImmutableList.copyOf((Iterable) futures), false, MoreExecutors.sameThreadExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback) {
        addCallback(future, callback, MoreExecutors.sameThreadExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback, Executor executor) {
        Preconditions.checkNotNull(callback);
        future.addListener(new C03094(future, callback), executor);
    }

    public static <V, X extends Exception> V get(Future<V> future, Class<X> exceptionClass) throws Exception {
        boolean z;
        Preconditions.checkNotNull(future);
        if (RuntimeException.class.isAssignableFrom(exceptionClass)) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (ExecutionException e2) {
            wrapAndThrowExceptionOrError(e2.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    public static <V, X extends Exception> V get(Future<V> future, long timeout, TimeUnit unit, Class<X> exceptionClass) throws Exception {
        boolean z;
        Preconditions.checkNotNull(future);
        Preconditions.checkNotNull(unit);
        if (RuntimeException.class.isAssignableFrom(exceptionClass)) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z, "Futures.get exception type (%s) must not be a RuntimeException", exceptionClass);
        try {
            return future.get(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (TimeoutException e2) {
            throw newWithCause(exceptionClass, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable cause, Class<X> exceptionClass) throws Exception {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        } else if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        } else {
            throw newWithCause(exceptionClass, cause);
        }
    }

    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e) {
            wrapAndThrowUnchecked(e.getCause());
            throw new AssertionError();
        }
    }

    private static void wrapAndThrowUnchecked(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }

    private static <X extends Exception> X newWithCause(Class<X> exceptionClass, Throwable cause) {
        for (Constructor<X> constructor : preferringStrings(Arrays.asList(exceptionClass.getConstructors()))) {
            Exception instance = (Exception) newFromConstructor(constructor, cause);
            if (instance != null) {
                if (instance.getCause() == null) {
                    instance.initCause(cause);
                }
                return instance;
            }
        }
        throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> constructors) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
    }

    @Nullable
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable cause) {
        X x = null;
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if (!paramType.equals(String.class)) {
                if (!paramType.equals(Throwable.class)) {
                    break;
                }
                params[i] = cause;
            } else {
                params[i] = cause.toString();
            }
        }
        try {
            x = constructor.newInstance(params);
        } catch (IllegalArgumentException e) {
        } catch (InstantiationException e2) {
        } catch (IllegalAccessException e3) {
        } catch (InvocationTargetException e4) {
        }
        return x;
    }

    private static <V> ListenableFuture<List<V>> listFuture(ImmutableList<ListenableFuture<? extends V>> futures, boolean allMustSucceed, Executor listenerExecutor) {
        return new CombinedFuture(futures, allMustSucceed, listenerExecutor, new C08166());
    }
}
