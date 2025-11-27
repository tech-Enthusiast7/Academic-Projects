package com.unified;

import javax.swing.*;

import com.lms.DBConnection;

import java.awt.*;
import java.sql.*;

public class ReturnBookPanel extends JPanel {
    private JTextField txtBookId, txtMemberId;
    private JButton btnReturn;

    public ReturnBookPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblBookId = new JLabel("Book ID:");
        txtBookId = new JTextField(15);
        JLabel lblMemberId = new JLabel("Member ID:");
        txtMemberId = new JTextField(15);
        btnReturn = new JButton("Return Book");

        btnReturn.addActionListener(e -> returnBook());

        gbc.gridx = 0; gbc.gridy = 0; add(lblBookId, gbc);
        gbc.gridx = 1; add(txtBookId, gbc);

        gbc.gridx = 0; gbc.gridy++; add(lblMemberId, gbc);
        gbc.gridx = 1; add(txtMemberId, gbc);

        gbc.gridx = 1; gbc.gridy++; add(btnReturn, gbc);
    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(txtBookId.getText());
            int memberId = Integer.parseInt(txtMemberId.getText());

            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement checkIssue = conn.prepareStatement(
                    "SELECT * FROM issue WHERE book_id = ? AND member_id = ? AND return_date IS NULL");
                checkIssue.setInt(1, bookId);
                checkIssue.setInt(2, memberId);
                ResultSet rs = checkIssue.executeQuery();

                if (rs.next()) {
                    PreparedStatement returnStmt = conn.prepareStatement(
                        "UPDATE issue SET return_date = CURDATE() WHERE book_id = ? AND member_id = ?");
                    returnStmt.setInt(1, bookId);
                    returnStmt.setInt(2, memberId);
                    returnStmt.executeUpdate();

                    PreparedStatement updateQty = conn.prepareStatement(
                        "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?");
                    updateQty.setInt(1, bookId);
                    updateQty.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book Returned!");
                } else {
                    JOptionPane.showMessageDialog(this, "No issued record found.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error returning book.");
        }
    }
}
