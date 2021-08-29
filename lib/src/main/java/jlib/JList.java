package jlib;

import java.util.Iterator;
import java.util.Optional;

import lombok.ToString;

/*
 * A very simple doubly linked list implementation.
 */
@ToString
public class JList<T> implements Iterable<T> {

    private int size;
    private JListNode<T> head;
    private JListNode<T> tail;

    public JList() {
        this.size = 0;
        this.head = this.tail = null;
    }

    /*
     * Retrieves the data element at the given index.
     *
     * Watch out retrieving from a Linked List is o(n) time
     * complexity so use this method judiciusly.
     */
    public Optional<T> get(final int index) {
        if (index >= this.size())
            return Optional.empty();

        int count = 0;
        T val = null;
        for (final T element : this) {
            if (count == index) {
                val = element;
                break;
            }
            count++;
        }
        return Optional.ofNullable(val);
    }

    public Optional<JListNode<T>> head() {
        return Optional.ofNullable(this.head);
    }

    public Optional<JListNode<T>> tail() {
        return Optional.ofNullable(this.tail);
    }

    /*
     * Pop off the last element of the list and return it.
     */
    public Optional<T> pop() {
        if (this.size() == 0)
            return Optional.empty();

        final T val = this.tail.getData();
        final JListNode<T> previous = this.tail.getPrevious();

        // If previous is null that means we are poping 
        // the last element. So just revert to the state
        // of an empty list.
        if (previous == null) {
            this.head = this.tail = null;
        } else {
            this.tail.setPrevious(null);
            this.tail = previous;
            this.tail.setNext(null);
        }

        this.size--;
        return Optional.of(val);
    }

    /*
     * Insert an element at the end of the List.
     */
    public void push(final T data) {
        final JListNode<T> newNode = new JListNode<>(data);
        if (this.size() == 0) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            newNode.setPrevious(this.tail);
            this.tail = newNode;
        }
        this.size++;
    }

    /*
     * size returns the size of the list.
     */
    public int size() {
        return this.size;
    }

    /*
     * Return true if the list is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new JListIterator<T>(this);
    }

    public class JListIterator<T> implements Iterator<T> {
        private JListNode<T> current;

        public JListIterator(final JList<T> list) {
            if (list.head().isPresent())
                this.current = list.head().get();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            final T next = this.current.getData();
            this.current = this.current.getNext();
            return next;
        }

    }
}
