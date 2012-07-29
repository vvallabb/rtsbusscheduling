package schedule;

import java.util.ArrayList;
import java.util.Iterator;

public class BusScheduler {
	
	private int maxBuses=10;

	public ArrayList schedule(ArrayList passengers)
	{
		ArrayList places = fillPlaces(passengers);
		/*Iterator it = places.iterator();
		
		while(it.hasNext())
		{
			Place place = (Place)it.next();
			System.out.print("Place : "+place.getName());
			System.out.print("No of pax in :"+place.getNo_of_pax_in());
			System.out.print("No of pax out :"+place.getNo_of_pax_out());
			ArrayList passengersArr = place.getPassengers();
			Iterator passengersIt = passengersArr.iterator();
			while(passengersIt.hasNext())
			{
				Passenger p = (Passenger)passengersIt.next();
				System.out.print("Passenger : "+p.getName());
			}
		}*/
		
		ArrayList buses = new ArrayList();
		int count=0;
		
		while(places.size()!=0)
		{
			Bus bus = new Bus();
			bus.setBusId(count);
			count++;
			String source = "Electronic City";
			bus.setSource(source);
			ArrayList route = bus.getHybridNearestNeighbour(source,places);
			if(route!=null)
			{
			bus.setRoute(route);
			buses.add(bus);
			}
			Iterator placesIterator = places.iterator();
			ArrayList removePlace = new ArrayList();
			while(placesIterator.hasNext())
			{
				Place place = (Place) placesIterator.next();
				if(place.getNo_of_pax_in()==0 && place.getNo_of_pax_out()==0)
				{
					removePlace.add(place);
				}
			}
			
			Iterator removePlaceIterator = removePlace.iterator();
			while(removePlaceIterator.hasNext())
			{
				places.remove(removePlaceIterator.next());
			}
			
			if(count==maxBuses)
				break;
		}
		
		return buses;
	}
	
	public ArrayList fillPlaces(ArrayList passengers)
	{
		ArrayList places = new ArrayList();
		
		Iterator placesIterator;
		Iterator passengerIterator = passengers.iterator();
		boolean sourceFlag=false;
		boolean destinationFlag=false;
		
		while(passengerIterator.hasNext())
		{
			placesIterator = places.iterator();
			Passenger passenger = (Passenger)passengerIterator.next();
			String passengerSource = passenger.getSource();
			String passengerDestination = passenger.getDestination();
			sourceFlag=false;
			destinationFlag=false;
			
			while(placesIterator.hasNext())
			{
				Place place = (Place) placesIterator.next();
				
				if(place.getName().equals(passengerSource))
				{
					int no_of_pax = place.getNo_of_pax_in();
					no_of_pax++;
					place.setNo_of_pax_in(no_of_pax);
					ArrayList passengerArr = place.getPassengers();
					passengerArr.add(passenger);
					place.setPassengers(passengerArr);
					sourceFlag=true;
				}
				else if(place.getName().equals(passengerDestination))
				{
					int no_of_pax = place.getNo_of_pax_out();
					no_of_pax++;
					place.setNo_of_pax_out(no_of_pax);
					ArrayList passengerArr = place.getPassengers();
					passengerArr.add(passenger);
					place.setPassengers(passengerArr);
					destinationFlag=true;
				}
				
			}
			
			if(sourceFlag==false)
			{
				
				Place place1 = new Place();
				place1.setName(passengerSource);
				place1.setNo_of_pax_in(1);
				ArrayList passengerArr = new ArrayList();
				passengerArr.add(passenger);
				place1.setPassengers(passengerArr);
				places.add(place1);
			}
			if(destinationFlag==false)
			{
				Place place1 = new Place();
				place1.setName(passengerDestination);
				place1.setNo_of_pax_out(1);
				ArrayList passengerArr = new ArrayList();
				passengerArr.add(passenger);
				place1.setPassengers(passengerArr);
				places.add(place1);
				
			}
		}
		
		
		return places;
	}
	
}
