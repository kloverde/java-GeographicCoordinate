## UPCOMING RELEASE

* Consolidated duplicated constructor code


## Release 1.1.1 (November 18, 2015)

* toString() now prints the actual decimal value - not a rounded one


## Release 1.1 (November 10, 2015)

* Added a new class, Point, which acts a wrapper of Latitude and Longitude
* Added a DistanceCalculator class for calculating the distance between two Points


## Release 1.0.1 (June 10, 2015)

* IllegalArgumentException is no longer declared to be thrown from Latitude.setDirection(), Longitude.setDirection() or GeographicCoordinate.equals(), as it's a RuntimeException.  It can still be thrown from the setDirections on null input; it was never possible to be thrown from GeographicCoordinate.equals().
