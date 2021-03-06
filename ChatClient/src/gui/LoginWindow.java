package gui;

import listener.AccountListener;
import main.Util;
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
    //suppress "user is unused" warning - it is used in an action listener
    @SuppressWarnings("unused")
    private AccountDB accounts;
    //suppress "user is unused" warning - it is used in an action listener
    @SuppressWarnings("unused")


    private AccountListener acctListener;

    /**
     * Constructs a new LoginWindow
     */
    public LoginWindow(AccountDB accounts, AccountListener acctListener) {
        this.accounts = accounts;
        this.acctListener = acctListener;
        frame = new JFrame();
        frame.setLayout(new GridLayout(3, 2));
        frame.setTitle("ChatApp");
        frame.setIconImage(new ImageIcon(this.getClass().getResource(Util.fillIconPath("join.png"))).getImage());

        //create text fields, labels, and buttons
        usernameField = new JTextField(25);
        passwordField = new JPasswordField(25);
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create New Account");

        //add action listeners to buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User exists = accounts.checkCredentials(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                if (exists != null) {
                    frame.dispose();
                    acctListener.loginAccepted(exists);
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect username or password.");
                }
            }
        });

        //action listener needs to be implemented
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CreateAccountWindow(accounts, acctListener);
            }
        });

        //add components to frame
        frame.add(new JLabel("Username"));
        frame.add(usernameField);
        frame.add(new JLabel("Password"));
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(createAccountButton);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
