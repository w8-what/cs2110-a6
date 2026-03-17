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

            DNode<T> currNode = head;
            int numLinkedNodes = 0;

            // Traversing the list from head to tail
            while (currNode.next != null) {
                // Checks that nodes do not store null elements (1)
                if (currNode.data == null) {
                    return false;
                }
                // Checks that the linking is consistent (4)
                if (currNode.next.prev != currNode) {
                    return false;
                }
                currNode = currNode.next;
                numLinkedNodes++;
            }

            // Checks for size matching and that the last node is same as tail (2) (3)
            boolean sizeMatch = this.size == numLinkedNodes;
            boolean lastIsTail = currNode == this.tail;

            return sizeMatch && lastIsTail;

            // TODO 1: By traversing the list from head to tail, check that (DONE!)
            //  (1) none of the nodes store null elements
            //  (2) the number of linked nodes is equal to the list's size
            //  (3) the last linked node is the same object as `tail`
            //  (4) the linking is consistent, i.e., for a non-tail node n, n.next.prev is n
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
        assert elem != null;

        this.insert(size, elem);

        assert invariantSatisfied();
    }

    @Override
    public void insert(int index, T elem) {
        assert 0 <= index && index <= size;
        assert elem != null;

        DNode<T> nodeAtIndex = getNode(index);

        if (size == 0) {
            // case 0
            DNode<T> newNode = new DNode<T>(elem, null, null);
            head = newNode;
            tail = newNode;
        }

        else if (index == 0) {
            // case 1
            DNode<T> newNode = new DNode<T>(elem, null, nodeAtIndex);
            nodeAtIndex.prev = newNode;
            head = newNode;
        }

        else if (index == size) {
            // case 2
            DNode<T> newNode = new DNode<T>(elem, nodeAtIndex, null);
            nodeAtIndex.next = newNode;
            tail = newNode;
        }

        else {
            // case 3
            DNode<T> newNode = new DNode<T>(elem, nodeAtIndex.prev, nodeAtIndex);
            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        assert 0 <= index && index < size;
        return getNode(index).data;
    }

    @Override
    public boolean contains(T elem) {
        int i = 0;
        DNode<T> currNode = head;

        // Iterates through list, checking if any element matches
        while (i < size) {
            if (currNode.data == elem) {
                return true;
            }
            currNode = currNode.next;
            i++;
        }

        // No element matches 'elem'
        return false;
    }

    @Override
    public int indexOf(T elem) {
        assert contains(elem);

        DNode<T> currNode = head;
        int i = 0;

        while (i < size) {
            if (currNode.data == elem) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public void set(int index, T elem) {
        assert 0 <= index && index < size();
        assert elem != null;

        this.getNode(index).data = elem;

        assert invariantSatisfied();
    }

    @Override
    public T remove(int index) {
        // TODO 2g: Implement this method according to its specifications.
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(T elem) {
        assert contains(elem);

        this.remove(this.indexOf(elem));

        assert invariantSatisfied();
    }

    /**
     * Returns the DNode at position 'index'
     */
    private DNode<T> getNode(int index) {
        assert 0 <= index && index < size;

        int i = 0;
        DNode<T> currNode = head;

        while (i < index) {
            currNode = currNode.next;
            i++;
        }
        return currNode;
    }

    /**
     * Adds the given `elem` just before this existing list `node`.
     */
//    private void spliceIn(DNode<T> node, T elem) {
//        assert elem != null;
//
//        if (node == tail) {
//            DNode<T> newNode = new DNode<>(node.data, node, node.next);
//
//        }
//
//        DNode<T> newNode = new DNode<>(node.data, node, node.next);
//
//        node.data = elem;
//        node.next = newNode;
//        newNode.next.prev = newNode;
//
//        assert invariantSatisfied();
//    }

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
