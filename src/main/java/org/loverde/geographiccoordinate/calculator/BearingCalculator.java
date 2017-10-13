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

import org.loverde.geographiccoordinate.Bearing;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.util.number.bigdecimal.BigDecimalCompare;


/**
 * This class calculates initial bearing and back azimuth.
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
 * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
 * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
 * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
 * </strong></p>
 */
public class BearingCalculator {

   private static final BigDecimal BD_360 = new BigDecimal( 360 ),
                                   BD_180 = new BigDecimal( 180 ),
                                   BD_NEG_180 = new BigDecimal( -180 );


   /**
    * <p>
    * Calculates the initial bearing that will take you from point A to point B.
    * Keep in mind that the bearing will change over the course of the trip and will need to be recalculated.
    * </p>
    *
    * <p><strong>
    * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
    * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
    * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
    * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
    * </strong></p>
    *
    * @param compassType The returned {@code Bearing} will be parameterized with this type, allowing you to safely cast it
    * @param from The departing point
    * @param to The destination point
    *
    * @return The initial bearing from A to B, and a mapping of the bearing to an 8, 16 or 32-point compass direction, depending on {@code compassType}
    *
    * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">http://www.movable-type.co.uk/scripts/latlong.html</a>.
    */
   public static Bearing<? extends CompassDirection> initialBearing( final Class<? extends CompassDirection> compassType, final Point from, final Point to ) {
      return newBearing( compassType, calculateBearing(from, to) );
   }

   /**
    * Calculates the back azimuth - the bearing that gets you back to your starting point
    *
    * <p><strong>
    * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
    * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
    * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
    * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
    * </strong></p>
    *
    * @param compassType The returned {@code Bearing} will be parameterized with this type, allowing you to safely cast it
    * @param initialBearing The initial bearing
    *
    * @return The back azimuth based on initial bearing
    */
   public static Bearing<? extends CompassDirection> backAzimuth( final Class<? extends CompassDirection> compassType, final BigDecimal initialBearing ) {
      return newBearing( compassType, calculateBackAzimuth(initialBearing) );
   }

   private static Bearing<? extends CompassDirection> newBearing( final Class<? extends CompassDirection> compassClass, final BigDecimal angle ) {
      if( compassClass == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_COMPASS_DIRECTION_NULL );

      if( compassClass == CompassDirection8.class ) {
         return new Bearing<CompassDirection8>( CompassDirection8.getByBearing(angle), angle );
      } else if( compassClass == CompassDirection16.class ) {
         return new Bearing<CompassDirection16>( CompassDirection16.getByBearing(angle), angle );
      } else if( compassClass == CompassDirection32.class ) {
         return new Bearing<CompassDirection32>( CompassDirection32.getByBearing(angle), angle );
      }

      return null;
   }

   private static BigDecimal calculateBearing( final Point from, final Point to ) {
      if( from == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_FROM_NULL );
      if( to == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_TO_NULL );
      if( from.getLatitude() == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_FROM_LATITUDE_NULL );
      if( from.getLongitude() == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_FROM_LONGITUDE_NULL );
      if( to.getLatitude() == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_TO_LATITUDE_NULL );
      if( to.getLongitude() == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_TO_LONGITUDE_NULL );

      final double fromLatRadians = from.getLatitude().toRadians(),
                   fromLonRadians = from.getLongitude().toRadians(),
                   toLatRadians   = to.getLatitude().toRadians(),
                   toLonRadians   = to.getLongitude().toRadians(),
                   deltaLon       = toLonRadians - fromLonRadians;

      final double y = Math.sin( deltaLon ) * Math.cos( toLatRadians );
      final double x = Math.cos( fromLatRadians ) * Math.sin( toLatRadians ) -
                       Math.sin( fromLatRadians ) * Math.cos( toLatRadians ) * Math.cos( deltaLon );

      final double bearing = Math.toDegrees( Math.atan2(y, x) );
      final double normalizedBearing = normalizeBearing( bearing );

      return new BigDecimal( normalizedBearing );
   }

   private static BigDecimal calculateBackAzimuth( final BigDecimal bearing ) {
      final BigDecimal zeroedBearing;
      BigDecimal backAzimuth;

      if( bearing == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_BEARING_NULL );
      if( !BigDecimalCompare.isWithinInclusiveRange(bearing, BigDecimal.ZERO, BD_360) ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE );

       zeroedBearing = BigDecimalCompare.isEqualTo( bearing, BD_360 ) ? BigDecimal.ZERO : bearing;

      if( BigDecimalCompare.isEqualTo(zeroedBearing, BD_180) || BigDecimalCompare.isEqualTo(zeroedBearing, BD_NEG_180)) {
         backAzimuth = BigDecimal.ZERO;
      } else if( BigDecimalCompare.isLessThan(zeroedBearing, BD_180)) {
         backAzimuth = zeroedBearing.add( BD_180 );
      } else {
         backAzimuth = zeroedBearing.subtract( BD_180 );
      }

      backAzimuth = normalizeBearing( backAzimuth );

      return backAzimuth;
   }

   private static double normalizeBearing( final double bearing ) {
      return (bearing + 360) % 360;
   }

   private static BigDecimal normalizeBearing( final BigDecimal bearing ) {
      return bearing.add( BD_360 ).remainder( BD_360 );
   }
}
