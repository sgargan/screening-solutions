package io.intercom.problems.proximity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <code>CustomerLocation</code> is a simple immutable model class to encapsulate details
 * of a Customer's global location. Instances are created automatically by Jackson as it
 * unmarshalls customer json.
 */
public class CustomerLocation {

  private Double latitude;

  private Double longitude;

  private String name;

  @JsonProperty("user_id")
  private Integer userId;

  public CustomerLocation(){ /* required for serialization */ }

  /**
   * Create a CustomerLocation instance for the given customer name and
   * latitude and longitude location data
   *
   * @param userId the user's id
   * @param name the customer's name
   * @param latitude the customer's latitude
   * @param longitude the customer's longitude
   */
  public CustomerLocation(Integer userId, String name, Double latitude, Double longitude){
    this.userId = userId;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    validate();
  }

  /**
   * Validates that a CustomerLocation has all the required fields name, latitude and longitude
   *
   * @throws java.lang.AssertionError if any of the required fields are missing.
   */
  public void validate(){
    StringBuilder b = new StringBuilder();
    if(userId == null) {
      b.append("'userId' is a required field in a CustomerLocation\n");
    }
    if(name == null) {
      b.append("'name' is a required field in a CustomerLocation\n");
    }
    if(latitude == null) {
      b.append("'latitude' is a required field in a CustomerLocation\n");
    }
    if(longitude == null) {
      b.append("'longitude' is a required field in a CustomerLocation\n");
    }

    if(b.length() > 0){
      throw new AssertionError(b.toString());
    }

  }

  /**
   * Retrieve the customers userId
   *
   * @return the customer's userId
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * Retieve the customer's latitude and longitude coordinates
   *
   * @return Coordinates the customers latitude and longitude coords
   */
  public Coordinates getCoordinates() {
    return new Coordinates(latitude, longitude);
  }

  /**
   * Retieve the customer's latitude
   *
   * @return Double latitude of the customer's location
   */
  public Double getLatitude() {
    return latitude;
  }

  /**
   * Retieve the customer's longitude
   *
   * @return Double latitude of the customer's location
   */
  public Double getLongitude() {
    return longitude;
  }


  /**
   * Retieve the customer's name
   *
   * @return String the customer's name
   */
  public String getName() {
    return name;
  }

}
