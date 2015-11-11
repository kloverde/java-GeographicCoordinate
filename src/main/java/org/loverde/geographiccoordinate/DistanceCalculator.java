/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


/**
 * <p>
 * This class calculates the distance bewteen two sets of coordinates as the crow
 * flies, using the common Haversine formula.  Haversine assumes a perfectly
 * spherical Earth, so some amount of error is guaranteed in all calculations.
 * </p>
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  I HAVE NO BACKGROUND IN, OR EVEN AN
 * UNDERSTANDING OF, GEODESY; I MERELY IMPLEMENTED A FORMULA I
 * FOUND ON WIKIPEDIA.  YOU WOULDN'T ENTRUST A WIKIPEDIA PAGE WITH
 * YOUR SAFETY, SO DON'T ENTRUST IT TO THIS SOFTWARE.  THIS WOULD
 * BE A GOOD TIME FOR YOU TO READ AND UNDERSTAND THE WAIVER PRESENT
 * IN THIS SOFTWARE'S LICENSE.
 * </p></strong>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">https://en.wikipedia.org/wiki/Haversine_formula</a>
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

      System.out.println( distance(point1, point2, Unit.KILOMETERS) );
      System.out.println( distance(point1, point2, Unit.METERS) );
      System.out.println( distance(point1, point2, Unit.MILES) );
      System.out.println( distance(point1, point2, Unit.NAUTICAL_MILES) );
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
    * Gets the distance between two sets of coordinates.  Functionally equivalent to
    * {@linkplain DistanceCalculator#distance(Latitude, Longitude, Latitude, Longitude, Unit)}
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
    * @param point1 The first set of latitude/longitude
    * @param point2 The second set of latitude/longitude
    * @param unit The unit of measurement
    *
    * @return The distance between {@code point1} and {@code point2}, expressed in terms of {@code unit}
    */
   public static double distance( final Point point1, final Point point2, final Unit unit ) {
      if( point1 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.POINT_1_NULL );
      if( point2 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.POINT_2_NULL );

      return distance( point1.getLatitude(), point1.getLongitude(),
                       point2.getLatitude(), point2.getLongitude(),
                       unit );
   }

   /**
    * <p>
    * Gets the distance between two sets of coordinates.  Functionally equivalent to
    * {@linkplain DistanceCalculator#distance(Point, Point, Unit)}
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
    * @param unit The unit of measurement
    *
    * @return The distance from point 1 to point 2, expressed in terms of {@code unit}
    */
   public static double distance( final Latitude latitude1, final Longitude longitude1, final Latitude latitude2, final Longitude longitude2, final Unit unit ) {
      if( latitude1 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LATITUDE_1_NULL );
      if( longitude1 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LONGITUDE_1_NULL );
      if( latitude2 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LATITUDE_2_NULL );
      if( longitude2 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LONGITUDE_2_NULL );
      if( unit == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.UNIT_NULL );

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
