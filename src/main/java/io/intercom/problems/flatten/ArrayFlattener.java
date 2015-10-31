package io.intercom.problems.flatten;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * <code>ArrayFlattener</code> is a utility used flatten
 * arbitrarily nested arrays of <code>Integer</code>s
 */
public class ArrayFlattener {

  /**
   * Takes an arbitrarily nested array of integers and recursively
   * iterates to create a flattened array with the contents i.e.
   * converting [[1,2,[3]],4] to [1,2,3,4]
   * <p>
   * Implementation will deal with malformed arrays with null and non
   * <code>Integer</code> contents and also arrays that reference
   * themselves introducing cycles.
   *
   * @param array the arbitrarily nested array of integers. Note that
   *              java's strong typing prohibits mixing integers and
   *              and arrays of integers in an Integer typed array so
   *              this implementation used a plain Object[] to represent
   *              the nested array
   * @return Integer[] an array containing the flattened contents of the given nested array
   */
  public static Integer[] flattenRecursively(Object[] array) {
    List<Integer> flattened = new ArrayList<>();
    Set visited = new HashSet();
    recurseArray(flattened, visited, array);
    return flattened.toArray(new Integer[flattened.size()]);
  }

  private static void recurseArray(List<Integer> flattened, Set visited, Object[] array) {
    if (array != null) {
      visited.add(array);
      for (Object o : array) {
        if (o != null) {
          if (o instanceof Integer) {
            flattened.add((Integer) o);
          } else if (o.getClass().isArray()) {
            // only process the array if we've not visited it before.
            if (!visited.contains(o)) {
              recurseArray(flattened, visited, (Object[]) o);
            }
          }
        }
      }
    }
  }

  /**
   * Takes an arbitrarily nested array of integers and iteratively
   * creates a flattened array with the contents i.e.
   * converting [[1,2,[3]],4] to [1,2,3,4]
   * <p>
   * Implementation will deal with malformed arrays with null and non
   * <code>Integer</code> contents and also arrays that reference
   * themselves introducing cycles.
   *
   * @param array the arbitrarily nested array of integers. Note that
   *              java's strong typing prohibits mixing integers and
   *              and arrays of integers in an Integer typed array so
   *              this implementation used a plain Object[] to represent
   *              the nested array
   * @return Integer[] an array containing the flattened contents of the given nested array
   */
  public static Integer[] flattenIteratively(Object[] array) {
    List<Integer> flattened = new ArrayList<>();
    Set visited = new HashSet();
    Stack processOrder = new Stack();
    processOrder.push(array);

    while (!processOrder.isEmpty()) {
      Object o = processOrder.pop();
      if (o != null) {
        if (o instanceof Integer) {
          // stack will have contents reversed so insert at the
          // front to avoid having to reverse array later.
          flattened.add(0, (Integer) o);
        } else if (o.getClass().isArray()) {
          visited.add(o);
          for (Object subelement : (Object[]) o) {
            // only process the array if we've not visited it before.
            if (!visited.contains(subelement)) {
              processOrder.push(subelement);
            }
          }
        }
      }
    }
    return flattened.toArray(new Integer[flattened.size()]);
  }
}
