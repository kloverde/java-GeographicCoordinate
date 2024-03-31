GeographicCoordinate 5.0.0
==========================

See LICENSE for this software's licensing terms.

GeographicCoordinate is a Java library for representing latitude, longitude and cardinal points of the compass, and
calculating distance and bearing between points.


## Features

* Unlike using floating-point primitives to represent latitude and longitude, GeographicCoordinate uses objects that
  enforce automatic range checking; there's no possibility of having an object with an invalid value.
* Coordinates can be initialized in floating-point form or as degrees, minutes and seconds
* Calculates the distance between two points, or the total travel distance between an unlimited number of points (using
  the Haversine formula).  Supports a wide array of units of distance.
* Calculates initial bearing and back azimuth
* Provides enumerations for 32, 16 and 8-point compass directions
* Look up a compass direction using a bearing or standard direction abbreviation


## Accuracy

Supposedly, the calculations of distance and bearing are accurate.  I didn't verify distance or bearing calculations in
the real world.  That being said:

* Distance was verified by interpolating a course I plotted on Bing Maps (see the DistanceCalculator JUnit tests).  The
  calculated value agreed with Bing's.

* Bearing was verified by comparing my calculations to two online calculators, and my values matched theirs.

Use this software at your own risk.


## Building

This project is known to build on Gradle 8.0.

1. Set values for `specVendor`, `implementationVendor` and `builtBy` in _gradle.properties_
2. Run `gradlew build`
