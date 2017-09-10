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

import org.junit.Test;


public class CompassDirectionTest {

   @Test
   public void getMin() {
      assertEquals( 354.375, CompassDirection.NORTH.getMin(), 0.0 );
      assertEquals( 5.625,   CompassDirection.NORTH_BY_EAST.getMin(), 0.0 );
      assertEquals( 16.875,  CompassDirection.NORTH_NORTHEAST.getMin(), 0.0 );
      assertEquals( 28.125,  CompassDirection.NORTHEAST_BY_NORTH.getMin(), 0.0 );
      assertEquals( 39.375,  CompassDirection.NORTHEAST.getMin(), 0.0 );
      assertEquals( 50.625,  CompassDirection.NORTHEAST_BY_EAST.getMin(), 0.0 );
      assertEquals( 61.875,  CompassDirection.EAST_NORTHEAST.getMin(), 0.0 );
      assertEquals( 73.125,  CompassDirection.EAST_BY_NORTH.getMin(), 0.0 );
      assertEquals( 84.375,  CompassDirection.EAST.getMin(), 0.0 );
      assertEquals( 95.625,  CompassDirection.EAST_BY_SOUTH.getMin(), 0.0 );
      assertEquals( 106.875, CompassDirection.EAST_SOUTHEAST.getMin(), 0.0 );
      assertEquals( 118.125, CompassDirection.SOUTHEAST_BY_EAST.getMin(), 0.0 );
      assertEquals( 129.375, CompassDirection.SOUTHEAST.getMin(), 0.0 );
      assertEquals( 140.625, CompassDirection.SOUTHEAST_BY_SOUTH.getMin(), 0.0 );
      assertEquals( 151.875, CompassDirection.SOUTH_SOUTHEAST.getMin(), 0.0 );
      assertEquals( 163.125, CompassDirection.SOUTH_BY_EAST.getMin(), 0.0 );
      assertEquals( 174.375, CompassDirection.SOUTH.getMin(), 0.0 );
      assertEquals( 185.625, CompassDirection.SOUTH_BY_WEST.getMin(), 0.0 );
      assertEquals( 196.875, CompassDirection.SOUTH_SOUTHWEST.getMin(), 0.0 );
      assertEquals( 208.125, CompassDirection.SOUTHWEST_BY_SOUTH.getMin(), 0.0 );
      assertEquals( 219.375, CompassDirection.SOUTHWEST.getMin(), 0.0 );
      assertEquals( 230.625, CompassDirection.SOUTHWEST_BY_WEST.getMin(), 0.0 );
      assertEquals( 241.875, CompassDirection.WEST_SOUTHWEST.getMin(), 0.0 );
      assertEquals( 253.125, CompassDirection.WEST_BY_SOUTH.getMin(), 0.0 );
      assertEquals( 264.375, CompassDirection.WEST.getMin(), 0.0 );
      assertEquals( 275.625, CompassDirection.WEST_BY_NORTH.getMin(), 0.0 );
      assertEquals( 286.875, CompassDirection.WEST_NORTHWEST.getMin(), 0.0 );
      assertEquals( 298.125, CompassDirection.NORTHWEST_BY_WEST.getMin(), 0.0 );
      assertEquals( 309.375, CompassDirection.NORTHWEST.getMin(), 0.0 );
      assertEquals( 320.625, CompassDirection.NORTHWEST_BY_NORTH.getMin(), 0.0 );
      assertEquals( 331.875, CompassDirection.NORTH_NORTHWEST.getMin(), 0.0 );
      assertEquals( 343.125, CompassDirection.NORTH_BY_WEST.getMin(), 0.0 );
   }

   @Test
   public void getMiddleAzimuth() {
      assertEquals( 0.0,    CompassDirection.NORTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 11.25,  CompassDirection.NORTH_BY_EAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 22.5,   CompassDirection.NORTH_NORTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 33.75,  CompassDirection.NORTHEAST_BY_NORTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 45.0,   CompassDirection.NORTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 56.25,  CompassDirection.NORTHEAST_BY_EAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 67.5,   CompassDirection.EAST_NORTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 78.75,  CompassDirection.EAST_BY_NORTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 90.0,   CompassDirection.EAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 101.25, CompassDirection.EAST_BY_SOUTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 112.5,  CompassDirection.EAST_SOUTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 123.75, CompassDirection.SOUTHEAST_BY_EAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 135.0,  CompassDirection.SOUTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 146.25, CompassDirection.SOUTHEAST_BY_SOUTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 157.5,  CompassDirection.SOUTH_SOUTHEAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 168.75, CompassDirection.SOUTH_BY_EAST.getMiddleAzimuth(), 0.0 );
      assertEquals( 180.0,  CompassDirection.SOUTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 191.25, CompassDirection.SOUTH_BY_WEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 202.5,  CompassDirection.SOUTH_SOUTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 213.75, CompassDirection.SOUTHWEST_BY_SOUTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 225.0,  CompassDirection.SOUTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 236.25, CompassDirection.SOUTHWEST_BY_WEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 247.5,  CompassDirection.WEST_SOUTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 258.75, CompassDirection.WEST_BY_SOUTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 270.0,  CompassDirection.WEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 281.25, CompassDirection.WEST_BY_NORTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 292.5,  CompassDirection.WEST_NORTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 303.75, CompassDirection.NORTHWEST_BY_WEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 315.0,  CompassDirection.NORTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 326.25, CompassDirection.NORTHWEST_BY_NORTH.getMiddleAzimuth(), 0.0 );
      assertEquals( 337.5,  CompassDirection.NORTH_NORTHWEST.getMiddleAzimuth(), 0.0 );
      assertEquals( 348.75, CompassDirection.NORTH_BY_WEST.getMiddleAzimuth(), 0.0 );
   }

