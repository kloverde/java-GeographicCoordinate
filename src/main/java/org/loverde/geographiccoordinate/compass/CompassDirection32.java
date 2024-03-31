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
 * Represents the directions on a 32-point compass, where points are 11.25 degrees apart
 *
 * @see <a href="https://en.wikipedia.org/wiki/Points_of_the_compass">https://en.wikipedia.org/wiki/Points_of_the_compass</a>
 */
public enum CompassDirection32 implements CompassDirection {
    NORTH               ( "N",     "354.38",   "0.00",     "5.62" ),
    NORTH_BY_EAST       ( "NbE",   "5.63",     "11.25",    "16.87" ),
    NORTH_NORTHEAST     ( "NNE",   "16.88",    "22.50",    "28.12" ),
    NORTHEAST_BY_NORTH  ( "NEbN",  "28.13",    "33.75",    "39.37" ),
    NORTHEAST           ( "NE",    "39.38",    "45.00",    "50.62" ),
    NORTHEAST_BY_EAST   ( "NEbE",  "50.63",    "56.25",    "61.87" ),
    EAST_NORTHEAST      ( "ENE",   "61.88",    "67.50",    "73.12" ),
    EAST_BY_NORTH       ( "EbN",   "73.13",    "78.75",    "84.37" ),
    EAST                ( "E",     "84.38",    "90.00",    "95.62" ),
    EAST_BY_SOUTH       ( "EbS",   "95.63",    "101.25",   "106.87" ),
    EAST_SOUTHEAST      ( "ESE",   "106.88",   "112.50",   "118.12" ),
    SOUTHEAST_BY_EAST   ( "SEbE",  "118.13",   "123.75",   "129.37" ),
    SOUTHEAST           ( "SE",    "129.38",   "135.00",   "140.62" ),
    SOUTHEAST_BY_SOUTH  ( "SEbS",  "140.63",   "146.25",   "151.87" ),
    SOUTH_SOUTHEAST     ( "SSE",   "151.88",   "157.50",   "163.12" ),
    SOUTH_BY_EAST       ( "SbE",   "163.13",   "168.75",   "174.37" ),
    SOUTH               ( "S",     "174.38",   "180.00",   "185.62" ),
    SOUTH_BY_WEST       ( "SbW",   "185.63",   "191.25",   "196.87" ),
    SOUTH_SOUTHWEST     ( "SSW",   "196.88",   "202.50",   "208.12" ),
    SOUTHWEST_BY_SOUTH  ( "SWbS",  "208.13",   "213.75",   "219.37" ),
    SOUTHWEST           ( "SW",    "219.38",   "225.00",   "230.62" ),
    SOUTHWEST_BY_WEST   ( "SWbW",  "230.63",   "236.25",   "241.87" ),
    WEST_SOUTHWEST      ( "WSW",   "241.88",   "247.50",   "253.12" ),
    WEST_BY_SOUTH       ( "WbS",   "253.13",   "258.75",   "264.37" ),
    WEST                ( "W",     "264.38",   "270.00",   "275.62" ),
    WEST_BY_NORTH       ( "WbN",   "275.63",   "281.25",   "286.87" ),
    WEST_NORTHWEST      ( "WNW",   "286.88",   "292.50",   "298.12" ),
    NORTHWEST_BY_WEST   ( "NWbW",  "298.13",   "303.75",   "309.37" ),
    NORTHWEST           ( "NW",    "309.38",   "315.00",   "320.62" ),
    NORTHWEST_BY_NORTH  ( "NWbN",  "320.63",   "326.25",   "331.87" ),
    NORTH_NORTHWEST     ( "NNW",   "331.88",   "337.50",   "343.12" ),
    NORTH_BY_WEST       ( "NbW",   "343.13",   "348.75",   "354.37" );

    private final String abbreviation;

    private final BigDecimal minimum;
    private final BigDecimal middle;
    private final BigDecimal maximum;

    private static final BigDecimal BD360 = new BigDecimal(360);
    private static final BigDecimal STEP = new BigDecimal("11.25");

    private static final Map<String, CompassDirection32> abbreviationMap = EnumHelper.populateEnumMap(
        CompassDirection32.class, CompassDirection32::getAbbreviation);


    CompassDirection32(final String abbr, final String min, final String mid, final String max) {
        abbreviation = abbr;
        minimum = new BigDecimal(min);
        middle = new BigDecimal(mid);
        maximum = new BigDecimal(max);
    }

    /**
     * @return The direction abbreviation (southeast = SE, southeast by south = SEbS, etc.)
     */
    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * @return A visually friendly, grammatically correct transformation of {@link #name()}, with all lowercase letters, and underscores changed to spaces
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
    public CompassDirection32 getPrevious() {
        final int ordinal = ordinal();
        final CompassDirection32[] values = values();

        return values[ordinal == 0 ? values.length - 1 : ordinal - 1];
    }

    @Override
    public CompassDirection32 getNext() {
        final int ordinal = ordinal();
        final CompassDirection32[] values = values();

        return values[ordinal == values.length - 1 ? 0 : ordinal + 1];
    }

    /**
     * @param abbr A 32-point compass direction abbreviation ("NWbN", etc.)
     * @return The compass direction corresponding to its abbreviation
     */
    public static CompassDirection32 getByAbbreviation(final String abbr) {
        return abbreviationMap.get(abbr);
    }

    /**
     * @param bearing Bearing in degrees.  Value must be 0 &lt;= x &lt;= 360 (360 is treated as 0.0)
     * @return The compass direction closest to the specified bearing
     */
    public static CompassDirection32 getByBearing(final BigDecimal bearing) {
        BigDecimal newBearing;
        final int idx;
        final CompassDirection32[] values;
        CompassDirection32 dir;

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
            } else if (bearing.compareTo(dir.getMinimum()) < 0) {
                dir = dir.getPrevious();
            }
        }

        return dir;
    }

    private static boolean isBearingWithinRange(final BigDecimal bearing, final CompassDirection32 direction) {
        if (direction != NORTH) {
            return bearing.compareTo(direction.getMinimum()) >= 0 && bearing.compareTo(direction.getMaximum()) <= 0;
        }

        // North is a special case where the minimum is greater than the maximum
        return (bearing.compareTo(NORTH.getMinimum()) >= 0 && bearing.compareTo(BD360) <= 0) ||
               (bearing.compareTo(ZERO) >= 0 && bearing.compareTo(NORTH.getMaximum()) <= 0);
    }
}
