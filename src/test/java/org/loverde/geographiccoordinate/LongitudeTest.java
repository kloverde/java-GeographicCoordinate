/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;

import junit.framework.TestCase;


public class LongitudeTest extends TestCase {
   private Longitude lon1;

   private static final String DEGREES_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.DEGREES_RANGE + Longitude.MAX_VALUE,
                               MINUTES_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.MINUTES_RANGE,
                               MINUTES_AND_SECONDS_MUST_BE_ZERO = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + Longitude.MAX_VALUE,
                               SECONDS_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.SECONDS_RANGE;

   @Override
   public void setUp() {
      lon1 = new Longitude( 12, 16, 23.45d, Longitude.Direction.EAST );
   }

   public void testConstant_maxValueIsCorrect() {
      assertEquals( 180, Longitude.MAX_VALUE );
   }

   public void testConstructor_actuallySetsStuffCorrectly_east() {
      final int deg = 1;
      final int min = 2;
      final double sec = 3;
      final Longitude.Direction dir = Longitude.Direction.EAST;

      final Longitude l = new Longitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_actuallySetsStuffCorrectly_west() {
      final int deg = 1;
      final int min = 2;
      final double sec = 3;
      final Longitude.Direction dir = Longitude.Direction.WEST;

      final Longitude l = new Longitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_actuallySetsStuffCorrectly_directionNeither() {
      final int deg = 0;
      final int min = 0;
      final double sec = 0;
      final Longitude.Direction dir = Longitude.Direction.NEITHER;

      final Longitude l = new Longitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_fail_directionNeither() {
      try {
         new Longitude( 1, 1, 1, Longitude.Direction.NEITHER );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.DIRECTION_INVALID) );
      }
   }

   public void testDoubleConstructor_success_directionEast() {
      final Longitude l = new Longitude( 1 );
      assertEquals( Longitude.Direction.EAST, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionWest() {
      final Longitude l = new Longitude( -1 );
      assertEquals( Longitude.Direction.WEST, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionNeither() {
      final Longitude l = new Longitude( 0 );
      assertEquals( Longitude.Direction.NEITHER, l.getDirection() );
   }

   public void testDoubleConstructor_success_maxValue() {
      final Longitude l = new Longitude( Longitude.MAX_VALUE );

      assertEquals( Longitude.MAX_VALUE, l.getDegrees() );
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Longitude.Direction.EAST, l.getDirection() );
      assertEquals( Double.valueOf(Longitude.MAX_VALUE), l.toDouble() );
   }

   public void testDoubleConstructor_fail_maxValueExceeded_degrees() {
      try {
         new Longitude( Longitude.MAX_VALUE + 1 );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testDoubleConstructor_fail_maxValueExceeded_minutesSeconds() {
      try {
         new Longitude( Longitude.MAX_VALUE + .000000001d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testDoubleConstructor_success_minValue() {
      final Longitude l = new Longitude( -Longitude.MAX_VALUE );

      assertEquals( Longitude.MAX_VALUE, l.getDegrees() );  // degrees are not negative - direction indicates sign
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Longitude.Direction.WEST, l.getDirection() );
      assertEquals( Double.valueOf(-Longitude.MAX_VALUE), l.toDouble() );
   }

   public void testDoubleConstructor_fail_minValueExceeded_degrees() {
      try {
         new Longitude( -(Longitude.MAX_VALUE + 1) );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testDoubleConstructor_fail_minValueExceeded_minutesSeconds() {
      try {
         new Longitude( -(Longitude.MAX_VALUE + .000000001d) );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testDoubleConstructor_success_directionRecognizedAsEast() {
      final Longitude l = new Longitude( 40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Longitude.Direction.EAST, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionRecognizedAsWest() {
      final Longitude l = new Longitude( -40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Longitude.Direction.WEST, l.getDirection() );
   }

   public void testConstructor_success_degreesAtMinValue() {
      new Longitude( 0, 10, 10, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_degreesBelowMinValue() {
      try {
         new Longitude( -1, 10, 20, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testConstructor_success_degreesAtMaxValue() {
      new Longitude( Longitude.MAX_VALUE, 0, 0, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_degreesExceedMaxValue() {
      try {
         new Longitude( Longitude.MAX_VALUE + 1, 0, 0, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testConstructor_fail_directionNull() {
      try {
         new Longitude( 1, 2, 3, null );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.DIRECTION_NULL) );
      }
   }

   public void testConstructor_success_minutesAtMinValue() {
      new Longitude( 10, 0, 10, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_minutesBelowMinValue() {
      try {
         new Longitude( 10, -1, 10, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_RANGE) );
      }
   }

   public void testConstructor_success_minutesAtMaxValue() {
      new Longitude( 10, 59, 10, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_minutesExceedMaxValue() {
      try {
         new Longitude( 10, 60, 1, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_RANGE) );
      }
   }

   public void testConstructor_fail_minutesMustBeZero() {
      try {
         new Longitude( Longitude.MAX_VALUE, 1, 0, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testConstructor_success_secondsAtMinValue() {
      new Longitude( 20, 10, 0, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_secondsBelowMinValue() {
      try {
         new Longitude( 20, 10, -.0000001d, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(SECONDS_RANGE) );
      }
   }

   public void testConstructor_success_secondsAtMaxValue() {
      new Longitude( 20, 10, 59.999999999999d, Longitude.Direction.EAST );
   }

   public void testConstructor_fail_secondsExceedMaxValue() {
      try {
         new Longitude( 10, 10, 60, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(SECONDS_RANGE) );
      }
   }

   public void testConstructor_fail_secondsMustBeZero() {
      try {
         new Longitude( Longitude.MAX_VALUE, 0, 1, Longitude.Direction.EAST );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testToDouble_success_east() {
      final Longitude l = new Longitude( 40, 26, 26.16d, Longitude.Direction.EAST );
      assertEquals( 40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testToDouble_success_west() {
      final Longitude l = new Longitude( 40, 26, 26.16d, Longitude.Direction.WEST );
      assertEquals( -40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testEquals_success_equalToOther() {
      final Longitude lon2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertEquals( lon1, lon2 );
      assertEquals( lon2, lon1 );
   }

   public void testEquals_success_equalToSelf() {
      assertEquals( lon1, lon1 );
   }

   public void testEquals_fail_null() {
      assertFalse( lon1.equals(null) );
   }

   public void testEquals_fail_degrees() {
      final Longitude l2 = new Longitude( lon1.getDegrees() + 1, lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_fail_minutes() {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes() + 1, lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_fail_seconds() {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds() + 1, lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_fail_DirectionDifferent() {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Longitude.Direction.WEST );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_fail_DifferentParentClass() {
      assertFalse( lon1.equals(Integer.valueOf(2)) );
   }

   public void testEquals_fail_LatitudeDirectionNorth() {
      final Latitude latitude = new Latitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.NORTH );
      assertFalse( lon1.equals(latitude) );
   }

   public void testEquals_fail_LatitudeDirectionSouth() {
      final Latitude latitude = new Latitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.SOUTH );
      assertFalse( lon1.equals(latitude) );
   }

   public void testEquals_fail_LatitudeDirectionNeither() {
      final Latitude latitude = new Latitude( 0, 0, 0, Latitude.Direction.NEITHER );
      assertFalse( lon1.equals(latitude) );
   }

   public void testHashCode_success_same() {
      final Longitude lon2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertEquals( lon1.hashCode(), lon2.hashCode() );
   }

   public void testHashCode_fail_differentTypeLatitudeDirectionNorth() {
      final Latitude lat = new Latitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.NORTH );
      assertFalse( lon1.hashCode() == lat.hashCode() );
   }

   public void testHashCode_fail_differentTypeLatitudeDirectionSouth() {
      final Latitude lat = new Latitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.SOUTH );
      assertFalse( lon1.hashCode() == lat.hashCode() );
   }

   public void testHashCode_fail_differentTypeLatitudeDirectionNeither() {
      final Latitude lat = new Latitude( 0, 0, 0, Latitude.Direction.NEITHER );
      final Longitude lon = new Longitude( lat.getDegrees(), lat.getMinutes(), lat.getSeconds(), Longitude.Direction.NEITHER );

      assertFalse( lat.hashCode() == lon.hashCode() );
   }

   public void testHashCode_fail_differentDegrees() {
      final Longitude l2 = new Longitude( lon1.getDegrees() + 1, lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.hashCode() == l2.hashCode() );
   }

   public void testHashCode_fail_differentMinutes() {
      final Longitude l = new Longitude( lon1.getDegrees(), lon1.getMinutes() + 1, lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.hashCode() == l.hashCode() );
   }

   public void testHashCode_fail_differentSeconds() {
      final Longitude l = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds() + 1, lon1.getDirection() );
      assertFalse( lon1.hashCode() == l.hashCode() );
   }

   public void testHashCode_fail_differentDirection() {
      final Longitude l = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Longitude.Direction.WEST );
      assertFalse( lon1.hashCode() == l.hashCode() );
   }

   public void testToRadians_success() {
      assertEquals( Math.toRadians(lon1.toDouble()), lon1.toRadians() );
   }

   public void testToString_success_east_localeWithPeriods() {
      assertEquals( "12°16'23.45\"E", lon1.toString(Locale.US) );
   }

   public void testToString_success_west_localeWithPeriods() {
      final Longitude l = new Longitude( 12, 16, 23.45d, Longitude.Direction.WEST );
      assertEquals( "12°16'23.45\"W", l.toString(Locale.US) );
   }

   public void testToString_success_east_localeWithCommas() {
      assertEquals( "12°16'23,45\"E", lon1.toString(Locale.FRANCE) );
   }

   public void testToString_success_west_localeWithCommas() {
      final Longitude l = new Longitude( 12, 16, 23.45d, Longitude.Direction.WEST );
      assertEquals( "12°16'23,45\"W", l.toString(Locale.FRANCE) );
   }

   public void testToString_success_noDirectionOnPrimeMeridian() {
      final Longitude l = new Longitude( 0 );
      assertEquals( "0°0'0\"", l.toString() );
   }

   public void testToString_success_noTrailingZeros() {
      final Longitude l = new Longitude(12, 16, 0, Longitude.Direction.EAST );
      assertEquals( "12°16'0\"E", l.toString() );
   }
}
