package servlet;

import dao.DaoFactory;
import model.*;
import model.libraryModel.*;
import model.servletModel.ServletMessage;
import model.transferModel.UploadTask;
import util.DBUtil;
import util.ServletUtil;

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

        // Get media type
        MediaTypeEnum mediaType = ServletUtil.parseMediaType(req.getParameter("mediaType"));

        // Get basic file characteristics
        String SHA256 = null;
        long size = 0L;
        if (mediaType != MediaTypeEnum.TV_SHOW) {
            SHA256 = req.getParameter("SHA256").toUpperCase();
            if (SHA256.trim().isEmpty()) {
                sendErrorMessage(resp, "SHA256 should not be empty.");
                return;
            }
            try {
                size = Long.parseLong(req.getParameter("size"));
            } catch (NumberFormatException e) {
                sendErrorMessage(resp, "Size should not be empty.");
                return;
            }
        }

        // Get basic metadata info
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
                    TVShow tvShow = parseTVShow(req, resp);
                    tvShow.setOwnerId(ownerId);
                    DaoFactory.getLibraryItemDao().add(tvShow);
                    requestType = "metadata";
                }
                break;
                case EPISODE:
                {
                    Episode episode = parseEpisode(req, resp);
                    episode.getTvShow().setOwnerId(ownerId);
                    episode.setOwnerId(ownerId);
                    episode.setSHA256(SHA256);
                    episode.setSize(size);
                    transferTaskItem = episode;
                }
                break;
                default: throw new IllegalArgumentException("Not supported media type.");
            }

            // Add to database
            if (mediaType != MediaTypeEnum.TV_SHOW) {
                DaoFactory.getLibraryItemDao().add(transferTaskItem);
            }
        } catch (FieldMissingException e) {
            // If any key field is missing, stop processing
            return;
        }

        // Create an upload task
        UploadTask uploadTask = null;
        if (mediaType != MediaTypeEnum.TV_SHOW)
            uploadTask = new UploadTask((transferTaskItem), false);

        // If requestType == "metadata", add the task to the completed queue and stop here
        if (requestType.equalsIgnoreCase("metadata")) {
            if (mediaType != MediaTypeEnum.TV_SHOW)
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
        if (genre != null && !genre.trim().isEmpty()) {
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

    private TVShow parseTVShow(HttpServletRequest req, HttpServletResponse resp) throws FieldMissingException, IOException {
        TVShow tvShow = new TVShow();
        // Not null IMDB
        try {
            int imdb = Integer.parseInt(req.getParameter("imdb"));
            tvShow.setImdb(imdb);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of IMDB.");
            throw new FieldMissingException();
        }
        // Not null season
        try {
            int season = Integer.parseInt(req.getParameter("season"));
            tvShow.setSeason(season);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of season.");
            throw new FieldMissingException();
        }
        // Not null title
        String title = req.getParameter("title");
        if (title.trim().isEmpty()) {
            sendErrorMessage(resp, "Title cannot be empty.");
            throw new FieldMissingException();
        }
        tvShow.setTitle(title);
        try {
            int releaseYear = Integer.parseInt(req.getParameter("releaseYear"));
            tvShow.setReleaseYear(releaseYear);
        } catch (NumberFormatException e) {
        }
        String plot = req.getParameter("plot");
        if (!plot.trim().isEmpty()) {
            tvShow.setPlot(plot);
        }
        try {
            int runtime = Integer.parseInt(req.getParameter("runtime"));
            tvShow.setRuntime(runtime);
        } catch (NumberFormatException e) {
        }
        String thumbUrl = req.getParameter("thumbUrl");
        if (!thumbUrl.trim().isEmpty()) {
            tvShow.setThumbUrl(thumbUrl);
        }
        try {
            double rating = Double.parseDouble(req.getParameter("rating"));
            tvShow.setRating(rating);
        } catch (NumberFormatException e) {
        }
        // Genre
        // TODO: It's now only one genre
        String genre = req.getParameter("genre");
        if (genre != null && !genre.trim().isEmpty()) {
            tvShow.setGenre(new String[] { genre });
        }
        return tvShow;
    }

    private Episode parseEpisode(HttpServletRequest req, HttpServletResponse resp) throws FieldMissingException, IOException {
        TVShow tvShow = new TVShow();
        Episode episode = new Episode();
        // Not null IMDB
        try {
            int imdb = Integer.parseInt(req.getParameter("imdb"));
            tvShow.setImdb(imdb);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of IMDB.");
            throw new FieldMissingException();
        }
        // Not null season
        try {
            int season = Integer.parseInt(req.getParameter("season"));
            tvShow.setSeason(season);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of season.");
            throw new FieldMissingException();
        }
        episode.setTvShow(tvShow);
        // Not null episodeNo
        try {
            int episodeNo = Integer.parseInt(req.getParameter("episodeNo"));
            episode.setEpisodeNo(episodeNo);
        } catch (NumberFormatException e) {
            sendErrorMessage(resp, "Invalid value of episodeNo.");
            throw new FieldMissingException();
        }
        episode.setTitle(req.getParameter("title"));
        try {
            int runtime = Integer.parseInt(req.getParameter("runtime"));
            episode.setRuntime(runtime);
        } catch (NumberFormatException e) {
        }
        episode.setStoryline(req.getParameter("storyline"));
        episode.setThumbUrl(req.getParameter("thumbUrl"));
        try {
            double rating = Double.parseDouble(req.getParameter("rating"));
            episode.setRating(rating);
        } catch (NumberFormatException e) {
        }
        return episode;
    }
}
