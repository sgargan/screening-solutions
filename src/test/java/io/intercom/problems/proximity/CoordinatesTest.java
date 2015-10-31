package io.intercom.problems.proximity;

import org.junit.Test;

import static io.intercom.problems.proximity.Coordinates.MEAN_EARTH_RADIUS;
import static java.lang.Math.PI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoordinatesTest {

  @Test
  public void shouldBeNoDistanceBetweenIdenticalCoordinates() {
    Coordinates coords = new Coordinates(123.5678, 123.45678);
    assertEquals(new Double(0.0), coords.distanceTo(coords));
  }

  @Test
  public void shouldCorrectlyCalculateArcsOfKnownMultiplesOfPi() {

    // there are pi radians in a semi circle
    Double piTimeMeanRadius = PI * MEAN_EARTH_RADIUS;

    Coordinates zero = new Coordinates(0.0, 0.0);
    Coordinates equatorial180 = new Coordinates(180.0, 0.0);

    assertEquals(piTimeMeanRadius, zero.distanceTo(equatorial180));

    // there are pi/4 radians in an arc to (0, 45.0)
    Coordinates ninety = new Coordinates(90.0, 0.0);
    Double piOver2 = PI / 2 * MEAN_EARTH_RADIUS;
    assertEquals(new Double(piOver2), zero.distanceTo(ninety));

    // there are pi/6 radians in an arc to (0, 30.0) there is a slight
    // rounding effect with doubles but far beyond the precision required.
    // so two distances are the essentially the same if their difference is
    // negligable
    Double distance = zero.distanceTo(new Coordinates(0.0, -30.0));
    assertTrue(distance - PI / 6 * MEAN_EARTH_RADIUS < 0.0000001);


    distance = new Coordinates(0.0, 45.0).distanceTo(new Coordinates(0.0, -45.0));
    assertTrue(distance - piOver2 < 0.0000001);
  }


}
