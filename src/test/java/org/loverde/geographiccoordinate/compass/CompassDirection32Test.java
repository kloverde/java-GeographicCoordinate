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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;


public class CompassDirection32Test {

   @Rule
   public ExpectedException thrown = ExpectedException.none();


   @Test
   public void getPrevious() {
      assertEquals( CompassDirection32.EAST, CompassDirection32.EAST_BY_SOUTH.getPrevious() );   // verify that getPrevious moves backward by 1
      assertEquals( CompassDirection32.NORTH_BY_WEST, CompassDirection32.NORTH.getPrevious() );  // verify loop-around
   }

   @Test
   public void getNext() {
     assertEquals( CompassDirection32.NORTH_BY_EAST, CompassDirection32.NORTH.getNext() );  // verify that getNext moves forward by 1
     assertEquals( CompassDirection32.NORTH, CompassDirection32.NORTH_BY_WEST.getNext());   // verify loop-around
   }

   @Test
   public void getByAbbreviation() {
      assertEquals( CompassDirection32.NORTH,              CompassDirection32.getByAbbreviation(CompassDirection32.NORTH.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTH_BY_EAST,      CompassDirection32.getByAbbreviation(CompassDirection32.NORTH_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTH_NORTHEAST,    CompassDirection32.getByAbbreviation(CompassDirection32.NORTH_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHEAST_BY_NORTH, CompassDirection32.getByAbbreviation(CompassDirection32.NORTHEAST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHEAST,          CompassDirection32.getByAbbreviation(CompassDirection32.NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHEAST_BY_EAST,  CompassDirection32.getByAbbreviation(CompassDirection32.NORTHEAST_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection32.EAST_NORTHEAST,     CompassDirection32.getByAbbreviation(CompassDirection32.EAST_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.EAST_BY_NORTH,      CompassDirection32.getByAbbreviation(CompassDirection32.EAST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection32.EAST,               CompassDirection32.getByAbbreviation(CompassDirection32.EAST.getAbbreviation()) );
      assertEquals( CompassDirection32.EAST_BY_SOUTH,      CompassDirection32.getByAbbreviation(CompassDirection32.EAST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection32.EAST_SOUTHEAST,     CompassDirection32.getByAbbreviation(CompassDirection32.EAST_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHEAST_BY_EAST,  CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHEAST_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHEAST,          CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHEAST_BY_SOUTH, CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHEAST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTH_SOUTHEAST,    CompassDirection32.getByAbbreviation(CompassDirection32.SOUTH_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTH_BY_EAST,      CompassDirection32.getByAbbreviation(CompassDirection32.SOUTH_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTH,              CompassDirection32.getByAbbreviation(CompassDirection32.SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTH_BY_WEST,      CompassDirection32.getByAbbreviation(CompassDirection32.SOUTH_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTH_SOUTHWEST,    CompassDirection32.getByAbbreviation(CompassDirection32.SOUTH_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHWEST_BY_SOUTH, CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHWEST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHWEST,          CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.SOUTHWEST_BY_WEST,  CompassDirection32.getByAbbreviation(CompassDirection32.SOUTHWEST_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection32.WEST_SOUTHWEST,     CompassDirection32.getByAbbreviation(CompassDirection32.WEST_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.WEST_BY_SOUTH,      CompassDirection32.getByAbbreviation(CompassDirection32.WEST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection32.WEST,               CompassDirection32.getByAbbreviation(CompassDirection32.WEST.getAbbreviation()) );
      assertEquals( CompassDirection32.WEST_BY_NORTH,      CompassDirection32.getByAbbreviation(CompassDirection32.WEST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection32.WEST_NORTHWEST,     CompassDirection32.getByAbbreviation(CompassDirection32.WEST_NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHWEST_BY_WEST,  CompassDirection32.getByAbbreviation(CompassDirection32.NORTHWEST_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHWEST,          CompassDirection32.getByAbbreviation(CompassDirection32.NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTHWEST_BY_NORTH, CompassDirection32.getByAbbreviation(CompassDirection32.NORTHWEST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTH_NORTHWEST,    CompassDirection32.getByAbbreviation(CompassDirection32.NORTH_NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection32.NORTH_BY_WEST,      CompassDirection32.getByAbbreviation(CompassDirection32.NORTH_BY_WEST.getAbbreviation()) );
   }

   @Test
   public void getByBearing_minMax() {
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTH.getMinimum()) );
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTH.getMaximum()) );

      assertEquals( CompassDirection32.NORTH_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.NORTH_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection32.NORTH_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.NORTH_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection32.NORTH_NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.NORTH_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.NORTH_NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.NORTH_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.NORTHEAST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection32.NORTHEAST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection32.NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.NORTHEAST_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection32.NORTHEAST_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.NORTHEAST_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection32.EAST_NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.EAST_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.EAST_NORTHEAST, CompassDirection32.getByBearing(CompassDirection32.EAST_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.EAST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.EAST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection32.EAST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.EAST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection32.EAST, CompassDirection32.getByBearing(CompassDirection32.EAST.getMinimum()) );
      assertEquals( CompassDirection32.EAST, CompassDirection32.getByBearing(CompassDirection32.EAST.getMaximum()) );

      assertEquals( CompassDirection32.EAST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.EAST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection32.EAST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.EAST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection32.EAST_SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.EAST_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.EAST_SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.EAST_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHEAST_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHEAST_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHEAST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHEAST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTHEAST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection32.SOUTH_SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTH_SOUTHEAST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTH_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTH_BY_EAST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTH.getMinimum()) );
      assertEquals( CompassDirection32.SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTH.getMaximum()) );

      assertEquals( CompassDirection32.SOUTH_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTH_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTH_SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTH_SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.SOUTH_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHWEST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHWEST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.SOUTHWEST_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection32.SOUTHWEST_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.SOUTHWEST_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection32.WEST_SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.WEST_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.WEST_SOUTHWEST, CompassDirection32.getByBearing(CompassDirection32.WEST_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.WEST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.WEST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection32.WEST_BY_SOUTH, CompassDirection32.getByBearing(CompassDirection32.WEST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection32.WEST, CompassDirection32.getByBearing(CompassDirection32.WEST.getMinimum()) );
      assertEquals( CompassDirection32.WEST, CompassDirection32.getByBearing(CompassDirection32.WEST.getMaximum()) );

      assertEquals( CompassDirection32.WEST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.WEST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection32.WEST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.WEST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection32.WEST_NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.WEST_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.WEST_NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.WEST_NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.NORTHWEST_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection32.NORTHWEST_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection32.NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.NORTHWEST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection32.NORTHWEST_BY_NORTH, CompassDirection32.getByBearing(CompassDirection32.NORTHWEST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection32.NORTH_NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.NORTH_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection32.NORTH_NORTHWEST, CompassDirection32.getByBearing(CompassDirection32.NORTH_NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection32.NORTH_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.NORTH_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection32.NORTH_BY_WEST, CompassDirection32.getByBearing(CompassDirection32.NORTH_BY_WEST.getMaximum()) );
   }

   @Test
   public void getByBearing_testRounding() {
      assertEquals( CompassDirection32.NORTH_BY_WEST, CompassDirection32.getByBearing(new BigDecimal("354.37499999999999")) );
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal("354.375")) );
   }

   @Test
   public void getByBearing_north() {
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(BigDecimal.ZERO) );
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal("359.9")) );
      assertEquals( CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal(360)) );
   }

   @Test
   public void getByBearing_invalidMin() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be in the range [0, 360]" );
      CompassDirection32.getByBearing( new BigDecimal("-0.000000000001") );
   }

   @Test
   public void getByBearing_invalidMax() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be in the range [0, 360]" );
      CompassDirection32.getByBearing( new BigDecimal("360.000000000001") );
   }
}
