/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import junit.framework.TestCase;

import org.loverde.geographiccoordinate.DistanceCalculator.Unit;


public class DistanceCalculatorTest extends TestCase {

   private Latitude latitude1, latitude2;

   private Longitude longitude1, longitude2;

   private Point point1, point2;


   @Override
   public void setUp() throws GeographicCoordinateException {
      latitude1 = new Latitude( 40, 42, 46, Latitude.Direction.NORTH );
      longitude1 = new Longitude(  74, 0, 21, Longitude.Direction.WEST );

      latitude2 = new Latitude( 38, 54, 17, Latitude.Direction.NORTH );
      longitude2 = new Longitude( 77, 0, 59, Longitude.Direction.WEST );

      point1 = new Point( latitude1, longitude1 );
      point2 = new Point( latitude2, longitude2 );
   }

   public void testDistance_kilometers() {
      final double distance = DistanceCalculator.distance( point1, point2, DistanceCalculator.Unit.KILOMETERS );
      assertEquals( 326.3834438586294d, distance );
   }

   public void testDistance_meters() {
      final double distance = DistanceCalculator.distance( point1, point2, DistanceCalculator.Unit.METERS );
      assertEquals( 326383.4438586294d, distance );
   }

   public void testDistance_miles() {
      final double distance = DistanceCalculator.distance( point1, point2, DistanceCalculator.Unit.MILES );
      assertEquals( 202.8052696369635d, distance );
   }

   public void testDistance_nauticalMiles() {
      final double distance = DistanceCalculator.distance( point1, point2, DistanceCalculator.Unit.NAUTICAL_MILES );
      assertEquals( 176.23296104677613d, distance );
   }

   public void testDistance_bothMethodsCalculateSameValues() {
      assertEquals( DistanceCalculator.distance(point1, point2, Unit.KILOMETERS),
                    DistanceCalculator.distance( latitude1, longitude1, latitude2, longitude2, Unit.KILOMETERS) );
   }
}
