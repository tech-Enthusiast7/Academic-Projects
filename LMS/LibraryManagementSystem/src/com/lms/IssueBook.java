package com.lms;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class IssueBook extends JFrame {
    private JTextField txtBookId, txtMemberId;
    private JButton btnIssue;

    public IssueBook() {
        setTitle("Issue Book");
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

        btnIssue = new JButton("Issue Book");
        btnIssue.setBounds(100, 120, 120, 30);
        add(btnIssue);

        btnIssue.addActionListener(e -> issueBook());

        setVisible(true);
    }

    private void issueBook() {
        int bookId = Integer.parseInt(txtBookId.getText());
        int memberId = Integer.parseInt(txtMemberId.getText());

        try (Connection conn = DBConnection.getConnection()) {
            // Check if book exists and is in stock
            PreparedStatement checkBook = conn.prepareStatement("SELECT quantity FROM books WHERE book_id = ?");
            checkBook.setInt(1, bookId);
            ResultSet rs = checkBook.executeQuery();

            if (rs.next()) {
                int qty = rs.getInt("quantity");
                if (qty > 0) {
                    // Issue the book
                    PreparedStatement issueStmt = conn.prepareStatement(
                            "INSERT INTO issue (book_id, member_id, issue_date) VALUES (?, ?, CURDATE())");
                    issueStmt.setInt(1, bookId);
                    issueStmt.setInt(2, memberId);
                    issueStmt.executeUpdate();

                    // Decrease quantity
                    PreparedStatement updateQty = conn.prepareStatement("UPDATE books SET quantity = quantity - 1 WHERE book_id = ?");
                    updateQty.setInt(1, bookId);
                    updateQty.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Book out of stock.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error issuing book.");
        }
    }

    public static void main(String[] args) {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        new IssueBook();
    }
}

