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
   public void bearing8() {
      final Bearing<CompassDirection8> bearing8 = BearingCalculator.bearing8( point1, point2 );
      assertEquals( 232.95302, bearing8.getBearing(), .00001 );
   }

   @Test
   public void bearingX_equivalence() {
      final Bearing<CompassDirection8>  bearing8  = BearingCalculator.bearing8( point1, point2 );
      final Bearing<CompassDirection16> bearing16 = BearingCalculator.bearing16( point1, point2 );
      final Bearing<CompassDirection32> bearing32 = BearingCalculator.bearing32( point1, point2 );

      assertEquals( bearing8.getBearing(), bearing16.getBearing(), 0 );
      assertEquals( bearing16.getBearing(), bearing32.getBearing(), 0 );
   }
}
