package web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import schedule.Passenger;

/**
 * Servlet implementation class AddMarkerServlet
 */
public class AddPassengerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

    /**
     * Default constructor. 
     */
    public AddPassengerServlet() {
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
		// TODO Auto-generated method stub
		//PrintWriter out = response.getWriter();
		String pSrc = request.getParameter("src");
		String pDest = request.getParameter("dest");
		String pName = request.getParameter("pname");
		String sStarttime = request.getParameter("starttime");
		String sEndtime = request.getParameter("endtime");
		String sMaxresponsetime = request.getParameter("maxresponsetime");
		String count;
		Date dStartTime = convertStringToDate(sStarttime);
		Date dEndTime = convertStringToDate(sEndtime);
		Date dMaxresponsetime = convertStringToDate(sMaxresponsetime);
		/*out.println("Passenger name is "+pName);
		out.println("Passenger source is "+pSrc);
		out.println("Passenger destination is "+pDest);*/
		HttpSession session = request.getSession();
		count = (String)session.getAttribute("count");
		
		if(count==null)
		{
			ArrayList pArr = new ArrayList();
			Passenger passenger = new Passenger();
			passenger.setName(pName);
			passenger.setSource(pSrc);
			passenger.setDestination(pDest);
			passenger.setStarttime(dStartTime);
			passenger.setEndtime(dEndTime);
			passenger.setMaxresponsetime(dMaxresponsetime);
			pArr.add(passenger);
			request.setAttribute("passengerarr", pArr);
			
			session.setAttribute("count", "1");
		}
		else
		{
			
			//System.out.println("temp is : "+session.getAttribute("temp"));
			ArrayList pArr = (ArrayList)session.getAttribute("passengerarr");
			Passenger passenger = new Passenger();
			passenger.setName(pName);
			passenger.setSource(pSrc);
			passenger.setDestination(pDest);
			passenger.setStarttime(dStartTime);
			passenger.setEndtime(dEndTime);
			passenger.setMaxresponsetime(dMaxresponsetime);
			pArr.add(passenger);
			request.setAttribute("passengerarr", pArr);
		}
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("addpassenger.jsp");
		rd.forward(request, response);

		
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

}
