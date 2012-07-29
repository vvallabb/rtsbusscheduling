package schedule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Bus {
	int busId;
	ArrayList route;
	Date busStartTime;
	Date busPresentTime;
	Date busEndTime;
	ArrayList tempPlaces;
	int presentCapacity;
	int maxCapacity=10;
	int averageSpeed=30;
	ArrayList confirmedPlaces;
	ArrayList passengers;
	ArrayList orderedPlaces;
	String source;
	
	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public ArrayList getOrderedPlaces() {
		return orderedPlaces;
	}


	public void setOrderedPlaces(ArrayList orderedPlaces) {
		this.orderedPlaces = orderedPlaces;
	}


	public ArrayList getPassengers() {
		return passengers;
	}


	public void setPassengers(ArrayList passengers) {
		this.passengers = passengers;
	}


	public ArrayList getConfirmedPlaces() {
		return confirmedPlaces;
	}


	public void setConfirmedPlaces(ArrayList confirmedPlaces) {
		this.confirmedPlaces = confirmedPlaces;
	}


	public int getPresentCapacity() {
		return presentCapacity;
	}


	public void setPresentCapacity(int presentCapacity) {
		this.presentCapacity = presentCapacity;
	}

	
	
	public ArrayList getTempPlaces() {
		return tempPlaces;
	}


	public void setTempPlaces(ArrayList tempPlaces) {
		this.tempPlaces = tempPlaces;
	}


	public Date getBusPresentTime() {
		return busPresentTime;
	}


	public void setBusPresentTime(Date busPresentTime) {
		this.busPresentTime = busPresentTime;
	}
	
	public void setBusPresentTime(Date previousTime,double distance){
		Calendar c = Calendar.getInstance();
		c.setTime(previousTime);
		c.add(Calendar.MINUTE, ((int)(distance*60/averageSpeed)+1));
		setBusPresentTime(c.getTime());
	}

	public Date getBusEndTime() {
		return busEndTime;
	}


	public void setBusEndTime(Date busEndTime) {
		this.busEndTime = busEndTime;
	}
	
	public void setBusEndTime()
	{
		Date busStartTime = getBusStartTime();
		Calendar c = Calendar.getInstance();
		c.setTime(busStartTime);
		c.add(Calendar.MINUTE, 240);
		setBusEndTime(c.getTime());
	}

	public Date getBusStartTime() {
		return busStartTime;
	}

	public void setBusStartTime(Date busStartTime) {
		this.busStartTime = busStartTime;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}
	
	public ArrayList getRoute() {
		return route;
	}

	public void setRoute(ArrayList route) {
		this.route = route;
	}

	public ArrayList getHybridNearestNeighbour(String source,ArrayList placesList)
	{
		ArrayList places;
		places = clonePlaces(placesList);
		
		Place firstNeighbour = findFirstNearestNeighbour(source,places);
		if(firstNeighbour==null)
		{
			fixOrderOfPlaces();
			return getOrderedPlaces();
		}
		CityDetails cd = new CityDetails();
		double distance = cd.getDistanceBetweenPlaces(source, firstNeighbour.getName());
		setBusStartTime(findBusStartTime(firstNeighbour,distance));
		System.out.print("Bus "+getBusId()+" is starting at "+source+" on "+getBusStartTime()+" and it is going");
		setBusEndTime();
		setBusPresentTime(getBusStartTime(),distance);
		
		if(getBusPresentTime().before(getBusEndTime()))
		{
		ArrayList tempPlaces = new ArrayList();
		tempPlaces.add(firstNeighbour);
		setTempPlaces(tempPlaces);
		places.remove(firstNeighbour);
		selectSourcePassengers(firstNeighbour);
		selectDestinationPassengers(firstNeighbour,placesList);
			if(places.size()==0)
			{
				fixOrderOfPlaces();
				return getOrderedPlaces();
			}
		}
		else{
			fixOrderOfPlaces();
			return getOrderedPlaces();
		}
		source = firstNeighbour.getName();
		while(true)
		{
			Place nearestNeighbour = findNearestNeighbour(source,places);
			if(nearestNeighbour==null)
			{
				fixOrderOfPlaces();
				return getOrderedPlaces();
			}
			distance = cd.getDistanceBetweenPlaces(source, nearestNeighbour.getName());
			setBusPresentTime(getBusPresentTime(),distance);
			if(getBusPresentTime().before(getBusEndTime()))
			{
			ArrayList tempPlaces = getTempPlaces();
			tempPlaces.add(nearestNeighbour);
			setTempPlaces(tempPlaces);
			places.remove(nearestNeighbour);
			selectSourcePassengers(nearestNeighbour);
			selectDestinationPassengers(nearestNeighbour,placesList);
				if(places.size()==0)
				{
					fixOrderOfPlaces();
					return getOrderedPlaces();
				}
			}
			else{
				fixOrderOfPlaces();
				return getOrderedPlaces();
			}
			source = nearestNeighbour.getName();
		}
		
		
	}

	private void selectDestinationPassengers(Place firstNeighbour,ArrayList placesList) {
		if(firstNeighbour.getNo_of_pax_out()!=0)
		{
			ArrayList placePassengers = firstNeighbour.getPassengers();
			Iterator placePassengerIterator = placePassengers.iterator();
			while(placePassengerIterator.hasNext())
			{
				Passenger passenger = (Passenger)placePassengerIterator.next();
				if(passenger.getDestination().equals(firstNeighbour.getName()) && passenger.getSourceConfirmedFlag()==1)
				{
					if(!getBusPresentTime().after(passenger.getEndtime()))
					{
						setPresentCapacity(getPresentCapacity()-1);
						passenger.setConfirmedFlag(1);
						passenger.setBusId(getBusId());
						ArrayList BusPassengers = getPassengers();
						if(BusPassengers==null)
						{
							BusPassengers = new ArrayList();
							BusPassengers.add(passenger);
							setPassengers(BusPassengers);
						}
						else
						{
							BusPassengers.add(passenger);
							setPassengers(BusPassengers);
						}
						
						Iterator placesIterator=placesList.iterator();
						while(placesIterator.hasNext())
						{
							Place place = (Place) placesIterator.next();
							ArrayList confirmedPlaces = getConfirmedPlaces();
							if(confirmedPlaces==null)
							{
								confirmedPlaces = new ArrayList();
							}
							if(passenger.getSource().equals(place.getName()))
							{
								int inNo = place.getNo_of_pax_in();
								inNo--;
								place.setNo_of_pax_in(inNo);
								confirmedPlaces.add(place);
							}
							if(passenger.getDestination().equals(place.getName()))
							{
								int outNo = place.getNo_of_pax_out();
								outNo--;
								place.setNo_of_pax_out(outNo);
								confirmedPlaces.add(place);
							}
							
							setConfirmedPlaces(confirmedPlaces);
						}						
					}
				}
			}
			
		}
		
	}


	private void selectSourcePassengers(Place firstNeighbour) {
		if(firstNeighbour.getNo_of_pax_in()!=0)
		{
			ArrayList placePassengers = firstNeighbour.getPassengers();
			Iterator placePassengerIterator = placePassengers.iterator();
			while(placePassengerIterator.hasNext())
			{
				Passenger passenger = (Passenger)placePassengerIterator.next();
				if(passenger.getSource().equals(firstNeighbour.getName()))
				{
					if(!getBusPresentTime().before(passenger.getStarttime()) && !getBusPresentTime().after(passenger.getMaxresponsetime()))
					{
						if(getPresentCapacity()<maxCapacity)
						{
						passenger.setSourceConfirmedFlag(1);
						setPresentCapacity(getPresentCapacity()+1);
						}
					}
				}
			}
		}
		
	}


	private ArrayList clonePlaces(ArrayList placesList) {
		Iterator placesIterator = placesList.iterator();
		ArrayList places = new ArrayList();
		
		while(placesIterator.hasNext())
		{
			Place place = (Place)placesIterator.next();
			places.add(place);
		}
		return places;
	}


	

	private void fixOrderOfPlaces() {
		ArrayList tempPlaces = getTempPlaces();
		ArrayList confirmedPlaces = getConfirmedPlaces();
		boolean flag = false;
		
		if(confirmedPlaces!=null)
		{
		Iterator tempPlacesIterator = tempPlaces.iterator();
		while(tempPlacesIterator.hasNext())
		{
			Place tempPlace = (Place)tempPlacesIterator.next();
			Iterator confirmedPlacesIterator = confirmedPlaces.iterator();
			flag = false;
			while(confirmedPlacesIterator.hasNext())
			{
				Place confirmedPlace = (Place)confirmedPlacesIterator.next();
				if(confirmedPlace.getName().equals(tempPlace.getName()))
				{
					flag = true;
				}
			}
			if(flag==true)
			{
				ArrayList finalPlaces = getOrderedPlaces();
				if(finalPlaces==null)
				{
					finalPlaces = new ArrayList();
				}
				System.out.print(" via "+tempPlace.getName());
				finalPlaces.add(tempPlace);
				setOrderedPlaces(finalPlaces);
			}
		}
		}
		
	}


	private Date findBusStartTime(Place place,double distance) {
		
		ArrayList passengers = place.getPassengers();
		Iterator passengerIterator = passengers.iterator();
		Date minTime = convertStringToDate("11:59 PM");
		
		while(passengerIterator.hasNext())
		{
			Passenger passenger = (Passenger)passengerIterator.next();
			Date passengerTime = passenger.getStarttime();
			if(passengerTime.before(minTime) && passenger.getSource().equals(place.getName()))
			{
				minTime = passengerTime;
			}
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(minTime);
		c.add(Calendar.MINUTE, -((int)(distance*60/averageSpeed)+1));
		return c.getTime();
	}
	
	private Date convertStringToDate(String sTime) {
		
		Calendar c = Calendar.getInstance();
		int today = c.getTime().getDate();
		int month = c.getTime().getMonth();
		int year = c.getTime().getYear();
		month++;
		year=year+1900;
		//System.out.println("Year is:"+year);
		//String Time = "12:10 AM";
		String str_date=today+"-"+month+"-"+year+" "+sTime;
        DateFormat formatter ; 
        Date date = null ;
  
         formatter = new SimpleDateFormat("dd-MM-yy hh:mm a");
             try {
				date = (Date)formatter.parse(str_date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
              //System.out.println("Today is " +date );
		
		return date;
	}

	private Place findFirstNearestNeighbour(String source,ArrayList places) {
		
		Iterator placesIterator = places.iterator();
		double minDistance=99999999.99999;
		double distance;
		Place nearestPlace=null;
		double hybridCalculatedDistance;
		
		while(placesIterator.hasNext())
		{
			Place place = (Place)placesIterator.next();
			CityDetails cd = new CityDetails();
			distance = cd.getDistanceBetweenPlaces(source, place.getName());
			hybridCalculatedDistance = 0.8 * distance + 0.2 * (1/(place.getNo_of_pax_in()+place.getNo_of_pax_out()));
				
			if(hybridCalculatedDistance<minDistance && place.getNo_of_pax_in()>0)
			{
				minDistance = hybridCalculatedDistance;
				nearestPlace = place;
			}
		}
		
		
		return nearestPlace;
	}
	
	private Place findNearestNeighbour(String source,ArrayList places) {
		
		Iterator placesIterator = places.iterator();
		double minDistance=99999999.99999;
		double distance;
		Place nearestPlace=null;
		double hybridCalculatedDistance;
		boolean sourceConfirmedFlag=false;
		
		while(placesIterator.hasNext())
		{
			Place place = (Place)placesIterator.next();
			CityDetails cd = new CityDetails();
			distance = cd.getDistanceBetweenPlaces(source, place.getName());
			hybridCalculatedDistance = 0.8 * distance + 0.2 * (1/(place.getNo_of_pax_in()+place.getNo_of_pax_out()));
				
			if(hybridCalculatedDistance<minDistance && (place.getNo_of_pax_in()>0 || place.getNo_of_pax_out()>0))
			{
				if(!(place.getNo_of_pax_in()>0))
				{
					ArrayList passengers = place.getPassengers();
					Iterator passengersIterator = passengers.iterator();
					sourceConfirmedFlag = false;
					while(passengersIterator.hasNext())
					{
						Passenger passenger = (Passenger)passengersIterator.next();
						if(passenger.getDestination().equals(place.getName()) && passenger.getSourceConfirmedFlag()==1)
							sourceConfirmedFlag = true;
					}
					
					if(sourceConfirmedFlag==true)
					{
						minDistance = hybridCalculatedDistance;
						nearestPlace = place;
					}
				}
				else
				{
				minDistance = hybridCalculatedDistance;
				nearestPlace = place;
				}
			}
		}
		
		
		return nearestPlace;
	}
	
	
}
