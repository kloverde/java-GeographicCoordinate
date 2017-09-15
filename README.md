GeographicCoordinate 3.0
========================

See LICENSE for this software's licensing terms.

GeographicCoordinate is a simple Java library for representing latitude and longitude, and for calculating distances and direction of travel.


## Features

* Unlike using floating-point primitives, GeographicCoordinate enforces automatic range checking:  objects can never have invalid values.
* Coordinates can be manipulated in floating-point form or as degrees, minutes and seconds.
* Calculate the distance between two points, or the total travel distance between an unlimited number of points (using the Haversine formula).  Supports a wide array of units of length.
* Determine the direction of travel between two points (as in points of a compass, such as north, northeast, etc.).  Provides representations of 32, 16 and 8-point compasses.
* Determine heading using two points (TODO)

Putting together the last three items, this library enables you to say, for instance, that you have traveled 12 km heading north-northwest on a heading of 123 degrees.


## Dependencies

1.  [BuildScripts](https://github.com/kloverde/BuildScripts)
2.  [NumberUtil](https://github.com/kloverde/java-NumberUtil) (included if you clone or fork the GeographicCoordinate repository)
3.  The Eclipse Buildship plugin (available on the Eclipse Marketplace) - only applicable if you plan on using the included Eclipse project files
4.  Gradle (if you plan on building from the command line using the provided build files)


## Building

1.  Update the path to BuildScripts in gradle.properties
2.  Provide a value for builtByName in gradle.properties
3.  Provide the path to the NumberUtil jar in build.gradle


## Donations

https://paypal.me/KurtisLoVerde/5

Thank you for your support!