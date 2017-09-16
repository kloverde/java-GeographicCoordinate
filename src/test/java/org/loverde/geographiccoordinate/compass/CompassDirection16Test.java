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
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.util.number.bigdecimal.BigDecimalCompare;


public class CompassDirection16Test {

   @Rule
   public ExpectedException thrown = ExpectedException.none();


   @Test
   public void getMinMidMaxIncreases() {
      for( final CompassDirection32 dir : CompassDirection32.values() ) {
         assertTrue( String.format("Comparing %s minimum to 0",   dir.name()), BigDecimalCompare.isGreaterThanOrEqualTo(dir.getMinimum(), BigDecimal.ZERO) );
         assertTrue( String.format("Comparing %s middle to max",  dir.name()), BigDecimalCompare.isLessThan(dir.getMiddle(), dir.getMaximum()) );
         assertTrue( String.format("Comparing %s maximum to 360", dir.name()), BigDecimalCompare.isLessThanOrEqualTo(dir.getMaximum(), new BigDecimal(360)) );
         assertTrue( String.format("Comparing %s maximum to next minimum", dir.name()), BigDecimalCompare.isLessThan(dir.getMaximum(), dir.getNext().getMinimum()) );

         if( dir != CompassDirection32.NORTH ) {
            assertTrue( String.format("Comparing %s minimum to middle", dir.name()), BigDecimalCompare.isLessThan(dir.getMinimum(), dir.getMiddle()) );
        } else {
           assertTrue( String.format("Comparing %s minimum to middle", dir.name()), BigDecimalCompare.isGreaterThan(dir.getMinimum(), dir.getMiddle()) );
        }
      }
   }

   @Test
   public void getPrevious() {
      assertEquals( CompassDirection16.EAST_SOUTHEAST, CompassDirection16.SOUTHEAST.getPrevious() );  // verify that getPrevious moves backward by 1
      assertEquals( CompassDirection16.NORTH_NORTHWEST, CompassDirection16.NORTH.getPrevious() );  // verify loop-around
   }

   @Test
   public void getNext() {
     assertEquals( CompassDirection16.WEST_SOUTHWEST, CompassDirection16.SOUTHWEST.getNext() );  // verify that getNext moves forward by 1
     assertEquals( CompassDirection16.NORTH, CompassDirection16.NORTH_NORTHWEST.getNext());   // verify loop-around
   }

   @Test
   public void getByAbbreviation() {
      assertEquals( CompassDirection16.NORTH,              CompassDirection16.getByAbbreviation(CompassDirection16.NORTH.getAbbreviation()) );
      assertEquals( CompassDirection16.NORTH_NORTHEAST,    CompassDirection16.getByAbbreviation(CompassDirection16.NORTH_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.NORTHEAST,          CompassDirection16.getByAbbreviation(CompassDirection16.NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.EAST_NORTHEAST,     CompassDirection16.getByAbbreviation(CompassDirection16.EAST_NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.EAST,               CompassDirection16.getByAbbreviation(CompassDirection16.EAST.getAbbreviation()) );
      assertEquals( CompassDirection16.EAST_SOUTHEAST,     CompassDirection16.getByAbbreviation(CompassDirection16.EAST_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.SOUTHEAST,          CompassDirection16.getByAbbreviation(CompassDirection16.SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.SOUTH_SOUTHEAST,    CompassDirection16.getByAbbreviation(CompassDirection16.SOUTH_SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection16.SOUTH,              CompassDirection16.getByAbbreviation(CompassDirection16.SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection16.SOUTH_SOUTHWEST,    CompassDirection16.getByAbbreviation(CompassDirection16.SOUTH_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection16.SOUTHWEST,          CompassDirection16.getByAbbreviation(CompassDirection16.SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection16.WEST_SOUTHWEST,     CompassDirection16.getByAbbreviation(CompassDirection16.WEST_SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection16.WEST,               CompassDirection16.getByAbbreviation(CompassDirection16.WEST.getAbbreviation()) );
      assertEquals( CompassDirection16.WEST_NORTHWEST,     CompassDirection16.getByAbbreviation(CompassDirection16.WEST_NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection16.NORTHWEST,          CompassDirection16.getByAbbreviation(CompassDirection16.NORTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection16.NORTH_NORTHWEST,    CompassDirection16.getByAbbreviation(CompassDirection16.NORTH_NORTHWEST.getAbbreviation()) );
   }

   @Test
   public void getByBearing_minMax() {
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(CompassDirection16.NORTH.getMinimum()) );
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(CompassDirection16.NORTH.getMaximum()) );

      assertEquals( CompassDirection16.NORTH_NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.NORTH_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.NORTH_NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.NORTH_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.EAST_NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.EAST_NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.EAST_NORTHEAST, CompassDirection16.getByBearing(CompassDirection16.EAST_NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.EAST, CompassDirection16.getByBearing(CompassDirection16.EAST.getMinimum()) );
      assertEquals( CompassDirection16.EAST, CompassDirection16.getByBearing(CompassDirection16.EAST.getMaximum()) );

      assertEquals( CompassDirection16.EAST_SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.EAST_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.EAST_SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.EAST_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.SOUTH_SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.SOUTH_SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection16.SOUTH_SOUTHEAST, CompassDirection16.getByBearing(CompassDirection16.SOUTH_SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection16.SOUTH, CompassDirection16.getByBearing(CompassDirection16.SOUTH.getMinimum()) );
      assertEquals( CompassDirection16.SOUTH, CompassDirection16.getByBearing(CompassDirection16.SOUTH.getMaximum()) );

      assertEquals( CompassDirection16.SOUTH_SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.SOUTH_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.SOUTH_SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.SOUTH_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection16.SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection16.WEST_SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.WEST_SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.WEST_SOUTHWEST, CompassDirection16.getByBearing(CompassDirection16.WEST_SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection16.WEST, CompassDirection16.getByBearing(CompassDirection16.WEST.getMinimum()) );
      assertEquals( CompassDirection16.WEST, CompassDirection16.getByBearing(CompassDirection16.WEST.getMaximum()) );

      assertEquals( CompassDirection16.WEST_NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.WEST_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.WEST_NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.WEST_NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection16.NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.NORTHWEST.getMaximum()) );

      assertEquals( CompassDirection16.NORTH_NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.NORTH_NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection16.NORTH_NORTHWEST, CompassDirection16.getByBearing(CompassDirection16.NORTH_NORTHWEST.getMaximum()) );
   }

   @Test
   public void getByBearing_testRounding() {
//      assertEquals( CompassDirection16.NORTH_BY_WEST, CompassDirection16.getByBearing(new BigDecimal("354.37499999999999")) );
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(new BigDecimal("354.375")) );
   }

   @Test
   public void getByBearing_north() {
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(BigDecimal.ZERO) );
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(new BigDecimal("359.9")) );
      assertEquals( CompassDirection16.NORTH, CompassDirection16.getByBearing(new BigDecimal(360)) );
   }

   @Test
   public void getByBearing_invalidMin() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be in the range [0, 360]" );
      CompassDirection16.getByBearing( new BigDecimal("-0.000000000001") );
   }

   @Test
   public void getByBearing_invalidMax() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Bearing must be in the range [0, 360]" );
      CompassDirection16.getByBearing( new BigDecimal("360.000000000001") );
   }
}
