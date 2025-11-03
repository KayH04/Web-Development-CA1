import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class RegistrationServlet extends HttpServlet{
	
	//ADDING INTO THE DATABASE GAMING PORTAL
	
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

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		
		
	String gamerTag = request.getParameter("gamerTag");
	String password1 = request.getParameter("Password1");
	String password2 = request.getParameter("Password2");
	int points = 500;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
	//VALIDATE THAT GAME TAG IS NOT ENPTY
	if (gamerTag == null || gamerTag.isEmpty()) {
		out.println("<html><body>Thanks, your gamer tag is: "
		+gamerTag+"</body></html>");
		return; }
	
	//VALIDATES PASSWORDS ARE NOT NULL AND MATCH
	if(!password1.equals(password2) || password1 == null) {
		out.println("<html><body.Error! The passwords you have entered do not match. "
				+ "Please try again. </body</html>");
		return;
	}
	PreparedStatement pstmt = null;
		
	try {
		//ADD GAMER TAG, PASSWORD AND POINTS TO MYSQL USING PREPARED STATEMENT	
		String sql ="INSERT INTO users (gamerTag, password, points) VALUES (?, ?, ?)";
		pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, gamerTag);
		pstmt.setString(2, password1);
		pstmt.setInt(3, 500);
		
		int rowsInserted = pstmt.executeUpdate();
		
		if(rowsInserted > 0) {
			response.sendRedirect("index.html");
			out.println("<html><body>Thank you for registering " + gamerTag +
					". You now have :" + points + " points. </body></html>");
		}else {
			out.println("<html><body>Error during registration - Please try again. </body></html>");
		}
		
	}catch(SQLException e) {
		out.println("<html><body>Database error</body></html");
		out.println("<pre>" + e.getMessage() + "</pre>");
		e.printStackTrace();
		
	}catch(Exception e) {
		out.println("<h3> General error occured: </h3>");
		out.println("<pre>" + e.getMessage() + "</pre>");
		e.printStackTrace();
	}


}
}

