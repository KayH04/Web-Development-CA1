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


//HANDLES USER AUTHENTICATION FOR THE GAMING PORTAL
public class LoginServlet extends HttpServlet{
	private Connection connection;
	
	
	public void init() throws ServletException{
		//CONNECT TO DATABASE "gamingPortal"
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/gamingPortal", "root", "rootroot1");
		} catch (SQLException e) {
			throw new ServletException("Database connection error", e); //PREVENTS SERVLET FROM LOADING
		}
	}
	
	//DOPOST() HANDLES POST REQUESTS FROM THE LOGIN FORM - VALIDATES USER CREDENTIALS
	/**
	 * REQUEST - CONTAINS GAMER TAG AND PASSWORD FROM LOGIN
	 * RESPONSE - SENDS SUCCESS REDIRECT OR ERROR MESSAGE TO USER
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String gamerTag = request.getParameter("gamerTag");
		String password = request.getParameter("password");
		
		PreparedStatement pstmt = null;
		ResultSet result = null;
		
		try {
			//SQL QUERY TO RETRIEVE DATA BASED ON GAMERTAG
			String sql = "SELECT password, gamerTag, points FROM users WHERE gamerTag = ?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  gamerTag);
			result = pstmt.executeQuery();
			
			//CHECK IF USER EXISTS
			if(result.next()) {
				String correctPassword = result.getString("password");
				
				if(password.equals(correctPassword)) { //USER FOUND 
				pstmt.setString(1, gamerTag);
				pstmt.setString(2, password);
				result = pstmt.executeQuery();
			
				HttpSession session = request.getSession();//CREATE/RETRIEVE SESSION FOR THE USER
				//STORE USER INFORMATION 
				session.setAttribute("gamerTag", gamerTag);
				session.setAttribute("points", result.getInt("points"));
				response.sendRedirect("index.html");//REDIRECT THE USER 
				
				} else {
				
				System.out.println("Incorrect password, please try again.");
				
				}
				
			} 
				
		} catch(SQLException e) {
			//ERROR DURING QUERY EXECUTION 
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<p>Invalid credentials. Please try again. </p>");
			out.println("<a href='login.html'>Return to Login</a>");
			out.println("</body></html>");
		
		
	
		}
	}
}
