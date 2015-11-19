/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


public class Longitude extends GeographicCoordinateImpl {

   public static enum Direction {
      EAST,
      WEST
   };

   public static final int MAX_VALUE = 180;

   private Direction direction;


   public Longitude() {
      super( GeographicCoordinateImpl.Type.LONGITUDE );
   }

   public Longitude( final double longitude ) throws GeographicCoordinateException {
      super( GeographicCoordinateImpl.Type.LONGITUDE,
             (int) Math.abs(longitude),
             (int) ((Math.abs(longitude) - (int)Math.abs(longitude)) * 60.0d),
             (((Math.abs(longitude) - (int)Math.abs(longitude)) * 60.0d) % 1.0d) * 60.0d );

      setDirection( longitude > 0.0d ? Direction.EAST : Direction.WEST );
   }

   public Longitude( final int degrees, final int minutes, final double seconds, final Direction dir ) throws GeographicCoordinateException {
      super( GeographicCoordinateImpl.Type.LONGITUDE, degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Longitude.Direction}
    */
   public void setDirection( final Longitude.Direction direction ) {
      if( direction == null )  throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      this.direction = direction;
   }

   public Longitude.Direction getDirection() {
      return direction;
   }

   @Override
   public double toDouble() {
      if( getDirection() == null )  throw new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      final double decimal = getDegrees() + (getMinutes() / 60.0d) + (getSeconds() / 3600.0d);

      return getDirection() == Direction.EAST ? decimal : -decimal;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = super.hashCode();

      result = prime * result + (direction == null ? 0 : direction.hashCode());

      return result;
   }

   @Override
   public boolean equals( final Object compareTo ) {
      final Longitude other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof Longitude) ) return false;

      other = (Longitude) compareTo;

      if( getDirection() == null && other.getDirection() != null ) return false;
      if( getDirection() != null && other.getDirection() == null ) return false;
      if( !getDirection().equals(other.getDirection()) ) return false;

      return super.equals( other );
   }

   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%s Direction (%s), decimal (%.15f)",
                            super.toString(),
                            getDirection(),
                            toDouble() );
   }
}
