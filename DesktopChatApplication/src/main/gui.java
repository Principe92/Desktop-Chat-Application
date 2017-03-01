package main;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JEditorPane;

public class gui {

	private JFrame frmChatapp;
	private JTextField textField;

	private IGuiListener listener;
	private JTextArea display;
	
	private JScrollPane ext_display;


	
	/*
	 * Things to add:
	 * inviteButton
	 * exitBUtton
	 * whisper menu
	 * kicoff menu
	 * */
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				//	gui window = new gui();
				//	window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public gui(IGuiListener listener) {
		this.listener = listener;
		initialize();
		//client = Client.getInstance();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmChatapp = new JFrame();
		frmChatapp.setTitle("Chatapp");
		frmChatapp.setBounds(100, 100, 580, 299);
		frmChatapp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatapp.getContentPane().setLayout(null);
		
		JButton btnSubmit = new JButton("Send");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//send
				String text = textField.getText();
				if(text.isEmpty()) return;

				
				listener.sendText(text);
				displayMessage(text);
				clearText();
				
			}
		});
		
		btnSubmit.setBounds(311, 228, 97, 23);
		frmChatapp.getContentPane().add(btnSubmit);
		
		textField = new JTextField();
		textField.setBounds(33, 229, 254, 21);
		frmChatapp.getContentPane().add(textField);
		textField.setColumns(10);
		
		display = new JTextArea();
		
		display.setBounds(33, 10, 375, 208);
		
		ext_display = new JScrollPane(display);
		ext_display.setBounds(33,25,375,190);
		
		frmChatapp.getContentPane().add(ext_display);
		
		JLabel usr_lbl = new JLabel("User Online");
		usr_lbl.setBounds(420, 6, 112, 21);
		frmChatapp.getContentPane().add(usr_lbl);
		
		JLabel chat_lbl = new JLabel("Chat");
		chat_lbl.setBounds(35, 10, 373, 13);
		frmChatapp.getContentPane().add(chat_lbl);
		
		JTextArea usr_account_list = new JTextArea();
		usr_account_list.setBounds(420, 26, 97, 189);
		JScrollPane usr_scrpane = new JScrollPane(usr_account_list);
		usr_scrpane.setBounds(420, 25, 120, 190);
		frmChatapp.getContentPane().add(usr_scrpane);
		
		JButton exitChatBtn = new JButton("Exit");
		exitChatBtn.setBounds(420, 228, 62, 23);
		exitChatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exit the chatroom -- leave for now
			}
		});
		frmChatapp.getContentPane().add(exitChatBtn);
		
		JButton kickoutBtn = new JButton("Ban");
		kickoutBtn.setBounds(485, 228, 55, 23);
		frmChatapp.getContentPane().add(kickoutBtn);
		
		frmChatapp.setVisible(true);
	}

	protected void clearText() {
		// TODO Auto-generated method stub
		textField.setText("");
	}

	public void displayMessage(String message) {
		// TODO Auto-generated method stub
		display.append(String.format("%s\n", message));
	}
}
