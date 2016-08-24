package com.google.common.collect;

import com.google.analytics.midtier.proto.containertag.MutableTypeSystem.Value;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

@GwtCompatible
public abstract class AbstractIterator<T> extends UnmodifiableIterator<T> {
    private T next;
    private State state;

    /* renamed from: com.google.common.collect.AbstractIterator.1 */
    static /* synthetic */ class C02251 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$AbstractIterator$State;

        static {
            $SwitchMap$com$google$common$collect$AbstractIterator$State = new int[State.values().length];
            try {
                $SwitchMap$com$google$common$collect$AbstractIterator$State[State.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$AbstractIterator$State[State.READY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED
    }

    protected abstract T computeNext();

    protected AbstractIterator() {
        this.state = State.NOT_READY;
    }

    protected final T endOfData() {
        this.state = State.DONE;
        return null;
    }

    public final boolean hasNext() {
        Preconditions.checkState(this.state != State.FAILED);
        switch (C02251.$SwitchMap$com$google$common$collect$AbstractIterator$State[this.state.ordinal()]) {
            case Value.TYPE_FIELD_NUMBER /*1*/:
                return false;
            case Value.STRING_FIELD_NUMBER /*2*/:
                return true;
            default:
                return tryToComputeNext();
        }
    }

    private boolean tryToComputeNext() {
        this.state = State.FAILED;
        this.next = computeNext();
        if (this.state == State.DONE) {
            return false;
        }
        this.state = State.READY;
        return true;
    }

    public final T next() {
        if (hasNext()) {
            this.state = State.NOT_READY;
            T result = this.next;
            this.next = null;
            return result;
        }
        throw new NoSuchElementException();
    }

    public final T peek() {
        if (hasNext()) {
            return this.next;
        }
        throw new NoSuchElementException();
    }
}
