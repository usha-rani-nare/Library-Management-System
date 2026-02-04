package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void viewBooks() {
        Connection conn = getConnection();
        String query = "SELECT * FROM Books";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id") + ", Name: " + rs.getString("book_name") +
                        ", Author: " + rs.getString("author") + ", Available: " + rs.getInt("available_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try { conn.close(); } catch (SQLException ignored) {}
    }
}

