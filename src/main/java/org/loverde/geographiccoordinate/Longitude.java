/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate;


/**
 * Lines of longitude run parallel to the Prime Meridian (perpendicular to the Equator).
 * Longitude denotes whether a location is east or west of the Prime Meridian.
 * The Prime Meridian is located at longitude 0.
 */
public class Longitude extends AbstractGeographicCoordinate {

   /**
    * Indicates whether a location is north or south of the Prime Meridian, or on the Prime Meridian
    */
   public static enum Direction implements AbstractDirection {
      /**
       * Indicates that the location is east of the Prime Meridian
       */
      EAST( "E" ),

      /**
       * Indicates that the location is west of the Prime Meridian
       */
      WEST( "W" ),

      /**
       * Indicates that the location is on the Prime Meridian
       * (neither east not west:  the longitude is exactly 0.0).
       */
      NEITHER( "" );

      private String abbreviation;

      private Direction( final String abbr ) {
         this.abbreviation = abbr;
      }

      @Override
      public String getAbbreviation() {
         return abbreviation;
      }
   };

   private Longitude.Direction direction;

   /**
    * When expressed as a floating-point number, valid longitudes sit in a
    * range of +/- 180.0.  When expressed as degrees/minutes/seconds, the
    * valid range for degrees is 0-180, with minutes and seconds equal to
    * 0 when degrees is 180.
    */
   public static final int MAX_VALUE = 180;


   /**
    * Creates a new longitude object
    *
    * @param longitude - A signed value.  Positive values are east; negative values are west.  Note that a value
    *                    of 0.0 is the Prime Meridian, which is neither east nor west.  If you supply a value of
    *                    0.0, the {@code direction} will be initialized to {@link Direction#NEITHER}.
    *
    * @throws GeographicCoordinateException If the supplied value falls outside of +/- {@linkplain Longitude#MAX_VALUE}
    */
   public Longitude( final double longitude ) {
      super( longitude );

      try {
         if( longitude == 0.0d ) {
            setDirection( Direction.NEITHER );
         } else {
            setDirection( longitude > 0.0d ? Direction.EAST : Direction.WEST );
         }
      } catch( final IllegalArgumentException e ) {
         throw new GeographicCoordinateException( e.getMessage(), e );
      }
   }

   /**
    * Creates a new longitude object
    *
    * @param degrees - Accepted range [0-180]
    * @param minutes - Accepted range [0-59] unless {@code degrees} is 180, in which case {@code minutes} must be 0
    * @param seconds - Accepted range [0-59.9999999999999] unless {@code degrees} is 180, in which case {@code seconds} must be 0
    * @param dir
    *
    * @throws GeographicCoordinateException If any arguments fall outside their accepted ranges, or if degrees/minutes/seconds
    *                                       are all 0 with a {@code direction} other than {@linkplain Direction#NEITHER}
    */
   public Longitude( final int degrees, final int minutes, final double seconds, final Longitude.Direction dir ) {
      super( degrees, minutes, seconds );

      try {
         setDirection( dir );
      } catch( final IllegalArgumentException e ) {
         throw new GeographicCoordinateException( e.getMessage(), e );
      }
   }

   /**
    * @param direction - A member of {@code Longitude.Direction}
    *
    * @throws IllegalArgumentException In the following situations:
    * <ul>
    *    <li>{@code direction} is null</li>
    *    <li>{@code direction} is {@linkplain Direction#NEITHER} but the latitude is not 0.0
    * </ul>
    */
   private void setDirection( final Longitude.Direction direction ) {
      if( direction == null )  throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      if( direction == Direction.NEITHER && !(getDegrees() == 0 && getMinutes() == 0 && getSeconds() == 0.0d) ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_INVALID );

      this.direction = direction;
   }

   /**
    * Indicates whether the location is east or west of the Prime Meridian, or on the Prime Meridian.
    * When on the Prime Meridian, the direction is {@linkplain Direction#NEITHER}.
    *
    * @return Orientation with respect to the Prime Meridian
    */
   @Override
   public Longitude.Direction getDirection() {
      return direction;
   }

   @Override
   public double toDouble() {
      // Sanity check for an impossible scenario
      if( getDirection() == null ) {
         final IllegalStateException stateEx = new IllegalStateException( GeographicCoordinateException.Messages.DIRECTION_NULL );
         throw new GeographicCoordinateException( stateEx.getMessage(), stateEx );
      }

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
    * Compares this object to an {@code instanceof} Longitude.  All Longitude fields are compared.
    *
    * @param compareTo - The object to compare to
    *
    * @return {@code true} if equal, {@code false} if not
    */
   @Override
   public boolean equals( final Object compareTo ) {
      final Longitude other;

      if( this == compareTo ) return true;
      if( compareTo == null ) return false;

      if( !(compareTo instanceof Longitude) ) return false;

      other = (Longitude) compareTo;

      if( getDirection() == null && other.getDirection() != null ) return false;
      if( getDirection() != null && other.getDirection() == null ) return false;
      if( !getDirection().equals(other.getDirection()) ) return false;

      return super.equals( other );
   }
}
