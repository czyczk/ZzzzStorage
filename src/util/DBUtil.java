package util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
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
//    private static final String DB_INIT_DIRECTORY = Paths.get(System.getProperty("user.dir") + "/database_init").toString();
    private static final String DB_NAME = "zzzz_media_vault";
    private static String getRootOfClasses() {
        String root = null;
        try {
            root = URLDecoder.decode(
                    Paths.get(
                            (DBUtil.class.getResource("/").getPath().substring(1))
                    ).toString(),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return root;
    }
    private static String getRootOfProject() {
        return getRootOfClasses().replace("WEB-INF/classes/", "");
    }
    public static String getDbDirectory() {
        return getRootOfProject() + "/database";
    }
    public static String getFileDbDirectory() {
        return getDbDirectory() + "/files";
    }
    private static String getDbInitDirectory() {
        return getRootOfClasses() + "/database_init";
    }
    // Tables needed
    private static final String[] TABLE_NAMES = {
            "mv_user",
            "tv_show", "tv_show_genre", "episode",
            "movie", "movie_genre", "movie_director",
            "music", "music_genre", "music_artist"
    };

    private static Connection createDatabase() {
        Connection con = null;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            con = DriverManager.getConnection("jdbc:derby:" + getDbDirectory() + "/" + DB_NAME + ";create=true");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
//            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            con = DriverManager.getConnection("jdbc:derby:" + getDbDirectory() + "/" + DB_NAME);
        } catch (SQLException e) {
            if (e.getErrorCode() == 40000) {
                // Handle error 40000: Database not found  -> Create the database
                return createDatabase();
//                // Retry getting connection
//                return getConnection();
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

    private static boolean hasTable(String tableName) {
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

    // Check for the existence of the tables passed in. Missing tables returned.
    private static List<String> hasTables(String[] tableNames) {
        Connection con = null;
        ResultSet rs = null;
        List<String> missingTables = new ArrayList<String>();

        try {
            con = getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            for (String tableName : tableNames) {
                rs = metaData.getTables(null, null, tableName.toUpperCase(), null);
                if (!rs.next()) {
                    missingTables.add(tableName);
                }
                close(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
        return missingTables;
    }

    private static List<String> checkForMissingTables() {
        List<String> missingTables = hasTables(TABLE_NAMES);
        for (String tableName : missingTables) {
            System.out.println("Table \"" + tableName + "\" doesn't exist.");
        }
        return missingTables;
    }

    public static void createTablesIfNotExist() {
        // Return if all tables exist
        List<String> missingTables = checkForMissingTables();
        if (missingTables.size() == 0) {
            System.out.println("Database set.");
            return;
        }

        Connection con = null;
        try {
            con = getConnection();
            for (String tableName : missingTables) {
                PreparedStatement ps = null;
                System.out.println("Creating table \"" + tableName + "\".");
                String path = getDbInitDirectory() + "/" + tableName + ".sql";
                String sql = FileUtil.readTextFile(path);
                ps = con.prepareStatement(sql);
                ps.execute();
                close(ps);
                // Create a default admin if (tableName == "user")
//                if (tableName.equals("mv_user")) {
//                    String extraSql = "INSERT INTO mv_user VALUES (0, 'admin', 'admin', 'admin', 1)";
//                    ps = con.prepareStatement(extraSql);
//                    ps.executeUpdate();
//                    close(ps);
//                    System.out.println("Admin account created.");
//                }
                System.out.println("Finished creating table \"" + tableName + "\".");
            }
            System.out.println("All tables have been created.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(con);
        }
    }

    public static void createFileDirectoryIfNotExists() {
        File file = new File(getFileDbDirectory());
        if (file.exists() && file.isFile())
            file.delete();
        else
            file.mkdir();
    }
}
