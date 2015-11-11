/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


/**
 * This class is a thin wrapper of a {@linkplain Latitude} and {@linkplain Longitude}
 */
public class Point {

   private Latitude latitude;
   private Longitude longitude;


   public Point( final Latitude latitude, final Longitude longitude ) {
      setLatitude( latitude );
      setLongitude( longitude );
   }

   public Latitude getLatitude() {
      return latitude;
   }

   public void setLatitude( final Latitude latitude ) {
      this.latitude = latitude;
   }

   public Longitude getLongitude() {
      return longitude;
   }

   public void setLongitude( final Longitude longitude ) {
      this.longitude = longitude;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
      result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
      return result;
   }

   @Override
   public boolean equals( final Object obj ) {
      if( this == obj ) return true;
      if( obj == null ) return false;
      if( !(obj instanceof Point) ) return false;

      final Point other = (Point) obj;

      if( getLatitude() == null ) {
         if( other.getLatitude() != null ) {
            return false;
         }
      } else if( !getLatitude().equals(other.getLatitude()) ) {
         return false;
      }

      if( getLongitude() == null ) {
         if( other.getLongitude() != null ) {
            return false;
         }
      } else if( !getLongitude().equals( other.getLongitude()) ) {
         return false;
      }

      return true;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder( 100 );

      sb.append( "[" );
      sb.append( getLatitude() );
      sb.append( "]" );

      sb.append( ", [" );
      sb.append( getLongitude() );
      sb.append( "]" );

      return sb.toString();
   }
}
