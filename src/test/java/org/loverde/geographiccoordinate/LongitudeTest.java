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
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.DIRECTION_CANT_BE_NEITHER;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.DIRECTION_NULL;


class LongitudeTest {

    private Longitude lon1;

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

        assertEquals(deg, l.degrees());
        assertEquals(min, l.minutes());
        assertEquals(sec, l.seconds(), 0.0);
        assertEquals(dir, l.direction());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_west() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Longitude.Direction dir = Longitude.Direction.WEST;

        final Longitude l = new Longitude(deg, min, sec, dir);

        assertEquals(deg, l.degrees());
        assertEquals(min, l.minutes());
        assertEquals(sec, l.seconds(), 0.0);
        assertEquals(dir, l.direction());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionNeither() {
        final int deg = 0;
        final int min = 0;
        final double sec = 0;
        final Longitude.Direction dir = Longitude.Direction.NEITHER;

        final Longitude l = new Longitude(deg, min, sec, dir);

        assertEquals(deg, l.degrees());
        assertEquals(min, l.minutes());
        assertEquals(sec, l.seconds(), 0.0);
        assertEquals(dir, l.direction());
    }

    @Test
    void constructor_fail_directionNeither() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(1, 1, 1, Longitude.Direction.NEITHER));
        assertEquals(DIRECTION_CANT_BE_NEITHER, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionEast() {
        final Longitude l = new Longitude(1);
        assertEquals(Longitude.Direction.EAST, l.direction());
    }

    @Test
    void doubleConstructor_success_directionWest() {
        final Longitude l = new Longitude(-1);
        assertEquals(Longitude.Direction.WEST, l.direction());
    }

    @Test
    void doubleConstructor_success_directionNeither() {
        final Longitude l = new Longitude(0);
        assertEquals(Longitude.Direction.NEITHER, l.direction());
    }

    @Test
    void doubleConstructor_success_maxValue() {
        final Longitude l = new Longitude(Longitude.MAX_VALUE);

        assertEquals(Longitude.MAX_VALUE, l.degrees());
        assertEquals(0, l.minutes());
        assertEquals(0.0, l.seconds(), 0.0);
        assertEquals(Longitude.Direction.EAST, l.direction());
        assertEquals(Double.valueOf(Longitude.MAX_VALUE), l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_degrees() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(Longitude.MAX_VALUE + 1));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_minutesSeconds() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(Longitude.MAX_VALUE + .000000001d));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_success_minValue() {
        final Longitude l = new Longitude(-Longitude.MAX_VALUE);

        assertEquals(Longitude.MAX_VALUE, l.degrees());  // degrees are not negative - direction indicates sign
        assertEquals(0, l.minutes());
        assertEquals(0.0, l.seconds(), 0.0);
        assertEquals(Longitude.Direction.WEST, l.direction());
        assertEquals(Double.valueOf(-Longitude.MAX_VALUE), l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_degrees() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(-(Longitude.MAX_VALUE + 1)));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_minutesSeconds() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(-(Longitude.MAX_VALUE + .000000001d)));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsEast() {
        final Longitude l = new Longitude(40.4406d);

        assertEquals(40, l.degrees());
        assertEquals(26, l.minutes());
        assertEquals(26.16d, l.seconds(), 0.00000000001236d);
        assertEquals(Longitude.Direction.EAST, l.direction());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsWest() {
        final Longitude l = new Longitude(-40.4406d);

        assertEquals(40, l.degrees());
        assertEquals(26, l.minutes());
        assertEquals(26.16d, l.seconds(), 0.00000000001236d);
        assertEquals(Longitude.Direction.WEST, l.direction());
    }

    @Test
    void constructor_success_degreesAtMinValue() {
        new Longitude(0, 10, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_degreesBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(-1, 10, 20, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_degreesAtMaxValue() {
        new Longitude((int) Longitude.MAX_VALUE, 0, 0, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_degreesExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude((int) Longitude.MAX_VALUE + 1, 0, 0, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_directionNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(1, 2, 3, null));
        assertEquals(DIRECTION_NULL, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMinValue() {
        new Longitude(10, 0, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_minutesBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(10, -1, 10, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMaxValue() {
        new Longitude(10, 59, 10, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_minutesExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(10, 60, 1, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_minutesMustBeZero() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude((int) Longitude.MAX_VALUE, 1, 0, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMinValue() {
        new Longitude(20, 10, 0, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_secondsBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(20, 10, -.0000001d, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMaxValue() {
        new Longitude(20, 10, 59.999999999999d, Longitude.Direction.EAST);
    }

    @Test
    void constructor_fail_secondsExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude(10, 10, 60, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_secondsMustBeZero() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Longitude((int) Longitude.MAX_VALUE, 0, 1, Longitude.Direction.EAST));
        assertEquals(Longitude.getRangeError(), e.getMessage());
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
    void toRadians_success() {
        assertEquals(Math.toRadians(lon1.toDouble()), lon1.toRadians(), 0.0);
    }

    @Test
    void toString_success_east() {
        assertEquals("12°16'23.45\"E", lon1.toString());
    }

    @Test
    void toString_success_west() {
        final Longitude l = new Longitude(12, 16, 23.45d, Longitude.Direction.WEST);
        assertEquals("12°16'23.45\"W", l.toString());
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
