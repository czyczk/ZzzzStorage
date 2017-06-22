package servlet;

import dao.DaoFactory;
import dao.UserDao;
import dao.UserDaoException;
import model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by czyczk on 2017-6-22.
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
    private UserDao userDao = DaoFactory.getUserDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get parameters
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Create a User object according to the parameters
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setAvatarId(1);

        // Try to add the user. An exception is thrown if the user exists.
        try {
            userDao.add(user);
        } catch (UserDaoException e) {
            // In case the user exists, send an error message to the page
            System.err.println("[Sign up error] The email address \"" + email + "\" has been used.");
            String errorMessage = "The email address has been used.";
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
            return;
        }

        // If no error occurs, update the attributes of the session and forward to the main page
        System.out.println("[Sign up] A new user has been created. Email=\"" + email + "\". Username=\"" + username + "\".");
        req.getSession().setAttribute("username", user.getUsername());
        req.getSession().setAttribute("email", user.getEmail());
        req.getSession().setAttribute("avatar_id", 1); // The new user is assigned with a default avatar.
        req.getSession().setAttribute("user_id", userDao.getNumTotalUsers()); // The ID of the new user is assigned by the user DAO.
        resp.setCharacterEncoding("UTF-8");
        resp.sendRedirect("main.jsp");
    }
}
