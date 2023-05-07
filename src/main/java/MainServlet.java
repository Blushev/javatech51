import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/main-servlet"})
public class MainServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        log("All Fine!");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        resp.getWriter().write("\nMethod Service");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String createDatingPage = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss").format(LocalDateTime.now());
        req.setAttribute("createDatingPage", createDatingPage);

        String path = getCurrentPath(req, resp);
        if (path == null) return;

        String backToParent = new File(path).getParent();
        if (backToParent != null) {
            req.setAttribute("directoryVisibilityOnTop", "block");
            req.setAttribute("backToParent", backToParent);
        } else {
            req.setAttribute("directoryVisibilityOnTop", "none");
        }

        // Таблица с содержимым текущей директории
        outputContentsDir(req, path);

        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private static String getCurrentPath(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getParameter("path");
        if (path == null) {
            path = System.getProperty("user.dir");
            resp.sendRedirect(String.format("%s%s?path=%s",
                    req.getContextPath(),
                    req.getServletPath(),
                    URLEncoder.encode(path, StandardCharsets.UTF_8.toString())));
            return null;
        }
        req.setAttribute("path", path);
        return path;
    }

    private void outputContentsDir(HttpServletRequest req, String path) {
        File f = new File(path);
        File[] allFiles = f.listFiles();

        if (allFiles != null) {
            List<File> directories = new ArrayList<>();
            List<File> files = new ArrayList<>();

            for (File file : allFiles) {
                if (file.getPath() != null) {
                    if (file.isDirectory())
                        directories.add(file);
                    else
                        files.add(file);
                }
            }

            req.setAttribute("directories", directories);
            req.setAttribute("files", files);
        }
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
