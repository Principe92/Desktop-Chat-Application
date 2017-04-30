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
 * This class displays a window for the user to create a new account
 */
public class CreateAccountWindow {

    private JFrame frame;
    private JTextField usernameField;
    private JTextField nickField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton submitButton;
    private JButton cancelButton;
    @SuppressWarnings("unused")
    private AccountDB accounts;
    private AccountListener acctListener;

    /**
     * Constructs a new CreateAccountWindow
     */
    public CreateAccountWindow(AccountDB accounts, AccountListener acctListener) {
        this.accounts = accounts;
        this.acctListener = acctListener;
        frame = new JFrame();
        frame.setLayout(new GridLayout(5, 2));
        frame.setTitle("ChatApp");
        frame.setIconImage(new ImageIcon(this.getClass().getResource(Util.fillIconPath("join.png"))).getImage());


        //create components
        usernameField = new JTextField(20);
        nickField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailField = new JTextField(20);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");

        //add action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!accounts.userIsAvailable(usernameField.getText())) {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                } else {
                    frame.dispose();
                    User newUser = accounts.createAccount(String.valueOf(passwordField.getPassword()), usernameField.getText(),
                            nickField.getText(), emailField.getText());
                    acctListener.loginAccepted(newUser);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //set frame properties and add components
        frame.setLayout(new GridLayout(5, 2));
        frame.add(new JLabel("Username"));
        frame.add(usernameField);
        frame.add(new JLabel("Nickname"));
        frame.add(nickField);
        frame.add(new JLabel("Password"));
        frame.add(passwordField);
        frame.add(new JLabel("Email Address"));
        frame.add(emailField);
        frame.add(submitButton);
        frame.add(cancelButton);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
