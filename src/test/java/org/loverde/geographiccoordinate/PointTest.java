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

package org.loverde.geographiccoordinate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class PointTest {

    private Latitude latitude1;

    private Longitude longitude1;

    private Point point1;


    @BeforeEach
    void setUp() {
        latitude1 = new Latitude(40, 42, 46.1, Latitude.Direction.NORTH);
        longitude1 = new Longitude(74, 0, 21.1, Longitude.Direction.WEST);

        point1 = new Point(latitude1, longitude1, "name");
    }

    @Test
    void constructor2arg_actuallySetsStuffCorrectly() {
        assertSame(latitude1, point1.getLatitude());
        assertSame(longitude1, point1.getLongitude());
        assertSame("name", point1.getName());
    }

    @Test
    void constructor2arg_fail_nullLatitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(null, longitude1));
        assertEquals("Latitude cannot be null", e.getMessage());
    }

    @Test
    void constructor2arg_fail_nullLongitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(latitude1, null));
        assertEquals("Longitude cannot be null", e.getMessage());
    }

    @Test
    void constructor3arg_fail_nullLatitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(null, longitude1, "name"));
        assertEquals("Latitude cannot be null", e.getMessage());
    }

    @Test
    void constructor3arg_fail_nullLongitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(latitude1, null, "name"));
        assertEquals("Longitude cannot be null", e.getMessage());
    }

    @Test
    void constructor3arg_fail_nullName() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(latitude1, longitude1, null));
        assertEquals("name cannot be null", e.getMessage());
    }

    @Test
    void equals_equal_sameAddress() {
        assertEquals(point1, point1);
    }

    @Test
    void equals_equal_differentAddresses() {
        final Latitude lat = new Latitude(point1.getLatitude().degrees(), point1.getLatitude().minutes(),
                point1.getLatitude().seconds(), point1.getLatitude().direction());

        final Longitude lon = new Longitude(point1.getLongitude().degrees(), point1.getLongitude().minutes(),
                point1.getLongitude().seconds(), point1.getLongitude().direction());

        assertEquals(point1, new Point(lat, lon, "name"));
    }

    @Test
    void equals_notEqual_differentLatitude() {
        final Latitude lat = new Latitude(latitude1.degrees() + 1, latitude1.minutes(), latitude1.seconds(), latitude1.direction());
        final Point newPoint = new Point(lat, point1.getLongitude());

        assertNotEquals(point1, newPoint);
    }

    @Test
    void equals_notEqual_differentLongitude() {
        final Longitude lon = new Longitude(longitude1.degrees() + 1, longitude1.minutes(), longitude1.seconds(), longitude1.direction());
        final Point newPoint = new Point(point1.getLatitude(), lon);

        assertNotEquals(point1, newPoint);
    }

    @Test
    void equals_notEqual_differentName() {
        final Point newPoint = new Point(point1.getLatitude(), point1.getLongitude(), point1.getName() + "different");

        assertNotEquals(point1, newPoint);
    }

    @Test
    void hashCode_notEqual_differentLatitude() {
        final Latitude lat = new Latitude(point1.getLatitude().degrees(), point1.getLatitude().minutes(),
                point1.getLatitude().seconds() + 1, point1.getLatitude().direction());

        final Longitude lon = new Longitude(point1.getLongitude().degrees(), point1.getLongitude().minutes(),
                point1.getLongitude().seconds(), point1.getLongitude().direction());

        final Point newPoint = new Point(lat, lon);

        assertNotEquals(point1.hashCode(), newPoint.hashCode());
    }

    @Test
    void hashCode_notEqual_differentLongitude() {
        final Latitude lat = new Latitude(point1.getLatitude().degrees(), point1.getLatitude().minutes(),
                point1.getLatitude().seconds(), point1.getLatitude().direction());

        final Longitude lon = new Longitude(point1.getLongitude().degrees(), point1.getLongitude().minutes(),
                point1.getLongitude().seconds() + 1, point1.getLongitude().direction());

        final Point newPoint = new Point(lat, lon);

        assertNotEquals(point1.hashCode(), newPoint.hashCode());
    }

    @Test
    void hashCode_notEqual_differentName() {
        final Point newPoint = new Point(point1.getLatitude(), point1.getLongitude(), point1.getName() + "different");

        assertNotEquals(point1.hashCode(), newPoint.hashCode());
    }
}
