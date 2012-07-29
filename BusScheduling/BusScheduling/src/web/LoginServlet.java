package web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import schedule.CommonService;

/**
 * Servlet implementation class AddMarkerServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

    /**
     * Default constructor. 
     */
    public LoginServlet() {
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
		
		String sUsername = request.getParameter("uname");
		String sPassword = request.getParameter("upass");
		
		
		HttpSession session = request.getSession();
		
		CommonService cs = new CommonService();
		Connection c = cs.initiateCon();
		int validUser = cs.authenticateUser(sUsername, sPassword, c);
		cs.closeCon(c);
		
		
		if(validUser==1)
		{
		session.setAttribute("valid", "yes");
	
		RequestDispatcher rd = request.getRequestDispatcher("start.jsp");
		rd.forward(request, response);
		}
		else
		{
			request.setAttribute("valid", "no");
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
		
	}

}
