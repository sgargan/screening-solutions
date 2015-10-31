package io.intercom.problems.proximity;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * <code>Coordinates</code> is a simple class to encapsulate a latitude and longitude and
 * the great circle based logic to calculate the distance between two instances.
 */
public class Coordinates {

  private Double latitude;

  private Double longitude;

  public final static Double MEAN_EARTH_RADIUS = 6371d;

  /**
   * Creates a new Coordinates object
   * @param latitude the latitude of the coordinates
   * @param longitude the longitude of the coordinates
   */
  public Coordinates(Double latitude, Double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  /**
   * Retrieve the latitude
   *
   * @return Double latitude of the coordinates
   */
  public Double getLatitude() {
    return latitude;
  }

  /**
   * Retrieve the longitude
   *
   * @return Double longitude of the coordinates
   */
  public Double getLongitude() {
    return longitude;
  }

  /**
   * Calculates the distance between two sets of Coordinates based on
   * the formula and mean earth radius value found here
   * <p>
   * https://en.wikipedia.org/wiki/Great-circle_distance#Computational_formulas
   *
   * @param there the other set of coordinates to find the distance to
   * @return the distance in kilometers between this set of coordinates and the given set
   */
  public Double distanceTo(Coordinates there) {

    Double lat1 = toRadians(this.latitude);
    Double lon1 = toRadians(this.longitude);
    Double lat2 = toRadians(there.latitude);
    Double lon2 = toRadians(there.longitude);

    Double deltaLongitude = Math.abs(lon1 - lon2);

    Double radians = acos(sin(lat1) * sin(lat2) +
        cos(lat1) * cos(lat2) * cos(deltaLongitude));

    return radians * MEAN_EARTH_RADIUS;
  }
}
