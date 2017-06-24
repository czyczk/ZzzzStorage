package dao;

import model.Music;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by czyczk on 2017-6-24.
 */
public class MusicDao implements ILibraryItemDao<Music> {
    /**
     * Load the music from the database. Null if the item does not exist.
     * @param item A sample item containing the deterministic characteristics of the target item.
     */
    public Music load(Music item) {
        // Check if the sample item contains the deterministic characteristics of the target item
        boolean isDeterministic = isDeterministic(item);
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

            // Append director information
//            result.setDirector(queryAdditionalInfo(result, "director"));
//            // Append genre information
//            result.setGenre(queryAdditionalInfo(result, "genre"));
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
        // TODO
    }

    @Override
    public void delete(Music item) {

    }

    @Override
    public void update(Music oldItem, Music newItem) {

    }

    // Check if the sample item contains the deterministic characteristics of the target item.
    private boolean isDeterministic(Music item) {
        return !(item.getSHA256() == null || item.getSize() == null || item.getOwnerId() == null);
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
}
