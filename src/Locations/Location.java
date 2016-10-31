package Locations;

import java.util.Comparator;


public class Location implements Comparable<Location> {

	private String Name;
	private double Latitude;
	private double Longitude;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name of the location
	 * @param lat
	 *            Latitude
	 * @param lon
	 *            Longitude
	 */
	public Location(String name, double lat, double lon) {
		this.Name = name;
		this.Latitude = lat;
		this.Longitude = lon;
	}

	/**
	 * Distance between two locations
	 * 
	 * @param dest
	 *            Destination Location
	 * @return Integer indication miles between the two places
	 */
	public int distanceTo(Location dest) {
		return (int) Math.round(Location.Coord_Distance(this.Latitude, this.Longitude, dest.getLatitude(), dest.getLongitude()));
	}

	/**
	 * Finds the distance between the start and end locations
	 * 
	 * @param start
	 * @param end
	 * @return Integer indication miles between the two places
	 */

	public static int distance(Location start, Location end) {
		return (int) Math.round(Location.Coord_Distance(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude()));
	}

	/**
	 * Finds the Distance on the earth between two coordinates in miles. It uses
	 * Haversine's formula and a normalized earth diameter so that accuracy is
	 * close to 1% has issues with points on the opposite sides of the earth.
	 * 
	 * @param lat1
	 *            latitude of the first location
	 * @param long1
	 *            longitude of the first location
	 * @param lat2
	 *            latitude of the second location
	 * @param long2
	 *            longitude of the second location
	 * @return Distance in miles between 2 coordinates.
	 */
	protected static double Coord_Distance(double lat1, double long1, double lat2, double long2) {

		double _eQuatorialEarthRadius = 3963.1905D;
		double _d2r = (Math.PI / 180D);
		double dlong = (long2 - long1) * _d2r;
		double dlat = (lat2 - lat1) * _d2r;
		double a = Math.pow(Math.sin(dlat / 2D), 2D)
				+ (Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r) * Math.pow(Math.sin(dlong / 2D), 2D));
		double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
		double d = _eQuatorialEarthRadius * c;

		return d;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return Name;

	}
	
	@Override
	public int compareTo(Location l) {

		return this.Name.compareToIgnoreCase(l.Name);

	}

	public double getLatitude() {
		return Latitude;
	}

	public double getLongitude() {
		return Longitude;
	}
	
	public String getName()
	{
		return Name;
	}
	public static Comparator<Location> LocationNameComparator = new Comparator<Location>() {
		@Override
		public int compare(Location L1, Location L2) {
			return L1.Name.compareToIgnoreCase(L2.Name);
		}
	};

}