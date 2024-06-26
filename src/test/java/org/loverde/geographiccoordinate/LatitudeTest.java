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


class LatitudeTest {

    private Latitude lat1;


    @BeforeEach
    void setUp() {
        lat1 = new Latitude(12, 16, 23.45d, Latitude.Direction.NORTH);
    }

    @Test
    void testConstant_maxValueIsCorrect() {
        assertEquals(90, Latitude.MAX_VALUE);
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionNorth() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Latitude.Direction dir = Latitude.Direction.NORTH;

        final Latitude l = new Latitude(deg, min, sec, dir);

        assertEquals(deg, l.degrees());
        assertEquals(min, l.minutes());
        assertEquals(sec, l.seconds(), 0.0);
        assertEquals(dir, l.direction());
    }

    @Test
    void constructor_actuallySetsStuffCorrectly_directionSouth() {
        final int deg = 1;
        final int min = 2;
        final double sec = 3;
        final Latitude.Direction dir = Latitude.Direction.SOUTH;

        final Latitude l = new Latitude(deg, min, sec, dir);

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
        final Latitude.Direction dir = Latitude.Direction.NEITHER;

        final Latitude l = new Latitude(deg, min, sec, dir);

        assertEquals(deg, l.degrees());
        assertEquals(min, l.minutes());
        assertEquals(sec, l.seconds(), 0.0);
        assertEquals(dir, l.direction());
    }

    @Test
    void constructor_fail_directionNeither() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(1, 1, 1, Latitude.Direction.NEITHER));
        assertEquals(DIRECTION_CANT_BE_NEITHER, e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionNorth() {
        final Latitude l = new Latitude(1);
        assertEquals(Latitude.Direction.NORTH, l.direction());
    }

    @Test
    void doubleConstructor_success_directionSouth() {
        final Latitude l = new Latitude(-1);
        assertEquals(Latitude.Direction.SOUTH, l.direction());
    }

    @Test
    void doubleConstructor_success_directionNeither() {
        final Latitude l = new Latitude(0);
        assertEquals(Latitude.Direction.NEITHER, l.direction());
    }

    @Test
    void doubleConstructor_success_maxValue() {
        final Latitude l = new Latitude(90);

        assertEquals(90, l.degrees());
        assertEquals(0, l.minutes());
        assertEquals(0.0, l.seconds(), 0.0);
        assertEquals(Latitude.Direction.NORTH, l.direction());
        assertEquals(90.0, l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_degrees() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(91));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_fail_maxValueExceeded_minutesSeconds() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(90.000000001d));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_success_minValue() {
        final Latitude l = new Latitude(-90);

        assertEquals(90, l.degrees());  // degrees are not negative - direction indicates sign
        assertEquals(0, l.minutes());
        assertEquals(0.0, l.seconds(), 0.0);
        assertEquals(Latitude.Direction.SOUTH, l.direction());
        assertEquals(-90.0, l.toDouble(), 0.0);
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_degrees() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(-91));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_fail_minValueExceeded_minutesSeconds() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(-90.000000001d));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsNorth() {
        final Latitude l = new Latitude(40.4406d);

        assertEquals(40, l.degrees());
        assertEquals(26, l.minutes());
        assertEquals(26.16d, l.seconds(), 0.00000000001236d);
        assertEquals(Latitude.Direction.NORTH, l.direction());
    }

    @Test
    void doubleConstructor_success_directionRecognizedAsSouth() {
        final Latitude l = new Latitude(-40.4406d);

        assertEquals(40, l.degrees());
        assertEquals(26, l.minutes());
        assertEquals(26.16d, l.seconds(), 0.00000000001236d);
        assertEquals(Latitude.Direction.SOUTH, l.direction());
    }

    @Test
    void constructor_success_degreesAtMinValue() {
        new Latitude(0, 10, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_degreesBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(-1, 10, 20, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_degreesAtMaxValue() {
        new Latitude(90, 0, 0, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_degreesExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude((int)Latitude.MAX_VALUE + 1, 0, 0, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_directionNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(1, 2, 3, null));
        assertEquals(DIRECTION_NULL, e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMinValue() {
        new Latitude(10, 0, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_minutesBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(10, -1, 10, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_minutesAtMaxValue() {
        new Latitude(10, 59, 10, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_minutesExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(10, 60, 1, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_minutesMustBeZero() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(90, 1, 0, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMinValue() {
        new Latitude(20, 10, 0, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_secondsBelowMinValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(20, 10, -.0000001d, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_success_secondsAtMaxValue() {
        new Latitude(20, 10, 59.999999999999d, Latitude.Direction.NORTH);
    }

    @Test
    void constructor_fail_secondsExceedMaxValue() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(10, 10, 60, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
    }

    @Test
    void constructor_fail_secondsMustBeZero() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Latitude(90, 0, 1, Latitude.Direction.NORTH));
        assertEquals(Latitude.getRangeError(), e.getMessage());
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
    void toRadians_success() {
        assertEquals(Math.toRadians(lat1.toDouble()), lat1.toRadians(), 0.0);
    }

    @Test
    void toString_success_north() {
        assertEquals("12°16'23.45\"N", lat1.toString());
    }

    @Test
    void toString_success_south() {
        final Latitude l = new Latitude(12, 16, 23.45d, Latitude.Direction.SOUTH);
        assertEquals("12°16'23.45\"S", l.toString());
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
