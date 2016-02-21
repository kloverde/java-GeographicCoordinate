/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


/**
 * <p>
 * This class calculates the distance bewteen coordinates using the Haversine formula.  Unlike
 * <a href="https://en.wikipedia.org/wiki/Vincenty's_formulae">Vincenty's formulae</a>, which
 * are designed to operate on an oblate spheroid, Haversine assumes a perfectly spherical
 * Earth, meaning Haversine is less accurate than Vincenty's formulae.  Haversine was chosen for
 * its ease of implementation, plus not having to worry about a known difficulty with Vincenty, in
 * which the iterative formulae converge very slowly for some inputs.  Thus, by using Haversine,
 * we also sidestep a potential performance issue.
 * </p>
 *
 * <p>
 * The Earth radius used in calculations is the volumetric mean radius, not the equatorial radius.  As of
 * the date this software was written, NASA's figure for the volumetric mean radius was 6371 km.
 * </p>
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  I HAVE NO BACKGROUND IN, OR EVEN AN
 * UNDERSTANDING OF, GEODESY; I MERELY IMPLEMENTED A FORMULA I
 * FOUND ON WIKIPEDIA.  YOU WOULDN'T ENTRUST A WIKIPEDIA PAGE WITH
 * YOUR SAFETY, SO DON'T ENTRUST IT TO THIS SOFTWARE.  THIS WOULD
 * BE A GOOD TIME FOR YOU TO READ AND UNDERSTAND THE WAIVER PRESENT
 * IN THIS SOFTWARE'S LICENSE.
 * </strong></p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">https://en.wikipedia.org/wiki/Haversine_formula</a>
 * @see <a href="http://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html">NASA's Earth radius figures</a>
 */
public class DistanceCalculator {
/*
   public static void main(String args[]) throws GeographicCoordinateException {
      Latitude latitude1 = new Latitude( 40, 42, 46, Latitude.Direction.NORTH );
      Longitude longitude1 = new Longitude(  74, 0, 21, Longitude.Direction.WEST );

      Latitude latitude2 = new Latitude( 38, 54, 17, Latitude.Direction.NORTH );
      Longitude longitude2 = new Longitude( 77, 0, 59, Longitude.Direction.WEST );

      Point point1 = new Point( latitude1, longitude1 );
      Point point2 = new Point( latitude2, longitude2 );

      System.out.println( distance(Unit.KILOMETERS, point1, point2) );
      System.out.println( distance(Unit.METERS, point1, point2) );
      System.out.println( distance(Unit.MILES, point1, point2) );
      System.out.println( distance(Unit.NAUTICAL_MILES, point1, point2) );
   }
*/
   /**
    * Units of distance - use this with the {@code distance} methods in this class.
    */
   public enum Unit {
      // Members are initialized with a conversion factor expressed in terms of one kilometer.

      METERS( 1000.0d ),

      MILES( 1.0d / 1.609344d ),

      KILOMETERS( 1.0d ),

      /**
       * This is the <strong>international</strong> nautical mile.  It's not to be confused with:
       * <ol>
       *    <li>The U.S. nautical mile, which was abandoned in 1954</li>
       *    <li>The Imperial (UK) nautical mile (also known as the Admiralty mile), which was abandoned in 1970</li>
       * </ol>
       *
       * @see <a href="https://en.wikipedia.org/wiki/Nautical_mile">https://en.wikipedia.org/wiki/Nautical_mile</a>
       */
      NAUTICAL_MILES( 1.0d / 1.852d );

      private double perKilometer;

      private Unit( final double perKilometer ) {
         this.perKilometer = perKilometer;
      }
   }

   private static final double EARTH_RADIUS_KILOMETERS = 6371.0d;


