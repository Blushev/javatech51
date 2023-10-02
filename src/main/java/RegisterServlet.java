import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Ваша логика регистрации нового пользователя (например, сохранение данных в базе данных)
        boolean isRegistered = registerUser(username, password, email);

        if (isRegistered) {
            // Устанавливаем атрибут сессии, указывающий, что пользователь вошел в систему
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("login.jsp"); // Перенаправление на страницу входа после успешной регистрации
        } else {
            // Если регистрация не удалась, вы можете перенаправить обратно на страницу регистрации с сообщением об ошибке
            response.sendRedirect("register.jsp?error=1");
        }
    }

    // Пример простой регистрации
    private boolean registerUser(String username, String password, String email) {
        // Здесь вы можете добавить код для сохранения данных пользователя в базе данных или другом хранилище данных
        // В этом примере регистрация всегда успешна для простоты
        return true;
    }
}