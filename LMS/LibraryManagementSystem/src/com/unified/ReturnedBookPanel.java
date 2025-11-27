package com.unified;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lms.DBConnection;

import java.awt.*;
import java.sql.*;

public class ReturnedBookPanel extends JPanel {
    private JTable table;

    public ReturnedBookPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Returned Books");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadReturnedBooks();
    }

    private void loadReturnedBooks() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
                SELECT i.issue_id, b.title, m.name, i.issue_date, i.return_date
                FROM issue i
                JOIN books b ON i.book_id = b.book_id
                JOIN members m ON i.member_id = m.member_id
                WHERE i.return_date IS NOT NULL
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Issue ID", "Book Title", "Member Name", "Issue Date", "Return Date"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("issue_id"),
                    rs.getString("title"),
                    rs.getString("name"),
                    rs.getDate("issue_date"),
                    rs.getDate("return_date")
                });
            }

            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading returned books.");
        }
    }
}
