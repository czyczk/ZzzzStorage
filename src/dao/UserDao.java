package dao;

import com.sun.org.apache.regexp.internal.RE;
import module.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by czyczk on 2017-6-18.
 */
public class UserDao implements IUserDao {
    // Package-accessible constructor
    UserDao() { }

    // Maintain the number of total users
    private Integer numTotalUsers = null;
    public int getNumTotalUsers() {
        if (numTotalUsers == null) {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sql =
                    "SELECT count(*) " +
                    "FROM mv_user";
            try {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    numTotalUsers = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(rs);
                DBUtil.close(ps);
                DBUtil.close(con);
            }
        }
        return numTotalUsers;
    }
    private int incrementAndGetNumTotalUsers() {
        numTotalUsers = getNumTotalUsers() + 1;
        return numTotalUsers;
    }

    /**
     * Add a user to the database.
     * @param user The user to be added.
     */
    @Override
    public void add(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        con = DBUtil.getConnection();
        // Check if the email address has been used
        String sql =
                "SELECT count(*) " +
                "FROM mv_user " +
                "WHERE email = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getEmail().toLowerCase());
            rs = ps.executeQuery();
            // Throw an exception if the email has been used
            while (rs.next()) {
                if (rs.getInt(1) > 0) throw new UserDaoException("The email address has been used.");
            }
            DBUtil.close(rs);
            DBUtil.close(ps);

            // Insert the user into the database
            sql = "INSERT INTO mv_user VALUES(?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, incrementAndGetNumTotalUsers());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail().toLowerCase());
            ps.setInt(5, 1);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    @Override
    public void delete(int id) {
        // TODO
        throw new NotImplementedException();
    }

    /**
     * Update a user profile. Only the username, the password and the avatar can be updated.
     * @param user A User object containing updated information.
     */
    @Override
    public void update(User user) {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = null;
        String sql =
                "UPDATE mv_user " +
                "SET username = ?, password = ?, avatar_id = ? " +
                "WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAvatarId());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(ps);
            DBUtil.close(con);
        }
    }

    /**
     * Load a user from the database.
     * @param id The ID of the target user to be loaded.
     * @return The target user.
     */
    @Override
    public User load(int id) {
        User result = null;
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM mv_user WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            result = toUser(rs);
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
     * Load a user from the database.
     * @param email The email address of the target user to be loaded.
     * @return The target user.
     */
    @Override
    public User load(String email) {
        User result = null;
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM mv_user WHERE email = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email.toLowerCase());
            rs = ps.executeQuery();
            result = toUser(rs);
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
     * Retrieve a list of all the users.
     * @return A list of all the users.
     */
    @Override
    public List<User> list() {
        List<User> userList = new ArrayList<>();
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM mv_user";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = toUser(rs);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(rs);
            DBUtil.close(ps);
            DBUtil.close(con);
        }
        return userList;
    }

    /**
     * Login. Return the user if success.
     * @param email The email address.
     * @param password The password.
     * @return The user to log into.
     */
    @Override
    public User login(String email, String password) {
        // Throw an exception if the user doesn't exist
        User user = load(email);
        if (user == null) throw new UserDaoException("The user with email address \"" + email + "\" does not exist.");

        // Check if the password is correct
        if (!password.equals(user.getPassword())) {
            throw new UserDaoException("The password is incorrect.");
        }

        // If the verification passes, return the user
        return user;
    }

    // Convert the data on the current cursor of the result set into a User object.
    private User toUser(ResultSet rs) throws SQLException {
        User result = null;
        while (rs.next()) {
            result = new User();
            result.setId(rs.getInt("id"));
            result.setUsername(rs.getString("username"));
            result.setPassword(rs.getString("password"));
            result.setEmail(rs.getString("email"));
            result.setAvatarId(rs.getInt("avatar_id"));
        }
        return result;
    }
}
