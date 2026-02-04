package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

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

    public static void borrowBook(int studentId, int bookId, int quantity) {
        Connection conn = getConnection();

        try {
            String checkQuery = "SELECT available_count FROM Books WHERE book_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("available_count") >= quantity) {
                String borrowQuery = "INSERT INTO Borrowing_Status (student_id, book_id, quantity, issue_date) VALUES (?, ?, ?, CURDATE())";
                PreparedStatement borrowStmt = conn.prepareStatement(borrowQuery);
                borrowStmt.setInt(1, studentId);
                borrowStmt.setInt(2, bookId);
                borrowStmt.setInt(3, quantity);
                borrowStmt.executeUpdate();

                String updateStock = "UPDATE Books SET available_count = available_count - ? WHERE book_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateStock);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, bookId);
                updateStmt.executeUpdate();

                System.out.println("Book borrowed successfully!");
            } else {
                System.out.println("Not enough copies available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try { conn.close(); } catch (SQLException ignored) {}
    }
}

