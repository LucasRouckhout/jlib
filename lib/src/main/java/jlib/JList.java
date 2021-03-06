package jlib;

import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * JList is a very basic implementation of a doubly linked List.
 * <p>
 * JList does not accept null elements.
 * <p>
 * It does not adhere to the
 */
@ToString
public class JList<T> implements Collection<T> {

    private int size;
    private JListNode<T> head;
    private JListNode<T> tail;

    public JList() {
        this.size = 0;
        this.head = this.tail = null;
    }

    /**
     * Retrieves the element at the given index.
     * <p>
     * Watch out, index retrieving from a Linked List is o(n) in time
     * complexity so use this method judiciously.
     *
     * @param index The index from which to get the element.
     * @return the element at the given index
     */
    public T get(final int index) {
        if (index >= this.size()) throw new IndexOutOfBoundsException(
                String.format("index = %d, size = %d", index, this.size)
        );
        if (index < 0) throw new IllegalArgumentException(
                String.format("index = %d", index)
        );

        int count = 0;
        T val = null;
        for (final T element : this) {
            if (count == index) {
                val = element;
                break;
            }
            count++;
        }
        return val;
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
     * @return An optional containing the last element of
     * the list. If the list was empty an empty optional is
     * returned.
     */
    public Optional<T> pop() {
        if (this.size() == 0)
            return Optional.empty();

        final var val = this.tail.getData();
        final var previous = this.tail.getPrevious();

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
     * Extracts a node from the list.
     * <p>
     * Depending on where in the list you are you have to
     * perform different "cleanup" tasks with the references
     * to next and previous.
     * <p>
     * There are 4 situations:
     * 1. There is only one element
     * 2. You are at the end of the list
     * 3. You are at the beginning of the list.
     * 4. You are somewhere in the middle.
     *
     * @param node The node to be extracted from the list.
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
        // in it to help out the GC. Don't know if this is
        // bullshit or not but doesn't hurt in any case.
        node.setPrevious(null);
        node.setNext(null);

        this.size--;
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

    public static class JListIterator<T> implements Iterator<T> {
        private JListNode<T> current;

        public JListIterator(final JList<T> list) {
            // Note that an explicit else is not needed here.
            // if head is null then current will be and hasNext
            // will return false.
            if (list.head().isPresent())
                this.current = list.head().get();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            final var next = this.current.getData();
            this.current = this.current.getNext();
            return next;
        }
    }

    /**
     * Ensures that this {@link JList} contains the specified element. This call will
     * always return true in the case of JList since duplicates are allowed.
     * <p>
     * This method adheres to the add method of the {@link Collection} interface.
     *
     * @param element element whose presence in this {@link JList} is to be ensured.
     * @return true is this collection was changed as a result of this call.
     * should always be true in the case of a JList.
     */
    @Override
    public boolean add(final T element) {
        final var newNode = new JListNode<T>(element);
        if (this.size() == 0) {
            this.head = newNode;
        } else {
            this.tail.setNext(newNode);
            newNode.setPrevious(this.tail);
        }
        this.tail = newNode;
        this.size++;
        return true;
    }

    /**
     * Adds all of the elements in the specified collection to this {@link JList}
     *
     * @param collection collection containing elements to be added to this
     *                   {@link JList}
     * @return true if this {@link JList} changed as a result of this call which
     * should always be the case for a {@link JList}.
     */
    @Override
    public boolean addAll(final Collection<? extends T> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public void clear() {
        this.head = this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean contains(final Object value) {
        return this.stream()
                .parallel()
                .anyMatch(val -> val.equals(value));
    }

    @Override
    public boolean containsAll(final Collection<?> collection) {
        return collection.stream()
                .parallel()
                .filter(this::contains)
                .count() == collection.size();
    }

    /**
     * Removes the first occurance of the given value from the list.
     * Equality is decided using the equals() method.
     *
     * @param value The value to remove.
     * @return true if a value was removed because of this call.
     * @throws NullPointerException If given value is null.
     */
    @Override
    public boolean remove(@NonNull final Object value) {
        if (this.isEmpty())
            return false;

        JListNode<T> node = this.head;
        boolean removed = false;
        for (int i = 0; i < this.size(); i++) {
            if (node.getData().equals(value)) {
                extractNode(node);
                removed = true;
                break;
            }
            node = node.getNext();
        }
        return removed;
    }

    @Override
    public boolean removeAll(final Collection<?> collection) {
        return collection.stream().allMatch(this::remove);
    }

    @Override
    public boolean retainAll(final Collection<?> arg0) {
        // TODO: Implement
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return this.stream().toArray();
    }

    @Override
    public <T> T[] toArray(final T[] arg0) {
        // TODO: Implement
        throw new UnsupportedOperationException();
    }

}
