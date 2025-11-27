package com.unified;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lms.DBConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchBookPanel extends JPanel {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable table;

    public SearchBookPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lblSearch = new JLabel("Search Title:");
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Search");

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchBook());

        // Show all books initially
        searchBook();
    }

    private void searchBook() {
        String keyword = txtSearch.getText().trim();

        try (Connection conn = DBConnection.getConnection()) {
            String sql;
            PreparedStatement stmt;

            if (keyword.isEmpty()) {
                sql = "SELECT * FROM books";
                stmt = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM books WHERE title LIKE ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Title", "Author", "Publisher", "Quantity"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("quantity")
                });
            }

            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching books.");
        }
    }
}
