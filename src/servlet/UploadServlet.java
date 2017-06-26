package servlet;

import dao.DaoFactory;
import model.*;
import model.libraryModel.*;
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
        req.setCharacterEncoding("UTF-8");
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
        String SHA256 = req.getParameter("SHA256").toUpperCase();
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
        System.out.println(req.getParameter("mediaType"));
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
                    Music music = parseMusic(req, resp);
                    music.setOwnerId(ownerId);
                    music.setSHA256(SHA256);
                    music.setSize(size);
                    transferTaskItem = music;
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
            // Refresh the page
            sendSuccessRedirection(resp, "upload.jsp");
            return;
        }

        // If requestType == "full", get the file part as well
        // Get the file part
        Part filePart = req.getPart("inputFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String[] fileNameParts = fileName.split("\\.");
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
        movie.setTitle(title);
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
        // Director
        // TODO: It's now only one director
        String director = req.getParameter("director");
        if (!director.trim().isEmpty()) {
            movie.setDirector(new String[] { director });
        }
        // Genre
        // TODO: It's now only one genre
        String genre = req.getParameter("genre");
        if (genre != null && !genre.trim().isEmpty()m) {
            movie.setGenre(new String[] { genre });
        }
        return movie;
    }

    private Music parseMusic(HttpServletRequest req, HttpServletResponse resp) throws IOException, FieldMissingException {
        Music music = new Music();
        // Not null title
        String title = req.getParameter("title");
        if (title.trim().isEmpty()) {
            sendErrorMessage(resp, "Title cannot be empty.");
            throw new FieldMissingException();
        }
        music.setTitle(title);
        // Not null album
        String album = req.getParameter("album");
        if (title.trim().isEmpty()) {
            sendErrorMessage(resp, "Album cannot be empty.");
            throw new FieldMissingException();
        }
        music.setAlbum(album);
        // Track
        try {
            int track = Integer.parseInt(req.getParameter("track"));
            music.setTrack(track);
        } catch (NumberFormatException e) {
        }
        // Duration
        try {
            int duration = Integer.parseInt(req.getParameter("duration"));
            music.setDuration(duration);
        } catch (NumberFormatException e) {
        }
        // Rating
        try {
            int rating = Integer.parseInt(req.getParameter("rating"));
            music.setRating(rating);
        } catch (NumberFormatException e) {
        }
        // Thumb URL
        String thumbUrl = req.getParameter("thumbUrl");
        if (!thumbUrl.trim().isEmpty()) {
            music.setThumbUrl(thumbUrl);
        }
        // Artist
        // TODO: It's now only one artist
        String artist = req.getParameter("artist");
        if (!artist.trim().isEmpty()) {
            music.setArtist(new String[] { artist });
        }
        // Genre
        // TODO: It's now only one genre
        String genre = req.getParameter("genre");
        if (genre != null && !genre.trim().isEmpty()) {
            music.setGenre(new String[] { genre });
        }
        return music;
    }
}
