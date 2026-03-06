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
        // TODO 5: Improve this definition using the `ListIterator.remove()` method.
        //  You are free to modify or delete any existing code in this method.
        while(list.contains(target)) {
            list.delete(target);
        }
    }
}
