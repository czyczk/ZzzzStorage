package util;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Database Util
 * Created by czyczk on 2017-6-17.
 */
public class DBUtil {
    private static final String DB_INIT_DIRECTORY = Paths.get(System.getProperty("user.dir") + "/database_init").toString();
    private static final String DB_NAME = "zzzz_media_vault";
    // Tables needed
    private static final String[] TABLE_NAMES = {
            "mv_user",
            "tv_show", "tv_show_genre", "episode",
            "movie", "movie_genre", "movie_director",
            "music", "music_genre", "music_artist"
    };

    public static void createDatabase() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:derby:" + DB_NAME + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
//            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:derby:" + DB_NAME);
        } catch (SQLException e) {
            if (e.getErrorCode() == 40000) {
                // Handle error 40000: Database not found  -> Create the database
                createDatabase();
                // Retry getting connection
                return getConnection();
            } else {
                e.printStackTrace();
            }
        }
        return con;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean hasTable(String tableName) {
        Connection con = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            rs = con.getMetaData().getTables(
                    null, null, tableName.toUpperCase(), null
            );
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(con);
        }
        return false;
    }

    private static List<String> checkForMissingTables() {
        List<String> missingTables = new ArrayList<>();
        for (String tableName : TABLE_NAMES) {
            if (!hasTable(tableName)) {
                System.out.println("Table \"" + tableName + "\" doesn't exist.");
                missingTables.add(tableName);
            }
        }
        return missingTables;
    }

    public static void createTablesIfNotExist() {
        // Return if all tables exist
        List<String> missingTables = checkForMissingTables();
        if (missingTables.size() == 0) return;

        Connection con = null;
        try {
            con = getConnection();
            for (String tableName : missingTables) {
                PreparedStatement ps = null;
                System.out.println("Creating table \"" + tableName + "\".");
                String path = DB_INIT_DIRECTORY + "/" + tableName + ".sql";
                String sql = FileUtil.readTextFile(path);
                ps = con.prepareStatement(sql);
                ps.execute();
                close(ps);
                // Create a default admin if (tableName == "user")
                if (tableName.equals("mv_user")) {
                    String extraSql = "INSERT INTO mv_usr VALUES (0, 'admin', 'admin', 1)";
                    ps = con.prepareStatement(extraSql);
                    ps.executeUpdate();
                    close(ps);
                }
                System.out.println("Finished creating table \"" + tableName + "\".");
            }
            System.out.println("All tables have been created.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
    }

//    public static void test() {
//        int numOfTables = countNumberOfTables();
//        System.out.println("There are " + numOfTables + " table(s).");
//        // Return if there are tables
//        if (numOfTables > 0) return;
//
//        Connection con = null;
//        try {
//            con = getConnection();
//            String tableName = "User";
//            PreparedStatement ps = null;
//            System.out.println("Creating table `" + tableName + "`.");
//            String path = DB_INIT_DIRECTORY + "/" + tableName + ".sql";
//            String sql = FileUtil.readTextFile(path);
//            System.out.println(sql);
//            ps = con.prepareStatement(sql);
//            ps.executeUpdate();
//            close(ps);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            close(con);
//        }
//
//        numOfTables = countNumberOfTables();
//        System.out.println("There are " + numOfTables + " table(s).");
//    }

//    public static void
}
