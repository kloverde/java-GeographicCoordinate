/*
 * GeographicCoordinate
 * https://github.com/kloverde/GeographicCoordinate
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
 * Represents the directions on a 32-point compass, where points are 11.25 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection32 {
   NORTH               ( "N",     "354.38",  "5.62" ),
   NORTH_BY_EAST       ( "NbE",   "5.63",    "16.87" ),
   NORTH_NORTHEAST     ( "NNE",   "16.88",   "28.12" ),
   NORTHEAST_BY_NORTH  ( "NEbN",  "28.13",   "39.37" ),
   NORTHEAST           ( "NE",    "39.38",   "50.62" ),
   NORTHEAST_BY_EAST   ( "NEbE",  "50.63",   "61.87" ),
   EAST_NORTHEAST      ( "ENE",   "61.88",   "73.12" ),
   EAST_BY_NORTH       ( "EbN",   "73.13",   "84.37" ),
   EAST                ( "E",     "84.38",   "95.62" ),
   EAST_BY_SOUTH       ( "EbS",   "95.63",   "106.87" ),
   EAST_SOUTHEAST      ( "ESE",   "106.88",  "118.12" ),
   SOUTHEAST_BY_EAST   ( "SEbE",  "118.13",  "129.37" ),
   SOUTHEAST           ( "SE",    "129.38",  "140.62" ),
   SOUTHEAST_BY_SOUTH  ( "SEbS",  "140.63",  "151.87" ),
   SOUTH_SOUTHEAST     ( "SSE",   "151.88",  "163.12" ),
   SOUTH_BY_EAST       ( "SbE",   "163.13",  "174.37" ),
   SOUTH               ( "S",     "174.38",  "185.62" ),
   SOUTH_BY_WEST       ( "SbW",   "185.63",  "196.87" ),
   SOUTH_SOUTHWEST     ( "SSW",   "196.88",  "208.12" ),
   SOUTHWEST_BY_SOUTH  ( "SWbS",  "208.13",  "219.37" ),
   SOUTHWEST           ( "SW",    "219.38",  "230.62" ),
   SOUTHWEST_BY_WEST   ( "SWbW",  "230.63",  "241.87" ),
   WEST_SOUTHWEST      ( "WSW",   "241.88",  "253.12" ),
   WEST_BY_SOUTH       ( "WbS",   "253.13",  "264.37" ),
   WEST                ( "W",     "264.38",  "275.62" ),
   WEST_BY_NORTH       ( "WbN",   "275.63",  "286.87" ),
   WEST_NORTHWEST      ( "WNW",   "286.88",  "298.12" ),
   NORTHWEST_BY_WEST   ( "NWbW",  "298.13",  "309.37" ),
   NORTHWEST           ( "NW",    "309.38",  "320.62" ),
   NORTHWEST_BY_NORTH  ( "NWbN",  "320.63",  "331.87" ),
   NORTH_NORTHWEST     ( "NNW",   "331.88",  "343.12" ),
   NORTH_BY_WEST       ( "NbW",   "343.13",  "354.37" );

   private String abbreviation;

   private BigDecimal minimum, maximum;

   private static final BigDecimal BD360 = new BigDecimal( 360 ),
                                   STEP  = new BigDecimal( "11.25" );

   private static final Map<String, CompassDirection32> map = EnumHelper.populateEnumMap_stringKey( CompassDirection32.class, "getAbbreviation" );


   private CompassDirection32( final String abbr, final String min, final String max ) {
      abbreviation = abbr;
      minimum = new BigDecimal( min );
      maximum = new BigDecimal( max );

      assert( BigDecimalCompare.isGreaterThanOrEqualTo(getMinimum(), BigDecimal.ZERO) );
      assert( BigDecimalCompare.isLessThanOrEqualTo(getMaximum(), new BigDecimal(360)) );
   }

   /**
    * @return The direction abbreviation (southeast = SE, southeast by south = SEbS, etc.)
    */
   public String getAbbreviation() {
      return abbreviation;
   }

   /**
    * @return The lower bound for a given direction
    */
   public BigDecimal getMinimum() {
      return minimum;
   }

   /**
    * @return The upper bound for a given direction
    */
   public BigDecimal getMaximum() {
      return maximum;
   }

   public CompassDirection32 getPrevious() {
      final int ordinal = ordinal();
      final CompassDirection32 values[] = values();

      return values[ ordinal == 0 ? values.length - 1 : ordinal - 1 ];
   }

   public CompassDirection32 getNext() {
      final int ordinal = ordinal();
      final CompassDirection32 values[] = values();

      return values[ ordinal == values.length - 1 ? 0 : ordinal + 1 ];
   }

   /**
    * @param abbr A 32-point compass direction abbreviation ("NWbN", etc.)
    *
    * @return The compass direction corresponding to its abbreviation
    */
   public static CompassDirection32 getByAbbreviation( final String abbr ) {
      return map.get( abbr );
   }

   /**
    * @param bearing Bearing (degrees).  Value must be 0 &lt;= x &lt;= 360 (360 is treated as 0.0)
    *
    * @return The compass direction closest to the specified bearing
    */
   public static CompassDirection32 getByBearing( final BigDecimal bearing ) {
      BigDecimal newBearing;
      final int idx;
      final CompassDirection32 values[];
      CompassDirection32 dir;

      if( BigDecimalCompare.isLessThan(bearing, BigDecimal.ZERO) || BigDecimalCompare.isGreaterThan(bearing, BD360) ) {
         throw new GeographicCoordinateException( "Bearing must be in the range [0, 360]" );
      }

      values = values();
      newBearing = BigDecimalCompare.isEqualTo( bearing, BD360 ) ? BigDecimal.ZERO : bearing;
      newBearing = bearing.setScale( 2,  RoundingMode.HALF_UP );
      idx = Math.min( newBearing.divide(STEP, 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).toBigInteger().intValue(), values.length - 1 );
      dir = values[ idx ];

      // If the bearing is greater than the direction's max, we need to go to the next one.
      // If the bearing is less than the min, we need to go the previous one.

      if( !isBearingWithinRange(newBearing, dir) ) {
         if( BigDecimalCompare.isGreaterThan(newBearing, dir.getMaximum()) ) {
            dir = dir.getNext();
         } else if( BigDecimalCompare.isLessThan(bearing, dir.getMinimum()) ) {
            dir = dir.getPrevious();
         }
      }

      return dir;
   }

   private static boolean isBearingWithinRange( final BigDecimal bearing, final CompassDirection32 direction ) {
      if( direction != NORTH ) {
         if( BigDecimalCompare.isWithinInclusiveRange(bearing, direction.getMinimum(), direction.getMaximum()) ) {
            return true;
         }
      } else {
         // North is a special case where the minimum is greater than the maximum
         if( BigDecimalCompare.isWithinInclusiveRange(bearing, NORTH.getMinimum(), BD360) ||
             BigDecimalCompare.isWithinInclusiveRange(bearing, BigDecimal.ZERO, NORTH.getMaximum()) ) {
            return true;
         }
      }

      return false;
   }
}
