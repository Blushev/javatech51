import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final Map<String, String> userDatabase = new HashMap<>(); // Симуляция базы данных

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (registerUser(username, password, email)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("register.jsp?error=1");
        }
    }

    private boolean registerUser(String username, String password, String email) {
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, password);
            return true;
        }
        return false; // Пользователь с таким именем уже существует
    }
}