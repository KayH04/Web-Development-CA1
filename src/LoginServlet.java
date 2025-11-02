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


public class LoginServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String gamerTag = request.getParameter("gamerTag");
		String password = request.getParameter("password");
		int points = request.getParameter("points");
		
		Connection connection;
		
		if(password.equals(password) && gamerTag.equals(gamerTag)) {
			PreparedStatement pstmt = connection.prepareStatement
					("SELECT password, gameTag, points FROM users");
			
			pstmt.setString(1, gamerTag);
			pstmt.setString(2, password);
			ResultSet result = pstmt.executeQuery();
			
		if(result.next()) {
			HttpSession session = request.getSession();
			session.setAttribute("gamerTag", gamerTag);
			session.setAttribute("points", result.getInt("points"));
			response.sendRedirect("index.html");
		}
		}
				
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
	}

}
