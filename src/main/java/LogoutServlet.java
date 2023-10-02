import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Удаление атрибута сессии, указывающего, что пользователь вошел в систему
        HttpSession session = request.getSession();
        session.removeAttribute("username");

        // Перенаправление на страницу входа после выхода
        response.sendRedirect("login.jsp");
    }
}