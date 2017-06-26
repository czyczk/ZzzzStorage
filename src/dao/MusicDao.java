package dao;

import model.libraryModel.Music;
import model.libraryModel.OrderByEnum;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czyczk on 2017-6-24.
 */
public class MusicDao implements ILibraryItemDao<Music> {
    // Check if the sample item contains the required properties.
    private boolean isValid(Music item) {
        return !(item.getSHA256() == null || item.getOwnerId() == null || item.getSize() == null || item.getTitle() == null || item.getAlbum() == null);
    }


    /**
     * Load the music from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     */
    public Music load(Music item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");
        // Check if the music exists in the user's library
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Music result = null;
        try {
            con = DBUtil.getConnection();
            // Append basic information
            String sql =
                    "SELECT * " +
                            "FROM music " +
                            "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
            ps.setInt(3, item.getOwnerId());
            rs = ps.executeQuery();
            // If there is a match, extract its basic info
            while (rs.next()) {
                result = toMusic(rs);
            }
            DBUtil.close(rs);
            DBUtil.close(ps);
            // Return if no such item is found
            if (result == null) {
                return null;
            }

            // Append artist information
            result.setArtist(queryAdditionalInfo(result, "artist"));
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
     * Add a movie item to the database
     * @param item The music item to be added.
     */
    @Override
    public void add(Music item) {
        // Check if the item contains the deterministic characteristics
        boolean isValid = isValid(item);
        if (!isValid) throw new IllegalArgumentException("The item does not contain all " +
                "the deterministic characteristics to identify an item.");

        // Check if the item exists
        Music existingMusic = load(item);
        if (existingMusic != null) throw new LibraryItemDaoException("The item exists already.");

        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUtil.getConnection();
            // Insert basic info
            String sql =
                    "INSERT INTO music(" +
                            "SHA256, " +
                            "size, " +
                            "owner_id, " +
                            "title, " +
                            "album, " +
                            "duration, " +
                            "track, " +
                            "thumb_url, " +
                            "rating) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
            ps.setInt(3, item.getOwnerId());
            ps.setString(4, item.getTitle());
            ps.setString(5, item.getAlbum());

            if (item.getDuration() != null) ps.setInt(6, item.getDuration());
            else ps.setNull(6, Types.INTEGER);

            if (item.getTrack() != null) ps.setInt(7, item.getTrack());
            else ps.setNull(7, Types.INTEGER);

            if (item.getThumbUrl() != null) ps.setString(8, item.getThumbUrl());
            else ps.setNull(8, Types.VARCHAR);

            if (item.getRating() != null) ps.setDouble(9, item.getRating());
            else ps.setNull(9, Types.DOUBLE);
            ps.executeUpdate();

            // Insert artist info
            updateAdditionalInfo(item, "artist");
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
     * Delete a music item from the database (including the additional info).
     * @param item The sample item containing the deterministic characteristics of the target item.
     */
    @Override
    public void delete(Music item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = item.isDeterministic();
        if (!isDeterministic) throw new IllegalArgumentException("The sample item does not contain all " +
                "the deterministic characteristics to identify a target item.");

        // Check if the item exists
        Music existingMusic = load(item);
        if (existingMusic == null) throw new LibraryItemDaoException("The item does not exist.");

        // Remove the item from "music".
        // ON DELETE CASCADE will manage to delete the additional information in other two tables.
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBUtil.getConnection();
            String sql = "DELETE FROM music " +
                    "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
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
    public void update(Music oldItem, Music newItem) {
        delete(oldItem);
        add(newItem);
    }

    public List<Music> list(int ownerId, OrderByEnum orderBy, int start, int range) {
        if (orderBy != OrderByEnum.ARTIST && orderBy != OrderByEnum.GENRE && orderBy != OrderByEnum.TITLE)
            throw new IllegalArgumentException("Not support order.");

        List<Music> result = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        if (orderBy == OrderByEnum.TITLE) {
            try {
                con = DBUtil.getConnection();
                String sql = "SELECT * FROM music WHERE owner_id=" + ownerId + " ORDER BY " + orderBy.toString() +
                        " OFFSET " + start + " ROWS FETCH NEXT " + range + " ROWS ONLY";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Music music = toMusic(rs);
                    music.setArtist(queryAdditionalInfo(music, "artist"));
                    music.setGenre(queryAdditionalInfo(music, "genre"));
                    result.add(music);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs);
                DBUtil.close(ps);
                DBUtil.close(con);
            }
        } else if (orderBy == OrderByEnum.ARTIST || orderBy == OrderByEnum.GENRE) {
            try {
                con = DBUtil.getConnection();
                String sql = "SELECT DISTINCT imdb, owner_id FROM music_" + orderBy.toString().toLowerCase() +
                        " WHERE owner_id = " + ownerId + " ORDER BY " + orderBy.toString().toLowerCase() +
                        " OFFSET " + start + " ROWS FETCH NEXT " + range + " ROWS ONLY";
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Music music = new Music();
                    music.setOwnerId(ownerId);
                    music.setSHA256(rs.getString("SHA256"));
                    music.setSize(rs.getLong("size"));
                    music = load(music);
                    music.setArtist(queryAdditionalInfo(music, "artist"));
                    music.setGenre(queryAdditionalInfo(music, "genre"));
                    result.add(music);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs);
                DBUtil.close(ps);
                DBUtil.close(con);
            }
        } else {
            throw new IllegalArgumentException("Not supported order for movies.");
        }
        return result;
    }

    // Convert the data on the current cursor of the result set into a Music object.
    private Music toMusic(ResultSet rs) throws SQLException {
        Music result = new Music();
        result.setOwnerId(rs.getInt("owner_id"));
        result.setSHA256(rs.getString("SHA256"));
        result.setSize(rs.getLong("size"));
        result.setTitle(rs.getString("title"));
        result.setAlbum(rs.getString("album"));
        int tempInt = 0;
        tempInt = rs.getInt("duration");
        if (!rs.wasNull()) {
            result.setDuration(tempInt);
        }
        tempInt = rs.getInt("rating");
        if (!rs.wasNull()) {
            result.setRating(tempInt);
        }
        tempInt = rs.getInt("track");
        if (!rs.wasNull()) {
            result.setTrack(tempInt);
        }
        result.setThumbUrl(rs.getString("thumb_url"));
        return result;
    }

    // Query additional info of an item
    private String[] queryAdditionalInfo(Music item, String infoType) {
        if (!infoType.equals("artist") && !infoType.equals("genre"))
            throw new IllegalArgumentException("Info type should be either \"artist\" or \"genre\".");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();
            // Prepare the sql statement according to the info type
            String sql = null;
            if (infoType.equals("artist")) {
                sql = "SELECT * " +
                        "FROM music_artist " +
                        "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            } else {
                sql = "SELECT * " +
                        "FROM music_genre " +
                        "WHERE SHA256 = ? AND size = ? AND owner_id = ?";
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, item.getSHA256());
            ps.setLong(2, item.getSize());
            ps.setInt(3, item.getOwnerId());
            rs = ps.executeQuery();
            // Append the attributes to a string, with each attribute separated by `
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                if (infoType.equals("artist")) {
                    sb.append(rs.getString("artist"));
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

    private void updateAdditionalInfo(Music item, String infoType) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            switch (infoType) {
                case "artist":
                    if (item.getArtist() != null) {
                        con = DBUtil.getConnection();
                        String sql = "INSERT INTO music_artist(SHA256, size, owner_id, artist)" +
                                "VALUES(?, ?, ?, ?)";
                        for (String artist : item.getArtist()) {
                            ps = con.prepareStatement(sql);
                            ps.setString(1, item.getSHA256());
                            ps.setLong(2, item.getSize());
                            ps.setInt(3, item.getOwnerId());
                            ps.setString(4, artist);
                            ps.executeUpdate();
                            DBUtil.close(ps);
                        }
                    }
                    break;
                case "genre":
                    if (item.getGenre() != null) {
                        con = DBUtil.getConnection();
                        String sql = "INSERT INTO music_genre(SHA256, size, owner_id, genre)" +
                                "VALUES(?, ?, ?, ?)";
                        for (String genre : item.getGenre()) {
                            ps = con.prepareStatement(sql);
                            ps.setString(1, item.getSHA256());
                            ps.setLong(2, item.getSize());
                            ps.setInt(3, item.getOwnerId());
                            ps.setString(4, genre);
                            ps.executeUpdate();
                            DBUtil.close(ps);
                        }
                    }
                    break;
                default: throw new IllegalArgumentException("Info type should be either \"artist\" or \"genre\".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }
}
