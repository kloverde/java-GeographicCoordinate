package org.loverde.geographiccoordinate;


public interface GeographicCoordinate {

   enum Type {
      LATITUDE,
      LONGITUDE
   }

   public int getDegrees();
   public void setDegrees( int degrees ) throws GeographicCoordinateException;

   public int getMinutes();
   public void setMinutes( int minutes ) throws GeographicCoordinateException;

   public double getSeconds();
   public void setSeconds( double seconds ) throws GeographicCoordinateException;

   public double toDouble();
   public double toRadians();
}
