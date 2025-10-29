package ;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegistrationServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException{
		
		
	String gamerTag = request.getParameter("Gamer Tag");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>Thanks, your gamer tag is: "
		+gamerTag+"</body></html>");
		
	
	String password = request.getParameter("Password");
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	}
	}
