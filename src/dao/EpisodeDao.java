package dao;

import model.libraryModel.Episode;
import model.libraryModel.MediaTypeEnum;
import model.libraryModel.OrderByEnum;
import model.libraryModel.TVShow;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czyczk on 2017-7-4.
 */
public class EpisodeDao implements ILibraryItemDao<Episode> {
    // Check if the sample item contains the required properties.
    private boolean isValid(Episode item) {
        return !(item.getTvShow() == null || !item.getTvShow().isDeterministic()) &&
                !(item.getEpisodeNo() == null || item.getSHA256() == null || item.getSize() == null);
    }

    /**
     * Load the episode from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     */
    public Episode load(Episode item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");
        // Check if the music exists in the user's library
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Episode result = null;
        try {
            con = DBUtil.getConnection();
            // Append basic information
            String sql =
                    "SELECT * " +
                            "FROM episode " +
                            "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
            ps.setInt(3, item.getOwnerId());
            rs = ps.executeQuery();
            // If there is a match, extract its basic info
            while (rs.next()) {
                result = toEpisode(rs);
            }
            DBUtil.close(rs);
            DBUtil.close(ps);
            // Return if no such item is found
            if (result == null) {
                return null;
            }
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
     * Add an episode item to the database.
     * @param item The episode item to be added.
     */
    @Override
    public void add(Episode item) {
        // Check if the item contains the deterministic characteristics
        boolean isValid = isValid(item);
        if (!isValid) throw new IllegalArgumentException("The item does not contain all " +
                "the deterministic characteristics to identify an item.");

        // Check if the item exists
        Episode existingItem = load(item);
        if (existingItem != null) throw new LibraryItemDaoException("The item exists already.");

        // Check if the TV show exists. Create the TV show first if it doesn't exist.
        TVShow tvShow = DaoFactory.getTvShowDao().load(item.getTvShow(), false);
        try {
            if (tvShow == null) {
                DaoFactory.getTvShowDao().add(item.getTvShow());
            }
        } catch (LibraryItemDaoException e) {
            e.printStackTrace();
            System.out.println("[LibraryItemDaoException] Failed to add the episode since the TV show cannot be created.");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();

            // Insert basic info
            String sql = "INSERT INTO episode(" +
                    "imdb, " +
                    "season, " +
                    "owner_id, " +
                    "episode_no, " +
                    "SHA256, " +
                    "size, " +
                    "title, " +
                    "runtime, " +
                    "storyline, " +
                    "thumb_url, " +
                    "rating) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getTvShow().getImdb());
            ps.setInt(2, item.getTvShow().getSeason());
            ps.setInt(3, item.getOwnerId());
            ps.setInt(4, item.getEpisodeNo());
            ps.setString(5, item.getSHA256());
            ps.setLong(6, item.getSize());

            if (item.getTitle() != null) ps.setString(7, item.getTitle());
            else ps.setNull(7, Types.VARCHAR);

            if (item.getRuntime() != null) ps.setInt(8, item.getRuntime());
            else ps.setNull(8, Types.INTEGER);

            if (item.getStoryline() != null) ps.setString(9, item.getStoryline());
            else ps.setNull(9, Types.VARCHAR);

            if (item.getThumbUrl() != null) ps.setString(10, item.getThumbUrl());
            else ps.setNull(10, Types.VARCHAR);

            if (item.getRating() != null) ps.setDouble(11, item.getRating());
            else ps.setNull(11, Types.DOUBLE);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    /**
     * Delete an episode item from the database (including the additional info).
     * @param item The sample item containing the deterministic characteristics of the target item.
     */
    @Override
    public void delete(Episode item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");

        // Check if the item exists
        Episode existingItem = load(item);
        if (existingItem == null) throw new LibraryItemDaoException("The item does not exist.");

        // Remove the item from "episode".
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUtil.getConnection();
//            // Count the number of episodes of this TV show
//            int count = DaoFactory.getLibraryItemDao().count(
//                    MediaTypeEnum.EPISODE,
//                    new String[] {
//                            "owner_id=" + item.getOwnerId(),
//                            "imdb=" + item.getTvShow().getImdb(),
//                            "season=" + item.getTvShow().getSeason()
//                    }
//            );
//            // Delete the TV show if there is only one episode
//            if (count == 1) {
//                DaoFactory.getTvShowDao().delete(item.getTvShow());
//            }
            // Else, just delete the episode
//            else {
            String sql = "DELETE FROM episode " +
                    "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
            ps.setInt(3, item.getOwnerId());
            ps.executeUpdate();
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    @Override
    public void update(Episode oldItem, Episode newItem) {
        delete(oldItem);
        add(newItem);
    }

    public List<Episode> list(int ownerId, int imdb, int season, OrderByEnum orderBy, int start, int range) {
        if (orderBy != OrderByEnum.TITLE && orderBy != OrderByEnum.EPISODE_NO)
            throw new IllegalArgumentException("Not supported order for episodes.");

        List<Episode> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            String sql = "SELECT * FROM episode WHERE owner_id=" + ownerId + " AND imdb=" + imdb + " AND season=" + season +
                    " ORDER BY " + orderBy.toString() +
                    " OFFSET " + start + " ROWS FETCH NEXT " + range + " ROWS ONLY";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Episode item = toEpisode(rs);
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return result;
    }

    // Convert the data on the current cursor of the result set into an Episode object.
    private Episode toEpisode(ResultSet rs) throws SQLException {
        TVShow tvShow = new TVShow();
        tvShow.setImdb(rs.getInt("imdb"));
        tvShow.setSeason(rs.getInt("season"));
        tvShow.setOwnerId(rs.getInt("owner_id"));
        tvShow = DaoFactory.getTvShowDao().load(tvShow, false); // Load title

        Episode result = new Episode();
        result.setTvShow(tvShow);
        result.setOwnerId(rs.getInt("owner_id"));
        result.setEpisodeNo(rs.getInt("episode_no"));
        result.setSHA256(rs.getString("SHA256"));
        result.setSize(rs.getLong("size"));
        result.setTitle(rs.getString("title"));
        int tempInt;
        tempInt = rs.getInt("runtime");
        if (!rs.wasNull()) {
            result.setRuntime(tempInt);
        }
        double tempDouble = rs.getInt("rating");
        if (!rs.wasNull()) {
            result.setRating(tempDouble);
        }
        result.setStoryline(rs.getString("storyline"));
        result.setThumbUrl(rs.getString("thumb_url"));
        return result;
    }


}
