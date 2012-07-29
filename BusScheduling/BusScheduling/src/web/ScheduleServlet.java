package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import schedule.BusScheduler;

/**
 * Servlet implementation class AddMarkerServlet
 */
public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

    /**
     * Default constructor. 
     */
    public ScheduleServlet() {
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
		HttpSession session = request.getSession();
		ArrayList pArr = (ArrayList)session.getAttribute("passengerarr");
		
		BusScheduler busScheduler = new BusScheduler();
		ArrayList buses = busScheduler.schedule(pArr);
		request.setAttribute("buses", buses);
		
		if(buses.size()==0)
		{
			RequestDispatcher rd = request.getRequestDispatcher("noRoute.html");
			rd.forward(request, response);
		}
		else
		{
		RequestDispatcher rd = request.getRequestDispatcher("buseslink.jsp");
		rd.forward(request, response);
		}
	}

}
