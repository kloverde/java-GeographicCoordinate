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


public class CompassDirectionTest {

   @Rule
   public ExpectedException thrown = ExpectedException.none();


   @Test
   public void getPrevious() {
      assertEquals( CompassDirection.EAST, CompassDirection.EAST_BY_SOUTH.getPrevious() );   // verify that getPrevious moves backward by 1
      assertEquals( CompassDirection.NORTH_BY_WEST, CompassDirection.NORTH.getPrevious() );  // verify loop-around
   }

   @Test
   public void getNext() {
     assertEquals( CompassDirection.NORTH_BY_EAST, CompassDirection.NORTH.getNext() );  // verify that getNext moves forward by 1
     assertEquals( CompassDirection.NORTH, CompassDirection.NORTH_BY_WEST.getNext());   // verify loop-around
   }

   @Test
   public void getByAbbreviation() {
      assertEquals( CompassDirection.NORTH,              CompassDirection.getByAbbreviation(CompassDirection.NORTH.getAbbreviation()) );
      assertEquals( CompassDirection.NORTH_BY_EAST,      CompassDirection.getByAbbreviation(CompassDirection.NORTH_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTH_NORTHEAST,    CompassDirection.getByAbbreviation(CompassDirection.NORTH_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHEAST_BY_NORTH, CompassDirection.getByAbbreviation(CompassDirection.NORTHEAST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHEAST,          CompassDirection.getByAbbreviation(CompassDirection.NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHEAST_BY_EAST,  CompassDirection.getByAbbreviation(CompassDirection.NORTHEAST_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection.EAST_NORTHEAST,     CompassDirection.getByAbbreviation(CompassDirection.EAST_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.EAST_BY_NORTH,      CompassDirection.getByAbbreviation(CompassDirection.EAST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection.EAST,               CompassDirection.getByAbbreviation(CompassDirection.EAST.getAbbreviation()) );
      assertEquals( CompassDirection.EAST_BY_SOUTH,      CompassDirection.getByAbbreviation(CompassDirection.EAST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection.EAST_SOUTHEAST,     CompassDirection.getByAbbreviation(CompassDirection.EAST_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHEAST_BY_EAST,  CompassDirection.getByAbbreviation(CompassDirection.SOUTHEAST_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHEAST,          CompassDirection.getByAbbreviation(CompassDirection.SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHEAST_BY_SOUTH, CompassDirection.getByAbbreviation(CompassDirection.SOUTHEAST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTH_SOUTHEAST,    CompassDirection.getByAbbreviation(CompassDirection.SOUTH_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTH_BY_EAST,      CompassDirection.getByAbbreviation(CompassDirection.SOUTH_BY_EAST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTH,              CompassDirection.getByAbbreviation(CompassDirection.SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTH_BY_WEST,      CompassDirection.getByAbbreviation(CompassDirection.SOUTH_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTH_SOUTHWEST,    CompassDirection.getByAbbreviation(CompassDirection.SOUTH_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHWEST_BY_SOUTH, CompassDirection.getByAbbreviation(CompassDirection.SOUTHWEST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHWEST,          CompassDirection.getByAbbreviation(CompassDirection.SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.SOUTHWEST_BY_WEST,  CompassDirection.getByAbbreviation(CompassDirection.SOUTHWEST_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection.WEST_SOUTHWEST,     CompassDirection.getByAbbreviation(CompassDirection.WEST_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.WEST_BY_SOUTH,      CompassDirection.getByAbbreviation(CompassDirection.WEST_BY_SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection.WEST,               CompassDirection.getByAbbreviation(CompassDirection.WEST.getAbbreviation()) );
      assertEquals( CompassDirection.WEST_BY_NORTH,      CompassDirection.getByAbbreviation(CompassDirection.WEST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection.WEST_NORTHWEST,     CompassDirection.getByAbbreviation(CompassDirection.WEST_NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHWEST_BY_WEST,  CompassDirection.getByAbbreviation(CompassDirection.NORTHWEST_BY_WEST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHWEST,          CompassDirection.getByAbbreviation(CompassDirection.NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTHWEST_BY_NORTH, CompassDirection.getByAbbreviation(CompassDirection.NORTHWEST_BY_NORTH.getAbbreviation()) );
      assertEquals( CompassDirection.NORTH_NORTHWEST,    CompassDirection.getByAbbreviation(CompassDirection.NORTH_NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection.NORTH_BY_WEST,      CompassDirection.getByAbbreviation(CompassDirection.NORTH_BY_WEST.getAbbreviation()) );
   }

   @Test
   public void getByBearing_minMiddleMax() {
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(CompassDirection.NORTH.getMinimum()) );
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(CompassDirection.NORTH.getMaximum()) );

      assertEquals( CompassDirection.NORTH_BY_EAST, CompassDirection.getByBearing(CompassDirection.NORTH_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection.NORTH_BY_EAST, CompassDirection.getByBearing(CompassDirection.NORTH_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection.NORTH_NORTHEAST, CompassDirection.getByBearing(CompassDirection.NORTH_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection.NORTH_NORTHEAST, CompassDirection.getByBearing(CompassDirection.NORTH_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection.NORTHEAST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.NORTHEAST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection.NORTHEAST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.NORTHEAST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection.NORTHEAST, CompassDirection.getByBearing(CompassDirection.NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection.NORTHEAST, CompassDirection.getByBearing(CompassDirection.NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection.NORTHEAST_BY_EAST, CompassDirection.getByBearing(CompassDirection.NORTHEAST_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection.NORTHEAST_BY_EAST, CompassDirection.getByBearing(CompassDirection.NORTHEAST_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection.EAST_NORTHEAST, CompassDirection.getByBearing(CompassDirection.EAST_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection.EAST_NORTHEAST, CompassDirection.getByBearing(CompassDirection.EAST_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection.EAST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.EAST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection.EAST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.EAST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection.EAST, CompassDirection.getByBearing(CompassDirection.EAST.getMinimum()) );
      assertEquals( CompassDirection.EAST, CompassDirection.getByBearing(CompassDirection.EAST.getMaximum()) );

      assertEquals( CompassDirection.EAST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.EAST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection.EAST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.EAST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection.EAST_SOUTHEAST, CompassDirection.getByBearing(CompassDirection.EAST_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection.EAST_SOUTHEAST, CompassDirection.getByBearing(CompassDirection.EAST_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection.SOUTHEAST_BY_EAST, CompassDirection.getByBearing(CompassDirection.SOUTHEAST_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection.SOUTHEAST_BY_EAST, CompassDirection.getByBearing(CompassDirection.SOUTHEAST_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection.SOUTHEAST, CompassDirection.getByBearing(CompassDirection.SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection.SOUTHEAST, CompassDirection.getByBearing(CompassDirection.SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection.SOUTHEAST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTHEAST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection.SOUTHEAST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTHEAST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection.SOUTH_SOUTHEAST, CompassDirection.getByBearing(CompassDirection.SOUTH_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection.SOUTH_SOUTHEAST, CompassDirection.getByBearing(CompassDirection.SOUTH_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection.SOUTH_BY_EAST, CompassDirection.getByBearing(CompassDirection.SOUTH_BY_EAST.getMinimum()) );
      assertEquals( CompassDirection.SOUTH_BY_EAST, CompassDirection.getByBearing(CompassDirection.SOUTH_BY_EAST.getMaximum()) );

      assertEquals( CompassDirection.SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTH.getMinimum()) );
      assertEquals( CompassDirection.SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTH.getMaximum()) );

      assertEquals( CompassDirection.SOUTH_BY_WEST, CompassDirection.getByBearing(CompassDirection.SOUTH_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection.SOUTH_BY_WEST, CompassDirection.getByBearing(CompassDirection.SOUTH_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection.SOUTH_SOUTHWEST, CompassDirection.getByBearing(CompassDirection.SOUTH_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection.SOUTH_SOUTHWEST, CompassDirection.getByBearing(CompassDirection.SOUTH_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection.SOUTHWEST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTHWEST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection.SOUTHWEST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.SOUTHWEST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection.SOUTHWEST, CompassDirection.getByBearing(CompassDirection.SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection.SOUTHWEST, CompassDirection.getByBearing(CompassDirection.SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection.SOUTHWEST_BY_WEST, CompassDirection.getByBearing(CompassDirection.SOUTHWEST_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection.SOUTHWEST_BY_WEST, CompassDirection.getByBearing(CompassDirection.SOUTHWEST_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection.WEST_SOUTHWEST, CompassDirection.getByBearing(CompassDirection.WEST_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection.WEST_SOUTHWEST, CompassDirection.getByBearing(CompassDirection.WEST_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection.WEST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.WEST_BY_SOUTH.getMinimum()) );
      assertEquals( CompassDirection.WEST_BY_SOUTH, CompassDirection.getByBearing(CompassDirection.WEST_BY_SOUTH.getMaximum()) );

      assertEquals( CompassDirection.WEST, CompassDirection.getByBearing(CompassDirection.WEST.getMinimum()) );
      assertEquals( CompassDirection.WEST, CompassDirection.getByBearing(CompassDirection.WEST.getMaximum()) );

      assertEquals( CompassDirection.WEST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.WEST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection.WEST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.WEST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection.WEST_NORTHWEST, CompassDirection.getByBearing(CompassDirection.WEST_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection.WEST_NORTHWEST, CompassDirection.getByBearing(CompassDirection.WEST_NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection.NORTHWEST_BY_WEST, CompassDirection.getByBearing(CompassDirection.NORTHWEST_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection.NORTHWEST_BY_WEST, CompassDirection.getByBearing(CompassDirection.NORTHWEST_BY_WEST.getMaximum()) );

      assertEquals( CompassDirection.NORTHWEST, CompassDirection.getByBearing(CompassDirection.NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection.NORTHWEST, CompassDirection.getByBearing(CompassDirection.NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection.NORTHWEST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.NORTHWEST_BY_NORTH.getMinimum()) );
      assertEquals( CompassDirection.NORTHWEST_BY_NORTH, CompassDirection.getByBearing(CompassDirection.NORTHWEST_BY_NORTH.getMaximum()) );

      assertEquals( CompassDirection.NORTH_NORTHWEST, CompassDirection.getByBearing(CompassDirection.NORTH_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection.NORTH_NORTHWEST, CompassDirection.getByBearing(CompassDirection.NORTH_NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection.NORTH_BY_WEST, CompassDirection.getByBearing(CompassDirection.NORTH_BY_WEST.getMinimum()) );
      assertEquals( CompassDirection.NORTH_BY_WEST, CompassDirection.getByBearing(CompassDirection.NORTH_BY_WEST.getMaximum()) );
   }

   @Test
   public void getByBearing_testRounding() {
      assertEquals( CompassDirection.NORTH_BY_WEST, CompassDirection.getByBearing(new BigDecimal("354.37499999999999")) );
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(new BigDecimal("354.375")) );
   }

   @Test
   public void getByBearing_north() {
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(BigDecimal.ZERO) );
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(new BigDecimal("359.9")) );
      assertEquals( CompassDirection.NORTH, CompassDirection.getByBearing(new BigDecimal(360)) );
   }

   @Test
   public void getByBearing_invalidMin() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be 0 <= x <= 360" );
      CompassDirection.getByBearing( new BigDecimal("-0.000000000001") );
   }

   @Test
   public void getByBearing_invalidMax() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be 0 <= x <= 360" );
      CompassDirection.getByBearing( new BigDecimal("360.000000000001") );
   }
}
