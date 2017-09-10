/*
 * GeographicCoordinate
 * https://github.com/kloverde/GeographicCoordinate
 *
 * Copyright (c) 2013 Kurtis LoVerde
 * All rights reserved
 *
 * Donations:  https://paypal.me/KurtisLoVerde/5
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *     2. Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *     3. Neither the name of the copyright holder nor the names of its
 *        contributors may be used to endorse or promote products derived from
 *        this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.loverde.geographiccoordinate;

import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.AbstractDirection;
import org.loverde.geographiccoordinate.internal.AbstractGeographicCoordinate;


/**
 * Lines of latitude run parallel to the Equator (perpendicular to the Prime Meridian).
 * Latitude denotes whether a location is north or south of the Equator.
 * The Equator is located at latitude 0.
 */
public class Latitude extends AbstractGeographicCoordinate {

   /**
    * Indicates whether a location is north or south of the Equator, or on the Equator
    */
   public static enum Direction implements AbstractDirection {
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

   private Latitude.Direction direction;

   /**
    * When expressed as a floating-point number, valid latitudes sit in a
    * range of +/- 90.0.  When expressed as degrees/minutes/seconds, the
    * valid range for degrees is 0-90, with minutes and seconds equal to
    * 0 when degrees is 90.
    */
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

      if( latitude == 0.0d ) {
         setDirection( Direction.NEITHER );
      } else {
         setDirection( latitude > 0.0d ? Direction.NORTH : Direction.SOUTH );
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

      setDirection( direction );
   }

   /**
    * @param direction - A member of {@linkplain Latitude.Direction}
    *
    * @throws GeographicCoordinateException In the following situations:
    * <ul>
    *    <li>{@code direction} is null</li>
    *    <li>{@code direction} is {@linkplain Direction#NEITHER} but the latitude is not 0.0
    * </ul>
    */
   private void setDirection( final Latitude.Direction direction ) {
      if( direction == null ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.DIRECTION_NULL );

      if( direction == Direction.NEITHER && !(getDegrees() == 0 && getMinutes() == 0 && getSeconds() == 0.0d) ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.DIRECTION_INVALID );

      this.direction = direction;
   }

   /**
    * Indicates whether the location is north or south of the Equator, or on the Equator.
    * When on the Equator, the direction is {@linkplain Direction#NEITHER}.
    *
    * @return Orientation with respect to the Equator
    */
   @Override
   public Latitude.Direction getDirection() {
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
}
