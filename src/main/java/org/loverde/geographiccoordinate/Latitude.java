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

import org.loverde.geographiccoordinate.internal.GeographicCoordinate;
import org.loverde.geographiccoordinate.internal.LatLonDirection;

import static org.loverde.geographiccoordinate.Latitude.Direction.SOUTH;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.*;
import static org.loverde.geographiccoordinate.internal.Objects.failIf;


/**
 * Lines of latitude run parallel to the Equator (perpendicular to the Prime Meridian).  Latitude denotes whether a
 * location is north or south of the Equator.  The Equator is located at latitude 0.
 */
public record Latitude(int degrees, int minutes, double seconds, Direction direction) implements GeographicCoordinate {

    /** Indicates whether a location is north or south of the Equator, or on the Equator */
    public enum Direction implements LatLonDirection {
        /** Indicates that the location is north of the Equator */
        NORTH("N"),

        /** Indicates that the location is south of the Equator */
        SOUTH("S"),

        /** Indicates that the location is on the Equator (neither north nor south:  the latitude is exactly 0.0). */
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
     * When expressed as a floating-point number, valid latitudes sit in a range of +/- 90.0.  When expressed as
     * degrees/minutes/seconds, the valid range for degrees is 0-90, with minutes and seconds equal to 0 when
     * degrees is 90.
     */
    public static final double MAX_VALUE = 90.0;


    /**
     * Creates a new latitude object
     *
     * @param degrees   - Accepted range [0-90]
     * @param minutes   - Accepted range [0-59] unless {@code degrees} is 90, in which case {@code minutes} must be 0
     * @param seconds   - Accepted range [0-59.9999999999999] unless {@code degrees} is 90, in which case {@code seconds} must be 0
     * @param direction - A {@linkplain Direction}
     * @throws IllegalArgumentException If any arguments fall outside their accepted ranges, or if degrees/minutes/seconds
     *                                  are all 0 with a {@code direction} other than {@linkplain Direction#NEITHER}
     */
    public Latitude {
        failIf(degrees < 0 || degrees > MAX_VALUE, Latitude::getRangeError);
        failIf(minutes < 0 || minutes > MAX_VALUE_MINUTES, Latitude::getRangeError);
        failIf(seconds < 0.0 || seconds > MAX_VALUE_SECONDS, Latitude::getRangeError);
        failIf(degrees == MAX_VALUE && (minutes > 0 || seconds > 0.0), Latitude::getRangeError);
        failIf(direction == null, () -> DIRECTION_NULL);
        failIf(direction == Direction.NEITHER && !(degrees == 0 && minutes == 0 && seconds == 0.0d), () -> DIRECTION_CANT_BE_NEITHER);
    }

    /**
     * Creates an immutable Latitude object.
     *
     * @param latitude - A signed value.  Positive values are north; negative values are south.  Note that a value
     *                   of 0.0 is the Equator, which is neither north nor south.  If you supply a value of 0.0,
     *                   the {@code direction} will be initialized to {@link Direction#NEITHER}.
     * @throws IllegalArgumentException If the supplied value falls outside +/- {@linkplain Latitude#MAX_VALUE}
     */
    public Latitude(final double latitude) {
        this(
            ((int) Math.abs(latitude)),
            ((int) ((Math.abs(latitude) - (int) Math.abs(latitude)) * 60.0d)),
            ((((Math.abs(latitude) - (int) Math.abs(latitude)) * 60.0d) % 1.0d) * 60.0d),

            switch ((int) Math.signum(latitude)) {
                case 1 -> Direction.NORTH;
                case -1 -> SOUTH;
                default -> Direction.NEITHER;
            }
        );
    }

    public double toDouble() {
        return (direction == SOUTH ? -1 : 1) * (degrees + ((minutes / 60.0) + (seconds / 3600.0)));
    }

    /** Returns the string representation provided by {@link #toDmsString()} */
    @Override
    public String toString() {
        return toDmsString();
    }

    public static String getRangeError() {
        return LAT_LON_RANGE_ERROR.replaceAll("\n", "%n").formatted(MAX_VALUE, MAX_VALUE, (int) MAX_VALUE, (int) MAX_VALUE);
    }
}
