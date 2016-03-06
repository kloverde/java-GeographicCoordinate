/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
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
   public static void main( String args[] ) {
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
      // Members are initialized with a conversion factor expressed in terms of 1 kilometer.

      /**
       * This is the international foot.  For those in the U.S., yes, that is the
       * foot you are accustomed to (12 inches = 1 ft).
       */
      FEET( 1000.0d / .3048d ),

      KILOMETERS( 1 ),

      METERS( 1000 ),

      MILES( 1.0d / 1.609344d ),

      /**
       * This is the <strong>international</strong> nautical mile.  It's not to be confused with:
       * <ol>
       *    <li>The U.S. nautical mile, which was abandoned in 1954</li>
       *    <li>The Imperial (UK) nautical mile (also known as the Admiralty mile), which was abandoned in 1970</li>
       * </ol>
       *
       * @see <a href="https://en.wikipedia.org/wiki/Nautical_mile">https://en.wikipedia.org/wiki/Nautical_mile</a>
       */
      NAUTICAL_MILES( 1.0d / 1.852d ),

      /**
       * <p>
       * For those of you living in the U.S., the U.S. Survey Foot is NOT the foot
       * you think of when you think of feet.  That is the
       * {@link Unit#FEET international foot}.  The survey foot is
       * used in geodetic surveys.  As defined by the National Bureau of Standards
       * in 1959:
       * </p>
       *
       * <p>
       * "Any data expressed in feet derived from and published as a result of geodetic surveys
       * within the United States will continue to bear the following relationship as defined
       * in 1893:  1 foot = 1200/3937 meter"
       * </p>
       *
       * @see <a href="http://www.ngs.noaa.gov/PUBS_LIB/FedRegister/FRdoc59-5442.pdf">http://www.ngs.noaa.gov/PUBS_LIB/FedRegister/FRdoc59-5442.pdf</a>
       */
      US_SURVEY_FEET( 1000.0d / (1200/3937.0d) ),

      YARDS( 1000.0d / .9144d );

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
    * visited.  This is equivalent to repeatedly calling {@link DistanceCalculator#distance(Unit, Latitude, Longitude, Latitude, Longitude)}.
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
      if( points == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Points are null") );
      if( points.length < 2 ) throw new GeographicCoordinateException( new IllegalArgumentException( "Need to provide at least 2 points") );

      double distance = 0;
      Point previous = points[0];

      for( int i = 1; i < points.length; i++ ) {
         final Point current = points[i];

         if( previous == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "points " + (i - 1) + " is null") );
         if( current == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "points " + i + " is null") );

         distance += distance( unit, previous.getLatitude(), previous.getLongitude(), current.getLatitude(), current.getLongitude() );
         previous = current;
      }

      return distance;
   }

   /**
    * <p>
    * Gets the distance between two sets of coordinates.  Functionally equivalent to
    * {@linkplain DistanceCalculator#distance(Unit, Point...)}
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
    * @param unit       The unit of distance
    * @param latitude1  Point 1 latitude
    * @param longitude1 Point 1 longitude
    * @param latitude2  Point 2 latitude
    * @param longitude2 Point 2 longitude
    *
    * @return The distance from point 1 to point 2, expressed in terms of {@code unit}
    */
   public static double distance( final Unit unit, final Latitude latitude1, final Longitude longitude1, final Latitude latitude2, final Longitude longitude2 ) {
      if( latitude1 == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Latitude 1 is null") );
      if( longitude1 == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Longitude 1 is null") );
      if( latitude2 == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Latitude 2 is null") );
      if( longitude2 == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Longitude 2 is null") );
      if( unit == null ) throw new GeographicCoordinateException( new IllegalArgumentException( "Unit is null") );

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
