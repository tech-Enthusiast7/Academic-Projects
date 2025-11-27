package com.unified;

import javax.swing.*;

import com.lms.DBConnection;

import java.awt.*;
import java.sql.*;

public class IssueBookPanel extends JPanel {
    private JTextField txtBookId, txtMemberId;
    private JButton btnIssue;

    public IssueBookPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblBookId = new JLabel("Book ID:");
        txtBookId = new JTextField(15);
        JLabel lblMemberId = new JLabel("Member ID:");
        txtMemberId = new JTextField(15);
        btnIssue = new JButton("Issue Book");

        btnIssue.addActionListener(e -> issueBook());

        gbc.gridx = 0; gbc.gridy = 0; add(lblBookId, gbc);
        gbc.gridx = 1; add(txtBookId, gbc);

        gbc.gridx = 0; gbc.gridy++; add(lblMemberId, gbc);
        gbc.gridx = 1; add(txtMemberId, gbc);

        gbc.gridx = 1; gbc.gridy++; add(btnIssue, gbc);
    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText());
            int memberId = Integer.parseInt(txtMemberId.getText());

            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement checkBook = conn.prepareStatement(
                    "SELECT quantity FROM books WHERE book_id = ?");
                checkBook.setInt(1, bookId);
                ResultSet rs = checkBook.executeQuery();

                if (rs.next()) {
                    int qty = rs.getInt("quantity");
                    if (qty > 0) {
                        PreparedStatement issueStmt = conn.prepareStatement(
                            "INSERT INTO issue (book_id, member_id, issue_date) VALUES (?, ?, CURDATE())");
                        issueStmt.setInt(1, bookId);
                        issueStmt.setInt(2, memberId);
                        issueStmt.executeUpdate();

                        PreparedStatement updateQty = conn.prepareStatement(
                            "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?");
                        updateQty.setInt(1, bookId);
                        updateQty.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Book Issued!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book out of stock!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error issuing book.");
        }
    }
}
