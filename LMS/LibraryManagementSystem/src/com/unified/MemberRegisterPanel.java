package com.unified;

import javax.swing.*;

import com.lms.DBConnection;

import java.awt.*;
import java.sql.*;

public class MemberRegisterPanel extends JPanel {
    private JTextField txtName, txtPhone;
    private JButton btnSave;

    public MemberRegisterPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblName = new JLabel("Name:");
        txtName = new JTextField(20);

        JLabel lblPhone = new JLabel("Phone:");
        txtPhone = new JTextField(20);

        btnSave = new JButton("Register Member");
        btnSave.addActionListener(e -> registerMember());

        gbc.gridx = 0; gbc.gridy = 0; add(lblName, gbc);
        gbc.gridx = 1; add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy++; add(lblPhone, gbc);
        gbc.gridx = 1; add(txtPhone, gbc);

        gbc.gridx = 1; gbc.gridy++; add(btnSave, gbc);
    }

    private void registerMember() {
        String name = txtName.getText();
        String phone = txtPhone.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO members (name, phone) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Member registered!");
                txtName.setText("");
                txtPhone.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering member.");
        }
    }
}
