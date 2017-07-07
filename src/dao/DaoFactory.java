package dao;

import model.libraryModel.Episode;

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

    private volatile static MusicDao musicDao;
    static MusicDao getMusicDao() {
        if (musicDao == null) {
            synchronized (DaoFactory.class) {
                if (musicDao == null) {
                    musicDao = new MusicDao();
                }
            }
        }
        return musicDao;
    }

    private volatile static TVShowDao tvShowDao;
    static TVShowDao getTvShowDao() {
        if (tvShowDao == null) {
            synchronized (DaoFactory.class) {
                if (tvShowDao == null) {
                    tvShowDao = new TVShowDao();
                }
            }
        }
        return tvShowDao;
    }

    private volatile static EpisodeDao episodeDao;
    static EpisodeDao getEpisodeDao() {
        if (episodeDao == null) {
            synchronized (DaoFactory.class) {
                if (episodeDao == null) {
                    episodeDao = new EpisodeDao();
                }
            }
        }
        return episodeDao;
    }
}
