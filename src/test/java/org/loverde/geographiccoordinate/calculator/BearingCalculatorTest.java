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
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.Bearing;
import org.loverde.geographiccoordinate.Latitude;
import org.loverde.geographiccoordinate.Longitude;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class BearingCalculatorTest {

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   private Latitude latitude1;
   private Longitude longitude1;

   private Latitude latitude2;
   private Longitude longitude2;

   @Mock
   private Point point1;

   @Mock
   private Point point2;


   @Before
   public void setUp() {
      latitude1 = new Latitude( 40, 42, 46, Latitude.Direction.NORTH );
      longitude1 = new Longitude( 74, 0, 21, Longitude.Direction.WEST );

      latitude2 = new Latitude( 38, 54, 17, Latitude.Direction.NORTH );
      longitude2 = new Longitude( 77, 0, 59, Longitude.Direction.WEST );

      point1 = new Point( latitude1, longitude1 );
      point2 = new Point( latitude2, longitude2 );

      MockitoAnnotations.initMocks( this );

      when( point1.getLatitude() ).thenReturn( latitude1);
      when( point1.getLongitude() ).thenReturn( longitude1 );

      when( point2.getLatitude() ).thenReturn( latitude2);
      when( point2.getLongitude() ).thenReturn( longitude2 );
   }

   @Test
   public void initialBearing_nullCompassDirectionType() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_COMPASS_DIRECTION_NULL );

      BearingCalculator.initialBearing( null, point1, point2 );
   }

   @Test
   public void initialBearing_nullFromLatitude() {
      when( point1.getLatitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_FROM_LATITUDE_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
   }

   @Test
   public void initialBearing_nullFromLongitude() {
      when( point1.getLongitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_FROM_LONGITUDE_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
   }

   @Test
   public void initialBearing_nullFromPoint() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_FROM_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, null, point2 );
   }

   @Test
   public void initialBearing_nullToLatitude() {
      when( point2.getLatitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_TO_LATITUDE_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
   }

   @Test
   public void initialBearing_nullToLongitude() {
      when( point2.getLongitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_TO_LONGITUDE_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
   }

   @Test
   public void initialBearing_nullToPoint() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_TO_NULL );

      BearingCalculator.initialBearing( CompassDirection8.class, point1, null );
   }

   @Test
   @SuppressWarnings("unchecked")
   public void initalBearing_compassDirection8() {
      final Bearing<CompassDirection8> bearing8 = (Bearing<CompassDirection8>) BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
      assertEquals( 232.95302, bearing8.getBearing().doubleValue(), .00001 );
   }

   @Test
   @SuppressWarnings("unchecked")
   public void initalBearing_compassDirection16() {
      final Bearing<CompassDirection16> bearing16 = (Bearing<CompassDirection16>) BearingCalculator.initialBearing( CompassDirection16.class, point1, point2 );
      assertEquals( 232.95302, bearing16.getBearing().doubleValue(), .00001 );
   }

   @Test
   @SuppressWarnings("unchecked")
   public void initalBearing_compassDirection32() {
      final Bearing<CompassDirection32> bearing32 = (Bearing<CompassDirection32>) BearingCalculator.initialBearing( CompassDirection32.class, point1, point2 );
      assertEquals( 232.95302, bearing32.getBearing().doubleValue(), .00001 );
   }

   @Test
   @SuppressWarnings("unchecked")
   public void initialBearing_equivalence() {
      final Bearing<CompassDirection8>  bearing8  = (Bearing<CompassDirection8>)  BearingCalculator.initialBearing( CompassDirection8.class, point1, point2 );
      final Bearing<CompassDirection16> bearing16 = (Bearing<CompassDirection16>) BearingCalculator.initialBearing( CompassDirection16.class, point1, point2 );
      final Bearing<CompassDirection32> bearing32 = (Bearing<CompassDirection32>) BearingCalculator.initialBearing( CompassDirection32.class, point1, point2 );

      assertEquals( bearing8.getBearing(), bearing16.getBearing() );
      assertEquals( bearing16.getBearing(), bearing32.getBearing() );
   }

   @Test
   public void backAzimuth_nullCompassDirectionType() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_COMPASS_DIRECTION_NULL );

      BearingCalculator.backAzimuth( null, BigDecimal.ZERO );
   }

   @Test
   public void backAzimuth_nullInitialBearing() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_BEARING_NULL );

      BearingCalculator.backAzimuth( CompassDirection8.class, null );
   }

   @Test
   public void backAzimuth_outOfLowerBound() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE );

      BearingCalculator.backAzimuth( CompassDirection8.class, new BigDecimal("-.00000000001") );
   }

   @Test
   public void backAzimuth_outOfUpperBound() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE );

      BearingCalculator.backAzimuth( CompassDirection8.class, new BigDecimal("360.00000000001") );
   }

   @Test
   public void backAzimuth_0() {
      @SuppressWarnings("unchecked")
      final Bearing<CompassDirection8> back = (Bearing<CompassDirection8>) BearingCalculator.backAzimuth( CompassDirection8.class, BigDecimal.ZERO );

      assertEquals( new BigDecimal(180), back.getBearing() );
   }

   @Test
   public void backAzimuth_179_9999() {
      final BigDecimal bearing = new BigDecimal( "179.9999999999" );

      @SuppressWarnings("unchecked")
      final Bearing<CompassDirection8> back = (Bearing<CompassDirection8>) BearingCalculator.backAzimuth( CompassDirection8.class, bearing );

      assertEquals( "359.9999999999", back.getBearing().toPlainString() );
   }

   @Test
   public void backAzimuth_180() {
      @SuppressWarnings("unchecked")
      final Bearing<CompassDirection8> back = (Bearing<CompassDirection8>) BearingCalculator.backAzimuth( CompassDirection8.class, new BigDecimal(180) );

      assertEquals( BigDecimal.ZERO, back.getBearing() );
   }

   @Test
   public void backAzimuth_180_0001() {
      final BigDecimal bearing = new BigDecimal( "180.00000000001" );

      @SuppressWarnings("unchecked")
      final Bearing<CompassDirection8> back = (Bearing<CompassDirection8>) BearingCalculator.backAzimuth( CompassDirection8.class, bearing );

      assertEquals( "0.00000000001", back.getBearing().toPlainString() );
   }

   @Test
   public void backAzimuth_360() {
      @SuppressWarnings("unchecked")
      final Bearing<CompassDirection8> back = (Bearing<CompassDirection8>) BearingCalculator.backAzimuth( CompassDirection8.class, new BigDecimal(360) );

      assertEquals( new BigDecimal(180), back.getBearing() );
   }
}
