package servlet;

import model.transferModel.DownloadTask;
import model.transferModel.UploadTask;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by czyczk on 2017-6-25.
 */
@WebServlet("/TransferTaskServlet")
public class TransferTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String requestType = req.getParameter("requestType");
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        ArrayList<UploadTask> uploadTasks = (ArrayList<UploadTask>) req.getSession().getAttribute("uploadTasks");
        ArrayList<DownloadTask> downloadTasks = (ArrayList<DownloadTask>) req.getSession().getAttribute("downloadTasks");
        switch (requestType) {
            case "uploading": {
                ArrayList<UploadTask> uploadingTasks = new ArrayList<>();
                for (UploadTask task : uploadTasks) {
                    if (task.isRunning()) {
                        uploadingTasks.add(task);
                    }
                }
                resp.getWriter().write(JsonUtil.getGson().toJson(uploadingTasks));
                System.out.println("[Session query] " + uploadingTasks.size() + " uploading item(s) returned.");
            }
            break;
            case "uploaded": {
                ArrayList<UploadTask> uploadedTasks = new ArrayList<>();
                for (UploadTask task : uploadTasks) {
                    if (!task.isRunning()) {
                        uploadedTasks.add(task);
                    }
                }
                resp.getWriter().write(JsonUtil.getGson().toJson(uploadedTasks));
                System.out.println("[Session query] " + uploadedTasks.size() + " uploaded item(s) returned.");
            }
            break;
            case "downloading": {
                ArrayList<DownloadTask> downloadingTasks = new ArrayList<>();
                for (DownloadTask task : downloadTasks) {
                    if (task.isRunning()) {
                        downloadingTasks.add(task);
                    }
                }
                resp.getWriter().write(JsonUtil.getGson().toJson(downloadingTasks));
                System.out.println("[Session query] " + downloadingTasks.size() + " downloading item(s) returned.");
            }
            break;
            case "downloaded": {
                ArrayList<DownloadTask> downloadedTasks = new ArrayList<>();
                for (DownloadTask task : downloadTasks) {
                    if (!task.isRunning()) {
                        downloadedTasks.add(task);
                    }
                }
                resp.getWriter().write(JsonUtil.getGson().toJson(downloadedTasks));
                System.out.println("[Session query] " + downloadedTasks.size() + " downloaded item(s) returned.");
            }
            break;
            default: throw new IllegalArgumentException("Not supported request type.");
        }
    }
}
