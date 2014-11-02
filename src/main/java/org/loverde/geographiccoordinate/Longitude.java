/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


public class Longitude extends GeographicCoordinate
{
   public static enum Direction
   {
      EAST,
      WEST
   };

   public static final int MAX_VALUE = 180;

   private Direction direction;

   public Longitude()
   {
      super( GeographicCoordinate.Type.LONGITUDE );
   }

   public Longitude( final double longitude )
   throws GeographicCoordinateException
   {
      super( GeographicCoordinate.Type.LONGITUDE,
             (int) Math.abs(longitude),
             (int) ((Math.abs(longitude) - (int)Math.abs(longitude)) * 60.0d),
             (((Math.abs(longitude) - (int)Math.abs(longitude)) * 60.0d) % 1.0d) * 60.0d );

      setDirection( longitude > 0.0d ? Direction.EAST : Direction.WEST );
   }

   public Longitude( final int degrees, final int minutes, final double seconds, final Direction dir )
   throws GeographicCoordinateException
   {
      super( GeographicCoordinate.Type.LONGITUDE, degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Longitude.Direction}
    * @throws IllegalArgumentException if {@code direction} is null
    */
   public void setDirection( final Direction direction )
   throws IllegalArgumentException
   {
      if( direction == null )
      {
         throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );
      }

      this.direction = direction;
   }

   public Direction getDirection()
   {
      return direction;
   }

   public double toDouble()
   {
      if( getDirection() == null )
      {
         throw new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );
      }

      final double decimal = getDegrees() + (getMinutes() / 60.0d) + (getSeconds() / 3600.0d);
      return getDirection() == Direction.EAST ? decimal : -decimal;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = super.hashCode();

      result = prime * result + (direction == null ? 0 : direction.hashCode());

      return result;
   }

   @Override
   public boolean equals( final Object compareTo )
   {
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
   public String toString()
   {
      return String.format( Locale.US,
                            "%s Direction (%s), decimal (%f)",
                            super.toString(),
                            getDirection(),
                            toDouble() );
   }
}
