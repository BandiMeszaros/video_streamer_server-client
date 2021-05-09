package videoServer.db;

import java.sql.*;

public class SQL_handler {


    private final Connection sql_conn;

    public SQL_handler() {
        String connectionString = "jdbc:sqlite:video_db/movies.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sql_conn = conn;
        }

    }

    public String selectedVideo(int videoId) {
        String videoRoot = "";
        try (Statement stmt = sql_conn.createStatement()) {
            String sqlString = "SELECT root FROM videos WHERE videoId=" + videoId;
            ResultSet rs = stmt.executeQuery(sqlString);
            if (rs.next()) {
                videoRoot = rs.getString("root");
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return videoRoot;
    }

    public String listAllVideos() {
        StringBuilder returnValue = new StringBuilder("Id\tTitle\n");
        try (Statement stmt = sql_conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT videoId, Title FROM videos");
            while (rs.next()) {
                returnValue.append(rs.getInt("videoId")).append("\t").append(rs.getString("Title")).append("\n");
            }
            rs.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return returnValue.toString();
    }

    public void close_connection()
    {
        try {
            sql_conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
