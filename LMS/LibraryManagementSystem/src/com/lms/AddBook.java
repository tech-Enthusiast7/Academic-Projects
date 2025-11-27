package com.lms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBook extends JFrame {
    private JTextField txtTitle, txtAuthor, txtPublisher, txtQty;
    private JButton btnSave;

    public AddBook() {
        setTitle("Add New Book");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(245, 245, 245)); // Light grey background
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
        btnSave.addActionListener(e -> addBook());

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        panel.add(lblTitle, gbc);
        gbc.gridx = 1; panel.add(txtTitle, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblAuthor, gbc);
        gbc.gridx = 1; panel.add(txtAuthor, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblPublisher, gbc);
        gbc.gridx = 1; panel.add(txtPublisher, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblQty, gbc);
        gbc.gridx = 1; panel.add(txtQty, gbc);

        gbc.gridx = 1; gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSave, gbc);

        add(panel);
        setVisible(true);
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(33, 37, 41));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(33, 150, 243)); // Material Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
    }

    private void addBook() {
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

    public static void main(String[] args) {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        SwingUtilities.invokeLater(AddBook::new);
    }
}
