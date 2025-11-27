package com.lms;

import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private JButton btnAddBook, btnSearchBook, btnIssueBook, btnReturnBook, btnRegisterMember;

    public Dashboard() {
        setTitle("Library Dashboard");
        setSize(400, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnAddBook = new JButton("Add Book");
        btnAddBook.setBounds(100, 30, 200, 40);
        add(btnAddBook);

        btnSearchBook = new JButton("Search Books");
        btnSearchBook.setBounds(100, 90, 200, 40);
        add(btnSearchBook);

        btnIssueBook = new JButton("Issue Book");
        btnIssueBook.setBounds(100, 150, 200, 40);
        add(btnIssueBook);

        btnReturnBook = new JButton("Return Book");
        btnReturnBook.setBounds(100, 210, 200, 40);
        add(btnReturnBook);

        btnRegisterMember = new JButton("Register Member");
        btnRegisterMember.setBounds(100, 270, 200, 40);
        add(btnRegisterMember);

        btnAddBook.addActionListener(e -> new AddBook());
        btnSearchBook.addActionListener(e -> new SearchBook());
        btnIssueBook.addActionListener(e -> new IssueBook());
        btnReturnBook.addActionListener(e -> new ReturnBook());
        btnRegisterMember.addActionListener(e -> new MemberRegister());

        setVisible(true);
    }

    public static void main(String[] args) {
    	
        new Dashboard();
    }
}
