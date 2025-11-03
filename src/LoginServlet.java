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


public class LoginServlet extends HttpServlet{
	private Connection connection;
	
	
	public void init() throws ServletException{
		//CONNECT TO DATABASE "gamingPortal"
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gamingPortal", "root", "rootroot1");
		} catch (SQLException e) {
			throw new ServletException("Database connection error", e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String gamerTag = request.getParameter("gamerTag");
		String password = request.getParameter("password");
		
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			String sql = "SELECT password, gamerTag, points FROM users WHERE gamerTag = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  gamerTag);
			result = pstmt.executeQuery();
			
			if(result.next()) {
				String correctPassword = result.getString("password");
				
				if(password.equals(correctPassword)) {
				pstmt.setString(1, gamerTag);
				pstmt.setString(2, password);
				result = pstmt.executeQuery();
			
				HttpSession session = request.getSession();
				session.setAttribute("gamerTag", gamerTag);
				session.setAttribute("points", result.getInt("points"));
				response.sendRedirect("index.html");
				
				} else {
				
				System.out.println("Incorrect password, please try again.");
				
				}
				
			} else {
				
			}
				
			
		} catch(SQLException e) {
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<p>Invalid credentials. Please try again. </p>");
			out.println("<a href='login.html'>Return to Login</a>");
			out.println("</body></html>");
		
		
	
		}
	}
}
