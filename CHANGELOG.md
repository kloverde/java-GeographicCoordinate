## Release 1.3 (UPCOMING RELEASE)

* Removed deprecated method DistanceCalculator.distance( point1, point2, Unit ).  Use DistanceCalculator( Unit, Point ... ).
* Integrated with the latest version of the BuildScripts project (https://github.com/kloverde/BuildScripts), adding findbugs support and new release packaging:  one archive contains all zips, readme, changelog and license.

## Release 1.2.1 (February 15, 2016)

* Renamed the new method from today's earlier release:  DistanceCalculator.totalTravelDistance( Unit, Point ... ) method is now DistanceCalculator.distance( Unit, Point ... ).
* Deprecated DistanceCalculator.distance( Point, Point, Unit ), as it has been rendered redundant by the vararg method above.  *This deprecated method will be removed in the near future - possibly in the next release.*
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
