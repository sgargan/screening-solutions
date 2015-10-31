package io.intercom.problems.proximity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <code>CustomerDataLoader</code> loads customer location data in Json format and
 * parses into a usable <code>CustomerLocation</code> objects.
 */
public class CustomerLocationDataLoader {

  private final Logger log = LoggerFactory.getLogger(getClass());

  /**
   * Reads location information from the given url and parses it into
   * CustomerLocation objects. Expects one full json location representation per
   * line and will ignore any lines that contain malformed input. All parsed locations are
   * validated to insure they contain all the required fields.
   *
   * @param customerLocationDetailsUrl the url of the json file containing the customer information.
   * @return List<CustomerLocation> a list of the CustomerLocations parsed from the contents of the URL.
   * @throws IOException if there is an error reading data from the url
   */
  public List<CustomerLocation> loadCustomerLocationDetails(URL customerLocationDetailsUrl) throws IOException {
    URLConnection con = customerLocationDetailsUrl.openConnection();
    con.setConnectTimeout(3000);
    try (InputStream in = con.getInputStream()) {
      return loadCustomerLocationDetails(in);
    } catch (IOException e) {
      throw new IOException("Could not load customer location details from '" + customerLocationDetailsUrl + "'", e);
    }
  }

  /**
   * Reads location information from the given <code>InputStream</code> and parses it into
   * CustomerLocation objects. Expects one full json location representation per
   * line and will ignore any lines that contain malformed input. All parsed locations are
   * validated to insure they contain all the required fields.
   *
   * @param customerJsonStream inputStream from which to read the customer information in json.
   * @return List<CustomerLocation> a list of the CustomerLocations parsed from the contents of the stream.
   * @throws IOException if there is an error reading data from the stream
   */
  protected List<CustomerLocation> loadCustomerLocationDetails(InputStream customerJsonStream) throws IOException {
    if (customerJsonStream == null) {
      return Collections.EMPTY_LIST;
    }

    ObjectMapper mapper = new ObjectMapper();

    try (InputStreamReader reader = new InputStreamReader(customerJsonStream)) {
      return new BufferedReader(reader).lines()
          .map(json -> parseObject(json, mapper))
          .filter(location -> location != null)
          .collect(Collectors.toList());
    }
  }

  private CustomerLocation parseObject(String line, ObjectMapper mapper) {
    try {
      CustomerLocation customer = mapper.readValue(line, CustomerLocation.class);
      customer.validate();
      return customer;
    } catch (IOException e) {
      log.error("Error parsing customer info '" + line + "', " + e.getMessage());
    }
    return null;
  }


}
