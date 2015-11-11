/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


public class GeographicCoordinateException extends Exception {

   private static final long serialVersionUID = -5390540198404132694L;

   public static final class Messages {
      private static final String LATITUDE = "Latitude",
                                  LONGITUDE = "Longitude",

                                  MAX_LATITUDE_DEGREES = "90",
                                  MAX_LONGITUDE_DEGREES = "180",

                                  DEGREES_RANGE = " degrees must be in a range of 0-",
                                  MINUTES_AND_SECONDS_MUST_BE_ZERO = " minutes and seconds must be 0 when degrees is ",
                                  MINUTES_RANGE = " minutes must be in a range of 0-59",
                                  SECONDS_RANGE = " seconds must be in a range of 0-59.9[..]";

      public static final String COORDINATE_TYPE_NULL = "Coordinate type is null",
                                 DIRECTION_NULL = "Coordinate direction is null",

                                 LATITUDE_DEGREES_RANGE = LATITUDE + DEGREES_RANGE + MAX_LATITUDE_DEGREES,
                                 LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO = LATITUDE + MINUTES_AND_SECONDS_MUST_BE_ZERO + MAX_LATITUDE_DEGREES,
                                 LATITUDE_MINUTES_RANGE = LATITUDE + MINUTES_RANGE,
                                 LATITUDE_SECONDS_RANGE = LATITUDE + SECONDS_RANGE,
                                 LATITUDE_1_NULL = LATITUDE + " 1 is null",
                                 LATITUDE_2_NULL = LATITUDE + " 2 is null",

                                 LONGITUDE_DEGREES_RANGE = LONGITUDE + DEGREES_RANGE + MAX_LONGITUDE_DEGREES,
                                 LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO = LONGITUDE + MINUTES_AND_SECONDS_MUST_BE_ZERO + MAX_LONGITUDE_DEGREES,
                                 LONGITUDE_MINUTES_RANGE = LONGITUDE + MINUTES_RANGE,
                                 LONGITUDE_SECONDS_RANGE = LONGITUDE + SECONDS_RANGE,
                                 LONGITUDE_1_NULL = LONGITUDE + " 1 is null",
                                 LONGITUDE_2_NULL = LONGITUDE + " 2 is null",

                                 POINT_1_NULL = "Point 1 is null",
                                 POINT_2_NULL = "Point 2 is null",

                                 UNIT_NULL = "Unit is null";
   }

   public GeographicCoordinateException() {
      super();
   }

   public GeographicCoordinateException( final String msg ) {
      super( msg );
   }

   public GeographicCoordinateException( final Throwable t ) {
      super( t );
   }

   public GeographicCoordinateException( final String msg, final Throwable t ) {
      super( msg, t );
   }
}
