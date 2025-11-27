package com.lms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class SearchBook extends JFrame {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable table;

    public SearchBook() {
        setTitle("Search Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblSearch = new JLabel("Enter Title:");
        lblSearch.setBounds(20, 20, 100, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(120, 20, 250, 25);
        add(txtSearch);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(400, 20, 100, 25);
        add(btnSearch);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 60, 540, 280);
        add(scrollPane);

        btnSearch.addActionListener(e -> searchBook());

        setVisible(true);
    }

    private void searchBook() {
        String keyword = txtSearch.getText().trim();

        try (Connection conn = DBConnection.getConnection()) {
            String sql;
            PreparedStatement stmt;

            if (keyword.isEmpty()) {
                // show all books if no search text
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

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No books found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching books.");
        }
    }


    public static void main(String[] args) {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        new SearchBook();
    }
}