   @Test
   public void getMax() {
      assertEquals( 5.625,   CompassDirection.NORTH.getMax(), 0.0 );
      assertEquals( 16.875,  CompassDirection.NORTH_BY_EAST.getMax(), 0.0 );
      assertEquals( 28.125,  CompassDirection.NORTH_NORTHEAST.getMax(), 0.0 );
      assertEquals( 39.375,  CompassDirection.NORTHEAST_BY_NORTH.getMax(), 0.0 );
      assertEquals( 50.625,  CompassDirection.NORTHEAST.getMax(), 0.0 );
      assertEquals( 61.875,  CompassDirection.NORTHEAST_BY_EAST.getMax(), 0.0 );
      assertEquals( 73.125,  CompassDirection.EAST_NORTHEAST.getMax(), 0.0 );
      assertEquals( 84.375,  CompassDirection.EAST_BY_NORTH.getMax(), 0.0 );
      assertEquals( 95.625,  CompassDirection.EAST.getMax(), 0.0 );
      assertEquals( 106.875, CompassDirection.EAST_BY_SOUTH.getMax(), 0.0 );
      assertEquals( 118.125, CompassDirection.EAST_SOUTHEAST.getMax(), 0.0 );
      assertEquals( 129.375, CompassDirection.SOUTHEAST_BY_EAST.getMax(), 0.0 );
      assertEquals( 140.625, CompassDirection.SOUTHEAST.getMax(), 0.0 );
      assertEquals( 151.875, CompassDirection.SOUTHEAST_BY_SOUTH.getMax(), 0.0 );
      assertEquals( 163.125, CompassDirection.SOUTH_SOUTHEAST.getMax(), 0.0 );
      assertEquals( 174.375, CompassDirection.SOUTH_BY_EAST.getMax(), 0.0 );
      assertEquals( 185.625, CompassDirection.SOUTH.getMax(), 0.0 );
      assertEquals( 196.875, CompassDirection.SOUTH_BY_WEST.getMax(), 0.0 );
      assertEquals( 208.125, CompassDirection.SOUTH_SOUTHWEST.getMax(), 0.0 );
      assertEquals( 219.375, CompassDirection.SOUTHWEST_BY_SOUTH.getMax(), 0.0 );
      assertEquals( 230.625, CompassDirection.SOUTHWEST.getMax(), 0.0 );
      assertEquals( 241.875, CompassDirection.SOUTHWEST_BY_WEST.getMax(), 0.0 );
      assertEquals( 253.125, CompassDirection.WEST_SOUTHWEST.getMax(), 0.0 );
      assertEquals( 264.375, CompassDirection.WEST_BY_SOUTH.getMax(), 0.0 );
      assertEquals( 275.625, CompassDirection.WEST.getMax(), 0.0 );
      assertEquals( 286.875, CompassDirection.WEST_BY_NORTH.getMax(), 0.0 );
      assertEquals( 298.125, CompassDirection.WEST_NORTHWEST.getMax(), 0.0 );
      assertEquals( 309.375, CompassDirection.NORTHWEST_BY_WEST.getMax(), 0.0 );
      assertEquals( 320.625, CompassDirection.NORTHWEST.getMax(), 0.0 );
      assertEquals( 331.875, CompassDirection.NORTHWEST_BY_NORTH.getMax(), 0.0 );
      assertEquals( 343.125, CompassDirection.NORTH_NORTHWEST.getMax(), 0.0 );
      assertEquals( 354.375, CompassDirection.NORTH_BY_WEST.getMax(), 0.0 );
   }

   @Test
   public void getPrevious() {
      assertEquals( CompassDirection.EAST, CompassDirection.EAST_BY_SOUTH.getPrevious() );   // verify that getPrevious iterates backward by 1
      assertEquals( CompassDirection.NORTH_BY_WEST, CompassDirection.NORTH.getPrevious() );  // verify loop-around
   }

   @Test
   public void getNext() {
     assertEquals( CompassDirection.NORTH_BY_EAST, CompassDirection.NORTH.getNext() );  // verify that getNext iterates forward by 1
     assertEquals( CompassDirection.NORTH, CompassDirection.NORTH_BY_WEST.getNext());   // verify loop-around
   }

   /** Spot checking EnumHelper */
   @Test
   public void getByAbbreviation() {
      assertEquals( CompassDirection.EAST_BY_NORTH, CompassDirection.getByAbbreviation("EbN") );
      assertEquals( CompassDirection.WEST_SOUTHWEST, CompassDirection.getByAbbreviation("WSW") );
   }

   public void getByBearing() {

   }
}
