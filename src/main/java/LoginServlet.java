import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Ваша логика проверки логина и пароля (например, сравнение с данными в базе данных)
        boolean isAuthenticated = authenticate(username, password);

        if (isAuthenticated) {
            // Устанавливаем атрибут сессии, указывающий, что пользователь вошел в систему
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Перенаправление на главную страницу с учетом логина пользователя
            String homeDirectory = "c:/Users/Student/filemanager/" + username; // Путь к домашней папке пользователя
            response.sendRedirect("main-servlet?path=" + URLEncoder.encode(homeDirectory, StandardCharsets.UTF_8.toString()));
        } else {
            // Если аутентификация не удалась, вы можете перенаправить обратно на страницу входа с сообщением об ошибке
            response.sendRedirect("login.jsp?error=1");
        }
    }

    // Пример простой аутентификации
    private boolean authenticate(String username, String password) {
        // Здесь вы можете добавить код для проверки логина и пароля в базе данных или другом хранилище данных
        // В этом примере аутентификация всегда успешна для простоты
        return true;
    }
}