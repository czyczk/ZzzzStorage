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
}
