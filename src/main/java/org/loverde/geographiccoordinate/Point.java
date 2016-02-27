/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


/**
 * This class is a thin wrapper of a {@linkplain Latitude} and {@linkplain Longitude}.
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
    */
   public Point( final Latitude latitude, final Longitude longitude ) {
      setLatitude( latitude );
      setLongitude( longitude );
   }

   /**
    * Creates a new Point object
    *
    * @param latitude - {@linkplain Latitude}
    * @param longitude - {@linkplain Longitude}
    * @param name - Use for identification, such as displaying a label on a map
    */
   public Point( final Latitude latitude, final Longitude longitude, final String name ) {
      this( latitude, longitude );
      setName( name );
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

   public String getName() {
      return name;
   }

   /**
    * @param name - Use for identification, such as displaying a label on a map
    */
   public void setName( final String name ) {
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
