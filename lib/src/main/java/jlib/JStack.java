package jlib;

import java.util.Optional;

/**
 * A very basis Stack implementation on top
 * of a doubly linked list.
 */
public class JStack<T> {
    private final JList<T> list;

    public JStack() {
        this.list = new JList<T>();
    }

    /**
     * Pop of the top element of the stack.
     *
     * @return An optional containing the top element of the stack. The
     * optional will be empty if the stack is empty.
     */
    public Optional<T> pop() {
        return this.list.pop();
    }

    /**
     * Return the top element of the stack without removing it.
     *
     * @return An optional containing the top element of the stack. Empty
     * optional if the stack is empty.
     */
    public Optional<T> peek() {
        final Optional<JListNode<T>> node = this.list.tail();
        if (node.isEmpty())
            return Optional.empty();
        return Optional.of(node.get().getData());
    }

    /**
     * Push an element onto the stack.
     *
     */
    public void push(final T data) {
        this.list.add(data);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}
