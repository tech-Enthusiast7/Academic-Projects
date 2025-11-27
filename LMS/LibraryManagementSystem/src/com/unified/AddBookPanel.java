package com.unified;

import javax.swing.*;

import com.lms.DBConnection;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBookPanel extends JPanel {
    private JTextField txtTitle, txtAuthor, txtPublisher, txtQty;
    private JButton btnSave;

    public AddBookPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("Title:");
        styleLabel(lblTitle);
        txtTitle = new JTextField(20);

        JLabel lblAuthor = new JLabel("Author:");
        styleLabel(lblAuthor);
        txtAuthor = new JTextField(20);

        JLabel lblPublisher = new JLabel("Publisher:");
        styleLabel(lblPublisher);
        txtPublisher = new JTextField(20);

        JLabel lblQty = new JLabel("Quantity:");
        styleLabel(lblQty);
        txtQty = new JTextField(20);

        btnSave = new JButton("Save Book");
        styleButton(btnSave);

        btnSave.addActionListener(e -> saveBook());

        gbc.gridx = 0; gbc.gridy = 0; add(lblTitle, gbc);
        gbc.gridx = 1; add(txtTitle, gbc);
        gbc.gridx = 0; gbc.gridy++; add(lblAuthor, gbc);
        gbc.gridx = 1; add(txtAuthor, gbc);
        gbc.gridx = 0; gbc.gridy++; add(lblPublisher, gbc);
        gbc.gridx = 1; add(txtPublisher, gbc);
        gbc.gridx = 0; gbc.gridy++; add(lblQty, gbc);
        gbc.gridx = 1; add(txtQty, gbc);
        gbc.gridx = 1; gbc.gridy++; add(btnSave, gbc);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(33, 37, 41));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(76, 175, 80)); // green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
    }

    private void saveBook() {
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String publisher = txtPublisher.getText();
        int qty = Integer.parseInt(txtQty.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO books (title, author, publisher, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, publisher);
            stmt.setInt(4, qty);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
                txtTitle.setText("");
                txtAuthor.setText("");
                txtPublisher.setText("");
                txtQty.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding book.");
        }
    }
}
