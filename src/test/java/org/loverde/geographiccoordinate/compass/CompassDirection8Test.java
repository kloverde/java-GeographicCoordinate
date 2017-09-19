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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.util.number.bigdecimal.BigDecimalCompare;


public class CompassDirection8Test {

   @Rule
   public ExpectedException thrown = ExpectedException.none();


   @Test
   public void getMinMidMaxIncreases() {
      for( final CompassDirection8 dir : CompassDirection8.values() ) {
         assertTrue( String.format("Comparing %s minimum to 0",   dir.name()), BigDecimalCompare.isGreaterThanOrEqualTo(dir.getMinimum(), BigDecimal.ZERO) );
         assertTrue( String.format("Comparing %s middle to max",  dir.name()), BigDecimalCompare.isLessThan(dir.getMiddle(), dir.getMaximum()) );
         assertTrue( String.format("Comparing %s maximum to 360", dir.name()), BigDecimalCompare.isLessThanOrEqualTo(dir.getMaximum(), new BigDecimal(360)) );
         assertTrue( String.format("Comparing %s maximum to next minimum", dir.name()), BigDecimalCompare.isLessThan(dir.getMaximum(), dir.getNext().getMinimum()) );

         if( dir != CompassDirection8.NORTH ) {
            assertTrue( String.format("Comparing %s minimum to middle", dir.name()), BigDecimalCompare.isLessThan(dir.getMinimum(), dir.getMiddle()) );
        } else {
           assertTrue( String.format("Comparing %s minimum to middle", dir.name()), BigDecimalCompare.isGreaterThan(dir.getMinimum(), dir.getMiddle()) );
        }
      }
   }

   @Test
   public void getPrevious() {
      assertEquals( CompassDirection8.NORTHEAST, CompassDirection8.EAST.getPrevious() );  // verify that getPrevious moves backward by 1
      assertEquals( CompassDirection8.NORTHWEST, CompassDirection8.NORTH.getPrevious() );  // verify loop-around
   }

   @Test
   public void getNext() {
     assertEquals( CompassDirection8.WEST, CompassDirection8.SOUTHWEST.getNext() );  // verify that getNext moves forward by 1
     assertEquals( CompassDirection8.NORTH, CompassDirection8.NORTHWEST.getNext());  // verify loop-around
   }

   @Test
   public void getByAbbreviation() {
      assertEquals( CompassDirection8.NORTH,     CompassDirection8.getByAbbreviation(CompassDirection8.NORTH.getAbbreviation()) );
      assertEquals( CompassDirection8.NORTHEAST, CompassDirection8.getByAbbreviation(CompassDirection8.NORTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection8.EAST,      CompassDirection8.getByAbbreviation(CompassDirection8.EAST.getAbbreviation()) );
      assertEquals( CompassDirection8.SOUTHEAST, CompassDirection8.getByAbbreviation(CompassDirection8.SOUTHEAST.getAbbreviation()) );
      assertEquals( CompassDirection8.SOUTH,     CompassDirection8.getByAbbreviation(CompassDirection8.SOUTH.getAbbreviation()) );
      assertEquals( CompassDirection8.SOUTHWEST, CompassDirection8.getByAbbreviation(CompassDirection8.SOUTHWEST.getAbbreviation()) );
      assertEquals( CompassDirection8.WEST,      CompassDirection8.getByAbbreviation(CompassDirection8.WEST.getAbbreviation()) );
      assertEquals( CompassDirection8.NORTHWEST, CompassDirection8.getByAbbreviation(CompassDirection8.NORTHWEST.getAbbreviation()) );
   }

   @Test
   public void getByAngle_minMax() {
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(CompassDirection8.NORTH.getMinimum()) );
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(CompassDirection8.NORTH.getMaximum()) );

      assertEquals( CompassDirection8.NORTHEAST, CompassDirection8.getByAngle(CompassDirection8.NORTHEAST.getMinimum()) );
      assertEquals( CompassDirection8.NORTHEAST, CompassDirection8.getByAngle(CompassDirection8.NORTHEAST.getMaximum()) );

      assertEquals( CompassDirection8.EAST, CompassDirection8.getByAngle(CompassDirection8.EAST.getMinimum()) );
      assertEquals( CompassDirection8.EAST, CompassDirection8.getByAngle(CompassDirection8.EAST.getMaximum()) );

      assertEquals( CompassDirection8.SOUTHEAST, CompassDirection8.getByAngle(CompassDirection8.SOUTHEAST.getMinimum()) );
      assertEquals( CompassDirection8.SOUTHEAST, CompassDirection8.getByAngle(CompassDirection8.SOUTHEAST.getMaximum()) );

      assertEquals( CompassDirection8.SOUTH, CompassDirection8.getByAngle(CompassDirection8.SOUTH.getMinimum()) );
      assertEquals( CompassDirection8.SOUTH, CompassDirection8.getByAngle(CompassDirection8.SOUTH.getMaximum()) );

      assertEquals( CompassDirection8.SOUTHWEST, CompassDirection8.getByAngle(CompassDirection8.SOUTHWEST.getMinimum()) );
      assertEquals( CompassDirection8.SOUTHWEST, CompassDirection8.getByAngle(CompassDirection8.SOUTHWEST.getMaximum()) );

      assertEquals( CompassDirection8.WEST, CompassDirection8.getByAngle(CompassDirection8.WEST.getMinimum()) );
      assertEquals( CompassDirection8.WEST, CompassDirection8.getByAngle(CompassDirection8.WEST.getMaximum()) );

      assertEquals( CompassDirection8.NORTHWEST, CompassDirection8.getByAngle(CompassDirection8.NORTHWEST.getMinimum()) );
      assertEquals( CompassDirection8.NORTHWEST, CompassDirection8.getByAngle(CompassDirection8.NORTHWEST.getMaximum()) );
   }

   @Test
   public void getByAngle_testRounding() {
      assertEquals( CompassDirection8.NORTHWEST, CompassDirection8.getByAngle(new BigDecimal("337.494999999999999999")) );
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(new BigDecimal("337.495")) );
   }

   @Test
   public void getByAngle_north() {
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(BigDecimal.ZERO) );
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(new BigDecimal("359.9")) );
      assertEquals( CompassDirection8.NORTH, CompassDirection8.getByAngle(new BigDecimal(360)) );
   }

   @Test
   public void getByAngle_invalidMin() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Angle must be in the range [0, 360]" );
      CompassDirection8.getByAngle( new BigDecimal("-0.000000000001") );
   }

   @Test
   public void getByAngle_invalidMax() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Angle must be in the range [0, 360]" );
      CompassDirection8.getByAngle( new BigDecimal("360.000000000001") );
   }
}
