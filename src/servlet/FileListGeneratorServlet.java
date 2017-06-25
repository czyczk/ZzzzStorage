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
                MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());
                Integer total = DaoFactory.getLibraryItemDao().count(mediaType, new String[] { ("owner_id = " + ownerId) });
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

                // Query the database and generate a list
                List<? extends LibraryItem> itemList = DaoFactory.getLibraryItemDao().list(ownerId, mediaType, orderBy, start, range);
                String itemListJson = JsonUtil.getGson().toJson(itemList);

                // Send the list
                resp.setContentType("text/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(itemListJson);

                System.out.println("[SQL query] " + itemList.size() + " item(s) returned for user " + ownerId + ".");
            }
            break;
            default:
                throw new NotImplementedException();
        }
    }
}
