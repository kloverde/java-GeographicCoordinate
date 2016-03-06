/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;

import junit.framework.TestCase;


public class LatitudeTest extends TestCase {
   private Latitude lat1;

   private static final String DEGREES_RANGE = "Latitude" + GeographicCoordinateException.Messages.DEGREES_RANGE + Latitude.MAX_VALUE,
                               MINUTES_RANGE = "Latitude" + GeographicCoordinateException.Messages.MINUTES_RANGE,
                               MINUTES_AND_SECONDS_MUST_BE_ZERO = "Latitude" + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + Latitude.MAX_VALUE,
                               SECONDS_RANGE = "Latitude" + GeographicCoordinateException.Messages.SECONDS_RANGE;

   @Override
   public void setUp() throws GeographicCoordinateException {
      lat1 = new Latitude( 12, 16, 23.45d, Latitude.Direction.NORTH );
   }

   public void testConstant_maxValueIsCorrect() {
      assertEquals( 90, Latitude.MAX_VALUE );
   }

   public void testConstructor_actuallySetsStuffCorrectly_directionNorth() {
      final int deg = 1;
      final int min = 2;
      final double sec = 3;
      final Latitude.Direction dir = Latitude.Direction.NORTH;

      final Latitude l = new Latitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_actuallySetsStuffCorrectly_directionSouth() {
      final int deg = 1;
      final int min = 2;
      final double sec = 3;
      final Latitude.Direction dir = Latitude.Direction.SOUTH;

      final Latitude l = new Latitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_actuallySetsStuffCorrectly_directionNeither() {
      final int deg = 0;
      final int min = 0;
      final double sec = 0;
      final Latitude.Direction dir = Latitude.Direction.NEITHER;

      final Latitude l = new Latitude( deg, min, sec, dir );

      assertEquals( deg, l.getDegrees() );
      assertEquals( min, l.getMinutes() );
      assertEquals( sec, l.getSeconds() );
      assertEquals( dir, l.getDirection() );
   }

   public void testConstructor_fail_directionNeither() {
      try {
         new Latitude( 1, 1, 1, Latitude.Direction.NEITHER );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.DIRECTION_INVALID) );
      }
   }

