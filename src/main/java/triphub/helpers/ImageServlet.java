package triphub.helpers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    private static final String BASE_PATH = "/Users/brendan/EnvJEE/Tools/wildfly-18.0.0.Final/data/triphub/images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1); // Ignore le slash initial
        File file = new File(BASE_PATH, filename);
        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
