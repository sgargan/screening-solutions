package io.intercom.problems.proximity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>ProximityCalculator</code> determines the customers in a collection that are based within
 * a given radius of the Intercom Office.
 */
public class ProximityCalculator {

  private final List<CustomerLocation> customerLocations;

  public static final Coordinates IntercomOfficeDublin = new Coordinates(53.3381985, -6.2592576);

  /**
   * Creates a new ProximityCalculator with the given collection of customers
   *
   * @param customerLocations a List of customers whos proximity will be tested.
   */
  public ProximityCalculator(List<CustomerLocation> customerLocations) {
    if (customerLocations == null) {
      throw new IllegalArgumentException("Guest list calculator requires a valid list of customer locations");
    }
    this.customerLocations = customerLocations;
  }

  /**
   * Filter the loaded customer list for customers within the given distance radius. The resulting
   * filtered list is sorted by userId ascending.
   *
   * @param distance radius in kilometers within which customers have to be located to get included.
   * @return the list of customers that are based within the supplied radius of the office.
   */
  public List<CustomerLocation> calculateCustomersWithinRadius(Double distance) {

    return customerLocations.stream()
        .filter(customer -> customer.getCoordinates().distanceTo(IntercomOfficeDublin) <= distance)
        .sorted((customer1, customer2) -> customer1.getUserId() - customer2.getUserId())
        .collect(Collectors.toList());
  }

}
