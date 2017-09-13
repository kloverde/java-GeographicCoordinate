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


/**
 * Represents the directions on a 32-point compass, where points are 11.25 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection {
   NORTH             ( "N",    "354.38", "0.00",   "5.62" ),
   NORTH_BY_EAST     ( "NbE",  "5.63",   "11.25",  "16.87" ),
   NORTH_NORTHEAST   ( "NNE",  "16.88",  "22.50",  "28.12" ),
   NORTHEAST_BY_NORTH( "NEbN", "28.13",  "33.75",  "39.37" ),
   NORTHEAST         ( "NE",   "39.38",  "45.00",  "50.62" ),
   NORTHEAST_BY_EAST ( "NEbE", "50.63",  "56.25",  "61.87" ),
   EAST_NORTHEAST    ( "ENE",  "61.88",  "67.50",  "73.12" ),
   EAST_BY_NORTH     ( "EbN",  "73.13",  "78.75",  "84.37" ),
   EAST              ( "E",    "84.38",  "90.00",  "95.62" ),
   EAST_BY_SOUTH     ( "EbS",  "95.63",  "101.25", "106.87" ),
   EAST_SOUTHEAST    ( "ESE",  "106.88", "112.50", "118.12" ),
   SOUTHEAST_BY_EAST ( "SEbE", "118.13", "123.75", "129.37" ),
   SOUTHEAST         ( "SE",   "129.38", "135.00", "140.62" ),
   SOUTHEAST_BY_SOUTH( "SEbS", "140.63", "146.25", "151.87" ),
   SOUTH_SOUTHEAST   ( "SSE",  "151.88", "157.50", "163.12" ),
   SOUTH_BY_EAST     ( "SbE",  "163.13", "168.75", "174.37" ),
   SOUTH             ( "S",    "174.38", "180.00", "185.62" ),
   SOUTH_BY_WEST     ( "SbW",  "185.63", "191.25", "196.87" ),
   SOUTH_SOUTHWEST   ( "SSW",  "196.88", "202.50", "208.12" ),
   SOUTHWEST_BY_SOUTH( "SWbS", "208.13", "213.75", "219.37" ),
   SOUTHWEST         ( "SW",   "219.38", "225.00", "230.62" ),
   SOUTHWEST_BY_WEST ( "SWbW", "230.63", "236.25", "241.87" ),
   WEST_SOUTHWEST    ( "WSW",  "241.88", "247.50", "253.12" ),
   WEST_BY_SOUTH     ( "WbS",  "253.13", "258.75", "264.37" ),
   WEST              ( "W",    "264.38", "270.00", "275.62" ),
   WEST_BY_NORTH     ( "WbN",  "275.63", "281.25", "286.87" ),
   WEST_NORTHWEST    ( "WNW",  "286.88", "292.50", "298.12" ),
   NORTHWEST_BY_WEST ( "NWbW", "298.13", "303.75", "309.37" ),
   NORTHWEST         ( "NW",   "309.38", "315.00", "320.62" ),
   NORTHWEST_BY_NORTH( "NWbN", "320.63", "326.25", "331.87" ),
   NORTH_NORTHWEST   ( "NNW",  "331.88", "337.50", "343.12" ),
   NORTH_BY_WEST     ( "NbW",  "343.13", "348.75", "354.37" );

   private String abbreviation;

   private BigDecimal minimum, middle, maximum;

   private static final BigDecimal BD360 = new BigDecimal( 360 ),
                                   STEP  = new BigDecimal( "11.25" );

   private static final Map<String, CompassDirection> map = EnumHelper.populateEnumMap_stringKey( CompassDirection.class, "getAbbreviation" );


   private CompassDirection( final String abbr, final String min, final String mid, final String max ) {
      abbreviation = abbr;
      minimum = new BigDecimal( min );
      middle  = new BigDecimal( mid );
      maximum = new BigDecimal( max );
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
    * @return The middle of the range for a given direction
    */
   public BigDecimal getMiddle() {
      return middle;
   }

   /**
    * @return The upper bound for a given direction
    */
   public BigDecimal getMaximum() {
      return maximum;
   }

   public CompassDirection getPrevious() {
      final int ordinal = ordinal();
      final CompassDirection values[] = values();

      return values[ ordinal == 0 ? values.length - 1 : ordinal - 1 ];
   }

   public CompassDirection getNext() {
      final int ordinal = ordinal();
      final CompassDirection values[] = values();

      return values[ ordinal == values.length - 1 ? 0 : ordinal + 1 ];
   }

   /**
    * @param abbr A 32-point compass direction abbreviation ("NWbN", etc.)
    *
    * @return The compass direction corresponding to its abbreviation
    */
   public static CompassDirection getByAbbreviation( final String abbr ) {
      return map.get( abbr );
   }

   /**
    * @param bearing Bearing (degrees)
    *
    * @return The compass direction closest to the specified bearing
    */
   public static CompassDirection getByBearing( final BigDecimal bearing ) {
      final BigDecimal roundedBearing;
      final int idx;
      final CompassDirection values[];
      CompassDirection dir;

      if( bearing.compareTo(BigDecimal.ZERO) == -1 || bearing.compareTo(BD360) >= 0 ) throw new GeographicCoordinateException( "Bearing must be 0 <= x < 360" );

      values = values();
      roundedBearing = bearing.setScale( 2,  RoundingMode.HALF_UP );
      idx = Math.min( roundedBearing.divide( STEP, 2, RoundingMode.HALF_UP ).setScale( 0, RoundingMode.HALF_UP ).toBigInteger().intValue(), values.length - 1 );
      dir = values[ idx ];

      // If the bearing is greater than the direction's max, we need to go to the next one.
      // If the bearing is less than the min, we need to go the previous one.

      if( !isBearingWithinRange(roundedBearing, dir) ) {
         if( roundedBearing.compareTo(dir.getMaximum()) == 1 ) {
            dir = dir.getNext();
         } else if( roundedBearing.compareTo(dir.getMinimum()) == -1 ) {
            dir = dir.getPrevious();
         }
      }

      return dir;
   }

   private static boolean isBearingWithinRange( final BigDecimal bearing, final CompassDirection direction ) {
      if( direction != NORTH ) {
         if( bearing.compareTo(direction.getMinimum()) >= 0 && bearing.compareTo(direction.getMaximum()) <= 0 ) {
            return true;
         }
      } else {
         // North is a special case where the minimum is greater than the maximum

         if( (bearing.compareTo(NORTH.getMinimum()) >= 0 && bearing.compareTo(BD360) == -1) ||                     // 354.38 <= bearing < 360
             (bearing.compareTo(NORTH.getMiddle()) >= 0 && bearing.compareTo(direction.getMaximum()) <= 0) ) {     // 0 <= bearing <= 5.62
            return true;
         }
      }

      return false;
   }
}
