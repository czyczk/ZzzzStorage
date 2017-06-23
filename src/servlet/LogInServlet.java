package servlet;

import dao.DaoFactory;
import dao.UserDao;
import dao.UserDaoException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("UTF-8");
            if (errorMessage.contains("password")) {
                resp.getWriter().write("Incorrect password.");
            } else {
                resp.getWriter().write("The user with the email address does not exist.");
            }
            return;
        }

        // If no error occurs, update the attributes of the session and forward to the main page
        System.out.println("[Log in] User with email \"" + email + "\" logged in.");
        req.getSession().setAttribute("email", email);
        req.getSession().setAttribute("username", user.getUsername());
        req.getSession().setAttribute("email", user.getEmail());
        req.getSession().setAttribute("avatar_id", user.getAvatarId());
        req.getSession().setAttribute("user_id", user.getId());
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect("main.jsp");
    }
}
