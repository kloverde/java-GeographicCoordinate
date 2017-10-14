GeographicCoordinate 3.0
========================

See LICENSE for this software's licensing terms.

GeographicCoordinate is a Java library for representing latitude, longitude and cardinal points of the compass, and calculating distance and bearing between points.


## Features

* Unlike using floating-point primitives to represent latitude and longitude, GeographicCoordinate enforces automatic range checking:  objects can never have invalid values.
* Coordinates can be initialized in floating-point form or as degrees, minutes and seconds, and manipulated in either form thereafter
* Calculates the distance between two points, or the total travel distance between an unlimited number of points (using the Haversine formula).  Supports a wide array of units of distance.
* Calculates initial bearing and back azimuth
* Provides enumerations for 32, 16 and 8-point compass directions
* Look up a compass direction using a bearing or standard direction abbreviation


## Accuracy

Supposedly, the calculations of distance and bearing are accurate.  I didn't hop into a car to verify distance, and I didn't buy an expensive military-issue compass to verify bearings.

Distance was verified by interpolating a course I plotted on Bing Maps (see the DistanceCalculator JUnit tests).  The calculated value agreed with Bing's.

Bearing was verified by comparing my calculations to two online calculators, and my values matched theirs.

Use this software at your own risk.


## Compile and Runtime Dependencies

1.  [NumberUtil](https://github.com/kloverde/java-NumberUtil) (included)


## Development Setup Dependencies

You only need to bother with these if you want to build using the same setup I used (see included project and build files).

1.  [BuildScripts](https://github.com/kloverde/BuildScripts)
2.  The Eclipse Buildship plugin (available on the Eclipse Marketplace)
3.  Gradle


## Building

1.  Update the path to BuildScripts in gradle.properties
2.  Provide a value for builtByName in gradle.properties
3.  Provide the path to the NumberUtil jar in build.gradle


## Donations

https://paypal.me/KurtisLoVerde/5

Thank you for your support!
