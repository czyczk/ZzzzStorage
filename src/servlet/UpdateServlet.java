package servlet;

import com.google.gson.JsonSyntaxException;
import dao.DaoFactory;
import model.User;
import model.libraryModel.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.JsonUtil;
import util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-26.
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/json");

        // Active user
        User user = (User) req.getSession().getAttribute("activeUser");
        int ownerId = user.getId();

        // Media type
        MediaTypeEnum mediaType = ServletUtil.parseMediaType(req.getParameter("mediaType"));

        // Old and new items
        String oldItemJson = req.getParameter("oldItem");
        String newItemJson = req.getParameter("newItem");

        try {
            switch (mediaType) {
                case MOVIE:
                {
                    System.out.println("[SQL update] Updating a movie item.");
                    Movie oldItem = JsonUtil.getGson().fromJson(oldItemJson, Movie.class);
                    Movie newItem = JsonUtil.getGson().fromJson(newItemJson, Movie.class);
                    oldItem.setOwnerId(ownerId);
                    newItem.setOwnerId(ownerId);
                    DaoFactory.getLibraryItemDao().update(oldItem, newItem);
                }
                break;
                case MUSIC:
                {
                    System.out.println("[SQL update] Updating a music item.");
                    Music oldItem = JsonUtil.getGson().fromJson(oldItemJson, Music.class);
                    Music newItem = JsonUtil.getGson().fromJson(newItemJson, Music.class);
                    oldItem.setOwnerId(ownerId);
                    newItem.setOwnerId(ownerId);
                    DaoFactory.getLibraryItemDao().update(oldItem, newItem);
                }
                break;
                case TV_SHOW:
                {
                    System.out.println("[SQL update] Updating a TV show item.");
                    TVShow oldItem = JsonUtil.getGson().fromJson(oldItemJson, TVShow.class);
                    TVShow newItem = JsonUtil.getGson().fromJson(newItemJson, TVShow.class);
                    oldItem.setOwnerId(ownerId);
                    newItem.setOwnerId(ownerId);
                    DaoFactory.getLibraryItemDao().update(oldItem, newItem);
                }
                break;
                case EPISODE:
                {
                    System.out.println("[SQL update] Updating an episode item.");
                    Episode oldItem = JsonUtil.getGson().fromJson(oldItemJson, Episode.class);
                    Episode newItem = JsonUtil.getGson().fromJson(newItemJson, Episode.class);
                    oldItem.setOwnerId(ownerId);
                    newItem.setOwnerId(ownerId);
                    DaoFactory.getLibraryItemDao().update(oldItem, newItem);
                }
                break;
                default: throw new IllegalArgumentException("Not supported media type.");
            }
        } catch (JsonSyntaxException e) {
            System.err.println("[SQL update] Json syntax error.");
            e.printStackTrace();
            ServletUtil.sendServletMessage(resp, "error", e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ServletUtil.sendServletMessage(resp, "error", e.getMessage());
        }

        ServletUtil.sendServletMessage(resp, "success", null);
        System.out.println("[SQL update] Successfully updated an item.");
    }
}
