/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


/**
 * <p>
 * This class calculates the distance bewteen two sets of coordinates as the crow
 * flies using the Haversine formula.  When implemented correctly, Haversine has
 * an error of up to 0.3% due to its assumption of a perfectly spherical Earth.
 * </p>
 *
 * <p><strong>
 * ACCURACY IS NOT GUARANTEED.  AS STATED IN THIS SOFTWARE'S LICENSE, YOU BEAR
 * FULL AND SOLE RESPONSIBILITY FOR YOUR USE OF THIS SOFTWARE.  THAT INCLUDES
 * YOUR USE OF CALCULATIONS PERFORMED BY THIS SOFTWARE.  DO NOT RELY ON THESE
 * CALCULATIONS FOR NAVIGATION <em>OR FOR ANY OTHER PURPOSE</em>.
 * </strong></p>
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

      System.out.println( distance(new Point(latitude1, longitude1), new Point(latitude2, longitude2), Unit.KILOMETERS) );
      System.out.println( distance(new Point(latitude1, longitude1), new Point(latitude2, longitude2), Unit.METERS) );
      System.out.println( distance(new Point(latitude1, longitude1), new Point(latitude2, longitude2), Unit.MILES) );
      System.out.println( distance(new Point(latitude1, longitude1), new Point(latitude2, longitude2), Unit.NAUTICAL_MILES) );
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

      private Unit( final double perKilometer) {
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
    * ACCURACY IS NOT GUARANTEED.  AS STATED IN THIS SOFTWARE'S LICENSE, YOU BEAR
    * FULL AND SOLE RESPONSIBILITY FOR YOUR USE OF THIS SOFTWARE.  THAT INCLUDES
    * YOUR USE OF CALCULATIONS PERFORMED BY THIS SOFTWARE.  DO NOT RELY ON THESE
    * CALCULATIONS FOR NAVIGATION <em>OR FOR ANY OTHER PURPOSE</em>.
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
    * ACCURACY IS NOT GUARANTEED.  AS STATED IN THIS SOFTWARE'S LICENSE, YOU BEAR
    * FULL AND SOLE RESPONSIBILITY FOR YOUR USE OF THIS SOFTWARE.  THAT INCLUDES
    * YOUR USE OF CALCULATIONS PERFORMED BY THIS SOFTWARE.  DO NOT RELY ON THESE
    * CALCULATIONS FOR NAVIGATION <em>OR FOR ANY OTHER PURPOSE</em>.
    * </strong></p>
    *
    * @param lat1 Point 1 latitude
    * @param lon1 Point 1 longitude
    * @param lat2 Point 2 latitude
    * @param lon2 Point 2 longitude
    * @param unit The unit of measurement
    *
    * @return The distance from point 1 to point 2, expressed in terms of {@code unit}
    */
   public static double distance( final Latitude lat1, final Longitude lon1, final Latitude lat2, final Longitude lon2, final Unit unit ) {
      if( lat1 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LATITUDE_1_NULL );
      if( lon1 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LONGITUDE_1_NULL );
      if( lat2 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LATITUDE_2_NULL );
      if( lon2 == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LONGITUDE_2_NULL );
      if( unit == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.UNIT_NULL );

      // φ = latitude
      // λ = longitude
      // Δφ = latitude delta
      // Δλ = longitude delta

      final double φ1 = lat1.toRadians(),
                   φ2 = lat2.toRadians(),
                   λ1 = lon1.toRadians(),
                   λ2 = lon2.toRadians(),
                   Δφ = φ2 - φ1,
                   Δλ = λ2 - λ1;

      final double d = (2.0d * EARTH_RADIUS_KILOMETERS) * Math.asin( Math.sqrt( Math.pow(Math.sin(Δφ / 2.0d), 2.0d)
                                                                                + ( Math.cos(φ1) * Math.cos(φ2) * Math.pow(Math.sin(Δλ / 2.0d), 2.0d) ) ) );

      return d * unit.perKilometer;
   }
}
