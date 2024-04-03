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

package org.loverde.geographiccoordinate.calculator;

import java.math.BigDecimal;

import org.loverde.geographiccoordinate.Bearing;
import org.loverde.geographiccoordinate.Point;
import org.loverde.geographiccoordinate.compass.CompassDirection;
import org.loverde.geographiccoordinate.compass.CompassDirection16;
import org.loverde.geographiccoordinate.compass.CompassDirection32;
import org.loverde.geographiccoordinate.compass.CompassDirection8;

import static java.math.BigDecimal.ZERO;
import static org.loverde.geographiccoordinate.exception.ExceptionMessages.*;
import static org.loverde.geographiccoordinate.internal.Objects.failIf;


/**
 * This class calculates initial bearing and back azimuth.
 *
 * <p><strong>
 * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN UNDERSTANDING OF, GEODESY, AND MERELY
 * IMPLEMENTED FORMULAS FOUND ONLINE.  DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME TO
 * READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
 * </strong></p>
 */
public class BearingCalculator {

    private static final BigDecimal BD_360 = new BigDecimal(360);
    private static final BigDecimal BD_180 = new BigDecimal(180);
    private static final BigDecimal BD_NEG_180 = new BigDecimal(-180);


    /**
     * <p>
     * Calculates the initial bearing that will take you from point A to point B.
     * Keep in mind that the bearing will change over the course of the trip and will need to be recalculated.
     * </p>
     *
     * <p><strong>
     * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN UNDERSTANDING OF, GEODESY, AND MERELY
     * IMPLEMENTED FORMULAS FOUND ONLINE.  DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME TO
     * READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
     * </strong></p>
     *
     * @param compassType The returned {@code Bearing} will be parameterized with this type, allowing you to safely cast it
     * @param from        The departing point
     * @param to          The destination point
     * @return The initial bearing from A to B, and a mapping of the bearing to an 8, 16 or 32-point compass direction, depending on {@code compassType}
     * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">http://www.movable-type.co.uk/scripts/latlong.html</a>.
     */
    public static Bearing<? extends CompassDirection> initialBearing(final Class<? extends CompassDirection> compassType, final Point from, final Point to) {
        return newBearing(compassType, calculateBearing(from, to));
    }

    /**
     * Calculates the back azimuth - the bearing that gets you back to your starting point
     *
     * <p><strong>
     * THIS IS HOBBYIST SOFTWARE.  THE AUTHOR HAS NO BACKGROUND IN, OR EVEN AN UNDERSTANDING OF, GEODESY, AND MERELY
     * IMPLEMENTED FORMULAS FOUND ONLINE.  DON'T ENTRUST YOUR SAFETY TO THIS SOFTWARE.  NOW WOULD BE A GOOD TIME TO
     * READ AND UNDERSTAND THE WAIVER PRESENT IN THIS SOFTWARE'S LICENSE.
     * </strong></p>
     *
     * @param compassType    The returned {@code Bearing} will be parameterized with this type, allowing you to safely cast it
     * @param initialBearing The initial bearing
     * @return The back azimuth based on initial bearing
     */
    public static Bearing<? extends CompassDirection> backAzimuth(final Class<? extends CompassDirection> compassType, final BigDecimal initialBearing) {
        return newBearing(compassType, calculateBackAzimuth(initialBearing));
    }

    private static Bearing<? extends CompassDirection> newBearing(final Class<? extends CompassDirection> compassType, final BigDecimal angle) {
        if (compassType == CompassDirection8.class) {
            return new Bearing<>(CompassDirection8.getByBearing(angle), angle);
        } else if (compassType == CompassDirection16.class) {
            return new Bearing<>(CompassDirection16.getByBearing(angle), angle);
        } else if (compassType == CompassDirection32.class) {
            return new Bearing<>(CompassDirection32.getByBearing(angle), angle);
        }

        throw new IllegalArgumentException(COMPASS_TYPE_NULL);
    }

    private static BigDecimal calculateBearing(final Point from, final Point to) {
        failIf(from == null, () -> STARTING_POINT_NULL);
        failIf(to == null, () -> BEARING_TO_NULL);
        failIf(from.latitude() == null, () -> BEARING_FROM_LATITUDE_NULL);
        failIf(from.longitude() == null, () -> BEARING_FROM_LONGITUDE_NULL);
        failIf(to.latitude() == null, () -> BEARING_TO_LATITUDE_NULL);
        failIf(to.longitude() == null, () -> BEARING_TO_LONGITUDE_NULL);

        final double fromLatRadians = from.latitude().toRadians(),
                     fromLonRadians = from.longitude().toRadians(),
                     toLatRadians = to.latitude().toRadians(),
                     toLonRadians = to.longitude().toRadians(),
                     deltaLon = toLonRadians - fromLonRadians;

        final double y = Math.sin(deltaLon) * Math.cos(toLatRadians);
        final double x = Math.cos(fromLatRadians) * Math.sin(toLatRadians) - Math.sin(fromLatRadians) * Math.cos(toLatRadians) * Math.cos(deltaLon);

        final double bearing = Math.toDegrees(Math.atan2(y, x));
        final double normalizedBearing = normalizeBearing(bearing);

        return new BigDecimal(normalizedBearing);
    }

    private static BigDecimal calculateBackAzimuth(final BigDecimal bearing) {
        final BigDecimal zeroedBearing;
        BigDecimal backAzimuth;

        failIf(bearing == null, () -> BEARING_NULL);
        failIf(bearing.compareTo(ZERO) < 0 || bearing.compareTo(BD_360) > 0, () -> BEARING_OUT_OF_RANGE);

        zeroedBearing = bearing.compareTo(BD_360) == 0 ? ZERO : bearing;

        if (zeroedBearing.compareTo(BD_180) == 0 || zeroedBearing.compareTo(BD_NEG_180) == 0) {
            backAzimuth = ZERO;
        } else if (zeroedBearing.compareTo(BD_180) < 0) {
            backAzimuth = zeroedBearing.add(BD_180);
        } else {
            backAzimuth = zeroedBearing.subtract(BD_180);
        }

        backAzimuth = normalizeBearing(backAzimuth);

        return backAzimuth;
    }

    private static double normalizeBearing(final double bearing) {
        return (bearing + 360) % 360;
    }

    private static BigDecimal normalizeBearing(final BigDecimal bearing) {
        return bearing.add(BD_360).remainder(BD_360);
    }
}
