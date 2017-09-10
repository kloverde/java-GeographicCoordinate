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

import java.util.Map;

import org.loverde.geographiccoordinate.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.EnumHelper;


/**
 * Represents the directions on a 32-point compass, where points are 11.25 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection {
   NORTH( "N" ),
   NORTH_BY_EAST( "NbE" ),
   NORTH_NORTHEAST( "NNE" ),
   NORTHEAST_BY_NORTH( "NEbN" ),
   NORTHEAST( "NE" ),
   NORTHEAST_BY_EAST( "NEbE" ),
   EAST_NORTHEAST( "ENE" ),
   EAST_BY_NORTH( "EbN" ),
   EAST( "E" ),
   EAST_BY_SOUTH( "EbS" ),
   EAST_SOUTHEAST( "ESE" ),
   SOUTHEAST_BY_EAST( "SEbE" ),
   SOUTHEAST( "SE" ),
   SOUTHEAST_BY_SOUTH( "SEbS" ),
   SOUTH_SOUTHEAST( "SSE" ),
   SOUTH_BY_EAST( "SbE" ),
   SOUTH( "S" ),
   SOUTH_BY_WEST( "SbW" ),
   SOUTH_SOUTHWEST( "SSW"),
   SOUTHWEST_BY_SOUTH( "SWbS" ),
   SOUTHWEST( "SW" ),
   SOUTHWEST_BY_WEST( "SWbW" ),
   WEST_SOUTHWEST( "WSW" ),
   WEST_BY_SOUTH( "WbS" ),
   WEST( "W" ),
   WEST_BY_NORTH( "WbN" ),
   WEST_NORTHWEST( "WNW" ),
   NORTHWEST_BY_WEST( "NWbW" ),
   NORTHWEST( "NW" ),
   NORTHWEST_BY_NORTH( "NWbN" ),
   NORTH_NORTHWEST( "NNW" ),
   NORTH_BY_WEST( "NbW" );

   private String abbreviation;

   private static final double STEP = 11.25;
   private static final Map<String, CompassDirection> map = EnumHelper.populateEnumMap_stringKey( CompassDirection.class, "getAbbreviation" );


   private CompassDirection( final String abbr ) {
      abbreviation = abbr;
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
   public double getMin() {
      final int ordinal = ordinal();

      return ordinal == 0 ? 360.0 - (STEP / 2.0) : (ordinal * STEP) - (STEP / 2.0);
   }

   /**
    * @return The middle of the range for a given direction
    */
   public double getMiddleAzimuth() {
      return ordinal() * STEP;
   }

   /**
    * @return The upper bound for a given direction
    */
   public double getMax() {
      return (ordinal() * STEP) + (STEP / 2.0);
   }

   public CompassDirection getNextDirection() {
      final int ordinal = ordinal();
      final CompassDirection values[] = values();

      return values[ ordinal == values.length - 1 ? 0 : ordinal + 1 ];
   }

   /**
    * Use a compass direction abbreviation to get its corresponding enum member
    *
    * @param abbr The direction abbreviation ("NWbN", etc.)
    *
    * @return The compass direction corresponding to its abbreviation
    */
   public static CompassDirection getDirectionByAbbreviation( final String abbr ) {
      return map.get( abbr );
   }

   public static CompassDirection getDirectionByAngle( final double angleInDegrees ) throws GeographicCoordinateException{
      if( angleInDegrees < 0 || angleInDegrees >= 360 ) throw new GeographicCoordinateException( "Angle must be 0 <= angle < 360" );

      final double step = 11.25;
      final double start = 360 - (step / 2);  // minimum bound for north

      return values()[ (int)(angleInDegrees / step) ];
   }
}
