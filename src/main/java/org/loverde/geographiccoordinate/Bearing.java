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

import java.math.BigDecimal;

import org.loverde.geographiccoordinate.compass.CompassDirection;
import org.loverde.geographiccoordinate.exception.GeographicCoordinateException;

import static java.math.BigDecimal.ZERO;
import static org.loverde.geographiccoordinate.exception.GeographicCoordinateException.Messages.BEARING_NULL;
import static org.loverde.geographiccoordinate.internal.Objects.failIf;


/**
 * A class containing an exact bearing and a mapping of the bearing to a general compass direction
 *
 * @param <T> An implementation of {@linkplain CompassDirection}
 */
public class Bearing<T extends CompassDirection> {

    private T compassDirection;
    private BigDecimal bearing;


    public Bearing() {
    }

    public Bearing(final T compassDirection, final BigDecimal bearing) {
        setCompassDirection(compassDirection);
        setBearing(bearing);
    }

    public T getCompassDirection() {
        return compassDirection;
    }

    public void setCompassDirection(final T compassDirection) {
        this.compassDirection = compassDirection;
    }

    public BigDecimal getBearing() {
        return bearing;
    }

    public void setBearing(final BigDecimal bearing) {
        failIf(bearing == null, () -> BEARING_NULL);
        failIf((bearing.compareTo(ZERO) < 0) || bearing.compareTo(new BigDecimal(360)) > 0, () -> GeographicCoordinateException.Messages.BEARING_OUT_OF_RANGE);

        this.bearing = bearing;
    }
}
