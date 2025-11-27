package com.lms;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class MemberRegister extends JFrame {
    private JTextField txtName, txtPhone;
    private JButton btnSave;

    public MemberRegister() {
        setTitle("Register Member");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(30, 30, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 30, 200, 25);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(30, 70, 100, 25);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(130, 70, 200, 25);
        add(txtPhone);

        btnSave = new JButton("Save");
        btnSave.setBounds(150, 120, 100, 30);
        add(btnSave);

        btnSave.addActionListener(e -> registerMember());

        setVisible(true);
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

    public static void main(String[] args) {
    	try {
    	    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}

        new MemberRegister();
    }
}
