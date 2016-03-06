/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


/**
 * Attempting to extend this class will result in a runtime exception during
 * instantiation.  This class exists to share internal implemenation logic
 * between {@linkplain Latitude} and {@linkplain Longitude} - the only
 * coordinate classes you should be working with.
 */
public abstract class AbstractGeographicCoordinate implements GeographicCoordinate {

   private int degrees;
   private int minutes;
   private double seconds;

   private int maxValueDegrees;

   private static final int MAX_VALUE_MINUTES = 59;
   private static final double MAX_VALUE_SECONDS = 59.9999999999999d;

   private static final int DECIMAL_FORMAT_MAX_FACTION_DIGITS = 15;


   /**
    * @throws GeographicCoordinateException If you extend this class yourself
    */
   public AbstractGeographicCoordinate( final int degrees, final int minutes, final double seconds ) {
      if( !(this instanceof Latitude) && !(this instanceof Longitude) ) throw new GeographicCoordinateException( GeographicCoordinateException.Messages.DISALLOWED_EXTENDS );

      setMaxValueDegrees( this instanceof Latitude ? Latitude.MAX_VALUE : Longitude.MAX_VALUE );

      try {
         setDegrees( degrees );
         setMinutes( minutes );
         setSeconds( seconds );
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }
   }

   /**
    * @throws GeographicCoordinateException If you extend this class yourself
    */
   public AbstractGeographicCoordinate( final double value ) {
      this( (int) Math.abs(value),
            (int) ((Math.abs(value) - (int)Math.abs(value)) * 60.0d),
            (((Math.abs(value) - (int)Math.abs(value)) * 60.0d) % 1.0d) * 60.0d );
   }

   @Override
   public int getDegrees() {
      return degrees;
   }

   @Override
   public int getMinutes() {
      return minutes;
   }

   @Override
   public double getSeconds() {
      return seconds;
   }

   @Override
   public double toRadians() {
      return Math.toRadians( toDouble() );
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;

      result = prime * result + degrees;
      result = prime * result + minutes;

      final long temp = Double.doubleToLongBits( seconds );
      result = prime * result + (int) (temp ^ (temp >>> 32));

      return result;
   }

   /**
    * Compares this object to an {@code instanceof} AbstractGeographicCoordinate.  All fields are compared.
    *
    * @param compareTo - The object to compare to
    *
    * @return {@code true} if equivalent, {@code false} if not
    */
   @Override
   public boolean equals( final Object compareTo ) {
      final AbstractGeographicCoordinate other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof AbstractGeographicCoordinate) ) return false;

      other = (AbstractGeographicCoordinate) compareTo;

      if( getDegrees() != other.getDegrees() ) return false;

      if( getMaxValueDegrees() != other.getMaxValueDegrees() ) return false;

      if( getMinutes() != other.getMinutes() ) return false;

      if( getSeconds() != other.getSeconds() ) return false;

      return true;
   }

   /**
    * Returns a degrees-minutes-seconds formatted string for the default locale.
    * For example,
    *
    * <ul>
    *    <li>In the United States:  30°60'40.912"N</li>
    *    <li>In France:  30°60'40,912"N</li>
    * </ul>
    *
    * @throws GeographicCoordinateException If {@linkplain #getDirection()} returs null
    *
    * @see #toString(Locale)
    */
   @Override
   public String toString() {
      return toString( Locale.getDefault() );
   }

   /**
    * Returns a degrees-minutes-seconds formatted string for the specified locale.
    * For example,
    *
    * <ul>
    *    <li>For {@linkplain Locale#US}:  30°60'40.912"N</li>
    *    <li>For {@linkplain Locale#FRANCE}:  30°60'40,912"N</li>
    * </ul>
    *
    * @param locale - The locale to localize to
    *
    * @throws GeographicCoordinateException If {@code locale} is null or {@linkplain #getDirection()} returns null
    *
    * @see #toString()
    */
   public String toString( final Locale locale ) {
      final DecimalFormat fmt;
      final AbstractDirection direction = getDirection();

      String str = null;

      if( locale == null ) throw new GeographicCoordinateException( new IllegalArgumentException(GeographicCoordinateException.Messages.LOCALE_NULL) );
      if( direction == null ) throw new GeographicCoordinateException( new IllegalStateException(GeographicCoordinateException.Messages.DIRECTION_NULL) );

      fmt = new DecimalFormat( "0", DecimalFormatSymbols.getInstance(locale) );
      fmt.setMaximumFractionDigits( DECIMAL_FORMAT_MAX_FACTION_DIGITS );

      str = String.format( locale,
                           "%d°%d'%s\"%s",
                           getDegrees(),
                           getMinutes(),
                           fmt.format( getSeconds() ),
                           direction.getAbbreviation() );

      return str;
   }

   private void setMaxValueDegrees( final int max ) {
      maxValueDegrees = max;
   }

   private int getMaxValueDegrees() {
      return maxValueDegrees;
   }

   private void setDegrees( final int degrees ) {
      if( degrees < 0 || degrees > getMaxValueDegrees() ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.DEGREES_RANGE + getMaxValueDegrees() );
      }

      if( degrees == getMaxValueDegrees() && (getMinutes() != 0 || getSeconds() != 0) ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + getMaxValueDegrees() );
      }

      this.degrees = degrees;
   }

   private void setMinutes( final int mins ) {
      if( mins < 0 || mins > MAX_VALUE_MINUTES ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.MINUTES_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && mins != 0 ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + getMaxValueDegrees() );
      }

      this.minutes = mins;
   }

   private void setSeconds( final double seconds ) {
      if( seconds < 0.0d || seconds > MAX_VALUE_SECONDS ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.SECONDS_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && seconds != 0.0d ) {
         throw new IllegalArgumentException( this.getClass().getSimpleName() + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + getMaxValueDegrees() );
      }

      this.seconds = seconds;
   }
}
