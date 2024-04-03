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
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.*;


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
        assertSame(latitude1, point1.latitude());
        assertSame(longitude1, point1.longitude());
        assertSame("name", point1.name());
    }

    @Test
    void constructor2arg_fail_nullLatitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(null, longitude1));
        assertEquals(LATITUDE_NULL, e.getMessage());
    }

    @Test
    void constructor2arg_fail_nullLongitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(latitude1, null));
        assertEquals(LONGITUDE_NULL, e.getMessage());
    }

    @Test
    void constructor3arg_fail_nullLatitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(null, longitude1, "name"));
        assertEquals(LATITUDE_NULL, e.getMessage());
    }

    @Test
    void constructor3arg_fail_nullLongitude() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Point(latitude1, null, "name"));
        assertEquals(LONGITUDE_NULL, e.getMessage());
    }
}
