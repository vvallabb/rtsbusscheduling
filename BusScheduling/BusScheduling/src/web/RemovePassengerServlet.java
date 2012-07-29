package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemovePassengerServlet
 */
public class RemovePassengerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemovePassengerServlet() {
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

		int removeCount = Integer.parseInt(request.getParameter("passenger"));
		HttpSession session = request.getSession();
		ArrayList pArr = (ArrayList)session.getAttribute("passengerarr");
		
		
		pArr.remove(removeCount);
		
		request.setAttribute("passengerarr", pArr);
		RequestDispatcher rd;
		
		if(pArr.size()==0)
		 rd = request.getRequestDispatcher("start.jsp");
		else
		 rd = request.getRequestDispatcher("addpassenger.jsp");
		
		rd.forward(request, response);
		
	}

}
