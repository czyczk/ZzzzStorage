package servlet;

import dao.DaoFactory;
import model.*;
import model.libraryModel.FieldMissingException;
import model.libraryModel.FileAssociatedItem;
import model.libraryModel.MediaTypeEnum;
import model.libraryModel.Movie;
import model.servletModel.ServletMessage;
import model.transferModel.UploadTask;
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
import java.util.ArrayList;

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
        ArrayList<UploadTask> uploadTasks = (ArrayList<UploadTask>) req.getSession().getAttribute("uploadTasks");

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
        FileAssociatedItem transferTaskItem = null;
        try {
            switch (mediaType) {
                case MOVIE:
                {
                    Movie movie = parseMovie(req, resp);
                    movie.setOwnerId(ownerId);
                    movie.setSHA256(SHA256);
                    movie.setSize(size);
                    transferTaskItem = movie;
                }
                break;
                case MUSIC:
                {
                    // TODO
                }
                break;
                case TV_SHOW:
                {
                    // TODO
                }
                break;
                default: throw new IllegalArgumentException("Not supported media type.");
            }

            // Add to database
            DaoFactory.getLibraryItemDao().add(transferTaskItem);
        } catch (FieldMissingException e) {
            // If any key field is missing, stop processing
            return;
        }

        // Create an upload task
        UploadTask uploadTask = new UploadTask((transferTaskItem), false);

        // If requestType == "metadata", add the task to the completed queue and stop here
        if (requestType.equalsIgnoreCase("metadata")) {
            uploadTasks.add(uploadTask);
            return;
        }

        // If requestType == "full", get the file part as well
        // Get the file part
        Part filePart = req.getPart("inputFile");
        System.out.println(filePart.getSubmittedFileName());
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String[] fileNameParts = fileName.split(".");
        String extension = "";
        if (fileNameParts.length > 1) {
            extension = fileNameParts[fileNameParts.length - 1];
        }
        InputStream fileContentStream = filePart.getInputStream();

        // Target path : [fileDbPath]/[SHA256] + . + [extension]
        String fileDbPath = DBUtil.getFileDbDirectory() + "/";
        String targetFilename = SHA256 + "." + extension;
        String targetFullPath = fileDbPath + targetFilename;
        FileOutputStream fos = new FileOutputStream(targetFullPath);

        // Add the task to the processing queue
        uploadTask.setRunning(true);
        uploadTasks.add(uploadTask);

        // Refresh the page
        sendSuccessRedirection(resp, "upload.jsp");

        // Receive the uploading file
        byte[] buffer = new byte[4096];
        int len = 0;
        while ((len = (fileContentStream.read(buffer))) > 0) {
            fos.write(buffer, 0, len);
            uploadTask.setBytesTransferred(uploadTask.getBytesTransferred() + len);
        }
        fos.close();
        uploadTask.setRunning(false);
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
