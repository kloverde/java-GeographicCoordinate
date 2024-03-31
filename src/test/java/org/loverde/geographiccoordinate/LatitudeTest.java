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

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;

import static org.junit.jupiter.api.Assertions.*;


class LatitudeTest {

    private Latitude lat1;

    private static final String DEGREES_RANGE = "Latitude" + GeographicCoordinateException.Messages.DEGREES_RANGE + Latitude.MAX_VALUE,
            MINUTES_RANGE = "Latitude" + GeographicCoordinateException.Messages.MINUTES_RANGE,
            MINUTES_AND_SECONDS_MUST_BE_ZERO = "Latitude" + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + Latitude.MAX_VALUE,
            SECONDS_RANGE = "Latitude" + GeographicCoordinateException.Messages.SECONDS_RANGE;

    @BeforeEach
    void setUp() {
        lat1 = new Latitude(12, 16, 23.45d, Latitude.Direction.NORTH);
    }

    @Test
    void _maxValueIsCorrect() {
        assertEquals(90, Latitude.MAX_VALUE);
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionNorth() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Latitude.Direction dir = Latitude.Direction.NORTH;

        final Latitude l = new Latitude(deg, min, sec, dir);

        assertEquals(deg, l.getDegrees());
        assertEquals(min, l.getMinutes());
        assertEquals(sec, l.getSeconds(), 0.0);
        assertEquals(dir, l.getDirection());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionSouth() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Latitude.Direction dir = Latitude.Direction.SOUTH;

        final Latitude l = new Latitude(deg, min, sec, dir);

        assertEquals(deg, l.getDegrees());
        assertEquals(min, l.getMinutes());
        assertEquals(sec, l.getSeconds(), 0.0);
        assertEquals(dir, l.getDirection());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionNeither() {
        final int deg = 0;
        final int min = 0;
        final double sec = 0;
        final Latitude.Direction dir = Latitude.Direction.NEITHER;

        final Latitude l = new Latitude(deg, min, sec, dir);

        assertEquals(deg, l.getDegrees());
        assertEquals(min, l.getMinutes());
        assertEquals(sec, l.getSeconds(), 0.0);
        assertEquals(dir, l.getDirection());
    }

    @Test
    void constructor_fail_directionNeither() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(1, 1, 1, Latitude.Direction.NEITHER));
        assertEquals(GeographicCoordinateException.Messages.DIRECTION_INVALID, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionNorth() {
        final Latitude l = new Latitude(1);
        assertEquals(Latitude.Direction.NORTH, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionSouth() {
        final Latitude l = new Latitude(-1);
        assertEquals(Latitude.Direction.SOUTH, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionNeither() {
        final Latitude l = new Latitude(0);
        assertEquals(Latitude.Direction.NEITHER, l.getDirection());
    }

    @Test
    void doubleConstructor_success_maxValue() {
        final Latitude l = new Latitude(90);

        assertEquals(90, l.getDegrees());
        assertEquals(0, l.getMinutes());
        assertEquals(0.0, l.getSeconds(), 0.0);
        assertEquals(Latitude.Direction.NORTH, l.getDirection());
        assertEquals(90.0, l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_degrees() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(91));
        assertEquals(GeographicCoordinateException.Messages.LATITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_minutesSeconds() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(90.000000001d));
        assertEquals(GeographicCoordinateException.Messages.LATITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_success_minValue() {
        final Latitude l = new Latitude(-90);

        assertEquals(90, l.getDegrees());  // degrees are not negative - direction indicates sign
        assertEquals(0, l.getMinutes());
        assertEquals(0.0, l.getSeconds(), 0.0);
        assertEquals(Latitude.Direction.SOUTH, l.getDirection());
        assertEquals(-90.0, l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_degrees() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(-91));
        assertEquals(GeographicCoordinateException.Messages.LATITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_minutesSeconds() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(-90.000000001d));
        assertEquals(GeographicCoordinateException.Messages.LATITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsNorth() {
        final Latitude l = new Latitude(40.4406d);

        assertEquals(40, l.getDegrees());
        assertEquals(26, l.getMinutes());
        assertEquals(26.16d, l.getSeconds(), 0.00000000001236d);
        assertEquals(Latitude.Direction.NORTH, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsSouth() {
        final Latitude l = new Latitude(-40.4406d);

        assertEquals(40, l.getDegrees());
        assertEquals(26, l.getMinutes());
        assertEquals(26.16d, l.getSeconds(), 0.00000000001236d);
        assertEquals(Latitude.Direction.SOUTH, l.getDirection());
    }

    @Test
    void constructor_success_degreesAtMinValue() {
        new Latitude(0, 10, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_degreesBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(-1, 10, 20, Latitude.Direction.NORTH));
        assertEquals(DEGREES_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_degreesAtMaxValue() {
        new Latitude(90, 0, 0, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_degreesExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(Latitude.MAX_VALUE + 1, 0, 0, Latitude.Direction.NORTH));
        assertEquals(DEGREES_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_directionNull() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(1, 2, 3, null));
        assertEquals(GeographicCoordinateException.Messages.DIRECTION_NULL, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMinValue() {
        new Latitude(10, 0, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_minutesBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(10, -1, 10, Latitude.Direction.NORTH));
        assertEquals(MINUTES_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMaxValue() {
        new Latitude(10, 59, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_minutesExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(10, 60, 1, Latitude.Direction.NORTH));
        assertEquals(MINUTES_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_minutesMustBeZero() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(90, 1, 0, Latitude.Direction.NORTH));
        assertEquals(MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMinValue() {
        new Latitude(20, 10, 0, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_secondsBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(20, 10, -.0000001d, Latitude.Direction.NORTH));
        assertEquals(SECONDS_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMaxValue() {
        new Latitude(20, 10, 59.999999999999d, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_secondsExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(10, 10, 60, Latitude.Direction.NORTH));
        assertEquals(SECONDS_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_secondsMustBeZero() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Latitude(90, 0, 1, Latitude.Direction.NORTH));
        assertEquals(MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage());
    }

    @Test
    void toDouble_success_north() {
        final Latitude l = new Latitude(40, 26, 26.16d, Latitude.Direction.NORTH);
        assertEquals(40.4406d, l.toDouble(), 0.00000016666667d);
    }

    @Test
    void toDouble_success_south() {
        final Latitude l = new Latitude(40, 26, 26.16d, Latitude.Direction.SOUTH);
        assertEquals(-40.4406d, l.toDouble(), 0.00000016666667d);
    }

    @Test
    void equals_success_equalToOther() {
        final Latitude lat2 = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection());
        assertEquals(lat1, lat2);
        assertEquals(lat2, lat1);
    }

    @Test
    void equals_success_equalToSelf() {
        assertEquals(lat1, lat1);
    }

    @Test
    void equals_fail_null() {
        assertNotEquals(null, lat1);
    }

    @Test
    void equals_fail_degrees() {
        final Latitude l2 = new Latitude(lat1.getDegrees() + 1, lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection());
        assertNotEquals(lat1, l2);
    }

    @Test
    void equals_fail_minutes() {
        final Latitude l2 = new Latitude(lat1.getDegrees(), lat1.getMinutes() + 1, lat1.getSeconds(), lat1.getDirection());
        assertNotEquals(lat1, l2);
    }

    @Test
    void equals_fail_seconds() {
        final Latitude l2 = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds() + 1, lat1.getDirection());
        assertNotEquals(lat1, l2);
    }

    @Test
    void equals_fail_directionDifferent() {
        final Latitude l2 = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Latitude.Direction.SOUTH);
        assertNotEquals(lat1, l2);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_differentParentClass() {
        assertNotEquals(lat1, Integer.valueOf(2));
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    // This is not a mistake.  Here, we test a Latitude object against a Longitude object that's configured to match
    // the Latitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_longitudeDirectionEast() {
        final Longitude longitude = new Longitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.EAST);
        assertNotEquals(lat1, longitude);
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    @SuppressWarnings("unlikely-arg-type")
    // This is not a mistake.  Here, we test a Latitude object against a Longitude object that's configured to match
    // the Latitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @Test
    void equals_fail_longitudeDirectionWest() {
        final Longitude longitude = new Longitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.WEST);
        assertNotEquals(lat1, longitude);
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    @SuppressWarnings("unlikely-arg-type")
    // This is not a mistake.  Here, we test a Latitude object against a Longitude object that's configured to match
    // the Latitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @Test
    void equals_fail_longitudeDirectionNeither() {
        final Longitude longitude = new Longitude(0, 0, 0, Longitude.Direction.NEITHER);
        assertNotEquals(lat1, longitude);
    }

    @Test
    void hashCode_success_same() {
        final Latitude lat2 = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection());
        assertEquals(lat1.hashCode(), lat2.hashCode());
    }

    @Test
    void hashCode_fail_differentTypeLongitudeDirectionEast() {
        final Longitude lon = new Longitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.EAST);
        assertNotEquals(lat1.hashCode(), lon.hashCode());
    }

    @Test
    void hashCode_differentType_longitudeDirectionWest() {
        final Longitude lon = new Longitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Longitude.Direction.WEST);
        assertNotEquals(lat1.hashCode(), lon.hashCode());
    }

    @Test
    void hashCode_fail_differentTypeLongitudeDirectionNeither() {
        final Latitude lat = new Latitude(0, 0, 0, Latitude.Direction.NEITHER);
        final Longitude lon = new Longitude(lat.getDegrees(), lat.getMinutes(), lat.getSeconds(), Longitude.Direction.NEITHER);

        assertNotEquals(lat.hashCode(), lon.hashCode());
    }

    @Test
    void hashCode_fail_differentDegrees() {
        final Latitude l2 = new Latitude(lat1.getDegrees() + 1, lat1.getMinutes(), lat1.getSeconds(), lat1.getDirection());
        assertNotEquals(lat1.hashCode(), l2.hashCode());
    }

    @Test
    void hashCode_fail_differentMinutes() {
        final Latitude l = new Latitude(lat1.getDegrees(), lat1.getMinutes() + 1, lat1.getSeconds(), lat1.getDirection());
        assertNotEquals(lat1.hashCode(), l.hashCode());
    }

    @Test
    void hashCode_fail_differentSeconds() {
        final Latitude l = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds() + 1, lat1.getDirection());
        assertNotEquals(lat1.hashCode(), l.hashCode());
    }

    @Test
    void hashCode_fail_differentDirection() {
        final Latitude l = new Latitude(lat1.getDegrees(), lat1.getMinutes(), lat1.getSeconds(), Latitude.Direction.SOUTH);
        assertNotEquals(lat1.hashCode(), l.hashCode());
    }

    @Test
    void toRadians_success() {
        assertEquals(Math.toRadians(lat1.toDouble()), lat1.toRadians(), 0.0);
    }

    @Test
    void toString_fail_nullLocale() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> lat1.toString(null));
        assertEquals(GeographicCoordinateException.Messages.LOCALE_NULL, e.getMessage());
    }

    @Test
    void toString_success_north_localeWithPeriods() {
        assertEquals("12°16'23.45\"N", lat1.toString(Locale.US));
    }

    @Test
    void toString_success_south_localeWithPeriods() {
        final Latitude l = new Latitude(12, 16, 23.45d, Latitude.Direction.SOUTH);
        assertEquals("12°16'23.45\"S", l.toString(Locale.US));
    }

    @Test
    void toString_success_north_localeWithCommas() {
        assertEquals("12°16'23,45\"N", lat1.toString(Locale.FRANCE));
    }

    @Test
    void toString_success_south_localeWithCommas() {
        final Latitude l = new Latitude(12, 16, 23.45d, Latitude.Direction.SOUTH);
        assertEquals("12°16'23,45\"S", l.toString(Locale.FRANCE));
    }

    @Test
    void toString_success_noDirectionOnEquator() {
        final Latitude l = new Latitude(0);
        assertEquals("0°0'0\"", l.toString());
    }

    @Test
    void toString_success_noTrailingZeros() {
        final Latitude l = new Latitude(12, 16, 0, Latitude.Direction.NORTH);
        assertEquals("12°16'0\"N", l.toString());
    }
}
