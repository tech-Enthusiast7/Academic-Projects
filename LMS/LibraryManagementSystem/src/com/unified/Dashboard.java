package com.unified;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public Dashboard() {
        setTitle("Library Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Layout for whole frame
        setLayout(new BorderLayout());

        // Left panel: menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        menuPanel.setBackground(new Color(33, 150, 243)); // Blue side panel

        // Buttons for navigation
        JButton btnAddBook = createMenuButton("Add Book");
        JButton btnSearchBook = createMenuButton("Search Books");
        JButton btnIssueBook = createMenuButton("Issue Book");
        JButton btnReturnBook = createMenuButton("Return Book");
        JButton btnRegisterMember = createMenuButton("Register Member");
        JButton btnIssuedBooks = createMenuButton("Issued Books");
        JButton btnReturnedBooks = createMenuButton("Returned Books");
        JButton btnShowMembers = createMenuButton("Show Members");


        // Add buttons to menu panel
        menuPanel.add(btnAddBook);
        menuPanel.add(btnSearchBook);
        menuPanel.add(btnIssueBook);
        menuPanel.add(btnReturnBook);
        menuPanel.add(btnRegisterMember);
        menuPanel.add(btnIssuedBooks);
        menuPanel.add(btnReturnedBooks);
        menuPanel.add(btnShowMembers);
        
        add(menuPanel, BorderLayout.WEST);

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add all "screens" as cards
        mainPanel.add(new AddBookPanel(), "AddBook");
        mainPanel.add(new SearchBookPanel(), "SearchBook");
        mainPanel.add(new IssueBookPanel(), "IssueBook");
        mainPanel.add(new ReturnBookPanel(), "ReturnBook");
        mainPanel.add(new MemberRegisterPanel(), "MemberRegister");
        mainPanel.add(new IssuedBookPanel(), "IssuedBooks");
        mainPanel.add(new ReturnedBookPanel(), "ReturnedBooks");
        mainPanel.add(new ShowMemberPanel(), "ShowMembers");



        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        btnAddBook.addActionListener(e -> cardLayout.show(mainPanel, "AddBook"));
        btnSearchBook.addActionListener(e -> cardLayout.show(mainPanel, "SearchBook"));
        btnIssueBook.addActionListener(e -> cardLayout.show(mainPanel, "IssueBook"));
        btnReturnBook.addActionListener(e -> cardLayout.show(mainPanel, "ReturnBook"));
        btnRegisterMember.addActionListener(e -> cardLayout.show(mainPanel, "MemberRegister"));
        btnIssuedBooks.addActionListener(e -> cardLayout.show(mainPanel, "IssuedBooks"));
        btnReturnedBooks.addActionListener(e -> cardLayout.show(mainPanel, "ReturnedBooks"));
        btnShowMembers.addActionListener(e -> cardLayout.show(mainPanel, "ShowMembers"));



        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(25, 118, 210));
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        return button;
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
