## Release 2.1.1 (March 6, 2016)

* Tweaked exception re-throwing


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
* Changed the Latitude/Longitude toString methods to return a degree/minute/second format, such as 12°34'56.789"N.  Likewise, Point's toString method now uses this format.
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
