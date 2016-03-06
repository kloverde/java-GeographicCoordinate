/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate;

import junit.framework.TestCase;


public class PointTest extends TestCase {

   private Latitude latitude1;

   private Longitude longitude1;

   private Point point1;


   @Override
   public void setUp() throws GeographicCoordinateException {
      latitude1 = new Latitude( 40, 42, 46.1, Latitude.Direction.NORTH );
      longitude1 = new Longitude(  74, 0, 21.1, Longitude.Direction.WEST );

      point1 = new Point( latitude1, longitude1, "name" );
   }

   public void test2argConstructor_actuallySetsStuffCorrectly() {
      assertSame( latitude1, point1.getLatitude() );
      assertSame( longitude1, point1.getLongitude() );
      assertSame( "name", point1.getName() );
   }

   public void test2argConstructor_fail_nullLatitude() {
      try {
         new Point( null, longitude1 );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.LATITUDE_NULL) );
      }
   }

   public void test2argConstructor_fail_nullLongitude() {
      try {
         new Point( latitude1, null );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.LONGITUDE_NULL) );
      }
   }

   public void test3argConstructor_fail_nullLatitude() {
      try {
         new Point( null, longitude1, "name" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.LATITUDE_NULL) );
      }
   }

   public void test3argConstructor_fail_nullLongitude() {
      try {
         new Point( latitude1, null, "name" );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.LONGITUDE_NULL) );
      }
   }

   public void test3argConstructor_fail_nullName() {
      try {
         new Point( latitude1, longitude1, null );
      } catch( final GeographicCoordinateException e ) {
         assertTrue( e.getMessage().endsWith(GeographicCoordinateException.Messages.NAME_NULL) );
      }
   }

   public void testEquals_equal_sameAddress() {
      assertEquals( point1, point1 );
   }

   public void testEquals_equal_differentAddresses() {
      final Latitude lat  = new Latitude( point1.getLatitude().getDegrees(), point1.getLatitude().getMinutes(),
                                          point1.getLatitude().getSeconds(), point1.getLatitude().getDirection() );

      final Longitude lon = new Longitude( point1.getLongitude().getDegrees(), point1.getLongitude().getMinutes(),
                                           point1.getLongitude().getSeconds(), point1.getLongitude().getDirection() );

      assertEquals( point1, new Point(lat, lon, new String("name")) );
   }

   public void testEquals_notEqual_differentLatitude() {
      final Latitude lat = new Latitude( latitude1.getDegrees() + 1, latitude1.getMinutes(), latitude1.getSeconds(), latitude1.getDirection() );
      final Point newPoint = new Point( lat, point1.getLongitude() );

      assertFalse( point1.equals(newPoint) );
   }

   public void testEquals_notEqual_differentLongitude() {
      final Longitude lon = new Longitude( longitude1.getDegrees() + 1, longitude1.getMinutes(), longitude1.getSeconds(), longitude1.getDirection() );
      final Point newPoint = new Point( point1.getLatitude(), lon );

      assertFalse( point1.equals(newPoint) );
   }

   public void testEquals_notEqual_differentName() {
      final Point newPoint = new Point( point1.getLatitude(), point1.getLongitude(), point1.getName() + "different" );

      assertFalse( point1.equals(newPoint) );
   }

   public void testHashCode_notEqual_differentLatitude() {
      final Latitude lat  = new Latitude( point1.getLatitude().getDegrees(), point1.getLatitude().getMinutes(),
                                          point1.getLatitude().getSeconds() + 1, point1.getLatitude().getDirection() );

      final Longitude lon = new Longitude( point1.getLongitude().getDegrees(), point1.getLongitude().getMinutes(),
                                           point1.getLongitude().getSeconds(), point1.getLongitude().getDirection() );

      final Point newPoint = new Point( lat, lon );

      assertFalse( point1.hashCode() == newPoint.hashCode() );
   }

   public void testHashCode_notEqual_differentLongitude() {
      final Latitude lat  = new Latitude( point1.getLatitude().getDegrees(), point1.getLatitude().getMinutes(),
                                          point1.getLatitude().getSeconds(), point1.getLatitude().getDirection() );

      final Longitude lon = new Longitude( point1.getLongitude().getDegrees(), point1.getLongitude().getMinutes(),
                                           point1.getLongitude().getSeconds() + 1, point1.getLongitude().getDirection() );

      final Point newPoint = new Point( lat, lon );

      assertFalse( point1.hashCode() == newPoint.hashCode() );
   }

   public void testHashCode_notEqual_differentName() {
      final Point newPoint = new Point( point1.getLatitude(), point1.getLongitude(), point1.getName() + "different" );

      assertFalse( point1.hashCode() == newPoint.hashCode() );
   }
}
