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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Handling GET request to /register");
        getServletContext().getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        Map<String, String> userDatabase = UserDataManager.getUserDatabase();
        LOGGER.info("Trying to register user: " + username);

        if (registerUser(userDatabase, username, password, email)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            LOGGER.info("Registration successful. Redirecting to /login.jsp");


            response.sendRedirect("login.jsp");
        } else {
            LOGGER.warning("Registration failed for user: " + username);
            response.sendRedirect("register.jsp?error=1");
        }
    }

    private boolean registerUser(Map<String, String> userDatabase, String username, String password, String email) {
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, password);
            return true;
        }
        return false; // Пользователь с таким именем уже существует
    }
}