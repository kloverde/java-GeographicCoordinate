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


class LongitudeTest {

    private Longitude lon1;

    private static final String DEGREES_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.DEGREES_RANGE + Longitude.MAX_VALUE,
            MINUTES_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.MINUTES_RANGE,
            MINUTES_AND_SECONDS_MUST_BE_ZERO = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.MINUTES_AND_SECONDS_MUST_BE_ZERO + Longitude.MAX_VALUE,
            SECONDS_RANGE = Longitude.class.getSimpleName() + GeographicCoordinateException.Messages.SECONDS_RANGE;

    @BeforeEach
    void setUp() {
        lon1 = new Longitude(12, 16, 23.45d, Longitude.Direction.EAST);
    }

    @Test
    void testConstant_maxValueIsCorrect() {
        assertEquals(180, Longitude.MAX_VALUE);
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_east() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Longitude.Direction dir = Longitude.Direction.EAST;

        final Longitude l = new Longitude(deg, min, sec, dir);

        assertEquals(deg, l.getDegrees());
        assertEquals(min, l.getMinutes());
        assertEquals(sec, l.getSeconds(), 0.0);
        assertEquals(dir, l.getDirection());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_west() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Longitude.Direction dir = Longitude.Direction.WEST;

        final Longitude l = new Longitude(deg, min, sec, dir);

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
        final Longitude.Direction dir = Longitude.Direction.NEITHER;

        final Longitude l = new Longitude(deg, min, sec, dir);

        assertEquals(deg, l.getDegrees());
        assertEquals(min, l.getMinutes());
        assertEquals(sec, l.getSeconds(), 0.0);
        assertEquals(dir, l.getDirection());
    }

