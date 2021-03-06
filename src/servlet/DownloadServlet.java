package servlet;

import model.User;
import model.libraryModel.*;
import model.transferModel.DownloadTask;
import util.DBUtil;
import util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by czyczk on 2017-6-25.
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Get current active user
        User user = (User) req.getSession().getAttribute("activeUser");
        int ownerId = user.getId();
        ArrayList<DownloadTask> downloadTasks = (ArrayList<DownloadTask>) req.getSession().getAttribute("downloadTasks");

        // Get the SHA256, size and the indicated filename of the target file
        String SHA256 = req.getParameter("SHA256");
        long size = Long.parseLong(req.getParameter("size"));
        String indicatedFilename = req.getParameter("indicatedFilename");

        // Look for the target file
        File directory = new File(DBUtil.getFileDbDirectory());
        File[] files = directory.listFiles((dir, name) -> name.startsWith(SHA256));

        // Handle exceptions if no file is found
        if (files == null || files.length == 0) {
            throw new FileNotFoundException("No file with a SHA256 " + SHA256 + " was found.");
        }
        File target = null;
        for (File file : files) {
            if (file.length() == size) {
                target = file;
                break;
            }
        }
        String extension = target.getName().replace(SHA256, "");
        if (extension.length() > 0) {
            indicatedFilename += extension;
        }
        if (target == null) {
            throw new FileNotFoundException("Size conflict. No file with the same size was found.");
        }

        // Prepare to transfer the file
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType(getServletContext().getMimeType(target.getName()));
        resp.setHeader("Content-Length", String.valueOf(target.length()));
        resp.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + URLEncoder.encode(indicatedFilename, "UTF-8"));
        Files.copy(target.toPath(), resp.getOutputStream());

        // Get media type to generate a corresponding downloaded task
        MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());
        FileAssociatedItem item = FileAssociatedItem.createItem(mediaType, SHA256, size);
//        try {
//            item = FileAssociatedItem.createItem(mediaType, SHA256, size);
//            switch (mediaType) {
//                case MOVIE:
//                {
//                    int imdb = Integer.parseInt(req.getParameter("imdb"));
//                    Movie movie = (Movie) item;
//                    movie.setImdb(imdb);
//                    item = movie;
//                }
//                break;
//                case EPISODE:
//                {
//                    int imdb = Integer.parseInt(req.getParameter("imdb"));
//                    int season = Integer.parseInt(req.getParameter("season"));
//                    int episodeNo = Integer.parseInt(req.getParameter("episodeNo"));
//                    TVShow tvShow = new TVShow();
//                    tvShow.setOwnerId(ownerId);
//                    tvShow.setImdb(imdb);
//                    tvShow.setSeason(season);
//                    Episode episode = (Episode) item;
//                    episode.setTvShow(tvShow);
//                    episode.setEpisodeNo(episodeNo);
//                    item = episode;
//                }
//                break;
//            }
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
        item.setOwnerId(ownerId);
        DownloadTask task = new DownloadTask(item, indicatedFilename, false);
        downloadTasks.add(task);
    }
}
