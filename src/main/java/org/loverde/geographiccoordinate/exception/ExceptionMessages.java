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

package org.loverde.geographiccoordinate.exception;


public class ExceptionMessages {

    public static final String LAT_LON_RANGE_ERROR = """
        Value out of range.  Values must be within the following ranges (inclusive):

        Decimal:  -%f to %f

        Degrees:  0 to %d
        Minutes:  0 to 59
        Seconds:  0 to 59.9[...]
        (Minutes and seconds must be 0 when degrees is %d)
        """;
    public static final String DIRECTION_NULL = "Direction is null";
    public static final String DIRECTION_CANT_BE_NEITHER = "Direction can only be NEITHER when the value is 0.0";
    public static final String BEARING_OUT_OF_RANGE = "Bearing is out of range [0, 360]";
    public static final String COMPASS_TYPE_NULL = "Compass type is null";
    public static final String BEARING_NULL = "Bearing is null";
    public static final String LATITUDE_NULL = "Latitude is null";
    public static final String LONGITUDE_NULL = "Longitude is null";

    public static final String STARTING_POINT_NULL = "Starting point is null";
    public static final String BEARING_TO_NULL = "'to' is null";
    public static final String BEARING_FROM_LATITUDE_NULL = "'from' latitude is null";
    public static final String BEARING_FROM_LONGITUDE_NULL = "'from' longitude is null";
    public static final String BEARING_TO_LATITUDE_NULL = "'to' latitude is null";
    public static final String BEARING_TO_LONGITUDE_NULL = "'to' longitude is null";
}
