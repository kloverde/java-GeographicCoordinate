/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


public class Latitude extends GeographicCoordinate {
   public static enum Direction {
      NORTH,
      SOUTH
   };

   public static final int MAX_VALUE = 90;

   private Direction direction;

   public Latitude() {
      super( GeographicCoordinate.Type.LATITUDE );
   }

   public Latitude( final double latitude )
   throws GeographicCoordinateException {
      super( GeographicCoordinate.Type.LATITUDE,
             (int) Math.abs(latitude),
             (int) ((Math.abs(latitude) - (int)Math.abs(latitude)) * 60.0d),
             (((Math.abs(latitude) - (int)Math.abs(latitude)) * 60.0d) % 1.0d) * 60.0d );

      setDirection( latitude > 0.0d ? Direction.NORTH : Direction.SOUTH );
   }

   public Latitude( final int degrees, final int minutes, final double seconds, final Direction dir )
   throws GeographicCoordinateException {
      super( GeographicCoordinate.Type.LATITUDE, degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Latitude.Direction}
    */
   public void setDirection( final Latitude.Direction direction ) {
      if( direction == null ) {
         throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );
      }

      this.direction = direction;
   }

   public Latitude.Direction getDirection() {
      return direction;
   }

   public double toDouble() {
      if( getDirection() == null ) {
         throw new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );
      }

      final double decimal = getDegrees() + (getMinutes() / 60.0d) + (getSeconds() / 3600.0d);
      return getDirection() == Direction.NORTH ? decimal : -decimal;
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
      final Latitude other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof Latitude) ) return false;

      other = (Latitude) compareTo;

      if( getDirection() == null && other.getDirection() != null ) return false;
      if( getDirection() != null && other.getDirection() == null ) return false;
      if( !getDirection().equals(other.getDirection()) ) return false;

      return super.equals( other );
   }

   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%s Direction (%s), decimal (%f)",
                            super.toString(),
                            getDirection(),
                            toDouble() );
   }
}
