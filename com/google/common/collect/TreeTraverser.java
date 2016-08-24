package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@GwtCompatible(emulated = true)
@Beta
public abstract class TreeTraverser<T> {

    private static final class PostOrderNode<T> {
        final Iterator<T> childIterator;
        final T root;

        PostOrderNode(T root, Iterator<T> childIterator) {
            this.root = Preconditions.checkNotNull(root);
            this.childIterator = (Iterator) Preconditions.checkNotNull(childIterator);
        }
    }

    /* renamed from: com.google.common.collect.TreeTraverser.1 */
    class C07591 extends FluentIterable<T> {
        final /* synthetic */ Object val$root;

        C07591(Object obj) {
            this.val$root = obj;
        }

        public UnmodifiableIterator<T> iterator() {
            return TreeTraverser.this.preOrderIterator(this.val$root);
        }
    }

    /* renamed from: com.google.common.collect.TreeTraverser.2 */
    class C07602 extends FluentIterable<T> {
        final /* synthetic */ Object val$root;

        C07602(Object obj) {
            this.val$root = obj;
        }

        public UnmodifiableIterator<T> iterator() {
            return TreeTraverser.this.postOrderIterator(this.val$root);
        }
    }

    /* renamed from: com.google.common.collect.TreeTraverser.3 */
    class C07613 extends FluentIterable<T> {
        final /* synthetic */ Object val$root;

        C07613(Object obj) {
            this.val$root = obj;
        }

        public UnmodifiableIterator<T> iterator() {
            return new BreadthFirstIterator(this.val$root);
        }
    }

    private final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        private final Queue<T> queue;

        BreadthFirstIterator(T root) {
            this.queue = new ArrayDeque();
            this.queue.add(root);
        }

        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        public T peek() {
            return this.queue.element();
        }

        public T next() {
            T result = this.queue.remove();
            Iterables.addAll(this.queue, TreeTraverser.this.children(result));
            return result;
        }
    }

    private final class PreOrderIterator extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> stack;

        PreOrderIterator(T root) {
            this.stack = new ArrayDeque();
            this.stack.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(root)));
        }

        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        public T next() {
            Iterator<T> itr = (Iterator) this.stack.getLast();
            T result = Preconditions.checkNotNull(itr.next());
            if (!itr.hasNext()) {
                this.stack.removeLast();
            }
            Iterator<T> childItr = TreeTraverser.this.children(result).iterator();
            if (childItr.hasNext()) {
                this.stack.addLast(childItr);
            }
            return result;
        }
    }

    private final class PostOrderIterator extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> stack;

        PostOrderIterator(T root) {
            this.stack = new ArrayDeque();
            this.stack.addLast(expand(root));
        }

        protected T computeNext() {
            while (!this.stack.isEmpty()) {
                PostOrderNode<T> top = (PostOrderNode) this.stack.getLast();
                if (top.childIterator.hasNext()) {
                    this.stack.addLast(expand(top.childIterator.next()));
                } else {
                    this.stack.removeLast();
                    return top.root;
                }
            }
            return endOfData();
        }

        private PostOrderNode<T> expand(T t) {
            return new PostOrderNode(t, TreeTraverser.this.children(t).iterator());
        }
    }

    public abstract Iterable<T> children(T t);

    public final FluentIterable<T> preOrderTraversal(T root) {
        Preconditions.checkNotNull(root);
        return new C07591(root);
    }

    UnmodifiableIterator<T> preOrderIterator(T root) {
        return new PreOrderIterator(root);
    }

    public final FluentIterable<T> postOrderTraversal(T root) {
        Preconditions.checkNotNull(root);
        return new C07602(root);
    }

    UnmodifiableIterator<T> postOrderIterator(T root) {
        return new PostOrderIterator(root);
    }

    public final FluentIterable<T> breadthFirstTraversal(T root) {
        Preconditions.checkNotNull(root);
        return new C07613(root);
    }
}
