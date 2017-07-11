package servlet;

import dao.DaoFactory;
import model.User;
import model.libraryModel.*;
import model.servletModel.ServletMessage;
import util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-26.
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");

        // Get current active user
        User user = (User) req.getSession().getAttribute("activeUser");
        int ownerId = user.getId();

        // Get media type
        MediaTypeEnum mediaType = ServletUtil.parseMediaType(req.getParameter("mediaType"));

        // For a library item that is NOT file associated, such as a TV show, perform special operations
        if (mediaType == MediaTypeEnum.TV_SHOW) {
            try {
                int imdb = Integer.parseInt(req.getParameter("imdb"));
                int season = Integer.parseInt(req.getParameter("season"));
                TVShow tvShow = new TVShow();
                tvShow.setOwnerId(ownerId);
                tvShow.setImdb(imdb);
                tvShow.setSeason(season);
                DaoFactory.getLibraryItemDao().delete(tvShow);
            } catch (NumberFormatException e) {
                ServletUtil.sendServletMessage(resp, "error", e.getMessage());
                return;
            }
        }
        // For file associated items, get its SHA256 and size to identify a file and perform deletion
        else {
            String SHA256 = req.getParameter("SHA256");
            long size = Long.parseLong(req.getParameter("size"));


            FileAssociatedItem item;
            try {
                item = FileAssociatedItem.createItem(mediaType, SHA256, size);
                switch (mediaType) {
                    case MOVIE:
                    {
                        int imdb = Integer.parseInt(req.getParameter("imdb"));
                        Movie movie = (Movie) item;
                        movie.setImdb(imdb);
                        item = movie;
                    }
                    break;
                    case EPISODE:
                    {
                        int imdb = Integer.parseInt(req.getParameter("imdb"));
                        int season = Integer.parseInt(req.getParameter("season"));
                        int episodeNo = Integer.parseInt(req.getParameter("episodeNo"));
                        TVShow tvShow = new TVShow();
                        tvShow.setOwnerId(ownerId);
                        tvShow.setImdb(imdb);
                        tvShow.setSeason(season);
                        Episode episode = (Episode) item;
                        episode.setTvShow(tvShow);
                        episode.setEpisodeNo(episodeNo);
                        item = episode;
                    }
                    break;
                }
            } catch (IllegalArgumentException e) {
                ServletUtil.sendServletMessage(resp, "error", e.getMessage());
                return;
            }
            item.setOwnerId(ownerId);

            // Delete the item
            DaoFactory.getLibraryItemDao().delete(item);
        }


        // Send a success message
        System.out.println("[SQL delete] An item is deleted.");
        ServletUtil.sendServletMessage(resp, "success", null);
    }

}
