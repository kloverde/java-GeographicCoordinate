/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 *
 * https://github.com/kloverde/GeographicCoordinate
 */

package org.loverde.geographiccoordinate;


/**
 * This class is a thin wrapper of a {@linkplain Latitude} and {@linkplain Longitude}.
 * It adds a {@link Point#Point(Latitude, Longitude, String) name} field.
 */
public class Point {

   private Latitude latitude;
   private Longitude longitude;

   private String name;


   /**
    * Creates a new Point object
    *
    * @param latitude - {@linkplain Latitude}
    * @param longitude - {@linkplain Longitude}
    *
    * @throws GeographicCoordinateException If either parameter is null
    */
   public Point( final Latitude latitude, final Longitude longitude ) {
      try {
         setLatitude( latitude );
         setLongitude( longitude );
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }
   }

   /**
    * Creates a new Point object
    *
    * @param latitude - {@linkplain Latitude}
    * @param longitude - {@linkplain Longitude}
    * @param name - Use for identification, such as displaying a caption on a map.  Null is permitted.
    *
    * @throws GeographicCoordinateException If any parameter is null
    */
   public Point( final Latitude latitude, final Longitude longitude, final String name ) {
      this( latitude, longitude );

      try {
         setName( name );
      } catch( final Exception e ) {
         throw new GeographicCoordinateException( e );
      }
   }

   public Latitude getLatitude() {
      return latitude;
   }

   private void setLatitude( final Latitude latitude ) {
      if( latitude == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LATITUDE_NULL );

      this.latitude = latitude;
   }

   public Longitude getLongitude() {
      return longitude;
   }

   private void setLongitude( final Longitude longitude ) {
      if( longitude == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.LONGITUDE_NULL );

      this.longitude = longitude;
   }

   public String getName() {
      return name;
   }

   /**
    * @param name - Use for identification, such as displaying a label on a map
    */
   private void setName( final String name ) {
      if( name == null ) throw new IllegalArgumentException( GeographicCoordinateException.Messages.NAME_NULL );

      this.name = name;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
      result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
   }

   /**
    * Compares this object to an {@code instanceof} Point.  All fields are compared.
    *
    * @param obj - The object to compare to
    *
    * @return {@code true} if equal, {@code false} if not
    */
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
      } else if( !getLongitude().equals(other.getLongitude()) ) {
         return false;
      }

      if( getName() == null ) {
         if( other.getName() != null ) {
            return false;
         }
      } else if( !getName().equals(other.getName()) ) {
         return false;
      }

      return true;
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder( 80 );

      if( getName() != null ) {
         sb.append( getName() );
         sb.append( " " );
      }

      sb.append( "{" );

      sb.append( getLatitude() );
      sb.append( " , " );
      sb.append( getLongitude() );

      sb.append( "}" );

      return sb.toString();
   }
}
