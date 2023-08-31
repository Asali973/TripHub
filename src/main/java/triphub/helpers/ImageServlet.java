package triphub.helpers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A servlet that serves images from a predefined directory based on the URL path info.
 * The servlet is mapped to "/images/*" URL pattern, meaning any request that matches this
 * pattern will be handled by this servlet.
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    /**
     * The base path where the images are stored. You may need to adjust this path
     * based on your deployment environment.
     */
    private static final String BASE_PATH = "/Users/brendan/EnvJEE/Tools/wildfly-18.0.0.Final/data/triphub/images";

    /**
     * Handles the HTTP GET requests. It retrieves an image from the BASE_PATH based on
     * the path info provided in the URL and sends it as a response.
     *
     * @param request  The HttpServletRequest object that contains the request the client made of the servlet.
     * @param response The HttpServletResponse object that contains the response the servlet returns to the client.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException      if an I/O error occurs while the servlet is handling the GET request.
     */
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
