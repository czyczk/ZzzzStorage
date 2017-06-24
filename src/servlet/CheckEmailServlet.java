package servlet;

import dao.DaoFactory;
import dao.UserDao;
import dao.UserDaoException;
import model.ServletMessage;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ZhangHaodong on 2017/6/24.
 */
@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
    private UserDao userDao = DaoFactory.getUserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = new User();
        user.setEmail(email);
        user.setAvatarId(1);
        try {
            userDao.add(user);
        } catch (UserDaoException e) {
            // In case the user exists, send an error message to the page
            System.err.println("[Sign up error] The email address \"" + email + "\" has been used.");
            ServletMessage servletMessage = new ServletMessage("error", "The email address has been used.");
//            req.setAttribute("errorMessage", errorMessage);
//            req.getRequestDispatcher("welcome.jsp").forward(req, resp);
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(servletMessage.toJson());
            return;
        }
    }
}
