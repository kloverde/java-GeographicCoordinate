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

package org.loverde.geographiccoordinate.compass;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;


class CompassDirection8Test {

    @ParameterizedTest(name = "Verify that {0} has increasing values for minimum, middle and maximum")
    @EnumSource(CompassDirection8.class)
    void minimumMiddleAndMaximumIncrease(final CompassDirection8 direction) {
        assertTrue(direction.getMinimum().compareTo(ZERO) >= 0, "Minimum should be greater than or equal to zero");

        if (direction != CompassDirection8.NORTH) {
            assertTrue(direction.getMiddle().compareTo(direction.getMinimum()) > 0, "Middle should be greater than minimum");
        }  else {
            assertTrue(direction.getMiddle().compareTo(direction.getMinimum()) < 0, "Middle should be less than minimum (special case for NORTH)");
        }

        assertTrue(direction.getMiddle().compareTo(direction.getMaximum()) < 0, "Middle should be less than maximum");
        assertTrue(direction.getMaximum().compareTo(direction.getMiddle()) > 0, "Maximum should be greater than middle");
        assertTrue(direction.getMaximum().compareTo(new BigDecimal(360)) <= 0, "Maximum should be less than or equal to 360");
        assertTrue(direction.getMaximum().compareTo(direction.getNext().getMinimum()) < 0, "Maximum should be less than the next minimum");
    }

    @Test
    void getPrevious() {
        assertEquals(CompassDirection8.NORTHEAST, CompassDirection8.EAST.getPrevious());  // verify that getPrevious moves backward by 1
        assertEquals(CompassDirection8.NORTHWEST, CompassDirection8.NORTH.getPrevious());  // verify loop-around
    }

    @Test
    void getNext() {
        assertEquals(CompassDirection8.WEST, CompassDirection8.SOUTHWEST.getNext());  // verify that getNext moves forward by 1
        assertEquals(CompassDirection8.NORTH, CompassDirection8.NORTHWEST.getNext());  // verify loop-around
    }

    @ParameterizedTest(name = "Verify that {0} is retrievable by its abbreviation")
    @EnumSource(CompassDirection8.class)
    void getByAbbreviation(final CompassDirection8 direction) {
        assertEquals(direction, CompassDirection8.getByAbbreviation(direction.getAbbreviation()));
    }

    @ParameterizedTest(name = "Verify that {0} is retrievable by min/max/middle bearings")
    @EnumSource(CompassDirection8.class)
    void getByBearing(final CompassDirection8 direction) {
        assertEquals(direction, CompassDirection8.getByBearing(direction.getMinimum()), "Should be retrievable by minimum");
        assertEquals(direction, CompassDirection8.getByBearing(direction.getMiddle()), "Should be retrievable by middle");
        assertEquals(direction, CompassDirection8.getByBearing(direction.getMaximum()), "Should be retrievable by maximum");
    }

    @Test
    void getByBearing_testRounding() {
        assertEquals(CompassDirection8.NORTHWEST, CompassDirection8.getByBearing(new BigDecimal("337.494999999999999999")));
        assertEquals(CompassDirection8.NORTH, CompassDirection8.getByBearing(new BigDecimal("337.495")));
    }

    @Test
    void getByBearing_north() {
        assertEquals(CompassDirection8.NORTH, CompassDirection8.getByBearing(ZERO));
        assertEquals(CompassDirection8.NORTH, CompassDirection8.getByBearing(new BigDecimal("359.9")));
        assertEquals(CompassDirection8.NORTH, CompassDirection8.getByBearing(new BigDecimal(360)));
    }

    @Test
    void getByBearing_invalidMin() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> CompassDirection8.getByBearing(new BigDecimal("-0.000000000001")));
        assertEquals("Bearing -0.000000000001 is not in range [0, 360]", e.getMessage());
    }

    @Test
    void getByBearing_invalidMax() {
        Exception e = assertThrows(GeographicCoordinateException.class, () -> CompassDirection8.getByBearing(new BigDecimal("360.000000000001")));
        assertEquals("Bearing 360.000000000001 is not in range [0, 360]", e.getMessage());
    }

    @Test
    void getPrintName() {
        assertEquals("east", CompassDirection8.EAST.getPrintName());
        assertEquals("north", CompassDirection8.NORTH.getPrintName());
        assertEquals("northeast", CompassDirection8.NORTHEAST.getPrintName());
        assertEquals("northwest", CompassDirection8.NORTHWEST.getPrintName());
        assertEquals("south", CompassDirection8.SOUTH.getPrintName());
        assertEquals("southeast", CompassDirection8.SOUTHEAST.getPrintName());
        assertEquals("southwest", CompassDirection8.SOUTHWEST.getPrintName());
        assertEquals("west", CompassDirection8.WEST.getPrintName());
    }
}
