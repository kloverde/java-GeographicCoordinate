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
import java.math.RoundingMode;
import java.util.Map;

import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.EnumHelper;

import static java.math.BigDecimal.ZERO;


/**
 * Represents the directions on an 8-point compass, where points are 45 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection8 implements CompassDirection {
    NORTH      ( "N",    "337.80",   "0.00",     "22.49" ),
    NORTHEAST  ( "NE",   "22.50",    "45.00",    "67.49" ),
    EAST       ( "E",    "67.50",    "90.00",    "112.49" ),
    SOUTHEAST  ( "SE",   "112.50",   "135.00",   "157.49" ),
    SOUTH      ( "S",    "157.50",   "180.00",   "202.49" ),
    SOUTHWEST  ( "SW",   "202.50",   "225.00",   "247.49" ),
    WEST       ( "W",    "247.50",   "270.00",   "292.49" ),
    NORTHWEST  ( "NW",   "292.50",   "315.00",   "337.49" );

    private final String abbreviation;

    private final BigDecimal minimum;
    private final BigDecimal middle;
    private final BigDecimal maximum;

    private static final BigDecimal BD360 = new BigDecimal(360);
    private static final BigDecimal STEP = new BigDecimal(45);

    private static final Map<String, CompassDirection8> abbreviationMap = EnumHelper.populateEnumMap(
        CompassDirection8.class, CompassDirection8::getAbbreviation);


    CompassDirection8(final String abbr, final String min, final String mid, final String max) {
        abbreviation = abbr;
        minimum = new BigDecimal(min);
        middle = new BigDecimal(mid);
        maximum = new BigDecimal(max);
    }

    /**
     * @return The direction abbreviation (southeast = SE, west-southwest = WSW, etc.)
     */
    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @return A visually friendly, grammatically correct transformation of {@link #name()}, with all lowercase letters,
     *         and underscores changed to spaces
     */
    @Override
    public String getPrintName() {
        return name().toLowerCase().replaceAll("_", " ");
    }

    /**
     * @return The lower bound for a given direction
     */
    @Override
    public BigDecimal getMinimum() {
        return minimum;
    }

    /**
     * @return The middle azimuth - this is when you're heading &quot;dead on&quot; in the given direction
     */
    @Override
    public BigDecimal getMiddle() {
        return middle;
    }

    /**
     * @return The upper bound for a given direction
     */
    @Override
    public BigDecimal getMaximum() {
        return maximum;
    }

    @Override
    public CompassDirection8 getPrevious() {
        final int ordinal = ordinal();
        final CompassDirection8[] values = values();

        return values[ordinal == 0 ? values.length - 1 : ordinal - 1];
    }

    @Override
    public CompassDirection8 getNext() {
        final int ordinal = ordinal();
        final CompassDirection8[] values = values();

        return values[ordinal == values.length - 1 ? 0 : ordinal + 1];
    }

    /**
     * @param abbr An 8-point compass direction abbreviation ("ENE", etc.)
     * @return The compass direction corresponding to its abbreviation
     */
    public static CompassDirection8 getByAbbreviation(final String abbr) {
        return abbreviationMap.get(abbr);
    }

    /**
     * @param bearing Bearing in degrees.  Value must be 0 &lt;= x &lt;= 360 (360 is treated as 0.0)
     * @return The compass direction closest to the specified bearing
     */
    public static CompassDirection8 getByBearing(final BigDecimal bearing) {
        BigDecimal newBearing;
        final int idx;
        final CompassDirection8[] values;
        CompassDirection8 dir;

        if (bearing.compareTo(ZERO) < 0 || bearing.compareTo(BD360) > 0) {
            throw new GeographicCoordinateException(String.format("Bearing %s is not in range [0, 360]", bearing.toPlainString()));
        }

        values = values();
        newBearing = (bearing.compareTo(BD360) == 0 ? ZERO : bearing).setScale(2, RoundingMode.HALF_UP);
        idx = Math.min(newBearing.divide(STEP, 2, RoundingMode.HALF_UP).setScale(0, RoundingMode.HALF_UP).toBigInteger().intValue(), values.length - 1);
        dir = values[idx];

        // If the bearing is greater than the direction's max, we need to go to the next one.
        // If the bearing is less than the min, we need to go the previous one.

        if (!isBearingWithinRange(newBearing, dir)) {
            if (newBearing.compareTo(dir.getMaximum()) > 0) {
                dir = dir.getNext();
            } else if (newBearing.compareTo(dir.getMinimum()) < 0) {
                dir = dir.getPrevious();
            }
        }

        return dir;
    }

    private static boolean isBearingWithinRange(final BigDecimal bearing, final CompassDirection8 direction) {
        if (direction != NORTH) {
            return bearing.compareTo(direction.getMinimum()) >= 0 && bearing.compareTo(direction.getMaximum()) <= 0;
        }

        // North is a special case where the minimum is greater than the maximum
        return (bearing.compareTo(NORTH.getMinimum()) >= 0 && bearing.compareTo(BD360) <= 0) ||
               (bearing.compareTo(ZERO) >= 0 && bearing.compareTo(NORTH.getMaximum()) <= 0);
    }
}
