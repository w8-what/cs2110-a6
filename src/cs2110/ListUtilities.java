package cs2110;

import java.util.Iterator;

/**
 * Contains static methods for checking whether a list is mirrored.
 */
public class ListUtilities {

    /**
     * Removes every instance of `target` from the given `list`.
     */
    public static <T> void removeAllOccurrences(CS2110List<T> list, T target) {

        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(target)) {
                iterator.remove();
            }
        }
    }
}
