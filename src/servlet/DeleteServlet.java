package servlet;

import dao.DaoFactory;
import model.User;
import model.libraryModel.FileAssociatedItem;
import model.libraryModel.MediaTypeEnum;
import model.servletModel.ServletMessage;
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

        // Get the SHA256, size and the media type of the target file
        String SHA256 = req.getParameter("SHA256");
        long size = Long.parseLong(req.getParameter("size"));
        MediaTypeEnum mediaType = ServletUtil.parseMediaType(req.getParameter("mediaType"));

        FileAssociatedItem item;
        try {
            item = FileAssociatedItem.createItem(mediaType, SHA256, size);
        } catch (IllegalArgumentException e) {
            ServletUtil.sendServletMessage(resp, "error", e.getMessage());
            return;
        }

        // Delete the item
        DaoFactory.getLibraryItemDao().delete(item);

        // Send a success message
        ServletUtil.sendServletMessage(resp, "success", null);
    }

}