/*
 * Copyright (C) 2015 Kurtis LoVerde
 * All rights reserved
 */

package org.loverde.geographiccoordinate;


public interface GeographicCoordinate {

   public int getDegrees();
   public int getMinutes();
   public double getSeconds();
   public IDirection getDirection();

   public double toDouble();
   public double toRadians();
}
