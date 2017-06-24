package servlet;

import model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Created by czyczk on 2017-6-24.
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new SecurityException("Do use POST method.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Collect user info
        User user = (User) req.getSession().getAttribute("activeUser");
        int ownerId = user.getId();

        String requestType = req.getParameter("requestType");
        /* requestType:
         * "metadata": receive only the metadata fields
         * "full": receive the metadata fields as well as the file itself
         */

        // Prepare to send messages
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");

        // Get basic file characteristics
        String SHA256 = req.getParameter("SHA256");
        if (SHA256.trim().isEmpty()) {
            sendErrorMessage(resp, "SHA256 should not be empty.");
            return;
        }
        long size = 0L;
        try {
            size = Long.parseLong(req.getParameter("size"));
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Size should not be empty.");
            return;
        }

        // Get basic metadata info
        MediaTypeEnum mediaType = parseMediaType(req.getParameter("mediaType"));
        try {
            switch (mediaType) {
                case MOVIE:
                {
                    Movie movie = parseMovie(req, resp);
                    // TODO: add to database
                }
            }
        } catch (FieldMissingException e) {
            // If any key field is missing, stop processing
            return;
        }

        // If requestType == "metadata", stop here
        if (requestType.equalsIgnoreCase("metadata")) return;

        // If requestType == "full", get file part as well
        // Get file part
        Part filePart = req.getPart("inputFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String[] fileNameParts = fileName.split(".");
        String extension = fileNameParts[fileNameParts.length - 1];
        InputStream fileContentStream = filePart.getInputStream();

        // Target path : [dbPath]/files/[SHA256] + . + [extension]
        String fileDbPath = DBUtil.getDbDirectory() + "/files/";
        String targetFilename = SHA256 + "." + extension;
        String targetFullPath = fileDbPath + targetFilename;

        // Receive the uploading file
        FileOutputStream fos = new FileOutputStream(targetFullPath);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = (fileContentStream.read(buffer))) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        fileContentStream.close();
    }

    private MediaTypeEnum parseMediaType(String str) {
        if (str.equalsIgnoreCase("movie")) {
            return MediaTypeEnum.MOVIE;
        } else if (str.equalsIgnoreCase("music")) {
            return MediaTypeEnum.MUSIC;
        } else if (str.equalsIgnoreCase("tv show")) {
            return MediaTypeEnum.TV_SHOW;
        } else {
            throw new IllegalArgumentException("Unsupported media type.");
        }
    }

    private void sendErrorMessage(HttpServletResponse resp, String message) throws IOException {
        ServletMessage sm = new ServletMessage("error", message);
        resp.getWriter().write(sm.toJson());
    }

    private void sendSuccessRedirection(HttpServletResponse resp, String address) throws IOException {
        ServletMessage sm = new ServletMessage("success", "upload.jsp");
        resp.getWriter().write(sm.toJson());
    }

    private Movie parseMovie(HttpServletRequest req, HttpServletResponse resp) throws FieldMissingException, IOException {
        Movie movie = new Movie();
        // Not null IMDB
        try {
            int imdb = Integer.parseInt(req.getParameter("imdb"));
            movie.setImdb(imdb);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of IMDB.");
            throw new FieldMissingException();
        }
        // Not null title
        String title = req.getParameter("title");
        if (title.trim().isEmpty()) {
            sendErrorMessage(resp, "Title cannot be empty.");
            throw new FieldMissingException();
        }
        try {
            int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
            movie.setReleaseYear(releaseYear);
        } catch (NumberFormatException e) {
        }
        String plot = req.getParameter("plot");
        if (!plot.trim().isEmpty()) {
            movie.setPlot(plot);
        }
        try {
            int duration = Integer.parseInt(req.getParameter("duration"));
            movie.setDuration(duration);
        } catch (NumberFormatException e) {
        }
        String thumbUrl = req.getParameter("thumbUrl");
        if (!thumbUrl.trim().isEmpty()) {
            movie.setThumbUrl(thumbUrl);
        }
        try {
            double rating = Double.parseDouble(req.getParameter("rating"));
            movie.setRating(rating);
        } catch (NumberFormatException e) {
        }
        return movie;
    }
}
