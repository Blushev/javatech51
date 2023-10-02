import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Map<String, String> userDatabase = new HashMap<>(); // Симуляция базы данных

    static {
        userDatabase.put("user1", "password1");
        userDatabase.put("user2", "password2");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (authenticate(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("main-servlet");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }

    private boolean authenticate(String username, String password) {
        String storedPassword = userDatabase.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
}