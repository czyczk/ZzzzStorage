package config;

//import test.TestMovieDao;
import util.DBUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by czyczk on 2017-6-22.
 */
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Create the database
        DBUtil.createTablesIfNotExist();
        // Create the directory for storing files
        DBUtil.createFileDirectoryIfNotExists();

        // Create sample movies for testing purpose
//        TestMovieDao.testAddFull();
//        TestMovieDao.testAddLite();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
