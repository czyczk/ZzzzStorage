package dao;

import model.libraryModel.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by czyczk on 2017-6-23.
 */
public class LibraryItemDao implements ILibraryItemDao<LibraryItem> {
    @Override
    public void add(LibraryItem item) {
        switch (item.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().add((Movie) item);
                break;
            case MUSIC:
                DaoFactory.getMusicDao().add((Music) item);
                break;
            case TV_SHOW:
                DaoFactory.getTvShowDao().add((TVShow) item);
                break;
            case EPISODE:
                DaoFactory.getEpisodeDao().add((Episode) item);
                break;
            default: throw new NotImplementedException();
        }
    }

    @Override
    public void delete(LibraryItem item) {
        switch (item.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().delete((Movie) item);
                break;
            case MUSIC:
                DaoFactory.getMusicDao().delete((Music) item);
                break;
            case TV_SHOW:
                DaoFactory.getTvShowDao().delete((TVShow) item);
                break;
            case EPISODE:
                DaoFactory.getEpisodeDao().delete((Episode) item);
                break;
            default: throw new NotImplementedException();
        }
    }

    @Override
    public void update(LibraryItem oldItem, LibraryItem newItem) {
        if (oldItem.getMediaType() != newItem.getMediaType())
            throw new LibraryItemDaoException("Media types of the two items are not consistent.");

        switch (newItem.getMediaType()) {
            case MOVIE:
                DaoFactory.getMovieDao().update((Movie) oldItem, (Movie) newItem);
                break;
            case MUSIC:
                DaoFactory.getMusicDao().update((Music) oldItem, (Music) newItem);
                break;
            case TV_SHOW:
                DaoFactory.getTvShowDao().update((TVShow) oldItem, (TVShow) newItem);
                break;
            case EPISODE:
                DaoFactory.getEpisodeDao().update((Episode) oldItem, (Episode) newItem);
                break;
            default: throw new NotImplementedException();
        }
    }

    public int count(MediaTypeEnum mediaType) {
        return count(mediaType, null);
    }

    /**
     * Count the items meeting the conditions passed in. Use at your own risk.
     * @param additionalConditions Additional conditions.
     * @return Number of items meeting the conditions.
     */
    public int count(MediaTypeEnum mediaType, String[] additionalConditions) {
        int count = 0;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            String sql = "SELECT count(*) FROM " + mediaType.toString().toLowerCase();
            if (additionalConditions != null && additionalConditions.length > 0) {
                for (int i = 0; i < additionalConditions.length; i++) {
                    if (i == 0)
                        sql += " WHERE ";
                    else
                        sql += " AND ";
                    sql += additionalConditions[i];
                }
            }
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return count;
    }

    public List<Movie> listMovies() {
        return DaoFactory.getMovieDao().list();
    }
    public List<? extends LibraryItem> list(int ownerId, MediaTypeEnum mediaType, OrderByEnum orderBy, int start, int range) {
        switch (mediaType) {
            case MOVIE:
                return DaoFactory.getMovieDao().list(ownerId, orderBy, start, range);
            case MUSIC:
                return DaoFactory.getMusicDao().list(ownerId, orderBy, start, range);
            case TV_SHOW:
                return DaoFactory.getTvShowDao().list(ownerId, orderBy, start, range);
            case EPISODE:
                throw new IllegalArgumentException("Try listEpisodes(ownerId, imdb, season, orderBy, start, range) instead.");
            default: throw new NotImplementedException();
        }
    }
    public List<Episode> listEpisodes(int ownerId, int imdb, int season, OrderByEnum orderBy, int start, int range) {
        return DaoFactory.getEpisodeDao().list(ownerId, imdb, season, orderBy, start, range);
    }
}
