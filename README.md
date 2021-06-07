GeographicCoordinate 4.2.1
==========================

See LICENSE for this software's licensing terms.

GeographicCoordinate is a Java library for representing latitude, longitude and cardinal points of the compass, and calculating distance and bearing between points.

Starting with v4.0, Java 10 or later is required.  See the v4.0 release notes in CHANGELOG.MD for an explanation of why this is so.  For lower JRE versions, use a release prior to v4.0.  There are no API or runtime differences between the last 3.x release and 4.0.


## Features

* Unlike using floating-point primitives to represent latitude and longitude, GeographicCoordinate uses objects that enforce automatic range checking; there's no possibility of having an object with an invalid value.
* Coordinates can be initialized in floating-point form or as degrees, minutes and seconds
* Calculates the distance between two points, or the total travel distance between an unlimited number of points (using the Haversine formula).  Supports a wide array of units of distance.
* Calculates initial bearing and back azimuth
* Provides enumerations for 32, 16 and 8-point compass directions
* Look up a compass direction using a bearing or standard direction abbreviation


## Accuracy

Supposedly, the calculations of distance and bearing are accurate.  I didn't verify distance or bearing calculations in the real world.  Doing so would have required purchasing a military-grade compass, which I presume would be expensive.  (Whether necessary or not, preciseness is a thing for me).  That being said:

Distance was verified by interpolating a course I plotted on Bing Maps (see the DistanceCalculator JUnit tests).  The calculated value agreed with Bing's.

Bearing was verified by comparing my calculations to two online calculators, and my values matched theirs.

Use this software at your own risk.


## Building

This project is known to build on Gradle 7.0.

1.  Get [BuildScripts](https://github.com/kloverde/BuildScripts)
2.  Build [NumberUtil](https://github.com/kloverde/java-NumberUtil) and publish it locally with `gradle publishtomavenlocal`
3.  Set `buildScriptsDir` (the path to BuildScripts) in gradle.properties
4.  Set `builtBy` in gradle.properties
5.  Run `gradle build`
