/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


/**
 * Lines of longitude run parallel to the Prime Meridian (perpendicular to the Equator).
 * Longitude denotes whether a location is east or west of the Prime Meridian.
 * The Prime Meridian is located at longitude 0.
 */
public class Longitude extends GeographicCoordinateImpl {

   public static enum Direction {
      EAST( "E" ),
      WEST( "W" );

      private String abbreviation;

      private Direction( final String abbr ) {
         this.abbreviation = abbr;
      }

      public String getAbbreviation() {
         return abbreviation;
      }
   };

   private Direction direction;

   public static final int MAX_VALUE = 180;


   public Longitude() {
      super();
   }

   /**
    * Creates a new longitude object
    *
    * @param longitude - A signed value.  Positive values are east; negative values are west.  Note that a value
    *                    of 0.0 is the Prime Meridian, which is neither east nor west.  It is equally valid to
    *                    say 0°E or 0°W; they are the same.  For this library's purposes, if you supply a value
    *                    of 0.0, the direction will be initialized to {@link Direction#EAST}, but you should
    *                    ignore the direction.
    *
    * @throws GeographicCoordinateException If the supplied value falls outside of +/- {@linkplain Longitude#MAX_VALUE}
    */
   public Longitude( final double longitude ) throws GeographicCoordinateException {
      super( longitude );
      setDirection( longitude >= 0.0d ? Direction.EAST : Direction.WEST );
   }

   /**
    * Creates a new longitude object
    *
    * @param degrees - Accepted range [0-180]
    * @param minutes - Accepted range [0-59] unless {@code degrees} is 180, in which case {@code minutes} must be 0
    * @param seconds - Accepted range [0-59.9999999999999] unless {@code degrees} is 180, in which case {@code seconds} must be 0
    * @param dir
    *
    * @throws GeographicCoordinateException If any arguments fall outside their accepted ranges
    */
   public Longitude( final int degrees, final int minutes, final double seconds, final Longitude.Direction dir ) throws GeographicCoordinateException {
      super( degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Longitude.Direction}
    */
   public void setDirection( final Longitude.Direction direction ) {
      if( direction == null )  throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      this.direction = direction;
   }

   /**
    * Indicates whether your location is east or west of the Prime Meridian.  The Prime Meridian (0.0) is neither east nor west.
    * If you want to know if you're on the Prime Meridian, check the value returned by {@link #toDouble()} and ignore the value
    * returned by this method.
    *
    * @return East/west indicator
    */
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

   /**
    * Compares this {@code Longitude} to another object
    *
    * @return {@code true} if equal, {@code false} if not
    */
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

   /**
    * Returns a degree-minute-seconds formatted longitude.  For example:  30°40'50.123"E
    */
   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%s%s",
                            super.toString(),
                            getDirection().getAbbreviation() );
   }
}
