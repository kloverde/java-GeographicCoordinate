## Release 1.0.1 (2015-06-10)

- IllegalArgumentException is no longer declared to be thrown from Latitude.setDirection(), Longitude.setDirection() or GeographicCoordinate.equals(), as it's a RuntimeException.  It can still be thrown from the setDirections on null input; it was never possible to be thrown from GeographicCoordinate.equals().