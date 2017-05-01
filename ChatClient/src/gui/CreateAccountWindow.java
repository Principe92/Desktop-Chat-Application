package gui;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import listener.AccountListener;
import main.Constant;
import main.Util;
import model.AccountDB;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.Constant.PWDLEN;

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

        //add action listeners to check the enabled input for creating the account
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//Check the availability of the user
                if (!accounts.userIsAvailable(usernameField.getText())) {
                    JOptionPane.showMessageDialog(frame, Constant.EXISTING_USERNAME);
                }
                //Check validity of the email address input
                else if (!checkEmailValidity(emailField.getText())) {
                    JOptionPane.showMessageDialog(frame, Constant.INVALID_EMAIL);
                }
                
                //Check validity of the password whether it contains special characters or not
                else if (!checkPwdValidity(String.valueOf(passwordField.getPassword()))) {
                	JOptionPane.showMessageDialog(frame, Constant.INVALID_PWD);
                }
                
                else {
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
    /*
    * checking the validity (length and special characters of the pwd entered
    * */
    private boolean checkPwdValidity(String pwdField) {
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(pwdField);
        boolean b = (pwdField.length() < Constant.PWDLEN || !m.find()) ? false : true;
        
        
        return b;
    }
    
    /*
     * checking the validity of the emailaddress
     * */
     private boolean checkEmailValidity(String emailField) {
         boolean b = (emailField.endsWith(Constant.EMAIL_DOM1) 
        		 || emailField.endsWith(Constant.EMAIL_DOM2)
        		 && emailField.contains(Constant.EMAIL_ATTRIBUTE)) ? true : false;
         return b;
     }
     
    
    
}
