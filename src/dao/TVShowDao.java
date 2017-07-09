package dao;

import model.libraryModel.Episode;
import model.libraryModel.MediaTypeEnum;
import model.libraryModel.OrderByEnum;
import model.libraryModel.TVShow;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czyczk on 2017-7-4.
 */
class TVShowDao implements ILibraryItemDao<TVShow> {
    // Check if the sample item contains the required properties.
    private boolean isValid(TVShow item) {
        return item.isDeterministic() && item.getTitle() != null;
    }

    /**
     * Load the TV show from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     * @param full true to retrieve all attributes and false to retrieve only the deterministic attributes.
     */
    public TVShow load(TVShow item, boolean full) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");
        // Check if the TV show exists in the user's library
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        TVShow result = null;
        try {
            con = DBUtil.getConnection();
            // Append basic information
            String sql =
                    "SELECT * " +
                            "FROM tv_show " +
                            "WHERE imdb = ? AND season = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getSeason());
            ps.setInt(3, item.getOwnerId());
            rs = ps.executeQuery();
            // If there is a match, extract its basic info
            while (rs.next()) {
                result = toTVShow(rs, full);
            }
            DBUtil.close(rs);
            DBUtil.close(ps);
            // Return if no such item is found
            if (result == null) {
                return null;
            }

            // Append genre information
            result.setGenre(queryAdditionalInfo(result, "genre"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return result;
    }

    /**
     * Add a TV show item to the database
     * @param item The TV show item to be added.
     */
    @Override
    public void add(TVShow item) {
        // Check if the item contains the deterministic characteristics
        boolean isValid = isValid(item);
        if (!isValid) throw new IllegalArgumentException("The item does not contain all " +
                "the deterministic characteristics to identify an item.");

        // Check if the item exists
        TVShow existingItem = load(item, false);
        if (existingItem != null) throw new LibraryItemDaoException("The item exists already.");

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            // Insert basic info
            String sql = "INSERT INTO tv_show(" +
                    "imdb, " +
                    "season, " +
                    "owner_id, " +
                    "title, " +
                    "runtime, " +
                    "release_year, " +
                    "plot, " +
                    "thumb_url, " +
                    "rating) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getSeason());
            ps.setInt(3, item.getOwnerId());
            ps.setString(4, item.getTitle());

            if (item.getRuntime() != null) ps.setInt(5, item.getRuntime());
            else ps.setNull(5, Types.INTEGER);

            if (item.getReleaseYear() != null) ps.setInt(6, item.getReleaseYear());
            else ps.setNull(6, Types.INTEGER);

            if (item.getPlot() != null) ps.setString(7, item.getPlot());
            else ps.setNull(7, Types.VARCHAR);

            if (item.getThumbUrl() != null) ps.setString(8, item.getThumbUrl());
            else ps.setNull(8, Types.VARCHAR);

            if (item.getRating() != null) ps.setDouble(9, item.getRating());
            else ps.setNull(9, Types.DOUBLE);

            ps.executeUpdate();

            // Insert genre info
            updateAdditionalInfo(item, "genre");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    /**
     * Delete a TV show item from the database (including the additional info).
     * @param item The sample item containing the deterministic characteristics of the target item.
     */
    @Override
    public void delete(TVShow item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");

        // Check if the item exists
        TVShow existingItem = load(item, false);
        if (existingItem == null) throw new LibraryItemDaoException("The item does not exist.");

        // Remove the item from "tv_show".
        // ON DELETE CASCADE will manage to delete the additional information in other tables.
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUtil.getConnection();
            String sql = "DELETE FROM tv_show " +
                    "WHERE imdb = ? AND season = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getSeason());
            ps.setInt(3, item.getOwnerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    @Override
    public void update(TVShow oldItem, TVShow newItem) {
        EpisodeDao episodeDao = DaoFactory.getEpisodeDao();
        int ownerId = oldItem.getOwnerId();
        // Retrieve all the episodes containing the information of the old item if there's any
        int countEpisodes = DaoFactory.getLibraryItemDao().count(MediaTypeEnum.EPISODE, new String[] {
                "owner_id=" + ownerId, "imdb=" + oldItem.getImdb(), "season=" + oldItem.getSeason()
        });
        List<Episode> episodes = null;
        if (countEpisodes > 0) {
            episodes = episodeDao.list(
                    ownerId, oldItem.getImdb(), oldItem.getSeason(),
                    OrderByEnum.EPISODE_NO, 0, countEpisodes);
        }
        // Delete the old TV show (along with the episodes in the database with the old information)
        delete(oldItem);
        // If there are episodes with old information, update them
        if (episodes != null) {
            for (Episode episode : episodes) {
                episode.setTvShow(newItem);
            }
        }

        // Add the new TV show and restore the items
        add(newItem);
        if (episodes != null) {
            for (Episode episode : episodes) {
                episodeDao.add(episode);
            }
        }
    }

    public List<TVShow> list(int ownerId, OrderByEnum orderBy, int start, int range) {
        if (orderBy != OrderByEnum.IMDB && orderBy != OrderByEnum.GENRE && orderBy != OrderByEnum.TITLE)
            throw new IllegalArgumentException("Not supported order for TV shows.");

        List<TVShow> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (orderBy == OrderByEnum.TITLE || orderBy == OrderByEnum.IMDB) {
            try {
                con = DBUtil.getConnection();
                String sql = "SELECT * FROM tv_show WHERE owner_id=" + ownerId +
                        " ORDER BY " + orderBy.toString() + ", season " +
                        " OFFSET " + start + " ROWS FETCH NEXT " + range + " ROWS ONLY";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    TVShow item = toTVShow(rs, true);
                    item.setGenre(queryAdditionalInfo(item, "genre"));
                    result.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs);
                DBUtil.close(ps);
                DBUtil.close(con);
            }
        } else if (orderBy == OrderByEnum.GENRE) {
            try {
                con = DBUtil.getConnection();
                String sql = "SELECT DISTINCT imdb, season, owner_id FROM tv_show_" + orderBy.toString().toLowerCase() +
                        " WHERE owner_id = " + ownerId + " ORDER BY " + orderBy.toString().toLowerCase() +
                        " OFFSET " + start + " ROWS FETCH NEXT " + range + " ROWS ONLY";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    TVShow item = new TVShow();
                    item.setOwnerId(ownerId);
                    item.setImdb(rs.getInt("imdb"));
                    item.setSeason(rs.getInt("season"));
                    item = load(item, true);
                    item.setGenre(queryAdditionalInfo(item, "genre"));
                    result.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs);
                DBUtil.close(ps);
                DBUtil.close(con);
            }
        } else {
            throw new NotImplementedException();
        }
        return result;
    }

    // Convert the data on the current cursor of the result set into a TVShow object.
    private TVShow toTVShow(ResultSet rs, boolean full) throws SQLException {
        TVShow result = new TVShow();
        result.setOwnerId(rs.getInt("owner_id"));
        result.setImdb(rs.getInt("imdb"));
        result.setSeason(rs.getInt("season"));
        result.setTitle(rs.getString("title"));
        if (full) {
            int tempInt;
            tempInt = rs.getInt("release_year");
            if (!rs.wasNull())
                result.setReleaseYear(tempInt);
            tempInt = rs.getInt("runtime");
            if (!rs.wasNull()) {
                result.setRuntime(tempInt);
            }
            double tempDouble;
            tempDouble = rs.getDouble("rating");
            if (!rs.wasNull()) {
                result.setRating(tempDouble);
            }
            result.setPlot(rs.getString("plot"));
            result.setThumbUrl(rs.getString("thumb_url"));
        }
        return result;
    }

    // Query additional info of an item
    private String[] queryAdditionalInfo(TVShow item, String infoType) {
        if (!infoType.equals("genre"))
            throw new IllegalArgumentException("Info type should be \"genre\".");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            // Prepare the sql statement according to the info type
            String sql = null;
            sql = "SELECT * " +
                    "FROM tv_show_genre " +
                    "WHERE imdb = ? AND season = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getSeason());
            ps.setInt(3, item.getOwnerId());
            rs = ps.executeQuery();
            // Append the attributes to a string, with each attribute separated by `
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append(rs.getString("genre"));
                sb.append('`');
            }
            if (sb.length() > 0) {
                String[] attrs = sb.toString().split("`");
                return attrs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return null;
    }

    private void updateAdditionalInfo(TVShow item, String infoType) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            switch (infoType) {
                case "genre":
                    if (item.getGenre() != null) {
                        con = DBUtil.getConnection();
                        String sql = "INSERT INTO tv_show_genre(imdb, season, owner_id, genre)" +
                                "VALUES(?, ?, ?, ?)";
                        for (String genre : item.getGenre()) {
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, item.getImdb());
                            ps.setInt(2, item.getSeason());
                            ps.setInt(3, item.getOwnerId());
                            ps.setString(4, genre);
                            ps.executeUpdate();
                            DBUtil.close(ps);
                        }
                    }
                    break;
                default: throw new IllegalArgumentException("Info type should be \"genre\".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }
}
