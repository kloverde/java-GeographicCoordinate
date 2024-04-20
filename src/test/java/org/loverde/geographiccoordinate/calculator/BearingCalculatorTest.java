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

import static org.junit.jupiter.api.Assertions.*;
import static org.loverde.geographiccoordinate.calculator.BearingCalculator.backAzimuth;
import static org.loverde.geographiccoordinate.calculator.BearingCalculator.initialBearing;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.BEARING_NULL;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.BEARING_OUT_OF_RANGE;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.COMPASS_TYPE_NULL;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.STARTING_POINT_NULL;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loverde.geographiccoordinate.Bearing;
import org.loverde.geographiccoordinate.Latitude;
import org.loverde.geographiccoordinate.Longitude;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;
import org.loverde.geographiccoordinate.exception.ExceptionMessages;


class BearingCalculatorTest {

    private Point point1;

    private Point point2;


    @BeforeEach
    void setUp() {
        final Latitude latitude1 = new Latitude(40, 42, 46, Latitude.Direction.NORTH);
        final Longitude longitude1 = new Longitude(74, 0, 21, Longitude.Direction.WEST);

        final Latitude latitude2 = new Latitude(38, 54, 17, Latitude.Direction.NORTH);
        final Longitude longitude2 = new Longitude(77, 0, 59, Longitude.Direction.WEST);

        point1 = new Point(latitude1, longitude1);
        point2 = new Point(latitude2, longitude2);
    }

    @Test
    void initialBearing_nullCompassDirectionType() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> initialBearing(null, point1, point2));
        assertEquals(COMPASS_TYPE_NULL, e.getMessage());
    }

    @Test
    void initialBearing_nullFromPoint() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> initialBearing(CompassDirection8.class, null, point2));
        assertEquals(STARTING_POINT_NULL, e.getMessage());
    }

    @Test
    void initialBearing_nullToPoint() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> initialBearing(CompassDirection8.class, point1, null));
        assertEquals(ExceptionMessages.BEARING_TO_NULL, e.getMessage());
    }

    @Test
    void initalBearing_compassDirection8() {
        final Bearing<CompassDirection8> bearing8 = initialBearing(CompassDirection8.class, point1, point2);
        assertEquals(232.95302, bearing8.getBearing().doubleValue(), .00001);
    }

    @Test
    void initalBearing_compassDirection16() {
        final Bearing<CompassDirection16> bearing16 = initialBearing(CompassDirection16.class, point1, point2);
        assertEquals(232.95302, bearing16.getBearing().doubleValue(), .00001);
    }

    @Test
    void initalBearing_compassDirection32() {
        final Bearing<CompassDirection32> bearing32 = initialBearing(CompassDirection32.class, point1, point2);
        assertEquals(232.95302, bearing32.getBearing().doubleValue(), .00001);
    }

    @Test
    void initialBearing_equivalence() {
        final Bearing<CompassDirection8> bearing8 = initialBearing(CompassDirection8.class, point1, point2);
        final Bearing<CompassDirection16> bearing16 = initialBearing(CompassDirection16.class, point1, point2);
        final Bearing<CompassDirection32> bearing32 = initialBearing(CompassDirection32.class, point1, point2);

        assertEquals(bearing8.getBearing(), bearing16.getBearing());
        assertEquals(bearing16.getBearing(), bearing32.getBearing());
    }

    @Test
    void backAzimuth_nullCompassDirectionType() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> backAzimuth(null, BigDecimal.ZERO));
        assertEquals(COMPASS_TYPE_NULL, e.getMessage());
    }

    @Test
    void backAzimuth_nullInitialBearing() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> backAzimuth(CompassDirection8.class, null));
        assertEquals(BEARING_NULL, e.getMessage());
    }

    @Test
    void backAzimuth_outOfLowerBound() {
        BigDecimal bearing = new BigDecimal("-.00000000001");
        Exception e = assertThrows(IllegalArgumentException.class, () -> backAzimuth(CompassDirection8.class, bearing));
        assertEquals(BEARING_OUT_OF_RANGE.formatted(bearing.toPlainString()), e.getMessage());
    }

    @Test
    void backAzimuth_outOfUpperBound() {
        BigDecimal bearing = new BigDecimal("360.00000000001");
        Exception e = assertThrows(IllegalArgumentException.class, () -> backAzimuth(CompassDirection8.class, bearing));
        assertEquals(BEARING_OUT_OF_RANGE.formatted(bearing.toPlainString()), e.getMessage());
    }

    @Test
    void backAzimuth_0() {
        final Bearing<CompassDirection8> back = backAzimuth(CompassDirection8.class, BigDecimal.ZERO);
        assertEquals(new BigDecimal(180), back.getBearing());
    }

    @Test
    void backAzimuth_179_9999() {
        final BigDecimal bearing = new BigDecimal("179.9999999999");

        final Bearing<CompassDirection8> back = backAzimuth(CompassDirection8.class, bearing);

        assertEquals("359.9999999999", back.getBearing().toPlainString());
    }

    @Test
    void backAzimuth_180() {
        final Bearing<CompassDirection8> back = backAzimuth(CompassDirection8.class, new BigDecimal(180));

        assertEquals(BigDecimal.ZERO, back.getBearing());
    }

    @Test
    void backAzimuth_180_0001() {
        final BigDecimal bearing = new BigDecimal("180.00000000001");

        final Bearing<CompassDirection8> back = backAzimuth(CompassDirection8.class, bearing);

        assertEquals("0.00000000001", back.getBearing().toPlainString());
    }

    @Test
    void backAzimuth_360() {
        final Bearing<CompassDirection8> back = backAzimuth(CompassDirection8.class, new BigDecimal(360));

        assertEquals(new BigDecimal(180), back.getBearing());
    }
}