   /**
    * <p>
    * Gets the total distance between an unlimited number of {@linkplain Point}s.  For example, if the
    * distance from point A to point B is 3, and the distance from point B to point C is 2, the total
    * distance traveled will be (3 + 2) = 5.  Just pass {@code Point}s in the order in which they're
    * visited.  This is equivalent to repeatedly calling {@link DistanceCalculator#distance(Latitude, Longitude, Latitude, Longitude, Unit)}.
    * </p>
    *
    * <p><strong>
    * THIS IS HOBBYIST SOFTWARE.  I HAVE NO BACKGROUND IN, OR EVEN AN
    * UNDERSTANDING OF, GEODESY; I MERELY IMPLEMENTED A FORMULA I
    * FOUND ON WIKIPEDIA.  YOU WOULDN'T ENTRUST A WIKIPEDIA PAGE WITH
    * YOUR SAFETY, SO DON'T ENTRUST IT TO THIS SOFTWARE.  THIS WOULD
    * BE A GOOD TIME FOR YOU TO READ AND UNDERSTAND THE WAIVER PRESENT
    * IN THIS SOFTWARE'S LICENSE.
    * </strong></p>
    *
    * @param unit   The unit of distance
    *
    * @param points A vararg of {@linkplain Point}s arranged in the order in which they are visited.
    *               You must provide at least 2, otherwise an {@code IllegalArgumentException} will
    *               be thrown.
    *
    * @return The total distance traveled, expressed in terms of {@code unit}
    */
   public static double distance( final Unit unit, final Point ... points ) {
      if( points == null ) throw new IllegalArgumentException( "Points are null" );
      if( points.length < 2 ) throw new IllegalArgumentException( "Need to provide at least 2 points" );

      double distance = 0;
      Point previous = points[0];

      for( int i = 1; i < points.length; i++ ) {
         final Point current = points[i];

         if( previous == null ) throw new IllegalArgumentException( "points " + (i - 1) + " is null" );
         if( current == null ) throw new IllegalArgumentException( "points " + i + " is null" );

         distance += distance( previous.getLatitude(), previous.getLongitude(), current.getLatitude(), current.getLongitude(), unit );
         previous = current;
      }

      return distance;
   }

   /**
    * <p>
    * Gets the distance between two sets of coordinates.  Functionally equivalent to
    * {@linkplain DistanceCalculator#distance(Unit, Point ...)}
    * <p>
    *
    * <p><strong>
    * THIS IS HOBBYIST SOFTWARE.  I HAVE NO BACKGROUND IN, OR EVEN AN
    * UNDERSTANDING OF, GEODESY; I MERELY IMPLEMENTED A FORMULA I
    * FOUND ON WIKIPEDIA.  YOU WOULDN'T ENTRUST A WIKIPEDIA PAGE WITH
    * YOUR SAFETY, SO DON'T ENTRUST IT TO THIS SOFTWARE.  THIS WOULD
    * BE A GOOD TIME FOR YOU TO READ AND UNDERSTAND THE WAIVER PRESENT
    * IN THIS SOFTWARE'S LICENSE.
    * </strong></p>
    *
    * @param latitude1 Point 1 latitude
    * @param longitude1 Point 1 longitude
    * @param latitude2 Point 2 latitude
    * @param longitude2 Point 2 longitude
    * @param unit The unit of distance
    *
    * @return The distance from point 1 to point 2, expressed in terms of {@code unit}
    */
   public static double distance( final Latitude latitude1, final Longitude longitude1, final Latitude latitude2, final Longitude longitude2, final Unit unit ) {
      if( latitude1 == null ) throw new IllegalArgumentException( "Latitude 1 is null" );
      if( longitude1 == null ) throw new IllegalArgumentException( "Longitude 1 is null" );
      if( latitude2 == null ) throw new IllegalArgumentException( "Latitude 2 is null" );
      if( longitude2 == null ) throw new IllegalArgumentException( "Longitude 2 is null" );
      if( unit == null ) throw new IllegalArgumentException( "Unit is null" );

      final double lat1 = latitude1.toRadians(),
                   lat2 = latitude2.toRadians(),
                   lon1 = longitude1.toRadians(),
                   lon2 = longitude2.toRadians(),
                   deltaLat = lat2 - lat1,
                   deltaLon = lon2 - lon1;

      final double d = (2.0d * EARTH_RADIUS_KILOMETERS) * Math.asin( Math.sqrt( Math.pow(Math.sin(deltaLat / 2.0d), 2.0d)
                        + ( Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2.0d), 2.0d) ) ) );

      return d * unit.perKilometer;
   }
}
