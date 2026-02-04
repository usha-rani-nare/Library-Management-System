package Library;

import java.sql.*;

public class StudentManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void viewStudents() {
        Connection conn = getConnection();
        String query = "SELECT * FROM Students";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("student_id") + ", Name: " + rs.getString("name") +
                        ", Dept: " + rs.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try { conn.close(); } catch (SQLException ignored) {}
    }
}






	