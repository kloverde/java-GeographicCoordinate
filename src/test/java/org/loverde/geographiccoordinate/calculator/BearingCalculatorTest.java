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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.loverde.geographiccoordinate.Bearing;
import org.loverde.geographiccoordinate.Latitude;
import org.loverde.geographiccoordinate.Longitude;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;


public class BearingCalculatorTest {

   private static final Latitude latitude1 = new Latitude( 40, 42, 46, Latitude.Direction.NORTH );
   private static final Longitude longitude1 = new Longitude( 74, 0, 21, Longitude.Direction.WEST );

   private static final Latitude latitude2 = new Latitude( 38, 54, 17, Latitude.Direction.NORTH );
   private static final Longitude longitude2 = new Longitude( 77, 0, 59, Longitude.Direction.WEST );

   private static final Point point1 = new Point( latitude1, longitude1 );
   private static final Point point2 = new Point( latitude2, longitude2 );


   @Test
   @SuppressWarnings("unchecked")
   public void bearing8() {
      final Bearing<CompassDirection8> bearing8 = (Bearing<CompassDirection8>) BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );

      assertEquals( 232.95302, bearing8.getBearing().doubleValue(), .00001 );
   }

   @Test
   @SuppressWarnings("unchecked")
   public void bearingX_equivalence() {
      final Bearing<CompassDirection8>  bearing8  = (Bearing<CompassDirection8>)  BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
      final Bearing<CompassDirection16> bearing16 = (Bearing<CompassDirection16>) BearingCalculator.initialBearing( CompassDirection16.class, point1, point2 );
      final Bearing<CompassDirection32> bearing32 = (Bearing<CompassDirection32>) BearingCalculator.initialBearing( CompassDirection32.class, point1, point2 );

      assertEquals( bearing8.getBearing(), bearing16.getBearing() );
      assertEquals( bearing16.getBearing(), bearing32.getBearing() );
   }
}
