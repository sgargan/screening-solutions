package io.intercom.problems.proximity;

import java.net.URL;
import java.util.List;

public class Main {

  public static void main(String[] args) throws Exception {
    URL customerData = new URL("https://gist.githubusercontent.com/brianw/19896c50afa89ad4dec3/raw/6c11047887a03483c50017c1d451667fd62a53ca/gistfile1.txt");
    List<CustomerLocation> customers = new CustomerLocationDataLoader().loadCustomerLocationDetails(customerData);

    ProximityCalculator calc = new ProximityCalculator(customers);
    List<CustomerLocation> invited = calc.calculateCustomersWithinRadius(100.0);

    System.out.println("Party Guest list (" + invited.size() + " customers)");
    System.out.println("-----------------------------------");

    invited.stream().forEach(customer -> {
      System.out.println("id: " + customer.getUserId() + ", name: " + customer.getName());
    });

  }
}
