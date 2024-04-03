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

import static java.math.BigDecimal.ZERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.BEARING_OUT_OF_RANGE;


class CompassDirection32Test {

    @ParameterizedTest(name = "Verify that {0} has increasing values for minimum, middle and maximum")
    @EnumSource(CompassDirection32.class)
    void minimumMiddleAndMaximumIncrease(final CompassDirection32 direction) {
        assertTrue(direction.getMinimum().compareTo(ZERO) >= 0, "Minimum should be greater than or equal to zero");

        if (direction != CompassDirection32.NORTH) {
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
        assertEquals(CompassDirection32.EAST, CompassDirection32.EAST_BY_SOUTH.getPrevious());   // verify that getPrevious moves backward by 1
        assertEquals(CompassDirection32.NORTH_BY_WEST, CompassDirection32.NORTH.getPrevious());  // verify loop-around
    }

    @Test
    void getNext() {
        assertEquals(CompassDirection32.NORTH_BY_EAST, CompassDirection32.NORTH.getNext());  // verify that getNext moves forward by 1
        assertEquals(CompassDirection32.NORTH, CompassDirection32.NORTH_BY_WEST.getNext());   // verify loop-around
    }

    @ParameterizedTest(name = "Verify that {0} is retrievable by its abbreviation")
    @EnumSource(CompassDirection32.class)
    void getByAbbreviation(final CompassDirection32 direction) {
        assertEquals(direction, CompassDirection32.getByAbbreviation(direction.getAbbreviation()));
    }

    @ParameterizedTest(name = "Verify that {0} is retrievable by min/max/middle bearings")
    @EnumSource(CompassDirection32.class)
    void getByBearing(final CompassDirection32 direction) {
        assertEquals(direction, CompassDirection32.getByBearing(direction.getMinimum()), "Should be retrievable by minimum");
        assertEquals(direction, CompassDirection32.getByBearing(direction.getMiddle()), "Should be retrievable by middle");
        assertEquals(direction, CompassDirection32.getByBearing(direction.getMaximum()), "Should be retrievable by maximum");
    }

    @Test
    void getByBearing_testRounding() {
        assertEquals(CompassDirection32.NORTH_BY_WEST, CompassDirection32.getByBearing(new BigDecimal("354.37499999999999")));
        assertEquals(CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal("354.375")));
    }

    @Test
    void getByBearing_north() {
        assertEquals(CompassDirection32.NORTH, CompassDirection32.getByBearing(ZERO));
        assertEquals(CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal("359.9")));
        assertEquals(CompassDirection32.NORTH, CompassDirection32.getByBearing(new BigDecimal(360)));
    }

    @Test
    void getByBearing_invalidMin() {
        BigDecimal bearing = new BigDecimal("-0.000000000001");
        Exception e = assertThrows(IllegalArgumentException.class, () -> CompassDirection32.getByBearing(bearing));
        assertEquals(BEARING_OUT_OF_RANGE.formatted(bearing.toPlainString()), e.getMessage());
    }

    @Test
    void getByBearing_invalidMax() {
        BigDecimal bearing = new BigDecimal("360.000000000001");
        Exception e = assertThrows(IllegalArgumentException.class, () -> CompassDirection32.getByBearing(bearing));
        assertEquals(BEARING_OUT_OF_RANGE.formatted(bearing.toPlainString()), e.getMessage());
    }

    @Test
    void getPrintName() {
        assertEquals("north", CompassDirection32.NORTH.getPrintName());
        assertEquals("north by east", CompassDirection32.NORTH_BY_EAST.getPrintName());
        assertEquals("north northeast", CompassDirection32.NORTH_NORTHEAST.getPrintName());
        assertEquals("northeast by north", CompassDirection32.NORTHEAST_BY_NORTH.getPrintName());
        assertEquals("northeast", CompassDirection32.NORTHEAST.getPrintName());
        assertEquals("northeast by east", CompassDirection32.NORTHEAST_BY_EAST.getPrintName());
        assertEquals("east northeast", CompassDirection32.EAST_NORTHEAST.getPrintName());
        assertEquals("east by north", CompassDirection32.EAST_BY_NORTH.getPrintName());
        assertEquals("east", CompassDirection32.EAST.getPrintName());
        assertEquals("east by south", CompassDirection32.EAST_BY_SOUTH.getPrintName());
        assertEquals("east southeast", CompassDirection32.EAST_SOUTHEAST.getPrintName());
        assertEquals("southeast by east", CompassDirection32.SOUTHEAST_BY_EAST.getPrintName());
        assertEquals("southeast", CompassDirection32.SOUTHEAST.getPrintName());
        assertEquals("southeast by south", CompassDirection32.SOUTHEAST_BY_SOUTH.getPrintName());
        assertEquals("south southeast", CompassDirection32.SOUTH_SOUTHEAST.getPrintName());
        assertEquals("south by east", CompassDirection32.SOUTH_BY_EAST.getPrintName());
        assertEquals("south", CompassDirection32.SOUTH.getPrintName());
        assertEquals("south by west", CompassDirection32.SOUTH_BY_WEST.getPrintName());
        assertEquals("south southwest", CompassDirection32.SOUTH_SOUTHWEST.getPrintName());
        assertEquals("southwest by south", CompassDirection32.SOUTHWEST_BY_SOUTH.getPrintName());
        assertEquals("southwest", CompassDirection32.SOUTHWEST.getPrintName());
        assertEquals("southwest by west", CompassDirection32.SOUTHWEST_BY_WEST.getPrintName());
        assertEquals("west southwest", CompassDirection32.WEST_SOUTHWEST.getPrintName());
        assertEquals("west by south", CompassDirection32.WEST_BY_SOUTH.getPrintName());
        assertEquals("west", CompassDirection32.WEST.getPrintName());
        assertEquals("west by north", CompassDirection32.WEST_BY_NORTH.getPrintName());
        assertEquals("west northwest", CompassDirection32.WEST_NORTHWEST.getPrintName());
        assertEquals("northwest by west", CompassDirection32.NORTHWEST_BY_WEST.getPrintName());
        assertEquals("northwest", CompassDirection32.NORTHWEST.getPrintName());
        assertEquals("northwest by north", CompassDirection32.NORTHWEST_BY_NORTH.getPrintName());
        assertEquals("north northwest", CompassDirection32.NORTH_NORTHWEST.getPrintName());
        assertEquals("north by west", CompassDirection32.NORTH_BY_WEST.getPrintName());
    }
}
