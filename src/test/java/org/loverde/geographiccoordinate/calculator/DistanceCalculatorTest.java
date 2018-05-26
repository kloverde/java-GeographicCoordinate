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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loverde.geographiccoordinate.Latitude;
import org.loverde.geographiccoordinate.Longitude;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.calculator.DistanceCalculator.Unit;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class DistanceCalculatorTest {

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   private Latitude latitude1, latitude2;

   private Longitude longitude1, longitude2;

   private Point point1, point2;

   @Mock
   private Point mockPoint;

   private double fpDelta = 1E-15;


   @Before
   public void setUp() {
      latitude1 = new Latitude( 40, 42, 46, Latitude.Direction.NORTH );
      longitude1 = new Longitude(  74, 0, 21, Longitude.Direction.WEST );

      latitude2 = new Latitude( 38, 54, 17, Latitude.Direction.NORTH );
      longitude2 = new Longitude( 77, 0, 59, Longitude.Direction.WEST );

      point1 = new Point( latitude1, longitude1 );
      point2 = new Point( latitude2, longitude2 );

      MockitoAnnotations.initMocks( this );
      Mockito.when( mockPoint.getLatitude() ).thenReturn( latitude1 );
      Mockito.when( mockPoint.getLongitude() ).thenReturn( longitude1 );
   }

   @Test
   public void distance_noPoints() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Need to provide at least 2 points" );

      DistanceCalculator.distance( Unit.KILOMETERS );
   }

   @Test
   public void distance_onePoint() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Need to provide at least 2 points" );

      DistanceCalculator.distance( Unit.KILOMETERS, point1 );
   }

   @Test
   public void distance_nullPoint() {
      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "points 0 is null" );

      DistanceCalculator.distance( Unit.KILOMETERS, null, point2 );
   }

   /**
    * {@linkplain Point}'s constructor doesn't allow null {@linkplain Latitude} or {@linkplain Longitude},
    * and the class is immutable.  If that were to change, we need to know that DistanceCalculator detects
    * nulls, so this test uses Mockito to force Point to return a null.
    */
   @Test
   public void distance_nullLatitudePoint1() {
      Mockito.when( mockPoint.getLatitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Latitude 1 is null" );

      DistanceCalculator.distance( Unit.KILOMETERS, mockPoint, point2 );
   }

   /**
    * {@linkplain Point}'s constructor doesn't allow null {@linkplain Latitude} or {@linkplain Longitude},
    * and the class is immutable.  If that were to change, we need to know that DistanceCalculator detects
    * nulls, so this test uses Mockito to force a null to force Point to return a null.
    */
   @Test
   public void distance_nullLatitudePoint2() {
      Mockito.when( mockPoint.getLatitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Latitude 2 is null" );

      DistanceCalculator.distance( Unit.KILOMETERS, point1, mockPoint );
   }

   /**
    * {@linkplain Point}'s constructor doesn't allow null {@linkplain Latitude} or {@linkplain Longitude},
    * and the class is immutable.  If that were to change, we need to know that DistanceCalculator detects
    * nulls, so this test uses Mockito to force a null to force Point to return a null.
    */
   @Test
   public void distance_nullLongitudePoint1() {
      Mockito.when( mockPoint.getLongitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Longitude 1 is null" );

      DistanceCalculator.distance( Unit.KILOMETERS, mockPoint, point2 );
   }

   /**
    * {@linkplain Point}'s constructor doesn't allow null {@linkplain Latitude} or {@linkplain Longitude},
    * and the class is immutable.  If that were to change, we need to know that DistanceCalculator detects
    * nulls, so this test uses Mockito to force a null to force Point to return a null.
    */
   @Test
   public void distance_nullLongitudePoint2() {
      Mockito.when( mockPoint.getLongitude() ).thenReturn( null );

      thrown.expect( GeographicCoordinateException.class );
      thrown.expectMessage( "Longitude 2 is null" );

      DistanceCalculator.distance( Unit.KILOMETERS, point1, mockPoint );
   }

   @Test
   public void distance_feet() {
      final double distance = DistanceCalculator.distance( Unit.FEET, point1, point2 );
      assertEquals( 1070813.1682907024d, distance, fpDelta );
   }

   @Test
   public void distance_kilometers() {
      final double distance = DistanceCalculator.distance( Unit.KILOMETERS, point1, point2 );
      assertEquals( 326.3838536950061d, distance, fpDelta );
   }

   @Test
   public void distance_meters() {
      final double distance = DistanceCalculator.distance( Unit.METERS, point1, point2 );
      assertEquals( 326383.8536950061d, distance, fpDelta );
   }

   @Test
   public void distance_miles() {
      final double distance = DistanceCalculator.distance( Unit.MILES, point1, point2 );
      assertEquals( 202.80552429748153, distance, fpDelta );
   }

   @Test
   public void distance_nauticalMiles() {
      final double distance = DistanceCalculator.distance( Unit.NAUTICAL_MILES, point1, point2 );
      assertEquals( 176.23318234071604d, distance, fpDelta );
   }

   @Test
   public void distance_usSurveyfeet() {
      final double distance = DistanceCalculator.distance( Unit.US_SURVEY_FEET, point1, point2 );
      assertEquals( 1070811.026664366d, distance, fpDelta );
   }

   @Test
   public void distance_yards() {
      final double distance = DistanceCalculator.distance( Unit.YARDS, point1, point2 );
      assertEquals( 356937.7227635675d, distance, fpDelta );
   }

   /**
    * <p>
    * Uses interpolation to approximate a 95.5-mile trip along Interstate 5 in California, from (35.048983, -118.987977)
    * to (36.078247, -120.103787).  The trip distance and all coordinates are as reported by Bing Maps on February 15, 2016.
    * </p>
    *
    * <p>
    * The interpolated distance is 95.47462091551327 miles, whereas the true road distance is 95.5 miles - a
    * difference of less than .026 miles using 20 points.  Bing's figure of 95.5 is of course rounded, so if
    * Bing rounded up, we're even closer to the real distance.  Bing might also have rounded down, which
    * would put us farther away.
    * </p>
    *
    * <p>
    * As decent as this result is, if you use it to justify using this software to estimate fuel requirements
    * for a plane or something, <strong>you're a complete fool.</strong>  Don't even think about doing this.
    * </p>
    *
    * @see <a href="http://binged.it/1SPhHjq">Shortened trip URL</a>  - Who knows how long this will exist before Bing deletes it...
    * @see <a href="http://www.bing.com/mapspreview?&ty=0&rtp=pos.35.048992_-118.987968_I-5%20N%2c%20Bakersfield%2c%20CA%2093307_I-5%20N%2c%20Bakersfield%2c%20CA%2093307__e_~pos.36.078312_-120.103737_I-5%20N%2c%20Huron%2c%20CA%2093234_I-5%20N%2c%20Huron%2c%20CA%2093234__e_&mode=d&u=0&tt=I-5%20N%2c%20Bakersfield%2c%20CA%2093307%20to%20I-5%20N%2c%20Huron%2c%20CA%2093234&tsts2=%2526ty%253d18%2526q%253d35.04899226103765%25252c-118.98797131369996%2526mb%253d36.379826~-121.444888~34.715083~-117.418399&tstt2=I-5%20N%2c%20Bakersfield%2c%20CA%2093307&tsts1=%2526ty%253d0%2526rtp%253dpos.35.048992_-118.987968_I-5%252520N%25252c%252520Bakersfield%25252c%252520CA%25252093307_I-5%252520N%25252c%252520Bakersfield%25252c%252520CA%25252093307__e_~pos.36.078312_-120.103737_I-5%252520N%25252c%252520Huron%25252c%252520CA%25252093234_I-5%252520N%25252c%252520Huron%25252c%252520CA%25252093234__e_%2526mode%253dd%2526u%253d0&tstt1=I-5%20N%2c%20Bakersfield%2c%20CA%2093307%20to%20I-5%20N%2c%20Huron%2c%20CA%2093234&tsts0=%2526ty%253d18%2526q%253d36.07831163070724%25252c-120.1037394074518%2526mb%253d36.084772~-120.119465~36.071852~-120.088008&tstt0=I-5%20N%2c%20Huron%2c%20CA%2093234&cp=36.078659~-120.107106&lvl=16&ftst=1&ftics=True&v=2&sV=1&form=S00027">Full trip URL</a> - Use this if the shortened URL doesn't work.  This URL is so ridiculous, there's no telling how long it will actually work before Bing changes the URL format.
    */
   @Test
   public void distance_interpolateActualTrip() {
      Point points[] = new Point[20];
      int idx = 0;

      points[idx++] = new Point( new Latitude(35.048983d), new Longitude(-118.987977d) );
      points[idx++] = new Point( new Latitude(35.084629d), new Longitude(-119.025986d) );
      points[idx++] = new Point( new Latitude(35.110199d), new Longitude(-119.053642d) );
      points[idx++] = new Point( new Latitude(35.157555d), new Longitude(-119.106155d) );
      points[idx++] = new Point( new Latitude(35.167416d), new Longitude(-119.117012d) );
      points[idx++] = new Point( new Latitude(35.210117d), new Longitude(-119.163582d) );
      points[idx++] = new Point( new Latitude(35.250221d), new Longitude(-119.206154d) );
      points[idx++] = new Point( new Latitude(35.298851d), new Longitude(-119.258682d) );
      points[idx++] = new Point( new Latitude(35.314541d), new Longitude(-119.279282d) );
      points[idx++] = new Point( new Latitude(35.333168d), new Longitude(-119.304924d) );
      points[idx++] = new Point( new Latitude(35.494205d), new Longitude(-119.528030d) );
      points[idx++] = new Point( new Latitude(35.500153d), new Longitude(-119.534805d) );
      points[idx++] = new Point( new Latitude(35.570133d), new Longitude(-119.610878d) );
      points[idx++] = new Point( new Latitude(35.597832d), new Longitude(-119.637657d) );
      points[idx++] = new Point( new Latitude(35.607117d), new Longitude(-119.646233d) );
      points[idx++] = new Point( new Latitude(35.851257d), new Longitude(-119.829330d) );
      points[idx++] = new Point( new Latitude(35.974480d), new Longitude(-119.952690d) );
      points[idx++] = new Point( new Latitude(36.003834d), new Longitude(-119.981400d) );
      points[idx++] = new Point( new Latitude(36.028889d), new Longitude(-120.019310d) );
      points[idx++] = new Point( new Latitude(36.078247d), new Longitude(-120.103787d) );

      final double distance = DistanceCalculator.distance( Unit.MILES, points );

      assertEquals( 95.5d, distance, .026d );
   }
}
