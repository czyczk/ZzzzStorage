package servlet;

import dao.DaoFactory;
import dao.UserDao;
import dao.UserDaoException;
import model.servletModel.ServletMessage;
import model.User;
import model.transferModel.DownloadTask;
import model.transferModel.UploadTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by czyczk on 2017-6-22.
 */
@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    private UserDao userDao = DaoFactory.getUserDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Try to log in. No exception is thrown if success. Error type is indicated in the exception.
        User user = null;
        try {
            user = userDao.login(email, password);
        } catch (UserDaoException e) {
            String errorMessage = e.getMessage();
            System.err.println("[Log in error] " + errorMessage);
//            req.setAttribute("errorMessage", errorMessage);
//            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            ServletMessage servletMessage = new ServletMessage();
            servletMessage.setRequestStatus("error");
            if (errorMessage.contains("password")) {
                servletMessage.setMessage("Incorrect password.");
            } else {
                servletMessage.setMessage("The user with the email address does not exist.");
            }
            resp.getWriter().write(servletMessage.toJson());
            return;
        }

        // If no error occurs, update the attributes of the session and forward to the main page
        System.out.println("[Log in] User with email \"" + email + "\" logged in.");
        req.getSession().setAttribute("activeUser", user);
        req.getSession().setAttribute("downloadTasks", new ArrayList<DownloadTask>());
        req.getSession().setAttribute("uploadTasks", new ArrayList<UploadTask>());
        ServletMessage servletMessage = new ServletMessage("success", "main.jsp");
        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(servletMessage.toJson());
    }
}