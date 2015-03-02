/*
 * Copyright (C) 2013 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;

import java.util.Locale;


public abstract class GeographicCoordinate {
   protected static enum Type {
      LATITUDE,
      LONGITUDE
   }

   private Type type;

   private int degrees;
   private int minutes;
   private double seconds;

   private int maxValueDegrees;

   private static final int MAX_VALUE_MINUTES = 59;
   private static final double MAX_VALUE_SECONDS = 59.9999999999999d;

   public GeographicCoordinate( final Type type ) {
      setType( type );
      setMaxValueDegrees( type == Type.LATITUDE ? Latitude.MAX_VALUE : Longitude.MAX_VALUE );
   }

   public GeographicCoordinate( final Type type, final int degrees, final int minutes, final double seconds )
   throws GeographicCoordinateException {
      this( type );

      setDegrees( degrees );
      setMinutes( minutes );
      setSeconds( seconds );
   }

   private void setType( final Type t ) {
      if( t == null ) {
         throw new IllegalArgumentException( GeographicCoordinateException.Messages.COORDINATE_TYPE_NULL );
      }

      type = t;
   }

   private Type getType() {
      return type;
   }

   private void setMaxValueDegrees( final int maxValHrs ) {
      maxValueDegrees = maxValHrs;
   }

   private int getMaxValueDegrees() {
      return maxValueDegrees;
   }

   public void setDegrees( final int deg )
   throws GeographicCoordinateException {
      if( deg < 0 || deg > getMaxValueDegrees() ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_DEGREES_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_DEGREES_RANGE );
      }

      if( deg == getMaxValueDegrees() && (getMinutes() != 0 || getSeconds() != 0) ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      degrees = deg;
   }

   public int getDegrees() {
      return degrees;
   }

   public void setMinutes( final int mins )
   throws GeographicCoordinateException {
      if( mins < 0 || mins > MAX_VALUE_MINUTES ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && mins != 0 ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.minutes = mins;
   }

   public int getMinutes() {
      return minutes;
   }

   public void setSeconds( final double seconds )
   throws GeographicCoordinateException {
      if( seconds < 0.0d || seconds > MAX_VALUE_SECONDS ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_SECONDS_RANGE
                                                : GeographicCoordinateException.Messages.LONGITUDE_SECONDS_RANGE );
      }

      if( getDegrees() == getMaxValueDegrees() && seconds != 0.0d ) {
         throw new GeographicCoordinateException( getType().equals(Type.LATITUDE)
                                                ? GeographicCoordinateException.Messages.LATITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO
                                                : GeographicCoordinateException.Messages.LONGITUDE_MINUTES_AND_SECONDS_MUST_BE_ZERO );
      }

      this.seconds = seconds;
   }

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

      result = prime * result + (type == null ? 0 : type.hashCode());

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
   public boolean equals( final Object compareTo )
   throws IllegalArgumentException {
      final GeographicCoordinate other;

      if( this == compareTo ) return true;

      if( !(compareTo instanceof GeographicCoordinate) ) return false;

      other = (GeographicCoordinate) compareTo;

      if( getType() != null && other.getType() == null ) return false;
      if( getType() == null && other.getType() != null ) return false;
      if( !getType().equals(other.getType()) ) return false;

      if( getDegrees() != other.getDegrees() ) return false;

      if( getMaxValueDegrees() != other.getMaxValueDegrees() ) return false;

      if( getMinutes() != other.getMinutes() ) return false;

      if( getSeconds() != other.getSeconds() ) return false;

      return true;
   }

   @Override
   public String toString() {
      return String.format( Locale.US,
                            "Degrees (%d) Minutes (%d) Seconds (%f)",
                            getDegrees(),
                            getMinutes(),
                            getSeconds() );
   }
}
