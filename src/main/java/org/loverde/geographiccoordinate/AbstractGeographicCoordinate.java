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

   private static final DecimalFormat decimalFormat;

   static {
      decimalFormat = new DecimalFormat( "0", DecimalFormatSymbols.getInstance(Locale.ENGLISH) );
      decimalFormat.setMaximumFractionDigits( 15 );
   }

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

   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%d°%d'%s\"",
                            getDegrees(),
                            getMinutes(),
                            decimalFormat.format(getSeconds()) );
   }

   private void setMaxValueDegrees( final int max ) {
      maxValueDegrees = max;
   }

   private int getMaxValueDegrees() {
      return maxValueDegrees;
   }

   private void setDegrees( final int degrees ) {
      if( degrees < 0 || degrees > getMaxValueDegrees() ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_DEGREES_RANGE
                                           : GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE );
      }

      if( degrees == getMaxValueDegrees() && (getMinutes() != 0 || getSeconds() != 0) ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                           : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.degrees = degrees;
   }

   private void setMinutes( final int mins ) {
      if( mins < 0 || mins > MAX_VALUE_MINUTES ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_RANGE
                                           : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && mins != 0 ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                           : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.minutes = mins;
   }

   private void setSeconds( final double seconds ) {
      if( seconds < 0.0d || seconds > MAX_VALUE_SECONDS ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_SECONDS_RANGE
                                           : GeographicCoordinateException.Messages.LONGITUDE_SECONDS_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && seconds != 0.0d ) {
         throw new IllegalArgumentException( this instanceof Latitude
                                           ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                           : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.seconds = seconds;
   }
}