   public void testDoubleConstructor_success_directionNorth() {
      final Latitude l = new Latitude( 1 );
      assertEquals( Latitude.Direction.NORTH, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionSouth() {
      final Latitude l = new Latitude( -1 );
      assertEquals( Latitude.Direction.SOUTH, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionNeither() {
      final Latitude l = new Latitude( 0 );
      assertEquals( Latitude.Direction.NEITHER, l.getDirection() );
   }

   public void testDoubleConstructor_success_maxValue() throws GeographicCoordinateException {
      final Latitude l = new Latitude( 90 );

      assertEquals( 90, l.getDegrees() );
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Latitude.Direction.NORTH, l.getDirection() );
      assertEquals( 90.0d, l.toDouble() );
   }

   public void testDoubleConstructor_fail_maxValueExceeded_degrees() {
      try {
         new Latitude( 91 );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testDoubleConstructor_fail_maxValueExceeded_minutesSeconds() {
      try {
         new Latitude( 90.000000001d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testDoubleConstructor_success_minValue() throws GeographicCoordinateException {
      final Latitude l = new Latitude( -90 );

      assertEquals( 90, l.getDegrees() );  // degrees are not negative - direction indicates sign
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Latitude.Direction.SOUTH, l.getDirection() );
      assertEquals( -90.0d, l.toDouble() );
   }

   public void testDoubleConstructor_fail_minValueExceeded_degrees() {
      try {
         new Latitude( -91 );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testDoubleConstructor_fail_minValueExceeded_minutesSeconds() {
      try {
         new Latitude( -90.000000001d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testDoubleConstructor_success_directionRecognizedAsNorth() {
      final Latitude l = new Latitude( 40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Latitude.Direction.NORTH, l.getDirection() );
   }

   public void testDoubleConstructor_success_directionRecognizedAsSouth() {
      final Latitude l = new Latitude( -40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Latitude.Direction.SOUTH, l.getDirection() );
   }

   public void testConstructor_success_degreesAtMinValue() {
      new Latitude( 0, 10, 10, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_degreesBelowMinValue() {
      try {
         new Latitude( -1, 10, 20, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testConstructor_success_degreesAtMaxValue() throws GeographicCoordinateException {
      new Latitude( 90, 0, 0, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_degreesExceedMaxValue() {
      try {
         new Latitude( Latitude.MAX_VALUE + 1, 0, 0, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(DEGREES_RANGE) );
      }
   }

   public void testConstructor_fail_directionNull() {
      try {
         new Latitude( 1, 2, 3, null );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.DIRECTION_NULL) );
      }
   }

   public void testConstructor_success_minutesAtMinValue() throws GeographicCoordinateException {
      new Latitude( 10, 0, 10, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_minutesBelowMinValue() {
      try {
         new Latitude( 10, -1, 10, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_RANGE) );
      }
   }

   public void testConstructor_success_minutesAtMaxValue() {
      new Latitude( 10, 59, 10, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_minutesExceedMaxValue() {
      try {
         new Latitude( 10, 60, 1, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_RANGE) );
      }
   }

   public void testConstructor_fail_minutesMustBeZero() {
      try {
         new Latitude( 90, 1, 0, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testConstructor_success_secondsAtMinValue() {
      new Latitude( 20, 10, 0, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_secondsBelowMinValue() {
      try {
         new Latitude( 20, 10, -.0000001d, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.SECONDS_RANGE) );
      }
   }

   public void testConstructor_success_secondsAtMaxValue() {
      new Latitude( 20, 10, 59.999999999999d, Latitude.Direction.NORTH );
   }

   public void testConstructor_fail_secondsExceedMaxValue() {
      try {
         new Latitude( 10, 10, 60, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(SECONDS_RANGE) );
      }
   }

   public void testConstructor_fail_secondsMustBeZero() {
      try {
         new Latitude( 90, 0, 1, Latitude.Direction.NORTH );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(MINUTES_AND_SECONDS_MUST_BE_ZERO) );
      }
   }

   public void testToDouble_success_north() {
      final Latitude l = new Latitude( 40, 26, 26.16d, Latitude.Direction.NORTH );
      assertEquals( 40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testToDouble_success_south() {
      final Latitude l = new Latitude( 40, 26, 26.16d, Latitude.Direction.SOUTH );
      assertEquals( -40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testEquals_success_equalToOther() {
      final Latitude lat2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
      assertEquals( lat1, lat2 );
      assertEquals( lat2, lat1 );
   }

   public void testEquals_success_equalToSelf() {
      assertEquals( lat1, lat1 );
   }

   public void testEquals_fail_null() {
      assertFalse( lat1.equals(null) );
   }

   public void testEquals_fail_degrees() {
      final Latitude l2 = new Latitude( lat1.getDegrees() + 1, lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_fail_minutes() {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes() + 1, lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_fail_seconds() {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds() + 1, lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_fail_directionDifferent() {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Latitude.Direction.SOUTH );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_fail_differentParentClass() {
      assertFalse( lat1.equals(Integer.valueOf(2)) );
   }

   public void testEquals_fail_longitudeDirectionEast() {
      final Longitude longitude = new Longitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.EAST );
      assertFalse( lat1.equals(longitude) );
   }

   public void testEquals_fail_longitudeDirectionWest() {
      final Longitude longitude = new Longitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.WEST );
      assertFalse( lat1.equals(longitude) );
   }

   public void testEquals_fail_longitudeDirectionNeither() {
      final Longitude longitude = new Longitude( 0, 0, 0, Longitude.Direction.NEITHER );
      assertFalse( lat1.equals(longitude) );
   }

   public void testHashCode_success_same() {
      final Latitude lat2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
      assertEquals( lat1.hashCode(), lat2.hashCode() );
   }

   public void testHashCode_fail_differentTypeLongitudeDirectionEast() {
      final Longitude lon = new Longitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.EAST );
      assertFalse( lat1.hashCode() == lon.hashCode() );
   }

   public void testHashCode_differentType_longitudeDirectionWest() {
      final Longitude lon = new Longitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.WEST );
      assertFalse( lat1.hashCode() == lon.hashCode() );
   }

   public void testHashCode_fail_differentTypeLongitudeDirectionNeither() {
      final Latitude lat = new Latitude( 0, 0, 0, Latitude.Direction.NEITHER );
      final Longitude lon = new Longitude( lat.getDegrees(), lat.getMinutes(), lat.getSeconds(), Longitude.Direction.NEITHER );

      assertFalse( lat.hashCode() == lon.hashCode() );
   }

   public void testHashCode_fail_differentDegrees() {
      final Latitude l2 = new Latitude( lat1.getDegrees() + 1, lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.hashCode() == l2.hashCode() );
   }

   public void testHashCode_fail_differentMinutes() {
      final Latitude l = new Latitude( lat1.getDegrees(), lat1.getMinutes() + 1, lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.hashCode() == l.hashCode() );
   }

   public void testHashCode_fail_differentSeconds() {
      final Latitude l = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds() + 1, lat1.getDirection() );
      assertFalse( lat1.hashCode() == l.hashCode() );
   }

   public void testHashCode_fail_differentDirection() {
      final Latitude l = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Latitude.Direction.SOUTH );
      assertFalse( lat1.hashCode() == l.hashCode() );
   }

   public void testToRadians_success() {
      assertEquals( Math.toRadians(lat1.toDouble()), lat1.toRadians() );
   }

   public void testToString_success_north_localeWithPeriods() {
      assertEquals( "12°16'23.45\"N", lat1.toString(Locale.US) );
   }

   public void testToString_success_south_localeWithPeriods() {
      final Latitude l = new Latitude( 12, 16, 23.45d, Latitude.Direction.SOUTH );
      assertEquals( "12°16'23.45\"S", l.toString(Locale.US) );
   }

   public void testToString_success_north_localeWithCommas() {
      assertEquals( "12°16'23,45\"N", lat1.toString(Locale.FRANCE) );
   }

   public void testToString_success_south_localeWithCommas() {
      final Latitude l = new Latitude( 12, 16, 23.45d, Latitude.Direction.SOUTH );
      assertEquals( "12°16'23,45\"S", l.toString(Locale.FRANCE) );
   }

   public void testToString_success_noDirectionOnEquator() {
      final Latitude l = new Latitude( 0 );
      assertEquals( "0°0'0\"", l.toString() );
   }

   public void testToString_success_noTrailingZeros() {
      final Latitude l = new Latitude(12, 16, 0, Latitude.Direction.NORTH );
      assertEquals( "12°16'0\"N", l.toString() );
   }
}
