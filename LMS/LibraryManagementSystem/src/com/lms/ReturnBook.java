package com.lms;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ReturnBook extends JFrame {
    private JTextField txtBookId, txtMemberId;
    private JButton btnReturn;

    public ReturnBook() {
        setTitle("Return Book");
        setSize(350, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblBookId = new JLabel("Book ID:");
        lblBookId.setBounds(30, 30, 100, 25);
        add(lblBookId);

        txtBookId = new JTextField();
        txtBookId.setBounds(130, 30, 150, 25);
        add(txtBookId);

        JLabel lblMemberId = new JLabel("Member ID:");
        lblMemberId.setBounds(30, 70, 100, 25);
        add(lblMemberId);

        txtMemberId = new JTextField();
        txtMemberId.setBounds(130, 70, 150, 25);
        add(txtMemberId);

        btnReturn = new JButton("Return Book");
        btnReturn.setBounds(100, 120, 130, 30);
        add(btnReturn);

        btnReturn.addActionListener(e -> returnBook());

        setVisible(true);
    }

    private void returnBook() {
        int bookId = Integer.parseInt(txtBookId.getText());
        int memberId = Integer.parseInt(txtMemberId.getText());

        try (Connection conn = DBConnection.getConnection()) {
            // Check if book was issued
            PreparedStatement checkIssue = conn.prepareStatement(
                "SELECT * FROM issue WHERE book_id = ? AND member_id = ? AND return_date IS NULL");
            checkIssue.setInt(1, bookId);
            checkIssue.setInt(2, memberId);
            ResultSet rs = checkIssue.executeQuery();

            if (rs.next()) {
                // Update return date
                PreparedStatement returnStmt = conn.prepareStatement(
                        "UPDATE issue SET return_date = CURDATE() WHERE book_id = ? AND member_id = ?");
                returnStmt.setInt(1, bookId);
                returnStmt.setInt(2, memberId);
                returnStmt.executeUpdate();

                // Increase quantity
                PreparedStatement updateQty = conn.prepareStatement(
                        "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?");
                updateQty.setInt(1, bookId);
                updateQty.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No issued record found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error returning book.");
        }
    }

    public static void main(String[] args) {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        new ReturnBook();
    }
}

