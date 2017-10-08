/*
 * GeographicCoordinate
 * https://github.com/kloverde/java-GeographicCoordinate
 *
 * Copyright (c) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * Donations:  https://paypal.me/KurtisLoVerde/5
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     2. Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *     3. Neither the name of the copyright holder nor the names of its
 *        contributors may be used to endorse or promote products derived from
 *        this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.loverde.geographiccoordinate.exception;


public class GeographicCoordinateException extends RuntimeException {

   private static final long serialVersionUID = -5390540198404132694L;

   /**
    * No guarantee is made that these constants will remain unchanged, or that they will not be renamed or removed.
    * Anything in this class can change at any time.  These constants are exposed for convenience in keeping the
    * internal implementation and JUnit tests in sync.  Do not circumvent the package protection to use these
    * constants in client code.
    */
   public static final class Messages {
      public static final String DEGREES_RANGE = " degrees must be in a range of 0-",
                                 MINUTES_AND_SECONDS_MUST_BE_ZERO = " minutes and seconds must be 0 when degrees is ",
                                 MINUTES_RANGE = " minutes must be in a range of 0-59",
                                 SECONDS_RANGE = " seconds must be in a range of 0-59.9[..]";

      public static final String DIRECTION_INVALID  = "Direction is invalid",
                                 DIRECTION_NULL     = "Direction is null",
                                 DISALLOWED_EXTENDS = "This class may only be extended by Latitude or Longitude",
                                 LATITUDE_NULL      = "Latitude is null",
                                 LOCALE_NULL        = "Locale is null",
                                 LONGITUDE_NULL     = "Longitude is null",
                                 NAME_NULL          = "Name is null";

      public static final String BEARING_COMPASS_DIRECTION_NULL = "CompassDirection class is null",
                                 BEARING_FROM_NULL              = "'from' is null",
                                 BEARING_TO_NULL                = "'to' is null",
                                 BEARING_FROM_LATITUDE_NULL     = "'from' latitude is null",
                                 BEARING_FROM_LONGITUDE_NULL    = "'from' longitude is null",
                                 BEARING_TO_LATITUDE_NULL       = "'to' latitude is null",
                                 BEARING_TO_LONGITUDE_NULL      = "'to' longitude is null";
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
