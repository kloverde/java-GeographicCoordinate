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

package org.loverde.geographiccoordinate.compass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.EnumHelper;
import org.loverde.util.number.bigdecimal.BigDecimalCompare;


/**
 * Represents the directions on a 16-point compass, where points are 22.5 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection16 implements CompassDirection {
   NORTH            ( "N",     "348.75",   "0.00",     "11.24" ),
   NORTH_NORTHEAST  ( "NNE",   "11.25",    "22.5",     "33.74" ),
   NORTHEAST        ( "NE",    "33.75",    "45.00",    "56.24" ),
   EAST_NORTHEAST   ( "ENE",   "56.25",    "67.50",    "78.74" ),
   EAST             ( "E",     "78.75",    "90.00",    "101.24" ),
   EAST_SOUTHEAST   ( "ESE",   "101.25",   "112.50",   "123.74" ),
   SOUTHEAST        ( "SE",    "123.75",   "135.00",   "146.24" ),
   SOUTH_SOUTHEAST  ( "SSE",   "146.25",   "157.50",   "168.74" ),
   SOUTH            ( "S",     "168.75",   "180.00",   "191.24" ),
   SOUTH_SOUTHWEST  ( "SSW",   "191.25",   "202.50",   "213.74" ),
   SOUTHWEST        ( "SW",    "213.75",   "225.00",   "236.24" ),
   WEST_SOUTHWEST   ( "WSW",   "236.25",   "247.50",   "258.74" ),
   WEST             ( "W",     "258.75",   "270.00",   "281.24" ),
   WEST_NORTHWEST   ( "WNW",   "281.25",   "292.50",   "303.74" ),
   NORTHWEST        ( "NW",    "303.75",   "315.00",   "326.24" ),
   NORTH_NORTHWEST  ( "NNW",   "326.25",   "337.50",   "348.74" );

   private String abbreviation;

   private BigDecimal minimum, middle, maximum;

   private static final BigDecimal BD360 = new BigDecimal( 360 ),
                                   STEP  = new BigDecimal( "22.5" );

   private static final Map<String, CompassDirection16> map = EnumHelper.populateEnumMap_stringKey( CompassDirection16.class, "getAbbreviation" );


   private CompassDirection16( final String abbr, final String min, final String mid, final String max ) {
      abbreviation = abbr;
      minimum = new BigDecimal( min );
      middle  = new BigDecimal( mid );
      maximum = new BigDecimal( max );
   }

   /**
    * @return The direction abbreviation (southeast = SE, west-southwest = WSW, etc.)
    */
   @Override
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * @return The lower bound for a given direction
    */
   @Override
   public BigDecimal getMinimum() {
      return minimum;
   }

   /**
    * @return The middle azimuth - this is when you're heading &quot;dead on&quot; in the given direction
    */
   @Override
   public BigDecimal getMiddle() {
      return middle;
   }

   /**
    * @return The upper bound for a given direction
    */
   @Override
   public BigDecimal getMaximum() {
      return maximum;
   }

   @Override
   public CompassDirection16 getPrevious() {
      final int ordinal = ordinal();
      final CompassDirection16 values[] = values();

      return values[ ordinal == 0 ? values.length - 1 : ordinal - 1 ];
   }

   @Override
   public CompassDirection16 getNext() {
      final int ordinal = ordinal();
      final CompassDirection16 values[] = values();

      return values[ ordinal == values.length - 1 ? 0 : ordinal + 1 ];
   }

   /**
    * @param abbr A 16-point compass direction abbreviation ("ENE", etc.)
    *
    * @return The compass direction corresponding to its abbreviation
    */
   public static CompassDirection16 getByAbbreviation( final String abbr ) {
      return map.get( abbr );
   }

   /**
    * @param angle Angle in degrees.  Value must be 0 &lt;= x &lt;= 360 (360 is treated as 0.0)
    *
    * @return The compass direction closest to the specified angle
    */
   public static CompassDirection16 getByAngle( final BigDecimal angle ) {
      BigDecimal newAngle;
      final int idx;
      final CompassDirection16 values[];
      CompassDirection16 dir;

      if( BigDecimalCompare.isLessThan(angle, BigDecimal.ZERO) || BigDecimalCompare.isGreaterThan(angle, BD360) ) {
         throw new GeographicCoordinateException( "Angle must be in the range [0, 360]" );
      }

      values = values();
      newAngle = BigDecimalCompare.isEqualTo( angle, BD360 ) ? BigDecimal.ZERO : angle;
      newAngle = angle.setScale( 2,  RoundingMode.HALF_UP );
      idx = Math.min( newAngle.divide(STEP, 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).toBigInteger().intValue(), values.length - 1 );
      dir = values[ idx ];

      // If the angle is greater than the direction's max, we need to go to the next one.
      // If the angle is less than the min, we need to go the previous one.

      if( !isAngleWithinRange(newAngle, dir) ) {
         if( BigDecimalCompare.isGreaterThan(newAngle, dir.getMaximum()) ) {
            dir = dir.getNext();
         } else if( BigDecimalCompare.isLessThan(angle, dir.getMinimum()) ) {
            dir = dir.getPrevious();
         }
      }

      return dir;
   }

   private static boolean isAngleWithinRange( final BigDecimal angle, final CompassDirection16 direction ) {
      if( direction != NORTH ) {
         if( BigDecimalCompare.isWithinInclusiveRange(angle, direction.getMinimum(), direction.getMaximum()) ) {
            return true;
         }
      } else {
         // North is a special case where the minimum is greater than the maximum
         if( BigDecimalCompare.isWithinInclusiveRange(angle, NORTH.getMinimum(), BD360) ||
             BigDecimalCompare.isWithinInclusiveRange(angle, BigDecimal.ZERO, NORTH.getMaximum()) ) {
            return true;
         }
      }

      return false;
   }
}
