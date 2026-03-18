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
         * Create a Node containing element `elem`, pointing backward to node 'prev' (may be
         * `null`), and pointing forward to node `next` (may be `null`).
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
            int numLinkedNodes = 1;

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
            // Checks that tail's data is not null
            if (currNode.data == null) {
                return false;
            }

            // Checks for size matching and that the last node is same as tail (2) (3)
            boolean sizeMatch = this.size == numLinkedNodes;
            boolean lastIsTail = currNode == this.tail;

            return sizeMatch && lastIsTail;
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

        if (size == 0) {
            // case 1: insert into empty list
            DNode<T> newNode = new DNode<T>(elem, null, null);
            head = newNode;
            tail = newNode;
        } else if (index == size) {
            // case 2: insert at the end of the list
            DNode<T> nodeAtIndex = getNode(index - 1);
            DNode<T> newNode = new DNode<T>(elem, nodeAtIndex, null);
            nodeAtIndex.next = newNode;
            tail = newNode;
        } else {
            DNode<T> nodeAtIndex = getNode(index);

            // case 3: insert at the beginning of the list
            if (index == 0) {
                // case 1
                DNode<T> newNode = new DNode<T>(elem, null, nodeAtIndex);
                nodeAtIndex.prev = newNode;
                head = newNode;
            } else {
                // case 4: all other cases
                DNode<T> newNode = new DNode<T>(elem, nodeAtIndex.prev, nodeAtIndex);
                newNode.prev.next = newNode;
                newNode.next.prev = newNode;
            }
        }
        size++;
        assert invariantSatisfied();
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
            if (currNode.data.equals(elem)) {
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
            if (currNode.data.equals(elem)) {
                return i;
            }
            currNode = currNode.next;
            i++;
        }
        return -1; // Did not find element
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
        assert 0 <= index && index < size;

        DNode<T> nodeAtIndex = getNode(index);

        // case 1: remove from a list of size 1
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            // case 2: remove the first node of the list
            if (index == 0) {
                head = nodeAtIndex.next;
                nodeAtIndex.next.prev = null;
            }

            // case 3: remove the last node of the list
            else if (index == size - 1) {
                tail = nodeAtIndex.prev;
                nodeAtIndex.prev.next = null;
            }

            // case 4: all other cases
            else {
                nodeAtIndex.next.prev = nodeAtIndex.prev;
                nodeAtIndex.prev.next = nodeAtIndex.next;
            }
        }
        size--;
        assert invariantSatisfied();

        return nodeAtIndex.data;
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
        private boolean canRemove = false;
        private DNode<T> lastReturned = null;

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
            lastReturned = nextToVisit;
            nextToVisit = nextToVisit.next;
            canRemove = true;
            return elem;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException();
            }

            DNode<T> nodeToRemove = lastReturned;

            if (size == 1) {
                head = null;
                tail = null;
            } else if (nodeToRemove == head) {
                head = nodeToRemove.next;
                head.prev = null;
            } else if (nodeToRemove == tail) {
                tail = nodeToRemove.prev;
                tail.next = null;
            } else {
                nodeToRemove.prev.next = nodeToRemove.next;
                nodeToRemove.next.prev = nodeToRemove.prev;
            }
            size--;
            canRemove = false;
            assert invariantSatisfied();
        }
    }
}
