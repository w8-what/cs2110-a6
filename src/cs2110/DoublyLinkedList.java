package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A list of elements of type `T` implemented using a doubly-linked chain. `null` may not be stored
 * as an element in this list.
 */
public class DoublyLinkedList<T> implements CS2110List<T> {

    /**
     * A node of a doubly-linked list whose elements have type `T`.
     */
    private static class DNode<T> {

        /**
         * The element in this node.
         */
        T data;

        /**
         * The next node in the chain (or null if this is the tail node).
         */
        DNode<T> next;

        /**
         * The previous node in the chain (or null if this is the head node).
         */
        DNode<T> prev;

        /**
         * Create a Node containing element `elem`, pointing backward to node 'prev' (may be `null`),
         * and pointing forward to node `next` (may be `null`).
         */
        DNode(T elem, DNode<T> prev, DNode<T> next) {
            data = elem;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * The number of elements in the list.  Equal to the number of linked nodes reachable from
     * `head` (including `head` itself) using `next` arrows.
     */
    private int size;

    /**
     * The first node of the doubly-linked list (null for empty list). `head.prev` must be null
     */
    private DNode<T> head;

    /**
     * The last node of the doubly-linked list (null for empty list). `tail.next` must be null.
     */
    private DNode<T> tail;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private boolean invariantSatisfied() {
        if (size < 0) {
            return false;
        }
        if (size == 0) {
            return head == null && tail == null;
        } else { // size > 0
            if (head == null || head.prev != null || tail == null) {
                return false;
            }

            // TODO 1: By traversing the list from head to tail, check that
            //  (1) none of the nodes store null elements
            //  (2) the number of linked nodes is equal to the list's size
            //  (3) the last linked node is the same object as `tail`
            //  (4) the linking is consistent, i.e., for a non-tail node n, n.next.prev is n
            return false;
        }
    }

    /**
     * Constructs an empty list.
     */
    public DoublyLinkedList() {
        size = 0;
        head = null;
        tail = null;
        assert invariantSatisfied();
    }

    @Override
    public void add(T elem) {
        // TODO 2a: Implement this method according to its specifications.
    }

    @Override
    public void insert(int index, T elem) {
        // TODO 2b: Implement this method according to its specification
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        // TODO 2c: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T elem) {
        // TODO 2d: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(T elem) {
        // TODO 2e: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public void set(int index, T elem) {
        // TODO 2f: Implement this method according to its specifications.
    }

    @Override
    public T remove(int index) {
        // TODO 2g: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(T elem) {
        // TODO 2h: Implement this method according to its specifications.
    }

    /**
     * Return an iterator over the elements of this list (in forward order). To ensure the
     * functionality of this iterator, this list should not be mutated while the iterator is in
     * use.
     */
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    /**
     * An iterator over a doubly-linked list. Guarantees correct behavior (visits each element
     * exactly once in the correct order) only if the list is not mutated during the lifetime of
     * this iterator except through its `remove()` method.
     */
    private class ListIterator implements Iterator<T> {

        /**
         * The node whose value will next be returned by the iterator, or null once the iterator
         * reaches the end of the list.
         */
        private DNode<T> nextToVisit;

        /**
         * Constructs a new ListIterator over this list
         */
        public ListIterator() {
            nextToVisit = head;
        }

        @Override
        public boolean hasNext() {
            return nextToVisit != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T elem = nextToVisit.data;
            nextToVisit = nextToVisit.next;
            return elem;
        }

        @Override
        public void remove() {
            // TODO 4: Implement this method according to the `Iterator.remove()` specifications.
            //  This may require you to add fields to the `ListIterator` class and modify these
            //  fields within other `ListIterator` methods.
            throw new UnsupportedOperationException();
        }
    }
}
