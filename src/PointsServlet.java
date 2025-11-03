import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PointsServlet extends HttpServlet{
	
	private Connection connection;
	
		public void init() throws ServletException{
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/gamingPortal", "root", "rootroot1");
			} catch (SQLException e) {
				throw new ServletException("Database connection error", e);
			
		
			}
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		
		HttpSession session =request.getSession(false);
		if(session == null || session.getAttribute("gamerTag")== null) {
			response.sendRedirect("login.html");
			return;
		}
		String gamerTag;
		String password;
		int points;
		int purchaseAmount;
		
		
		
		PreparedStatement pstmt = connection.prepareStatement(
				"SELECT points, gamerTag FROM users");
		
	if(points > purchaseAmount) {
		
		PreparedStatement update = connection.prepareStatement(
				"UPDATE users SET points =?");
	} else {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Error! the purchase amount will send your balance below 0 points");
	}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<p>Welcome back"+ gamerTag + "</p>");
		out.println("<p>You have :<b>" + points + "</b> points</p>");
		
	}
		
}
