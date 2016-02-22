/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public abstract class GeographicCoordinateImpl implements GeographicCoordinate {

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

   public GeographicCoordinateImpl() {
      setMaxValueDegrees( this instanceof Latitude ? Latitude.MAX_VALUE : Longitude.MAX_VALUE );
   }

   public GeographicCoordinateImpl( final double value ) throws GeographicCoordinateException {
      this( (int) Math.abs(value),
            (int) ((Math.abs(value) - (int)Math.abs(value)) * 60.0d),
            (((Math.abs(value) - (int)Math.abs(value)) * 60.0d) % 1.0d) * 60.0d );
   }

   public GeographicCoordinateImpl( final int degrees, final int minutes, final double seconds ) throws GeographicCoordinateException {
      this();

      setDegrees( degrees );
      setMinutes( minutes );
      setSeconds( seconds );
   }

   private void setMaxValueDegrees( final int max ) {
      maxValueDegrees = max;
   }

   private int getMaxValueDegrees() {
      return maxValueDegrees;
   }

   @Override
   public void setDegrees( final int degrees ) throws GeographicCoordinateException {
      if( degrees < 0 || degrees > getMaxValueDegrees() ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_DEGREES_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE );
      }

      if( degrees == getMaxValueDegrees() && (getMinutes() != 0 || getSeconds() != 0) ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.degrees = degrees;
   }

   @Override
   public int getDegrees() {
      return degrees;
   }

   @Override
   public void setMinutes( final int mins ) throws GeographicCoordinateException {
      if( mins < 0 || mins > MAX_VALUE_MINUTES ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && mins != 0 ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.minutes = mins;
   }

   @Override
   public int getMinutes() {
      return minutes;
   }

   @Override
   public void setSeconds( final double seconds ) throws GeographicCoordinateException {
      if( seconds < 0.0d || seconds > MAX_VALUE_SECONDS ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_SECONDS_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_SECONDS_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && seconds != 0.0d ) {
         throw new GeographicCoordinateException( this instanceof Latitude
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.seconds = seconds;
   }

   @Override
   public double getSeconds() {
      return seconds;
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
    * Compares this {@code GeographicCoordinate} to another
    *
    * @param compareTo The {@code GeographicCoordinate} to compare to
    *
    * @return true if equivalent, false if not
    */
   @Override
   public boolean equals( final Object compareTo ) {
      final GeographicCoordinateImpl other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof GeographicCoordinate) ) return false;

      other = (GeographicCoordinateImpl) compareTo;

      if( getDegrees() != other.getDegrees() ) return false;

      if( getMaxValueDegrees() != other.getMaxValueDegrees() ) return false;

      if( getMinutes() != other.getMinutes() ) return false;

      if( getSeconds() != other.getSeconds() ) return false;

      return true;
   }

   @Override
   public double toRadians() {
      return Math.toRadians( toDouble() );
   }

   /**
    * Don't use this directly -- this string does not include the {@code direction}.  Use {@linkplain Latitude#toString()} or {@linkplain Longitude#toString()}.
    */
   @Override
   public String toString() {
      return String.format( Locale.US,
                            "%d°%d'%s\"",
                            getDegrees(),
                            getMinutes(),
                            decimalFormat.format(getSeconds()) );
   }
}
