package servlet;

import dao.DaoFactory;
import model.*;
import model.libraryModel.LibraryItem;
import model.libraryModel.MediaTypeEnum;
import model.libraryModel.OrderByEnum;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by czyczk on 2017-6-24.
 */
@WebServlet("/FileListGeneratorServlet")
public class FileListGeneratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // Fetch the current active user
        User activeUser = (User) req.getSession().getAttribute("activeUser");
        int ownerId = activeUser.getId();

        // Get request type
        String requestType = req.getParameter("requestType");
        switch (requestType) {
            case "count": {
                // Get media type (and additional parameters for EPISODE)
                MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());
                Integer total;
                Integer imdb;
                Integer season;
                if (mediaType == MediaTypeEnum.EPISODE) {
                    // Get additional parameters for EPISODE
                    imdb = Integer.parseInt(req.getParameter("imdb"));
                    season = Integer.parseInt(req.getParameter("season"));
                    // Get the total number of items of that TV show
                    total = DaoFactory.getLibraryItemDao().count(mediaType, new String[] { "owner_id=" + ownerId, "imdb=" + imdb, "season=" + season });
                } else {
                    // Get the total number of items of that type of media
                    total = DaoFactory.getLibraryItemDao().count(mediaType, new String[] { ("owner_id = " + ownerId) });
                }
                System.out.println("[SQL query] There are " + total + " item(s) of type " + mediaType.toString() + " in user " + ownerId + ".");
                resp.setContentType("text/plain");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(total.toString());
            }
            break;
            case "list": {
                // Get media type, order by and range info
                MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());
                OrderByEnum orderBy = OrderByEnum.valueOf(req.getParameter("orderBy").toUpperCase());
                int start = Integer.parseInt(req.getParameter("start"));
                int range = Integer.parseInt(req.getParameter("range"));
                Integer imdb;
                Integer season;

                List<? extends LibraryItem> itemList;
                if (mediaType == MediaTypeEnum.EPISODE) {
                    // Get additional parameters for EPISODE
                    imdb = Integer.parseInt(req.getParameter("imdb"));
                    season = Integer.parseInt(req.getParameter("season"));
                    // Query the database and generate a list
                    itemList = DaoFactory.getLibraryItemDao().listEpisodes(ownerId, imdb, season, orderBy, start, range);
                } else {
                    // Query the database and generate a list
                    itemList = DaoFactory.getLibraryItemDao().list(ownerId, mediaType, orderBy, start, range);
                }
                String itemListJson = JsonUtil.getGson().toJson(itemList);

                // Send the list
                resp.setContentType("text/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(itemListJson);

                System.out.println("[SQL query] " + itemList.size() + " item(s) returned for user " + ownerId + ".");
            }
            break;
            case "exists": {
                // Get media type
                MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());
                if (mediaType == MediaTypeEnum.EPISODE) {
                    int imdb = Integer.parseInt(req.getParameter("imdb"));
                    int season = Integer.parseInt(req.getParameter("season"));
                    int count = DaoFactory.getLibraryItemDao().count(MediaTypeEnum.TV_SHOW, new String[] { "owner_id="+ownerId, "imdb="+imdb, "season="+season });

                    // Send the list
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write(((Boolean) (count != 0)).toString());

                    System.out.println("[SQL query] The TV show with IMDB=" + imdb + ", season=" + season + " exists: " + (count != 0) + ".");
                } else
                    throw new IllegalArgumentException("Illegal value for mediaType.");
            }
            break;
            default:
                throw new IllegalArgumentException("Illegal value for requestType.");
        }
    }
}
