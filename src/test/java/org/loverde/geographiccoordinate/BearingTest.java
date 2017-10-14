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

package org.loverde.geographiccoordinate;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.compass.CompassDirection8;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;


public class BearingTest {

   @Rule
   public ExpectedException thrown = ExpectedException.none();


   @Test
   public void setBearing_null() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_BEARING_NULL );

      new Bearing<CompassDirection8>().setBearing( null );
   }

   @Test
   public void setBearing_outOfLowBound() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE );

      new Bearing<CompassDirection8>().setBearing( new BigDecimal("-.0000000000001") );
   }

   @Test
   public void setBearing_atLowBound() {
      new Bearing<CompassDirection8>().setBearing( BigDecimal.ZERO );
   }

   @Test
   public void setBearing_atHighBound() {
      new Bearing<CompassDirection8>().setBearing( new BigDecimal(360) );
   }

   @Test
   public void setBearing_outsideHighBound() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE );

      new Bearing<CompassDirection8>().setBearing( new BigDecimal("360.0000000000000001") );
   }
}
