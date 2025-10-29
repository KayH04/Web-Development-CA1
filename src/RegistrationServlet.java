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



public class RegistrationServlet extends HttpServlet{
	
	//ADDING INTO THE DATABASE GAMING PORTAL
	public RegistrationServlet() throws Exception{
	
	private Connection connection;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		
		
	String gamerTag = request.getParameter("gamerTag");
	String password1 = request.getParameter("Password1");
	String password2 = request.getParameter("Password2");
	int points = 500;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
	if (gamerTag == null || gamerTag.isEmpty()) {
		out.println("<html><body>Thanks, your gamer tag is: "
		+gamerTag+"</body></html>");
		return; }
	
	
	if(!password1.equals(password2) || password1 == null) {
		out.println("<html><body.Error! The passwords you have entered do not match. "
				+ "Please try again. </body</html>");
		return;
	}
	
	if(password1.equals(password2)){
		out.println("<html><body>Thank you for registerin with us." + "\n" + 
		"You now have :" + points + " points </body></html>");
	}
		
	
	//ADD GAMER TAG, PASSWORD AND POINTS TO MYSQL USING PREPARED STATEMENT
		Connection connection;
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/gamingPortal","root","rootroot1");
		
		
		String sql ="INSERT INTO users (gamerTag, password, points) VALUES (?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, gamerTag);
		pstmt.setString(2, password1);
		pstmt.setInt(3, 500);
		int rowsInserted = pstmt.executeUpdate();
		if(rowsInserted > 0) {
			out.println("<html><body>Thank you for registering " + gamerTag +
					". You now have :" + points + " points. </body></html>");
		}else {
			out.println("<html><body>Error during registration - Please try again. </body></html>");
		}
		
	

}
	}























