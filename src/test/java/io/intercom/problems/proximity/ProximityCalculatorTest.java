package io.intercom.problems.proximity;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.intercom.problems.proximity.ProximityCalculator.IntercomOfficeDublin;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProximityCalculatorTest {

  final double distanceInOneDegree = new Coordinates(0.0, 0.0).distanceTo(new Coordinates(1.0, 0.0));

  private List<CustomerLocation> customers;

  @Before
  public void createTestCustomerList() {

    // create customers each one degree further away from the office than the last
    customers = range(1, 6)
        .mapToObj(x -> new CustomerLocation(x, "Customer-" + x, IntercomOfficeDublin.getLatitude() + x, IntercomOfficeDublin.getLongitude()))
        .collect(Collectors.toList());
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldComplainAboutEmptyOrNullCustomerList(){
    new ProximityCalculator(null);
  }

  @Test
  public void shouldCorrectlyFilterCustomersByDistance() {

    ProximityCalculator sut = new ProximityCalculator(customers);

    // the 0.0001 added to the distance accounts for rounding errors
    assertEquals(0, sut.calculateCustomersWithinRadius(distanceInOneDegree * 0.0001).size());
    assertEquals(1, sut.calculateCustomersWithinRadius(distanceInOneDegree * 1.0001).size());
    assertEquals(2, sut.calculateCustomersWithinRadius(distanceInOneDegree * 2.0001).size());
    assertEquals(3, sut.calculateCustomersWithinRadius(distanceInOneDegree * 3.0001).size());
    assertEquals(4, sut.calculateCustomersWithinRadius(distanceInOneDegree * 4.0001).size());

  }

  @Test
  public void shouldReturnCustomersListWithUserIDAscending() {

    Collections.shuffle(customers);
    ProximityCalculator sut = new ProximityCalculator(customers);

    List<CustomerLocation> withinRadius = sut.calculateCustomersWithinRadius(distanceInOneDegree * 4.0001);
    assertEquals(4, withinRadius.size());

    // the id of each entry should be less than the subsequent one
    for (int x = 0; x < withinRadius.size() - 1; x++) {
      assertTrue(withinRadius.get(x).getUserId() < withinRadius.get(x + 1).getUserId());
    }

  }

}
