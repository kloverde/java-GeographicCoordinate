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
public class Latitude extends AbstractGeographicCoordinate {

   /**
    * Indicates whether a location is north or south of the Equator, or on the Equator
    */
   public static enum Direction {
      /**
       * Indicates that the location is north of the Equator
       */
      NORTH( "N" ),

      /**
       * Indicates that the location is south of the Equator
       */
      SOUTH( "S" ),

      /**
       * Indicates that the location is on the Equator
       * (neither north nor south:  the latitude is exactly 0.0).
       */
      NEITHER( "NEITHER" );

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


   /**
    * Creates a new latitude object
    *
    * @param latitude - A signed value.  Positive values are north; negative values are south.  Note that a value
    *                   of 0.0 is the Equator, which is neither north nor south.  If you supply a value of 0.0,
    *                   the {@code direction} will be initialized to {@link Direction#NEITHER}.
    *
    * @throws GeographicCoordinateException If the supplied value falls outside of +/- {@linkplain Latitude#MAX_VALUE}
    */
   public Latitude( final double latitude ) {
      super( latitude );

      try {
         if( latitude == 0.0d ) {
            setDirection( Direction.NEITHER );
         } else {
            setDirection( latitude > 0.0d ? Direction.NORTH : Direction.SOUTH );
         }
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }
   }

   /**
    * Creates a new latitude object
    *
    * @param degrees   - Accepted range [0-90]
    * @param minutes   - Accepted range [0-59] unless {@code degrees} is 90, in which case {@code minutes} must be 0
    * @param seconds   - Accepted range [0-59.9999999999999] unless {@code degrees} is 90, in which case {@code seconds} must be 0
    * @param direction - A {@linkplain Latitude.Direction}
    *
    * @throws GeographicCoordinateException If any arguments fall outside their accepted ranges, or if degrees/minutes/seconds
    *                                       are all 0 with a {@code direction} other than {@linkplain Direction#NEITHER}
    */
   public Latitude( final int degrees, final int minutes, final double seconds, final Latitude.Direction direction ) {
      super( degrees, minutes, seconds );

      try {
         setDirection( direction );
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }
   }

   /**
    * @param direction - A member of {@linkplain Latitude.Direction}
    *
    * @throws IllegalArgumentException In the following situations:
    * <ul>
    *    <li>{@code direction} is null</li>
    *    <li>{@code direction} is {@linkplain Direction#NEITHER} but the latitude is not 0.0
    * </ul>
    */
   private void setDirection( final Latitude.Direction direction ) {
      if( direction == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      if( direction == Direction.NEITHER && !(getDegrees() == 0 && getMinutes() == 0 && getSeconds() == 0.0d) ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.DIRECTION_INVALID );

      this.direction = direction;
   }

   /**
    * Indicates whether the location is north or south of the Equator, or on the Equator.
    * When on the Equator, the direction is {@linkplain Direction#NEITHER}.
    *
    * @return Orientation with respect to the Equator
    */
   public Latitude.Direction getDirection() {
      return direction;
   }

   @Override
   public double toDouble() {
      // Sanity check for an impossible scenario
      if( getDirection() == null ) throw new GeographicCoordinateException( new IllegalStateException(GeographicCoordinateException.Messages.DIRECTION_NULL) );

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
    * Compares this object to an {@code instanceof} Latitude.  All Latitude fields are compared.
    *
    * @param compareTo - The object to compare to
    *
    * @return {@code true} if equal, {@code false} if not
    */
   @Override
   public boolean equals( final Object compareTo ) {
      final Latitude other;

      if( this == compareTo ) return true;
      if( compareTo == null ) return false;

      if( !(compareTo instanceof Latitude) ) return false;

      other = (Latitude) compareTo;

      if( getDirection() == null && other.getDirection() != null ) return false;
      if( getDirection() != null && other.getDirection() == null ) return false;
      if( !getDirection().equals(other.getDirection()) ) return false;

      return super.equals( other );
   }

   /**
    * Returns a degrees-minutes-seconds formatted latitude for the default locale.
    * For example,
    *
    * <ul>
    *    <li>In the United States:  30°60'40.912"N</li>
    *    <li>In France:  30°60'40,912"N</li>
    * </ul>
    *
    * @see #toString(Locale)
    */
   @Override
   public String toString() {
      return toString( Locale.getDefault() );
   }

   /**
    * Returns a degrees-minutes-seconds formatted latitude for the specified locale.
    * For example,
    *
    * <ul>
    *    <li>For {@linkplain Locale#US}:  30°60'40.912"N</li>
    *    <li>For {@linkplain Locale#FRANCE}:  30°60'40,912"N</li>
    * </ul>
    *
    * @param locale - The locale to localize the output for
    *
    * @see #toString()
    */
   @Override
   public String toString( final Locale locale ) {
      String str = null;

      try {
         str = super.toString( locale );
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }

      str = String.format( locale,
                           "%s%s",
                           str,
                           getDirection() != Direction.NEITHER ? getDirection().getAbbreviation() : "" );

      return str;
   }
}
