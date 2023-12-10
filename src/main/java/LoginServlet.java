import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Handling GET request to /login");
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Map<String, String> userDatabase = UserDataManager.getUserDatabase();
        LOGGER.info("Trying to authenticate user: " + username);

        if (authenticate(userDatabase, username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            LOGGER.info("Authentication successful. Redirecting to /main-servlet");

            response.sendRedirect("main-servlet");
        } else {
            LOGGER.warning("Authentication failed for user: " + username);
            response.sendRedirect("login.jsp?error=1");
        }
    }

    private boolean authenticate(Map<String, String> userDatabase, String username, String password) {
        String storedPassword = userDatabase.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}