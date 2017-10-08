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


/**
 * This class calculates the initial bearing between two points in reference to magnetic north -
 * in other words, the bearing you would follow on a compass to get from point A to point B.
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
 * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
 * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
 * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
 * </strong></p>
 *
 * This class implements the formula found at
 * <a href="http://www.movable-type.co.uk/scripts/latlong.html">http://www.movable-type.co.uk/scripts/latlong.html</a>.
 */
public class BearingCalculator {

   /**
    * Calculates the initial bearing that will take you from point A to point B.
    * Keep in mind that the bearing will change over the course of the trip and will need to be recalculated.
    *
    * @param compassType The returned Bearing will be parameterized as this type, allowing you to safely cast it
    * @param from The departing point
    * @param to The destination point
    *
    * @return The initial bearing from A to B, and a mapping of the bearing to an 8, 16 or 32-point compass direction, depending on {@code compassDirectionType}
    */
   public static Bearing<? extends CompassDirection> initialBearing( final Class<? extends CompassDirection> compassType, final Point from, final Point to ) {
      return newBearing( compassType, calculateBearing(from, to) );
   }

   /**
    * Calculates the back azimuth - the bearing that gets you back to your starting point
    *
    * @param compassType The returned Bearing will be parameterized as this type, allowing you to safely cast it
    * @param bearing Your starting point
    *
    * @return
    */
   public static Bearing<? extends CompassDirection> backAzimuth( final Class<? extends CompassDirection> compassType, final Bearing<? extends CompassDirection> bearing ) {
      return newBearing( compassType, calculateBackAzimuth(bearing.getBearing()) );
   }

   private static Bearing<? extends CompassDirection> newBearing( final Class<? extends CompassDirection> compassClass, final BigDecimal angle ) {
      if( compassClass == null ) throw new GeographicCoordinateException( "" );

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
      if( from == null ) throw new GeographicCoordinateException( "'from' is null" );
      if( to == null ) throw new GeographicCoordinateException( "'to' is null" );
      if( from.getLatitude() == null ) throw new GeographicCoordinateException( "'from' latitude is null" );
      if( from.getLongitude() == null ) throw new GeographicCoordinateException( "'from' longitude is null" );
      if( to.getLatitude() == null ) throw new GeographicCoordinateException( "'to' latitude is null" );
      if( to.getLongitude() == null ) throw new GeographicCoordinateException( "'to' longitude is null" );

      final double fromLatRadians = from.getLatitude().toRadians(),
                   fromLonRadians = from.getLongitude().toRadians(),
                   toLatRadians   = to.getLatitude().toRadians(),
                   toLonRadians   = to.getLongitude().toRadians(),
                   deltaLon       = toLonRadians - fromLonRadians;

      final double y = Math.sin( deltaLon ) * Math.cos( toLatRadians );
      final double x = Math.cos( fromLatRadians ) * Math.sin( toLatRadians ) -
                       Math.sin( fromLatRadians ) * Math.cos( toLatRadians ) * Math.cos( deltaLon );

      final double bearing = Math.toDegrees( Math.atan2(y,  x) );
      final double normalizedBearing = (bearing + 360) % 360;

      return new BigDecimal( normalizedBearing );
   }

   private static BigDecimal calculateBackAzimuth( final BigDecimal bearing ) {
      return bearing;
   }
}
