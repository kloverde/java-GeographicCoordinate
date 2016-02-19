/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import junit.framework.TestCase;


public class LatitudeTest extends TestCase {
   private Latitude lat1, lat2;

   @Override
   public void setUp()
   throws GeographicCoordinateException {
      lat1 = new Latitude( 12, 16, 23.45d, Latitude.Direction.NORTH );
      lat2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
   }

   public void testSetDegrees_minValueSuccess() throws GeographicCoordinateException {
      final int degrees = 0;
      final Latitude l = new Latitude();

      l.setDegrees( degrees );
      assertEquals( degrees, l.getDegrees() );
   }

   public void testSetDegrees_minValueFail() {
      final int degrees = -1;
      final Latitude l = new Latitude();

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_DEGREES_RANGE, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetDegrees_maxValueSuccess() throws GeographicCoordinateException {
      final int degrees = 90;
      final Latitude l = new Latitude();

      l.setDegrees( degrees );
      assertEquals( degrees, l.getDegrees() );
   }

   public void testSetDegrees_maxValueFailOnMinutes() throws GeographicCoordinateException {
      final int mins = 1;
      final int degrees = 90;
      final Latitude l = new Latitude();

      l.setMinutes( mins );

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( mins, l.getMinutes() );
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetDegrees_maxValueFailOnSeconds() throws GeographicCoordinateException {
      final double secs = 1.0d;
      final int degrees = 90;
      final Latitude l = new Latitude();

      l.setSeconds( secs );

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( secs, l.getSeconds() );
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetDegrees_maxValueFail() {
      final int degrees = 91;
      final Latitude l = new Latitude();

      try {
         l.setDegrees( degrees );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getDegrees() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_DEGREES_RANGE, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetDirection_failDirectionNull() {
      final Latitude.Direction bak = lat1.getDirection();

      try {
         lat1.setDirection( null );
         fail( "Expected exception" );
      } catch( final IllegalArgumentException e ) {
         assertSame( bak, lat1.getDirection() );
         assertEquals( GeographicCoordinateException.Messages.DIRECTION_NULL, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetMinutes_minValueSuccess() throws GeographicCoordinateException {
      final int mins = 0;
      final Latitude l = new Latitude();

      l.setMinutes( mins );
      assertEquals( mins, l.getMinutes() );
   }

   public void testSetMinutes_minValueFail() {
      final int mins = -1;
      final Latitude l = new Latitude();

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_RANGE, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetMinutes_maxValueSuccess() throws GeographicCoordinateException {
      final int mins = 59;
      final Latitude l = new Latitude();

      l.setMinutes( mins );
      assertEquals( mins, l.getMinutes() );
   }

   public void testSetMinutes_maxValueFail() {
      final int mins = 60;
      final Latitude l = new Latitude();

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_RANGE, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetMinutes_failOnMaxDegrees() throws GeographicCoordinateException {
      final int degrees = 90;
      final int mins = 1;
      final Latitude l = new Latitude();

      l.setDegrees( degrees );

      try {
         l.setMinutes( mins );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( degrees, l.getDegrees() );
         assertEquals( 0, l.getMinutes() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetSeconds_minValueSuccess() throws GeographicCoordinateException {
      final double sec = 0.0d;
      final Latitude l = new Latitude();

      l.setSeconds( sec );
      assertEquals( sec, l.getSeconds() );
   }

   public void testSetSeconds_minValueFail() {
      final double sec = -.0000001d;
      final Latitude l = new Latitude();

      try {
         l.setSeconds( sec );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_SECONDS_RANGE, e.getMessage() );
      }
      catch( final Exception e )
      {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetSeconds_maxValueSuccess() throws GeographicCoordinateException {
      final double sec = 59.999999999999d;
      final Latitude l = new Latitude();

      l.setSeconds( sec );
      assertEquals( sec, l.getSeconds() );
   }

   public void testSetSeconds_maxValueFail() {
      final double sec = 60.0d;
      final Latitude l = new Latitude();

      try {
         l.setSeconds( sec );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_SECONDS_RANGE, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testSetSeconds_failOnMaxDegrees() throws GeographicCoordinateException {
      final int degrees = 90;
      final double secs = 1.0d;
      final Latitude l = new Latitude();

      l.setDegrees( degrees );

      try {
         l.setSeconds( secs );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( degrees, l.getDegrees() );
         assertEquals( 0.0d, l.getSeconds() );
         assertEquals( GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage() );
      } catch( final Exception e ) {
         fail( "Wrong exception: " + e );
      }
   }

   public void testToDouble_north() throws GeographicCoordinateException {
      final Latitude l = new Latitude( 40, 26, 26.16d, Latitude.Direction.NORTH );
      assertEquals( 40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testToDouble_south() throws GeographicCoordinateException {
      final Latitude l = new Latitude( 40, 26, 26.16d, Latitude.Direction.SOUTH );
      assertEquals( -40.4406d, l.toDouble(), 0.00000016666667d );
   }

   public void testDoubleConstructor_north() throws GeographicCoordinateException {
      final Latitude l = new Latitude( 40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Latitude.Direction.NORTH, l.getDirection() );
   }

   public void testDoubleConstructor_south() throws GeographicCoordinateException {
      final Latitude l = new Latitude( -40.4406d );

      assertEquals( 40, l.getDegrees() );
      assertEquals( 26, l.getMinutes() );
      assertEquals( 26.16d, l.getSeconds(), 0.00000000001236d );
      assertEquals( Latitude.Direction.SOUTH, l.getDirection() );
   }

   public void testEquals_equalToOther() {
      assertEquals( lat1, lat2 );
   }

   public void testEquals_equalToSelf() {
      assertEquals( lat1, lat1 );
   }

   public void testEquals_failDegrees() throws GeographicCoordinateException {
      final Latitude l2 = new Latitude( lat1.getDegrees() + 1, lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_failMinutes() throws GeographicCoordinateException {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes() + 1, lat1.getSeconds(), lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_failSeconds() throws GeographicCoordinateException {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds() + 1.0d, lat1.getDirection() );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_failDirectionDifferent() throws GeographicCoordinateException {
      final Latitude l2 = new Latitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Latitude.Direction.SOUTH );
      assertFalse( lat1.equals(l2) );
   }

   public void testEquals_failDifferentParentClass() {
      assertFalse( lat1.equals(Integer.valueOf(2)) );
   }

   public void testEquals_failDifferentSiblingClass() throws GeographicCoordinateException {
      final Longitude longitude = new Longitude( lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.EAST );
      assertFalse( lat1.equals(longitude) );
   }

   public void testHashCode_same() {
      assertEquals( lat1, lat2 );
      assertEquals( lat1.hashCode(), lat2.hashCode() );
   }

   public void testHashCode_differentType() throws GeographicCoordinateException {
      Longitude lon = new Longitude();

      lon.setDegrees( lat1.getDegrees() );
      lon.setMinutes( lat1.getMinutes() );
      lon.setSeconds( lat1.getSeconds() );

      assertFalse( lat1.hashCode() == lon.hashCode() );
   }

   public void testHashCode_differentDegrees() throws GeographicCoordinateException {
      lat2.setDegrees( lat1.getDegrees() + 1 );
      assertFalse( lat1.hashCode() == lat2.hashCode() );
   }

   public void testHashCode_differentMinutes() throws GeographicCoordinateException {
      lat2.setMinutes( lat1.getMinutes() + 1 );
      assertFalse( lat1.hashCode() == lat2.hashCode() );
   }

   public void testHashCode_differentSeconds() throws GeographicCoordinateException {
      lat2.setSeconds( lat1.getSeconds() + 1 );
      assertFalse( lat1.hashCode() == lat2.hashCode() );
   }

   public void testHashCode_differentDirection() {
      lat2.setDirection( Latitude.Direction.SOUTH );
      assertFalse( lat1.hashCode() == lat2.hashCode() );
   }

   public void testToRadians() {
      assertEquals( Math.toRadians(lat1.toDouble()), lat1.toRadians() );
   }
}
