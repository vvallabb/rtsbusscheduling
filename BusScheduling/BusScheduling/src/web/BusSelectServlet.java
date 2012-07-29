package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import schedule.Bus;
import schedule.CityDetails;
import schedule.Place;

/**
 * Servlet implementation class BusSelectServlet
 */
public class BusSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BusSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int selectBusId = Integer.parseInt(request.getParameter("busselection"));
		HttpSession session = request.getSession();
		ArrayList buses = (ArrayList)session.getAttribute("buses");
		ArrayList placeNames = new ArrayList();
		Iterator busesIterator = buses.iterator();
	
		while(busesIterator.hasNext())
		{
			Bus bus = (Bus)busesIterator.next();
			if(bus.getBusId()==selectBusId)
			{
				ArrayList route = bus.getRoute();
				
				
				Iterator routeIterator = route.iterator();
				
				placeNames.add(bus.getSource());
				System.out.println(bus.getSource());
				while(routeIterator.hasNext())
				{
					Place place = (Place) routeIterator.next();
					placeNames.add(place.getName());
					System.out.println(place.getName());
				}
				break;
			}
		}
		
			
		String url = constructUrl(placeNames);
		request.setAttribute("url", url);
		RequestDispatcher rd = request.getRequestDispatcher("displayMap.jsp");
		rd.forward(request, response);
	}

	private String constructUrl(ArrayList placeNames) {
		
		Iterator placeNamesIterator = placeNames.iterator();
		int count = 1;
		String source = null;
		String destination = null;
		String url = "http://in.maps.yahoo.com/#?";
		int zoomValue = 6;
		CityDetails cd = new CityDetails();
		
		while(placeNamesIterator.hasNext())
		{
			String place = (String)placeNamesIterator.next();
			if(count==1)
				source=place;
			else if(count==placeNames.size())
				destination=place;
			count++;
		}
		
		
		url = url+"lat="+cd.getLat(source)+"&lon="+cd.getLon(source)+"&z="+zoomValue+"&";
		url=url+"plat="+cd.getLat(destination)+"&"+"plon="+cd.getLon(destination)+"&";
		url=url+"pz=5&addr=From%20"+source+"%20to%20"+destination;
		
		placeNamesIterator = placeNames.iterator();
		count=1;
		while(placeNamesIterator.hasNext())
		{
			String place = (String)placeNamesIterator.next();
			if(count!=1 && count!=placeNames.size())
			{
				url=url+"%20via%20"+place;
			}
			count++;
		}
		
		System.out.println("url:"+url);
		
		return url;
	}

}
