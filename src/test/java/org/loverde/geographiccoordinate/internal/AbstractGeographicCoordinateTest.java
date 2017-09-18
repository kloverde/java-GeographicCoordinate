/*
 * Copyright (C) 2016 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate.internal;

import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.LatLonDirection;
import org.loverde.geographiccoordinate.internal.AbstractGeographicCoordinate;

import junit.framework.TestCase;


public class AbstractGeographicCoordinateTest extends TestCase {

   private class GeoSubclass extends AbstractGeographicCoordinate {
      public GeoSubclass( double d ) {
         super( d );
      }

      @Override
      public double toDouble() {
         return 0;
      }

      @Override
      public LatLonDirection getDirection() {
         return null;
      }
   }

   public void testSubclassPrevention() {
      try {
         new GeoSubclass( 1.234d );
         fail( "Expected exception" );
      } catch( final GeographicCoordinateException e ) {
         assertEquals( GeographicCoordinateException.Messages.DISALLOWED_EXTENDS, e.getMessage() );
      }
   }
}
