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


/**
 * This class is a thin wrapper of a {@linkplain Latitude} and {@linkplain Longitude}.
 * It adds a {@link Point#Point(Latitude, Longitude, String) name} field.
 */
public class Point {

    private Latitude latitude;
    private Longitude longitude;

    private String name;


    /**
     * Creates a new Point object
     *
     * @param latitude  - {@linkplain Latitude}
     * @param longitude - {@linkplain Longitude}
     * @throws GeographicCoordinateException If either parameter is null
     */
    public Point(final Latitude latitude, final Longitude longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Creates a new Point object
     *
     * @param latitude  - {@linkplain Latitude}
     * @param longitude - {@linkplain Longitude}
     * @param name      - Use for identification, such as displaying a caption on a map.  Null is permitted.
     * @throws GeographicCoordinateException If any parameter is null
     */
    public Point(final Latitude latitude, final Longitude longitude, final String name) {
        this(latitude, longitude);
        setName(name);
    }

    public Latitude getLatitude() {
        return latitude;
    }

    private void setLatitude(final Latitude latitude) {
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude cannot be null");
        }

        this.latitude = latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    private void setLongitude(final Longitude longitude) {
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude cannot be null");
        }

        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name - Use for identification, such as displaying a label on a map
     */
    private void setName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }

        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * Compares this object to an {@code instanceof} Point.  All fields are compared.
     *
     * @param obj - The object to compare to
     * @return {@code true} if equal, {@code false} if not
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point other)) return false;

        if (getLatitude() == null) {
            if (other.getLatitude() != null) {
                return false;
            }
        } else if (!getLatitude().equals(other.getLatitude())) {
            return false;
        }

        if (getLongitude() == null) {
            if (other.getLongitude() != null) {
                return false;
            }
        } else if (!getLongitude().equals(other.getLongitude())) {
            return false;
        }

        if (getName() == null) {
            return other.getName() == null;
        } else return getName().equals(other.getName());
    }

    @Override
    public String toString() {
        return "%s {%s , %s}".formatted(getName(), getLatitude(), getLongitude());
    }
}
