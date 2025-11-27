package com.unified;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.lms.DBConnection;

import java.awt.*;
import java.sql.*;

public class ShowMemberPanel extends JPanel {
    private JTable table;

    public ShowMemberPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("All Members");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(lblTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        loadMembers();
    }

    private void loadMembers() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM members";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Member ID", "Name", "Phone"}, 0
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("phone")
                });
            }

            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching members.");
        }
    }
}

