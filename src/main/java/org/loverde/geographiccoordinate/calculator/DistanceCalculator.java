/*
 * GeographicCoordinate
 * https://github.com/kloverde/java-GeographicCoordinate
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

package org.loverde.geographiccoordinate.calculator;

import org.loverde.geographiccoordinate.Latitude;
import org.loverde.geographiccoordinate.Longitude;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;


/**
 * <p>
 * This class calculates the distance bewteen coordinates using the Haversine formula.  Unlike
 * <a href="https://en.wikipedia.org/wiki/Vincenty's_formulae">Vincenty's formulae</a>, which
 * are designed to operate on an oblate spheroid, Haversine assumes a perfectly spherical
 * Earth, meaning Haversine is less accurate than Vincenty's formulae.  Haversine was chosen for
 * its ease of implementation, plus not having to worry about a known difficulty with Vincenty, in
 * which the iterative formulae converge very slowly for some inputs.  Thus, by using Haversine,
 * we also sidestep a potential performance issue.
 * </p>
 *
 * <p>
 * The Earth radius used in calculations is the volumetric mean radius, not the equatorial radius.  As of
 * the date this software was written, NASA's figure for the volumetric mean radius was 6371.008 km.
 * </p>
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
 * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
 * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
 * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
 * </strong></p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">https://en.wikipedia.org/wiki/Haversine_formula</a>
 * @see <a href="http://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html">NASA's Earth radius figures</a>
 */
public class DistanceCalculator {

   /**
    * Units of distance - use this with the {@code distance} method in this class.
    */
   public enum Unit {
      // Members are initialized with a conversion factor expressed in terms of 1 kilometer.

      CENTIMETERS( 1.0d / 100000.0d ),

      INCHES( 1.0d / 39370.1d ),

      /**
       * This is the international foot.  For those in the U.S., yes, that is the
       * foot you are accustomed to (12 inches = 1 ft).
       */
      FEET( 1000.0d / .3048d ),

      KILOMETERS( 1 ),

      METERS( 1000 ),

      MILES( 1.0d / 1.609344d ),

      /**
       * This is the <strong>international</strong> nautical mile.  It's not to be confused with:
       * <ol>
       *    <li>The U.S. nautical mile, which was abandoned in 1954</li>
       *    <li>The Imperial (UK) nautical mile (also known as the Admiralty mile), which was abandoned in 1970</li>
       * </ol>
       *
       * @see <a href="https://en.wikipedia.org/wiki/Nautical_mile">https://en.wikipedia.org/wiki/Nautical_mile</a>
       */
      NAUTICAL_MILES( 1.0d / 1.852d ),

      /**
       * <p>
       * For those of you living in the U.S., the U.S. Survey Foot is NOT the foot you think of
       * when you think of feet.  That is the {@link Unit#FEET international foot}.  The survey
       * foot is used in geodetic surveys.  As defined by the National Bureau of Standards in 1959:
       * </p>
       *
       * <p>
       * "Any data expressed in feet derived from and published as a result of geodetic surveys
       * within the United States will continue to bear the following relationship as defined
       * in 1893:  1 foot = 1200/3937 meter"
       * </p>
       *
       * @see <a href="http://www.ngs.noaa.gov/PUBS_LIB/FedRegister/FRdoc59-5442.pdf">http://www.ngs.noaa.gov/PUBS_LIB/FedRegister/FRdoc59-5442.pdf</a>
       */
      US_SURVEY_FEET( 1000.0d / (1200/3937.0d) ),

      YARDS( 1000.0d / .9144d );

      private double perKilometer;

      private Unit( final double perKilometer ) {
         this.perKilometer = perKilometer;
      }
   }

   /** http://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html */
   private static final double EARTH_RADIUS_KILOMETERS = 6371.008;


   /**
    * <p>
    * Gets the total distance between an unlimited number of {@linkplain Point}s.  For example, if the
    * distance from point A to point B is 3, and the distance from point B to point C is 2, the total
    * distance traveled will be (3 + 2) = 5.  Just pass {@code Point}s in the order in which they're
    * visited.
    * </p>
    *
    * <p><strong>
    * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN
    * UNDERSTANDING OF, GEODESY, AND MERELY IMPLEMENTED FORMULAS FOUND ONLINE.
    * DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME
    * TO READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
    * </strong></p>
    *
    * @param unit   The unit of distance
    *
    * @param points A vararg of {@linkplain Point}s arranged in the order in which they are visited.
    *               You must provide at least 2, otherwise a {@linkplain GeographicCoordinateException}
    *               will be thrown.
    *
    * @return The total distance traveled, expressed in terms of {@code unit}
    */
   public static double distance( final Unit unit, final Point ... points ) {
      if( unit == null ) throw new GeographicCoordinateException( "Unit is null" );
      if( points == null ) throw new GeographicCoordinateException( "Points are null" );
      if( points.length < 2 ) throw new GeographicCoordinateException( "Need to provide at least 2 points" );

      double distance = 0;
      Point previous = points[0];

      for( int i = 1; i < points.length; i++ ) {
         final Point current = points[i];

         if( previous == null ) throw new GeographicCoordinateException( "points " + (i - 1) + " is null" );
         if( current == null ) throw new GeographicCoordinateException( "points " + i + " is null" );

         final Latitude latitude1 = previous.getLatitude(),
                        latitude2 = current.getLatitude();

         final Longitude longitude1 = previous.getLongitude(),
                         longitude2 = current.getLongitude();

         if( latitude1 == null ) throw new GeographicCoordinateException( "Latitude 1 is null" );
         if( latitude2 == null ) throw new GeographicCoordinateException( "Latitude 2 is null" );
         if( longitude1 == null ) throw new GeographicCoordinateException( "Longitude 1 is null" );
         if( longitude2 == null ) throw new GeographicCoordinateException( "Longitude 2 is null" );

         final double lat1 = latitude1.toRadians(),
                      lat2 = latitude2.toRadians(),
                      lon1 = longitude1.toRadians(),
                      lon2 = longitude2.toRadians(),
                      deltaLat = lat2 - lat1,
                      deltaLon = lon2 - lon1;

         final double d = (2.0d * EARTH_RADIUS_KILOMETERS) * Math.asin( Math.sqrt( Math.pow(Math.sin(deltaLat / 2.0d), 2.0d)
                        + ( Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2.0d), 2.0d) ) ) );

         distance += d * unit.perKilometer;
         previous = current;
      }

      return distance;
   }
}
