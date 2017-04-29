package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import model.User;
import model.AccountDB;

/**
 * This class displays the login window
 */
public class LoginWindow {
	private Jframe frame;

	/**
	 * Constructs a new LoginWindow
	 * @param user
	 */
	public LoginWindow(AccountDB accounts, AccountListener acctListener) {
		this.accounts = accounts;
        this.acctListener = acctListener;
		frame = new JFrame();
		frame.setLayout(new GridLayout(3,2));
		
		//create text fields, labels, and buttons
		usernameField = new JTextField(25);
		passwordField = new JPasswordField(25);
		loginButton = new JButton("Login");
		createAccountButton = new JButton("Create New Account");
		
		//add action listeners to buttons
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				User exists = accounts.checkCredentials(usernameField.getText(), passwordField.getPassword());
				if (exists!=null) {
                    frame.dispose();
                    acctListener.loginAccepted(exists);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Incorrect username or password.");
                }
			}
		});
		
		//action listener needs to be implemented
		createAccountButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton createAccountButton;
	//suppress "user is unused" warning - it is used in an action listener
	@SuppressWarnings("unused")
	private AccountDB accounts;
	//suppress "user is unused" warning - it is used in an action listener
	@SuppressWarnings("unused")
    public interface AccountListener{
        void loginAccepted(User user);
    }
    private AccountListener acctListener;
    
}
