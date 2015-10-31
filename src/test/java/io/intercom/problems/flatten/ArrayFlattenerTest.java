package io.intercom.problems.flatten;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;
import static io.intercom.problems.flatten.ArrayFlattener.flattenIteratively;
import static io.intercom.problems.flatten.ArrayFlattener.flattenRecursively;

public class ArrayFlattenerTest {

  private Object[] nested = new Object[]{new Object[]{1, 2, new Object[]{3}}, 4};

  private Object[] empty = new Object[]{};

  private Object[] withNullsAndNonIntegers = new Object[]{1, "too", 2, null, new Integer[]{3, 4}};

  private Object[] arrayWithCycle;

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Before
  public void createArrayWithCycle(){
    //create an array and have one of its elements cyclically point to itself
    arrayWithCycle = new Object[]{1, null};
    arrayWithCycle[1] = arrayWithCycle;

  }

  @Test
  public void shouldFlattenArrayRecursively() {

    assertArrayEquals(new Integer[]{1, 2, 3, 4}, flattenRecursively(nested));
    assertArrayEquals(new Integer[]{1, 2, 3, 4}, flattenRecursively(withNullsAndNonIntegers));
    assertArrayEquals(new Integer[]{}, flattenRecursively(null));
    assertArrayEquals(new Integer[]{}, flattenRecursively(empty));
    assertArrayEquals(new Integer[]{1}, flattenRecursively(arrayWithCycle));

  }

  @Test
  public void shouldFlattenArrayIteratively() {

    assertArrayEquals(new Integer[]{1, 2, 3, 4}, flattenIteratively(nested));
    assertArrayEquals(new Integer[]{1, 2, 3, 4}, flattenIteratively(withNullsAndNonIntegers));
    assertArrayEquals(new Integer[]{}, flattenIteratively(null));
    assertArrayEquals(new Integer[]{}, flattenIteratively(empty));
    assertArrayEquals(new Integer[]{1}, flattenRecursively(arrayWithCycle));

  }

  @Test(expected = StackOverflowError.class)
  public void shouldShowStackLimitationOfDeeplyNestedRecursion(){

    Object[] deeplyNested = new Object[]{0};
    for(int x = 1; x < 100000; x++){
       deeplyNested = new Object[]{x, deeplyNested};
    }

    flattenRecursively(deeplyNested);
  }

}