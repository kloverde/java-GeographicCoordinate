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

package org.loverde.geographiccoordinate.calculator;

import java.math.BigDecimal;

import org.loverde.geographiccoordinate.Angle;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;


public class AngleCalculator {

   /**
    * Calculates the angle of a line connecting {@code point1} and ${code point2} in reference to the equator
    *
    * @param from The first set of latitude/longitude
    * @param to The second set of latitude/longitude
    *
    * @return The angle of A and B, and a mapping of the angle to a 32-point compass direction
    */
   public static Angle<CompassDirection32> angle32( final Point from, final Point to ) {
      final BigDecimal bdAngle = new BigDecimal( calculateAngle(from, to) );
      final Angle<CompassDirection32> angle = new Angle<>( CompassDirection32.getByAngle(bdAngle), bdAngle.doubleValue() );

      return angle;
   }

   /**
    * Calculates the angle of a line connecting {@code point1} and ${code point2} in reference to the equator
    *
    * @param from The first set of latitude/longitude
    * @param to The second set of latitude/longitude
    *
    * @return The angle of A and B, and a mapping of the angle to a 16-point compass direction
    */
   public static Angle<CompassDirection16> angle16( final Point from, final Point to ) {
      final BigDecimal bdAngle = new BigDecimal( calculateAngle(from, to) );
      final Angle<CompassDirection16> angle = new Angle<>( CompassDirection16.getByAngle(bdAngle), bdAngle.doubleValue() );

      return angle;
   }

   /**
    * Calculates the angle of a line connecting {@code point1} and ${code point2} in reference to the equator
    *
    * @param from The first set of latitude/longitude
    * @param to The second set of latitude/longitude
    *
    * @return The angle of A and B, and a mapping of the angle to an 8-point compass direction
    */
   public static Angle<CompassDirection8> angle8( final Point from, final Point to ) {
      final BigDecimal bdAngle = new BigDecimal( calculateAngle(from, to) );
      final Angle<CompassDirection8> angle = new Angle<>( CompassDirection8.getByAngle(bdAngle), bdAngle.doubleValue() );

      return angle;
   }

   // http://www.movable-type.co.uk/scripts/latlong.html
   // see bearing section
   private static double calculateAngle( final Point from, final Point to ) {
      if( from == null ) throw new GeographicCoordinateException( "'from' is null" );
      if( to == null ) throw new GeographicCoordinateException( "'to' is null" );

      final double angle = 0;//Math.atan2( to.getLatitude().toDouble() - from.getLatitude().toDouble(),
                             //            to.getLongitude().toDouble() - from.getLongitude().toDouble() );

      return angle;
   }
}
