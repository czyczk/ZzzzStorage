package servlet;

import model.ServletMessage;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-24.
 */
public class FileListGeneratorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inMessageJson = req.getParameter("listRequest");
        ServletMessage inMessage = JsonUtil.getGson().fromJson(inMessageJson, ServletMessage.class);
        String requestJson = inMessage.getMessage();
        
    }
}