    @Test
    void constructor_fail_directionNeither() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(1, 1, 1, Longitude.Direction.NEITHER));
        assertEquals(GeographicCoordinateException.Messages.DIRECTION_INVALID, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionEast() {
        final Longitude l = new Longitude(1);
        assertEquals(Longitude.Direction.EAST, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionWest() {
        final Longitude l = new Longitude(-1);
        assertEquals(Longitude.Direction.WEST, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionNeither() {
        final Longitude l = new Longitude(0);
        assertEquals(Longitude.Direction.NEITHER, l.getDirection());
    }

    @Test
    void doubleConstructor_success_maxValue() {
        final Longitude l = new Longitude(Longitude.MAX_VALUE);

        assertEquals(Longitude.MAX_VALUE, l.getDegrees());
        assertEquals(0, l.getMinutes());
        assertEquals(0.0, l.getSeconds(), 0.0);
        assertEquals(Longitude.Direction.EAST, l.getDirection());
        assertEquals(Double.valueOf(Longitude.MAX_VALUE), l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_degrees() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(Longitude.MAX_VALUE + 1));
        assertEquals(GeographicCoordinateException.Messages.LONGITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_minutesSeconds() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(Longitude.MAX_VALUE + .000000001d));
        assertEquals(GeographicCoordinateException.Messages.LONGITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_success_minValue() {
        final Longitude l = new Longitude(-Longitude.MAX_VALUE);

        assertEquals(Longitude.MAX_VALUE, l.getDegrees());  // degrees are not negative - direction indicates sign
        assertEquals(0, l.getMinutes());
        assertEquals(0.0, l.getSeconds(), 0.0);
        assertEquals(Longitude.Direction.WEST, l.getDirection());
        assertEquals(Double.valueOf(-Longitude.MAX_VALUE), l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_degrees() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(-(Longitude.MAX_VALUE + 1)));
        assertEquals(GeographicCoordinateException.Messages.LONGITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_minutesSeconds() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(-(Longitude.MAX_VALUE + .000000001d)));
        assertEquals(GeographicCoordinateException.Messages.LONGITUDE_RANGE_DECIMAL, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsEast() {
        final Longitude l = new Longitude(40.4406d);

        assertEquals(40, l.getDegrees());
        assertEquals(26, l.getMinutes());
        assertEquals(26.16d, l.getSeconds(), 0.00000000001236d);
        assertEquals(Longitude.Direction.EAST, l.getDirection());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsWest() {
        final Longitude l = new Longitude(-40.4406d);

        assertEquals(40, l.getDegrees());
        assertEquals(26, l.getMinutes());
        assertEquals(26.16d, l.getSeconds(), 0.00000000001236d);
        assertEquals(Longitude.Direction.WEST, l.getDirection());
    }

    @Test
    void constructor_success_degreesAtMinValue() {
        new Longitude(0, 10, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_degreesBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(-1, 10, 20, Longitude.Direction.EAST));
        assertEquals(DEGREES_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_degreesAtMaxValue() {
        new Longitude(Longitude.MAX_VALUE, 0, 0, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_degreesExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(Longitude.MAX_VALUE + 1, 0, 0, Longitude.Direction.EAST));
        assertEquals(DEGREES_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_directionNull() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(1, 2, 3, null));
        assertEquals(GeographicCoordinateException.Messages.DIRECTION_NULL, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMinValue() {
        new Longitude(10, 0, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_minutesBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(10, -1, 10, Longitude.Direction.EAST));
        assertEquals(MINUTES_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMaxValue() {
        new Longitude(10, 59, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_minutesExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(10, 60, 1, Longitude.Direction.EAST));
        assertEquals(MINUTES_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_minutesMustBeZero() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(Longitude.MAX_VALUE, 1, 0, Longitude.Direction.EAST));
        assertEquals(MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMinValue() {
        new Longitude(20, 10, 0, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_secondsBelowMinValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(20, 10, -.0000001d, Longitude.Direction.EAST));
        assertEquals(SECONDS_RANGE, e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMaxValue() {
        new Longitude(20, 10, 59.999999999999d, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_secondsExceedMaxValue() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(10, 10, 60, Longitude.Direction.EAST));
        assertEquals(SECONDS_RANGE, e.getMessage());
    }

    @Test
    void constructor_fail_secondsMustBeZero() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> new Longitude(Longitude.MAX_VALUE, 0, 1, Longitude.Direction.EAST));
        assertEquals(MINUTES_AND_SECONDS_MUST_BE_ZERO, e.getMessage());
    }

    @Test
    void toDouble_success_east() {
        final Longitude l = new Longitude(40, 26, 26.16d, Longitude.Direction.EAST);
        assertEquals(40.4406d, l.toDouble(), 0.00000016666667d);
    }

    @Test
    void toDouble_success_west() {
        final Longitude l = new Longitude(40, 26, 26.16d, Longitude.Direction.WEST);
        assertEquals(-40.4406d, l.toDouble(), 0.00000016666667d);
    }

    @Test
    void equals_success_equalToOther() {
        final Longitude lon2 = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection());
        assertEquals(lon1, lon2);
        assertEquals(lon2, lon1);
    }

    @Test
    void equals_success_equalToSelf() {
        assertEquals(lon1, lon1);
    }

    @Test
    void equals_fail_null() {
        assertNotEquals(null, lon1);
    }

    @Test
    void equals_fail_degrees() {
        final Longitude l2 = new Longitude(lon1.getDegrees() + 1, lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection());
        assertNotEquals(lon1, l2);
    }

    @Test
    void equals_fail_minutes() {
        final Longitude l2 = new Longitude(lon1.getDegrees(), lon1.getMinutes() + 1, lon1.getSeconds(), lon1.getDirection());
        assertNotEquals(lon1, l2);
    }

    @Test
    void equals_fail_seconds() {
        final Longitude l2 = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds() + 1, lon1.getDirection());
        assertNotEquals(lon1, l2);
    }

    @Test
    void equals_fail_direction() {
        assertNotEquals(lon1.getDirection(), Longitude.Direction.WEST);  // sanity check

        final Longitude l2 = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Longitude.Direction.WEST);
        assertNotEquals(lon1, l2);
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_differentParentClass() {
        assertNotEquals(lon1, Integer.valueOf(2));
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    // This is not a mistake.  Here, we test a Longitude object against a Latitude object that's configured to match
    // the Longitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_LatitudeDirectionNorth() {
        final Latitude latitude = new Latitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.NORTH);
        assertNotEquals(lon1, latitude);
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    // This is not a mistake.  Here, we test a Longitude object against a Latitude object that's configured to match
    // the Longitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_LatitudeDirectionSouth() {
        final Latitude latitude = new Latitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.SOUTH);
        assertNotEquals(lon1, latitude);
    }

    // At first glance, this test might seem like a copy/paste error was made because it compares two different types.
    // This is not a mistake.  Here, we test a Longitude object against a Latitude object that's configured to match
    // the Longitude object as closely as possible (degrees, minutes, seconds).  Although it would be incorrect, it's
    // possible to write an equals() that would pass that.
    @SuppressWarnings("unlikely-arg-type")
    @Test
    void equals_fail_LatitudeDirectionNeither() {
        final Latitude latitude = new Latitude(0, 0, 0, Latitude.Direction.NEITHER);
        assertNotEquals(lon1, latitude);
    }

    @Test
    void hashCode_success_same() {
        final Longitude lon2 = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection());
        assertEquals(lon1.hashCode(), lon2.hashCode());
    }

    @Test
    void hashCode_fail_differentTypeLatitudeDirectionNorth() {
        final Latitude lat = new Latitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.NORTH);
        assertNotEquals(lon1.hashCode(), lat.hashCode());
    }

    @Test
    void hashCode_fail_differentTypeLatitudeDirectionSouth() {
        final Latitude lat = new Latitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Latitude.Direction.SOUTH);
        assertNotEquals(lon1.hashCode(), lat.hashCode());
    }

    @Test
    void hashCode_fail_differentTypeLatitudeDirectionNeither() {
        final Latitude lat = new Latitude(0, 0, 0, Latitude.Direction.NEITHER);
        final Longitude lon = new Longitude(lat.getDegrees(), lat.getMinutes(), lat.getSeconds(), Longitude.Direction.NEITHER);

        assertNotEquals(lat.hashCode(), lon.hashCode());
    }

    @Test
    void hashCode_fail_differentDegrees() {
        final Longitude l2 = new Longitude(lon1.getDegrees() + 1, lon1.getMinutes(), lon1.getSeconds(), lon1.getDirection());
        assertNotEquals(lon1.hashCode(), l2.hashCode());
    }

    @Test
    void hashCode_fail_differentMinutes() {
        final Longitude l = new Longitude(lon1.getDegrees(), lon1.getMinutes() + 1, lon1.getSeconds(), lon1.getDirection());
        assertNotEquals(lon1.hashCode(), l.hashCode());
    }

    @Test
    void hashCode_fail_differentSeconds() {
        final Longitude l = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds() + 1, lon1.getDirection());
        assertNotEquals(lon1.hashCode(), l.hashCode());
    }

    @Test
    void hashCode_fail_differentDirection() {
        final Longitude l = new Longitude(lon1.getDegrees(), lon1.getMinutes(), lon1.getSeconds(), Longitude.Direction.WEST);
        assertNotEquals(lon1.hashCode(), l.hashCode());
    }

    @Test
    void toRadians_success() {
        assertEquals(Math.toRadians(lon1.toDouble()), lon1.toRadians(), 0.0);
    }

    @Test
    void toString_fail_nullLocale() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> lon1.toString(null));
        assertEquals(GeographicCoordinateException.Messages.LOCALE_NULL, e.getMessage());
    }

    @Test
    void toString_success_east_localeWithPeriods() {
        assertEquals("12°16'23.45\"E", lon1.toString(Locale.US));
    }

    @Test
    void toString_success_west_localeWithPeriods() {
        final Longitude l = new Longitude(12, 16, 23.45d, Longitude.Direction.WEST);
        assertEquals("12°16'23.45\"W", l.toString(Locale.US));
    }

    @Test
    void toString_success_east_localeWithCommas() {
        assertEquals("12°16'23,45\"E", lon1.toString(Locale.FRANCE));
    }

    @Test
    void toString_success_west_localeWithCommas() {
        final Longitude l = new Longitude(12, 16, 23.45d, Longitude.Direction.WEST);
        assertEquals("12°16'23,45\"W", l.toString(Locale.FRANCE));
    }

    @Test
    void toString_success_noDirectionOnPrimeMeridian() {
        final Longitude l = new Longitude(0);
        assertEquals("0°0'0\"", l.toString());
    }

    @Test
    void toString_success_noTrailingZeros() {
        final Longitude l = new Longitude(12, 16, 0, Longitude.Direction.EAST);
        assertEquals("12°16'0\"E", l.toString());
    }
}
