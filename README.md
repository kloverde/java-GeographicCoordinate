GeographicCoordinate 4.2
========================

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

Supposedly, the calculations of distance and bearing are accurate.  I didn't verify distance or bearing calculations in the real world.  Doing so would have required purchasing a military-issue compass and then taking it on a hike.  The hiking part sounded fine to me, but the purchase of an expensive compass did not.  That being said:

Distance was verified by interpolating a course I plotted on Bing Maps (see the DistanceCalculator JUnit tests).  The calculated value agreed with Bing's.

Bearing was verified by comparing my calculations to two online calculators, and my values matched theirs.

Use this software at your own risk.


## Compile and Runtime Dependencies

1.  [NumberUtil](https://github.com/kloverde/java-NumberUtil) (included)


## Development Setup Dependencies

You only need to bother with these if you want to build using the same setup I used (see included project and build files).

1.  [BuildScripts](https://github.com/kloverde/BuildScripts)
2.  The Eclipse Buildship plugin (available on the Eclipse Marketplace if it wasn't pre-packaged with your version of Eclipse).  This is alluded to in the IDE Setup section below.
3.  Gradle


## IDE Setup

In the root directory, you'll find a subdirectory named `.eclipse_project_files`, which contains... you guessed it, Eclipse project files.  They enable you to import a pre-configured project that "just works" so that you don't have to fuss around.

If you're an Eclipse user, you'll know that this is not where Eclipse keeps them - rather, they belong in the project root directory.  I archived the project files in a different location so that they could be saved without interfering with people who like to set up projects their own way.

If you want to use them, read `.eclipse_project_files/README.txt` for a description of the Eclipse plugins you'll need to install.  Copy `.classpath` and `.project` to the project root directory, then import the project.


## Building

1.  Update the path to BuildScripts in gradle.properties
2.  Provide a value for builtByName in gradle.properties
3.  Provide the path to the NumberUtil jar in build.gradle


## Donations

https://paypal.me/KurtisLoVerde/5

Thank you for your support!
