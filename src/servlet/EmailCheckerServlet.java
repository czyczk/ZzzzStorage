package servlet;

import dao.DaoFactory;
import dao.UserDao;
import model.servletModel.ServletMessage;
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
@WebServlet("/EmailCheckerServlet")
public class EmailCheckerServlet extends HttpServlet {
    private UserDao userDao = DaoFactory.getUserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        User user = userDao.load(email);
        ServletMessage message = null;
        if (user == null) {
            message = new ServletMessage("match not found", "The user with the email address does not exist.");
        } else {
            message = new ServletMessage("match found", "The email address has been used.");
        }
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(message.toJson());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
