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

import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;
import org.loverde.geographiccoordinate.internal.GeographicCoordinate;
import org.loverde.geographiccoordinate.internal.LatLonDirection;

import static org.loverde.geographiccoordinate.exception.GeographicCoordinateException.Messages.DIRECTION_CANT_BE_NEITHER;
import static org.loverde.geographiccoordinate.exception.GeographicCoordinateException.Messages.LAT_LON_RANGE_ERROR;


/**
 * Lines of longitude run parallel to the Prime Meridian (perpendicular to the Equator).  Longitude denotes whether a
 * location is east or west of the Prime Meridian.  The Prime Meridian is located at longitude 0.
 */
public record Longitude (int degrees, int minutes, double seconds, Longitude.Direction direction) implements GeographicCoordinate {

    /** Indicates whether a location is north or south of the Prime Meridian, or on the Prime Meridian */
    public enum Direction implements LatLonDirection {
        /** Indicates that the location is east of the Prime Meridian */
        EAST("E"),

        /** Indicates that the location is west of the Prime Meridian */
        WEST("W"),

        /** Indicates that the location is on the Prime Meridian (neither east not west:  the longitude is exactly 0.0). */
        NEITHER("");

        private final String abbreviation;

        Direction(final String abbr) {
            this.abbreviation = abbr;
        }

        @Override
        public String getAbbreviation() {
            return abbreviation;
        }
    }

    /**
     * When expressed as a floating-point number, valid longitudes sit in a range of +/- 180.0.  When expressed as
     * degrees/minutes/seconds, the valid range for degrees is 0-180, with minutes and seconds equal to 0 when
     * degrees is 180.
     */
    public static final double MAX_VALUE = 180.0;


    /**
     * Creates a new longitude object
     *
     * @param degrees   - Accepted range [0-180]
     * @param minutes   - Accepted range [0-59] unless {@code degrees} is 180, in which case {@code minutes} must be 0
     * @param seconds   - Accepted range [0-59.9999999999999] unless {@code degrees} is 180, in which case {@code seconds} must be 0
     * @param direction - A {@linkplain Longitude.Direction}
     * @throws GeographicCoordinateException If any arguments fall outside their accepted ranges, or if degrees/minutes/seconds
     *                                       are all 0 with a {@code direction} other than {@linkplain Direction#NEITHER}
     */
    public Longitude {
        if (degrees < 0.0 || degrees > MAX_VALUE) {
            throw new IllegalArgumentException(getRangeError());
        }

        if (minutes < 0.0 || minutes > MAX_VALUE_MINUTES) {
            throw new IllegalArgumentException(getRangeError());
        }

        if (seconds < 0.0 || seconds > MAX_VALUE_SECONDS) {
            throw new IllegalArgumentException(getRangeError());
        }

        if (degrees == MAX_VALUE && (minutes != 0.0 || seconds != 0.0)) {
            throw new IllegalArgumentException(getRangeError());
        }

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (direction == Direction.NEITHER && !(degrees == 0 && minutes == 0 && seconds == 0.0d)) {
            throw new IllegalArgumentException(DIRECTION_CANT_BE_NEITHER);
        }
    }

    /**
     * Creates a new longitude object
     *
     * @param longitude - A signed value.  Positive values are east; negative values are west.  Note that a value
     *                    of 0.0 is the Prime Meridian, which is neither east nor west.  If you supply a value of
     *                    0.0, the {@code direction} will be initialized to {@link Direction#NEITHER}.
     * @throws GeographicCoordinateException If the supplied value falls outside of +/- {@linkplain Longitude#MAX_VALUE}
     */
    public Longitude(final double longitude) {
        this(
            ((int) Math.abs(longitude)),
            ((int) ((Math.abs(longitude) - (int) Math.abs(longitude)) * 60.0d)),
            ((((Math.abs(longitude) - (int) Math.abs(longitude)) * 60.0d) % 1.0d) * 60.0d),

            switch ((int) Math.signum(longitude)) {
                case 1 -> Direction.EAST;
                case -1 -> Direction.WEST;
                default -> Direction.NEITHER;
            }
        );
    }

    public double toDouble() {
        final double decimal = degrees() + (minutes() / 60.0d) + (seconds() / 3600.0d);
        return direction() == Direction.EAST ? decimal : -decimal;
    }

    /** Returns the string representation provided by {@link #toDmsString()} */
    @Override
    public String toString() {
        return toDmsString();
    }

    public static String getRangeError() {
        return LAT_LON_RANGE_ERROR.formatted(MAX_VALUE, MAX_VALUE, (int) MAX_VALUE, (int) MAX_VALUE);
    }
}
