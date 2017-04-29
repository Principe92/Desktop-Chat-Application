package ChatAppGUI;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ChatLibrary.User;

/**
 * This class displays a window for the user to create a new account 
 */
public class CreateAccountWindow {
	
	private Jframe frame;
	/**
	 * Constructs a new CreateAccountWindow
	 * @param user
	 */
	public CreateAccountWindow(final User user, AccountDB accounts) {
		this.user = user;
		this.accounts = accounts;
		repaint();
	}
	
	public void repaint() {
		frame = new JFrame();
		
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
				if(!accouns.userIsAvailable(usernameField.getText())){
					//PICK NEW USERNAME
				}
				else {
					frame.dispose();
				//ID??
					accounts.createAccount(passwordField.getPassword(), usernameField.getText(),
							nickField.getText(), emailField.getText());
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
	
	private JTextField usernameField;
	private JTextField nickField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JButton submitButton;
	private JButton cancelButton;
	@SuppressWarnings("unused")
	private User user;
	private AccountDB accounts;
}
