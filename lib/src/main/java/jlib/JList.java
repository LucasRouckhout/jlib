package jlib;

import java.util.Iterator;
import java.util.Optional;

import lombok.NonNull;
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

    /**
     * Retrieves the data element at the given index.
     *
     * Watch out retrieving from a Linked List is o(n) time
     * complexity so use this method judiciusly.
     *
     * @param index     The index from which to get the element.
     * @return          Returns an optional containing the data at
     *                  that index. If the list is empty or you
     *                  asked for an index that does not have an
     *                  element at it an empty optional will be
     *                  returned.
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

    /**
     * Pop off the last element of the list and return it.
     *
     * @return  An optional containing the last element of
     *          the list. If the list was empty an empty optional is
     *          returned.
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

    /**
     * Returns true if the JList contains the given value.
     * Equality is decided using the equals() method.
     *
     * @param   value   The value to check.
     */
    public boolean contains(final T value) {
        boolean contains = false;
        for (final T val : this) {
            contains = val.equals(value);
            break;
        }
        return contains;
    }

    /**
     * Removes the first occurance of the given value from the list.
     * Equality is decided using the equals() method.
     *
     * @param   value   The value to remove.
     * @return  Returns an optional containing the removed value.
                If no value was found an empty optional is returned.
     * @throws  NullPointerException If given value is null.
     */
    public Optional<T> remove(@NonNull final T value) {
        if (this.isEmpty())
            return Optional.empty();

        JListNode<T> node = this.head;
        T val = null;
        for (int i = 0; i < this.size(); i++) {
            if (node.getData().equals(value)) {
                extractNode(node);
                val = node.getData();
                break;
            }
            node = node.getNext();
        }
        return Optional.ofNullable(val);
    }

    /**
     * Extracts a node from the list.
     *
     * Depending on where in the list you are you have to
     * perform different "cleanup" tasks with the references
     * to next and previous.
     *
     * There are 4 situations:
     * 1. There is only one element
     * 2. You are at the end of the list
     * 3. You are at the beginning of the list.
     * 4. You are somewhere in the middle.
     *
     * @param node  The node to be extracted from the list.
     */
    private void extractNode(final JListNode<T> node) {
        if (node.getNext() == null && node.getPrevious() == null) {
            this.head = this.tail = null;

        } else if (node.getNext() == null) {
            node.getPrevious().setNext(null);
            this.tail = node.getPrevious();

        } else if (node.getPrevious() == null) {
            node.getNext().setPrevious(null);
            this.head = node.getNext();

        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());

        }

        // Since we have taken out the node we do not
        // want to have any lingering references to
        // the nodes in the list if the node is no longer
        // in it.
        node.setPrevious(null);
        node.setNext(null);

        this.size--;
    }

    /**
     * Insert an element at the end of the List.
     *
     * @param element  The element to be added.
     */
    public void push(final T element) {
        final JListNode<T> newNode = new JListNode<>(element);
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

    /**
     * Returns the size of the list.
     */
    public int size() {
        return this.size;
    }

    /**
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
