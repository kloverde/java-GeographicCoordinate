/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


/**
 * Lines of latitude run parallel to the Equator (perpendicular to the Prime Meridian).
 * Latitude denotes whether a location is north or south of the Equator.
 * The Equator is located at latitude 0.
 */
public class Latitude extends GeographicCoordinateImpl {

   public static enum Direction {
      NORTH( "N" ),
      SOUTH( "S" );

      private String abbreviation;

      private Direction( final String abbr ) {
         this.abbreviation = abbr;
      }

      public String getAbbreviation() {
         return abbreviation;
      }
   };

   private Latitude.Direction direction;

   public static final int MAX_VALUE = 90;


   public Latitude() {
      super();
   }

   /**
    * Creates a new latitude object
    *
    * @param latitude - A signed value.  Positive values are north; negative values are south.  Note that a value
    *                   of 0.0 is the Equator, which is neither north nor south.  It is equally valid to say 0°N
    *                   or 0°S; they are the same.  For this library's purposes, if you supply a value of 0.0,
    *                   the direction will be initialized to {@link Direction#NORTH}, but you should ignore
    *                   the direction.
    *
    * @throws GeographicCoordinateException If the supplied value falls outside of +/- {@linkplain Latitude#MAX_VALUE}
    */
   public Latitude( final double latitude ) throws GeographicCoordinateException {
      super( latitude );
      setDirection( latitude >= 0.0d ? Direction.NORTH : Direction.SOUTH );
   }

   /**
    * Creates a new latitude object
    *
    * @param degrees - Accepted range [0-90]
    * @param minutes - Accepted range [0-59] unless {@code degrees} is 90, in which case {@code minutes} must be 0
    * @param seconds - Accepted range [0-59.9999999999999] unless {@code degrees} is 90, in which case {@code seconds} must be 0
    * @param dir
    *
    * @throws GeographicCoordinateException If any arguments fall outside their accepted ranges
    */
   public Latitude( final int degrees, final int minutes, final double seconds, final Latitude.Direction dir ) throws GeographicCoordinateException {
      super( degrees, minutes, seconds );
      setDirection( dir );
   }

   /**
    * @param direction Must be a member of {@code Latitude.Direction}
    */
   public void setDirection( final Latitude.Direction direction ) {
      if( direction == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      this.direction = direction;
   }

   /**
    * Indicates whether your location is north or south of the Equator.  The Equator (0.0) is neither north nor south.
    * If you want to know if you're on the Equator, check the value returned by {@link #toDouble()} and ignore the
    * value returned by this method.
    *
    * @return North/south indicator
    */
   public Latitude.Direction getDirection() {
      return direction;
   }

   @Override
   public double toDouble() {
      if( getDirection() == null ) throw new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );

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

   /**
    * Compares this {@code Latitude} to another object
    *
    * @return {@code true} if equal, {@code false} if not
    */
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

   /**
    * Returns a degree-minute-seconds formatted latitude.  For example:  30°60'40.912"N
    */
   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%s%s",
                            super.toString(),
                            getDirection().getAbbreviation() );
   }
}
