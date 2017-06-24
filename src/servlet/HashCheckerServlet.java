package servlet;

import dao.DaoFactory;
import model.MediaTypeEnum;
import model.ServletMessage;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-24.
 */
@WebServlet("/HashCheckerServlet")
public class HashCheckerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Collect necessary data
        User user = (User) req.getSession().getAttribute("activeUser");
        int ownerId = user.getId();
        String SHA256 = req.getParameter("SHA256");
        long size = Long.parseLong(req.getParameter("size"));
        MediaTypeEnum mediaType = MediaTypeEnum.valueOf(req.getParameter("mediaType").toUpperCase());

        // Ready for response
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        ServletMessage message = new ServletMessage();

        // Query for existence of the file
        int numOfThisFile = DaoFactory.getLibraryItemDao().count(mediaType, new String[] { "SHA256 = " + SHA256, "size = " + size });
        boolean isFileExisting = numOfThisFile > 0;

        // If a file with the same SHA256 is not found, the user needs to upload the file.
        if (!isFileExisting)
            message.setMessage("full");

        int itemOfThisUser = DaoFactory.getLibraryItemDao().count(mediaType, new String[] { ("owner_id = " + ownerId), "SHA256 = " + SHA256, "size = " + size });
        boolean isFileExistingInUserLib = itemOfThisUser > 0;

        // Else, if the file is found in the database but not in user library, the user needs only to upload the metadata.
        if (!isFileExistingInUserLib)
            message.setMessage("metadata");
        // Else, the file is found in the user library. The user cannot upload a second same item.
        else
            message.setMessage("conflict");

        // Send the message
        resp.getWriter().write(message.toJson());
    }
}
