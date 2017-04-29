package gui;

import model.AccountDB;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class displays the login window
 */
public class LoginWindow {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;
    private User user;
    private AccountDB accounts;

    /**
     * Constructs a new LoginWindow
     *
     * @param user
     */
    public LoginWindow(final User user, AccountDB accounts) {
        this.user = user;
        this.accounts = accounts;
        frame = new JFrame();
        frame.setLayout(new GridLayout(3, 2));

        //create text fields, labels, and buttons
        usernameField = new JTextField(25);
        passwordField = new JPasswordField(25);
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create New Account");

        //add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //CREATE ACCOUNT WINDOW
                //new CreateAccountWindow(user);
            }
        });

        //action listener needs to be implemented
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               /* boolean exists = accounts.checkCredentials(usernameField.getText(), passwordField.getPassword());
                //if true, isLoggedIn = true
                if (exists) {
                    //if false, System.out.println("Login attempt failed");
                }
               // user.setisLoggedIn(true);*/
            }
        });

        //add components to frame
        frame.add(new JLabel("Password"));
        frame.add(usernameField);

        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(createAccountButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
