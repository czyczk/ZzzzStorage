package dao;

import model.Movie;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czyczk on 2017-6-23.
 */
class MovieDao implements ILibraryItemDao<Movie> {
    /**
     * Add a movie item to the database
     * @param item The movie item to be added.
     */
    @Override
    public void add(Movie item) {
        // Check if the item contains the deterministic characteristics
        boolean isDeterministic = isDeterministic(item);
        if (!isDeterministic) throw new IllegalArgumentException("The item does not contain all " +
                "the deterministic characteristics to identify an item.");

        // Check if the item exists
        Movie existingMovie = load(item);
        if (existingMovie != null) throw new LibraryItemDaoException("The item exists already.");

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            // Insert basic info
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
                            "thumb_url, " +
                            "rating) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getOwnerId());
            ps.setString(3, item.getSHA256());
            ps.setLong(4, item.getSize());
            ps.setString(5, item.getTitle());

            if (item.getReleaseYear() != null) ps.setInt(6, item.getReleaseYear());
            else ps.setNull(6, Types.INTEGER);

            if (item.getDuration() != null) ps.setInt(7, item.getDuration());
            else ps.setNull(7, Types.INTEGER);

            if (item.getPlot() != null) ps.setString(8, item.getPlot());
            else ps.setNull(8, Types.VARCHAR);

            if (item.getThumbUrl() != null) ps.setString(9, item.getThumbUrl());
            else ps.setNull(9, Types.VARCHAR);

            if (item.getRating() != null) ps.setDouble(10, item.getRating());
            else ps.setNull(10, Types.DOUBLE);
            ps.executeUpdate();

            // Insert director info
            updateAdditionalInfo(item, "director");
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
     * Delete a movie item from the database (including the additional info).
     * @param item The sample item containing the deterministic characteristics of the target item.
     */
    @Override
    public void delete(Movie item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = isDeterministic(item);
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");

        // Check if the item exists
        Movie existingMovie = load(item);
        if (existingMovie == null) throw new LibraryItemDaoException("The item does not exist.");

        // Remove the item from "movie".
        // ON DELETE CASCADE will manage to delete the additional information in other two tables.
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUtil.getConnection();
            String sql = "DELETE FROM movie " +
                    "WHERE imdb = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.getImdb());
            ps.setInt(2, item.getOwnerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    /**
     * Load the movie from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     */
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
    public void update(Movie oldItem, Movie newItem) {
        delete(oldItem);
        add(newItem);
    }

    public List<Movie> list() {
        List<Movie> result = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            String sql = "SELECT * FROM movie";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movie movie = toMovie(rs);
                movie.setDirector(queryAdditionalInfo(movie, "director"));
                movie.setGenre(queryAdditionalInfo(movie, "genre"));
                result.add(movie);
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

    // Convert the data on the current cursor of the result set into a Movie object.
    private Movie toMovie(ResultSet rs) throws SQLException {
        Movie result = new Movie();
        result.setImdb(rs.getInt("imdb"));
        result.setOwnerId(rs.getInt("owner_id"));
        result.setSHA256(rs.getString("SHA256"));
        result.setSize(rs.getLong("size"));
        result.setTitle(rs.getString("title"));
        result.setReleaseYear(rs.getInt("release_year"));
        int tempInt = 0;
        tempInt = rs.getInt("duration");
        if (!rs.wasNull()) {
            result.setDuration(tempInt);
        }
        result.setPlot(rs.getString("plot"));
        result.setThumbUrl(rs.getString("thumb_url"));
        double tempDouble = 0.0;
        tempDouble = rs.getDouble("rating");
        if (!rs.wasNull()) {
            result.setRating(tempDouble);
        }
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
            throw new IllegalArgumentException("Info type should be either \"director\" or \"genre\".");

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

    private void updateAdditionalInfo(Movie item, String infoType) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            switch (infoType) {
                case "director":
                    if (item.getDirector() != null) {
                        con = DBUtil.getConnection();
                        String sql = "INSERT INTO movie_director(imdb, owner_id, director)" +
                                "VALUES(?, ?, ?)";
                        for (String director : item.getDirector()) {
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, item.getImdb());
                            ps.setInt(2, item.getOwnerId());
                            ps.setString(3, director);
                            ps.executeUpdate();
                            DBUtil.close(ps);
                        }
                    }
                    break;
                case "genre":
                    if (item.getGenre() != null) {
                        con = DBUtil.getConnection();
                        String sql = "INSERT INTO movie_genre(imdb, owner_id, genre)" +
                                "VALUES(?, ?, ?)";
                        for (String genre : item.getGenre()) {
                            ps = con.prepareStatement(sql);
                            ps.setInt(1, item.getImdb());
                            ps.setInt(2, item.getOwnerId());
                            ps.setString(3, genre);
                            ps.executeUpdate();
                            DBUtil.close(ps);
                        }
                    }
                    break;
                    default: throw new IllegalArgumentException("Info type should be either \"director\" or \"genre\".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }
}
