/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


public class GeographicCoordinateException extends RuntimeException {

   private static final long serialVersionUID = -5390540198404132694L;

   /**
    * No guarantee is made that these constants will remain unchanged, or that they will not be renamed or removed.
    * Anything in this class can change at any time.  These constants are exposed for convenience in keeping the
    * internal implementation and JUnit tests in sync.  Do not circumvent the package protection to use these
    * constants in client code.
    */
   protected static final class Messages {
      public static final String DEGREES_RANGE = " degrees must be in a range of 0-",
                                 MINUTES_AND_SECONDS_MUST_BE_ZERO = " minutes and seconds must be 0 when degrees is ",
                                 MINUTES_RANGE = " minutes must be in a range of 0-59",
                                 SECONDS_RANGE = " seconds must be in a range of 0-59.9[..]";

      public static final String DIRECTION_INVALID  = "Direction is invalid",
                                 DIRECTION_NULL     = "Direction is null",
                                 DISALLOWED_EXTENDS = "This class may only be extended by Latitude or Longitude",
                                 LATITUDE_NULL      = "Latitude is null",
                                 LONGITUDE_NULL     = "Longitude is null",
                                 NAME_NULL          = "Name is null";
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
