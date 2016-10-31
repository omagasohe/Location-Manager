package Locations;

import java.util.*;

@SuppressWarnings("serial")
public class LocationList extends ArrayList<Location> {

	public LocationList()
	{
		//List<Location> places = new ArrayList<Location>();

	}
		
	public Location closest(int index) {
		return closest(this.get(index));
	}

	public Location closest(Location start) {
		int min = 999999,idx=99;
		for (int i = 0; i < this.size();i++) {
			Location end = this.get(i);
			
			if(start == end )
				continue;
			
			int d = start.distanceTo(end);
			if ((d > 0) && (d < min)) {
				min = d;
				idx = i;
			}
		}
		return this.get(idx);
	}
	public int distanceBetween(int idx1,int idx2)
	{
		return this.get(idx1).distanceTo(this.get(idx2));
	}
	
}
