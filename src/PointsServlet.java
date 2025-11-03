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

//MANAGES PLAYERS POINT SIN THE GAMING PORTAL

public class PointsServlet extends HttpServlet{
	
	private Connection connection;
	
	//INIT() CREATES DATABSE CONNECTION 
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
		
		//RETRIEVES USERS GAMERTAG FROM SESSION 
		String gamerTag = (String) session.getAttribute("gamerTag");
		
		//TRANSACTION DETAILS FROM FORM SUBMISSION 
		String action = request.getParameter("action");
		int points = Integer.parseInt(request.getParameter("points"));
		int purchaseAmount = Integer.parseInt(request.getParameter("purchaseAmount"));
		
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
		//GET USERS CURRENT POINTS FROM DATABASE
		String selectSql ="SELECT points, gamerTag FROM users WHERE gamerTag = ?";
		pstmt = connection.prepareStatement(selectSql);
		pstmt.setString(1,  gamerTag);
		result = pstmt.executeQuery();
		
		int currentPoints = result.getInt("points");
		int newPoints = currentPoints;
		
	if(currentPoints > purchaseAmount) { //CALCULATE NEW POINTS AFTER TRANSACTION
		newPoints = currentPoints - purchaseAmount;
		
		PreparedStatement update = connection.prepareStatement(
				"UPDATE users SET points =? WHERE gamerTag =?");
	} else {// IF BALANCE GOES BELOW 0 SHOW ERROR
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Error! the purchase amount will send your balance below 0 points");
	}

	} catch(SQLException e) {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<p>Welcome back"+ gamerTag + "</p>");
		out.println("<p>You have :<b>" + points + "</b> points</p>");
	}
		
}
}
