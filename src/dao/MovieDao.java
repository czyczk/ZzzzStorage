package dao;

import model.Movie;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by czyczk on 2017-6-23.
 */
class MovieDao implements ILibraryItemDao<Movie> {
    /**
     * Add a movie item to the database. Use it only when the item does not exist! (Use load() to check the existence.)
     * @param item The movie item to be added.
     */
    @Override
    public void add(Movie item) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            String sql =
                    "INSERT INTO movie(" +
                            "imdb, " +
                            "owner_id, " +
                            "SHA256, " +
                            "size, " +
                            "title, " +
                            "release_year, " +
                            "duration, " +
                            "plot, " +
                            "thumbnail_url, " +
                            "rating) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getOwnerId());
            ps.setString(3, item.getSHA256());
            ps.setLong(4, item.getSize());
            ps.setString(5, item.getTitle());
            ps.setInt(6, item.getReleaseYear());
            ps.setInt(7, item.getDuration());
            ps.setString(8, item.getPlot());
            ps.setString(9, item.getThumbUrl());
            ps.setDouble(10, item.getRating());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    @Override
    public void delete(Movie item) {
        // TODO
    }

    /**
     * Load the movie from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     */
    @Override
    public Movie load(Movie item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = isDeterministic(item);
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");
        // Check if the movie exists in the user's library
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie result = null;
        try {
            con = DBUtil.getConnection();
            // Append basic information
            String sql =
                    "SELECT * " +
                    "FROM movie " +
                    "WHERE imdb = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getOwnerId());
            rs = ps.executeQuery();
            // There is a same movie belonging to the user
            while (rs.next()) {
                result = toMovie(rs);
            }
            DBUtil.close(rs);
            DBUtil.close(ps);
            // Return if no such item is found
            if (result == null) {
                return null;
            }

            // Append director information
            result.setDirector(queryAdditionalInfo(result, "director"));
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

    @Override
    public void update(Movie item) {
        // TODO
    }

    public List<Movie> list() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            String sql = "SELECT * FROM movie";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return null;

    }

    // Convert the data on the current cursor of the result set into a Movie object.
    private Movie toMovie(ResultSet rs) throws SQLException {
        Movie result = new Movie();
        result.setImdb(rs.getInt("imdb"));
        result.setOwnerId(rs.getInt("owner_id"));
        result.setSHA256(rs.getString("SHA256"));
        result.setSize(rs.getLong("size"));
        result.setTitle(rs.getString("title"));
        result.setReleaseYear(rs.getInt("release_year"));
        result.setDuration(rs.getInt("duration"));
        result.setPlot(rs.getString("plot"));
        result.setThumbUrl(rs.getString("thumb_url"));
        result.setRating(rs.getDouble("rating"));
        return result;
    }

    // Check if the sample item contains the deterministic characteristics of the target item.
    private boolean isDeterministic(Movie item) {
        if (item.getImdb() == null || item.getOwnerId() == null) {
            return false;
        }
        return true;
    }

    // Query additional info of an item (deterministic information must be provided) (null if no additional info returned)
    private String[] queryAdditionalInfo(Movie item, String infoType) {
        if (!infoType.equals("director") && !infoType.equals("genre"))
            throw new IllegalArgumentException("Info type should be \"director\" or \"genre\".");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            // Prepare the sql statement according to the info type
            String sql = null;
            if (infoType.equals("director")) {
                sql = "SELECT * " +
                        "FROM movie_director " +
                        "WHERE imdb = ? AND owner_id = ?";
            } else {
                sql = "SELECT * " +
                        "FROM movie_genre " +
                        "WHERE imdb = ? AND owner_id = ?";
            }
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getOwnerId());
            rs = ps.executeQuery();
            // Append the attributes to a string, with each attribute separated by `
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                if (infoType.equals("director")) {
                    sb.append(rs.getString("director"));
                } else {
                    sb.append(rs.getString("genre"));
                }
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
}