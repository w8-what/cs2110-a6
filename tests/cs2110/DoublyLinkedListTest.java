package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class DoublyLinkedListTest extends ListTest {

    @Override
    public <T> CS2110List<T> constructList() {
        return new DoublyLinkedList<T>();
    };

    @Test
    void testRemoveSingleElement() {
        CS2110List<Integer> list = constructList();
        list.add(1);

        Iterator<Integer> it = list.iterator();
        assertEquals(1, it.next());

        it.remove();

        assertEquals(0, list.size());
        assertFalse(list.iterator().hasNext());
    }

    @Test
    void testRemoveHead() {
        CS2110List<Integer> list = constructList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        assertEquals(1, it.next());

        it.remove(); // remove head

        assertEquals(2, list.size());
        assertEquals(2, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void testRemoveTail() {
        CS2110List<Integer> list = constructList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        it.next();
        it.next();
        assertEquals(3, it.next());

        it.remove(); // remove tail

        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
    }

    @Test
    void testRemoveMiddle() {
        CS2110List<Integer> list = constructList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();
        it.next();
        assertEquals(2, it.next());

        it.remove(); // remove middle

        assertEquals(2, list.size());
        assertEquals(1, list.get(0));
        assertEquals(3, list.get(1));
    }

    @Test
    void testRemoveWithoutNextThrows() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);

        Iterator<Integer> it = list.iterator();

        boolean threw = false;

        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }

        assertTrue(threw);
    }


    @Test
    void testRemoveAllElementsUsingIterator() {
        CS2110List<Integer> list = constructList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();

        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        assertEquals(0, list.size());
        assertFalse(list.iterator().hasNext());
    }


    @Test
    void testIteratorContinuesCorrectlyAfterRemove() {
        CS2110List<Integer> list = constructList();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator<Integer> it = list.iterator();

        assertEquals(1, it.next());
        it.remove();

        assertEquals(2, it.next());
        assertEquals(3, it.next());
    }

    @Test
    void testDoubleRemoveThrows() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.add(1);
        list.add(2);

        Iterator<Integer> it = list.iterator();

        it.next();
        it.remove();

        boolean threw = false;

        try {
            it.remove();
        } catch (IllegalStateException e) {
            threw = true;
        }
        assertTrue(threw);
    }
}
