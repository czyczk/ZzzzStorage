package dao;

/**
 * Created by czyczk on 2017-6-18.
 */
public class DaoFactory {
    private DaoFactory() { }
    private volatile static UserDao userDao;
    public static UserDao getUserDao() {
        if (userDao == null) {
            synchronized (DaoFactory.class) {
                if (userDao == null) {
                    userDao = new UserDao();
                }
            }
        }
        return userDao;
    }

    private volatile static LibraryItemDao libraryItemDao;
    public static LibraryItemDao getLibraryItemDao() {
        if (libraryItemDao == null) {
            synchronized (DaoFactory.class) {
                if (libraryItemDao == null) {
                    libraryItemDao = new LibraryItemDao();
                }
            }
        }
        return libraryItemDao;
    }

    private volatile static MovieDao movieDao;
    static MovieDao getMovieDao() {
        if (movieDao == null) {
            synchronized (DaoFactory.class) {
                if (movieDao == null) {
                    movieDao = new MovieDao();
                }
            }
        }
        return movieDao;
    }
}
