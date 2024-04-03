# Release 5.0.0 (April 3, 2024)

* Removed dependencies on my external `BuildScripts` and `NumberUtil` projects
* Retargeted at Java 17 + Gradle 8.0
* Upgraded to JUnit 5
* Upgraded Mockito
* The internals are updated to use modern Java features
* Removed reflection from the compass direction internals
* Added [Spotbugs](https://spotbugs.github.io/) to the build script

Breaking changes:

* `Latitude`, `Longitude` and `Point` are now records rather than classes (getter names changed, can no longer be extended)
* `AbstractGeographicCoordinate` has been removed, as well as the exception constants in its nested `Messages` class
* TODO: `IllegalArgumentException` has replaced `GeographicCoordinateException`.  `GeographicCoordinateException` has been removed.
* Exception messages have changed
* `toString(Locale)` has been removed in favor of `toString()`.  Apparently the international standard is to use U.S. formatting for coordinates.
* The `EnumHelper` API has changed (you weren't using an API that was labeled _internal_, were you? 👀)
* `Latitude.MAX_VALUE` and `Longitude.MAX_VALUE` have been changed to doubles

# Release 4.2.1 (May 8, 2021)

* Fixed a compilation error observed on OpenJDK 15/Linux where the 'degree' symbol was not a UTF-8 character.  Whatever it was previously, it's been replaced with a compliant character.  This changes the value returned by `toString` in `Latitude` and `Longitude`.

* The project's build script has been updated to be compatible with Gradle 7.0 and the latest version of [BuildScripts](https://github.com/kloverde/BuildScripts).

* Beginning with this release, releases will be source only.  This includes first-party dependencies such as [NumberUtil](https://github.com/kloverde/java-NumberUtil), which you'll need to build yourself.


# Release 4.2 (June 16, 2018)

* Added a `getPrintName` method to the `CompassDirection` classes.  This returns a grammatically correct version of `name()`, changing all letters to lowercase and all underscores to spaces


# Release 4.1.1 (June 13, 2018)

* Fixed incorrect exception messages.  When supplying an invalid value to the Latitude(double) or Longitude(double) constructors, the resulting exception message said that the lower bound for valid values was 0.  This is incorrect when using floating-point notation; valid values can be negative.  The actual validation logic was correct - this was only an issue with the literal text of the error message.

* Moved Eclipse project files to `.eclipse_project_files` so that my project setup isn't forced on everyone.  See `.eclipse_project_files/README.txt` for more information.

* Updated JUnit tests


# Release 4.1 (June 5, 2018)

* Distance calculations were incorrect (completely unusable) when units of centimeters or inches were specified.


# Release 4.0 (May 26, 2018)

This release ends support for Java 8 and 9.  Java 10 is now required, which is why this release has a major version bump despite the fact that the only code changes are on the testing side.  Read on...

* Tweaked the JUnit tests after discovering that they fail with Java 9 and 10.  [Java 8 introduced a floating point bug](https://bugs.openjdk.java.net/browse/JDK-8039915) which was fixed in Java 9, but the tests were only ever run on Java 8, so I was unaware that different JREs were giving different results for floating point calculations.  Although the tests were using an epsilon for floating-point comparisons, it wasn't large enough to account for the floating point change introduced in Java 9, so the tests failed.
* Upgraded Mockito to prevent "[Illegal reflective access](https://github.com/mockito/mockito/issues/1295)" when building on Java 9 and 10.

The reasons for ending support for Java 8 and 9 are:

1. Due to floating point differences, keeping the JUnit tests working from Java 8 to 10 would have required allowing a greater margin of error in calculations
2. The status of the JUnit tests on Java 9 is unknowable because Oracle ended support for Java 9.  They pulled the SDK from their site, so I can't install it to test.

If you build this project from source, and you use my [https://github.com/kloverde/BuildScripts](BuildScripts) project to do it, you'll need to pull the latest version.  I had to remove findbugs from BuildScripts because it's incompatible with Java 10 - plus, [the project is dead](https://mailman.cs.umd.edu/pipermail/findbugs-discuss/2016-November/004321.html).


# Release 3.0.1 (April 22, 2018)

Only the README has been updated.  The README incorrectly stated that the Latitude/Longitude classes are mutable.  They are not - this was a relic of prior behavior in v1.3 and earlier.


# Release 3.0 (October 13, 2017)

Major update.  This release contains breaking and non-breaking changes.

Breaking changes:

* The source and binaries now target Java 8
* New package structure.  Things you weren't meant to use in the first place (and which you probably aren't using) have been moved to a new 'internal' package.  You shouldn't need to refactor code resulting from this change unless you were doing something odd.
* DistanceCalculator has been moved to a new 'calculator' package
* GeographicCoordinateException has been moved to a new 'exception' package
* DistanceCalculator.distance(Unit, Latitude, Longitude, Latitude, Longitude) has been removed.  Use the existing vararg method distance(Unit, Point ...) instead.

Non-breaking changes:

* Added BearingCalculator, which calculates the initial bearing and back azimuth
* Added compass enumerations which represent the directions found on 32, 16 and 8-point compasses, such as north, northwest, etc.  The enumerations provide standard direction abbreviations as well as lookup by abbreviation and bearing.
* Added support for centimeters and inches to DistanceCalculator
* JUnit tests migrated from JUnit 3.8 to JUnit 4
* The Eclipse project files have been migrated to Buildship, so they no longer have hardcoded paths to my filesystem.  This will make it easier for others to import a working project, but requires the installation of the Buildship plugin.
* Updated Earth's volumetric mean radius in DistanceCalculator to NASA's latest figure (was 6371 km - now is 6371.008 km)


# Release 2.1.2 (March 16, 2016)

* Only the README has been updated


## Release 2.1.1 (March 6, 2016)

* Eliminated most exception wrapping.  Previously, IllegalArgumentException would be thrown for invalid input, which would then be wrapped in a GeographicCoordinateException and re-thrown.  After some consideration, it was decided that this served no useful purpose, and so GeographicCoordinateException is the lowest-level exception.  Exception wrapping is still performed in limited scenarios where the type of wrapped exception conveys additional meaningful information, such as IllegalStateException.


## Release 2.1 (March 6, 2016)

* Note:  getAbbreviation() for Latitude.Direction.NEITHER and Longitude.Direction.NEITHER now returns empty string instead of "NEITHER".
* Updated Latitude and Longitude's toString() to format seconds according to the default locale
* Added toString(Locale) to Latitude and Longitude to accommodate locale-specific decimal formatting


## Release 2.0.1 (March 4, 2016)

* Some javadoc fixes


## Release 2.0 (March 2, 2016)

* The Latitude, Longitude and Point classes have always implemented hashCode(), so as to allow their use in HashMaps.  Despite this, the classes were mutable, which, as you might know, is incorrect.  Given the choice of either making the classes immutable or taking away hashCode(), it was decided to make them immutable.  *If you are on a previous release and are currently using setters, you will need to refactor your code.*
* As an additional result of hiding the setters in Latitude, Longitude and Point, their no-arg constructors have been removed and the setters have been removed from the GeographicCoordinate interface.
* The GeographicCoordinateImpl class has been renamed to AbstractGeographicCoordinate to better reflect its nature.
* As a result of making the coordinate classes immutable, AbstractGeographicCoordinate's no-arg constructor has also been removed.  No client code should be impacted, since client code would have been instantiating the Latitude and Longitude classes only.
* Extending AbstractGeographicCoordinate is now disallowed and is enforced by throwing an exception from its constructors.  This is not a client class.  No client code should be impacted, since client code would have been instantiating the Latitude and Longitude classes only.
* GeographicCoordinateException has been changed from a checked exception to a runtime exception.  You can now decide for yourself whether to explicitly catch it during instantiation.
* Added a 'name' field to the Point class
* Added Direction.NEITHER to Latitude and Longitude to represent the Equator and Prime Meridian, since they are neither north, south, east nor west
* Added the international foot, U.S. survey foot and yard as units of length to DistanceCalculator
* Fixed NullPointerException in Latitude/Longitude's .equals() when null was passed


## Release 1.3 (February 21, 2016)

This release contains several breaking changes which were necessary for code cleanliness.  To minimize impact to third-party applications, it was decided to get all of them out of the way in a single release, rather than drag the process out.  Most if not all third-party applications will be unaffected by these changes, as they deal with obscure things.  The one change worth calling particular attention to is to DistanceCalculator.distance, explained below.

* Removed deprecated method DistanceCalculator.distance(point1, point2, Unit).  Use DistanceCalculator(Unit, Point ...) instead.
* Changed the parameter order of DistanceCalculator.distance(Latitude, Longitude, Latitude, Longitude, Unit).  Unit has been moved from the last parameter to the first parameter to be consistent with the vararg distance method.
* Removed the GeographicCoordinate.Type enum, as it was unnecessary.  No client code should be impacted, since client code would have been working with the Latitude and Longitude classes directly.
* Removed GeographicCoordinate.Type from all GeographicCoordinate constructors.  No client code should be impacted, since client code would have been working with the Latitude and Longitude classes directly.
* Removed GeographicCoordinateException.Messages.COORDINATE_TYPE_NULL, as it can no longer be thrown
* Changed GeographicCoordinateException.Messages from public to protected, as client code should not be using them.  The constants contained therein exist solely to keep the internal code and JUnit tests in sync.  The javadoc has been updated to indicate this.
* Changed the Latitude/Longitude toString methods to return a degree/minute/second format, such as 12�34'56.789"N.  Likewise, Point's toString method now uses this format.
* When using the Latitude(double) constructor to create a latitude of 0.0 (the Equator), the direction will be considered north.  Previously, it was considered south.  No client code should be impacted because it's never correct to examine the direction when talking about the Equator, since it is neither north nor south.
* When using the Longitude(double) constructor to create a longitude of 0.0 (the Prime Meridian), the direction will be considered east.  Previously, it was considered west.  No client code should be impacted because it's never correct to examine the direction when talking about the Prime Meridian, since it is neither east nor west.
* Integrated with the latest version of the BuildScripts project (https://github.com/kloverde/BuildScripts), adding findbugs integration, JUnit integration and new release packaging:  one archive contains all jars and the readme, changelog and license.
* Various javadoc updates


## Release 1.2.1 (February 15, 2016)

* Renamed the new method from today's earlier release:  DistanceCalculator.totalTravelDistance(Unit, Point ...) method is now DistanceCalculator.distance(Unit, Point ...).
* Deprecated DistanceCalculator.distance(Point, Point, Unit), as it has been rendered redundant by the vararg method above.  *This deprecated method will be removed in the near future - possibly in the next release.*
* Updated README and javadoc


## Release 1.2 (February 15, 2016)

* Added a totalTravelDistance method to DistanceCalculator.  This method calculates the total distance traveled between an unlimited number of points (A to B to C, etc.).
* The following constants have been removed from GeographicCoordinateException.Messages:  LATITUDE_1_NULL, LATITUDE_2_NULL, LONGITUDE_1_NULL, LONGITUDE_2_NULL, POINT_1_NULL, POINT_2_NULL, UNIT_NULL.
* Consolidated duplicated constructor code in the Latitude and Longitude classes


## Release 1.1.1 (November 18, 2015)

* toString() now prints the actual decimal value - not a rounded one


## Release 1.1 (November 10, 2015)

* Added a new class, Point, which acts a wrapper of Latitude and Longitude
* Added a DistanceCalculator class for calculating the distance between two Points


## Release 1.0.1 (June 10, 2015)

* IllegalArgumentException is no longer declared to be thrown from Latitude.setDirection(), Longitude.setDirection() or GeographicCoordinate.equals(), as it's a RuntimeException.  It can still be thrown from the setDirections on null input; it was never possible to be thrown from GeographicCoordinate.equals().
