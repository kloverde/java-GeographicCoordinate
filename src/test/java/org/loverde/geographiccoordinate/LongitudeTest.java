/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import junit.framework.TestCase;


public class LongitudeTest extends TestCase {
   private Longitude lon1, lon2;

   @Override
   public void setUp() throws GeographicCoordinateException {
      lon1 = new Longitude( 2, 4, 6.123d, Longitude.Direction.EAST );
      lon2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
   }

   public void testDoubleConstructor_maxValue_success() throws GeographicCoordinateException {
      final Longitude l = new Longitude( 180 );

      assertEquals( 180, l.getDegrees() );
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Longitude.Direction.EAST, l.getDirection() );
      assertEquals( 180.0d, l.toDouble() );
   }

   public void testDoubleConstructor_maxValueExceeded_degrees() {
      try {
         new Longitude( 181 );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE, e.getMessage() );
      }
   }

   public void testDoubleConstructor_maxValueExceeded_minutesSeconds() {
      try {
         new Longitude( -180.000000001d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testDoubleConstructor_minValue_success() throws GeographicCoordinateException {
      final Longitude l = new Longitude( -180 );

      assertEquals( 180, l.getDegrees() );  // degrees are not negative - direction indicates sign
      assertEquals( 0, l.getMinutes() );
      assertEquals( 0.0d, l.getSeconds() );
      assertEquals( Longitude.Direction.WEST, l.getDirection() );
      assertEquals( -180.0d, l.toDouble() );
   }

   public void testDoubleConstructor_minValueExceeded_degrees() {
      try {
         new Longitude( -181 );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE, e.getMessage() );
      }
   }

   public void testDoubleConstructor_minValueExceeded_minutesSeconds() {
      try {
         new Longitude( -180.000000001d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testSetDegrees_minValueSuccess() throws GeographicCoordinateException {
      final int degrees = 0;
      final Longitude l = new Longitude();

      l.setDegrees( degrees );
      assertEquals( degrees, l.getDegrees() );
   }

   public void testSetDegrees_minValueFail() {
      final int degrees = -1;
      final Longitude l = new Longitude();

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE, e.getMessage() );
      }
   }

   public void testSetDegrees_maxValueSuccess() throws GeographicCoordinateException {
      final int degrees = 180;
      final Longitude l = new Longitude();

      l.setDegrees( degrees );
      assertEquals( degrees, l.getDegrees() );
   }

   public void testSetDegrees_maxValueFailOnMinutes() throws GeographicCoordinateException {
      final int mins = 1;
      final int degrees = 180;
      final Longitude l = new Longitude();

      l.setMinutes( mins );

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( mins, l.getMinutes() );
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testSetDegrees_maxValueFailOnSeconds() throws GeographicCoordinateException {
      final double secs = 1.0d;
      final int degrees = 180;
      final Longitude l = new Longitude();

      l.setSeconds( secs );

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( secs, l.getSeconds() );
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testSetDegrees_maxValueFail() {
      final int degrees = 181;
      final Longitude l = new Longitude();

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE, e.getMessage() );
      }
   }

   public void testSetDirection_failDirectionNull() {
      final Longitude.Direction bak = lon1.getDirection();

      try {
         lon1.setDirection( null );
         fail( "Expected exception" );
      } catch( final IllegalArgumentException e ) {
         assertSame( bak, lon1.getDirection() );
         assertEquals( GeographicCoordinateException.Messages.DIRECTION_NULL, e.getMessage() );
      }
   }

   public void testSetMinutes_minValueSuccess() throws GeographicCoordinateException {
      final int mins = 0;
      final Longitude l = new Longitude();

      l.setMinutes( mins );
      assertEquals( mins, l.getMinutes() );
   }

   public void testSetMinutes_minValueFail() {
      final int mins = -1;
      final Longitude l = new Longitude();

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_RANGE, e.getMessage() );
      }
   }

   public void testSetMinutes_maxValueSuccess() throws GeographicCoordinateException {
      final int mins = 59;
      final Longitude l = new Longitude();

      l.setMinutes( mins );
      assertEquals( mins, l.getMinutes() );
   }

   public void testSetMinutes_maxValueFail() {
      final int mins = 60;
      final Longitude l = new Longitude();

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_RANGE, e.getMessage() );
      }
   }

   public void testSetMinutes_failOnMaxDegrees() throws GeographicCoordinateException {
      final int degrees = 180;
      final int mins = 1;
      final Longitude l = new Longitude();

      l.setDegrees( degrees );

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( degrees, l.getDegrees() );
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testSetSeconds_minValueSuccess() throws GeographicCoordinateException {
      final double sec = 0.0d;
      final Longitude l = new Longitude();

      l.setSeconds( sec );
      assertEquals( sec, l.getSeconds() );
   }

   public void testSetSeconds_minValueFail() {
      final double sec = -.0000001d;
      final Longitude l = new Longitude();

      try {
         l.setSeconds( sec );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_SECONDS_RANGE, e.getMessage() );
      }
   }

   public void testSetSeconds_maxValueSuccess() throws GeographicCoordinateException {
      final double sec = 59.999999999999d;
      final Longitude l = new Longitude();

      l.setSeconds( sec );
      assertEquals( sec, l.getSeconds() );
   }

   public void testSetSeconds_maxValueFail() {
      final double sec = 60.0d;
      final Longitude l = new Longitude();

      try {
         l.setSeconds( sec );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_SECONDS_RANGE, e.getMessage() );
      }
   }

   public void testSetSeconds_failOnMaxDegrees() throws GeographicCoordinateException {
      final int degrees = 180;
      final double secs = 1.0d;
      final Longitude l = new Longitude();

      l.setDegrees( degrees );

      try {
         l.setSeconds( secs );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( degrees, l.getDegrees() );
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      }
   }

   public void testToDouble_west() throws GeographicCoordinateException {
      final Longitude l = new Longitude( 79, 59, 45.9594d, Longitude.Direction.WEST );
      assertEquals( -79.9961d, l.toDouble(), 0.00000016666667d );
   }

   public void testToDouble_east() throws GeographicCoordinateException {
      final Longitude l = new Longitude( 79, 59, 45.9594d, Longitude.Direction.EAST );
      assertEquals( 79.9961d, l.toDouble(), 0.00000016666667d );
   }

   public void testDoubleConstructor_west() throws GeographicCoordinateException {
      final Longitude l = new Longitude( -79.9961d );

      assertEquals( 79, l.getDegrees() );
      assertEquals( 59, l.getMinutes() );
      assertEquals( 45.9594d, l.getSeconds(), 0.00059999999435d );
      assertEquals( Longitude.Direction.WEST, l.getDirection() );
   }

   public void testDoubleConstructor_east() throws GeographicCoordinateException {
      final Longitude l = new Longitude( 79.9961d );

      assertEquals( 79, l.getDegrees() );
      assertEquals( 59, l.getMinutes() );
      assertEquals( 45.9594d, l.getSeconds(), 0.00059999999435d );
      assertEquals( Longitude.Direction.EAST, l.getDirection() );
   }

   public void testEquals_equalToOther() throws GeographicCoordinateException {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertEquals( lon1, l2 );
   }

   public void testEquals_equalToSelf() {
      assertEquals( lon1, lon1 );
   }

   public void testEquals_failDegrees() throws GeographicCoordinateException {
      final Longitude l2 = new Longitude( lon1.getDegrees() + 1, lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_failMinutes() throws GeographicCoordinateException {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes() + 1, lon1.getSeconds(), lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_failSeconds() throws GeographicCoordinateException {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds() + 1.0d, lon1.getDirection() );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_failDirectionDifferent() throws GeographicCoordinateException {
      final Longitude l2 = new Longitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Longitude.Direction.WEST );
      assertFalse( lon1.equals(l2) );
   }

   public void testEquals_failDifferentParentClass() {
      assertFalse( lon1.equals(Integer.valueOf(2)) );
   }

   public void testEquals_failDifferentSiblingClass() throws GeographicCoordinateException {
      final Latitude latitude = new Latitude( lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.NORTH );
      assertFalse( lon1.equals(latitude) );
   }

   public void testHashCode_same() {
      assertEquals( lon1, lon2 );
      assertEquals( lon1.hashCode(), lon2.hashCode() );
   }

   public void testHashCode_differentType() throws GeographicCoordinateException {
      final Longitude lon = new Longitude();

      lon.setDegrees( lon1.getDegrees() );
      lon.setMinutes( lon1.getMinutes() );
      lon.setSeconds( lon1.getSeconds() );

      assertFalse( lon1.hashCode() == lon.hashCode() );
   }

   public void testHashCode_differentDegrees() throws GeographicCoordinateException {
      lon2.setDegrees( lon1.getDegrees() + 1 );
      assertFalse( lon1.hashCode() == lon2.hashCode() );
   }

   public void testHashCode_differentMinutes() throws GeographicCoordinateException {
      lon2.setMinutes( lon1.getMinutes() + 1 );
      assertFalse( lon1.hashCode() == lon2.hashCode() );
   }

   public void testHashCode_differentSeconds() throws GeographicCoordinateException {
      lon2.setSeconds( lon1.getSeconds() + 1 );
      assertFalse( lon1.hashCode() == lon2.hashCode() );
   }

   public void testHashCode_differentDirection() {
      lon2.setDirection( Longitude.Direction.WEST );
      assertFalse( lon1.hashCode() == lon2.hashCode() );
   }

   public void testToRadians() {
      assertEquals( Math.toRadians(lon1.toDouble()), lon1.toRadians() );
   }

   public void testToString_east() {
      assertEquals( "2°4'6.123\"E", lon1.toString() );
   }

   public void testToString_west() {
      lon1.setDirection( Longitude.Direction.WEST );
      assertEquals( "2°4'6.123\"W", lon1.toString() );
   }

   public void testToString_noTrailingZeros() throws GeographicCoordinateException {
      lon1.setSeconds( 0 );
      assertEquals( "2°4'0\"E", lon1.toString() );
   }
}
